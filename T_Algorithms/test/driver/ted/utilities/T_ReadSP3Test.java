/*
 * T_ReadSP3Test.java
 * JUnit based test
 *
 * Created on June 3, 2005, 1:32 PM
 */

package driver.ted.utilities;

import junit.framework.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.*;

/**
 *
 * @author tdriver
 */
public class T_ReadSP3Test extends TestCase {
    
    public T_ReadSP3Test(String testName) {
        super(testName);
    }

    protected void setUp() throws Exception {
    }

    protected void tearDown() throws Exception {
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(T_ReadSP3Test.class);
        
        return suite;
    }

    /**
     * Test of getData method, of class driver.ted.utilities.T_ReadSP3.
     */
    public void testGetData() {
        System.out.println("testGetData");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDataForPRN method, of class driver.ted.utilities.T_ReadSP3.
     */
    public void testGetDataForPRN() {
        System.out.println("testGetDataForPRN");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /**
     * Test of getDataForTime method, of class driver.ted.utilities.T_ReadSP3.
     */
    public void testGetDataForTime() {
        System.out.println("testGetDataForTime");
        
        T_ReadSP3 sp3 = new T_ReadSP3("C:\\Documents and Settings\\tdriver\\My Documents\\dev\\cpp\\NIMA\\EPHEMERI\\Debug\\NIM12514.EPH");
    T_JulianDate t = new T_JulianDate(1,1,2004,4,15,0);
    int prn = 10;
    
    // test getting from specific time
    double[][] timedata = sp3.getDataForTime(t.getJulianDate());//2453005.5);
    for (int i = 0; i < timedata.length; i++){
        for (int q = 0; q < 9; q++){
            System.out.println("" + timedata[i][q] );
        }
    }
    }

    /**
     * Test of main method, of class driver.ted.utilities.T_ReadSP3.
     */
    public void testMain() {
        System.out.println("testMain");
        
        // TODO add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }
    
}
