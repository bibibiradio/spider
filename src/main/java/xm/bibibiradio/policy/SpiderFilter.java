package xm.bibibiradio.policy;

import java.util.Properties;

import xm.bibibiradio.spider.WarpUrl;

public interface SpiderFilter {
    public boolean isNeedOutput(WarpUrl warpUrl, String html);

    public boolean isNeedScan(WarpUrl warpUrl, String html);

    public void enableProp(Properties prop);

    public void setMyId(int myId);
}
