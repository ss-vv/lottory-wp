package com.xhcms.lottery.alarm.main;

import java.io.File;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.impl.MonitorTask;

public class SpringMonitorTask extends MonitorTask {
    
    private ClassPathXmlApplicationContext context;
    
    public static final String PID_FILE = "/dev/shm/alarm";
    
    public SpringMonitorTask(JobTimer timer, ClassPathXmlApplicationContext context){
        this.context = context;
        this.setName("Monitor Task");
        this.setPeriod(2L);
        this.setTimer(timer);
    }
    
    @Override
    public void destroy(){
        super.destroy();
        context.destroy();
    }
    
    @Override
   	public void run() {
       File file = new File(PID_FILE);
       if (!file.exists()) {
           log.info("stopping...");
           destroy();
       }
   	}
}
