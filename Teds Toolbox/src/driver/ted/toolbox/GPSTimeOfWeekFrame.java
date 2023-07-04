/*
 * LunarToolFrame.java
 *
 * Created on September 10, 2002, 2:18 PM
 */

package driver.ted.toolbox;
import driver.ted.utilities.T_GPSTime;
import driver.ted.astro.T_LunarCoords;
import driver.ted.utilities.T_Utilities;
import driver.ted.utilities.T_JulianDate;
import driver.ted.utilities.T_Converter;
import driver.ted.utilities.T_Time;
import java.util.prefs.Preferences;
import java.awt.event.*;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 *
 * @author  tdriver
 */
public class GPSTimeOfWeekFrame extends javax.swing.JInternalFrame {
    
    String[] days = {"Sunday","Monday", "Tuesday","Wednesday","Thursday","Friday","Saturday"};
    javax.swing.SpinnerModel dayModel = new javax.swing.SpinnerListModel(days); 
    javax.swing.SpinnerNumberModel hourModel = new javax.swing.SpinnerNumberModel(0,0,23,1);
    javax.swing.SpinnerNumberModel minuteModel = new javax.swing.SpinnerNumberModel(0,0,59,1);
    javax.swing.SpinnerNumberModel secondModel = new javax.swing.SpinnerNumberModel(0,0,59,1);
    boolean isApplet;
    
    // update every second
    javax.swing.Timer timer1 = new javax.swing.Timer(1000,new ActionListener(){
      public void actionPerformed(ActionEvent evt) {
        T_JulianDate temp = new T_JulianDate();
        dayModel.setValue(temp.getDayOfWeek());
        hourModel.setValue(new Integer(temp.getTime().getHour()));
        minuteModel.setValue(new Integer(temp.getTime().getMinute()));
        secondModel.setValue(new Integer(temp.getTime().getSecond()));
        CalculateTimeOfWeek();
        }
      }); 
      
    
    /** Creates new form LunarRiseSetFrame */
    public GPSTimeOfWeekFrame(boolean applet) {
        isApplet = applet;
        initComponents();
        
    }
    
    
    void calcFromSeconds(ActionEvent e) {
    long towtemp;

    // Validate Input
    try{
      towtemp = Long.parseLong(towS.getText());
    }catch (NumberFormatException nfe){
      towS.grabFocus();
      towS.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in Seconds must be a valid number!",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (towtemp < 0 || towtemp > 604799){
      towS.grabFocus();
      towS.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in Seconds must be between 0 and 604799",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
    }else{

     updateDayAndTime((double)towtemp);
     // update the Z and K Fields
     CalculateTimeOfWeek();
    }
  }

  void calcFromZ(ActionEvent e) {
    long towZtemp;

    // Validate Input
    try{
      towZtemp = Long.parseLong(towZ.getText());
    }catch (NumberFormatException nfe){
      towZ.grabFocus();
      towZ.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in Z-Counts must be a valid number!",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (towZtemp < 0 || towZtemp > 403199){
      towZ.grabFocus();
      towZ.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in Z-Counts must be between 0 and 403199",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
    }else{
     // update the S and K Fields
     updateDayAndTime((double)(towZtemp*1.5));

     // update the S and K Fields
     CalculateTimeOfWeek();
    }
  }

  void calcFromK(ActionEvent e) {
    double towKtemp;

    // Validate Input
    try{
      towKtemp = Double.parseDouble(towK.getText());
    }catch (NumberFormatException nfe){
      towK.grabFocus();
      towK.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in K-Points must be a valid number!",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
      return;
    }
    if (towKtemp < 0 || towKtemp >= 672){
      towK.grabFocus();
      towK.setText("0");
      JOptionPane.showMessageDialog(this,"Time of Week in K-Points must be between 0 and less than 672",
                                      "Invalid Input",JOptionPane.WARNING_MESSAGE);
    }else{
     updateDayAndTime((double)(towKtemp*900.0));
     // update the S and Z Fields
     CalculateTimeOfWeek();
    }
  }

