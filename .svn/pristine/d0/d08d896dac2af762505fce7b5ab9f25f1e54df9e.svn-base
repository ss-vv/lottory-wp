package com.xhcms.lottery.account.thrift.impl;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.thrift.config.ThriftConfig;
import com.unison.thrift.user.account.UserAccountHandler;
import com.unison.thrift.user.account.gen.UserAccountHandlerGen;
import com.xhcms.lottery.account.thrift.UserAccountClient;

public class UserAccountClientImpl implements UserAccountClient {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ThriftConfig config;
	
	@Override
	public long findWeiboUserIdByLotteryUid(long uid) {
		String address = config.getAddress();
		int port = config.getPort();
		int clientTimeout = config.getClientTimeout();
		TTransport transport = new TFramedTransport(new TSocket(address, port,
				clientTimeout));
		TProtocol tProtocol = new TCompactProtocol(transport);
		
		TMultiplexedProtocol protocol = new TMultiplexedProtocol(tProtocol,
				UserAccountHandler.class.getName());
		UserAccountHandlerGen.Client userAccountClient = new UserAccountHandlerGen.Client(
				protocol);
		long weiboUserId = 0L;
		try {
			transport.open();
			weiboUserId = userAccountClient
					.findWeiboUserIdByLotteryUid(uid+"");
			log.debug("通过lotteryUid查找weiboUserId={}", weiboUserId);
		} catch (Throwable e) {
			log.error("调用idl接口，通过大V彩用户ID={},查询微博用户ID出现异常.", new Object[]{uid, e});
		} finally {
			transport.close();
		}
		return weiboUserId;
	}
}