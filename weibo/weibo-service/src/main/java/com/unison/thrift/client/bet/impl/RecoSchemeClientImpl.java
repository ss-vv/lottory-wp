package com.unison.thrift.client.bet.impl;

import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.thrift.client.bet.RecoSchemeClient;
import com.unison.thrift.config.ThriftConfig;
import com.unison.thrift.config.ThriftConnMgr;
import com.unison.thrift.scheme.service.RecoSchemeHandler;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.unison.thrift.scheme.service.gen.RecoSchemeHandlerGen;

@Service
public class RecoSchemeClientImpl implements RecoSchemeClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ThriftConfig config;

	@Override
	public BetSchemeData viewScheme(long schemeId) {
		ThriftConnMgr thriftConnMgr = new ThriftConnMgr(config);
		TProtocol tProtocol = thriftConnMgr.getTProtocol(RecoSchemeHandler.class.getName());
		
		RecoSchemeHandlerGen.Client recoSchemeClient = new RecoSchemeHandlerGen.Client(tProtocol);
		try {
			thriftConnMgr.openTransport();
			return recoSchemeClient.viewScheme(schemeId);
		} catch (Exception e) {
			logger.error("query recommend scheme, schemeId = {}",schemeId,e);
		} finally {
			thriftConnMgr.closeTransport();
		}
		return null;
	}
}