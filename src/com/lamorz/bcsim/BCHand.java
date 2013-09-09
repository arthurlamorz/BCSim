package com.lamorz.bcsim;

import java.util.Random;

public class BCHand {

	private static final int PLAYER_WIN_RATE = 4931756;
	private static final int MAX_RANDOM_NUMBER = 9999999;
	
	private int m_handID;
	private boolean m_win;
	private int m_gain;
	
	public BCHand() {
		super();
	}

	public void play(int handID, int bet)
	{
		m_handID = handID;
		
		Random ranGen = new Random();
		m_win = ranGen.nextInt(MAX_RANDOM_NUMBER)<PLAYER_WIN_RATE ? true : false;
		m_gain = bet;
		if (!m_win)
			m_gain = -m_gain;
			
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
