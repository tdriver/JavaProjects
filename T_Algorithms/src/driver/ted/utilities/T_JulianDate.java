
package driver.ted.utilities;

import java.text.DecimalFormat;
import java.io.Serializable;


/** <U>Title:</U> T_JulianDate Class
 * <U>Description:</U> Provides a Julian Date implementation based upon
 * Jean Meeus' Astronomical Algorithms.<br>
 * <U>Copyright:</U>(c) 2002<br>
 * <U>Change History:</U><br>
 *
 * @author Ted Driver
 * @version 1.1
 */

public class T_JulianDate extends Object implements Serializable{

    /** True if the current year is a leap year, false otherwise.
     */
  private boolean leapYear;

  /** True if the current date is in the Gregorian Calendar (prior to Oct 15, 1582).
   */
  private boolean gregorian;

  /** Day of Year, the julian day number within the year (1-366).
   */
  private int dayOfYear;

  /** The day of the week (0 = Sunday through 6 = Saturday).
   */
  private int dayOfWeek;

  /** The julian date.
   */
  private double JD;

  /** The fraction of the day: 0.000000 midnight and 0.500000 noon.
   */
  private double fractionOfDay;

  /** Time structure to store current time.
   */
  private T_Time time;

  /** Date structure to store current date.
   */
  private T_Date date;

  /** T_JulianDate Default Constructor that sets the date and time to the current
   * computer time.
   */
  public T_JulianDate() {
    date = new T_Date();
    time = new T_Time();
    // sets the time and date to the current system time
    setJulianDate(); // will call TimeToFrac and CommonToJulian
  }

  /** T_JulianDate Constructor that sets the date and time based
   * on the given parameters.
   * @param mo The month 1-12
   * @param da The day 1-31
   * @param y The year -4712 to 10000
   * @param h The hour 0-23
   * @param mi The minutes 0-59
   * @param s The seconds 0-59
   */
  public T_JulianDate(int mo, int da, int y, int h, int mi, int s){
    date = new T_Date();
    time = new T_Time();
    date.setDate(mo,da,y);
    time.setTime(h,mi,s);
    TimeToFrac();
    CommonToJulian();
  }

  /** T_JulianDate Constructor that sets the date and time based
   * on the given T_Time and T_Date objects.
   * @param ti The T_Time class initialized to a specific time.
   * @param da The T_Date class initialized to a specific date.
   */
  public T_JulianDate(T_Time ti, T_Date da){
    date = da;
    time = ti;
    TimeToFrac();
    CommonToJulian();
  }

  /** T_JulianDate Constructor that sets the date and time based
   * on a double provided as the julian date.
   * @param j The double representing the julian date
   */
  public T_JulianDate(double j){
    date = new T_Date();
    time = new T_Time();
    JD = j;
    JulianToCommon();
    FracToTime();
  }

  // &&&&&&&&&&&&&&  Helper functions  &&&&&&&&&&&&&&&&&&&&&&
  /**
   * This function determines whether the current year is a leap year
   * and sets the leapYear variable accordingly.
   */
  private void CalculateLeapYear(){
    // bissextile year calculation
    // assumes that the year has already been set in the date
    int year = date.getYear();

    if ( (year % 4) == 0){ // possible candidate
      if( gregorian ){
        if ( (year % 100) == 0 ){ // must check centurial years
          if ( (year % 400) == 0 ){
            leapYear = true;
          }else{
            leapYear = false;
          }
        }else{ // not centurial year, but divisible by 4
          leapYear = true;
        }
      }else{ // not gregorian calendar, but divisible by 4
        leapYear = true;
      }
    }else{ // not divisible by 4
      leapYear = false;
    }
  }

  /**
   * This function determines the day of the year 1-366, based on an
   * algorithm from page 65 of Jean Meeus' <i>Astronomical Algorithms</i> book.
   */
  private void CalculateDayOfYear(){
  //
    int N, K = 2, D, M;

    CalculateLeapYear();

    if ( leapYear ) K = 1;

    D = date.getDay();
    M = date.getMonth();

    N = (int)(275*M/9) - K*(int)((M+9)/12) + D - 30;

    dayOfYear = N;

  }

  /**
   * This function determines the month and day of the year from a day of year.
   * This algorithm is based on an algorithm from Chapter 7 page 66 of Jean Meeus'
   * <i>Astronomical Algorithms</i> book.
   */
  private void CalculateDateFromDayOfYear(){
    int K = 2; // K = 2 in non-leap years
    int month, day;

    CalculateLeapYear();
    if ( leapYear ) K = 1;

    month = (int)(9.0*(K + dayOfYear)/275.0 + 0.98);
    if (dayOfYear < 32) month = 1;
    day = dayOfYear - (int)(275*month/9) + K*(int)( (month + 9)/12 ) + 30;

    date.setMonth(month);
    date.setDay(day);
    CommonToJulian();
  }

