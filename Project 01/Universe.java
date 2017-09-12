/**
 * This is the Assessor class
 * It displays the input options for the user
 * during operation of the virtual tutor program
 * 
 */
/**
 *
 * @author David Edwards
 * cse360
 * recitation project 1
 * team 4
 * Submitted: 9/11/2017
 */
package edu.asu.CSE360._01._04;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;

public class Assessor extends JPanel {
    
    JLabel lblQuestion, lblName;
    JComboBox<String> cboMenu;
    JPanel btnPanel, cboPanel, rdbPanel;
    JTextField txtInput;
    JPanel txtPanel;
    String name = "David";
	String options[];
	String question;
	JCheckBox[] chkOptions;
	JButton[] btnOptions;
        
    //default constructor
    public Assessor() {
    	
    	//initialize question and string text
    	question = "What is the value of 11+12+11 ?";
    	options = new String[4];
    	options[0] = "Thirty four";
		options[1] = "Thirty five";
		options[2] = "Thirty nine";
		options[3] = "Thirty three";
		
		//set layout and border for the panel
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));
    	
		//initialize JPanels
        txtPanel = new JPanel();       
        btnPanel = new JPanel();
        cboPanel = new JPanel();
        rdbPanel = new JPanel();
        
        //set layouts for the panels
        cboPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        rdbPanel.setLayout(new BoxLayout(rdbPanel, BoxLayout.Y_AXIS));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		txtPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        //initialize question label
        lblQuestion = new JLabel(question);
        lblName = new JLabel(name, SwingConstants.CENTER);
        
        cboMenu = new JComboBox<String>(options);
        cboMenu.setRenderer(new ComboBoxRenderer("Select"));	//change the default renderer of the ComboBox
        cboMenu.setSelectedIndex(-1);	//set default text as the current index
        cboMenu.addActionListener(new ActionListener() {
        	//invoked when the combobox item is selected
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = cboMenu.getSelectedIndex();
				if(selectedIndex >= 0)
					displayDialog(cboMenu.getItemAt(selectedIndex));
			}
		});
		cboPanel.add(cboMenu);
		
		chkOptions = new JCheckBox[options.length];
		btnOptions = new JButton[options.length];
		
		for(int i=0; i< options.length; i++) {
			chkOptions[i] = new JCheckBox(options[i]);
			rdbPanel.add(chkOptions[i]);
			scaleCheckBoxIcon(chkOptions[i]);	//scale CheckBox to size according to the current viewport
			//action listener to the checkbox to respond to user's action
			chkOptions[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					/**allows for multiple options to be selected by the user.
					 * Thus, it iterates through the CheckBoxes and concatenates the answer(s) selected by the user
					 */
					String answers = "";
					for (JCheckBox jCheckBox : chkOptions) {
						if(jCheckBox.isSelected())
							answers += jCheckBox.getText() + "\n";
					}
					if(answers != "")
						displayDialog(answers);
				}
			});
			
			btnOptions[i] = new JButton(options[i]);
			btnPanel.add(Box.createVerticalStrut(15));	//add a vertical spacing between the buttons in the box layout
			btnPanel.add(btnOptions[i]);
			//button clicked event listener which responds when a button is pressed by the user
			btnOptions[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					displayDialog(e.getActionCommand());
				}
			});
		}
		
		//initialize the TextField and adds an action listener to it that responds when user presses the Enter key
		txtInput = new JTextField();
		txtInput.setBorder(new LineBorder(Color.BLACK, 3));
		txtInput.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayDialog(txtInput.getText());
			}
		});
		txtPanel.add(txtInput);
		this.add(lblName);
		
		this.addComponentListener(new ComponentAdapter() {
			/**invoked when the size of the parent frame changes and subsequently the size of the Assessor panel changes
			 * changes the font size of the JLabels, ComboBox, CheckBoxes, JButtons and JTextField
			 */
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
				lblName.setFont(lblName.getFont().deriveFont((width + height)/15f));
				lblQuestion.setFont(lblQuestion.getFont().deriveFont((width + height)/40f));
				cboMenu.setFont(cboMenu.getFont().deriveFont((width + height)/40f));
				cboMenu.setSize(new Dimension(width - 100, cboMenu.getHeight()));
				for (JCheckBox jcb : chkOptions) {
					jcb.setFont(jcb.getFont().deriveFont((width + height)/40f));
					scaleCheckBoxIcon(jcb);		//Scale CheckBoxes as per the size of the parent
				}
				for (JButton jb : btnOptions)
					jb.setFont(jb.getFont().deriveFont((width + height)/40f));
				txtInput.setFont(txtInput.getFont().deriveFont((width + height)/40f));
				txtInput.setPreferredSize(new Dimension(width - 80, 80));	//change the width of the TextField as per the width of the panel
		}
	});
    }
    
    private void displayDialog(String answer) {
		JOptionPane.showMessageDialog(this, "Your answer :- \n" + answer, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
    /**
     * changeState performs the state operations based on the 
     * input parameter state
     * @param state 
     */
    public void changeState(int state) {
    	//clear the JPanel's components
    	this.removeAll();
    	//check the state's value for 0
		if(state == 0)
			this.add(lblName);
		else {
			//add question to the panel in the border layout
			this.add(lblQuestion,BorderLayout.NORTH);
			//based on the state, the corresponding panel is added to the Assessor panel
			if(state == 1) {
				this.add(cboPanel);
				cboMenu.setSelectedIndex(-1);
			}
			else if(state == 2) {
				for (JCheckBox jcb : chkOptions)
					jcb.setSelected(false);
				this.add(rdbPanel);
			}
			else if(state == 3) {
				this.add(btnPanel);
			}
			else if(state == 4) {
				txtInput.setText("");
				this.add(txtPanel);
			}
		}
	}
    
    /**
     * Action event method for the ComboBox
     * These perform the necessary operations when the value in a ComboBox is clicked
     */ 
    private void cboMenuActionPerformed(ActionEvent evt) {
    	int selectedIndex = cboMenu.getSelectedIndex();
		if(selectedIndex >= 0)
			JOptionPane.showMessageDialog(this, cboMenu.getItemAt(selectedIndex));
    }
    
    /**Due to high screen resolution, the CheckBoxes are so small that it is not visible properly
     * On top of that, when the application is resized, its size is not consistent with other elements
     * Therefore, to handle this scenario, we have included the code to scale the CheckBoxes w.r.t. the Frame
     * This code snippet has been taken from stackoverflow
     * Reference: https://stackoverflow.com/questions/4770022/make-jcheckbox-bigger#answer-26995048
     * @param checkbox
     */
 	public static void scaleCheckBoxIcon(JCheckBox checkbox){
 	    boolean previousState = checkbox.isSelected();
 	    checkbox.setSelected(false);
 	    FontMetrics boxFontMetrics =  checkbox.getFontMetrics(checkbox.getFont());
 	    Icon boxIcon = UIManager.getIcon("CheckBox.icon");
 	    BufferedImage boxImage = new BufferedImage(
 	        boxIcon.getIconWidth(), boxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
 	    Graphics graphics = boxImage.createGraphics();
 	    try{
 	        boxIcon.paintIcon(checkbox, graphics, 0, 0);
 	    }finally{
 	        graphics.dispose();
 	    }
 	    ImageIcon newBoxImage = new ImageIcon(boxImage);
 	    Image finalBoxImage = newBoxImage.getImage().getScaledInstance(
 	        boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH);
 	    checkbox.setIcon(new ImageIcon(finalBoxImage));

 	    checkbox.setSelected(true);
 	    Icon checkedBoxIcon = UIManager.getIcon("CheckBox.icon");
 	    BufferedImage checkedBoxImage = new BufferedImage(
 	        checkedBoxIcon.getIconWidth(),  checkedBoxIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
 	    Graphics checkedGraphics = checkedBoxImage.createGraphics();
 	    try{
 	        checkedBoxIcon.paintIcon(checkbox, checkedGraphics, 0, 0);
 	    }finally{
 	        checkedGraphics.dispose();
 	    }
 	    ImageIcon newCheckedBoxImage = new ImageIcon(checkedBoxImage);
 	    Image finalCheckedBoxImage = newCheckedBoxImage.getImage().getScaledInstance(boxFontMetrics.getHeight(), boxFontMetrics.getHeight(), Image.SCALE_SMOOTH);
 	    checkbox.setSelectedIcon(new ImageIcon(finalCheckedBoxImage));
 	    checkbox.setSelected(false);
 	    checkbox.setSelected(previousState);
 	}
    
 	/**Since ComboBox does not allow a default text (or selection of -1 index) by default,
 	 * we are creating a custom ListcellRenderer for the ComboBox and overriding the default one.
 	 * It allows us to set a default text 'title' for the ComboBox.
 	 */
    class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
        private String title;

        public ComboBoxRenderer(String title) {
            this.title = title;
        }

        //Overrides the default rendering operation for the component with a custom one
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean hasFocus)
        {
            if (index == -1 && value == null) setText(title);
            else setText(value.toString());
            return this;
        }
    }
    
}
