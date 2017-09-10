import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.net.*;

public class Tutor extends JPanel{
	JLabel nameLabel;
	String name = "GARY";
	JEditorPane jep;
	JScrollPane scrollPane;

    public Tutor() {
    	this.setLayout(new BorderLayout());
    	nameLabel = new JLabel(name);
    	jep = new JEditorPane();
        jep.setEditable(false);
        jep.setContentType("text/html");
        scrollPane = new JScrollPane(jep);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(nameLabel);
        this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				nameLabel.setFont(nameLabel.getFont().deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight())/15f));
			}
		});
    }

    public void changeState(int state) {
    	if(state == 0) {
    		this.remove(scrollPane);
    		this.add(nameLabel);
    	}
    	else {
    		this.remove(nameLabel);
    		this.add(scrollPane);
    		String dir = System.getProperty("user.dir");
    		try {
    			File file = new File("resources//P" + state + ".html");
                jep.setPage(file.toURI().toURL());
            } catch (IOException e) {
                jep.setText("<html>Could not load</html>");
                System.err.println(e);
            }
    	}
    }
}

