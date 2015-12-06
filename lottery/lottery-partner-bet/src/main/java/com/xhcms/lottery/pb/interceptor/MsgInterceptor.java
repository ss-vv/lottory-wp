package com.xhcms.lottery.pb.interceptor;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.struts2.StrutsStatics;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.xhcms.lottery.pb.model.Constants;
import com.xhcms.lottery.pb.model.MESSAGE_TYPE;
import com.xhcms.lottery.pb.util.DateTimeUtil;
import com.xhcms.lottery.pb.util.JAXBContextUtil;
import com.xhcms.lottery.pb.util.MD5Util;
import com.xhcms.lottery.pb.util.VelocityUtil;
import com.xhcms.lottery.pb.xml.model.Msg;

public class MsgInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(MsgInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(StrutsStatics.HTTP_REQUEST);
		String bodyContent = (String) request.getAttribute(Constants.BODY_CONTENT);
		String partnerId = request.getHeader(Constants.PARTNER_ID);
		
		JAXBContext jaxbContext = JAXBContextUtil.getJaxbContext();
		Unmarshaller um = jaxbContext.createUnmarshaller();
		if (null != um) {
			Msg msg = (Msg) um.unmarshal(new StringReader(bodyContent));
			request.setAttribute(Constants.REQUESR_MSG, msg);
			return invocation.invoke();
		} else {
			logger.error("投注请求 bodyContent:{} \n 未完成解析",bodyContent);
			this.errorMsg(partnerId,"500","系统错误");
			return "error";
		}
	}
	
	private void errorMsg(String partnerId,String code,String msg) {
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
	}

	private String getErrorResponseXml(String partnerId,String timeString,String code,String msg) {
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
}
