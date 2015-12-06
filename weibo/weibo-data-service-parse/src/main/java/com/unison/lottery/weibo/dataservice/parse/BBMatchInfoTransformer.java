package com.unison.lottery.weibo.dataservice.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 篮球抽取信息时使用的属性变换器。
 * 
 * @author Yang Bo
 */
public class BBMatchInfoTransformer {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private Pattern matchTimePattern = Pattern.compile("(\\d+)/(\\d+)/(\\d+)(\\D+)(\\d+):(\\d+):(\\d+)");
	
	/**
	 * 把 "2014/10/10 7:30:01" 转换为 "2014-10-10 07:30:00". 
	 * 把"2014/10/10 17:30:01" 转换为 "2014-10-10 17:30:00"
	 * @param input 原始输入
	 * @return 转换后的串
	 */
	public String matchTimeStr(String input){
		if (StringUtils.isBlank(input)) {
			return "";
		}
		Matcher matcher = matchTimePattern.matcher(input);
		if (matcher.matches()){
			return String.format("%s-%s-%s %s:%s:00", 
					matcher.group(1),
					matcher.group(2),
					matcher.group(3),
					StringUtils.leftPad(matcher.group(5), 2, "0"),
					matcher.group(6));
		}else{
			logger.error("不能转换 matchTime 串，格式未知！输入为：{}", input);
			return input;
		}
	}
	
	// xxx[123] -> xxx
	private String removeBrackets(String input){
		if (StringUtils.isBlank(input)){
			return input;
		}
		return input.replaceAll("\\[.*\\]", "");
	}
	
	public String homeTeam(String input){
		return removeBrackets(input);
	}
	public String homeTeamE(String input){
		return removeBrackets(input);
	}
	public String homeTeamF(String input){
		return removeBrackets(input);
	}

	public String guestTeam(String input){
		return removeBrackets(input);
	}
	public String guestTeamE(String input){
		return removeBrackets(input);
	}
	public String guestTeamF(String input){
		return removeBrackets(input);
	}
	
	/**
	 * 将“30（分）:01（秒）”转换为秒，即30*60+1=1801秒;将"29.7"转换为29秒
	 * @param input
	 * @return
	 */
	public String remainTime(String input) {
		String result=null;
		if(StringUtils.isNotBlank(input)){
			input = input.trim();
			if(input.matches("(\\d+):(\\d+)")){
				String[] splits = input.split(":");
				if(null!=splits&&splits.length==2){
					int minute=Integer.parseInt(StringUtils.removeStart(splits[0], "0"));
					int second=Integer.parseInt(StringUtils.removeStart(splits[1], "0"));
					result=Integer.toString(minute*60+second);
				}
			}
			else if(input.matches("(\\d+).(\\d+)")){
				
				String[] splits = input.split("\\.");
				if(null!=splits&&splits.length==2){
					
					int second=Integer.parseInt(StringUtils.removeStart(splits[0], "0"));
					result=Integer.toString(second);
				}
			}
			else{
				throw new WrongRemainTimeFormatException("错误的RemainTime格式:"+input);
			}
		}else{
			result = "";
		}
		return result;
	}
}
