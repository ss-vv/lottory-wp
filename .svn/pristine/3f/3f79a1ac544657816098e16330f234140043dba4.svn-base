package com.xhcms.lottery.commons.client.translate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.xhcms.lottery.lang.LotteryPlatformId;

/**
 * 多平台投注内容工具类
 * @author lei.li@davcai.com
 */
public class MultiPlatformBetCodeUtil {

	/**多个平台投注内容分隔符**/
	public static final String SEP_PLATFORM = "#";
	/**平台和投注内容之间的分隔符**/
	public static final String SEP_PLATFORM_CONTENT = ">";
	private static final String format = "%s" + SEP_PLATFORM_CONTENT + "%s" + SEP_PLATFORM;
	
	/**返回出票平台数据*/
	public static String[] platforms() {
		return new String[]{
			LotteryPlatformId.ZUN_AO,
			LotteryPlatformId.AN_RUI_ZHI_YING
		};
	}
	
	/**
	 * 组装多个出票平台投注内容格式串
	 * @param platform
	 * @param betCode
	 * @return
	 */
	public static String format(Map<String, String> map) {
		StringBuilder buf = new StringBuilder();
		if(null != map && map.size() > 0) {
			Set<String> keys = map.keySet();
			Iterator<String> iter = keys.iterator();
			while(iter.hasNext()) {
				String key = iter.next();
				String val = map.get(key);
				if(StringUtils.isNotBlank(key) && StringUtils.isNotBlank(val)) {
					String result = String.format(format, key, val);
					buf.append(result);
				}
			}
			return buf.subSequence(0, buf.length() - 1).toString();
		}
		return buf.toString();
	}
	/**
	 * 将字符串转换为多个出票平台投注内容格式map
	 * @param str
	 * @return
	 */
	public static Map<String, String> parse(String str){
		if(StringUtils.isBlank(str)){
			return null;
		}
		Map<String, String> result=null;
		String[] str4Platforms = str.split(SEP_PLATFORM);
		if(null!=str4Platforms&&str4Platforms.length>=1){
			result=new HashMap<String, String>();
			for(String betCodeStr:str4Platforms){
				if(StringUtils.isNotBlank(betCodeStr)){
					String[] split = betCodeStr.split(SEP_PLATFORM_CONTENT);
					if(null!=split&&split.length==2){
						result.put(split[0],split[1]);
					}
				}
			}
		}
		return result;
		
	}
}