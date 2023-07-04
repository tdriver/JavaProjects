/*
 * TedsToolboxApplet.java
 *
 * Created on April 26, 2005, 11:34 AM
 */

package driver.ted.toolbox;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author tdriver
 */
public class TedsToolboxApplet extends javax.swing.JApplet {
    public void init(){
        ToolBoxJPanel.app = new ToolBoxJPanel();
//        Thread kicker = new Thread(ToolBoxApp.app);
//        kicker.start();
//        jd = new T_JulianDate();
//        timer1.start();
                
        //ToolBoxApp.app.setVisible(true);
    
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(ToolBoxJPanel.app, BorderLayout.CENTER);
    }
    
}
