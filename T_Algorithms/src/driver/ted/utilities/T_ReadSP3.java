/**
 * T_ReadSP3.java
 *
 * Created on June 2, 2005, 12:39 PM
 *
 * Title:        T_ReadSP3 class
 * Description:  This class read precise ephemeris files from NGA.
 * You must use JRE 1.5 or later, as this implementation uses a StringBuilder object.
 * Copyright:    Copyright (c) 2005 - 2006
 * @author       Ted Driver
 * @version 1.0
 *
 * Change from version 1.0:
 * 
 */

package driver.ted.utilities;
import java.io.*;
import java.util.StringTokenizer;
import java.util.*;
import java.text.DecimalFormat;

/**
 * This utility reads a SP3 formatted ephemeris file - typically from the NGA.
 * @author Ted Driver
 */
public class T_ReadSP3 {
    
    File file = null;
    double[][] timeData, satData;
    Map sats;
    Map times;
        
    StringBuilder line = new StringBuilder();
    T_JulianDate JD = new T_JulianDate();
    int numEpochs;
    int numberOfSats;
    double epochLength;
    
    BufferedReader in = null;
    
    boolean Inited = false;
    
   // definitions for retrieving data from data array and string comparisons
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
    private static final int XDOT = 3;
    private static final int YDOT = 4;
    private static final int ZDOT = 5;
    private static final int CLOCKCORR = 6;
    private static final int CLOCKCORRDOT = 7;
    private static final int SVID = 8;
    private static final int JDATE = 8;
    private static final String NEWEPOCH = "*";
    private static final String POSITIONRECORD = "P";
    private static final String VELOCITYRECORD = "V";
    private static final String EOF = "EOF";
    private static final int NUMBEROFDATAELEMENTS = 9;
    
    /**
     * Creates a new instance of T_ReadSP3
     * No File is initialized
     */
    public T_ReadSP3() {
    }
    
    /**
     * Creates a new instance of T_ReadSP3 with a SP3 file initialized using the filename.
     * @param filename The name of the SP3 file to open
     */
    public T_ReadSP3(String filename) {
        // try to open the file here and read in all the data
        initFile(filename);        
    }
    
    /**
     * Use to set the filename if none was initialized by the constructor.
     * @param filename The path to the file to open.
     */
    public void setFile(String filename){
        
        initFile(filename);
    }
    
    /**
     * This function returns an array of times as defined by the NGA ephemeris file.
     * @return a double array of times in Julian Date format
     */
    public double[] getTimes(){
        double[] returning = new double[numEpochs];
        if(Inited){
            int q = 0;
            for (Iterator i=times.keySet().iterator(); i.hasNext();){
               returning[q] = ((Double)i.next()).doubleValue();
               q++;
             }
           return returning;    
        }else{
           System.out.println("No File loaded");
           return returning; 
        }
    }
    
    /**
     * This function returns an array of SVIDs as defined by the SP3 ephemeris file.
     * @return an int array of SVIDs
     */
    public int[] getSVIDs(){
        int[] returning = new int[numberOfSats];
        if(Inited){           
           int q = 0;
           for (Iterator i=sats.keySet().iterator(); i.hasNext();){
               returning[q] = ((Integer)i.next()).intValue();
               q++;
           }
           return returning;
        }else{
           System.out.println("No File loaded");
           return returning;
        }
    }
    
    /**
     * This function returns an array of position and velocity and clock parameters for a given PRN for
     * a given time.  If either the PRN or the time do not exist, an full size array of zeros is returned.
     * The array is arranged as x, y, z, xdot, ydot, zdot, clockcorr, clockcorrdot, SVID
     * and the units are m, m, m, m/s, m/s, m/s, microseconds, 0.0001 microseconds/sec, unitless respectively.
     * @param PRN The PRN for which you want to obtain data
     * @param J The Julian Date for which you want to obtain Data
     * @return a double array of position, velocity and clock data for that PRN and Time
     */
    public double[] getData(int PRN, double J){
        int returnID = -1;
        if(Inited){
            if(times.containsKey(J) && sats.containsKey(PRN)){
                double[][] temp = (double[][])times.get(J);
                //Got the time record, now search for the correct PRN within it
                for(int q = 0; q < temp.length; q++){
                    if( (((int)temp[q][SVID]) == PRN) ){
                        returnID = q;
                        break;
                    }                       
                }
                if(returnID != -1){
                    return temp[returnID] ;
                }else{
                    return new double[NUMBEROFDATAELEMENTS]; 
                }
            }else{
                return new double[NUMBEROFDATAELEMENTS]; 
            }            
        }else{            
            return new double[NUMBEROFDATAELEMENTS];
        }     
    }
    /**
     * 
     */
    /**
     * This function returns an 2-D array of position and velocity and clock parameters for a given PRN 
     * If the PRN does not exist, a full size array of zeros is returned.
     * The array is arranged as array[numberOfEpochs][x, y, z, xdot, ydot, zdot, clockcorr, clockcorrdot, time]
     * and the units are m, m, m, m/s, m/s, m/s, microseconds, 0.0001 microseconds/sec, Julian Date respectively
     * @param PRN The PRN of the vehicle you want to get data for
     * @return double array of data for that PRN
     */
    public double[][] getDataForPRN(int PRN){
        if(Inited){
             if (sats.containsKey(PRN)){
               return (double[][]) sats.get(PRN); 
            }else{
                return new double[numberOfSats][NUMBEROFDATAELEMENTS];
            }
        }else{
            System.out.println("No File loaded");
            return new double[numberOfSats][NUMBEROFDATAELEMENTS];
        }  
    }
    
