package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.NewWinListService;
import com.xhcms.lottery.commons.data.ShowWinListVo;

public class NewWinListAction extends BaseAction{

	private static final long serialVersionUID = 2631430924536263326L;
	@Autowired
	private NewWinListService newWinListService;
	private PageResult<ShowWinListVo> data=new PageResult<ShowWinListVo>();
	/**
	 * 晒单助人7日奖金
	 * @return
	 */
	public String showDay7SchemeWin(){
		List<ShowWinListVo> list=newWinListService.findShowWinList(Keys.DAY_7_SHOW_SCHEME_HELP_BONUS);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 跟单中奖 7日
	 * @return
	 */
	public String showDay7FollowSchemeWin(){
		List<ShowWinListVo> list=newWinListService.findFollowWinList(Keys.DAY_7_FOLLOW_SCHEME_BONUS);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 晒单助人50单
	 * @return
	 */
	public String showMatch50SchemeWin(){
		List<ShowWinListVo> list=newWinListService.findMatch50ShowSchemeWinList(Keys.MATCH_50_SHOW_SCHEME_HELP_BONUS);
		data.setResults(list);
		return SUCCESS;
		
	}
	/**
	 * 跟单50单中奖
	 * @return
	 */
	public String showMatch50FollowSchemeWin(){
		
		List<ShowWinListVo> list=newWinListService.findMatch50FollowSchemeWinList(Keys.MATCH_50_FOLLOW_SCHEME_BONUS);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 7日内晒单胜率
	 * @return
	 */
	public String showDay7SchemeWinShengLv(){
		List<ShowWinListVo> list=newWinListService.findDay7ShowSchemeYingLiLv(Keys.DAY_7_SHOW_SCHEME_SHENGLV);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 晒单 50单胜率
	 * @return
	 */
	public String showMatch50SchemeWinShengLv(){
		List<ShowWinListVo> list=newWinListService.findMatch50ShowSchemeYingLiLv(Keys.MATCH_50_SHOW_SCHEME_SHENGLV);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 跟单7日内胜率
	 * @return
	 */
	public String showDay7FollowSchemeWinYingliLv(){
		List<ShowWinListVo> list=newWinListService.findDay7FollowSchemeYingLilv(Keys.DAY_7_FOLLOW_SCHEME_SHENGLV);
		data.setResults(list);
		return SUCCESS;
	}
	/**
	 * 跟50单胜率
	 * @return
	 */
	public String showMatch50FollowShemeWinYingliLv(){
		List<ShowWinListVo> list=newWinListService.findMatch50FollowSchemeYingLiLv(Keys.MATCH_50_FOLLOW_SCHEME_SHENGLV);
		data.setResults(list);
		return SUCCESS;
	}
	public PageResult<ShowWinListVo> getData() {
		return data;
	}
	public void setData(PageResult<ShowWinListVo> data) {
		this.data = data;
	}

}
