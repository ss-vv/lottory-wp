package com.unison.lottery.weibo.web.action.pc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.LCIssueService;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.ScoreService;
import com.xhcms.lottery.lang.Constants;

/**
 * 比分直播
 * @author Wang Lei
 *
 */
public class ScoreAction extends BaseAction{

	private static final long serialVersionUID = -7915311989920730857L;
	@Autowired
	private ScoreService scoreService;
	@Autowired
	private LCIssueService lCIssueService;
	private String date;
	public String[][] result;
	private PageResult<QTMatchVO> data = new PageResult<QTMatchVO>();
	
	// ajax获取竞彩足球比分
	public String loadJZ(){
		Date day = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(StringUtils.isNotBlank(date)){
				day = sdf.parse(date);
			}
			setData(scoreService.listJCZQscore(day));
		} catch (Exception e) {
			log.debug("",e);
		}
		
		return SUCCESS;
	}
	
	// ajax获取竞彩篮球比分
	public String loadJL(){
		return SUCCESS;
	}
	
	// ajax获取传统足彩比分
	public String loadCT(){
		try {
			setData(scoreService.listCTZCscore(date));
		} catch (Exception e) {
			log.debug("",e);
		}
		
		return SUCCESS;
	}
	
	public String JCZQ(){
		return SUCCESS;
	}
	public String JCLQ(){
		return SUCCESS;
	}
	public String CTZC(){
		result = lCIssueService.getCTIssueInfo(Constants.PLAY_24_ZC_14,null);
		return SUCCESS;
	}
	
	public PageResult<QTMatchVO> getData() {
		return data;
	}
	public void setData(PageResult<QTMatchVO> data) {
		this.data = data;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
