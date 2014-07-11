package com.viva.vivalistening.playaudio;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.viva.vivalistening.Defs;

public class PlayAudioFromLocalFile extends PlayAudio{
	
	public boolean open(Bundle args,Activity activity){
		
		String strPath = args.getString(Defs.VALUE_FILE);
		File file = new File(strPath);
		Uri uri = Uri.fromFile(file);
		MediaPlayer player = MediaPlayer.create(activity.getApplicationContext(), uri);
		if(player == null){
			return false;
		}
		m_player = player;
		initialize();
		return true;
		
	}

}
