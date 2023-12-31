/*
 * Geodetic_ECEF.java
 *
 * Created on August 26, 2002, 8:48 PM
 */

package driver.ted.toolbox;

/**
 *
 * @author  tdriver
 */
public class Geodetic_ECEFFrame extends javax.swing.JInternalFrame {
    boolean isApplet;
    /** Creates new form Geodetic_ECEF */
    public Geodetic_ECEFFrame(boolean applet) {
        isApplet = applet;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        locationInputBean1 = new driver.ted.beans.Location.LocationInputBean();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 204, 0)));
        setClosable(true);
        setIconifiable(true);
        setTitle("Geodetic/ECEF Conversion");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/coordtool.gif")));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        locationInputBean1.setBorder(new javax.swing.border.TitledBorder(null, " Location", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP));
        getContentPane().add(locationInputBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 11, 290, 190));

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-345)/2, (screenSize.height-246)/2, 345, 246);
    }//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
       if(!isApplet){
           driver.ted.toolbox.ToolBoxApp.decrementFrameCount();
       }else{
           driver.ted.toolbox.ToolBoxJPanel.decrementFrameCount();
       }       
    }//GEN-LAST:event_formInternalFrameClosed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private driver.ted.beans.Location.LocationInputBean locationInputBean1;
    // End of variables declaration//GEN-END:variables
    
}
