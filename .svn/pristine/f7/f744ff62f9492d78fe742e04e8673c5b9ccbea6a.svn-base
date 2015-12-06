package com.davcai.lottery.platform.client.anruizhiying.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.davcai.lottery.platform.client.anruizhiying.constants.ParamNames;

public class AnRuiZhiYingUrlUtil {

	private static List<String> shouldIgnoreParamNames=new ArrayList<String>();
	static{
		shouldIgnoreParamNames.add(ParamNames.CHANNEL_ID);
		shouldIgnoreParamNames.add(ParamNames.SIGN);
		shouldIgnoreParamNames.add(ParamNames.TRANSCODE);
	}

	/**
	 * 根据提供的params生成最终的参数字符串，但params中的transCode、ChannelID和Sign不起作用，而是以参数列表中的transCode，channelId及由程序计算
	 * 出的sign值为准
	 * @param interfaceUrl
	 * @param transCode
	 * @param channelId
	 * @param params
	 * @param paramsShouldSign
	 * @param key 
	 * @return
	 */
	public static String makeFinalParamStringWithSign(
			String transCode, String channelId, Map<String, Object> params, LinkedHashSet<String> paramsShouldSign, String key) {
		if (checkFail( transCode, channelId, paramsShouldSign, key,params)) {
			return null;
		}
		List<String> toSignStrings = makeToSignStrings(channelId, params,
				paramsShouldSign);
		String sign=AnRuiZhiYingSignUtil.sign(toSignStrings,key);
		if(StringUtils.isNotBlank(sign)){
			StringBuilder sb = makeFinalUrl( transCode, channelId,
					params, sign);
			return sb.toString();
		}
		
		return null;
	}

	private static boolean checkFail(String transCode,
			String channelId, LinkedHashSet<String> paramsShouldSign, String key, Map<String, Object> params) {
		return  StringUtils.isBlank(transCode)
				|| StringUtils.isBlank(channelId) || StringUtils.isBlank(key)
				|| null == paramsShouldSign || paramsShouldSign.isEmpty()
				||null==params||params.isEmpty();
	}

	private static StringBuilder makeFinalUrl(
			String transCode, String channelId, Map<String, Object> params,
			String sign) {
		StringBuilder sb=new StringBuilder();
		sb.append(ParamNames.TRANSCODE);
		sb.append("=");
		sb.append(transCode);
		if(null!=params&&!params.isEmpty()){
			
			for (Entry<String, Object> param : params.entrySet()) {
				if (!shouldIgnoreParamNames.contains(param.getKey())
						&& null != param.getValue()
						&& StringUtils.isNotBlank(param.getValue()
								.toString())) {
					addUrlParam(param.getKey(),
							param.getValue().toString(), sb);
				}
			}
		}
		addUrlParam(ParamNames.CHANNEL_ID,channelId, sb);
		addUrlParam(ParamNames.SIGN,sign, sb);
		return sb;
	}

	private static List<String> makeToSignStrings(String channelId,
			Map<String, Object> params, LinkedHashSet<String> paramsShouldSign) {
		List<String> toSignStrings=new ArrayList<String>();
		toSignStrings.add(channelId);
		for (String paramShouldSign : paramsShouldSign) {
			if (StringUtils.isNotBlank(paramShouldSign)
					&& null != params.get(paramShouldSign)
					&& !shouldIgnoreParamNames.contains(paramShouldSign)) {
				toSignStrings.add(params.get(paramShouldSign).toString());
			}
		}
		return toSignStrings;
	}

	private static void addUrlParam(String key,String value, StringBuilder sb) {
		sb.append("&");
		sb.append(key);
		sb.append("=");
		sb.append(value.replace("+", "%2b"));
	}

}
