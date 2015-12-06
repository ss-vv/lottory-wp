package com.unison.thrift.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.unison.thrift.scheme.service.BetSchemeHandler;
import com.unison.thrift.scheme.service.RecoSchemeHandler;
import com.unison.thrift.scheme.service.gen.BetSchemeHandlerGen;
import com.unison.thrift.scheme.service.gen.RecoSchemeHandlerGen;
import com.unison.thrift.server.ThriftServer;
import com.unison.thrift.user.account.UserAccountHandler;
import com.unison.thrift.user.account.gen.UserAccountHandlerGen;

public class WSLauncher {

	public static final String PID_FILE = "/dev/shm/ws";

	private static final Logger log = LoggerFactory.getLogger(WSLauncher.class);

	public static void main(String[] args) {
		log.info("weibo service starting...");
		log.info("加载配置信息...");
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:spring-*.xml");

		new ThriftServer(context, register(context));
		File lock = new File(PID_FILE);
		while (lock.exists()) {
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, TProcessor> register(ApplicationContext context) {
		UserAccountHandler userAccountHandler = context
				.getBean(UserAccountHandler.class);
		UserAccountHandlerGen.Processor userAccountProc = new UserAccountHandlerGen.Processor(
				userAccountHandler);
		
		BetSchemeHandler betSchemeHandler = context
				.getBean(BetSchemeHandler.class);
		BetSchemeHandlerGen.Processor betSchemeProcessor = new BetSchemeHandlerGen.Processor(
				betSchemeHandler);
		
		RecoSchemeHandler recoSchemeHandler = context
				.getBean(RecoSchemeHandler.class);
		RecoSchemeHandlerGen.Processor recoSchemeProcessor = new RecoSchemeHandlerGen.Processor(
				recoSchemeHandler);
		
		Map<String, TProcessor> map = new HashMap<String, TProcessor>();
		map.put(UserAccountHandler.class.getName(), userAccountProc);
		map.put(BetSchemeHandler.class.getName(), betSchemeProcessor);
		map.put(RecoSchemeHandler.class.getName(), recoSchemeProcessor);
		return map;
	}
}