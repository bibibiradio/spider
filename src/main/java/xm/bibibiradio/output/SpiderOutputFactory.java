package xm.bibibiradio.output;

import java.util.Properties;

public class SpiderOutputFactory {
    public static SpiderOutput provide(String outputType, Properties prop) {
        if (outputType.equals("file"))
            return new FileOutput(prop);
        else if (outputType.equals("httpfile"))
            return new HttpFileOutput(prop);
        else if (outputType.equals("multi"))
            return new MultiThreadOuptput(prop);
        else
            return null;
    }
}
