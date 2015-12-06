package com.unison.lottery.weibo.dataservice.commons.parse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.model.BBLeagueModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBMatchInfoDocument;
import com.unison.lottery.weibo.dataservice.commons.model.BBMatchInfoRealtimeDocument;
import com.unison.lottery.weibo.dataservice.commons.model.BBOddsRealtimeModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBTeamModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFChangeModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFResultModel;
import com.unison.lottery.weibo.dataservice.commons.model.BJEuroreOddsModel;
import com.unison.lottery.weibo.dataservice.commons.model.BaseNameModel;
import com.unison.lottery.weibo.dataservice.commons.model.FBBFDataModel;
import com.unison.lottery.weibo.dataservice.commons.model.LeagueModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchAgendaModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchTypeModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchidModel;
import com.unison.lottery.weibo.dataservice.commons.model.TeamModel;
import com.unison.lottery.weibo.dataservice.commons.model.TechnicModel;
import com.unison.lottery.weibo.dataservice.commons.util.CharSetDecider;

@SuppressWarnings("restriction")
@Service
public class ParseServiceImpl implements ParseService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String THIRD_LEVEL_SPLIT_MARK = ",";
	private static final String SECOND_LEVEL_SPLIT_MARK = ";";
	private static final String TOP_LEVEL_SPLIT_MARK = "$";
	@Autowired
	private CharSetDecider charSetDecider;

	@Override
	public Bindings parseJSFromUTF8String(String js) {
		Bindings bindings=null;
		if(StringUtils.isNotBlank(js)){
			ScriptEngineManager mgr = new ScriptEngineManager();  
			ScriptEngine engine = mgr.getEngineByExtension("js");
			try {
				js=StringUtils.remove(js, "ShowBf();");
				engine.eval(js);
				bindings=engine.getBindings(ScriptContext.ENGINE_SCOPE);
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}
		return bindings;
	}
	
	

	@Override
	public Bindings parseJSFromFile(File jsFile,
			DataInterfaceName dataInterfaceName) {
		Bindings bindings=null;
		if(null!=jsFile&&jsFile.exists()){
			try {
				String encoding = charSetDecider
						.decideCharsetFromName(dataInterfaceName);
				String js=FileUtils.readFileToString(jsFile, encoding);
				js=StringUtils.remove(js, "ShowBf();");
				bindings=parseJSFromUTF8String(js);
			}  catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bindings;

	}



	@Override
	public List<List<String>> nativeArrayToListOfStringList(
			NativeArray nativeArray) {
		List<List<String>> result=null;
		if(null!=nativeArray){
			result=new ArrayList<List<String>>();
			
			NativeArray[] aStrs = (NativeArray[]) nativeArray
					.toArray(new NativeArray[1]);
	  
	    	if(null!=aStrs&&aStrs.length>0){
	    		
	    		for(NativeArray aItem:aStrs){
	    			
	    			if(null!=aItem){
	    				List<String> strList=new ArrayList<String>();
	    				String[] strs=(String[]) aItem.toArray(new String[1]);
	        			if(null!=strs&&strs.length>0){
	        				for(String strItem:strs){
	        					strList.add(strItem);
	        					
	        				}
	        			}
	        			result.add(strList);
	    			}
	    			
	    		}
	    	}
			
		}
		return result;
		
	}

	
	@Override
	public List<List<Object>> nativeArrayToListOfObjectList(
			NativeArray nativeArray) {
		List<List<Object>> result=null;
		if(null!=nativeArray){
    		result=new ArrayList<List<Object>>();
			NativeArray[] cObjs = (NativeArray[]) nativeArray
					.toArray(new NativeArray[1]);
        	if(null!=cObjs&&cObjs.length>0){
        		for(NativeArray cObj:cObjs){
        			if(null!=cObj){
        				List<Object> objList=new ArrayList<Object>();
        				Object[] objs=(Object[]) cObj.toArray(new Object[1]);
        				if(null!=objs&&objs.length>0){
        					for(Object obj:objs){
        						objList.add(obj);
	        					
	        				}
        				}
        				result.add(objList);
        			}
        			
        		}
        		
        	}
    	}
		return result;
	}



	@Override
	public List<String> nativeArrayToListOfString(NativeArray nativeArray) {
		List<String> result=null;
		if(null!=nativeArray){
    		result=new ArrayList<String>();
    		String[] strs=(String[]) nativeArray.toArray(new String[1]);
        	if(null!=strs&&strs.length>0){
        		for(String str:strs){
        			result.add(str);
        		}
        	}
    	}
		return result;
	}



	



	@Override
	public List<List<List<String>>> parseTextFromUTF8String(String str) {
		List<List<List<String>>> result=null;
		if(StringUtils.isNotBlank(str)){
			result=new ArrayList<List<List<String>>>();
			String[] topLevelStrs = StringUtils.splitPreserveAllTokens(str,
					TOP_LEVEL_SPLIT_MARK);
			if(null!=topLevelStrs&&topLevelStrs.length>0){
				for(String topLevelStr:topLevelStrs){
					List<List<String>> secondLevelStrList=new ArrayList<List<String>>();
					result.add(secondLevelStrList);
					if(StringUtils.isNotBlank(topLevelStr)){
						String[] secondLevelStrs = StringUtils
								.splitPreserveAllTokens(topLevelStr,
										SECOND_LEVEL_SPLIT_MARK);
						if (null != secondLevelStrs
								&& secondLevelStrs.length > 0) {
							for(String secondLevelStr:secondLevelStrs){
								List<String> thirdLevelStrList=new ArrayList<String>();
								secondLevelStrList.add(thirdLevelStrList);
								if(StringUtils.isNotBlank(secondLevelStr)){
									String[] thirdLevelStrs = StringUtils
											.splitPreserveAllTokens(
													secondLevelStr,
													THIRD_LEVEL_SPLIT_MARK);
									if (null != thirdLevelStrs
											&& thirdLevelStrs.length > 0) {
										for(String thirdLevelStr:thirdLevelStrs){
											thirdLevelStrList
													.add(thirdLevelStr);
										}
									}
								}
							}
						}
					}
					
				}
			}
		}
		return result;
	}



	@Override
	public List<List<List<String>>> parseTextFromFile(File file,
			DataInterfaceName dataInterfaceName) {
		List<List<List<String>>> result=null;
		if(null!=file&&file.exists()){
			try {
				String encoding = charSetDecider
						.decideCharsetFromName(dataInterfaceName);
				String str=FileUtils.readFileToString(file, encoding);
				result=parseTextFromUTF8String(str);
			}  catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<List<List<String>>> parseXmlFromFileWithDom(File xmlFile,
			DataInterfaceName dataInterfaceName) {
		List<List<List<String>>> result = null;
		try {
			if (null != xmlFile && xmlFile.exists()) {
				String encoding = charSetDecider
						.decideCharsetFromName(dataInterfaceName);
				String xml = FileUtils.readFileToString(xmlFile, encoding);
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				Document doc = dBuilder.parse(is);
				if(doc.hasChildNodes()){
					result=new ArrayList<List<List<String>>>();
					NodeList nodeList=doc.getChildNodes();
					if(null!=nodeList&&nodeList.getLength()>0){
						parseXmlFromFileWithChildDom(nodeList,result);
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@Override
	public List<List<List<String>>> parseXmlFromStringWithDom(String xmlStr,
			DataInterfaceName dataInterfaceName) {
		List<List<List<String>>> result = null;
		try {
			if (StringUtils.isNotBlank(xmlStr)) {
				DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				InputStream is = new ByteArrayInputStream(xmlStr.getBytes());
				Document doc = dBuilder.parse(is);
				if(doc.hasChildNodes()){
					result=new ArrayList<List<List<String>>>();
					//rootNode
					NodeList nodeList=doc.getChildNodes();
					if(null!=nodeList&&nodeList.getLength()>0){
						NodeList childNodeList = nodeList.item(0)
								.getChildNodes();
						for (int i = 0; i < childNodeList.getLength(); i++) {
							Node nodeItem = childNodeList.item(i);
							parseXmlFromFileWithChildDom(
									nodeItem.getChildNodes(), result);
						}
						
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}



	private void parseXmlFromFileWithChildDom(NodeList nodeList,
			List<List<List<String>>> result) {
		List<List<String>> list=new ArrayList<List<String>>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node nodeItem = nodeList.item(i);
			if(StringUtils.isNotBlank(nodeItem.getNodeValue())){
				List<String> stringList=new ArrayList<String>();
				String[] stringArr = StringUtils.splitPreserveAllTokens(
						nodeItem.getNodeValue(), ",");
				if(null!=stringArr&&stringArr.length>0){
					for(String str:stringArr){
						stringList.add(str);
					}
				}
				list.add(stringList);
			}
			if(nodeItem.hasChildNodes()){
				NodeList model=nodeItem.getChildNodes();
				parseXmlFromFileWithChildDom(model, result);
			}
		}
		result.add(list);
		
	}





	@Override
	public  Object parseXmlFromFileWithJAXB(File xmlFile,
			DataInterfaceName dataInterfaceName) {
		Object result = null;
		try {
			if (null != xmlFile && xmlFile.exists()) {
				String encoding = charSetDecider
						.decideCharsetFromName(dataInterfaceName);
				String text = FileUtils.readFileToString(xmlFile, encoding);
				if(StringUtils.isNotBlank(text)){
					JAXBContext jaxbContext= getInstanceJAXBContext(dataInterfaceName);
					if(null!=jaxbContext){
						Unmarshaller um=jaxbContext.createUnmarshaller();
						result=um.unmarshal(new StringReader(text.trim()));
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
		
	@Override
	public  Object parseXmlFromStringWithJAXB(String xmlStr,
			DataInterfaceName dataInterfaceName) {
		Object result = null;
		try {
			Object middleResultObject = null;
			if (StringUtils.isNotBlank(xmlStr)) {
				JAXBContext jaxbContext= getInstanceJAXBContext(dataInterfaceName);
				if(null!=jaxbContext){
					Unmarshaller um=jaxbContext.createUnmarshaller();
					middleResultObject = um.unmarshal(new StringReader(xmlStr
							.trim()));
				}
			}
			switch (dataInterfaceName) {
			case FBBFDayOdds:
				result = changToFBBFData(middleResultObject);
				break;

			default:
				result = middleResultObject;
				break;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private Object changToFBBFData(Object middleResultObject) {
		if (middleResultObject != null) {
			FBBFDataModel fbbfData = (FBBFDataModel) middleResultObject;
			if (fbbfData.matchAgendaList != null) {
				List<MatchAgendaModel> matchAgendas = fbbfData.matchAgendaList;
				for (MatchAgendaModel matchAgenda : matchAgendas) {
					// 解析赛事类型
					matchAgenda.setMatchTypeName(parseStringData(matchAgenda
							.league));
					// 解析主队
					matchAgenda.setHomeName(parseStringData(matchAgenda
							.home));
					// 解析客队信息
					matchAgenda.setAwayName(parseStringData(matchAgenda
							.away));
					//解析主、客队id
					if(StringUtils.length(matchAgenda.home)>=4){
						matchAgenda.setHomeTeamId(matchAgenda.home.split(",")[3]);
					}
					if(StringUtils.length(matchAgenda.away)>=4){
						matchAgenda.setAwayTeamId(matchAgenda.away.split(",")[3]);
					}
				}

			}
		}
		return middleResultObject;
	}

	private BaseNameModel parseStringData(String data) {
		if (data != null) {
			String[] matchTypeNameArray = data.split(",");
			if (matchTypeNameArray != null) {
				int i = 0;
				BaseNameModel matchType = new BaseNameModel();
				for (String type : matchTypeNameArray) {
					if (i == 0) {
						matchType.setChineseName(type);
					} else if (i == 1) {
						matchType.setTraditional(type);
					} else if(i==2){
						matchType.setEnglishName(type);
					}
					i++;
				}
				return matchType;
			}
		}
		return null;
	}
	
	private JAXBContext getInstanceJAXBContext(
			DataInterfaceName dataInterfaceName) {
		JAXBContext result=null;
		try {
			switch(dataInterfaceName){
				case BBBjEuropeOdds:
					//蓝球百家欧赔接口
					result= JAXBContext.newInstance(BJEuroreOddsModel.class);
				break;
				case BJ_EUROPE_ODDS:
					//足球百家欧赔接口
					result= JAXBContext.newInstance(BJEuroreOddsModel.class);
				break;
				case BFResult:
					//赛程赛果
					result= JAXBContext.newInstance(BFResultModel.class);
				break;
				case BFYingChao:
					//获取英超本赛季的赛程
					result= JAXBContext.newInstance(BFResultModel.class);
				break;
				case League:
					//联赛杯赛
					result= JAXBContext.newInstance(LeagueModel.class);
				break;
				case Team:
					//球队资料
					result= JAXBContext.newInstance(TeamModel.class);
				break;
				case Matchid:
					//彩票赛事与球探网的关联表
					result= JAXBContext.newInstance(MatchidModel.class);
				break;
				case Technic:
					//一天内比赛的技术统计
					result= JAXBContext.newInstance(TechnicModel.class);
				break;
				case BF_XMLByID:
					//按赛程ID查该比赛的数据
					result= JAXBContext.newInstance(BFResultModel.class);
				break;
				case BFChange:
					//20秒内变化数据
					result= JAXBContext.newInstance(BFChangeModel.class);
				break;
				case BFChange2:
					//150秒内变化数据，两个格式完全一样
					result= JAXBContext.newInstance(BFChangeModel.class);
				break;
				
				case BBMatchInfoToday:	// 篮球今日赛程赛果
				case BBMatchInfo:
					// 篮球历史赛程、赛果
					result= JAXBContext.newInstance(BBMatchInfoDocument.class);
				break;
				case BBMatchInfoRealtime:
					// 篮球即时比分
				result = JAXBContext
						.newInstance(BBMatchInfoRealtimeDocument.class);
					break;
				case BBTeam:
					//篮球球队资料
					result= JAXBContext.newInstance(BBTeamModel.class);
				break;
				case BBLeague:
					//篮球联赛信息
					result= JAXBContext.newInstance(BBLeagueModel.class);
					break;
				case BBChangeOdds30Sec:
					//篮球即时赔率
					result= JAXBContext.newInstance(BBOddsRealtimeModel.class);
					break;
			case FBBFDayOdds:
				// 足球当天比分数据
				result = JAXBContext.newInstance(FBBFDataModel.class);
				break;
				default:
					logger.error("不支持的接口类型：{}", dataInterfaceName);
				break;
			}
		
		}catch(Throwable e){
			logger.error("不能解析xml！", e);
		}
		return result;
	}

}
