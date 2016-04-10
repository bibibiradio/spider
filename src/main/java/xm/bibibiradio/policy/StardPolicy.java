package xm.bibibiradio.policy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import xm.bibibiradio.listener.Notifer;
import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.SpiderConfig;

public class StardPolicy extends Notifer implements SpiderPolicy {
    private SpiderPolicy next;
    private String       getCssQuery;
    private String       scanCssQuery;
    private String       contentTag;

    private SpiderFilter filter;

    public StardPolicy() {
        filter = new StardFilter();
        filter.enableProp(SpiderConfig.getConfig().getProp());

        getCssQuery = SpiderConfig.getConfig().getProp().getProperty("getCssQuery");
        scanCssQuery = SpiderConfig.getConfig().getProp().getProperty("scanCssQuery");
        contentTag = SpiderConfig.getConfig().getProp().getProperty("contentTag");
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
    }

    @Override
    public void setNext(SpiderPolicy next) {
        // TODO Auto-generated method stub

    }

}
