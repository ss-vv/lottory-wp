package com.unison.lottery.wap.action.ssq;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.ucenter.lang.Constant;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.data.BetOption;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.ChooseType;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.commons.persist.service.DigitalBetService;
import org.springframework.beans.factory.annotation.Autowired;
import com.laicai.util.ComputerUtils;
import com.laicai.lotteryCategory.frequence.GroupSelectionStrategy;

/**
 * 投注相关 action.
 * 
 * @author 张得斌
 */
public class BuyAction extends BaseAction{
	private static final long serialVersionUID = 7861315462015474321L;

 	@Autowired
 	private IssueService issueService;
 	
 	@Autowired
	private DigitalBetService digitalBetService;
 	
	// 彩票种类id
	private String lotteryId = "SSQ" ;
 	
 	private IssueInfo issueInfo;

	private Integer playId;
	
	// 投注倍数
	private String times = "1";

	// 供用户选择的红球号码集合
	private String redBalls = new String();

	// 供用户选择的蓝球号码集合
	private String blueBalls = new String();
	
	// 供用户选择的红球胆号集合
	private String redDanBalls = new String();
	
	// 供用户选择的红球拖号集合
	private String redTuoBalls = new String();
	
	// 错误信息
 	private String error;
 	
 	//投注内容
 	private java.util.List<String> resultList = new ArrayList<String>();
	private java.util.List<String> selectRedBalls;
	private java.util.List<String> selectDanBalls;
	private java.util.List<String> selectTuoBalls;
	private java.util.List<String> selectBlueBalls;
    
    private String redirectURL;

	public String getRedirectURL() {
		return this.redirectURL;
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	// 选择类型：0 手选；1胆拖；2机选
	private Integer selectType;

	//投注数量
 	private Integer betCount;
 	
 	//随机生成投注的数量
 	private Integer randomCount = 5;
 	
 	//随机输入的投注数量
 	private String inputRandomNum;
	
	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}
	
	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public String getInputRandomNum() {
		return inputRandomNum;
	}

	public void setInputRandomNum(String inputRandomNum) {
		this.inputRandomNum = inputRandomNum;
	}

	public Integer getRandomCount() {
		return randomCount;
	}

	public void setRandomCount(Integer randomCount) {
		this.randomCount = randomCount;
	}
	
	public String getRedBalls() {
		return this.redBalls;
	}

	public void setRedBalls(String redBalls) {
		this.redBalls = redBalls;
	}

	public String getBlueBalls() {
		return this.blueBalls;
	}

	public void setBlueBalls(String blueBalls) {
		this.blueBalls = blueBalls;
	}

	public String getRedDanBalls() {
		return this.redDanBalls;
	}

	public void setRedDanBalls(String redDanBalls) {
		this.redDanBalls = redDanBalls;
	}

	public String getRedTuoBalls() {
		return this.redTuoBalls;
	}

	public void setRedTuoBalls(String redTuoBalls) {
		this.redTuoBalls = redTuoBalls;
	}
	public java.util.List<String> getSelectRedBalls() {
		return this.selectRedBalls;
	}

	public void setSelectRedBalls(java.util.List<String> selectRedBalls) {
		this.selectRedBalls = selectRedBalls;
	}
	
	public java.util.List<String> getSelectDanBalls() {
		return this.selectDanBalls;
	}

	public void setSelectDanBalls(java.util.List<String> selectDanBalls) {
		this.selectDanBalls = selectDanBalls;
	}

	public java.util.List<String> getSelectTuoBalls() {
		return this.selectTuoBalls;
	}

	public void setSelectTuoBalls(java.util.List<String> selectTuoBalls) {
		this.selectTuoBalls = selectTuoBalls;
	}

	public java.util.List<String> getSelectBlueBalls() {
		return this.selectBlueBalls;
	}

	public void setSelectBlueBalls(java.util.List<String> selectBlueBalls) {
		this.selectBlueBalls = selectBlueBalls;
	}
 	
 	//最近5期开奖号码
 	private java.util.List<IssueInfo> ballotRecordList;
	
