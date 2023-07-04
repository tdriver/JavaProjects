/*
 * T_ReadAlmanac.java
 *
 * Created on September 27, 2002, 11:00 AM
 */

package driver.ted.utilities.Almanac;

/**
 * Title:        T_ReadAlmanac class
 * Description:  This class Reads in a SEM formatted almanac file and provides methods to get the data
 * Copyright:    (c) 2002 - 2006
 * @author       Ted Driver
 * @version 1.0
 *
 * Changes:
 */

import java.io.*;
import java.util.StringTokenizer;

public class T_ReadAlmanac extends Object implements java.io.Serializable{
    
    private static final int SVN = 0;
    private static final int AVERAGEURA = 1;
    private static final int ECCENTRICITY = 2;
    private static final int INCOFFSET = 3;
    private static final int OMEGADOT = 4;
    private static final int SQRTSMA = 5;
    private static final int OMEGA0 = 6;
    private static final int OMEGA = 7;
    private static final int MEANANOMALY = 8;
    private static final int AF0 = 9;
    private static final int AF1 = 10;
    private static final int HEALTH = 11;
    private static final int SATCONFIG = 12;
    
    
    File file = null;
    String line = null;        
    BufferedReader in = null;
    StringTokenizer st = null;
    // Alamanac contents
    int numberOfPRNs = 0, GPSWeek = 0;
    long  GPSTOA = 0;
    // 13 data items for each of 32 Possible PRNs
    double[][] PRN = new double[33][13];
    boolean almanacReadStatus = false;

    String almanacName = "";
    
    /**
     * Constructor - opens the almanac file pointed to by almanacFileName
     * @param almanacFileName The full path to the almanac file to open
     */
    public T_ReadAlmanac(String almanacFileName) {
         file = new File(almanacFileName);
        try{
            in = new BufferedReader(new FileReader(file));
        }catch(IOException ex) { ex.printStackTrace();}
        
         // initialize PRN data structure
         for(int a=0; a < 33; a++){
             for (int b = 0; b < 13; b++){
                 PRN[a][b] = 0.0;
             }
         }
         
        readHeader();
        readPRNs();
        almanacReadStatus = true;
    }
    
     private void readHeader(){
       // 1st line contains the number of PRNs and the almanac name
        try{
            line = in.readLine();
        }catch(IOException ex) { ex.printStackTrace();}
        st = new StringTokenizer(line);
        numberOfPRNs = Integer.parseInt(st.nextToken());
//     System.out.println("PRNs: " + numberOfPRNs);
  
        almanacName = st.nextToken();
//     System.out.println("almanacName = " + almanacName);
     
        // 2nd line contains the GPS week and TOW
        try{
            line = in.readLine();
        }catch(IOException ex){ ex.printStackTrace(); }
        st = new StringTokenizer(line);
        GPSWeek = Integer.parseInt(st.nextToken());
        GPSTOA = Long.parseLong(st.nextToken());            
//    System.out.println("GPSWeek = " + GPSWeek);
//    System.out.println("GPSTOA = " + GPSTOA);    
    
    }
    
    private void readPRNs(){
        int currentPRN = -1;
        for (int i = 1; i <= numberOfPRNs; i++){
            try{
                
                // read blank line
                in.readLine();
                // read 1st line containing PRN number
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}        
            st = new StringTokenizer(line);
            currentPRN = Integer.parseInt(st.nextToken());
//        System.out.println("PRN = " + currentPRN);
            
            try{
                // read 2nd line containing SVN number
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][SVN] = Double.parseDouble(st.nextToken());
//        System.out.println("SVN = " + PRN[currentPRN][SVN]);
            
            try{
                // read 3rd line containing AVERAGE URA number
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][AVERAGEURA] = Double.parseDouble(st.nextToken());
//        System.out.println("AVERAGEURA = " + PRN[currentPRN][AVERAGEURA]);
            
            try{
                // read 4th line containing 1st row of orbital data
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][ECCENTRICITY] = Double.parseDouble(st.nextToken());
            PRN[currentPRN][INCOFFSET] = Double.parseDouble(st.nextToken())*Math.PI; // return in radians
            PRN[currentPRN][OMEGADOT] = Double.parseDouble(st.nextToken())*Math.PI;
//         System.out.print("ECCENTRICITY = " + PRN[currentPRN][ECCENTRICITY]);
//         System.out.print(" INCOFFSET = " + PRN[currentPRN][INCOFFSET]);
//         System.out.println(" OMEGADOT = " + PRN[currentPRN][OMEGADOT]);
            
            try{
                // read 5th line containing 2nd row of orbital data
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][SQRTSMA] = Double.parseDouble(st.nextToken());
            PRN[currentPRN][OMEGA0] = Double.parseDouble(st.nextToken())*Math.PI;
            PRN[currentPRN][OMEGA] = Double.parseDouble(st.nextToken())*Math.PI;
//         System.out.print("SQRTSMA = " + PRN[currentPRN][SQRTSMA]);
//         System.out.print(" OMEGA0 = " + PRN[currentPRN][OMEGA0]);
//         System.out.println(" OMEGA = " + PRN[currentPRN][OMEGA]);   
         
            
            try{
                // read 6th line containing 3rd row of orbital data
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][MEANANOMALY] = Double.parseDouble(st.nextToken())*Math.PI;
            PRN[currentPRN][AF0] = Double.parseDouble(st.nextToken());
            PRN[currentPRN][AF1] = Double.parseDouble(st.nextToken());
//         System.out.print("MEANANOMALY = " + PRN[currentPRN][MEANANOMALY]);
//         System.out.print(" AF0 = " + PRN[currentPRN][AF0]);
//         System.out.println(" AF1 = " + PRN[currentPRN][AF1]);   
            
            try{
                // read 7th line containing the SVs health
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][HEALTH] = Double.parseDouble(st.nextToken());
        //System.out.println("HEALTH = " + PRN[currentPRN][HEALTH]);
            
            try{
                // read 8th line containing the satellite configuration from SF4 page 25
                line = in.readLine();
            }catch(IOException ex){ ex.printStackTrace();}    
            st = new StringTokenizer(line);
            PRN[currentPRN][SATCONFIG] = Double.parseDouble(st.nextToken());
       // System.out.println("SATCONFIG = " + PRN[currentPRN][SATCONFIG]);
        }   
    }
    
    
   /**
    * Returns a status of the alamanc read operation.  If the almanac 
    * was read successfully, will return true, otherwise false.
    * @return True if almanac read successfully, otherwise false
    */
   public boolean getAlmanacReadStatus(){
       return almanacReadStatus;
    }
    
