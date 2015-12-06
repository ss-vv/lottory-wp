package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lt_win")
public class WinPOWithSchemePO extends WinPOBase {
	
	private static final long serialVersionUID = -4933960422053227007L;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "scheme_id", updatable=false, insertable=false)
	private BetSchemePO scheme;

	public BetSchemePO getScheme() {
		return scheme;
	}

	public void setScheme(BetSchemePO scheme) {
		this.scheme = scheme;
	}


}
