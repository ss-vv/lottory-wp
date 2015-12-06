package com.xhcms.lottery.dc.task.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.dc.persist.service.TicketService;

/**
 * 
 * <p>Title: 查询已经截止并且未出票的合买方案</p>
 * <p>Description: 检查合买方案是否符合强制出票条件，如果符合则出票；如果过期则流标</p>
 * @author 王磊
 * @version 1.0.0
 */
public class ForceBuyTicketTask extends Job {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private SystemConf systemConf;
    
    /**
     * 合买提前结束的时间，单位为秒,从配置文件获取，默认为600秒钟
     */
    int advancedSecond=600;
    
    @Override
    protected void execute() {
    	advancedSecond = systemConf.getIntegerValueByKey(SystemConf.BETTIME.GROUPBUY_DEFAULT_AHEAD_SECOND);
    	for(Long schemeId : ticketService.listDeadlinesGroupbuyScheme(advancedSecond)){
            try{
                ticketService.forceBuyTicketAndFlow(schemeId);
            }catch(Exception e){
                log.warn(e.getMessage());
            }
        }
    }

    @Override
    public String toString(){
        return "ForceBuyTicketTask";
    }
}
