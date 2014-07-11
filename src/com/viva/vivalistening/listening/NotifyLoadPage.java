package com.viva.vivalistening.listening;

import android.os.AsyncTask;
import android.os.Bundle;
import android.webkit.WebView;

import org.json.JSONArray;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.INotify;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.playaudio.PlayAudio;
import com.viva.vivalistening.processtext.ProcessText;
import com.viva.vivalistening.R;

public class NotifyLoadPage extends Command{
	
	String m_id = "";
	ICommandActivity m_activity = null;
	
	
	class Task extends AsyncTask<Void,Void,String>{

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			
			String strFile = Defs.LISTENING_FOLDER + "/" + m_id + ".lrc";
			//ProcessText text = ProcessText.newInstance(Defs.CLASS_NAME_PROCESS_TEXT);
			ProcessText.m_instance.setFile(strFile);
			JSONArray arr = ProcessText.m_instance.buildText();
			String strUrl="javascript:"+"buildText("+arr.toString()+")";
			
			strFile = Defs.LISTENING_FOLDER + "/" + m_id + ".mp3";
			Bundle arg = new Bundle();
			arg.putString(Defs.VALUE_FILE, strFile);	
			PlayAudio.m_instance.open(arg, m_activity.getActivity());
			
			return strUrl;
		}
		
		 protected void onPostExecute(String ret){
			 WebView view = (WebView)m_activity.getActivity().findViewById(R.id.listening_text);
			 view.loadUrl(ret);
			 
			 ListeningItem item = DataManager.m_instance.getListeningItem(m_id);
			 if(item.m_nPos > -1){
				 String strUrl="javascript:"+"set_current_pos("+item.m_nPos+")";
				 view.loadUrl(strUrl);
			 }
			 m_activity.showMask(false);
			 
			 
		 }
		
	}


	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		m_id = arg.getString(Defs.VALUE_ID);
		m_activity = activity;
		activity.showMask(true);
		
		new Task().execute();
		
	}

	

}
