package com.unison.lottery.weibo.web.action.pc.ajax;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.lang.EntityType;
/**
 * 晒单红人榜
 * @author Administrator
 *
 */
public class ScoreTopListAction  extends BaseAction{

	private static final long serialVersionUID = 5785905327172437179L;
	@Autowired
    private UserScoreService userScoreService;
	private String lottery;
	private List<UserScore> data;
	public String loadScoreTopList(){
		//晒单战绩榜
		 data = userScoreService
						.listTopUserScoreOfLottery(lottery, EntityType.TOP_USER_SCORE);
		return SUCCESS;
	}
	public String getLottery() {
		return lottery;
	}
	public void setLottery(String lottery) {
		this.lottery = lottery;
	}
	public List<UserScore> getData() {
		return data;
	}
	public void setData(List<UserScore> data) {
		this.data = data;
	}

	
}
