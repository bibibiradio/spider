package xm.bibibiradio.load;

import static org.junit.Assert.*;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        try{
            URL jarUrl = new URL("file:/Users/xiaoleixl/projects/java/spider/target/spider-2.2.0.jar");
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl},this.getClass().getClassLoader());
            Class<?> classLoaded = classLoader.loadClass("xm.bibibiradio.load.Test");
            ILoadTest test = (ILoadTest) classLoaded.newInstance();
            System.out.print(test.getTest("loadTest"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}
