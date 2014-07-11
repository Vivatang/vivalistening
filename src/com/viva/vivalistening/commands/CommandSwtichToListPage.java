//package com.viva.vivalistening.commands;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.view.Menu;
//import android.view.View;
//import android.widget.TextView;
//
//import com.viva.vivalistening.Args;
//import com.viva.vivalistening.Defs;
//import com.viva.vivalistening.R;
//
//public class CommandSwtichToListPage extends Command{
//	
//	
//	
//	public CommandSwtichToListPage(){
//		
//	}
//
//	@Override
//	public void execute(Args arg) {
//		// TODO Auto-generated method stub
//		
//		Activity activity = (Activity)arg.getValueToObject(Defs.VALUE_ACTIVITY);
//		Menu menu = (Menu)arg.getValueToObject(Defs.VALUE_MENU);
//		
//		ActionBar actionBar = activity.getActionBar();
//		actionBar.setHomeButtonEnabled(false);
//		actionBar.setIcon(R.drawable.content);
//		
//		View view = activity.findViewById(R.id.view_listening_items);
//		view.setVisibility(View.VISIBLE);
//		view = activity.findViewById(R.id.view_listening_text);
//		view.setVisibility(View.GONE);
//		view = activity.findViewById(R.id.view_listening_duration);
//		view.setVisibility(View.GONE);
//		
//		
//		TextView tv = (TextView)actionBar.getCustomView().findViewById(R.id.title_bar_text);
//		tv.setText(R.string.title_bar_listening_list);
//		
//		menu.findItem(R.id.menu_refresh).setVisible(true);
//		menu.findItem(R.id.menu_time).setVisible(true);
//		menu.findItem(R.id.menu_translation).setVisible(false);
//		
//		
//		
//	}
//	
//}
