package xm.bibibiradio.spider;

import java.net.URL;

import org.apache.log4j.Logger;

import xm.bibibiradio.util.LogFactory;

public class WarpUrl {
    //private WarpUrl parent;
    final private Logger LOGGER = LogFactory.provide(WarpUrl.class);
    private URL url;
    private int deep;
    private String content;
    public WarpUrl(String urlStr,WarpUrl parent){
        if(parent == null){
            deep = 0;
        }else{
            deep = parent.getDeep() + 1;
        }
        
        if(urlStr == null){
            return;
        }
        
        try{
            url = new URL(urlStr);
        }catch(Exception ex1){
            try{
                if(urlStr.startsWith("//")){
                    url = new URL(new StringBuilder(parent.getUrl().getProtocol()).append(":").append(urlStr).toString());
                }else if(urlStr.startsWith("/")){
                    url = new URL(new StringBuilder(parent.getUrl().getProtocol()).append("://").append(parent.getUrl().getHost()).append(urlStr).toString());
                }else{
                    url = new URL(new StringBuilder(parent.getUrl().getProtocol()).append("://").append(parent.getUrl().getHost()).append(urlStr).toString());
                }
            }catch(Exception ex2){
                LOGGER.error("error",ex2);
            }
            
        }
    }
    public URL getUrl() {
        return url;
    }
    public void setURL(URL url) {
        this.url = url;
    }
    public int getDeep() {
        return deep;
    }
    public void setDeep(int deep) {
        this.deep = deep;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
}
