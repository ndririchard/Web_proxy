package proxy_listener;

import logger.Logger;

public class ProxyListener {
    private static ProxyListener _pl;
    private String host;
    // constructor
    private ProxyListener(){
        this.host = "10.188.197.85";
        Logger.instance().info("ProxyListener", "New instance of ProxyListener");
    }
    public static ProxyListener instance(){
        if (_pl == null){
            _pl = new ProxyListener();
        }
        return _pl;
    }
    // properties
    public String[] getSetup1(){
        /**
         * this function setup proxy socket
         * return [host, remoteport, localport]
         */
        String[] res = {this.host, "8002", "8001"};
        return res;
    }
    public String[] getSetup2(){
        String[] res = {this.host, "8000", "8002"};
        return res;
    }
}
