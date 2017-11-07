/**
 * Companion - displays companion image to the user based on user's response to the quiz
 * Recitation Project # 03
 * Recitation Group #01 Team #04
 * Completion Time = 12 + 1 + 3 + 4 = 20 hours 
 * @author Tyler Wong [Additional - David Edwards, Amit Ranjan]
 * @version 3.0
 */

package edu.CSE360.rec01.group04;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class CompanionPanel extends JPanel implements Runnable {

	String name = "Tyler";
	private JLabel lbl;
	boolean reaction = false;
	float zoomLevel = 1.0f;
	float zoomFactor = 0.25f;
	boolean animate = true;
	String _observerState = "uninitialized";
	ImageIcon imgIcon = null;
	
	public Companion currentCompanion;

	
	public void setCompanion(Companion x) {
		
		this.stopAnimation();
		currentCompanion = x;
	}
	
	public void displayCompanion() {
		
		currentCompanion.updateCompanion(this);
		
		this.startAnimation();
	}
	
	public CompanionPanel() {
		lbl = new JLabel(name, SwingConstants.CENTER);

		// sets layout and border for the panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));


		this.add(lbl); // add the label (with name) to the panel
		this.addComponentListener(new ComponentAdapter() {
			/**
			 * listener for when the frame is resized and subsequently the panel is resized
			 * resizes the corresponding image/text based on the state of the slider
			 */
			public void componentResized(ComponentEvent e) {
				int state = ControlCenter.getInstance().getSliderState();
				lbl.setFont(
						lbl.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight()) / 15f));
				if (state > 0 && imgIcon != null)
					lbl.setIcon(new ImageIcon(getScaledImage(e.getComponent().getWidth(), e.getComponent().getHeight())));
			}
		});
	}

	// flag which marks the stop of the animation
	public void stopAnimation() {
		this.animate = false;
	}

	public void startAnimation() {
		this.animate = true;
	}
	
	public void run() {
		//		animate = true;
		System.out.println(animate);
		try {
			while (animate) {
				zoomLevel += zoomFactor;
				if (zoomLevel >= 2.0f || zoomLevel <= 1.0f)
					zoomFactor *= -1;
				lbl.setIcon(new ImageIcon(getScaledImage(this.getWidth(), this.getHeight())));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			animate = false;
		}
	}
	
	/**
	 * Project 02 Update: -------------------------------------------- Function
	 * modified to accommodate for the zoom-in and zoom-out based on the Zoom Level
	 * Removed the use of Image.getScaledInstance() function because of frequent
	 * update to the JLabel, lbl, which displays the image. Creating a new image
	 * from an empty buffered image increases the performance and has a noticeable
	 * impact on the application's user experience
	 */
	// returns the scaled image, maintaining the original aspect ratio of the image
	private Image getScaledImage(int boundaryWidth, int boundaryHeight) {
		// Issue on a Mac system - zoom level goes beyond the threshold
		if (zoomLevel < 1.0f || zoomLevel > 2.0f) {
			zoomLevel = 1.0f;
			zoomFactor = 0.25f;
		}

		int newWidth = imgIcon.getIconWidth();
		int newHeight = imgIcon.getIconHeight();
		int originalWidth = imgIcon.getIconWidth();
		int originalHeight = imgIcon.getIconHeight();
		if (originalWidth > boundaryWidth) {
			newWidth = boundaryWidth;
			newHeight = (newWidth * originalHeight) / originalWidth;
		}
		if (newHeight > boundaryHeight) {
			newHeight = boundaryHeight;
			newWidth = (newHeight * originalWidth) / originalHeight;
		}

		newWidth = (int) (newWidth * zoomLevel);
		newHeight = (int) (newHeight * zoomLevel);
		BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, Image.SCALE_SMOOTH);

		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(imgIcon.getImage(), 0, 0, newWidth, newHeight, null);
		g.dispose();
		return resizedImage;
	}
	
	public void setCompanionImage(ImageIcon icon) {
		this.imgIcon = icon;
		lbl.setIcon(null);
		lbl.setText(null);
		zoomLevel = 1.0f; // zoom level reset
		zoomFactor = 0.25f; // zoom factor reset
		if (ControlCenter.getInstance().getSliderState() > 0) {
			ImageIcon imgIcon = new ImageIcon(getScaledImage(this.getWidth(), this.getHeight()));
			if (imgIcon != null && imgIcon.getIconWidth() > 0)
				lbl.setIcon(imgIcon);
			else
				lbl.setText("Companion Unavailable...");
		} else
			lbl.setText(name);
		this.revalidate();
	}


}
