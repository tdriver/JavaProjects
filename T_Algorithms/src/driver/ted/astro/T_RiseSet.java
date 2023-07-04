package driver.ted.astro;
import driver.ted.utilities.*;
import java.text.DecimalFormat;
import java.io.Serializable;

/**
 * Title:        T_Utilities
 * Description:  This class will be used to create and maintain the utility classes in the utility package driver.ted.utilities
 * Copyright:    (c) 2001 - 2006
 * @author Ted Driver
 * @version 1.2

 * No Changes to this class
 * Change from Version 1.1
 * Added an interpolation boundary check
 * The interpolation routine was trying to interpolate over 359 - 0 degrees around
 * March 20 (when the Sun crosses the equator.
 * This caused the max number of iterations to be exceeded.
 *
 * Febuary 6, 2004 Changed the dt computation to make it just the number of leap seconds currently output
 * by USNO (22) plus 32.184 (the number of leap seconds at the start of international Atomic time in 1958
 *
 */

public class T_RiseSet implements Serializable{

  /**
   * Solar hzero value. actually this is 34+16 minutes of arc; 34 for standard refraction and 16
   * for the solar radius 34+16=50 = 0.8333.  minus because it is still below
   * the horizon when it appears to be rising due to refraction.
   */
  static final  double Sun_hzero = -0.8333;

  /**
   * Planetary hzero value, takes into account refraction only
   */
  static final double Planet_hzero = -0.5667;

  
  /**
   * tolerance value used in deltam calculation
   */
  static final double tolerance = 1e-5;

  /**
   * Sun identifier
   */
  public static final int SUN = 1;
  /**
   * Planet identifier
   */
  public static final int PLANET = 2;
  /**
   * Moon identifier
   */
  public static final int MOON = 3;


  private String RiseTime;
  private String SetTime;
  private String TransitTime;
  private String AmountOfDaylight;
  private double RiseTimeDouble, SetTimeDouble, TransitTimeDouble, AmountOfDaylightDouble;
  private double  RiseAzimuth, TransitAzimuth, SetAzimuth, RiseElevation, TransitElevation, SetElevation;
  private double lat, lon, offset;
  private T_JulianDate JD;
  private int calcType;
  private boolean DATA_FRESH;
  private boolean DEBUG;

  /**
   * Default constructor to calculate rise and set times for provided date and location
   * The latitude must be in degrees.decimal format.
   * The longitude must be in degrees.decimal measured negatively West of Greenwich (-180.0 to 180.0)
   * The time offset must be in hours.decimal (-7.5 for example) and negative west of Greenwich.
   * The JulianDate must contain the date for which to calculate the data
   * The bodyType parameter must be either T_RiseSet.SUN or T_RiseSet.PLANET or T_RiseSet.MOON
   * @param J {@link T_JulianDate} object defining the time
   * @param latitude Latitude in degrees.decimal
   * @param longitude Longitude in degrees.decimal
   * @param timeOffset Time offset for the reported times
   * @param bodyType Use the defined types in this class
   */
  public T_RiseSet(T_JulianDate J,
                    double latitude,
                    double longitude,
                    float timeOffset,
                    int bodyType
                    ) {
    JD = new T_JulianDate(J.getJulianDate());
    lat = latitude;
    lon = -longitude; // correct for different longitude measurement
    offset = timeOffset;
    AmountOfDaylight = "";
    if (bodyType == PLANET) System.out.println("Planet Data not available yet.");
    calcType = bodyType;
    DATA_FRESH = false;
    DEBUG = false;
    CalculateRiseSet();
  }

  /**
   * Debug constructor to calculate rise and set times for a given time and place
   *
   */
  public T_RiseSet() {
    JD = new T_JulianDate(3,20,2002,0,0,0);
    lat = 42.3333;
    lon = 71.0833;
    offset = 0;
    AmountOfDaylight = "";
//    calcType = PLANET;
    calcType = SUN;
    DEBUG = true;
    CalculateRiseSet();

  }


