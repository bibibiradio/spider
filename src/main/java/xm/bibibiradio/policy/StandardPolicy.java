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
    private String       scanContentTagCssQuery;
    private String       scanContentTagTarget;
    private int          scanContentTagLay1 = -1;
    private int          scanContentTagLay2 = -1;
    private String       getContentTagCssQuery;
    private String       getContentTagTarget;
    private int          getContentTagLay1  = -1;
    private int          getContentTagLay2  = -1;
    private Properties   prop;

    private int          myId               = -1;

    private SpiderFilter filter;

    public StandardPolicy(Properties prop, int myId) throws Exception {
        this.prop = prop;
        this.myId = myId;

        filter = new StandardFilter(myId);
        filter.enableProp(GlobalConfig.getConfig().getProp());
        if (myId == -1) {
            getCssQuery = this.prop.getProperty("getCssQuery");
            scanCssQuery = this.prop.getProperty("scanCssQuery");
            getContentTag = this.prop.getProperty("contentTag");
            scanContentTag = "href";
        } else {
            ArrayList<String> policyConfig = PolicyParser.getPolicyParser().parse(prop).get(myId);
            scanCssQuery = policyConfig.get(1);
            scanContentTag = policyConfig.get(2);
            getCssQuery = policyConfig.get(3);
            getContentTag = policyConfig.get(4);

            ArrayList<String> scts = PolicyParser.getPolicyParser().parseContentTag(scanContentTag);
            String first = scts.get(0);
            if (first.equals("text")) {
                scanContentTagLay1 = 0;
                scanContentTagLay2 = 0;
            } else if (first.equals("attr")) {
                scanContentTagLay1 = 0;
                scanContentTagLay2 = 1;
                scanContentTagTarget = scts.get(1);
            } else {
                scanContentTagLay1 = 1;
                scanContentTagCssQuery = scts.get(0);
                String secend = scts.get(1);
                if (secend.equals("text")) {
                    scanContentTagLay2 = 0;
                } else if (secend.equals("attr")) {
                    scanContentTagLay2 = 1;
                    scanContentTagTarget = scts.get(2);
                }
            }

            ArrayList<String> gcts = PolicyParser.getPolicyParser().parseContentTag(getContentTag);
            first = gcts.get(0);
            if (first.equals("text")) {
                getContentTagLay1 = 0;
                getContentTagLay2 = 0;
            } else if (first.equals("attr")) {
                getContentTagLay1 = 0;
                getContentTagLay2 = 1;
                getContentTagTarget = gcts.get(1);
            } else {
                getContentTagLay1 = 1;
                getContentTagCssQuery = gcts.get(0);
                String secend = gcts.get(1);
                if (secend.equals("text")) {
                    getContentTagLay2 = 0;
                } else if (secend.equals("attr")) {
                    getContentTagLay2 = 1;
                    getContentTagTarget = gcts.get(2);
                }
            }
        }
    }

    @Override
    public void scan(WarpUrl url, String html) throws Exception {
        // TODO Auto-generated method stub
        Document doc = Jsoup.parse(html);
        Elements eles = null;

        eles = doc.select(getCssQuery);
        for (Element ele : eles) {
            if (1 == getContentTagLay1) {
                Elements inners = ele.select(getContentTagCssQuery);
                for (Element inner : inners) {
                    WarpUrl warpUrl;
                    if (getContentTagLay2 == 0) {
                        warpUrl = new WarpUrl(inner.text(), url);
                        warpUrl.setContent(inner.text());
                        warpUrl.setResourceHash(warpUrl.getContent().hashCode());
                    } else {
                        warpUrl = new WarpUrl(inner.attr(getContentTagTarget), url);
                        warpUrl.setContent(inner.attr(getContentTagTarget));
                        warpUrl.setResourceHash(warpUrl.getContent().hashCode());
                    }
                    if (warpUrl.getUrl() == null)
                        continue;
                    if (filter.isNeedOutput(warpUrl, ele.toString()))
                        notify(NEEDOUTPUT, warpUrl);
                }
            } else {
                WarpUrl warpUrl;
                if (getContentTagLay2 == 0) {
                    warpUrl = new WarpUrl(ele.text(), url);
                    warpUrl.setContent(ele.text());
                    warpUrl.setResourceHash(warpUrl.getContent().hashCode());
                } else {
                    warpUrl = new WarpUrl(ele.attr(getContentTagTarget), url);
                    warpUrl.setContent(ele.attr(getContentTagTarget));
                    warpUrl.setResourceHash(warpUrl.getContent().hashCode());
                }

                if (warpUrl.getUrl() == null)
                    continue;
                if (filter.isNeedOutput(warpUrl, ele.toString()))
                    notify(NEEDOUTPUT, warpUrl);
            }
        }

        eles = doc.select(scanCssQuery);
        for (Element ele : eles) {
            if (1 == scanContentTagLay1) {
                Elements inners = ele.select(scanContentTagCssQuery);
                for (Element inner : inners) {
                    WarpUrl warpUrl;
                    if (scanContentTagLay2 == 0) {
                        warpUrl = new WarpUrl(inner.text(), url);
                        warpUrl.setResourceHash(inner.text().hashCode());
                    } else {
                        warpUrl = new WarpUrl(inner.attr(scanContentTagTarget), url);
                        warpUrl.setResourceHash(inner.attr(scanContentTagTarget).hashCode());
                    }
                    if (warpUrl.getUrl() == null)
                        continue;
                    if (filter.isNeedScan(warpUrl, ele.toString()))
                        notify(NEEDSCAN, warpUrl);
                }
            } else {
                WarpUrl warpUrl;
                if (scanContentTagLay2 == 0) {
                    warpUrl = new WarpUrl(ele.text(), url);
                    warpUrl.setResourceHash(ele.text().hashCode());
                } else {
                    warpUrl = new WarpUrl(ele.attr(scanContentTagTarget), url);
                    warpUrl.setResourceHash(ele.attr(scanContentTagTarget).hashCode());
                }

                if (warpUrl.getUrl() == null)
                    continue;
                if (filter.isNeedScan(warpUrl, ele.toString()))
                    notify(NEEDSCAN, warpUrl);
            }
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
        NotiferProxy.getDefaultNotiferProxy().register(eventId, listener);
    }

    @Override
    public void notify(int eventId, Object notifyBody) {
        // TODO Auto-generated method stub
    	NotiferProxy.getDefaultNotiferProxy().notify(eventId, notifyBody);
    }

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
        if (myId > 0) {

        }
    }

}
