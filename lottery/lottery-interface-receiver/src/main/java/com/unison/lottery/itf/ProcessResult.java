package com.unison.lottery.itf;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import com.unison.lottery.mc.uni.InterfaceConfig;
import com.unison.lottery.mc.uni.ResponseMessage;

/**
 * 要返回给接口的处理结果，可以生成返回消息。
 * @author Yang Bo
 */
public abstract class ProcessResult<ResultType> {
	private String returnTranscode;		// 响应消息的 transcode
	private String returnMsg;			// 响应消息的msg内容
	private String digestedKey;			// 响应消息的签名
	private String errorMsg;			// 处理失败时的错误消息
	private List<ResultType> returnResults = new LinkedList<ResultType>(); // 结果对象

	private boolean fail = false;		// 是否处理失败
	
	// configure property
	private InterfaceConfig config;
	private String errorTmplName;		// 用来产生错误消息的模板名.
	private String tmplName;			// 用来产生消息的模板名.

	private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	
    static {
        Velocity.addProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.addProperty("file.resource.loader.path", "classpath");
        Velocity.addProperty("file.resource.loader.cache", "false");
        Velocity.addProperty("file.resource.loader.modificationCheckInterval", "2");

        Velocity.init();
    }

	/**
	 * 准备返回消息中用到的各个字段。
	 */
	public void prepareReturnMessage(){
		String returnTime = timeFormat.format(new Date());
		VelocityContext ctx = prepareContext();
		ctx.put("returnTime", returnTime);
		ctx.put("config", config);
		ctx.put("returnTranscode", returnTranscode);
		ctx.put("errorMsg", errorMsg);
		ctx.put("returnResults", returnResults);
		
		returnMsg = evaluateVelocityTemplate(ctx);
		digestedKey = digestMsg();
	}
	
	/**
	 * @return velocity上下文.
	 */
	protected abstract VelocityContext prepareContext();
	
	private String evaluateVelocityTemplate(VelocityContext ctx) {
        StringWriter sw = new StringWriter();
        String template = fail ? getErrorTmplName() : getTmplName();
        Velocity.getTemplate(template, "utf-8").merge(ctx, sw);
        String msg = sw.toString();
        return msg.replaceAll("\n", "");
	}

	private String digestMsg() {
		String apiKey = config.getKey();
		return ResponseMessage.digestMessage(returnTranscode, returnMsg, apiKey);
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getReturnTranscode() {
		return returnTranscode;
	}

	public void setReturnTranscode(String returnTranscode) {
		this.returnTranscode = returnTranscode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getDigestedKey() {
		return digestedKey;
	}

	public void setDigestedKey(String digestedKey) {
		this.digestedKey = digestedKey;
	}

	public InterfaceConfig getConfig() {
		return config;
	}

	public void setConfig(InterfaceConfig config) {
		this.config = config;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorTmplName() {
		return errorTmplName;
	}

	public void setErrorTmplName(String errorTmplName) {
		this.errorTmplName = errorTmplName;
	}

	public boolean isFail() {
		return fail;
	}

	public void setFail(boolean fail) {
		this.fail = fail;
	}

	public String getTmplName() {
		return tmplName;
	}

	public void setTmplName(String tmplName) {
		this.tmplName = tmplName;
	}

	public List<ResultType> getReturnResults() {
		return returnResults;
	}
}
