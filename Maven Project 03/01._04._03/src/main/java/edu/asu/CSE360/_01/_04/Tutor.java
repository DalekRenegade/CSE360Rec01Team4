/**
 * Displays HTML files
 * Assignment number: Recitation Project 3
 * Completion time: 11
 *
 * @author Gary Chen
 * @version latest
 */

package edu.asu.CSE360._01._04;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Tutor extends JPanel {
	JLabel nameLabel;
	String name = "Gary";
	JEditorPane jep;
	JScrollPane scrollPane;

	public Tutor() {
		// sets layout and border for the panel
		this.setLayout(new BorderLayout());
		this.setBorder(
				BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.BLACK, 3)));
		nameLabel = new JLabel(name, SwingConstants.CENTER);
		jep = new JEditorPane();
		jep.setEditable(false);
		jep.setContentType("text/html");

		scrollPane = new JScrollPane(jep);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(nameLabel);
		this.addComponentListener(new ComponentAdapter() { // Adjust size of text depending on JFrame size
			public void componentResized(ComponentEvent e) {
				nameLabel.setFont(nameLabel.getFont()
						.deriveFont((e.getComponent().getWidth() + e.getComponent().getHeight()) / 15f));
			}
		});
	}

	public void changeState(int state) {
		if (state == 0) {
			this.remove(scrollPane); // Removes scrollPane
			this.add(nameLabel); // Adds name to be displayed
		} else {
			this.remove(nameLabel); // Removes name
			this.add(scrollPane); // Adds scrollPane to be displayed
			String dir = System.getProperty("user.dir");
			try {
				jep.setPage(this.getClass().getResource("/P" + state + ".html"));
			} catch (IOException e) { // When file is not found or failed to load
				jep.setText("<html>Could not load</html>");
				System.err.println(e);
			}
		}
	}

	static String readFile(String path) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}

}
