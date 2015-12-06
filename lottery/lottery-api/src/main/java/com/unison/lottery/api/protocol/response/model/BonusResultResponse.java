package com.unison.lottery.api.protocol.response.model;

import java.util.List;
import com.unison.lottery.api.protocol.common.model.Item;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class BonusResultResponse extends HaveReturnStatusResponse {

	private String name;
	
	private String status;
	
	private String desc;
	
	private List<Item> list;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<Item> getList() {
		return list;
	}

	public void setList(List<Item> list) {
		this.list = list;
	}
}