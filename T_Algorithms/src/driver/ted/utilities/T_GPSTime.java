package driver.ted.utilities;

/** <U>Title:</U> T_GPSTime Class<br>
 * <U>Description:</U> The class provides GPS Time functions.<br>
 * <U>Copyright:</U> (c) 2002 - 2006<br>
 * <U>Change History:</U><br>
 * Changes:
 * @author Ted Driver
 * @version 1.1
 */

public class T_GPSTime {
  private T_JulianDate JD;
  private T_JulianDate gpsEpochs[] = new T_JulianDate[5];
  private int TimeOfWeek;
  private int GPSWeek;
  private int baseEpoch;
  private T_JulianDate baseDate;
  private double KPoints;
  private long ZCounts;

  /** The number of GPS epochs allowed for calculations.
   * Set to 5.
   * @todo add all the GPS GA and Monitor Station sites here or in another higher level class
   */
  static final int NUMBER_OF_GPS_EPOCHS = 5;
 //  private T_Site COSPM = new T_Site();

  /** Constructor that creates a GPSTime object with the current system time
   */
  public T_GPSTime() {

    // get the current date and time
    JD = new T_JulianDate();

    // initialize the base GPS Epochs
    initEpochs();

    // calculate the pertinent quantities
    calculateClosestEpochFromJD();

    // GPSWeek and TimeOfWeek are set in this function
    calculateWeekAndTimeOfWeek();

    // Calculate the rest
    calculateKPointsAndZCounts();

  }

  /**
   * Constructor that creates a GPSTime object with the initial time specified by the {@link T_JulianDate} object
   * @param J {@link T_JulianDate} object specifying the initial time
   */  
  public T_GPSTime(T_JulianDate J){

    // define the user-specified time
    this.JD = J;

    // initialize the base GPS Epochs
    initEpochs();

    // calculate the pertinent quantities
    calculateClosestEpochFromJD();

    // GPSWeek and TimeOfWeek are set in this function
    calculateWeekAndTimeOfWeek();

    // Calculate the K-Points and Z-Counts
    calculateKPointsAndZCounts();
  }

  /**
   * Constructor that creates a GPSTime object with the initial time set by the GPSWeek and timeOfWeek parameters
   * @param GPSWeek Must be counted from the initial GPS Epoch (Jan 6, 1980)
   * @param timeOfWeek Must be integer seconds from Sunday midnight
   */  
  public T_GPSTime(int GPSWeek, int timeOfWeek){
    this.GPSWeek = GPSWeek;
    this.TimeOfWeek = timeOfWeek;

    // initialize the base GPS Epochs
    initEpochs();

    // calculate the JD and remaining quantities
    calculateJD();

    calculateClosestEpochFromJD();

    // Calculate the K-Points and Z-Counts
    calculateKPointsAndZCounts();
  }

  // accessors
  /**
   * Utility method to return the GPS week number associated with the {@link T_JulianDate} object.
   * This method does NOT update the state of the T_GPSTime object
   * @param J {@link T_JulianDate} object
   * @return Returns the integer GPS week number since the GPS Epoch (Jan 6, 1980)
   */  
  public int getGPSWeek(T_JulianDate J){
    // save original state (JD only)
    T_JulianDate temp = JD;

    // assign new date and calculate the new quantities
    JD = J;
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();

    // get the currently calculated GPSWeek to return
    int tempWeek = GPSWeek;

    // restore the original state (JD only) and recalculate
    JD = temp;
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();

    // return the saved week
    return tempWeek;

  }

  /**
   * Utility method to return the time of week in seconds associated with the {@link T_JulianDate} object.
   * This method does NOT update the state of the T_GPSTime object
   * @param J {@link T_JulianDate} object
   * @return Integer time of week in seconds (0 - 604799)
   */  
  public int getTimeOfWeek(T_JulianDate J){
    // save original state (JD only)
    T_JulianDate temp = JD;

    // assign new date and calculate the new quantities
    JD = J;
    //calculateBaseEpoch();
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();

    // get the currently calculated GPSWeek to return
    int tempTOW = TimeOfWeek;

    // restore the original state (JD only) and recalculate
    JD = temp;
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();

    // return the saved week
    return tempTOW;
  }

  /**
   * Returns the GPS Week of the current GPSTime object
   * @return Integer GPS week since Jan 6, 1980
   */  
  public int getGPSWeek(){
    return GPSWeek;
  }


  /**
   * Returns the time of week of the current GPSTime object
   * @return Integer time of week (0-604799)
   */  
  public int getTimeOfWeek(){
    return TimeOfWeek;
  }

  /**
   * Returns the date of the latest epoch associated with the GPSTime object
   * <PRE>1st epoch = January 6, 1980, 00:00:00</PRE>
   * <PRE>2nd epoch = August 22, 1999, 00:00:00</PRE>
   * <PRE>3rd epoch = April 8, 2019, 00:00:00</PRE>
   * <PRE>4th epoch = November 21, 2038, 00:00:00</PRE>
   * <PRE>5th epoch = July 7th, 2058, 00:00:00</PRE>
   *  
   * Times greater than the 5th epoch are not supported.
   * @return String of the date of the latest epoch
   */  
  public String getGPSEpoch(){
    return baseDate.toMediumString();
  }

