package com.unison.lottery.api.framework.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.log.model.ActivityLog;
import com.unison.lottery.api.framework.log.model.DetailLog;
import com.unison.lottery.api.framework.log.model.RequestContent;
import com.unison.lottery.api.framework.log.model.ResponseContent;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;
import com.xhcms.commons.util.Text;

public class JsonLogManager implements ILogManager {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	private List<String> propertiesShouldEncodeByMD5;
	private String valuePattern="=\"([^\"]+)\"";
	

	@Override
	public void recordActivityDetailLog(HttpServletRequest httpRequest) {
		DetailLog detailLog=(DetailLog) httpRequest.getAttribute(Constants.DETAIL_LOG_NAME);
		User userAfterLogin=null;
		try{
			userAfterLogin=(User) httpRequest.getAttribute(VONames.USER_AFTER_LOGIN_VO_NAME);

		}
		catch(Exception e){
			e.printStackTrace();
		}

		if(null!=detailLog){
			ActivityLog activityLog=makeActivityLog(detailLog,userAfterLogin);
			String logStr=ActivityLog.toJsonString(activityLog);
			logStr=filterSecurityProperty(logStr);
			logger.info(logStr);
		}
		
		
	}

	

	public String filterSecurityProperty(String logStr) {
		if(null!=propertiesShouldEncodeByMD5&&!propertiesShouldEncodeByMD5.isEmpty()){
			for(String property:propertiesShouldEncodeByMD5){
				logStr=replacePropertyAndValueWithMD5(logStr,property);
			}
		}
		return logStr;
	}



	private String replacePropertyAndValueWithMD5(String logStr, String property) {
		String propertyAndValuePattern=makePropertyAndValuePattern(property);
		Pattern pattern=Pattern.compile(propertyAndValuePattern,Pattern.DOTALL|Pattern.MULTILINE);
		Matcher matcher=pattern.matcher(logStr);
		if(matcher.matches()){
			String replacement = makeReplacement(property, matcher);
			logStr=logStr.replaceAll(makeReplacePattern(property,matcher.group(1)), replacement);
		}
		
		return logStr;
	}



	private String makeReplacePattern(String property, String value) {
		
		return property+"="+"\""+value+"\"";
	}



	private String makeReplacement(String property, Matcher matcher) {
		String replacement=matcher.group(1);
		replacement=property+"="+"\""+Text.MD5Encode(replacement)+"\"";
		return replacement;
	}



	private String makePropertyAndValuePattern(String property) {
		
		
		return ".*"+property+valuePattern+".*";
	}



	private ActivityLog makeActivityLog(DetailLog detailLog, User userAfterLogin) {
		ActivityLog log=new ActivityLog();
		MethodRequest methodRequest=detailLog.getRequestObj();
		if(null!=userAfterLogin){
			log.userId=userAfterLogin.getId();
		}
       	
       	log.validId=methodRequest.validId;
        log.deviceId=methodRequest.deviceId;
        log.methodType=methodRequest.actionType;
        log.methodName=methodRequest.name;
        log.modelName=methodRequest.modelName;
        log.clientVersion=methodRequest.clientVersion;
        log.channel=methodRequest.channel;
        Date data=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String time=df.format(data);
        log.time=time;
        log.requestContent=new RequestContent();
        log.responseContent=new ResponseContent();
        log.requestContent.methodRequest=methodRequest;
        log.responseContent.response=detailLog.getResponseObj();
        return log;
	
		
	}



	public List<String> getPropertiesShouldEncodeByMD5() {
		return propertiesShouldEncodeByMD5;
	}



	public void setPropertiesShouldEncodeByMD5(
			List<String> propertiesShouldEncodeByMD5) {
		this.propertiesShouldEncodeByMD5 = propertiesShouldEncodeByMD5;
	}



	public String getValuePattern() {
		return valuePattern;
	}



	public void setValuePattern(String valuePattern) {
		this.valuePattern = valuePattern;
	}

}
