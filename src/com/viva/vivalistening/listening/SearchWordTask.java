package com.viva.vivalistening.listening;

import java.io.InputStream;

import android.os.AsyncTask;
import android.os.Bundle;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.INotify;

/*
 * Run the request of the gain word info in the background
 * 
 * @author Andrei
 * 
 * @since 1.1
 */
public class SearchWordTask extends AsyncTask<String, Void, Void> {

	//notify the search result
	INotify notify = null;
	
	//the search result
	WordInfo info = null;
	
	public SearchWordTask(INotify notify){
		this.notify = notify;
	}
	
	public WordInfo getWordInfo(){
		return this.info;
	}
	
	
	/*
	 * send the search request and process the response to be WordInfo
	 * object
	 * 
	 * @return if the return is true, it mean correctly gain the wordInfo
	 * otherwise, don't gain it
	 */
	@Override
	protected Void doInBackground(String... params) {
		
		this.info = SearchWord.search(params[0]);
		return null;
		
	}

	/*
	 * notify the search result to the user
	 * 
	 * @param result the search result . If the result is true, it will
	 * notify user to gain the word info
	 */
	@Override
	protected void onPostExecute(Void result) {
		
		Bundle data = new Bundle();
		if (this.info == null) {
			data.putBoolean(Defs.VALUE_RESULT, false);
		}
		else{
			data.putBoolean(Defs.VALUE_RESULT, true);
		}
		
		this.notify.onNotify(Defs.ID_SEARCH_WORD, data);
	}

}
