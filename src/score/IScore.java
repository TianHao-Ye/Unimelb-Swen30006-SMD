package score;

import ch.aplu.jcardgame.Hand;
import log.Log;

public interface IScore {
	public IScore getWrappedScore();
	public void  logScore(Log log, int playerIndex);
	public int getScore();
}
