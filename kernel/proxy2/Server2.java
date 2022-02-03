package kernel.proxy2;

import java.io.*;
import java.net.*;

import utility.Utility;

public class Server2 {
    private String host;
    private int rp, lp;
    // constructor
    public Server2(String host, int rp, int lp){
        this.host = host;
        this.rp = rp;
        this.lp = lp;
        System.out.println("Starting proxy for " + host + ":" + rp + " on port " + lp);
        this.runServer();
    }
    //properties
    private void runServer() {
        try{
            try (// Creating a ServerSocket to listen for connections with  
            ServerSocket s = new ServerSocket(this.lp)) {
                final byte[] request =new byte[501];
                byte[] reply = new byte[501];

                while(true){
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
                        String text = "Proxy server cannot connect to " + host + ":"  
                        + rp + ":\n" + e + "\n";
                        byte[] msg = text.getBytes();
                        out.print(Utility.instance().encrypt(msg));  
                        out.flush();  
                        client.close();  
                        continue;  
                    }

                    // Get server streams.  
                    final InputStream streamFromServer = server.getInputStream();  
                    final OutputStream streamToServer = server.getOutputStream();

                    // a thread to read the client's requests and pass them  
                    // to the server. A separate thread for asynchronous.  
                    Thread t = new Thread() {  
                        public void run() {  
                        int bytesRead;  
                        try {  
                            while ((bytesRead = streamFromClient.read(request)) != -1) {  
                            streamToServer.write(Utility.instance().decrypt(request), 0, bytesRead);  
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
                            streamToClient.write(Utility.instance().encrypt(reply), 0, bytesRead);  
                            streamToClient.flush();  
                        }  
                    } catch (IOException e) {  
                    }  
                    // The server closed its connection to us, so we close our  
                    // connection to our client.  
                    streamToClient.close();
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
