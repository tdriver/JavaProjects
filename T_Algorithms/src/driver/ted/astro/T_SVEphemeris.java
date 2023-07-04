/*
 * T_SVEphemeris.java
 *
 * Created on October 1, 2002, 8:00 AM
 */

package driver.ted.astro;

import java.io.Serializable;
import driver.ted.utilities.Almanac.T_ReadAlmanac;
import driver.ted.utilities.T_GPSTime;
import driver.ted.utilities.T_Vector;

/** T_SVEphemeris 
  * Title:       T_SVEphemeris
 * Description:  T_SVEphemeris calculates an SV ephemeris based upon the GPS SEM
 * Almanac. The coordinates calculated are the cartesian coordinates in
 * the ECEF frame.
 * Copyright:    (c) 2002 - 2007
 * @author Ted Driver
 * @version 1.3
 * Change from 1.2 to 1.3 - (Feb 2007)
 *Changed the way tk is calculated - based on further review of truth data
 */
public class T_SVEphemeris implements Serializable {
    
    T_ReadAlmanac alm;
    double[][] svcoords = new double[33][6]; // changed to 6 from 3 to account for velocities
    
    boolean goodToGo = false;
    
    // Earth's Mu value
    static double Mu = 3.986005e14; // meters^3/sec^2
    
    // Omega Dot for the Earth
    static double Earth_Odot = 7.2921151467e-5; // radians/sec
    
    // initial value of inclination for the GPS orbits
    static double iZero = 0.94247779607694; //0.3*Math.PI;
    
    /** Creates a new instance of T_SVEphemeris when supplied an almanac file name.
     * @param filename The SEM alnamac filename to open and read
     */
    public T_SVEphemeris(String filename) {
        // get the almanac filename
        alm = new T_ReadAlmanac(filename);
        goodToGo = alm.getAlmanacReadStatus();
    }
    
