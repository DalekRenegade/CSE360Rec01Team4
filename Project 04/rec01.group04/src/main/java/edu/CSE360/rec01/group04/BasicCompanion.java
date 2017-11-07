/**
 * Companion - displays companion image to the user based on user's response to the quiz
 * Recitation Project # 03
 * Recitation Group #01 Team #04
 * Completion Time = 12 + 1 + 3 + 4 = 20 hours 
 * @author Tyler Wong [Additional - David Edwards, Amit Ranjan]
 * @version 3.0
 */
package edu.CSE360.rec01.group04;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.security.auth.Subject;
import javax.swing.*;
import javax.swing.border.*;

import java.util.Observable;


public class BasicCompanion implements Companion {
	String name = "Tyler";
	private ImageIcon[] images;
	float zoomLevel = 1.0f;
	float zoomFactor = 0.25f;
	boolean animate = true;
	private String _observerState;
	Assessor assessor;
	ControlCenter c1;
	int[][] status;
	int[] total;

	public BasicCompanion() {
		_observerState = "uninitialized";
	}



	/**
	 * project 4 update:
	 * the companions will change their reaction based on the design patterns
	 */
	public void update(Observable o, Object arg) {
		//_observerState = Universe.getInstance().assessor.getState();
		ControlCenter cc = ControlCenter.getInstance();
		
		_observerState = cc.getObserverState();
		updateCompanion(Universe.companionPanel);
	}




	public void updateCompanion(JComponent panel) {
		// TODO Auto-generated method stub
		ImageIcon face = null;
		if (_observerState == "Excellent")
			face = new ImageIcon(this.getClass().getResource("/Happy.png"));
		else if (_observerState == "Good")
			face = new ImageIcon(this.getClass().getResource("/Happy.png"));
		else if (_observerState == "Bad")
			face = new ImageIcon(this.getClass().getResource("/Worry.png"));
		else if (_observerState == "Help!")
			face = new ImageIcon(this.getClass().getResource("/Sorry.png"));
		else
			face = new ImageIcon(this.getClass().getResource("/Thinking.png"));
		((CompanionPanel)panel).setCompanionImage(face);
	}



	
	
}