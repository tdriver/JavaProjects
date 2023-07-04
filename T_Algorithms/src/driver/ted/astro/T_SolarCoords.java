package driver.ted.astro;

import driver.ted.utilities.*;
import java.io.Serializable;

/**
 * Title:        T_SolarCoords
 * Description:  This class implements the T_SolarCoords class which returns coordinates for the sun
 * Copyright:    (c) 2001 - 2006
 * @author Ted Driver
 * @version 1.1
 *
 */

public class T_SolarCoords implements Serializable{

  //T_Vector R = new T_Vector(3); // solar rectangular coordinates
  // alphal is the low precision Right Ascension Coordinate
  private double alphal;
  // deltal is the low precision declination coordinate
  private double deltal;
  // alphah is the high precision Right Ascension
  // private double alphah;
  //deltah is the high precision declination coordinate
  // private double deltah;

  // JD is the time to evaluate the coordinates at, defaults to current system time
  private T_JulianDate JD = new T_JulianDate();


  /**
   * Default Constructor, calculates the coordinates with the current system time.
   */
  public T_SolarCoords() {
    calcSolarCoords();
  }

  /**
   * Constructor: creates object with time defined by {@link T_JulianDate} object
   * @param J {@link T_JulianDate} object defining the time
   */
  public T_SolarCoords(T_JulianDate J){
    JD = J;
    calcSolarCoords();
  }

  /**
   * Set the time for which coordinates will be calculated
   * @param J {@link T_JulianDate} object defining the time
   */  
  public void setDateTime(T_JulianDate J){
    JD = J;
    calcSolarCoords();
  }

  /**
   * Low precision Right Ascension Coordinate. Accurate to 0.01 degrees.
   * @return Right Ascension in degrees (0-360)
   */
  public double getAlpha_low(){
    return alphal;
  }

  /**
   * Low precision Declination Coordinate. Accurate to 0.01 degrees
   * @return Declination in degrees (-180 to 180)
   */
  public double getDelta_low(){
    return deltal;
  }

  private void calcSolarCoords(){

	double T, Lzero, M, e, C, Th, v, R, Om, lam, Th2;
	double alpha, delta, eps, epscorr;

	T_Date D = new T_Date();

	// JD is set is the call for each coordinate
	D = JD.getDate();

	T = (JD.getJulianDate() - 2451545.0)/36525.0;
	//System.out.println("T = " + T + "\n");

	Lzero = 280.46645 + 36000.76983*T + 0.0003032*T*T;
	//System.out.println("L0 = " + Lzero + "\n");
	Lzero = T_Utilities.normalize(0,360,Lzero);
	//System.out.println("L0 normed = " + Lzero + "\n");

	M = 357.52910 + 35999.05030*T - 0.0001559*T*T - 0.00000048*T*T*T;
	//System.out.println("M = " + M + "\n");
	M = T_Utilities.normalize(0,360,M);
	//System.out.println( "Normed M = " + M + "\n");


	e = 0.016708617 - 0.000042037*T - 0.0000001236*T*T;
	//System.out.println("e = " + e + "\n");

	C = (1.914600 - 0.004817*T - 0.000014*T*T)*Math.sin(M*T_Utilities.DtR)
	+ (0.019993 - 0.000101*T)*Math.sin(2*M*T_Utilities.DtR)
	+ 0.000290*Math.sin(3*M*T_Utilities.DtR);
	//System.out.println("C = " + C + "\n");

	Th = Lzero + C;
	//System.out.println("Th = " + Th + "\n");

	v = M + C;
	//System.out.println("v = " + v + "\n");

	R = 1.000001018*(1-e*e)/(1.0 + e*Math.cos(v*T_Utilities.DtR));
	//System.out.println("R = " + R + "\n");

	Om = 125.04 - 1934.136*T;
	//System.out.println("Om = " + Om + "\n");

	lam = Th - 0.00569 - 0.00478*Math.sin(Om*T_Utilities.DtR);
	//System.out.println("lam = " + lam + "\n");

	Th2 = Th - 0.01397*((double)D.getYear() - 2000.0);

	eps = 84381.448 - 46.8150*T - 0.00059*T*T + 0.001813*T*T*T;
	epscorr = 0.00256*Math.cos(Om*T_Utilities.DtR);
	eps *= T_Utilities.AsToRad/T_Utilities.DtR;
	//System.out.println(" Uncorrected eps = " + eps + "\n");
	eps += epscorr;
	//System.out.println("eps = " + eps + "\n");

	alpha = Math.atan2(Math.cos(eps*T_Utilities.DtR)*Math.sin(lam*T_Utilities.DtR),Math.cos(lam*T_Utilities.DtR));
	delta = Math.asin(Math.sin(eps*T_Utilities.DtR)*Math.sin(lam*T_Utilities.DtR));
	alphal = T_Utilities.normalize(0,360,alpha/T_Utilities.DtR);
	deltal = T_Utilities.normalize(-180,180,delta/T_Utilities.DtR);
	//System.out.println("normed alpha = " + alpha/T_Utilities.DtR + "\n");
	//System.out.println("normed delta = " + delta/T_Utilities.DtR + "\n");
  }

   /**
    * Test program
    * @param args args
    */
   public static void main( String args[] )
   {
    // This is the test case to run to debug the algorithm.
    // Algorithm verified on December 18, 2001 by Ted Driver
    // JD is from Jean Meeus Example on Page 153 of the Astronomical Algorithms book
    // uncomment the output lines in the calc function above and run T_Solar from the command line
    // java driver.ted.astro.T_Solar
     T_SolarCoords S = new T_SolarCoords(new T_JulianDate(2448908.5));
     System.exit(0);
   }





/*  public double getAlpha_high(){
    return Double.MAX_VALUE;
  }

  public double getDelta_high(){
    return Double.MAX_VALUE;
  }

  public T_Vector getSolarCoords(){
    return new T_Vector();
  }
*/



}
