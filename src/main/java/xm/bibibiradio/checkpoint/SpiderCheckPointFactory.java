package xm.bibibiradio.checkpoint;

public interface SpiderCheckPointFactory {
	public SpiderCheckPoint provide(String checkPointType) throws Exception;
}
