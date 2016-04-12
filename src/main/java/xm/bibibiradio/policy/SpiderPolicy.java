package xm.bibibiradio.policy;

import xm.bibibiradio.spider.WarpUrl;

public interface SpiderPolicy {
    final static int NEEDOUTPUT = 1;
    final static int NEEDSCAN = 2;
    public void scan(WarpUrl url,String html) throws Exception;
    public void setNext(SpiderPolicy next);
    public void setMyId(int myId);
}
