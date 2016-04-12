package xm.bibibiradio.util;

import java.util.Properties;

public class GlobalConfig {
    private Properties prop;
    
    private static GlobalConfig config;
    
    static public GlobalConfig getConfig(){
        if(config == null){
            config = new GlobalConfig();
            config.init();
        }
        return config;
    }
    
    public GlobalConfig(){
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
