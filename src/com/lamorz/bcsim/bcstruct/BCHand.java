package com.lamorz.bcsim.bcstruct;

import java.util.Random;

public class BCHand {

	private static final int MAX_RANDOM_NUMBER = 9999999;
	
	private int m_handID;
	private boolean m_win;
	private int m_gain;
	private int m_winRate;
	
	public BCHand() {
		super();
		m_winRate = BCManager.getInstance().getWinRate();
	}

	public void play(int handID, int bet)
	{
		m_handID = handID;
		
		Random ranGen = new Random();
		m_win = ranGen.nextInt(MAX_RANDOM_NUMBER)<m_winRate ? true : false;
		m_gain = bet;
		if (!m_win)
		{
			m_gain = -m_gain;
			BCManager.getInstance().incrementLose();
		}
		else
			BCManager.getInstance().incrementWin();
			
	}
	
	public void play(int handID, int winAmount, int loseAmount)
	{
		m_handID = handID;
		
		Random ranGen = new Random();
		m_win = ranGen.nextInt(MAX_RANDOM_NUMBER)<m_winRate ? true : false;
		if (!m_win)
		{
			m_gain = loseAmount;
			BCManager.getInstance().incrementLose();
		}
		else
		{
			m_gain = winAmount;
			BCManager.getInstance().incrementWin();
		}
			
	}
	
	//Generated getsetters
	/**
	 * @return the handID
	 */
	public int getHandID() {
		return m_handID;
	}
	/**
	 * @param handID the handID to set
	 */
	public void setHandID(int handID) {
		m_handID = handID;
	}
	
	/**
	 * @return the win
	 */
	public boolean isWin() {
		return m_win;
	}

	/**
	 * @param win the win to set
	 */
	public void setWin(boolean win) {
		m_win = win;
	}

	/**
	 * @return the gain
	 */
	public int getGain() {
		return m_gain;
	}
	/**
	 * @param gain the gain to set
	 */
	public void setGain(int gain) {
		m_gain = gain;
	}
	
	
	
}
