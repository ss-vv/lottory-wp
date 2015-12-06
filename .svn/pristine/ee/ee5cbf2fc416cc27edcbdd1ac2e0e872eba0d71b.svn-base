package com.xhcms.lottery.commons.utils;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.PrintableTicketDao;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;
import com.xhcms.lottery.conf.SystemConf;
import com.xhcms.lottery.utils.Ticket2ShiTiDianCodeTool;

public class ShiTiDianBetContentUpgradeUtil {
	@Autowired
	private SystemConf systemConf;
	
	@Autowired
	private PrintableTicketDao printableTicketDao;

	
	/**
	 * 对于竞彩足球和竞彩篮球的混合投注玩法，尝试对投注内容进行升级和降级操作
	 * @param lotteryPlatformId
	 * @param ticketIds
	 */
	@Transactional
	public void tryUpgradeOrDowngradeBetContent(String lotteryPlatformId, Set<Long> ticketIds){
		if(StringUtils.isNotBlank(lotteryPlatformId)&&null!=ticketIds&&!ticketIds.isEmpty()){
			String shouldUpgradeShiTiDians=systemConf.getShouldUpgradeShiTiDians();
			List<PrintableTicketPO> printableTickets=printableTicketDao.findByTicketIds(ticketIds);
			if(shouldUpgrade(lotteryPlatformId, shouldUpgradeShiTiDians)){//此时需要升级
				if(null!=printableTickets&&!printableTickets.isEmpty()){
					for(PrintableTicketPO printableTicket:printableTickets){
						String newBetContent=Ticket2ShiTiDianCodeTool.upgradeBetContent(printableTicket.getPrintBetContent());
						if(!StringUtils.equals(newBetContent, printableTicket.getPrintBetContent())){
							printableTicket.setPrintBetContent(newBetContent);
						}
					}
				}
			}else{//此时需要尝试降级
				if(null!=printableTickets&&!printableTickets.isEmpty()){
					for(PrintableTicketPO printableTicket:printableTickets){
						String newBetContent=Ticket2ShiTiDianCodeTool.downgradeBetContent(printableTicket.getPrintBetContent());
						if(!StringUtils.equals(newBetContent, printableTicket.getPrintBetContent())){
							printableTicket.setPrintBetContent(newBetContent);
						}
					}
				}
				
			}
		}
	}


	protected boolean shouldUpgrade(String lotteryPlatformId,
			String shouldUpgradeShiTiDians) {
		boolean result=false;
		if(StringUtils.isNotBlank(shouldUpgradeShiTiDians)){
			
			String[] splits = shouldUpgradeShiTiDians.split(",");
			if(null!=splits&&splits.length>0){
				for(String split:splits){
					if(StringUtils.equals(split, lotteryPlatformId)){
						result=true;
						break;
					}
				}
			}
			
		}
		return result;
	}

}
