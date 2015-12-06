package com.xhcms.lottery.mc.jc;

import java.io.Serializable;

import java.io.ByteArrayInputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>Title: 竞彩类响应的返回数据（XML）解析器</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public abstract class JCParser implements Serializable {

    private static final long serialVersionUID = 4027008776604695547L;

    public static final int ERROR_CODE = 9999;

    protected Logger log = LoggerFactory.getLogger(getClass());

    // 有效的交易代码
    private int transCode;

    /**
     * 解析响应数据
     * @param resp 响应数据
     * @return 交易代码
     */
    public int parse(String resp) {
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");

        try {
            Document doc = reader.read(new ByteArrayInputStream(resp.getBytes("utf-8")));
            Element root = doc.getRootElement();
            int code = parseCode(root);
            if (transCode != code) {
                return code;
            }

            parseBody(root.element("body"));
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

        return transCode;
    }

    /**
     * 解析响应体
     * @param body
     */
    public abstract void parseBody(Element body);

    private int parseCode(Element root) {
        int code;
        Element head = root.element("head");
        try {
            if(head.hasContent()){
                code = Integer.parseInt(head.elementText("transcode"));
                if (code == ERROR_CODE) {
                    Element msg = root.element("body").element("msg");
                    code = Integer.parseInt(msg.attributeValue("errorCode"));
                    if(log.isDebugEnabled()){
                        log.debug("errorCode:" + code + ", msg:" + msg.attributeValue("msg"));
                    }
                }
            }else{
                code = Integer.parseInt(head.attributeValue("transcode"));
            }            
        } catch (NumberFormatException e) {
            code = ERROR_CODE;
        }
        return code;
    }

    public void setTransCode(int transCode) {
        this.transCode = transCode;
    }

}
