package driver.ted.utilities;
import java.text.DecimalFormat;  // used for number formatting
import java.io.Serializable;
/**
 * Title:        T_Utilities
 * Description:  This class represents a simple date, it is maintained in
 * the utility package driver.ted.utilities
 * Copyright:    (c) 2001 - 2006
 * @author Ted Driver
 * @version 1.1
 * Changes:
 *
 */


public class T_Date extends Object implements Serializable {
    /** The Day of the month (1-31)
     */
   private int day;     // 1 - 31
   
   /**The month of the year (1-12)
    */
   private int month;   // 1 - 12
   
   /** The year (-4712 - 10000)
    */
   private int year;   // -4712 - 10000

   /** Constructor takes three integer arguments to set the initial date
    * The month will be set between 1 and 12 even if a larger number is passed.
    * The resultant month will just the the supplied month mod 12.
    *
    * The day will be checked for consistency based upon the month previously set.
    * A day that is not within the typical range for the given month will
    * be caught and defaulted to 1
    *
    * The Year must be greater than or equal to -4712 and less than 10000.
    * @param m The Month
    * @param d The Day
    * @param y The Year
    */
  public T_Date(int m, int d, int y) {
      setMonth(m);
      setYear(y);
      setDay(d);
  }

  /** Default constructor sets the date to January 1, 2000, the start of the J2000
   * epoch.
   */
  public T_Date(){
    setMonth(1);
    setDay(1);
    setYear(2000);
  }

  /** Private Function checkMonth tests the month entry.  If the month is between 1 and
   * 12, returns immediately. If the month is greater than 12, the year is incremented
   * and the month is returned mod 12.
   * If the month is less than 0, the year is decremented and the month is returned
   * mod 12.
   * If the month is 0, the year is decremented by one and the month is returned as 12.
   * (December)
   * @param testmonth The month to check
   * @return The month between 1 and 12
   */
  private int checkMonth(int testmonth){
    int test = testmonth;
    if (test >= 1 && test <= 12) return test;
    if (test > 12){ // checks the month and updates the year as necessary
       // Determine how many years to increment
       int increment = (int)(test/12);
       year  = checkYear (getYear() + increment);
       test %= 12;
       return test;
    }else if (test < 0){
      // Determine how many years to decrement
      int increment = (int)(-test/12);
      year = checkYear (getYear() - increment);
      test %= 12;
      return -test;
    }else if (test == 0){
      year = checkYear (getYear() - 1);
      test = 12;
      return test;
    }
    return -1; // this shouldn't happen!

  }

  /** Checks the year input. If the year is less than -4712, displays an error message
   * and sets the year to -4712.  If the year is greater than 10000, displays an
   * error message and sets the year to 10000, otherwise returns the year.
   * @param testyear The year to check
   * @return Integer representing the year bounded to be between -4712 and 10000
   */  
  private int checkYear(int testyear){
  final int MAX_YEAR = 10000;
    if (testyear < -4712){
      javax.swing.JOptionPane.showMessageDialog(null,
        "Year must be greater than or equal to -4712...year set to -4712",
        "Error in T_Date checkYear",
        javax.swing.JOptionPane.ERROR_MESSAGE);
      return -4712;
    }else if (testyear > MAX_YEAR){
      javax.swing.JOptionPane.showMessageDialog(null,
        "Year must be less than or equal to " + MAX_YEAR + " ...year set to " + MAX_YEAR,
        "Error in T_Date checkYear",
        javax.swing.JOptionPane.ERROR_MESSAGE);

      return MAX_YEAR;
    }
    return testyear;
  }

  /** Checks the day input against the currently set month to make sure the number of
   * days is correct. If testDat is greater than zero and less than or equal to
   * the number of days for the current month, returns the day.  If the month is
   * February and the the day input is 29, will also perform a leap year check
   * to see if the current year allows a February 29th. If so, returns the day input.
   * Otherwise, displays an error message and returns a 1 for the day.
   * @param testDay the day to check
   * @return The integer day
   */  
  private int checkDay( int testDay )
   {
      int daysPerMonth[] = { 0, 31, 28, 31, 30,
                             31, 30, 31, 31, 30,
                             31, 30, 31 };

      if ( testDay > 0 && testDay <= daysPerMonth[ month ] )
         return testDay;

      // February: Check for leap year
      if ( month == 2 && testDay == 29 &&
           ( year % 400 == 0 || ( year % 4 == 0 && year % 100 != 0 ) ) ){
         return testDay;
      }

      // possibly add code here to update the month if the day is greater than
      // it should be for a given month

      javax.swing.JOptionPane.showMessageDialog(null,"Day " + testDay +
                                    " out of range...setting day to 1",
                                    "Error in T_Date checkDay",
                                    javax.swing.JOptionPane.ERROR_MESSAGE );
      return 1;  // leave object in consistent state
   }

// &&&&&&&&&&&&&&&&&&&&&&&   set methods (mutators)  &&&&&&&&&&&&&&&&&&&&&

  /**
    * @param m
    * @param d
    * @param y  */   
  public void advanceDate(int m, int d, int y){
    setMonth(getMonth() + m);
    setDay(getDay() + d);
    setYear(getYear() + y);
  }

  /**
   * @param m
   * @param d
   * @param y  */  
  public void setDate(int m, int d, int y){
    // set year first
    setYear(y);
    // set month, could possibly update year
    setMonth(m);
    // set day, may possibly update month
    setDay(d);
  }

  /**
   * @param m  */  
  public void setMonth(int m){
    month = checkMonth(m);
  }

  /**
   * @param d  */  
  public void setDay(int d){
    day = checkDay(d);
  }

  /**
   * @param y  */  
  public void setYear(int y){
    year = checkYear(y);
  }
// &&&&&&&&&&&&&&&&&&&&&  get methods (accessors)  &&&&&&&&&&&&&&&&&&&&&77

  /**
   * @return  The integer month */  
  public int getMonth(){
    return month;
  }

  /**
   * @return  Tthe integer day */  
  public int getDay(){
    return day;
  }

  /**
   * @return The integer year
   */  
  public int getYear(){
    return year;
  }


  /** Returns the date in short format.
   * Example: 12/14/2002
   * @return The string representing the short date
   */  
  public String toString(){
      String dateString = "";
      if(month < 10) dateString += "0" + month;
      else dateString += month;
      
      dateString += "/";
      
      if(day < 10) dateString += "0" + day;
      else dateString += day;
      
      dateString += "/" + year;
      
      return dateString;
 }

 /** Returns a string representing the medium length date.
  * Example: December 14, 2002
  * @return A String representing the date defined by this object
  */ 
 public String toMediumString(){
  String monthsofYear[] = {"xxx", "January", "February",
                  "March", "April", "May", "June", "July",
                  "August","September","October","November","December"};
  return monthsofYear[month] + " " + day + ", " + year;
 }
}
