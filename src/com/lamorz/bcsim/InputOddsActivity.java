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
import android.graphics.Typeface;

public class InputOddsActivity extends Activity implements OnClickListener {

	private int m_betLevel;
	private ScrollView f_scrollView;
	private TableLayout f_tableLayout;
	private Button f_buttonSave;
	private Typeface f_typeface;
	private Map<String, BCBetTreeTableRow> m_tableRowMap;
	
	private Button[] m_levelButtons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_odds);
		m_betLevel = 0;
		f_scrollView = (ScrollView) findViewById(R.id.mainScrollView);
		f_tableLayout = (TableLayout) findViewById(R.id.mainTableLayout);
		f_typeface = BCLayoutManager.getInstance().getTypeface();
		
		f_buttonSave = (Button)findViewById(R.id.buttonSave);
		f_buttonSave.setTypeface(f_typeface);
		f_buttonSave.setOnClickListener(this);
		
		m_levelButtons = new Button[BCBetTreeManager.TOTAL_BET_LEVELS];
		
		String resIdStr = "";
		int resId = -1;
		for (int i=0; i<BCBetTreeManager.TOTAL_BET_LEVELS; i++)
		{
			resIdStr = "Button0" + (i+1);
			resId = BCLayoutManager.getResId(resIdStr, "id");
			m_levelButtons[i] = (Button)findViewById(resId);
			m_levelButtons[i].setTypeface(f_typeface);
			m_levelButtons[i].setOnClickListener(this);
		}
		
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
		if (v == f_buttonSave)
			BCBetTreeManager.getInstance().saveBetTree(m_betLevel);
		else
		{
			for (int i=0; i<BCBetTreeManager.TOTAL_BET_LEVELS; i++)
			{
				if (v == m_levelButtons[i])
				{
					m_betLevel = i;
					createTreeUI();
				}
			}
		}
	}

	public void createTreeUI()
	{
		BCBetTreeManager betTreeMgr = BCBetTreeManager.getInstance();
		BCBetTree betTree = betTreeMgr.getBetTree(m_betLevel);
		
		m_tableRowMap = new HashMap<String, BCBetTreeTableRow>();
		f_tableLayout.removeAllViews();
		
		createNodeUI(betTree.getRootNode(), "");
		
	}
	
	public void createNodeUI(BCBetTreeNode betTreeNode, String prevResult)
	{
		if (betTreeNode == null) 
			return;
		int styleId = BCLayoutManager.getResId("textStyle0" + (m_betLevel+1), "style");
		BCBetTreeTableRow tbrow   = new BCBetTreeTableRow(this, betTreeNode, prevResult, styleId);
		 
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
