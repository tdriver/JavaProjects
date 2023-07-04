package driver.ted.utilities;

/** <U>Title:</U> T_EarthAtmosphere<br>
 * <U>Description:</U>  This class will provide tropospheric and ionospheric corrections
 * for GPS Signals.
 * Not yet ported from C++.<br>
 * <U>Copyright:</U> (c) 2001 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */

public class T_EarthAtmosphere {

    /** Not implemented yet
     */    
  public T_EarthAtmosphere() {
  }
  
//  public String toString(){
//   return "This method not implemented yet.";
//  }
}



/*
// Class Earth_Atmosphere --------------------------------------------------------------------

Earth_Atmosphere::Earth_Atmosphere(){
  mstype ms;

  // initialize MS time offsets
  ms = HAW;
  offset[ms] = 86400.0 - 10*3600.0; // 10 hours later than UT
  ms = KWAJ;
  offset[ms] = 12*3600.0; // 12 hours ahead of UT
  ms = DIEGO;
  offset[ms] = 6*3600.0; // 6 hours ahead of UT
  ms = ANSC;
  offset[ms] = 0.0; // equal to UT
  ms = COSP;
  offset[ms] = 86400.0 - 6*3600.0; // 6 hours later than UT
  ms = CAPE;
  offset[ms] = 86400.0 - 4*3600.0; // 4 hours later than UT

  // Initialize Tropo data
  ms = HAW;
  Ps[ms] = 105.183;
  Td[ms] = 18.15;
  Tc[ms] = 24.9083;
  hmm[ms] = 6375696.75;

  ms = KWAJ;
  Ps[ms] = 106.100;
  Td[ms] = 23.958;
  Tc[ms] = 27.925;
  hmm[ms] = 6377688.14;

  ms = DIEGO;
  Ps[ms] = 106.025;
  Td[ms] = 23.225;
  Tc[ms] = 27.225;
  hmm[ms] = 6377735.29;

  ms = ANSC;
  Ps[ms] = 104.6083;
  Td[ms] = 19.2667;
  Tc[ms] = 25.3833;
  hmm[ms] = 6377835.03;

  ms = COSP;
  Ps[ms] = 85.2083;
  Td[ms] = -3.1667;
  Tc[ms] = 9.175;
  hmm[ms] = 6371675.29;

  ms = CAPE;
  Ps[ms] = 100.000;
  Td[ms] = 18;
  Tc[ms] = 25;
  hmm[ms] = 6373282.46;

} // end Earth_Atmosphere::Earth_Atmosphere()

Vector Earth_Atmosphere::Iono_Error(Vector Tecef, Vector Recef, double t){
  double TEC, F;
  Earth_Coords D;
  Vector zel(2), Error(2);

  // calculate the az and el of the transmitter;
  zel = D.azel(Tecef, Recef);

  // Calculate the obliquity function
  F = 1.0 + 16*pow((0.53-5.55e-3*(90-zel[1]*DtR)),3);

  // Calculate te TEC
  TEC = F*1e18*(0.55 - 0.45*cos(2.0*PI*t/86400.0));

  // calculate the iono errors
  Error[0] = 40.31*TEC/(2.0*C*L1*L1);
  Error[1] = 40.31*TEC/(2.0*C*L2*L2);

  return Error;
}

double Earth_Atmosphere::Tropo_Error(Vector Recef, mstype MS){
  double hma, dzd, dzw, ex, Fd, Fw, dt;
  Earth_Coords D;
  Vector G, zel;

  // assign hma, the antenna phase center height
  G = D.RMS(MS);
	hma = G.mag();

  // Calculate delta Z dry
  dzd = 2.312e-2*Ps[MS]/(Tc[MS] = 273.15);
  dzd *= Tc[MS] + 269.04 + 5.0*(hmm[MS] - hma)/148.98;

  // Calculate delta Z wet
  dzw = 1 + 5.0*(hmm[MS] - hma)/(13268 - 97.96*Tc[MS]);
  dzw *= (13268 - 97.96*Tc[MS])*0.0746*6.11/pow((Tc[MS] + 273.15),2.0);
  ex = 7.5*Td[MS]/pow((Td[MS] + 237.3),2.0);
  dzw *= pow(10,ex);

  // get the az and el
  zel = D.azel(Recef, G);

  // Calculate the dry obliquity
  Fd = hma*cos(zel[1])/(hmm[MS] + 0.15*(40082.0 + 148.98*Tc[MS]));
  Fd = 1 - Fd*Fd;
  if (Fd <= 0.0){
    cout << "Dry Obliquity is <= 0 in Tropo_Error!" << endl;
    exit(1);
  }
  Fd = 1/sqrt(Fd);

  // Calculate the wet obliquity
  Fw = hma*cos(zel[1])/(hmm[MS] + 0.15*(13268 - 97.96*Tc[MS]));
  Fw = 1 - Fw*Fw;
  if (Fw <= 0.0){
    cout << "Wet Obliquity is <= 0 in Tropo_Error!" << endl;
    exit(1);
  }
  Fw = 1/sqrt(Fw);

  // Calculate the tropospheric Delay
  dt = (dzd*Fd + dzw*Fw)/C;

  return dt;

}

double Earth_Atmosphere::time_offset(mstype MS){
  return offset[MS];
}
// end of class Earth_Atmosphere
*/