package com.unison.lottery.itf;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.parser.MessageParser;
import com.unison.lottery.mc.uni.parser.ParseException;
import com.unison.lottery.mc.uni.parser.ParserStatus;

/**
 * 通知处理器.
 * @author Yang Bo
 */
public abstract class NotifyProcessor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected MessageParser msgParser;

	/**
	 * 生成错误对象，用于返回给平台。
	 * @param error 解析异常
	 * @return 错误对象，是“ProcessResults”的特例对象。
	 */
	protected abstract ProcessResult<?> generateErrorResult(ParseException error);

	/**
	 * 处理接收到的信息。
	 * @param ret 解析器返回状态值。
	 * @param status 存放有解析结果的状态对象
	 * @return 处理结果对象，用于反馈给彩票接口平台
	 */
	protected abstract ProcessResult<?> process(int ret, ParserStatus status);

	/**
	 * 创建一个解析器状态对象，对应特定的消息。
	 * @return
	 */
	protected abstract ParserStatus generateParserStatus();
	
	protected abstract ProcessResult<?> generateProcessResult();
	
	
	public ProcessResult<?> process(String msg){
		if (StringUtils.isBlank(msg)){
			throw new IllegalArgumentException("msg is empty!");
		}
		ParserStatus status = generateParserStatus();
		try {
			int ret = msgParser.parse(msg, status);
			logger.debug("Parse Message success, status is: {}", status);
			ProcessResult<?> result = process(ret, status);
			result.prepareReturnMessage();
			return result;
		} catch (ParseException e) {
			logger.error("Parse message failed!", e);
			return generateErrorResult(e);
		}
	}

	public MessageParser getMsgParser() {
		return msgParser;
	}

	public void setMsgParser(MessageParser msgParser) {
		this.msgParser = msgParser;
	}
}
