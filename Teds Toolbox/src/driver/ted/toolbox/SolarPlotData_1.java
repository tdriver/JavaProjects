/*
 * PlotData.java
 *
 * Created on March 1, 2003, 8:33 AM
 */

package driver.ted.toolbox;

import driver.ted.utilities.T_JulianDate;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
/**
 *
 * @author  tdriver
 */
public class SolarPlotData_1 extends javax.swing.JFrame  {
    
    double angles[][];
    double times[][], yData[];
    String timeLabels[][];

    boolean whatToPlot[];
    
    private XYItemRenderer angleRenderer;
    private XYItemRenderer timeRenderer;
    private XYDataset angleDataset;
    private XYDataset timeDataset;
    private JFreeChart angleChart;
    private JFreeChart timeChart;
    
//    public static enum angleTypes {
//        RISEAZ(true,0),
//        TRANSITEL(true,1),
//        SETAZ(true,2);
//        
//        private boolean included; //included in plot
//        private int seriesNumber; // what series is this
//        
//        angleTypes(boolean toInclude, int series){
//            this.included = toInclude;
//            seriesNumber = series;
//        }
//        
//        public void renumberSeries(){
//            int count = 0;
//            for( angleTypes t : angleTypes.values()){
//                if(t.included()){
//                    t.setSeries(count++);
//                }                    
//            }
//        }
//        
//        public void setInclude(boolean toInclude){
//            this.included = toInclude;
//        }
//        public boolean included(){
//            return included;
//        }
//        
//        public void setSeries(int series){
//            seriesNumber = series;
//        }
//        
//        public int series(){
//            return seriesNumber;
//        }
//    }
//    
//    public static enum timeTypes {
//        RISETIME(true,0),
//        TRANSITTIME(true,1),
//        SETTIME(true,2),
//        AMOUNTOFDAYLIGHT(true,3);
//        
//        private boolean included; //included in plot
//        private int seriesNumber; // what series is this
//        
//        timeTypes(boolean toInclude, int series){
//            this.included = toInclude;
//            this.seriesNumber = series;
//        }
//        
//        public static void renumberSeries(){
//            int count = 0;
//            for( timeTypes t : timeTypes.values()){
//                if(t.included()){
//                    t.setSeries(count++);
//                }                    
//            }
//        }
//        
//        public void setInclude(boolean toInclude){
//            this.included = toInclude;
//        }
//        
//        public boolean included(){
//            return included;
//        }
//        
//        public void setSeries(int series){
//            seriesNumber = series;
//        }
//        
//        public int series(){
//            return seriesNumber;
//        }
//    }
    
