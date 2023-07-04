package driver.ted.beans;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.event.*;

/**
 * Title:        Ted's ColorChooser
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.1
 *
 * Change from version 1.0:
 * porting to the Forte Environment from JBuilder
 * Changes: TBD
 *
 */

public class T_ColorChooser extends JPanel {
  GridLayout gridLayout1 = new GridLayout();
  JPanel redPanel = new JPanel();
  JPanel greenPanel = new JPanel();
  JPanel bluePanel = new JPanel();
  JPanel colorValuePanel = new JPanel();
  JPanel setPanel = new JPanel();
  JButton setButton = new JButton();
  JLabel redLabel = new JLabel();
  JSlider red = new JSlider();
  JTextField redField = new JTextField();
  JLabel greenLabel = new JLabel();
  JLabel blueLabel = new JLabel();
  JLabel colorValueLabel = new JLabel();
  JSlider green = new JSlider();
  JTextField greenField = new JTextField();
  JSlider blue = new JSlider();
  JTextField blueField = new JTextField();
  ButtonGroup buttonGroup1 = new ButtonGroup();
  JRadioButton decimalRadio = new JRadioButton();
  JRadioButton hexRadio = new JRadioButton();
  JTextField colorValueField = new JTextField();
  private Color currentColor, labelColor;
  private int DefaultColor = 255;
  private boolean DECIMAL = true;
  private transient Vector actionListeners;
  JLabel jLabel1 = new JLabel();

