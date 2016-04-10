package xm.bibibiradio.output;

import xm.bibibiradio.spider.WarpUrl;

public interface SpiderOutput {
    public void start() throws Exception;
    public void output(WarpUrl warpUrl) throws Exception;
    public void close() throws Exception;
}
