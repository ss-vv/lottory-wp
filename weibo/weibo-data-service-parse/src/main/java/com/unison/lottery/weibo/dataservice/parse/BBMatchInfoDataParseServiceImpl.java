package com.unison.lottery.weibo.dataservice.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.dataservice.commons.parse.ExtractEngine;
import com.unison.lottery.weibo.dataservice.commons.parse.ExtractException;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
@Service
public class BBMatchInfoDataParseServiceImpl implements
		BBMatchInfoDataParseService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public List<BBMatchInfoData> parseBBMatchInfoDataFromStrings(
			List<String> strs) {
		List<BBMatchInfoData> matches = new LinkedList<BBMatchInfoData>();
		if(null!=strs&&!strs.isEmpty()){
			ExtractEngine<BBMatchInfoData> extractEngine = ExtractEngineFactory.configTodayBBMatchEngine();
			makeBBMatchInfoDatas(strs, matches, extractEngine);
		}
		
		return matches;
	}

	private void makeBBMatchInfoDatas(List<String> strs,
			List<BBMatchInfoData> matches,
			ExtractEngine<BBMatchInfoData> extractEngine) {
		for (String match : strs){
			makeBBMatchInfoData(matches, extractEngine, match);
		}
	}

	private void makeBBMatchInfoData(List<BBMatchInfoData> matches,
			ExtractEngine<BBMatchInfoData> extractEngine, String match) {
		try {
			BBMatchInfoData matchBean = extractEngine.extractBeanFromText(match);
			composeMatchTime(matchBean);
			makeIsNeutral(matchBean);
			matches.add(matchBean);
		} catch (ExtractException | ParseException e) {
			logger.error("不能抽取今日篮球赛程赛果：{}", match, e);
		}
	}
	
	private void makeIsNeutral(BBMatchInfoData matchBean) {
		if(null!=matchBean){
			if(StringUtils.equals(BBMatchInfoData.IS_NOT_MIDDLE_MATCH, matchBean.getMiddleMatchState())){
				matchBean.setIsNeutral(false);
			}
			else if(StringUtils.equals(BBMatchInfoData.IS_MIDDLE_MATCH, matchBean.getMiddleMatchState())){
				matchBean.setIsNeutral(true);
			}
		}
		
		
	}

	private void composeMatchTime(BBMatchInfoData matchBean) throws ParseException {
		if(matchBean == null){
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = matchBean.getMatchTimeStr();
		matchBean.setMatchTime(sdf.parse(time));
	}

	@Override
	public List<BBMatchInfoData> parseBBMatchInfoDataOfScheduleFromStrings(
			List<String> strs) {
		List<BBMatchInfoData> matches = new LinkedList<BBMatchInfoData>();
		if(null!=strs&&!strs.isEmpty()){
			ExtractEngine<BBMatchInfoData> extractEngine = ExtractEngineFactory.configBBMatchInfo4ScheduleEngine();
			makeBBMatchInfoDatas(strs, matches, extractEngine);
		}
		
		return matches;
	}

	@Override
	public List<BBMatchInfoData> parseBBMatchInfoDataRealTimeFromStrings(
			List<String> contents) {
		List<BBMatchInfoData> matches = new LinkedList<BBMatchInfoData>();
		if(null!=contents&&!contents.isEmpty()){
			ExtractEngine<BBMatchInfoData> extractEngine = ExtractEngineFactory.configRealtimeBBMatchEngine();
			for (String match : contents){
				try {
					BBMatchInfoData matchBean = extractEngine
							.extractBeanFromText(match);
					makeIsNeutral(matchBean);
					matches.add(matchBean);
				} catch (ExtractException e) {
					logger.error("不能抽取今日篮球即时变化数据：{},异常为:{}", match, e);
				}
			}
		}
		
		return matches;
	}

}
