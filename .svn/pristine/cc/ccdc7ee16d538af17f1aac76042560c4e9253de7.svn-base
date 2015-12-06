package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.CTMatchService;
/**
 * 传统足球比分预设
 * @author haohao
 *
 */
public class AjaxPresetCTZCMatchAction extends BaseAction{

	private static final long serialVersionUID = -144718294987029699L;
	private Data data;
	private String id;
	private String halfScore;//半场比分
	private String score;//全场比分
	private String status;
	@Autowired
	private CTMatchService ctMatchService;
	public String execute(){
		ctMatchService.updateCTMatchScore(id,halfScore,score,status);
		data=Data.success(null);
		return SUCCESS;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHalfScore() {
		return halfScore;
	}
	public void setHalfScore(String halfScore) {
		this.halfScore = halfScore;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
