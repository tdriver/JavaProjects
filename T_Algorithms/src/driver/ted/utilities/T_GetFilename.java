/*
 * T_GetFilename.java
 *
 * Created on July 15, 2003, 9:18 AM
 */

package driver.ted.utilities;

/**
 * Title:        T_GetFilename
 * Description:  Provides capability for user to choose a file and return a string pointing to that file
 * Copyright:    (c) 2006
 * Company:
 * @author Ted Driver
 * @version 1.0
 * Changes:

/**
 * Utility to allow the user to select a file and return a string path to the file
 * @author Ted Driver
 */
public class T_GetFilename {
    javax.swing.JFileChooser chooser;
    /** Creates a new instance of T_GetFilename */
    public T_GetFilename() {
        chooser = new javax.swing.JFileChooser(); 
    }
    
    /**
     * When called,opens a file selector dialog allowing the user to select a file.
     * @return a string with the entire path to the file including the filename
     */
    public String getFilename(){
        try{
            if(chooser.showOpenDialog(null) != javax.swing.JFileChooser.CANCEL_OPTION){
                return chooser.getSelectedFile().getCanonicalPath();
            }else{
                return new String("");
            }
        }catch(java.io.IOException ioe){
            javax.swing.JOptionPane.showMessageDialog(null,
            ioe.toString(),
            "Error",
            javax.swing.JOptionPane.ERROR);
            return new String("");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("File Selected = " + new T_GetFilename().getFilename());
        System.exit(1);
        
    }
    
}
