package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PrintableFile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8689746250603640270L;
	
	private Long id;
	private String fileName;
	private Date createTime;
	private String filePath;
	private String fileUrl;
	private String color="#FFFFFF";
	private boolean win;
	private int winTicketCount;
	private float totalBonus; 
	
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
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
