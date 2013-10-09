package com.lamorz.bcsim.bcstruct;

import java.util.List;
import java.util.ArrayList;

public class BCStatistics {

	private List<Integer> m_results;
	private int m_noOfSamples;
	private int m_lowest;
	private int m_cumulativeLow;
	private int m_currentTotalLost;
	private int m_maxTotalLost;
	private int m_currentConsecutiveLost;
	private int m_maxConsecutiveLost;
	
	private int m_totalGain;
	private int m_sumXSquared;
	
	private int m_historyHigh;
	private int m_maxDiffFromHigh;
	
	private double m_mean;
	private double m_sd;
	
	
	public BCStatistics() {
		super();
		// TODO Auto-generated constructor stub
		m_results = new ArrayList<Integer>();
		m_lowest = 0;
		m_cumulativeLow = 0;
		m_totalGain = 0;
		m_sumXSquared = 0;
		m_currentTotalLost = 0;
		m_maxTotalLost = 0;
		m_currentConsecutiveLost = 0;
		m_maxConsecutiveLost = 0;
		m_mean = 0.0;
		m_sd = 0.0;
	}
	
	
	
	/**
	 * @return the maxDiffFromHigh
	 */
	public int getMaxDiffFromHigh() {
		return m_maxDiffFromHigh;
	}



	/**
	 * @param maxDiffFromHigh the maxDiffFromHigh to set
	 */
	public void setMaxDiffFromHigh(int maxDiffFromHigh) {
		m_maxDiffFromHigh = maxDiffFromHigh;
	}



	/**
	 * @return the historyHigh
	 */
	public int getHistoryHigh() {
		return m_historyHigh;
	}


	/**
	 * @param historyHigh the historyHigh to set
	 */
	public void setHistoryHigh(int historyHigh) {
		m_historyHigh = historyHigh;
	}


	public int getCumulativeLow() {
		return m_cumulativeLow;
	}


	public void setCumulativeLow(int cumulativeLow) {
		m_cumulativeLow = cumulativeLow;
	}


	public int getTotalGain() {
		return m_totalGain;
	}


	public void setTotalGain(int totalGain) {
		m_totalGain = totalGain;
	}


	public int getMaxTotalLost() {
		return m_maxTotalLost;
	}


	public void setMaxTotalLost(int maxTotalLost) {
		m_maxTotalLost = maxTotalLost;
	}


	public int getNoOfSamples() {
		return m_noOfSamples;
	}


	public void setNoOfSamples(int noOfSamples) {
		m_noOfSamples = noOfSamples;
	}


	public List<Integer> getResults() {
		return m_results;
	}

	public int getLowest() {
		return m_lowest;
	}
	public void setLowest(int lowest) {
		m_lowest = lowest;
	}
	public int getMaxConsecutiveLost() {
		return m_maxConsecutiveLost;
	}
	public void setMaxConsecutiveLost(int maxConsecutiveLost) {
		m_maxConsecutiveLost = maxConsecutiveLost;
	}
	public double getMean() {
		return m_mean;
	}
	public void setMean(double mean) {
		m_mean = mean;
	}
	public double getSd() {
		return m_sd;
	}
	public void setSd(double sd) {
		m_sd = sd;
	}
	
	public void putResult(int result)
	{
		//m_results.add(result);
		
		m_sumXSquared += result*result;
		m_totalGain += result;
		m_noOfSamples++;
		if (m_totalGain < m_cumulativeLow)
			m_cumulativeLow = m_totalGain;
		
		//m_noOfSamples = m_results.size();
		
		if (result < m_lowest)
			m_lowest = result;
		
		
		if (result < 0)
		{
			m_currentConsecutiveLost += 1;
			m_currentTotalLost += result;
			
			if (m_currentConsecutiveLost > m_maxConsecutiveLost)
				m_maxConsecutiveLost = m_currentConsecutiveLost;
			
			if (m_currentTotalLost < m_maxTotalLost)
				m_maxTotalLost = m_currentTotalLost;
		}
		else
		{
			m_currentConsecutiveLost = 0;
			m_currentTotalLost = 0;
		}
		
		if (m_totalGain > m_historyHigh)
			m_historyHigh = m_totalGain;
		
		int diffFromHigh = m_totalGain - m_historyHigh;
		if (diffFromHigh < m_maxDiffFromHigh)
			m_maxDiffFromHigh = diffFromHigh;
	}
	
	public double calculateMean()
	{
		double total = 0;
		
		if (m_noOfSamples == 0) return 0;
		
		
		m_mean = ((double)m_totalGain)/((double)m_noOfSamples);
		return m_mean;
		
	}
	
	public double calculateVariance()
    {
		if (m_noOfSamples == 0) return 0;
		
        double mean = calculateMean();
        double meanXSquared = (double)m_sumXSquared/m_noOfSamples;
        
        return meanXSquared - (mean * mean);
       
    }

    public double calculateStdDev()
    {
    	m_sd = Math.sqrt(calculateVariance());
        return m_sd;
    }
	
}
