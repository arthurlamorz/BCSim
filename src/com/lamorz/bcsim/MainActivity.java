package com.lamorz.bcsim;


import com.lamorz.bcsim.bcstruct.BCHand;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;

import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;

import com.lamorz.bcsim.bcstruct.BCHand;
import com.lamorz.bcsim.bcstruct.BCGame;
import com.lamorz.bcsim.bcstruct.BCRound;
import com.lamorz.bcsim.bcstruct.BCManager;

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

	private Typeface f_typeface;
	private Button f_buttonStart;
	private Button f_buttonOdds;
	private TableLayout f_tableLayoutResult;
	private int m_totalAmount;
	private int m_currentRound;
	private TextView m_textViewGain;
	private TextView m_textViewWin;
	private TextView m_textViewLose;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		f_typeface = BCLayoutManager.getInstance().getTypeface();
		
		f_buttonStart = (Button) findViewById(R.id.buttonStart);
		f_buttonStart.setOnClickListener(this);
		f_buttonStart.setTypeface(f_typeface);
		
		f_buttonOdds = (Button) findViewById(R.id.buttonOdds);
		f_buttonOdds.setOnClickListener(this);
		f_buttonOdds.setTypeface(f_typeface);
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.MainRelativeLayout);
		
		m_textViewGain = (TextView) rl.findViewById(R.id.TextViewGainMain);
		m_textViewGain.setTypeface(f_typeface);
		
		m_textViewLose = (TextView) rl.findViewById(R.id.TextViewLoseMain);
		m_textViewLose.setTypeface(f_typeface);
		
		m_textViewWin = (TextView) rl.findViewById(R.id.TextViewWinMain);
		m_textViewWin.setTypeface(f_typeface);
		
		f_tableLayoutResult = (TableLayout) findViewById(R.id.tableLayoutResult);
		
		m_totalAmount = 0;
		m_currentRound = 0;
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 @Override
     public void onClick(View view)  {
		 
		 if (view == f_buttonStart)
		 {
			 BCManager manager = BCManager.getInstance();
			 manager.reset();
			 f_tableLayoutResult.removeAllViews();
			 m_currentRound = 0;
			 
			 int finalGain = manager.simulate(5000, 72, 12);
			 int oldRound = m_currentRound;
			 m_currentRound = manager.getCurrentRound();
			 
			 m_textViewGain.setText(" "+finalGain);
			 m_textViewWin.setText(" "+manager.getNoOfWin());
			 m_textViewLose.setText(" "+manager.getNoOfLose());
			 
			 
			 for (int roundID = oldRound; roundID < m_currentRound; roundID++)
			 {
				 BCRound round = manager.getRound(roundID);
				 //drawRound(roundID, round);
				 /*
				 try{
				 Thread.sleep(1000);
				 }
				 catch (Exception e)
				 {
					 e.printStackTrace();
				 }
				 */
			 }
		 }
		 else if (view == f_buttonOdds)
		 {
			 Intent intentApp = new Intent(MainActivity.this, InputOddsActivity.class);

			 MainActivity.this.startActivity(intentApp);
		 }
	}
	
	public void drawRound(int roundID, BCRound round)
	{

		m_totalAmount = 0;
		String displayStr = "";
		TableRow tbrow   = new TableRow(this);
		TextView textView = new TextView(this);
		for (int gameID=0; gameID < BCRound.GAMES_PER_ROUND; gameID++)
		 {
	 
			 BCGame game = round.getGame(gameID);
	
			 for (int i=0; i<BCGame.HANDS_PER_GAME; i++)
			 {
				 BCHand hand = game.getHand(i);
				 displayStr += ("|" + String.format("%1$3d", roundID) + "-" + gameID + "-" + hand.getHandID() + "| " + (hand.isWin()?"W":"L") + "   | " + String.format("%1$3d", hand.getGain()) + "  |\n");
				 
			 }
	 
			 //Gson gson = new Gson();
			 //String json = gson.toJson(round);
			 
			 m_totalAmount += game.getTotalGain(); 
			 displayStr += ("|-------+-----+------|\n|Gain" + String.format("%1$3d", game.getTotalGain()) + "|Amount:" + String.format("%1$5d", m_totalAmount) + "|\n|-------+-----+------|\n");
		 }
		
		textView.setText(displayStr);
		textView.setTextAppearance(getApplicationContext(), R.style.textStyle01);
		textView.setTypeface(f_typeface);
		tbrow.addView(textView);
		f_tableLayoutResult.addView(tbrow);
	}
	 
	public Typeface getTypeface() {return f_typeface;}
	

}
