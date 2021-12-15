package scoringRule;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class StarterScoringRule implements IScoringRule {
	private final String scoringType = "starter";
	private final int scoreValue = 2;
	
	@Override
	public IScore evaluateHand(Hand hand, IScore score) {
		Hand starter = hand.extractCardsWithRank(Cribbage.Rank.JACK);
		if(hand.getNumberOfCardsWithRank(Cribbage.Rank.JACK)==1) {
			IScore addOnScore = new AddOnScore(score, scoringType, scoreValue, starter);
			return addOnScore;
		}
		return score;
	}

	@Override
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		// TODO Auto-generated method stub
		return null;
	}

}
