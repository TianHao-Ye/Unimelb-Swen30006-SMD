package scoringRule;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import score.AddOnScore;
import score.IScore;
import cribbage.Cribbage;

public class TotalScoringRule implements IScoringRule {
	private HashMap<Integer, Integer> scoringValue = null;
	private HashMap<Integer, String> scoringType = null;
	
	public TotalScoringRule(){
		this.scoringValue = new HashMap<Integer, Integer>();
		this.scoringType = new HashMap<Integer, String>();
		this.scoringValue.put(15, 2);
		this.scoringValue.put(31, 2);
		this.scoringType.put(15, "fifteen");
		this.scoringType.put(31, "thirtyone");
	}
	
	private int getValue(Card c) {
		return ((Cribbage.Rank)c.getRank()).value;
	}
	
	private Hand getEmptyHand(Hand hand){
		Hand combination = hand.extractCardsWithRank(Cribbage.Rank.JACK);
		combination.removeAll(false);
		return combination;
	}
	
	public Hand createHandCopy(Hand hand) {
		ArrayList<Card> cards = hand.getCardList();
		Hand hand_copy = getEmptyHand(hand);
		for(Card c: cards) {
			hand_copy.insert(c.getSuit(), c.getRank(), false);
		}
		return hand_copy;
	}

	@Override
	public IScore evaluateHand(Hand hand_org, IScore score) {
		Hand hand = createHandCopy(hand_org);
		hand.sort(Hand.SortType.POINTPRIORITY, false);
		
		ArrayList<Hand> ans = new ArrayList<Hand>();
		ans = combinationSum(15, hand);
		for(Hand h: ans) {
			score = new AddOnScore(score, scoringType.get(15), scoringValue.get(15), h);
		}
		return score;
	}

	private ArrayList<Hand> combinationSum(int target, Hand hand) {
		ArrayList<Card> cards = hand.getCardList();
		// container to hold the final combinations
		ArrayList<Hand> results = new ArrayList<Hand>();
		ArrayList<Card> comb = new ArrayList<Card>();

        backtrack(comb, target, 0, cards, results, hand);
        return results;		
	}
	private void backtrack(ArrayList<Card> comb, int remain, int curr, ArrayList<Card> cards,
            ArrayList<Hand> results, Hand hand) {
		if (remain == 0) {
            // make a deep copy of the current combination.
			Hand comb_copy = getEmptyHand(hand);
			for(Card c: comb) {
				comb_copy.insert(c, false);
			}

            results.add(comb_copy);
            return;
        }

		for (int nextCurr = curr; nextCurr < cards.size(); nextCurr++) {
            Card c = cards.get(nextCurr);
            int c_value = getValue(c);
            // add a new element to the current combination
            comb.add(c);
            // continue the exploration with the updated combination
            backtrack(comb, remain - c_value, nextCurr+1, cards, results, hand);
            // backtrack the changes, so that we can try another candidate
            comb.remove(comb.size()-1);
        }
	}

	
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		int total = Cribbage.total(segment.getSegment());
		if(scoringValue.containsKey(total)) {
			IScore addOnScore = new AddOnScore(score, scoringType.get(total), scoringValue.get(total));
			return addOnScore;
		}
		return score;
	}

}
