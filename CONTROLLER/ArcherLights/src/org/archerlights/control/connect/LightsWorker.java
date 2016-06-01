/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control.connect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.archerlights.control.Counter;
import org.archerlights.control.TeamMode;
import org.archerlights.control.Light;
import sun.net.www.http.HttpClient;

/**
 *
 * @author arogarth
 */
public class LightsWorker implements Counter.CounterListener {

    public LightsWorker() {
        new RegistryServer();
    }
    
    private void notifyClients(String params) {
        for(String address : LightsClients.getInstance().clientIps) {
            try {
                String addr = "http://" + address + ":8081/";
                
                URL url = new URL(addr);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                System.out.println("Send DATA: " + params);
                
                con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
                
                con.getInputStream();
                
                con.disconnect();
                
            } catch (Exception ex) {
            }
            
        }
    }
    
    private String getDefaultAcousticSettings(Integer times) {
        int pause = 500;
        int duration = 600;
        
        return String.format("times=%s&duration=%s&pause=%s", times, duration, pause);
    }
    
    private String getLights() {
        return this.getLights(null);
    }
    
    private String getLights(Light light) {
        
        int lights = this.lights;
        
        if(light != null) lights = light.value();
        
        if(this.mode.equals(TeamMode.AB)) {
            if((lights & Light.CD.value()) != 0) {
                lights = lights ^ Light.CD.value();    
            }
            
            lights = lights | Light.AB.value();
        } else {
            if((lights & Light.AB.value()) != 0) {
                lights = lights ^ Light.AB.value();
            }
            lights = lights | Light.CD.value();
        }
        
        this.lights = lights;
        
        return "lights=" + lights;
    }
    
    private TeamMode mode = TeamMode.AB;
    private Integer lights = Light.RED.value();
    
    @Override
    public void startWaiting() {
        String params = "";
        
        params = this.getDefaultAcousticSettings(2) + "&" + getLights(Light.RED);
        
        this.notifyClients(params);
    }

    @Override
    public void startShooting() {
        String params = "";
        
        params = this.getDefaultAcousticSettings(1) + "&" + getLights(Light.GREEN);
        
        this.notifyClients(params);
    }

    @Override
    public void stopShooting() {
        String params = "";
        
        params = this.getDefaultAcousticSettings(3) + "&" + getLights(Light.RED);
        
        this.notifyClients(params);
    }

    @Override
    public void emergencyStop() {
        String params = "";
        
        params = this.getDefaultAcousticSettings(10) + "&" + getLights(Light.RED);
        
        this.notifyClients(params);
    }

    @Override
    public void setTime(Integer time) {
        
        if (Counter.getInstance().isShooting()
         && !Counter.getInstance().isWaiting()
         && time <= Counter.getInstance().getCountDownTime()) {
            
            String params = "";

            params = getLights(Light.YELLOW);

            this.notifyClients(params);
            
        }
    }

    @Override
    public void setTeam(TeamMode mode, Integer currentRound, Integer maxRound) {
        this.mode = mode;
        
        String params =  getLights();
        
        this.notifyClients(params);
    }

}
