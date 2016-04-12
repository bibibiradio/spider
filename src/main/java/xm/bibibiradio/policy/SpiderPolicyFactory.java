package xm.bibibiradio.policy;

import java.util.Properties;

public class SpiderPolicyFactory {
    public static SpiderPolicy provide(String policyType,Properties prop){
        if(policyType.equals("normal"))
            return new NormalPolicy(prop);
        else if(policyType.equals("stard"))
            return new StardPolicy(prop);
        else
            return null;
    }
}
