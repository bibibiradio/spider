package xm.bibibiradio.spider;

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
import xm.bibibiradio.util.SpiderConfig;
import xm.bibibiradio.warphttpsender.WarpedHttpSender;

public class SpiderManager implements Listener {
    final static private Logger LOGGER  = LogFactory.provide(SpiderManager.class);
    static int                  MAXDEEP = 4;

    private SpiderPolicy        policy;
    private UrlPool             urlPool;
    private WarpedHttpSender    httpSender;
    private SpiderOutput        output;

    public SpiderManager() {
        init();
    }

    private void init() {
        String policyName = SpiderConfig.getConfig().getProp().getProperty("policyMod");
        String outputName = SpiderConfig.getConfig().getProp().getProperty("outputMod");

        policy = SpiderPolicyFactory.provide(policyName);
        output = SpiderOutputFactory.provide(outputName);
        MAXDEEP = Integer.valueOf(SpiderConfig.getConfig().getProp().getProperty("deep"));

        ((Notifer) policy).register(SpiderPolicy.NEEDOUTPUT, this);
        ((Notifer) policy).register(SpiderPolicy.NEEDSCAN, this);

        urlPool = new UrlPool();
        httpSender = new WarpedHttpSender();
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
        if(urlPool.add(warpUrl))
            LOGGER.info(new StringBuilder().append("will spider ").append(warpUrl.getUrl().toString()));
    }
}