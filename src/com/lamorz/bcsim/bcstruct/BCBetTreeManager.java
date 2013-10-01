package com.lamorz.bcsim.bcstruct;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.IOException;

public class BCBetTreeManager {
	
	public static final int TOTAL_BET_LEVELS = 8;
	private BCBetTree[] m_betTrees;
	
	private static BCBetTreeManager m_instance= null;


    public static synchronized BCBetTreeManager getInstance(){
    	if(null == m_instance){
    		m_instance = new BCBetTreeManager();
    	}
    	return m_instance;
    }
	
	public BCBetTreeManager()
	{
		m_betTrees = new BCBetTree[TOTAL_BET_LEVELS];
		
		for (int i=0; i<TOTAL_BET_LEVELS; i++)
		{
			 try{
				 Reader reader = new InputStreamReader(new FileInputStream(Environment.getExternalStorageDirectory().toString() + "//betTree0.json"), "UTF-8");
			 	 
		            Gson gson = new GsonBuilder().create();
		            m_betTrees[i] = gson.fromJson(reader, BCBetTree.class);
		            
		        }
			 catch (IOException e)
			 {
				 e.printStackTrace();
				 m_betTrees[i] = new BCBetTree();		
			 }
		}
	}

	public BCBetTree getBetTree(int level) {
		if (level >= TOTAL_BET_LEVELS)
			return null;
		return m_betTrees[level];
	}
	
}