  public void setLatitude(double latitude){
    lat = latitude;
    DATA_FRESH = false;
  }

  public void setLongitude(double longitude){
    lon = longitude;
    DATA_FRESH = false;
  }

  public void setTimeOffset(float timeOffset){
    offset = timeOffset;
    DATA_FRESH = false;
  }

  public void setbodyType(int bodyType){
    if (bodyType == PLANET) System.out.println("Planet Data not currently available.");
    else{
      calcType = bodyType;
      DATA_FRESH = false;
    }
  }

  public double getLatitude(){
    DecimalFormat df = new DecimalFormat("0.0000");
    return Double.parseDouble(df.format(lat));
  }


  public double getLongitude(){
    DecimalFormat df = new DecimalFormat("000.0000");
    // minus sign because I measure lon negative west of Greenwich
    return Double.parseDouble(df.format(-lon));
  }


  public float getOffset(){
    DecimalFormat df = new DecimalFormat("0.0");
    return Float.parseFloat(df.format(offset));
  }


  /**
   * public function to get the rise time
   */
   public String getRiseTime(){
      if(!DATA_FRESH){
       CalculateRiseSet();
      }
      return RiseTime;
   }

   public double getRiseTimeDouble(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return RiseTimeDouble;
   }
   
   public double getRiseElevation(){

    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return RiseElevation;
   }

   public double getRiseAzimuth(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
     return RiseAzimuth;
   }

   public String getTransitTime(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return TransitTime;
   }
   
   public double getTransitTimeDouble(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return TransitTimeDouble;
   }

    public double getTransitElevation(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return TransitElevation;
   }

    public double getTransitAzimuth(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return TransitAzimuth;
   }

   public String getSetTime(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return SetTime;
   }
   
   public double getSetTimeDouble(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return SetTimeDouble;
   }

   public double getSetElevation(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return SetElevation;
   }

   public double getSetAzimuth(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return SetAzimuth;
   }

   public int getBodyType(){
    return calcType;
   }

   public String getAmountOfDaylight(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return AmountOfDaylight;
   }
   
   public double getAmountOfDaylightDouble(){
    if (!DATA_FRESH){
     CalculateRiseSet();
    }
    return AmountOfDaylightDouble;
   }


