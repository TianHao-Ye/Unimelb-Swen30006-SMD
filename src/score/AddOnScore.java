package score;

import ch.aplu.jcardgame.Hand;
import log.Log;
import scoringRule.IScoringRule;

public class AddOnScore implements IScore {
	private IScore wrappedScore;
	private int addedScore;
	private String addedType;
	private Hand addedCardsCombination = null;
	private String addedCardsCombinationString = null;
	
	@Override
	public void logScore(Log log, int playerIndex) {
		if(! (wrappedScore instanceof BaseScore)) {
			wrappedScore.logScore(log, playerIndex);
		}
		if(addedCardsCombination == null) {
			log.logging(String.format("score, P%d, %d, %d, %s", playerIndex, this.getScore(), addedScore, addedType));


		}
		else{
			log.logging(String.format("score, P%d, %d, %d, %s, %s", playerIndex, this.getScore(), addedScore, addedType, addedCardsCombinationString));
		}
	}
	
	public IScore getWrappedScore() {
		return wrappedScore;
	}
	
	public Hand getScoringCombination() {
		return addedCardsCombination;
	}
	public void setScoringCombinationString(String text) {
		this.addedCardsCombinationString = text;
	}

	public AddOnScore(IScore wrappedScore, String addedType, int addedPoints) {
		this.wrappedScore = wrappedScore;
		this.addedType = addedType;
		this.addedScore = addedPoints;
	}
	
	public AddOnScore(IScore wrappedScore, String addedType, int addedPoints, Hand addedCardsCombination) {
		this.wrappedScore = wrappedScore;
		this.addedType = addedType;
		this.addedScore = addedPoints;
		this.addedCardsCombination = addedCardsCombination;
	}

	@Override
	public int getScore() {
		return this.wrappedScore.getScore() + this.addedScore;
	}
}
