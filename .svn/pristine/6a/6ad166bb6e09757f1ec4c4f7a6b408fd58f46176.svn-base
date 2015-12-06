package com.xhcms.lottery.dc.task.repeat;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.repeat.RepeatPlan;

/**
 * 
 * @desc 轮询选出可被执行的追号计划
 * @createTime 2013-8-9
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public abstract class AbstractRepeatPlanTask extends Job {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Override
    protected abstract void execute();

	protected abstract List<RepeatPlan> getRepeatPlanListCanExecuted();
	
	@Override
    public String toString(){
        return "AbstractRepeatPlanTask";
    }
}
