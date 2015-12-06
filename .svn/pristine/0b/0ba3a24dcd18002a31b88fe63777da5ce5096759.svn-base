package com.xhcms.lottery.admin.web.action.scheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.service.BetTimeService;
import com.xhcms.lottery.utils.DateUtils;

public class BetTimeAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private BetTimeService betTimeService;
	private List<LotteryOpenSalePO> results;
	private String data;
	
	private Long id;
	private String opentime;
	private String endtime;
	private String mtime;
	private Integer endtimeKua;
	private Integer mtimeKua;
	private String today;
	
	public String save(){
		LotteryOpenSalePO po  = new LotteryOpenSalePO();
		po.setId(id);
		SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
		try {
			po.setOpenTime(df.parse(opentime));
			po.setEndTime(df.parse(endtime));
			po.setMachineOfftime(df.parse(mtime));
			po.setIsEndTimeCrossDay(endtimeKua);
			po.setIsMachineOfftimeCrossDay(mtimeKua);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		betTimeService.save(po);
		data = "success";
		return SUCCESS;
	}
	@Override
	public String execute(){
		results = betTimeService.getLotteryOpenSalePOs();
		today = DateUtils.getWeekDayWithTime(new Date());
		return SUCCESS;
	}

	public List<LotteryOpenSalePO> getResults() {
		return results;
	}

	public String getData() {
		return data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOpentime() {
		return opentime;
	}
	public void setOpentime(String opentime) {
		this.opentime = opentime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public String getToday() {
		return today;
	}
	public void setEndtimeKua(Integer endtimeKua) {
		this.endtimeKua = endtimeKua;
	}
	public void setMtimeKua(Integer mtimeKua) {
		this.mtimeKua = mtimeKua;
	}
}
