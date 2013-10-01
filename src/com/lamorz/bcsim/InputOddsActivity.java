package com.lamorz.bcsim;

import com.google.gson.Gson;
import com.lamorz.bcsim.bcstruct.BCBetTree;
import com.lamorz.bcsim.bcstruct.BCBetTreeManager;
import com.lamorz.bcsim.bcstruct.BCBetTreeNode;


import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.ScrollView;

public class InputOddsActivity extends Activity implements OnClickListener {

	private int m_betLevel;
	private ScrollView f_scrollView;
	private TableLayout f_tableLayout;
	private Button f_buttonSave;
	private Map<String, BCBetTreeTableRow> m_tableRowMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_odds);
		m_betLevel = 0;
		f_scrollView = (ScrollView) findViewById(R.id.mainScrollView);
		f_tableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
		f_buttonSave = (Button)findViewById(R.id.buttonSave);
		f_buttonSave.setOnClickListener(this);
		m_tableRowMap = new HashMap<String, BCBetTreeTableRow>();
		
		createTreeUI();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.input_odds, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		BCBetTreeManager.getInstance().saveBetTree(0);
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
		 
		 m_tableRowMap.put(prevResult, tbrow);
		 f_tableLayout.addView(tbrow);
		 
		 createNodeUI(betTreeNode.getWinNode(), prevResult + "W");
		 createNodeUI(betTreeNode.getLoseNode(), prevResult + "L");
		
	}
	
	public BCBetTreeTableRow getConjugateRow(String resultStr)
	{
		if (resultStr == null || resultStr.length() == 0)
			return null;
		
		char lastChar = resultStr.charAt(resultStr.length() - 1);
		String targetStr = null;
		BCBetTreeTableRow conjugateRow = null;
		
		if (lastChar == 'W')
		{
			targetStr = resultStr.substring(0, resultStr.length()-1) + "L";
			conjugateRow = m_tableRowMap.get(targetStr);
			
			return conjugateRow;
			
		}
		
		else if (lastChar == 'L')
		{
			targetStr = resultStr.substring(0, resultStr.length()-1) + "W";
			conjugateRow = m_tableRowMap.get(targetStr);
			
			return conjugateRow;
			
		}
		
		return null;
	}
}
