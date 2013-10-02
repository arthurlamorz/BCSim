package com.lamorz.bcsim.bcstruct;

import android.util.SparseArray;

public class BCManager {

	private SparseArray<BCRound> m_rounds;
	private int m_currentRound;
	
	private static BCManager m_instance= null;
	
	public BCManager()
	{
		m_rounds = new SparseArray<BCRound>();
		m_currentRound = 0;
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

	public static synchronized BCManager getInstance(){
		if (m_instance == null)
			m_instance = new BCManager();
    	return m_instance;
    }
	
	public int simulate(int noOfRounds, int initialAmount, int haltAmount)
	{
		int gainFromLastRound = 0;
		for (int i=0; i<noOfRounds; i++)
		{
			BCRound round = new BCRound(gainFromLastRound);
			gainFromLastRound += round.playAllGames();
			m_rounds.put(m_currentRound, round);
		}
		
		return gainFromLastRound;
	}
	
}
