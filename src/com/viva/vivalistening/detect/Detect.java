package com.viva.vivalistening.detect;



import org.json.JSONArray;
import com.viva.vivalistening.Defs;
import com.viva.vivalistening.Utility;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.processtext.ProcessText;

abstract public class Detect {
	
	public static Detect m_instance = null;
	
	//ProcessText m_processText = null;
	
	abstract String[] detectFiles(String strPath);
	
	public static Detect newInstance(String className) {
		try {
			Class c = Class.forName(className);
			return (Detect)c.newInstance();
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
	
	public void gainListeningItems(String strPath,DataManager dataManager,ProcessText processText){
	
		String[] files = detectFiles(strPath);
		
		if(files == null){
			return;
		}
		
		for(int i = 0 ; i < files.length ; i++){
			String strFile = files[i];
			int pos = strFile.indexOf(".lrc");
			if(pos > 1 && pos == strFile.length() - 4){
				addListeningItem(strPath,strFile, dataManager,processText);
				
			}
		}
		
	}
	
	void addListeningItem(String strFolder,String strFile,DataManager dataManager,ProcessText processText){
		String id = strFile.substring(0, strFile.length() - 4);
		ListeningItem item = dataManager.getListeningItem(id);
		if(item != null){
			item.m_bExit = true;
		}
		else{
			item = new ListeningItem();
			item.m_id = id;
			
			processText.setFile(strFolder + "/" + strFile);
			JSONArray arr = processText.buildText();
			try{
				item.m_strTitle = processText.getTitle();
				JSONArray para = arr.optJSONArray(0);
				item.m_strSummary = para.optJSONObject(0).getString(Defs.JSON_TEXT);
			}
			catch(Exception ex){
				Utility.logError("Parse lrc error");
				return;
			}
			
			item.m_bExit = true;
			dataManager.addListeningItem(item);
		}
	}
	
	
	
}
