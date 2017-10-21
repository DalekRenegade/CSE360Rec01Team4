/**
 * Question Progress Report - keeps track of attempt count, correctness and various time values for result calculation
 * Recitation Project # 03
 * Recitation Group #01 Team #04
 * Completion Time = 1 hour
 * @author Amit Ranjan, David Edwards
 * @version 2.0
 */

package Project04;

public class QuestionProgressReport {

	int quesNo;
	int numAttempts = 0;
	long actualTimeTaken;
	long ansBestTimeLimit, ansAverageTimeLimit, ansUpperTimeLimit, ansExceededTimeLimit;
	boolean isCorrect;

	public QuestionProgressReport() {
		this.quesNo = 0;
		this.actualTimeTaken = 0;
		this.isCorrect = false;
		this.ansBestTimeLimit = 5;
		this.ansAverageTimeLimit = 10;
		this.ansUpperTimeLimit = 15;
		this.ansExceededTimeLimit = 20;
	}

	public QuestionProgressReport(int quesNo, long ansBestTimeLimit, long ansAverageTimeLimit, long ansUpperTimeLimit,
			long ansExceededTimeLimit) {
		this();
		this.quesNo = quesNo;
		this.ansBestTimeLimit = ansBestTimeLimit;
		this.ansAverageTimeLimit = ansAverageTimeLimit;
		this.ansUpperTimeLimit = ansUpperTimeLimit;
		this.ansExceededTimeLimit = ansExceededTimeLimit;
	}

	public void setAttempted() {
		this.numAttempts++;
	}

	public int getAttempts() {
		return this.numAttempts;
	}

	public void setCorrectness(boolean correct) {
		this.isCorrect = correct;
	}

	public boolean getCorrectness() {
		return this.isCorrect;
	}

	public void increaseTimer(long timer) {

	}

	public long getTimeTaken() {
		return this.actualTimeTaken;
	}

	public long getAnsBestTimeLimit() {
		return this.ansBestTimeLimit;
	}

	public long getAnsAverageTimeLimit() {
		return this.ansAverageTimeLimit;
	}

	public long getAnsUpperTimeLimit() {
		return this.ansUpperTimeLimit;
	}

	public long getAnsExceededTimeLimit() {
		return this.ansExceededTimeLimit;
	}

}