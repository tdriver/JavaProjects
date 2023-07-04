package driver.ted.utilities;

import java.text.DecimalFormat;
import java.io.Serializable;

/**
 * <U>Title:</U> T_Vector<br>
 * <U>Description:</U> This class provides the scientific vector functionality.
 * The precision variable is used for display purposes, the actual value used in calculations is the full
 * value allowed by a double.<br>
 * <U>Copyright:</U> (c) 2002 - 2006<br>
 * <U>Change History:</U><br>
 * @author Ted Driver
 * @version 1.1
 */

public class T_Vector implements Serializable{
  private double vector[];
  private int vector_size;
  private int vector_precision;

/**
 * Default Constructor.
 * The size of the Vector is set to 3, indexed from 0 to 2 [0,1,2]
 * The precision of the vector is set to 3 decimal places.
 */
  public T_Vector() {
    vector_size = 3; // default size
    vector_precision = 3; // three decimal places by default
    vector = new double[3];
  }
  /**
   * Constructor specifying an integer size.
   * A Vector will be allocated of the specified size.
   * Size must be greater than zero.
   * The precision of the vector is set to 3 decimal places.
   * @param size The size of the vector, otherwise known as the length.
   * The returned vector will have n elements indexed from 0 to size-1.
   */
  public T_Vector(int size){
    // specify size constructor
    vector_size = size;
    vector_precision = 3; // three decimal places by default
    vector = new double[size];
  }
  /**
   * Constructor specifying an integer size and an integer precision.
   * The returned vector will have the size specified by <b>size</b>.
   * Size must be greater than zero.
   * The returned vector will have a precision specified by <b>precision</b> and <b>precision</b>
   * must be greater than 1 and less than or equal to 15.
   * @param size The size of the vector, otherwise known as the length.
   * The returned vector will have <b>size</b> elements indexed from 0 to <b>size</b>-1.
   * @param precision The precision of the vector; the number of places to the
   * right of the decimal point.
   */
  public T_Vector(int size, int precision){
    // specify size constructor
    vector_size = size;
    vector_precision = precision; // user defined size and precision
    vector = new double[size];
  }

  /**
   * Constructor specifying three doubles with which to initialze a three-dimensional vector.
   * The returned vector will have a size of 3.
   * The returned vector will have a precision of 3 decimal places.
   * @param x The x value of the vector, indexed by 0.
   * @param y The y value of the vector, indexed by 1.
   * @param z The z value of the vector, indexed by 2.
   */
  public T_Vector (double x, double y, double z){
    // specify a 3d vector and assign values
    vector_size = 3;
    vector_precision = 3; // three decimal places by default
    vector = new double[3];
    vector[0] = x;
    vector[1] = y;
    vector[2] = z;
  }

