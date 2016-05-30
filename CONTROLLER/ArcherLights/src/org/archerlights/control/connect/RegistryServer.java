/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control.connect;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.archerlights.control.Counter;

/**
 *
 * @author arogarth
 */
public class RegistryServer {
    
    public RegistryServer() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8080), 0);
        
            server.createContext("/register", new RegistryHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Startet registry-server...");
            
        } catch (IOException ex) {
            //Logger.getLogger(RegistryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static class RegistryHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("New Client ...");
            
            LightsClients.getInstance().addAddClient(
                    he.getRemoteAddress().getAddress().getHostAddress());
            
            String response = "SUCCESFULL";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
            
            Counter.getInstance().notifyClients();
        }
        
    }
}
