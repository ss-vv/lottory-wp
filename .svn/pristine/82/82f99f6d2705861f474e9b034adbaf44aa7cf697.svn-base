package com.unison.lottery.weibo.data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果bean
 * @author 王磊
 * 
 */
public class PageResult<T> extends Result implements Serializable {
	private static final long serialVersionUID = 579716545808727533L;

	/**
	 * 指定类型的结果list
	 */
	private List<T> results;
	
	private int totalPages;		// 查询结果总页数
	private long totalResults;		// 查询的总结果数，用来计算总页数

	private PageRequest pageRequest;
	
	public PageResult() {
		
	}
	
	public PageResult(PageRequest pageRequest, List<T> results) {
		this.pageRequest = pageRequest;
		this.results = results;
	}

	public PageRequest getPageRequest() {
		return pageRequest;
	}

	public void setPageRequest(PageRequest pageRequest) {
		this.pageRequest = pageRequest;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public long getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
		this.totalPages = (int)Math.ceil((double)totalResults / pageRequest.getPageSize());
	}
}
