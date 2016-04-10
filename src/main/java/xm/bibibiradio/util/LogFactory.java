package xm.bibibiradio.util;

import org.apache.log4j.Logger;

public class LogFactory {
    public static Logger provide(String name){
        return Logger.getLogger(name);
    }
    
    public static Logger provide(@SuppressWarnings("rawtypes") Class clazz){
        return Logger.getLogger(clazz);
    }
}
