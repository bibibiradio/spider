package xm.bibibiradio.output;

import java.util.Properties;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.log4j.Logger;

import xm.bibibiradio.util.LogFactory;

public class SpiderOutputPoolFactory extends BasePoolableObjectFactory<SpiderOutput> {
    final static private Logger LOGGER = LogFactory.provide(SpiderOutputPoolFactory.class);

    private Properties          prop;

    public SpiderOutputPoolFactory(Properties prop) {
        this.prop = prop;
    }

    @Override
    public SpiderOutput makeObject() throws Exception {
        // TODO Auto-generated method stub
        SpiderOutput spiderOutput = SpiderOutputFactory
            .provide(prop.getProperty("outputMod"), prop);
        spiderOutput.start();
        return spiderOutput;
    }

    @Override
    public void destroyObject(SpiderOutput spiderOutput) {
        try {
            spiderOutput.close();
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }
    }

}
