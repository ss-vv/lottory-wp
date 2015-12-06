package com.xhcms.lottery.dc.feed.web.action;

import org.apache.commons.lang.StringUtils;

import com.xhcms.commons.lang.Paging;

public class BaseListAction extends BaseAction {
	private static final long serialVersionUID = -5377527177409033969L;

	protected int pageNo = 1;
	protected int maxResults = 30;
	protected Paging paging;
	
	public void setPageNo(String pageNo) {
		if (StringUtils.isEmpty(pageNo)) {
			this.pageNo = 1;
		} else {
			try {
				this.pageNo = Integer.parseInt(pageNo);
			} catch (NumberFormatException e) {
				this.pageNo = 1;
			}
		}
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = (maxResults < 0 ? 0 : (maxResults > 100 ? 100
				: maxResults));
	}

	protected void wrapPaging() {
		paging = new Paging(pageNo, maxResults);
	}

	public Paging getPaging() {
		return paging;
	}
}
