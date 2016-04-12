package xm.bibibiradio.spider;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xm.bibibiradio.util.GlobalConfig;

public class SpiderManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        SpiderManager spider;
        try {
            spider = new SpiderManager(GlobalConfig.getConfig().getProp());
            spider.spider("https://www.taobao.com");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
