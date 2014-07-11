package com.viva.vivalistening.commands;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;

public class CommandManager {
	
	Map<Integer, Command> m_commandMap = new HashMap<Integer, Command>();
	
	Handler m_handler = new Handler(){
	public void handleMessage(Message msg){
		int id = msg.what;
		ICommandActivity activity =  (ICommandActivity)msg.obj;
		Bundle data  = msg.getData();
		execute(id,data,activity);
		
	}
};

	public static CommandManager m_instance = new CommandManager();

	public void execute(int nID, Bundle args, ICommandActivity activity) {
		if (m_commandMap.containsKey(nID) == false) {
			return;
		}
		m_commandMap.get(nID).execute(args,activity);
	}
	
	public void postExecute(int nID, Bundle args, ICommandActivity activity){
		Message msg = m_handler.obtainMessage(nID,activity);
	    msg.setData(args);
	    msg.setTarget(m_handler);
	    msg.sendToTarget();
	}
	
	

	public void addCommand(int nID, Command cmd) {
		m_commandMap.put(nID, cmd);
	}

	public void removeCommand(int nID){
		m_commandMap.remove(nID);
	}
	
	public void clear() {
		m_commandMap.clear();
	}
}