  /**
   * Returns the K-Points (15-minute segments) associated with this GPSTime object
   * @return Double value of the K-Points since the latest GPS epoch
   */  
  public double getKpoints(){
    return KPoints;
  }

  /**
    * Returns the Z-Counts (1.5 second segments) since the latest GPS Epoch
    * @return Long value of the number of Z-Counts since the latest GPS Epoch
    */  
   public long getZCounts(){
    return ZCounts;
  }

  /**
   * Returns the GPS Epoch index
   * 
   * <PRE>1 = 1st epoch = January 6, 1980 to August 22, 1999</PRE>
   * <PRE>2 = 2nd epoch = August 22, 1999 to April 7, 2019</PRE>
   * <PRE>3 = 3rd epoch = April 8, 2019 to November 21, 2038</PRE>
   * <PRE>4 = 4th epoch = November 21, 2038 to July 7th, 2058</PRE>
   * <PRE>5 = 5th epoch > July 7th, 2058</PRE>
   * 
   * Times greater than the 5th epoch are not supported.
   * @return Integer GPS epoch value
   */  
  public int getGPSEpochIndex(){
    return baseEpoch + 1;
  }

  /**
   * Returns the value of the GPSTime in Julian Date format
   * @return double value representing the Julian Date of the objects GPSTime
   */  
  public double getGPSTime(){
     return JD.getJulianDate();
  }

  // mutators
  
  /**
   * Advances the time maintained in this object. Use negative values to decrement the time.
   * @param mo Months to advance
   * @param d Days to advance
   * @param y Years to advance
   * @param h Hours to advance
   * @param mi Minutes to advance
   * @param s Seconds to advance
   */  
  public void advance(int mo, int d, int y, int h, int mi, int s){
    JD.advance(mo,d,y,h,mi,s);
    calculateClosestEpochFromJD();
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();
  }
  
  /**
   * Set the GPS time to that specified by the {@link T_JulianDate} object
   * @param J T_JulianDate object specifying the time
   */  
  public void setGPSTime(T_JulianDate J){
    JD = J;
    calculateClosestEpochFromJD();
    calculateWeekAndTimeOfWeek();
    calculateKPointsAndZCounts();
  }

  /**
   * Sets the GPS Epoch for the GPS time from which the Z-Counts and K-Points are referenced.
   * <PRE>1 = 1st epoch = January 6, 1980 to August 22, 1999</PRE>
   * <PRE>2 = 2nd epoch = August 22, 1999 to April 7, 2019</PRE>
   * <PRE>3 = 3rd epoch = April 8, 2019 to November 21, 2038</PRE>
   * <PRE>4 = 4th epoch = November 21, 2038 to July 7th, 2058</PRE>
   * <PRE>5 = 5th epoch > July 7th, 2058</PRE>
   * @param epoch The integer epoch
   */  
  public void setGPSEpoch(int epoch){
    // be sure epochs are 1 through 5
    if (epoch > 0 && epoch <= NUMBER_OF_GPS_EPOCHS){
      baseEpoch = epoch - 1;
      baseDate = gpsEpochs[baseEpoch];
      calculateWeekAndTimeOfWeek();
      calculateKPointsAndZCounts();
    }else{
      javax.swing.JOptionPane.showMessageDialog(null,
      "Epoch " + epoch + " not supported: Epochs must be 1 to " + NUMBER_OF_GPS_EPOCHS,
      "Error Determining GPS Epoch",
      javax.swing.JOptionPane.ERROR_MESSAGE);
    }
}

  // Private Functions
  /**
   * Calculates the JulianDate
   */
   private void calculateJD(){
    // GPSWeek and TimeOfWeek already set
    // must calculate the JD and the K-Points and Z-Count

    // calculate the JD first
    double newJD = gpsEpochs[0].getJulianDate() + GPSWeek*7.0 + TimeOfWeek/86400.0;
    JD = new T_JulianDate(newJD);
   //System.out.println("JD set to: " + JD);
  }

