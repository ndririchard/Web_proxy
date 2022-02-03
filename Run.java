import kernel.proxy1.Server1;
import kernel.proxy2.Server2;
import proxy_listener.ProxyListener;
import utility.Utility;

public class Run {

    public static void main(String[] args) throws InterruptedException{
        //Utility.instance();

        Thread server1 = new Thread(){
            public void run(){
                String host = ProxyListener.instance().getSetup1()[0];
                int rp = Integer.parseInt(ProxyListener.instance().getSetup1()[1]);
                int lp = Integer.parseInt(ProxyListener.instance().getSetup1()[2]);
                new Server1(host, rp, lp);
            }
        };

        Thread server2 = new Thread(){
            public void run(){
                String host = ProxyListener.instance().getSetup2()[0];
                int rp = Integer.parseInt(ProxyListener.instance().getSetup2()[1]);
                int lp = Integer.parseInt(ProxyListener.instance().getSetup2()[2]);
                new Server2(host, rp, lp);
            }
        };

        server1.start(); server2.start();
        server1.join(); server2.join();

    }
    
}
