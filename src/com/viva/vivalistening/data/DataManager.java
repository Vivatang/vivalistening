package com.viva.vivalistening.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import com.viva.vivalistening.Defs;


public class DataManager implements IDataSource{
	int m_nTotalTime = 0;
	List<DayTimeItem> m_dayTimeArray = new ArrayList<DayTimeItem>();
	List<ListeningItem> m_listeningArray = new ArrayList<ListeningItem>();
	
	public static DataManager m_instance = new DataManager();

	public int getTotalTime(){
		return m_nTotalTime;
	}
	
	public void setTotalTime(int nTotalTime){
		m_nTotalTime = nTotalTime;
	}
	
	public void addTotalTime(int millSecs){
		m_nTotalTime += millSecs;
		Calendar date = Calendar.getInstance();
		DayTimeItem item = getDayTimeItem(date);
		if(item != null){
			item.m_nTotalTime += millSecs;
		}
		else{
			item = new DayTimeItem();
			item.m_date = date;
			item.m_nTotalTime = millSecs;
			addDayTimeItem(item);
		}
	}
	
	public int getDayTimeItemCount(){
		return m_dayTimeArray.size();
	}
	
	public DayTimeItem getDayTimeItem(int index){
		if(index >= m_dayTimeArray.size()){
			return null;
		}
		return m_dayTimeArray.get(index);
	}
	
	public DayTimeItem getDayTimeItem(Calendar date){
		for(int i = 0 ; i < m_dayTimeArray.size() ; i++){
			DayTimeItem item = m_dayTimeArray.get(i);
			if(date.get(Calendar.YEAR) == item.m_date.get(Calendar.YEAR) && 
					date.get(Calendar.MONTH) == item.m_date.get(Calendar.MONTH) &&
					date.get(Calendar.DAY_OF_MONTH) == item.m_date.get(Calendar.DAY_OF_MONTH)){
				
				return item;
				
			}
		}
		
		return null;
	}
	
	
	public void addDayTimeItem(DayTimeItem item){
		
		DayTimeItem it = getDayTimeItem(item.m_date);
		if(it != null){
			it.m_nTotalTime = item.m_nTotalTime;
		}else{
			m_dayTimeArray.add(item);
			Collections.sort(m_dayTimeArray);
			if(m_dayTimeArray.size() > Defs.MAX_DAYTIME){
				m_dayTimeArray.remove(m_dayTimeArray.size() - 1);
			}
		}	
	}
	
	public int getListeningItemCount(){
		return m_listeningArray.size();
	}
	
	public ListeningItem getListeningItem(int index){
		if(index >= m_listeningArray.size()){
			return null;
		}
		return m_listeningArray.get(index);
	}
	
	public ListeningItem getListeningItem(String id){
		for(int i = 0 ; i < m_listeningArray.size() ; i++){
			ListeningItem item = m_listeningArray.get(i);
			if(item.m_id.compareTo(id) == 0){
				return item;
			}
		}
		
		return null;
	}
	
	
	public void addListeningItem(ListeningItem item){
		ListeningItem it = getListeningItem(item.m_id);
		if(it != null){
			it.m_nTempTotalTime = item.m_nTempTotalTime;
			it.m_nTotalTime = item.m_nTotalTime;
			it.m_strSummary = item.m_strSummary;
			it.m_strTitle = item.m_strTitle;
		}
		else{
			m_listeningArray.add(item);
		}
		
	}
	
	public void moveToTop(String id){
		ListeningItem item1 = getListeningItem(id);
		ListeningItem item2 = getListeningItem(0);
		if(item1 == null || item2 == null){
			return;
		}
		if(item1.m_id.compareToIgnoreCase(item2.m_id) == 0){
			return;
		}
		
		for(int i = 0; i < m_listeningArray.size() ; i++){
			ListeningItem item = m_listeningArray.get(i);
			if(item.m_id.compareToIgnoreCase(item1.m_id) == 0){
				m_listeningArray.remove(i);
				break;
			}
		}
		
		m_listeningArray.add(0, item1);
	}
	
	public List<ListeningItem> getListeningArray(){
		return this.m_listeningArray;
	}
	
	
	
}
