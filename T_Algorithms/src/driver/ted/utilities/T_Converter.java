package driver.ted.utilities;

/**
 * Title:        T_Converter class
 * Description:  This class converts numbers from several formats into several formats
 * Copyright:    (c) 2002 - 2006
 * @author       Ted Driver
 * @version 1.1
 *
 * Changes:
 */

public class T_Converter {

  /**
   * The designator for BINARY inputs and outputs.  The BINARY
   * field designator is assigned a value of 2 and can be used to specify the radix
   * if desired.
   */
  public static final int BINARY = 2;

  /**
   * The designator for OCTAL inputs and outputs.  The OCTAL
   * field designator is assigned a value of 8 and can be used to specify the radix
   * if desired.
   */
  public static final int OCTAL = 8;

  /**
   * The designator for INTEGER inputs and outputs.  The INTEGER
   * field designator is assigned a value of 10 and can be used to specify the radix
   * if desired.
   */
  public static final int INTEGER = 10;

   /**
   * The designator for HEX inputs and outputs.  The HEX
   * field designator is assigned a value of 16 and can be used to specify the radix
   * if desired.
   */
  public static final int HEX = 16;

  /**
   * Default constructor does nothing.
   */
  public T_Converter() {
  }

  // accessors
  /**
   * This function will accept a string and return a binary representation
   * of the value supplied once converted to inputType.  If the pad variable is set,
   * the binary representation will be prepadded to the next multiple of 4 and
   * a space will be added between every 4th digit.
   * @param value The value to convert to binary
   * @param inputType One of the available fields in the class, specifying the
   * input type
   * @param pad An integer specifying how many digits to space in between numbers.
   *  Use a value of zero (0) if no padding is required. pad will typically be 4
   *  for hex representations, 3 for octal representations. A pad of 29 will designate that the
   *  binary string will be padded for the 29 bit GPS Z-Count, placing a space between the 19th
   *  and 20th LSBs to represent the Week and Time of Week.
   * @return A String containing the (possibly padded) binary representation of the input value
   */
  public static String getBinary(String value, int inputType, int pad){
    int zeroes;
    StringBuffer sb;

    // calculate the binary string
    String valueString = getNumberString(value,inputType,2);
    sb = new StringBuffer(valueString);

    // check for the 0 pad value, if zero, do not pad
    if (pad != 0){
      if(pad != 29){
          // calculate the number of zeroes to prepend to the string to make it a multiple of the pad
          zeroes = pad - ((valueString.length() + pad) % pad);
          if (zeroes == pad) zeroes = 0;
            //System.out.println("length = " + valueString.length());
            //System.out.println("zeroes = " + zeroes);

          // reverse the string so appending is actually prepending
          sb.reverse();

          // append the correct number of zeroes
          for (int i = 0; i < zeroes; i++){
            sb.append('0');
          }

          // reverse the string again to get the correct string back
          sb.reverse();

            //System.out.println("sb = " + sb.toString());

          // run through the length of the string, inserting spaces every pad value
          for (int i = sb.length()-1; i > 0; i--){
            if ( (i%pad == 0)){
              sb.insert(i,' ');
            }
          }
        //System.out.println("sb = " + sb.toString());
      }else{ // pad = 29

          // calculate the number of zeroes to prepend to the string to make it a 29 bit string
          zeroes = pad - valueString.length();
          if (zeroes == pad) zeroes = 0;
            //System.out.println("length = " + valueString.length());
            //System.out.println("zeroes = " + zeroes);

          // reverse the string so appending is actually prepending
          sb.reverse();

          // append the correct number of zeroes
          for (int i = 0; i < zeroes; i++){
            sb.append('0');
          }

          // reverse the string again to get the correct 29 bit string
          sb.reverse();

          // Create new Week and Time Of Week StringBuffers from original Z-Count
          StringBuffer week = new StringBuffer(sb.substring(0,10));
          StringBuffer TOW = new StringBuffer(sb.substring(10,29));

          // start the padding of each new sring

          // Reverse the week string
          week.reverse();

          // insert spaces every 4 bits
          for (int i = week.length()-1; i > 0; i--){
            if ( (i%4 == 0)){
              week.insert(i,' ');
            }
          }
          // return the original string
          week.reverse();

          // do the same for the time of week
          TOW.reverse();
          for (int i = TOW.length()-1; i > 0; i--){
            if ( (i%4 == 0)){
              TOW.insert(i,' ');
            }
          }
          TOW.reverse();

         // replace old string with new with additional padding to separate
         // the week and time of week fields
         sb.replace(0,sb.length(),week + "    " + TOW);

      }// end else
    }// end if != 0

    return sb.toString();
  }

  /**
   * This function will accept a string and return a Hex representation
   * of the value supplied once converted to inputType.
   * @param value The value to convert to Hex
   * @param inputType One of the available fields in the class, specifying the
   * input type
   * @return A String containing the Hex representation of the input value
   */
  public static String getHex(String value, int inputType){
    return getNumberString(value,inputType,16);
  }

  /**
   * This function will accept a string and return an Integer representation
   * of the value supplied once converted to inputType.
   * @param value The value to convert to an integer
   * @param inputType One of the available fields in the class, specifying the
   * input type
   * @return A String containing the integer representation of the input value
   */
  public static String getInt(String value, int inputType){
    return getNumberString(value,inputType,10);
  }

  /**
   * This function will accept a string and return an octal representation
   * of the value supplied once converted to inputType.
   * @param value The value to convert to octal
   * @param inputType One of the available fields in the class, specifying the
   * input type
   * @return A String containing the octal representation of the input value
   */
  public static String getOctal(String value, int inputType){
    return getNumberString(value,inputType,8);
  }

  /**
   * This function accepts a string, an input type and a output radix and
   * returns the string representation (using the output radix) of the value passed in.
   * This function will display an error message box if the inputType is not one of:
   * <ul><li><code>T_Converter.BINARY</code></li>
   * <li><code>T_Converter.INTEGER</code></li>
   * <li><code>T_Converter.HEX</code></li>
   * <li><code>T_Converter.OCTAL</code></li>
   * </ul>
   * @param value The string value to convert to a string with a specified output radix
   * @param inputType One of the available fields in this class, specifying the radix of the input value
   * @param outputRadix the radix ouf the string on output
   * @return A String containing the outputRadix representation of the input value
   */
  private static String getNumberString(String value, int inputType, int outputRadix){
    int inputRadix = 10;
    switch(inputType){
      case BINARY:
          inputRadix = BINARY;
          break;
      case INTEGER:
        inputRadix = INTEGER;
        break;
      case HEX:
        inputRadix = HEX;
        break;
      case OCTAL:
        inputRadix = OCTAL;
        break;
      default:
        javax.swing.JOptionPane.showMessageDialog(null,
                "The inputType parameter must be either T_Converter.BINARY, HEX, DECIMAL or OCTAL",
                "Error in T_Converter getBinary",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        break;
    }
     return new String(Long.toString(Long.parseLong(value,inputRadix),outputRadix));
  }

  /**
   * A program to provide the testing for the T_Converter class
   * @param args Array of strings to use in the main app
   */
  public static void main(String[] args) {
    T_Converter t_Con1 = new T_Converter();
    System.out.println(t_Con1.getBinary("41307A7",T_Converter.HEX,29));

    System.out.println(t_Con1.getHex("42",T_Converter.OCTAL));
    System.out.println(t_Con1.getInt("11011010010",T_Converter.BINARY));
    System.out.println(t_Con1.getOctal("42",T_Converter.INTEGER));

  }
}