  public T_ColorChooser() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    currentColor = new Color (DefaultColor, DefaultColor, DefaultColor);
    gridLayout1.setRows(5);
    gridLayout1.setColumns(1);
    this.setLayout(gridLayout1);
    redPanel.setLayout(null);
    setPanel.setLayout(null);
    setButton.setText("Set");
    setButton.setBounds(new Rectangle(105, 3, 53, 21));
    setButton.setNextFocusableComponent(red);
    setButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setButton_actionPerformed(e);
      }
    });
    colorValuePanel.setLayout(null);
    bluePanel.setLayout(null);
    greenPanel.setLayout(null);
    redLabel.setToolTipText("Select the Red Value");
    redLabel.setText(" R ");
    redLabel.setBounds(new Rectangle(0, 6, 16, 17));
    redField.setForeground(Color.white);
    redField.setPreferredSize(new Dimension(40, 21));
    redField.setHorizontalAlignment(SwingConstants.CENTER);
    redField.setBounds(new Rectangle(214, 0, 41, 31));
    redField.setEditable(false);
    red.setMinorTickSpacing(10);
    red.setMajorTickSpacing(50);
    red.setMaximum(255);
    red.setValue(255);
    red.setPaintTicks(true);
    red.setDoubleBuffered(true);
    red.setBounds(new Rectangle(14, 1, 200, 27));
    red.setNextFocusableComponent(green);
    red.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        red_stateChanged(e);
      }
    });
    greenLabel.setToolTipText("Select the Green Value");
    greenLabel.setText(" G ");
    greenLabel.setBounds(new Rectangle(0, 6, 16, 17));
    blueLabel.setToolTipText("Select the Blue Value");
    blueLabel.setText(" B ");
    blueLabel.setBounds(new Rectangle(0, 6, 14, 17));
    colorValueLabel.setText("Color Value");
    colorValueLabel.setBounds(new Rectangle(5, 4, 75, 17));
    greenField.setForeground(Color.white);
    greenField.setPreferredSize(new Dimension(40, 21));
    greenField.setHorizontalAlignment(SwingConstants.CENTER);
    greenField.setBounds(new Rectangle(214, 0, 41, 31));
    greenField.setEditable(false);
    green.setMinorTickSpacing(10);
    green.setMajorTickSpacing(50);
    green.setMaximum(255);
    green.setValue(255);
    green.setPaintTicks(true);
    green.setDoubleBuffered(true);
    green.setBounds(new Rectangle(14, 1, 200, 27));
    green.setNextFocusableComponent(blue);
    green.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        green_stateChanged(e);
      }
    });
    blueField.setForeground(Color.white);
    blueField.setPreferredSize(new Dimension(40, 21));
    blueField.setHorizontalAlignment(SwingConstants.CENTER);
    blueField.setBounds(new Rectangle(214, 0, 41, 31));
    blueField.setEditable(false);
    blue.setMinorTickSpacing(10);
    blue.setMajorTickSpacing(50);
    blue.setPaintTicks(true);
    blue.setMaximum(255);
    blue.setValue(255);
    blue.setDoubleBuffered(true);
    blue.setBounds(new Rectangle(14, 1, 200, 27));
    blue.setNextFocusableComponent(decimalRadio);
    blue.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        blue_stateChanged(e);
      }
    });
    decimalRadio.setText("Int");
    decimalRadio.setBounds(new Rectangle(78, 0, 43, 24));
    decimalRadio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        decimalRadio_actionPerformed(e);
      }
    });
    decimalRadio.setSelected(true);
    decimalRadio.setNextFocusableComponent(hexRadio);
    hexRadio.setText("Hex");
    hexRadio.setBounds(new Rectangle(123, 5, 57, 14));
    hexRadio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        hexRadio_actionPerformed(e);
      }
    });
    hexRadio.setNextFocusableComponent(colorValueField);
    colorValueField.setHorizontalAlignment(SwingConstants.CENTER);
    colorValueField.setBounds(new Rectangle(187, 0, 65, 25));
    colorValueField.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        colorValueField_mouseClicked(e);
      }
    });
    colorValueField.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        colorValueField_focusGained(e);
      }
    });
    colorValueField.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        colorValueField_actionPerformed(e);
      }
    });
    colorValueField.setNextFocusableComponent(setButton);
    this.setBorder(BorderFactory.createRaisedBevelBorder());
    jLabel1.setForeground(Color.black);
    jLabel1.setBounds(new Rectangle(200, 0, 41, 17));
    this.add(redPanel, null);
    redPanel.add(redLabel, null);
    redPanel.add(red, null);
    redPanel.add(redField, null);
    this.add(greenPanel, null);
    greenPanel.add(greenLabel, null);
    greenPanel.add(green, null);
    greenPanel.add(greenField, null);
    this.add(bluePanel, null);
    bluePanel.add(blueLabel, null);
    bluePanel.add(blue, null);
    bluePanel.add(blueField, null);
    this.add(colorValuePanel, null);
    colorValuePanel.add(colorValueLabel, null);
    colorValuePanel.add(hexRadio, null);
    colorValuePanel.add(colorValueField, null);
    colorValuePanel.add(decimalRadio, null);
    this.add(setPanel, null);
    setPanel.add(setButton, null);
    setPanel.add(jLabel1, null);
    buttonGroup1.add(decimalRadio);
    buttonGroup1.add(hexRadio);

    //set the colors and text to their initial values
    redField.setBackground(new Color(DefaultColor, 0, 0));
    greenField.setBackground(new Color(0, DefaultColor, 0));
    blueField.setBackground(new Color(0, 0, DefaultColor));
    red.setBackground(currentColor);
    green.setBackground(currentColor);
    blue.setBackground(currentColor);
    redField.setText( String.valueOf(red.getValue()) );
    greenField.setText( String.valueOf(green.getValue()) );
    blueField.setText( String.valueOf(blue.getValue()) );
    if(DECIMAL == false){
      colorValueField.setText(String.valueOf(Integer.toHexString(getColorValue(currentColor)).toUpperCase()));
    }else{
      colorValueField.setText(String.valueOf(Integer.toString(getColorValue(currentColor))));
    }

  }

  void setButton_actionPerformed(ActionEvent e) {
    fireActionPerformed(e);
  }

  public synchronized void removeActionListener(ActionListener l) {
    if (actionListeners != null && actionListeners.contains(l)) {
      Vector v = (Vector) actionListeners.clone();
      v.removeElement(l);
      actionListeners = v;
    }
  }
  public synchronized void addActionListener(ActionListener l) {
    Vector v = actionListeners == null ? new Vector(2) : (Vector) actionListeners.clone();
    if (!v.contains(l)) {
      v.addElement(l);
      actionListeners = v;
    }
  }
  protected void fireActionPerformed(ActionEvent e) {
    if (actionListeners != null) {
      Vector listeners = actionListeners;
      int count = listeners.size();
      for (int i = 0; i < count; i++) {
        ((ActionListener) listeners.elementAt(i)).actionPerformed(e);
      }
    }
  }

  void red_stateChanged(ChangeEvent e) {
    currentColor = new Color (red.getValue(), green.getValue(), blue.getValue());
    red.setBackground( currentColor );
    green.setBackground( currentColor );
    blue.setBackground( currentColor );
    redField.setText( String.valueOf(red.getValue()) );

    // use color determined from red slider only to set the background
    labelColor = new Color(red.getValue(),0, 0);
    redField.setBackground(labelColor);
    if(DECIMAL == false){
      colorValueField.setText(String.valueOf(Integer.toHexString(getColorValue(currentColor)).toUpperCase()));
    }else{
      colorValueField.setText(String.valueOf(Integer.toString(getColorValue(currentColor))));
    }
   }

  void green_stateChanged(ChangeEvent e) {
    greenField.setText( String.valueOf(green.getValue()) );

    // use color determined from green slider only to set the background
    labelColor = new Color(0, green.getValue(), 0);
    greenField.setBackground(labelColor);
    currentColor = new Color (red.getValue(), green.getValue(), blue.getValue());
    red.setBackground( currentColor );
    green.setBackground( currentColor );
    blue.setBackground( currentColor );
    if(DECIMAL == false){
      colorValueField.setText(String.valueOf(Integer.toHexString(getColorValue(currentColor)).toUpperCase()));
    }else{
      colorValueField.setText(String.valueOf(Integer.toString(getColorValue(currentColor))));
    }
  }

  void blue_stateChanged(ChangeEvent e) {
    currentColor = new Color (red.getValue(), green.getValue(), blue.getValue());
    red.setBackground( currentColor );
    green.setBackground( currentColor );
    blue.setBackground( currentColor );
    blueField.setText( String.valueOf(blue.getValue()) );

    // use color determined from blue slider only to set the background
    labelColor = new Color(0, 0, blue.getValue());
    blueField.setBackground(labelColor);
    if(DECIMAL == false){
      colorValueField.setText(String.valueOf(Integer.toHexString(getColorValue(currentColor)).toUpperCase()));
    }else{
      colorValueField.setText(String.valueOf(Integer.toString(getColorValue(currentColor))));
    }
  }

  private int getColorValue(Color c){
    int b = c.getBlue() << 16L;
    int g = c.getGreen() << 8L;
    int r = c.getRed();
    return b + g + r;
   }

  void decimalRadio_actionPerformed(ActionEvent e) {
    DECIMAL = true;
    colorValueField.setText(String.valueOf(Integer.toString(getColorValue(currentColor))));
    jLabel1.setText("");
  }

  void hexRadio_actionPerformed(ActionEvent e) {
    DECIMAL = false;
    colorValueField.setText(String.valueOf(Integer.toHexString(getColorValue(currentColor)).toUpperCase()));
    jLabel1.setText("(B G R)");
  }
  public java.awt.Color getCurrentColor() {
    return currentColor;
  }

  void colorValueField_actionPerformed(ActionEvent e) {
    int redMask = 0xFF;
    int greenMask = 0xFF00;
    int blueMask = 0xFF0000;
   try{
    // get the input color value and parse according to the format currently selected

    int newColorint = Integer.parseInt(colorValueField.getText(),DECIMAL?10:16);
    // create a new color with the value
    //masking to get the correct digits and shifting accordingling

    Color newColor = new Color(newColorint&redMask,(newColorint&greenMask)>>8,(newColorint&blueMask)>>16);
    // get the components of the new color
    int r = newColor.getRed();
    int g = newColor.getGreen();
    int b = newColor.getBlue();
    // set the sliders to the new value
    red.setValue(r);
    green.setValue(g);
    blue.setValue(b);
    // set the text boxes to the new color
    redField.setText(String.valueOf(r));
    greenField.setText(String.valueOf(g));
    blueField.setText(String.valueOf(b));
    //update the current Color and update the slider's backgrounds
    currentColor = newColor;
    red.setBackground(currentColor);
    green.setBackground(currentColor);
    blue.setBackground(currentColor);
   }catch(NumberFormatException nfe){
    javax.swing.JOptionPane.showMessageDialog(null,
            "You must enter an integer in the format specified!",
            "Color Chooser Entry Error",javax.swing.JOptionPane.ERROR_MESSAGE);
   }
  }

  void colorValueField_focusGained(FocusEvent e) {
    colorValueField.selectAll();
  }

  void colorValueField_mouseClicked(MouseEvent e) {
    colorValueField.selectAll();
  }
}