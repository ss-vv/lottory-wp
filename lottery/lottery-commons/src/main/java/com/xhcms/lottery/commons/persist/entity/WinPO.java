package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name = "lt_win")
public class WinPO extends WinPOBase {

	private static final long serialVersionUID = -8790173461294609191L;

	public WinPO() {

	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
