/*
 * PlotData.java
 *
 * Created on March 1, 2003, 8:33 AM
 */

package driver.ted.toolbox;

import driver.ted.utilities.T_JulianDate;
/**
 *
 * @author  tdriver
 */
public class SolarPlotData extends javax.swing.JFrame implements Runnable {
    
    double angles[][];
    double times[][], yData[];
    String timeLabels[][], yLabels[];
    boolean whatToPlot[];
    
    /** Creates new form PlotData */
    public SolarPlotData(double angleData[][], double timeData[][], String timeLabelData[][], boolean availableData[]) {
        initComponents();
        angles = angleData;
        times = timeData;
        timeLabels = timeLabelData;
        whatToPlot = availableData;
        
        //System.out.println("\nplotData[0].length = " + plotData[0].length);
        if (whatToPlot.length != 7){
            exitForm(null);
        }
        if(whatToPlot[0]){
            checkRiseTime.setEnabled(true);
        }
        if(whatToPlot[1]){
            checkTransitTime.setEnabled(true);
        }
        if(whatToPlot[2]){
            checkSetTime.setEnabled(true);
        }
        if(whatToPlot[3]){
           checkRiseAz.setEnabled(true); 
        }
        if(whatToPlot[4]){
           checkTransitEl.setEnabled(true); 
        }
        if(whatToPlot[5]){
            checkSetAz.setEnabled(true);
        }
        if(whatToPlot[6]){
            checkAmountOfDaylight.setEnabled(true);
        }
        
        // calculate the time y values
        // go from 04:30 to 21:00
        //T_JulianDate JD = new T_JulianDate(1,1,2001,21,0,0);
        //T_JulianDate JD = new T_JulianDate(1,1,2001,4,30,0);
       // T_JulianDate iterator = startJD;
        
        yData = new double[66];
        yLabels = new String[66];
        
        //boolean finished = false;
        double increment = 0.25, start = 4.0;
        for(int i = 0; i < 66; i++){
            yData[i] = start;
            yLabels[i] = driver.ted.utilities.T_Utilities.decimalToTimeHMS(start, 0);
            start += increment;
        }
//        do{
//            yData[
//            if(iterator.getJulianDate() >= stopJD.getJulianDate()){
//               finished = true;
//            }
//            iterator = new T_JulianDate(iterator.getJulianDate() + 900.0/86400.0);
//         }while(!finished);
        
    }
    

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jToolBar1 = new javax.swing.JToolBar();
        checkRiseAz = new javax.swing.JCheckBox();
        checkTransitEl = new javax.swing.JCheckBox();
        checkSetAz = new javax.swing.JCheckBox();
        checkRiseTime = new javax.swing.JCheckBox();
        checkTransitTime = new javax.swing.JCheckBox();
        checkSetTime = new javax.swing.JCheckBox();
        checkAmountOfDaylight = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        plot1 = new ptolemy.plot.Plot();
        plot2 = new ptolemy.plot.Plot();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("Select Output to Plot");
        checkRiseAz.setFont(new java.awt.Font("Dialog", 0, 10));
        checkRiseAz.setText("Rise Azimuth");
        checkRiseAz.setEnabled(false);
        checkRiseAz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRiseAzActionPerformed(evt);
            }
        });

        jToolBar1.add(checkRiseAz);

        checkTransitEl.setFont(new java.awt.Font("Dialog", 0, 10));
        checkTransitEl.setText("Transit Elevation");
        checkTransitEl.setEnabled(false);
        checkTransitEl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTransitElActionPerformed(evt);
            }
        });

        jToolBar1.add(checkTransitEl);

        checkSetAz.setFont(new java.awt.Font("Dialog", 0, 10));
        checkSetAz.setText("Set Azimuth");
        checkSetAz.setEnabled(false);
        checkSetAz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSetAzActionPerformed(evt);
            }
        });

        jToolBar1.add(checkSetAz);

        checkRiseTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkRiseTime.setText("Rise Time");
        checkRiseTime.setEnabled(false);
        checkRiseTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRiseTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkRiseTime);

        checkTransitTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkTransitTime.setText("Transit Time");
        checkTransitTime.setEnabled(false);
        checkTransitTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTransitTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkTransitTime);

        checkSetTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkSetTime.setText("Set Time");
        checkSetTime.setEnabled(false);
        checkSetTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSetTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkSetTime);

        checkAmountOfDaylight.setFont(new java.awt.Font("Dialog", 0, 10));
        checkAmountOfDaylight.setText("Amount of Daylight");
        checkAmountOfDaylight.setEnabled(false);
        checkAmountOfDaylight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAmountOfDaylightActionPerformed(evt);
            }
        });

        jToolBar1.add(checkAmountOfDaylight);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        plot1.setButtons(true);
        plot1.setMinimumSize(new java.awt.Dimension(5, 5));
        plot1.setTitle("Solar Data");
        jPanel1.add(plot1);

        plot2.setButtons(true);
        jPanel1.add(plot2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-669)/2, (screenSize.height-489)/2, 669, 489);
    }// </editor-fold>//GEN-END:initComponents

    private void checkAmountOfDaylightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAmountOfDaylightActionPerformed
        run();
    }//GEN-LAST:event_checkAmountOfDaylightActionPerformed

    private void checkSetAzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSetAzActionPerformed
       run();
    }//GEN-LAST:event_checkSetAzActionPerformed

    private void checkTransitElActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTransitElActionPerformed
        run();
    }//GEN-LAST:event_checkTransitElActionPerformed

    private void checkRiseAzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRiseAzActionPerformed
        run();
    }//GEN-LAST:event_checkRiseAzActionPerformed

    private void checkSetTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSetTimeActionPerformed
        run();
    }//GEN-LAST:event_checkSetTimeActionPerformed

    private void checkTransitTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTransitTimeActionPerformed
        run();
    }//GEN-LAST:event_checkTransitTimeActionPerformed

    private void checkRiseTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRiseTimeActionPerformed
        run();
    }//GEN-LAST:event_checkRiseTimeActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //System.exit(0);
    }//GEN-LAST:event_exitForm
    
    
    
    /** When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     *
     */
    public void run() {
        //T_JulianDate jd;
        plot1.clear(true);
        plot1.setMarksStyle("various");
        plot1.setConnected(false);
                        
        // plot1 angles
           if(checkRiseAz.isSelected()){
                for (int i = 0; i < angles.length; i++){
                    plot1.addPoint(0, angles[i][0], angles[i][1], true);
                    plot1.addXTick(timeLabels[i][0], angles[i][0]);
                }
                plot1.addLegend(0, "Rise Azimuth");
            }
            if(checkTransitEl.isSelected()){
                for (int i = 0; i < angles.length; i++){
                    plot1.addPoint(1, angles[i][0], angles[i][2], true);
                    plot1.addXTick(timeLabels[i][0], angles[i][0]);
                }
                plot1.addLegend(1, "Transit Elevation");
            }
            if(checkSetAz.isSelected()){
                for (int i = 0; i < angles.length; i++){
                    plot1.addPoint(2, angles[i][0], angles[i][3], true);
                    plot1.addXTick(timeLabels[i][0], angles[i][0]);
                }
                plot1.addLegend(2, "Set Azimuth");
            }
            plot1.setXLabel("Date");
            plot1.setYLabel("Degrees");
            plot1.setButtons(true);
            plot1.repaint();
        
          // plot2 Times
            plot2.clear(true);
            plot2.setMarksStyle("various");
            plot2.setConnected(false);
        
           if(checkRiseTime.isSelected()){
                for (int i = 0; i < angles.length; i++){
                    plot2.addPoint(0, times[i][0], times[i][1], true);
                    plot2.addXTick(timeLabels[i][0], angles[i][0]);
                }
                addTimeYTicks();
                plot2.addLegend(0, "Rise Time");
            }
            if(checkTransitTime.isSelected()){
                for (int i = 0; i < angles.length; i++){
                    plot2.addPoint(1, times[i][0], times[i][2], true);
                    plot2.addXTick(timeLabels[i][0], angles[i][0]);
                }
                addTimeYTicks();
                plot2.addLegend(1, "Transit Time");
            }
            if(checkSetTime.isSelected()){
                for (int i = 0; i < times.length; i++){
                    plot2.addPoint(2, times[i][0], times[i][3], true);
                    plot2.addXTick(timeLabels[i][0], angles[i][0]);
                }
                addTimeYTicks();
                plot2.addLegend(2, "Set Time");
            }
            if(checkAmountOfDaylight.isSelected()){
                //System.out.println("Times.length = " + times.length);
                for (int i = 0; i < times.length; i++){
                    plot2.addPoint(3, times[i][0], times[i][4], true);
                    plot2.addXTick(timeLabels[i][0], angles[i][0]);  
                }
                addTimeYTicks();
                plot2.addLegend(3, "Daylight");
            }
            
            plot2.setYLabel("Time");
            plot2.setXLabel("Date");
            plot2.setButtons(true);
            plot2.repaint();
    }    
    
    private void addTimeYTicks(){
        for(int i = 0; i < 66; i++){
            plot2.addYTick(yLabels[i], yData[i]);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkAmountOfDaylight;
    private javax.swing.JCheckBox checkRiseAz;
    private javax.swing.JCheckBox checkRiseTime;
    private javax.swing.JCheckBox checkSetAz;
    private javax.swing.JCheckBox checkSetTime;
    private javax.swing.JCheckBox checkTransitEl;
    private javax.swing.JCheckBox checkTransitTime;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private ptolemy.plot.Plot plot1;
    private ptolemy.plot.Plot plot2;
    // End of variables declaration//GEN-END:variables
    
}
