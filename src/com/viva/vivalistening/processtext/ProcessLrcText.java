package com.viva.vivalistening.processtext;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import com.viva.vivalistening.Defs;

abstract public class ProcessLrcText extends ProcessText{
	
	
	protected BufferedReader m_buf = null;
	
	
	class LrcItem implements Comparable<LrcItem>
	{
		public int m_nTime;
		public String m_strText = null;
		public boolean m_bEnd = false;
		public String m_strTranslation = null;
		@Override
		public int compareTo(LrcItem another) {
			// TODO Auto-generated method stub
			if(m_nTime > another.m_nTime){
				return 1;
			}
			else if(m_nTime < another.m_nTime){
				return -1;
			}
			
			return 0;
		}
	}
	
	@Override
	public JSONArray buildText() {
		// TODO Auto-generated method stub
		JSONArray arr = new JSONArray();
		List<LrcItem> items = read(m_buf);
		try{
//			JSONObject obj = new JSONObject();
//			obj.put(Defs.JSON_TITLE, m_strTitle);
//			arr.put(obj);
			JSONArray section = null;
			for(int i = 0 ; i < items.size() ; i++){
				LrcItem item  = items.get(i);
				if(section == null){
					section = new JSONArray();
				}
				JSONObject obj = new JSONObject();
				section.put(obj);
				obj.put(Defs.JSON_TIME, item.m_nTime);
				obj.put(Defs.JSON_TEXT, item.m_strText);
				if(item.m_bEnd == true || i == items.size() - 1){
					if(item.m_strTranslation != null){
						obj.put(Defs.JSON_TRANSLATION, item.m_strTranslation);
					}
					
					arr.put(section);
					section = null;
				}
			}
			
			
			
		}
		catch(Exception ex){
			
		}
		
		return arr;
	}
	
	List<LrcItem> read(BufferedReader buf){
		List<LrcItem> items = new ArrayList<LrcItem>();
		String strReg = "\\b[\\d{2}:\\d{2}.\\d{2}]\\b";
		Pattern par = Pattern.compile(strReg);
		try{
			String strLine = buf.readLine();
			strLine = strLine.trim();
			while(strLine != null){
				int index = strLine.indexOf(Defs.JSON_TITLE);
				if(index != -1){
					m_strTitle = strLine.substring(index+Defs.JSON_TITLE.length());
				}
				else{
					Matcher matcher = par.matcher(strLine);
					if(matcher.find() == true){
						LrcItem item = createItem(strLine);
						items.add(item);
					}
				}
				
				strLine = buf.readLine();
			}
			
		}
		catch(Exception ex){
			
		}
		
		Collections.sort(items);
		
		return items;
	}
	
	LrcItem createItem(String strLine){
		LrcItem item = new LrcItem();
		String strTime = strLine.substring(1, 3);
		item.m_nTime = Integer.parseInt(strTime)*1000*60;
		strTime = strLine.substring(4,6);
		item.m_nTime += Integer.parseInt(strTime)*1000;
		strTime = strLine.substring(7,9);
		item.m_nTime += Integer.parseInt(strTime)*10;
		
		int end = strLine.indexOf("#");
		if(end != -1){
			item.m_bEnd = true;
			item.m_strText = strLine.substring(10,end);
			if(end != strLine.length() - 1){
				item.m_strTranslation = strLine.substring(end+1);
			}
		}
		else{
			item.m_strText = strLine.substring(10);
		}
		
		item.m_strText += " ";	
		return item;
	}

}
