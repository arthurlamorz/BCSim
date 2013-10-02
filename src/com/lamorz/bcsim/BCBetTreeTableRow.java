package com.lamorz.bcsim;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;
import com.lamorz.bcsim.bcstruct.BCBetTreeNode;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputType;
import android.view.Gravity;

public class BCBetTreeTableRow extends TableRow implements TextWatcher {

	private TextView m_textView;
	private EditText m_editAmount;
	private BCBetTreeNode m_betTreeNode;
	private Typeface m_typeface;
	private int m_styleId;
	
	private String m_prevResult;
	
	public BCBetTreeTableRow(Context ctx)
	{
		super(ctx);
		initUI(ctx);		
	}
	
	public BCBetTreeTableRow(Context ctx, BCBetTreeNode betTreeNode, String prevResult, int styleId)
	{
		super(ctx);
		
		initUI(ctx);
		m_betTreeNode = betTreeNode;
		m_prevResult = prevResult;
		m_styleId = styleId;
		constructRow();
	}
	
	public TextView getTextView() {
		return m_textView;
	}

	public void setTextView(TextView textView) {
		m_textView = textView;
	}

	public EditText getEditAmount() {
		return m_editAmount;
	}

	public void setEditAmount(EditText editAmount) {
		m_editAmount = editAmount;
	}

	public BCBetTreeNode getBetTreeNode() {
		return m_betTreeNode;
	}

	public void setBetTreeNode(BCBetTreeNode betTreeNode) {
		m_betTreeNode = betTreeNode;
	}

	public String getPrevResult() {
		return m_prevResult;
	}

	public void setPrevResult(String prevResult) {
		m_prevResult = prevResult;
	}

	
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
		final StringBuilder sb = new StringBuilder(s.length());
		sb.append(s);
		String amountStr = sb.toString();
		
		int amount = 0;
		try { 
	        amount = Integer.parseInt(amountStr); 
	    } catch(NumberFormatException e) { 
	        return; 
	    }
		
		m_betTreeNode.setAmount(amount);
		InputOddsActivity activity = (InputOddsActivity) this.getContext();
		BCBetTreeTableRow conjugateRow = activity.getConjugateRow(m_prevResult);
		
		if (conjugateRow != null)
		{
			String conjugateAmountStr = conjugateRow.getEditAmount().getText().toString();
			try { 
		        if(Integer.parseInt(conjugateAmountStr) != -amount)
		        {
		        	conjugateRow.getBetTreeNode().setAmount(-amount);
		        	conjugateRow.getEditAmount().setText(Integer.toString(-amount));
		        }
		    } catch(NumberFormatException e) { 
		        return; 
		    }	
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		
	}
	
	private void initUI(Context ctx)
	{
		m_typeface = BCLayoutManager.getInstance().getTypeface();
		m_textView = new TextView(ctx);
		
		m_editAmount = new EditText(ctx);
		m_editAmount.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		m_editAmount.addTextChangedListener(this);
		
		this.addView(m_textView);
		this.addView(m_editAmount);
	}
	
	public void constructRow()
	{
		String layerText = "|";
		 for (int i=0; i<m_betTreeNode.getLayer(); i++)
			 layerText += "--";
		 layerText += " " + m_prevResult;
		 m_textView.setText(layerText);
		 m_textView.setTextAppearance(this.getContext(), m_styleId);
		 m_textView.setTypeface(m_typeface);
		 
		 m_editAmount.setText(String.valueOf(m_betTreeNode.getAmount()));
		 m_editAmount.setTextAppearance(this.getContext(), m_styleId);
		 int height = Math.round(m_editAmount.getTextSize());
		 m_editAmount.setHeight(height*2);
		 m_editAmount.setGravity(Gravity.BOTTOM);
		 m_editAmount.setBackgroundColor(0);
		 m_editAmount.setTypeface(m_typeface);
		
	}
}
