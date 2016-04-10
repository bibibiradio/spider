package xm.bibibiradio.util;

import java.util.Properties;

public class SpiderConfig {
    private Properties prop;
    
    private static SpiderConfig config;
    
    static public SpiderConfig getConfig(){
        if(config == null){
            config = new SpiderConfig();
            config.init();
        }
        return config;
    }
    
    public SpiderConfig(){
        prop = new Properties();
    }
    
    private void init(){
        prop.put("host", "bilibili.com");
        prop.put("filterUrl", "video");
        prop.put("output", "/Users/xiaoleixl/projects/java/spider/test.txt");
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }
    
    
}
