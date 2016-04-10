package xm.bibibiradio.spider;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UrlPool {
    private ConcurrentLinkedQueue<WarpUrl> pool;
    private Set<String> outputSet;
    private Set<String> scanSet;
    private static UrlPool urlPool;
    
    public UrlPool(){
        pool = new ConcurrentLinkedQueue<WarpUrl>();
        outputSet = new HashSet<String>();
        scanSet = new HashSet<String>();
        
        
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
        if(ret = scanSet.add(warpUrl.getUrl().toString()))
            pool.add(warpUrl);
        return ret;
    }
    
    public boolean isContainAlreadyOutput(WarpUrl warpUrl){
        return outputSet.contains(warpUrl.getUrl().toString());
    }
    
    public boolean addAlreadyOutput(WarpUrl warpUrl){
        return outputSet.add(warpUrl.getUrl().toString());
    }
}
