package com.unison.lottery.weibo.data.service.store.data;

import java.util.List;

/**
 * 篮球大小分页面数据对象
 * 
 * @desc
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-13
 * @version 1.0
 */
public class BBOddsBigSmallResult extends Team {

	private List<BBOddsBigSmallVO> bbOddsBigSmallList;

	public List<BBOddsBigSmallVO> getBbOddsBigSmallList() {
		return bbOddsBigSmallList;
	}

	public void setBbOddsBigSmallList(List<BBOddsBigSmallVO> bbOddsBigSmallList) {
		this.bbOddsBigSmallList = bbOddsBigSmallList;
	}
}