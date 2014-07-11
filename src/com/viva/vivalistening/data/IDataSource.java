package com.viva.vivalistening.data;

public interface IDataSource {
	
	public void setTotalTime(int nTotalTime);
	public int getTotalTime();
	
	public int getDayTimeItemCount();
	public DayTimeItem getDayTimeItem(int index);
	public void addDayTimeItem(DayTimeItem item);
	
	public int getListeningItemCount();
	public ListeningItem getListeningItem(int index);
	public void addListeningItem(ListeningItem item);
}
