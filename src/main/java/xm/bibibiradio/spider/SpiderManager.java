package xm.bibibiradio.spider;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.bibibiradio.httpsender.ResponseData;

import xm.bibibiradio.checkpoint.DefaultCheckPointFactory;
import xm.bibibiradio.checkpoint.SpiderCheckPoint;
import xm.bibibiradio.checkpoint.SpiderCheckPointFactory;
import xm.bibibiradio.listener.Listener;
import xm.bibibiradio.listener.Message;
import xm.bibibiradio.listener.NotiferProxy;
import xm.bibibiradio.output.SpiderOutputFactory;
import xm.bibibiradio.output.DefaultSpiderOutputFactory;
import xm.bibibiradio.output.SpiderOutput;
import xm.bibibiradio.policy.DefaultSpiderPolicyFactory;
import xm.bibibiradio.policy.SpiderPolicy;
import xm.bibibiradio.policy.SpiderPolicyFactory;
import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.warphttpsender.WarpedHttpSender;

public class SpiderManager implements Listener {
    final static private Logger LOGGER  = LogFactory.provide(SpiderManager.class);
    static int                  MAXDEEP = 4;

    private SpiderPolicy        policy;
    private UrlPool             urlPool;
    private WarpedHttpSender    httpSender;
    private SpiderOutput        output;
    private SpiderCheckPoint	spiderCheckPoint;
    private SpiderPolicyFactory spiderPolicyFactory;
    private SpiderOutputFactory spiderOutputFactory;
    private SpiderCheckPointFactory spiderCheckPointFactory;
    private CheckPointHandler checkPointHandler;

    private Properties          prop;
    
    public SpiderManager(Properties prop) throws Exception{
    	this.prop=prop;
    	this.spiderPolicyFactory=new DefaultSpiderPolicyFactory();
        this.spiderOutputFactory=new DefaultSpiderOutputFactory();
        this.spiderCheckPointFactory=new DefaultCheckPointFactory();
        init();
    }
    
    public SpiderManager(Properties prop,SpiderPolicyFactory spiderPolicyFactory,SpiderOutputFactory spiderOutputFactory,SpiderCheckPointFactory spiderCheckPointFactory) throws Exception {
        this.prop = prop;
        this.spiderPolicyFactory=spiderPolicyFactory;
        this.spiderOutputFactory=spiderOutputFactory;
        this.spiderCheckPointFactory=spiderCheckPointFactory;
        init();
    }

    private void init() throws Exception {
        String policyName = prop.getProperty("policyMod");
        String outputName = prop.getProperty("outputMod");
        String checkPointMod = prop.getProperty("checkpointMod");
        String mod = prop.getProperty("mod");
        
        SpiderPolicy last = null;
        SpiderPolicy tmp = null;
        String[] policys = policyName.split(",");
        int n = 0;
        for(String pName : policys){
            tmp = spiderPolicyFactory.provide(pName, prop ,n);
            if(tmp != null && n == 0){
                policy = tmp;
            }
            if(last != null && tmp != null){
                last.setNext(tmp);
            }
            last = tmp;
            n++;
        }
        
        NotiferProxy.getDefaultNotiferProxy().register(SpiderPolicy.NEEDOUTPUT, this);
        NotiferProxy.getDefaultNotiferProxy().register(SpiderPolicy.NEEDSCAN, this);
        
        if(mod.equals("single"))
            output = spiderOutputFactory.provide(outputName, prop);
        else
            output = spiderOutputFactory.provide(mod, prop);
        
        spiderCheckPoint = spiderCheckPointFactory.provide(checkPointMod);
        urlPool = spiderCheckPoint.restoreCheckPoint(prop);
        
        MAXDEEP = Integer.valueOf(prop.getProperty("deep"));
        
        if(urlPool == null)
        	urlPool = new UrlPool();
        
        httpSender = new WarpedHttpSender(prop);
        
        checkPointHandler = new CheckPointHandler();
        checkPointHandler.setCheckPoint(spiderCheckPoint);
        checkPointHandler.setUrlPool(urlPool);
        checkPointHandler.setProp(prop);
        Runtime.getRuntime().addShutdownHook(checkPointHandler);
    }

    public void spider(String url) {
        WarpUrl warpUrl = new WarpUrl(url, null);
        if (url == null || warpUrl == null) {
            return;
        }

        urlPool.add(warpUrl);

        try {
            output.start();
        } catch (Exception ex) {
            LOGGER.error("error", ex);
            return;
        }

        while (null != (warpUrl = urlPool.poll()) && warpUrl.getDeep() < MAXDEEP + 1) {
            try {
                ResponseData res = httpSender.send(warpUrl);
                if (res == null || res.getStatusCode() >= 400) {
                    continue;
                }

                policy.scan(warpUrl, new String(res.getResponseContent(), "UTF-8"));
            } catch (Exception ex) {
                LOGGER.error("error", ex);
            }
        }

        try {
            output.close();
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

    @Override
    public void notifyListener(Message message) {
        // TODO Auto-generated method stub
        switch (message.getEventId()) {
            case SpiderPolicy.NEEDOUTPUT:
                notifyDoNeedOutput((WarpUrl) message.getMsg());
                break;
            case SpiderPolicy.NEEDSCAN:
                notifyDoNeedScan((WarpUrl) message.getMsg());
                break;
            default:
                break;
        }

    }

    public void notifyDoNeedOutput(WarpUrl warpUrl) {
        try {
            if (urlPool.addAlreadyOutput(warpUrl)) {
                output.output(warpUrl);
            }
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

    public void notifyDoNeedScan(WarpUrl warpUrl) {
        if (urlPool.add(warpUrl))
            LOGGER.info(new StringBuilder().append("will spider ").append(
                warpUrl.getUrl().toString()));
    }
}
