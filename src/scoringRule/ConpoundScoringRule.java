package scoringRule;

import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import score.IScore;

public class ConpoundScoringRule implements IScoringRule{
	private ArrayList<IScoringRule> scoringStrategies = null;

	public ConpoundScoringRule() {
		this.scoringStrategies = new ArrayList<IScoringRule>();
	}
	
	
	public void addScoreRule(IScoringRule scoringStrategy) {
		scoringStrategies.add(scoringStrategy);
	}
	
	public void removeScoreRule(IScoringRule scoringStrategy) {
		scoringStrategies.remove(scoringStrategy);
	}
	
	@Override
	public IScore evaluateHand(Hand hand, IScore score) {
		for(IScoringRule s: scoringStrategies) {
			score = s.evaluateHand(hand, score);
		}
		return score;
	}
	
	//evaluate at stage play
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		for(IScoringRule s: scoringStrategies) {
			score = s.evaluateHand(segment, card, score);
		}
		return score;
	}
}