	public String getLotteryId() {
		return this.lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public Integer getPlayId() {
		return this.playId;
	}

	public void setPlayId(Integer playId) {
		this.playId = playId;
	}
 	
 	public IssueInfo getIssueInfo() {
		return this.issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	public java.util.List<IssueInfo> getList() {
		return this.ballotRecordList;
	}

	public java.util.List<String> getResultList() {
		return resultList;
	}

	public void setResultList(java.util.List<String> resultList) {
		this.resultList = resultList;
	}
	
	/**
	 * 显示手选第一页.
	 */
	@Override
	public String execute() {
		return custom();
	}
	
	public String custom() {
		String actionResult = INPUT;
		
		this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.
		this.getHistoryIssueInfos(5);
		
		return actionResult;
	}
	
	public String random() {
		String actionResult = INPUT;
		
		if(this.lotteryId == null){
			addActionError("请选择彩种!");
		}else{
			this.getHistoryIssueInfos(5);
			this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.
			
			if (this.inputRandomNum == null) {
				this.inputRandomNum = String.valueOf(5);
			}
			
			if (!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")) {
				error = "请输入机选注数1-99";
				actionResult = INPUT;
			}
			else {
				randomCount = Integer.valueOf(inputRandomNum);
				betCount = randomCount;

				ArrayList<ArrayList<Integer>> redRandomNumbers = new GroupSelectionStrategy(6).getRandomRes(randomCount, 1, 33);
				ArrayList<ArrayList<Integer>> blueRandomNumbers = new GroupSelectionStrategy(1).getRandomRes(randomCount, 1, 16);
				
				//生成投注内容
				this.generateContent(redRandomNumbers, blueRandomNumbers);

				this.setRequestParam();
			}
		}

		return actionResult;
	}
	
	public String xuanDan() {
		String actionResult = INPUT;

		this.getHistoryIssueInfos(5);
		this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.

		return actionResult;
	}
	
	public String xuanOther() {
		String actionResult;

		this.getHistoryIssueInfos(5);
		this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.
		
		int redDanCount = 0;
		if (this.redDanBalls != null && this.redDanBalls.length() > 0) {
			String[] selectedBalls = this.redDanBalls.split(",");
			redDanCount = selectedBalls == null ? 0 : selectedBalls.length;
			this.selectDanBalls = java.util.Arrays.asList(selectedBalls);
		}

		if (redDanCount > 0 && redDanCount <= 5) {
			actionResult = SUCCESS;
		}
		else {
			actionResult = "input_dan";
			super.addActionError("红球胆码最少选1个,最多选5个，您选了" + redDanCount + "个");
		}

		return actionResult;
	}
	
	public String submitOrder() {
		String actionResult = "";

		ChooseType chooseType = ChooseType.valueOfIndex(this.selectType);
		if (chooseType == null) {
			chooseType = ChooseType.MANUAL;
			this.selectType = chooseType.getIndex();
		}

		int multipleTimes = 0;
		try {
			multipleTimes = Integer.valueOf(this.times).intValue();
		}
		catch (NumberFormatException exception) {
			super.addActionError("投注倍数不能为空且必须为数字.");
		}

		PlayType tmpPlayType = PlayType.valueOfPlayId(this.playId);
		switch (tmpPlayType) {
			case SSQ_DS:
			case SSQ_FS:
			case SSQ_DT:
				if (multipleTimes < 1 || this.resultList == null || this.resultList.isEmpty()) {
					switch (tmpPlayType) {
						case SSQ_DS:
							actionResult = "input_ds";
							break;

						case SSQ_FS:
							actionResult = "input_fs";
							break;

						case SSQ_DT:
							actionResult = "input_dt_other";
							break;
					}
				}
				else {
					
					this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.

					try {
						DigitalBetRequest orderBetRequest = new DigitalBetRequest();

						orderBetRequest.setMultiple(multipleTimes);
						orderBetRequest.setLotteryId(this.lotteryId);
						orderBetRequest.setPlayType(tmpPlayType);
						orderBetRequest.setBetContents(this.resultList);
						orderBetRequest.setIssue(this.issueInfo.getIssueNumber());
						orderBetRequest.setChooseType(chooseType);

						BetScheme orderScheme = this.digitalBetService.prepareBet(orderBetRequest);
						this.betCount = orderScheme.getBetNote();
						orderBetRequest.setTotalNotesCount(this.betCount);
						orderBetRequest.setMoney(this.betCount * multipleTimes * 2);
						
						User user = (User) request.getSession().getAttribute(Constant.USER_KEY);
						if (user == null) {
							request.getSession().setAttribute(Constant.USER_SCHEME_KEY, orderScheme);
							int serverPort = request.getServerPort();
							this.redirectURL = "/login.do?referer=" + "http://" + request.getServerName() + (serverPort == 80 ? "" : ":" + serverPort) + "/ssq/order_confirm.do";
							actionResult = LOGIN;
						}
						else {
							orderBetRequest.setUserId(user.getId());
							orderScheme = this.digitalBetService.bet(orderBetRequest);
							request.setAttribute("scheme", orderScheme);
							actionResult = SUCCESS;
						}
					}
					catch (BetException exception) {
						System.out.println(exception.getStackTrace());
						super.addActionError(getText("error." + exception.getErrorCode()));
					}

					request.setAttribute("issueNumber", issueInfo.getIssueNumber());
				}
				break;

			default:
				actionResult = ERROR;
				super.addActionError("双色球的玩法类型只支持单式、复式与胆拖.");
				break;
		}

		return actionResult;
	}
	
	/**
	 * 用户再次确认购买页面
	 */
	public String confirm() {
		String actionResult = null;
		this.getCurrentIssueInfo(); // 获取正在销售的当前期次信息.
		this.getHistoryIssueInfos(5);

		int redBallCount = 0;
		int blueBallCount = 0;
		
		int multipleTimes = 0;
		try {
			multipleTimes = Integer.valueOf(this.times).intValue();
		}
		catch (NumberFormatException exception) {
			super.addActionError("投注倍数不能为空且必须为数字.");
		}
		
		if(!isPost()){
			//  从session里读取
			User user = (User) request.getSession().getAttribute(Constant.USER_KEY);
			if (user != null) {
				// 仅用户登录后从session里读取(暂时逻辑)
				BetScheme orderScheme = (BetScheme) (request.getSession().getAttribute(Constant.USER_SCHEME_KEY));
				request.getSession().removeAttribute(Constant.USER_SCHEME_KEY); // 删除session值
				if (orderScheme != null) {
					DigitalBetRequest orderBetRequest = orderScheme.getDigitalBetRequest();
					if (orderBetRequest != null) {
						this.times = String.valueOf(orderBetRequest.getMultiple());
						this.lotteryId = orderBetRequest.getLotteryId();
						this.playId = orderBetRequest.getPlayType().toInt();
						this.selectType = Integer.valueOf(orderBetRequest.getChooseType().getIndex());
						
						java.util.List<String> betContentList = orderBetRequest.getBetContents();
						switch (orderBetRequest.getPlayType()) {
							case SSQ_DS:
								this.resultList = orderBetRequest.getBetContents();
								break;

							case SSQ_FS:
								if (betContentList != null && betContentList.size() > 0) {
									String betContent = betContentList.get(0);
									String[] betBallContents = betContent.split(BetOption.BAR_REGEXP);
									this.redBalls = betBallContents[0];
									this.blueBalls = betBallContents[1];
								}
								break;

							case SSQ_DT:
								if (betContentList != null && betContentList.size() > 0) {
									String betContent = betContentList.get(0);
									String[] betBallContents = betContent.split(BetOption.BAR_REGEXP);
									this.blueBalls = betBallContents[1];
									
									String[] danTuoBalls = betBallContents[0].split(BetOption.AT_SYMBOL);
									this.redDanBalls = danTuoBalls[0];
									this.redTuoBalls = danTuoBalls[1];
								}
								break;
						}
					}
				}
			}
		}
		
		boolean checkSucceed = false;
		PlayType tmpPlayType = PlayType.UNKNOWN;
		if(null != playId) {
			tmpPlayType = PlayType.valueOfPlayId(this.playId);
		}
		switch (tmpPlayType) {
			case SSQ_DS:
				this.betCount = this.resultList == null ? 0 : this.resultList.size();
				if (multipleTimes < 1 || multipleTimes > 99) {
					super.addActionError("投注倍数最大为99，最小为1.");
				}
				else {
					if (this.betCount * multipleTimes <= 10000) {
						checkSucceed = true;
					}
					else {
						super.addActionError("双色球不能发起超过1万注的方案");
					}
				}
				
				if (!checkSucceed) {
					actionResult = "input_ds";
				}
				break;
				
			case SSQ_FS:
				if (this.redBalls != null && this.redBalls.length() > 0) {
					String[] selectedBalls = this.redBalls.split(",");
					redBallCount = selectedBalls == null ? 0 : selectedBalls.length;
					this.selectRedBalls = java.util.Arrays.asList(selectedBalls);
				}

				if (this.blueBalls != null && this.blueBalls.length() > 0) {
					String[] selectedBalls = this.blueBalls.split(",");
					blueBallCount = selectedBalls == null ? 0 : selectedBalls.length;
					this.selectBlueBalls = java.util.Arrays.asList(selectedBalls);
				}

				if (redBallCount < 6 || redBallCount > 16 || blueBallCount < 1 || blueBallCount > 16) {
					if (redBallCount < 6) {
						super.addActionError("红球最少选6个，您只选了" + redBallCount + "个");
					}
					if(redBallCount > 16) {
						super.addActionError("红球最多选16个，您选了" + redBallCount + "个");
					}
					if (blueBallCount < 1) {
						super.addActionError("蓝球最少选1个，您只选了" + blueBallCount + "个");
					}
					if(blueBallCount > 16) {
						super.addActionError("蓝球最多选16个，您选了" + blueBallCount + "个");
					}
				}
				else if (multipleTimes < 1 || multipleTimes > 99) {
					super.addActionError("投注倍数最大为99，最小为1.");
				}
				else {
					if (redBallCount == 6 && blueBallCount == 1) { // 如果用户选择的是6+1则设置为单式投注
						this.playId = PlayType.SSQ_DS.toInt();
					}
					
					long redCombCount = ComputerUtils.combination(redBallCount, 6);
					if (redCombCount * blueBallCount * multipleTimes > 10000) {
						super.addActionError("双色球不能发起超过1万注的方案");
					}
					else {
						checkSucceed = true;
						this.betCount = (int)redCombCount * blueBallCount;
						String betContent = this.redBalls.toString() + BetOption.VERTICALBAR + this.blueBalls.toString();
						this.resultList.add(betContent.replaceAll(" ", "")); // 过滤所有空格.
					}
				}

				if (!checkSucceed) {
					actionResult = "input_fs";
				}
				break;
				
			case SSQ_DT:
				int redDanCount = 0;
				int redTuoCount = 0;
				if (this.redDanBalls != null && this.redDanBalls.length() > 0) {
					String[] selectedBalls = this.redDanBalls.split(",");
					redDanCount = selectedBalls == null ? 0 : selectedBalls.length;
					this.selectDanBalls = java.util.Arrays.asList(selectedBalls);
				}

				if (redDanCount < 1 || redDanCount > 6) {
					actionResult = "input_dt_dan";
					super.addActionError("红球胆码最少选1个,最多选5个，您选了" + redDanCount + "个");
				}
				else {
					if (this.redTuoBalls != null && this.redTuoBalls.length() > 0) {
						String[] selectedBalls = this.redTuoBalls.split(",");
						redTuoCount = selectedBalls == null ? 0 : selectedBalls.length;
						this.selectTuoBalls = java.util.Arrays.asList(selectedBalls);
					}

					if (this.blueBalls != null && this.blueBalls.length() > 0) {
						String[] selectedBalls = this.blueBalls.split(",");
						blueBallCount = selectedBalls == null ? 0 : selectedBalls.length;
						this.selectBlueBalls = java.util.Arrays.asList(selectedBalls);
					}
					if (redDanCount + redTuoCount < 7 || redDanCount + redTuoCount > 16 || blueBallCount < 1) {
						actionResult = "input_dt_other";
						if (redDanCount + redTuoCount < 7) {
							super.addActionError("拖码选择有误, 胆码+拖码≥7");
						}
						if(redDanCount + redTuoCount > 16) {
							super.addActionError("红球选择范围（6~16个）！");
						}
						if (blueBallCount < 1) {
							super.addActionError("蓝球最少选1个，您只选了0个");
						}
					}
					else if (multipleTimes < 1 || multipleTimes > 99) {
						actionResult = "input_dt_other";
						super.addActionError("投注倍数最大为99，最小为1.");
					}
					else {
						int tmpDanMaximum = 6 - redDanCount;
						long redCombCount = ComputerUtils.combination(redTuoCount, tmpDanMaximum);

						if (redCombCount * blueBallCount * multipleTimes > 10000) {
							super.addActionError("双色球不能发起超过1万注的方案");
						}
						else {
							checkSucceed = true;
							this.betCount = (int)redCombCount * blueBallCount;

							String betContent = this.redDanBalls.toString() + BetOption.AT_SYMBOL + this.redTuoBalls.toString() + BetOption.VERTICALBAR + this.blueBalls.toString();
							this.resultList.add(betContent.replaceAll(" ", "")); // 过滤所有空格.
						}
					}
				}
				break;
		}
		
		this.setRequestParam();
		
		return checkSucceed ? "order_confirm" : actionResult;
	}
	
	private void getCurrentIssueInfo()
	{
		try {
			this.issueInfo = this.issueService.getCurrentSalingIssueOfWF(this.lotteryId, new java.util.Date());
		}
		catch (BetException exception) {
			System.out.println(exception.getStackTrace());
			addActionError(getText("error." + exception.getErrorCode()));
		}
	}

	private void getHistoryIssueInfos(int maxResults) {
		if (maxResults < 0) {
			maxResults = 5;
		}
		if (maxResults > 100) {
			maxResults = 100;
		}

		String[] status = new String[] { "3", "4" };
		String stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
		if(Constants.JX11.equals(lotteryId)) {
			this.ballotRecordList = this.issueService.findBBallotRecords(this.lotteryId, status, stopTime, maxResults);
		} else if(Constants.SSQ.equals(lotteryId)) {
			this.ballotRecordList = this.issueService.findBBallotRecordsOfWF(this.lotteryId, status, stopTime, maxResults);
		}
	}
	
	private void setRequestParam() {
		request.setAttribute("betCount", this.betCount);// 注数
		request.setAttribute("resultList", this.resultList); // 投注内容

		try {
			int multiple = Integer.valueOf(this.times).intValue();
			request.setAttribute("times", Integer.valueOf(multiple)); // 倍数
		}
		catch (NumberFormatException exception) {
			System.out.println(exception.getStackTrace());
		}
	}
	
	private void generateContent(ArrayList<ArrayList<Integer>> redNumbers, ArrayList<ArrayList<Integer>> blueNumbers) {
		if (redNumbers == null || blueNumbers == null) {
			throw new NullPointerException();
		}
		else if (redNumbers.size() != blueNumbers.size()) {
			throw new IllegalArgumentException();
		}
		
		for (int i = 0; i < redNumbers.size(); i++) {
			StringBuffer stringBuffer = new StringBuffer();
			
			ArrayList<Integer> redNumberList = redNumbers.get(i);
			ArrayList<Integer> blueNumberList = blueNumbers.get(i);
			for (int j = 0; j < redNumberList.size(); j++) {
				Integer redNumber = redNumberList.get(j);
				if (j > 0) {
					stringBuffer.append(BetOption.COMMA);
				}
				
				//把小于10的数字前加0
				if(redNumber<10){
					stringBuffer.append("0");
				}
				stringBuffer.append(redNumber);
			}

			stringBuffer.append(BetOption.VERTICALBAR);
			
			for (int j = 0; j < blueNumberList.size(); j++) {
				Integer blueNumber = blueNumberList.get(j);
				if (j > 0) {
					stringBuffer.append(BetOption.COMMA);
				}
				
				//把小于10的数字前加0
				if(blueNumber<10){
					stringBuffer.append("0");
				}
				stringBuffer.append(blueNumber);
			}

			this.resultList.add(stringBuffer.toString());
		}
	}
}