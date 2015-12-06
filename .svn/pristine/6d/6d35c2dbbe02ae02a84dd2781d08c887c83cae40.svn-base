package com.unison.lottery.mc.uni.client;

import java.util.HashMap;
import java.util.List;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryOrderResultStatus;

/**
 * 交易结果查询接口（003）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class QueryOrderResultClient extends ZMClient {
    
	public QueryOrderResultClient(){
		setTranscode("003");
	}
	
	/**
	 * 查询票的交易结果。
	 * @param tickets 要查询的票
	 * @param status 存放解析结果的状态
	 */
    public boolean post(List<Long> tickets, QueryOrderResultStatus status){
        HashMap<String, Object> values = new HashMap<String, Object>();
        
        values.put("tickets", tickets);
        
        return postWithStatus(values, status);
    }
    
}
