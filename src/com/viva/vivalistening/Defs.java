package com.viva.vivalistening;

public class Defs {

	public static final String TAG_ROOT = "vivalistening";
	public static final String TAG_TOTAL_TIME = "totaltime";
	public static final String TAG_LISTENING_INFO = "listeninginfo";
	public static final String TAG_DAY_TIME_INFO = "durationinfo";
	public static final String TAG_ITEM = "item";
	public static final String TAG_LISTENING_ITEM_ID = "id";
	public static final String TAG_LISTENING_ITEM_TITLE = "title";
	public static final String TAG_LISTENING_ITEM_SUMMARY = "summary";
	public static final String TAG_LISTENING_ITEM_TOTAL_TIME = "totaltime";
	public static final String TAG_DAY_TIME_ITEM_YEAR = "year";
	public static final String TAG_DAY_TIME_ITEM_MONTH = "month";
	public static final String TAG_DAY_TIME_ITEM_DAY	= "day";
	public static final String TAG_DAY_TIME_ITEM_TOTAL_TIME = "totaltime";
	public static final String TAG_LISTENING_ITEM_DURATION = "duration";
	
	public static final String TAG_KEY = "key";
	public static final String TAG_PS = "ps";
	public static final String TAG_PRON = "pron";
	public static final String TAG_POS = "pos";
	public static final String TAG_ACCEPTATION = "acceptation";
	public static final String TAG_SENT = "sent";
	public static final String TAG_ORIG = "orig";
	public static final String TAG_TRANS = "trans";
	
	
	
	
	
	public static final String VALUE_FILE = "filepath";
	public static final String VALUE_ACTIVITY = "activity";
	public static final String VALUE_ID = "id";
	public static final String VALUE_MENU = "menu";
	public static final String VALUE_POSITION = "pos";
	public static final String VALUE_TIMER = "timer";
	public static final String VALUE_CMD_ID = "CMD_id";
	public static final String VALUE_X = "x";
	public static final String VALUE_Y = "y";
	public static final String VALUE_TEMP_TOTAL_TIME = "temp_total_time";
	public static final String VALUE_RESULT = "result";
	public static final String VALUE_WORD = "word";
	
	
	public static final String JSON_TITLE = "[title]";
	public static final String JSON_TIME = "time";
	public static final String JSON_TEXT = "text";
	public static final String JSON_TRANSLATION = "translation";
	
	public static int ID_STATRT = 1000;
	public static int ID_PLAY_END = ID_STATRT + 1;
	public static int ID_INITIALIZE = ID_STATRT + 2;
	public static int ID_CLICK_ITEM = ID_STATRT + 3;
	public static int ID_PLAY_AUDIO_TIMER = ID_STATRT + 4;
	public static int ID_SET_POSITION = ID_STATRT + 5;
	public static int ID_LOAD_PAGE = ID_STATRT + 6;
	public static int ID_CALL_RING = ID_STATRT + 7;
	public static int ID_FLING = ID_STATRT + 8;
	public static int ID_REFRESH = ID_STATRT + 9;
	public static int ID_TIME = ID_STATRT + 10;
	public static int ID_SEARCH_WORD = ID_STATRT + 11;
	
	public static String LISTENING_FOLDER = "/mnt/sdcard/vivalistening";
	public static String CONFIG_FILE = "vivalistening.xml";
	
	public static String CLASS_NAME_DETECT = "com.viva.vivalistening.detect.DetectFromLocalFolder";
	public static String CLASS_NAME_SERIALIZATION = "com.viva.vivalistening.data.DataSerializationFromFile";
	public static String CLASS_NAME_PROCESS_TEXT = "com.viva.vivalistening.processtext.ProcessLrcTextFromLocalFile";
	public static String CLASS_NAME_PLAYAUDIO = "com.viva.vivalistening.playaudio.PlayAudioFromLocalFile";
	
	public static int TIMER = 500;
	public static int MAX_DAYTIME = 28;
	
    public static String TRANSLATION_URL = "http://dict-co.iciba.com/api/dictionary.php?key=38E4B706BB50D338F84DABA313E41833&w=";
	
	
}
