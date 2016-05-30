/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control.connect;

import java.util.ArrayList;

/**
 *
 * @author arogarth
 */
public class LightsClients {
    private static LightsClients instance;
    
    private LightsClients() {
        
    }
    
    public static LightsClients getInstance() {
        if(LightsClients.instance == null)
            LightsClients.instance = new LightsClients();
        
        return LightsClients.instance;
    }
    
    public ArrayList<String> clientIps = new ArrayList<String>();
    
    public void addAddClient(String address) {
        System.out.println("New Client " + address);
        
        if(!isAddressExists(address)) this.clientIps.add(address);
    }
    
    public boolean isAddressExists(String address) {
        
        for(String addr : this.clientIps) {
            if(addr.equals(address)) return true;
        }
        
        return false;
    }
}
