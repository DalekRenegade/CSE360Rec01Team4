/**
 *this is the Companion interface
 *it extends the observer pattern
 *Completion time: 1 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */

package edu.CSE360.rec01.group04;
import javax.swing.JComponent;
import java.util.Observer;

public interface Companion extends Observer {
	
	public void updateCompanion(JComponent panel);

}
