/**
 * This is the Assessor class
 * It displays the input options for the user
 * during operation of the virtual tutor program
 * 
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * David Edwards
 * cse360
 * recitation project 1
 * team 4
 * Submitted: 9/11/2017
 */
public class Assessor extends JPanel {
    
    JLabel lblNumber;
    JComboBox<String> cboMenu;
    JCheckBox chkOption1, chkOption2, chkOption3, chkOption4;
    JButton btnOption1, btnOption2, btnOption3, btnOption4;
    JPanel btnPanel, cboPanel, rdbPanel;
    JTextField txtInput;
    JPanel txtPanel;
    String strOption1, strOption2, strOption3, strOption4;
    FlowLayout flo;
    String name = "David";
    Box box;
	
        
    //default constructor
    public Assessor() {
    	
        //initialize string option variables
    	strOption1 = new String("Option 1");
        strOption2 = new String("Option 2");
        strOption3 = new String("Option 3");
        strOption4 = new String("Option 4");
        
        
        //initialize label lblnumber
    	lblNumber = new JLabel();
        
        //initialize combo box cboMenu
        cboMenu = new JComboBox<String>();
        
        //add string option items to cboMenu
        cboMenu.addItem(strOption1);
        cboMenu.addItem(strOption2);
        cboMenu.addItem(strOption3);
        cboMenu.addItem(strOption4);
        
        //initialize check boxes 
        chkOption1 = new JCheckBox();
        chkOption2 = new JCheckBox();
        chkOption3 = new JCheckBox();
        chkOption4 = new JCheckBox();
        
        //initialize buttons
        btnOption1 = new JButton();
        btnOption2 = new JButton();
        btnOption3 = new JButton();
        btnOption4 = new JButton();
        
        //initialize text field txtInput
        txtInput = new JTextField();
        
        //initialize JPanels
        txtPanel = new JPanel();       
        btnPanel = new JPanel();
        cboPanel = new JPanel();
        rdbPanel = new JPanel();
        
        //initialize FlowLayout flo
        flo = new FlowLayout();
        
        //initialize box 
        box = new Box(BoxLayout.Y_AXIS);
        
        /**
         * Add the action listeners to each component
         */
        cboMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cboMenuActionPerformed(e);
            }
        });   
    
        chkOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkOption1ActionPerformed(e);
            }
        });
        
        chkOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkOption2ActionPerformed(e);
            }
        });
        
        chkOption3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkOption3ActionPerformed(e);
            }
        });
        
        chkOption4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chkOption4ActionPerformed(e);
            }
        });
    
        btnOption1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOption1ActionPerformed(e);
            }
        });

        btnOption2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOption2ActionPerformed(e);
            }
        });

        btnOption3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOption3ActionPerformed(e);
            }
        });

        btnOption4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnOption4ActionPerformed(e);
            }
        });
        
        txtInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtInputActionPerformed(e);
            }
        });

        //set the text of lblNumber when program runs
        lblNumber.setText(name);

        //add lblNumber to the panel for state 0
        add(lblNumber);
    }
	
    /**
     * changeState performs the state operations based on the 
     * input parameter state
     * @param state 
     */
    public void changeState(int state) {
        
        //check the state's value for 0
        if (state == 0) { 
            
            //clear the JPanel's components
            removeAll();
            
            //set the layout of the JPanel
            setLayout(flo);
            
            
            lblNumber.setText(name);
            
            add(lblNumber);
            
        }
        
        //check for 1
        if (state == 1) {
            
            //clear the component field
            removeAll();
            
            //set the panel layouts
            setLayout(new GridLayout(2,1));
            cboPanel.setLayout(flo);
            
            
            cboMenu.setSelectedItem(strOption1);
            
            lblNumber.setText("Question 1");
            
            cboPanel.add(cboMenu);
            
            add(lblNumber);
            add(cboPanel);
            
        }
        
        //check for 2
        if (state == 2) {
            
            //clear the component fields
            removeAll();
            rdbPanel.removeAll();
            btnPanel.removeAll();
            
            //set the panel layouts
            setLayout(new GridLayout(3,1));
            rdbPanel.setLayout(new GridLayout(3,1));
            btnPanel.setLayout(flo);
            
            //set text of check boxes
            chkOption1.setText(strOption1);
            chkOption2.setText(strOption2);
            chkOption3.setText(strOption3);
            chkOption4.setText(strOption4);
            
            //set text of number label
            lblNumber.setText("Question 2");
            
            //add question number label onto the JPanel
            add(lblNumber);
            
            //add check boxes onto the JPanel
            rdbPanel.add(chkOption1);
            rdbPanel.add(chkOption2);
            rdbPanel.add(chkOption3);
            rdbPanel.add(chkOption4);
            
            add(rdbPanel);
            add(btnPanel);
        }
        
        //check for 3
        if (state == 3) {
            
            //remove components that may be on the panels
            removeAll();
            btnPanel.removeAll();
          
            //set layout
            setLayout(new GridLayout(2,1));          
            
            //set text of lblnumber
            lblNumber.setText("Question 3");
            
            //set text of buttons
            btnOption1.setText(strOption1);
            btnOption2.setText(strOption2);
            btnOption3.setText(strOption3);
            btnOption4.setText(strOption4);
            
            //add lblNumber to jpanel
            add(lblNumber);
            
            //add buttons to btnPanel            
            btnPanel.add(btnOption1);
            btnPanel.add(btnOption2);
            btnPanel.add(btnOption3);
            btnPanel.add(btnOption4);
            
            //set btnPanel maxsize
            btnPanel.setMaximumSize(new Dimension(100,200));
            
            //have box display buttons through center
            box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
            
            //add the btnPanel to box
            box.add(btnPanel);
            
            //add box to the jpanel
            add(box);
            
        }
        
        if (state == 4) {
            
            //remove components
            removeAll();
            txtPanel.removeAll();
            btnPanel.removeAll();
            
            //set the panel's layout
            setLayout(new GridLayout(3,1));
            txtPanel.setLayout(new FlowLayout());
            
            //set lblNumber's text
            lblNumber.setText("Question 4");
            
            //set the placeholder text for txtInput
            txtInput.setText("Write here...");
            
            //set the size for txtInput
            txtInput.setPreferredSize(new Dimension(150, 27));
            
            //add txtInput to txtPanel
            txtPanel.add(txtInput);
            
            //add components to jpanel
            add(lblNumber);
            add(txtPanel);
            add(btnPanel);
        }
        
    }
    /**
     * Action event methods
     * These perform the necessary operations when
     * a UI component is clicked
     */
    
    private void chkOption1ActionPerformed(ActionEvent e) {
        if (chkOption1.isSelected()) {
            
            JOptionPane.showMessageDialog(this, "chk1!");
        } else {
            
        }
    }
    
    private void chkOption2ActionPerformed(ActionEvent e) {
        if (chkOption2.isSelected()) {
            
            JOptionPane.showMessageDialog(this, "chk2!");
        } else {
            
        }
    }
    
    private void chkOption3ActionPerformed(ActionEvent e) {
        if (chkOption3.isSelected()) {
            
            JOptionPane.showMessageDialog(this, "chk3!");
        } else {
            
        }
    }
    
    private void chkOption4ActionPerformed(ActionEvent e) {
        if (chkOption4.isSelected()) {
            
            JOptionPane.showMessageDialog(this, "chk4!");
        } else {
            
        }
    }
    
    private void txtInputActionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(txtInput, "Text Submitted!");
    }
    
    private void btnOption4ActionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(this, "Opt4!");
    }
    
    private void btnOption3ActionPerformed(ActionEvent e) {
        
        
        JOptionPane.showMessageDialog(this, "Opt3!");
    }
    
    private void btnOption2ActionPerformed(ActionEvent e) {       
        
        JOptionPane.showMessageDialog(this, "Opt2!");
    }
    
    private void btnOption1ActionPerformed(ActionEvent e) {
        
        
        JOptionPane.showMessageDialog(this, "Opt1!");
    }
    
    private void btnNextActionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(this, "Next!");    
    }
       
    private void cboMenuActionPerformed(ActionEvent evt) {
        
        JOptionPane.showMessageDialog(this, "Menu!");
    }
    
    
}
