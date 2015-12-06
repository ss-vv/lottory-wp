package com.xhcms.lottery.commons.data;

import java.io.Serializable;

public class MayPMVoucher implements Serializable {

	private static final long serialVersionUID = -6659953144598286526L;

	private int weekNum;

    private int fwAmount;

    private int swAmount;

    private int twAmount;

    private int fowAmount;

    private int fiwAmount;

    private String fwVoucher;

    private String swVoucher;

    private String twVoucher;

    private String fowVoucher;

    private String fiwVoucher;

	public int getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}

	public int getFwAmount() {
		return fwAmount;
	}

	public void setFwAmount(int fwAmount) {
		this.fwAmount = fwAmount;
	}

	public int getSwAmount() {
		return swAmount;
	}

	public void setSwAmount(int swAmount) {
		this.swAmount = swAmount;
	}

	public int getTwAmount() {
		return twAmount;
	}

	public void setTwAmount(int twAmount) {
		this.twAmount = twAmount;
	}

	public int getFowAmount() {
		return fowAmount;
	}

	public void setFowAmount(int fowAmount) {
		this.fowAmount = fowAmount;
	}

	public int getFiwAmount() {
		return fiwAmount;
	}

	public void setFiwAmount(int fiwAmount) {
		this.fiwAmount = fiwAmount;
	}

	public String getFwVoucher() {
		return fwVoucher;
	}

	public void setFwVoucher(String fwVoucher) {
		this.fwVoucher = fwVoucher;
	}

	public String getSwVoucher() {
		return swVoucher;
	}

	public void setSwVoucher(String swVoucher) {
		this.swVoucher = swVoucher;
	}

	public String getTwVoucher() {
		return twVoucher;
	}

	public void setTwVoucher(String twVoucher) {
		this.twVoucher = twVoucher;
	}

	public String getFowVoucher() {
		return fowVoucher;
	}

	public void setFowVoucher(String fowVoucher) {
		this.fowVoucher = fowVoucher;
	}

	public String getFiwVoucher() {
		return fiwVoucher;
	}

	public void setFiwVoucher(String fiwVoucher) {
		this.fiwVoucher = fiwVoucher;
	}

}
