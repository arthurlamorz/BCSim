package com.lamorz.bcsim;

public class BCBetTreeNode {
	
	private BCBetTreeNode m_winNode;
	private BCBetTreeNode m_loseNode;
	private int m_amount;
	private int m_totalAmount;
	
	public BCBetTreeNode() {
		super();
		m_winNode = null;
		m_loseNode = null;
		m_amount = 0;
		m_totalAmount = 0;
	}
	
	public BCBetTreeNode(BCBetTreeNode winNode, BCBetTreeNode loseNode) {
		super();
		m_winNode = winNode;
		m_loseNode = loseNode;
	}
	public BCBetTreeNode(BCBetTreeNode winNode, BCBetTreeNode loseNode,
			int amount) {
		super();
		m_winNode = winNode;
		m_loseNode = loseNode;
		m_amount = amount;
		m_totalAmount = 0;
	}
	// Generated getsetters
	/**
	 * @return the totalAmount
	 */
	public int getTotalAmount() {
		return m_totalAmount;
	}
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(int totalAmount) {
		m_totalAmount = totalAmount;
	}
	/**
	 * @return the amount
	 */
	public int getAmount() {
		return m_amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		m_amount = amount;
	}
	/**
	 * @return the winNode
	 */
	public BCBetTreeNode getWinNode() {
		return m_winNode;
	}
	/**
	 * @param winNode the winNode to set
	 */
	public void setWinNode(BCBetTreeNode winNode) {
		m_winNode = winNode;
	}
	/**
	 * @return the loseNode
	 */
	public BCBetTreeNode getLoseNode() {
		return m_loseNode;
	}
	/**
	 * @param loseNode the loseNode to set
	 */
	public void setLoseNode(BCBetTreeNode loseNode) {
		m_loseNode = loseNode;
	}
	
	
	
}
