package driver.ted.utilities;

/** <U>Title:</U> T_Site<br>
 * <U>Description:</U> This class maintains its coordinates in ECEF XYZ and spherical geodetic (lat, lon,
 * height).  Whenever a coordinate is set or changed, a conversion is made so that the coordinates
 * in both frames are always up to date.<br>
 * <U>Copyright:</U> (c) 2002 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */

 public class T_Site {

     /** X coordinate in the ECEF Frame.
      */
  private double X;

  /** Y coordinate in the ECEF Frame.
   */
  private double Y;

  /** Z coordinate in the ECEF Frame.
   */
  private double Z;

  /** The latitude coordinate in the geodetic frame.
   */
   private double latitude;

   /** The longitude coordinate in the geodetic frame.
    */
   private double longitude;

   /** The height coordinate in the geodetic frame.
    */
   private double height;

   /** Tolerance value for the iteration to determine the Lat, Lon and Height values.
    * ITERATION_TOLERANCE = 1e-3.
    */
   private static double ITERATION_TOLERANCE = 1e-3;

   /** Default Constructor, initializes X, Y, Z and
    * latitude, longitude and height to 0.0.
    */
  public T_Site() {
    latitude = longitude = height = 0.0;
    convertLatLonHeightToXYZ();
  }

  /** Constructor sets X, Y and Z.
   * Then calculates the geodetic latitude, longitude and height based
   * on these values.
   * @param initialX The initial X value
   * @param initialY The initial Y value
   * @param initialZ The initial Z value
   */
  public T_Site(double initialX, double initialY, double initialZ) {
    X = initialX;
    Y = initialY;
    Z = initialZ;
    // convert the ECEF coordinates to Geo
    convertXYZToLatLonHeight();
  }

// accessors

  /** Returns the current geodetic longitude value.
   * The value returned will be in the range <code>(0,2*PI]</code>.
   * @return A double representing the geodetic longitude in radians between <code>0</code> and <code>2*PI</code>.
   */
  public double getLongitude(){
    return longitude;
  }

  /** Returns the current geodetic latitude value.
   * The value returned will be in the range <CODE>(-PI/2, PI/2)</CODE>.
   * @return A double representing the geodetic latitude in radians between ,<code>-PI/2</code> and <code>PI/2</code>.
   */
  public double getLatitude(){
    return latitude;
  }

  /** Returns the current geodetic height value.
   * The value returned is the height in meters above mean sea level.
   * @return A double representing the geodetic height in meters above mean sea level.
   */
  public double getHeight(){
    return height;
  }

  /** Returns the current Earth-Centered-Earth-Fixed (ECEF) X coordinate.
   * The value returned is the X coordinate in meters as measured from the center of the Earh.
   * @return A double representing the ECEF X coordinate in meters.
   */
  public double getX(){
    return X;
  }

  /** Returns the current Earth-Centered-Earth-Fixed (ECEF) Y coordinate.
   * The value returned is the Y coordinate in meters as measured from the center of the Earh.
   * @return A double representing the ECEF Y coordinate in meters.
   */
  public double getY(){
    return Y;
  }

  /** Returns the current Earth-Centered-Earth-Fixed (ECEF) Z coordinate.
   * The value returned is the Z coordinate in meters as measured from the center of the Earh.
   * @return A double representing the ECEF Z coordinate in meters.
   */
  public double getZ(){
    return Z;
  }

// Mutators

  /** Sets the geodetic longitude value.
   * @param newLongitude The new longitude value.  The longitude value must be in radians.
   */
  public void setLongitude(double newLongitude){
    longitude = T_Utilities.normalize(0,2.0*Math.PI,newLongitude);
    convertLatLonHeightToXYZ();
  }

  /** Sets the geodetic latitude value.
   * @param newLatitude The new latitude value.  The latitude value must be in radians.
   */
  public void setLatitude(double newLatitude){
    latitude = T_Utilities.normalize(-Math.PI/2.0,Math.PI/2.0,newLatitude);
    convertLatLonHeightToXYZ();
  }

  /** Sets the geodetic height value.
   * @param newHeight The new height value.  The height value must be in meters and must be larger than -T_Utilities.EarthEquatorialRadius.
   * If the height is less than this value it will be set to -T_Utilities.EarthEquatorialRadius.
   */
  public void setHeight(double newHeight){
    if(newHeight >= -T_Utilities.EarthEquatorialRadius){
      height = newHeight;
      convertLatLonHeightToXYZ();
    }else{
      javax.swing.JOptionPane.showMessageDialog(null,
                          "The height value must be " + (-T_Utilities.EarthEquatorialRadius) +
                           " or greater, height set to " + (-T_Utilities.EarthEquatorialRadius),
                          "Error in setting height in T_Site",
                          javax.swing.JOptionPane.ERROR_MESSAGE);
      height = -T_Utilities.EarthEquatorialRadius;
      convertLatLonHeightToXYZ();
    }
  }


  /** Sets the ECEF X coordinate.
   * @param newX The new X coordinate value.  The X value must be in meters.
   */
  public void setX(double newX){
    X = newX;
    convertXYZToLatLonHeight();
  }

  /** Sets the ECEF Y coordinate.
   * @param newY The new Y coordinate value.  The Y value must be in meters.
   */
  public void setY(double newY){
   Y = newY;
   convertXYZToLatLonHeight();
  }

  /** Sets the ECEF Z coordinate.
   * @param newZ The new Z coordinate value.  The Z value must be in meters.
   */
  public void setZ(double newZ){
   Z = newZ;
   convertXYZToLatLonHeight();
}

  /** Sets the geodetic latitude, longitude and height values.
   * @param newLatitude The new latitude value.  The latitude value must be in radians.
   * @param newLongitude The new longitude value.  The longitude value must be in radians.
   * @param newHeight The new height value.  The height value must be in meters and must be larger than -T_Utilities.EarthEquatorialRadius.
   * If the height is less than this value it will be set to -T_Utilities.EarthEquatorialRadius.
   */
  public void setLatLonHeight(double newLatitude, double newLongitude, double newHeight){
    latitude = T_Utilities.normalize(-Math.PI/2.0,Math.PI/2.0,newLatitude);
    longitude = T_Utilities.normalize(0,2.0*Math.PI,newLongitude);
    if(newHeight >= -T_Utilities.EarthEquatorialRadius){
      height = newHeight;
    }else{
      javax.swing.JOptionPane.showMessageDialog(null,
                          "The height value must be " + (-T_Utilities.EarthEquatorialRadius) +
                           " or greater, height set to " + (-T_Utilities.EarthEquatorialRadius),
                          "Error in setting height in T_Site",
                          javax.swing.JOptionPane.ERROR_MESSAGE);
      height = -T_Utilities.EarthEquatorialRadius;
    }
    convertLatLonHeightToXYZ();
  }

  /** Sets the ECEF X, Y and Z coordinates.
   * @param newX The new X coordinate value.  The X value must be in meters and may not be negative.
   *                 If X is negative, it will be set to zero (0).
   * @param newY The new Y coordinate value.  The Y value must be in meters and may not be negative.
   *                 If Y is negative, it will be set to zero (0).
   * @param newZ The new Z coordinate value.  The Z value must be in meters and may not be negative.
   *                 If Z is negative, it will be set to zero (0).
   */
  public void setXYZ(double newX, double newY, double newZ){
     X = newX;
     Y = newY;
     Z = newZ;
     convertXYZToLatLonHeight();
  }
  
  /**
   * Returns a listing of the sites coordinates in ECEF and Geocentric formats.
   * @return String representation of sites coordinates
   */
  public String toString(){
    String temp = "Earth Centered-Earth Fixed Coordinates: [X (meters),Y (meters),Z (meters)] = \n[";
    temp += String.valueOf(getX()) + ", ";
    temp += String.valueOf(getY()) + ", ";
    temp += String.valueOf(getZ()) + "]\n";
    temp += "geodetic Coordinates: [Latitude (radians), Longitude (radians), Height (meters)] = \n[";
    temp += String.valueOf(getLatitude()) + ", ";
    temp += String.valueOf(getLongitude()) + ", ";
    temp += String.valueOf(getHeight()) + "]\n";
    return temp;
  }

  /** Converts the XYZ coordinates to geodetic Lat, Lon, Height
   * This algorithm is based on the algorithm in the Goddard Trajectory Determination
   * System.
   */
  private void convertXYZToLatLonHeight(){
    double Zi, Zib, Ziprev, N_Plus_h, sinPhi, N;
     double esquared = T_Utilities.EarthEccentricity*T_Utilities.EarthEccentricity;

    Zi = -esquared*Z;

    do{
      Ziprev = Zi;
      Zib = Z - Zi;
      N_Plus_h = Math.sqrt(X*X + Y*Y + Zib*Zib);
      sinPhi = Zib/N_Plus_h;
      N = T_Utilities.EarthEquatorialRadius/Math.sqrt(1.0-esquared*sinPhi*sinPhi);
      Zi = -N*esquared*sinPhi;
    }while( (Math.abs(Zi) - Math.abs(Ziprev)) > ITERATION_TOLERANCE);

    longitude = Math.atan2(Y, X);
    latitude = Math.asin(sinPhi);
    height = N_Plus_h - N;
}
  /** Converts the Lat, Lon and Height value to ECEF X, Y and Z.
   * This algorithm is based on the algorithm in the Goddard Trajectory Determination
   * System.
   */
  private void convertLatLonHeightToXYZ(){
  	double N;
        double esquared = T_Utilities.EarthEccentricity*T_Utilities.EarthEccentricity;

	N = T_Utilities.EarthEquatorialRadius;
	N /= Math.sqrt(1.0 - esquared*Math.sin(latitude)*Math.sin(latitude));

	X = (N + height)*Math.cos(latitude)*Math.cos(longitude);
	Y = (N + height)*Math.cos(latitude)*Math.sin(longitude);
	Z = (N*(1-esquared) + height)*Math.sin(latitude);
}

/** Executable program to test the T_Site functionality.
 * @param args Argument string.  Not needed for this function
 */
 public static void main(String[] args) {
  T_Site site1 = new T_Site(T_Utilities.EarthEquatorialRadius,T_Utilities.EarthEquatorialRadius,0.0);
  System.out.println("Initializing with X and Y = T_Utilities.EarthEquatorialRadius (= " + T_Utilities.EarthEquatorialRadius + ") and Z = 0.0");
  System.out.println("Latitude (degrees) = " + site1.getLatitude()/T_Utilities.DtR + ", Longitude (degrees) = " + site1.getLongitude()/T_Utilities.DtR + ", Height (meters AMSL) = " + site1.getHeight());
  System.out.println("toString(): \n" + site1.toString());
  T_Site site2 = new T_Site();
  System.out.println("Site 2: " + site2.toString());
  }

}