/*
 * TimeIntervalFrame.java
 *
 * Created on August 1, 2002, 4:46 PM
 */

/**
 *
 * @author  tdriver
 */

package driver.ted.toolbox;

import driver.ted.utilities.*;

public class TimeIntervalFrame extends javax.swing.JInternalFrame {
    
    T_JulianDate jdate1, jdate2;
    boolean isApplet;
    
    /** Creates new form TimeIntervalFrame */
    public TimeIntervalFrame(boolean applet) {
        isApplet = applet;
        initComponents();
        jdate2 = new T_JulianDate();        
        jdate1 = new T_JulianDate(2451544.5);
        
        date1Field.setText(jdate1.getDate().toString());
        time1Field.setText(jdate1.getTime().toString());
        jdate1Field.setText(new java.text.DecimalFormat("0.000000").format(jdate1.getJulianDate()));
        date2Field.setText(jdate2.getDate().toString());
        time2Field.setText(jdate2.getTime().toString());
        jdate2Field.setText(new java.text.DecimalFormat("0.000000").format(jdate2.getJulianDate()));
//        dateTimeSpinnerBean1.setDateTime(jdate1.getJulianDate());
//        dateTimeSpinnerBean2.setDateTime(jdate2.getJulianDate());
//       try{
//            dateInputBean2.setMonth(jdate2.getDate().getMonth());
//            dateInputBean2.setDay(jdate2.getDate().getDay());
//            dateInputBean2.setYear(jdate2.getDate().getYear());
//            timeInputBean2.setHour(jdate2.getTime().getHour());
//            timeInputBean2.setMinute(jdate2.getTime().getMinute());
//            timeInputBean2.setSecond(jdate2.getTime().getSecond());
//        }catch(java.beans.PropertyVetoException pve){
//            javax.swing.JOptionPane.showMessageDialog(this, 
//                pve.getMessage(),
//                "Error Setting Time 2",
//                javax.swing.JOptionPane.ERROR_MESSAGE);
//        }
        
        
        updateDisplay();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        dateTimeSpinnerBean2 = new driver.ted.beans.TimeDate.dateTimeSpinnerBean();
        getCurrentTimeButton2 = new javax.swing.JButton();
        julianDateInputBean2 = new driver.ted.beans.TimeDate.JulianDateInputBean();
        jPanel11 = new javax.swing.JPanel();
        julianDateInputBean1 = new driver.ted.beans.TimeDate.JulianDateInputBean();
        getCurrentTimeButton1 = new javax.swing.JButton();
        dateTimeSpinnerBean1 = new driver.ted.beans.TimeDate.dateTimeSpinnerBean();
        jPanel2 = new javax.swing.JPanel();
        ymdIntervalField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        dayIntervalField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        date2Field = new javax.swing.JTextField();
        time2Field = new javax.swing.JTextField();
        jdate2Field = new javax.swing.JTextField();
        jPanel31 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        date1Field = new javax.swing.JTextField();
        time1Field = new javax.swing.JTextField();
        jdate1Field = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 51, 255)));
        setClosable(true);
        setIconifiable(true);
        setTitle("Time Interval Analysis");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/timetool.gif")));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setPreferredSize(new java.awt.Dimension(502, 413));
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Set Time 2"));
        dateTimeSpinnerBean2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTimeSpinnerBean2PropertyChange(evt);
            }
        });

        jPanel1.add(dateTimeSpinnerBean2, new org.netbeans.lib.awtextra.AbsoluteConstraints(16, 25, 220, -1));

        getCurrentTimeButton2.setText("Get Current Time");
        getCurrentTimeButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getCurrentTimeButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(getCurrentTimeButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(63, 123, -1, -1));

        julianDateInputBean2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                julianDateInputBean2PropertyChange(evt);
            }
        });

        jPanel1.add(julianDateInputBean2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 74, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(254, 9, 240, 160));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Set Time 1"));
        julianDateInputBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                julianDateInputBean1PropertyChange(evt);
            }
        });

        jPanel11.add(julianDateInputBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(54, 74, -1, -1));

        getCurrentTimeButton1.setText("Get Current Time");
        getCurrentTimeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getCurrentTimeButton1ActionPerformed(evt);
            }
        });

        jPanel11.add(getCurrentTimeButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 123, -1, -1));

        dateTimeSpinnerBean1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateTimeSpinnerBean1PropertyChange(evt);
            }
        });

        jPanel11.add(dateTimeSpinnerBean1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 21, 220, 50));

        getContentPane().add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 160));

        jPanel2.setLayout(new java.awt.GridLayout(4, 1, 5, 2));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Interval (Time 2 - Time 1)"));
        ymdIntervalField.setEditable(false);
        ymdIntervalField.setFont(new java.awt.Font("Dialog", 0, 14));
        ymdIntervalField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ymdIntervalField.setMinimumSize(getPreferredSize());
        ymdIntervalField.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel2.add(ymdIntervalField);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Years : Days : Hours : Mins : Secs");
        jPanel2.add(jLabel2);
        jLabel2.getAccessibleContext().setAccessibleName("Y:D:H:M:S");

        dayIntervalField.setFont(new java.awt.Font("Dialog", 0, 14));
        dayIntervalField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dayIntervalField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dayIntervalFieldActionPerformed(evt);
            }
        });

        jPanel2.add(dayIntervalField);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Days");
        jPanel2.add(jLabel1);

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 265, 230, 110));

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Time 2"));
        jPanel6.setLayout(new java.awt.GridLayout(3, 1));

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel31.setText("Date ");
        jPanel6.add(jLabel31);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Time ");
        jPanel6.add(jLabel41);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel51.setText("Julian Date ");
        jPanel6.add(jLabel51);

        jPanel3.add(jPanel6, java.awt.BorderLayout.WEST);

        jPanel7.setLayout(new java.awt.GridLayout(3, 1));

        date2Field.setEditable(false);
        date2Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date2Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel7.add(date2Field);

        time2Field.setEditable(false);
        time2Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        time2Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel7.add(time2Field);

        jdate2Field.setEditable(false);
        jdate2Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jdate2Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel7.add(jdate2Field);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(256, 171, 230, 90));

        jPanel31.setLayout(new java.awt.BorderLayout());

        jPanel31.setBorder(javax.swing.BorderFactory.createTitledBorder("Time 1"));
        jPanel5.setLayout(new java.awt.GridLayout(3, 1, 2, 0));

        date1Field.setEditable(false);
        date1Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        date1Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel5.add(date1Field);

        time1Field.setEditable(false);
        time1Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        time1Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel5.add(time1Field);

        jdate1Field.setEditable(false);
        jdate1Field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jdate1Field.setSelectionColor(new java.awt.Color(255, 255, 51));
        jPanel5.add(jdate1Field);

        jPanel31.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new java.awt.GridLayout(3, 1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Date ");
        jPanel4.add(jLabel3);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Time ");
        jPanel4.add(jLabel4);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Julian Date ");
        jPanel4.add(jLabel5);

        jPanel31.add(jPanel4, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(14, 173, 230, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dateTimeSpinnerBean2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTimeSpinnerBean2PropertyChange
        jdate2 = new T_JulianDate(dateTimeSpinnerBean2.getDateTime());
        updateDisplay();
    }//GEN-LAST:event_dateTimeSpinnerBean2PropertyChange

    private void dateTimeSpinnerBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateTimeSpinnerBean1PropertyChange
        jdate1 = new T_JulianDate(dateTimeSpinnerBean1.getDateTime());
        updateDisplay();
    }//GEN-LAST:event_dateTimeSpinnerBean1PropertyChange

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if(!isApplet){
            driver.ted.toolbox.ToolBoxApp.decrementFrameCount();
        }else{
            driver.ted.toolbox.ToolBoxJPanel.decrementFrameCount();
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void dayIntervalFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dayIntervalFieldActionPerformed
        double days = 0;
        try{
            days = Double.parseDouble(dayIntervalField.getText());
        }catch(NumberFormatException nfe){
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Day Interval must be a double value",
                "Error Setting Day Interval",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }        
        jdate2.setJulianDate(jdate1.getJulianDate() + days);
        updateDisplay();
    }//GEN-LAST:event_dayIntervalFieldActionPerformed

    private void getCurrentTimeButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getCurrentTimeButton2ActionPerformed
        jdate2 = new T_JulianDate();
        updateDisplay();
    }//GEN-LAST:event_getCurrentTimeButton2ActionPerformed

    private void getCurrentTimeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getCurrentTimeButton1ActionPerformed
        jdate1 = new T_JulianDate();
        
