package driver.ted.utilities;

import javax.swing.*;

/** <U>Title:</U> T_Interpolate<br>
 * <U>Description:</U> This class provides interpolation functions used within T_Algorithms.
 * These algorithms are based upon Jean Meeus' Astronomical Algorithms book, Chapter 3,
 * pages 23-34.<br>
 * <U>Copyright:</U> (c) 2001 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */

public class T_Interpolate {

  /** Interpolation difference values
   */
  private double A,B,C,D,E,F,G,H,J,K;

  /** Boolean to make sure Interpolation definition is done prior to interpolating.
   */
  private boolean INTERPOLATION_DEFINED;

  /** Extremum values and the interpolating factors
   */
  private double y, ym, nm, nzero, delta_nzero, n, interval, Xmid, Ymid;

  /** The maximum percent error in the difference.
   */
  private double maxDiff;

  /** Length of the interpolating vector, 3 or 5.
   */
  private int length;

  /** Constructor initializes all values to zero (0) ,
   * and <CODE>INTERPOLATION_DEFINED</CODE> to false.
   */  
  public T_Interpolate() {
    A = B = C = D = E = F = G = H = J = K = 0.0;
    y = ym = nm = nzero = delta_nzero = n = interval = Xmid = Ymid = 0.0;
    maxDiff = 0.0;
    length = 0;
    INTERPOLATION_DEFINED = false;
  }

  /** Provides statistics for the current Interpolation.
   * Including, Percent differences and the A, B and C values.
   * @return A Sting listing Interpolation statistics.
   */  
  public String toString (){
    String s = "";
    s+= "Statistics of current interpolation:\n";
    s+= "  Max Percent Difference = "  + maxDiff + "%\n";
    s+= "Differences:\n";
    s+= "A = " + A + "\n";
    s+= "  C = " + C + "\n";
    s+= "B = " + B + "\n";
    return s;
  }

  /**
   * Sets the X and Y values to interpolate over.
   * Also calculates differences (A,B & C) and
   * sets the <CODE>INTERPOLATION_DEFINED</CODE> flag to true.
   * @param X The array of X values to interpolate between.
   * Must be either 3 or 5 in length.
   * @param Y The array of Y values to interpolate over.
   * Must be either 3 or 5 in length.
   */  
  public void defineInterpolation(T_Vector X, T_Vector Y){
    if (X.getSize() != Y.getSize()){
      javax.swing.JOptionPane.showMessageDialog(null,"X and Y vectors in interpolation routine not equal lengths!");
      return;
    }
    length = Y.getSize();
    maxDiff = 0;
    if (length == 3){

     // calculate the third differences
     A = Y.getElement(1) - Y.getElement(0); // System.out.println("In interpolate::set, A = " + A + "\n");
     B = Y.getElement(2) - Y.getElement(1); // System.out.println("In interpolate::set, B = " + B + "\n");
     C = B - A;      // System.out.println("In interpolate::set, C = " + C + "\n");

     // set the mid points
     Xmid = X.getElement(1);  // System.out.println("In Interpolate::set, Xmid = " + Xmid + "\n");
     Ymid = Y.getElement(1);  // System.out.println("In Interpolate::set, Ymid = " + Ymid + "\n");
     
     for (int i = 0; i < 3; i++){
         // third difference percentage test
         double test = Math.abs(C)/Y.getElement(i);
         if ( test > maxDiff){
             maxDiff = test;
        }
     }

     // calculate the interval
     interval = X.getElement(1) - X.getElement(0);
     // System.out.println("In Interpolate::set, Interval = " + interval + "\n");

     INTERPOLATION_DEFINED = true;

    } else if (length == 5){
     System.out.println(" 5 value Interpolation is not supported yet" + "\n");
     return;
    } else {
     System.out.println(" Vector must be either 3 or 5 elements in length!" + "\n");
     return;
    }
  }

  /**
   * Provides the interpolating factor, n.
   * @return n, the double interpolating factor
   */  
  public double get_n(){
    return n;
  }

  /** Provides the maximum Y value defined from the three or 5 input Y values.
   * @return the maximum Y value, Y as a double.
   */  
  public double getYmax(){
    if(C != 0 && INTERPOLATION_DEFINED){
      return Ymid - (A+B)*(A+B)/(8.0*C);
    }else{
      System.out.println("C = 0 in Interpolate get_ymax()! or Interpolation not defined yet" + "\n");
      return Double.NaN;
    }
  }

  /** Provides the maximum interpolating factor, n_max.
   * @return the double n_max, the maximum interpolating factor.
   */  
 public double get_nmax(){
    if(C != 0 && INTERPOLATION_DEFINED){
      return -(A+B)/(2.0*C);
    }else{
      System.out.println("C = 0 in Interpolate get_nmax()! or Interpolation not defined yet" + "\n");
      return Double.NaN;
    }
 }