  void CalculateTimeOfWeek() {

      DecimalFormat kp = new DecimalFormat("0.000");

      String day = (String)dayModel.getValue();
      int dayIndex = getIndexOfDay(day);
      long hours = ((Integer)hourModel.getValue()).intValue();
      long minutes = ((Integer)minuteModel.getValue()).intValue();
      long seconds = ((Integer)secondModel.getValue()).intValue();
      
      long towSeconds = dayIndex*86400 + hours*3600 + minutes*60 + seconds;
                    
      String tempString = "";
      tempString += towSeconds;
      towS.setText(tempString);

      // Reset the Temp String
      tempString = "";

      // Calculate the Z-Counts
      double towZCounts  = towSeconds/1.5;
      tempString += (int)towZCounts;
      towZ.setText(tempString);

      // Reset the Temp String
      tempString = "";

      // Calculate the K_Points
      double towKPoints  = towSeconds/900.0;
      tempString += kp.format(towKPoints);
      towK.setText(tempString);

    }

  int getIndexOfDay(String day){
   if(day.compareTo(days[0]) == 0) return 0;
   else if (day.compareTo(days[1]) == 0) return 1;
   else if (day.compareTo(days[2]) == 0) return 2;
   else if (day.compareTo(days[3]) == 0) return 3;
   else if (day.compareTo(days[4]) == 0) return 4;
   else if (day.compareTo(days[5]) == 0) return 5;
   else if (day.compareTo(days[6]) == 0) return 6;
   return -1;
 }

