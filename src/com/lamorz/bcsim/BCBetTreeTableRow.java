package com.lamorz.bcsim;

import android.content.Context;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.EditText;
import com.lamorz.bcsim.bcstruct.BCBetTreeNode;
import android.graphics.Typeface;

public class BCBetTreeTableRow extends TableRow {

	private TextView m_textView;
	private EditText m_editAmount;
	private BCBetTreeNode m_betTreeNode;
	private Typeface m_typeface;
	private String m_prevResult;
	
	public BCBetTreeTableRow(Context ctx)
	{
		super(ctx);
		initUI(ctx);		
	}
	
	public BCBetTreeTableRow(Context ctx, BCBetTreeNode betTreeNode, String prevResult)
	{
		super(ctx);
		
		initUI(ctx);
		m_betTreeNode = betTreeNode;
		m_prevResult = prevResult;
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

	private void initUI(Context ctx)
	{
		m_typeface = BCLayoutManager.getInstance().getTypeface();
		m_textView = new TextView(ctx);
		
		m_editAmount = new EditText(ctx);
		
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
		 m_textView.setTextAppearance(this.getContext(), R.style.btnStyleRetro);
		 m_textView.setTypeface(m_typeface);
		 
		 m_editAmount.setText(String.valueOf(m_betTreeNode.getAmount()));
		 m_editAmount.setTextAppearance(this.getContext(), R.style.btnStyleRetro);
		 m_editAmount.setTypeface(m_typeface);
		
	}
}