  /**
   * This function determines the day of the week (0 through 6).
   * This algorithm is based on an algorithm from page 65 of Jean Meeus'
   * <i>Astronomical Algorithms</i> book.
   */
  private void CalculateDayOfWeek(){
    T_Time t = new T_Time(time); // save current time
    long temp;

    time.setTime(0,0,0); // reset to midnight of the current day

    // recalculate JD parameters based on time change
    TimeToFrac();
    CommonToJulian();

    // find day of week
    temp = (long)(JD + 1.5);
    dayOfWeek = (int)(temp % 7);

    // restore time and date values
    time = t;
    TimeToFrac(); // get back to old time
    CommonToJulian(); // get back to old date
  }

  /** This method returns a String representation of the Julian Date
   * including the double, date and time values.
   * The Format of the string is:<br>
   * <CODE>2451545.0 -- 01/01/00 12:00:00</CODE>
   *
   * @return A string representing the current Julian Date
   */
  public String toString(){
    String output = "";
    DecimalFormat jdf = new DecimalFormat("0.000000");
    output += jdf.format(JD) + " -- " + date + " " + time;
    return output;
  }

  /** This method returns a longer String representation of the Julian Date
   * including the double, date and time values.
   * Example format:<br>
   * <CODE>January 01, 2000 12:00:00, 2451545.0</CODE>
   * @return A string representing the current Julian Date.
   */
  public String toMediumString(){
    String output = "";
    DecimalFormat jdf = new DecimalFormat("0.000000");
    output += date.toMediumString() + " " + time.toString() + ", " + jdf.format(JD);
    return output;
  }
  
  /** This method returns a String representation of the Julian Date
   * in STK's UTCG format.
   * The Format of the string is:<br>
   * <CODE>(dd mmm yyyy hh:mm:ss.s)</CODE>
   *
   * @return A string representing the current Julian Date in STK UTCG format
   */
  public String toSTKUTCGString(){
    String output = "";
    //(dd mmm yyyy hh:mm:ss.s). 
    String day = Integer.toString(date.getDay());
    String monthsofYear[] = {"xxx", "Jan", "Feb",
                  "Mar", "Apr", "May", "Jun", "Jul",
                  "Aug","Sep","Oct","Nov","Dec"};
    String mon = monthsofYear[date.getMonth()];
    String year = Integer.toString(date.getYear());
    String hour = Integer.toString(time.getHour());
    String min = Integer.toString(time.getMinute());
    String sec = Integer.toString(time.getSecond());
    output = day + " " + mon + " " + year + " " + hour + ":" + min + ":" + sec + ".0";
    //output += date.toMediumString() + " " + time.toString() + ", " + jdf.format(JD);
    return output;
  }


  // Date functions

  /**
   * This function determines whether the current date is in the Gregorian calendar
   * (prior to Oct 15, 1582) and sets the gregorian boolean accordingly.
   */
  private void CalculateGregorian(){
    gregorian = true; // positive assumption
    int Y = date.getYear();
    int M = date.getMonth();
    double D = (double)date.getDay() + fractionOfDay;

    if( Y < 1582 ){
      gregorian = false;
    }else if( (Y == 1582) && (M < 10) ){
      gregorian = false;
    }else if( (Y == 1582) && (M == 10) && (D < 15) ){
      gregorian = false;
    }
  }

  /**
   * This function calculates the Julian Date from common date parameters.
   * Prior to calling this function, the <code>TimeToFrac</code> function must have been
   * called to set the <code>FractionOfDay</code> variable
   */
  private void CommonToJulian(){
  // requires that FractionOfDay is already calculated by TimeToFrac
    long A, B, temp;

    int Y = date.getYear();
    int M = date.getMonth();
    double D = (double)date.getDay() + fractionOfDay;

    CalculateGregorian();

    if( (M == 1) || (M == 2) ){
      Y -= 1;
      M += 12;
    }

    if(gregorian){
      A = (int)(Y/100);
      B = 2 - A + (int)(A/4);
    }else{
      B = 0;
    }

    temp = (long)(365.25*(Y + 4716));
    JD = temp;
    temp = (long)(30.6001*(M + 1));
    JD += temp;
    JD += D + B - 1524.5;
  }

