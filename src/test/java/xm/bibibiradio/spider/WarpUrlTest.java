package xm.bibibiradio.spider;

import static org.junit.Assert.*;

import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class WarpUrlTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        try{
            String c = "http://123/home/admin/a.jpg?t=123";
            URL url = new URL(c);
            System.out.println(url.getProtocol());
            System.out.println(url.getHost());
            System.out.println(url.getPath());
            System.out.println(url.getQuery());
            System.out.println(url.toString());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
