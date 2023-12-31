/*
 * LunarToolFrame.java
 *
 * Created on September 10, 2002, 2:18 PM
 */

package driver.ted.toolbox;

import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.awt.*;
import java.util.*;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author  tdriver
 */
public class ColorChooserFrame extends javax.swing.JInternalFrame {
    
     public static Color gradColor1 = Color.white;
     public static Color gradColor2 = Color.black;
     boolean TEXT_SELECTED = true;
     Color currentColor = Color.white;
     boolean isApplet;
   
    /** Creates new form LunarRiseSetFrame */
      public ColorChooserFrame(boolean applet) {
        isApplet = applet;
        initComponents();
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        buttonGroup1 = new javax.swing.ButtonGroup();
        textRadio = new javax.swing.JRadioButton();
        backRadio = new javax.swing.JRadioButton();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        backPanel = new javax.swing.JPanel();
        frontPanel = new javax.swing.JPanel();
        panelButton = new javax.swing.JButton();
        gradPanel = new gradientPane();
        jSlider1 = new javax.swing.JSlider();
        gradButton = new javax.swing.JButton();
        t_ColorChooser1 = new driver.ted.beans.T_ColorChooser();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(135, 77, 48)));
        setClosable(true);
        setIconifiable(true);
        setTitle("Color Chooser");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/driver/ted/toolbox/images/utilitiestool.gif")));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setPreferredSize(new java.awt.Dimension(515, 341));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        buttonGroup1.add(textRadio);
        textRadio.setSelected(true);
        textRadio.setText("Text");
        textRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textRadioActionPerformed(evt);
            }
        });

        getContentPane().add(textRadio, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        buttonGroup1.add(backRadio);
        backRadio.setText("Background");
        backRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backRadioActionPerformed(evt);
            }
        });

        getContentPane().add(backRadio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, -1, -1));

        jTextArea1.setFont(new java.awt.Font("Courier", 0, 12));
        jTextArea1.setText("This is a test of the Emergency\nBroadcast System.  If this had\nbeen an actual emergency, you\nwould have been given instructions...");
        jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        getContentPane().add(jTextArea1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 220, 80));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 190, 30, 100));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 30, 100));

        jCheckBox1.setToolTipText("Select to change the color of the panel below");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        getContentPane().add(jCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(264, 167, -1, -1));

        jCheckBox2.setToolTipText("Select to change the color of the panel below");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        getContentPane().add(jCheckBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 167, -1, -1));

        backPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        backPanel.setBackground(new java.awt.Color(0, 0, 0));
        frontPanel.setBackground(new java.awt.Color(255, 255, 255));
        backPanel.add(frontPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 130, 80));

        getContentPane().add(backPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 190, 150, 100));

        panelButton.setBackground(new java.awt.Color(255, 255, 255));
        panelButton.setText("Switch");
        panelButton.setToolTipText("Press to switch which color will be changed");
        panelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                panelButtonActionPerformed(evt);
            }
        });

        getContentPane().add(panelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 170, -1, 20));

        gradPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        getContentPane().add(gradPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 210, 110));

        jSlider1.setMinimum(1);
        jSlider1.setToolTipText("Slide to change gradient spacing");
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        getContentPane().add(jSlider1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 140, -1));

        gradButton.setText("Switch");
        gradButton.setToolTipText("Press to switch which color will be changed");
        gradButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gradButtonActionPerformed(evt);
            }
        });

        getContentPane().add(gradButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(429, 9, -1, 20));

        t_ColorChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_ColorChooser1ActionPerformed(evt);
            }
        });

        getContentPane().add(t_ColorChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 270, 150));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void t_ColorChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_ColorChooser1ActionPerformed
 currentColor = t_ColorChooser1.getCurrentColor();
    // Check the Text area
    if(TEXT_SELECTED == true){
      jTextArea1.setForeground(currentColor);
    }else{
      jTextArea1.setBackground(currentColor);
    }
    if(jCheckBox1.isSelected()){
      jPanel1.setBackground(currentColor);
    }

    if(jCheckBox2.isSelected()){
      jPanel2.setBackground(currentColor);
    }

    // set the main region in the bordered panel
    frontPanel.setBackground(currentColor);

    
    gradColor1 = currentColor;
    gradPanel.repaint();

    //update switch buttons
    gradButton.setBackground(gradColor1);
    panelButton.setBackground(currentColor);
    }//GEN-LAST:event_t_ColorChooser1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        jPanel2.setBackground(currentColor);
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        jPanel1.setBackground(currentColor);
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void panelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_panelButtonActionPerformed
        Color temp = frontPanel.getBackground();
        frontPanel.setBackground(backPanel.getBackground());
        backPanel.setBackground(temp);
        panelButton.setBackground(frontPanel.getBackground());
    }//GEN-LAST:event_panelButtonActionPerformed

    private void gradButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gradButtonActionPerformed
        Color temp = gradColor1;
        gradColor1 = gradColor2;
        gradColor2 = temp;
        gradButton.setBackground(gradColor1);
        gradPanel.repaint();
    }//GEN-LAST:event_gradButtonActionPerformed

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        gradPanel.repaint();
    }//GEN-LAST:event_jSlider1StateChanged

    private void backRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backRadioActionPerformed
        if(TEXT_SELECTED)
      TEXT_SELECTED = false;
    }//GEN-LAST:event_backRadioActionPerformed

    private void textRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textRadioActionPerformed
        if(!TEXT_SELECTED)
      TEXT_SELECTED = true;
    }//GEN-LAST:event_textRadioActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        if(!isApplet){
            driver.ted.toolbox.ToolBoxApp.decrementFrameCount();
        }else{
            driver.ted.toolbox.ToolBoxJPanel.decrementFrameCount();
        }
    }//GEN-LAST:event_formInternalFrameClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backPanel;
    private javax.swing.JRadioButton backRadio;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel frontPanel;
    private javax.swing.JButton gradButton;
    private javax.swing.JPanel gradPanel;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public static javax.swing.JSlider jSlider1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton panelButton;
    private driver.ted.beans.T_ColorChooser t_ColorChooser1;
    private javax.swing.JRadioButton textRadio;
    // End of variables declaration//GEN-END:variables
    
class gradientPane extends JPanel{

  Color color1;
  Color color2;
  float y1, y2;
  float width, height, percent;


 public gradientPane(){
  color1 = Color.white;
  color2 = Color.black;
  y1 = 100;
  y2 = 50;
  width = 215;
  height = 111;
  percent = (float)0.5;
 }


 public void paintComponent(Graphics g){
  // get the define parameters
    color1 = ColorChooserFrame.gradColor1;
    color2 = ColorChooserFrame.gradColor2;
    percent = (float)ColorChooserFrame.jSlider1.getValue()/100;
    
    // calculate the gradient coordinates
    //y2 = (1 - percent)*width;
    y2 = percent*width;
    y1 = percent*height;

    Graphics2D g2d = (Graphics2D) g;

    super.paintComponent(g);

    g2d.setPaint(
          new GradientPaint(0,y2,color1,
                            0,y1,color2,
                            true));   // cyclic
    g2d.fill(new Rectangle2D.Double(0,0,width,height));
  }
}

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
    decimalRadio.setText("Integer");
    decimalRadio.setBounds(new Rectangle(78, 0, 62, 24));
    decimalRadio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        decimalRadio_actionPerformed(e);
      }
    });
    decimalRadio.setSelected(true);
    decimalRadio.setNextFocusableComponent(hexRadio);
    hexRadio.setText("Hex");
    hexRadio.setBounds(new Rectangle(143, 5, 43, 14));
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

}
