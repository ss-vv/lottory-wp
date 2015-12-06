package com.unison.lottery.wap.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.laicai.lotteryCategory.ComputerFactory;
import com.laicai.lotteryCategory.LotteryCategoryEnum;
import com.laicai.lotteryCategory.LotteryComputer;
import com.laicai.lotteryCategory.frequence.AnySelectionStrategy;
import com.laicai.lotteryCategory.frequence.DirectSelectionStrategy;
import com.laicai.lotteryCategory.frequence.GroupSelectionStrategy;
import com.unison.lottery.wap.data.CustomCheckBox;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.BetException;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;


public class JX11s5Action extends BaseAction {
	

	private static final long serialVersionUID = -6657932965202032013L;
	//彩票种类id
	private String lotteryId;
	//玩法id
	private Integer playId;
	//任|直|组选几个数
	private Integer selectNum;
	//玩法名字
	private String playName;
	//选择类型：0 手选；1胆拖；2机选
	private Integer selectType;
	//投注倍数
	private String times = "1";
	//供用户选择的第一位的集合
	private String firstGroup=new String();
	//供用户选择的第二位的集合
	private String secondGroup=new String();
	//供用户选择的第三位的集合
	private String thirdGroup=new String();
	//错误信息
 	private String error;
 	private StringBuffer betContent = new StringBuffer();
 	//投注内容
 	private String content;
 	//胆码内容
 	private String danContent;

	//投注数量
 	private Integer betCount;
 	//随机生成投注的数量
 	private Integer randomCount = 5;
 	//随机输入的投注数量
 	private String inputRandomNum;
 	
 	//投注内容
 	private List<String> resultList = new ArrayList<String>();
 	
 	@Autowired
 	private IssueService issueService;
 	
 	private IssueInfo issueInfo;
 	
 	//最近5期开奖号码
 	private List<IssueInfo> ballotRecordList;
 	
	public IssueInfo getIssueInfo() {
		return issueInfo;
	}

	public void setIssueInfo(IssueInfo issueInfo) {
		this.issueInfo = issueInfo;
	}

	public List<String> getResultList() {
		return resultList;
	}

	public void setResultList(List<String> resultList) {
		this.resultList = resultList;
	}

	public String getDanContent() {
		return danContent;
	}

	public void setDanContent(String danContent) {
		this.danContent = danContent;
	}
 	
 	private boolean isSelectDan;
 	
 	public boolean isSelectDan() {
		return isSelectDan;
	}

	public void setSelectDan(boolean isSelectDan) {
		this.isSelectDan = isSelectDan;
	}

	private List<CustomCheckBox> first = new ArrayList<CustomCheckBox>();

 	
	public List<CustomCheckBox> getFirst() {
		return first;
	}

	public void setFirst(List<CustomCheckBox> first) {
		this.first = first;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}

	public Integer getPlayId() {
		return playId;
	}

	public void setPlayId(Integer playId) {
		this.playId = playId;
	}

	public Integer getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(Integer selectNum) {
		this.selectNum = selectNum;
	}

	public String getPlayName() {
		return playName;
	}

	public void setPlayName(String playName) {
		this.playName = playName;
	}

	public Integer getSelectType() {
		return selectType;
	}

