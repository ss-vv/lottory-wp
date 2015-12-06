package com.xhcms.lottery.commons.data.bonus;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 等价集合
 * 
 * @author lei.li@davcai.com
 */
public class EquivalentOptionList {

	private List<JCLQBetOptGroup> jclqEquOptions = new ArrayList<JCLQBetOptGroup>();
	private List<JCZQBetOptGroup> jczqEquOptions = new ArrayList<JCZQBetOptGroup>();

	public List<JCLQBetOptGroup> getJclqEquOptions() {
		return jclqEquOptions;
	}

	public void setJclqEquOptions(List<JCLQBetOptGroup> jclqEquOptions) {
		this.jclqEquOptions = jclqEquOptions;
	}

	public List<JCZQBetOptGroup> getJczqEquOptions() {
		return jczqEquOptions;
	}

	public void setJczqEquOptions(List<JCZQBetOptGroup> jczqEquOptions) {
		this.jczqEquOptions = jczqEquOptions;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
