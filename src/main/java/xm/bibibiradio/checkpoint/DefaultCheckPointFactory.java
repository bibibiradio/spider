package xm.bibibiradio.checkpoint;

public class DefaultCheckPointFactory implements SpiderCheckPointFactory {

	@Override
	public SpiderCheckPoint provide(String checkPointType) throws Exception {
		// TODO Auto-generated method stub
		if(checkPointType.equals("file"))
			return new FileSpiderCheckPoint();
		else
			return null;
	}

}
