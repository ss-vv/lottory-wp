package com.xhcms.lottery.dc.task.ticket;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.event.QueryPrizeMessage;

/**
 * 远程出票查询奖金任务  接口关闭
 * @author Next
 *
 */
public class YuanChengChuPiaoGetBonusListTask  extends Job {

	@Autowired
    private MessageSender messageSender;
	
	
	@Override
	protected void execute() throws Exception {
		
		try
		{			
			log.debug("GetBonusListTask begin run");
			QueryPrizeMessage msg = new QueryPrizeMessage();
			//远程出票查询奖金不需要票，只需要时间
			msg.setType("YuanChengChuPiao");
			//队列是那个  接收消息在哪
			messageSender.send(msg);
		}	
		catch(Exception te){
    		log.error("can not send buy ticket message YuanChengChuPiao");
    	}
		
		
	}
	
	
	
	@Override
    public String toString(){
        return "GetBonusListTask";
    }
}
