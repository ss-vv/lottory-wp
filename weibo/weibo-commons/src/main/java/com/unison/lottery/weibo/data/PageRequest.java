package com.unison.lottery.weibo.data;

import java.io.Serializable;

/**
 * 分页请求bean
 * @author Wang Lei
 *
 */
public class PageRequest implements Serializable {
	private static final long serialVersionUID = 519308039634486978L;
	
	/**
	 * 当前页码下标，从1开始。
	 */
	private int pageIndex = 1;
	
	/**
	 * 每页记录数
	 */
	private int pageSize = 15;
	
	/**
	 * 总记录数
	 */
	private int recordCount = 0;
	
	/**
	 * 总页数
	 */
	private int pageCount = 0;

	/**
	 * 换页按钮个数
	 */
	private int pageButtons = 10;

	/**
	 * 排序字段. 页面记录按照本字段排序。
	 */
	private String sortName;
	
	/**
	 * 排序顺序，"asc"正序，"desc"反序
	 */
	private String sortOrder = SORT_ORDER_DESC;

	/**
	 * 升序
	 */
	public static final String SORT_ORDER_ASC = "asc";
	
	/**
	 * 降序
	 */
	public static final String SORT_ORDER_DESC = "desc";

	/**
	 * 重新计算页数.
	 */
	public void calculate() {
		pageCount = (recordCount + pageSize - 1) / pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	/**
	 * 根据 pageIndex 和 pageSize 计算本页第一条记录的下标（下标从0开始）。
	 * @return 本页第一条记录的下标。
	 */
	public int getOffset() {
		if (this.pageIndex > 0 && this.pageSize > 0)
			return (this.pageIndex - 1) * this.pageSize;
		return 0;
	}

	/**
	 * 页号，从1开始。
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getSortName() {
		return sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		calculate();
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public int getRecordCount(){
		return this.recordCount;
	}
	
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
		calculate();
	}

	public int getPageButtons() {
		return pageButtons;
	}

	public void setPageButtons(int pageButtons) {
		this.pageButtons = pageButtons;
	}

	/**
	 * 前台用到，计算起始页号。
	 */
	public int getPageStart() {
		return Math.max(pageIndex - pageButtons / 3, 1);
	}

	/**
	 * 前台用到，计算结束页号。
	 */
	public int getPageEnd() {
		return Math.min(getPageStart() + pageButtons, getPageCount());
	}
}