	public void setSelectType(Integer selectType) {
		this.selectType = selectType;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getFirstGroup() {
		return firstGroup;
	}

	public void setFirstGroup(String firstGroup) {
		this.firstGroup = firstGroup;
	}

	public String getSecondGroup() {
		return secondGroup;
	}

	public void setSecondGroup(String secondGroup) {
		this.secondGroup = secondGroup;
	}

	public String getThirdGroup() {
		return thirdGroup;
	}

	public void setThirdGroup(String thirdGroup) {
		this.thirdGroup = thirdGroup;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String execute(){
		if(lotteryId != null){
			try {
				String[] status = new String[]{"3", "4"};
				int maxResults = 5;
				String stopTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
				ballotRecordList = issueService.findBBallotRecords(lotteryId, status, stopTime, maxResults);
				issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
			} catch (BetException e) {
				addActionError(getText("error."+e.getErrorCode()));
			}
		}else{
			addActionError("请选择彩种");
		}
		
		return SUCCESS;
	}
	
	
	
	public String selectPlay(){
		if(playId !=null){
			try {
				issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
			} catch (BetException e) {
				addActionError(getText("error."+e.getErrorCode()));
			}
			initFirstGroupChkbox();
		}else{
			addActionError("请选择玩法");
		}
		return INPUT;
	}
	
	public String computeBet(){
		initFirstGroupChkbox();
		String[] firstChkValues = null;
		String[] secondChkValues =null;
		String[] thirdChkValues = null;
		try {
			issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
		} 
		catch (BetException e) {
			addActionError(getText("error."+e.getErrorCode()));
			return INPUT;
		}
		String result=INPUT;
		
		
			if(!times.matches("^([1-9]|[1-9][0-9])$")){
				error = "请输入1-99倍";
				return INPUT;
			}
			
			User user  = (User)request.getSession().getAttribute(Constant.USER_KEY);
			
			if(user == null){
				if(randomCount !=null){
					request.getSession().setAttribute("randomCount",randomCount);
				}
				if(inputRandomNum!=null){
					request.getSession().setAttribute("inputRandomNum",inputRandomNum);
				}
				if(times!=null){
					request.getSession().setAttribute("times", Integer.valueOf(times));
				}
				
			}else{
				//注意：如果是从登陆页面跳转回来，执行下面的逻辑
				if(!isPost()){
					Integer randomCountInSession = (Integer) request.getSession().getAttribute("randomCount");
					String inputRandomNumInSession = (String)request.getSession().getAttribute("inputRandomNum");		
					Integer timesInSession = (Integer)request.getSession().getAttribute("times");
					
					if(randomCountInSession != null){
						randomCount = randomCountInSession;
						request.getSession().setAttribute("randomCount",null);
					}
					
					if(inputRandomNumInSession !=null){
						inputRandomNum = inputRandomNumInSession;
						request.getSession().setAttribute("inputRandomNum",null);
					}
					
					if(timesInSession != null){
						times = String.valueOf(timesInSession);
						request.getSession().setAttribute("times",null);
					}
					
					
					String[] firstChkValuesInSession = (String[]) request.getSession().getAttribute("firstChkValues");

					if(firstChkValuesInSession != null){
						firstChkValues = firstChkValuesInSession;
						request.getSession().setAttribute("firstChkValues",null);
					}
					
					
					String[] secondChkValuesInSession = (String[]) request.getSession().getAttribute("secondChkValues");

					if(secondChkValuesInSession != null){
						secondChkValues = secondChkValuesInSession;
						request.getSession().setAttribute("secondChkValues",null);
					}
					
					String[] thirdChkValuesInSession = (String[]) request.getSession().getAttribute("thirdChkValues");

					if(thirdChkValuesInSession != null){
						thirdChkValues = thirdChkValuesInSession;
						request.getSession().setAttribute("thirdChkValues",null);
					}
					
					
	
				}
			}
			
			
			
			if(firstChkValues==null){
				//注意如果一个empty的字符串split(", ")后的数组长度是1，而不是0；所以要对empty的字符串设置数组长度为0
				if(this.getFirstGroup().isEmpty()){
					firstChkValues = new String[0];
				}else{
					firstChkValues = this.getFirstGroup().split(", ");
					request.getSession().setAttribute("firstChkValues",firstChkValues);
				}
			}
			
			if(secondChkValues==null){
				//注意如果一个empty的字符串split(", ")后的数组长度是1，而不是0；所以要对empty的字符串设置数组长度为0
				if(this.getSecondGroup().isEmpty()){
					secondChkValues = new String[0];
				}else{
					secondChkValues = this.getSecondGroup().split(", ");
					request.getSession().setAttribute("secondChkValues",secondChkValues);
				}
			}
			if(thirdChkValues==null){
				//注意如果一个empty的字符串split(", ")后的数组长度是1，而不是0；所以要对empty的字符串设置数组长度为0
				if(this.getThirdGroup().isEmpty()){
					thirdChkValues = new String[0];
				}else{
					thirdChkValues = this.getThirdGroup().split(", ");
					request.getSession().setAttribute("thirdChkValues",thirdChkValues);
				}
			}

			
			
			LotteryComputer computer = ComputerFactory.getComputer(LotteryCategoryEnum.FREQUENCE);
			switch (playId) {
			//前二直选
			case 19:
				switch (selectType) {
				// 手选
				case 0:
					if (firstChkValues.length == 0
							|| secondChkValues.length == 0) {
						error = "选择方案不完整";
						result = INPUT;
					}
					// 计算投注注数
					else {
						Set<Integer> firstSet = new HashSet<Integer>();
						Set<Integer> secondSet = new HashSet<Integer>();
						for (String firstPosNum : firstChkValues) {
							firstSet.add(Integer.parseInt(firstPosNum));
							betContent.append(firstPosNum);
							betContent.append(",");

						}
						betContent.replace(betContent.length() - 1,
								betContent.length(), "|");
						for (String secondPosNum : secondChkValues) {
							secondSet.add(Integer.parseInt(secondPosNum));
							betContent.append(secondPosNum);
							betContent.append(",");
						}
						content = betContent.subSequence(0,
								betContent.length() - 1).toString();

						betCount = computer
								.getBetCount(new DirectSelectionStrategy(2, 0,
										firstSet, secondSet));
						if (betCount == 0) {
							error = "选择方案格式错误，请重新选择";
							result = INPUT;
						} else {
							resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
							setRequestParam();
							result = SUCCESS;
						}
					}
					break;

				// 机选
				case 2:
					
					if(randomCount==11){
						if(!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")){
							error = "请输入机选注数1-99";
							result = INPUT;
							break;
						}else{
							randomCount = Integer.valueOf(inputRandomNum);
							betCount = randomCount;
							request.getSession().setAttribute("randomCount", randomCount);
						}
					}else{
						betCount = randomCount;
					}
					ArrayList<ArrayList<Integer>> randomResults = new DirectSelectionStrategy(2).getRandomRes(randomCount,1, 11);
					generateContent(randomResults,"|");
					setRequestParam();
					result = SUCCESS;
						
				default:
					break;
				}
				
				
				break;
			//前三直选
			case 20:
				if(selectType == 0){
					if(firstChkValues.length==0 || secondChkValues.length==0 || thirdChkValues.length == 0){
						error = "你的选择方案不完整";
						result = INPUT;
					}
					//计算投注注数
					else{
						Set<Integer> firstSet = new HashSet<Integer>();
						Set<Integer> secondSet = new HashSet<Integer>();
						Set<Integer> thirdSet = new HashSet<Integer>();
						for (String firstPosNum : firstChkValues) {
							firstSet.add(Integer.parseInt(firstPosNum));
							betContent.append(firstPosNum);
							betContent.append(",");
							
						}
						betContent.replace(betContent.length()-1, betContent.length(), "|");
						for (String secondPosNum : secondChkValues) {
							secondSet.add(Integer.parseInt(secondPosNum));
							betContent.append(secondPosNum);
							betContent.append(",");
							
						}
						betContent.replace(betContent.length()-1, betContent.length(), "|");
						for (String thirdPosNum : thirdChkValues) {
							thirdSet.add(Integer.parseInt(thirdPosNum));
							betContent.append(thirdPosNum);
							betContent.append(",");
						}
						
						betCount = computer.getBetCount(new DirectSelectionStrategy(3, 0, firstSet,secondSet,thirdSet));
						if(betCount == 0){
							error = "选择方案格式错误，请重新选择";
							result = INPUT;
						}else{
							resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
							setRequestParam();
							result = SUCCESS;
						}
					}
					
				//机选	
				}else if(selectType == 2){
					
					if(randomCount==11){
						if(!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")){
							error = "请输入机选注数1-99";
							result = INPUT;
							break;
						}else{
							randomCount = Integer.valueOf(inputRandomNum);
							betCount = randomCount;
							request.getSession().setAttribute("randomCount", randomCount);
						}
					}else{
						betCount = randomCount;
					}
					ArrayList<ArrayList<Integer>> randomResults = new DirectSelectionStrategy(3).getRandomRes(randomCount.intValue(),1, 11);
					generateContent(randomResults,"|");
					setRequestParam();
					result = SUCCESS;
				}
				
			
				break;
			//前二组选	
			case 21:
				switch (selectType) {
					//手选
					case 0:
						if(firstChkValues.length<2){
							error = "至少选择"+2+"个数";
							result = INPUT;
						}else{
							Set<Integer> firstSet = new HashSet<Integer>();
							for (String firstPosNum : firstChkValues) {
								firstSet.add(Integer.parseInt(firstPosNum));
								betContent.append(firstPosNum);
								betContent.append(",");
							}
							resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
							betCount = computer.getBetCount(new GroupSelectionStrategy(2, 0, firstSet));
							setRequestParam();
							result = SUCCESS;
						}
						break;
					//胆拖	
					case 1:
						if(danContent !=null&& !danContent.isEmpty()){
							String[] danNum = danContent.split(",");
							Set<Integer> danSet = new HashSet<Integer>();
							Set<Integer> tuoSet = new HashSet<Integer>();
							if((danNum.length+firstChkValues.length)<selectNum+1){
								for (int i = 0; i < danNum.length; i++) {
									Integer index = new Integer(danNum[i]);
									first.remove(index-1);
								}
								error = "胆拖个数相加至少为"+(selectNum+1)+"个";
								result = INPUT;
							}else{
								betContent.append("(");
								for (String string : danNum) {
									danSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								betContent.replace(betContent.length()-1, betContent.length(), ")");
								for (String string : firstChkValues) {
									tuoSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
								betCount = computer.getBetCount(new GroupSelectionStrategy(2, danNum.length,tuoSet,danSet));
								setRequestParam();
								result = SUCCESS;
							}
							
						}else{
							error = "胆拖个数相加至少为"+selectNum+1+"个";
							result = INPUT;
						}
						break;
					//机选
					case 2:
						
						if(randomCount==11){
							if(!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")){
								error = "请输入机选注数1-99";
								result = INPUT;
								break;
							}else{
								randomCount = Integer.valueOf(inputRandomNum);
								betCount = randomCount;
								request.getSession().setAttribute("randomCount", randomCount);
							}
						}else{
							betCount = randomCount;
						}						
						
						ArrayList<ArrayList<Integer>> randomResults = new GroupSelectionStrategy(2).getRandomRes(randomCount, 1, 11);
						generateContent(randomResults,",");
						setRequestParam();
						result = SUCCESS;
						break;
	
					default:
						break;
				}
				
					
				break;
				
			//前三组选	
			case 22:
				switch (selectType) {
					//手选
					case 0:
						if(firstChkValues.length<3){
							error = "至少选择"+3+"个数";
							result = INPUT;
						}else{
							Set<Integer> firstSet = new HashSet<Integer>();
							for (String firstPosNum : firstChkValues) {
								firstSet.add(Integer.parseInt(firstPosNum));
								betContent.append(firstPosNum);
								betContent.append(",");
							}
							resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
							betCount = computer.getBetCount(new GroupSelectionStrategy(3, 0, firstSet));
							setRequestParam();
							result = SUCCESS;
						}
						break;
					//胆托
					case 1:
						if(danContent !=null&& !danContent.isEmpty()){
							String[] danNum = danContent.split(",");
							//显示去掉胆码的拖码集合
							showTuoNums(danNum);
							Set<Integer> danSet = new HashSet<Integer>();
							Set<Integer> tuoSet = new HashSet<Integer>();
							if((danNum.length+firstChkValues.length)<selectNum+1){
								error = "胆拖个数相加至少为"+(selectNum+1)+"个";
								result = INPUT;
							}else{
								betContent.append("(");
								for (String string : danNum) {
									danSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								betContent.replace(betContent.length()-1, betContent.length(), ")");
								for (String string : firstChkValues) {
									tuoSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
								betCount = computer.getBetCount(new GroupSelectionStrategy(3, danNum.length,tuoSet,danSet));
								setRequestParam();
								result = SUCCESS;
							}
							
						}else{
							error = "胆拖个数相加至少为"+selectNum+1+"个";
							result = INPUT;
						}
						
						
						break;
					//机选	
					case 2:	
						
						if(randomCount==11){
							if(!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")){
								error = "请输入机选注数1-99";
								result = INPUT;
								break;
							}else{
								randomCount = Integer.valueOf(inputRandomNum);
								betCount = randomCount;
								request.getSession().setAttribute("randomCount", randomCount);
								
							}
						}else{
							betCount = randomCount;
						}
						ArrayList<ArrayList<Integer>> randomResults = new GroupSelectionStrategy(3).getRandomRes(randomCount, 1, 11);
						//生成投注内容
						generateContent(randomResults,",");
						setRequestParam();
						result = SUCCESS;
						break;
					default:
						break;
					}
				
				
				
				break;
				
			//任选	
			default:

				switch (selectType) {
					// 手选
					case 0:
						if (firstChkValues.length < playId - 10) {
	
							int i = playId - 10;
							error = "至少选择" + i + "个数";
							result = INPUT;
	
						} else {
							Set<Integer> firstSet = new HashSet<Integer>();
							for (String firstPosNum : firstChkValues) {
								firstSet.add(Integer.parseInt(firstPosNum));
								betContent.append(firstPosNum);
								betContent.append(",");
							}
//							ArrayList<String> randomResult = new ArrayList<String>();
//							randomResult.add(betContent.subSequence(0, betContent.length()-1).toString());
//							resultList.add(randomResult);
							resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
							betCount = computer
									.getBetCount(new AnySelectionStrategy(
											playId - 10, 0, firstSet));
							setRequestParam();
							result = SUCCESS;
						}
						break;
	
					// 胆拖
					case 1:
						
						if(danContent !=null&& !danContent.isEmpty()){
							String[] danNum = danContent.split(",");
							//显示去掉胆码的拖码集合
							showTuoNums(danNum);
							Set<Integer> danSet = new HashSet<Integer>();
							Set<Integer> tuoSet = new HashSet<Integer>();
							if((danNum.length+firstChkValues.length)<selectNum+1){
								error = "胆拖个数相加至少为"+(selectNum+1)+"个";
								result = INPUT;
							}else{
								betContent.append("(");
								for (String string : danNum) {
									danSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								betContent.replace(betContent.length()-1, betContent.length(), ")");
								for (String string : firstChkValues) {
									tuoSet.add(Integer.parseInt(string));
									betContent.append(string);
									betContent.append(",");
								}
								resultList.add(betContent.subSequence(0, betContent.length()-1).toString());
								betCount = computer.getBetCount(new AnySelectionStrategy(playId - 10, danNum.length,tuoSet,danSet));
								setRequestParam();
								result = SUCCESS;
							}
							
						}else{
							error = "胆拖个数相加至少为"+selectNum+1+"个";
							result = INPUT;
						}
						
						break;
	
					// 机选
					case 2:
						
						
						
						if (randomCount == 11) {
							if (!inputRandomNum.matches("^([1-9]|[1-9][0-9])$")) {
								error = "请输入机选注数1-99";
								result = INPUT;
								break;
							} else {
								randomCount = Integer.valueOf(inputRandomNum);
								betCount = randomCount;
								request.getSession().setAttribute("randomCount", randomCount);
								request.getSession().setAttribute("inputRandomNum", inputRandomNum);
							}
						}else{
							betCount = randomCount;
						}
						
	
						ArrayList<ArrayList<Integer>> randomResults = new AnySelectionStrategy(
								playId - 10).getRandomRes(randomCount, 1, 11);
						generateContent(randomResults, ",");
						setRequestParam();
						result = SUCCESS;
						
						break;
					default:
						break;
					}

				break;
			}
			
		return result;
	}

	private void showTuoNums(String[] danNum) {
		List danNumList = Arrays.asList(danNum);
		for (Iterator<CustomCheckBox> iterator = first
				.iterator(); iterator.hasNext();) {
			CustomCheckBox chkbox = (CustomCheckBox) iterator
					.next();
			if (danNumList.contains(chkbox.getValue())) {
				iterator.remove();
			}
		}
	}

	private void setRequestParam() {
		//投注内容
		request.setAttribute("resultList", resultList);
		// 注数
		request.setAttribute("betCount", betCount);
		// 倍数
		request.setAttribute("times", Integer.valueOf(times));
		
//		request.getSession().setAttribute("times", Integer.valueOf(times));
		
	}

	private void generateContent(ArrayList<ArrayList<Integer>> randomResults,String seperator) {
		for (ArrayList<Integer> list : randomResults) {
			StringBuffer sb = new StringBuffer();
			for (Integer randomNum : list) {
				//把小于10的数字前加0
				if(randomNum<10){
					sb.append("0");
				}
				sb.append(randomNum);
				sb.append(seperator);
			}
			resultList.add(sb.subSequence(0,sb.length() - 1).toString());
		}
	}
	
	public String selectTuo() {
		
		initFirstGroupChkbox();
		try {
				issueInfo = issueService.getCurrentSalingIssueForShow(lotteryId, new Date());
		} 
		catch (BetException e) {
				addActionError(getText("error."+e.getErrorCode()));
				return INPUT;
		}
		
		String result = INPUT;
		if (playId != null) {			
			playName = PlayType.valueOfPlayName(playId);

			String[] firstChkValues = this.getFirstGroup().split(", ");
			if (firstChkValues.length > selectNum - 1) {
				error = "胆码选择至多" + (selectNum - 1) + "个！";
				result = INPUT;
			}else if(firstChkValues.length == 1 && firstChkValues[0].equals("")){
				error = "请选择胆码";
				result = INPUT;
			}	
			else {
				StringBuffer sb = new StringBuffer();
				for (String string : firstChkValues) {
					sb.append(string);
					sb.append(",");
				}
				danContent = sb.subSequence(0, sb.length()-1)
						.toString();

				for (Iterator<CustomCheckBox> iterator = first.iterator(); iterator
						.hasNext();) {
					CustomCheckBox chk = (CustomCheckBox) iterator.next();
					if ((this.getFirstGroup().indexOf(chk.getValue())) != -1) {
						iterator.remove();
					}
				}

				Set<Integer> firstSet = new HashSet<Integer>();
				for (String firstPosNum : firstChkValues) {
					System.out.println("firstPosNum-->" + firstPosNum);

					firstSet.add(Integer.parseInt(firstPosNum));
					// betContent.append(firstPosNum);
					// betContent.append(",");
				}
				result = INPUT;
			}

		}
		return result;
	}
	
	private  void initFirstGroupChkbox(){
 		for (int i = 1; i <= 11; i++) {
 			CustomCheckBox chk = new CustomCheckBox();
 			if(i<10){
 				
 				chk.setName("0"+i);
 				chk.setValue("0"+i);
 			}else{
 				chk.setName(String.valueOf(i));
 				chk.setValue(String.valueOf(i));
 			}
 			
 			first.add(chk);
 		}
	}
	
	public Integer getBetCount() {
		return betCount;
	}

	public void setBetCount(Integer betCount) {
		this.betCount = betCount;
	}

	public List<IssueInfo> getList() {
		return ballotRecordList;
	}
}
