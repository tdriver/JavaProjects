package driver.ted.astro;

import driver.ted.utilities.*;
import java.io.Serializable;

/**
 * Title:        T_Utilities
 * Description:  This project will be used to create and maintain the utility classes in the utility package driver.ted.utilities
 * Copyright:    (c) 2001 - 2006
 * @author Ted Driver
 * @version 1.1
 *
 * Change from version 1.0:
  */

  //


public class T_Nutate implements Serializable{
  static double AstoRad = 4.848136812e-6;      // Arcseconds to Radians Conversion

  double M_nute[][], B_nute[][], A_nute[][]; // Nutation coefficients
  T_JulianDate JD;    // The Date of interest
  double dpsi, eps, deltae; // nutation in longitude and true obliquity in radians;
  double T;         // time in julian centuries

  /**
   * Method to Return delta psi (the nutation in longitude) in radians.
   * This method is provided a T_JulianDate object and then returns the nutation in Longitude.
   * @param J The Julian Date for which the nutation in longitude is desired
   * @return The nutation in longitude, delta psi in radians
   */
  public double get_dpsi(T_JulianDate J){
    JD = J;
    calc();
    return dpsi;
  }

  /**
   * Method to Return the true obliquity of the elipitic in radians.
   * This method is provided a T_JulianDate object and then returns true obliquity of the elipitic in radians.
   * (epsilon + delta epsilon)
   * @param J The Julian Date for which the true obliquity of the eliptic is desired
   * @return true obliquity of the eliptic , epsilon + delta epsilon in radians
   */
  public double get_eps(T_JulianDate J){
    JD = J;
    calc();
    return eps;
  }


  /**
   * Method to Return delta epsilon, in radians.
   * This method is provided a T_JulianDate object and then returns delta eps  of the true obliquity of the elipitic in radians.
   * (epsilon + delta epsilon)
   * @param J The {@link T_JulianDate} object for which the true obliquity of the eliptic is desired
   * @return delta epsilon, the change from the true obliquity of the eliptic , epsilon + delta epsilon in radians
   */
  public double get_deps(T_JulianDate J){
    JD = J;
    calc();
    return deltae;
  }

  /**
   * Private method to calculate the nutation related parameters.
   * This method is derived from Jean Meeus' Nutation and Obliquity chapter in
   * his Astronomical algorithms book, Chapter 21.
   */
  private void calc(){
      double  alpha[], L[], A[], B[];
      double Meane, JDE;
      int i, j;

      alpha = new double[106];
      L = new double[5];
      A = new double[106];
      B = new double[106];

      // re-initialize the nutation parameters to zero
      dpsi = 0;
      eps = 0;
      deltae = 0;

      // calculate the T value from JD here!
      JDE = JD.getJulianDate() + 67.0/86400.0; // 67.0 is the Delta UT term for year 2000

      // time in julian centuries
      T = (JDE - 2451545.0)/36525.0;
      //cout << "T = " << T << endl;

      // initialize alpha to all zeros
      for (i = 0; i < 106; i++) alpha[i] = 0.0;

      // fill in the L values
      L[0] = 485866.733 + 1.717915923e9*T + 31.310*T*T + 0.064*T*T*T;
      L[1] = 1287099.804 + 129.5965812e6*T - 0.577*T*T - 0.012*T*T*T;
      L[2] = 335778.877 + 1.739527263e9*T - 13.257*T*T + 0.011*T*T*T;
      L[3] = 1072261.307 + 1.602961601e9*T - 6.891*T*T + 0.019*T*T*T;
      L[4] = 450160.280 - 6.962890539e6*T + 7.455*T*T + 0.008*T*T*T;

      // populate the alpha vector using M and L;
      for (i = 0; i < 106; i++){
        for (j = 0; j < 5; j++){
          alpha[i] += M_nute[i][j]*L[j];
        }
        alpha[i] *= AstoRad; // convert alpha to radians from arcseconds
      }

      //  calculate the A and B values from the nutation table
      for (i = 0; i < 106; i++){
        A[i] = A_nute[i][0] + A_nute[i][1]*T;
        B[i] = B_nute[i][0] + B_nute[i][1]*T;
      }

      // compute the nutation longitude in arcseconds (d psi)
      for (i = 0; i < 106; i++){
        dpsi += A[i]*Math.sin(alpha[i]);
      }

      // convert dpsi to radians
      dpsi *= AstoRad;

      // compute the nutation in obliquity in arcseconds (d epsilon)
      for (i = 0; i < 106; i++){
             deltae += B[i]*Math.cos(alpha[i]);
      }

      // calculate the mean obliquity of the Earth
      Meane = 84381.448-46.815*T-0.00059*T*T + .001813*T*T; // This is in arcsecs

      // calculate the true obliquity
      eps =(Meane + deltae)*AstoRad;

      // convert deltae to radians
      deltae *= AstoRad;

  } // end calc()

