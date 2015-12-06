package com.xhcms.lottery.dc.task.ticket;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.dc.persist.service.TicketService;

/**
 * 
 * <p>Title: 检查已出票但未兑奖的方案，并对参与人扣款和返还（出票失败）</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class DeductTask extends Job {

    @Autowired
    private TicketService ticketService;
    
    @Override
    protected void execute() {
        for(Long schemeId : ticketService.listStopScheme()){
            try{
                ticketService.deductAndReturn(schemeId);
            }catch(Exception e){
                log.warn(e.getMessage());
            }
        }
    }
    
    @Override
    public String toString(){
        return "DeductTask";
    }
}
