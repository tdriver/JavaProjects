package driver.ted;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class JedFileView extends FileView {
    ImageIcon txtIcon = new ImageIcon("images/txtIcon.gif");
    ImageIcon archiveIcon = new ImageIcon("images/archiveIcon.gif");
    
    public String getName(File f) {
        return null; // let the L&F FileView figure this out
    }
    
    public String getDescription(File f) {
        return null; // let the L&F FileView figure this out
    }
    
    public Boolean isTraversable(File f) {
        return null; // let the L&F FileView figure this out
    }
    
    public String getTypeDescription(File f) {
        String extension = Utils.getExtension(f);
        String type = null;

        if (extension != null) {
            if (extension.equals(Utils.text)) {
                type = "Text File";
            } else if (extension.equals(Utils.arc)){
                type = "JED Journal File";
            }
        }
        return type;
    }
    
    public Icon getIcon(File f) {
        String extension = Utils.getExtension(f);
        Icon icon = null;

        if (extension != null) {
            if (extension.equals(Utils.text)) {
                icon = txtIcon;
            } else if (extension.equals(Utils.arc)) {
                icon = archiveIcon;
            } 
        }
        return icon;
    }
}
