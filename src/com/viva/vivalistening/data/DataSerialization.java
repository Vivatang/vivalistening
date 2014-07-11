package com.viva.vivalistening.data;

import android.os.Bundle;



abstract public class DataSerialization {
	
	public static DataSerialization m_instance = null;
	
	public static DataSerialization newInstance(String className) {
		try {
			Class c = Class.forName(className);
			return (DataSerialization)c.newInstance();
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
	
	public abstract void setDataSource(IDataSource dataSource);
	public abstract boolean load(Bundle args);
	public abstract boolean save(Bundle args);
}
