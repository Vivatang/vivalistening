package com.viva.vivalistening.listening;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.R;
import com.viva.vivalistening.Utility;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;

public class NotifyListeningTimer extends Command{

	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		String id = arg.getString(Defs.VALUE_ID);
		int pos = arg.getInt(Defs.VALUE_POSITION);
		int timer = arg.getInt(Defs.VALUE_TIMER);
		
		DataManager.m_instance.addTotalTime(timer);
		
		ListeningItem item = DataManager.m_instance.getListeningItem(id);
		item.m_nPos = pos;
		item.m_nTotalTime += timer;
		item.m_nTempTotalTime += timer;
		
		TextView tv = (TextView)activity.getActivity().findViewById(R.id.listening_text_total_time);
		tv.setText(Utility.getTotalTime(item.m_nTempTotalTime));
		
		String strUrl="javascript:"+"set_current_pos("+pos+")";
		WebView wv =  (WebView)activity.getActivity().findViewById(R.id.listening_text);
		wv.loadUrl(strUrl);	
	}

}
