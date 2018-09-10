package xm.bibibiradio.output;

import java.util.Properties;

public class DefaultSpiderOutputFactory implements SpiderOutputFactory{
    public SpiderOutput provide(String outputType, Properties prop) throws Exception{
        if (outputType.equals("file"))
            return new FileOutput(prop);
        else if (outputType.equals("httpfile"))
            return new HttpFileOutput(prop);
        else if (outputType.equals("multi"))
            return null;
        else
            return null;
    }
}
