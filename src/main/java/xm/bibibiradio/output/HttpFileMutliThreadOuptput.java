package xm.bibibiradio.output;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;

public class HttpFileMutliThreadOuptput implements SpiderOutput {
    final private static Logger             LOGGER = LogFactory
                                                       .provide(HttpFileMutliThreadOuptput.class);
    private Properties                      prop;
    private ExecutorService                 threadPool;
    private GenericObjectPool<SpiderOutput> spiderOutputPool;
    private int                             threadNum;

    private class WarpedSpiderOutputToRun implements Runnable {
        private SpiderOutput content;
        private WarpUrl      warpUrl;

        public WarpedSpiderOutputToRun(SpiderOutput content, WarpUrl warpUrl) {
            this.content = content;
            this.warpUrl = warpUrl;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                content.output(warpUrl);
            } catch (Exception ex) {
                LOGGER.error("error", ex);
            }
        }

    }

    public HttpFileMutliThreadOuptput(Properties prop) {
        this.prop = prop;
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        File basePathFile = new File(prop.getProperty("basePath"));
        if (basePathFile.isFile())
            throw new Exception(prop.getProperty("basePath") + " not a directory");
        if (!basePathFile.exists()) {
            basePathFile.mkdirs();
        }

        threadNum = Integer.valueOf(prop.getProperty("thread"));
        threadPool = Executors.newFixedThreadPool(threadNum);
        spiderOutputPool = new GenericObjectPool<SpiderOutput>(new SpiderOutputPoolFactory(prop));
        spiderOutputPool.setLifo(false);
        spiderOutputPool.setMaxActive(threadNum);
        spiderOutputPool.setMaxIdle(threadNum);
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
        //threadPool.submit(new DoThread(prop, warpUrl, httpSenderPool));
        SpiderOutput spiderOutput = null;
        try {
            spiderOutput = spiderOutputPool.borrowObject();
            if (spiderOutput != null)
                threadPool.submit(new WarpedSpiderOutputToRun(spiderOutput, warpUrl));

        } catch (Exception ex) {
            LOGGER.error("error", ex);
        } finally {
            if (spiderOutput != null)
                spiderOutputPool.returnObject(spiderOutput);
        }
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub

    }

}
