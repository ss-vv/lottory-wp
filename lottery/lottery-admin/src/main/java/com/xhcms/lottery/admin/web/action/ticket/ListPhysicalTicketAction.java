package com.xhcms.lottery.admin.web.action.ticket;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.PrintableFileService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.commons.data.PrintableTicket;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class ListPhysicalTicketAction extends BaseListAction {
    private static final long serialVersionUID = -6405871733143923846L;
    
    @Autowired
    private PrintableFileService printableFileService;
    @Autowired
    private TicketService ticketService;
    /**已导出票文件的id*/
    private long fileId;
    private String lotteryId;
    private String playId;
    private String passType;
    private String sponsor;
    private Long schemeId;
    private Long ticketId;
    private int state = EntityStatus.TICKET_READY_FOR_HANDWORK;
    private Ticket ticket;
    private List<Ticket> tickets;
    private List<PrintableTicket> printableTickets;
    private Data data;
    private List<PrintableFile> printFile;
    private String lotteryPlatformId;
    @Override
    public String execute(){
        init();
        if(lotteryId == null){
            lotteryId = "JCZQ";
        }
        if(StringUtils.isBlank(playId)){
            playId = null;
        }
        if(StringUtils.isBlank(passType)){
            passType = null;
        }
        tickets =ticketService.listPhysicalStoreTicket(paging,from, to, state, lotteryId, schemeId, ticketId, lotteryPlatformId);
        return SUCCESS;
    }
    
    public String ticketDetail(){
    	try {
			ticket = ticketService.getTicket(ticketId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return SUCCESS;
    }
    
    public String exportedTicketFileDetail(){
    	printableTickets = printableFileService.findTicketByFileId(fileId);
    	return SUCCESS;
    }
    public String listHighSpeedPrintFile(){
    	init();
    	printFile = ticketService.listHighSpeedPrintFileByTicketId(paging, from, to, ticketId, lotteryPlatformId);
    	return SUCCESS;
    }
    
    public String tickExporSuccess(){
    	if(ticketService.tickExportSuccess(ticketId)){
    		data.setSuccess(true);
    	}
    	data.setSuccess(false);
    	data.setData("尚未成功导出高速出票文件!");
    	return SUCCESS;
    }
    public String getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(String lotteryId) {
        this.lotteryId = lotteryId;
    }
    
    public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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

	public List<Ticket> getTickets() {
		return tickets;
	}

	public List<PrintableFile> getPrintFile() {
		return printFile;
	}

	public void setPrintFile(List<PrintableFile> printFile) {
		this.printFile = printFile;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public long getFileId() {
		return fileId;
	}

	public List<PrintableTicket> getPrintableTickets() {
		return printableTickets;
	}
}