    /**
     * Returns the number of SVs in the almanac
     * @return Number of SVs in the almanac
     */
    public int getNumberOfSVs(){
        return numberOfPRNs;
    }
    
    /**
     * Returns the name of the almanac as defined in the almanac file
     * @return String The name of the almanac
     */
    public String getAlmanacName(){
        return almanacName;
    }
    
    /**
     * Returns the GPS week of the almanac - usually from the latest epoch
     * @return GPS week since latest epoch
     */
    public int getGPSWeek(){
        return GPSWeek;
    }
    
    /**
     * Returns the Time of week parameter for the almanac. (The time at which the almanac curve fit was performed)
     * @return Long, Time Of week
     */
    public long getGPSTOA(){
        return GPSTOA;
    }
    
    /**
     * Returns the SVN number for a given PRN.  PRN must be between 1-32
     * @param PRNNumber PRN for which SVN is desired
     * @throws java.lang.ArrayIndexOutOfBoundsException PRN must be between 1 and 32
     * @return SVN number associated with PRN
     */
    public int getSVN(int PRNNumber) throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return (int)PRN[PRNNumber][SVN];
    }
    
    /**
     * Returns the User Range Accuracy number for a given PRN
     * @param PRNNumber The PRN for which the URA is desired
     * @throws java.lang.ArrayIndexOutOfBoundsException PRN must be between 1 and 32
     * @return The URA value (between 1-15)
     */
    public int getURA(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return (int)PRN[PRNNumber][AVERAGEURA];
    }
    
    /**
     * Returns the eccentricity value for a given PRN
     * @param PRNNumber The PRN for which the eccentricity is desired.
     * @throws java.lang.ArrayIndexOutOfBoundsException PRN must be between 1 and 32
     * @return eccentricity
     */
    public double getEccentricity(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][ECCENTRICITY];
    }
    
    /**
     * Returns the inclination offset from 0.3 semi-circles for a given PRN
     * @param PRNNumber The PRN for which the inclination offset is desired
     * @throws java.lang.ArrayIndexOutOfBoundsException PRN must be between 1 and 32
     * @return the inclination offset in semi-circles
     */
    public double getInclinationOffset(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][INCOFFSET];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Omega dot for the given PRN
     */
    public double getOmegaDot(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][OMEGADOT];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Semi-major axis for the given PRN
     */
    public double getSqrtSemiMajorAxis(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][SQRTSMA];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Omega zero for the given PRN
     */
    public double getOmegaZero(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][OMEGA0];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Omega for the given PRN
     */
    public double getOmega(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][OMEGA];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Mean anomaly for the given PRN
     */
    public double getMeanAnomaly(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][MEANANOMALY];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Zeroth order clock correction for the given PRN
     */
    public double getAFZero(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][AF0];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return First order clock correction for the given PRN
     */
    public double getAFOne(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return PRN[PRNNumber][AF1];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return health value for the given PRN
     */
    public int getHealth(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return (int)PRN[PRNNumber][HEALTH];
    }
    
    /**
     * 
     * @param PRNNumber 
     * @throws java.lang.ArrayIndexOutOfBoundsException 
     * @return Satellite configuration for the given PRN
     */
    public int getSatConfig(int PRNNumber)throws ArrayIndexOutOfBoundsException{
        if ( (PRNNumber > 32) || (PRNNumber <= 0)) throw new ArrayIndexOutOfBoundsException("PRN must be between 1 and 32");
        return (int)PRN[PRNNumber][SATCONFIG];
    }
    
   
}
