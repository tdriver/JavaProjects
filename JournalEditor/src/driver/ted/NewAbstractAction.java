/*
 * newAbstractAction.java
 *
 * Created on July 1, 2002, 1:11 PM
 */

package driver.ted;

import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author  tdriver
 */
public abstract class NewAbstractAction extends AbstractAction {
    
    public NewAbstractAction() {
    }
    
    public NewAbstractAction( String name, Icon icon, 
      String description, Integer mnemonic )
   {
      // initialize properties
      if(name != null) setName( name );
      if (icon != null) setSmallIcon( icon );
      if (description != null) setShortDescription( description );
      if (mnemonic != null) setMnemonic( mnemonic );
   }
   
   // set Action name
   public void setName( String name )
   {
      putValue( Action.NAME, name );
   }
   
   // set Action Icon
   public void setSmallIcon( Icon icon )
   {
      putValue( Action.SMALL_ICON, icon );
   }
   
   // set Action short description
   public void setShortDescription( String description )
   {
      putValue( Action.SHORT_DESCRIPTION, description );
   }
   
   // set Action mnemonic key
   public void setMnemonic( Integer mnemonic ) 
   {
      putValue( Action.MNEMONIC_KEY, mnemonic );
   }
   
   // abstract actionPerformed method 
   public abstract void actionPerformed( ActionEvent event );
    
}
