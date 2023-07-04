package driver.ted.astro;

import driver.ted.utilities.T_JulianDate;
import driver.ted.utilities.T_Utilities;
import driver.ted.astro.T_Nutate;
import java.io.Serializable;

/**
 * Title:        T_LunarCoords
 * Description:  This file implements the T_LunarCoords class
 * Copyright:    (c) 2002 - 2006
 * @author Ted Driver
 * @version 1.3
 * 
 * Change from version 1.2:
 * Now determine k differently...use the full jd instead of defining mid points for a given month.
 * 
 * Change from version 1.1:
 * Added code to determine the moon phase dates for new, first quarter, full and last quarter
 * 
 */

  

public class T_LunarCoords implements Serializable{

  /**
   * New Moon identifier
   */
  public static final int NEW_MOON = 0;
  /**
   * First Quarter identifier
   */
  public static final int FIRST_QUARTER = 1;
  /**
   * Full moon identifier
   */
  public static final int FULL_MOON = 2;
  /**
   * Last quarter identifier
   */
  public static final int LAST_QUARTER = 3;
    
  private T_JulianDate JD = new T_JulianDate();
  private double lunarRightAscension;
  private double lunarDeclination;
  private double rD[], rM[], rMPrime[], rF[], rCoeffSine[], rCoeffCosine[];
  private double bD[], bM[], bMPrime[], bF[], bCoeffSine[];
  private boolean CONSTANTS_INITED = false, FRESH_DATA = false;
  private double geoLongitude, geoLatitude, delta, equatorialParallax;
  private double apparentGeoLongitude;  // equal to the geoLongitude + deltaPsi, the nutation in Longitude.
  private double apparentRightAscension, apparentDeclination; // derived from apparentGeoLongitude and geoLatitude
  private double JDE; // the JD of the calculated phase
  
  
  

  /**
   * Constructor: initializes with current system time
   */
  public T_LunarCoords() {
   rD = new double[60]; rM = new double[60]; rMPrime = new double[60]; rF = new double[60]; rCoeffSine = new double[60]; rCoeffCosine = new double[60];
   bD = new double[60]; bM = new double[60]; bMPrime = new double[60]; bF = new double[60]; bCoeffSine = new double[60];
   
  }

  /**
   * Constructor: initializes with time defined by {@link T_JulianDate } object
   * @param J The {@link T_JulianDate} object defining the time
   */
  public T_LunarCoords(T_JulianDate J){
    this.JD = J;
   rD = new double[60]; rM = new double[60]; rMPrime = new double[60]; rF = new double[60]; rCoeffSine = new double[60]; rCoeffCosine = new double[60];
   bD = new double[60]; bM = new double[60]; bMPrime = new double[60]; bF = new double[60]; bCoeffSine = new double[60];
  }

  /**
   * Sets the date and time for this object
   * @param J {@link T_JulianDate} object defining the new time.
   */
  public void setDateTime(T_JulianDate J){
    this.JD = J;
    FRESH_DATA = false;
  }

  /**
   * Public function to return a value between 0 and 1 representing the
   * percent of the lit moon's face facing Earth.
   * The value 0 represents a new Moon, or 0% lit, and 1 represents a Full Moon
   * or 100% lit.
   * @param J The {@link T_JulianDate} to use for the calculation
   * @return A double value between 0 and 1
   */
  public double getIllumination(T_JulianDate J){
    // Jean Meeus Algorithm from Astronomical Algorithms page 315, Chapter 46
    double T;
    double D, M, Mp, i, k;
    double dtr = T_Utilities.DtR;

    // Chapter 21 equation 21.1
    T = (J.getJulianDate() - 2451545.0)/36525.0;

    // Mean elongation of the Moon (Chapter 45 equation 45.2)
    D = 297.8502042 + 445267.1115168*T - 0.0016300*T*T
         + T*T*T/545868.0 - T*T*T*T/113065000.0;
    D = T_Utilities.normalize(0.0,360.0,D);
    //System.out.println("D = " + D);

    // Sun's Mean Anomaly (Chapter 45 equation 45.3)
    M = 357.5291092 + 35999.0502909*T
          - 0.0001536*T*T + T*T*T/24490000.0;
    M = T_Utilities.normalize(0.0,360.0,M);
    //System.out.println("M = " + M);

    // Moon's Mean Anomaly (Chapter 45 equation 45.4)
    Mp = 134.9634114 + 477198.8676313*T
          + 0.0089970*T*T + T*T*T/69699.0 - T*T*T*T/14712000.0;
    Mp = T_Utilities.normalize(0.0,360.0,Mp);
    //System.out.println("Mp = " + Mp);

    // i from chapter 46 equation 46.4

    i = 180.0 - D - 6.289*Math.sin(Mp*dtr);
            i += 2.100*Math.sin(M*dtr);
            i -= 1.274*Math.sin( (2.0*D - Mp)*dtr );
            i -= 0.658*Math.sin(2.0*D*dtr);
            i -= 0.214*Math.sin(2.0*Mp*dtr);
            i -= 0.110*Math.sin(D*dtr);
    i = T_Utilities.normalize(0.0,360.0,i);

   // System.out.println("i = " + i);

    // k from equation 46.1
    k = (1.0 + Math.cos(i*dtr))/2.0;

    return k;
  }


