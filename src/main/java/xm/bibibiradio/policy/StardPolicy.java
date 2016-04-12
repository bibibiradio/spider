package xm.bibibiradio.policy;

import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xm.bibibiradio.listener.Listener;
import xm.bibibiradio.listener.Notifer;
import xm.bibibiradio.listener.NotiferProxy;
import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.GlobalConfig;

public class StardPolicy implements SpiderPolicy,Notifer {
    private SpiderPolicy next;
    private String       getCssQuery;
    private String       scanCssQuery;
    private String       contentTag;
    private Properties prop;
    
    private Notifer notifer;

    private SpiderFilter filter;

    public StardPolicy(Properties prop) {
        this.prop = prop;
        
        filter = new StardFilter();
        filter.enableProp(GlobalConfig.getConfig().getProp());

        getCssQuery = this.prop.getProperty("getCssQuery");
        scanCssQuery = this.prop.getProperty("scanCssQuery");
        contentTag = this.prop.getProperty("contentTag");
        
        notifer = new NotiferProxy();
    }

    @Override
    public void scan(WarpUrl url, String html) throws Exception {
        // TODO Auto-generated method stub
        Document doc = Jsoup.parse(html);
        Elements eles = null;

        eles = doc.select(getCssQuery);
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr(contentTag), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedOutput(warpUrl, html))
                notify(NEEDOUTPUT, warpUrl);
        }

        eles = doc.select(scanCssQuery);
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr("href"), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedScan(warpUrl, html))
                notify(NEEDSCAN, warpUrl);
        }
        
        if(next != null){
            next.scan(url, html);
        }
    }

    @Override
    public void setNext(SpiderPolicy next) {
        // TODO Auto-generated method stub
        this.next = next;
    }

    @Override
    public void register(int eventId, Listener listener) {
        // TODO Auto-generated method stub
        notifer.register(eventId, listener);
    }

    @Override
    public void notify(int eventId, Object notifyBody) {
        // TODO Auto-generated method stub
        notifer.notify(eventId, notifyBody);
    }

}
