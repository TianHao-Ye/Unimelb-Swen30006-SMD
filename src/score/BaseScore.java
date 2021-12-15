package score;

import ch.aplu.jcardgame.Hand;
import log.Log;

public class BaseScore implements IScore {
	private int score;

	@Override
	public void logScore(Log log, int playerIndex) {
	}
	
	public BaseScore(int score) {
		this.score = score;
	}

	@Override
	public IScore getWrappedScore() {
		return null;
	}

	@Override
	public int getScore() {
		return score;
	}

}
