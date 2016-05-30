/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.lights.connect;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.archerlights.lights.Light;
import sun.swing.LightweightContent;

/**
 *
 * @author arogarth
 */
public class LightsClient {
    public LightsClient() {
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(8081), 0);
        
            server.createContext("/", new LightsHandler());
            server.setExecutor(null); // creates a default executor
            server.start();
            
        } catch (IOException ex) {
            //Logger.getLogger(RegistryServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static class LightsHandler implements HttpHandler  {

        @Override
        public void handle(HttpExchange he) throws IOException {
            InputStream is = he.getRequestBody();
            int ch;
            StringBuilder sb = new StringBuilder();
            while((ch = is.read()) != -1) sb.append((char)ch);
            
            System.out.println(sb.toString());
            
            String[] params = sb.toString().trim().split("&");
            Long duration = 0l;
            Integer times = 0;
            Long pause = 0l;
            Integer lights = 0;
            
            for(String param : params) {
                String[] val = param.split("=");
                
                if(val[0].equals("duration")) duration = Long.parseLong(val[1]);
                if(val[0].equals("pause")) pause = Long.parseLong(val[1]);
                if(val[0].equals("times")) times = Integer.parseInt(val[1]);
                if(val[0].equals("lights")) lights = Integer.parseInt(val[1]);
                                
            }
            this.acousticLoud(times, duration, pause);
            this.setLight(lights);
            
            String response = "SUCCESFULL";
            he.sendResponseHeaders(200, response.length());
            try (OutputStream os = he.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
        
        private void acousticLoud(int times, long duration, long pause) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for(int i=0; i < times; i++) {
                            System.out.println("Start Sound...");
                            Thread.sleep(duration);
                            System.out.println("End Sound...");
                            Thread.sleep(pause);
                        }
                    } catch (InterruptedException ex) { }
                }
            }).start();
        }
        
        private void setLight(Integer light) {
            // SET ALL OFF
            System.out.println("Set all lights OFF");
            
            if((Light.RED.value() & light) != 0) {
                // SET RED ON
                System.out.println("Set red light on");
                
            }
            if((Light.YELLOW.value() & light) != 0) {
                // SET YELLOW ON
                System.out.println("Set yellow light on");
                
            }
            if((Light.GREEN.value() & light) != 0) {
                // SET GREEN ON
                System.out.println("Set green light on");
                
            }
            if((Light.AB.value() & light) != 0) {
                // SET GREEN ON
                System.out.println("Set AB light on");
                
            }
            if((Light.CD.value() & light) != 0) {
                // SET GREEN ON
                System.out.println("Set CD light on");
                
            }
        }
    }
}
