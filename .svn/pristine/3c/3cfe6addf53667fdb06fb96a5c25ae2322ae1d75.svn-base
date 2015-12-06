package com.unison.lottery.api.redEnvalope.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskExecutionContext;
import it.sauronsoftware.cron4j.TaskTable;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoaderListener;

import com.davcai.lottery.service.RedEnvalopeService;
import com.unison.lottery.weibo.data.service.store.persist.entity.RedEnvalopePO;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.entity.AccountPO;
import com.xhcms.lottery.commons.persist.service.AccountService;

/**
 * 检测红包是否有效，每天凌晨12点检测
 * 
 * @author baoxing.peng
 * @since 2015-05-26 15:36:00
 */
public class UserRedEnvalopeValidDetection extends ContextLoaderListener {
	private Logger logger = LoggerFactory
			.getLogger(UserRedEnvalopeValidDetection.class);

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ApplicationContext context = this.getCurrentWebApplicationContext();
		UserRedEnvalopeValidDetection detecter = (UserRedEnvalopeValidDetection) context
				.getBean("userRedEnvalopeValidDetection");
		detecter.start();
	}

	private void start() {
		Thread cThread =new Thread(communicateThread);
		cThread.setDaemon(true);
		cThread.start();
	}

	private SchedulingPattern communicate_cron_exp = new SchedulingPattern(
			"* * * * *");
	@Autowired
	private RedEnvalopeService redEnvalopeService;
	@Autowired
	private AccountService accountService;
	private Runnable communicateThread = new Runnable() {
		@Override
		public void run() {
			Scheduler sch = new Scheduler();
			sch.addTaskCollector(new TaskCollector() {
				@Override
				public TaskTable getTasks() {
					TaskTable table = new TaskTable();
					
					table.add(communicate_cron_exp, new Task() {
						@Override
						@Transactional
						public void execute(TaskExecutionContext context)
								throws RuntimeException {
							logger.info("红包失效检测启动");
							List<RedEnvalopePO> redEnvalopePOs = redEnvalopeService
									.findAllInvalidEvalope();
							for (RedEnvalopePO redEnvalopePO : redEnvalopePOs) {
								Long leaveAmount = redEnvalopePO.getRedEnvalopeAmount()-
										(redEnvalopePO.getGrabedEnvalopeAmount()==null?0:redEnvalopePO.getGrabedEnvalopeAmount());
								logger.info("剩余金额:{}",leaveAmount);
								if(leaveAmount>0){
									BigDecimal leaveAmountDecimal = new BigDecimal(leaveAmount);
									leaveAmountDecimal = leaveAmountDecimal.divide(new BigDecimal(100.0),2,RoundingMode.HALF_UP);
									accountService.withSendRedReturn(leaveAmountDecimal, redEnvalopePO);
								}
								redEnvalopePO.setStatus("1");
								redEnvalopePO.setAmountReturnUserAccount(leaveAmount);
								redEnvalopeService.updateRedEnvalope(redEnvalopePO);
							}
							logger.info("红包失效检测完成，共检测{}个用户的红包",redEnvalopePOs.size());
						}
					});
					
					return table;
				}
			});
			sch.start();
		}
	};

	public RedEnvalopeService getRedEnvalopeService() {
		return redEnvalopeService;
	}

	public void setRedEnvalopeService(RedEnvalopeService redEnvalopeService) {
		this.redEnvalopeService = redEnvalopeService;
	}

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}
	
}
