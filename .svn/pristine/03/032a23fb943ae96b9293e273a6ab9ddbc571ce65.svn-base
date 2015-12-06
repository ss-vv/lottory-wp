package com.xhcms.lottery.dc.task.match;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.persist.service.BJDCMatchService;

public class UpdateBJDCMatchStatusTask extends Job {

    @Autowired
    private BJDCMatchService bjdcMatchService;
    
    @Override
    protected void execute() throws Exception {
    	bjdcMatchService.updateMatchStatus();
    }

    @Override
    public String toString(){
        return UpdateBJDCMatchStatusTask.class.getSimpleName();
    }
} 