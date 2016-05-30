/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.archerlights.control.forms;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 *
 * @author arogarth
 */
public abstract class JFullscreenFrame extends javax.swing.JFrame {

    private GraphicsDevice device;

    private JFullscreenFrame previuos;

    public JFullscreenFrame(JFullscreenFrame parent) {
        this.previuos = parent;

        init();
    }

    private void init() {
        Boolean fullscreen = System.getProperty("fullscreen", "1").equals("1");
                
        if (fullscreen) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            device = ge.getDefaultScreenDevice();

            if (device.isFullScreenSupported()) {
                setUndecorated(true);
                device.setFullScreenWindow(this);
            }

            setPreferredSize(new Dimension(
                    device.getDisplayMode().getWidth(),
                    device.getDisplayMode().getHeight()
            ));

            this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        } else {
            setPreferredSize(new Dimension(1024, 600));
        }
        
        pack();
    }
}
