package com.xhcms.lottery.mc.sms.client;

import java.util.HashMap;
import java.util.Map;

import com.xhcms.lottery.commons.client.HttpClient;

/**
 * <p>Title: 短信发送</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class SMSSendClient {

    private String corpId;

    private String corpPwd;

    private String corpService;

    private String url;

    public String post(Map<String, Object> values) {
        Map<String, Object> params = new HashMap<String, Object>();
        for (Map.Entry<String, Object> e : values.entrySet()) {
            params.put(e.getKey(), e.getValue());
        }
        params.put("corp_id", corpId);
        params.put("corp_pwd", corpPwd);
        params.put("corp_service", corpService);
        HttpClient client = new HttpClient();
        client.setCharset("gbk");
        return client.post(url, params);
    }

    public void setCorpPwd(String corpPwd) {
        this.corpPwd = corpPwd;
    }

    public void setCorpService(String corpService) {
        this.corpService = corpService;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
