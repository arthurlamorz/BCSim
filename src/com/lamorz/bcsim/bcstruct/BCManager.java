package com.lamorz.bcsim.bcstruct;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class BCManager {

	private Map<Integer, BCRound> m_rounds;
	private int m_currentRound;
	private int m_initialAmount;
	private int m_haltAmount;
	
	private static BCManager m_instance= null;
	
	public BCManager()
	{
		m_rounds = new HashMap<Integer, BCRound>();
		m_currentRound = 0;
		m_initialAmount = 0;
		m_haltAmount = 0;
	}
	
	public BCRound getRound(int roundNo)
	{
		return m_rounds.get(roundNo);
	}
	
	public int getCurrentRound() {
		return m_currentRound;
	}

	public void setCurrentRound(int currentRound) {
		m_currentRound = currentRound;
	}
	
	/**
	 * @return the initialAmount
	 */
	public int getInitialAmount() {
		return m_initialAmount;
	}

	/**
	 * @param initialAmount the initialAmount to set
	 */
	public void setInitialAmount(int initialAmount) {
		m_initialAmount = initialAmount;
	}

	/**
	 * @return the haltAmount
	 */
	public int getHaltAmount() {
		return m_haltAmount;
	}

	/**
	 * @param haltAmount the haltAmount to set
	 */
	public void setHaltAmount(int haltAmount) {
		m_haltAmount = haltAmount;
	}

	public static synchronized BCManager getInstance(){
		if (m_instance == null)
			m_instance = new BCManager();
    	return m_instance;
    }
	
	public void reset()
	{
		m_rounds.clear();
		m_currentRound = 0;
	}
	
	public int simulate(int noOfRounds, int initialAmount, int haltAmount)
	{
		int gainFromLastRound = 0;
		for (int i=0; i<noOfRounds; i++)
		{
			BCRound round = new BCRound(gainFromLastRound);
			gainFromLastRound += round.playAllGames();
			m_rounds.put(m_currentRound, round);
			m_currentRound++;
		}
		
		return gainFromLastRound;
	}
	
	public List<Integer> multipleSimulate(int noOfPass, int noOfRounds, int initialAmount, int haltAmount)
	{
		List<Integer> gains = new ArrayList<Integer>();
		return gains;
	}
	
}
