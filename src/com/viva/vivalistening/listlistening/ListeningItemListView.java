package com.viva.vivalistening.listlistening;

import java.util.List;


import com.viva.vivalistening.Defs;
import com.viva.vivalistening.INotify;
import com.viva.vivalistening.R;
import com.viva.vivalistening.Utility;
import com.viva.vivalistening.data.ListeningItem;

import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;




public class ListeningItemListView extends ListView{
	
	INotify m_notify = null;
	
	class ClickItem implements View.OnClickListener{
		
		String m_id = "";
		
		ClickItem(String id){
			m_id = id;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ListeningItemAdapter extends BaseAdapter{
		
		List<ListeningItem> m_array = null;
		
		public void setListeningItemArray(List<ListeningItem> array){
			m_array = array;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if(m_array == null){
				return 0;
			}
			
			return m_array.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			
			if(m_array == null){
				return null;
			}
			
			return m_array.get(position);
		}

		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if(m_array == null){
				return null;
			}
			
			// TODO Auto-generated method stub
			ListeningItem item = m_array.get(position);
			if(convertView == null){
				convertView = LinearLayout.inflate(getContext(), R.layout.listening_item, null);
				TextView tv = (TextView)convertView.findViewById(R.id.listening_item_title);
				TextPaint paint = tv.getPaint();
				paint.setFakeBoldText(true);
				float fs = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, getContext().getResources().getDisplayMetrics());
				paint.setTextSize(fs);
			}
			
			TextView tv  = (TextView)convertView.findViewById(R.id.listening_item_title);
			tv.setText(item.m_strTitle);
			tv = (TextView)convertView.findViewById(R.id.listening_item_total_time);
			String strTime = Utility.getTotalTime(item.m_nTotalTime);
			tv.setText(strTime);
			tv = (TextView)convertView.findViewById(R.id.listening_item_summary);
			tv.setText(item.m_strSummary);
			tv = (TextView)convertView.findViewById(R.id.listening_item_duration);
			tv.setText(Utility.getTotalTime2(item.m_nDuration));
			
			//convertView.setTag(item.m_id);
			
			convertView.setOnClickListener(new ClickItem(item.m_id) {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(m_notify != null){
						
						Bundle arg = new Bundle();
						arg.putString(Defs.VALUE_ID, m_id);
						m_notify.onNotify(Defs.ID_CLICK_ITEM, arg);
					}
					
				}
			});
			
			return convertView;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}	
	}
	
	ListeningItemAdapter m_adapter = null;
	
	public ListeningItemListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public ListeningItemListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	public ListeningItemListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initialize();
	}
	
	void initialize(){
		m_adapter = new ListeningItemAdapter();
		this.setAdapter(m_adapter);
	}
	
	public void update(List<ListeningItem> array){
		m_adapter.setListeningItemArray(array);
		m_adapter.notifyDataSetChanged();
	}
	
	public void setNotify(INotify notify){
		m_notify = notify;
	}
	
}
