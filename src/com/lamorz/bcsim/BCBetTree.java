package com.lamorz.bcsim;


public class BCBetTree {
	
	private BCBetTreeNode m_rootNode;
	public static final int MAX_TREE_LAYERS = 5;

	public BCBetTree() {
		super();
		m_rootNode = new BCBetTreeNode(null, null, 0, 0);
		init(0);
	}
	
	public void init(int layers)
	{
		constructTreeLayer(layers, m_rootNode);
	}
	
	public void constructTreeLayer(int layer, BCBetTreeNode node)
	{
		if (layer ==  MAX_TREE_LAYERS) return;
		
		BCBetTreeNode winNode = new BCBetTreeNode(null, null, 1, layer+1);
		constructTreeLayer(layer+1, winNode);
		BCBetTreeNode loseNode = new BCBetTreeNode(null, null, -1, layer+1);
		constructTreeLayer(layer+1, loseNode);
		node.setWinNode(winNode);
		node.setLoseNode(loseNode);
	}

	public BCBetTreeNode getRootNode() {
		return m_rootNode;
	}

	public void setRootNode(BCBetTreeNode rootNode) {
		m_rootNode = rootNode;
	}

}
