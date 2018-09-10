package xm.bibibiradio.output;

import java.util.Properties;

public interface SpiderOutputFactory {
	public SpiderOutput provide(String outputType, Properties prop) throws Exception;
}
