package xm.bibibiradio.spider;

import java.util.Properties;

import org.apache.log4j.Logger;

import xm.bibibiradio.checkpoint.SpiderCheckPoint;
import xm.bibibiradio.util.LogFactory;

public class CheckPointHandler extends Thread {
	final static private Logger LOGGER  = LogFactory.provide(CheckPointHandler.class);
	
	private SpiderCheckPoint checkPoint;
	private UrlPool urlPool;
	private Properties prop;
	
	public CheckPointHandler() {
		super("CheckPointHandler save run");
	}
	
	public void run() {
		try {
			checkPoint.storeCheckPoint(urlPool, prop);
		}catch(Exception ex) {
			LOGGER.error("error message",ex);
		}
	}

	public SpiderCheckPoint getCheckPoint() {
		return checkPoint;
	}

	public void setCheckPoint(SpiderCheckPoint checkPoint) {
		this.checkPoint = checkPoint;
	}

	public UrlPool getUrlPool() {
		return urlPool;
	}

	public void setUrlPool(UrlPool urlPool) {
		this.urlPool = urlPool;
	}

	public Properties getProp() {
		return prop;
	}

	public void setProp(Properties prop) {
		this.prop = prop;
	}
	
	
	
}