   private void CalculateRiseSet(){
        T_SolarCoords S[] = new T_SolarCoords[3];
        T_LunarCoords M[] = new T_LunarCoords[3];
        T_Sidereal STime = new T_Sidereal();
        T_Interpolate InterpAlpha = new T_Interpolate();
        T_Interpolate InterpDelta = new T_Interpolate();
        T_Time T = new T_Time();
        T_Date D = new T_Date();
        String temp = "";
        double hzero, cosHzero, H, Hzero, h[], phi, L, DtR;
        double dT, n[], Theta[], Thzero, a, d, local_offset, A[], m[], deltam[];
        T_Vector j, alpha, delta;
        int i;
        int counter = 1;

        j = new T_Vector(3);
        alpha = new T_Vector(3);
        delta = new T_Vector(3);

        m = new double[3];
        deltam = new double[3];
        h = new double[3];
        n = new double[3];
        Theta = new double[3];
        A = new double[3];
        DtR = T_Utilities.DtR;

        // use the variable names Meeus uses
        L = lon;
        phi = lat;
        local_offset = offset;


        // find the body type specified and select the corresponding hzero value
        try{
          if(calcType == SUN) hzero = Sun_hzero;
          else if(calcType == PLANET) hzero = Planet_hzero;
          else if (calcType == MOON) hzero = 0.0; // calculate the moon's hzero below, if needed
          else throw new NumberFormatException();
        }catch(NumberFormatException nfe){
          System.out.println("Incorrect body type defined in T_RiseSet!");
          return;
        }

        // Be sure the JD is at 0 UT, rounding if necessary
        if (JD.getJulianDate() - Math.floor(JD.getJulianDate())  < 0.5){
           JD.setJulianDate(JD.getJulianDate() - 1.0);
        }
        JD.setJulianDate(Math.floor(JD.getJulianDate()) + 0.5);
        if(DEBUG){
         System.out.println("JD = " + JD.getJulianDate());
        }

        // set dT
        // approximate formula from Meeus Chapter 9
        //dT = -15.0 + Math.pow(JD.getJulianDate() - 2382148,2)/41048480.0;
        
        //formula derived from linear regression of Table 9A on page 72 of Meeus Astronomical Algorithms
        // regression is done for dT values from 1904 to 1992
        // residuals will be about 5-10 seconds for the last century
        // Meeus formula provides results that are too large
        //dT = 0.5273*(double)JD.getDate().getYear() - 996.26;
        //System.out.println("DT = " + dT);
        // Feb 6, 2004 Change
        dT = 54.184; // equals 22 + 32.184
        if(DEBUG){
          dT = 56.0;
        }

        // sets the x vector for interpolation
        j.setElement(1,JD.getJulianDate() + dT/86400.0);
        j.setElement(0,j.getElement(1) - 1.0);
        j.setElement(2,j.getElement(1) + 1.0);

        // get solar coordinates
        if(calcType == SUN){
          for (i = 0; i <= 2; i++){
           S[i] = new T_SolarCoords(new T_JulianDate(j.getElement(i)));
           alpha.setElement(i,S[i].getAlpha_low());
           delta.setElement(i,S[i].getDelta_low());
          }
        }else if(calcType == MOON){
          for (i = 0; i <= 2; i++){
           M[i] = new T_LunarCoords(new T_JulianDate(j.getElement(i)));
           if(i == 1){
               // calculate the hzero value
               hzero = 0.7275*M[i].getEquatorialParallax()/DtR - 0.5666666666667; // degrees
           }
           alpha.setElement(i,M[i].getAlpha());
           delta.setElement(i,M[i].getDelta());
          }
        }
        //Define the interpolations and check for boundary crossings
        InterpAlpha.defineInterpolation(j,alpha);

        // Check Interpolation max diffs for alpha
        if(InterpAlpha.getMaxPercentDiff() > 10){
            
            // One or more alphas out of range
            if(InterpAlpha.getSecondDiffC() > 0){ // positive means both 1 and 2 elements need to be modified
                /**
                 * Y Elements x, y, z, differences: A = y-x, B = z-y, C = B-A
                 * so C = z-2y+x
                 * C will be positive (and large) if just x is much different than y and z 
                 * need to update y and z in this case
                 * C will be negative (and large) if both x and y are much different than z 
                 * need to update juct z in this case
                 */
                // reset the alpha vector
                alpha.setElement(1, alpha.getElement(1) + 360.0);
                alpha.setElement(2, alpha.getElement(2) + 360.0);
                //re-define the interpolation with new coordinates
                InterpAlpha.defineInterpolation(j,alpha);
            }else{ //InterpAlpha.getSecondDiffC() < 0 Only element 2 needs to be modified
                alpha.setElement(2, alpha.getElement(2) + 360.0);
                InterpAlpha.defineInterpolation(j,alpha);
            }
        }

        // Check Interpolation max diffs for delta
        InterpDelta.defineInterpolation(j,delta);
        if(InterpDelta.getMaxPercentDiff() > 10){
            // One or more alphas out of range
            if(InterpDelta.getSecondDiffC() > 0){ // positive means both 1 and 2 elements need to be modified
                delta.setElement(1, delta.getElement(1) + 360.0);
                delta.setElement(2, delta.getElement(2) + 360.0);
                InterpDelta.defineInterpolation(j,delta);
            }else{ // InterpDelta.getSecondDiffC() < 0 Only element 2 needs to be modified
                delta.setElement(2, delta.getElement(2) + 360.0);
                InterpDelta.defineInterpolation(j,delta);
            }
        }
        // get the sidereal time
        Thzero = STime.getApparentSiderealTime(JD);
//        if(DEBUG){
//          System.out.println("Thzero = "+ Thzero);
//          Thzero = 177.74208;
//        }

        // calculate the cosine of Hzero
        cosHzero = (Math.sin(hzero*DtR) - Math.sin(phi*DtR)*
                  Math.sin(delta.getElement(1)*DtR))/(Math.cos(phi*DtR)*Math.cos(delta.getElement(1)*DtR));
//        if(DEBUG){
//          System.out.println("cosHzero = " + cosHzero);
//        }

        if (Math.abs(cosHzero) > 1.0){
          System.out.println("Warning! cos(Ho) is greater than 1 in T_RiseSet!");
          return;
        }

        Hzero = Math.acos(cosHzero)/DtR; // needed in degrees
//        if(DEBUG){
//          System.out.println("HZero = " + Hzero);
//        }

        m[0] = (alpha.getElement(1) + L - Thzero)/360.0;
        m[1] = m[0] - Hzero/360.0;
        m[2] = m[0] + Hzero/360.0;
//        if(DEBUG){
//         System.out.println("m[0] = " + m[0]);
//         System.out.println("m[1] = " + m[1]);
//         System.out.println("m[2] = " + m[2]);
//        }


        for (i = 0; i <= 2; i++){
          m[i] = T_Utilities.normalize(0,1,m[i]);
        }
//        if(DEBUG){
//         System.out.println("normed m[0] = " + m[0]);
//         System.out.println("normed m[1] = " + m[1]);
//         System.out.println("normed m[2] = " + m[2]);
//        }

        do{ // until deltam's are small

          for (i = 0; i <= 2; i++){ // for each m

            Theta[i] = T_Utilities.normalize(0,360,Thzero + 360.985647*m[i]);
            if(DEBUG){
              System.out.println("Theta[" +i+ "] = " + Theta[i]);
            }

            n[i] = m[i] + dT/86400.0;
            if(DEBUG){
              System.out.println("n[" +i+ "] = " + n[i]);
            }

            // Interpolate over n
            a = InterpAlpha.interpolate(0,n[i]);

            // don't need this for transits (i = 0) but am leaving it in so d will be defined
            d = InterpDelta.interpolate(0,n[i]);

            H = T_Utilities.normalize(-180,180,Theta[i] - L - a);
//            if(DEBUG){
//              System.out.println("H = " + H);
//            }

            // don't need this for transits but calculate the altitude and azimuth for output
            h[i] = Math.asin(Math.sin(phi*DtR)*Math.sin(d*DtR) +
                 Math.cos(phi*DtR)*Math.cos(d*DtR)*Math.cos(H*DtR));
            h[i] /= DtR; // convert to degrees
            if(DEBUG){
              System.out.println("h[" +i+ "] = " + h[i]);
            }

            A[i] = Math.atan2(Math.sin(H*DtR),
                      (Math.cos(H*DtR)*Math.sin(phi*DtR) - Math.tan(d*DtR)*Math.cos(phi*DtR)));
            A[i] /= DtR;
            A[i] = T_Utilities.normalize(0,360,A[i]+180);
//            if(DEBUG){
//              System.out.println("A[" +i+ "] = " + A[i]);
//            }

            if (i == 0){ // for transits only
              deltam[0] = -H/360.0;
            }else { // otherwise
              deltam[i] = (h[i] - hzero)/(360*Math.cos(d*DtR)*Math.cos(phi*DtR)*Math.sin(H*DtR));
            }
            if(DEBUG){
              System.out.println("deltam[" +i+ "] = " + deltam[i]);
            }

            m[i] += deltam[i];
            if(DEBUG){
              System.out.println("rawm[" +i+ "] = " + m[i]);
            }

            m[i] = T_Utilities.normalize(0,1,m[i]);
//            if(DEBUG){
//              System.out.println("corrected m[" +i+ "] = " + m[i]);
//              System.out.println("counter = " + counter);
//            }
          }
          counter++;
          if(counter == 25) break;
        }while(Math.abs(deltam[1]) > tolerance );

        if (counter >= 25){
          javax.swing.JOptionPane.showMessageDialog(null,
            "Iterations exceeded in T_RiseSet",
            "Iterations Exceeded",
            javax.swing.JOptionPane.ERROR_MESSAGE);        
          counter = 1;
        }
        
        // use this string to display the previous day condition
        String pds[] = new String[3];
        for (i = 0; i <= 2; i++){ // check for previous day conditions
           if ( (m[i]*24 + local_offset) < 0){
               m[i] += 1.0;
               pds[i] = new String(" PD");
           }else pds[i] = new String("");
        }
        String nds[] = new String[3];
        for (i = 0; i <= 2; i++){ // check for next day conditions
           if ( (m[i]*24 + local_offset) > 24.0){
               m[i] -= 1.0;
               nds[i] = new String(" ND");
           }else nds[i] = new String("");
        }

        RiseAzimuth = A[1];
        SetAzimuth = A[2];
        TransitAzimuth = A[0];
        RiseElevation = h[1];
        TransitElevation = h[0];
        SetElevation = h[2];
        if(calcType == MOON || calcType == PLANET){
            RiseTimeDouble = m[1]*24+local_offset;
            RiseTime = T_Utilities.decimalToTimeHMS(RiseTimeDouble,0) + pds[1] + nds[1];
            SetTimeDouble = m[2]*24+local_offset;
            SetTime = T_Utilities.decimalToTimeHMS(SetTimeDouble,0) + pds[2] + nds[2];
            TransitTimeDouble = m[0]*24+local_offset;
            TransitTime = T_Utilities.decimalToTimeHMS(TransitTimeDouble,0) + pds[0] + nds[0];
        }else if(calcType == SUN){
            RiseTimeDouble = m[1]*24+local_offset;
            RiseTime = T_Utilities.decimalToTimeHMS(RiseTimeDouble,0);
            SetTimeDouble = m[2]*24+local_offset;
            SetTime = T_Utilities.decimalToTimeHMS(SetTimeDouble,0);
            TransitTimeDouble = m[0]*24+local_offset;
            TransitTime = T_Utilities.decimalToTimeHMS(TransitTimeDouble,0);
        }
        // Calculate the amount of daylight
        if( (m[2] - m[1]) < 0){
          // If Negative, add 1 to get correct amount if time.
          AmountOfDaylightDouble = (m[2] - m[1] + 1)*24.0;
          AmountOfDaylight = T_Utilities.decimalToTimeHMS(AmountOfDaylightDouble,0);
        }else{
          AmountOfDaylightDouble = (m[2] - m[1])*24.0;
          AmountOfDaylight = T_Utilities.decimalToTimeHMS(AmountOfDaylightDouble,0);
        }

        DATA_FRESH = true;
   }


