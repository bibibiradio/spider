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
    
    private ArrayList<String> outputWhiteUrls;
    private ArrayList<String> outputBlackUrls;
    private ArrayList<String> outputWhiteContents;
    private ArrayList<String> outputBlackContents;
    private ArrayList<String> scanWhiteUrls;
    private ArrayList<String> scanBlackUrls;
    private ArrayList<String> scanWhiteContents;
    private ArrayList<String> scanBlackContents;

    public StandardFilter(int myId) {
        this.myId = myId;
    }

    @Override
    public boolean isNeedOutput(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
    	
    	if(outputWhiteUrls != null) {
	    	for(String whiteUrl : outputWhiteUrls) {
	    		if(warpUrl.getUrl().toString().contains(whiteUrl)) {
	    			return true;
	    		}
	    	}
    	}
    	
    	if(outputWhiteContents != null) {
	    	for(String whiteContent : outputWhiteContents) {
	    		if(html.contains(whiteContent)) {
	    			return true;
	    		}
	    	}
    	}
    	
    	if(outputBlackUrls != null) {
	    	for(String blackUrl : outputBlackUrls) {
	    		if(warpUrl.getUrl().toString().contains(blackUrl)) {
	    			return false;
	    		}
	    	}
    	}
    	
    	if(outputBlackContents != null) {
	    	for(String blackContent : outputBlackContents) {
	    		if(html.contains(blackContent)) {
	    			return false;
	    		}
	    	}
    	}
    	
    	
    	return true;
    }

    @Override
    public boolean isNeedScan(WarpUrl warpUrl, String html) {
        // TODO Auto-generated method stub
    	if(scanWhiteUrls != null) {
	    	for(String whiteUrl : scanWhiteUrls) {
	    		if(warpUrl.getUrl().toString().contains(whiteUrl)) {
	    			return true;
	    		}
	    	}
    	}
    	
    	if(scanWhiteContents != null) {
	    	for(String whiteContent : scanWhiteContents) {
	    		if(html.contains(whiteContent)) {
	    			return true;
	    		}
	    	}
    	}
    	
    	if(scanBlackUrls != null) {
	    	for(String blackUrl : scanBlackUrls) {
	    		if(warpUrl.getUrl().toString().contains(blackUrl)) {
	    			return false;
	    		}
	    	}
    	}
    	
    	if(scanBlackContents != null) {
	    	for(String blackContent : scanBlackContents) {
	    		if(html.contains(blackContent)) {
	    			return false;
	    		}
	    	}
    	}
    	
    	
    	return true;
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
            
            if(outputWhiteUrl != null) {
            	outputWhiteUrls = PolicyParser.getPolicyParser().parseContentTag(outputWhiteUrl);
            }
            
            if(outputWhiteHtml != null) {
            	outputWhiteContents = PolicyParser.getPolicyParser().parseContentTag(outputWhiteHtml);
            }
            
            if(outputBlackUrl != null) {
            	outputBlackUrls = PolicyParser.getPolicyParser().parseContentTag(outputBlackUrl);
            }
            
            if(outputBlackHtml != null) {
            	outputBlackContents = PolicyParser.getPolicyParser().parseContentTag(outputBlackHtml);
            }
            
            if(scanWhiteUrl != null) {
            	scanWhiteUrls = PolicyParser.getPolicyParser().parseContentTag(scanWhiteUrl);
            }
            
            if(scanWhiteHtml != null) {
            	scanWhiteContents = PolicyParser.getPolicyParser().parseContentTag(scanWhiteHtml);
            }
            
            if(scanBlackUrl != null) {
            	scanBlackUrls = PolicyParser.getPolicyParser().parseContentTag(scanBlackUrl);
            }
            
            if(scanBlackHtml != null) {
            	scanBlackContents = PolicyParser.getPolicyParser().parseContentTag(scanBlackHtml);
            }
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

    public ArrayList<String> getOutputWhiteUrls() {
		return outputWhiteUrls;
	}

	public void setOutputWhiteUrls(ArrayList<String> outputWhiteUrls) {
		this.outputWhiteUrls = outputWhiteUrls;
	}

	public ArrayList<String> getOutputBlackUrls() {
		return outputBlackUrls;
	}

	public void setOutputBlackUrls(ArrayList<String> outputBlackUrls) {
		this.outputBlackUrls = outputBlackUrls;
	}

	public ArrayList<String> getOutputWhiteContents() {
		return outputWhiteContents;
	}

	public void setOutputWhiteContents(ArrayList<String> outputWhiteContents) {
		this.outputWhiteContents = outputWhiteContents;
	}

	public ArrayList<String> getOutputBlackContents() {
		return outputBlackContents;
	}

	public void setOutputBlackContents(ArrayList<String> outputBlackContents) {
		this.outputBlackContents = outputBlackContents;
	}

	public ArrayList<String> getScanWhiteUrls() {
		return scanWhiteUrls;
	}

	public void setScanWhiteUrls(ArrayList<String> scanWhiteUrls) {
		this.scanWhiteUrls = scanWhiteUrls;
	}

	public ArrayList<String> getScanBlackUrls() {
		return scanBlackUrls;
	}

	public void setScanBlackUrls(ArrayList<String> scanBlackUrls) {
		this.scanBlackUrls = scanBlackUrls;
	}

	public ArrayList<String> getScanWhiteContents() {
		return scanWhiteContents;
	}

	public void setScanWhiteContents(ArrayList<String> scanWhiteContents) {
		this.scanWhiteContents = scanWhiteContents;
	}

	public ArrayList<String> getScanBlackContents() {
		return scanBlackContents;
	}

	public void setScanBlackContents(ArrayList<String> scanBlackContents) {
		this.scanBlackContents = scanBlackContents;
	}

	public int getMyId() {
		return myId;
	}

	@Override
    public void setMyId(int myId) {
        // TODO Auto-generated method stub
        this.myId = myId;
    }

}