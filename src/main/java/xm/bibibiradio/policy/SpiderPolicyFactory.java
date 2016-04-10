package xm.bibibiradio.policy;

public class SpiderPolicyFactory {
    public static SpiderPolicy provide(String policyType){
        if(policyType.equals("normal"))
            return new NormalPolicy();
        else if(policyType.equals("stard"))
            return new StardPolicy();
        else
            return null;
    }
}
