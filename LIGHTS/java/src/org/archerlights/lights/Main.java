/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.lights;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.archerlights.lights.connect.LightsClient;

/**
 *
 * @author arogarth
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
        // TODO code application logic here
        LightsClient lightsClient = new LightsClient();
        
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String addr = "http://127.0.0.1" + ":8080/register";
                    URL url = new URL(addr);
                    
                    while(true) {
                        System.out.println("Try to register...");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.getInputStream();
                        con.disconnect();
                        
                        Thread.sleep(10l * 1000l);
                    }
                
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
}
