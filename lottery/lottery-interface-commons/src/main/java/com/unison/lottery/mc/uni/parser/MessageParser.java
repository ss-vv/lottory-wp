package com.unison.lottery.mc.uni.parser;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.InterfaceConfig;
import com.unison.lottery.mc.uni.ResponseMessage;
import com.xhcms.lottery.utils.EncodingUtils;


/**
 * 中民接口消息解析器基础类。
 * @author Yang Bo
 * @version 1.0.0
 */
public abstract class MessageParser implements Serializable {

    private static final long serialVersionUID = 4027035776612695547L;

    public static final int EXCEPTION_CODE = 999;	// 异常transcode

    private final String TRANSCODE = "transcode";
    private final String MSG = "msg";
    private final String KEY = "key";
    private final String PARTNERID = "partnerid";
    private final String AMP = "&";
	private final String EQUAL = "=";
	private final int RESPONSE_PARAM_NUM = 4;
    
    protected Logger logger = LoggerFactory.getLogger(getClass());

    // 期待的正确交易代码
    private int expectedTransCode;

    private InterfaceConfig config;

    /**
     * 解析响应数据.
     * @param resp 响应数据
     * @param status 
     * @return 交易代码，或者错误交易代码。
     * @throws ParseException 格式错误
     */
    public int parse(String resp, ParserStatus status) throws ParseException {
    	logger.debug("resp={}",resp);
    	ResponseMessage responseMessage = splitResponse(resp);
    	logger.debug("ResponseMessage: {}", responseMessage);
    	checkKey(responseMessage);
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");
        int code = EXCEPTION_CODE;
        try {
            Document doc = reader.read(new ByteArrayInputStream(
            		responseMessage.getMsg().getBytes("utf-8")));
            Element root = doc.getRootElement();
            code = parseHead(root);
            if (code == EXCEPTION_CODE){
            	return parseExceptionMessage(root);
            }
            if (expectedTransCode != code) {
            	logger.error("Transcode is not same as expected. actual: {}, expected: {}",
            			code, expectedTransCode);
                return code;
            }
            parseBody(root.element("body"), status);
        } catch (Exception e) {
            logger.error("Can not parse response!", e);
            logger.error("Fail to parse response: {}", resp);
        }
        return code;
    }

	/**
     * 拆分响应消息.因为没有对参数url encoding所以要特别小心。
     * @param resp
     * @return
     * @throws ParseException 格式错误
     */
    private ResponseMessage splitResponse(String resp) throws ParseException {
    	try{
    		return splitResponseByOrder(resp);
    	}catch(Exception e){
    		logger.error("Can not splitResponseByOrder, Try other method.", e);
    		return splitResponseByAmps(resp);
    	}
	}

    /**
     * 按照中民文档定义的参数顺序解析.
     * @throws ParseException 格式错误
     */
	private ResponseMessage splitResponseByOrder(String response) throws ParseException {
		int endIndex = 0;
		int beginIndex = 0;
		int order = 0;
		
		String[] paramsInOrder = new String[]{TRANSCODE, MSG, KEY, PARTNERID};
		String[] nextParamsInOrder = new String[]{AMP+TRANSCODE+EQUAL, AMP+MSG+EQUAL, 
				AMP+KEY+EQUAL, AMP+PARTNERID+EQUAL};
		String[] valuesInOrder = new String[paramsInOrder.length];
		
		for (String param : paramsInOrder) {
			if (!response.startsWith(param, beginIndex)){
				throw new ParseException("ZM Response format wrong. expected: " + 
						param + ", but is: " + response.substring(beginIndex));
			}
			for (String nextParam : nextParamsInOrder){
				endIndex = response.indexOf(nextParam, beginIndex);
				if (endIndex != -1){
					break;
				}
			}
			String onePart = null;
			if (endIndex==-1){
				endIndex = response.length();
			}
			onePart = response.substring(beginIndex, endIndex);
			valuesInOrder[order] = onePart.substring(onePart.indexOf(EQUAL)+1);
			order++;
			beginIndex = endIndex+1;
		}
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setTranscode(valuesInOrder[0]);
		responseMessage.setMsg(valuesInOrder[1]);
		responseMessage.setKey(valuesInOrder[2]);
		responseMessage.setParterid(valuesInOrder[3]);
		return responseMessage;
	}
	
	/**
	 * 按照 '&'拆分
	 * @throws ParseException 格式错误
	 */
	private ResponseMessage splitResponseByAmps(String resp) throws ParseException {
		String parts[] = resp.split(AMP);
		if (parts.length != RESPONSE_PARAM_NUM ){
			throw new ParseException("ZM Response format wrong, not 4 parameters: " + resp);
		}
		ResponseMessage message = new ResponseMessage();
		for (String onePart : parts){
			int equalIndex = onePart.indexOf(EQUAL);
			String name = onePart.substring(0, equalIndex).trim();
			String value = onePart.substring(equalIndex+1);
			if (name.equalsIgnoreCase(TRANSCODE)){
				message.setTranscode(value);
			}
			if (name.equalsIgnoreCase(MSG)){
				message.setMsg(value);
			}
			if (name.equalsIgnoreCase(KEY)){
				message.setKey(value);
			}
			if (name.equalsIgnoreCase(PARTNERID)){
				message.setParterid(value);
			}
		}
		return message;
	}

	private void checkKey(ResponseMessage responseMessage) throws ParseException {
		StringBuilder builder = new StringBuilder();
		builder.append(responseMessage.getTranscode())
			.append(responseMessage.getMsg()).append(config.getKey());
		String targetKey = StringUtils.EMPTY;
		try {
			targetKey = EncodingUtils.md5StringWithUTF8(builder.toString());
		} catch (UnsupportedEncodingException e) {
			throw new ParseException(e);
		}
		String key = responseMessage.getKey();
		if (!StringUtils.isBlank(key) && targetKey.compareToIgnoreCase(key) != 0){
			logger.error("Key is invalid! key={}, expectedKey={}", key, targetKey);
			logger.error("Config key is: {}", config.getKey());
			throw new ParseException("Key is invalid!");
		}
	}

	/**
     * 解析响应体
     * @param body
	 * @param status 
     */
    public abstract void parseBody(Element body, ParserStatus status) throws ParseException;

    private int parseHead(Element root) {
        int code;
        Element head = root.element("head");
        try {
            code = Integer.parseInt(head.attributeValue("transcode"));
        } catch (NumberFormatException e) {
        	logger.error("Parse transcode in header error!", e);
            code = EXCEPTION_CODE;
        }
        return code;
    }

    private int parseExceptionMessage(Element root) {
    	Element error = root.element("body").element("error");
        int code = Integer.parseInt(error.attributeValue("transcode"));
        logger.error("exception msg transcode: {}, msg: {}", 
        		code, error.attributeValue("message"));
        return code;
	}

	public int getExpectedTransCode() {
		return expectedTransCode;
	}

	public void setExpectedTransCode(int expectedTransCode) {
		this.expectedTransCode = expectedTransCode;
	}

	public InterfaceConfig getConfig() {
		return config;
	}

	public void setConfig(InterfaceConfig config) {
		this.config = config;
	}
}
