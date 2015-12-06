package com.xhcms.lottery.admin.web.action.ticket;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.PrintableFileService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.PrintableTicket;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class PrintableTicketAction extends BaseListAction {
    private static final long serialVersionUID = -6405871733143923846L;
    @Autowired
    private TicketService ticketService;

    private String lotteryId;
    private String playId;
    private Long schemeId;
    private Long ticketId;
    private String lotteryPlatformId;
    private String exportToShitidian;
    private int state = EntityStatus.TICKET_READY_FOR_HANDWORK;
   
    private List<PrintableTicket> printableTickets;
    
    
    private List<Long> printableTicketIds;

    @Autowired
	private PrintableFileService printableFileService;
    
    private String downloadUrl;
    
    @Override
    public String execute(){
        init();
        if(lotteryId == null){
            lotteryId = "JCZQ";
        }
        if(StringUtils.isBlank(playId)){
            playId = Constants.ALL_PLAY_ID;
        }
        if(state<0){
        	state=EntityStatus.TICKET_READY_FOR_HANDWORK;
        }
        paging.setMaxResults(100);
        printableTickets=ticketService.listPrintableTickets(paging,from, to, state, lotteryId, schemeId, ticketId,playId, lotteryPlatformId);
        return SUCCESS;
    }
    
    public String exportPrintableFile(){
    	downloadUrl=printableFileService.export(printableTicketIds,lotteryId,playId, lotteryPlatformId, exportToShitidian);
    	return SUCCESS;
    }
    
    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }
    

	public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }


    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public List<PrintableTicket> getPrintableTickets() {
		return printableTickets;
	}

	public void setPrintableTickets(List<PrintableTicket> printableTickets) {
		this.printableTickets = printableTickets;
	}


	public List<Long> getPrintableTicketIds() {
		return printableTicketIds;
	}

	public void setPrintableTicketIds(List<Long> printableTicketIds) {
		this.printableTicketIds = printableTicketIds;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public String getExportToShitidian() {
		return exportToShitidian;
	}

	public void setExportToShitidian(String exportToShitidian) {
		this.exportToShitidian = exportToShitidian;
	}
}

