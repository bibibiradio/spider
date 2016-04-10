package xm.bibibiradio.output;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.SpiderConfig;

public class HttpFileOutputTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        SpiderOutput t = SpiderOutputFactory.provide("httpfile");
        SpiderConfig.getConfig().getProp().put("basePath", "/Users/xiaoleixl/projects/java/spider/");
        try {
            t.start();
            t.output(new WarpUrl("http://i1.hdslb.com/headers/bdddec22494b50d792487028c17ece76.png",null));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
