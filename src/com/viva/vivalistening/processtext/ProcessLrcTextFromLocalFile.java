package com.viva.vivalistening.processtext;

import java.io.BufferedReader;
import java.io.FileReader;

public class ProcessLrcTextFromLocalFile extends ProcessLrcText{

	@Override
	public void setFile(String strFile) {
		// TODO Auto-generated method stub
		try{
			m_buf = new BufferedReader(new FileReader(strFile));
		}
		catch(Exception ex){
			
		}	
		
	}
}
