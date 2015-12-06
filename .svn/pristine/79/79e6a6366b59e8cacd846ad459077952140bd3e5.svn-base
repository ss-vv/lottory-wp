package com.unison.lottery.pm.web.action;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.pm.data.ShowFollowResult;
import com.unison.lottery.pm.service.ShowFollowWinListService;
import com.xhcms.commons.lang.Data;

/**
 * 晒单跟单TOP榜单
 * @author yongli zhu
 *
 */
public class AjPmSfTOPListAction extends BaseAction{

	private static final long serialVersionUID = 6950898713088223009L;

	@Autowired
	private ShowFollowWinListService showFollowWinListService;
	
	/**
	 * 排名列表大小
	 */
	private int top=20;
		
	private Data data;

	/**
	 * 活动id
	 */
	private Long promotionId;

	@Override
    public String execute() throws Exception {
		List<ShowFollowResult> results = showFollowWinListService.findShowFollowTOP(top, promotionId);
		data = Data.success(results);
        return SUCCESS;
	}
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}	
	
	public Long getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Long promotionId) {
		this.promotionId = promotionId;
	}
}
