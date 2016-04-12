package xm.bibibiradio.policy;

import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xm.bibibiradio.executechain.ExecuteChain;
import xm.bibibiradio.listener.Listener;
import xm.bibibiradio.listener.Notifer;
import xm.bibibiradio.listener.NotiferProxy;
import xm.bibibiradio.spider.WarpUrl;

public class NormalPolicy implements SpiderPolicy, Notifer, ExecuteChain {

    private SpiderPolicy next;
    private Properties   prop;
    private SpiderFilter filter;
    private Notifer      notifer;
    private int          myId;

    public NormalPolicy(Properties prop,int myId) {
        this.prop = prop;
        this.myId = myId;
        notifer = new NotiferProxy();
    }

    @Override
    public void scan(WarpUrl url, String html) throws Exception {
        // TODO Auto-generated method stub
        if (filter == null) {
            filter = new StandardFilter(myId);
            filter.enableProp(prop);
        }
        Document doc = Jsoup.parse(html);
        Elements eles = null;
        eles = doc.select("img[src]");
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr("src"), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedOutput(warpUrl, html))
                notify(NEEDOUTPUT, warpUrl);
        }

        eles = doc.select("a[href]");
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr("href"), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedScan(warpUrl, html))
                notify(NEEDSCAN, warpUrl);
            if (filter.isNeedOutput(warpUrl, html))
                notify(NEEDOUTPUT, warpUrl);
        }

        if (next != null) {
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

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

}
