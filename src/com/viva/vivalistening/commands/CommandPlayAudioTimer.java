//package com.viva.vivalistening.commands;
//
//import android.app.Activity;
//import android.webkit.WebView;
//import android.widget.TextView;
//
//import com.viva.vivalistening.Args;
//import com.viva.vivalistening.Defs;
//import com.viva.vivalistening.R;
//import com.viva.vivalistening.Utility;
//import com.viva.vivalistening.data.DataManager;
//import com.viva.vivalistening.data.ListeningItem;
//
//public class CommandPlayAudioTimer extends Command{
//
//	@Override
//	public void execute(Args arg) {
//		// TODO Auto-generated method stub
//		Activity activity = (Activity)arg.getValueToObject(Defs.VALUE_ACTIVITY);
//		int pos = arg.getValueToInt(Defs.VALUE_POSITION);
//		int timer = arg.getValueToInt(Defs.VALUE_TIMER);
//		
//		WebView wv =  (WebView)activity.findViewById(R.id.listening_text);
//		String strID = (String)wv.getTag();
//		
//		ListeningItem item = DataManager.m_instance.getListeningItem(strID);
//		item.m_nDayTime += timer;
//		DataManager.m_instance.addTotalTime(timer);
//		
//		TextView tv = (TextView)activity.findViewById(R.id.listening_text_total_time);
//		tv.setText(Utility.getTotalTime(item.m_nDayTime));
//		
//		item.m_nPos = pos;
//		
//		String strUrl="javascript:"+"set_current_pos("+pos+")";
//		wv.loadUrl(strUrl);	
//	}
//
//}
