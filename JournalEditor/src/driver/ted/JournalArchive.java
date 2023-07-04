package driver.ted;

import java.io.Serializable;
import java.util.*;
import java.io.*;
import java.util.zip.*;

import javax.swing.tree.*;

class JournalArchive implements Serializable{
    /**
     * Use this class to maintain an entire archive of journal entries
     * A new instance of this class will be created whenever a new journal
     * archive is loaded.
     */
    
    private String archiveName;
    private JournalType archiveTypes;
    private TreeMap journalEntries;
    private HashMap pwMap;
    private JournalEntry currentEntry;
    private char[] pw = null;
    private boolean dirtyState; //if true, archive changes have been made
    private boolean pwEnabled;
    
    // Constructors
    /** Create an empty archive
     */
    public JournalArchive(){
        // initialize name and archiveTypes
        archiveName = new String("Unnamed Archive");
        archiveTypes = new JournalType();
        journalEntries = new TreeMap();
        dirtyState = false;
        pwEnabled = false;
    }
    
    /** Create an archive from a predefined Archive name.
     * Typically used for creating an archive from persistent storage
     * @param ArchiveName The name of the archive being created.
     */
    public JournalArchive(String ArchiveName){
        // initialize name and archiveTypes
        
        archiveName = ArchiveName;
        archiveTypes = new JournalType();
        // try to load the entries from the named Archive
        journalEntries = new TreeMap();
        dirtyState = false;  
        pwEnabled = false;
    }
    
    // Mutators
    /** Set the name of the current archive.
     * This will update the tree with the new name as well.
     *
     * @param treePanel The Dynamic Tree object whose root node will
     * be renamed.
     * @param ArchiveName The name for the archive
     */    
    public void setArchiveName(DynamicTree treePanel,File currentFile){
        if (currentFile ==  null) 
            archiveName = "Untitled";
        else
        archiveName = currentFile.getName();
        treePanel.setRootNodeName(archiveName);   
        dirtyState = true;
    }
    
    
    /** Adds a new Journal Category to the the archive.
     * @param JType The new journal type
     * @return True if the journal category did not exist and was added successfully.
     * False if the journal category already existed.
     */    
    public boolean addNewJournalType(String JType){
        try{
          archiveTypes.addJournalType(JType);
          dirtyState = true;
          return true;
        }catch(DuplicateValueException dve){
         // the journal type already exists...don't add it.
                  /*  javax.swing.JOptionPane.showMessageDialog(null,
                    dve.toString(),"Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE); */
            return false;
        }
    }
    
     
    
    /** Adds a new journal entry to the archive
     * @param treePanel The Dynammic Tree object to update with the
     * new journal entry information
     * @param JType The journal category to put the new entry into.
     * @param current The journal entry object
     */    
    public void addJournalEntry(DynamicTree treePanel, String JType, JournalEntry current, boolean shouldSelect){
                
        // assign the journal entry to be the current JE
        currentEntry = current;
        
       // check if the new journal type can be added
        if(addNewJournalType(JType)){   
                      
            // insert into map
            if(!journalEntries.containsKey(current.getKey())){
                journalEntries.put(current.getKey(),currentEntry);
                
                // add new category node
                DefaultMutableTreeNode category = treePanel.addObject(null,JType);
                
                // add new entry to newly created node
                treePanel.addObject(category,current.toString(), true, shouldSelect);
                dirtyState = true;
            }else{
                System.out.println("Already contains Key: " + current.getKey());
            }
            // debug
            //System.out.println("Contents = " + journalEntries);
        }else{ // journal type already exists
            
            if(!journalEntries.containsKey(current.getKey())){
                journalEntries.put(current.getKey(),currentEntry);
                
                // add new category node
                DefaultMutableTreeNode category = treePanel.getCategoryNode(JType);
                treePanel.addObject(category,current.toString(), true, shouldSelect);
                dirtyState = true;
            }else{
                System.out.println("Already contains Key: " + current.getKey());
            }
            // debug
            //System.out.println("Contents = " + journalEntries);
            // add new type the to tree
            
        }
    }
  
