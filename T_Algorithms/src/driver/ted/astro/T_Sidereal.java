package driver.ted.astro;

import driver.ted.utilities.*;
import java.io.Serializable;

/**
 * Title:        T_Sidereal
 * Description:  This class will be used to calculate and return sidereal times
 * Copyright:    (c) 2001 - 2006
 * @author Ted Driver
 * @version 1.1
 *
 */

public class T_Sidereal implements Serializable{

  private double Thzero, Thzerocorr;    // The sideral Time at Greenwich and the correction for apparent ST
  private double dpsi, eps; // the nutation parameters

  /**
   * Constructor
   */
  public T_Sidereal() {
    Thzero = Thzerocorr = 0.0;
    dpsi = eps = 0.0;
  }

  // returns the Sideral Time at Greenwich in degrees
  /**
   * Returns the Sideral Time at Greenwich in degrees
   * @param JD The {@link T_JulianDate} object defining the time for which the sidereal time is desired
   * @return Sidereal time in degrees
   */
  public double getSiderealTime(T_JulianDate JD){
    double T;

    T = (JD.getJulianDate()-2451545.0)/36525.0;

    // from equation 11.4 in Astronomical Algorithms
    Thzero = 280.46061837 + 360.98564736629*(JD.getJulianDate() - 2451545.0)
           + 0.000387933*T*T - T*T*T/38710000.0;

    return T_Utilities.normalize(0,360,Thzero);
  }

  // returns the apparent Sidereal Time in degrees
  /**
   * The apparent sidereal time in degrees at the specified julian date.
   * @param JD {@link T_JulianDate} object defining the julian date for which the sidereal time is desired
   * @return Apparent sidereal time in degrees
   */
  public double getApparentSiderealTime(T_JulianDate JD){
    double T, correction, Th;
    T_Nutate N = new T_Nutate();

    dpsi = N.get_dpsi(JD)/T_Utilities.AsToRad; // convert to arcseconds
    eps = N.get_eps(JD);

    correction = (dpsi*Math.cos(eps));
    correction *= T_Utilities.AsToRad/T_Utilities.DtR; // convert back to radians then to degrees

    T = (JD.getJulianDate()-2451545.0)/36525.0;

    // from equation 11.4 in Astronomical Algorithms
    Th = 280.46061837 + 360.98564736629*(JD.getJulianDate() - 2451545.0)
       + 0.000387933*T*T - T*T*T/38710000.0;

    Thzerocorr = Th + correction;
    return T_Utilities.normalize(0,360,Thzerocorr);
  }

  /**
   * Test program
   * @param args args
   */
  public static void main(String[] args) {
    T_Sidereal S = new T_Sidereal();

    // the following JD corresponds to Example 11.b on page 84 of Meeus' Astronomical Algorithms

    T_JulianDate J = new T_JulianDate(2446896.30625);
    System.out.println("Example 11.b: For J = " + J.getJulianDate());
    System.out.println("S.getSiderealTime(J) = " + S.getSiderealTime(J));
    System.out.println(T_Utilities.decimalToDMS(S.getSiderealTime(J)/15.0,4));
    System.out.println("Above answer should be 8 34 57.0896");

    J.setJulianDate(2446895.5);

    System.out.println("\nExample 11.a: For J = " + J.getJulianDate());
    System.out.println("S.getSiderealTime(J) = " + S.getSiderealTime(J));
    System.out.println("S.getApparentSiderealTime(J) = " + S.getApparentSiderealTime(J));
    System.out.println(T_Utilities.decimalToDMS(S.getApparentSiderealTime(J)/15.0,4));
    System.out.println("Above answer should be 13 10 46.1351");
    System.exit(0);
  }
}
