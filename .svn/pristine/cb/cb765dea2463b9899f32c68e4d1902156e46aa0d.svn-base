package com.unison.thrift.server;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import com.unison.thrift.config.ThriftConfig;

public class ThriftServer {

	private Logger log = LoggerFactory.getLogger(getClass());

	public ThriftServer() {
		log.info(getClass().getSimpleName() + " starting...");
	}

	public ThriftServer(ApplicationContext ctx, Map<String, TProcessor> map) {
		try {
			ThriftConfig config = ctx.getBean(ThriftConfig.class);
			TNonblockingServerSocket socket = new TNonblockingServerSocket(
					config.getPort());

			TMultiplexedProcessor processor = new TMultiplexedProcessor();
			
			init(processor, map);
			
			THsHaServer.Args arg = new THsHaServer.Args(socket);
			arg.protocolFactory(new TCompactProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			TServer server = new THsHaServer(arg);

			log.info("thrift listener on [port=" + config.getPort() + "]");
			server.serve();
		} catch (TTransportException e) {
			log.error("启动thrift server error.", e);
		}
	}
	
	protected void init(TMultiplexedProcessor processor, 
			Map<String, TProcessor> map) {
		if(null != processor && map.size() > 0) {
			Set<Entry<String, TProcessor>> entrySet = map.entrySet();
			Iterator<Entry<String, TProcessor>> iter = entrySet.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, TProcessor> entry = 
						(Map.Entry<String, TProcessor>) iter.next();
				String serviceName = entry.getKey();
				TProcessor proc = entry.getValue();
				processor.registerProcessor(serviceName, proc);
				log.info("注册服务接口.serviceName={}", serviceName);
			}
		}
	}
}