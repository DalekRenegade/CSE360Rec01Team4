/**
 *this is the Happy Companion
 * it follows the decorator pattern  of the companion
 *Completion time: 3 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */
package edu.CSE360.rec01.group04;

import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class HappyCompanion extends CompanionDecorator {

	public void updateCompanion(JComponent panel) {
		// TODO Auto-generated method stub
		super.updateCompanion(panel);
		ImageIcon face = new ImageIcon(this.getClass().getResource("/Happy.png"));
		((CompanionPanel)panel).setCompanionImage(face);
	}
}
