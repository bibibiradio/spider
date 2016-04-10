package xm.bibibiradio.output;

public class SpiderOutputFactory {
    public static SpiderOutput provide(String outputType){
        if(outputType.equals("file"))
            return new FileOutput();
        else if(outputType.equals("httpfile"))
            return new HttpFileOutput();
        else
            return null;
    }
}
