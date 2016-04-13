package xm.bibibiradio.policy;

import static org.junit.Assert.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StandardFilterTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test() {
        String html = "<a id=\"3\" data-level=\"2.1\"><img src=3 />123</a>";
        Document doc = Jsoup.parse(html);
        Elements eles = doc.select("a[data-level^=2.]");
        for(Element ele : eles){
            System.out.println(ele.toString());
            System.out.println(ele.text());
        }
    }

}
