package driver.ted.utilities;

/**
 * Title:        T_Coordinates
 * Description:  Provides Coordinate Conversion utilities
 * Copyright:    (c) 2001 - 2006
 * Company:
 * @author Ted Driver
 * @version 1.1
 * Changes:
 *2/17/2004
 *Updated to add EquatorialToLocalHorizontal conversion
 *
 */

import java.text.DecimalFormat;
import java.util.*;
import driver.ted.astro.T_Sidereal;


/**
 * Static class to provide coordinate conversion utilities.
 */
public class T_Coordinates {
  // Common Variables
  static double R_Earth; // Earth Equatorial radius
  static double flattening; // Earths flattening factor
  // Coordinates of the Air Force GPS Monitor Stations
//  List haw = new ArrayList(3);
//  List kwaj = new ArrayList(3);
//  List diego = new ArrayList(3);
//  List ansc = new ArrayList(3);
//  List cosp = new ArrayList(3);
//  List cape = new ArrayList(3);

  /**
   * Default Constructor
   */
  public T_Coordinates() {
   R_Earth = 6378137.0;
   flattening = 3.352810664747e-3;
//   haw.add(0, new Double(-0.5511982282e7));
//   haw.add(1,new Double(-0.2200248096e7));
//   haw.add(2,new Double(0.2329481654e7));
//   kwaj.add(0,new Double(-0.6160884561e7));
//   kwaj.add(1,new Double(0.1339851686e7));
//   kwaj.add(2,new Double(0.0960842977e7));
//   diego.add(0,new Double(0.1916197323e7));
//   diego.add(1,new Double(0.6029998996e7));
//   diego.add(2,new Double(-0.0801737517e7));
//   ansc.add(0,new Double(0.6118524204e7));
//   ansc.add(1,new Double(-0.1572350829e7));
//   ansc.add(2,new Double(-0.0876464089e7));
//   cosp.add(0,new Double(-0.1248567221e7));
//   cosp.add(1,new Double(-0.4819433246e7));
//   cosp.add(2,new Double(0.3976500193e7));
//   cape.add(0,new Double(0.0918957e7));
//   cape.add(1,new Double(-0.553453e7));
//   cape.add(2,new Double(0.302376e7));
  }

  /**
   * Converts ECEF coordinates to Geocentric coordinates
   * Conversion from Earth-Centered, Earth Fixed coordinates (x, y, z) to
   * Geocentric coordinates (Latitude, Longitude, Height) with units {rad, rad, meters}
   * @param ECEF_Coords The List of X,Y,Z values to convert to Geocentric coordinates
   * @return LatLong double array
   * <PRE>LatLong[0] = Latitude in radians</PRE>
   * <PRE>LatLong[1] = Longitude in radians</PRE>
   * <PRE>LatLong[2] = Altitude in meters</PRE>
   */

  public static double[] ECEF_To_Geo(double[] ECEF_Coords){
    double a, b, e, ep, p, theta, N, latlon[];
    double R[] = new double[3];
    double latlong[] = new double[3];
    R[0] = ECEF_Coords[0]; // X
    R[1] = ECEF_Coords[1]; // Y
    R[2] = ECEF_Coords[2]; // Z

    a = R_Earth;
    b = a*(1.0 - flattening);
    e = (2.0 - flattening)*flattening;  // 2f - f^2
    ep = e/(1.0-e);

    p = Math.sqrt(R[0]*R[0] + R[1]*R[1]);
    theta = Math.atan(R[2]*a/(p*b));

    latlong[0] = p - e*a*Math.pow(Math.cos(theta),3);
    latlong[0] = (R[2] + ep*b*Math.pow(Math.sin(theta),3))/latlong[0];
    latlong[0] = Math.atan(latlong[0]);

    latlong[1] = Math.atan2(R[1],R[0]);
    N = a*a/Math.sqrt(Math.pow(a*Math.cos(latlong[0]),2) + Math.pow(a*Math.sin(latlong[0]),2));
    latlong[2] = p/Math.cos(latlong[0]) - N;

    return latlong;
  }
  /**
   * Converts Right Ascension and Declination values to local Azimuth, Elevation
   * Equatorial (RA, Dec) with units (deg, deg) are converted to
   * Local horizontal coordinates (Azimuth, Elevation) with units {deg, deg}
   * @param RA The Right Ascension coordinate to convert
   * @param Dec The Declination to convert
   * @param Lat The Latitude of the location the observer is at
   * @param Long The Longitude of the location the observer is at (reckoned negative WEST)
   * @param t The {@link T_JulianDate} object representing the time for the conversion
   * @return AzEl double array.  <PRE>AzEl[0] = Azimuth in degrees reckoned clockwise from north.</PRE>  
   * <PRE>AzEl[1] = Elevation in degrees.</PRE>
   */
  public static double[] EquatorialToLocalHorizontal(double RA, double Dec, double Lat, double Long, T_JulianDate t){
      T_Sidereal S = new T_Sidereal();
      double AzEl[] = new double[2];
     
      // calculate the apparent sidereal time at instant t (my sidereal time class already does this.
      double thzero = S.getApparentSiderealTime(t);
      double H = thzero + Long - RA;
      // normalize H to between 0 and 360
      double Hnorm = T_Utilities.normalize(0,360,H);
      // convert Hnorm to radians
      double HnormR = Hnorm*T_Utilities.DtR;
      
      AzEl[0] = Math.atan2(Math.sin(HnormR),(Math.cos(HnormR)*Math.sin(Lat*T_Utilities.DtR) - Math.tan(Dec*T_Utilities.DtR)*Math.cos(Lat*T_Utilities.DtR)));
      AzEl[1] = Math.sin(Lat*T_Utilities.DtR)*Math.sin(Dec*T_Utilities.DtR) + Math.cos(Lat*T_Utilities.DtR)*Math.cos(Dec*T_Utilities.DtR)*Math.cos(HnormR);
      
      // Convert to Degrees and reckon Azimuth from the north
      AzEl[0] = AzEl[0]/T_Utilities.DtR + 180.0;
      AzEl[1] = AzEl[1]/T_Utilities.DtR;
      
      return AzEl;
 }

 
  /**
   * Test program for T_Coordinates
   * @param args Arguments
   */
  public static void main(String[] args) {
      T_JulianDate t = new T_JulianDate(4,10, 1987, 19,21,00);
      T_Coordinates c = new T_Coordinates(); 
      double Lat = 38.92138889;
      double Long = -77.06555555;
      double RA = 347.3193375;
      double Dec = -6.719891667;
      double[] newc = c.EquatorialToLocalHorizontal(RA,Dec,Lat,Long, t);
      System.out.println("Az = " + newc[0] + " El = " + newc[1] );
      
      
  }
}