package com.lamorz.bcsim;


import com.lamorz.bcsim.bcstruct.BCHand;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import android.graphics.Typeface;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;

import com.lamorz.bcsim.bcstruct.BCHand;
import com.lamorz.bcsim.bcstruct.BCGame;
import com.lamorz.bcsim.bcstruct.BCRound;

import com.google.gson.Gson;

public class MainActivity extends Activity implements OnClickListener {

	private Typeface f_typeface;
	private Button f_buttonStart;
	private Button f_buttonOdds;
	private TableLayout f_tableLayoutResult;
	private int m_totalAmount;
	private int m_currentRound;
	
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
			 BCRound round = new BCRound();
			 int gain = round.playAllGames();
		 
			 for (int gameID=0; gameID < BCRound.GAMES_PER_ROUND; gameID++)
			 {
		 
				 BCGame game = round.getGame(gameID);
		
				 for (int i=0; i<BCGame.HANDS_PER_GAME; i++)
				 {
					 TableRow tbrow   = new TableRow(this);
					 BCHand hand = game.getHand(i);
					 TextView text_v1 = new TextView(this);
					 text_v1.setText("|" + String.format("%1$3d", m_currentRound) + "-" + gameID + "-" + hand.getHandID() + "| " + (hand.isWin()?"W":"L") + "   | " + String.format("%1$3d", hand.getGain()) + "  |");
					 text_v1.setTextAppearance(getApplicationContext(), R.style.btnStyleRetro);
					 text_v1.setTypeface(f_typeface);

					 tbrow.addView(text_v1);

					 f_tableLayoutResult.addView(tbrow);
				 }
		 
				 TableRow tbrowEnd = new TableRow(this);
				 TextView text_vEnd = new TextView(this);
				 
				 Gson gson = new Gson();
				 String json = gson.toJson(round);
				 
				 m_totalAmount += game.getTotalGain(); 
				 text_vEnd.setText("|-------+-----+------|\n|Gain" + String.format("%1$3d", game.getTotalGain()) + "|Amount:" + String.format("%1$5d", m_totalAmount) + "|\n|-------+-----+------|");
				 text_vEnd.setTextAppearance(getApplicationContext(), R.style.btnStyleRetro);
				 text_vEnd.setTypeface(f_typeface);
				 tbrowEnd.addView(text_vEnd);
				 f_tableLayoutResult.addView(tbrowEnd);
			 }
			 m_currentRound++;
			 
		 }
		 else if (view == f_buttonOdds)
		 {
			 Intent intentApp = new Intent(MainActivity.this, InputOddsActivity.class);

			 MainActivity.this.startActivity(intentApp);
		 }
	}
	
	public Typeface getTypeface() {return f_typeface;}
	

}
