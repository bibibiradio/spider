package xm.bibibiradio.policy;

import java.util.Properties;

import xm.bibibiradio.spider.WarpUrl;

class StardFilter implements SpiderFilter {
    private Properties prop;
    private String     outputWhiteUrl, outputWhiteHtml, outputBlackUrl, outputBlackHtml;
    private String     scanWhiteUrl, scanWhiteHtml, scanBlackUrl, scanBlackHtml;

    @Override
    public boolean isNeedOutput(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        if ((outputWhiteUrl == null || warpUrl.getUrl().getHost().indexOf(outputWhiteUrl) != -1)
            && (outputBlackUrl == null || warpUrl.getUrl().toString().indexOf(outputBlackUrl) == -1)
            && (outputWhiteHtml == null || warpUrl.getUrl().getHost().indexOf(outputWhiteHtml) != -1)
            && (outputBlackHtml == null || warpUrl.getUrl().getHost().indexOf(outputBlackHtml) != -1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNeedScan(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        if ((scanWhiteUrl == null || warpUrl.getUrl().getHost().indexOf(scanWhiteUrl) != -1)
            && (scanBlackUrl == null || warpUrl.getUrl().toString().indexOf(scanBlackUrl) == -1)
            && (scanWhiteHtml == null || warpUrl.getUrl().getHost().indexOf(scanWhiteHtml) != -1)
            && (scanBlackHtml == null || warpUrl.getUrl().getHost().indexOf(scanBlackHtml) != -1)) {
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

    

}