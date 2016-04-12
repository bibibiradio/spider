package xm.bibibiradio.policy;

import java.util.ArrayList;
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
import xm.bibibiradio.util.GlobalConfig;

public class StandardPolicy implements SpiderPolicy, Notifer, ExecuteChain {
    private SpiderPolicy next;
    private String       getCssQuery;
    private String       scanCssQuery;
    private String       getContentTag;
    private String       scanContentTag;
    private Properties   prop;

    private int          myId = -1;

    private Notifer      notifer;

    private SpiderFilter filter;

    public StandardPolicy(Properties prop,int myId) throws Exception {
        this.prop = prop;
        this.myId = myId;

        filter = new StandardFilter(myId);
        filter.enableProp(GlobalConfig.getConfig().getProp());
        if(myId == -1){
            getCssQuery = this.prop.getProperty("getCssQuery");
            scanCssQuery = this.prop.getProperty("scanCssQuery");
            getContentTag = this.prop.getProperty("contentTag");
            scanContentTag = "href";
        }else{
            ArrayList<String> policyConfig = PolicyParser.getPolicyParser().parse(prop).get(myId);
            scanCssQuery = policyConfig.get(1);
            scanContentTag = policyConfig.get(2);
            getCssQuery = policyConfig.get(3);
            getContentTag = policyConfig.get(4);
        }

        notifer = new NotiferProxy();
    }

    @Override
    public void scan(WarpUrl url, String html) throws Exception {
        // TODO Auto-generated method stub
        Document doc = Jsoup.parse(html);
        Elements eles = null;

        eles = doc.select(getCssQuery);
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr(getContentTag), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedOutput(warpUrl, ele.toString()))
                notify(NEEDOUTPUT, warpUrl);
        }

        eles = doc.select(scanCssQuery);
        for (Element ele : eles) {
            WarpUrl warpUrl = new WarpUrl(ele.attr(scanContentTag), url);
            if (warpUrl.getUrl() == null)
                continue;
            if (filter.isNeedScan(warpUrl, ele.toString()))
                notify(NEEDSCAN, warpUrl);
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
        if(myId > 0){
            
        }
    }
    
    

}
