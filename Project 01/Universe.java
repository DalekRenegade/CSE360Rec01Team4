import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.MetalSliderUI;

public class Universe extends JFrame implements ChangeListener {
	
	JSlider js;
	JPanel centralPanel, sliderPanel, fourthPanel;
	JLabel fourthLabel;
	Companion companion;
	Tutor tutor;
	Assessor assessor;
	String authorName = "XYZ";
	
	public Universe() {
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);
		
		fourthPanel = new JPanel();
		fourthPanel.setLayout(new BorderLayout());
		fourthPanel.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.YELLOW, 5), new EmptyBorder(20, 20, 20, 20)));
		fourthLabel = new JLabel(authorName, SwingConstants.CENTER);
		fourthPanel.add(fourthLabel);
		
		centralPanel = new JPanel();
		centralPanel.setLayout(new GridLayout(2, 2));
		centralPanel.setBackground(Color.WHITE);
		
		initializeSlider();
		this.add(centralPanel, BorderLayout.CENTER);
		this.add(sliderPanel, BorderLayout.SOUTH);
		
		companion = new Companion();
		tutor = new Tutor();
		assessor = new Assessor(this);
		centralPanel.add(companion);
		centralPanel.add(tutor);
		centralPanel.add(assessor);
		centralPanel.add(fourthPanel);
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				setJsliderSize();
				fourthLabel.setFont(fourthLabel.getFont().deriveFont((fourthPanel.getWidth() + fourthPanel.getHeight())/15f));
			}
		});
	}
	
	private void initializeSlider() {
		
		sliderPanel = new JPanel();
		sliderPanel.setBackground(Color.WHITE);
		
		js = new JSlider(JSlider.HORIZONTAL, 0, 4, 0);
		js.setBackground(Color.WHITE);
		js.setMajorTickSpacing(1);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setSnapToTicks(true);
		setJsliderSize();
		js.setUI(new MetalSliderUI() {
		    protected void scrollDueToClickInTrack(int direction) {
		        int value = slider.getValue(); 
		        if (slider.getOrientation() == JSlider.HORIZONTAL) {
		            value = this.valueForXPosition(slider.getMousePosition().x);
		        } else if (slider.getOrientation() == JSlider.VERTICAL) {
		            value = this.valueForYPosition(slider.getMousePosition().y);
		        }
		        slider.setValue(value);
		    }
		    
		    @Override
		    public void paintTrack(Graphics g) {
		        Graphics2D g2d = (Graphics2D) g;
		        Rectangle r = trackRect;
		        r.height = 20;
		        g2d.setPaint(new Color(192,192,192));
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
		        
		        x[0] = rectX - 5;	//Left bottom
		        x[1] = rectX - 5;	//Left top
		        x[2] = rectX + 20;	//Right top
		        x[3] = rectX + 20;	//Right bottom
		        
		        y[0] = rectY - 10;	//Left bottom
		        y[1] = rectY + 20;	//Left top
		        y[2] = rectY + 20;	//Right bottom
		        y[3] = rectY - 10;	//Right top
		        
		        Polygon poly = new Polygon(x, y, n);
		        g2d.setPaint(Color.GREEN);
		        g2d.fill(poly);
		    }
		});
		sliderPanel.add(js);
		js.addChangeListener(this);
	}
	
	private void setJsliderSize() {
		Dimension dim;
		if(this.isVisible())
			dim = new Dimension(this.getWidth() - 200, 200);
		else
			dim = new Dimension(500, 200);
		if(js!=null)
			js.setPreferredSize(dim);
		js.revalidate();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		int value = js.getValue();
		companion.setImage(value);
		tutor.setContent(value);
		assessor.setQuestionType(value);
		fourthLabel.setVisible(value == 0 ? true : false);
		this.revalidate();
		this.repaint();
	}
	
	public static void setUIFont(FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
	
	public static void main(String[] args) {
		setUIFont(new FontUIResource(new Font("Serif", Font.BOLD, 30)));
		Universe u = new Universe();
		u.setMinimumSize(new Dimension(1000, 1000));
		u.setSize(1000, 1000);
		u.pack();
		u.setVisible(true);
	}

}
