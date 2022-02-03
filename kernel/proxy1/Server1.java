package kernel.proxy1;

import java.io.*;
import java.net.*;
import cache.Cache;
import utility.Utility;

public class Server1 {
    private String host;
    private int rp, lp;
    // constructor
    public Server1(String host, int rp, int lp){
        this.host = host;
        this.rp = rp;
        this.lp = lp;
        System.out.println("Starting proxy for " + host + ":" + rp + " on port " + lp);
        this.runServer();
    }
    //properties
    private void runServer(){
        try{
            try (// Creating a ServerSocket to listen for connections with  
            ServerSocket s = new ServerSocket(this.lp)) {
                final byte[] request = new byte[501];
                byte[] reply = new byte[501];

                while (true){
                    Socket client = null, server = null; 

                    // It will wait for a connection on the local port  
                    client = s.accept();  
                    final InputStream streamFromClient = client.getInputStream();  
                    final OutputStream streamToClient = client.getOutputStream();

                    // create connection to the server
                    try {  
                        server = new Socket(host, rp);  
                    } catch (IOException e) {  
                        PrintWriter out = new PrintWriter(streamToClient);  
                        out.print("Proxy server cannot connect to " + host + ":"  
                            + rp + ":\n" + e + "\n");  
                        out.flush();  
                        client.close();  
                        continue;  
                    } 

                    // Get server streams.  
                    final InputStream streamFromServer = server.getInputStream();  
                    final OutputStream streamToServer = server.getOutputStream();

                    // catch request and if it has already be done
                    streamFromClient.read(request);
                    if (Cache.instance().find(request) == null){
                        Thread t = new Thread() {  
                            public void run() {  
                              int bytesRead;  
                              try {  
                                while ((bytesRead = streamFromClient.read(request)) != -1) {  
                                  streamToServer.write(Utility.instance().encrypt(request), 0, bytesRead);  
                                  streamToServer.flush();  
                                }  
                              } catch (IOException e) {  
                              }  
                    
                              // the client closed the connection to us, so close our  
                              // connection to the server.  
                              try {  
                                streamToServer.close();  
                              } catch (IOException e) {  
                              }  
                            }  
                        };  
                    
                        // Start the client-to-server request thread running  
                        t.start(); 
                        
                        // Read the server's responses  
                        // and pass them back to the client. 
                        
                        int bytesRead;  
                        try {  
                            while ((bytesRead = streamFromServer.read(reply)) != -1) {
                                System.out.println(bytesRead);
                                streamToClient.write(Utility.instance().decrypt(reply), 0, bytesRead);  
                                streamToClient.flush();  
                            }  
                        } catch (IOException e) {  
                        }  
                        // The server closed its connection to us, so we close our  
                        // connection to our client.  
                        streamToClient.close(); 

                        // add the request to the cache
                        Cache.instance().addreq_res(request, reply);  

                    }else{
                        reply = Cache.instance().find(request);
                        streamToClient.write(reply, 0, reply.length);  
                        streamToClient.flush();  
                    }
                    try {  
                        if (server != null)  
                          server.close();  
                        if (client != null)  
                          client.close();  
                      } catch (IOException e) {  
                    }  
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
            
        }

    }
}
