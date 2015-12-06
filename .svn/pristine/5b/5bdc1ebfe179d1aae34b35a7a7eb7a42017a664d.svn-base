package com.xhcms.lottery.commons.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Commission implements Serializable {

    private static final long serialVersionUID = -3272723070148265038L;

    private String sponsor;
    
    private BigDecimal commission;

    private Date createdTime;
    
    private Long schemeId;
    
    private Long followSchemeId;
    
    public Commission(){
    	
    }
    
    public Commission(String sponsor, BigDecimal commission,
			Date createdTime, Long schemeId,Long followSchemeId) {
		super();
		this.sponsor = sponsor;
		this.commission = commission;
		this.createdTime = createdTime;
		this.schemeId = schemeId;
		this.followSchemeId = followSchemeId;
	}

	public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public Long getFollowSchemeId() {
		return followSchemeId;
	}

	public void setFollowSchemeId(Long followSchemeId) {
		this.followSchemeId = followSchemeId;
	}
	
}
