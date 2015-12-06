package com.xhcms.lottery.pb.action;

import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.util.DateTimeUtil;
import com.xhcms.lottery.pb.util.MD5Util;
import com.xhcms.lottery.pb.util.VelocityUtil;

public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	protected String version = Constants.API_VERSION;
	protected String time = "";
	protected String reqTime = "";
	protected String partnerId = "";
    protected HttpServletRequest request;  
    protected HttpServletResponse response;
    public BaseAction(){
    	initTime();
    }    
    @Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;  
	}
	
	public HttpServletResponse getRespone() {
		return response;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	
	protected String errorMsg(String partnerId,String code,String msg) {
		String timeString = DateTimeUtil.getTimeString(new Date());
		HttpServletResponse response = (HttpServletResponse) ActionContext
				.getContext().get(StrutsStatics.HTTP_RESPONSE);
		response.setHeader(Constants.PARTNER_ID, partnerId);
		response.setHeader(Constants.MSG_TYPE,MESSAGE_TYPE.INVALID_MSG.getCode());
		response.setHeader(Constants.SIGNATURE,
				MD5Util.md5(partnerId + Constants.PARTNER_KEYS.get(partnerId)
						+ getErrorResponseXml(partnerId,timeString,code,msg).trim()));
		
		ValueStack vs = ActionContext.getContext().getValueStack();
		vs.set("partnerId", partnerId);
		vs.set("time", timeString);
		vs.set("version", Constants.API_VERSION);
		vs.set("code", code);
		vs.set("msg", msg);
		return ERROR;
	}

	protected String getErrorResponseXml(String partnerId,String timeString,String code,String msg) {
		VelocityContext vContext = new VelocityContext();
		vContext.put("partnerId", partnerId);
		vContext.put("time", timeString);
		vContext.put("version", Constants.API_VERSION);
		vContext.put("code", code);
		vContext.put("msg", msg);
		Template vTemplate = VelocityUtil.getVelocityEngine().getTemplate(
				"../vm/error.xml");
		StringWriter writer = new StringWriter();
		vTemplate.merge(vContext, writer);
		String rspXml = writer.toString().trim();
		return rspXml;
	}
	
	protected String getSignatureMD5(VelocityContext velocityContext,String tmplPath){
		return MD5Util.md5(this.partnerId
				+ Constants.PARTNER_KEYS.get(this.partnerId) 
				+ getResponseXml(velocityContext,tmplPath).trim());
	}
	
	protected String getResponseXml(VelocityContext velocityContext,String tmplPath){
		Template vTemplate = VelocityUtil.getVelocityEngine().getTemplate(tmplPath);
		StringWriter writer = new StringWriter();
		vTemplate.merge(velocityContext, writer);
		String rspXml = writer.toString().trim();
		logger.info("服务器端投注响应消息体：\n{}",rspXml);
		return rspXml;
	}
	
	public String getVersion() {
		return version;
	}
	public void initTime() {
		this.time = DateTimeUtil.getTimeString(new Date());
	}
	public String getPartnerId() {
		return partnerId;
	}
	public String getTime() {
		return time;
	}
	
	protected String getIPaddress(){
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
}
