package com.unison.thrift.client;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import com.unison.thrift.user.account.UserAccountHandler;
import com.unison.thrift.user.account.gen.UserAccountHandlerGen;

public class UserAccountClientDemo {

	public static void main(String[] arsg) {

		String address = "58.83.235.132";
		int port = 32100;
		int clientTimeout = 30000;

		TTransport transport = new TFramedTransport(new TSocket(address, port,
				clientTimeout));
		TProtocol tProtocol = new TCompactProtocol(transport);
		
		TMultiplexedProtocol protocol = new TMultiplexedProtocol(tProtocol,
				UserAccountHandler.class.getName());
		UserAccountHandlerGen.Client userAccountClient = new UserAccountHandlerGen.Client(
				protocol);

		try {
			transport.open();
			long weiboUserId = userAccountClient
					.findWeiboUserIdByLotteryUid("1676");
			System.out.println("weiboUserId = " + weiboUserId);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			transport.close();
		}
	}

}