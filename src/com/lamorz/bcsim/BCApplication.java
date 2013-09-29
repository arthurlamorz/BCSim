package com.lamorz.bcsim;

import android.app.Application;

public class BCApplication extends Application {
	
		
	public void onCreate() {
	   
		BCLayoutManager.getInstance(getApplicationContext());
	   
	}
	
}
