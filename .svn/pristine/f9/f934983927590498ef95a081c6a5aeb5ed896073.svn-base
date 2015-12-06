package com.xhcms.lottery.pb.interceptor;

import java.io.StringWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.xhcms.lottery.pb.util.FileUtil;
import com.xhcms.lottery.pb.util.MD5Util;
import com.xhcms.lottery.pb.util.VelocityUtil;

/**
 * 用户认证
 * 
 * @author Hassan
 */
public class VerifyUserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(VerifyUserInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ctx
				.get(StrutsStatics.HTTP_REQUEST);
		String msgTpye = request.getHeader(Constants.MSG_TYPE);

		String partnerId = request.getHeader(Constants.PARTNER_ID);
		String key = Constants.PARTNER_KEYS.get(partnerId);
		String bodyContent = FileUtil.inputStreamToString(
				request.getInputStream()).trim();
		String signature = request.getHeader(Constants.SIGNATURE);

		logger.info("用户名:{}", partnerId);
		logger.info("收到一条请求消息，内容:\n{}", bodyContent);
		logger.info("消息类型:{}", msgTpye);
		logger.info("客户端签名:{}", signature);

		if (StringUtils.isBlank(signature) 
				|| StringUtils.isBlank(msgTpye)
				|| StringUtils.isBlank(partnerId)
				|| StringUtils.isBlank(bodyContent) 
				|| StringUtils.isBlank(key)) {
			this.errorMsg(partnerId,"403","非法访问");
			return "error";
		}

		String sigBody = partnerId + key + bodyContent;
		logger.info("被签名体:{}", sigBody);
		logger.info("被签名体字节数:{}", sigBody.getBytes().length);
		String serverGeneSign = MD5Util.md5(sigBody);
		logger.info("服务器生成签名:{}", serverGeneSign);

		if (serverGeneSign.equals(signature)) {//检验签名
			if (MESSAGE_TYPE.isMineMember(msgTpye)) {//检验消息是否被系统支持
				request.setAttribute(Constants.BODY_CONTENT, bodyContent);
				return invocation.invoke();
			} else {
				this.errorMsg(partnerId,"404","消息类型不正确");
				return "error";
			}
		} else {
			this.errorMsg(partnerId,"403","非法访问");
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
		logger.info("服务器端错误息响应消息体：\n{}",rspXml);
		return rspXml;
	}
}