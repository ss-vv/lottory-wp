package com.unison.lottery.mc.uni.handler;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.IOrderTicketClientWithMultiPlatform;
import com.davcai.lottery.platform.client.IQueryPrizeClientWithMultiPlatform;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.unison.lottery.mc.uni.client.IPrizeProcessor;
import com.unison.lottery.mc.uni.client.QueryPrizeClient;
import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.client.Translator;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.event.QueryPrizeMessage;
import com.xhcms.lottery.lang.LotteryPlatformId;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 查询ticket中奖情况 Message Queue Handler.<br/>
 * da，只选取“彩种+期号”唯一的ticket发送给mc。<br/>
 * mc，一次查询出所有该“彩种+期号”的ticket并处理。
 * 
 * @author Yang Bo
 */
public class QueryPrizeMQHandler implements MessageHandler<QueryPrizeMessage>, IPrizeProcessor {

	private static final long serialVersionUID = -4540296483098035353L;

	private Logger log = LoggerFactory.getLogger(getClass());

//    private QueryPrizeClient client;

    @Autowired
	private TicketService ticketService;
    
    @Autowired
	private IQueryPrizeClientWithMultiPlatform queryPrizeWithMultiPlatformClient;

    @Override
    public void handle(QueryPrizeMessage tm) {
    	
    	queryPrizesWithMultiPlatforms(tm);
    	
    		
       
    	
        
    }

	

	private void queryPrizesWithMultiPlatforms(QueryPrizeMessage tm) {
		String lotteryPlatformId = decideLotteryPlatformId(tm);
		
//		//远程出票查询奖金不需要票，只需要时间
//		if(null == lotteryPlatformId && tm.getType().equals("YuanChengChuPiao"))
//			lotteryPlatformId = LotteryPlatformId.YUAN_CHENG_CHU_PIAO;
		
		log.debug("需要处理{}平台的查询票中奖",lotteryPlatformId);
		QueryPrizeResponse queryPrizeResponse=queryPrizeWithMultiPlatformClient.queryPrize(lotteryPlatformId,tm.getTickets());
		if(null!=queryPrizeResponse&&null!=queryPrizeResponse.getResponse4Loops()&&!queryPrizeResponse.getResponse4Loops().isEmpty()){
			for(QueryPrizeResponse4OneLoop response4OneLoop:queryPrizeResponse.getResponse4Loops()){
				ticketService.prize(response4OneLoop.getTicketsMap());
			}
		}
		
//		for(Ticket ticket: tm.getTickets()){
//        	
//    		handleWithZM(ticket);
//    	}
    	
		
	}



	private String decideLotteryPlatformId(QueryPrizeMessage tm) {
		String result=null;
		if(null!=tm&&null!=tm.getTickets()&&!tm.getTickets().isEmpty()){
			try{
				Ticket ticket = tm.getTickets().get(0);
				if(null!=ticket){
					if(StringUtils.isBlank(ticket.getLotteryPlatformId())||StringUtils.equals(ticket.getLotteryPlatformId(), LotteryPlatformId.ZUN_AO)){
						result=LotteryPlatformId.ZUN_AO;
					}
					else if(StringUtils.equals(ticket.getLotteryPlatformId(), LotteryPlatformId.AN_RUI_ZHI_YING)){
						result=LotteryPlatformId.AN_RUI_ZHI_YING;
					}
				}
			}catch(Throwable e){//可能是旧的生产环境生成的消息造成的异常，按照尊奥处理
				e.printStackTrace();
				result=LotteryPlatformId.ZUN_AO;
			}
			
				
		}
		return result;
	}






//	private void handleWithZM(Ticket ticket) {
//		String zmLotteryId;
//		QueryPrizeParserStatus parserStatus = new QueryPrizeParserStatus();
//		try {
//			zmLotteryId = Translator.lcPlayIdToZMLotteryId(ticket.getPlayId());
//			String issue = getIssueDate(ticket);
//			client.postWithStatus(zmLotteryId, issue, parserStatus, this);
//		} catch (TranslateException e) {
//			log.error("Can not translate lcPlayId to zmLotteryId. Continue to query next issue.", e);
//		}
//	}
    
	/**
	 * 获取正确的“期号”。对于竞彩是ticket的创建日期。
	 * @param ticket 投注的彩票
	 * @return 期号
	 */
//	public String getIssueDate(Ticket ticket) {
//		SimpleDateFormat issueDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//		if (StringUtils.isEmpty(ticket.getIssue())) {
//			return issueDateFormatter.format(ticket.getCreatedTime());
//		}
//		return ticket.getIssue();
//	}

//	public void setClient(QueryPrizeClient client) {
//        this.client = client;
//    }

	@Override
	public void process(QueryPrizeParserStatus prizeStatus) {
		Map<Long, Ticket> ts = prizeStatus.getTicketIdTickets();
		if(ts.size() > 0){
            ticketService.prize(ts);
        }
	}

}
