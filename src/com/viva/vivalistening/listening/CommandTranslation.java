package com.viva.vivalistening.listening;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.R;
import com.viva.vivalistening.commands.Command;
import com.viva.vivalistening.listening.WordInfo.SentenceInfo;
import com.viva.vivalistening.listening.WordInfo.WordExplanation;

public class CommandTranslation extends Command{

	ICommandActivity m_activity = null;
	Task m_task = null;
	
	class Task extends AsyncTask<String,Void,WordInfo>{

		@Override
		protected WordInfo doInBackground(String... params) {
			// TODO Auto-generated method stub
			return SearchWord.search(params[0]);
		
		}
		
		@Override
		protected void onPostExecute(WordInfo result) {
			if (result != null){
				setResult(result);
			}
			
			m_task = null;
		}
		
	}
	
	@Override
	public void execute(Bundle arg, ICommandActivity activity) {
		// TODO Auto-generated method stub
		m_activity = activity;
		int id = arg.getInt(Defs.VALUE_CMD_ID);
		if(id == R.id.listening_text_search_btn || id == R.id.listening_text_search_text){
			
			TextView tv = (TextView) m_activity.getActivity().findViewById(
					R.id.listening_text_search_text);
			String word = tv.getText().toString().toLowerCase();

			// hide the soft-keyboard
			View v = m_activity.getActivity().findViewById(
					R.id.listening_text_search_text);

			InputMethodManager imm = (InputMethodManager) v.getContext()
					.getSystemService(Context.INPUT_METHOD_SERVICE);

			if (imm.isActive()) {

				imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

			}

			m_task = new Task();
			m_task.execute(word);
			m_activity.getActivity().findViewById(R.id.listening_text_search).setVisibility(View.GONE);
		}
		else if(id == R.id.title_bar_learning_translation){
			m_activity.getActivity().findViewById(R.id.listenig_search_word).setVisibility(View.GONE);
			m_activity.getActivity().findViewById(R.id.listening_text_search).setVisibility(View.VISIBLE);
		}
		else{
			if(m_task != null){
				m_task.cancel(true);
			}
		}
		
	}
	
	void setResult(WordInfo info){
		
		TextView tx = (TextView)m_activity.getActivity().findViewById(R.id.search_word_word);
		tx.setText(info.word);
		
		String strPS = "|" + info.psUK + "|";
		tx = (TextView)m_activity.getActivity().findViewById(R.id.search_word_uk_ps).findViewById(R.id.search_word_ps);
		tx.setText(strPS);
		
		tx = (TextView)m_activity.getActivity().findViewById(R.id.search_word_uk_ps).findViewById(R.id.search_word_ps_type);
		tx.setText("UK");
		
		strPS = "|" + info.psUS + "|";
		tx = (TextView)m_activity.getActivity().findViewById(R.id.search_word_us_ps).findViewById(R.id.search_word_ps);
		tx.setText(strPS);
		
		tx = (TextView)m_activity.getActivity().findViewById(R.id.search_word_uk_ps).findViewById(R.id.search_word_ps_type);
		tx.setText("US");
		
		
		ViewGroup vg = (ViewGroup) m_activity.getActivity().findViewById(R.id.search_word_exps);
		for(int i = 0 ; i < info.expArray.size() ; i++){
			TextView tv = new TextView( m_activity.getActivity());
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
			WordExplanation exp = info.expArray.get(i);
			tv.setText(exp.type + " " + exp.explanation);
			tv.setGravity(Gravity.CENTER);
			tv.setSingleLine(true);
			tv.setBackgroundColor(Color.RED);
			
			int m = m_activity.getActivity().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, m, 0, 0);
			vg.addView(tv,params);
			
		}
		
		vg = (ViewGroup) m_activity.getActivity().findViewById(R.id.search_word_sents);
		for(int i = 0 ; i < info.sentArray.size() ; i++){
			

			SentenceInfo sents = info.sentArray.get(i);
			ViewGroup vgt = (ViewGroup)LinearLayout.inflate(m_activity.getActivity(), R.layout.view_translation, null);
			
			TextView txt = (TextView)vgt.getChildAt(0);
			String str = sents.org.replaceAll("\n", "");
			txt.setText(str);
			
			txt = (TextView)vgt.getChildAt(1);
			str = sents.translation.replaceAll("\n", "");
			txt.setText(str);
			
			int m = m_activity.getActivity().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, m, 0, 0);
			vg.addView(vgt,params);
			
		}
	
		m_activity.getActivity().findViewById(R.id.listenig_search_word).setVisibility(View.VISIBLE);
				
	}

}
