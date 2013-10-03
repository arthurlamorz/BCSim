package com.lamorz.bcsim.bcstruct;

import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileInputStream;
import java.io.FileWriter;
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
			loadBetTree(i);
	}

	public void loadBetTree(int level)
	{
		try{
			 Reader reader = new InputStreamReader(new FileInputStream(Environment.getExternalStorageDirectory().toString() + "//betTree" + level + ".json"), "UTF-8");
		 	 
	            Gson gson = new GsonBuilder().create();
	            m_betTrees[level] = gson.fromJson(reader, BCBetTree.class);
	            
	        }
		 catch (IOException e)
		 {
			 e.printStackTrace();
			 m_betTrees[level] = new BCBetTree();		
		 }
	}
	
	public void saveBetTree(int level)
	{
		Gson gson = new Gson();
		String json = gson.toJson(BCBetTreeManager.getInstance().getBetTree(level));
		
		try {
			 
			FileWriter file = new FileWriter(Environment.getExternalStorageDirectory().toString() + "//betTree" + level +".json", false);
			file.write(json);
			file.flush();
			file.close();
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BCBetTree getBetTree(int level) {
		if (level >= TOTAL_BET_LEVELS || level < 0)
			return null;
		return m_betTrees[level];
	}
	
}
