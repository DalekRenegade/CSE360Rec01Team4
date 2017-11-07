/**
 *this is the Guided Quiz
 * it follows the decorator pattern as it extends Unguided quiz class and implements the quiz Quiz interface 
 * it provides hints to the user if a question is answered incorrectly
 *Completion time: 3 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */
package edu.CSE360.rec01.group04;

public class GuidedQuiz extends UnguidedQuiz implements Quiz {
	
	String hint[][][];
	
	public void addHints(String[][][] hints) {
		this.hint = hints;
	}
	
	public String[][][] getHints() {
		return this.hint;
	}
	
	public void setQuiz(QuizReader qr) {
		
		
		options = qr.options;
		question = qr.question;
		answer = qr.answer;	
		addHints(qr.hint);
	}

}
