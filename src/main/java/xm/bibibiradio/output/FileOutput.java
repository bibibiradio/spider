package xm.bibibiradio.output;

import java.io.FileOutputStream;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.SpiderConfig;

public class FileOutput implements SpiderOutput {
    private FileOutputStream output;
    private String fileName;
    public FileOutput(){
        fileName = SpiderConfig.getConfig().getProp().getProperty("output");
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
        output.write((warpUrl.getDeep()+"  "+warpUrl.getUrl().toString()+"\r\n").getBytes("UTF-8"));
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        output = new FileOutputStream(fileName);
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        if(output != null)
            output.close();
    }

}
