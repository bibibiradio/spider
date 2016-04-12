package xm.bibibiradio.output;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.bibibiradio.httpsender.ResponseData;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;
import xm.bibibiradio.warphttpsender.WarpedHttpSender;

public class HttpFileOutput implements SpiderOutput {
    final static private Logger LOGGER = LogFactory.provide(HttpFileOutput.class);
    private WarpedHttpSender    httpSender;
    private Properties          prop;

    public HttpFileOutput(Properties prop) {
        this.prop = prop;
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        httpSender = new WarpedHttpSender(prop);
        File basePathFile = new File(prop.getProperty("basePath"));
        if (basePathFile.isFile())
            throw new Exception(prop.getProperty("basePath") + " not a directory");
        if (!basePathFile.exists()) {
            basePathFile.mkdirs();
        }
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
        OutputStream output = null;
        try {
            ResponseData res = httpSender.send(warpUrl);
            if (res == null || res.getStatusCode() >= 400) {
                LOGGER.info(new StringBuilder("GET ").append(warpUrl.getUrl().toString()).append(
                    " Fail"));
                return;
            }

            StringBuilder sb = new StringBuilder(prop.getProperty("basePath"));
            sb.append(warpUrl.getDeep());
            sb.append("httpfile");
            sb.append(warpUrl.getUrl().getPath() != null ? warpUrl.getUrl().getPath()
                .replace("/", ".") : "");
            sb.append(warpUrl.getUrl().getQuery() != null ? warpUrl.getUrl().getQuery() : "");

            String fileName = sb.toString();

            output = new FileOutputStream(fileName);

            output.write(res.getResponseContent());

            LOGGER.info(new StringBuilder().append("GET ").append(warpUrl.getUrl().toString())
                .append("-->").append(fileName));
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        } finally {
            if (output != null)
                output.close();
        }
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub

    }

}
