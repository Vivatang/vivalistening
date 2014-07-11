package com.viva.vivalistening.processtext;

import org.json.JSONArray;

abstract public class ProcessText {
	
	public static ProcessText m_instance = null;
	
	String m_strTitle = "";
	
	public static ProcessText newInstance(String className) {
		try {
			Class c = Class.forName(className);
			return (ProcessText)c.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	abstract public JSONArray buildText();
	public abstract void setFile(String strPath);
	
	public String getTitle(){
		return m_strTitle;
	}
}
