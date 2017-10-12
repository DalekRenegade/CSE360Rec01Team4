/**
 * Parent class for Tutor, Companion, and Assessor
 * Invokes state change and UI update based on slider position and user's actions
 * Keeps track of the time taken to answer each question by the user
 * Recitation Project # 03
 * Recitation Group #01 Team #04
 * Completion Time = 11 + 1 + 4 = 17 hours 
 * @author Amit Ranjan
 * @version 1.3
 */

package Project03;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicSliderUI;

public class Universe extends JFrame implements ChangeListener {

	private static Universe universeInstance;

	JSlider js;
	JPanel centralPanel, sliderPanel, fourthPanel;
	JLabel fourthLabel;
	Companion companion;
	Tutor tutor;
	Assessor assessor;
	Thread companionThread;
	String authorName = "Amit";
	QuestionProgressReport[] progressReportList;
	int sliderValue;
	int elapsedTime = 0;
	Timer currentTimer;

	public Universe() {
		// Set layout, default actions and background
		super("Interactive Tutor");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);

		this.sliderValue = 0;
		progressReportList = new QuestionProgressReport[5];
		progressReportList[0] = new QuestionProgressReport();
		progressReportList[1] = new QuestionProgressReport(1, 20, 30, 40, 50);
		progressReportList[2] = new QuestionProgressReport(2, 20, 30, 40, 50);
		progressReportList[3] = new QuestionProgressReport(3, 20, 30, 40, 50);
		progressReportList[4] = new QuestionProgressReport(4, 20, 30, 40, 50);

