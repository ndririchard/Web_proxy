package cache;

import java.util.ArrayList;
import logger.Logger;

public class Cache {
    private ArrayList<byte[]> _req = new ArrayList<byte[]>(),  
                            _res = new ArrayList<byte[]>();
    private static Cache _cache;
    // constructor
    private Cache(){
        Logger.instance().info("Cache", "New cache is created");
    }
    public static Cache instance() {
        if (_cache == null){
            _cache = new Cache();
        }
        return _cache;
    }
    // properties
    public void clearCache(){
        this._req.clear(); this._res.clear();
        Logger.instance().info("Cache", "The cache is now empty");
    }
    public void addreq_res(byte[] req, byte[] res){
        this._req.add(req); this._res.add(res);
        Logger.instance().info("Cache", "New request is added to the cache");
    }
    public byte[] find(byte[] req){
        byte[] res = null;
        for (int i=0; i<this._req.size(); i++){
            if (this._req.get(i) == req){
                res = this._res.get(i);
            }
        }
        return res;
    }

}
