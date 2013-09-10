package com.lamorz.bcsim;


public class BCBetTree {
	
	private BCBetTreeNode m_rootNode;

	public BCBetTree() {
		super();
		m_rootNode = new BCBetTreeNode();
		init();
	}
	
	public void init()
	{
		BCBetTreeNode winNode = new BCBetTreeNode(null, null, 1);
		BCBetTreeNode loseNode = new BCBetTreeNode(null, null, -1);
		m_rootNode.setWinNode(winNode);
		m_rootNode.setLoseNode(loseNode);
		
		
	}
	

}
