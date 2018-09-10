package xm.bibibiradio.output;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.GlobalConfig;

public class HttpFileOutputTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() throws Exception{
        SpiderOutput t = new DefaultSpiderOutputFactory().provide("httpfile",GlobalConfig.getConfig().getProp());
        GlobalConfig.getConfig().getProp().put("interval", "1000");
        GlobalConfig.getConfig().getProp().put("retry", "3");
        GlobalConfig.getConfig().getProp().put("ctimeout", "10000");
        GlobalConfig.getConfig().getProp().put("stimeout", "30000");
        GlobalConfig.getConfig().getProp().put("basePath", "/Users/xiaoleixl/projects/java/spider/");
        try {
            t.start();
            t.output(new WarpUrl("http://i1.hdslb.com/headers/bdddec22494b50d792487028c17ece76.png",null));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    @Test
    public void test2(){
        File file = new File("./../../../");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
