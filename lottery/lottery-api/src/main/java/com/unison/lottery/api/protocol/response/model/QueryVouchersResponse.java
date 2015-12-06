package com.unison.lottery.api.protocol.response.model;

import com.xhcms.commons.lang.Paging;

public class QueryVouchersResponse extends HaveReturnStatusResponse {

	private Paging paging;

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	
	
}
