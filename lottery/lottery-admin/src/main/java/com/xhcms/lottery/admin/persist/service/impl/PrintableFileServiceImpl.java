package com.xhcms.lottery.admin.persist.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.admin.persist.service.PrintableFileService;
import com.xhcms.lottery.admin.util.PrintableFileUtil;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.commons.data.PrintableTicket;
import com.xhcms.lottery.commons.persist.dao.PrintableFileDao;
import com.xhcms.lottery.commons.persist.dao.PrintableTicketDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;
import com.xhcms.lottery.commons.persist.entity.PrintableTicketPO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.commons.utils.ShiTiDianBetContentUpgradeUtil;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryPlatformIdEnum;
import com.xhcms.lottery.utils.BeanUtilsTools;
import com.xhcms.lottery.utils.DateUtils;

public class PrintableFileServiceImpl implements PrintableFileService {
	private Logger log =LoggerFactory.getLogger(getClass());
	@Autowired
	private PrintableTicketDao printableTicketDao;
	
	@Autowired
	private PrintableFileDao printableFileDao;

	@Autowired
	private PrintableFileUtil printableFileUtil;

	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private ShiTiDianBetContentUpgradeUtil shiTiDianBetContentUpgradeUtil;
	
	@Override
	@Transactional
	public String export(List<Long> printableTicketIds,String lotteryId,String playId, String lotteryPlatformId, String exportToShitidian) {
		if(null == LotteryPlatformIdEnum.getlotteryPlatformIdEnumById(lotteryPlatformId)){
			log.error("非法的LotteryPlatformId={}",lotteryPlatformId);
			return "";
		}
		String downloadUrl=null; 
		if(null!=printableTicketIds&&!printableTicketIds.isEmpty()&&StringUtils.isNotBlank(lotteryId)&&StringUtils.isNotBlank(playId)){
			Set<Long> ticketIds=makeTicketIds(printableTicketIds);
			shiTiDianBetContentUpgradeUtil.tryUpgradeOrDowngradeBetContent(exportToShitidian,ticketIds);
			List<PrintableTicketPO> tickets=printableTicketDao.findByIds(printableTicketIds, lotteryPlatformId, EntityStatus.TICKET_READY_FOR_HANDWORK);
			if(null!=tickets&&!tickets.isEmpty()){
				List<String> printBetContents=makePrintBetContents(tickets);
				Date now = new Date();
				String tmpOldLotteryPlatformId=lotteryPlatformId;
				if(null != LotteryPlatformIdEnum.getlotteryPlatformIdEnumById(exportToShitidian)){//导出到其他实体店，需要把现有票的平台id改成目标实体店平台id
					lotteryPlatformId = exportToShitidian;
				}
				PrintableFile printableFile=printableFileUtil.createFile(printBetContents,now,printableTicketIds, lotteryId, playId, lotteryPlatformId);
				if(null!=printableFile){
					PrintableFilePO printableFilePO=new PrintableFilePO();
					printableFilePO.setCreateTime(now);
					printableFilePO.setFileName(printableFile.getFileName());
					printableFilePO.setFilePath(printableFile.getFilePath());
					printableFilePO.setFileUrl(printableFile.getFileUrl());
					printableFilePO.setPrintableTickets(tickets);
					printableFilePO.setLotteryPlatformId(lotteryPlatformId);
					printableFileDao.save(printableFilePO);
					ticketDao.updateStatus(printableTicketIds, EntityStatus.TICKET_READY_FOR_HANDWORK, EntityStatus.TICKET_EXPORTED, "已导出到高速打印文件中");
					if(!tmpOldLotteryPlatformId.equals(lotteryPlatformId)){
						ticketDao.updateLotteryPlatformId(printableTicketIds,lotteryPlatformId);
						log.info("票id={}的票原有LotteryPlatformId为：{},更新LotteryPlatformId为:{}",
								new Object[]{printableTicketIds,tmpOldLotteryPlatformId,lotteryPlatformId});
					}
					downloadUrl=printableFile.getFileUrl();
				}
				
			}
		}
		return downloadUrl;
	}

	private Set<Long> makeTicketIds(List<Long> printableTicketIds) {
		Set<Long> result=new HashSet<Long>();
		for(Long printableTicketId:printableTicketIds){
			result.add(printableTicketId);
		}
		return result;
	}

	private List<String> makePrintBetContents(List<PrintableTicketPO> tickets) {
		List<String> result=null;
		if(null!=tickets&&!tickets.isEmpty()){
			result=new ArrayList<String>();
			for(PrintableTicketPO ticket:tickets){
				if(StringUtils.isNotBlank(ticket.getPrintBetContent())){
					result.add(ticket.getPrintBetContent());
				}
			}
		}
		return result;
	}

	private String makeFileUrl(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	private String makeFilePath(String fileName) {
		// TODO Auto-generated method stub
		return null;
	}

	private String makeFileName(List<Long> printableTicketIds, Date now) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public List<PrintableTicket> findTicketByFileId(long fileId) {
		List<Object> list = printableTicketDao.findTicketByFileId(fileId);
		List<PrintableTicket> list2 = new ArrayList<PrintableTicket>();
		
		for (Object o : list) {
			Object[] or = (Object[])o;
			TicketPO tPO = (TicketPO)or[0];
			PrintableTicketPO pPO = (PrintableTicketPO)or[1];
			PrintableTicket t = new PrintableTicket();
			BeanUtilsTools.copyNotNullProperties(tPO, t, null);
			t.setPrintBetContent(pPO.getPrintBetContent());
			list2.add(t);
		}
		List<Long> ptIds = new ArrayList<Long>();
		for(PrintableTicket pt : list2) {
			ptIds.add(pt.getId());
		}
		if(ptIds != null && ptIds.size() > 0) {
			List<Object[]> ticketIdWithSchemeOfftime = ticketDao.findTicketWithOfftime(ptIds);
			if(null != ticketIdWithSchemeOfftime) {
				for(PrintableTicket pt : list2) {
					Long ticketId = pt.getId();
					for(Object[] obj : ticketIdWithSchemeOfftime) {
						if(null != ticketId && obj.length >= 3 && ticketId.equals(obj[0])) {
							Date schemeOfftime = (Date)obj[1];
							pt.setSchemeOfftime(schemeOfftime);
							pt.setMachineOfftime((Date)obj[2]);
							break;
						}
					}
				}
			}
		}
		return list2;
	}
}
