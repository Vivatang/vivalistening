package com.viva.vivalistening.data;

import java.util.Calendar;
import java.util.Comparator;


public class DayTimeItem implements Comparable<DayTimeItem>
{
	public Calendar m_date = Calendar.getInstance();
	public int m_nTotalTime = 0;
	@Override
	public int compareTo(DayTimeItem another) {
		// TODO Auto-generated method stub
		if(this.m_date.after(another.m_date) == true){
			return -1;
		}
		else{
			return 1;
		}
	}
	
}
