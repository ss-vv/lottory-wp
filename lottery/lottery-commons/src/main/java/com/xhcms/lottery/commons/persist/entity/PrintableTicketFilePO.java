package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lt_printable_ticket_file")
public class PrintableTicketFilePO implements Serializable{
	private static final long serialVersionUID = 5844273360714862478L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="printable_ticket_id",nullable=false)
	private Long printableTicketId;
	@Column(name="file_id",nullable=false)
	private String fileId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPrintableTicketId() {
		return printableTicketId;
	}
	public void setPrintableTicketId(Long printableTicketId) {
		this.printableTicketId = printableTicketId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
}
