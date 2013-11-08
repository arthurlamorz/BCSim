package com.lamorz.bcsim.bcstruct;


public class BCGame {
	
	public static final int HANDS_PER_GAME = 5;
	private BCHand[] m_hands;
	private int m_currentHand;
	private int m_betLevel;
	private int m_totalGain;
	private int m_prevGain;
	private boolean m_isHalted;
	private BCBetTreeNode m_currentBetTreeNode;
	
	public BCGame()
	{
		init(0,0);
	}
	
	public BCGame(int prevGain)
	{
		int betLevel = determineBetLevel(prevGain);
		init(betLevel, prevGain);
	}
	
	private void init(int betLevel, int prevGain)
	{
		m_hands = new BCHand[HANDS_PER_GAME];	
		m_currentHand = 0;
		m_prevGain = prevGain;
		m_betLevel = betLevel;
		m_currentBetTreeNode = BCBetTreeManager.getInstance().getBetTree(m_betLevel).getRootNode();
	}
	
	
	
	public int getPrevGain() {
		return m_prevGain;
	}

	public void setPrevGain(int prevGain) {
		m_prevGain = prevGain;
	}

	public int getBetLevel() {
		return m_betLevel;
	}

	public void setBetLevel(int betLevel) {
		m_betLevel = betLevel;
	}

	/**
	 * @return the isHalted
	 */
	public boolean isHalted() {
		return m_isHalted;
	}

	/**
	 * @param isHalted the isHalted to set
	 */
	public void setHalted(boolean isHalted) {
		m_isHalted = isHalted;
	}

	public int determineBetLevel(int gain)
	{
		BCBetTreeManager betTreeMgr = BCBetTreeManager.getInstance();
		
		int betLevel = 0;
		if (gain >= betTreeMgr.getBetTree(7).getRootNode().getAmount())
			betLevel = 7;
		else if (gain >= betTreeMgr.getBetTree(6).getRootNode().getAmount())
			betLevel = 6;
		else if (gain >= betTreeMgr.getBetTree(5).getRootNode().getAmount())
			betLevel = 5;
		else if (gain >= betTreeMgr.getBetTree(4).getRootNode().getAmount())
			betLevel = 4;
		else if (gain >= betTreeMgr.getBetTree(3).getRootNode().getAmount())
			betLevel = 3;
		else if (gain >= betTreeMgr.getBetTree(2).getRootNode().getAmount())
			betLevel = 2;
		else if (gain >= betTreeMgr.getBetTree(1).getRootNode().getAmount())
			betLevel = 1;
		
		if (gain <= BCManager.getInstance().getHaltAmount())
			m_isHalted = true;
		else if (gain >= BCManager.getInstance().getUpperHaltAmount())
			m_isHalted = true;
		else
			m_isHalted = false;
		
		return betLevel;
	}
	
	public BCHand playSingleHand()
	{
		if (m_currentHand == HANDS_PER_GAME)
		{
			m_currentBetTreeNode = null;
			return null;
		}
		BCHand hand = new BCHand();
		
		if (m_isHalted)
			hand.play(m_currentHand, 0);
		else
			hand.play(m_currentHand, m_currentBetTreeNode.getWinNode().getAmount(), m_currentBetTreeNode.getLoseNode().getAmount());
		
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
		
		boolean isDanger = true;
		for (int i=0; i<4; i++)
		{
			if (m_hands[i].isWin())
				isDanger = false;
		}
		if (isDanger)
			BCManager.getInstance().incrementDanger();
		
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
