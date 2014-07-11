package com.viva.vivalistening;


import android.app.Activity;
import android.util.Log;

public class Utility {

	static final boolean m_bOutput = true;

	static public void logInfo(String strLog) {
		if (m_bOutput == false) {
			return;
		}

		Log.i("[VivaListening]", strLog);

	}

	static public void logError(String strErr) {
		if (m_bOutput == false) {
			return;
		}

		Log.e("[VivaListening]", strErr);
	}
	
	static public String getTotalTime2(int time){
		String strTime = "";
		int t = time/1000;
		
		int m = t/60;
		int s = t%60;
		
		if(m<10){
			strTime += "0";
		}
		strTime += m;
		
		strTime += ":";
		if(s<10){
			strTime += "0";
		}
		strTime += s;
		
		return strTime;
	}
	
	static public String getTotalTime(int time){
		
		String strTime = "";
		int t = time/1000;
		int h = t/3600;
		
		t = t%3600;
		int m = t/60;
		int s = t%60;
		
		if(h<10){
			strTime += "0";
		}
		strTime += h;
		
		strTime += ":";
		if(m<10){
			strTime += "0";
		}
		strTime += m;
		
		strTime += ":";
		if(s<10){
			strTime += "0";
		}
		strTime += s;
		
		return strTime;
		
	}
	
	
	static public String getDayTime(Activity activity,int time){
		
		if(time == 0){
			return "N/A";
		}
	
		String strTime = "";
		int t = time/1000;
		int h = t/3600;
		
		t = t%3600;
		int m = t/60;
		int s = t%60;
		
		if(h > 0){
			strTime += h;	
			strTime += activity.getResources().getString(R.string.hour);
		}
		
		if(m > 0){
			strTime += m;	
			strTime += activity.getResources().getString(R.string.min);
		}
		
		if(s > 0){
			strTime += s;
			strTime += activity.getResources().getString(R.string.sec);
		}
		
		
		return strTime;
		
	}
}
