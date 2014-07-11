package com.viva.vivalistening.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.os.Bundle;

import com.viva.vivalistening.data.ListeningItem;
import com.viva.vivalistening.Defs;
import com.viva.vivalistening.Utility;

public class DataSerializationFromFile extends DataSerialization{

	IDataSource m_dataSource = null;
	
	@Override
	public void setDataSource(IDataSource dataSource) {
		// TODO Auto-generated method stub
		m_dataSource = dataSource;
	}

	@Override
	public boolean load(Bundle args) {
		// TODO Auto-generated method stub
		
		assert(m_dataSource != null);
		String strFile = args.getString(Defs.VALUE_FILE);
		assert(strFile != null);
		File file = new File(strFile);
		if(file.exists() == false){
			Utility.logError(strFile + "don't exist!");
			return false;
		}
		
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			
			NodeList ns = doc.getElementsByTagName(Defs.TAG_TOTAL_TIME);
			if(ns.getLength() != 1){
				Utility.logError("error happen in totaltime element");
				return false;
			}
			
			
			Element el = (Element)(ns.item(0));
			int nTotalTime = Integer.parseInt(el.getTextContent());
			m_dataSource.setTotalTime(nTotalTime);
			
			ns = doc.getElementsByTagName(Defs.TAG_LISTENING_INFO);
			if(ns.getLength() != 1){
				Utility.logError("error happen in listeninginfo element");
				return false;
			}
			loadListeningItems((Element)(ns.item(0)));
			
			ns = doc.getElementsByTagName(Defs.TAG_DAY_TIME_INFO);
			if(ns.getLength() != 1){
				Utility.logError("error happen in durationinfo element");
				return false;
			}
			loadDayTimeItems((Element)(ns.item(0)));
				
		}
		catch(Exception ex){
			Utility.logError(ex.getMessage());
		}
		
		return true;
	}

	@Override
	public boolean save(Bundle args) {
		// TODO Auto-generated method stub
		assert(m_dataSource != null);
		String strFile = args.getString(Defs.VALUE_FILE);
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement(Defs.TAG_ROOT);
			doc.appendChild(root);
			
			
			Element el = doc.createElement(Defs.TAG_TOTAL_TIME);
			el.setTextContent(String.valueOf(m_dataSource.getTotalTime()));
			root.appendChild(el);
			saveListeningItems(root,doc);
			saveTotalTimeItems(root,doc);
			
			File file = new File(strFile);
			if(file.exists()){
				file.delete();
			}
			
			TransformerFactory factoryTransform = TransformerFactory.newInstance();  
	        Transformer transformer = factoryTransform.newTransformer();  
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	        DOMSource source = new DOMSource(doc);  
	        
	        transformer.transform(source, new StreamResult(new BufferedWriter(  
	                new OutputStreamWriter(new FileOutputStream(strFile), "UTF-8"))));
	        
//	        Calendar cal = Calendar.getInstance();
//	        int year = cal.get(Calendar.YEAR);
//			int month = cal.get(Calendar.MONTH);
//			int day = cal.get(Calendar.DAY_OF_MONTH);
//			int h = cal.get(Calendar.HOUR_OF_DAY);
//			int m = cal.get(Calendar.MINUTE);
//			int s = cal.get(Calendar.SECOND);
//	        String strBK = Defs.LOCAL_FOLDER + "/" + year+month+day + h + m + s + ".xml";
//	        transformer.transform(source, new StreamResult(new BufferedWriter(  
//	                new OutputStreamWriter(new FileOutputStream(strBK), "UTF-8"))));
			
		}
		catch(Exception ex){
			Utility.logError(ex.getMessage());
			return false;
		}
		
		return true;
	}
	
	void loadListeningItems(Element el){
		NodeList ns = el.getElementsByTagName(Defs.TAG_ITEM);
		for(int i = 0 ; i < ns.getLength() ; i++){
			Element item = (Element)ns.item(i);
			ListeningItem info = new ListeningItem();
			info.m_id = item.getAttribute(Defs.TAG_LISTENING_ITEM_ID);
			info.m_strTitle = item.getAttribute(Defs.TAG_LISTENING_ITEM_TITLE);
			info.m_strSummary = item.getAttribute(Defs.TAG_LISTENING_ITEM_SUMMARY);
			info.m_nTotalTime = Integer.parseInt(item.getAttribute(Defs.TAG_LISTENING_ITEM_TOTAL_TIME));
			info.m_nDuration = Integer.parseInt(item.getAttribute(Defs.TAG_LISTENING_ITEM_DURATION));
			m_dataSource.addListeningItem(info);
		}
	}
	
	void loadDayTimeItems(Element el){
		NodeList ns = el.getElementsByTagName(Defs.TAG_ITEM);
		for(int i = 0 ; i < ns.getLength() ; i++){
			Element item = (Element)ns.item(i);
			DayTimeItem info = new DayTimeItem();
			int year = Integer.parseInt(item.getAttribute(Defs.TAG_DAY_TIME_ITEM_YEAR));
			int month = Integer.parseInt(item.getAttribute(Defs.TAG_DAY_TIME_ITEM_MONTH));
			int day = Integer.parseInt(item.getAttribute(Defs.TAG_DAY_TIME_ITEM_DAY));
			info.m_nTotalTime = Integer.parseInt(item.getAttribute(Defs.TAG_DAY_TIME_ITEM_TOTAL_TIME));
			info.m_date.set(year, month, day,0,0,0);
			m_dataSource.addDayTimeItem(info);
		}
	}
	
	void saveListeningItems(Element root,Document doc){
		Element el = doc.createElement(Defs.TAG_LISTENING_INFO);
		root.appendChild(el);
		
		for(int i = 0 ; i < m_dataSource.getListeningItemCount() ; i++){
			ListeningItem info = m_dataSource.getListeningItem(i);
			Element item = doc.createElement(Defs.TAG_ITEM);
			item.setAttribute(Defs.TAG_LISTENING_ITEM_ID, info.m_id);
			item.setAttribute(Defs.TAG_LISTENING_ITEM_TITLE, info.m_strTitle);
			item.setAttribute(Defs.TAG_LISTENING_ITEM_SUMMARY, info.m_strSummary);
			item.setAttribute(Defs.TAG_LISTENING_ITEM_TOTAL_TIME, String.valueOf(info.m_nTotalTime));
			item.setAttribute(Defs.TAG_LISTENING_ITEM_DURATION, String.valueOf(info.m_nDuration));
			el.appendChild(item);
		}
	}
	
	void saveTotalTimeItems(Element root,Document doc){
		Element el = doc.createElement(Defs.TAG_DAY_TIME_INFO);
		root.appendChild(el);
		
		for(int i = 0 ; i < m_dataSource.getDayTimeItemCount(); i++){
			DayTimeItem info =  m_dataSource.getDayTimeItem(i);
			Element item = doc.createElement(Defs.TAG_ITEM);
			item.setAttribute(Defs.TAG_DAY_TIME_ITEM_YEAR, String.valueOf(info.m_date.get(Calendar.YEAR)));
			item.setAttribute(Defs.TAG_DAY_TIME_ITEM_MONTH, String.valueOf(info.m_date.get(Calendar.MONTH)));
			item.setAttribute(Defs.TAG_DAY_TIME_ITEM_DAY, String.valueOf(info.m_date.get(Calendar.DAY_OF_MONTH)));
			item.setAttribute(Defs.TAG_DAY_TIME_ITEM_TOTAL_TIME, String.valueOf(info.m_nTotalTime));
			el.appendChild(item);
		}
		
	}

}
