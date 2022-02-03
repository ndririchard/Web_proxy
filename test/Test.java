package test;

import cache.Cache;

public class Test {
    
    public static void testCache(){
        Cache.instance();
        byte[] req = new byte[1024], res = new byte[4024];
        Cache.instance().addreq_res(req, res);
        System.out.println(Cache.instance().find(req) == res);
    }
    public static void main(String[] args){
        testCache();
    }
}