  private void calcLunarCoords(){
     
    double LPrime, D, M, MPrime, F;
    double A1, A2, A3;  
    double E, T;
    double sumL = 0.0; 
    double sumB = 0.0; 
    double sumR = 0.0;
    T_Nutate nutate = new T_Nutate();
      
    if(!CONSTANTS_INITED)
          initConstants();
    
      // Chapter 21 equation 21.1
    T = (JD.getJulianDate() - 2451545.0)/36525.0;
    
    // Moon's mean longitude, referred to the mean equinox of date including the light time term
    LPrime = 218.3164591 + 481267.88134236*T - 0.0013268*T*T + T*T*T/538841 - T*T*T*T/65194000;
    LPrime = T_Utilities.normalize(0.0,360.0,LPrime);
    //System.out.println("LPrime = " + LPrime);
    
    // Mean elongation of the Moon (Chapter 45 equation 45.2)
    D = 297.8502042 + 445267.1115168*T - 0.0016300*T*T + T*T*T/545868.0 - T*T*T*T/113065000.0;
    D = T_Utilities.normalize(0.0,360.0,D);
    //System.out.println("D = " + D);
      
    // Sun's Mean Anomaly (Chapter 45 equation 45.3)
    M = 357.5291092 + 35999.0502909*T - 0.0001536*T*T + T*T*T/24490000.0;
    M = T_Utilities.normalize(0.0,360.0,M);
    //System.out.println("M = " + M);

    // Moon's Mean Anomaly (Chapter 45 equation 45.4)
    MPrime = 134.9634114 + 477198.8676313*T + 0.0089970*T*T + T*T*T/69699.0 - T*T*T*T/14712000.0;
    MPrime = T_Utilities.normalize(0.0,360.0,MPrime);
    //System.out.println("MPrime = " + MPrime);
    
    // Moon's Argument of Latitude
    F = 93.2720993 + 483202.0175273*T - 0.0034029*T*T - T*T*T/3526000 + T*T*T*T/863310000;
    F = T_Utilities.normalize(0.0,360.0,F);
    //System.out.println("F = " + F);
      
    A1 = 119.75 + 131.849*T;
    A2 = 53.09 + 479264.290*T;
    A3 = 313.45 + 481266.484*T;
    
    E = 1.0 - 0.002516*T - 0.0000074*T*T;
    
    double rTerm = 1.0, bTerm = 0.0;;
    
    for (int i = 0; i < 60; i++){
        if(Math.abs(rM[i]) == 1.0) rTerm  = E;
        else if (Math.abs(rM[i]) == 2.0) rTerm = E*E;
        else rTerm = 1.0;
        
        sumL += rTerm*rCoeffSine[i]*Math.sin( (rD[i]*D + rM[i]*M + rMPrime[i]*MPrime + rF[i]*F)*T_Utilities.DtR );
        sumR += rTerm*rCoeffCosine[i]*Math.cos( (rD[i]*D + rM[i]*M + rMPrime[i]*MPrime + rF[i]*F)*T_Utilities.DtR );
        
        if(Math.abs(bM[i]) == 1.0) bTerm = E;
        else if (Math.abs(bM[i]) == 2.0) bTerm = E*E;
        else bTerm = 1.0;
        sumB += bTerm*bCoeffSine[i]*Math.sin( (bD[i]*D + bM[i]*M + bMPrime[i]*MPrime + bF[i]*F)*T_Utilities.DtR );
    }
    
    // now add the additive terms to sumL and sumB
    sumL += 3958.0*Math.sin(A1*T_Utilities.DtR);
    sumL += 1962.0*Math.sin( (LPrime - F)*T_Utilities.DtR);
    sumL += 318.0*Math.sin(A2*T_Utilities.DtR);
    
    sumB += -2235.0*Math.sin(LPrime*T_Utilities.DtR);
    sumB += 382.0*Math.sin(A3*T_Utilities.DtR);
    sumB += 175.0*Math.sin((A1 - F)*T_Utilities.DtR);
    sumB += 175.0*Math.sin((A1 + F)*T_Utilities.DtR);
    sumB += 127.0*Math.sin((LPrime - MPrime)*T_Utilities.DtR);
    sumB += -115.0*Math.sin((LPrime + MPrime)*T_Utilities.DtR);
    
    geoLongitude = LPrime + sumL/1000000;
    geoLatitude = sumB/1000000;
    delta = 385000.56 + sumR/1000;
    
    // calculate the nutation parameters
    double epsilon;
    epsilon = nutate.get_eps(JD);
//    System.out.println("epsilon = " + T_Utilities.decimalToDMS(epsilon/T_Utilities.DtR,2));
  
    // add the nutation in longitude to the geocentric longitude
    apparentGeoLongitude = geoLongitude + nutate.get_dpsi(JD)/T_Utilities.DtR; // all in degrees
//    System.out.println("appGeoLongitude = " + apparentGeoLongitude + ": " + T_Utilities.decimalToDMS(apparentGeoLongitude,2));
    
    double numeratorTanAlpha, sinDelta, DtR;
    DtR = T_Utilities.DtR;
    numeratorTanAlpha = Math.sin(apparentGeoLongitude*DtR)*Math.cos(epsilon) - Math.tan(geoLatitude*DtR)*Math.sin(epsilon);
    apparentRightAscension = Math.atan2(numeratorTanAlpha,Math.cos(apparentGeoLongitude*DtR))/DtR; // now in degrees
    
    
    sinDelta = Math.sin(geoLatitude*DtR)*Math.cos(epsilon);
    sinDelta += Math.cos(geoLatitude*DtR)*Math.sin(epsilon)*Math.sin(apparentGeoLongitude*DtR);
    apparentDeclination = Math.asin(sinDelta)/DtR; // now in degrees
    
    
    equatorialParallax = Math.asin(6378.14/delta);
//    System.out.println("geoLongitude = " + geoLongitude);
//    System.out.println("geoLatitude = " + geoLatitude);
//    System.out.println("delta = " + delta);
//    System.out.println("equatorialParallax = " + equatorialParallax/T_Utilities.DtR + " " + T_Utilities.decimalToDMS(equatorialParallax/T_Utilities.DtR,1));
//    System.out.println("apparentRightAscension = " + apparentRightAscension + ": " + T_Utilities.decimalToRAHMS(apparentRightAscension,1));
//    System.out.println("apparentDeclination = " + apparentDeclination + ": " + T_Utilities.decimalToDMS(apparentDeclination,1));
    
  }
  /**
   * Private function to return a value of k representing the approximate date and phase 
   * for the lunar phase calculation
   * 
   * @param month The integer month for which the phase is to be determined 
   * @param year The double year for which the phase is to be determined 
   * @param phaseType One of the phase types declared public in this class
   * @return k the value of k for this month and phase type
   */
  private double determineK(T_JulianDate t, int phaseType){
     double year = t.getDate().getYear();
      if ((phaseType < NEW_MOON) || (phaseType > LAST_QUARTER)) return -10000.0;
//      year = Math.floor(year); // delete any decimals already present in year;
//      switch(month){
//          case 1: year += 0.04; break;
//          case 2: year += 0.13; break;
//          case 3: year += 0.21; break;
//          case 4: year += 0.29; break;
//          case 5: year += 0.37; break;
//          case 6: year += 0.46; break;
//          case 7: year += 0.54; break;
//          case 8: year += 0.62; break;
//          case 9: year += 0.71; break;
//          case 10: year += 0.79; break;
//          case 11: year += 0.88; break;
//          case 12: year += 0.96; break;
//      }
      // now determine approximate k value
      //get year.decimal value
      year += (double)(t.getDayOfYear()/365.0);
      double k = (year - 2000)*12.3685;
      k = Math.floor(k);
      
      switch(phaseType){
          case NEW_MOON: k += 0.0; break;
          case FIRST_QUARTER: k += 0.25; break;
          case FULL_MOON: k += 0.50; break;
          case LAST_QUARTER: k +=0.75; break;
      }
      
      return k;
            
  }
  /**
   * @param month The integer month for which the phase is to be determined 
   * @param year The double year for which the phase is to be determined 
   * @param timeOffset the local time offset in decimal hours (-7.5 for example) negative west of Greenwich
   * @param phaseType One of the phase types declared public in this class
   */
  private void calcPhases(T_JulianDate today, double timeOffset, int phaseType){
      double k, T;
      double E, M, Mprime, F, Omega;
      double sum = 0.0;
      int year = today.getDate().getYear();
      
      
      
      // Determine k here 
      k = determineK( today,  phaseType);
      //System.out.println("k = " + k);
      if (k == -10000.0){
          //return bad stuff, k not deterined properly
      }
      
      // Calc T
      T = k/1236.85;
      //System.out.println("\nT = " + T);
      
      // Calc 47.1
      JDE = 2451550.09765 + 29.530588853 * k
                          + 0.0001337*T*T
                          - 0.000000150*T*T*T
                          + 0.00000000073*T*T*T*T;
      
      //System.out.println("\nJDE = " + JDE);
            
      // Calc E, M, Mprime, F, Omega
      E = 1.0 - 0.002516*T - 0.0000074*T*T;
      //System.out.println("\nE = " + E);
      
      M = 2.5534 + 29.10535669*k 
                 - 0.0000218*T*T
                 - 0.00000011*T*T*T;
      //System.out.println("\nM = " + M);
     // System.out.println("\tNormalized M = "+ T_Utilities.normalize(0,360,M));
      
      // convert to Radians
      M *= T_Utilities.DtR;
      
      Mprime = 201.5643 + 385.81693528*k
                        + 0.0107438*T*T
                        + 0.00001239*T*T*T
                        - 0.000000058*T*T*T*T;
      //System.out.println("\nMprime = " + Mprime);
      //System.out.println("\tNormalized Mprime = "+ T_Utilities.normalize(0,360,Mprime));
      
      // convert to Radians
      Mprime *= T_Utilities.DtR;
      
      F = 160.7108 + 390.67050274*k
                   - 0.0016341*T*T
                   - 0.00000227*T*T*T
                   + 0.000000011*T*T*T*T;
      //System.out.println("\nF = " + F);
      //System.out.println("\tNormalized F = "+ T_Utilities.normalize(0,360,F));
      
      // convert to Radians
      F *= T_Utilities.DtR;
      
      Omega = 124.7746 - 1.56375580*k
                       + 0.0020691*T*T
                       + 0.00000215*T*T*T;
      //System.out.println("\nOmega = " + Omega);
     // System.out.println("\tNormalized Omega = "+ T_Utilities.normalize(0,360,Omega));
      
      // convert to Radians
      Omega *= T_Utilities.DtR;
      
      if(phaseType == NEW_MOON){
        sum += -0.4072*Math.sin(Mprime);
        sum += 0.17241*E*Math.sin(M);
        sum += 0.01608*Math.sin(2*Mprime);
        sum += 0.01039*Math.sin(2*F);
        sum += 0.00739*E*Math.sin(Mprime-M);
        sum += -0.00514*E*Math.sin(Mprime+M);
        sum += 0.00208*E*E*Math.sin(2*M);
        sum += -0.00111*Math.sin(Mprime-2*F);
        sum += -0.00057*Math.sin(Mprime+2*F);
        sum += 0.00056*E*Math.sin(2*Mprime+M);
        sum += -0.00042*Math.sin(3*Mprime);
        sum += 0.00042*E*Math.sin(M+2*F);
        sum += 0.00038*E*Math.sin(M-2*F);
        sum += -0.00024*E*Math.sin(2*Mprime-M);
        sum += -0.00017*Math.sin(Omega);
        sum += -0.00007*Math.sin(Mprime+2*M);
        sum += 0.00004*Math.sin(2*Mprime-2*F);
        sum += 0.00004*Math.sin(3*M);
        sum += 0.00003*Math.sin(Mprime+M-2*F);
        sum += 0.00003*Math.sin(2*Mprime+2*F);
        sum += -0.00003*Math.sin(Mprime+M+2*F);
        sum += 0.00003*Math.sin(Mprime-M+2*F);
        sum += -0.00002*Math.sin(Mprime-M-2*F);
        sum += -0.00002*Math.sin(3*Mprime+M);
        sum += 0.00002*Math.sin(4*Mprime);
        //System.out.println("\nSum1 = " + sum);

      }else if ( (phaseType == FIRST_QUARTER) || (phaseType == LAST_QUARTER) ){
        double W = 0;
        sum += -0.62801*Math.sin(Mprime);
        sum += 0.17172*E*Math.sin(M);
        sum += -0.01183*E*Math.sin(Mprime+M);
        sum += 0.00862*Math.sin(2*Mprime);
        sum += 0.00804*Math.sin(2*F);
        sum += 0.00454*E*Math.sin(Mprime-M);
        sum += 0.00204*E*E*Math.sin(2*M);
        sum += -0.0018*Math.sin(Mprime-2*F);
        sum += -0.0007*Math.sin(Mprime+2*F);
        sum += -0.0004*Math.sin(3*Mprime);
        sum += -0.00034*E*Math.sin(2*Mprime-M);
        sum += 0.00032*E*Math.sin(M+2*F);
        sum += 0.00032*E*Math.sin(M-2*F);
        sum += -0.00028*E*E*Math.sin(Mprime+2*M);
        sum += 0.00027*E*Math.sin(2*Mprime+M);
        sum += -0.00017*Math.sin(Omega);
        sum += -0.00005*Math.sin(Mprime-M-2*F);
        sum += 0.00004*Math.sin(2*Mprime+2*F);
        sum += -0.00004*Math.sin(Mprime+M+2*F);
        sum += 0.00004*Math.sin(Mprime-2*M);
        sum += 0.00003*Math.sin(Mprime+M-2*F);
        sum += 0.00003*Math.sin(3*M);
        sum += 0.00002*Math.sin(2*Mprime-2*F);
        sum += 0.00002*Math.sin(Mprime-M+2*F);
        sum += -0.00002*Math.sin(3*Mprime+M);
        
        //System.out.println("\nSum1 = " + sum);
        
        // calculate additional quarter corrections
        W = 0.00306 - 0.00038*E*Math.cos(M) + 0.00026*Math.cos(Mprime)
                - 0.00002*Math.cos(Mprime-M) + 0.00002*Math.cos(Mprime + M)
                + 0.00002*Math.cos(2*F);
        
        //System.out.println("\nW = " + W);
        
        if(phaseType == FIRST_QUARTER){
            sum += W;
        }else{
            sum -= W;
        }                

      }else if (phaseType == FULL_MOON){
        sum += -0.40614*Math.sin(Mprime);
        sum += 0.17302*E*Math.sin(M);
        sum += 0.01614*Math.sin(2*Mprime);
        sum += 0.01043*Math.sin(2*F);
        sum += 0.00734*E*Math.sin(Mprime-M);
        sum += -0.00515*E*Math.sin(Mprime+M);
        sum += 0.00209*E*E*Math.sin(2*M);
        sum += -0.00111*Math.sin(Mprime-2*F);
        sum += -0.00057*Math.sin(Mprime+2*F);
        sum += 0.00056*E*Math.sin(2*Mprime+M);
        sum += -0.00042*Math.sin(3*Mprime);
        sum += 0.00042*E*Math.sin(M+2*F);
        sum += 0.00038*E*Math.sin(M-2*F);
        sum += -0.00024*E*Math.sin(2*Mprime-M);
        sum += -0.00017*Math.sin(Omega);
        sum += -0.00007*Math.sin(Mprime+2*M);
        sum += 0.00004*Math.sin(2*Mprime-2*F);
        sum += 0.00004*Math.sin(3*M);
        sum += 0.00003*Math.sin(Mprime+M-2*F);
        sum += 0.00003*Math.sin(2*Mprime+2*F);
        sum += -0.00003*Math.sin(Mprime+M+2*F);
        sum += 0.00003*Math.sin(Mprime-M+2*F);
        sum += -0.00002*Math.sin(Mprime-M-2*F);
        sum += -0.00002*Math.sin(3*Mprime+M);
        sum += 0.00002*Math.sin(4*Mprime);
        //System.out.println("\nSum1 = " + sum);
      }
      
      // calculate 14 additional corrections
      //System.out.println("14 additional = " + calc14AdditionalCorrections(k, T));
      
      JDE += sum + calc14AdditionalCorrections(k, T);
      //System.out.println("\nJDE Finally = " + JDE);
      
      // correct for Delta T 
      // formula derived from linear regression of Table 9A on page 72 of Meeus Astronomical Algorithms
      // regression is done for dT values from 1904 to 1992
      // residuals will be about 5-10 seconds for the last century
      // Meeus formula provides results that are too large
      double dT = 0.5273*(double)year - 996.26; // this is meeus formula ->// -15.0 + Math.pow(JDE - 2382148,2)/41048480.0;
      //System.out.println("DT = " + dT);
      JDE -= dT/86400;
      
      // Correct for Time Offset
      JDE += timeOffset/24.0;
      
  }
  
