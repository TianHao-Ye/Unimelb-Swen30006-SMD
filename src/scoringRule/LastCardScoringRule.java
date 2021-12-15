package scoringRule;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class LastCardScoringRule implements IScoringRule {
	private String scoringType = "go";
	private final int scoreValue = 1;

	@Override
	public IScore evaluateHand(Hand hand, IScore score) {
		// TODO Auto-generated method stub
		return score;
	}
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		if(segment.isNewSegment() && card ==null) {
			IScore addOnScore = new AddOnScore(score, scoringType, scoreValue);
			return addOnScore;
		}
		if(segment.isEmptyHand() && card ==null) {
			IScore addOnScore = new AddOnScore(score, scoringType, scoreValue);
			return addOnScore;
		}
		return score;
	}

}
