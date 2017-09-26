/**
 * Companion.java
 * Recitation Project # 01
 * Recitation Group #01 Team #04 
 * @author Tyler Wong
 */
package edu.asu.CSE360._01._04;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

public class Companion extends JPanel implements Runnable
{
	String name = "Tyler";
	private JLabel lbl;
	int state = 0;
	private ImageIcon[] images;
	float zoomLevel = 1.0f;
	float zoomFactor = 0.25f;
	boolean animate = true;
	
	public Companion() 
	{
		//sets layout and border for the panel
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));
		lbl = new JLabel(name, SwingConstants.CENTER);
		images = new ImageIcon[4];

		//Load images from local files into memory
		try {
			images[0] = new ImageIcon("resources//Happy.png");
			images[1] = new ImageIcon("resources//Thinking.png");
			images[2] = new ImageIcon("resources//Worry.png");
			images[3] = new ImageIcon("resources//Sorry.png");
		} catch (Exception e) {
			System.out.println(e);
		}
		this.add(lbl);	//add the label (with name) to the panel
		this.addComponentListener(new ComponentAdapter() {
			/**listener for when the frame is resized and subsequently the panel is resized
			*  resizes the corresponding image/text based on the state of the slider
			*/
			public void componentResized(ComponentEvent e) {
				lbl.setFont(lbl.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight())/15f));
				if(state > 0 && images[state - 1] != null)
					lbl.setIcon(new ImageIcon(getScaledImage(e.getComponent().getWidth(), e.getComponent().getHeight())));
			}
		});
	}
	
	/**			Project 02 Update:
	 * --------------------------------------------
	 * Runnable interface implementation - threading
	 * Function creates an animation which by redrawing the companion image as if the 
	 * image is zoomed-in and zoomed-out continuously.
	 * The image scalability w.r.t. the panel is preserved as the image zooms in and out itself
	 */
	@Override
	public void run() {
		animate = true;
		try {
			while(animate) {
				zoomLevel += zoomFactor;
				if(zoomLevel >= 2.0f || zoomLevel <= 1.0f)
					zoomFactor *= -1;
				lbl.setIcon(new ImageIcon(getScaledImage(this.getWidth(), this.getHeight())));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			animate = false;
		}
	}
	
	//flag which marks the stop of the animation
	public void stopAnimation() {
		this.animate = false;
	}
	
	//update the panel with name or respective image
	public void changeState(int state) {
		this.state = state;
		lbl.setIcon(null);
		lbl.setText(null);
		zoomLevel = 1.0f;			//zoom level reset
		zoomFactor = 0.25f;			//zoom factor reset
		if(state > 0) {
			ImageIcon imgIcon = new ImageIcon(getScaledImage(this.getWidth(), this.getHeight()));
			if(imgIcon != null && imgIcon.getIconWidth() > 0)
				lbl.setIcon(imgIcon);
			else
				lbl.setText("Unavailable...");
		}
		else
			lbl.setText(name);
		this.revalidate();
	}
	
	/**			Project 02 Update:
	 * --------------------------------------------
	 * Function modified to accommodate for the zoom-in and zoom-out based on the Zoom Level
	 * Removed the use of Image.getScaledInstance() function because of frequent update to the 
	 * JLabel, lbl, which displays the image. 
	 * Creating a new image from an empty buffered image increases the performance and 
	 * has a noticeable impact on the application's user experience
	 */
	//returns the scaled image, maintaining the original aspect ratio of the image
	private Image getScaledImage(int boundaryWidth, int boundaryHeight) {
		//Issue on a Mac system - zoom level goes beyond the threshold
		if(zoomLevel < 1.0f || zoomLevel > 2.0f) {
			zoomLevel = 1.0f;
			zoomFactor = 0.25f;
		}
		ImageIcon imgIcon = images[state - 1];
		int newWidth = imgIcon.getIconWidth();
		int newHeight = imgIcon.getIconHeight();
		int originalWidth = imgIcon.getIconWidth();
		int originalHeight = imgIcon.getIconHeight();
		if(originalWidth > boundaryWidth) {
			newWidth = boundaryWidth;
			newHeight = (newWidth * originalHeight) / originalWidth;
		}
		if(newHeight > boundaryHeight) {
			newHeight = boundaryHeight;
			newWidth = (newHeight * originalWidth) / originalHeight; 
		}
		
		newWidth = (int)(newWidth * zoomLevel);
		newHeight = (int)(newHeight * zoomLevel);
		BufferedImage resizedImage = new BufferedImage(newWidth , newHeight, Image.SCALE_SMOOTH);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(imgIcon.getImage(), 0, 0, newWidth , newHeight , null);
		g.dispose();
		return resizedImage;
	}
	
	
}


