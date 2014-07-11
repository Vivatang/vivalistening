//package com.viva.vivalistening.commands;
//
//import org.json.JSONArray;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.View;
//import android.webkit.WebView;
//import android.widget.TextView;
//
//import com.viva.vivalistening.Args;
//import com.viva.vivalistening.Defs;
//import com.viva.vivalistening.R;
//import com.viva.vivalistening.data.DataManager;
//import com.viva.vivalistening.data.ListeningItem;
//import com.viva.vivalistening.playaudio.PlayAudio;
//
//
//public class CommandSwitchToTextPage extends Command{
//
//
//	public CommandSwitchToTextPage(){
//
//	}
//	@Override
//	public void execute(Args arg) {
//		
//		
//		// TODO Auto-generated method stub
//		assert(arg!=null);
//		
//		String strID = arg.getValueToString(Defs.VALUE_ID);
//		assert(strID != null);
//		
//		ListeningItem item = DataManager.m_instance.getListeningItem(strID);
//		if(item == null){
//			return;
//		}
//		
//		
//		Activity activity = (Activity)arg.getValueToObject(Defs.VALUE_ACTIVITY);
//		Menu menu = (Menu)arg.getValueToObject(Defs.VALUE_MENU);
//		
//		ActionBar actionBar = activity.getActionBar();
//		actionBar.setHomeButtonEnabled(true);
//		actionBar.setIcon(R.drawable.play);
//	
//
//		View view = activity.findViewById(R.id.view_listening_items);
//		view.setVisibility(View.GONE);
//		view = activity.findViewById(R.id.view_listening_text);
//		view.setVisibility(View.VISIBLE);
//		view = activity.findViewById(R.id.view_listening_duration);
//		view.setVisibility(View.GONE);
//		
//		menu.findItem(R.id.menu_refresh).setVisible(false);
//		menu.findItem(R.id.menu_time).setVisible(false);
//		menu.findItem(R.id.menu_translation).setVisible(true);
//		
//		TextView tv = (TextView)actionBar.getCustomView().findViewById(R.id.title_bar_text);
//		tv.setText(item.m_strTitle);
//		
//	
//		WebView wv = (WebView)activity.findViewById(R.id.listening_text);
//		wv.loadUrl("file:///android_asset/listening.html");
//		wv.setTag(strID);
//		
//		CommandManager.m_instance.addCommand(KeyEvent.KEYCODE_BACK, new CommandSwtichToListPage());
//		
//		String strFile = Defs.LISTENING_FOLDER + "/" + item.m_id + ".mp3";
//		
//		Args para = new Args();
//		para.add(Defs.VALUE_FILE, strFile);
//		para.add(Defs.VALUE_ACTIVITY, activity);
//		PlayAudio.m_instance.close();
//		PlayAudio.m_instance.open(para);
//				
//	}
//
//}
