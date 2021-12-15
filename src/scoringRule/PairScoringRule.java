package scoringRule;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import cribbage.Cribbage.Rank;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class PairScoringRule implements IScoringRule{
	private HashMap<Integer, Integer> scoringValue = null;
	private HashMap<Integer, String> scoringType = null;
	
	
	public PairScoringRule() {
		this.scoringValue = new HashMap<Integer, Integer>();
		this.scoringType = new HashMap<Integer, String>();
		scoringType.put(2, "pair2");
		scoringType.put(3, "pair3");
		scoringType.put(4, "pair4");
		scoringValue.put(2, 2);
		scoringValue.put(3, 6);
		scoringValue.put(4, 12);
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
		HashMap<Cribbage.Rank, Integer> cardNumber = new HashMap<Cribbage.Rank, Integer>();
		ArrayList<Card> cards = hand.getCardList();
		
		//count card number of same rank
		for (Card c: cards) {
			if(!cardNumber.containsKey((Cribbage.Rank)c.getRank())) {
				cardNumber.put((Cribbage.Rank) c.getRank(), 1);
			}
			else{
				Integer previousNum = cardNumber.get((Cribbage.Rank) c.getRank());
				cardNumber.put((Cribbage.Rank) c.getRank(), previousNum+1);
			}
		}
		
		// start with 2, add possible pairs
		for (int i=2; i<5; i++) {
			for (Cribbage.Rank r: cardNumber.keySet()) {
				if(cardNumber.get(r) == i) {
					Hand pair = hand.extractCardsWithRank(r);
					score = new AddOnScore(score, scoringType.get(i), scoringValue.get(i), pair);
				}
			}
		}
		return score;
	}
	
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		Hand s = segment.getSegment();
		if(card == null) {
			return score;
		}
		int segmentSize = s.getNumberOfCards();
		if (segmentSize == 0) {
			return score;
		}
		int segmentIndex = segmentSize-1;
		int pair = 0;
		ArrayList<Card> cards = s.getCardList();
		Card lastCard = cards.get(segmentIndex);
		int lastCardRank = ((Cribbage.Rank) lastCard.getRank()).order;
		int cardRank = ((Cribbage.Rank) card.getRank()).order;
		while(lastCardRank == cardRank) {
			pair++;
			segmentIndex--;
			if(segmentIndex == -1) {
				break;
			}
			
			lastCard = cards.get(segmentIndex);
			lastCardRank = ((Cribbage.Rank) lastCard.getRank()).order;
		}

		if(pair>=2) {
			IScore addOnScore = new AddOnScore(score, scoringType.get(pair), scoringValue.get(pair));
			return addOnScore;
		}
		
		return score;
		
	}
}
