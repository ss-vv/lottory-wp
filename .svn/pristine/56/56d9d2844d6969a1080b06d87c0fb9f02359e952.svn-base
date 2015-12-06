package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.vo.PromptMatchVO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.SearchService;

public class SearchMatchAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private SearchService searchService;
	private String key;
	private List<PromptMatchVO> data;
	public String execute(){
		pageRequest.setPageIndex(1);
		pageRequest.setPageSize(10);
		data = searchService.querryPromptMatch(key, pageRequest);
		return SUCCESS;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<PromptMatchVO> getData() {
		return data;
	}
}
