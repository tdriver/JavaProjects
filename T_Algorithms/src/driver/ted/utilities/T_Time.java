
package driver.ted.utilities;   // place in utilities package
import java.text.DecimalFormat;  // used for number formatting
import java.io.Serializable;

/** <U>Title:</U> T_Time class<br>
 * <U>Description:</U> Class defining basic time implementation<br>
 * <U>Copyright:</U> (c) 2002 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */


public class T_Time extends Object implements Serializable{
   private int hour;     // 0 - 23
   private int minute;   // 0 - 59
   private int second;   // 0 - 59

   
   /** T_Time constructor initializes each instance variable
    * to zero. Ensures that T_Time object starts in a
    * consistent state.
    */   
   public T_Time() { setTime( 0, 0, 0 ); }

   
   /** T_Time constructor: hour supplied, minute and second
    * defaulted to 0.
    * @param h The integer hour
    */   
   public T_Time( int h ) { setTime( h, 0, 0 ); }

   
   /** T_Time constructor: hour and minute supplied, second
    * defaulted to 0.
    * @param h The integer hour
    * @param m The integer minute
    */   
   public T_Time( int h, int m ) { setTime( h, m, 0 ); }

   
   /** T_Time constructor: hour, minute and second supplied.
    * @param h The integer hour
    * @param m The integer minute
    * @param s The integer seconds
    */   
   public T_Time( int h, int m, int s ) { setTime( h, m, s ); }

   
   /** T_Time constructor with another T_Time object supplied.
    * @param time The T_Time object to base this one upon.
    */   
   public T_Time( T_Time time )
   {
      setTime( time.getHour(),
               time.getMinute(),
               time.getSecond() );
   }

   
   /** Set the time to a new value using 24 hour time.
    * This function will perform validity checks on the data.
    * If the second or minute values are greater than 59,
    * the minute or hour will be updated as well.
    * @param h The integer hour to set
    * @param m The integer minute to set
    * @param s The integer second to set
    */   
   public void setTime( int h, int m, int s )
   {

     hour = h;  // set the hour
     minute = m;   // set the minute
     second = s;  // set the second
     normalize(); // normalize the allow the correct ranges
   }

   /** Normalizes the current time elements to the following:
    * <CODE>0 <= hours <= 23</CODE>
    * <CODE>0 <= minutes <= 59</CODE>
    * <CODE>0 <= seconds <= 23</CODE>
    * This function will increment minutes if seconds is > 59 and
    * it will increment hours if minutes > 59.
    * Hours will only be adjusted to be within the limits described above.
    */   
   private void normalize(){
    minute += second/60;
    hour += minute/60;
    hour %= 24;
    minute %= 60;
    second %= 60;
  }

  /** Advances the current time by the specified amounts
   * @param h The number of hours to advance (positive or negative)
   * @param m The number of minutes to advance (positive or negative)
   * @param s The number of seconds to advance (positive or negative)
   */  
  public void advanceTime(int h, int m, int s){
    hour += h;
    minute += m;
    second += s;
    normalize();
  }
  
  /** Sets the hour
   * @param h The new hour value
   */  
   public void setHour( int h ){
    setTime(h,getMinute(), getSecond());
   }

   /** Sets the minutes
    * @param m The new minute value
    */   
   public void setMinute( int m ){
    setTime(getHour(), m, getSecond() );
   }

   /** Sets the seconds
    * @param s the new seconds value
    */   
   public void setSecond( int s ){
    setTime(getHour(), getMinute(), s);
   }

   /** Provides the current hour
    * @return The integer hour
    */   
   public int getHour() { return hour; }

   /** Provides the current minute
    * @return The integer minutes
    */   
   public int getMinute() { return minute; }

   /** Provides the current seconds
    * @return The integer seconds
    */   
   public int getSecond() { return second; }

   // Convert to String in 24 hour-time format
   /** Provides a time output in string format.
    * Example formats: <CODE>02:14:02</CODE> and <CODE>22:02:17</CODE>
    *
    * @return A string representing the current time
    */   
   public String toString()
   {
      DecimalFormat twoDigits = new DecimalFormat( "00" );

      return twoDigits.format( getHour() ) + ":" +
             twoDigits.format( getMinute() ) + ":" +
             twoDigits.format( getSecond() );
   }

   // Convert to String in standard-time format
   /** Provides the current time in 12 hour (AM/PM) format.
    * Example time outputs: <CODE>02:14:02 AM</CODE> and <CODE>10:02:17 PM</CODE>
    * @return A string representing the current time in 12 hour format.
    */   
   public String toAMPMString()
   {
      DecimalFormat twoDigits = new DecimalFormat( "00" );

      return ( ( getHour() == 12 || getHour() == 0 ) ?
               12 : getHour() % 12 ) + ":" +
             twoDigits.format( getMinute() ) + ":" +
             twoDigits.format( getSecond() ) +
             ( getHour() < 12 ? " AM" : " PM" );
   }
}
