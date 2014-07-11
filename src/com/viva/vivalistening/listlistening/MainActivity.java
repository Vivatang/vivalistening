package com.viva.vivalistening.listlistening;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;






import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.INotify;
import com.viva.vivalistening.R;
import com.viva.vivalistening.commands.CommandManager;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;



import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements INotify ,ICommandActivity{
	
	ProgressDialog m_dlg = null;  
	boolean m_bProcessing = false;
	Menu m_menu = null;
	long m_exitTime = 0;
	
	View.OnClickListener m_click = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int id = (Integer)v.getTag();
			CommandManager.m_instance.execute(id, null, MainActivity.this);
			
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeActionBar();
		ListeningItemListView lv = (ListeningItemListView)this.findViewById(R.id.view_listening_items);
		lv.setNotify(this);
		CommandManager.m_instance.execute(R.id.title_bar_main_refresh, null, this);
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
////		MenuInflater inflater = getMenuInflater();
////		inflater.inflate(R.menu.main, menu);
////		m_menu = menu;
//		return true;
//	}

	@Override
	public void onNotify(int id, Bundle arg) {
		// TODO Auto-generated method stub
		CommandManager.m_instance.execute(id, arg, this);
		
	}
	

	
	void initializeActionBar(){
		
		Drawable bd = getResources().getDrawable(R.drawable.gradient_title_bar);		
		getActionBar().setBackgroundDrawable(bd);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		View view = LinearLayout.inflate(getApplicationContext(),R.layout.title_bar_main, null);
		ActionBar.LayoutParams layout = new  ActionBar.LayoutParams( ActionBar.LayoutParams.MATCH_PARENT,  ActionBar.LayoutParams.WRAP_CONTENT);
		layout.gravity = Gravity.CENTER;
		getActionBar().setCustomView(view,layout);
	
		TextView tv = (TextView)view.findViewById(R.id.title_bar_main_title);
		tv.setText(R.string.title_bar_listening_list);
		
		View v = view.findViewById(R.id.title_bar_main_refresh);
		v.setTag(R.id.title_bar_main_refresh);
		v.setOnClickListener(m_click);
		
		v = view.findViewById(R.id.title_bar_main_time);
		v.setTag(R.id.title_bar_main_time);
		v.setOnClickListener(m_click);
		
		
	}
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		if(keyCode == KeyEvent.KEYCODE_BACK){
			
			long time = Calendar.getInstance().getTimeInMillis();
			if(time - m_exitTime > 2000){
				Toast.makeText(this, R.string.exit_prompt, Toast.LENGTH_LONG).show();
			}
			else{
				finish();
				android.os.Process.killProcess(android.os.Process.myPid()); 
			}
			
			m_exitTime = time;
			return true;
		}
		else{
			return super.onKeyDown(keyCode, event);
		}
		
	}
	
//	public boolean onOptionsItemSelected(MenuItem item) {
//		
//		
//		
//		CommandManager.m_instance.execute(item.getItemId(), null, this);
//
//		return super.onOptionsItemSelected(item);
//	}
	
//	public void setPosition(String strPos){
//		int pos = Integer.parseInt(strPos);
//		Message msg = m_handler.obtainMessage(0,pos);
//		m_handler.sendMessage(msg);
//	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void showMask(boolean bShow) {
		// TODO Auto-generated method stub
		m_bProcessing = bShow;
		if(bShow){
			m_dlg = new ProgressDialog(this,AlertDialog.THEME_HOLO_DARK);
			m_dlg.setMessage(getResources().getText(R.string.loading));
			m_dlg.setCancelable(false);
			m_dlg.show();
		}
		else{
			if(m_dlg != null){
				m_dlg.dismiss();
			}
		}
	}

	

	
	protected void onRestart (){
		super.onRestart();
		ListeningItemListView view = (ListeningItemListView)getActivity().findViewById(R.id.view_listening_items);
		view.update(DataManager.m_instance.getListeningArray());
	}
	
	

}
