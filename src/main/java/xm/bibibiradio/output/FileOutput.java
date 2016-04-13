package xm.bibibiradio.output;

import java.io.FileOutputStream;
import java.util.Properties;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.GlobalConfig;

public class FileOutput implements SpiderOutput {
    private FileOutputStream output;
    private String           fileName;
    private Properties       prop;

    public FileOutput(Properties prop) {
        this.prop = prop;
        fileName = this.prop.getProperty("basePath");
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
//        output.write((warpUrl.getDeep() + "  " + warpUrl.getUrl().toString() + "\r\n")
//            .getBytes("UTF-8"));
        output.write((warpUrl.getContent() + "\r\n")
          .getBytes("UTF-8"));
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        output = new FileOutputStream(fileName);
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        if (output != null)
            output.close();
    }

}
