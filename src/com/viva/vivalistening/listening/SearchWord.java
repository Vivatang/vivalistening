/*文件名  SearchWord.java
 * 
 * 修改人  Qi-feng Tang
 * 修改时间  2014-06-17
 * 修改内容  添加网络查询单词功能
 * 
 * 修改人 Qi-feng Tang
 * 修改时间  2014-06-18
 * 修改内容  SearchWord类里添加网络判断功能
 */

package com.viva.vivalistening.listening;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.viva.vivalistening.Defs;
import com.viva.vivalistening.listening.WordInfo.SentenceInfo;
import com.viva.vivalistening.listening.WordInfo.WordExplanation;

/* 从网络获取单词的英文解释
 * 用户可以通过这个类获取到单词的中文解释，音标和例句
 * @author  Qi-feng Tang
 * @version  1.0
 * @since  1.0 
 */
public class SearchWord {

	
	/*
	 * 查询单词 由于网上查询需要一定的时间，所以查询结果是以异步方式返回。
	 * 
	 * @param word 索要查询的单词
	 * 
	 * @return void
	 * 
	 * @since 1.0
	 */
	public static WordInfo search(String word) {
		
		InputStream in = sendRequest(word);
		if(in == null){
			return null;
		}
		
		return parseResult(in);
	}

	

	/*
	 * 发送查询单词请求
	 * 
	 * @param word 索要查询的单词
	 * 
	 * @return boolean
	 * 
	 * @since 1.0
	 */
	protected static InputStream sendRequest(String strWord) {

		String strUrl = Defs.TRANSLATION_URL + strWord;

		HttpGet httpGet = new HttpGet(strUrl);

		HttpClient httpClient = new DefaultHttpClient();

		InputStream inputStream = null;

		String strRet = "";

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			inputStream = httpEntity.getContent();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return inputStream;
	}// end of InputStream sendRequest(String strWord)

	/*
	 * 解析查询的结果 把结果转换成WordInfo对象
	 * 
	 * @param inputStream 查询返回的内容
	 * 
	 * @return boolean 如果成功返回true，如果失败返回false
	 * 
	 * @since 1.0
	 */

	/*
	 * <dict num="219" id="219" name="219"> <key>face</key> <ps>feɪs</ps> <pron>
	 * http://res.iciba.com/resource/amp3/oxford/0/2a/b8/2
	 * ab8a23c8c7b8779cd9fecb335b21d49.mp3 </pron> <ps>fes</ps> <pron>
	 * http://res
	 * .iciba.com/resource/amp3/1/0/d5/ca/d5ca322453f2986b752e58b11af83d96.mp3
	 * </pron> <pos>n.</pos> <acceptation>面容；表面；脸；方面；</acceptation> <pos>vt.&
	 * vi.</pos> <acceptation>面对；面向…；正视；承认；</acceptation> <pos>vt.</pos>
	 * <acceptation>（感到不能）对付；（明知不好办而）交谈；必须对付（某情况）；面临…；</acceptation> <sent>
	 * <orig> Each face object has a single glyph slot object that can be
	 * accessed as face- >glyph. </orig>
	 * <trans>每一个face对象都有一个字形槽对象，可以通过face->glyph来访问。</trans> </sent> <sent>
	 * <orig> A voucher of RMB100 for FACE Restaurant will be issued to each
	 * order during this promotion. </orig>
	 * <trans>促销期间每笔订单将获得一张FACE餐厅的100元用餐代金券。</trans> </sent> <sent> <orig>
	 * Animal statues in face of the other dragons, beasts face, face riding
	 * birds and baby animals. </orig>
	 * <trans>动物石像中的另一类是人面龙、人面兽、人面鸟和骑兽娃娃。</trans> </sent> <sent> <orig> Place
	 * mask onto face, gently press mask on face until it fits the face. </orig>
	 * <trans>将面膜敷于面部，用手指轻压使其充分贴紧面部。</trans> </sent> <sent> <orig> Face
	 * detection technology mainly includes algorithms based on face knowledge,
	 * template matching and face learning. </orig>
	 * <trans>目前的人脸检测技术主要包括基于知识、模板匹配和基于学习的人脸检测算法。</trans> </sent> </dict>
	 */
	protected static WordInfo parseResult(InputStream inputStream) {

		WordInfo wordInfo = new WordInfo();
		try {
			Element el = null;
			NodeList nl = null;
			
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(inputStream);

			// 获取单词
			nl = doc.getElementsByTagName(Defs.TAG_KEY);
			if (nl.getLength() != 1) {
				throw new Exception("the count of key isn't 1");
			}
			el = (Element) nl.item(0);
			wordInfo.word = el.getTextContent();

			// 获取音标
			nl = doc.getElementsByTagName(Defs.TAG_PS);
			if (nl.getLength() != 2) {
				throw new Exception("the count of the ps must be 2");
			}
			// 第一个是英式发音英标
			el = (Element) nl.item(0);
			wordInfo.psUK = el.getTextContent();
			// 第二个是美式发音英标
			el = (Element) nl.item(1);
			wordInfo.psUS = el.getTextContent();

			// 获取英标朗读链接
			nl = doc.getElementsByTagName(Defs.TAG_PRON);
			if (nl.getLength() != 2) {
				throw new Exception("the count of the pron must be 2");
			}
			// 第一个是英式发音英标链接
			el = (Element) nl.item(0);
			wordInfo.psUKLink = el.getTextContent();
			// 第二个是美式发音英标链接
			el = (Element) nl.item(1);
			wordInfo.psUSLink = el.getTextContent();

			// 获取单词的属性
			nl = doc.getElementsByTagName(Defs.TAG_POS);
			if (nl.getLength() == 0) {
				throw new Exception("the count of the pos must be more than 0");
			}
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				WordExplanation exp = wordInfo.new WordExplanation();
				exp.type = el.getTextContent();
				wordInfo.expArray.add(exp);
			}

			// 单词中文解释,中文解释是和单词的属性一一对应的
			nl = doc.getElementsByTagName(Defs.TAG_ACCEPTATION);
			// 如果解释的条目数量和单词属性数量不一致，则非法
			if (nl.getLength() != wordInfo.expArray.size()) {
				throw new Exception("the count of the pos must be more than 0");
			}
			// 放入属性所对应的中文解释
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				wordInfo.expArray.get(i).explanation = el.getTextContent();
			}

			// 获取单词的英文例句
			nl = doc.getElementsByTagName(Defs.TAG_ORIG);
			if (nl.getLength() == 0) {
				throw new Exception("the count of the orig must be more than 0");
			}
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				SentenceInfo sent = wordInfo.new SentenceInfo();
				sent.org = el.getTextContent();
				wordInfo.sentArray.add(sent);
			}

			// 获取例句的中文解释,中文解释是和例句一一对应的
			nl = doc.getElementsByTagName(Defs.TAG_TRANS);
			// 如果中文解释数量和例句不一致，则非法
			if (nl.getLength() != wordInfo.sentArray.size()) {
				throw new Exception(
						"the count of the trans must be more than 0");
			}
			// 放入例句所对应的中文解释
			for (int i = 0; i < nl.getLength(); i++) {
				el = (Element) nl.item(i);
				wordInfo.sentArray.get(i).translation = el
						.getTextContent();
			}

		}// end of try
		catch (Exception ex) {
			wordInfo = null;
			ex.printStackTrace();
		}

		return wordInfo;
	}// end of boolean parseResult(InputStream inputStream)

}
