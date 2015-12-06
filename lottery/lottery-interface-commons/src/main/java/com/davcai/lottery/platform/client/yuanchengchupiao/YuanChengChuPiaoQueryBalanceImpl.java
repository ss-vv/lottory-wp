package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractQueryBalanceClient;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryBalanceResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryBalanceResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public class YuanChengChuPiaoQueryBalanceImpl extends AbstractQueryBalanceClient {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("yuanChengChuPiaoQueryBalanceSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	
	@Override
	public QueryBalanceResponse4OneLoop queryBalanceForOneLoop() {
		YuanChengChuPiaoQueryBalanceResponse resp=null;
		Map<String, Object> params = new HashMap<String,Object>();
		resp=(YuanChengChuPiaoQueryBalanceResponse)clientSupport.doPostWithRetry(params);
		return toQueryBalance4OneLoop(resp);
	}
	
	public AbstractYuanChengChuPiaoClientSupport getClientSupport() {
		return clientSupport;
	}

	public void setClientSupport(AbstractYuanChengChuPiaoClientSupport clientSupport) {
		this.clientSupport = clientSupport;
	}

	private QueryBalanceResponse4OneLoop toQueryBalance4OneLoop(YuanChengChuPiaoQueryBalanceResponse resp) {
		QueryBalanceResponse4OneLoop response4OneLoop=new QueryBalanceResponse4OneLoop();
		response4OneLoop.setError(resp.getError());
		response4OneLoop.setMessage(resp.getMessage());
		response4OneLoop.setBalance(resp.getBalance());
		return response4OneLoop;
	}


}
