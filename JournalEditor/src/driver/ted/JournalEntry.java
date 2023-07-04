package driver.ted;

import javax.swing.*;
import java.io.*;
import javax.swing.text.*;
import driver.ted.utilities.T_JulianDate;

class JournalEntry implements Serializable, Comparable{

    /**
     *journal text contained in a styled document
     */
    private DefaultStyledDocument journalEntry;
    
    /**
     *journal date implemented as a T_JulianDate
     */
    //private T_JulianDate journalDate;
    private double journalDate;
    
    /**
     * journal entry type
     */
    private String journalType;
    
    /**
     * Entry Name ... won't change
     */
    private String ENTRY_NAME;
    
    /**
     * Constructor that sets the journal type and uses the current system
     * time as the journal entry time.
     *@param Type The type of entry this journal entry represents
     */
    public JournalEntry(){
        journalEntry = new DefaultStyledDocument();
        T_JulianDate temp = new T_JulianDate(); 
        journalDate = temp.getJulianDate();
        //journalDate = new T_JulianDate();        
        journalType = null;
        defineEntryName();
    }   
    
    /**
     * Constructor that sets the journal type and uses the current system
     * time as the journal entry time.
     *@param Type The type of entry this journal entry represents
     */
    public JournalEntry(String Type){
        journalEntry = new DefaultStyledDocument();
        T_JulianDate temp = new T_JulianDate(); 
        journalDate = temp.getJulianDate();
        //journalDate = new T_JulianDate();        
        journalType = Type;
        defineEntryName();
    }
    
    /**
     * Constructor that sets the journal type and time to use for journal entry time.
     *@param Type The type of entry this journal entry represents
     *@param JulianDate The double representing the julian date of the entry
     */
    public JournalEntry(String Type, double JulianDate){
        journalEntry = new DefaultStyledDocument();
        T_JulianDate temp = new T_JulianDate(JulianDate); 
        journalDate = temp.getJulianDate();
        //journalDate = new T_JulianDate(JulianDate);        
        journalType = Type;
        defineEntryName();
    }
    
        
   /* public void setDocument(Document doc){
        journalEntry = (DefaultStyledDocument)doc;
    }
    
  */  public DefaultStyledDocument getDocument(){
        return journalEntry;
    }
    
    
    public String getJournalDate(){
        T_JulianDate temp = new T_JulianDate(journalDate); 
        return temp.toMediumString();
    }
    
    public double getJulianDate(){
        return journalDate;
    }
    
    public String getKey(){
     /*   // should use some kind of hashcode here
        double jdate = journalDate.getJulianDate();       
        String key = String.valueOf(jdate) + " " + journalType;
        // Debug
        System.out.println("   Returning Key ..." + key);
      
        return key;    
      */
        // Debug
       // System.out.println("   Returning Key ..." + this.toString());
        return this.toString();
    }
    
    public void setJournalType(String Type){
       journalType = Type;
    }
    
    public String getJournalType(){
        return journalType;
    }
    
    public String toString(){
     return ENTRY_NAME;
    }
    
    /*           Private Functions     */
    /**
     * Function to define the name of the Entry that will appear on the tree
     */
    private void defineEntryName(){
     if(ENTRY_NAME == null){
        // define ENTRY_NAME for the first time
        T_JulianDate temp = new T_JulianDate(journalDate); 
        String entryDate = temp.getDate().toString();
        String entryTime = temp.getTime().toString();
        ENTRY_NAME = entryDate + ", " + entryTime; 
     }
    }
    
    public int compareTo(java.lang.Object obj) {
        JournalEntry entry = (JournalEntry)obj;
        int returnValue;
        T_JulianDate temp = new T_JulianDate(journalDate); 
        
        returnValue = journalType.compareToIgnoreCase(entry.getJournalType());
        if(returnValue != 0) 
            return returnValue;
        
        // reached here so journal types match
        // now test the Julian Dates
        if(temp.getJulianDate() < entry.getJulianDate())
            return -1;
        if(temp.getJulianDate() > entry.getJulianDate())
            return 1;
        
        // dates must match so return 0
        return 0;
    }
    
 }
 
 


