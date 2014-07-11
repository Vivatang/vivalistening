package com.viva.vivalistening.detect;

import java.io.File;

import com.viva.vivalistening.processtext.ProcessLrcTextFromLocalFile;

public class DetectFromLocalFolder extends Detect{

	@Override
	String[] detectFiles(String strFolder) {
		// TODO Auto-generated method stub
		File folder = new File(strFolder);
		String[] files = folder.list();
		return files;
	}

}