  /**
   * The opposite of CommonToJulian, this function calculates the
   * common date from julian date parameters.
   */
  private void JulianToCommon(){
    double tempJD, F, Z, A, B, C, D, E, day, month, year;
    long alpha;

    tempJD = JD;
    tempJD += 0.5;

    Z = T_Utilities.getIntOfDouble(tempJD);
    F = T_Utilities.getDecimalOfDouble(tempJD);

    if (Z < 2299161L){
       A = Z;
    }else{
       alpha = (int)( (Z-1867216.25)/36524.25 );
       A = Z + 1 + alpha - (int)(alpha/4);
    }

    B = A + 1524;
    C = (int)( (B - 122.1)/365.25 );
    D = (int)(365.25*C);
    E = (int)( (B - D)/30.6001 );

    // Set day
    day = B - D - (int)(30.6001*E) + F;


    // reset fractionOfDay with new value
    fractionOfDay = T_Utilities.getDecimalOfDouble(day);
    // reuse A to store integer part of day number
    A = T_Utilities.getIntOfDouble(day);

    // Set month
    if (E < 14){
      month = E - 1;
    }else if( (E == 14) || (E == 15) ){
      month = E - 13;
    }else{
      javax.swing.JOptionPane.showMessageDialog(null,
           "Month setting incorrect in T_JulianDate.JulianToCommon() Function");
      return;
    }

    // Set year
    if (month > 2){
      year = C - 4716;
    }else if( (month == 1) || (month == 2) ){
      year = C - 4715;
    }else{
      javax.swing.JOptionPane.showMessageDialog(null,
           "Year setting incorrect in T_JulianDate.JulianToCommon() Function");
      return;
    }

    // Reset Date with calculated values
    date.setDate((int)month,(int)A,(int)year);
  }

  // Time Functions
  /**
   * This function converts the current fraction of day to a time.
   */
  private void FracToTime(){
    double s, T;
    int timevar;

    s = fractionOfDay*86400.0;

    // convert T to hours.decimal
    T = s/3600.0;
    // extract the integer hours from T in seconds
    timevar = T_Utilities.getIntOfDouble(T);
    //set the hours of the time variable
    time.setHour(timevar);

    // convert T to minutes.decimal
    T = T_Utilities.getDecimalOfDouble(T)*60.0;
    // extract the integer minutes from T
    timevar = T_Utilities.getIntOfDouble(T);
    // set the minutes of the time variable
    time.setMinute(timevar);

    // convert T to seconds
    T = T_Utilities.getDecimalOfDouble(T)*60.0;
    // extract the number of seconds
    timevar = T_Utilities.getIntOfDouble(T);
    // see if we need to round the result
    if ( T_Utilities.getDecimalOfDouble(T) >= 0.5){
      timevar += 1;
    }
    // set the seconds of the time variable
    time.setSecond(timevar);
  }

  /**
   * This function converts the current time to a fraction of day.
   */
  private void TimeToFrac(){
    fractionOfDay = (double)time.getSecond() +
                    (double)(time.getMinute())*60.0 +
                    (double)(time.getHour())*3600.0;
    fractionOfDay /= 86400.0;
  }


  // &&&&&&&&&&&&&&  Mutators   &&&&&&&&&&&&&&&&&&&&&&&&&&&&&
  /** This function will set the Julian date based on the day of year.  The
   * year currently set is used.
   * @param doy The interger day of the year.
   */
  public void setDayOfYear(int doy){
    // Assume error checking has been done to ensure doy is an int
    dayOfYear = doy;
    CalculateDateFromDayOfYear();
  }

  /** This function will increment (or decrement) the date and time by the
   * specified amounts.
   * Negative values can be used to decrement the date or time.
   * @param mo The number of months to advance (positive or negative).
   * @param d The number of days to advance (positive or negative).
   * @param y The number of years to advance (positive or negative).
   * @param h The number of hours to advance (positive or negative).
   * @param mi The number of minutes to advance (positive or negative).
   * @param s The number of seconds to advance (positive or negative).
   */
  public void advance(int mo, int d, int y, int h, int mi, int s){
    date.advanceDate(mo, d, y);
    time.advanceTime(h, mi, s);
    TimeToFrac();
    CommonToJulian();
  }

  /** This function will change the Julian date to the new
   * date and time as specified by the input parameters.
   * @param mo The number of months to advance
   * @param d The number of days to advance
   * @param y The number of years to advance
   * @param h The number of hours to advance
   * @param mi The number of minutes to advance
   * @param s The number of seconds to advance
   */
  public void setJulianDate(int mo, int d, int y, int h, int mi, int s){
    date.setDate(mo,d,y);
    time.setTime(h,mi,s);
    TimeToFrac();
    CommonToJulian();
  }

