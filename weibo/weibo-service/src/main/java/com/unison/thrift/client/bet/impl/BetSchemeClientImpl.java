package com.unison.thrift.client.bet.impl;

import org.apache.thrift.protocol.TProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.thrift.client.bet.BetSchemeClient;
import com.unison.thrift.config.ThriftConfig;
import com.unison.thrift.config.ThriftConnMgr;
import com.unison.thrift.scheme.service.BetSchemeHandler;
import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.unison.thrift.scheme.service.gen.BetSchemeHandlerGen;

@Service
public class BetSchemeClientImpl implements BetSchemeClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ThriftConfig config;

	@Override
	public BetSchemeData getSchemeById(long schemeId) {
		ThriftConnMgr thriftConnMgr = new ThriftConnMgr(config);
		TProtocol tProtocol = thriftConnMgr.getTProtocol(BetSchemeHandler.class.getName());
		
		BetSchemeHandlerGen.Client betSchemeClient = new BetSchemeHandlerGen.Client(
				tProtocol);
		try {
			thriftConnMgr.openTransport();
			return betSchemeClient.getSchemeById(schemeId);
		} catch (Exception e) {
			logger.error("query bet scheme, schemeId = {}",schemeId,e);
		} finally {
			thriftConnMgr.closeTransport();
		}
		return null;
	}

	@Override
	public boolean isCanSendShowScheme(long schemeId) {
		ThriftConnMgr thriftConnMgr = new ThriftConnMgr(config);
		TProtocol tProtocol = thriftConnMgr.getTProtocol(BetSchemeHandler.class.getName());

		BetSchemeHandlerGen.Client betSchemeClient = new BetSchemeHandlerGen.Client(
				tProtocol);
		try {
			thriftConnMgr.openTransport();
			return betSchemeClient.isCanSendShowScheme(schemeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			thriftConnMgr.closeTransport();
		}
		return false;
	}

	@Override
	public boolean updateBetSchemePublishShow(long schemeId) {
		ThriftConnMgr thriftConnMgr = new ThriftConnMgr(config);
		TProtocol tProtocol = thriftConnMgr.getTProtocol(BetSchemeHandler.class.getName());

		BetSchemeHandlerGen.Client betSchemeClient = new BetSchemeHandlerGen.Client(
				tProtocol);
		boolean result = false;
		try {
			thriftConnMgr.openTransport();
			result = betSchemeClient.updateBetSchemePublishShow(schemeId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			thriftConnMgr.closeTransport();
		}
		return result;
	}

	
}