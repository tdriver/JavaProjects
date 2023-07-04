/*
 * ToolBoxJPanel.java
 *
 * Created on May 14, 2005, 10:24 AM
 */

package driver.ted.toolbox;
import javax.swing.JWindow;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
/**
 *
 * @author  tdriver
 */
public class ToolBoxJPanel extends javax.swing.JPanel {
    public static int frameCount = 0;
    static ToolBoxJPanel app;
//    final String latitudePref = "Latitude";
//    final String longitudePref = "Longitude";
//    final String heightPref = "Height";
//    final String timeOffsetPref = "TimeOffset";
    
    /** Creates new form ToolBoxJPanel */
    public ToolBoxJPanel() {
        initComponents();
        desktopPane.setBackground(java.awt.Color.black);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jToolBar1 = new javax.swing.JToolBar();
        timetoolButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        timetoolButton1 = new javax.swing.JButton();
        gpstoolButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        gpstoolButton1 = new javax.swing.JButton();
        coordinatetoolButton = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        coordinatetoolButton1 = new javax.swing.JButton();
        solartoolButton = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        solartoolButton1 = new javax.swing.JButton();
        lunartoolButton = new javax.swing.JButton();
        siderealtoolButton = new javax.swing.JButton();
        utilitiestoolButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        utilitiestoolButton1 = new javax.swing.JButton();
        desktopPane = new javax.swing.JDesktopPane();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);
        jToolBar1.setName("Ted's Toolbox Toolbar");
        timetoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/timetool.gif")));
        timetoolButton.setToolTipText("Time Analysis");
        timetoolButton.setFocusPainted(false);
        timetoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(timetoolButton);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setMaximumSize(new java.awt.Dimension(1, 25));
        jToolBar1.add(jSeparator2);

        timetoolButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/timetool.gif")));
        timetoolButton1.setToolTipText("Time Interval Analysis");
        timetoolButton1.setFocusPainted(false);
        timetoolButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timetoolButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(timetoolButton1);

        gpstoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/GPStool.gif")));
        gpstoolButton.setToolTipText("GPS time");
        gpstoolButton.setFocusPainted(false);
        gpstoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gpstoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(gpstoolButton);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator3.setMaximumSize(new java.awt.Dimension(1, 25));
        jToolBar1.add(jSeparator3);

        gpstoolButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/GPStool.gif")));
        gpstoolButton1.setToolTipText("Time Of Week");
        gpstoolButton1.setFocusPainted(false);
        gpstoolButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gpstoolButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(gpstoolButton1);

        coordinatetoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/coordtool.gif")));
        coordinatetoolButton.setToolTipText("Geodetic/ECEF Conversion");
        coordinatetoolButton.setFocusPainted(false);
        coordinatetoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordinatetoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(coordinatetoolButton);

        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator6.setMaximumSize(new java.awt.Dimension(1, 25));
        jToolBar1.add(jSeparator6);

        coordinatetoolButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/coordtool.gif")));
        coordinatetoolButton1.setToolTipText("Geodetic DIstance Calculator");
        coordinatetoolButton1.setFocusPainted(false);
        coordinatetoolButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coordinatetoolButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(coordinatetoolButton1);

        solartoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/solartool.gif")));
        solartoolButton.setToolTipText("Daily Solar Tool");
        solartoolButton.setFocusPainted(false);
        solartoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solartoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(solartoolButton);

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setMaximumSize(new java.awt.Dimension(1, 25));
        jToolBar1.add(jSeparator5);

        solartoolButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/solartool.gif")));
        solartoolButton1.setToolTipText("Solar Plotter");
        solartoolButton1.setFocusPainted(false);
        solartoolButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solartoolButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(solartoolButton1);

        lunartoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/lunartool.gif")));
        lunartoolButton.setToolTipText("Lunar Tool");
        lunartoolButton.setFocusPainted(false);
        lunartoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lunartoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(lunartoolButton);

        siderealtoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/siderealtool.gif")));
        siderealtoolButton.setToolTipText("Sidereal Tool");
        siderealtoolButton.setFocusPainted(false);
        siderealtoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siderealtoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(siderealtoolButton);

        utilitiestoolButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/utilitiestool.gif")));
        utilitiestoolButton.setToolTipText("Number Conversions");
        utilitiestoolButton.setFocusPainted(false);
        utilitiestoolButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utilitiestoolButtonActionPerformed(evt);
            }
        });

        jToolBar1.add(utilitiestoolButton);

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator4.setMaximumSize(new java.awt.Dimension(1, 25));
        jToolBar1.add(jSeparator4);

        utilitiestoolButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/utilitiestool.gif")));
        utilitiestoolButton1.setToolTipText("Color Chooser");
        utilitiestoolButton1.setFocusPainted(false);
        utilitiestoolButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utilitiestoolButton1ActionPerformed(evt);
            }
        });

        jToolBar1.add(utilitiestoolButton1);

        add(jToolBar1, java.awt.BorderLayout.NORTH);

        desktopPane.setMinimumSize(new java.awt.Dimension(685, 540));
        add(desktopPane, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void utilitiestoolButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utilitiestoolButton1ActionPerformed
        ColorChooserFrame f = new ColorChooserFrame(true);
        showInternalFrame(f);
    }//GEN-LAST:event_utilitiestoolButton1ActionPerformed

    private void utilitiestoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utilitiestoolButtonActionPerformed
       NumberConversionFrame ncf = new NumberConversionFrame(true);
       showInternalFrame(ncf);
    }//GEN-LAST:event_utilitiestoolButtonActionPerformed

    private void siderealtoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siderealtoolButtonActionPerformed
       SiderealToolFrame SDFrame = new SiderealToolFrame(true);
        showInternalFrame(SDFrame);
    }//GEN-LAST:event_siderealtoolButtonActionPerformed

    private void lunartoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lunartoolButtonActionPerformed
        LunarToolFrame Frame = new LunarToolFrame(true);
        showInternalFrame(Frame);
    }//GEN-LAST:event_lunartoolButtonActionPerformed

    private void solartoolButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solartoolButton1ActionPerformed
        DurationSolarFrame Frame = new DurationSolarFrame(true);
        showInternalFrame(Frame);
    }//GEN-LAST:event_solartoolButton1ActionPerformed

    private void solartoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solartoolButtonActionPerformed
       SolarRiseSetFrame rsFrame = new SolarRiseSetFrame(true);
        showInternalFrame(rsFrame);
    }//GEN-LAST:event_solartoolButtonActionPerformed

    private void coordinatetoolButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordinatetoolButton1ActionPerformed
        GeodeticDistanceFrame gdf = new GeodeticDistanceFrame(true);
        showInternalFrame(gdf);
    }//GEN-LAST:event_coordinatetoolButton1ActionPerformed

    private void coordinatetoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coordinatetoolButtonActionPerformed
       Geodetic_ECEFFrame ge = new Geodetic_ECEFFrame(true);
        showInternalFrame(ge);
    }//GEN-LAST:event_coordinatetoolButtonActionPerformed

    private void gpstoolButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gpstoolButton1ActionPerformed
        GPSTimeOfWeekFrame GPSFrame = new GPSTimeOfWeekFrame(true);
        showInternalFrame(GPSFrame);
    }//GEN-LAST:event_gpstoolButton1ActionPerformed

    private void gpstoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gpstoolButtonActionPerformed
        GPSToolFrame GPSFrame = new GPSToolFrame(true);
        showInternalFrame(GPSFrame);
    }//GEN-LAST:event_gpstoolButtonActionPerformed

    private void timetoolButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetoolButton1ActionPerformed
        TimeIntervalFrame tf = new TimeIntervalFrame(true);
        showInternalFrame(tf);
    }//GEN-LAST:event_timetoolButton1ActionPerformed

    private void timetoolButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_timetoolButtonActionPerformed
        TimeFrame tf = new TimeFrame(true);
        showInternalFrame(tf);
    }//GEN-LAST:event_timetoolButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton coordinatetoolButton;
    private javax.swing.JButton coordinatetoolButton1;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JButton gpstoolButton;
    private javax.swing.JButton gpstoolButton1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JButton lunartoolButton;
    private javax.swing.JButton siderealtoolButton;
    private javax.swing.JButton solartoolButton;
    private javax.swing.JButton solartoolButton1;
    private javax.swing.JButton timetoolButton;
    private javax.swing.JButton timetoolButton1;
    private javax.swing.JButton utilitiestoolButton;
    private javax.swing.JButton utilitiestoolButton1;
    // End of variables declaration//GEN-END:variables
    
    
    private void showInternalFrame(javax.swing.JInternalFrame frame){
        desktopPane.add(frame);
        frame.setLocation(24*frameCount, 24*frameCount);
        frame.show();
        frameCount++;
    }
    
     public static void decrementFrameCount() {
        frameCount--;
        if(frameCount < 0 ) frameCount = 0;
    }
}
