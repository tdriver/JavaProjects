package driver.ted.utilities;

import java.text.DecimalFormat;

/** <U>Title:</U> T_Utilities<br>
 * <U>Description:</U>  This class provides static utility functions.<br>
 * <U>Copyright:</U>    (c) 2002 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */

public class T_Utilities {
    /** Constant value for the Speed of Light c.
     * c = 299792458 m/s
     */
  public static final double C = 299792458L;   // speed of light
  /** Constant value for the GPS L1 Frequency.
   * L1 = 1575.42 MHz
   */
  public static final double L1 = 1575.42e6;  // GPS Link 1 Frequency
  /** Constant Value for the GPS L2 Frequency.
   * L2 = 1227.6 MHz
   */
  public static final double L2 = 1227.6e6;   // GPS Link 2 Frequency
  /** Constant Value for Pi to 24 decimal places.
   * Pi = 3.141592653589793238462643
   */
  public static final double PI = 3.141592653589793238462643;

  /** Constant Value for the degrees to Radians conversion factor.
   * DtR = PI/180.0
   */
  public static final double DtR = PI/180.0;

  /** Constant Value for the Earth Equatorial Radius.
   * EarthEquatorialRadius = 6378137.0  meters
   */
   public static final double EarthEquatorialRadius = 6378137.0;

   /** Constant value for the Earth's eccentricity.
    * EarthEccentricity = 0.0818191908426156, no units
    */
  public static final double EarthEccentricity = 0.0818191908426156;

  /** Constant value to convert arcseconds to radians.
   * Use the inverse of this number to convert from radians to arcseconds
   * AsToRad = 4.848136812e-6
   */
  public static double AsToRad = 4.848136812e-6;

  /** Static Public function to bound a number between a min and max value.
   * Example 1: to convert 270 degrees to the range -180 to 180 degrees
   * <P><CODE>normalize(-180, 180, 270, 15)</CODE></P>
   * returns -90.0
   * Example 2 to convert a decimal to its fractional value
   * <P><CODE>normalize(0,1,23.789456,6)</CODE></P>
   * returns 0.789456
   * @param min The minimum value the number can have
   * @param max The maximum value the number can have
   * @param x The value to bound
   * @param precision The precision of the returned value
   * @return The value bounded between min and max
   */
  public static double normalize(double min, double max, double x, int precision)
  {
    double range = 0.0, result = 0.0;
    DecimalFormat df = new DecimalFormat(getPrecisionString(1,precision));
    if (min >= max){
      javax.swing.JOptionPane.showMessageDialog(null,"Normalize error: min >= max!",
            "Normalize Function Error",javax.swing.JOptionPane.ERROR_MESSAGE);
      return Double.MAX_VALUE;
    }else if ( (min <= x) && (x <= max) ){
  	return Double.parseDouble(df.format(x));
    }else{
      range = max - min;
      result = (x-min)/range;
      result = result - Math.floor(result);
      result = min + result*range;
      return Double.parseDouble(df.format(result));
    }
  }

  /** Static Public function to bound a number between a min and max value, using a default precision of 15.
   * Example 1: to convert 270 degrees to the range -180 to 180 degrees
   * <P><CODE>normalize(-180, 180, 270)</CODE></P>P>
   * returns -90.0
   * Example 2 to convert a decimal to its fractional value
   * <P><CODE>normalize(0,1,23.789456)</CODE></P>
   * returns 0.789456000000001
   * @param min The minimum value the number can have
   * @param max The maximum value the number can have
   * @param x The value to bound
   * @return The value bounded between min and max
   */
  public static double normalize(double min, double max, double x){
    // default to 15 decimal places precision
    return normalize(min,max,x,15);
  }

  /**
   * Public function to return a string to use in the DecimalFormat constructor, provided an integer.
   * This function is called as the argument to a DecimalFormat construction and will provide
   * a string with the number of 0's after the decimal point as defined by the supplied precision value
   * @param leading Specifies the number of leading zeros (to the left of the decimal point)
   * @param precision Specifies the number of trailing zeros (to the right of the decimal point)
   * @return The format string for the DecimalFormat construction
   */
  public static String getPrecisionString(int leading, int precision){
    String s = "";
    // add the preceeding 0's
    for (int i = 0; i < leading; i++){
      s += "0";
    }
    // add the post 0's
    for (int i = 0; i < precision; i++){
      // add the decimal point if we get in the loop
      if(i == 0) s += ".";
      s += "0";
    }
    return s;
  }

  // the following two functions replace the functionality of the modf
  // function in the c++ language.  There maybe a Java way to do this,
  // but I didn't find it in looking for 10 minutes. TED.

  /** This function casts the double to an int, effectively returning
   * just the integer portion of the double value. 
   * This function gets the integer portion of the double provided.
   * All numbers to the left of the decimal point, including sign.
   *
   * @param value The double value whose integer part is needed
   * @return The integer part of the double value
   */  
  public static int getIntOfDouble(double value){
     return (int)value;
  }
   
  /**
   * This function gets all numbers to the right of the decimal of
   * the double provided, including sign.
   * @param a Double value to extract the right side from
   * @return A double representing that part of the input value to the right of the decimal point 
   */
  public static double getDecimalOfDouble(double a){

    // display only 15 decimal digits regardless
    DecimalFormat nf = new DecimalFormat("0.000000000000000");

    // convert input double to a string for manipulation
    String dstr = new Double(a).toString();

    // find the length of the string
    int length = dstr.length();

    // find the index of the decimal point
    int dpindex = dstr.indexOf(".");

    // set the maximum number of digits to the right of the decimal
    // point to be the length of the original number
    if ( (length - dpindex - 1) > 15){
      length = 15;
    }else{
      length = length - dpindex - 1;
    }
    nf.setMaximumFractionDigits(length);

    // get the fraction value and format it
    String answerstr = nf.format( (Double.valueOf(dstr)).doubleValue() - getIntOfDouble(a) );

    // return fraction after converting it from a string to a double
    return (Double.valueOf(answerstr)).doubleValue();
  }

