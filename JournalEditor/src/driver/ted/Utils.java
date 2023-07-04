package driver.ted;
import java.io.File;

public class Utils {

    public final static String arc = "jed";
    public final static String text = "txt";
    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    public static File setExtension(File f, String ext) {
        String p = f.getPath();
        String pathAndFile;
        File newFile;
        int i = p.lastIndexOf('.');

        if(i > 0  && i < p.length() - 1){ // currently has extension
            pathAndFile = p.substring(0,i+1); // be sure to get the 'dot' as well!
            pathAndFile += ext;
            newFile = new File(pathAndFile);
        }else{// no extension 
            p += "." + ext;
            newFile = new File(p);
        }
        return newFile;
    }
}
