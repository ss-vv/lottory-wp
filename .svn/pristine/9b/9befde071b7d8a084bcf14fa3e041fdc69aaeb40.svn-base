package com.unison.lottery.weibo.dataservice.parse;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 篮球博彩公司id转换工具。
 * 
 * @author Yang Bo
 */
public class BBCorpIdUtils {
	
	private static Logger logger = LoggerFactory.getLogger(BBCorpIdUtils.class);
	
	// 让分
	private static Map<String, String> corpIdMap = new HashMap<>();
	
	static {
		/*
		1：澳门   让分
		2：易胜博 让分
		3：皇冠   让分
		8：Bet365 让分
		9：韦德   让分
		*/
		corpIdMap.put("1", "265");
		corpIdMap.put("2", "90");
		corpIdMap.put("3", "458");
		corpIdMap.put("8", "214");
		corpIdMap.put("9", "82");

		/*
		4：澳门   总分
		5：易胜博 总分
		6：皇冠   总分
		11：bet365总分
		12：韦德  总分
		*/
		corpIdMap.put("4", "265");
		corpIdMap.put("5", "90");
		corpIdMap.put("6", "458");
		corpIdMap.put("11", "214");
		corpIdMap.put("12", "82");
	
	}
	
	/**
	 * 将 让分类型公司id 统一为 篮球标准公司id，即百家欧赔中的公司id。
	 * @param concedeCorpId 让分、总分公司id
	 * @return 篮球统一公司id
	 */
	public static String unifyCorpId(String concedeCorpId){
		String standardCorpId = corpIdMap.get(concedeCorpId);
		if (standardCorpId == null){
			logger.error("不识别的公司让分、总分公司id：{}", concedeCorpId);
			// 返回负值
			return "-"+concedeCorpId;
		}
		return standardCorpId;
	}
}
