package com.lamorz.bcsim.bcstruct;


public class BCRound {

	public static final int GAMES_PER_ROUND = 4;
	private BCGame[] m_games;
	
	private int m_currentGame;
	private int m_betLevel;
	private int m_gainFromLastRound;
	private int m_gainFromThisRound;
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
		m_gainFromLastRound = gainFromLastRound;
		m_gainFromThisRound = 0;
	}
	
	public int playAllGames()
	{
		for (m_currentGame=0; m_currentGame < GAMES_PER_ROUND; m_currentGame++)
		{
			BCGame game = new BCGame(m_betLevel);
			m_games[m_currentGame] = game;
			
			m_gainFromThisRound += game.playAllHands();
		}
		
		return m_gainFromThisRound;
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
