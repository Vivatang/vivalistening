package com.viva.vivalistening.listening;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;


import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.commands.Command;

public class CommandAdjustVolume extends Command{

	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		int x = arg.getInt(Defs.VALUE_X);
		int y = arg.getInt(Defs.VALUE_Y);
		if(Math.abs(y) > Math.abs(x)){
			return;
		}
		
		AudioManager ad = (AudioManager)activity.getActivity().getSystemService(Context.AUDIO_SERVICE);
		activity.getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		if(x > 50 ){
			ad.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		            AudioManager.ADJUST_RAISE,
		            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
		}
		else if(x < -50){
			ad.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		            AudioManager.ADJUST_LOWER,
		            AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
		}
		
	}

}