  /** This function will change the Julian date to the new
   * date and time as specified by the input parameters
   * @param d A T_Date class with a specified date set
   * @param t A T_Time class with the specified time set
   */
  public void setJulianDate(T_Date d, T_Time t){
    date = d;
    time = t;
    TimeToFrac();
    CommonToJulian();
  }

  /** This function will set the julian date to the current system time and date
   * using the default machine timezone.  The current time is obtained by using the
   * Gregorian Calendar constructor.
   */
  public void setJulianDate(){
    int offset = 0; // hour offset
    // get the system time on the machine running in their default time zone
    java.util.GregorianCalendar gc = new java.util.GregorianCalendar();

    // set the time
    if (gc.get(gc.AM_PM) == 1) // it's PM, add 12 hours to time (gc's time is modulo 12)
      offset = 12;

    time.setTime(gc.get(gc.HOUR) + offset , gc.get(gc.MINUTE), gc.get(gc.SECOND));
    // set the date
    date.setDate(gc.get(gc.MONTH) + 1, gc.get(gc.DAY_OF_MONTH), gc.get(gc.YEAR));

    // calculate the Julian Date
    TimeToFrac();
    CommonToJulian();
  }
  
  /**
   * Set the date and update the Julian Date
   * @param mo The month (1-12)
   * @param day The day (1-31) Must correct for the defined month at the time of setting.
   * @param year The year (-4712 to 10000)
   */
  public void setDate(int mo, int day, int year) {
      this.getDate().setDate(mo, day, year);
      TimeToFrac();
      CommonToJulian();
  }
  
/**
   * Set the time and update the Julian Date
   * @param hr The hour
   * @param min The minute
   * @param sec The second
   */
  public void setTime(int hr, int min, int sec) {
      this.getTime().setTime(hr, min, sec);
      TimeToFrac();
      CommonToJulian();
  }
  
  /** This function will set the julian date to the date represented by the double
   * value passed in.
   * @param j The double value representing the julian date
   */
  public void setJulianDate(double j){
    JD = j;
    JulianToCommon();
    FracToTime();
  }

  // &&&&&&&&&&&&&&&&&&&  Accessors  &&&&&&&&&&&&&&&&&&&&&&&&
  /** This function returns the current julian date double value.
   * @return A double value representing the current date and time.
   */
  public double getJulianDate(){
    return JD;
  }

  /** This function returns the current leap year boolean value.
   * @return A boolean denoting whether the current year is a leap year (<code>true</code>)
   * or not (<code>false</code>).
   */
  public boolean getLeapYear(){
    CalculateLeapYear();
    return leapYear;
  }

  /** This function returns the current gregorian boolean value.
   * @return A boolean denoting whether the current date is in the gregorian calendar (<code>true</code>)
   * or not (<code>false</code>).
   */
  public boolean getGregorian(){
    CalculateGregorian();
    return gregorian;
  }

  /** This function returns a string representation of the day of week.
   * @return A String denoting the current day of the week
   */
  public String getDayOfWeek(){
    String daysOfWeek[] = {"Sunday","Monday","Tuesday","Wednesday",
                          "Thursday","Friday","Saturday"};
    CalculateDayOfWeek();
    return daysOfWeek[dayOfWeek];
  }

  /** This function returns an integer representing the day of the week.
   * @return An integer denoting the day of the week (0 = Sunday through 6 = Saturday)
   */
  public int getIntDayOfWeek(){
    CalculateDayOfWeek();
    return dayOfWeek;
  }

  /** This function returns the julian day or the day of the year.
   * @return An integer denoting the current day of the year (0-366).
   */
  public int getDayOfYear(){
    CalculateDayOfYear();
    return dayOfYear;
  }

  /** This function returns the julian day or the day of the year 
   * with a fraction appended representing the time of day.
   * @return A double denoting the current day of the year (0-366) and time of day (0 < 1) .
   */
  public double getDayOfYearAndTime(){
    CalculateDayOfYear();
    return dayOfYear + fractionOfDay;
  }
  
  /** This function returns the modified Julian date.  The modified Julian Date
   * is the Julian Date minus 2400000.5.
   * @return A double denoting the modified julian date.
   */
  public double getModifiedJulianDate(){
    return (JD - 2400000.5);
  }

  /** This function returns the T_Date class for the current date.
   * @return A T_Date class that contains the current date.
   */
  public T_Date getDate(){
    return date;
  }

  /** This function returns the T_Time class for the current time.
   * @return A T_Time class that contains the current time.
   */
  public T_Time getTime(){
    return time;
  }  
}