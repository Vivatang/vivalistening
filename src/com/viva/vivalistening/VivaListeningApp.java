package com.viva.vivalistening;

import java.io.File;

import com.viva.vivalistening.commands.*;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.DataSerialization;
import com.viva.vivalistening.detect.Detect;
import com.viva.vivalistening.listening.CommandAdjustVolume;
import com.viva.vivalistening.listening.CommandPlayAudio;
import com.viva.vivalistening.listening.CommandTranslation;
import com.viva.vivalistening.listening.NotifyListeningTimer;
import com.viva.vivalistening.listening.NotifyLoadPage;
import com.viva.vivalistening.listlistening.CommandRefresh;
import com.viva.vivalistening.listlistening.CommandSwitchToDayTimePage;
import com.viva.vivalistening.listlistening.NotifyClickListeningItem;
import com.viva.vivalistening.playaudio.PlayAudio;
import com.viva.vivalistening.processtext.ProcessText;

import android.app.Application;
import android.os.Bundle;
import android.os.Environment;

public class VivaListeningApp extends Application {
	public void onCreate (){
		initializeFolder();
		ProcessText.m_instance = ProcessText.newInstance(Defs.CLASS_NAME_PROCESS_TEXT);
		PlayAudio.m_instance = PlayAudio.newInstance(Defs.CLASS_NAME_PLAYAUDIO);
		Detect.m_instance = Detect.newInstance(Defs.CLASS_NAME_DETECT);
		DataSerialization.m_instance = DataSerialization.newInstance(Defs.CLASS_NAME_SERIALIZATION);;
		initializeCommandManager();
		initializeDataManager();
	}
	
	void initializeDataManager(){
//		File file = Environment.getExternalStorageDirectory();
//		Defs.LISTENING_FOLDER = file.getAbsolutePath();
		String strFile = Defs.LISTENING_FOLDER + "/" + Defs.CONFIG_FILE;
		DataSerialization.m_instance.setDataSource(DataManager.m_instance);
		Bundle args = new Bundle();
		args.putString(Defs.VALUE_FILE, strFile);
		DataSerialization.m_instance.load(args);
	}
	
	void initializeCommandManager(){
		
		Command cmd = new CommandRefresh();
		CommandManager.m_instance.addCommand(R.id.title_bar_main_refresh , cmd);
		
		cmd = new NotifyClickListeningItem();
		CommandManager.m_instance.addCommand(Defs.ID_CLICK_ITEM,cmd);
		
		cmd = new CommandPlayAudio();
		CommandManager.m_instance.addCommand(Defs.ID_SET_POSITION,cmd);
		CommandManager.m_instance.addCommand(Defs.ID_PLAY_END,cmd);
		CommandManager.m_instance.addCommand(R.id.title_bar_learning_play,cmd);
		CommandManager.m_instance.addCommand(Defs.ID_CALL_RING,cmd);
		
		cmd = new NotifyListeningTimer();
		CommandManager.m_instance.addCommand(Defs.ID_PLAY_AUDIO_TIMER, cmd);
		
		cmd = new NotifyLoadPage();
		CommandManager.m_instance.addCommand(Defs.ID_LOAD_PAGE, cmd);
		
		cmd = new CommandAdjustVolume();
		CommandManager.m_instance.addCommand(Defs.ID_FLING, cmd);
		
		cmd = new CommandSwitchToDayTimePage();
		CommandManager.m_instance.addCommand(R.id.title_bar_main_time, cmd);
		
		cmd = new CommandTranslation();
		CommandManager.m_instance.addCommand(R.id.title_bar_learning_translation, cmd);
		CommandManager.m_instance.addCommand(R.id.listening_text_search_btn, cmd);
		CommandManager.m_instance.addCommand(R.id.listening_text_search_text, cmd);
		
		
	}
	
	void initializeFolder(){
		
		File file = Environment.getExternalStorageDirectory();
		Defs.LISTENING_FOLDER = file.getAbsolutePath() + "/vivalistening";
		file = new File(Defs.LISTENING_FOLDER);
		if(file.exists() == false){
			file.mkdir();
		}
	}
}
