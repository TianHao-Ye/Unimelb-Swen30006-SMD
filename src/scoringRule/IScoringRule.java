package scoringRule;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.IScore;

public interface IScoringRule {

	public IScore evaluateHand(Hand hand, IScore score);
	
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score);
}
