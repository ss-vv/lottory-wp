package com.xhcms.lottery.dc.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xhcms.commons.job.JobTimer;
import com.xhcms.commons.job.impl.MonitorTask;

public class SpringMonitorTask extends MonitorTask {
    
    private ClassPathXmlApplicationContext context;
    
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
    
}
