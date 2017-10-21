/**
*this is the Assessor class
*it displays the input options for the user
*requirement implemented: Verifies answer and displays 
*appropriate dialog. Also allows user to choose when to 
*start the quiz.
*Completion time: 14 hours
*
*@author David Edwards, Amit Ranjan
*@Version 2.0
*/

package Project04;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.swing.DefaultListModel;
import javax.swing.ComboBoxModel;

public class Assessor extends JPanel {

	DefaultListModel list;
	ComboBoxModel comboList;
	JLabel lblQuestion, lblName;
	JComboBox<String> cboMenu;
	JPanel btnPanel, cboPanel, rdbPanel, txtPanel;
	JTextField txtInput;
	String name = "David";
	String options[][];
	JButton btnStartQuiz;

	String question[], answer[], userAnswer[];
	JCheckBox[] chkOptions;
	JButton[] btnOptions;
	QuestionProgressReport[] progressReportList;

	int state = 0;

	private String currentElement;
	private int optionCount = 0;
	private int questionCount = 0;
	private int count = 0;
	private int answerIndex = 0;

	// default constructor
	public Assessor(QuestionProgressReport[] progressReportList) {
		this.progressReportList = progressReportList;
		list = new DefaultListModel();
		question = new String[10];
		answer = new String[4];
		userAnswer = new String[4];
		options = new String[4][4];
		btnStartQuiz = new JButton("Start Quiz");

		/**
		 * Added 10/5/2017 to implement xml file support modified from original source
		 * to suit project source:
		 * https://www.ntu.edu.sg/home/ehchua/programming/java/J6d_xml.html
		 */
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(this.getClass().getResource("/content.xml").toURI()), new MyHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// set layout and border for the panel
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));

		// initialize JPanels
		txtPanel = new JPanel();
		btnPanel = new JPanel();
		cboPanel = new JPanel();
		rdbPanel = new JPanel();

		// set layouts for the panels
		cboPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		rdbPanel.setLayout(new BoxLayout(rdbPanel, BoxLayout.Y_AXIS));
		btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
		txtPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		// initialize question label
		lblQuestion = new JLabel();
		lblName = new JLabel(name, SwingConstants.CENTER);
		cboMenu = new JComboBox<String>();

		cboMenu.addItem("Select");
		for (int i = 0; i < options[0].length; i++) {
			cboMenu.addItem(options[0][i]);
		}
		cboMenu.setModel(new DefaultComboBoxModel(options[0]));
		cboMenu.setRenderer(new ComboBoxRenderer("Select")); // change the default renderer of the ComboBox
		cboMenu.setSelectedIndex(-1); // set default text as the current index
		cboMenu.addActionListener(new ActionListener() {
			// invoked when the combobox item is selected
			// @Override
			public void actionPerformed(ActionEvent e) {
				int selectedIndex = cboMenu.getSelectedIndex();
				if (selectedIndex >= 0) {
					userAnswer[0] = cboMenu.getItemAt(selectedIndex);
					displayDialog(userAnswer[0].equals(answer[0]));
				}
			}
		});
		cboPanel.add(cboMenu);

		chkOptions = new JCheckBox[options[1].length];
		btnOptions = new JButton[options[2].length];

		for (int i = 0; i < options[1].length; i++) {
			chkOptions[i] = new JCheckBox(options[1][i]);
			rdbPanel.add(chkOptions[i]);
			scaleCheckBoxIcon(chkOptions[i]); // scale CheckBox to size according to the current viewport
			// action listener to the checkbox to respond to user's action
			chkOptions[i].addActionListener(new ActionListener() {
				// @Override
				public void actionPerformed(ActionEvent e) {
					/**
					 * allows for multiple options to be selected by the user. Thus, it iterates
					 * through the CheckBoxes and concatenates the answer(s) selected by the user
					 */
					userAnswer[1] = "";
					for (JCheckBox jCheckBox : chkOptions) {
						if (jCheckBox.isSelected())
							userAnswer[1] = String.join(",", userAnswer[1], jCheckBox.getText());
					}
					if (userAnswer[1] != "")
						userAnswer[1] = userAnswer[1].substring(1);
					if (!userAnswer[1].equals(""))
						displayDialog(userAnswer[1].equals(answer[1]));
				}
			});
		}
		for (int i = 0; i < options[2].length; i++) {
			btnOptions[i] = new JButton(options[2][i]);
			btnPanel.add(Box.createVerticalStrut(15)); // add a vertical spacing between the buttons in the box layout
			btnPanel.add(btnOptions[i]);
			// button clicked event listener which responds when a button is pressed by the
			// user
			btnOptions[i].addActionListener(new ActionListener() {
				// @Override
				public void actionPerformed(ActionEvent e) {
					userAnswer[2] = e.getActionCommand();
					displayDialog(userAnswer[2].equals(answer[2]));
				}
			});
		}

		// initialize the TextField and adds an action listener to it that responds when
		// user presses the Enter key
		txtInput = new JTextField();
		txtInput.setBorder(new LineBorder(Color.BLACK, 3));
		txtInput.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent e) {
				userAnswer[3] = txtInput.getText().trim().replaceAll(" +", " ");
				displayDialog(userAnswer[3].equalsIgnoreCase(answer[3]));
			}
		});
		txtPanel.add(txtInput);
		this.add(lblName);

		btnStartQuiz.addActionListener(new ActionListener() {
			// @Override
			public void actionPerformed(ActionEvent e) {
				updateComponent();
				Universe.getInstance().startTimer();
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			/**
			 * invoked when the size of the parent frame changes and subsequently the size
			 * of the Assessor panel changes changes the font size of the JLabels, ComboBox,
			 * CheckBoxes, JButtons and JTextField
			 */
			public void componentResized(ComponentEvent e) {
				int width = e.getComponent().getWidth();
				int height = e.getComponent().getHeight();
				lblName.setFont(lblName.getFont().deriveFont((width + height) / 15f));
				lblQuestion.setFont(lblQuestion.getFont().deriveFont((width + height) / 40f));
				cboMenu.setFont(cboMenu.getFont().deriveFont((width + height) / 40f));
				cboMenu.setSize(new Dimension(width - 100, cboMenu.getHeight()));
				for (JCheckBox jcb : chkOptions) {
					jcb.setFont(jcb.getFont().deriveFont((width + height) / 40f));
					scaleCheckBoxIcon(jcb); // Scale CheckBoxes as per the size of the parent
				}
				for (JButton jb : btnOptions)
					jb.setFont(jb.getFont().deriveFont((width + height) / 40f));
				txtInput.setFont(txtInput.getFont().deriveFont((width + height) / 40f));
				txtInput.setPreferredSize(new Dimension(width - 80, 80)); // change the width of the TextField as per
				// the width of the panel
			}
		});
	}

	private void updateComponent() {
		this.remove(btnStartQuiz);
		// add question to the panel in the border layout
		this.add(lblQuestion, BorderLayout.NORTH);
		// based on the state, the corresponding panel is added to the Assessor panel
		if (state == 1) {
			this.add(cboPanel);
			cboMenu.setSelectedIndex(-1);
			lblQuestion.setText(question[0]);
		} else if (state == 2) {
			for (JCheckBox jcb : chkOptions)
				jcb.setSelected(false);
			this.add(rdbPanel);
			lblQuestion.setText(question[1]);
		} else if (state == 3) {
			this.add(btnPanel);
			lblQuestion.setText(question[2]);
		} else if (state == 4) {
			txtInput.setText("");
			this.add(txtPanel);
			lblQuestion.setText(question[3]);
		}
		repaint();
	}

	private void displayDialog(boolean isCorrect) {
		// increment the attempts variable and set the correctness
		progressReportList[this.state].setAttempted();
		progressReportList[this.state].setCorrectness(isCorrect);
		// change the reaction in companion to respond to the new answer
		Universe.getInstance().companion.changeReaction();
		if (isCorrect)
			Universe.getInstance().stopTimer();

		// display a message dialog to the user based on the answer's correctness
		JOptionPane.showMessageDialog(this, isCorrect ? "Correct Answer!" : "Wrong Answer!", "Message",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * changeState performs the state operations based on the input parameter state
	 * 
	 * @param state
	 */
	public void changeState(int state) {
		this.state = state;
		// clear the JPanel's components
		this.removeAll();
		// check the state's value for 0
		if (state == 0)
			this.add(lblName);
		else
			this.add(btnStartQuiz);
	}

	/**
	 * Action event method for the ComboBox These perform the necessary operations
	 * when the value in a ComboBox is clicked
	 */
	private void cboMenuActionPerformed(ActionEvent evt) {
		int selectedIndex = cboMenu.getSelectedIndex();
		if (selectedIndex >= 0)
			JOptionPane.showMessageDialog(this, cboMenu.getItemAt(selectedIndex));
	}

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
	 * Added 10/5/2017 to implement xml into project source modified to work in this
	 * project Inner class for the Callback Handlers.
	 */
	class MyHandler extends DefaultHandler {
		// Callback to handle element start tag
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			currentElement = qName;
			if (currentElement.equals("Question")) {
				String isbn = attributes.getValue("id");
				String type = attributes.getValue("Category");
			}
		}

		// Callback to handle element end tag
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			currentElement = "";
		}

		// Callback to handle the character text data inside an element
		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			if (currentElement.equals("Option")) {
				String opt = new String(chars, start, length);
				options[count][optionCount] = opt;

				optionCount++;
			} else if (currentElement.equals("QText")) {
				String ques = new String(chars, start, length);
				question[questionCount] = ques;
				questionCount++;
			} else if (currentElement.equals("Answer")) {
				String ans = new String(chars, start, length);
				answer[answerIndex] = ans;
				answerIndex++;
				optionCount = 0;
				count++;
			}
		}
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