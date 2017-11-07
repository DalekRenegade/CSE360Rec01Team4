/**
 *this is the Companion decorator
 *it implements the companion interface
 *Completion time: 1 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */

package edu.CSE360.rec01.group04;

import java.util.Observable;

import javax.swing.JComponent;

public class CompanionDecorator implements Companion {
	
	protected Companion c;

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		c.update(o, arg);
	}
	
	public void add(Companion c) {
		this.c = c;
	}

	public void updateCompanion(JComponent panel) {
		// TODO Auto-generated method stub
		c.updateCompanion(panel);
	}

}
