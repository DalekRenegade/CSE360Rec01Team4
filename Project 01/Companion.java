/**
 * Companion.java
 * Recitation Project # 01
 * Recitation Group #01 Team #04 
 * @author Tyler Wong
 */
package edu.asu.CSE360._01._04;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Companion extends JPanel
{
	String name = "Tyler";
	private JLabel lbl;
	int state = 0;
	private ImageIcon[] images;
	
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
				if(state > 0 && images[state - 1] != null)
					lbl.setIcon(new ImageIcon(getScaledImage(e.getComponent().getWidth(), e.getComponent().getHeight())));
				else
					lbl.setFont(lbl.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight())/15f));
			}
		});
	}
	//update the panel with name or respective image 
	public void changeState(int state)
	{
		this.state = state;
		lbl.setIcon(null);
		lbl.setText(null);
		
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
	//returns the scaled image, maintaining the original aspect ratio of the image
	private Image getScaledImage(int boundaryWidth, int boundaryHeight) {
		ImageIcon imgIcon = images[state - 1];
		Image scaledImg = null;
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
		return imgIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	}
	
}