    //Accessors
    /** Gets the journal entry specified as the active one.
     * @return A journal entry object
     */    
    public JournalEntry getCurrentEntry(){
        return currentEntry;
    }
    /**
     *Checks if the specified Journal Entry is valid
     *and if so, makes it the current entry.
     *@param EntryName The name of the Journal Entry
     *@return The JournalEntry Object if valid, null otherwise
     */
    public JournalEntry getJournalEntry(String EntryName){
        // dummy code to get 
       // String key = currentEntry.getKey();
        // create key here from EntryName
        if(EntryName == null){
            return null;
        }else{
            if(!journalEntries.containsKey(EntryName)){
                return null;
            }else{
                currentEntry = (JournalEntry)journalEntries.get((String)EntryName);
                return currentEntry;
            }
        }
    }
    
    /** Get the current Archive Name
     *This name will ahve no path name associated with it, just the name of thr archive 
     *(which is also the name of the file stored on disk)
     */
    public String getArchiveName(){
        return archiveName;
    }
    
   // File operations
    
    
    /** Saves the current Archive.
     */
    
    public boolean saveArchive(DynamicTree treePanel, File fileName){
        ObjectOutputStream output;

        // collect the tree information (archive structure)
        try{ // to open the file
            output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream( fileName )) );
        }catch(IOException e){
            return false;
        } 
        
        // output the data to the file
          try{
            //System.out.println( journalEntries.size() + " entries");
            // output the password status and and password to file first
            
            output.writeObject(packPassword());
            output.writeObject( journalEntries );
            output.flush();           
            output.close();
            dirtyState = false;
            
          }catch(IOException iox){
           String message2 = "";
           System.out.println("Error Saving Data To " + fileName + iox.toString());
          }

      //  statusBar.setText("Error opening "+fileName);
      
        // save the first object (structure) then save all the entries
        //System.out.println("File (not really) saved to: " + fileName);
        return true;        
    }
    
    
    /** Opens the user selected Archive
     * @param fileName The name of the arechive to open
     */    
   public void openFile(File file) throws java.io.IOException, RuntimeException{
      try
      {
        // Get the size of the opened file.
        int size = (int)file.length();

        // Set to zero a counter for counting the number of
        // characters that have been read from the file.
        int chars_read = 0;

        // Create an input reader based on the file, so we can read its data.
        // FileReader handles international character encoding conversions.
        FileReader in = new FileReader(file);

        // Create a character array of the size of the file,
        // to use as a data buffer, into which we will read
        // the text data.
        char[] data = new char[size];

        // Read all available characters into the buffer.
        while(in.ready()) {
          // Increment the count for each character read,
          // and accumulate them in the data buffer.
          chars_read += in.read(data, chars_read, size - chars_read);
        }
        in.close();

        // Create a temporary string containing the data,
        String text = new String(data, 0, chars_read);
        
        // insert the string into the current document
        try{
            currentEntry.getDocument().insertString(currentEntry.getDocument().getLength(),
                                                    text,
                                                    new javax.swing.text.SimpleAttributeSet());
            dirtyState = true;
        } catch (javax.swing.text.BadLocationException ble) {
              System.err.println("Couldn't insert text into entry.");
        }
      }
      catch (IOException e)
      {
          throw e;
      //  statusBar.setText("Error opening "+fileName);
      }
    }

    
    // Save current journal entry as a text file, asking user for new destination name.
    // Report to statuBar.
    /** Saves current journal entry as a text file.
     * @return Returns true if the text file was saved correctly false otherwise.
     */    
   public boolean saveAsFile(javax.swing.JFrame parent, javax.swing.text.EditorKit kit, javax.swing.JFileChooser chooser) {
      String currFileName;
      // Use the SAVE version of the dialog, test return for Approve/Cancel
      if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showSaveDialog(parent)) {
        // Set the current file name to the user's selection,
        // then do a regular saveFile
        currFileName = chooser.getSelectedFile().getPath();
        //repaints menu after item is selected
        parent.repaint();
        try
          {
            // Open a file of the current name.
            File file = new File (currFileName);

            // Create an output writer that will write to that file.
            // FileWriter handles international characters encoding conversions.
            FileWriter out = new FileWriter(file);

            String text;

            try{
                kit.write(out, currentEntry.getDocument(),0,currentEntry.getDocument().getLength());
                //out.write(text);
            }catch(IOException ioe){
                return false;
            }catch(javax.swing.text.BadLocationException ble){
                return false;
            }
            out.close();
            //this.dirty = false;
           // updateCaption();
            return true;
          }
          catch (IOException e) {
            //statusBar.setText("Error saving "+currFileName);
          }
          return false;
      }
      else {
        parent.repaint();
        return false;
      }
    }
   
    /** Exports all journal entries to a user defined filesystem directory or to a standard zip file.
     * @return Returns true if the entries were written correctly, false otherwise.
     */    
   public boolean export(javax.swing.JFrame parent, javax.swing.text.EditorKit kit, boolean createZip){
       // temporary entry variable
       JournalEntry tempEntry;
       String outputDirectory, outputSubDirectory, zipOutputDirectory, revisedOutputDirectory;
       String fileName, categoryName;
       String outputZipFileName = "";
       long entryCounter =0, categoryCounter = 0;
       java.text.DecimalFormat df = new java.text.DecimalFormat("0.000000");
       File file;
       FileWriter out;
       boolean okToDelete = false;
       
       if(this.isPasswordEnabled()){
          if( javax.swing.JOptionPane.showConfirmDialog(parent,
           "Warning! The exported journal will no longer have password protection! Continue?",
           "Loss of Protection",
           javax.swing.JOptionPane.WARNING_MESSAGE) == javax.swing.JOptionPane.CANCEL_OPTION){
               return false;
          }
          // Ok to proceed, ask for password to export
          passwordDialog pd = new passwordDialog(null, true);
          pd.setTitle("Enter Password to Export Journal");
          pd.setVisible(true);
          if(!this.isPasswordGood(pd.getPassword())){
           javax.swing.JOptionPane.showMessageDialog(null, 
            "Password is invalid, please enter a valid password to export this Journal",
            "Invalid password",                
            javax.swing.JOptionPane.ERROR_MESSAGE);     
            return false;             
          }
       }
      // Use the SAVE version of the dialog, test return for Approve/Cancel
       javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
       chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
       chooser.setDialogTitle("Export to...");
       //chooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
      if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showDialog(parent, "Export")) {
        // Set the current directory name to the user's selection,
        outputDirectory = chooser.getSelectedFile().getPath();
        zipOutputDirectory = outputDirectory + "\\" + archiveName;
        revisedOutputDirectory = outputDirectory + "\\Journal - " + archiveName;
        
        //repaints menu after item is selected
        parent.repaint();
        try{  
           // create the top level directory first
           File tempFile = new File(revisedOutputDirectory);
           //System.out.println("File Created: " + tempFile.getPath());
           if(!tempFile.exists()){
              tempFile.mkdir();
              okToDelete = true;
              //System.out.println("Created directory: " + revisedOutputDirectory);
           }
           
           // do some preliminary zip stuff if necessary
           if(createZip){
            // create the zip output filename.  delete the .jed from the archive name and add a .zip
            int lio = zipOutputDirectory.lastIndexOf(".");
            if (lio == -1){
                outputZipFileName = zipOutputDirectory + ".zip";
            }else{                
                outputZipFileName = zipOutputDirectory.substring(0,lio) + ".zip";
            }
            // check for existence of file 
            if(new File(outputZipFileName).exists()){
                 if (javax.swing.JOptionPane.showConfirmDialog(parent,
                         "File " + outputZipFileName + " exists! Overwrite?",
                        "File Exists",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.WARNING_MESSAGE) == javax.swing.JOptionPane.NO_OPTION){
                            return false;
                 }
               }
           }
           
           // start the file creation
           // Iterate over all Journal Entries
           Iterator entryIterator = journalEntries.values().iterator();
           while(entryIterator.hasNext()){
                tempEntry = (JournalEntry) entryIterator.next();
                categoryName = tempEntry.getJournalType();     
                outputSubDirectory = revisedOutputDirectory + "\\" + categoryName;
                file = new File(outputSubDirectory);
                if(!file.exists()){
                    file.mkdir();
                }
                // Open a file of the current name.
                fileName = df.format(tempEntry.getJulianDate());
                file = new File (outputSubDirectory + "\\" + fileName + ".txt");
                 entryCounter++; 
                // Create an output writer that will write to that file.
                out = new FileWriter(file);
                
                try{
                    kit.write(out, tempEntry.getDocument(),0,tempEntry.getDocument().getLength());
                }catch(IOException ioe){
                    javax.swing.JOptionPane.showMessageDialog(parent,
                    "Error writing file: " + ioe.toString(),
                    "IO Exception",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
                }catch(javax.swing.text.BadLocationException ble){
                    return false;
                }
                // close this file before opening another one.
                out.close();
           }
           // create Zip File if necessary
           if(createZip){
               // this will list all of the category directories
               Set filesToAdd = new HashSet();
               File[] categories = tempFile.listFiles();
              
               //FileInputStream
            for(int i = 0; i < categories.length; i++){
                if(categories[i].isDirectory()){ // ok, now add the files in that directory
                    File[] thisDirectoriesFiles = categories[i].listFiles();
                    for (int j = 0; j < thisDirectoriesFiles.length; j++){
                        filesToAdd.add( (File) thisDirectoriesFiles[j]);
                    }
                }else if (categories[i].isFile()){
                    filesToAdd.add( (File) categories[i]);
                }
            }
               // now have all of the files to add to the zip file
               byte[] buf = new byte[1024];
               Iterator it = filesToAdd.iterator();
               try{
                   // create the Zip output stream with the new name
                   ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(outputZipFileName));
                   
                   // loop through all of the files to add
                   while(it.hasNext()){
                       // create a string buffer that has the full file path name for this particular entry
                       StringBuffer currentFileBuf = new StringBuffer(((File)it.next()).getAbsolutePath());
                       
                       // create a string from the buffer to pass to the File Input Stream constructor
                       String currentFile = currentFileBuf.toString();
                       
                       // create a shortened string to pass to the ZipEntry constructor as the path that will show up
                       // in the Zip file
                       String shortFileName = currentFileBuf.delete(0,outputDirectory.length()+1).toString(); // +1 to remove the slash as well
                       
                       // create the input file stream from the currentFile
                       FileInputStream in = new FileInputStream(currentFile);
                       
                       // Add Zip entry to output stream with shortened path name
                       zout.putNextEntry(new ZipEntry(shortFileName));
                       
                       // transfer bytes from the file to the zip file
                       int len;
                       while( (len=in.read(buf))>0){
                        zout.write(buf,0,len);
                       }
                       
                       // Complete the entry
                       zout.closeEntry();
                       in.close();
                       
                       // delete this text file if OK
                       if(okToDelete){
                           File tempToDelete = new File(currentFile);
                           if(!tempToDelete.delete()){
                               System.out.println(currentFile + " not deleted!");
                           }
                       }
                   }
                   zout.close();
                   
                   // delete the categories that were created, if OK
                   if(okToDelete){
                       for (int k = 0; k < categories.length; k++){
                        if(!categories[k].delete()){
                            System.out.println(categories[k] + " not deleted");
                        }
                       }

                       File tempFileToDelete = new File(revisedOutputDirectory);
                       if(!tempFileToDelete.delete()){
                            System.out.println(tempFileToDelete + " not deleted");
                        }
                   }
                   
               }catch (IOException ioe){
                   javax.swing.JOptionPane.showMessageDialog(parent,
                    "Error writing ZIP file: " + ioe.toString(),
                    "IO Exception",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
               }
              } // end if createZip
           // everything worked, return success
           String typeOfWrite;
           typeOfWrite = createZip ? " to Zip File":" to Directory";
           javax.swing.JOptionPane.showMessageDialog(parent,
              entryCounter + " entries written.",
              "Successful Export" + typeOfWrite,
              javax.swing.JOptionPane.INFORMATION_MESSAGE);
           return true;
          
      }catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(parent,
                    "Error writing file: " + e.toString(),
                    "IO Exception",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                    return false;
          }
      } else { // user cancelled directory selection
        parent.repaint();
        return false;
      }
   }
   
   
   public File importFromDirectory(javax.swing.JFrame parent, javax.swing.text.EditorKit kit, DynamicTree treePanel){
       String inputDirectory;
       // get the directory name to import    
       javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
       chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
       chooser.setDialogTitle("Import a Directory");
       chooser.setApproveButtonText("Import");
      if (javax.swing.JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(parent)) {
        // Set the current directory name to the user's selection,
        inputDirectory = chooser.getSelectedFile().getPath();
        
        // check directory for validity by testing for the "Journal - " text in the beginning of the directory name
        int io = inputDirectory.indexOf("Journal - ");
        String newArchiveName = "";
        if(io == -1){
            // not a valid directory choice, doesn't contain the above test string
            javax.swing.JOptionPane.showMessageDialog(parent,
                "Not a valid directory: " + inputDirectory,
                "Invalid Directory",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }else{
            // get the new journal name: this assumes there is no text in front of the Journal Text
             newArchiveName += chooser.getSelectedFile().getName().substring("Journal - ".length());            
        }
        
        parent.repaint();
        // get the filenames to add
        File tempFile = new File(inputDirectory);
        File[] categories = tempFile.listFiles();
        FileReader in;
        TreeMap newEntries; // new journal entry collection
        JournalEntry tempEntry;
        String entryJD;
        String entryCategory;
        long entryCounter = 0, categoryCount = 0;
        
        for(int i = 0; i < categories.length; i++){
                if(categories[i].isDirectory()){ // ok, now get the files in that directory (ignore if just a file)
                    categoryCount++;
                    File[] thisDirectoriesFiles = categories[i].listFiles();
                    entryCategory = categories[i].getName();
                    for (int j = 0; j < thisDirectoriesFiles.length; j++){
                        // need to delete the ".txt" suffix if it exists
                        int lio = thisDirectoriesFiles[j].getName().lastIndexOf(".");
                        if(lio == -1){
                            entryJD = thisDirectoriesFiles[j].getName();
                        }else{
                            entryJD = thisDirectoriesFiles[j].getName().substring(0,lio);
                        }
                        try{
                            in = new FileReader(thisDirectoriesFiles[j]);
                            tempEntry = new JournalEntry(entryCategory,Double.parseDouble(entryJD));    
                            kit.read(in,tempEntry.getDocument(),0);
                            // OK, now add the entry to the archive
                            this.addJournalEntry(treePanel,tempEntry.getJournalType(),tempEntry,false);
                            entryCounter++;
                        }catch(FileNotFoundException fnf){
                            javax.swing.JOptionPane.showMessageDialog(parent,
                                "File  not found: " + fnf.toString(),
                                "File Not Found Exception",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        }catch(NumberFormatException nfe){
                            javax.swing.JOptionPane.showMessageDialog(parent,
                                "Error reading journal file name: " + nfe.toString(),
                                "Number Format Exception",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        }catch(IOException ioe){
                            javax.swing.JOptionPane.showMessageDialog(parent,
                                "Error reading file: " + ioe.toString(),
                                "IO Exception",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        }catch(javax.swing.text.BadLocationException ble){
                           javax.swing.JOptionPane.showMessageDialog(parent,
                                "Couldn't insert text into entry: " + ble.toString(),
                                "Bad Location Exception",
                                javax.swing.JOptionPane.ERROR_MESSAGE);
                        }
                        
                    }
                }
            }
        javax.swing.JOptionPane.showMessageDialog(parent,
            entryCounter + " entries read in " + categoryCount + " categories.",
            "Successful Import from Directory",
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
        File returnFile = new File(chooser.getSelectedFile().getParent() + "\\" + newArchiveName);
        returnFile = Utils.setExtension(returnFile,"jed");
             
        return returnFile;
        
      }else{ // decided to cancel
          parent.repaint();
          return null;
      }
   }
   
   public boolean openArchive(java.io.File fileName, DynamicTree treePanel) {
        ObjectInputStream input;
        String pwEnabledStatus;
        char[] archivePassword;
        TreeMap in;
        boolean goodToGo = false;
        
        try{ // to open the file
            input = new ObjectInputStream(new GZIPInputStream(new FileInputStream( fileName )) );
        }catch(IOException e){
            javax.swing.JOptionPane.showMessageDialog(null,
            "IO error. Will try to open using old method...",
            "Error Opening Journal File",
            javax.swing.JOptionPane.WARNING_MESSAGE);
            try{
                input = new ObjectInputStream(new FileInputStream( fileName ) );
            }catch(IOException e2){
                javax.swing.JOptionPane.showMessageDialog(null,
                "IO error: " + e2.toString() + ". Sorry, failed.",
                "Error Opening Journal File",
                javax.swing.JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        // read in the password enabled status, password and archive object
        try{
            //pwEnabledStatus = (String)input.readObject();
            //archivePassword = (char[])input.readObject();
            unpackPassword((HashMap)input.readObject());
            in = (TreeMap)input.readObject();
            input.close();           
        }catch(IOException iox){           
          System.out.println("IOEx: Error Opening Data From: " + fileName + " " + iox.toString());
          // Here, let's try to get a type opf the new object
          return false;
        }catch(ClassNotFoundException cnf){
          System.out.println("CNFE: Error Opening Data From:" + fileName + " " + cnf.toString());
          return false;
        }
        // all good
        // set the password stuff
       //this.setPasswordEnabled(Boolean.valueOf(pwEnabledStatus).booleanValue());
        //this.setPassword(archivePassword);   
        // clear the current tree and archive types
        this.clear(treePanel);
         // prior to loading the entries, must check password if necessary
        if(this.isPasswordEnabled()){
            passwordDialog pd = new passwordDialog(null, true);
            pd.setTitle("Enter Password to Open Journal");
            pd.setVisible(true);
            if(pd.getPassword() != null){
                if(this.isPasswordGood(pd.getPassword())){
                    goodToGo = true;                       
                }else{
                    javax.swing.JOptionPane.showMessageDialog(null, 
                    "Password is invalid, please enter a valid password to open this Journal next time.",
                    "Invalid password",                
                    javax.swing.JOptionPane.ERROR_MESSAGE);     
                    goodToGo = false;
                }
            }else{
                // cancel pressed on the password opening dialog
                goodToGo = false;
            }
        }else{
            goodToGo = true;
        }
        
        if(goodToGo){
            Map.Entry e;
            
            // iterate through the objects and fill in the tree
            for (Iterator i = in.entrySet().iterator(); i.hasNext();){
                e = (Map.Entry)i.next();
                // add the treepanel, journaltype, journal entry and do not select this entry when added
                this.addJournalEntry(treePanel,((JournalEntry)e.getValue()).getJournalType(),(JournalEntry)e.getValue(), false);
            } 
            return true;
        }
        
        return false;
    }
    
    public boolean isDirty(){
        return dirtyState;
    }
    
    public void setDirty(boolean state){
        dirtyState = state;
    }
    
    public void setPassword(char[] word){
        pw = word;
    }
    
    public boolean isPasswordEnabled(){
        return pwEnabled;
    }
    
    public void setPasswordEnabled(boolean enabled){
        pwEnabled = enabled;
    }
    
    public void clearPassword(){
        this.setPasswordEnabled(false);
        this.setPassword(null);
    }
    
    public boolean isPasswordGood(char[] passwordToTry){
      if (pw.length != passwordToTry.length) return false;
      for (int i=0; i < pw.length; i++){
        if (pw[i] != passwordToTry[i]) return false;
      }
      return true;
    }
    
    public void removeJournalType(String typeToRemove){
        JournalEntry e;
        Set toDelete = new HashSet();
        // iterate through the objects and fill in the vector
        for (Iterator i = journalEntries.entrySet().iterator(); i.hasNext();){
            //look for the entries that have that category and remove them.
            e = (JournalEntry)((Map.Entry)i.next()).getValue();
            if(e.getJournalType() == typeToRemove){
                //System.out.println("Entry Key = " + e.getKey());
                toDelete.add(e.getKey());
            }
        } 
        // now were are done iterating, delete those found
        for (Iterator j = toDelete.iterator(); j.hasNext();){
            journalEntries.remove((String)j.next());
        }
        // remove the archive type
        archiveTypes.removeJournalType(typeToRemove);
    }
    
    public void clear(DynamicTree treePanel){
        treePanel.clear();
        archiveTypes.clear();
        journalEntries.clear();        
    }
    
    // Check if file is dirty.
    // If so get user to make a "Save? yes/no/cancel" decision.
    /** Determines if the archive changes should be saved or not.
     * @return True if the user saves or abandons changes, false otherwise
     */    
    boolean okToAbandon() {
       if (dirtyState) {
        return false;
       }
       return true;
    }
    
    private HashMap packPassword() {
        HashMap pwStore = new HashMap();
        
        if(!pwEnabled){
            pwStore.put("length",new Integer(0));
            return pwStore;
        }else{
            pwStore.put("length",new Integer(pw.length));
            for (int i = 0; i < pw.length; i++){
                pwStore.put(new Integer(i),new Character(pw[i]));
            }
            return pwStore;
        }
    }
    
    private void unpackPassword(HashMap pwStore){
        int length = ((Integer)(pwStore.get("length"))).intValue();
        if(length == 0){
            pwEnabled = false;
            pw = null;
           return;
        }else{
            pwEnabled = true;
            pw = new char[length];
            for (int i = 0; i < length; i++){
                pw[i] = ((Character)(pwStore.get(new Integer(i)))).charValue();
            }
            return;
        }
    }
    
    public boolean removeEntry(JournalEntry entry) {
        String key = entry.getKey();
        if (journalEntries.containsKey(key)){
            journalEntries.remove(key);
            return true;
        }
        return false;
    }
    
}
