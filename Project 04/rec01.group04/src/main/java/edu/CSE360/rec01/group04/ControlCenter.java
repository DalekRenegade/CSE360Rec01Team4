/**
 *this is the Control Center class
 *follows the observable pattern and singleton pattern.
 *Completion time: 30 hours
 *
 *@author David Edwards, [Amit Ranjan]
 *@Version 3.0
 */
package edu.CSE360.rec01.group04;
import java.util.Observable;


public class ControlCenter extends Observable {
	
	final int SLIDER = 8;
	final int QUESTION = 2;
	String _observerState;
	
	private static ControlCenter _instance;
	
	private int[][] correctAnswers = new int[SLIDER][QUESTION];
	private int[][] wrongAnswers = new int[SLIDER][QUESTION];
	private int[][] timeSpent = new int[SLIDER][QUESTION];
	private int total[] = new int[SLIDER];
	private int[][] status;
	
	int sliderState;
	
	public Quiz gquiz, uquiz;
	
	protected ControlCenter() {}
	
	public void ReadQuiz() {
		QuizReader qr = new QuizReader();
	
		
		gquiz = new GuidedQuiz();
		uquiz = new UnguidedQuiz();
		
		gquiz.setQuiz(qr);
		uquiz.setQuiz(qr);
	}
	
	public int getSliderCount() {
		return SLIDER;
	}
	
	public static ControlCenter getInstance() {
		if (_instance == null) {
			_instance = new ControlCenter();
		} 
		
		return _instance;
	}
	
	public void addObserver(Object obj) {
		
		if (obj != null) {
			if (_instance == null) {
				_instance = getInstance();
				
			}
			
			_instance.addObserver(obj);
		}
	}
	public void setCorrect(int i, int j) {
		correctAnswers[i][j]++;
		total[i]++;
	}
	
	public void setIsDefaultSliderState(int sliderState) {
		this.sliderState = sliderState;
	}
	
	public int getSliderState() {
		return sliderState;
	}
	
	public void setWrong(int i, int j) {
		wrongAnswers[i][j]++;
		total[i]++;
	}
	
	public void setTime(int i, int j, int s) {
		timeSpent[i][j] += s;
		
	}
	
	public String getObserverState() {
		
		return _observerState;
	}
	
	public void setStatus() {
		int numCorrect[] = new int[SLIDER];
		int numWrong[] = new int[SLIDER];
		int avgTime[] = new int[SLIDER];
		
		//first array: numCorrect, numWrong, total
		//second array: corresponding slider position
		int[][] retVal = new int[3][SLIDER];
		
		//set all values to zero
		for (int i = 0; i < SLIDER; i++) {
			numCorrect[i] = 0;
			numWrong[i] = 0;
			avgTime[i] = 0;
		}
		
		//get total number of correct
		for (int i = 0; i < SLIDER; i++) {
			for (int j = 0; j < QUESTION; j++) {
				numCorrect[i] += correctAnswers[i][j];
				numWrong[i] += wrongAnswers[i][j];
				avgTime[i] += timeSpent[i][j];
			}
			//calculate avgTimeSpent but do not divide by 0
			if (total[i] != 0)
				avgTime[i] = avgTime[i]/total[i];
			
			//build the array to return
			retVal[0][i] = numCorrect[i];
			retVal[1][i] = numWrong[i];
			retVal[2][i] = avgTime[i];
			
		}
		
		if (numCorrect[sliderState-1] == 0 && numWrong[sliderState-1] == 0) {
			_observerState = "unset";
		} else if (numCorrect[sliderState-1] > numWrong[sliderState-1]*1.5 && avgTime[sliderState-1] < 90) {
			_observerState = "Excellent";
		} else if (numCorrect[sliderState-1] > numWrong[sliderState-1]*1.1 && avgTime[sliderState-1] < 90) {
			_observerState = "Good";
		} else if (numCorrect[sliderState-1] >= numWrong[sliderState-1] && avgTime[sliderState-1] < 90) {
			_observerState = "Bad";
		} else if (numCorrect[sliderState-1] > numWrong[sliderState-1]*3 && avgTime[sliderState-1] > 90) {
			_observerState = "Excellent";
		} else if (numCorrect[sliderState-1] >= numWrong[sliderState-1]*2 && avgTime[sliderState-1] > 90) {
			_observerState = "Good";
		} else if (numCorrect[sliderState-1] >= numWrong[sliderState-1] && avgTime[sliderState-1] > 90) {
			_observerState = "Bad";
		} else if (numCorrect[sliderState-1] < numWrong[sliderState-1]) {
			_observerState = "Help!";
		}
		
		setChanged();
		//notifyObservers();
	}
	
	
//	public

}
