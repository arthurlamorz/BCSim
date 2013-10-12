package com.lamorz.bcsim;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lamorz.bcsim.bcstruct.BCManager;
import com.lamorz.bcsim.bcstruct.BCStatistics;

public class BCSimulateThread extends Thread {

	private BCManager m_bcManager;
	
	private boolean m_isRunning; 
	private int m_noOfPasses;
	private int m_noOfRounds;
	private int m_upperLimit;
	private int m_lowerLimit;
	private int m_haltAmount;
	private int m_currentPass;
	
	public BCSimulateThread ()
	{
		m_bcManager = BCManager.getInstance();
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(BCLayoutManager.getInstance().getCtx());
		m_noOfPasses = Integer.parseInt(SP.getString("no_of_passes_text", "1000"));
		m_noOfRounds = Integer.parseInt(SP.getString("no_of_rounds_text", "10"));
		m_upperLimit = Integer.parseInt(SP.getString("upper_limit_text", "3"));
		m_lowerLimit = Integer.parseInt(SP.getString("lower_limit_text", "-5"));
		m_haltAmount = Integer.parseInt(SP.getString("halt_amount_text", "-5"));
		BCManager.getInstance().setHaltAmount(m_haltAmount);
		m_isRunning = false;
		m_currentPass = 0;
	}
 
	/**
	 * @return the currentPass
	 */
	public int getCurrentPass() {
		return m_currentPass;
	}

	/**
	 * @return the isRunning
	 */
	public boolean isRunning() {
		return m_isRunning;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		m_isRunning = true;
		BCStatistics stats = new BCStatistics();

		for (m_currentPass=0; m_currentPass<m_noOfPasses; m_currentPass++)
		{
			BCManager.getInstance().reset();
			stats.putResult(BCManager.getInstance().simulate(m_noOfRounds, m_upperLimit, m_lowerLimit));
		}
		
		stats.calculateMean();
		stats.calculateStdDev();
		stats.calculateMeanRoundToTarget();
		stats.calculateSDRoundToTarget();
		BCManager.getInstance().setNewestStats(stats);
		m_isRunning = false;
		
	}
	
	
}
