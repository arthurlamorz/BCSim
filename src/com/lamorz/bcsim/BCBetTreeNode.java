package com.lamorz.bcsim;

public class BCBetTreeNode {
	
	private BCBetTreeNode m_winNode;
	private BCBetTreeNode m_loseNode;
	private int m_amount;
	private int m_totalAmount;
	private int m_layer;
	
	public int getLayer() {
		return m_layer;
	}

	public void setLayer(int layer) {
		m_layer = layer;
	}

	public BCBetTreeNode() {
		super();
		m_winNode = null;
		m_loseNode = null;
		m_amount = 0;
		m_totalAmount = 0;
		m_layer = 0;
	}
	
	public BCBetTreeNode(BCBetTreeNode winNode, BCBetTreeNode loseNode) {
		super();
		m_winNode = winNode;
		m_loseNode = loseNode;
		m_layer = 0;
	}
	public BCBetTreeNode(BCBetTreeNode winNode, BCBetTreeNode loseNode,
			int amount, int layer) {
		super();
		m_winNode = winNode;
		m_loseNode = loseNode;
		m_amount = amount;
		m_totalAmount = 0;
		m_layer = layer;
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
