package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lt_printable_ticket")
public class PrintableTicketPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5844273360714862478L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="ticket_id",nullable=false)
	private Long ticketId;
	@Column(name="print_bet_content",nullable=false)
	private String printBetContent;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTicketId() {
		return ticketId;
	}
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}
	public String getPrintBetContent() {
		return printBetContent;
	}
	public void setPrintBetContent(String printBetContent) {
		this.printBetContent = printBetContent;
	}
}
