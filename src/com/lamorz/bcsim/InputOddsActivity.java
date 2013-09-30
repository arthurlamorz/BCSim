package com.lamorz.bcsim;

import com.lamorz.bcsim.bcstruct.BCBetTree;
import com.lamorz.bcsim.bcstruct.BCBetTreeManager;
import com.lamorz.bcsim.bcstruct.BCBetTreeNode;
import com.lamorz.bcsim.bcstruct.BCHand;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TableLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;
import android.graphics.Typeface;


public class InputOddsActivity extends Activity {

	private int m_betLevel;
	private ScrollView f_scrollView;
	private TableLayout f_tableLayout;
	private Typeface f_typeface; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_odds);
		m_betLevel = 0;
		f_scrollView = (ScrollView) findViewById(R.id.mainScrollView);
		f_tableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
		f_typeface = BCLayoutManager.getInstance().getTypeface();
		createTreeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input_odds, menu);
		return true;
	}

	public void createTreeUI()
	{
		BCBetTreeManager betTreeMgr = BCBetTreeManager.getInstance();
		BCBetTree betTree = betTreeMgr.getBetTree(m_betLevel);
		
		createNodeUI(betTree.getRootNode(), "");
		
	}
	
	public void createNodeUI(BCBetTreeNode betTreeNode, String prevResult)
	{
		if (betTreeNode == null) 
			return;
		
		BCBetTreeTableRow tbrow   = new BCBetTreeTableRow(this, betTreeNode, prevResult);
		 
		 
		 f_tableLayout.addView(tbrow);
		 
		 createNodeUI(betTreeNode.getWinNode(), prevResult + "W");
		 createNodeUI(betTreeNode.getLoseNode(), prevResult + "L");
		
	}
}