    /** Returns the double array of coordinates for each SV
     * Returns all 32 rows of data, PRNs without data will have zeros
     * in their fields
     * the array should be accessed like this:
     * data[PRN][0] = PRN X coordinate
     * data[PRN][1] = PRN Y coordinate
     * data[PRN][2] = PRN Z coordinate
     * data[PRN][3] = PRN X velocity
     * data[PRN][4] = PRN Y velocity
     * data[PRN][5] = PRN Z velocity
     * @param gpsTime The time of week to use to evaluate the XYZ coordinates
     * @param weekNumber The week number to use to evaluate the coordinates
     * @return The two-dimensional double array of data of the XYZ coordinates
     * for each PRN (1-32)
     */    
    public double[][] getXYZ(long gpsTime, int weekNumber){
        if(goodToGo){ 
            //System.out.println("Time\tPRN\tTk");
            for (int i = 1; i <= 32; i++){
               calculateOrbit(i,gpsTime,weekNumber);
            }
            // hardcoded outages for 2002 /256 al3 almanac only
//            svcoords[12][0] = 0.0;svcoords[12][1] = 0.0;svcoords[12][2] = 0.0;svcoords[12][3] = 0.0;
//            svcoords[16][0] = 0.0;svcoords[16][1] = 0.0;svcoords[16][2] = 0.0;svcoords[16][3] = 0.0;
//            svcoords[19][0] = 0.0;svcoords[19][1] = 0.0;svcoords[19][2] = 0.0;svcoords[19][3] = 0.0;
            return svcoords;
        }else{
            return new double[33][6];
        }
    }
    
        
    private void calculateOrbit(int prn, long gpsTime, int weekNumber){
        double A = 0.0;
        double n = 0.0;
        double nZero = 0.0;
        double tk = 0.0;
        double Mk = 0.0;
        double vk = 0.0;
        double Ek = 0.0;
        double phik = 0.0;
        double uk = 0.0;
        double rk = 0.0;
        double ik = 0.0;
        double omegak = 0.0;
        
        T_GPSTime gpst = new T_GPSTime(weekNumber, (int)gpsTime);
        
       
        A = alm.getSqrtSemiMajorAxis(prn)*alm.getSqrtSemiMajorAxis(prn);
        nZero = Math.sqrt(Mu)/Math.pow(A,1.5);
        
        // calculate tk
        //tk = (gpsTime - (alm.getAFZero(prn) + alm.getGPSTOA()) )/(1.0 + alm.getAFOne(prn));
        //tk = driver.ted.utilities.T_Utilities.normalize(-302400, 302400, tk);
        int weekDifference = gpst.getGPSWeek()%1024 - alm.getGPSWeek();
        tk = weekDifference*604800 + (gpsTime - alm.getGPSTOA());
        //System.out.print(gpsTime + "\t");
        //System.out.print(prn + "\t");
        //System.out.println(tk);        
        n = nZero; // no delta n for almanacs
        
        Mk = alm.getMeanAnomaly(prn) + n*tk;
        double e = alm.getEccentricity(prn);
       
        //Iterate to find Ek using Meeus method
        Ek = calculateEk(Mk,e, 1e-12); // 1e-12 tolerance        
       
        double sinvk = Math.sqrt(1.0 - e*e)*Math.sin(Ek)/(1.0 - e*Math.cos(Ek));
        double cosvk = (Math.cos(Ek) - e)/(1.0 - e*Math.cos(Ek));
        
        vk = Math.atan2(sinvk,cosvk);
        
        phik = vk + alm.getOmega(prn); 
        
        uk = phik; // no delta uk here, almanacs
        
        rk = A*(1.0 - e*Math.cos(Ek)); // no delta r for almanacs
        
        // check how to add this to get the correct inclination
        ik = iZero + alm.getInclinationOffset(prn);
        
        double xkPrime = rk*Math.cos(uk);
        double ykPrime = rk*Math.sin(uk);
        
        // check units on this equation
        omegak = alm.getOmegaZero(prn) + (alm.getOmegaDot(prn) - Earth_Odot)*tk - Earth_Odot*alm.getGPSTOA();
        //System.out.println("Omegak = " + omegak);
        svcoords[prn][0] = xkPrime*Math.cos(omegak) - ykPrime*Math.cos(ik)*Math.sin(omegak);
        svcoords[prn][1] = xkPrime*Math.sin(omegak) + ykPrime*Math.cos(ik)*Math.cos(omegak);
        svcoords[prn][2] = ykPrime*Math.sin(ik);   
        
        
        // OPEN SOURCE version for vel calculation
        // taken from the gpstk project file AlmOrbit.cpp
        // from: http://www.gpstk.org/bin/view/Documentation/WebHome
        // their LGPL license allows us to use and redistribute freely.
        // I'm using this implementation because it is slightly faster that Vince's code above
        // (just my guess)
        // Ted D. Sept 2007
//        double dek, dlk, div, domk, duv, drv, dxp, dyp, velx, vely, velz;
//        double r = rk;
//        double sinc = Math.sin(ik);
//        double cinc = Math.cos(ik);
//        double sinea = Math.sin(Ek);
//        double cosea = Math.cos(Ek);
//        double san = Math.sin(omegak);
//        double can = Math.cos(omegak);
//        double cosu = Math.cos(phik);
//        double sinu = Math.sin(phik);
//        double xip = r * cosu;
//        double yip = r * sinu;
//        dek = n * A / r;
//        dlk = Math.sqrt(Mu * A * (1 - e * e)) / r / r;
//        div = 0;
//        domk = alm.getOmegaDot(prn) - Earth_Odot;
//        duv = dlk;
//        drv = A * e * dek * Math.sin(Ek);
//
//        dxp = drv * cosu - r * sinu * duv;
//        dyp = drv * sinu + r * cosu * duv;
//
//        velx = dxp * can - xip * san * domk - dyp * cinc * san + yip * (sinc * san * div - cinc * can * domk);
//        vely = dxp * san + xip * can * domk + dyp * cinc * can - yip * (sinc * can * div + cinc * san * domk);
//        velz = dyp * sinc + yip * cinc * div;
        
        // Vince Coppola's velocity algorithm
        T_Vector pos = new T_Vector(svcoords[prn][0],svcoords[prn][1], svcoords[prn][2]);
        // create trig variables
        double CosOmegak = Math.cos(omegak);
        double SinOmegak = Math.sin(omegak);
        double CosIk = Math.cos(ik);
        double SinIk = Math.sin(ik);
        
        double rdot = (e * n * A * A) / rk * Math.sin(Ek);
        double hr = (n * Math.sqrt(1 - e * e) * A * A) / rk;

        // * find current unit vector along angular mom vector */

        T_Vector unit_mom = new T_Vector(SinIk * SinOmegak,-SinIk * CosOmegak, CosIk);

        //* find current unit_r vector */
        T_Vector unit_r = pos.divide(rk);
        //Cartesian unit_r = pos.Divide(rk);

                //* find current unit vector defining unit_theta */
        T_Vector unit_theta = unit_mom.cross(unit_r);

        //* find current inertial velocity components */
        T_Vector vel_r = unit_r.multiply(rdot);
        T_Vector vel_theta = unit_theta.multiply(hr);
        T_Vector cbf_inertVel = vel_r.plus(vel_theta);

        //* find relative velocity */

        T_Vector angVel = new T_Vector(0, 0, Earth_Odot);
        T_Vector tempVel = angVel.cross(pos);
        T_Vector vel = cbf_inertVel.subtract(tempVel);    
       
        svcoords[prn][3] = vel.getElement(0);
        svcoords[prn][4] = vel.getElement(1);
        svcoords[prn][5] = vel.getElement(2);
    }
    
    private double calculateEk(double M, double e, double tol){
        // Meeus Method from page 187 of Astronomical Algorithms
        double E0 = M, E1 = 0.0;
        double correction = Double.MAX_VALUE;
        int iterations = 0;
        while(Math.abs(correction)  > tol && iterations < 25){
            correction = (M + e*Math.sin(E0) - E0)/(1.0 - e*Math.cos(E0));
            //System.out.println("\t\tCorrection: " + correction);
            E1 = E0 + correction;
            E0 = E1;
            iterations++;
        }
        //System.out.println("\t\tIterations: " + iterations);
        return E1;
    }
    
    /** Use this to test the class
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        T_SVEphemeris eph = new T_SVEphemeris("C:\\source\\depot\\Astrolib\\Development\\Aquarius\\Tests\\Navigation.Tests\\NavLibPrototype\\Run Definitions\\RunData\\almanac_sem_week0407_233472.al3");
        eph.getXYZ(900,1431);
        
    }
    
}
