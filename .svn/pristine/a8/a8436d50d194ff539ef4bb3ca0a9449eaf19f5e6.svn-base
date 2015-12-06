package com.davcai.data.statistic.task.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

public class PlayOption {
	private String playId;//玩法id
	private int minCountOfChar;//一个选项最少由几个字符组成
	private Map<String,Integer> optionCodeMap=new HashMap<String,Integer>();//各选项二进制编码
	private int allOptionAppearCode;//所有选项均出现时的二进制编码
	public int getMinCountOfChar() {
		return minCountOfChar;
	}
	public void setMinCountOfChar(int minCountOfChar) {
		this.minCountOfChar = minCountOfChar;
	}
	public Map<String, Integer> getOptionCodeMap() {
		return optionCodeMap;
	}
	public void setOptionCodeMap(Map<String, Integer> optionCodeMap) {
		this.optionCodeMap = optionCodeMap;
	}
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public int getAllOptionAppearCode() {
		return allOptionAppearCode;
	}
	public void setAllOptionAppearCode(int allOptionAppearCode) {
		this.allOptionAppearCode = allOptionAppearCode;
	}
	/**
	 * 根据选项出现字符串计算选项出现二进制代码
	 * @param optionAppearString
	 * @return
	 */
	public int computeOptionAppear(String optionAppearString) {
		int result=0;
		if(StringUtils.isNotBlank(optionAppearString)){
			if(minCountOfChar==1){
				char[] chars = optionAppearString.toCharArray();
				if(null!=chars&&chars.length>0){
					String key;
					for(char c:chars){
						key=String.valueOf(c);
						if(optionCodeMap.containsKey(key)){
							result=result|optionCodeMap.get(key);
						}
					}
				}
			}
		}
		
		return result;
	}
	/**
	 * 判断是否为“全包”的二进制代码
	 * @param optionAppearCode
	 * @return
	 */
	public boolean isAllApearCode(int optionAppearCode) {
		
		return optionAppearCode==allOptionAppearCode;
	}
	

}
