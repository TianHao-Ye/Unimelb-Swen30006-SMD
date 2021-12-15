package factory;

import scoringRule.ConpoundScoringRule;
import scoringRule.FlushScoringRule;
import scoringRule.IScoringRule;
import scoringRule.JackScoringRule;
import scoringRule.LastCardScoringRule;
import scoringRule.PairScoringRule;
import scoringRule.RunScoringRule;
import scoringRule.StarterScoringRule;
import scoringRule.TotalScoringRule;

public class ScoringRuleFactory {
	private static ScoringRuleFactory scoringRuleFactory = null;
	private ScoringRuleFactory() {
		
	}
	public IScoringRule getScoringRules(String gameStage){
		gameStage = gameStage.toLowerCase();
		ConpoundScoringRule scoringRules = new ConpoundScoringRule();
		if(gameStage == "starter") {
			scoringRules.addScoreRule(new StarterScoringRule());
		}
		else if(gameStage == "play") {
			scoringRules.addScoreRule(new TotalScoringRule());
			scoringRules.addScoreRule(new LastCardScoringRule());
			scoringRules.addScoreRule(new RunScoringRule());
			scoringRules.addScoreRule(new PairScoringRule());
		}
		else if(gameStage == "show") {
			scoringRules.addScoreRule(new TotalScoringRule());
			scoringRules.addScoreRule(new RunScoringRule());
			scoringRules.addScoreRule(new PairScoringRule());
			scoringRules.addScoreRule(new FlushScoringRule());
			scoringRules.addScoreRule(new JackScoringRule());
		}
		return scoringRules;
	}

	public static ScoringRuleFactory getInstance() {
		if(scoringRuleFactory == null) {
			scoringRuleFactory = new ScoringRuleFactory();
		}
		return scoringRuleFactory;
	}

}
