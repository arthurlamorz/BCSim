package com.lamorz.bcsim.bcstruct;

import com.lamorz.bcsim.BCBetTree;

public class BCGame {
	
	private final int HANDS_PER_GAME = 5;
	private BCHand[] m_hands;
	private int m_currentHand;
	private int m_betLevel;
	private int m_totalGain;
	private BCBetTree m_betTree;
	
	public BCGame()
	{
		init(1);
	}
	
	public BCGame(int betLevel)
	{
		init(betLevel);
	}
	
	private void init(int betLevel)
	{
		m_hands = new BCHand[HANDS_PER_GAME];	
		m_currentHand = 0;
		m_betLevel = betLevel;
		m_betTree = new BCBetTree(); 
	}
	
	public int getBetLevel() {
		return m_betLevel;
	}

	public void setBetLevel(int betLevel) {
		m_betLevel = betLevel;
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
			m_totalGain = playSingleHand().getGain();
		
		return m_totalGain;
	}

	public BCHand getHand(int handID)
	{
		if (handID >= HANDS_PER_GAME)
			return null;
		
		return m_hands[handID];
	}

	public int getCurrentHand() {
		return m_currentHand;
	}

	public void setCurrentHand(int currentHand) {
		m_currentHand = currentHand;
	}

}