//        System.out.println(dateInputBean1.getMonth());
//        System.out.println(dateInputBean1.getDay());
//        System.out.println(dateInputBean1.getYear());
//        System.out.println(timeInputBean1.getHour());
//        System.out.println(timeInputBean1.getMinute());
//        System.out.println(timeInputBean1.getSecond());
        updateDisplay();
    }//GEN-LAST:event_getCurrentTimeButton1ActionPerformed

    private void julianDateInputBean2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_julianDateInputBean2PropertyChange
        jdate2.setJulianDate(julianDateInputBean2.getJD());
        updateDisplay();
    }//GEN-LAST:event_julianDateInputBean2PropertyChange

    private void julianDateInputBean1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_julianDateInputBean1PropertyChange
        jdate1.setJulianDate(julianDateInputBean1.getJD());
        updateDisplay();
    }//GEN-LAST:event_julianDateInputBean1PropertyChange
        
    private void updateDisplay(){
        int year, day;
        int hour, minute, second;
        
        // update the time displays first
        date1Field.setText(jdate1.getDate().toString());
        time1Field.setText(jdate1.getTime().toString());
        jdate1Field.setText(new java.text.DecimalFormat("0.000000").format(jdate1.getJulianDate()));
        date2Field.setText(jdate2.getDate().toString());
        time2Field.setText(jdate2.getTime().toString());
        jdate2Field.setText(new java.text.DecimalFormat("0.000000").format(jdate2.getJulianDate()));
                
        // get the year difference
        double diff = jdate2.getJulianDate() - jdate1.getJulianDate();        
        
        year = jdate2.getDate().getYear() - jdate1.getDate().getYear();

        // get the day difference 
        day = T_Utilities.getIntOfDouble(jdate2.getDayOfYear() - jdate1.getDayOfYear());

        // get the Hour difference
        hour = jdate2.getTime().getHour() - jdate1.getTime().getHour();

        // get the Minute difference
        minute = jdate2.getTime().getMinute() - jdate1.getTime().getMinute();

        // get the Second difference
        second = jdate2.getTime().getSecond() - jdate1.getTime().getSecond();
        
        // Updating to not have negative values in the fields 3/3/2006
        if(second < 0){
            second = 60 + second;
            minute -= 1;
        }
        if (minute < 0){
            minute = 60 + minute;
            hour -= 1;
        }
        if (hour < 0){
            hour = 24 + hour;
            day -= 1;
        }
        if (day < 0){
            day = 365 + day;
            year -= 1;
        }

        ymdIntervalField.setText(year + ":" + day + ":" + hour + ":" + minute + ":" + second);            

        java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
        dayIntervalField.setText(df.format(diff));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField date1Field;
    private javax.swing.JTextField date2Field;
    private driver.ted.beans.TimeDate.dateTimeSpinnerBean dateTimeSpinnerBean1;
    private driver.ted.beans.TimeDate.dateTimeSpinnerBean dateTimeSpinnerBean2;
    private javax.swing.JTextField dayIntervalField;
    private javax.swing.JButton getCurrentTimeButton1;
    private javax.swing.JButton getCurrentTimeButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField jdate1Field;
    private javax.swing.JTextField jdate2Field;
    private driver.ted.beans.TimeDate.JulianDateInputBean julianDateInputBean1;
    private driver.ted.beans.TimeDate.JulianDateInputBean julianDateInputBean2;
    private javax.swing.JTextField time1Field;
    private javax.swing.JTextField time2Field;
    private javax.swing.JTextField ymdIntervalField;
    // End of variables declaration//GEN-END:variables
    
}