 /** The interpolation method.  Call this method with a value of x between the
  * X values defined in <CODE>defineInterpolation</CODE> above.  The interpolating
  * factor n will be calculated as <CODE>n = (x-xMid)/interval</CODE> where
  * <CODE>xMid</CODE> is the middle value of the X values passed into the
  * <CODE>defineInterpolation</CODE> method above and
  * <CODE>interval</CODE> is the difference between successive X values in the X
  * array passed into the <CODE>defineInterpolation</CODE> method above.
  * @param x An double value between the X values defined in the X array supplied in
  * <CODE>defineInterpolation</CODE>
  * @return An interpolated Y value corresponding to the the input X value.
  */ 
 public double interpolate(double x){
   if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else if(interval <= 0.0){
      System.out.println("Interval is <= 0!");
      return Double.NaN;
   }

    n = (x - Xmid)/interval;
      //System.out.println("Interpolate Interp: Interval = " + n*interval + "\n");
    y = Ymid + n/2.0*(A + B + n*C);
    return y;
 }

 /** The interpolation method.  Call this method with a value of x between the
  * X values defined in <CODE>defineInterpolation</CODE> above and a predefined
  * interpolating factor N.
  * @param x An double value between the X values defined in the X array supplied in
  * <CODE>defineInterpolation</CODE>
  * @param N The predefined interpolating factor.
  * @return An interpolated Y value corresponding to the the input X value.
  */ 
 public double interpolate(double x, double N){
   if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else if(interval <= 0.0){
      System.out.println("Interval is <= 0!, Exiting.");
      return Double.NaN;
   }
   n = N;
    //System.out.println("Interpolate Interp: Interval = " + n*interval + "\n");
   y = Ymid + n/2.0*(A + B + n*C);
   return y;
 }
 
 /** The getMaxPercentDiff method.  Call this method to see the maximum percent difference
  * of C compared to all input Y values.
  * 
  * @return The maximum percent (between 0 and 1) of the ABC of the third difference over all input Y values.
  */ 
 public double getMaxPercentDiff(){
   if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else{
       return maxDiff;
   }
 }
 
 /**
  * Returns the first difference (A) equal to y2 - y1
  * @return The value of y2 - y1
  */
 public double getFirstDiffA(){
     if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else{
       return A;
   }
 }
 
 /**
  * Returns the first difference (B) equal to y3 - y2
  * @return The value of y3 - y2
  */
 public double getFirstDiffB(){
     if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else{
       return B;
   }
 }
 
 /**
  * Returns the second difference (C) equal to B - A (y1 + y3 - 2*y2)
  * @return The value of B - A
  */
 public double getSecondDiffC(){
     if(!INTERPOLATION_DEFINED){
      System.out.println("Interpolation not defined yet!");
      return Double.NaN;
   }else{
       return C;
   }
 }
 

 /** An executable program used to test the interpolation algorithm.
  * Example program:<PRE>
  * T_Interpolate I = new T_Interpolate();
  * T_Vector X = new T_Vector(3);
  * T_Vector Y = new T_Vector(3);
  *
  * date.setJulianDate(11,7,1992,0,0,0);
  * X.setElement(0,date.getJulianDate());
  * Y.setElement(0,0.884226);
  *
  * date.setJulianDate(11,8,1992,0,0,0);
  * X.setElement(1,date.getJulianDate());
  * Y.setElement(1,0.877366);
  *
  * date.setJulianDate(11,9,1992,0,0,0);
  * X.setElement(2,date.getJulianDate());
  * Y.setElement(2,0.870531);
  *
  * I.defineInterpolation(X,Y);
  * // Be Sure the units you are interpolating between are the same
  * date.setJulianDate(11,8,1992,4,21,0);
  * System.out.println("Interpolation at 4:21 on the 8th is: ");
  * System.out.println(I.interpolate(date.getJulianDate()));
  * System.out.println(I.toString());
  * System.exit(0);</PRE>
  *
  * Output:
  * <pre>
  * Interpolation at 4:21 on the 8th is: 
  * 0.8761253012701685
  * Statistics of current interpolation:
  * Percent Difference = 0.0028273314740861226%
  * Differences:
  * A = -0.006859999999999977
  *  C = 2.5000000000052758E-5
  * B = -0.0068349999999999245</pre>
  * @param args String parameter, not used in this test application.
  */ 
  public static void main(String[] args) {
    T_Interpolate I = new T_Interpolate();
    // Test error conditions
    T_JulianDate date = new T_JulianDate();
    T_Vector X = new T_Vector(3);
    T_Vector Y = new T_Vector(3);

    date.setJulianDate(3,19,2002,0,0,0);
    X.setElement(0,date.getJulianDate());
    Y.setElement(0,0.884226);

    date.setJulianDate(3,20,2002,0,0,0);
    X.setElement(1,date.getJulianDate());
    Y.setElement(1,0.877366);

    date.setJulianDate(3,21,2002,0,0,0);
    X.setElement(2,date.getJulianDate());
    Y.setElement(2,0.870531);

    I.defineInterpolation(X,Y);
    // Be Sure the units you are interpolating between are the same
    date.setJulianDate(3,20,2002,6,0,0);
    System.out.println("Interpolation at 6:00 on the 20th is: ");
    System.out.println(I.interpolate(date.getJulianDate()));
    System.out.println(I.toString());
    System.out.println(I.getMaxPercentDiff());
    System.exit(0);

  }


}

/*








*/