  // The methods commented out below are for debugging purposes and are not needed by the class to work.
 /* public double get_M_nute(int i, int j)
   {
    return M_nute[i][j];
   }

  public double get_B_nute(int i, int j)
   {
    return B_nute[i][j];
   }

  public double get_A_nute(int i, int j)
   {
    return A_nute[i][j];
   }
  */
/**
   * Default constructor, initializes all of the Nutation numeric arrays.
   * This constructor initializes the numeric arrays to the values outlined in
   * Chapter 21 of Jean Meeus' Astronomical Algorithms, pages 133-134.
   */
  public T_Nutate() {
    // set the data to initial values
      dpsi = 0.0;
      eps = 0.0;
      deltae = 0.0;
      T = 0.0;
      M_nute = new double[106][5];
      B_nute = new double[106][2];
      A_nute = new double[106][2];

    // Nutation coefficient data
    // derived from ???
      M_nute[0][0] = 0;
      M_nute[0][1] = 0;
      M_nute[0][2] = 0;
      M_nute[0][3] = 0;
      M_nute[0][4] = 1;
      A_nute[0][0] = -17.1996;
      A_nute[0][1] = -0.017642;
      B_nute[0][0] = 9.2025;
      B_nute[0][1] = 0.00089;

      M_nute[1][0] = 0;
      M_nute[1][1] = 0;
      M_nute[1][2] = 0;
      M_nute[1][3] = 0;
      M_nute[1][4] = 2;
      A_nute[1][0] = 0.2062;
      A_nute[1][1] = 2e-05;
      B_nute[1][0] = -0.0895;
      B_nute[1][1] = 5e-05;

      M_nute[2][0] = -2;
      M_nute[2][1] = 0;
      M_nute[2][2] = 2;
      M_nute[2][3] = 0;
      M_nute[2][4] = 1;
      A_nute[2][0] = 0.0046;
      A_nute[2][1] = 0;
      B_nute[2][0] = -0.0024;
      B_nute[2][1] = 0;

      M_nute[3][0] = 2;
      M_nute[3][1] = 0;
      M_nute[3][2] = -2;
      M_nute[3][3] = 0;
      M_nute[3][4] = 0;
      A_nute[3][0] = 0.0011;
      A_nute[3][1] = 0;
      B_nute[3][0] = 0;
      B_nute[3][1] = 0;

      M_nute[4][0] = -2;
      M_nute[4][1] = 0;
      M_nute[4][2] = 2;
      M_nute[4][3] = 0;
      M_nute[4][4] = 2;
      A_nute[4][0] = -0.0003;
      A_nute[4][1] = 0;
      B_nute[4][0] = 0.0001;
      B_nute[4][1] = 0;

      M_nute[5][0] = 1;
      M_nute[5][1] = -1;
      M_nute[5][2] = 0;
      M_nute[5][3] = -1;
      M_nute[5][4] = 0;
      A_nute[5][0] = -0.0003;
      A_nute[5][1] = 0;
      B_nute[5][0] = 0;
      B_nute[5][1] = 0;

      M_nute[6][0] = 0;
      M_nute[6][1] = -2;
      M_nute[6][2] = 2;
      M_nute[6][3] = -2;
      M_nute[6][4] = 1;
      A_nute[6][0] = -0.0002;
      A_nute[6][1] = 0;
      B_nute[6][0] = 0.0001;
      B_nute[6][1] = 0;

      M_nute[7][0] = 2;
      M_nute[7][1] = 0;
      M_nute[7][2] = -2;
      M_nute[7][3] = 0;
      M_nute[7][4] = 1;
      A_nute[7][0] = 0.0001;
      A_nute[7][1] = 0;
      B_nute[7][0] = 0;
      B_nute[7][1] = 0;

      M_nute[8][0] = 0;
      M_nute[8][1] = 0;
      M_nute[8][2] = 2;
      M_nute[8][3] = -2;
      M_nute[8][4] = 2;
      A_nute[8][0] = -1.3187;
      A_nute[8][1] = -0.00016;
      B_nute[8][0] = 0.5736;
      B_nute[8][1] = -0.00031;

      M_nute[9][0] = 0;
      M_nute[9][1] = 1;
      M_nute[9][2] = 0;
      M_nute[9][3] = 0;
      M_nute[9][4] = 0;
      A_nute[9][0] = 0.1426;
      A_nute[9][1] = -0.00034;
      B_nute[9][0] = 0.0054;
      B_nute[9][1] = -1e-05;

      M_nute[10][0] = 0;
      M_nute[10][1] = 1;
      M_nute[10][2] = 2;
      M_nute[10][3] = -2;
      M_nute[10][4] = 2;
      A_nute[10][0] = -0.0517;
      A_nute[10][1] = 0.00012;
      B_nute[10][0] = 0.0224;
      B_nute[10][1] = -6e-05;

      M_nute[11][0] = 0;
      M_nute[11][1] = -1;
      M_nute[11][2] = 2;
      M_nute[11][3] = -2;
      M_nute[11][4] = 2;
      A_nute[11][0] = 0.0217;
      A_nute[11][1] = -5e-05;
      B_nute[11][0] = -0.0095;
      B_nute[11][1] = 3e-05;

      M_nute[12][0] = 0;
      M_nute[12][1] = 0;
      M_nute[12][2] = 2;
      M_nute[12][3] = -2;
      M_nute[12][4] = 1;
      A_nute[12][0] = 0.0129;
      A_nute[12][1] = 1e-05;
      B_nute[12][0] = -0.007;
      B_nute[12][1] = 0;

      M_nute[13][0] = 2;
      M_nute[13][1] = 0;
      M_nute[13][2] = 0;
      M_nute[13][3] = -2;
      M_nute[13][4] = 0;
      A_nute[13][0] = 0.0048;
      A_nute[13][1] = 0;
      B_nute[13][0] = 0.0001;
      B_nute[13][1] = 0;

      M_nute[14][0] = 0;
      M_nute[14][1] = 0;
      M_nute[14][2] = 2;
      M_nute[14][3] = -2;
      M_nute[14][4] = 0;
      A_nute[14][0] = -0.0022;
      A_nute[14][1] = 0;
      B_nute[14][0] = 0;
      B_nute[14][1] = 0;

      M_nute[15][0] = 0;
      M_nute[15][1] = 2;
      M_nute[15][2] = 0;
      M_nute[15][3] = 0;
      M_nute[15][4] = 0;
      A_nute[15][0] = 0.0017;
      A_nute[15][1] = -1e-05;
      B_nute[15][0] = 0;
      B_nute[15][1] = 0;

      M_nute[16][0] = 0;
      M_nute[16][1] = 1;
      M_nute[16][2] = 0;
      M_nute[16][3] = 0;
      M_nute[16][4] = 1;
      A_nute[16][0] = -0.0015;
      A_nute[16][1] = 0;
      B_nute[16][0] = 0.0009;
      B_nute[16][1] = 0;

      M_nute[17][0] = 0;
      M_nute[17][1] = 2;
      M_nute[17][2] = 2;
      M_nute[17][3] = -2;
      M_nute[17][4] = 2;
      A_nute[17][0] = -0.0016;
      A_nute[17][1] = 1e-05;
      B_nute[17][0] = 0.0007;
      B_nute[17][1] = 0;

      M_nute[18][0] = 0;
      M_nute[18][1] = -1;
      M_nute[18][2] = 0;
      M_nute[18][3] = 0;
      M_nute[18][4] = 1;
      A_nute[18][0] = -0.0012;
      A_nute[18][1] = 0;
      B_nute[18][0] = 0.0006;
      B_nute[18][1] = 0;

      M_nute[19][0] = -2;
      M_nute[19][1] = 0;
      M_nute[19][2] = 0;
      M_nute[19][3] = 2;
      M_nute[19][4] = 1;
      A_nute[19][0] = -0.0006;
      A_nute[19][1] = 0;
      B_nute[19][0] = 0.0003;
      B_nute[19][1] = 0;

      M_nute[20][0] = 0;
      M_nute[20][1] = -1;
      M_nute[20][2] = 2;
      M_nute[20][3] = -2;
      M_nute[20][4] = 1;
      A_nute[20][0] = -0.0005;
      A_nute[20][1] = 0;
      B_nute[20][0] = 0.0003;
      B_nute[20][1] = 0;

      M_nute[21][0] = 2;
      M_nute[21][1] = 0;
      M_nute[21][2] = 0;
      M_nute[21][3] = -2;
      M_nute[21][4] = 1;
      A_nute[21][0] = 0.0004;
      A_nute[21][1] = 0;
      B_nute[21][0] = -0.0002;
      B_nute[21][1] = 0;

      M_nute[22][0] = 0;
      M_nute[22][1] = 1;
      M_nute[22][2] = 2;
      M_nute[22][3] = -2;
      M_nute[22][4] = 1;
      A_nute[22][0] = 0.0004;
      A_nute[22][1] = 0;
      B_nute[22][0] = -0.0002;
      B_nute[22][1] = 0;

      M_nute[23][0] = 1;
      M_nute[23][1] = 0;
      M_nute[23][2] = 0;
      M_nute[23][3] = -1;
      M_nute[23][4] = 0;
      A_nute[23][0] = -0.0004;
      A_nute[23][1] = 0;
      B_nute[23][0] = 0;
      B_nute[23][1] = 0;

      M_nute[24][0] = 2;
      M_nute[24][1] = 1;
      M_nute[24][2] = 0;
      M_nute[24][3] = -2;
      M_nute[24][4] = 0;
      A_nute[24][0] = 0.0001;
      A_nute[24][1] = 0;
      B_nute[24][0] = 0;
      B_nute[24][1] = 0;

      M_nute[25][0] = 0;
      M_nute[25][1] = 0;
      M_nute[25][2] = -2;
      M_nute[25][3] = 2;
      M_nute[25][4] = 1;
      A_nute[25][0] = 0.0001;
      A_nute[25][1] = 0;
      B_nute[25][0] = 0;
      B_nute[25][1] = 0;

      M_nute[26][0] = 0;
      M_nute[26][1] = 1;
      M_nute[26][2] = -2;
      M_nute[26][3] = 2;
      M_nute[26][4] = 0;
      A_nute[26][0] = -0.0001;
      A_nute[26][1] = 0;
      B_nute[26][0] = 0;
      B_nute[26][1] = 0;

      M_nute[27][0] = 0;
      M_nute[27][1] = 1;
      M_nute[27][2] = 0;
      M_nute[27][3] = 0;
      M_nute[27][4] = 2;
      A_nute[27][0] = 0.0001;
      A_nute[27][1] = 0;
      B_nute[27][0] = 0;
      B_nute[27][1] = 0;

      M_nute[28][0] = -1;
      M_nute[28][1] = 0;
      M_nute[28][2] = 0;
      M_nute[28][3] = 1;
      M_nute[28][4] = 1;
      A_nute[28][0] = 0.0001;
      A_nute[28][1] = 0;
      B_nute[28][0] = 0;
      B_nute[28][1] = 0;

      M_nute[29][0] = 0;
      M_nute[29][1] = 1;
      M_nute[29][2] = 2;
      M_nute[29][3] = -2;
      M_nute[29][4] = 0;
      A_nute[29][0] = -0.0001;
      A_nute[29][1] = 0;
      B_nute[29][0] = 0;
      B_nute[29][1] = 0;

      M_nute[30][0] = 0;
      M_nute[30][1] = 0;
      M_nute[30][2] = 2;
      M_nute[30][3] = 0;
      M_nute[30][4] = 2;
      A_nute[30][0] = -0.2274;
      A_nute[30][1] = -2e-05;
      B_nute[30][0] = 0.0977;
      B_nute[30][1] = -5e-05;

      M_nute[31][0] = 1;
      M_nute[31][1] = 0;
      M_nute[31][2] = 0;
      M_nute[31][3] = 0;
      M_nute[31][4] = 0;
      A_nute[31][0] = 0.0712;
      A_nute[31][1] = 1e-05;
      B_nute[31][0] = -0.0007;
      B_nute[31][1] = 0;

      M_nute[32][0] = 0;
      M_nute[32][1] = 0;
      M_nute[32][2] = 2;
      M_nute[32][3] = 0;
      M_nute[32][4] = 1;
      A_nute[32][0] = -0.0386;
      A_nute[32][1] = -4e-05;
      B_nute[32][0] = 0.02;
      B_nute[32][1] = 0;

      M_nute[33][0] = 1;
      M_nute[33][1] = 0;
      M_nute[33][2] = 2;
      M_nute[33][3] = 0;
      M_nute[33][4] = 2;
      A_nute[33][0] = -0.0301;
      A_nute[33][1] = 0;
      B_nute[33][0] = 0.0129;
      B_nute[33][1] = -1e-05;

      M_nute[34][0] = 1;
      M_nute[34][1] = 0;
      M_nute[34][2] = 0;
      M_nute[34][3] = -2;
      M_nute[34][4] = 0;
      A_nute[34][0] = -0.0158;
      A_nute[34][1] = 0;
      B_nute[34][0] = -0.0001;
      B_nute[34][1] = 0;

      M_nute[35][0] = -1;
      M_nute[35][1] = 0;
      M_nute[35][2] = 2;
      M_nute[35][3] = 0;
      M_nute[35][4] = 2;
      A_nute[35][0] = 0.0123;
      A_nute[35][1] = 0;
      B_nute[35][0] = -0.0053;
      B_nute[35][1] = 0;

      M_nute[36][0] = 0;
      M_nute[36][1] = 0;
      M_nute[36][2] = 0;
      M_nute[36][3] = 2;
      M_nute[36][4] = 0;
      A_nute[36][0] = 0.0063;
      A_nute[36][1] = 0;
      B_nute[36][0] = -0.0002;
      B_nute[36][1] = 0;

      M_nute[37][0] = 1;
      M_nute[37][1] = 0;
      M_nute[37][2] = 0;
      M_nute[37][3] = 0;
      M_nute[37][4] = 1;
      A_nute[37][0] = 0.0063;
      A_nute[37][1] = 1e-05;
      B_nute[37][0] = -0.0033;
      B_nute[37][1] = 0;

      M_nute[38][0] = -1;
      M_nute[38][1] = 0;
      M_nute[38][2] = 0;
      M_nute[38][3] = 0;
      M_nute[38][4] = 1;
      A_nute[38][0] = -0.0058;
      A_nute[38][1] = -1e-05;
      B_nute[38][0] = 0.0032;
      B_nute[38][1] = 0;

      M_nute[39][0] = -1;
      M_nute[39][1] = 0;
      M_nute[39][2] = 2;
      M_nute[39][3] = 2;
      M_nute[39][4] = 2;
      A_nute[39][0] = -0.0059;
      A_nute[39][1] = 0;
      B_nute[39][0] = 0.0026;
      B_nute[39][1] = 0;

      M_nute[40][0] = 1;
      M_nute[40][1] = 0;
      M_nute[40][2] = 2;
      M_nute[40][3] = 0;
      M_nute[40][4] = 1;
      A_nute[40][0] = -0.0051;
      A_nute[40][1] = 0;
      B_nute[40][0] = 0.0027;
      B_nute[40][1] = 0;

      M_nute[41][0] = 0;
      M_nute[41][1] = 0;
      M_nute[41][2] = 2;
      M_nute[41][3] = 2;
      M_nute[41][4] = 2;
      A_nute[41][0] = -0.0038;
      A_nute[41][1] = 0;
      B_nute[41][0] = 0.0016;
      B_nute[41][1] = 0;

      M_nute[42][0] = 2;
      M_nute[42][1] = 0;
      M_nute[42][2] = 0;
      M_nute[42][3] = 0;
      M_nute[42][4] = 0;
      A_nute[42][0] = 0.0029;
      A_nute[42][1] = 0;
      B_nute[42][0] = -0.0001;
      B_nute[42][1] = 0;

      M_nute[43][0] = 1;
      M_nute[43][1] = 0;
      M_nute[43][2] = 2;
      M_nute[43][3] = -2;
      M_nute[43][4] = 2;
      A_nute[43][0] = 0.0029;
      A_nute[43][1] = 0;
      B_nute[43][0] = -0.0012;
      B_nute[43][1] = 0;

      M_nute[44][0] = 2;
      M_nute[44][1] = 0;
      M_nute[44][2] = 2;
      M_nute[44][3] = 0;
      M_nute[44][4] = 2;
      A_nute[44][0] = -0.0031;
      A_nute[44][1] = 0;
      B_nute[44][0] = 0.0013;
      B_nute[44][1] = 0;

      M_nute[45][0] = 0;
      M_nute[45][1] = 0;
      M_nute[45][2] = 2;
      M_nute[45][3] = 0;
      M_nute[45][4] = 0;
      A_nute[45][0] = 0.0026;
      A_nute[45][1] = 0;
      B_nute[45][0] = -0.0001;
      B_nute[45][1] = 0;

      M_nute[46][0] = -1;
      M_nute[46][1] = 0;
      M_nute[46][2] = 2;
      M_nute[46][3] = 0;
      M_nute[46][4] = 1;
      A_nute[46][0] = 0.0021;
      A_nute[46][1] = 0;
      B_nute[46][0] = -0.001;
      B_nute[46][1] = 0;

      M_nute[47][0] = -1;
      M_nute[47][1] = 0;
      M_nute[47][2] = 0;
      M_nute[47][3] = 2;
      M_nute[47][4] = 0;
      A_nute[47][0] = 0.0016;
      A_nute[47][1] = 0;
      B_nute[47][0] = -0.0008;
      B_nute[47][1] = 0;

      M_nute[48][0] = 1;
      M_nute[48][1] = 0;
      M_nute[48][2] = 0;
      M_nute[48][3] = -2;
      M_nute[48][4] = 1;
      A_nute[48][0] = -0.0013;
      A_nute[48][1] = 0;
      B_nute[48][0] = 0.0007;
      B_nute[48][1] = 0;

      M_nute[49][0] = -1;
      M_nute[49][1] = 0;
      M_nute[49][2] = 2;
      M_nute[49][3] = 2;
      M_nute[49][4] = 1;
      A_nute[49][0] = -0.001;
      A_nute[49][1] = 0;
      B_nute[49][0] = 0.0005;
      B_nute[49][1] = 0;

      M_nute[50][0] = 1;
      M_nute[50][1] = 1;
      M_nute[50][2] = 0;
      M_nute[50][3] = -2;
      M_nute[50][4] = 0;
      A_nute[50][0] = -0.0007;
      A_nute[50][1] = 0;
      B_nute[50][0] = 0;
      B_nute[50][1] = 0;

      M_nute[51][0] = 0;
      M_nute[51][1] = 1;
      M_nute[51][2] = 2;
      M_nute[51][3] = 0;
      M_nute[51][4] = 2;
      A_nute[51][0] = 0.0007;
      A_nute[51][1] = 0;
      B_nute[51][0] = -0.0003;
      B_nute[51][1] = 0;

      M_nute[52][0] = 0;
      M_nute[52][1] = -1;
      M_nute[52][2] = 2;
      M_nute[52][3] = 0;
      M_nute[52][4] = 2;
      A_nute[52][0] = -0.0007;
      A_nute[52][1] = 0;
      B_nute[52][0] = 0.0003;
      B_nute[52][1] = 0;

      M_nute[53][0] = 1;
      M_nute[53][1] = 0;
      M_nute[53][2] = 2;
      M_nute[53][3] = 2;
      M_nute[53][4] = 2;
      A_nute[53][0] = -0.0008;
      A_nute[53][1] = 0;
      B_nute[53][0] = 0.0003;
      B_nute[53][1] = 0;

      M_nute[54][0] = 1;
      M_nute[54][1] = 0;
      M_nute[54][2] = 0;
      M_nute[54][3] = 2;
      M_nute[54][4] = 0;
      A_nute[54][0] = 0.0006;
      A_nute[54][1] = 0;
      B_nute[54][0] = 0;
      B_nute[54][1] = 0;

      M_nute[55][0] = 2;
      M_nute[55][1] = 0;
      M_nute[55][2] = 2;
      M_nute[55][3] = -2;
      M_nute[55][4] = 2;
      A_nute[55][0] = 0.0006;
      A_nute[55][1] = 0;
      B_nute[55][0] = -0.0003;
      B_nute[55][1] = 0;

      M_nute[56][0] = 0;
      M_nute[56][1] = 0;
      M_nute[56][2] = 0;
      M_nute[56][3] = 2;
      M_nute[56][4] = 1;
      A_nute[56][0] = -0.0006;
      A_nute[56][1] = 0;
      B_nute[56][0] = 0.0003;
      B_nute[56][1] = 0;

      M_nute[57][0] = 0;
      M_nute[57][1] = 0;
      M_nute[57][2] = 2;
      M_nute[57][3] = 2;
      M_nute[57][4] = 1;
      A_nute[57][0] = -0.0007;
      A_nute[57][1] = 0;
      B_nute[57][0] = 0.0003;
      B_nute[57][1] = 0;

      M_nute[58][0] = 1;
      M_nute[58][1] = 0;
      M_nute[58][2] = 2;
      M_nute[58][3] = -2;
      M_nute[58][4] = 1;
      A_nute[58][0] = 0.0006;
      A_nute[58][1] = 0;
      B_nute[58][0] = -0.0003;
      B_nute[58][1] = 0;

      M_nute[59][0] = 0;
      M_nute[59][1] = 0;
      M_nute[59][2] = 0;
      M_nute[59][3] = -2;
      M_nute[59][4] = 1;
      A_nute[59][0] = -0.0005;
      A_nute[59][1] = 0;
      B_nute[59][0] = 0.0003;
      B_nute[59][1] = 0;

      M_nute[60][0] = 1;
      M_nute[60][1] = -1;
      M_nute[60][2] = 0;
      M_nute[60][3] = 0;
      M_nute[60][4] = 0;
      A_nute[60][0] = 0.0005;
      A_nute[60][1] = 0;
      B_nute[60][0] = 0;
      B_nute[60][1] = 0;

      M_nute[61][0] = 2;
      M_nute[61][1] = 0;
      M_nute[61][2] = 2;
      M_nute[61][3] = 0;
      M_nute[61][4] = 1;
      A_nute[61][0] = -0.0005;
      A_nute[61][1] = 0;
      B_nute[61][0] = 0.0003;
      B_nute[61][1] = 0;

      M_nute[62][0] = 0;
      M_nute[62][1] = 1;
      M_nute[62][2] = 0;
      M_nute[62][3] = -2;
      M_nute[62][4] = 0;
      A_nute[62][0] = -0.0004;
      A_nute[62][1] = 0;
      B_nute[62][0] = 0;
      B_nute[62][1] = 0;

      M_nute[63][0] = 1;
      M_nute[63][1] = 0;
      M_nute[63][2] = -2;
      M_nute[63][3] = 0;
      M_nute[63][4] = 0;
      A_nute[63][0] = 0.0004;
      A_nute[63][1] = 0;
      B_nute[63][0] = 0;
      B_nute[63][1] = 0;

      M_nute[64][0] = 0;
      M_nute[64][1] = 0;
      M_nute[64][2] = 0;
      M_nute[64][3] = 1;
      M_nute[64][4] = 0;
      A_nute[64][0] = -0.0004;
      A_nute[64][1] = 0;
      B_nute[64][0] = 0;
      B_nute[64][1] = 0;

      M_nute[65][0] = 1;
      M_nute[65][1] = 1;
      M_nute[65][2] = 0;
      M_nute[65][3] = 0;
      M_nute[65][4] = 0;
      A_nute[65][0] = -0.0003;
      A_nute[65][1] = 0;
      B_nute[65][0] = 0;
      B_nute[65][1] = 0;

      M_nute[66][0] = 1;
      M_nute[66][1] = 0;
      M_nute[66][2] = 2;
      M_nute[66][3] = 0;
      M_nute[66][4] = 0;
      A_nute[66][0] = 0.0003;
      A_nute[66][1] = 0;
      B_nute[66][0] = 0;
      B_nute[66][1] = 0;

      M_nute[67][0] = 1;
      M_nute[67][1] = -1;
      M_nute[67][2] = 2;
      M_nute[67][3] = 0;
      M_nute[67][4] = 2;
      A_nute[67][0] = -0.0003;
      A_nute[67][1] = 0;
      B_nute[67][0] = 0.0001;
      B_nute[67][1] = 0;

      M_nute[68][0] = -1;
      M_nute[68][1] = -1;
      M_nute[68][2] = 2;
      M_nute[68][3] = 2;
      M_nute[68][4] = 2;
      A_nute[68][0] = -0.0003;
      A_nute[68][1] = 0;
      B_nute[68][0] = 0.0001;
      B_nute[68][1] = 0;

      M_nute[69][0] = -2;
      M_nute[69][1] = 0;
      M_nute[69][2] = 0;
      M_nute[69][3] = 0;
      M_nute[69][4] = 1;
      A_nute[69][0] = -0.0002;
      A_nute[69][1] = 0;
      B_nute[69][0] = 0.0001;
      B_nute[69][1] = 0;

      M_nute[70][0] = 3;
      M_nute[70][1] = 0;
      M_nute[70][2] = 2;
      M_nute[70][3] = 0;
      M_nute[70][4] = 2;
      A_nute[70][0] = -0.0003;
      A_nute[70][1] = 0;
      B_nute[70][0] = 0.0001;
      B_nute[70][1] = 0;

      M_nute[71][0] = 0;
      M_nute[71][1] = -1;
      M_nute[71][2] = 2;
      M_nute[71][3] = 2;
      M_nute[71][4] = 2;
      A_nute[71][0] = -0.0003;
      A_nute[71][1] = 0;
      B_nute[71][0] = 0.0001;
      B_nute[71][1] = 0;

      M_nute[72][0] = 1;
      M_nute[72][1] = 1;
      M_nute[72][2] = 2;
      M_nute[72][3] = 0;
      M_nute[72][4] = 2;
      A_nute[72][0] = 0.0002;
      A_nute[72][1] = 0;
      B_nute[72][0] = -0.0001;
      B_nute[72][1] = 0;

      M_nute[73][0] = -1;
      M_nute[73][1] = 0;
      M_nute[73][2] = 2;
      M_nute[73][3] = -2;
      M_nute[73][4] = 1;
      A_nute[73][0] = -0.0002;
      A_nute[73][1] = 0;
      B_nute[73][0] = 0.0001;
      B_nute[73][1] = 0;

      M_nute[74][0] = 2;
      M_nute[74][1] = 0;
      M_nute[74][2] = 0;
      M_nute[74][3] = 0;
      M_nute[74][4] = 1;
      A_nute[74][0] = 0.0002;
      A_nute[74][1] = 0;
      B_nute[74][0] = -0.0001;
      B_nute[74][1] = 0;

      M_nute[75][0] = 1;
      M_nute[75][1] = 0;
      M_nute[75][2] = 0;
      M_nute[75][3] = 0;
      M_nute[75][4] = 2;
      A_nute[75][0] = -0.0002;
      A_nute[75][1] = 0;
      B_nute[75][0] = 0.0001;
      B_nute[75][1] = 0;

      M_nute[76][0] = 3;
      M_nute[76][1] = 0;
      M_nute[76][2] = 0;
      M_nute[76][3] = 0;
      M_nute[76][4] = 0;
      A_nute[76][0] = 0.0002;
      A_nute[76][1] = 0;
      B_nute[76][0] = 0;
      B_nute[76][1] = 0;

      M_nute[77][0] = 0;
      M_nute[77][1] = 0;
      M_nute[77][2] = 2;
      M_nute[77][3] = 1;
      M_nute[77][4] = 2;
      A_nute[77][0] = 0.0002;
      A_nute[77][1] = 0;
      B_nute[77][0] = -0.0001;
      B_nute[77][1] = 0;

      M_nute[78][0] = -1;
      M_nute[78][1] = 0;
      M_nute[78][2] = 0;
      M_nute[78][3] = 0;
      M_nute[78][4] = 2;
      A_nute[78][0] = 0.0001;
      A_nute[78][1] = 0;
      B_nute[78][0] = -0.0001;
      B_nute[78][1] = 0;

      M_nute[79][0] = 1;
      M_nute[79][1] = 0;
      M_nute[79][2] = 0;
      M_nute[79][3] = -4;
      M_nute[79][4] = 0;
      A_nute[79][0] = -0.0001;
      A_nute[79][1] = 0;
      B_nute[79][0] = 0;
      B_nute[79][1] = 0;

      M_nute[80][0] = -2;
      M_nute[80][1] = 0;
      M_nute[80][2] = 2;
      M_nute[80][3] = 2;
      M_nute[80][4] = 2;
      A_nute[80][0] = 0.0001;
      A_nute[80][1] = 0;
      B_nute[80][0] = -0.0001;
      B_nute[80][1] = 0;

      M_nute[81][0] = -1;
      M_nute[81][1] = 0;
      M_nute[81][2] = 2;
      M_nute[81][3] = 4;
      M_nute[81][4] = 2;
      A_nute[81][0] = -0.0002;
      A_nute[81][1] = 0;
      B_nute[81][0] = 0.0001;
      B_nute[81][1] = 0;

      M_nute[82][0] = 2;
      M_nute[82][1] = 0;
      M_nute[82][2] = 0;
      M_nute[82][3] = -4;
      M_nute[82][4] = 0;
      A_nute[82][0] = -0.0001;
      A_nute[82][1] = 0;
      B_nute[82][0] = 0;
      B_nute[82][1] = 0;

      M_nute[83][0] = 1;
      M_nute[83][1] = 1;
      M_nute[83][2] = 2;
      M_nute[83][3] = -2;
      M_nute[83][4] = 2;
      A_nute[83][0] = 0.0001;
      A_nute[83][1] = 0;
      B_nute[83][0] = -0.0001;
      B_nute[83][1] = 0;

      M_nute[84][0] = 1;
      M_nute[84][1] = 0;
      M_nute[84][2] = 2;
      M_nute[84][3] = 2;
      M_nute[84][4] = 1;
      A_nute[84][0] = -0.0001;
      A_nute[84][1] = 0;
      B_nute[84][0] = 0.0001;
      B_nute[84][1] = 0;

      M_nute[85][0] = -2;
      M_nute[85][1] = 0;
      M_nute[85][2] = 2;
      M_nute[85][3] = 4;
      M_nute[85][4] = 2;
      A_nute[85][0] = -0.0001;
      A_nute[85][1] = 0;
      B_nute[85][0] = 0.0001;
      B_nute[85][1] = 0;

      M_nute[86][0] = -1;
      M_nute[86][1] = 0;
      M_nute[86][2] = 4;
      M_nute[86][3] = 0;
      M_nute[86][4] = 2;
      A_nute[86][0] = 0.0001;
      A_nute[86][1] = 0;
      B_nute[86][0] = 0;
      B_nute[86][1] = 0;

      M_nute[87][0] = 1;
      M_nute[87][1] = -1;
      M_nute[87][2] = 0;
      M_nute[87][3] = -2;
      M_nute[87][4] = 0;
      A_nute[87][0] = 0.0001;
      A_nute[87][1] = 0;
      B_nute[87][0] = 0;
      B_nute[87][1] = 0;

      M_nute[88][0] = 2;
      M_nute[88][1] = 0;
      M_nute[88][2] = 2;
      M_nute[88][3] = -2;
      M_nute[88][4] = 1;
      A_nute[88][0] = 0.0001;
      A_nute[88][1] = 0;
      B_nute[88][0] = -0.0001;
      B_nute[88][1] = 0;

      M_nute[89][0] = 2;
      M_nute[89][1] = 0;
      M_nute[89][2] = 2;
      M_nute[89][3] = 2;
      M_nute[89][4] = 2;
      A_nute[89][0] = -0.0001;
      A_nute[89][1] = 0;
      B_nute[89][0] = 0;
      B_nute[89][1] = 0;

      M_nute[90][0] = 1;
      M_nute[90][1] = 0;
      M_nute[90][2] = 0;
      M_nute[90][3] = 2;
      M_nute[90][4] = 1;
      A_nute[90][0] = -0.0001;
      A_nute[90][1] = 0;
      B_nute[90][0] = 0;
      B_nute[90][1] = 0;

      M_nute[91][0] = 0;
      M_nute[91][1] = 0;
      M_nute[91][2] = 4;
      M_nute[91][3] = -2;
      M_nute[91][4] = 2;
      A_nute[91][0] = 0.0001;
      A_nute[91][1] = 0;
      B_nute[91][0] = 0;
      B_nute[91][1] = 0;

      M_nute[92][0] = 3;
      M_nute[92][1] = 0;
      M_nute[92][2] = 2;
      M_nute[92][3] = -2;
      M_nute[92][4] = 2;
      A_nute[92][0] = 0.0001;
      A_nute[92][1] = 0;
      B_nute[92][0] = 0;
      B_nute[92][1] = 0;

      M_nute[93][0] = 1;
      M_nute[93][1] = 0;
      M_nute[93][2] = 2;
      M_nute[93][3] = -2;
      M_nute[93][4] = 0;
      A_nute[93][0] = -0.0001;
      A_nute[93][1] = 0;
      B_nute[93][0] = 0;
      B_nute[93][1] = 0;

      M_nute[94][0] = 0;
      M_nute[94][1] = 1;
      M_nute[94][2] = 2;
      M_nute[94][3] = 0;
      M_nute[94][4] = 1;
      A_nute[94][0] = 0.0001;
      A_nute[94][1] = 0;
      B_nute[94][0] = 0;
      B_nute[94][1] = 0;

      M_nute[95][0] = -1;
      M_nute[95][1] = -1;
      M_nute[95][2] = 0;
      M_nute[95][3] = 2;
      M_nute[95][4] = 1;
      A_nute[95][0] = 0.0001;
      A_nute[95][1] = 0;
      B_nute[95][0] = 0;
      B_nute[95][1] = 0;

      M_nute[96][0] = 0;
      M_nute[96][1] = 0;
      M_nute[96][2] = -2;
      M_nute[96][3] = 0;
      M_nute[96][4] = 1;
      A_nute[96][0] = -0.0001;
      A_nute[96][1] = 0;
      B_nute[96][0] = 0;
      B_nute[96][1] = 0;

      M_nute[97][0] = 0;
      M_nute[97][1] = 0;
      M_nute[97][2] = 2;
      M_nute[97][3] = -1;
      M_nute[97][4] = 2;
      A_nute[97][0] = -0.0001;
      A_nute[97][1] = 0;
      B_nute[97][0] = 0;
      B_nute[97][1] = 0;

      M_nute[98][0] = 0;
      M_nute[98][1] = 1;
      M_nute[98][2] = 0;
      M_nute[98][3] = 2;
      M_nute[98][4] = 0;
      A_nute[98][0] = -0.0001;
      A_nute[98][1] = 0;
      B_nute[98][0] = 0;
      B_nute[98][1] = 0;

      M_nute[99][0] = 1;
      M_nute[99][1] = 0;
      M_nute[99][2] = -2;
      M_nute[99][3] = -2;
      M_nute[99][4] = 0;
      A_nute[99][0] = -0.0001;
      A_nute[99][1] = 0;
      B_nute[99][0] = 0;
      B_nute[99][1] = 0;

      M_nute[100][0] = 0;
      M_nute[100][1] = -1;
      M_nute[100][2] = 2;
      M_nute[100][3] = 0;
      M_nute[100][4] = 1;
      A_nute[100][0] = -0.0001;
      A_nute[100][1] = 0;
      B_nute[100][0] = 0;
      B_nute[100][1] = 0;

      M_nute[101][0] = 1;
      M_nute[101][1] = 1;
      M_nute[101][2] = 0;
      M_nute[101][3] = -2;
      M_nute[101][4] = 1;
      A_nute[101][0] = -0.0001;
      A_nute[101][1] = 0;
      B_nute[101][0] = 0;
      B_nute[101][1] = 0;

      M_nute[102][0] = 1;
      M_nute[102][1] = 0;
      M_nute[102][2] = -2;
      M_nute[102][3] = 2;
      M_nute[102][4] = 0;
      A_nute[102][0] = -0.0001;
      A_nute[102][1] = 0;
      B_nute[102][0] = 0;
      B_nute[102][1] = 0;

      M_nute[103][0] = 2;
      M_nute[103][1] = 0;
      M_nute[103][2] = 0;
      M_nute[103][3] = 2;
      M_nute[103][4] = 0;
      A_nute[103][0] = 0.0001;
      A_nute[103][1] = 0;
      B_nute[103][0] = 0;
      B_nute[103][1] = 0;

      M_nute[104][0] = 0;
      M_nute[104][1] = 0;
      M_nute[104][2] = 2;
      M_nute[104][3] = 4;
      M_nute[104][4] = 2;
      A_nute[104][0] = -0.0001;
      A_nute[104][1] = 0;
      B_nute[104][0] = 0;
      B_nute[104][1] = 0;

      M_nute[105][0] = 0;
      M_nute[105][1] = 1;
      M_nute[105][2] = 0;
      M_nute[105][3] = 1;
      M_nute[105][4] = 0;
      A_nute[105][0] = 0.0001;
      A_nute[105][1] = 0;
      B_nute[105][0] = 0;
      B_nute[105][1] = 0;

  }
}// end of class T_Nutate
