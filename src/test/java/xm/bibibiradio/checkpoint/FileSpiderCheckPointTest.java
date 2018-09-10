package xm.bibibiradio.checkpoint;

import static org.junit.Assert.*;

import org.junit.Test;

import xm.bibibiradio.spider.UrlPool;
import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.GlobalConfig;

public class FileSpiderCheckPointTest {

	@Test
	public void test() throws Exception {
		UrlPool urlPool= new UrlPool();
		
		WarpUrl w =new WarpUrl("https://www.baidu.com",null);
		w.setResourceHash(111);
		urlPool.add(w);
		
		w =new WarpUrl("https://www.taobao.com",null);
		w.setResourceHash(222);
		urlPool.add(w);
		
		w =new WarpUrl("https://www.baidu.com/123",null);
		w.setResourceHash(123);
		urlPool.addAlreadyOutput(w);
		
		SpiderCheckPoint s = new DefaultCheckPointFactory().provide("file");
		
		GlobalConfig.getConfig().getProp().setProperty("ckl", "aaa.txt");
		s.storeCheckPoint(urlPool, GlobalConfig.getConfig().getProp());
		
		UrlPool urlPool2 = s.restoreCheckPoint(GlobalConfig.getConfig().getProp());
		
		WarpUrl u1 = urlPool2.poll();
		System.out.println(u1==null?null:u1.getUrl().toString());
		u1 = urlPool2.poll();
		System.out.println(u1==null?null:u1.getUrl().toString());
		u1 = urlPool2.poll();
		System.out.println(u1==null?null:u1.getUrl().toString());
		
		WarpUrl u2 = new WarpUrl("https://www.baidu.com/123",null);
		u2.setResourceHash(123);
		System.out.println(urlPool2.isContainAlreadyOutput(u2));
		
		u2 = new WarpUrl("https://www.ssss.com/123",null);
		u2.setResourceHash(321);
		System.out.println(urlPool2.isContainAlreadyOutput(u2));
		
	}

}
