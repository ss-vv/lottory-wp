package com.xhcms.lottery.mc.jc.client;

import java.util.HashMap;
import java.util.List;

import com.xhcms.lottery.mc.jc.JCClient;

/**
 * 
 * <p>Title: 竞彩彩票出票情况查询客户端</p>
 * <p>Description: 包括竞彩足球和篮球的各种玩法</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class JCTicketClient extends JCClient{
    
    public boolean post(List<Long> tickets){
        HashMap<String, Object> values = new HashMap<String, Object>();
        
        values.put("tickets", tickets);
        
        return post(values);
    }
    
}
