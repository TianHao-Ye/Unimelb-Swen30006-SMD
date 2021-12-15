package scoringRule;

import java.util.ArrayList;
import java.util.HashMap;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import cribbage.Cribbage;
import log.Log;
import score.AddOnScore;
import score.IScore;

public class RunScoringRule implements IScoringRule {
	private HashMap<Integer, Integer> scoringValue = null;
	private HashMap<Integer, String> scoringType = null;

	public RunScoringRule() {
		this.scoringValue = new HashMap<Integer, Integer>();
		this.scoringType = new HashMap<Integer, String>();
		scoringValue.put(3, 3);
		scoringValue.put(4, 4);
		scoringValue.put(5, 5);
		scoringValue.put(6, 6);
		scoringValue.put(7, 7);
		scoringType.put(3, "run3");
		scoringType.put(4, "run4");
		scoringType.put(5, "run5");
		scoringType.put(6, "run6");
		scoringType.put(7, "run7");
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
		
		int handSize = hand.getNumberOfCards();
		int sequenceSize = 3;
		ArrayList<Hand> resultSequences = new ArrayList<Hand>();
		Hand[] sequences;

		// finding longest possible sequences
		while(sequenceSize <= handSize) {
			sequences = hand.extractSequences(sequenceSize);
			if(sequences.length == 0) {
				break;
			}
			// for every newly found sequence
			for(Hand sequence: sequences) {
				// iterate previously found shorter sequences
				for (Hand existed_squence: resultSequences) {
					Card one_of_card = existed_squence.getLast();
					//if one of card in newly found sequence exists in previously found shorter sequences, remove shorter sequence
					if(sequence.contains(one_of_card)) {
						resultSequences.remove(existed_squence);
					}
				}
				// add new longer sequence to the result
				resultSequences.add(sequence);
			}
			sequenceSize++;
		}
		// really add score
		for (Hand h: resultSequences) {
			int run_number = h.getNumberOfCards();
			score = new AddOnScore(score, scoringType.get(run_number), scoringValue.get(run_number), h);
		}
		return score;
	}
	public IScore evaluateHand(Cribbage.Segment segment, Card card, IScore score) {
		Hand s = segment.getSegment();
		int segmentSize = s.getNumberOfCards();
		if(card == null || segmentSize <=2) {
			return score;
		}
		ArrayList<Card> cards = s.getCardList();
		int maxRun = 0;
		int sequenceSize = 3;
		Hand[] sequences;
		while(sequenceSize <= segmentSize) {
			sequences = s.extractSequences(sequenceSize);
			// no sequence of this size, also no for size larger than it.
			if(sequences.length == 0) {
				break;
			}
			//examine sequences
			for (Hand sequence: sequences) {
				boolean runContinuous = true;
				for(int i=segmentSize-1; i>(segmentSize-sequenceSize-1); i--) {
					if(!sequence.contains(cards.get(i))) {
						runContinuous = false;
					}
				}
				if(runContinuous == true) {
					maxRun = sequenceSize;
				}
			}
			// try larger sequence size
			sequenceSize ++;
		}
		
		if(maxRun >=3) {
			IScore addOnScore = new AddOnScore(score, scoringType.get(maxRun), scoringValue.get(maxRun));
			return addOnScore;
		}

		return score;		
	}
}