    /**
     * This function returns an 2-D array of position and velocity and clock parameters for a given time 
     * If the time does not exist, a full size array of zeros is returned.
     * The array is arranged as array[numberOfSats][x, y, z, xdot, ydot, zdot, clockcorr, clockcorrdot, time]
     * and the units are m, m, m, m/s, m/s, m/s, microseconds, 0.0001 microseconds/sec, unitless respectively
     * @param J The Julian Date you want to obtain data for
     * @return double array of data for that time
     */
    public double[][] getDataForTime(double J){
        if (Inited){
            if(times.containsKey(J)){
                return (double[][])times.get(J);           
            }else{
                return new double[numberOfSats][NUMBEROFDATAELEMENTS]; 
            }
        }else{
            System.out.println("No File loaded");
            return new double[numberOfSats][NUMBEROFDATAELEMENTS];
        }   
    }
    
    private void initFile(String filename){
        file = new File(filename);
        boolean notDone = true, firstEpoch = true, isPVFile = true;
        int epochCount = 0, satCount = 0;
        int[] SVIDs = new int[34];

        try{
            in = new BufferedReader(new FileReader(file));
            
            for(int i = 1; i <= 22; i++){ // read 22 line of header
              line.replace(0,line.length(), in.readLine());
              if (i == 1){                 
                 // now get header line 1 start time and number of epochs (need number of epochs though)
                  numEpochs = Integer.parseInt(line.substring(32,39).trim());
                  // chaeck Character 3 of line 1 for P or V.  If P, then no velocity data in file
                  if(line.substring(2,3).equals(POSITIONRECORD)){
                      isPVFile = false;
                  }
//                  System.out.println("JD=" + JD);
//                  System.out.println("numEpochs="+numEpochs);
             }else if (i == 2){
                  epochLength = Double.parseDouble(line.substring(24,38).trim());
//                  System.out.println("epochLength="+epochLength);
                 // alternatively get line two Start time in GPS weeks and TOW and epoch length (in seconds) (need epoch length though)
             }else if (i == 3){
                  numberOfSats = Integer.parseInt(line.substring(4,6).trim());
                  
                  SVIDs[0] = Integer.parseInt(line.substring(9,12).trim());
                  SVIDs[1] = Integer.parseInt(line.substring(12,15).trim());
                  SVIDs[2] = Integer.parseInt(line.substring(15,18).trim());
                  SVIDs[3] = Integer.parseInt(line.substring(18,21).trim());
                  SVIDs[4] = Integer.parseInt(line.substring(21,24).trim());
                  SVIDs[5] = Integer.parseInt(line.substring(24,27).trim());
                  SVIDs[6] = Integer.parseInt(line.substring(27,30).trim());
                  SVIDs[7] = Integer.parseInt(line.substring(30,33).trim());
                  SVIDs[8] = Integer.parseInt(line.substring(33,36).trim());
                  SVIDs[9] = Integer.parseInt(line.substring(36,39).trim());
                  SVIDs[10] = Integer.parseInt(line.substring(39,42).trim());
                  SVIDs[11] = Integer.parseInt(line.substring(42,45).trim());
                  SVIDs[12] = Integer.parseInt(line.substring(45,48).trim());
                  SVIDs[13] = Integer.parseInt(line.substring(48,51).trim());
                  SVIDs[14] = Integer.parseInt(line.substring(51,54).trim());
                  SVIDs[15] = Integer.parseInt(line.substring(54,57).trim());
                  SVIDs[16] = Integer.parseInt(line.substring(57,60).trim());
//                  System.out.println("numSats ="+numberOfSats);
             }else if (i == 4){
                  SVIDs[17] = Integer.parseInt(line.substring(9,12).trim());
                  SVIDs[18] = Integer.parseInt(line.substring(12,15).trim());
                  SVIDs[19] = Integer.parseInt(line.substring(15,18).trim());
                  SVIDs[20] = Integer.parseInt(line.substring(18,21).trim());
                  SVIDs[21] = Integer.parseInt(line.substring(21,24).trim());
                  SVIDs[22] = Integer.parseInt(line.substring(24,27).trim());
                  SVIDs[23] = Integer.parseInt(line.substring(27,30).trim());
                  SVIDs[24] = Integer.parseInt(line.substring(30,33).trim());
                  SVIDs[25] = Integer.parseInt(line.substring(33,36).trim());
                  SVIDs[26] = Integer.parseInt(line.substring(36,39).trim());
                  SVIDs[27] = Integer.parseInt(line.substring(39,42).trim());
                  SVIDs[28] = Integer.parseInt(line.substring(42,45).trim());
                  SVIDs[29] = Integer.parseInt(line.substring(45,48).trim());
                  SVIDs[30] = Integer.parseInt(line.substring(48,51).trim());
                  SVIDs[31] = Integer.parseInt(line.substring(51,54).trim());
                  SVIDs[32] = Integer.parseInt(line.substring(54,57).trim());
                  SVIDs[33] = Integer.parseInt(line.substring(57,60).trim());
             }
           }
// We have now read all header lines.             
            // now create the data arrays and maps, we have all the data we need
            sats = new TreeMap();//numberOfSats);
            times = new TreeMap();//numEpochs);
            timeData = new double[numberOfSats][NUMBEROFDATAELEMENTS];
            // let's add all we need to the sats map now
            for (int i = 0; i < numberOfSats; i++){
            // add new empty satData array to sats map for each SVID obtained in header lines 3 and 4
            // then we can extract each array as the time changes comes up and put in the data
                sats.put(SVIDs[i], new double[numEpochs][NUMBEROFDATAELEMENTS]);
            }
            do{
               line.replace(0,line.length(), in.readLine());
               
                //get the first character to check if it is a new epoch, position or velocity or EOF
               // EPOCH record
               if(line.substring(0,1).equals(NEWEPOCH)){
                   if(firstEpoch){
                       JD.setJulianDate(Integer.parseInt(line.substring(8,10).trim()), // month
                                   Integer.parseInt(line.substring(11,13).trim()), // day
                                   Integer.parseInt(line.substring(3,7).trim()), // year
                                   Integer.parseInt(line.substring(14,16).trim()), // hour
                                   Integer.parseInt(line.substring(17,19).trim()), //minute
                                   0);//Integer.parseInt(line.substring(20,31))); // sec
                       timeData = new double[numberOfSats][NUMBEROFDATAELEMENTS];
                       firstEpoch = false;
                   }else{ // not first epoch
                        // add a new item to the time map based on data collected in the last epoch
                       times.put(convertTimeKey(JD.getJulianDate()), timeData);
                       timeData = new double[numberOfSats][NUMBEROFDATAELEMENTS];
                       
                       satCount = 0;
                       epochCount++;
                       JD.setJulianDate(Integer.parseInt(line.substring(8,10).trim()), // month
                                       Integer.parseInt(line.substring(11,13).trim()), // day
                                       Integer.parseInt(line.substring(3,7).trim()), // year
                                       Integer.parseInt(line.substring(14,16).trim()), // hour
                                       Integer.parseInt(line.substring(17,19).trim()), //minute
                                       0);//Integer.parseInt(line.substring(20,31))); // sec
                       //System.out.println("JD=" + JD);
                   }
                // Position Record
               }else if(line.substring(0,1).equals(POSITIONRECORD)){
                   // PositionRecord, multiply by 1000 to get from kilometers to meters
                   double x = Double.parseDouble(line.substring(4,18).trim())*1000.0;
                   double y = Double.parseDouble(line.substring(18,32).trim())*1000.0;
                   double z = Double.parseDouble(line.substring(32,46).trim())*1000.0;
                   double c = Double.parseDouble(line.substring(46,60).trim());
                   int id = Integer.parseInt(line.substring(1,4).trim());
                   // collect the timedata in the array
                   timeData[satCount][SVID] = id;
                   timeData[satCount][X] = x;
                   timeData[satCount][Y] = y;
                   timeData[satCount][Z] = z;
                   timeData[satCount][CLOCKCORR] = c;
                   // collect the sat data in the correct array
                   if(sats.containsKey(id)){
                       double[][] satData = (double[][])sats.get(id);
                       satData[epochCount][JDATE] = JD.getJulianDate();
                       satData[epochCount][X] = x;
                       satData[epochCount][Y] = y;
                       satData[epochCount][Z] = z;
                       satData[epochCount][CLOCKCORR] = c;
                       sats.put(id, satData);
                   }else{
                       System.out.println("WHOA! File contains PRN not listed in Header!!!");
                   }
                   if(!isPVFile){
                       satCount++;
                   }
               // Velocity Record
               }else if (line.substring(0,1).equals(VELOCITYRECORD)){
                   // velocity record, divide by 10 to get from decimeters/sec to meters/sec
                   double xd = Double.parseDouble(line.substring(4,18).trim())/10.0;
                   double yd = Double.parseDouble(line.substring(18,32).trim())/10.0;
                   double zd = Double.parseDouble(line.substring(32,46).trim())/10.0;
                   double cd = Double.parseDouble(line.substring(46,60).trim());
                   int id = Integer.parseInt(line.substring(1,4).trim());
                   // collect time data
                   timeData[satCount][XDOT] = xd;
                   timeData[satCount][YDOT] = yd; 
                   timeData[satCount][ZDOT] = zd;
                   timeData[satCount][CLOCKCORRDOT] = cd; 
                   if(sats.containsKey(id)){
                       double[][] satData = (double[][])sats.get(id);
                       satData[epochCount][XDOT] = xd;
                       satData[epochCount][YDOT] = yd;
                       satData[epochCount][ZDOT] = zd;
                       satData[epochCount][CLOCKCORRDOT] = cd;
                       sats.put(id, satData);
                   }else{
                       System.out.println("WHOA! File contains PRN not listed in Header!!!");
                   }
                   // update the sat counter, since we'return done with this SVs record
                   satCount++;
               // EOF
               }else if (line.substring(0, 3).equals(EOF)){
                    // we're done, but don't forget to collect the last data point!
                    times.put(convertTimeKey(JD.getJulianDate()), timeData);
                    notDone = false;
                    in.close();
                    
                    // let the code know we're good to return results
                    Inited = true;                    
               }else{
                   // bad record type!
                   System.out.println("Bad line = " + line);
               }
            }while(notDone);
            
        }catch(IOException ex) { 
            ex.printStackTrace();
        }
    }
    
