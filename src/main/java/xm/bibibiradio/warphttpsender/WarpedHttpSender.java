package xm.bibibiradio.warphttpsender;

import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;

import com.bibibiradio.httpsender.HttpSender;
import com.bibibiradio.httpsender.HttpSenderFactory;
import com.bibibiradio.httpsender.ResponseData;

public class WarpedHttpSender {
    final private static Logger LOGGER = LogFactory.provide(WarpedHttpSender.class);
    private HttpSender httpSender;
    private String cookie;
    private String ua;
    private Properties prop;
    
    private HashMap<String,String> header;
    
    public WarpedHttpSender(Properties prop) throws Exception{
        try {
            this.prop = prop;
            httpSender = new HttpSenderFactory().provide("implV1");
            httpSender.setSendFreq(Integer.valueOf(this.prop.getProperty("interval")));
            httpSender.setCodec(true);
            httpSender.setRetryTime(Integer.valueOf(this.prop.getProperty("retry")));
            httpSender.setTimeout(Long.valueOf(Integer.valueOf(this.prop.getProperty("ctimeout"))));
            httpSender.setSoTimeout(Long.valueOf(Integer.valueOf(this.prop.getProperty("stimeout"))));
            httpSender.setAutoRedirect(true);
            httpSender.setCheckPeerCert(Boolean.valueOf(this.prop.getProperty("checkPeerCert")));
            if(!this.prop.getProperty("proxyIp").equals("None")){
                httpSender.setHttpProxy(this.prop.getProperty("proxyIp"),Integer.valueOf(this.prop.getProperty("proxyPort")));
            }

            httpSender.start();

            if(!this.prop.getProperty("headerFile").equals("None")){
                header = HeaderParser.parseFile(this.prop.getProperty("headerFile"),this.prop.getProperty("headerFileSplit"));
            }

            cookie = this.prop.getProperty("cookie");
            ua = this.prop.getProperty("ua");
            
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
    
    public ResponseData send(WarpUrl warpUrl) throws Exception{
        return httpSender.send(warpUrl.getUrl().toString(), 0, header,null);
    }
}
