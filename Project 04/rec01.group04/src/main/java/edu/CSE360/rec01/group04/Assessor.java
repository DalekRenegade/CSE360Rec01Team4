/**
 *this is the Assessor class
 *it displays the input options for the user
 *requirement implemented: Verifies answer and displays 
 *appropriate dialog. Also allows user to choose when to 
 *start the quiz.
 *Completion time: 14 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */

package edu.CSE360.rec01.group04;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.util.Observable;
import java.awt.font.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Assessor implements ActionListener {


	JLabel lblQuestion[], lblName;
	@SuppressWarnings("unchecked")
	JComboBox[] cboMenu;
	//JComboBox<String> cboMenu0, cboMenu1;

	//JPanel btnPanel[], cboPanel, rdbPanel[];
	
	JPanel[][] componentHolder;

	//JPanel txtPanel[];
	JTextField txtInput[];
	String name = "David";
	String[][][] options, hints;
	JButton btnUnguidedQuiz, btnGuidedQuiz, btnSubmit[];
	JPanel assessorPanel;
	JScrollPane assessorScrollPane;
	String problemType;
	int problemID;
	Quiz quiz;
	int counts[], numTries[][];
	JLabel lblHints;
	boolean isGuided = false;
	

	String[][] question, answer, userAnswer;
	JCheckBox[][] chkOptions;
	JButton[][] btnOptions;
	ControlCenter cc;

	int state = 0;

	private String currentElement;
	private int optionCount = 0;
	private int questionCount = 0;
	private int count = 0;
	private int answerIndex = 0;
	private int category = -1;
	Thread cThread;

	// default constructor
	public Assessor() {
		//set cc to the instance of ControlCenter
		cc = ControlCenter.getInstance();
				
		GuidedQuiz quiz = (GuidedQuiz)cc.gquiz;
		UnguidedQuiz uquiz = (UnguidedQuiz)cc.uquiz;
		hints = ((GuidedQuiz)quiz).getHints();
		
		//get the important constants from quiz
		//ideally, we can add questions to xml file and have GUI respond completely without refactoring
		counts = quiz.getCounts();
		problemID = counts[0];
		questionCount = counts[1];
		optionCount = counts[2];
		

		//load the quiz from Quiz.java into arrays
		answer = quiz.getAnswer();
		question = quiz.getQuestion();
		options = quiz.getOptions();
		userAnswer = new String[problemID][questionCount];
		
		assessorPanel = new JPanel();
		assessorScrollPane = new JScrollPane(assessorPanel);
		assessorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		assessorScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		//initialize components
		btnUnguidedQuiz = new JButton("Start Quiz");
		btnGuidedQuiz = new JButton("<html>Start Quiz<br>Under Guidance<html>");
		btnOptions = new JButton[questionCount][optionCount];
		txtInput = new JTextField[questionCount];
		lblQuestion = new JLabel[problemID];
		chkOptions = new JCheckBox[questionCount][optionCount];
		btnSubmit = new JButton[questionCount];
		cboMenu = new JComboBox[questionCount];
		lblHints = new JLabel();
		lblHints.setForeground(Color.RED);
		
		
		numTries = new int[problemID][questionCount];

		//initialize btnSubmit on heap
		for (int i = 0; i < questionCount; i++)
			btnSubmit[i] = new JButton("Submit");

		//initialize lblQuestion on heap
		for (int i = 0; i < problemID; i++)
			lblQuestion[i] = new JLabel();

		//initialize chkOptions on heap
		for (int k = 0; k < questionCount; k++)
			for (int i = 0; i < optionCount; i++)
				chkOptions[k][i] = new JCheckBox();

		//initialize JPanels
		//txtPanel = new JPanel[questionCount];
		componentHolder = new JPanel[problemID][questionCount];

		

		//initialize JPanels on the heap and set their layouts
//		for (int i = 0; i < questionCount; i++) {
//			//cboPanel[i] = new JPanel();
//			rdbPanel[i] = new JPanel();
//			btnPanel[i] = new JPanel();
//			txtPanel[i] = new JPanel();
//
//			//cboPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
//			rdbPanel[i].setLayout(new BoxLayout(rdbPanel[i], BoxLayout.Y_AXIS));
//			btnPanel[i].setLayout(new BoxLayout(btnPanel[i], BoxLayout.Y_AXIS));
//			txtPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT));
//		}
		for (int i = 0; i < problemID; i++) {
			for (int j = 0; j < questionCount; j++) {
				componentHolder[i][j] = new JPanel();
				
				if (i != 1) 
					componentHolder[i][j].setLayout(new FlowLayout(FlowLayout.LEFT));
				else
					componentHolder[i][j].setLayout(new BoxLayout(componentHolder[i][j], BoxLayout.Y_AXIS));
				
			}
		}

		// set layout and border for the panel
		assessorPanel.setLayout(new BorderLayout());
		
		assessorPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));

		lblName = new JLabel(name, SwingConstants.CENTER);

		String[][] optionData = new String[2][4];

		for (int i = 0; i < questionCount; i++) {

			
			
			for (int k = 0; k < options[0][i].length; k++) 
				optionData[i][k] = options[0][i][k];

			cboMenu[i] = new JComboBox(optionData[i]);

			final int a = i;

			cboMenu[i].setModel(new DefaultComboBoxModel(options[0][i]));
			cboMenu[i].setRenderer(new ComboBoxRenderer("Select")); // change the default renderer of the ComboBox
			cboMenu[i].setSelectedIndex(0); // set default text as the current index
			//			int selected = cboMenu[i].getSelectedIndex();
			cboMenu[i].addActionListener(new ActionListener() {
				// invoked when the combobox item is selected
				// @Override

				public void actionPerformed(ActionEvent e) {
					//					System.out.println("Action");

					int selectedIndex = cboMenu[a].getSelectedIndex();
					if (selectedIndex >= 0) {
						userAnswer[0][a] = (String)cboMenu[a].getItemAt(selectedIndex);

						displayDialog(userAnswer[state-1][a].equals(answer[state-1][a]), a, state-1);
					}
				}
			});


			chkOptions[i] = new JCheckBox[options[1][i].length];

			btnOptions[i] = new JButton[options[1][i].length];
		}
		for (int k = 0; k < questionCount; k++) {
			final int a = k;
			for (int i = 0; i < options[1][k].length; i++) {
				chkOptions[k][i] = new JCheckBox();
				chkOptions[k][i].setText(options[1][k][i]);
				//chkOptions[1][i] = new JCheckBox(options[1][1][i]);
				componentHolder[1][k].add(chkOptions[k][i]);
				//rdbPanel1.add(chkOptions[1][i]);
				scaleCheckBoxIcon(chkOptions[k][i]); // scale CheckBox to size according to the current viewport

			}
		}
		
		
		for (int k = 0; k < questionCount; k++) {
			final int a = k;
			
			for (int i = 0; i < options[2][k].length; i++) {
				
				btnOptions[k][i] = new JButton(options[2][k][i]);
				componentHolder[2][k].add(Box.createVerticalStrut(15)); // add a vertical spacing between the buttons in the box layout
				componentHolder[2][k].add(btnOptions[k][i]);
				// button clicked event listener which responds when a button is pressed by the user
				
				btnOptions[k][i].addActionListener(new ActionListener() {
					// @Override
					public void actionPerformed(ActionEvent e) {
						userAnswer[state-1][a] = e.getActionCommand();
						displayDialog(userAnswer[state-1][a].equals(answer[state-1][a]), a, state-1);
					}
				});
			}
		}

		for (int i = 0; i < questionCount; i++) {
			final int a = i;

			btnSubmit[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					userAnswer[1][a] = "";
					for (JCheckBox jCheckBox : chkOptions[a]) {
						if (jCheckBox.isSelected())
							userAnswer[state-1][a] = String.join(",", userAnswer[state-1][a], jCheckBox.getText());
					}
					if (userAnswer[state-1][a] != "")
						userAnswer[state-1][a] = userAnswer[state-1][a].substring(1);
					if (!userAnswer[state-1][a].equals(""))
						displayDialog(userAnswer[state-1][a].equals(answer[state-1][a]), a, state-1);
				}
			});

			componentHolder[1][i].add(btnSubmit[i]);
		}

		for (int k = 0; k < questionCount; k++) {
			final int a = k;

			// initialize the TextField and adds an action listener to it that responds when
			// user presses the Enter key
			txtInput[k] = new JTextField();
			txtInput[k].setBorder(new LineBorder(Color.BLACK, 3));
			txtInput[k].addActionListener(new ActionListener() {
				// @Override
				public void actionPerformed(ActionEvent e) {
					userAnswer[state-1][a] = txtInput[a].getText().trim().replaceAll(" +", " ");
					displayDialog(userAnswer[state-1][a].equalsIgnoreCase(answer[state-1][a]), a, state-1);
				}
			});


			componentHolder[3][k].add(txtInput[k]);
			
		}

		assessorPanel.add(lblName);
		btnUnguidedQuiz.addActionListener(this);
		btnGuidedQuiz.addActionListener(this);

		
		
		
		//add lblHints to componentHolder