  /**
   * Function to return a string in Degree:Minute:Second format, provided a decimal value.
   * This function converts decimal degrees to DMS format
   * @return A string representing the DMS format of value
   * @param value The value to convert to DMS format
   * @param precision The precision of the seconds in the DMS string
   */
  public static String decimalToDMS(double value, int precision){
    String DMS = "";
    int degrees, minutes;
    double fraction, seconds;
    //DecimalFormat format = new DecimalFormat("00");
    DecimalFormat seconds_format = new DecimalFormat(getPrecisionString(2,precision));

    degrees = (int)value;
    fraction = value - degrees;
    minutes = (int)(fraction*60.0);
    fraction = fraction*60.0 - minutes;
    seconds = fraction*60.0;
    DMS += degrees + " " + minutes + " " + seconds_format.format(seconds);
    return DMS;
  }

  /**
   * Utility to convert signed latitude degree.decimal values to DMS strings with directions added. 
   * If value is positive, the DMS string will have an 'N' appended; if negative, an 'S' will be
   * appended to the DMS string.
   * @return The DMS string with the added direction
   * @param value The signed degree value to convert to a DMS string
   * @param precision The precision of the seconds value in the string
   */
  public static String decimalToDMSDIRV(double value, int precision){
    String DMS = "";
    String DIR = "";
    int degrees, minutes;
    double fraction, seconds;
    //DecimalFormat format = new DecimalFormat("00");
    DecimalFormat seconds_format = new DecimalFormat(getPrecisionString(2,precision));

    degrees = (int)value;
    if (value < 0){
      value = Math.abs(value);
      degrees = Math.abs(degrees);
      DIR = "S";
    }else{
      DIR = "N";
    }
    fraction = value - degrees;
    minutes = (int)(fraction*60.0);
    fraction = fraction*60.0 - minutes;
    seconds = fraction*60.0;
    DMS += degrees  + " "  + minutes + "' " + seconds_format.format(seconds) + "\" " + DIR;
    return DMS;
  }

  /**
   * Utility to convert signed longitude degree.decimal values to DMS strings with directions added. 
   * If value is positive, the DMS string will have an 'E' appended; if negative, a 'W' will be
   * appended to the DMS string.
   * @return The DMS string with the added direction
   * @param value The signed degree value to convert to a DMS string
   * @param precision The precision of the seconds value in the string
   */
  public static String decimalToDMSDIRH(double value, int precision){
    String DMS = "";
    String DIR = "";
    int degrees, minutes;
    double fraction, seconds;
    //DecimalFormat format = new DecimalFormat("00");
    DecimalFormat seconds_format = new DecimalFormat(getPrecisionString(2,precision));

    degrees = (int)value;
    if (value < 0){
      value = Math.abs(value);
      degrees = Math.abs(degrees);
      DIR = "W";
    }else{
      DIR = "E";
    }
    fraction = value - degrees;
    minutes = (int)(fraction*60.0);
    fraction = fraction*60.0 - minutes;
    seconds = fraction*60.0;
    DMS += degrees  + " "  + minutes + "' " + seconds_format.format(seconds) + "\" " + DIR;
    return DMS;
  }

  /**
   * Public function to return a string to use in the format hh:mm:ss provided a double and an int precision.
   * This function is called to return a specified format string for decimal times.  The precision affects how
   * many decimal places the seconds term will have in the result.
   * @param value The value to convert to a time string
   * @param precision The number of decimal places to show in the seconds
   * @return The format string for the DecimalFormat construction
   */
  public static String decimalToTimeHMS(double value, int precision){
    String HMS = "";
    int hours, minutes;
    double fraction, seconds;
    DecimalFormat intformat = new DecimalFormat("00");
    DecimalFormat seconds_format = new DecimalFormat(getPrecisionString(2,precision));

    hours = (int)value;
    fraction = value - hours;
    minutes = (int)(fraction*60.0);
    fraction = fraction*60.0 - minutes;
    seconds = fraction*60.0;
    HMS += intformat.format(hours) + ":" + intformat.format(minutes) + ":" + seconds_format.format(seconds);
    return HMS;
  }

  /**
   * Public function to return a string to use in the format hh mm ss.s provided a double and an int precision.
   * This function is called to return a specified format string for decimal times.  The precision affects how
   * many decimal places the seconds term will have in the result.
   * This functiuon is primarily for converting Right Ascension values, as the decimal
   * supplied is divided by 15 prior to conversion.
   * @param value The value to convert to a time string
   * @param precision The number of decimal places to show in the seconds
   * @return The formatted string for Right Ascension
   */
  public static String decimalToRAHMS(double value, int precision){
    String HMS = "";
    int hours, minutes;
    double fraction, seconds;
    //DecimalFormat format = new DecimalFormat("00");
    DecimalFormat seconds_format = new DecimalFormat(getPrecisionString(2,precision));

    //Convert degrees to hours, 15 degrees per hour
    value /= 15.0;

    hours = (int)value;
    fraction = value - hours;
    minutes = (int)(fraction*60.0);
    fraction = fraction*60.0 - minutes;
    seconds = fraction*60.0;
    HMS += hours + ":" + minutes + ":" + seconds_format.format(seconds);
    return HMS;
  }
}