  private double calc14AdditionalCorrections(double k, double T){
        double A[], sum = 0.0;
        A = new double[14];
        
        A[0] = 299.77 + 0.107408*k - 0.009173*T*T;
        A[1] = 251.88 + 0.016321*k;
        A[2] = 251.83 + 26.651886*k;
        A[3] = 349.42 + 36.412478*k;
        A[4] = 84.66 + 18.206239*k;
        A[5] = 141.74 + 53.303771*k;
        A[6] = 207.14 + 2.453732*k;
        A[7] = 154.84 + 7.306860*k;
        A[8] = 34.52 + 27.261239*k;
        A[9] = 207.19 + 0.121824*k;
        A[10] = 291.34 + 1.844379*k;
        A[11] = 161.72 + 24.198154*k;
        A[12] = 239.56 + 25.513099*k;
        A[13] = 331.55 + 3.592518*k;
        
        sum += 0.000325*Math.sin(A[0]*T_Utilities.DtR);
        sum += 0.000165*Math.sin(A[1]*T_Utilities.DtR);
        sum += 0.000164*Math.sin(A[2]*T_Utilities.DtR);
        sum += 0.000126*Math.sin(A[3]*T_Utilities.DtR);
        sum += 0.00011*Math.sin(A[4]*T_Utilities.DtR);
        sum += 0.000062*Math.sin(A[5]*T_Utilities.DtR);
        sum += 0.00006*Math.sin(A[6]*T_Utilities.DtR);
        sum += 0.000056*Math.sin(A[7]*T_Utilities.DtR);
        sum += 0.000047*Math.sin(A[8]*T_Utilities.DtR);
        sum += 0.000042*Math.sin(A[9]*T_Utilities.DtR);
        sum += 0.00004*Math.sin(A[10]*T_Utilities.DtR);
        sum += 0.000037*Math.sin(A[11]*T_Utilities.DtR);
        sum += 0.000035*Math.sin(A[12]*T_Utilities.DtR);
        sum += 0.000023*Math.sin(A[13]*T_Utilities.DtR);
        
        return sum;
  }

