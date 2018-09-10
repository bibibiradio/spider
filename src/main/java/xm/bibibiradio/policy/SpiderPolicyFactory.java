package xm.bibibiradio.policy;

import java.util.Properties;

public interface SpiderPolicyFactory {
	public SpiderPolicy provide(String policyType, Properties prop, int idx) throws Exception;
}
