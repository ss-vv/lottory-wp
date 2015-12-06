package core;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.Task;

public class WeiboStatisticLauncher {
	private static final Logger log = LoggerFactory.getLogger(WeiboStatisticLauncher.class);

	public static void main(String[] args) {
		log.info("weibo-statistic starting...");
		
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath*:spring-*.xml");

		JobTimer timer = new JobTimer();
		SpringMonitorTask mTask = new SpringMonitorTask(timer, context);
		timer.addTask(mTask);

		for (Task t : context.getBeansOfType(Task.class).values()) {
			timer.addTask(t);
		}
		
		File lock = new File(SpringMonitorTask.PID_FILE);
        while(lock.exists()) {
	        try {
	            Thread.sleep(2000L);
	        } catch (InterruptedException e) {
	        }
        }
	}
}
