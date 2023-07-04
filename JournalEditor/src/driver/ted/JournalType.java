package driver.ted;

import java.io.Serializable;
import java.util.*;
/*
 * JournalType.java
 *
 * Created on March 6, 2002, 12:22 PM
 */

/**
 *
 * @author  tdriver
 * @version 
 */
public class JournalType implements Serializable {

    private Set journalTypes = new TreeSet();
    
    /** Creates new JournalType */
    public JournalType() {
    }
    
    /**
     *Create a new journalType
     *@param JType adds JType as a new Journal Type
     */
    public void addJournalType(String JType) throws DuplicateValueException{
         if(!journalTypes.add(JType)){
             throw new DuplicateValueException("Journal Type: " + JType + " already exists.");
         }
    }
    
    public void removeJournalType(String type){
        journalTypes.remove(type);
    }
    
    public String toString(){
        return journalTypes.toString();
    }
    
    public boolean contains(String JType){
        return journalTypes.contains(JType);
    }
    
    public int size(){
        return journalTypes.size();
    }
    
    public void clear(){
        journalTypes.clear();
    }
    
    
 }
 
 class DuplicateValueException extends java.lang.Exception{
     public DuplicateValueException(String message) {
         super(message);
     }
     
     public DuplicateValueException(){
         super("Value Already Exists");
     }
 }