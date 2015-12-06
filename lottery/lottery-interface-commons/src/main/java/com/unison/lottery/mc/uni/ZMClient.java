package com.unison.lottery.mc.uni;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.parser.MessageParser;
import com.unison.lottery.mc.uni.parser.ParserStatus;
import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.client.HttpClient;

/**
 * 中民接口客户端基础类。
 * @author Yang Bo
 * @version 1.0.0
 */
public abstract class ZMClient {
    private static final int REPEAT = 0;	// 重试
    private static final int NEXT = 1;		// 停止3次重复的尝试，并返回成功
    private static final int STOP = 2;		// 停止3次重复的尝试，并返回失败
    
    // 中民新接口需要特别指定 content-type，否则接收失败
    private static final String CONTENT_TYPE = "text/xml";

    private Logger log = LoggerFactory.getLogger(getClass());

    private String url;

    private String key;

    private String version;

    private String transcode;

    private String partnerId;

    private String template;

    private MessageParser parser;

    static {
        Velocity.addProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.addProperty("file.resource.loader.path", "classpath");
        Velocity.addProperty("file.resource.loader.cache", "false");
        Velocity.addProperty("file.resource.loader.modificationCheckInterval", "2");

        Velocity.init();
    }

    protected boolean postWithStatus(Map<String, Object> values, ParserStatus status) {
    	int code = NEXT;
        for (int i = 0; i < 3; i++) {
            code = send(values, status);
            if (code == NEXT) {
                return true;
            }
            if (code == STOP) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 发送POST请求
     * @param values 请求参数
     * @return
     */
    protected boolean post(Map<String, Object> values) {
        return postWithStatus(values, null);
    }

    private int send(Map<String, Object> values, ParserStatus status) {
        try {
        	long start = System.currentTimeMillis();
        	Map<String,Object> params = wrap(values);
            String resp = new HttpClient().postNoEncodingWithContentType(url, params, CONTENT_TYPE);
           
            long costTime = System.currentTimeMillis() - start;
            log.debug("url:{}, PostTime:{} ms", url, costTime);
            log.debug("request params: {}\nresponse: {}", params, resp);
            if (StringUtils.isBlank(resp)){
            	log.error("Response is empty!");
            	return REPEAT;
            }
            int errorCode = parser.parse(resp, status);
            if (status != null){
            	status.setErrorCode(errorCode);
            }
            return proceed(errorCode);
        } catch (Exception e) {
            log.error("send fail: {}", e.toString(), e);
        }
        return NEXT;
    }

    private int proceed(int code) {
    	if (code < 900){
    		return NEXT;
    	}
        switch (code) {
            case 999:	// 系统异常
                return REPEAT; // 重试
        	/**
        	 * 原來星匯公司的實現對下面的情况返回 STOP
        	 * <pre>
        	 *  9003 身份无效
        	 * 	9004 账户余额不足
        	 * 	9005 期次无效或未开启接收数据
        	 * 	9006 一次请求的订单数不正确
        	 * 	9007 彩票名称编号不正确
        	 * </pre>
        	 */
            case 901:	// 无此用户
            case 908:	// 账户余额不足
            case 919:	// 密钥匹配错误
            	return STOP; // STOP 是不重试,但中断发送本次BuyTicketMessage消息的其他票。
            default:
            	return NEXT;
        }
    }

    private Map<String,Object> wrap(Map<String, Object> values) {
        VelocityContext ctx = new VelocityContext();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            ctx.put(e.getKey(), e.getValue());
        }

        ctx.put("transcode", transcode);
        ctx.put("partnerid", partnerId);
        ctx.put("version", version);
        ctx.put("time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        StringWriter sw = new StringWriter();
        Velocity.getTemplate(template, "utf-8").merge(ctx, sw);

        String msg = sw.toString();
        log.debug("msg={}",msg);
        // 保持顺序很重要，因为中民接口设计缺陷，导致需要依赖参数的顺序。
        Map<String,Object> params = new LinkedHashMap<String, Object>();
        params.put("transcode", transcode);
        params.put("msg", msg);
        StringBuffer plainText = new StringBuffer(transcode).append(msg).append(key);
        params.put("key", Text.MD5Encode(plainText.toString()).toLowerCase());
        params.put("partnerid", partnerId);
        return params;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    																																																																																														
    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setParser(MessageParser parser) {
        this.parser = parser;
    }

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public MessageParser getParser() {
		return parser;
	}

}
