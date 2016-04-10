package xm.bibibiradio.spider;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpiderManagerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        SpiderManager spider = new SpiderManager();
        spider.spider("https://www.taobao.com");
    }

}