		// Initialize fourth panel with its layout, border
		fourthPanel = new JPanel();
		fourthPanel.setLayout(new BorderLayout());
		fourthPanel.setBackground(Color.WHITE);
		fourthPanel.setBorder(
				BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));
		fourthLabel = new JLabel(authorName, SwingConstants.CENTER);
		fourthPanel.add(fourthLabel);

		// Central panel which contains the sub-panels in a grid layout
		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(2, 2));
		centralPanel.setBackground(Color.WHITE);

		initializeSlider(); // Initialize the slider
		this.add(centralPanel, BorderLayout.CENTER); // Add the central panel to the center of the frame
		this.add(sliderPanel, BorderLayout.SOUTH); // Add the slider panel to the bottom of the frame

		// Initialize the companion, tutor and assessor panels. Add these and the 4th
		// panel to the central panel
		companion = new Companion(progressReportList);
		tutor = new Tutor();
		assessor = new Assessor(progressReportList);
		centralPanel.add(companion);
		centralPanel.add(tutor);
		centralPanel.add(assessor);
		centralPanel.add(fourthPanel);

		// Add an event listener which responds to the JFrame being resized
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				setJsliderSize(); // Change the size of the slider and its components as per the size of the frame
				// Change the size of the font of the 4th label as the frame is resized
				fourthLabel.setFont(
						fourthLabel.getFont().deriveFont((fourthPanel.getWidth() + fourthPanel.getHeight()) / 15f));
			}
		});
	}

	private void initializeSlider() {

		sliderPanel = new JPanel();

		// Initialize default slider and repaint it to obtain the custom slider
		js = new JSlider(JSlider.HORIZONTAL, 0, 4, 0);
		js.setBackground(Color.WHITE);
		js.setMajorTickSpacing(1);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setSnapToTicks(true);
		setJsliderSize();
		/**
		 * Slider (parent - BasicSliderUI) Clicking on the horizontal event Due to
		 * inconsistency in code between Mac and Windows regarding MetalSliderUI class,
		 * we switched to BasicSliderUI
		 */
		js.setUI(new BasicSliderUI(js) {
			/**
			 * Clicking anywhere on the slider horizontal bar will invoke this function Sets
			 * the slider thumb to always move to the nearest tick
			 */
			protected void scrollDueToClickInTrack(int direction) throws NullPointerException { // added 10/11/2017 by
																								// David Edwards due to
																								// an occasional
																								// exception when
																								// clicking on the
																								// slider
				int value = slider.getValue();
				if (slider.getOrientation() == JSlider.HORIZONTAL) {
					value = this.valueForXPosition(slider.getMousePosition().x);
				} else if (slider.getOrientation() == JSlider.VERTICAL) {
					value = this.valueForYPosition(slider.getMousePosition().y);
				}
				slider.setValue(value);
			}

			/**
			 * Paints the slider and the thumb to resize them as per the size of the frame
			 * Custom slider is painted because of the size and view problems arising due to
			 * high screen resolution
			 */
			@Override
			public void paintTrack(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				Rectangle r = trackRect;
				r.height = 20;
				g2d.setPaint(new Color(192, 192, 192));
				g2d.fill(r);
			}

			@Override
			public void paintThumb(Graphics g) {
				Graphics2D g2d = (Graphics2D) g;
				int n = 4;
				int[] x = new int[n];
				int[] y = new int[n];

				int rectX = thumbRect.x;
				int rectY = thumbRect.y;

				x[0] = rectX - 5; // Left bottom
				x[1] = rectX - 5; // Left top
				x[2] = rectX + 20; // Right top
				x[3] = rectX + 20; // Right bottom

				y[0] = rectY - 10; // Left bottom
				y[1] = rectY + 20; // Left top
				y[2] = rectY + 20; // Right bottom
				y[3] = rectY - 10; // Right top

				Polygon poly = new Polygon(x, y, n);
				g2d.setPaint(Color.GREEN);
				g2d.fill(poly);
			}
		});
		sliderPanel.add(js); // Adding slider to the frame
		js.addChangeListener(this); // Adding slider change listener
	}

	// Changes the width of the slider horizontal bar when the size of the Frame
	// changes
	private void setJsliderSize() {
		Dimension dim;
		if (this.isVisible())
			dim = new Dimension(this.getWidth() - 200, 200);
		else
			dim = new Dimension(500, 200);
		if (js != null)
			js.setPreferredSize(dim);
		js.revalidate();
	}

	/**
	 * Project 02 Update: -------------------------------------------- Added
	 * threading to animate the companion image Starts the animation thread based on
	 * the current state of the slider
	 */
	// Invoked when the slider position changes - includes changing the state of
	// panels as per slider
	// @Override
	public void stateChanged(ChangeEvent e) {
		companion.stopAnimation();
		companionThread = null;

		stopTimer();

		sliderValue = js.getValue();
		companion.changeState(sliderValue);
		tutor.changeState(sliderValue);
		assessor.changeState(sliderValue);
		fourthPanel.setVisible(sliderValue == 0 ? true : false);
		this.revalidate();
		this.repaint();
		if (sliderValue != 0)
			try {
				companionThread = new Thread(companion);
				companionThread.start();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	}

	// A Timer function that handles records users
	Timer startTimer() {
		elapsedTime = 0;
		Timer timer = new Timer("Timer" + sliderValue);
		timer.schedule(new TimerTask() {
			public void run() {
				elapsedTime++;
			}
		}, 0, 1000); // Every second
		return timer;
	}

	public void stopTimer() {
		if (currentTimer != null) {
			currentTimer.cancel();
			currentTimer.purge();
			progressReportList[sliderValue].increaseTimer(elapsedTime);
		}
		currentTimer = null;
		elapsedTime = 0;
	}

	public static void setUIDefaults(FontUIResource f, Color defaultColor) {
		// Enumerate all the components from the UI manager and perform common operation
		// on those keys
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			String valueString = "" + value;
			// Change the default font for all the components
			if (value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
				Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
			// Change the default background color of all the Panels, ComboBoxes, CheckBoxes
			// and the border of BasicSliderUI
			else if ((value instanceof ColorUIResource)
					&& ((key.toString() == "Panel.background") || (key.toString() == "Combobox.background")
							|| (key.toString() == "CheckBox.background") || (key.toString() == "Slider.focus"))) {
				UIManager.put(key, new ColorUIResource(defaultColor));
			}
			// For Mac
			else if ((valueString.contains("AquaImageFactory"))
					&& ((key.toString() == "Panel.background") || (key.toString() == "Combobox.background")
							|| (key.toString() == "CheckBox.background") || (key.toString() == "Slider.focus"))) {
				UIManager.put(key, new ColorUIResource(defaultColor));
			}

		}
	}

	public static Universe getInstance() {
		if (universeInstance == null)
			universeInstance = new Universe();
		return universeInstance;
	}

	public static void main(String[] args) {
		// Change the default UI layout of the components
		setUIDefaults(new FontUIResource(new Font("Serif", Font.BOLD, 30)), Color.WHITE);
		// Initialize Universe object and display the window
		Universe u = Universe.getInstance();
		u.setMinimumSize(new Dimension(1000, 1000));
		u.setSize(1000, 1000);
		u.pack();
		u.setVisible(true);
	}

}