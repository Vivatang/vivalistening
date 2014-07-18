package com.viva.vivalistening.listening;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.ICommandActivity;
import com.viva.vivalistening.INotify;
import com.viva.vivalistening.R;
import com.viva.vivalistening.Utility;
import com.viva.vivalistening.commands.CommandManager;
import com.viva.vivalistening.data.DataManager;
import com.viva.vivalistening.data.DataSerialization;
import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.playaudio.PlayAudio;

public class ListeningActivity extends Activity implements INotify,
		ICommandActivity {

	String m_id = "";
	GestureDetector m_gd = null;
	ProgressDialog m_dlg = null;
	boolean m_bProcessing = false;
	boolean m_bDoubleClick = false;

	View.OnClickListener m_click = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			if (m_bProcessing == false) {

				int id = (Integer) v.getTag();
				Bundle arg = new Bundle();
				arg.putString(Defs.VALUE_ID, m_id);
				arg.putInt(Defs.VALUE_CMD_ID, id);
				CommandManager.m_instance.execute(id, arg,
						ListeningActivity.this);
			}

			// TODO Auto-generated method stub
			// int id = (Integer)v.getTag();
			// CommandManager.m_instance.execute(id, null,
			// ListeningActivity.this);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listening);

		Bundle data = null;
		if (savedInstanceState != null) {
			data = savedInstanceState;
		} else {
			data = getIntent().getExtras();
		}

		m_id = data.getString(Defs.VALUE_ID);
		ListeningItem item = DataManager.m_instance.getListeningItem(m_id);
		if (savedInstanceState != null) {
			item.m_nPos = data.getInt(Defs.VALUE_POSITION);
			item.m_nTempTotalTime = data.getInt(Defs.VALUE_TEMP_TOTAL_TIME);
		}

		initializeActionBar();
		initializeWebView();
		initializeGesture();
		//initializePsSpinner();
		initializeTabHost();
		intializeViews();
		
		TextView tv = (TextView) findViewById(R.id.listening_text_total_time);
		tv.setText(Utility.getTotalTime(item.m_nTempTotalTime));

		PlayAudio.m_instance.setNotify(this);
	}

	@SuppressLint("JavascriptInterface")
	void initializeWebView() {
		WebView wv = (WebView) findViewById(R.id.listening_text);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setLongClickable(false);
		wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		wv.addJavascriptInterface(this, "activity");
		wv.loadUrl("file:///android_asset/listening.html");
		wv.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				onNotify(Defs.ID_LOAD_PAGE, null);
			}
		});
	}

	void initializeActionBar() {

		Drawable bd = getResources().getDrawable(R.drawable.gradient_title_bar);
		getActionBar().setBackgroundDrawable(bd);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setHomeButtonEnabled(false);
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(false);
		View view = LinearLayout.inflate(getApplicationContext(),
				R.layout.title_bar_listening, null);
		ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
				ActionBar.LayoutParams.MATCH_PARENT,
				ActionBar.LayoutParams.WRAP_CONTENT);
		layout.gravity = Gravity.CENTER;
		getActionBar().setCustomView(view, layout);
		ListeningItem item = DataManager.m_instance.getListeningItem(m_id);
		TextView tv = (TextView) view
				.findViewById(R.id.title_bar_learning_title);
		tv.setText(item.m_strTitle);

		View v = view.findViewById(R.id.title_bar_learning_play);
		v.setTag(R.id.title_bar_learning_play);
		v.setOnClickListener(m_click);

		v = view.findViewById(R.id.title_bar_learning_translation);
		v.setTag(R.id.title_bar_learning_translation);
		v.setOnClickListener(m_click);
		
		v = findViewById(R.id.listening_text_search_btn);
		v.setTag(R.id.listening_text_search_btn);
		v.setOnClickListener(m_click);
		
	}

	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public void showMask(boolean bShow) {
		// TODO Auto-generated method stub
		m_bProcessing = bShow;
		if (bShow) {
			m_dlg = new ProgressDialog(this, AlertDialog.THEME_HOLO_DARK);
			m_dlg.setMessage(getResources().getText(R.string.loading));
			m_dlg.setCancelable(false);
			m_dlg.show();
		} else {
			if (m_dlg != null) {
				m_dlg.dismiss();
			}
		}
	}

	@Override
	public void onNotify(int id, Bundle arg) {
		// TODO Auto-generated method stub
		if (arg == null) {
			arg = new Bundle();
		}
		arg.putString(Defs.VALUE_ID, m_id);
		arg.putInt(Defs.VALUE_CMD_ID, id);
		CommandManager.m_instance.execute(id, arg, this);

	}

	public boolean onOptionsItemSelected(MenuItem item) {

		if (m_bProcessing == false) {
			Bundle arg = new Bundle();
			arg.putString(Defs.VALUE_ID, m_id);
			arg.putInt(Defs.VALUE_CMD_ID, item.getItemId());
			CommandManager.m_instance.execute(item.getItemId(), arg, this);
		}

		return super.onOptionsItemSelected(item);
	}

	public void setPosition(String strPos) {
		int pos = Integer.parseInt(strPos);
		Bundle data = new Bundle();
		data.putInt(Defs.VALUE_POSITION, pos);
		data.putString(Defs.VALUE_ID, m_id);
		data.putInt(Defs.VALUE_CMD_ID, Defs.ID_SET_POSITION);
		CommandManager.m_instance.postExecute(Defs.ID_SET_POSITION, data, this);

	}

	void initializeDetectCall() {
		TelephonyManager telephony = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
		telephony.listen(new PhoneStateListener() {

			public void onCallStateChanged(int state, String incomingNumber) {
				if (TelephonyManager.CALL_STATE_RINGING == state) {
					onNotify(Defs.ID_CALL_RING, null);
				}
				super.onCallStateChanged(state, incomingNumber);
			}

		}, PhoneStateListener.LISTEN_CALL_STATE);
	}

	void initializeGesture() {

		m_gd = new GestureDetector(this,
				new GestureDetector.OnGestureListener() {

					@Override
					public boolean onDown(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						// TODO Auto-generated method stub
						Bundle data = new Bundle();
						data.putInt(Defs.VALUE_X, (int) velocityX);
						data.putInt(Defs.VALUE_Y, (int) velocityY);
						onNotify(Defs.ID_FLING, data);

						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						// TODO Auto-generated method stub
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						// TODO Auto-generated method stub
						return false;
					}

				});

		m_gd.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {

			@Override
			public boolean onDoubleTap(MotionEvent e) {
				// TODO Auto-generated method stub

				onNotify(R.id.title_bar_learning_play, null);

				return false;
			}

			@Override
			public boolean onDoubleTapEvent(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean onSingleTapConfirmed(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

		});

		View v = findViewById(R.id.listening_text);
		v.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return m_gd.onTouchEvent(event);
			}
		});
	}

	protected void onDestroy() {

		PlayAudio.m_instance.close();
		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (m_bProcessing == false) {
			super.onKeyDown(keyCode, event);
		}
		return true;

	}

	protected void onSaveInstanceState(Bundle outState) {
		ListeningItem item = DataManager.m_instance.getListeningItem(m_id);
		outState.putString(Defs.VALUE_ID, m_id);
		outState.putInt(Defs.VALUE_POSITION, item.m_nPos);
		outState.putInt(Defs.VALUE_TEMP_TOTAL_TIME, item.m_nTempTotalTime);

		super.onSaveInstanceState(outState);
	}

	protected void onPause() {
		String strFile = Defs.LISTENING_FOLDER + "/" + Defs.CONFIG_FILE;
		Bundle args = new Bundle();
		args.putString(Defs.VALUE_FILE, strFile);
		DataSerialization.m_instance.save(args);
		super.onPause();
	}


	void initializeTabHost() {

		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		TabWidget tw = tabHost.getTabWidget();
		tw.setDividerDrawable(new ColorDrawable(Color.TRANSPARENT));

		String title = this.getResources().getString(R.string.search_word_exps);
		tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator(title)
				.setContent(R.id.search_word_exps));

		title = this.getResources().getString(R.string.search_word_sents);
		tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator(title)
				.setContent(R.id.search_word_sents));

		for (int i = 0; i < tw.getTabCount(); i++) {
			View v = tw.getChildTabViewAt(i);
			TextView tv = (TextView)v.findViewById(android.R.id.title);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
		}

	}
	
	void intializeViews(){
		
		findViewById(R.id.listening_text_search_text).setTag(R.id.listening_text_search_text);
		findViewById(R.id.listening_text_search_text).setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if( event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){
					m_click.onClick(v);
				}
				return false;
			}
		});
		
	   findViewById(R.id.listenig_search_word).setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			v.setVisibility(View.GONE);
		}
	});

		
	}
	
	
}
