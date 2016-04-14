package xm.bibibiradio.policy;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import xm.bibibiradio.spider.WarpUrl;
import xm.bibibiradio.util.LogFactory;

class StandardFilter implements SpiderFilter {
    final static Logger LOGGER = LogFactory.provide(StandardFilter.class);

    private Properties  prop;
    private String      outputWhiteUrl, outputWhiteHtml, outputBlackUrl, outputBlackHtml;
    private String      scanWhiteUrl, scanWhiteHtml, scanBlackUrl, scanBlackHtml;
    private int         myId;

    public StandardFilter(int myId) {
        this.myId = myId;
    }

    @Override
    public boolean isNeedOutput(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        if ((outputWhiteUrl == null || warpUrl.getUrl().toString().indexOf(outputWhiteUrl) != -1)
            && (outputBlackUrl == null || warpUrl.getUrl().toString().indexOf(outputBlackUrl) == -1)
            && (outputWhiteHtml == null || html.indexOf(outputWhiteHtml) != -1)
            && (outputBlackHtml == null || html.indexOf(outputBlackHtml) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNeedScan(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        if ((scanWhiteUrl == null || warpUrl.getUrl().toString().indexOf(scanWhiteUrl) != -1)
            && (scanBlackUrl == null || warpUrl.getUrl().toString().indexOf(scanBlackUrl) == -1)
            && (scanWhiteHtml == null || html.indexOf(scanWhiteHtml) != -1)
            && (scanBlackHtml == null || html.indexOf(scanBlackHtml) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enableProp(Properties prop) {
        // TODO Auto-generated method stub
        //        host = prop.getProperty("host").equals("") ? null : prop.getProperty("host");
        //        filterUrl = prop.getProperty("filterUrl").equals("") ? null : prop.getProperty("filterUrl");
        this.prop = prop;
        try {
            ArrayList<String> config = PolicyParser.getPolicyParser().parse(prop).get(myId);
            outputWhiteUrl = config.get(5).equals("") ? null : config.get(5);
            outputBlackUrl = config.get(6).equals("") ? null : config.get(6);
            outputWhiteHtml = config.get(7).equals("") ? null : config.get(7);
            outputBlackHtml = config.get(8).equals("") ? null : config.get(8);
            scanWhiteUrl = config.get(9).equals("") ? null : config.get(9);
            scanBlackUrl = config.get(10).equals("") ? null : config.get(10);
            scanWhiteHtml = config.get(11).equals("") ? null : config.get(11);
            scanBlackHtml = config.get(12).equals("") ? null : config.get(12);
        } catch (Exception ex) {
            LOGGER.error("error", ex);
        }

    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public String getOutputWhiteUrl() {
        return outputWhiteUrl;
    }

    public void setOutputWhiteUrl(String outputWhiteUrl) {
        this.outputWhiteUrl = outputWhiteUrl;
    }

    public String getOutputWhiteHtml() {
        return outputWhiteHtml;
    }

    public void setOutputWhiteHtml(String outputWhiteHtml) {
        this.outputWhiteHtml = outputWhiteHtml;
    }

    public String getOutputBlackUrl() {
        return outputBlackUrl;
    }

    public void setOutputBlackUrl(String outputBlackUrl) {
        this.outputBlackUrl = outputBlackUrl;
    }

    public String getOutputBlackHtml() {
        return outputBlackHtml;
    }

    public void setOutputBlackHtml(String outputBlackHtml) {
        this.outputBlackHtml = outputBlackHtml;
    }

    public String getScanWhiteUrl() {
        return scanWhiteUrl;
    }

    public void setScanWhiteUrl(String scanWhiteUrl) {
        this.scanWhiteUrl = scanWhiteUrl;
    }

    public String getScanWhiteHtml() {
        return scanWhiteHtml;
    }

    public void setScanWhiteHtml(String scanWhiteHtml) {
        this.scanWhiteHtml = scanWhiteHtml;
    }

    public String getScanBlackUrl() {
        return scanBlackUrl;
    }

    public void setScanBlackUrl(String scanBlackUrl) {
        this.scanBlackUrl = scanBlackUrl;
    }

    public String getScanBlackHtml() {
        return scanBlackHtml;
    }

    public void setScanBlackHtml(String scanBlackHtml) {
        this.scanBlackHtml = scanBlackHtml;
    }

    @Override
    public void setMyId(int myId) {
        // TODO Auto-generated method stub
        this.myId = myId;
    }

}