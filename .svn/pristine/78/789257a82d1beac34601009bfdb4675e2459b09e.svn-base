package com.unison.lottery.itf;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.mc.uni.parser.ParseException;
import com.unison.lottery.mc.uni.parser.ParserStatus;

/**
 * 不需要给中民返回响应信息的处理类
 * @author 陈岩
 *
 */
public abstract class NotifyWithoutResult4ZMProcesser extends NotifyProcessor {

	
	
	public ProcessResult<?> process(String msg){
		if (StringUtils.isBlank(msg)){
			throw new IllegalArgumentException("msg is empty!");
		}
		ParserStatus status = generateParserStatus();
		try {
			int ret = msgParser.parse(msg, status);
			logger.debug("Parse Message success, status is: {}", status);
			ProcessResult<?> result = process(ret, status);
			//result.prepareReturnMessage();
			return result;
		} catch (ParseException e) {
			logger.error("Parse message failed!", e);
			return generateErrorResult(e);
		}
	}

}
