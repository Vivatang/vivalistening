package com.viva.vivalistening.listlistening;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;


import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.R;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.detect.Detect;
import com.viva.vivalistening.playaudio.PlayAudio;
import com.viva.vivalistening.processtext.ProcessText;

public class CommandRefresh extends Command{

	//String m_strFile = null;
	
	ICommandActivity m_activity = null;
	
	class Task extends AsyncTask<Void,Void,List<ListeningItem>>{

		@Override
		protected List<ListeningItem> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//ProcessText pt = ProcessText.newInstance(Defs.CLASS_NAME_PROCESS_TEXT);
			ProcessText.m_instance.setFile(Defs.LISTENING_FOLDER + "/" + Defs.CONFIG_FILE);
			
			for(int i = 0 ;i < DataManager.m_instance.getListeningItemCount() ; i++){
				ListeningItem item = DataManager.m_instance.getListeningItem(i);
				item.m_bExit = false;
			}
			
			Detect.m_instance.gainListeningItems(Defs.LISTENING_FOLDER, DataManager.m_instance,ProcessText.m_instance);
			
			List<ListeningItem> list = new ArrayList<ListeningItem>();
			for(int i = 0 ;i < DataManager.m_instance.getListeningItemCount() ; i++){
				ListeningItem item = DataManager.m_instance.getListeningItem(i);
				if(item.m_bExit == true){
					if(item.m_nDuration == 0){
						Bundle args = new Bundle();
						args.putString(Defs.VALUE_FILE, Defs.LISTENING_FOLDER + "/" + item.m_id + ".mp3");
						PlayAudio.m_instance.open(args, m_activity.getActivity());
						item.m_nDuration = PlayAudio.m_instance.getDuration();
					}
					
					list.add(item);
				}
			}
			
			return list;
		}
		
		protected void onPostExecute(List<ListeningItem> list){
			ListeningItemListView view = (ListeningItemListView)m_activity.getActivity().findViewById(R.id.view_listening_items);
			view.update(list);
			m_activity.showMask(false);
		}
		
	}
	
	
	
	public void execute(Bundle arg, ICommandActivity activity){
		m_activity = activity;
		activity.showMask(true);
		Task task = new Task();
		task.execute();
		
	}
	
	

}
