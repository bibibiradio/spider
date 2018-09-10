package xm.bibibiradio.spider;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UrlPool implements Serializable{
    private ConcurrentLinkedQueue<WarpUrl> pool;
    private Set<Integer> outputSet;
    private Set<Integer> scanSet;
    private static UrlPool urlPool;
    
    public UrlPool(){
        pool = new ConcurrentLinkedQueue<WarpUrl>();
        outputSet = new HashSet<Integer>();
        scanSet = new HashSet<Integer>();
        
        
    }
    static public UrlPool getPool(){
        if(urlPool == null){
            urlPool = new UrlPool();
        }
        return urlPool;
    }
    
    public WarpUrl poll(){
        return pool.poll();
    }
    
    public boolean add(WarpUrl warpUrl){
        boolean ret;
        if(ret = scanSet.add(warpUrl.getResourceHash()))
            pool.add(warpUrl);
        return ret;
    }
    
    public boolean isContainAlreadyOutput(WarpUrl warpUrl){
    	return outputSet.contains(warpUrl.getResourceHash());
    }
    
    public boolean addAlreadyOutput(WarpUrl warpUrl){
    	return outputSet.add(warpUrl.getResourceHash());
    }
	public Set<Integer> getOutputSet() {
		return outputSet;
	}
	public void setOutputSet(Set<Integer> outputSet) {
		this.outputSet = outputSet;
	}
	public Set<Integer> getScanSet() {
		return scanSet;
	}
	public void setScanSet(Set<Integer> scanSet) {
		this.scanSet = scanSet;
	}
	public void setPool(ConcurrentLinkedQueue<WarpUrl> pool) {
		this.pool = pool;
	}
    
    
}
