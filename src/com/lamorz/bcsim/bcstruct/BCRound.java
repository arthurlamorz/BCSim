package com.lamorz.bcsim.bcstruct;


public class BCRound {

	public static final int GAMES_PER_ROUND = 4;
	private BCGame[] m_games;
	
	private int m_currentGame;
	private int m_betLevel;
	private int m_totalGain;
	private int m_gainFromThisRound;
	private boolean m_isReset;
	private BCBetTree m_betTree;
	
	public BCRound()
	{
		init(0);
	}
	
	public BCRound(int gainFromLastRound)
	{
		init(gainFromLastRound);
	}
	
	private void init(int gainFromLastRound)
	{
		m_betTree = new BCBetTree();
		m_games = new BCGame[GAMES_PER_ROUND];
		m_currentGame = 0;
		m_betLevel = 0;
		m_totalGain = gainFromLastRound;
		m_gainFromThisRound = 0;
		m_isReset = false;
	}
	
	public int playAllGames()
	{
		for (m_currentGame=0; m_currentGame < GAMES_PER_ROUND; m_currentGame++)
		{
			int gain = 0;
			BCGame game = new BCGame(m_totalGain);
			m_games[m_currentGame] = game;
			
			gain = game.playAllHands();
			m_gainFromThisRound += gain;
			m_totalGain += gain;
		}
		
		return m_gainFromThisRound;
	}
	
	public boolean isReset() {
		return m_isReset;
	}

	public void setReset(boolean isReset) {
		m_isReset = isReset;
	}

	public int getTotalGain() {
		return m_totalGain;
	}

	public void setTotalGain(int totalGain) {
		m_totalGain = totalGain;
	}

	public BCGame getGame(int gameID)
	{
		if (gameID >= GAMES_PER_ROUND)
			return null;
		
		return m_games[gameID];
	}
	
	public BCGame playSingleGame()
	{
		if (m_currentGame == GAMES_PER_ROUND)
			return null;
		
		BCGame game = new BCGame(1);
		game.playAllHands();
		
		m_games[m_currentGame] = game;
		m_currentGame++;
		
		return game;
	}
	
	public int getBetLevel() {
		return m_betLevel;
	}

	public void setBetLevel(int betLevel) {
		m_betLevel = betLevel;
	}

}
