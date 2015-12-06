package com.xhcms.lottery.commons.persist.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.bonus.EquivalentOptionList;
import com.xhcms.lottery.commons.data.bonus.JCLQBetOptGroup;
import com.xhcms.lottery.commons.data.bonus.JCZQBetOptGroup;
import com.xhcms.lottery.lang.LotteryId;

public class OptionGroupByScoreService {
	
	private static final Logger log = LoggerFactory.getLogger(OptionGroupByScoreService.class);
	
	private static final Map<List<String>, JCLQBetOptGroup> jclqOptionsMap;
	private static final Map<List<String>, JCZQBetOptGroup> jczqOptionsMap;
	
	static {
		jclqOptionsMap = convertJCLQOptionsJsonToBean();
		jczqOptionsMap = convertJCZQOptionsJsonToBean();
	}
	
	public static Map<List<String>, JCLQBetOptGroup> getJclqOptionsMap() {
		return jclqOptionsMap;
	}
	
	public static Map<List<String>, JCZQBetOptGroup> getJczqOptionsMap() {
		return jczqOptionsMap;
	}
	
	/**
	 * 根据分值查找命中的结果集
	 * @param score
	 */
	@SuppressWarnings("unchecked")
	public static <EquResults> EquResults findHitResults(String lotteryId, BigDecimal score, EquResults result) {
		Set<List<String>> keys = null;
		if(LotteryId.JCLQ.name().equals(lotteryId)) {
			keys = jclqOptionsMap.keySet();
		} else if(LotteryId.JCZQ.name().equals(lotteryId)) {
			keys = jczqOptionsMap.keySet();
		}
		if(null != keys && keys.size() > 0) {
			Iterator<List<String>> iter = keys.iterator();
			while(iter.hasNext()) {
				List<String> list = iter.next();
				if(list.contains(score.toString())) {
					if(LotteryId.JCLQ.name().equals(lotteryId)) {
						result = (EquResults) jclqOptionsMap.get(list);
					} else if(LotteryId.JCZQ.name().equals(lotteryId)) {
						result = (EquResults) jczqOptionsMap.get(list);
					}
					break;
				}
			}
		}
		return result;
	}
	
	private static Map<List<String>, JCLQBetOptGroup> convertJCLQOptionsJsonToBean() {
		String json = readGroupOptionsJsonByScore(LotteryId.JCLQ.name().toLowerCase());
		log.debug("jclq group options json={}", json);
		Map<List<String>, JCLQBetOptGroup> map = new HashMap<List<String>, JCLQBetOptGroup>();
		if(StringUtils.isBlank(json)) {
			return map;
		}
		Map<String, Class<? extends JCLQBetOptGroup>> classMap = new HashMap<String, Class<? extends JCLQBetOptGroup>>();
		classMap.put("jclqEquOptions", JCLQBetOptGroup.class);
		EquivalentOptionList jsonBean = (EquivalentOptionList) JSONObject.toBean(JSONObject.fromObject(json), EquivalentOptionList.class, classMap);
		if(null != jsonBean) {
			List<JCLQBetOptGroup> list = jsonBean.getJclqEquOptions();
			Iterator<JCLQBetOptGroup> iter = list.iterator();
			while(iter.hasNext()) {
				JCLQBetOptGroup jlBetOptSet = iter.next();
				List<String> scores = jlBetOptSet.getScores();
				map.put(scores, jlBetOptSet);
			}
		}
		return map;
	}
	
	private static Map<List<String>, JCZQBetOptGroup> convertJCZQOptionsJsonToBean() {
		String json = readGroupOptionsJsonByScore(LotteryId.JCZQ.name().toLowerCase());
		log.debug("jczq group options json={}", json);
		Map<List<String>, JCZQBetOptGroup> map = new HashMap<List<String>, JCZQBetOptGroup>();
		if(StringUtils.isBlank(json)) {
			return map;
		}
		Map<String, Class<? extends JCZQBetOptGroup>> classMap = new HashMap<String, Class<? extends JCZQBetOptGroup>>();
		classMap.put("jczqEquOptions", JCZQBetOptGroup.class);
		EquivalentOptionList jsonBean = (EquivalentOptionList) JSONObject.toBean(JSONObject.fromObject(json), EquivalentOptionList.class, classMap);
		if(null != jsonBean) {
			List<JCZQBetOptGroup> list = jsonBean.getJczqEquOptions();
			Iterator<JCZQBetOptGroup> iter = list.iterator();
			while(iter.hasNext()) {
				JCZQBetOptGroup jzBetOptSet = iter.next();
				List<String> scores = jzBetOptSet.getScores();
				map.put(scores, jzBetOptSet);
			}
		}
		return map;
	}
	
	/**
	 * 读取玩法选项数据
	 * @return
	 */
	private static String readGroupOptionsJsonByScore(String lotteryId) {
		BufferedReader reader = null;
		InputStream in = null;
		StringBuilder buf = new StringBuilder();
		try {
			String filePath = "conf/" + lotteryId +"-group-options-by-score.json";
			in = OptionGroupByScoreService.class.getClassLoader().getResourceAsStream(filePath);
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			String readLine = "";
			while(StringUtils.isNotBlank(readLine = reader.readLine())) {
				buf.append(readLine);
			}
		} catch (Exception e) {
			log.warn("读取分组的投注项json数据异常.");
			e.printStackTrace();
		} finally {
			if(null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return buf.toString();
	}
}