/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control;

import org.archerlights.control.connect.LightsWorker;
import org.archerlights.control.connect.RegistryServer;
import org.archerlights.control.forms.MainControl;

/**
 *
 * @author arogarth
 */
public class ArcherLights {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        for(int i=0; i<args.length; i++) {
            String option = args[i].replace("--", "");
            if(option.equals("nofullscreen")) {
                System.setProperty("fullscreen", "0");
            }
        }
        
        MainControl mc = new MainControl(null);
        
        Counter.getInstance().addCounterListerner(mc);
        Counter.getInstance().addCounterListerner(new LightsWorker());
        
        mc.initNewRound();
        mc.setVisible(true);
        
    }
    
}
