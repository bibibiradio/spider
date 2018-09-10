package xm.bibibiradio.checkpoint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import xm.bibibiradio.spider.UrlPool;

public class FileSpiderCheckPoint implements SpiderCheckPoint{
	
	@Override
	public void storeCheckPoint(UrlPool urlPool, Properties prop) throws Exception {
		// TODO Auto-generated method stub
		String storeLable = prop.getProperty("ckl");
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(storeLable,false));
		out.writeObject(urlPool);
		out.close();
	}

	@Override
	public UrlPool restoreCheckPoint(Properties prop) throws Exception {
		// TODO Auto-generated method stub
		String storeLable = prop.getProperty("ckl");
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(storeLable));
		UrlPool urlPool = (UrlPool)in.readObject();
		in.close();
		return urlPool;
	}

}
