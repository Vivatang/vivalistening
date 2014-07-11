package com.viva.vivalistening.listeningdaytime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.viva.vivalistening.R;
import com.viva.vivalistening.Utility;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.DayTimeItem;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayTimeActivity extends Activity {

	static int[] m_weekArray = new int[] { 0, R.string.week_1, R.string.week_2,
			R.string.week_3, R.string.week_4, R.string.week_5, R.string.week_6,
			R.string.week_7 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listening_duration);
		initializeActionBar();
		

		String strCount = getResources().getString(R.string.count)
				+ Utility.getDayTime(this,DataManager.m_instance.getTotalTime());
		TextView txt = (TextView) findViewById(R.id.listening_duration_total_time);
		txt.setText(strCount);
		updateDayTimes();
	}

	void updateDayTimes() {

		LinearLayout root = (LinearLayout) findViewById(R.id.listening_duration_list_total_time);
		root.removeAllViews();
		Calendar cal = Calendar.getInstance();
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		int times = 4;
		List<DayTimeItem> items = new ArrayList<DayTimeItem>();
		do {
			int w = cal.get(Calendar.WEEK_OF_YEAR);
			if (w != week) {
				week = w;
				times--;
				if (times < 0) {
					break;
				}
				updateDayTimesInOneWeek(items);
				items.clear();
			}

			DayTimeItem item = new DayTimeItem();
			item.m_date.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
					cal.get(Calendar.DAY_OF_MONTH));
			DayTimeItem it = DataManager.m_instance.getDayTimeItem(item.m_date);
			if (it != null) {
				item.m_nTotalTime = it.m_nTotalTime;
			}
			items.add(item);
			cal.add(Calendar.DAY_OF_YEAR, -1);

		} while (true);

	}

	void updateDayTimesInOneWeek(List<DayTimeItem> items) {

		LinearLayout root = (LinearLayout) findViewById(R.id.listening_duration_list_total_time);
		String strRange = "";
		DayTimeItem item = items.get(0);
		strRange += item.m_date.get(Calendar.YEAR) + "/"
				+ (item.m_date.get(Calendar.MONTH) + 1) + "/"
				+ item.m_date.get(Calendar.DAY_OF_MONTH) + " - ";
		if (items.size() > 1) {
			item = items.get(items.size() - 1);
			strRange += item.m_date.get(Calendar.YEAR) + "/"
					+ (item.m_date.get(Calendar.MONTH) + 1) + "/"
					+ item.m_date.get(Calendar.DAY_OF_MONTH);
		}

		TextView title = new TextView(this);
		title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
		title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
		title.setText(strRange);
		// title.setPadding(0, 10, 0, 10);
		title.setTextColor(Color.BLACK);
		root.addView(title);

		for (int i = 0; i < items.size(); i++) {
			item = items.get(i);
			int index = item.m_date.get(Calendar.DAY_OF_WEEK);
			String strDuration = getResources().getString(m_weekArray[index]);
			strDuration += ": " + Utility.getDayTime(this, item.m_nTotalTime);

			TextView duration = new TextView(this);
			duration.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
			duration.setText(strDuration);
			// duration.setPadding(0, 5, 0, 0);
			duration.setTextColor(Color.BLACK);
			root.addView(duration);
		}
	}
	
	void initializeActionBar(){
		Drawable bd = getResources().getDrawable(R.drawable.gradient_title_bar);		
		getActionBar().setBackgroundDrawable(bd);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		View view = LinearLayout.inflate(getApplicationContext(),R.layout.title_bar_daytime, null);
		ActionBar.LayoutParams layout = new  ActionBar.LayoutParams( ActionBar.LayoutParams.WRAP_CONTENT,  ActionBar.LayoutParams.WRAP_CONTENT);
		layout.gravity = Gravity.CENTER;
		getActionBar().setCustomView(view,layout);
	
		TextView tv = (TextView)view.findViewById(R.id.title_bar_daytime_title);
		tv.setText(R.string.title_bar_total_time_list);
		

	}

	
}