  /**
   * Test program
   * @param args args
   */
  public static void main(String[] args) {
    T_JulianDate J = new T_JulianDate(12,31,2003,0,0,0);
    T_LunarCoords t_LunarCoords1 = new T_LunarCoords();
    System.out.println("Phase for " + J.toMediumString() + " is " +
    t_LunarCoords1.getIllumination(J)*100.0 + "%" );
    T_JulianDate Moonphase = new T_JulianDate(t_LunarCoords1.getPhase(J,-7.0,LAST_QUARTER));
    System.out.println(Moonphase.toString());
  }
  
  private void initConstants(){
        rD[0] = 0; rM[0] = 0; rMPrime[0] = 1; rF[0] = 0; rCoeffSine[0] = 6288774; rCoeffCosine[0] = -20905355;
        rD[1] = 2; rM[1] = 0; rMPrime[1] = -1; rF[1] = 0; rCoeffSine[1] = 1274027; rCoeffCosine[1] = -3699111;
        rD[2] = 2; rM[2] = 0; rMPrime[2] = 0; rF[2] = 0; rCoeffSine[2] = 658314; rCoeffCosine[2] = -2955968;
        rD[3] = 0; rM[3] = 0; rMPrime[3] = 2; rF[3] = 0; rCoeffSine[3] = 213618; rCoeffCosine[3] = -569925;
        rD[4] = 0; rM[4] = 1; rMPrime[4] = 0; rF[4] = 0; rCoeffSine[4] = -185116; rCoeffCosine[4] = 48888;
        rD[5] = 0; rM[5] = 0; rMPrime[5] = 0; rF[5] = 2; rCoeffSine[5] = -114332; rCoeffCosine[5] = -3149;
        rD[6] = 2; rM[6] = 0; rMPrime[6] = -2; rF[6] = 0; rCoeffSine[6] = 58793; rCoeffCosine[6] = 246158;
        rD[7] = 2; rM[7] = -1; rMPrime[7] = -1; rF[7] = 0; rCoeffSine[7] = 57066; rCoeffCosine[7] = -152138;
        rD[8] = 2; rM[8] = 0; rMPrime[8] = 1; rF[8] = 0; rCoeffSine[8] = 53322; rCoeffCosine[8] = -170733;
        rD[9] = 2; rM[9] = -1; rMPrime[9] = 0; rF[9] = 0; rCoeffSine[9] = 45758; rCoeffCosine[9] = -204586;
        rD[10] = 0; rM[10] = 1; rMPrime[10] = -1; rF[10] = 0; rCoeffSine[10] = -40923; rCoeffCosine[10] = -129620;
        rD[11] = 1; rM[11] = 0; rMPrime[11] = 0; rF[11] = 0; rCoeffSine[11] = -34720; rCoeffCosine[11] = 108743;
        rD[12] = 0; rM[12] = 1; rMPrime[12] = 1; rF[12] = 0; rCoeffSine[12] = -30383; rCoeffCosine[12] = 104755;
        rD[13] = 2; rM[13] = 0; rMPrime[13] = 0; rF[13] = -2; rCoeffSine[13] = 15327; rCoeffCosine[13] = 10321;
        rD[14] = 0; rM[14] = 0; rMPrime[14] = 1; rF[14] = 2; rCoeffSine[14] = -12528; rCoeffCosine[14] = 0;
        rD[15] = 0; rM[15] = 0; rMPrime[15] = 1; rF[15] = -2; rCoeffSine[15] = 10980; rCoeffCosine[15] = 79661;
        rD[16] = 4; rM[16] = 0; rMPrime[16] = -1; rF[16] = 0; rCoeffSine[16] = 10675; rCoeffCosine[16] = -34782;
        rD[17] = 0; rM[17] = 0; rMPrime[17] = 3; rF[17] = 0; rCoeffSine[17] = 10034; rCoeffCosine[17] = -23210;
        rD[18] = 4; rM[18] = 0; rMPrime[18] = -2; rF[18] = 0; rCoeffSine[18] = 8548; rCoeffCosine[18] = -21636;
        rD[19] = 2; rM[19] = 1; rMPrime[19] = -1; rF[19] = 0; rCoeffSine[19] = -7888; rCoeffCosine[19] = 24208;
        rD[20] = 2; rM[20] = 1; rMPrime[20] = 0; rF[20] = 0; rCoeffSine[20] = -6766; rCoeffCosine[20] = 30824;
        rD[21] = 1; rM[21] = 0; rMPrime[21] = -1; rF[21] = 0; rCoeffSine[21] = -5163; rCoeffCosine[21] = -8379;
        rD[22] = 1; rM[22] = 1; rMPrime[22] = 0; rF[22] = 0; rCoeffSine[22] = 4987; rCoeffCosine[22] = -16675;
        rD[23] = 2; rM[23] = -1; rMPrime[23] = 1; rF[23] = 0; rCoeffSine[23] = 4036; rCoeffCosine[23] = -12831;
        rD[24] = 2; rM[24] = 0; rMPrime[24] = 2; rF[24] = 0; rCoeffSine[24] = 3994; rCoeffCosine[24] = -10445;
        rD[25] = 4; rM[25] = 0; rMPrime[25] = 0; rF[25] = 0; rCoeffSine[25] = 3861; rCoeffCosine[25] = -11650;
        rD[26] = 2; rM[26] = 0; rMPrime[26] = -3; rF[26] = 0; rCoeffSine[26] = 3665; rCoeffCosine[26] = 14403;
        rD[27] = 0; rM[27] = 1; rMPrime[27] = -2; rF[27] = 0; rCoeffSine[27] = -2689; rCoeffCosine[27] = -7003;
        rD[28] = 2; rM[28] = 0; rMPrime[28] = -1; rF[28] = 2; rCoeffSine[28] = -2602; rCoeffCosine[28] = 0;
        rD[29] = 2; rM[29] = -1; rMPrime[29] = -2; rF[29] = 0; rCoeffSine[29] = 2390; rCoeffCosine[29] = 10056;
        rD[30] = 1; rM[30] = 0; rMPrime[30] = 1; rF[30] = 0; rCoeffSine[30] = -2348; rCoeffCosine[30] = 6322;
        rD[31] = 2; rM[31] = -2; rMPrime[31] = 0; rF[31] = 0; rCoeffSine[31] = 2236; rCoeffCosine[31] = -9884;
        rD[32] = 0; rM[32] = 1; rMPrime[32] = 2; rF[32] = 0; rCoeffSine[32] = -2120; rCoeffCosine[32] = 5751;
        rD[33] = 0; rM[33] = 2; rMPrime[33] = 0; rF[33] = 0; rCoeffSine[33] = -2069; rCoeffCosine[33] = 0;
        rD[34] = 2; rM[34] = -2; rMPrime[34] = -1; rF[34] = 0; rCoeffSine[34] = 2048; rCoeffCosine[34] = -4950;
        rD[35] = 2; rM[35] = 0; rMPrime[35] = 1; rF[35] = -2; rCoeffSine[35] = -1773; rCoeffCosine[35] = 4130;
        rD[36] = 2; rM[36] = 0; rMPrime[36] = 0; rF[36] = 2; rCoeffSine[36] = -1595; rCoeffCosine[36] = 0;
        rD[37] = 4; rM[37] = -1; rMPrime[37] = -1; rF[37] = 0; rCoeffSine[37] = 1215; rCoeffCosine[37] = -3958;
        rD[38] = 0; rM[38] = 0; rMPrime[38] = 2; rF[38] = 2; rCoeffSine[38] = -1110; rCoeffCosine[38] = 0;
        rD[39] = 3; rM[39] = 0; rMPrime[39] = -1; rF[39] = 0; rCoeffSine[39] = -892; rCoeffCosine[39] = 3258;
        rD[40] = 2; rM[40] = 1; rMPrime[40] = 1; rF[40] = 0; rCoeffSine[40] = -810; rCoeffCosine[40] = 2616;
        rD[41] = 4; rM[41] = -1; rMPrime[41] = -2; rF[41] = 0; rCoeffSine[41] = 759; rCoeffCosine[41] = -1897;
        rD[42] = 0; rM[42] = 2; rMPrime[42] = -1; rF[42] = 0; rCoeffSine[42] = -713; rCoeffCosine[42] = -2117;
        rD[43] = 2; rM[43] = 2; rMPrime[43] = -1; rF[43] = 0; rCoeffSine[43] = -700; rCoeffCosine[43] = 2354;
        rD[44] = 2; rM[44] = 1; rMPrime[44] = -2; rF[44] = 0; rCoeffSine[44] = 691; rCoeffCosine[44] = 0;
        rD[45] = 2; rM[45] = -1; rMPrime[45] = 0; rF[45] = -2; rCoeffSine[45] = 596; rCoeffCosine[45] = 0;
        rD[46] = 4; rM[46] = 0; rMPrime[46] = 1; rF[46] = 0; rCoeffSine[46] = 549; rCoeffCosine[46] = -1423;
        rD[47] = 0; rM[47] = 0; rMPrime[47] = 4; rF[47] = 0; rCoeffSine[47] = 537; rCoeffCosine[47] = -1117;
        rD[48] = 4; rM[48] = -1; rMPrime[48] = 0; rF[48] = 0; rCoeffSine[48] = 520; rCoeffCosine[48] = -1571;
        rD[49] = 1; rM[49] = 0; rMPrime[49] = -2; rF[49] = 0; rCoeffSine[49] = -487; rCoeffCosine[49] = -1739;
        rD[50] = 2; rM[50] = 1; rMPrime[50] = 0; rF[50] = -2; rCoeffSine[50] = -399; rCoeffCosine[50] = 0;
        rD[51] = 0; rM[51] = 0; rMPrime[51] = 2; rF[51] = -2; rCoeffSine[51] = -381; rCoeffCosine[51] = -4421;
        rD[52] = 1; rM[52] = 1; rMPrime[52] = 1; rF[52] = 0; rCoeffSine[52] = 351; rCoeffCosine[52] = 0;
        rD[53] = 3; rM[53] = 0; rMPrime[53] = -2; rF[53] = 0; rCoeffSine[53] = -340; rCoeffCosine[53] = 0;
        rD[54] = 4; rM[54] = 0; rMPrime[54] = -3; rF[54] = 0; rCoeffSine[54] = 330; rCoeffCosine[54] = 0;
        rD[55] = 2; rM[55] = -1; rMPrime[55] = 2; rF[55] = 0; rCoeffSine[55] = 327; rCoeffCosine[55] = 0;
        rD[56] = 0; rM[56] = 2; rMPrime[56] = 1; rF[56] = 0; rCoeffSine[56] = -323; rCoeffCosine[56] = 1165;
        rD[57] = 1; rM[57] = 1; rMPrime[57] = -1; rF[57] = 0; rCoeffSine[57] = 299; rCoeffCosine[57] = 0;
        rD[58] = 2; rM[58] = 0; rMPrime[58] = 3; rF[58] = 0; rCoeffSine[58] = 294; rCoeffCosine[58] = 0;
        rD[59] = 2; rM[59] = 0; rMPrime[59] = -1; rF[59] = -2; rCoeffSine[59] = 0; rCoeffCosine[59] = 8752;


        bD[0] = 0; bM[0] = 0; bMPrime[0] = 0; bF[0] = 1; bCoeffSine[0] = 5128122;
        bD[1] = 0; bM[1] = 0; bMPrime[1] = 1; bF[1] = 1; bCoeffSine[1] = 280602;
        bD[2] = 0; bM[2] = 0; bMPrime[2] = 1; bF[2] = -1; bCoeffSine[2] = 277693;
        bD[3] = 2; bM[3] = 0; bMPrime[3] = 0; bF[3] = -1; bCoeffSine[3] = 173237;
        bD[4] = 2; bM[4] = 0; bMPrime[4] = -1; bF[4] = 1; bCoeffSine[4] = 55413;
        bD[5] = 2; bM[5] = 0; bMPrime[5] = -1; bF[5] = -1; bCoeffSine[5] = 46271;
        bD[6] = 2; bM[6] = 0; bMPrime[6] = 0; bF[6] = 1; bCoeffSine[6] = 32573;
        bD[7] = 0; bM[7] = 0; bMPrime[7] = 2; bF[7] = 1; bCoeffSine[7] = 17198;
        bD[8] = 2; bM[8] = 0; bMPrime[8] = 1; bF[8] = -1; bCoeffSine[8] = 9266;
        bD[9] = 0; bM[9] = 0; bMPrime[9] = 2; bF[9] = -1; bCoeffSine[9] = 8822;
        bD[10] = 2; bM[10] = -1; bMPrime[10] = 0; bF[10] = -1; bCoeffSine[10] = 8216;
        bD[11] = 2; bM[11] = 0; bMPrime[11] = -2; bF[11] = -1; bCoeffSine[11] = 4324;
        bD[12] = 2; bM[12] = 0; bMPrime[12] = 1; bF[12] = 1; bCoeffSine[12] = 4200;
        bD[13] = 2; bM[13] = 1; bMPrime[13] = 0; bF[13] = -1; bCoeffSine[13] = -3359;
        bD[14] = 2; bM[14] = -1; bMPrime[14] = -1; bF[14] = 1; bCoeffSine[14] = 2463;
        bD[15] = 2; bM[15] = -1; bMPrime[15] = 0; bF[15] = 1; bCoeffSine[15] = 2211;
        bD[16] = 2; bM[16] = -1; bMPrime[16] = -1; bF[16] = -1; bCoeffSine[16] = 2065;
        bD[17] = 0; bM[17] = 1; bMPrime[17] = -1; bF[17] = -1; bCoeffSine[17] = -1870;
        bD[18] = 4; bM[18] = 0; bMPrime[18] = -1; bF[18] = -1; bCoeffSine[18] = 1828;
        bD[19] = 0; bM[19] = 1; bMPrime[19] = 0; bF[19] = 1; bCoeffSine[19] = -1794;
        bD[20] = 0; bM[20] = 0; bMPrime[20] = 0; bF[20] = 3; bCoeffSine[20] = -1749;
        bD[21] = 0; bM[21] = 1; bMPrime[21] = -1; bF[21] = 1; bCoeffSine[21] = -1565;
        bD[22] = 1; bM[22] = 0; bMPrime[22] = 0; bF[22] = 1; bCoeffSine[22] = -1491;
        bD[23] = 0; bM[23] = 1; bMPrime[23] = 1; bF[23] = 1; bCoeffSine[23] = -1475;
        bD[24] = 0; bM[24] = 1; bMPrime[24] = 1; bF[24] = -1; bCoeffSine[24] = -1410;
        bD[25] = 0; bM[25] = 1; bMPrime[25] = 0; bF[25] = -1; bCoeffSine[25] = -1344;
        bD[26] = 1; bM[26] = 0; bMPrime[26] = 0; bF[26] = -1; bCoeffSine[26] = -1335;
        bD[27] = 0; bM[27] = 0; bMPrime[27] = 3; bF[27] = 1; bCoeffSine[27] = 1107;
        bD[28] = 4; bM[28] = 0; bMPrime[28] = 0; bF[28] = -1; bCoeffSine[28] = 1021;
        bD[29] = 4; bM[29] = 0; bMPrime[29] = -1; bF[29] = 1; bCoeffSine[29] = 833;
        bD[30] = 0; bM[30] = 0; bMPrime[30] = 1; bF[30] = -3; bCoeffSine[30] = 777;
        bD[31] = 4; bM[31] = 0; bMPrime[31] = -2; bF[31] = 1; bCoeffSine[31] = 671;
        bD[32] = 2; bM[32] = 0; bMPrime[32] = 0; bF[32] = -3; bCoeffSine[32] = 607;
        bD[33] = 2; bM[33] = 0; bMPrime[33] = 2; bF[33] = -1; bCoeffSine[33] = 596;
        bD[34] = 2; bM[34] = -1; bMPrime[34] = 1; bF[34] = -1; bCoeffSine[34] = 491;
        bD[35] = 2; bM[35] = 0; bMPrime[35] = -2; bF[35] = 1; bCoeffSine[35] = -451;
        bD[36] = 0; bM[36] = 0; bMPrime[36] = 3; bF[36] = -1; bCoeffSine[36] = 439;
        bD[37] = 2; bM[37] = 0; bMPrime[37] = 2; bF[37] = 1; bCoeffSine[37] = 422;
        bD[38] = 2; bM[38] = 0; bMPrime[38] = -3; bF[38] = -1; bCoeffSine[38] = 421;
        bD[39] = 2; bM[39] = 1; bMPrime[39] = -1; bF[39] = 1; bCoeffSine[39] = -366;
        bD[40] = 2; bM[40] = 1; bMPrime[40] = 0; bF[40] = 1; bCoeffSine[40] = -351;
        bD[41] = 4; bM[41] = 0; bMPrime[41] = 0; bF[41] = 1; bCoeffSine[41] = 331;
        bD[42] = 2; bM[42] = -1; bMPrime[42] = 1; bF[42] = 1; bCoeffSine[42] = 315;
        bD[43] = 2; bM[43] = -2; bMPrime[43] = 0; bF[43] = -1; bCoeffSine[43] = 302;
        bD[44] = 0; bM[44] = 0; bMPrime[44] = 1; bF[44] = 3; bCoeffSine[44] = -283;
        bD[45] = 2; bM[45] = 1; bMPrime[45] = 1; bF[45] = -1; bCoeffSine[45] = -229;
        bD[46] = 1; bM[46] = 1; bMPrime[46] = 0; bF[46] = -1; bCoeffSine[46] = 223;
        bD[47] = 1; bM[47] = 1; bMPrime[47] = 0; bF[47] = 1; bCoeffSine[47] = 223;
        bD[48] = 0; bM[48] = 1; bMPrime[48] = -2; bF[48] = -1; bCoeffSine[48] = -220;
        bD[49] = 2; bM[49] = 1; bMPrime[49] = -1; bF[49] = -1; bCoeffSine[49] = -220;
        bD[50] = 1; bM[50] = 0; bMPrime[50] = 1; bF[50] = 1; bCoeffSine[50] = -185;
        bD[51] = 2; bM[51] = -1; bMPrime[51] = -2; bF[51] = -1; bCoeffSine[51] = 181;
        bD[52] = 0; bM[52] = 1; bMPrime[52] = 2; bF[52] = 1; bCoeffSine[52] = -177;
        bD[53] = 4; bM[53] = 0; bMPrime[53] = -2; bF[53] = -1; bCoeffSine[53] = 176;
        bD[54] = 4; bM[54] = -1; bMPrime[54] = -1; bF[54] = -1; bCoeffSine[54] = 166;
        bD[55] = 1; bM[55] = 0; bMPrime[55] = 1; bF[55] = -1; bCoeffSine[55] = -164;
        bD[56] = 4; bM[56] = 0; bMPrime[56] = 1; bF[56] = -1; bCoeffSine[56] = 132;
        bD[57] = 1; bM[57] = 0; bMPrime[57] = -1; bF[57] = -1; bCoeffSine[57] = -119;
        bD[58] = 4; bM[58] = -1; bMPrime[58] = 0; bF[58] = -1; bCoeffSine[58] = 115;
        bD[59] = 2; bM[59] = -2; bMPrime[59] = 0; bF[59] = 1; bCoeffSine[59] = 107;
        
        CONSTANTS_INITED = true;  

  }
  
  /**
   * Returns the apparent Right Ascension of the moon at the given time
   * @return Apparent Right Ascension
   */
  public double getAlpha() {
      if(!FRESH_DATA){
          calcLunarCoords();
      }
      return apparentRightAscension;
  }
  
  /**
   * Return the apparent Declination
   * @return Apparent Declination
   */
  public double getDelta(){
      if(!FRESH_DATA){
          calcLunarCoords();
      }
      return apparentDeclination;
  }
  /**
   * Returns the Moon's equatorial Parallax in Radians
   * @return equatorial parallax (radians)
   */
  public double getEquatorialParallax(){
      if(!FRESH_DATA){
          calcLunarCoords();
      }
      return equatorialParallax;
  }
  
  /**
   * Returns the time of the specified phase, including the specified time offset
   * @param today The {@link T_JulianDate} object specifying the time
   * @param offset The time offset in hours
   * @param phaseType The type of phase requested - use one of the predefined objects in the class.
   * @return Returns the Julian Date of the specified phase
   */
  public double getPhase(T_JulianDate today, double offset, int phaseType){
      calcPhases(today, offset, phaseType);
      return JDE;
  }
  
}