    private static double convertTimeKey(double time){
        // display only 15 decimal digits regardless
    DecimalFormat nf = new DecimalFormat("0.000000");

    // convert input double to a string for manipulation
    String dstr = new Double(time).toString();
    //nf.setMaximumFractionDigits(6);

    // get the fraction value and format it
    String answerstr = nf.format( Double.valueOf(dstr).doubleValue());

    // return fraction after converting it from a string to a double
    return (Double.valueOf(answerstr)).doubleValue();
    }
    
/**
 * Test program
 * @param args Arguments
 */
public static void main(String[] args)
 {
    T_ReadSP3 sp3 = new T_ReadSP3("C:\\Documents and Settings\\tdriver\\My Documents\\dev\\cpp\\NIMA\\EPHEMERI\\Debug\\NIM12514.EPH");
    T_JulianDate t = new T_JulianDate(1,1,2004,4,15,0);
    int prn = 13;
    
    
    // test the keyset returner
//    double[] times = sp3.getTimes();
//    for (int i = 0; i <times.length; i++ ){
//        System.out.println(""+times[i]);
//    }
//    
    // test getting the SVIDs
//    int[] SVs = sp3.getSVIDs();
//    for (int i = 0; i <SVs.length; i++ ){
//        System.out.println(""+SVs[i]);
//    }
      // test getting from specific time
//    double[][] timedata = sp3.getDataForTime(convertTimeKey(t.getJulianDate()));//2453005.5);
//    for (int i = 0; i < timedata.length; i++){
//        for (int q = 0; q < 9; q++){
//            System.out.println("" + timedata[i][q] );
//        }
//    }
   // test getting from specific PRN
//    double[][] PRNdata = sp3.getDataForPRN(prn);
//    //System.out.println("Length PRNData = " + PRNdata.length);
//    for (int i = 0; i < PRNdata.length; i++){
//        for (int q = 0; q < 9; q++){
//            System.out.println("" + PRNdata[i][q] );
//        }
//    }
    
    // test getting specific time and PRN
    double[] test = sp3.getData(prn, convertTimeKey(t.getJulianDate()));
    for (int i = 0; i < test.length; i++){
       System.out.println("" + test[i] );
    }
}
}





