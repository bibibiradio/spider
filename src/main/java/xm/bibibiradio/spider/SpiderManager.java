package xm.bibibiradio.spider;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.bibibiradio.httpsender.ResponseData;

import xm.bibibiradio.listener.Listener;
import xm.bibibiradio.listener.Message;
import xm.bibibiradio.listener.Notifer;
import xm.bibibiradio.output.SpiderOutput;
import xm.bibibiradio.output.SpiderOutputFactory;
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

    private Properties          prop;

    public SpiderManager(Properties prop) throws Exception {
        this.prop = prop;
        init();
    }

    private void init() throws Exception {
        String policyName = prop.getProperty("policyMod");
        String outputName = prop.getProperty("outputMod");
        
        SpiderPolicy last = null;
        SpiderPolicy tmp = null;
        String[] policys = policyName.split(",");
        int n = 0;
        for(String pName : policys){
            tmp = SpiderPolicyFactory.provide(pName, prop);
            if(tmp != null && n == 0){
                policy = tmp;
            }
            if(last != null && tmp != null){
                last.setNext(tmp);
            }
            ((Notifer) tmp).register(SpiderPolicy.NEEDOUTPUT, this);
            ((Notifer) tmp).register(SpiderPolicy.NEEDSCAN, this);
            last = tmp;
            n++;
        }

        output = SpiderOutputFactory.provide(outputName, prop);
        MAXDEEP = Integer.valueOf(prop.getProperty("deep"));

        urlPool = new UrlPool();
        httpSender = new WarpedHttpSender(prop);
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
