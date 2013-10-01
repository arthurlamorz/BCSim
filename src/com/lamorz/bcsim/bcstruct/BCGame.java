package com.lamorz.bcsim.bcstruct;


public class BCGame {
	
	public static final int HANDS_PER_GAME = 5;
	private BCHand[] m_hands;
	private int m_currentHand;
	private int m_betLevel;
	private int m_totalGain;
	private BCBetTreeNode m_currentBetTreeNode;
	
	public BCGame()
	{
		init(0);
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
		
		m_currentBetTreeNode = BCBetTreeManager.getInstance().getBetTree(m_betLevel).getRootNode();
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
		{
			m_currentBetTreeNode = null;
			return null;
		}
		BCHand hand = new BCHand();
		hand.play(m_currentHand, m_currentBetTreeNode.getWinNode().getAmount());
		
		if(hand.isWin())
			m_currentBetTreeNode = m_currentBetTreeNode.getWinNode();
		else
			m_currentBetTreeNode = m_currentBetTreeNode.getLoseNode();
		
		m_hands[m_currentHand] = hand;
		m_currentHand++;
		
		return hand; 
	}
	
	public int playAllHands()
	{
		while (m_currentHand < HANDS_PER_GAME)
			m_totalGain += playSingleHand().getGain();
		
		return m_totalGain;
	}

	public int getTotalGain() {
		return m_totalGain;
	}

	public void setTotalGain(int totalGain) {
		m_totalGain = totalGain;
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
