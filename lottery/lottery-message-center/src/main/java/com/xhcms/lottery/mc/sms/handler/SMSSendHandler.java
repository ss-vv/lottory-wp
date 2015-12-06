package com.xhcms.lottery.mc.sms.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.lottery.mc.sms.client.SMSSendClient;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class SMSSendHandler implements MessageHandler<SMSSendMessage> {

    private static final long serialVersionUID = -6450014246179186697L;

    private static final Logger logger = Logger.getLogger(SMSSendHandler.class);

    private SMSSendClient client;

    @Override
    public void handle(SMSSendMessage sm) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", sm.getMobile());
        params.put("msg_content", sm.getContent());
        params.put("corp_msg_id", sm.getMsgId());
        params.put("ext", sm.getExt());
        logger.info(client.post(params));
    }

    public void setClient(SMSSendClient client) {
        this.client = client;
    }

}