    /** Creates new form PlotData */
    public SolarPlotData_1(double angleData[][], double timeData[][], String timeLabelData[][], boolean availableData[]) {
        initComponents();
        angles = angleData;
        times = timeData;
        timeLabels = timeLabelData;
//        whatToPlot = availableData;
        
//        //System.out.println("\nplotData[0].length = " + plotData[0].length);
//        if (whatToPlot.length != 7){
//            exitForm(null);
//        }
//        if(whatToPlot[0]){
//            checkRiseTime.setEnabled(true);
//            checkRiseTime.setSelected(true);
//        }
//        if(whatToPlot[1]){
//            checkTransitTime.setEnabled(true);
//            checkTransitTime.setSelected(true);
//        }
//        if(whatToPlot[2]){
//            checkSetTime.setEnabled(true);
//            checkSetTime.setSelected(true);
//        }
//        if(whatToPlot[3]){
//           checkRiseAz.setEnabled(true); 
//           checkRiseAz.setSelected(true); 
//        }
//        if(whatToPlot[4]){
//           checkTransitEl.setEnabled(true); 
//           checkTransitEl.setSelected(true); 
//        }
//        if(whatToPlot[5]){
//            checkSetAz.setEnabled(true);
//            checkSetAz.setSelected(true);
//        }
//        if(whatToPlot[6]){
//            checkAmountOfDaylight.setEnabled(true);
//            checkAmountOfDaylight.setSelected(true);
//        }
        run();
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
        plotPanel = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("Select Output to Plot");
        checkRiseAz.setFont(new java.awt.Font("Dialog", 0, 10));
        checkRiseAz.setSelected(true);
        checkRiseAz.setText("Rise Azimuth");
        checkRiseAz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRiseAzActionPerformed(evt);
            }
        });

        jToolBar1.add(checkRiseAz);

        checkTransitEl.setFont(new java.awt.Font("Dialog", 0, 10));
        checkTransitEl.setSelected(true);
        checkTransitEl.setText("Transit Elevation");
        checkTransitEl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTransitElActionPerformed(evt);
            }
        });

        jToolBar1.add(checkTransitEl);

        checkSetAz.setFont(new java.awt.Font("Dialog", 0, 10));
        checkSetAz.setSelected(true);
        checkSetAz.setText("Set Azimuth");
        checkSetAz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSetAzActionPerformed(evt);
            }
        });

        jToolBar1.add(checkSetAz);

        checkRiseTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkRiseTime.setSelected(true);
        checkRiseTime.setText("Rise Time");
        checkRiseTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkRiseTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkRiseTime);

        checkTransitTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkTransitTime.setSelected(true);
        checkTransitTime.setText("Transit Time");
        checkTransitTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkTransitTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkTransitTime);

        checkSetTime.setFont(new java.awt.Font("Dialog", 0, 10));
        checkSetTime.setSelected(true);
        checkSetTime.setText("Set Time");
        checkSetTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSetTimeActionPerformed(evt);
            }
        });

        jToolBar1.add(checkSetTime);

        checkAmountOfDaylight.setFont(new java.awt.Font("Dialog", 0, 10));
        checkAmountOfDaylight.setSelected(true);
        checkAmountOfDaylight.setText("Amount of Daylight");
        checkAmountOfDaylight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAmountOfDaylightActionPerformed(evt);
            }
        });

        jToolBar1.add(checkAmountOfDaylight);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.NORTH);

        plotPanel.setLayout(new java.awt.GridLayout(2, 1));

        getContentPane().add(plotPanel, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-691)/2, (screenSize.height-605)/2, 691, 605);
    }// </editor-fold>//GEN-END:initComponents

    private void checkAmountOfDaylightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAmountOfDaylightActionPerformed
        int series = 3;
        boolean visible = this.timeRenderer.getItemVisible(series, 0);
        timeRenderer.setSeriesVisible(series, new Boolean(!visible));
//        boolean included = timeTypes.AMOUNTOFDAYLIGHT.included();
//        timeTypes.AMOUNTOFDAYLIGHT.setInclude(!included);
//        timeTypes.renumberSeries();
//        run();
    }//GEN-LAST:event_checkAmountOfDaylightActionPerformed

    private void checkSetAzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSetAzActionPerformed
       int series = 2;
        boolean visible = this.angleRenderer.getItemVisible(series, 0);
        angleRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkSetAzActionPerformed

    private void checkTransitElActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTransitElActionPerformed
        int series = 1;
        boolean visible = this.angleRenderer.getItemVisible(series, 0);
        angleRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkTransitElActionPerformed

    private void checkRiseAzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRiseAzActionPerformed
        int series = 0;
        boolean visible = this.angleRenderer.getItemVisible(series, 0);
        angleRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkRiseAzActionPerformed

    private void checkSetTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSetTimeActionPerformed
        int series = 2;
        boolean visible = this.timeRenderer.getItemVisible(series, 0);
        timeRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkSetTimeActionPerformed

    private void checkTransitTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkTransitTimeActionPerformed
        int series = 1;
        boolean visible = this.timeRenderer.getItemVisible(series, 0);
        timeRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkTransitTimeActionPerformed

    private void checkRiseTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkRiseTimeActionPerformed
        int series = 0;
        boolean visible = this.timeRenderer.getItemVisible(series, 0);
        timeRenderer.setSeriesVisible(series, new Boolean(!visible));
    }//GEN-LAST:event_checkRiseTimeActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
       
    }//GEN-LAST:event_exitForm
    
private JFreeChart createAngleChart(XYDataset dataset) {
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
        "Solar Angle Data", // title
        "Date", // x-axis label
        "Degrees", // y-axis label
        dataset, // data
        true, // create legend?
        true, // generate tooltips?
        false // generate URLs?
    );
    chart.setBackgroundPaint(Color.white);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);
    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);
    this.angleRenderer = plot.getRenderer(0);
    if (this.angleRenderer instanceof XYLineAndShapeRenderer) {
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) this.angleRenderer;
        renderer.setBaseShapesFilled(true);
        renderer.setBaseShapesVisible(true);
    }
    return chart;
}

