package xm.bibibiradio.warphttpsender;

import java.util.HashMap;

import org.apache.log4j.Logger;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.util.SpiderConfig;

import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderFactory;
import com.bibibiradio.httpsender.ResponseData;

public class WarpedHttpSender {
    final private static Logger LOGGER = LogFactory.provide(WarpedHttpSender.class);
    private HttpSender httpSender;
    private String cookie;
    private String ua;
    
    private HashMap<String,String> header;
    
    public WarpedHttpSender(){
        try {
            httpSender = new HttpSenderFactory().provide("implV1");
            httpSender.setSendFreq(Integer.valueOf(SpiderConfig.getConfig().getProp().getProperty("interval")));
            httpSender.setCodec(true);
            httpSender.setRetryTime(Integer.valueOf(SpiderConfig.getConfig().getProp().getProperty("retry")));
            httpSender.setTimeout(Long.valueOf(Integer.valueOf(SpiderConfig.getConfig().getProp().getProperty("ctimeout"))));
            httpSender.setSoTimeout(Long.valueOf(Integer.valueOf(SpiderConfig.getConfig().getProp().getProperty("stimeout"))));
            httpSender.setAutoRedirect(true);
            
            httpSender.start();
            
            cookie = SpiderConfig.getConfig().getProp().getProperty("cookie");
            ua = SpiderConfig.getConfig().getProp().getProperty("ua");
            
            if(cookie != null && !cookie.equals("")){
                if(header == null)
                    header = new HashMap<String,String>();
                header.put("Cookie", cookie);
            }
            
            if(ua != null && !ua.equals("")){
                if(header == null)
                    header = new HashMap<String,String>();
                header.put("User-Agent", ua);
            }
        } catch (Exception e) {
            LOGGER.error("error",e);
        }
    }
    
    public ResponseData send(WarpUrl warpUrl){
        return httpSender.send(warpUrl.getUrl().toString(), 0, null,null);
    }
}