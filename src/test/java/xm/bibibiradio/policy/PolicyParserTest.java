package xm.bibibiradio.policy;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PolicyParserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        Properties prop = new Properties();
        prop.put("policyConf", "start:a[href]:href:img[src]:src:::* * * * *::,std:a[href]:href:img[src]:src:::* * * * *::::::end");
        try {
            System.out.println(PolicyParser.getPolicyParser().parse(prop));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
