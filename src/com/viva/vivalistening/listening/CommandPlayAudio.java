package com.viva.vivalistening.listening;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;


import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.INotify;
import com.viva.vivalistening.R;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.playaudio.PlayAudio;

public class CommandPlayAudio extends Command{
	
	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		int cmdID = arg.getInt(Defs.VALUE_CMD_ID);
		String id = arg.getString(Defs.VALUE_ID);
		ListeningItem item = DataManager.m_instance.getListeningItem(id);
		ImageView v = (ImageView)activity.getActivity().getActionBar().getCustomView().findViewById(R.id.title_bar_learning_play);
		if(cmdID == Defs.ID_PLAY_END){
			item.m_nPos = 0;
			v.setImageResource(R.drawable.play);
			//activity.getActivity().getActionBar().setIcon(R.drawable.play);
		}
		else if((cmdID == R.id.title_bar_learning_play && PlayAudio.m_instance.isPlaying() == true) || cmdID == Defs.ID_CALL_RING){
			PlayAudio.m_instance.pause();
			v.setImageResource(R.drawable.play);
			//activity.getActivity().getActionBar().setIcon(R.drawable.play);
		}
		else{
			if(cmdID == Defs.ID_SET_POSITION){
				item.m_nPos = arg.getInt(Defs.VALUE_POSITION);
			}
			PlayAudio.m_instance.play(item.m_nPos);
			//activity.getActivity().getActionBar().setIcon(R.drawable.pause);
			v.setImageResource(R.drawable.pause);
			DataManager.m_instance.moveToTop(id);
		}
		
	}
	
	

}
