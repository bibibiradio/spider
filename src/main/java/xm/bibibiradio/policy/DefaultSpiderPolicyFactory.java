package xm.bibibiradio.policy;

import java.util.Properties;

public class DefaultSpiderPolicyFactory implements SpiderPolicyFactory{
    private static int id = 0;

    public SpiderPolicy provide(String policyType, Properties prop ,int idx) throws Exception {
        SpiderPolicy newPolicy;
        if (policyType.equals("std"))
            newPolicy = new StandardPolicy(prop, idx);
        else
            return null;
        return newPolicy;
    }
}
