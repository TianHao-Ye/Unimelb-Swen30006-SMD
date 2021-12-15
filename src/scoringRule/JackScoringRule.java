package scoringRule;

import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class JackScoringRule implements IScoringRule {
	private String scoringType = "jack";
	private final int scoreValue = 1;

	@Override
	public IScore evaluateHand(Hand hand, IScore score) {
		Card starter = hand.getLast();
		hand.removeLast(false);
		Hand h = hand.extractCardsWithRank(Cribbage.Rank.JACK);
		if(h.getNumberOfCards() != 0) {
			if(h.getFirst().getSuit() == starter.getSuit()) {
				IScore addOnScore = new AddOnScore(score, scoringType, scoreValue, h);
				return addOnScore;
			}
		}
		return score;
	}

	@Override
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		return null;
	}

}
