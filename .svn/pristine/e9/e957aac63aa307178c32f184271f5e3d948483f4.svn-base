package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "lt_printable_file")
public class PrintableFilePO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2629255548532390135L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="file_name",nullable=false)
	private String fileName;
	@Column(name="create_time",nullable=false)
	private Date createTime;
	@Column(name="file_path",nullable=false)
	private String filePath;
	@Column(name="file_url",nullable=false)
	private String fileUrl;
	@Column(name="lottery_platform_id")
	private String lotteryPlatformId;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="lt_printable_ticket_file",joinColumns=@JoinColumn(name="file_id"),inverseJoinColumns=@JoinColumn(name="printable_ticket_id"))
	private List<PrintableTicketPO> printableTickets;
	
	@Transient
	private int winTicketCount;
	
	@Transient
	private float totalBonus; 
	
	@Transient
	private boolean win;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public List<PrintableTicketPO> getPrintableTickets() {
		return printableTickets;
	}

	public void setPrintableTickets(List<PrintableTicketPO> printableTickets) {
		this.printableTickets = printableTickets;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getWinTicketCount() {
		return winTicketCount;
	}

	public void setWinTicketCount(int winTicketCount) {
		this.winTicketCount = winTicketCount;
	}

	public float getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(float totalBonus) {
		this.totalBonus = totalBonus;
	}
}
