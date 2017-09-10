package companion;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Companion extends JPanel
{
	private JFrame frame1;
	
	private JPanel panel1;
	private JPanel panel2;
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	private JLabel label5;
	private JLabel label6;
	private JLabel label7;
	
	private ImageIcon image1;
	private ImageIcon image2;
	private ImageIcon image3;
	private ImageIcon image4;
	private ImageIcon image5;
	private ImageIcon image6;
	private ImageIcon image7;
	
	private JSlider jslider1;
	
	public Companion() 
	{		
		frame1 = new JFrame("Michael Bubble Appreciation Day or Month or Year or Eon");
		frame1.setSize(1500, 700);
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2,2));
		
		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		
		jslider1 = new JSlider(JSlider.HORIZONTAL, 0, 3, 0);
	    jslider1.setMajorTickSpacing(1);
		jslider1.setPaintTicks(true);
		jslider1.setPaintLabels(true);
		
		jslider1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
		        if (jslider1.getValue() == 1)
		        {
		        	panel1.remove(label1);
		        	panel1.remove(label2);
		        	panel1.remove(label3);
		        	panel1.remove(label4);
		        	panel1.remove(label5);
		        	panel1.remove(label6);
		        	panel1.remove(label7);

		        	panel1.add(label5); 
		        	panel1.add(label2);
		        	panel1.add(label3);
		        	panel1.add(label4);
		        }
		        
		        if (jslider1.getValue() == 2)
		        {
		        	panel1.remove(label1);
		        	panel1.remove(label2);
		        	frame1.revalidate();
		        	panel1.remove(label3);
		        	panel1.remove(label4);
		        	panel1.remove(label5);
		        	panel1.remove(label6);
		        	panel1.remove(label7);

		        	panel1.add(label6);
		        	panel1.add(label2); 
		        	panel1.add(label3);
		        	panel1.add(label4);
		        }
		        
		        if (jslider1.getValue() == 3)
		        {
		        	panel1.remove(label1);
		        	panel1.remove(label2);
		        	panel1.remove(label3);
		        	panel1.remove(label4);
		        	panel1.remove(label5);
		        	panel1.remove(label6);
		        	panel1.remove(label7);

		        	panel1.add(label7);
		        	panel1.add(label2);
		        	panel1.add(label3); 
		        	panel1.add(label4);
		        }

		        else if(jslider1.getValue() == 0)
		        {
		        	panel1.remove(label1);
			        frame1.revalidate();
		        	panel1.remove(label2);
		        	panel1.remove(label3);
		        	panel1.remove(label4);
		        	panel1.remove(label5);
		        	panel1.remove(label6);
		        	panel1.remove(label7);
		        	
		        	panel1.add(label1); 
		        	panel1.add(label2);
		        	panel1.add(label3);
		        	panel1.add(label4);
		        }
		        frame1.revalidate();
				//System.out.println(jslider1.getValue());
			}
		});

		
		image1 = new ImageIcon(getClass().getResource("thunderbuddy.jpg"));
		label1 = new JLabel();
        label1.setIcon(image1);
		
		image2 = new ImageIcon(getClass().getResource("thunderbuddy.jpg")); 
		label2 = new JLabel();
	    label2.setIcon(image2);
		
		image3 = new ImageIcon(getClass().getResource("thunderbuddy.jpg")); 
		label3 = new JLabel();
	    label3.setIcon(image3);
		
		image4 = new ImageIcon(getClass().getResource("thunderbuddy.jpg")); 
		label4 = new JLabel();
	    label4.setIcon(image4);
		
		image5 = new ImageIcon(getClass().getResource("DEUUEAUGH.jpg"));
		label5 = new JLabel();
        label5.setIcon(image5);

        image6 = new ImageIcon(getClass().getResource("OhBoy.jpg"));
        label6 = new JLabel();
        label6.setIcon(image6);
        
        image7 = new ImageIcon(getClass().getResource("RockHyrax.jpg"));
        label7 = new JLabel();
        label7.setIcon(image7);
        
        panel1.add(label1); 
        panel1.add(label2);
        panel1.add(label3);
        panel1.add(label4);
		panel2.add(jslider1);
		
		frame1.add(panel1);
		frame1.add(panel2, BorderLayout.SOUTH);
		
		frame1.pack();
		frame1.setVisible(true);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}


