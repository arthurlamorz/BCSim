package com.lamorz.bcsim;

import android.graphics.Typeface;
import android.content.Context;
import 	java.lang.reflect.Field;
import android.content.res.Resources;

public class BCLayoutManager {

	private Typeface f_typeface;
	private Context f_ctx;
	private static BCLayoutManager m_instance= null;
	
	public BCLayoutManager(Context ctx)
	{
		f_ctx = ctx;
		f_typeface = Typeface.createFromAsset(f_ctx.getAssets(), "fonts/FSEX300.ttf");
	}
	
	public static synchronized BCLayoutManager getInstance(){
    	return m_instance;
    }
	
	public static synchronized BCLayoutManager getInstance(Context ctx){
    	if(null == m_instance){
    		m_instance = new BCLayoutManager(ctx);
    	}
    	return m_instance;
    }

	public static int getResId(String variableName, String defType) {

		Resources r = BCLayoutManager.getInstance().getCtx().getResources();
		int resId = r.getIdentifier(variableName, defType, "com.lamorz.bcsim");
		return resId;
	}
	
	public Typeface getTypeface() {
		return f_typeface;
	}

	public void setTypeface(Typeface typeface) {
		f_typeface = typeface;
	}

	public Context getCtx() {
		return f_ctx;
	}

	public void setCtx(Context ctx) {
		f_ctx = ctx;
	}
	
	
}