  /**
   * Calculates the base date and epoch
   */
   private void calculateClosestEpochFromJD(){
    if(JD.getJulianDate() < gpsEpochs[0].getJulianDate()){
      baseEpoch = 0;
      baseDate = gpsEpochs[baseEpoch];
//      javax.swing.JOptionPane.showMessageDialog(null,
//      "Times prior to " + getGPSEpoch() + " not supported",
//      "Error Determining GPS Epoch",
//      javax.swing.JOptionPane.ERROR_MESSAGE);
    }else if (JD.getJulianDate() < gpsEpochs[1].getJulianDate()){
      baseEpoch = 0;
      baseDate = gpsEpochs[baseEpoch];
    }else if(JD.getJulianDate() < gpsEpochs[2].getJulianDate()){
      baseEpoch = 1;
      baseDate = gpsEpochs[baseEpoch];
    }else if(JD.getJulianDate() < gpsEpochs[3].getJulianDate()){
      baseEpoch = 2;
      baseDate = gpsEpochs[baseEpoch];
    }else if(JD.getJulianDate() < gpsEpochs[4].getJulianDate()){
      baseEpoch = 3;
      baseDate = gpsEpochs[baseEpoch];
    }else{
      baseEpoch = 4;
      baseDate = gpsEpochs[baseEpoch];
//      javax.swing.JOptionPane.showMessageDialog(null,
//      "Dates after " + getGPSEpoch() + " not supported",
//      "Error Determining GPS Epoch",
//      javax.swing.JOptionPane.ERROR_MESSAGE);
    }
  }

   /**
    * Calculates the GPSWeek and TimeOfWeek
    */
   private void calculateWeekAndTimeOfWeek(){
    // get the number of days since the currently defined epoch
    double interval = JD.getJulianDate() - baseDate.getJulianDate();

    GPSWeek = (int)(interval/7.0);

    // calculate the Time Of Week
    int day, hour, minute, second;
    day = JD.getIntDayOfWeek();
    hour = JD.getTime().getHour();
    minute = JD.getTime().getMinute();
    second = JD.getTime().getSecond();

    TimeOfWeek = day*86400 + hour*3600 + minute*60 + second;
   }

  /**
   * Calculates the K-Points and Z-Counts.
   */
  private void calculateKPointsAndZCounts(){
    // get the number of days since the currently defined epoch
    double interval = JD.getJulianDate() - baseDate.getJulianDate();

    // calculate the KPoints since base Epoch
    // interval in days, times 96 KPoints per day
    KPoints = interval*96.0;

    // interval in days, times 57600 Z-Counts per day
    ZCounts = (long)(interval*57600.0);
    //ZCounts = (GPSWeek << 19) + (long)(TimeOfWeek/1.5);
    // System.out.println("Internal ZCounts = " + ZCounts);
    // System.out.println("Internal ZCounts (alternate) = " + (interval*57600.0));
  }

  /**
   * initializes the gpsEpochs
   */
  private void initEpochs(){
    // define the GPS Epochs
    gpsEpochs[0] = new T_JulianDate(1,6,1980,0,0,0);
    gpsEpochs[1] = new T_JulianDate(8,22,1999,0,0,0);
    gpsEpochs[2] = new T_JulianDate(4,7,2019,0,0,0);
    gpsEpochs[3] = new T_JulianDate(11,21,2038,0,0,0);
    gpsEpochs[4] = new T_JulianDate(7,7,2058,0,0,0);
  }


// Main Function to test the class
  /**
   * Test program for the T_GPSTime class
   * @param args Arguments
   */  
  public static void main(String[] args) {
    T_GPSTime t_GPS1 = new T_GPSTime(new T_JulianDate(8,21,1999,23,59,59));
  System.out.println("GPS Week = " + t_GPS1.getGPSWeek());

  System.out.println("Time of Week = " +t_GPS1.getTimeOfWeek());
  System.out.println("KPoints = " +t_GPS1.getKpoints());
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS1.getZCounts()),T_Converter.INTEGER,0));
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS1.getZCounts()),T_Converter.INTEGER,4));
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS1.getZCounts()),T_Converter.INTEGER,0).length());
  t_GPS1.setGPSEpoch(1);
  System.out.println("GPS Full Week = " + t_GPS1.getGPSWeek());
  System.out.println("KPoints since initial epoch = " + t_GPS1.getKpoints());
   T_GPSTime t_GPS2 = new T_GPSTime();
  System.out.println("GPS Week = " + t_GPS2.getGPSWeek());

  System.out.println("Time of Week = " +t_GPS2.getTimeOfWeek());
  System.out.println("KPoints = " +t_GPS2.getKpoints());
  t_GPS2.setGPSEpoch(1);
  System.out.println("GPS Full Week = " +t_GPS2.getGPSWeek());
  System.out.println("KPoints since initial epoch = " +t_GPS2.getKpoints());
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS2.getZCounts()),T_Converter.INTEGER,0));
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS2.getZCounts()),T_Converter.INTEGER,4));
  System.out.println("ZCounts = " + T_Converter.getBinary(Long.toString(t_GPS2.getZCounts()),T_Converter.INTEGER,0).length());
  T_GPSTime g1 = new T_GPSTime(4095,290533);

  System.out.println("GPS Week: " + g1.getGPSWeek());
  System.out.println("GPS K-Points: " + g1.getKpoints());
  System.out.println("GPS TimeOfWeek: " + g1.getTimeOfWeek());
  System.out.println("GPS Z-Count: " + g1.getZCounts());
  System.out.println("GPSEpoch = " + g1.getGPSEpoch());
  System.out.println("GPSEpochIndex = " + g1.getGPSEpochIndex());
    System.exit(0);
  }
}