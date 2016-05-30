/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.lights;

/**
 *
 * @author arogarth
 */
public enum Light {
    OFF     (0),
    RED     (1),
    YELLOW  (2),
    GREEN   (4),
    AB      (8),
    CD      (16);
            
    private int val = 0;

    private Light(int val) {
        this.val = val;
    }
    
    public Integer value() {
        return val;
    }
    
    
}
