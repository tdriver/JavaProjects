/*
 * TimeFrame.java
 *
 * Created on July 30, 2002, 3:30 PM
 */

package driver.ted.toolbox;
import driver.ted.utilities.T_JulianDate;
import java.awt.event.*;
/**
 *
 * @author  tdriver
 */
public class TimeFrame extends javax.swing.JInternalFrame {
    
    T_JulianDate jdate;
    boolean isApplet;
    javax.swing.Timer timer1 = new javax.swing.Timer(1000,new ActionListener(){
      public void actionPerformed(ActionEvent evt) {
          jdate.advance(0,0,0,0,0,1);
          updateDisplay();
        }
      });

    /** Creates new form TimeFrame */
    public TimeFrame(boolean applet) {
        isApplet = applet;
        initComponents();
        timer1.stop();
        jdate = new T_JulianDate();
        updateDisplay();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        dateField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        modifiedJulianDateField = new javax.swing.JTextField();
        julianDateField = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dayOfYearField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        dayOfWeekField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        leapYearField = new javax.swing.JTextField();
        longDateFormatCheckBox = new javax.swing.JCheckBox();
        militaryTimeCheckBox = new javax.swing.JCheckBox();
        updateTimeCB = new javax.swing.JCheckBox();
        timeInputBean1 = new driver.ted.beans.TimeDate.TimeInputBean();
        dateInputBean1 = new driver.ted.beans.TimeDate.DateInputBean();
        julianDateInputBean1 = new driver.ted.beans.TimeDate.JulianDateInputBean();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        dayOfYearInput = new javax.swing.JTextField();
        getCurrentTimeButton = new javax.swing.JButton();
        updateCurrentTime = new javax.swing.JCheckBox();
        dateTimeSpinnerBean1 = new driver.ted.beans.TimeDate.dateTimeSpinnerBean();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 51, 255)));
        setClosable(true);
        setIconifiable(true);
        setTitle("Time Analysis");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/timetool.gif")));
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

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(new javax.swing.border.EtchedBorder());
        jLabel1.setText("Date");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        dateField.setEditable(false);
        dateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dateField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(dateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 130, -1));

        jLabel2.setText("Time");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, -1, -1));

        timeField.setEditable(false);
        timeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        timeField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(timeField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 130, -1));

        jLabel3.setText("Julian Date");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        modifiedJulianDateField.setEditable(false);
        modifiedJulianDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        modifiedJulianDateField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(modifiedJulianDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 130, -1));

        julianDateField.setEditable(false);
        julianDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        julianDateField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(julianDateField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 130, -1));

        jLabel31.setText("Modified Julian Date");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, -1, -1));

        jLabel5.setText("Day of Year");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        dayOfYearField.setEditable(false);
        dayOfYearField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dayOfYearField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(dayOfYearField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 130, -1));

        jLabel6.setText("Day of Week");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, -1, -1));

        dayOfWeekField.setEditable(false);
        dayOfWeekField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dayOfWeekField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(dayOfWeekField, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 130, -1));

        jLabel7.setText("Leap Year?");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        leapYearField.setEditable(false);
        leapYearField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        leapYearField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel1.add(leapYearField, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 60, -1));

        longDateFormatCheckBox.setSelected(true);
        longDateFormatCheckBox.setText("Long Date");
        longDateFormatCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                longDateFormatCheckBoxActionPerformed(evt);
            }
        });

        jPanel1.add(longDateFormatCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 20));

        militaryTimeCheckBox.setSelected(true);
        militaryTimeCheckBox.setText("24 Hour");
        militaryTimeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                militaryTimeCheckBoxActionPerformed(evt);
            }
        });

        jPanel1.add(militaryTimeCheckBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, -1, 20));

        updateTimeCB.setText("Continuously Update Time");
        updateTimeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTimeCBActionPerformed(evt);
            }
        });

        jPanel1.add(updateTimeCB, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 170, -1, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 290, 210));

        timeInputBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                timeInputBean1PropertyChange(evt);
            }
        });

        getContentPane().add(timeInputBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 320, -1, -1));

        dateInputBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateInputBean1PropertyChange(evt);
            }
        });

        getContentPane().add(dateInputBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        julianDateInputBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                julianDateInputBean1PropertyChange(evt);
            }
        });

        getContentPane().add(julianDateInputBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 320, -1, -1));

        jLabel4.setText("Enter Paramters");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 299, -1, -1));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBorder(new javax.swing.border.TitledBorder("Day of Year"));
        jPanel2.setMaximumSize(getPreferredSize());
        jPanel2.setMinimumSize(getPreferredSize());
        jPanel2.setPreferredSize(new java.awt.Dimension(122, 43));
        dayOfYearInput.setMaximumSize(getPreferredSize());
        dayOfYearInput.setMinimumSize(getPreferredSize());
        dayOfYearInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayOfYearInputActionPerformed(evt);
            }
        });

        jPanel2.add(dayOfYearInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 17, 111, 17));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, -1, 40));

        getCurrentTimeButton.setText("Get Current Time");
        getCurrentTimeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getCurrentTimeButtonActionPerformed(evt);
            }
        });

        getContentPane().add(getCurrentTimeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, -1, -1));

        updateCurrentTime.setText("Continuously Update Current Time");
        updateCurrentTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateCurrentTimeActionPerformed(evt);
            }
        });

        getContentPane().add(updateCurrentTime, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, -1, -1));

        dateTimeSpinnerBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTimeSpinnerBean1PropertyChange(evt);
            }
        });

        getContentPane().add(dateTimeSpinnerBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(65, 405, 240, 50));

        setBounds(0, 0, 400, 495);
    }//GEN-END:initComponents

    private void dateTimeSpinnerBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTimeSpinnerBean1PropertyChange
        jdate = new T_JulianDate(dateTimeSpinnerBean1.getDateTime());
        updateDisplay();
    }//GEN-LAST:event_dateTimeSpinnerBean1PropertyChange

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if(!isApplet){
            driver.ted.toolbox.ToolBoxApp.decrementFrameCount();
        }else{
            driver.ted.toolbox.ToolBoxJPanel.decrementFrameCount();
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void updateCurrentTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateCurrentTimeActionPerformed
        if(updateCurrentTime.isSelected()){
            jdate = new T_JulianDate();
            timer1.start();
            getCurrentTimeButton.setEnabled(false);
            updateTimeCB.setEnabled(false);
        }else{
            timer1.stop();
            getCurrentTimeButton.setEnabled(true);
            updateTimeCB.setEnabled(true);
        }
    }//GEN-LAST:event_updateCurrentTimeActionPerformed

    private void dayOfYearInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayOfYearInputActionPerformed
        int doy = jdate.getDayOfYear();
        try{
          doy = Integer.parseInt(dayOfYearInput.getText());
        }catch(NumberFormatException nfe){
          javax.swing.JOptionPane.showMessageDialog(this,
               "Day of Year must be an integer",
               "Error in Day of Year Calculation",
               javax.swing.JOptionPane.ERROR_MESSAGE);
          return;
        }
        jdate.setDayOfYear(doy);
        updateDisplay();
    }//GEN-LAST:event_dayOfYearInputActionPerformed

    private void julianDateInputBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_julianDateInputBean1PropertyChange
        jdate = new T_JulianDate(julianDateInputBean1.getJD());
        updateDisplay();
    }//GEN-LAST:event_julianDateInputBean1PropertyChange

    private void timeInputBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_timeInputBean1PropertyChange
        jdate = new T_JulianDate( // month, day, year hour, min, sec
            dateInputBean1.getMonth(),
            dateInputBean1.getDay(),
            dateInputBean1.getYear(),
            timeInputBean1.getHour(),
            timeInputBean1.getMinute(),
            timeInputBean1.getSecond());
