package com.xhcms.lottery.mc.jc;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.util.Text;
import com.xhcms.lottery.commons.client.HttpClient;

/**
 * 
 * <p>Title: 竞彩类通讯客户端</p>
 * <p>Description: 包括竞彩篮球和足球</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public abstract class JCClient {
    private static final int REPEAT = 0;
    private static final int NEXT = 1;
    private static final int STOP = 2;

    private Logger log = LoggerFactory.getLogger(getClass());

    private String url;

    private String key;

    private String transcode;

    private String version;

    private String partnerId;

    private String template;

    private JCParser parser;

    static {
        Velocity.addProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.addProperty("file.resource.loader.path", "classpath");
        Velocity.addProperty("file.resource.loader.cache", "false");
        Velocity.addProperty("file.resource.loader.modificationCheckInterval", "2");

        Velocity.init();
    }

    /**
     * 发送POST请求
     * @param values 请求参数
     * @return
     */
    public boolean post(Map<String, Object> values) {
        int code = NEXT;
        for (int i = 0; i < 3; i++) {
            code = send(values);
            if (code == NEXT) {
                return true;
            }
            if (code == STOP) {
                return false;
            }
        }
        return true;
    }

    private int send(Map<String, Object> values) {
        try {
        	long start = System.currentTimeMillis();
            String resp = new HttpClient().post(url, wrap(values));
            long costTime = System.currentTimeMillis() - start;
            log.debug("url:{}, PostTime:{} ms", url, costTime);
            return proceed(parser.parse(resp));
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return NEXT;
    }

    private int proceed(int code) {
        switch (code) {
            case 9001:
                return REPEAT;
            case 9003:
            case 9004:
            case 9005:
            case 9007:
                return STOP;
            case 9002:
            case 9006:
            default:
                return NEXT;
        }
    }

    private String wrap(Map<String, Object> values) {
        VelocityContext ctx = new VelocityContext();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            ctx.put(e.getKey(), e.getValue());
        }

        ctx.put("transcode", transcode);
        ctx.put("partnerid", partnerId);
        ctx.put("version", version);
        ctx.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        StringWriter sw = new StringWriter();
        Velocity.getTemplate(template, "utf-8").merge(ctx, sw);

        String msg = sw.toString();
        return Text.MD5Encode(msg + key).toUpperCase() + msg;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public void setParser(JCParser parser) {
        this.parser = parser;
    }

}
