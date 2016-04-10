package xm.bibibiradio.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.bibibiradio.httpsender.ResponseData;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.util.SpiderConfig;
import xm.bibibiradio.warphttpsender.WarpedHttpSender;

public class HttpFileOutput implements SpiderOutput {
    final static private Logger LOGGER = LogFactory.provide(HttpFileOutput.class);
    private WarpedHttpSender    httpSender;

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        httpSender = new WarpedHttpSender();
        File basePathFile = new File(SpiderConfig.getConfig().getProp()
                .getProperty("basePath"));
        if(basePathFile.isFile())
            throw new Exception(SpiderConfig.getConfig().getProp()
                .getProperty("basePath")+" not a directory");
        if(!basePathFile.exists()){
            basePathFile.mkdirs();
        }
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
        OutputStream output = null;
        try {
            ResponseData res = httpSender.send(warpUrl);
            if (res == null || res.getStatusCode() >= 400){
                LOGGER.info(new StringBuilder("GET ").append(warpUrl.getUrl().toString()).append(" Fail"));
                return;
            }

            StringBuilder sb = new StringBuilder(SpiderConfig.getConfig().getProp()
                .getProperty("basePath"));
            sb.append(warpUrl.getDeep());
            sb.append("httpfile");
            sb.append(warpUrl.getUrl().getPath() != null ? warpUrl.getUrl().getPath()
                .replace("/", ".") : "");
            sb.append(warpUrl.getUrl().getQuery() != null ? warpUrl.getUrl().getQuery() : "");

            String fileName = sb.toString();

            output = new FileOutputStream(fileName);

            output.write(res.getResponseContent());
            
            LOGGER.info(new StringBuilder().append("GET ").append(warpUrl.getUrl().toString()).append("-->").append(fileName));
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        } finally {
            if(output != null)
                output.close();
        }
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        
    }

}