  /**
   * Constructor specifying initial values and a precision for a vector.
   * The returned vector will have a size of 3.
   * The returned vector will have a precision specified by <b>precision</b> and <b>precision</b>
   * must be greater than 1 and less than or equal to 15.
   * @param x The x value of the vector, indexed by 0.
   * @param y The y value of the vector, indexed by 1.
   * @param z The z value of the vector, indexed by 2.
   * @param precision The number of decimal places required for this vector's elements.
   */
  public T_Vector (double x, double y, double z, int precision){

    try{
      if(precision >= 1 && precision <= 15){
        vector_size = 3;
        vector_precision = precision;
        vector = new double[3];
        vector[0] = x;
        vector[1] = y;
        vector[2] = z;
      }else{
        throw new NumberFormatException();
      }
    }catch(NumberFormatException nfe){
      String s = "Precision value must be between 1 and 15!";
      javax.swing.JOptionPane.showMessageDialog(null,s,
           "Vector Initialization error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
    }// end try/catch

  }// end constructor

  /**
   * Returns the size of the vector.
   * The returned int will indicate the number of elements in the vector, indexed from
   * 0 to getSize() - 1
   * @return int The size of the vector
   */
  public int getSize(){
    return vector_size;
  }

  /**
   * Accessor function to return an element at the specified index.
   * The returned double is the value stored at <b>index</b>
   * @param index The index in the vector array for the element to be returned
   * @return double The value at index <b>index</b>
   */
  public double getElement(int index){
    return vector[index];
  }

  /**
   * Accessor function to return the precision value for the vector.
   * The returned int will be from 1 to 15 inclusive.
   * @return int The precision value for this vector
   *
   */
  public int getPrecision(){
    return vector_precision;
  }

  /** Method to set an individual element within the vector.
   * The element specified by <b>index</b> will be set to the value specified by
   * <b>value</b>
   * @param index The index in the vector for which the value will be changed
   * @param value The value to set at element <b>index</b>
   * @throws ArrayIndexOutOfBoundsException The index must be between 0 and getSize() - 1
   */
  public void setElement(int index, double value) throws ArrayIndexOutOfBoundsException{
   try {
      // check that index is within the correct range for the vector
      if ((index >= 0) && (index <= (vector_size - 1)) ){
        // index within the correct range for the vector
        vector[index] = value;
      }else{
        String error = "Index = " + index + ", Must be between 0 and " + (vector_size -1);
        throw new ArrayIndexOutOfBoundsException(error);
      }
   }catch(ArrayIndexOutOfBoundsException ai){
    String s = "Vector Index out of range: " + ai.toString();
      javax.swing.JOptionPane.showMessageDialog(null,s,
           "Vector Index Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
   }
  }

  /**
   * Method to display the vector in convienent notation.
   * @return  The vector; [x,y,z,...]
   */
  public String toString(){
    StringBuffer s = new StringBuffer("[");
    DecimalFormat nf = new DecimalFormat(getPrecisionString());
    for (int i = 0; i < vector_size; i ++){
     s.append(nf.format(vector[i]));
     if( i < (vector_size - 1) )
      s.append(", ");
    }
    s.append("]");
    return s.toString();
  }

  /**
   * Addition function for vectors
   * Usage: v3 = v1 + v2 is implemented as v3 = v1.plus(v2)
   * <p>v3[0] = v1[0] + v2[0]</p>
   * <p>v3[1] = v1[1] + v2[1] etc.</p?
   * @param v The vector to add
   * @return The resultant T_Vector
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size
   */
  public T_Vector plus(T_Vector v) throws ArrayIndexOutOfBoundsException {
    try{
      if (v.getSize() != vector_size){
        String error = "Size 1 = " + vector_size + " Size 2 = " + v.getSize();
        throw new ArrayIndexOutOfBoundsException(error);
      }else{
        int new_precision = Math.min(v.getPrecision(), vector_precision);
        T_Vector out = new T_Vector(v.getSize(), new_precision);
        double temp;
        for (int i = 0; i < v.getSize(); i++){
          temp = vector[i];
          temp += v.getElement(i);
          out.setElement(i, temp);
        }
        return out;
     }
    }catch(ArrayIndexOutOfBoundsException ai){
      String s = "Vectors are not the same size in the T_Vector.plus function:\n" +
      ai.toString();

      javax.swing.JOptionPane.showMessageDialog(null,s,
           "Vector Addition Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  /**
   * Cross Product function for vectors
   * Usage: v3 = v1xv2 is implemented as v3 = v1.cross(v2)
   * <p>v3[0] = v1[1]*v2[2] - v2[1]*v1[2]</p>
   * <p>v3[1] = v1[0]*v2[2] - v2[0]*v1[2]</p>
   * <p>v3[2] = v1[0]*v2[1] - v2[0]*v1[1]</p>
   * @param v The vector to cross into
   * @return The vector representing the cross product of v1 and v2.
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size
   */
  public T_Vector cross(T_Vector v) throws ArrayIndexOutOfBoundsException {
    try{
      if (v.getSize() != vector_size){
        String error = "Vectors not the same size in T_Vector.cross method";
        throw new ArrayIndexOutOfBoundsException(error);
      }else if (vector_size != 3){
        String error = "Vector size not equal to 3 in T_Vector.cross Method";
        throw new ArrayIndexOutOfBoundsException(error);
      }else{
        int new_precision = Math.min(v.getPrecision(), vector_precision);
        T_Vector out = new T_Vector(v.getSize(), new_precision);
        double temp;
        // from the above comments, v1 is THIS vector and v2 is V, so
        // v3[0] = vector[1]*v.getElement(2) - v.getElement(1)*vector[2]
        // v3[1] = vector[0]*v.getElement(2) - v.getElement(0)*vector[2]
        // v3[2] = vector[0]*v.getElement(1) - v.getElement(0)*vector[1]

        // set x component
        temp = vector[1]*v.getElement(2) - v.getElement(1)*vector[2];
        out.setElement(0,temp);

        // set the y component
        temp = - vector[0]*v.getElement(2) + v.getElement(0)*vector[2];
        out.setElement(1,temp);

        // set the Z Component
        temp = vector[0]*v.getElement(1) - v.getElement(0)*vector[1];
        out.setElement(2,temp);

        return out;
     }
    }catch(ArrayIndexOutOfBoundsException ai){
      javax.swing.JOptionPane.showMessageDialog(null,ai.toString(),
           "Vector Cross Product Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
  
    /**
   * Multiply function for vectors
   * Usage: v2 = v1 x scaler is implemented as v3 = v1.multiply(scaler)
   * <p>v2[0] = v1[0]*scaler</p>
   * <p>v2[1] = v1[1]*scaler</p>
   * <p>v2[2] = v1[2]*scaler</p>
   * @param scaler The scaler to multiply
   * @return The vector representing the scaler multiplication of v1 and scaler.
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size
   */
  public T_Vector multiply(double scaler) throws ArrayIndexOutOfBoundsException {
    try{
        return new T_Vector(vector[0]*scaler,vector[1]*scaler,vector[2]*scaler);
    }catch(ArrayIndexOutOfBoundsException ai){
      javax.swing.JOptionPane.showMessageDialog(null,ai.toString(),
           "Vector M Product Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }
  
   /**
   * Divide function for vectors
   * Usage: v2 = v1 x scaler is implemented as v3 = v1.divide(scaler)
   * <p>v2[0] = v1[0]/scaler</p>
   * <p>v2[1] = v1[1]/scaler</p>
   * <p>v2[2] = v1[2]/scaler</p>
   * @param scaler The scaler to divide
   * @return The vector representing the scaler division of v1 and scaler.
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size
   */
  public T_Vector divide(double scaler) throws ArrayIndexOutOfBoundsException {
    try{
        return new T_Vector(vector[0]/scaler,vector[1]/scaler,vector[2]/scaler);
    }catch(ArrayIndexOutOfBoundsException ai){
      javax.swing.JOptionPane.showMessageDialog(null,ai.toString(),
           "Vector M Product Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }catch(ArithmeticException ae){
        javax.swing.JOptionPane.showMessageDialog(null,ae.toString(),
           "Vector Division Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }
    
  }
  
      /**
   * Multiply function for vectors
   * Usage: v3 = v1 - v2 is implemented as v3 = v1.subtract(v2)
   * <p>v3[0] = v1[0] - v2[0]</p>
   * <p>v3[1] = v1[1] - v2[1]</p>
   * <p>v3[2] = v1[2] - v2[2]</p>
   * @param v The vector to subtract from this vector
   * @return The vector representing the difference between this vector and v.
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size
   */
  public T_Vector subtract(T_Vector v) throws ArrayIndexOutOfBoundsException {
    try{
        return new T_Vector(vector[0] - v.getElement(0),vector[1] - v.getElement(1),vector[2] - v.getElement(2));
    }catch(ArrayIndexOutOfBoundsException ai){
      javax.swing.JOptionPane.showMessageDialog(null,ai.toString(),
           "Vector Subtract Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return null;
    }
  }

  /**
   * Dot Product function for vectors
   * Usage: x = v1*v2 is implemented as x = v1.dot(v2)
   * <p>x = v1[0]*v2[0] + v1[1]*v2[1] + ...</p>
   * @param v The vector to dot into
   * @return The double value representing the dot product of v1 and v2, or the
   * maximum value a double can attain, if an exception is thrown.
   * @throws ArrayIndexOutOfBoundsException If the vectors are not the same size,
   *  or if the vector size is not equal to 3.
   */
  public double dot(T_Vector v) throws ArrayIndexOutOfBoundsException {
    try{
      if (v.getSize() != vector_size){
        String error = "Size 1 = " + vector_size + " Size 2 = " + v.getSize();
        throw new ArrayIndexOutOfBoundsException(error);
      }else{
        // create a decimal format that consists of the minimum precision of either vector
        DecimalFormat df = new DecimalFormat(T_Utilities.getPrecisionString(1,
                                Math.min(v.getPrecision(), vector_precision)));
        double out = 0.0;
        for (int i = 0; i < v.getSize(); i++){
          out += v.getElement(i)*vector[i];
        }
        return Double.parseDouble(df.format(out));
     }
    }catch(ArrayIndexOutOfBoundsException ai){
      String s = "Vectors are not the same size in the T_Vector.dot function:\n" +
      ai.toString();

      javax.swing.JOptionPane.showMessageDialog(null,s,
           "Vector Dot Product Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    return Double.MAX_VALUE;
  }


  /**
   * Method to return the unit vector.
   * This method will ensure that the magnitude of the returned vector is 1.
   * The returned vector is [x/magnitude(), y/magnitude(), ...], where x and y
   *  are the components of the vector and magnitude() is the magnitude of the vector
   * @return The unitized vector
   */
   public T_Vector unit(){
    T_Vector unit = new T_Vector(vector[0],vector[1],vector[2], vector_precision);
    double mag = magnitude();
    try{
      if (mag == 0.0) throw new ArithmeticException();
      for (int i = 0; i < unit.getSize(); i++){
        unit.setElement(i,unit.getElement(i)/mag);
      }
      return unit;
    }catch(ArithmeticException dbz){
      String s = "Vector has zero magnitude, cannot make a unit vector\n";
      javax.swing.JOptionPane.showMessageDialog(null,s,
           "Unit Vector Error",
           javax.swing.JOptionPane.ERROR_MESSAGE);
      return this;
    }
   }

   /**
    * Method to return the magnitude of the vector
    * The magnitude is calculated as sqrt(x^2 + y^2 + z^2 + ...)
    * @return A double representing the magnitude of the vector
    */
   public double magnitude(){
    double mag = 0.0;
    for (int i = 0; i < vector_size; i++){
      mag += vector[i]*vector[i];
    }
    return Math.sqrt(mag);
   }

  // Private functions
  /**
   * Private function to return a string to use in the DecimalFormat constructor.
   * This function is called as the argument to a DecimalFormat construction and will provide
   * a string with the number of 0's after the decimal point as defined by vector_precision
   * @return The format string for the DecimalFormat construction
   */
  private String getPrecisionString(){
    String s = "0.";
    for (int i = 0; i < vector_precision; i++){
      s += "0";
    }
    return s;
  }


  /** An executable function to test the Vector Library.
   * This will display a message box with Vector results displayed.
   * @param args Argument string to the test program.
   * (Not needed for this test program)
   */  
  public static void main(String[] args) {
      
      T_Vector v1;
      T_Vector v2;
      T_Vector v3, v4;
    v1 = new T_Vector(-11,-2,-3);
    v2 = new T_Vector(3.14,2.71828,137, 3);
    v3 = new T_Vector(5, 6);
    v4 = v2.plus(v1);

    String s = "V1 S = " + v1.getSize() + " P = " + v1.getPrecision() + " " + v1 + "\n";
    s += "V2 Size = " + v2.getSize() + " P = " + v2.getPrecision() + " " + v2 + "\n";
    s += "V3 Size = " + v3.getSize() + " P = " + v3.getPrecision() + " " + v3 + "\n";
    //s += "v2 + v3 = " + v2.plus(v3) + "\n";
    s += " P = " + v4.getPrecision() + " v2 + v1 = " + v4 + "\n";
    s+= " v1*v2 = " + v2.dot(v1) + "\n";
    s+= " |v1| = " + v1.magnitude() + "\n";
    s+= " unit(v1) = " + v1.unit() + "\n";
    s+= " unit(v1).mag = " + v1.unit().magnitude() + "\n";
    s+= "v1 = " + v1 + "\n";
    s += "v1Xv2 = " + v1.cross(v2) + "\n";

    javax.swing.JOptionPane.showMessageDialog(null,s);
  }
  
}