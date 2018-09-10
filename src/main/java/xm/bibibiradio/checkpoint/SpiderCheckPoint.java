package xm.bibibiradio.checkpoint;

import java.util.Properties;

import xm.bibibiradio.spider.UrlPool;

public interface SpiderCheckPoint {
	public void storeCheckPoint(UrlPool urlPool,Properties prop) throws Exception;
	public UrlPool restoreCheckPoint(Properties prop) throws Exception;
}
