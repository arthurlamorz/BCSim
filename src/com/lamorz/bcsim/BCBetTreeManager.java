package com.lamorz.bcsim;

public class BCBetTreeManager {
	
	public static final int TOTAL_BET_LEVELS = 8;
	private BCBetTree[] m_betTrees;
	
	private static BCBetTreeManager m_instance= null;


    public static synchronized BCBetTreeManager getInstance(){
    	if(null == m_instance){
    		m_instance = new BCBetTreeManager();
    	}
    	return m_instance;
    }
	
	public BCBetTreeManager()
	{
		m_betTrees = new BCBetTree[TOTAL_BET_LEVELS];
		
		for (int i=0; i<TOTAL_BET_LEVELS; i++)
		{
			m_betTrees[i] = new BCBetTree();
		}
		
	}

	public BCBetTree getBetTree(int level) {
		if (level >= TOTAL_BET_LEVELS)
			return null;
		return m_betTrees[level];
	}
	
}
