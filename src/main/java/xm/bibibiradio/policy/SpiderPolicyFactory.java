package xm.bibibiradio.policy;

import java.util.Properties;

public class SpiderPolicyFactory {
    private static int id = 0;

    public static SpiderPolicy provide(String policyType, Properties prop) throws Exception {
        SpiderPolicy newPolicy;
        if (policyType.equals("std"))
            newPolicy = new StandardPolicy(prop, id++);
        else
            return null;
        return newPolicy;
    }
}
