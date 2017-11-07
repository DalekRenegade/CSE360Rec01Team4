/**
 *this is the UnGuided Quiz
 * it follows the decorator pattern as it extends Unguided quiz class and implements the quiz Quiz interface 
 * it does not provide hints to the user if a question is answered incorrectly
 *Completion time: 3 hours
 *
 *@author David Edwards, Amit Ranjan
 *@Version 3.0
 */

package edu.CSE360.rec01.group04;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UnguidedQuiz implements Quiz {
	
	String currentElement;
	int problemID;
	int questionCount;
	int optionCount;
	//int category;
	String options[][][];
	String question[][];
	String answer[][];
	String hint[][][];
	int answerIndex;
	int count;
	int hintNum;
//	final int PROBLEMS ;
	final int QCOUNT = 2;
	final int OPTCOUNT = 4;
	final int HINTS = 2;
	int totalCount = ControlCenter.getInstance().getSliderCount();
	

	public void setQuiz(QuizReader qr) {
		
		
		
		options = qr.options;
		question = qr.question;
		answer = qr.answer;		
	}
	
	/**
	 * getOptions returns the options from the xml file
	 * as a three dimensional array
	 * @return
	 */
	public String[][][] getOptions() {
		return options;
	}
	
	/**
	 * getQuestion returns the questions from the xml file
	 * as a two dimensional array
	 * @return
	 */
	public String[][] getQuestion() {
		return question;
	}
	
	/**
	 * getAnswer returns the answers from the xml file
	 * as a two dimensional array
	 * @return
	 */
	public String[][] getAnswer() {
		return answer;
	}
	
	
	
	/**
	 * getCounts returns an array of the important constants in the program
	 * used to keep hard-coded values to a minimum
	 * @return
	 */
	public int[] getCounts() {
		
		int[] counts = new int[4];
		
		counts[0] = ControlCenter.getInstance().getSliderCount();
		counts[1] = QCOUNT;
		counts[2] = OPTCOUNT;
		counts[3] = HINTS;
		
		return counts;
	}
}
