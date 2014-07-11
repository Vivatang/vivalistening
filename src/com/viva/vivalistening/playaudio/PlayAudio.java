package com.viva.vivalistening.playaudio;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.INotify;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

abstract public class PlayAudio {
	
	class Timer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Bundle data = new Bundle();
			data.putInt(Defs.VALUE_POSITION,m_player.getCurrentPosition());
			data.putInt(Defs.VALUE_TIMER, Defs.TIMER);
			if(m_notify != null){
				m_notify.onNotify(Defs.ID_PLAY_AUDIO_TIMER, data);
			}
			
			m_timer = new Timer();
			m_handler.postDelayed(m_timer, Defs.TIMER);
		}
		
	}
	
	MediaPlayer m_player = null;
	//int m_pos = 0;
	INotify m_notify = null;
	Runnable m_timer = null;
	Handler m_handler = new Handler();
	
	static public PlayAudio m_instance = null;
	
	static public PlayAudio newInstance(String className){
		try {
			Class c = Class.forName(className);
			return (PlayAudio)c.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public abstract boolean open(Bundle args,Activity activity);
	
	public void close(){
		if(m_player == null){
			return;
		}
		
		if(m_timer != null){
			m_handler.removeCallbacks(m_timer);
		}
		
		m_player.stop();
		m_player.release();
		m_player = null;
	}
	
	public boolean isPlaying(){
		if(m_player == null){
			return false;
		}
		return m_player.isPlaying();
	}
	
	public void play(int nPos){
		if(m_player == null){
			return;
		}
		m_player.seekTo(nPos);
	}
	
	public void pause(){
		if(m_player == null){
			return;
		}
		if(m_timer != null){
			m_handler.removeCallbacks(m_timer);
		}
		m_player.pause();
	}
	
	public int getPosition(){
		
		if(m_player == null){
			return 0;
		}
		
		return m_player.getCurrentPosition();
		
	}
	
	public int getDuration(){
		return m_player.getDuration();
	}
	
	public void setNotify(INotify notify){
		m_notify = notify;
	}
	
	void initialize(){
		if(m_player == null){
			return;
		}
		
		m_player.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
			public void onSeekComplete(MediaPlayer mp) {
				if(m_player != null && m_player.isPlaying() == false){
					m_player.start();
					
					if(m_timer != null){
						m_handler.removeCallbacks(m_timer);
					}
					
					m_timer = new Timer();
					m_handler.postDelayed(m_timer, Defs.TIMER);
					
				}
			}
		});
		
		m_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			public void onCompletion(MediaPlayer mp){
				if(m_notify != null){
					m_notify.onNotify(Defs.ID_PLAY_END,null);
				}
				
				if(m_timer != null){
					m_handler.removeCallbacks(m_timer);
				}
				m_timer = null;
			}
		});
	}
}
