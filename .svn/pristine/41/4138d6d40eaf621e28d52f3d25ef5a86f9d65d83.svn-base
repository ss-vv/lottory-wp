package com.unison.lottery.weibo.web.action.pc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.web.action.BaseAction;
import com.unison.lottery.weibo.web.service.BetNumService;
import com.xhcms.lottery.commons.data.BetInfoVo;

public class AjaxBetNumAction extends BaseAction{

	@Autowired
	private BetNumService betNumService;
	private static final long serialVersionUID = 1556347465967402947L;
	private List<BetInfoVo> data;
	public String execute(){
		getBetInfo();
		return SUCCESS;
	}
	private void getBetInfo(){
		List<BetInfoVo> info=new ArrayList<BetInfoVo>();
		String jczq=betNumService.getJCZQBetNum();
		String jclq=betNumService.getJCLQBetNum();
		String bjdc=betNumService.getBJDCBetNum();
		String ctzq=betNumService.getCTZQBetNum();
		String ssq=betNumService.getSSQBetNum();
		BetInfoVo b=new BetInfoVo();
		b.setLotteryName("jczq");
		b.setBetNum(jczq);
		info.add(b);
		b=new BetInfoVo();
		b.setLotteryName("jclq");
		b.setBetNum(jclq);
		info.add(b);
		b=new BetInfoVo();
		b.setLotteryName("bjdc");
		b.setBetNum(bjdc);
		info.add(b);
		b=new BetInfoVo();
		b.setLotteryName("ctzq");
		b.setBetNum(ctzq);
		info.add(b);
		b=new BetInfoVo();
		b.setLotteryName("ssq");
		b.setBetNum(ssq);
		info.add(b);
		data=info;
	}
	public List<BetInfoVo> getData() {
		return data;
	}
	public void setData(List<BetInfoVo> data) {
		this.data = data;
	}
	
	
	

}
