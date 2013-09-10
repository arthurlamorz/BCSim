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


public class MainActivity extends Activity implements OnClickListener {

	private Typeface f_typeface;
	private Button f_buttonStart;
	private TableLayout f_tableLayoutResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		f_typeface = Typeface.createFromAsset(getAssets(), "fonts/FSEX300.ttf");
		
		f_buttonStart = (Button) findViewById(R.id.buttonStart);
		f_buttonStart.setOnClickListener(this);
		
		f_tableLayoutResult = (TableLayout) findViewById(R.id.tableLayoutResult);
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	 @Override
     public void onClick(View view)  {
		 
		 for (int i=0; i<5; i++)
		 {
			 BCHand hand = new BCHand();
			 hand.play(i, 1);
			 TableRow tbrow   = new TableRow(this);

			 
		      TextView text_v1 = new TextView(this);
		      text_v1.setText("| " + hand.getHandID() + "     | " + (hand.isWin()?"W":"L") + "   | " + String.format("%1$3d", hand.getGain()) + "  |");
		      text_v1.setTextAppearance(getApplicationContext(), R.style.btnStyleRetro);
		      text_v1.setTypeface(f_typeface);

		      tbrow.addView(text_v1);
		     

		      f_tableLayoutResult.addView(tbrow);
		 }
		 
		 TableRow tbrowEnd = new TableRow(this);
		 TextView text_vEnd = new TextView(this);
	     text_vEnd.setText("|-------+-----+------|");
	     text_vEnd.setTextAppearance(getApplicationContext(), R.style.btnStyleRetro);
	     text_vEnd.setTypeface(f_typeface);
	     tbrowEnd.addView(text_vEnd);
	     f_tableLayoutResult.addView(tbrowEnd);
     }
	
	public Typeface getTypeface() {return f_typeface;}
	

}