private JFreeChart createTimeChart(XYDataset dataset) {
    JFreeChart chart = ChartFactory.createTimeSeriesChart(
        "Solar Time Data", // title
        "Date", // x-axis label
        "Time (hours)", // y-axis label
        dataset, // data
        true, // create legend?
        true, // generate tooltips?
        false // generate URLs?
    );
    chart.setBackgroundPaint(Color.white);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setDomainGridlinePaint(Color.white);
    plot.setRangeGridlinePaint(Color.white);
    plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
    plot.setDomainCrosshairVisible(true);
    plot.setRangeCrosshairVisible(true);
    this.timeRenderer = plot.getRenderer(0);
    if (this.timeRenderer instanceof XYLineAndShapeRenderer) {
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) this.timeRenderer;
        renderer.setBaseShapesFilled(true);
        renderer.setBaseShapesVisible(true);
    }
    return chart;
}
/**
* Creates a dataset, consisting of two series of monthly data.
*
* @return the dataset.
*/
private XYDataset createAngleDataset(double[][] angles){  
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        T_JulianDate temp;
        int month, day, year;
        
//        if(angleTypes.RISEAZ.included()){    
            TimeSeries s1 = new TimeSeries("Rise Azimuth", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(angles[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s1.add(new Day(day,month, year), angles[index][1]);
            }
            dataset.addSeries(s1);
//        }
   
//        if(angleTypes.TRANSITEL.included()){
            TimeSeries s2 = new TimeSeries("Transit Elevation", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(angles[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s2.add(new Day(day,month, year), angles[index][2]);
            }
            dataset.addSeries(s2);
//        }
//        if(angleTypes.SETAZ.included()){
            TimeSeries s3 = new TimeSeries("Set Azimuth", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(angles[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s3.add(new Day(day,month, year), angles[index][3]);
            }
            dataset.addSeries(s3);
//        }
    
    return dataset;
}
  

private XYDataset createTimeDataset(double[][] times) {
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    // Add all the available datasets to the dataset here
        int month, day, year;
        T_JulianDate temp;
        
//        if(timeTypes.RISETIME.included()){
            TimeSeries s4 = new TimeSeries("Rise Time", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(times[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s4.add(new Day(day,month, year), times[index][1]);
            }
            dataset.addSeries(s4); 
 //       }   
//        if(timeTypes.TRANSITTIME.included()){
            TimeSeries s5 = new TimeSeries("Transit Time", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(times[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s5.add(new Day(day,month, year), times[index][2]);
            }
            dataset.addSeries(s5);
//        }
//        if(timeTypes.SETTIME.included()){
            TimeSeries s6 = new TimeSeries("Set Time", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(times[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s6.add(new Day(day,month, year), times[index][3]);
            }
            dataset.addSeries(s6);
//        }
//        if(timeTypes.AMOUNTOFDAYLIGHT.included()){
            TimeSeries s7 = new TimeSeries("Amount Of Daylight", Day.class);
            for(int index=0; index < angles.length; index++){
                temp = new T_JulianDate(times[index][0]);
                month = temp.getDate().getMonth();
                day = temp.getDate().getDay();
                year = temp.getDate().getYear();
                //System.out.println(index + ": "+ month + "/" +day+"/"+year );
                s7.add(new Day(day,month, year), times[index][4]);
            }
            dataset.addSeries(s7);
//        }
    
    return dataset;
}
    
    
    public void run() {
        angleDataset = createAngleDataset(angles);
        angleChart = createAngleChart(angleDataset);
        ChartPanel angleChartPanel = new ChartPanel(angleChart);
        //angleChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        angleChartPanel.setMouseZoomable(true, false);
        plotPanel.add(angleChartPanel);
        
        timeDataset = createTimeDataset(times);
        JFreeChart timeChart = createTimeChart(timeDataset);
        ChartPanel timeChartPanel = new ChartPanel(timeChart);
        //timeChartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        timeChartPanel.setMouseZoomable(true, false);
        plotPanel.add(timeChartPanel);
 }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox checkAmountOfDaylight;
    private javax.swing.JCheckBox checkRiseAz;
    private javax.swing.JCheckBox checkRiseTime;
    private javax.swing.JCheckBox checkSetAz;
    private javax.swing.JCheckBox checkSetTime;
    private javax.swing.JCheckBox checkTransitEl;
    private javax.swing.JCheckBox checkTransitTime;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel plotPanel;
    // End of variables declaration//GEN-END:variables
    
}
