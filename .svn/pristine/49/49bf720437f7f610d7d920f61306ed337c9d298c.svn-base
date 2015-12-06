package com.xhcms.lottery.dc.task.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.persist.service.BBMatchService;
import com.xhcms.lottery.dc.persist.service.FBMatchService;

public class UpdateMatchStatusTask extends Job {

    @Autowired
    private FBMatchService fbMatchService;
    
    @Autowired
    private BBMatchService bbMatchService;
    
    @Override
    protected void execute() throws Exception {
        fbMatchService.updateMatchStatus();
        bbMatchService.updateMatchStatus();
    }

    @Override
    public String toString(){
        return "UpdateMatchStatusTask";
    }
}
