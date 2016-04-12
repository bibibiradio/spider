package xm.bibibiradio.policy;

import java.util.ArrayList;
import java.util.Properties;

public class PolicyParser {
    private static PolicyParser policyParser;
    
    public static PolicyParser getPolicyParser(){
        if(policyParser == null){
            policyParser = new PolicyParser();
        }
        return policyParser;
    }
    
    public ArrayList<ArrayList<String>> parse(Properties prop) throws Exception{
        String policyConfig = prop.getProperty("policyConf");
        String[] elesPolicyConfig = policyConfig.split(",");
        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>(); 
        
        for(String elePolicyConfig : elesPolicyConfig){
            ArrayList<String> list = new ArrayList<String>();
            String[] itemsStr = elePolicyConfig.split(":");
            for(String itemStr : itemsStr){
                list.add(itemStr);
            }
            ret.add(list);
        }
        
        return ret;
    }
}
