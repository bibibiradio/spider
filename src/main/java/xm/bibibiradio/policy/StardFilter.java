package xm.bibibiradio.policy;

import java.util.Properties;

import xm.bibibiradio.spider.WarpUrl;

class StardFilter implements SpiderFilter {
    private Properties prop;
    private String     host;
    private String     filterUrl;

    @Override
    public boolean isNeedOutput(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isNeedScan(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
        if ((host == null || warpUrl.getUrl().getHost().indexOf(host) != -1)
            && (filterUrl == null || warpUrl.getUrl().toString().indexOf(filterUrl) == -1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void enableProp(Properties prop) {
        // TODO Auto-generated method stub
        host = prop.getProperty("host").equals("") ? null : prop.getProperty("host");
        filterUrl = prop.getProperty("filterUrl").equals("") ? null : prop.getProperty("filterUrl");
        this.prop = prop;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

}