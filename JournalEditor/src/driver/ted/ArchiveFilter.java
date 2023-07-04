package driver.ted;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class ArchiveFilter extends FileFilter {
    
    // Accept all directories and all gif, jpg, or tiff files.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        //System.out.println("Extension = " + extension);
	if (extension != null) {
            if (extension.equals(Utils.arc)) {
                    return true;
            } else {
                return false;
            }
    	}

        return false;
    }
    
    // The description of this filter
    public String getDescription() {
        return "Journals";
    }
    
}