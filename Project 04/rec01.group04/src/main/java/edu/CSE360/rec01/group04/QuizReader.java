/**
 *this is the Quiz Reader
 * it reads the quiz questions from the XML file
 *Completion time: 5 hours
 *
 *@author David Edwards
 *@Version 3.0
 */

package edu.CSE360.rec01.group04;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class QuizReader {
	
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
	final int PROBLEMS = 8;
	final int QCOUNT = 2;
	final int OCOUNT = 4;
	final int HINTS = 2;

	public QuizReader() {
		
		options = new String[PROBLEMS][QCOUNT][OCOUNT];
		question = new String[PROBLEMS][QCOUNT];
		answer = new String[PROBLEMS][QCOUNT];
		hint = new String[PROBLEMS][QCOUNT][HINTS];
		
		/**
		 * Added 10/5/2017 to implement xml file support modified from original source
		 * to suit project source:
		 * https://www.ntu.edu.sg/home/ehchua/programming/java/J6d_xml.html
		 */
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(this.getClass().getResource("/content.xml").toURI()), new MyHandler());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
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
	 * getHints returns the hints from the xml file
	 * as a three dimensional array
	 * @return
	 */
	public String[][][] getHints() {
		return hint;
	}
	
	/**
	 * getCounts returns an array of the important constants in the program
	 * used to keep hard-coded values to a minimum
	 * @return
	 */
	public int[] getCounts() {
		
		int[] counts = new int[4];
		
		counts[0] = PROBLEMS;
		counts[1] = QCOUNT;
		counts[2] = OCOUNT;
		counts[3] = HINTS;
		
		return counts;
	}
	
	
	/**
	 * Added 10/5/2017 to implement xml into project source modified to work in this
	 * project Inner class for the Callback Handlers.
	 */
	class MyHandler extends DefaultHandler {
		// Callback to handle element start tag
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes)
				throws SAXException {
			currentElement = qName;
			if (currentElement.equals("Question")) {
				problemID = Integer.parseInt(attributes.getValue("id")) - 1;
				String type = attributes.getValue("Category");
				//category++;
				answerIndex = 0;
				questionCount = 0;
//				hintNum = 0;
			}
		}

		// Callback to handle element end tag
		@Override
		public void endElement(String uri, String localName, String qName) throws SAXException {
			currentElement = "";
		}

		// Callback to handle the character text data inside an element
		@Override
		public void characters(char[] chars, int start, int length) throws SAXException {
			if (currentElement.equals("Option")) {
				String opt = new String(chars, start, length);
				options[problemID][questionCount][optionCount] = opt;
//				System.out.println("option ProblemID: " + problemID);
//				System.out.println("Option questionCount: " + questionCount);
//				System.out.println("Option optionCount: " + optionCount);
//				System.out.println("Option: " + options[problemID][questionCount][optionCount]);

				optionCount++;
			} else if (currentElement.equals("QText")) {
				
				String ques = new String(chars, start, length);
				question[problemID][questionCount] = ques;
//				System.out.println("Question problemID: " + problemID);
//				System.out.println("Question questionCount: " + questionCount);
//				System.out.println("Question: " + question[problemID][questionCount]);
//				questionCount++;
//				hintNum = 0;
			} else if (currentElement.equals("Answer")) {
				String ans = new String(chars, start, length);
				answer[problemID][answerIndex] = ans;
//				System.out.println("Answer problemID: " + problemID);
//				System.out.println("Answer answerIndex: " + answerIndex);
//				System.out.println("Answer: " + answer[problemID][answerIndex]);
				
				answerIndex++;
				optionCount = 0;
				questionCount++;
				count++;
				hintNum = 0;
			} else if (currentElement.equals("Hint")) {
				String thehint = new String(chars, start, length);
				
				
				hint[problemID][questionCount][hintNum] = thehint;
				
//				System.out.println("Hint problemID: " + problemID);
//				System.out.println("Hint questionCount: " + questionCount);
//				System.out.println("Hint hintNum: " + hintNum);
//				System.out.println("Hint: " + thehint);
				
				hintNum++;
			}
		}
	}
}
