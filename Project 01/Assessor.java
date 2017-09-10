/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse360project;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * @author davidedwards
 */
public class Assessor extends JPanel {
    
    JLabel lblNumber = new JLabel();
    JComboBox cboMenu = new JComboBox();
    
    
    JCheckBox chkOption1 = new JCheckBox();
    JCheckBox chkOption2 = new JCheckBox();
    JCheckBox chkOption3 = new JCheckBox();
    JCheckBox chkOption4 = new JCheckBox();
    
    
    JButton btnOption1 = new JButton();
    JButton btnOption2 = new JButton();
    JButton btnOption3 = new JButton();
    JButton btnOption4 = new JButton();
    
    JTextField txtInput = new JTextField();
    JPanel txtPanel = new JPanel();
    
    String strOption1 = new String("Option 1");
    String strOption2 = new String("Option 2");
    String strOption3 = new String("Option 3");
    String strOption4 = new String("Option 4");
    
    JButton btnNext = new JButton("Next");
    
    
    JPanel btnPanel = new JPanel();
    JPanel cboPanel = new JPanel();
    JPanel rdbPanel = new JPanel();
    
    FlowLayout flo = new FlowLayout();
    public Assessor() {
        
        
        Dimension minimumSize = new Dimension(300,300);
        setPreferredSize(minimumSize);
        
        if (state.getState() == 0) {
            
            removeAll();
            setLayout(new FlowLayout());
            
            lblNumber.setText("David Edwards");
            
            add(lblNumber);
            
        }
        
        if (state.getState() == 1) {
            
            //clear the component field
            removeAll();
            cboPanel.removeAll();
            
            //set the panel layouts
            setLayout(new GridLayout(2,1));
            cboPanel.setLayout(flo);
            
            //populate list for cboMenu
            cboMenu.addItem(strOption1);
            cboMenu.addItem(strOption2);
            cboMenu.addItem(strOption3);
            cboMenu.addItem(strOption4);
            
            cboMenu.setSelectedItem(strOption1);
            
            lblNumber.setText("Question 1");
            
            cboPanel.add(cboMenu);
            
            add(lblNumber);
            add(cboPanel);
            
        }
        
        if (state.getState() == 2) {
            
            //clear the component field
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
            
            //add btnNext to the JPanel
            btnPanel.add(btnNext);
            
            add(rdbPanel);
            add(btnPanel);
        }
        
        if (state.getState() == 3) {
            
            //remove components that may be on the panels
            removeAll();
            btnPanel.removeAll();
          
            //set layout
            setLayout(new GridLayout(2,1));
            btnPanel.setLayout(new GridLayout(4,1));
            
            
            //set text of lblnumber
            lblNumber.setText("Question 3");
            
            //set text of buttons
            btnOption1.setText(strOption1);
            btnOption2.setText(strOption2);
            btnOption3.setText(strOption3);
            btnOption4.setText(strOption4);
            
            //add lblNumber to jpanel
            add(lblNumber);
            
            //add buttons to JPanel            
            btnPanel.add(btnOption1);
            btnPanel.add(btnOption2);
            btnPanel.add(btnOption3);
            btnPanel.add(btnOption4);
           
            add(btnPanel);
        }
        
        if (state.getState() == 4) {
            
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
            
            txtPanel.add(txtInput);
            btnPanel.add(btnNext);
            
            add(lblNumber);
            add(txtPanel);
            add(btnPanel);
        }
        cboMenu.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cboMenuActionPerformed(e);
                }
            });
        
        btnNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnNextActionPerformed(e);
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
    }
    
    private void btnOption4ActionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(this, "Hello!");
    }
    
    private void btnOption3ActionPerformed(ActionEvent e) {
        
        
        JOptionPane.showMessageDialog(this, "Hello!");
    }
    
    private void btnOption2ActionPerformed(ActionEvent e) {
        
        
        JOptionPane.showMessageDialog(this, "Hello!");
    }
    
    private void btnOption1ActionPerformed(ActionEvent e) {
        
        
        JOptionPane.showMessageDialog(this, "Hello!");
    }
    
    private void btnNextActionPerformed(ActionEvent e) {
        
        JOptionPane.showMessageDialog(this, "Hello!");    
    }
    
    
    
    private void cboMenuActionPerformed(ActionEvent evt) {
        
        JOptionPane.showMessageDialog(this, "Hello!");}
    
    public void changeState(int s) {
        
        state.changeState(s);
    }
    
}
