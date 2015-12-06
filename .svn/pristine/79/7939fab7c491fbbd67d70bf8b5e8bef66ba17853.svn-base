package com.unison.lottery.weibo.dataservice.parse;

import java.util.List;

import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;

public interface BBMatchInfoDataParseService {

	/**
	 * 从字符串列表中解析出BBMatchInfoData对象列表
	 * @param strs
	 * @return
	 */
	List<BBMatchInfoData> parseBBMatchInfoDataFromStrings(List<String> strs);

	/**
	 * 从赛程赛果接口的字符串列表中解析出BBMatchInfoData对象列表
	 * @param strs
	 * @return
	 */
	List<BBMatchInfoData> parseBBMatchInfoDataOfScheduleFromStrings(
			List<String> strs);

	/**
	 * 从即时变化的数据的字符串列表中解析出BBMatchInfoData对象列表
	 * @param contents
	 * @return
	 */
	List<BBMatchInfoData> parseBBMatchInfoDataRealTimeFromStrings(
			List<String> contents);

}
