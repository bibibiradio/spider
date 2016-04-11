package xm.bibibiradio.policy;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xm.bibibiradio.listener.NotiferProxy;
import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.SpiderConfig;

public class NormalPolicy extends NotiferProxy implements SpiderPolicy {
    
    private SpiderPolicy next;
    private SpiderFilter filter;
    public NormalPolicy(){
        next = null;
    }
    
    @Override
    public void scan(WarpUrl url, String html) throws Exception {
        // TODO Auto-generated method stub
        if(filter == null){
            filter = new StardFilter();
            filter.enableProp(SpiderConfig.getConfig().getProp());
            ((StardFilter)filter).setHost(url.getUrl().getHost());
        }
        Document doc = Jsoup.parse(html);
        Elements eles = null;
        eles = doc.select("img[src]");
        for(Element ele : eles){
            WarpUrl warpUrl = new WarpUrl(ele.attr("src"),url);
            if(warpUrl.getUrl() == null)
                continue;
            if(filter.isNeedOutput(warpUrl, html))
                notify(NEEDOUTPUT,warpUrl);
        }
        
        eles = doc.select("a[href]");
        for(Element ele : eles){
            WarpUrl warpUrl = new WarpUrl(ele.attr("href"),url);
            if(warpUrl.getUrl() == null)
                continue;
            if(filter.isNeedScan(warpUrl, html))
                notify(NEEDSCAN,warpUrl);
            if(filter.isNeedOutput(warpUrl, html))
                notify(NEEDOUTPUT,warpUrl);
        }
    }

    @Override
    public void setNext(SpiderPolicy next) {
        // TODO Auto-generated method stub
        
    }

}
