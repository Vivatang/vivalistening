package com.viva.vivalistening.listening;

import java.util.ArrayList;
import java.util.List;


/* 所查询单词的信息
 * 包括中文解释，音标，例句
 * @author  Qi-feng Tang
 * @version  1.0
 * @since  1.0 
 */
public class WordInfo{
	
	/* 所查询单词的解释
	 * 包括词语的类型（名词，动词...）,词语的解释
	 * @author  Qi-feng Tang
	 * @version  1.0
	 * @since  1.0 
	 */
	class WordExplanation{
		
		//词语的类型
		String type;
		
		//词语的解释
		String explanation;
		
	}
	
	/* 单词例句的信息
	 * 包括英文例句，例句的中文解释
	 * @author  Qi-feng Tang
	 * @version  1.0
	 * @since  1.0 
	 */
	class SentenceInfo{
		
		//英文例句
		String org;
		
		//例句的中文解释
		String translation;
		
	}
	
	//查询的英文单词
	public String word;
	
	//美式发音音标
	public String psUS;
	
	//英式发音音标
	public String psUK;
	
	//美式发音音标链接
	public String psUSLink;
		
	//英式发音音标链接
	public String psUKLink;
	
	//词语解释，一个词可能会有多个解释
	public List<WordExplanation> expArray = new ArrayList<WordExplanation>();
	
	//词语例句
	public List<SentenceInfo> sentArray = new ArrayList<SentenceInfo>();
	
}
