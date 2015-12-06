package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Bet implements Serializable {

    private static final long serialVersionUID = -8869508475152936569L;

    private int[] opts;
    private double[] maxOdds;

    private List<Ticket> tickets;
    private int note;
    private double maxBonus;
    private double minBonus;
    private String playId; // 方案的玩法id
    private int multiple;
    
    public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public int[] getOpts() {
        return opts;
    }

    public void setOpts(int[] opts) {
        this.opts = opts;
    }

    public double[] getMaxOdds() {
        return maxOdds;
    }

    public void setMaxOdds(double[] maxOdds) {
        this.maxOdds = maxOdds;
    }
    
    public double getMaxBonus() {
        return maxBonus;
    }

    public void setMaxBonus(double maxBonus) {
        this.maxBonus = maxBonus;
    }
    
    public void addMaxBonus(double maxBonus) {
        this.maxBonus += maxBonus;
    }

    public void addNote(int value) {
        this.note += value;
    }

    public void addTickets(List<Ticket> tickets) {
        if (this.tickets == null) {
            this.tickets = new LinkedList<Ticket>();
        }
        this.tickets.addAll(tickets);
    }
    
    public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String toString(){
    	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public double getMinBonus() {
		return minBonus;
	}

	public void setMinBonus(double minBonus) {
		this.minBonus = minBonus;
	}
}