//        System.out.println(dateInputBean1.getMonth());
//        System.out.println(dateInputBean1.getDay());
//        System.out.println(dateInputBean1.getYear());
//        System.out.println(timeInputBean1.getHour());
//        System.out.println(timeInputBean1.getMinute());
//        System.out.println(timeInputBean1.getSecond());
        updateDisplay();
    }//GEN-LAST:event_timeInputBean1PropertyChange

    private void updateTimeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateTimeCBActionPerformed
        if(updateTimeCB.isSelected()){
            timer1.start();
            getCurrentTimeButton.setEnabled(false);
        }else{
            timer1.stop();
            getCurrentTimeButton.setEnabled(true);
        }
    }//GEN-LAST:event_updateTimeCBActionPerformed

    private void getCurrentTimeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getCurrentTimeButtonActionPerformed
        jdate = new T_JulianDate();
        updateDisplay();
    }//GEN-LAST:event_getCurrentTimeButtonActionPerformed

    private void militaryTimeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_militaryTimeCheckBoxActionPerformed
        updateDisplay();
    }//GEN-LAST:event_militaryTimeCheckBoxActionPerformed

    private void longDateFormatCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_longDateFormatCheckBoxActionPerformed
        updateDisplay();
    }//GEN-LAST:event_longDateFormatCheckBoxActionPerformed

    private void dateInputBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateInputBean1PropertyChange
        // get all of the time and date inputs and create a time object
        jdate = new T_JulianDate( // month, day, year hour, min, sec
            dateInputBean1.getMonth(),
            dateInputBean1.getDay(),
            dateInputBean1.getYear(),
            timeInputBean1.getHour(),
            timeInputBean1.getMinute(),
            timeInputBean1.getSecond());
        System.out.println(dateInputBean1.getMonth());
        System.out.println(dateInputBean1.getDay());
        System.out.println(dateInputBean1.getYear());
        System.out.println(timeInputBean1.getHour());
        System.out.println(timeInputBean1.getMinute());
        System.out.println(timeInputBean1.getSecond());
        updateDisplay();
    }//GEN-LAST:event_dateInputBean1PropertyChange
    
    private void updateDisplay(){
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
        if(longDateFormatCheckBox.isSelected()){
            dateField.setText(jdate.getDate().toMediumString());
        }else{
            dateField.setText(jdate.getDate().toString());
        }
        if(militaryTimeCheckBox.isSelected()){
            timeField.setText(jdate.getTime().toString());
        }else{
            timeField.setText(jdate.getTime().toAMPMString());
        }
        julianDateField.setText(df.format(jdate.getJulianDate()));
        modifiedJulianDateField.setText(df.format(jdate.getModifiedJulianDate()));
        dayOfYearField.setText(String.valueOf(jdate.getDayOfYear()));
        dayOfWeekField.setText(jdate.getDayOfWeek());
        leapYearField.setText(String.valueOf(jdate.getLeapYear()));
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dateField;
    private driver.ted.beans.TimeDate.DateInputBean dateInputBean1;
    private driver.ted.beans.TimeDate.dateTimeSpinnerBean dateTimeSpinnerBean1;
    private javax.swing.JTextField dayOfWeekField;
    private javax.swing.JTextField dayOfYearField;
    private javax.swing.JTextField dayOfYearInput;
    private javax.swing.JButton getCurrentTimeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField julianDateField;
    private driver.ted.beans.TimeDate.JulianDateInputBean julianDateInputBean1;
    private javax.swing.JTextField leapYearField;
    private javax.swing.JCheckBox longDateFormatCheckBox;
    private javax.swing.JCheckBox militaryTimeCheckBox;
    private javax.swing.JTextField modifiedJulianDateField;
    private javax.swing.JTextField timeField;
    private driver.ted.beans.TimeDate.TimeInputBean timeInputBean1;
    private javax.swing.JCheckBox updateCurrentTime;
    private javax.swing.JCheckBox updateTimeCB;
    // End of variables declaration//GEN-END:variables
    
}
