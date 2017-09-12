import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Companion extends JPanel
{
	String name = "Tyler";
	private JLabel lbl;
	
	private ImageIcon[] images;
	int state;
	
	public Companion() 
	{		
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));
		lbl = new JLabel(name, SwingConstants.CENTER);
		state = 0;
		images = new ImageIcon[4];

		try {
			images[0] = new ImageIcon("resources//Happy.png");
			images[1] = new ImageIcon("resources//Thinking.png");
			images[2] = new ImageIcon("resources//Worry.png");
			images[3] = new ImageIcon("resources//Sorry.png");
		} catch (Exception e) {
			System.out.println(e);
		}
		this.add(lbl);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				lbl.setFont(lbl.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight())/15f));
			}
		});
	}
	
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


