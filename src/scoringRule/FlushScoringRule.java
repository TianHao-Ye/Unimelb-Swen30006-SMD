package scoringRule;

import java.util.HashMap;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class FlushScoringRule implements IScoringRule {
	private HashMap<Integer, Integer> scoringValue = null;
	private HashMap<Integer, String> scoringType = null;
	
	
	public FlushScoringRule() {
		this.scoringValue = new HashMap<Integer, Integer>();
		this.scoringType = new HashMap<Integer, String>();
		scoringType.put(4, "flush4");
		scoringType.put(5, "flush5");
		scoringValue.put(4, 4);
		scoringValue.put(5, 5);
	}
	@Override
	public IScore evaluateHand(Hand hand, IScore score) {
		Hand h = hand.extractCardsWithSuit(hand.getFirst().getSuit());
		int flush = h.getNumberOfCards();
		if(scoringType.containsKey(flush)) {
			IScore addOnScore = new AddOnScore(score, scoringType.get(flush), scoringValue.get(flush), h);
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
