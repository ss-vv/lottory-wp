package com.unison.thrift.config;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftConnMgr {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private TTransport transport;
	
	private ThriftConfig config;
	
	public ThriftConnMgr() {
	}
	
	public ThriftConnMgr(ThriftConfig config) {
		this.config = config;
	}
	
	public TProtocol getTProtocol(String serviceName) {
		if(config == null) {
			return null;
		}
		String address = config.getAddress();
		int port = config.getPort();
		int clientTimeout = config.getClientTimeout();
		transport = new TFramedTransport(new TSocket(address, port,
				clientTimeout));
		TProtocol tProtocol = new TCompactProtocol(transport);
		TMultiplexedProtocol protocol = new TMultiplexedProtocol(tProtocol,
				serviceName);
		
		return protocol;
	}
	
	public void openTransport() {
		try {
			if(null != transport) {
				transport.open();
			}
		} catch (Throwable e) {
			log.error("打开thrift连接，出现异常:{}", e);
		}
	}
	
	public void closeTransport() {
		try {
			if(null != transport) {
				transport.close();
			}
		} catch (Throwable e) {
			log.error("关闭thrift连接，出现异常:{}", e);
		} finally {
			if(null != transport) {
				transport.close();
			}
		}
	}
}