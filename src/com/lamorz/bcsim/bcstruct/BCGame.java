package com.lamorz.bcsim.bcstruct;

import com.lamorz.bcsim.BCBetTree;

public class BCGame {
	
	private final int HANDS_PER_GAME = 5;
	private BCHand[] m_hands;
	private int m_currentHand;
	private int m_betLevel;
	private BCBetTree m_betTree;
	
	public BCGame()
	{
		m_hands = new BCHand[HANDS_PER_GAME];	
		m_currentHand = 0;
		m_betLevel = 1;
		m_betTree = new BCBetTree(); 
	}
	
	public BCHand playSingleHand()
	{
		if (m_currentHand == HANDS_PER_GAME)
			return null;
		
		BCHand hand = new BCHand();
		hand.play(m_currentHand, m_betLevel);
		
		m_hands[m_currentHand] = hand;
		m_currentHand++;
		
		return hand; 
	}
	
	public int playAllHands()
	{
		while (m_currentHand < HANDS_PER_GAME)
			playSingleHand();
		
		return m_currentHand;
	}

	public BCHand getHand(int handNo)
	{
		return m_hands[handNo];
	}

}