  void updateDayAndTime(double tow){
    float d, h, m, s;

    d = (float)tow/86400;
    h = (float)(d - (int)d)*24;
    m = (float)(h - (int)h)*60;
    s = (float)(m - (int)m)*60;
    // round seconds
    if ( (s - (int)s) > 0.5) s += 1;

    // check rollover conditions
    if ( (s >= 60.00) ){
      s = 0;
      m += 1;
      if (m >= 60){
        m = 0;
        h += 1;
        if (h >= 24){
          h = 0;
          d += 1;
          if (d >= 7.0){
            JOptionPane.showMessageDialog(this,"Congratulations!  Ted Driver will buy you a soda because " +
                                          "he was sure this message would never come up!!!  e-mail him at driverte@yahoo.com" +
                                          " and tell him number 137 popped in the Toolbox program",
                                   "Invalid Input",JOptionPane.ERROR_MESSAGE);
          }
        }
      }
     }
     dayModel.setValue(days[(int)d]);
     hourModel.setValue(new Integer((int)h));
     minuteModel.setValue(new Integer((int)m));
     secondModel.setValue(new Integer((int)s));
  }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        buttonGroup1 = new javax.swing.ButtonGroup();
        towS = new javax.swing.JTextField();
        towK = new javax.swing.JTextField();
        towZ = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        minuteSpinner = new javax.swing.JSpinner();
        secondSpinner = new javax.swing.JSpinner();
        hourSpinner = new javax.swing.JSpinner();
        daySpinner = new javax.swing.JSpinner();
        jButton2 = new javax.swing.JButton();
        jToggleButton1 = new javax.swing.JToggleButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 102, 0)));
        setClosable(true);
        setIconifiable(true);
        setTitle("GPS Time of Week");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/GPStool.gif")));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setPreferredSize(new java.awt.Dimension(367, 259));
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

        towS.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        towS.setToolTipText("Enter the seconds of the week");
        towS.setBorder(new javax.swing.border.TitledBorder("Seconds"));
        towS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                towSActionPerformed(evt);
            }
        });

        getContentPane().add(towS, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 36, 110, -1));

        towK.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        towK.setToolTipText("Enter the K-Points of the week");
        towK.setBorder(new javax.swing.border.TitledBorder("K-Points"));
        towK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                towKActionPerformed(evt);
            }
        });

        getContentPane().add(towK, new org.netbeans.lib.awtextra.AbsoluteConstraints(234, 81, 110, -1));

        towZ.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        towZ.setToolTipText("Enter the K-Points of the week");
        towZ.setBorder(new javax.swing.border.TitledBorder("Z-Counts"));
        towZ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                towZActionPerformed(evt);
            }
        });

        getContentPane().add(towZ, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 128, 110, -1));

        jLabel1.setText("Time");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, -1));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Time Of Week");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 90, -1));

        minuteSpinner.setModel(minuteModel);
        minuteSpinner.setToolTipText("Select the minutes of the hour");
        minuteSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minuteSpinnerStateChanged(evt);
            }
        });

        getContentPane().add(minuteSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 80, -1));

        secondSpinner.setModel(secondModel);
        secondSpinner.setToolTipText("Select the seconds of the minute");
        secondSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                secondSpinnerStateChanged(evt);
            }
        });

        getContentPane().add(secondSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 80, -1));

        hourSpinner.setModel(hourModel);
        hourSpinner.setToolTipText("Select the hour of the day");
        hourSpinner.setMaximumSize(new java.awt.Dimension(33, 41));
        hourSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hourSpinnerStateChanged(evt);
            }
        });

        getContentPane().add(hourSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 80, -1));

        daySpinner.setModel(dayModel);
        daySpinner.setToolTipText("Select the day of the week");
        daySpinner.setValue("Sunday");
        daySpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                daySpinnerStateChanged(evt);
            }
        });

        getContentPane().add(daySpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 120, -1));

        jButton2.setText("Get Current Time");
        jButton2.setToolTipText("Sets the day, hour, minute and second to the current computer time");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 196, -1, -1));

        jToggleButton1.setText("Update");
        jToggleButton1.setToolTipText("Continuously updates display (1 sec interval)");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        getContentPane().add(jToggleButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(241, 196, -1, -1));

        jLabel3.setText("Hour:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, -1, -1));

        jLabel4.setText("Second:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, -1, -1));

        jLabel5.setText("Day:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel6.setText("Minute:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, -1, -1));

        pack();
    }//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if(!isApplet){
            driver.ted.toolbox.ToolBoxApp.decrementFrameCount();
        }else{
            driver.ted.toolbox.ToolBoxJPanel.decrementFrameCount();
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        if(jToggleButton1.isSelected()){
            timer1.start();
            jToggleButton1.setText("Stop");
        }else{
            timer1.stop();
            jToggleButton1.setText("Update");
        }
    }//GEN-LAST:event_jToggleButton1ActionPerformed

    private void towZActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_towZActionPerformed
        calcFromZ(evt);
    }//GEN-LAST:event_towZActionPerformed

    private void towKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_towKActionPerformed
        calcFromK(evt);
    }//GEN-LAST:event_towKActionPerformed

    private void towSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_towSActionPerformed
        calcFromSeconds(evt);
    }//GEN-LAST:event_towSActionPerformed

    private void secondSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_secondSpinnerStateChanged
        CalculateTimeOfWeek();
    }//GEN-LAST:event_secondSpinnerStateChanged

    private void minuteSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minuteSpinnerStateChanged
        CalculateTimeOfWeek();
    }//GEN-LAST:event_minuteSpinnerStateChanged

    private void hourSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hourSpinnerStateChanged
        CalculateTimeOfWeek();
    }//GEN-LAST:event_hourSpinnerStateChanged

    private void daySpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_daySpinnerStateChanged
       CalculateTimeOfWeek();
    }//GEN-LAST:event_daySpinnerStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    T_JulianDate temp = new T_JulianDate();
    dayModel.setValue(temp.getDayOfWeek());
    hourModel.setValue(new Integer(temp.getTime().getHour()));
    minuteModel.setValue(new Integer(temp.getTime().getMinute()));
    secondModel.setValue(new Integer(temp.getTime().getSecond()));
    CalculateTimeOfWeek();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JSpinner daySpinner;
    private javax.swing.JSpinner hourSpinner;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JSpinner minuteSpinner;
    private javax.swing.JSpinner secondSpinner;
    private javax.swing.JTextField towK;
    private javax.swing.JTextField towS;
    private javax.swing.JTextField towZ;
    // End of variables declaration//GEN-END:variables
    
}
