package xm.bibibiradio.output;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;

public class MultiThreadOuptput implements SpiderOutput {
    final private static Logger             LOGGER = LogFactory.provide(MultiThreadOuptput.class);
    private Properties                      prop;
    private ExecutorService                 threadPool;
    private GenericObjectPool<SpiderOutput> spiderOutputPool;
    private AtomicInteger                   waitNum;
    private int                             threadNum;

    private class WarpedSpiderOutputToRun implements Runnable {
        private GenericObjectPool<SpiderOutput> spiderOutputPool;
        private WarpUrl                         warpUrl;
        private AtomicInteger                   waitNum;

        public WarpedSpiderOutputToRun(WarpUrl warpUrl,
                                       GenericObjectPool<SpiderOutput> spiderOutputPool,
                                       AtomicInteger waitNum) {
            this.spiderOutputPool = spiderOutputPool;
            this.warpUrl = warpUrl;
            this.waitNum = waitNum;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            SpiderOutput spiderOutput = null;
            try {
                spiderOutput = spiderOutputPool.borrowObject();
                if (spiderOutput != null)
                    spiderOutput.output(warpUrl);
            } catch (Exception ex) {
                LOGGER.error("error", ex);
            } finally {
                try {
                    if (spiderOutput != null)
                        spiderOutputPool.returnObject(spiderOutput);
                } catch (Exception ex2) {
                    LOGGER.error("returnOutputToPool error", ex2);
                }
                waitNum.getAndDecrement();
            }
        }

    }

    public MultiThreadOuptput(Properties prop) {
        this.prop = prop;
    }

    @Override
    public void start() throws Exception {
        // TODO Auto-generated method stub
        threadNum = Integer.valueOf(prop.getProperty("thread"));
        threadPool = Executors.newFixedThreadPool(threadNum);
        spiderOutputPool = new GenericObjectPool<SpiderOutput>(new SpiderOutputPoolFactory(prop));
        spiderOutputPool.setLifo(false);
        spiderOutputPool.setMaxActive(threadNum);
        spiderOutputPool.setMaxIdle(threadNum);
        waitNum = new AtomicInteger(0);
    }

    @Override
    public void output(WarpUrl warpUrl) throws Exception {
        // TODO Auto-generated method stub
        waitNum.getAndIncrement();
        threadPool.submit(new WarpedSpiderOutputToRun(warpUrl, spiderOutputPool, waitNum));
    }

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        while (waitNum.get() > 0) {
            Thread.sleep(1000);
        }
        if (spiderOutputPool != null)
            spiderOutputPool.close();
        if (threadPool != null)
            threadPool.shutdown();
    }

}
