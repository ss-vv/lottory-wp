package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;


@Entity
@Table(name = "lt_dictionary")
public class DictionaryPO implements Serializable{

	private static final long serialVersionUID = 3738459553566616153L;

	@Id
	@Column(name = "datakey", unique = true, nullable = false)
    private String datakey;
	
	private String datavalue;

	public String getDatakey() {
		return datakey;
	}

	public void setDatakey(String datakey) {
		this.datakey = datakey;
	}

	public String getDatavalue() {
		return datavalue;
	}

	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}



}