//		for (int i = 0; i < problemID; i++) {
//			for (int j = 0; j < questionCount; j++) {
//				componentHolder[i][j].add(lblHints);
//			}
//		}

		assessorPanel.addComponentListener(new ComponentAdapter() {
			/**
			 * invoked when the size of the parent frame changes and subsequently the size
			 * of the Assessor panel changes changes the font size of the JLabels, ComboBox,
			 * CheckBoxes, JButtons and JTextField
			 */

			public void componentResized(ComponentEvent e) {
				for (int k = 0; k < questionCount; k++) {
					for (int i = 0; i < optionCount; i++) {
						int width = e.getComponent().getWidth();
						int height = e.getComponent().getHeight();
						lblName.setFont(lblName.getFont().deriveFont((width + height) / 15f));
						for (int q = 0; q < problemID; q++)
							lblQuestion[q].setFont(lblQuestion[q].getFont().deriveFont((width + height) / 40f));

						cboMenu[k].setFont(cboMenu[k].getFont().deriveFont((width + height) / 40f));
						cboMenu[k].setSize(new Dimension(width - 100, cboMenu[k].getHeight()));
						for (JCheckBox jcb : chkOptions[k]) {
							jcb.setFont(jcb.getFont().deriveFont((width + height) / 40f));
							scaleCheckBoxIcon(jcb); // Scale CheckBoxes as per the size of the parent
						}
						for (JButton jb : btnOptions[k])
							jb.setFont(jb.getFont().deriveFont((width + height) / 40f));
						txtInput[k].setFont(txtInput[k].getFont().deriveFont((width + height) / 40f));
						txtInput[k].setPreferredSize(new Dimension(width - 80, 80)); // change the width of the TextField as per
						// the width of the panel
					}
				}
			}

		});

	}
	
	public void actionPerformed(ActionEvent e) {
		isGuided = e.getSource() == btnGuidedQuiz;
		if (e.getSource() == btnGuidedQuiz || e.getSource() == btnUnguidedQuiz) {
			updateComponent(0);  //start at the first question
			Universe.getInstance().startTimer();
			cc.setStatus();
			cc.notifyObservers();
			

			try {
				cThread = new Thread(Universe.getInstance().companionPanel);
				cThread.start();

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
	}

	private void updateComponent(int questionNum) {
		assessorPanel.removeAll();
		// add question to the panel in the border layout
		assessorPanel.add(lblQuestion[questionNum], BorderLayout.NORTH);
		// based on the state, the corresponding panel is added to the Assessor panel
		if (state == 1) {

			cboMenu[questionNum].setSelectedIndex(-1);
			lblQuestion[questionNum].setText(question[0][questionNum]);
			componentHolder[0][questionNum].removeAll();
			componentHolder[0][questionNum].add(cboMenu[questionNum]);
			assessorPanel.add(componentHolder[0][questionNum]);
		} else if (state == 2) {
			for (JCheckBox jcb : chkOptions[questionNum])
				jcb.setSelected(false);
			assessorPanel.add(componentHolder[1][questionNum]);
			lblQuestion[questionNum].setText(question[1][questionNum]);
		} else if (state == 3 || state >= 5) {
			assessorPanel.add(componentHolder[2][questionNum]);
			lblQuestion[questionNum].setText(question[state-1][questionNum]);
			
		} else if (state == 4) {
			txtInput[questionNum].setText("");
			assessorPanel.add(componentHolder[3][questionNum]);
			lblQuestion[questionNum].setText(question[3][questionNum]);
		}
		assessorPanel.repaint();
	}

	private void displayDialog(boolean isCorrect, int problemNum, int categoryNum) {
		// increment the attempts variable and set the correctness
		//		progressReportList[this.state].setAttempted();
		//		progressReportList[this.state].setCorrectness(isCorrect);
		// change the reaction in companion to respond to the new answer
		//Universe.getInstance().bcompanion.changeReaction(); ***PROJECT 4 DESIGN PATTERNS HAVE RENDERED THIS OBSOLETE***

		
		if (isCorrect) {
			cc.setCorrect(categoryNum, problemNum);
			int time = Universe.getInstance().stopTimer();
			cc.setTime(categoryNum, problemNum, time);
			cc.setStatus();
			cc.notifyObservers();
			//notify the observers
//			this.Notify();

			System.out.println("Right. Time: " + time);

			//display a dialog to the user
			JOptionPane.showMessageDialog(assessorPanel, "Correct Answer!", "Message",JOptionPane.INFORMATION_MESSAGE);
			//set _assessorState to "correct"
			//_assessorState = "Correct";
			this.updateComponent(1);

		} else {
			
			numTries[categoryNum][problemNum]++;
			cc.setWrong(categoryNum, problemNum);
			int time = Universe.getInstance().stopTimer();
			cc.setTime(categoryNum, problemNum, time);
			System.out.println(isGuided + "---" + numTries[categoryNum][problemNum]);
			if(isGuided) {
				if (numTries[categoryNum][problemNum] == 1) {
					System.out.println(lblHints == null);
					lblHints.setText("<html>"+ hints[categoryNum][problemNum][0]);
					lblHints.setFont(new Font("Courier New", Font.BOLD, 12));
					componentHolder[categoryNum][problemNum].add(lblHints);

					componentHolder[categoryNum][problemNum].revalidate();
					componentHolder[categoryNum][problemNum].repaint();


				} else if (numTries[categoryNum][problemNum] == 2) {
					String current = lblHints.getText();
					current += "<br>" + hints[categoryNum][problemNum][1] + "</html>";
					lblHints.setText(current);
					lblHints.setFont(new Font("Courier New", Font.BOLD, 12));
					componentHolder[categoryNum][problemNum].revalidate();
					componentHolder[categoryNum][problemNum].repaint();
				}
			} else {
				cc.setStatus();
				cc.notifyObservers();
//				this.Notify();
				this.updateComponent(1);
			}
			
			
			//notify the observers
			cc.setStatus();
			cc.notifyObservers();
//			this.Notify();

			System.out.println("Wrong. Time: " + time);

			JOptionPane.showMessageDialog(assessorPanel, "Wrong Answer!", "Message",JOptionPane.INFORMATION_MESSAGE);
			//_assessorState = "Wrong";
		}

	}

	/**
	 * changeState performs the state operations based on the input parameter state
	 * 
	 * @param state
	 */
	public void changeState(int state) {
		this.state = state;
		// clear the JPanel's components
		assessorPanel.removeAll();
		assessorPanel.setLayout(new GridLayout(2,1));
		// check the state's value for 0
		if (state == 0)
			assessorPanel.add(lblName);
		else {
			assessorPanel.add(btnUnguidedQuiz);
			assessorPanel.add(btnGuidedQuiz);
		}
	}

	/**
	 * Action event method for the ComboBox These perform the necessary operations
	 * when the value in a ComboBox is clicked
	 */
	//	private void cboMenuActionPerformed(ActionEvent evt, int i) {
	//		int selectedIndex = cboMenu[i].getSelectedIndex();
	//		if (selectedIndex >= 0)
	//			JOptionPane.showMessageDialog(assessorPanel, cboMenu[i].getItemAt(selectedIndex));
	//	}

	/**
	 * Due to high screen resolution, the CheckBoxes are so small that it is not
	 * visible properly On top of that, when the application is resized, its size is
	 * not consistent with other elements Therefore, to handle this scenario, we
	 * have included the code to scale the CheckBoxes w.r.t. the Frame This code
	 * snippet has been taken from stackoverflow Reference:
	 * https://stackoverflow.com/questions/4770022/make-jcheckbox-bigger#answer-26995048
	 * 
	 * @param checkbox
	 */
	public static void scaleCheckBoxIcon(JCheckBox checkbox) {
		boolean previousState = checkbox.isSelected();
		checkbox.setSelected(false);
		FontMetrics boxFontMetrics = checkbox.getFontMetrics(checkbox.getFont());
		Icon boxIcon = UIManager.getIcon("CheckBox.icon");
		BufferedImage boxImage = new BufferedImage(boxIcon.getIconWidth(), boxIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = boxImage.createGraphics();
		try {
			boxIcon.paintIcon(checkbox, graphics, 0, 0);
		} finally {
			graphics.dispose();
		}
		ImageIcon newBoxImage = new ImageIcon(boxImage);
		Image finalBoxImage = newBoxImage.getImage().getScaledInstance(boxFontMetrics.getHeight(),
				boxFontMetrics.getHeight(), Image.SCALE_SMOOTH);
		checkbox.setIcon(new ImageIcon(finalBoxImage));

		checkbox.setSelected(true);
		Icon checkedBoxIcon = UIManager.getIcon("CheckBox.icon");
		BufferedImage checkedBoxImage = new BufferedImage(checkedBoxIcon.getIconWidth(), checkedBoxIcon.getIconHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics checkedGraphics = checkedBoxImage.createGraphics();
		try {
			checkedBoxIcon.paintIcon(checkbox, checkedGraphics, 0, 0);
		} finally {
			checkedGraphics.dispose();
		}
		ImageIcon newCheckedBoxImage = new ImageIcon(checkedBoxImage);
		Image finalCheckedBoxImage = newCheckedBoxImage.getImage().getScaledInstance(boxFontMetrics.getHeight(),
				boxFontMetrics.getHeight(), Image.SCALE_SMOOTH);
		checkbox.setSelectedIcon(new ImageIcon(finalCheckedBoxImage));
		checkbox.setSelected(false);
		checkbox.setSelected(previousState);
	}

	/**
	 * Since ComboBox does not allow a default text (or selection of -1 index) by
	 * default, we are creating a custom ListcellRenderer for the ComboBox and
	 * overriding the default one. It allows us to set a default text 'title' for
	 * the ComboBox.
	 * 
	 * @author Amit Ranjan
	 */
	class ComboBoxRenderer extends JLabel implements ListCellRenderer {
		private String title;

		public ComboBoxRenderer(String title) {
			this.title = title;
		}

		// Overrides the default rendering operation for the component with a custom one
		// @Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean hasFocus) {
			if (index == -1 && value == null)
				setText(title);
			else
				setText(value.toString());
			return this;
		}
	}
}