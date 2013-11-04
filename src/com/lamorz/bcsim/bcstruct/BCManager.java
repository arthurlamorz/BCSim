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
	private int m_totalGain;
	private int m_noOfWin;
	private int m_noOfLose;
	private int m_winRate;
	
	private BCStatistics m_newestStats; 
	
	private static BCManager m_instance= null;
	
	public BCManager()
	{
		m_rounds = new HashMap<Integer, BCRound>();
		m_currentRound = 0;
		m_initialAmount = 0;
		m_haltAmount = 0;
		m_totalGain = 0;
		m_noOfWin = 0;
		m_noOfLose = 0;
		m_winRate = 4931756;
		
		m_newestStats = null;
	}
	
	
	
	public int getWinRate() {
		return m_winRate;
	}



	public void setWinRate(int winRate) {
		m_winRate = winRate;
	}



	/**
	 * @return the newestStats
	 */
	public BCStatistics getNewestStats() {
		return m_newestStats;
	}

	/**
	 * @param newestStats the newestStats to set
	 */
	public void setNewestStats(BCStatistics newestStats) {
		m_newestStats = newestStats;
	}

	public BCRound getRound(int roundNo)
	{
		return m_rounds.get(roundNo);
	}
	
	public int getCurrentRound() {
		return m_currentRound;
	}

	public int getNoOfWin() {
		return m_noOfWin;
	}

	public void setNoOfWin(int noOfWin) {
		m_noOfWin = noOfWin;
	}

	public int getNoOfLose() {
		return m_noOfLose;
	}

	public void setNoOfLose(int noOfLose) {
		m_noOfLose = noOfLose;
	}

	public void setCurrentRound(int currentRound) {
		m_currentRound = currentRound;
	}
	
	/**
	 * @return the totalGain
	 */
	public int getTotalGain() {
		return m_totalGain;
	}

	/**
	 * @param totalGain the totalGain to set
	 */
	public void setTotalGain(int totalGain) {
		m_totalGain = totalGain;
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
		m_totalGain = 0;
		m_noOfWin = 0;
		m_noOfLose = 0;
	}
	
	public int simulate(int noOfRounds, int upperLimit, int lowerLimit)
	{
		int gainFromLastRound = 0;
		int m_totalGain = 0;
		for (int i=0; i<noOfRounds; i++)
		{
			BCRound round = new BCRound(gainFromLastRound);
			gainFromLastRound += round.playAllGames();
			m_rounds.put(m_currentRound, round);
			m_currentRound++;
			
			if (gainFromLastRound <= lowerLimit || gainFromLastRound >= upperLimit)
			{
				round.setReset(true);
				m_totalGain += gainFromLastRound;
				gainFromLastRound = 0;
			}
		}
		
		m_totalGain += gainFromLastRound;
		return m_totalGain;
	}
	
	public BCStatistics multipleSimulate(int noOfPass, int noOfRounds, int upperLimit, int lowerLimit)
	{
		BCStatistics stats = new BCStatistics();

		for (int i=0; i<noOfPass; i++)
		{
			stats.putResult(simulate(noOfRounds, upperLimit, lowerLimit));
		}
		
		stats.calculateMean();
		stats.calculateStdDev();
		stats.calculateMeanRoundToTarget();
		stats.calculateSDRoundToTarget();
		m_newestStats = stats;
		return stats;
	}
	
	public void incrementWin() { m_noOfWin ++; }
	public void incrementLose() { m_noOfLose ++; }
}
