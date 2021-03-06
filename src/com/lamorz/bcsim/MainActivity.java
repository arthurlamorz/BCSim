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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.lamorz.bcsim.bcstruct.BCStatistics;
import com.lamorz.bcsim.bcstruct.BCGame;
import com.lamorz.bcsim.bcstruct.BCRound;
import com.lamorz.bcsim.bcstruct.BCManager;

import java.util.Timer;
import java.util.TimerTask;


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
	private Button f_buttonSim;
	private TextView m_textViewMean;
	private TextView m_textViewSD;
	private TextView m_textViewMaxCon;
	private TextView m_textViewMaxTotal;
	
	private BCSimulateThread m_simThread;
	private Timer m_uiTimer;
	
	
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
		
		f_buttonSim = (Button) findViewById(R.id.ButtonSim);
		f_buttonSim.setOnClickListener(this);
		f_buttonSim.setTypeface(f_typeface);
		
		RelativeLayout rl = (RelativeLayout) findViewById(R.id.MainRelativeLayout);
		
		m_textViewGain = (TextView) rl.findViewById(R.id.TextViewGainMain);
		m_textViewGain.setTypeface(f_typeface);
		
		m_textViewLose = (TextView) rl.findViewById(R.id.TextViewLoseMain);
		m_textViewLose.setTypeface(f_typeface);
		
		m_textViewWin = (TextView) rl.findViewById(R.id.TextViewWinMain);
		m_textViewWin.setTypeface(f_typeface);
		
		m_textViewMean = (TextView) rl.findViewById(R.id.TextViewMean);
		m_textViewMean.setTypeface(f_typeface);
		
		m_textViewSD = (TextView) rl.findViewById(R.id.TextViewStDev);
		m_textViewSD.setTypeface(f_typeface);
		
		m_textViewMaxCon = (TextView) rl.findViewById(R.id.TextViewMaxConLost);
		m_textViewMaxCon.setTypeface(f_typeface);
		
		m_textViewMaxTotal = (TextView) rl.findViewById(R.id.TextViewMaxTotalLost);
		m_textViewMaxTotal.setTypeface(f_typeface);
		
		f_tableLayoutResult = (TableLayout) findViewById(R.id.tableLayoutResult);
		
		m_totalAmount = 0;
		m_currentRound = 0;
		
		m_simThread = new BCSimulateThread();
		
		m_uiTimer = new Timer();
		m_uiTimer.schedule(new TimerTask()
			{
		    	public void run()
		    	{
		                updateUI();
		        }
		    },0,250);
		
	}	
	
	public void updateUI()
	{	
			runOnUiThread(new Runnable()
			{
				public void run()
				{
					boolean isSimulating = m_simThread.isRunning();
					if (isSimulating)
					{
						String strSimText = "SIM" + String.format("%1$7d", m_simThread.getCurrentPass());
						m_textViewMean.setText(strSimText);
					}
					BCStatistics stats = BCManager.getInstance().getNewestStats();
					if (stats != null)
					{
						f_tableLayoutResult.removeAllViews();
						m_textViewMean.setText(String.format("%1.3f", stats.getMean()));
						m_textViewSD.setText(String.format("%1.3f", stats.getSd()));	
						m_textViewMaxCon.setText(String.format("%1$1d", +stats.getMaxConsecutiveLost()));
						String dispStr = String.format("%1$1d\n%2$3d\n%3$3d\n%4$5d\n",stats.getLowest(), stats.getMaxDiffFromHigh(), stats.getMaxTotalLost(), stats.getTotalGain());
						dispStr = dispStr + String.format("%1.3f\n%1.3f", stats.getMeanRoundsToTarget(), stats.getSdRoundsToTarget());
						m_textViewMaxTotal.setText(dispStr);
						BCManager.getInstance().setNewestStats(null);
					}
				}
			}
			);
		
	}
 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 /* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_settings)
		{
			Intent intentApp = new Intent(MainActivity.this, SettingsActivity.class);
			MainActivity.this.startActivity(intentApp);
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
     public void onClick(View view)  {
		 
		SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		
		double winRate = Double.parseDouble(SP.getString("win_probability_text", "0.4931756"));
		BCManager.getInstance().setWinRate(winRate);
		
		 if (view == f_buttonStart)
		 {
			boolean isShowDetails = SP.getBoolean("showDetails_checkbox",false);
			int noOfRounds = Integer.parseInt(SP.getString("no_of_rounds_text", "50"));
			int upperLimit = Integer.parseInt(SP.getString("upper_limit_text", "3"));
			int lowerLimit = Integer.parseInt(SP.getString("lower_limit_text", "-3"));
			int haltAmount = Integer.parseInt(SP.getString("halt_amount_text", "-3"));
			int upperHaltAmount = Integer.parseInt(SP.getString("upper_halt_amount_text", "3"));

			BCManager.getInstance().setHaltAmount(haltAmount);
			BCManager.getInstance().setUpperHaltAmount(upperHaltAmount);
			 
			BCManager manager = BCManager.getInstance();
			 manager.reset();
			 f_tableLayoutResult.removeAllViews();
			 m_currentRound = 0;
			 
			 int finalGain = manager.simulate(noOfRounds, upperLimit, lowerLimit);
			 int oldRound = m_currentRound;
			 m_currentRound = manager.getCurrentRound();
			 
			 m_textViewGain.setText(" "+finalGain+"\n "+manager.getDangerCount());
			 m_textViewWin.setText(" "+manager.getNoOfWin());
			 m_textViewLose.setText(" "+manager.getNoOfLose());
			 
			 m_totalAmount = 0;
			 for (int roundID = oldRound; roundID < m_currentRound; roundID++)
			 {
				 BCRound round = manager.getRound(roundID);
				 if (isShowDetails)
					 drawRound(roundID, round);
			 }
		 }
		 else if (view == f_buttonOdds)
		 {
			 Intent intentApp = new Intent(MainActivity.this, InputOddsActivity.class);

			 MainActivity.this.startActivity(intentApp);
		 }
		 else if (view == f_buttonSim)
		 {
			 BCManager manager = BCManager.getInstance();
			 
			 manager.reset();
			 
			 int haltAmount = Integer.parseInt(SP.getString("halt_amount_text", "-8"));
			 int upperHaltAmount = Integer.parseInt(SP.getString("upper_halt_amount_text", "-8"));
			manager.setHaltAmount(haltAmount);
			manager.setUpperHaltAmount(upperHaltAmount);
				
			 m_currentRound = 0;
			 m_totalAmount = 0;
			 m_simThread = new BCSimulateThread(); 
			 m_simThread.start();
			
		 }
	}
	
	public void drawRound(int roundID, BCRound round)
	{

		String displayStr = "";
		TableRow tbrow   = new TableRow(this);
		TextView textView = new TextView(this);
		for (int gameID=0; gameID < BCRound.GAMES_PER_ROUND; gameID++)
		 {
			 BCGame game = round.getGame(gameID);
	
			 for (int i=0; i<BCGame.HANDS_PER_GAME; i++)
			 {
				 BCHand hand = game.getHand(i);
				 displayStr += ("|" + String.format("%1$3d", roundID) + "-" + gameID + "-" + hand.getHandID() + "| " + (hand.isWin()?"W":"L") + "   | " + String.format("%1$5d", hand.getGain()) + "|\n");
				 
			 }
	 
			 //Gson gson = new Gson();
			 //String json = gson.toJson(round);
			 
			 displayStr += ("|-------+-----+------|\n|G:" + String.format("%1$5d", game.getTotalGain()) + "|Amount:" + String.format("%1$5d", game.getPrevGain() + game.getTotalGain()) + "|\n|-------+-----+------|\n");
		 }
		if (round.isReset())
		{
			m_totalAmount += round.getTotalGain(); 
			displayStr += ("|End " + String.format("%1$5d", round.getTotalGain()) + " T:  " + String.format("%1$6d", m_totalAmount) + "|");
		}
		
		textView.setText(displayStr);
		textView.setTextAppearance(getApplicationContext(), R.style.textStyle01);
		textView.setTypeface(f_typeface);
		tbrow.addView(textView);
		f_tableLayoutResult.addView(tbrow);
	}
	 
	public Typeface getTypeface() {return f_typeface;}
	

}
