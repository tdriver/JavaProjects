/*
 * splashPane.java
 *
 * Created on June 5, 2002, 3:45 PM
 */

package driver.ted;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 *
 * @author  tdriver
 */
public class splashPane extends javax.swing.JPanel {

    /** Creates new form splashPane */
    public splashPane() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setLayout(null);

        setBorder(new javax.swing.border.LineBorder(java.awt.Color.white, 1, true));
        setForeground(java.awt.Color.white);
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setPreferredSize(new java.awt.Dimension(361, 114));
        jLabel2.setFont(new java.awt.Font("Dialog", 2, 24));
        jLabel2.setForeground(java.awt.Color.green);
        jLabel2.setText("Jed - The Journal Editor");
        add(jLabel2);
        jLabel2.setBounds(60, 10, 256, 30);

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel3.setForeground(java.awt.Color.green);
        jLabel3.setText("Version 1.3.3");
        add(jLabel3);
        jLabel3.setBounds(150, 40, 80, 20);

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel4.setForeground(java.awt.Color.green);
        jLabel4.setText("Copyright (c) 2001-2006 by Ted Driver");
        add(jLabel4);
        jLabel4.setBounds(81, 60, 220, 20);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/images/embarsa.gif")));
        add(jLabel6);
        jLabel6.setBounds(20, 30, 32, 36);

    }
    // </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables

   public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D) g;
    super.paintComponent(g);
    g2d.setPaint(
          new GradientPaint(5,30, // x1 y1
                            //new Color(50,36,212), // initial color
                            Color.black,
                            5,100,  // x2, y2
                            new Color(158,0,60), // end color
                            //Color.blue,
                            false));   // cyclic
    g2d.fill(new Rectangle2D.Double(0,0,600,500));
   }
}