  public static void main(String[] args) {
    T_JulianDate J = new T_JulianDate(3,20,2002,0,0,0);
    T_JulianDate JD = new T_JulianDate();
    T_RiseSet t_RiseSet1 = new T_RiseSet(JD,39.0,-104.8,-7.0f,T_RiseSet.SUN);
    //T_RiseSet t_RiseSet1 = new T_RiseSet();
    //System.out.println("Solar data for: " + J.toMediumString());
    System.out.print("Rise Time = " + t_RiseSet1.getRiseTime());
    System.out.print("   Rise El = " + t_RiseSet1.getRiseElevation());
    System.out.println("   Rise Az = " + t_RiseSet1.getRiseAzimuth());
    System.out.print("Transit Time = " + t_RiseSet1.getTransitTime());
    System.out.print("  Transit El = " + t_RiseSet1.getTransitElevation());
    System.out.println("  Transit Az = " + t_RiseSet1.getTransitAzimuth());
    System.out.print("Set Time = " + t_RiseSet1.getSetTime());
    System.out.print("  Set El = " + t_RiseSet1.getSetElevation());
    System.out.println("  Set Az = " + t_RiseSet1.getSetAzimuth());
    System.out.println("Amount of Daylight = " + t_RiseSet1.getAmountOfDaylight());
  }
}