package com.unison.lottery.weibo.dataservice.parse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sun.org.mozilla.javascript.internal.NativeArray;

import com.unison.lottery.weibo.dataservice.commons.constants.DataInterfaceName;
import com.unison.lottery.weibo.dataservice.commons.download.DowloadService;
import com.unison.lottery.weibo.dataservice.commons.model.BBLeagueContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBLeagueModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBMatchInfoDocument;
import com.unison.lottery.weibo.dataservice.commons.model.BBMatchInfoRealtimeDocument;
import com.unison.lottery.weibo.dataservice.commons.model.BBOddsRealtimeModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBTeamContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.BBTeamModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFChangeModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFResultContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.BFResultModel;
import com.unison.lottery.weibo.dataservice.commons.model.BJEuroreOddsContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.BJEuroreOddsModel;
import com.unison.lottery.weibo.dataservice.commons.model.DataInterfaceResponse;
import com.unison.lottery.weibo.dataservice.commons.model.LeagueContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.LeagueModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchidContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.MatchidModel;
import com.unison.lottery.weibo.dataservice.commons.model.TeamContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.TeamModel;
import com.unison.lottery.weibo.dataservice.commons.model.TechnicContentModel;
import com.unison.lottery.weibo.dataservice.commons.model.TechnicModel;
import com.unison.lottery.weibo.dataservice.commons.parse.ExtractEngine;
import com.unison.lottery.weibo.dataservice.commons.parse.ExtractException;
import com.unison.lottery.weibo.dataservice.commons.parse.ParseService;
import com.unison.lottery.weibo.dataservice.commons.parse.TextDocument;
import com.unison.lottery.weibo.dataservice.commons.parse.TextField;
import com.unison.lottery.weibo.dataservice.parse.model.AsiaOdd;
import com.unison.lottery.weibo.dataservice.parse.model.BBBigSmallOdd;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBCurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBEuropeOdd;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeague;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchEuropeOddDetail;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchProcess;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsBigData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsConcedeData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOddsEuroData;
import com.unison.lottery.weibo.dataservice.parse.model.BBRangFen;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.BFChangeContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BaseName;
import com.unison.lottery.weibo.dataservice.parse.model.BigSmallOdd;
import com.unison.lottery.weibo.dataservice.parse.model.CountryDetail;
import com.unison.lottery.weibo.dataservice.parse.model.CurrentDayOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.EuropeOdd;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFChangeData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFDetail;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBjEuropeOddsContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.FBEvent;
import com.unison.lottery.weibo.dataservice.parse.model.FBEventContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchEuropeOddDetail;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTechnicContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTechnicData;
import com.unison.lottery.weibo.dataservice.parse.model.HalfBigSmallOdd;
import com.unison.lottery.weibo.dataservice.parse.model.HalfOdd;
import com.unison.lottery.weibo.dataservice.parse.model.League;
import com.unison.lottery.weibo.dataservice.parse.model.MatchAgenda;
import com.unison.lottery.weibo.dataservice.parse.model.MatchProcess;
import com.unison.lottery.weibo.dataservice.parse.model.MatchType;
import com.unison.lottery.weibo.dataservice.parse.model.Team;
import com.unison.lottery.weibo.dataservice.parse.model.TeamStatus;
import com.unison.lottery.weibo.dataservice.util.StringSplitUtilsAfterTrim;

@SuppressWarnings("restriction")
@Service
public class DataParseServiceImpl implements DataParseService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DowloadService dowloadService;

	@Autowired
	private ParseService parseService;

	@Autowired
	private BBMatchInfoDataParseService bBMatchInfoDataParseService;//篮球比分接口解析器

	/**
	 * 足球赔率接口
	 */
	@Override
	public CurrentDayOddsData getCurrentDayOdds() {
		CurrentDayOddsData result = null;
		DataInterfaceResponse response = dowloadService
				.downloadToString(DataInterfaceName.CurrentDayOdds);
		if (null != response && response.isSucc()
				&& StringUtils.isNotBlank(response.getResponseStr())) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromUTF8String(response.getResponseStr());
			result = changeParsedDataToCurrentDayOddsData(parsedData);
		}
		return result;
	}

	private CurrentDayOddsData changeParsedDataToCurrentDayOddsData(
			List<List<List<String>>> parsedData) {
		CurrentDayOddsData result = null;
		if (null != parsedData && !parsedData.isEmpty()) {
			if (parsedData.size() >= 7) {// 当前共有7个部分
				result = new CurrentDayOddsData();
				result.setLeagueDatas(changeParsedDataToLeagueDatas(parsedData
						.get(0)));
				result.setMatchProcessDatas(changeParsedDataToMatchProcessDatas(parsedData
						.get(1)));
				result.setAsiaOddDatas(changeParsedDataToAsiaOddDatas(parsedData
						.get(2)));
				result.setEuropeOddDatas(changeParsedDataToEuropeOddDatas(parsedData
						.get(3)));
				result.setBigSmallOddDatas(changeParsedDataToBigSmallOddDatas(parsedData
						.get(4)));
				result.setHalfOddDatas(changeParsedDataToHalfOddDatas(parsedData
						.get(5)));
				result.setHalfBigSmallOddDatas(changeParsedDataToHalfBigSmallOddDatas(parsedData
						.get(6)));
			}
		}
		return result;
	}

	private List<HalfBigSmallOdd> changeParsedDataToHalfBigSmallOddDatas(
			List<List<String>> list) {
		List<HalfBigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<HalfBigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 8) {// 当前共有8个部分
						HalfBigSmallOdd halfBigSmallOdd = new HalfBigSmallOdd();
						halfBigSmallOdd.setMatchId(listItem.get(0));
						halfBigSmallOdd.setCorpId(listItem.get(1));
						halfBigSmallOdd.setInitHandicap(listItem.get(2));
						halfBigSmallOdd.setInitBigOdd(listItem.get(3));
						halfBigSmallOdd.setInitSmallOdd(listItem.get(4));
						halfBigSmallOdd.setImmediateHandicap(listItem.get(5));
						halfBigSmallOdd.setImmediateBigOdd(listItem.get(6));
						halfBigSmallOdd.setImmediateSmallOdd(listItem.get(7));
						result.add(halfBigSmallOdd);
					}

				}
			}
		}
		return result;
	}

	private List<HalfOdd> changeParsedDataToHalfOddDatas(List<List<String>> list) {
		List<HalfOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<HalfOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 8) {// 当前共有8个部分
						HalfOdd halfOdd = new HalfOdd();
						halfOdd.setMatchId(listItem.get(0));
						halfOdd.setCorpId(listItem.get(1));
						halfOdd.setInitHandicap(listItem.get(2));
						halfOdd.setHomeInitOdd(listItem.get(3));
						halfOdd.setGuestInitOdd(listItem.get(4));
						halfOdd.setInitHandicap(listItem.get(5));
						halfOdd.setHomeImmediateOdd(listItem.get(6));
						halfOdd.setGuestImmediateOdd(listItem.get(7));
						result.add(halfOdd);
					}

				}
			}
		}
		return result;
	}

	private List<BigSmallOdd> changeParsedDataToBigSmallOddDatas(
			List<List<String>> list) {
		List<BigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 8) {// 当前共有9个部分
						BigSmallOdd bigSmallOdd = new BigSmallOdd();
						bigSmallOdd.setMatchId(listItem.get(0));
						bigSmallOdd.setCorpId(listItem.get(1));
						bigSmallOdd.setInitHandicap(listItem.get(2));
						bigSmallOdd.setInitBigOdd(listItem.get(3));
						bigSmallOdd.setInitSmallOdd(listItem.get(4));
						bigSmallOdd.setImmediateHandicap(listItem.get(5));
						bigSmallOdd.setImmediateBigOdd(listItem.get(6));
						bigSmallOdd.setImmediateSmallOdd(listItem.get(7));
						result.add(bigSmallOdd);
					}

				}
			}
		}
		return result;
	}

	private List<EuropeOdd> changeParsedDataToEuropeOddDatas(
			List<List<String>> list) {
		List<EuropeOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<EuropeOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 8) {// 当前共有8个部分
						EuropeOdd europseOdd = new EuropeOdd();
						europseOdd.setMatchId(listItem.get(0));
						europseOdd.setCorpId(listItem.get(1));
						europseOdd.setInitHomeWinOdd(listItem.get(2));
						europseOdd.setInitDrawOdd(listItem.get(3));
						europseOdd.setInitGuestWinOdd(listItem.get(4));
						europseOdd.setImmediateHomeWinOdd(listItem.get(5));
						europseOdd.setImmediateDrawOdd(listItem.get(6));
						europseOdd.setImmediateGuestWinOdd(listItem.get(7));
						result.add(europseOdd);
					}

				}
			}
		}
		return result;
	}

	private List<AsiaOdd> changeParsedDataToAsiaOddDatas(List<List<String>> list) {
		List<AsiaOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<AsiaOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 10) {// 当前共有9个部分
						AsiaOdd asiaOdd = new AsiaOdd();
						asiaOdd.setMatchId(listItem.get(0));
						asiaOdd.setCorpId(listItem.get(1));
						asiaOdd.setInitHandicap(listItem.get(2));
						asiaOdd.setHomeInitOdd(listItem.get(3));
						asiaOdd.setGuestInitOdd(listItem.get(4));
						asiaOdd.setImmediateHandicap(listItem.get(5));
						asiaOdd.setHomeImmediateOdd(listItem.get(6));
						asiaOdd.setGuestImmediateOdd(listItem.get(7));
						if (StringUtils.isNotBlank(listItem.get(8))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(8),
									"true")) {
								asiaOdd.setFengPan(true);
							}
						}
						if (StringUtils.isNotBlank(listItem.get(9))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(9),
									"true")) {
								asiaOdd.setZouDi(true);
							}
						}
						result.add(asiaOdd);
					}

				}
			}
		}
		return result;
	}

	private List<MatchProcess> changeParsedDataToMatchProcessDatas(
			List<List<String>> list) {
		List<MatchProcess> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<MatchProcess>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 25) {// 当前共有25个部分
						try {
							MatchProcess matchProcess = new MatchProcess();
							matchProcess.setMatchId(listItem.get(0));
							matchProcess.setLeagueId(listItem.get(1));
							matchProcess
									.setMatchTime(convertGMT8ToBeijingTime(listItem
											.get(2)));// 原始数据是北京时间
							matchProcess
									.setMatchTimeInGMT0(convertGMT8ToGMT0Time(listItem
											.get(2)));// 也记下标准时间
							matchProcess.setActualBeginTime(listItem.get(3));

							matchProcess.setHomeTeamStatus(makeTeamStatus(
									listItem.get(4), listItem.get(5),
									listItem.get(6), listItem.get(7),
									listItem.get(8), listItem.get(15),
									listItem.get(20), listItem.get(22)));
							matchProcess.setGuestTeamStatus(makeTeamStatus(
									listItem.get(9), listItem.get(10),
									listItem.get(11), listItem.get(12),
									listItem.get(13), listItem.get(16),
									listItem.get(21), listItem.get(23)));

							matchProcess.setMatchStatus(listItem.get(14));

							matchProcess.setTelevison(listItem.get(17));
							if (StringUtils.isNotBlank(listItem.get(18))) {
								if (StringUtils.equalsIgnoreCase(
										listItem.get(18), "true")) {
									matchProcess.setZlc(true);
								}
							}
							matchProcess.setLevel(listItem.get(19));
							matchProcess.setReserve(listItem.get(24));

							result.add(matchProcess);
						} catch (Exception e) {
							logger.error("", e);
						}

					} else if (listItem.size() >= 20) {// 当前共有20个部分
						try {
							MatchProcess matchProcess = new MatchProcess();
							matchProcess.setMatchId(listItem.get(0));
							matchProcess.setLeagueId(listItem.get(1));
							matchProcess
									.setMatchTime(convertGMT8ToBeijingTime(listItem
											.get(2)));// 原始数据是标准时间，需要转换为北京时间
							matchProcess
									.setMatchTimeInGMT0(convertGMT8ToGMT0Time(listItem
											.get(2)));// 也记下标准时间
							matchProcess.setActualBeginTime(listItem.get(3));

							matchProcess.setHomeTeamStatus(makeTeamStatus(
									listItem.get(4), listItem.get(5),
									listItem.get(6), listItem.get(7),
									listItem.get(8), listItem.get(15), "", ""));
							matchProcess
									.setGuestTeamStatus(makeTeamStatus(
									listItem.get(9), listItem.get(10),
									listItem.get(11), listItem.get(12),
									listItem.get(13), listItem.get(16),
									"", ""));

							matchProcess.setMatchStatus(listItem.get(14));

							matchProcess.setTelevison(listItem.get(17));
							if (StringUtils.isNotBlank(listItem.get(18))) {
								if (StringUtils.equalsIgnoreCase(
										listItem.get(18), "true")) {
									matchProcess.setZlc(true);
								}
							}
							matchProcess.setLevel(listItem.get(19));
							matchProcess.setReserve("");

							result.add(matchProcess);
						} catch (Exception e) {
							logger.error("", e);
						}

					}

				}
			}
		}
		return result;
	}
	

	private Date convertGMT8ToGMT0Time(String dateTimeStr) {
		return dateTransformBetweenTimeZone(dateTimeStr,
				TimeZone.getTimeZone("GMT+8"), TimeZone.getTimeZone("GMT+0"));
	}

	/**
	 * 从东八区时间转换为东八区时间
	 * @param dateTimeStr
	 *            毫秒数
	 * @return
	 */
	@Override
	public Date convertGMT8ToBeijingTime(String dateTimeStr) {
		return dateTransformBetweenTimeZone(dateTimeStr,
				TimeZone.getTimeZone("GMT+8"), TimeZone.getTimeZone("GMT+8"));
	}

	private Date dateTransformBetweenTimeZone(String timestamp,
			TimeZone sourceTimeZone, TimeZone targetTimeZone) {

		Date result = null;
		if (StringUtils.isNotBlank(timestamp)) {
			long sourceTime = Long.parseLong(timestamp);
			long targetTime = sourceTime - sourceTimeZone.getRawOffset()
					+ targetTimeZone.getRawOffset();
			result = new Date(targetTime);
		}
		return result;

	}
	
	
	
	private Date dateTimeStr(String dateStr,String dateFormatPattern){
		Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormatPattern);
			result = sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error("", e);
		}
		return result;
	}

	private Pattern positionPattern = Pattern.compile("\\d+");
	
	private TeamStatus makeTeamStatus(String teamId, String nameInGB,
			String nameInBig, String nameInEng, String positionStr,
			String score, String red, String yellow) {
		TeamStatus teamStatus = new TeamStatus();
		Team homeTeam = new Team();
		homeTeam.setId(teamId);
		homeTeam.setNameInGB(nameInGB);
		homeTeam.setNameInBig(nameInBig);
		homeTeam.setNameInEng(nameInEng);
		teamStatus.setTeam(homeTeam);
		if (StringUtils.isNotBlank(positionStr)) {
			try {
				//兼容"希腊超2"这种情况
				Matcher matcher = positionPattern.matcher(positionStr);
				if (matcher.find()){
					int position = Integer.parseInt(matcher.group());
					teamStatus.setPosition(position);
				}else{
					logger.error("排名串解析错误！", positionStr);
				}
			} catch (Exception e) {
				logger.error("", e);
			}

		}
		if (StringUtils.isNotBlank(score)) {
			try {
				int scoreInt = Integer.parseInt(score);
				teamStatus.setScore(scoreInt);
			} catch (Exception e) {
				logger.error("", e);
			}

		}
		if (StringUtils.isNotBlank(red)) {
			try {
				int redInt = Integer.parseInt(red);
				teamStatus.setRed(redInt);
			} catch (Exception e) {
				logger.error("", e);
			}

		}
		if (StringUtils.isNotBlank(yellow)) {
			try {
				int yellowInt = Integer.parseInt(yellow);
				teamStatus.setYellow(yellowInt);
			} catch (Exception e) {
				logger.error("", e);
			}

		}

		return teamStatus;
	}

	private List<League> changeParsedDataToLeagueDatas(List<List<String>> list) {
		List<League> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<League>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 8) {// 当前共有9个部分
						League league = new League();
						league.setId(listItem.get(0));
						league.setType(listItem.get(1));
						league.setColor(listItem.get(2));
						league.setNameInGB(listItem.get(3));
						league.setNameInBig(listItem.get(4));
						league.setNameInEng(listItem.get(5));
						league.setDoucmentPath(listItem.get(6));
						league.setImportantMatch(listItem.get(7));
						result.add(league);
					}

				}
			}
		}
		return result;
	}

	@Override
	public CurrentDayOddsData getCurrentDayOddsAndSaveFile() {
		CurrentDayOddsData result = null;
		DataInterfaceResponse response = dowloadService
				.downloadToFile(DataInterfaceName.CurrentDayOdds);
		if (null != response && response.isSucc()
				&& null != response.getResponseFile()
				&& response.getResponseFile().exists()) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromFile(response.getResponseFile(),
							DataInterfaceName.CurrentDayOdds);
			result = changeParsedDataToCurrentDayOddsData(parsedData);
		}
		return result;
	}
	
	
	/**
	 * 历史盘口接口
	 */
    @Override
    public CurrentDayOddsData getFBChangeOddsHistoryAndSaveFile(Date date){
    	CurrentDayOddsData result = null;
		
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			extendParams.put("date",dateStr);
		}
		DataInterfaceResponse response = dowloadService
				.downloadToFileWithExtendParams(
						DataInterfaceName.HistoryPanKou, extendParams);
		if (null != response && response.isSucc()
				&& null != response.getResponseFile()
				&& response.getResponseFile().exists()) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromFile(response.getResponseFile(),
							DataInterfaceName.HistoryPanKou);
			result = changeParsedDataToCurrentDayOddsData(parsedData);
		}
		return result;
    }

    @Override
    public CurrentDayOddsData getFBChangeOddsHistory(Date date){
    	CurrentDayOddsData result = null;
    	Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			extendParams.put("date",dateStr);
		}
		DataInterfaceResponse response = dowloadService
				.downloadToStringWithExtendParams(
						DataInterfaceName.HistoryPanKou, extendParams);
		if (null != response && response.isSucc()
				&& StringUtils.isNotBlank(response.getResponseStr())) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromUTF8String(response.getResponseStr());
			result = changeParsedDataToCurrentDayOddsData(parsedData);
		}
		return result;
    }
	
	
	

	/**
	 * 足球赔率30秒变化接口
	 */

	public CurrentDayOddsData getFBChangeOdds30SecAndSaveFile() {
		CurrentDayOddsData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.ChangeOdds);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			List<List<List<String>>> resultResponse = parseService
					.parseXmlFromFileWithDom(xmlFile,
							DataInterfaceName.ChangeOdds);
			result = changeFBChangeOdds30SecResponseToModel(resultResponse);
		}
		return result;
	}

	public CurrentDayOddsData getFBChangeOdds() {
		CurrentDayOddsData result = null;
		String contentStr = downloadData(DataInterfaceName.ChangeOdds);
		if (StringUtils.isNotBlank(contentStr)) {
			List<List<List<String>>> changeOddsResponse = parseService
					.parseXmlFromStringWithDom(contentStr,
							DataInterfaceName.ChangeOdds);
			result = changeFBChangeOdds30SecResponseToModel(changeOddsResponse);
		}
		return result;
	}

	private CurrentDayOddsData changeFBChangeOdds30SecResponseToModel(
			List<List<List<String>>> resultResponse) {
		CurrentDayOddsData result = null;
		if (null != resultResponse && !resultResponse.isEmpty()) {
			if (resultResponse.size() >= 5) {// 当前共有5个部分
				result = new CurrentDayOddsData();
				// 第一部分亚赔
				result.setAsiaOddDatas(changeParsed30SecChangeDataToAsiaOddDatas(resultResponse
						.get(0)));
				// 第二部分欧赔
				result.setEuropeOddDatas(changeParsed30SecChangeDataToEuropeOddDatas(resultResponse
						.get(1)));
				// 第三部分大小球变化
				result.setBigSmallOddDatas(changeParsed30SecChangeDataToBigSmallOddDatas(resultResponse
						.get(2)));
				// 半场亚赔
				result.setHalfOddDatas(changeParsed30SecChangeDataToHalfOddDatas(resultResponse
						.get(3)));
				// 半场大小球变化
				result.setHalfBigSmallOddDatas(changeParsed30SecChangeDataToHalfBigSmallOddDatas(resultResponse
						.get(4)));
			}
		}
		return result;
	}

	private List<AsiaOdd> changeParsed30SecChangeDataToAsiaOddDatas(
			List<List<String>> list) {
		List<AsiaOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<AsiaOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 7) {// 当前共有7个部分
						AsiaOdd asiaOdd = new AsiaOdd();
						asiaOdd.setMatchId(listItem.get(0));
						asiaOdd.setCorpId(listItem.get(1));
						// 即使盘口
						asiaOdd.setImmediateHandicap(listItem.get(2));
						// 主队即时赔率
						asiaOdd.setHomeImmediateOdd(listItem.get(3));
						// 客队即时赔率
						asiaOdd.setGuestImmediateOdd(listItem.get(4));
						// 是否封盘
						if (StringUtils.isNotBlank(listItem.get(5))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(5),
									"true")) {
								asiaOdd.setFengPan(true);
							}
						}
						// 是否走地
						if (StringUtils.isNotBlank(listItem.get(6))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(6),
									"true")) {
								asiaOdd.setZouDi(true);
							}
						}
						result.add(asiaOdd);
					}
				}
			}
		}
		return result;
	}

	private List<EuropeOdd> changeParsed30SecChangeDataToEuropeOddDatas(
			List<List<String>> list) {
		List<EuropeOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<EuropeOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						EuropeOdd europseOdd = new EuropeOdd();
						europseOdd.setMatchId(listItem.get(0));
						europseOdd.setCorpId(listItem.get(1));
						// 即时盘主胜赔率
						europseOdd.setImmediateHomeWinOdd(listItem.get(2));
						// 即时盘和局赔率
						europseOdd.setImmediateDrawOdd(listItem.get(3));
						// 即时盘客胜赔率
						europseOdd.setImmediateGuestWinOdd(listItem.get(4));
						result.add(europseOdd);
					}
				}
			}
		}
		return result;
	}

	private List<BigSmallOdd> changeParsed30SecChangeDataToBigSmallOddDatas(
			List<List<String>> list) {
		List<BigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						BigSmallOdd bigSmallOdd = new BigSmallOdd();
						bigSmallOdd.setMatchId(listItem.get(0));
						bigSmallOdd.setCorpId(listItem.get(1));
						// 即时盘盘口
						bigSmallOdd.setImmediateHandicap(listItem.get(2));
						// 即时盘大球赔率
						bigSmallOdd.setImmediateBigOdd(listItem.get(3));
						// 即时盘小球赔率
						bigSmallOdd.setImmediateSmallOdd(listItem.get(4));
						result.add(bigSmallOdd);
					}

				}
			}
		}
		return result;
	}

	private List<HalfOdd> changeParsed30SecChangeDataToHalfOddDatas(
			List<List<String>> list) {
		List<HalfOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<HalfOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						HalfOdd halfOdd = new HalfOdd();
						halfOdd.setMatchId(listItem.get(0));
						halfOdd.setCorpId(listItem.get(1));
						// 即时盘口
						halfOdd.setImmediateHandicap(listItem.get(2));
						// 即时盘大球赔率
						halfOdd.setHomeImmediateOdd(listItem.get(3));
						// 即时盘小球赔率
						halfOdd.setGuestImmediateOdd(listItem.get(4));
						result.add(halfOdd);
					}

				}
			}
		}
		return result;
	}

	private List<HalfBigSmallOdd> changeParsed30SecChangeDataToHalfBigSmallOddDatas(
			List<List<String>> list) {
		List<HalfBigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<HalfBigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						HalfBigSmallOdd halfBigSmallOdd = new HalfBigSmallOdd();
						halfBigSmallOdd.setMatchId(listItem.get(0));
						halfBigSmallOdd.setCorpId(listItem.get(1));
						// 即时盘盘口
						halfBigSmallOdd.setImmediateHandicap(listItem.get(2));
						// 即时盘大球赔率
						halfBigSmallOdd.setImmediateBigOdd(listItem.get(3));
						// 即时盘小球赔率
						halfBigSmallOdd.setImmediateSmallOdd(listItem.get(4));
						result.add(halfBigSmallOdd);
					}
				}
			}
		}
		return result;
	}
	
	
	

	/**
	 * 百家欧赔接口,三个参数只需要传一个,入传入多个,只取其中一个,优先级参数从左到右 不带参数：从现在开始起10天内的所有赔率 参数：?day=n,
	 * 从现在开始起n天内的所有赔率 参数：?date=yyyy-MM-dd 指定日期的所有赔率 参数：?min=5 近5分钟的变化数据
	 */
	public FBBjEuropeOddsData getFBBjEuropeOddsAndSaveFile(String dateNum,
			Date date, String minNum) {
		FBBjEuropeOddsData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if (StringUtils.isNotBlank(dateNum) || null != date
				|| StringUtils.isNotBlank(minNum)) {
			if(StringUtils.isNotBlank(dateNum)){
				extendParams.put("day", dateNum);
			}else if(null!=date){
				String dateStr=changeDateToString(date);
				if(StringUtils.isNotBlank(dateStr)){
					extendParams.put("date", dateStr);
				}
			}else if(StringUtils.isNotBlank(minNum)){
				extendParams.put("min", minNum);
			}
		}
		String pilePath = getFileRealPathFromDownloadDataToFileWithExtendParams(
				extendParams, DataInterfaceName.BJ_EUROPE_ODDS);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			BJEuroreOddsModel bjuroreOddsModel = (BJEuroreOddsModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BJ_EUROPE_ODDS);
			result = changeBJEuroreOddsResponseToModel(bjuroreOddsModel);
		}
		return result;
	}

	public FBBjEuropeOddsData getFBBjEuropeOdds(String dateNum, Date date,
			String minNum) {
		FBBjEuropeOddsData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if (StringUtils.isNotBlank(dateNum) || null != date
				|| StringUtils.isNotBlank(minNum)) {
			if(StringUtils.isNotBlank(dateNum)){
				extendParams.put("day", dateNum);
			}else if(null!=date){
				String dateStr=changeDateToString(date);
				if(StringUtils.isNotBlank(dateStr)){
					extendParams.put("date", dateStr);
				}
			}else if(StringUtils.isNotBlank(minNum)){
				extendParams.put("min", minNum);
			}
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.BJ_EUROPE_ODDS);
		if (StringUtils.isNotBlank(contentStr)) {
			BJEuroreOddsModel bjuroreOddsModel = (BJEuroreOddsModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BJ_EUROPE_ODDS);
			result = changeBJEuroreOddsResponseToModel(bjuroreOddsModel);
		}
		return result;
	}

	private FBBjEuropeOddsData changeBJEuroreOddsResponseToModel(
			BJEuroreOddsModel bjuroreOddsModel) {
		FBBjEuropeOddsData result = null;
		if (null != bjuroreOddsModel) {
			result = new FBBjEuropeOddsData();
			List<BJEuroreOddsContentModel> bjEuroreOddsContentModelList = bjuroreOddsModel.bjEuroreOddsContentModelList;
			if (null != bjEuroreOddsContentModelList
					&& !bjEuroreOddsContentModelList.isEmpty()) {
				List<FBBjEuropeOddsContentData> fbBjEuropeOddsContentDataList = new ArrayList<FBBjEuropeOddsContentData>();
				for (BJEuroreOddsContentModel model : bjEuroreOddsContentModelList) {
					FBBjEuropeOddsContentData fbBjEuropeOddsContentData = new FBBjEuropeOddsContentData();
					fbBjEuropeOddsContentData
							.setAwayName(changeParseNameToBaseName(model.away));
					fbBjEuropeOddsContentData
							.setHomeName(changeParseNameToBaseName(model.home));
					fbBjEuropeOddsContentData
							.setLeagueName(changeParseNameToBaseName(model.league));
					if (null != model && null != model.odds
							&& null != model.odds.o && !model.odds.o.isEmpty()) {
						List<String> strList = model.odds.o;
						List<FBMatchEuropeOddDetail> fbMatchEuropeOddDetailList = new ArrayList<FBMatchEuropeOddDetail>();
						for (String str : strList) {
							if (StringUtils.isNotBlank(str)) {
								String[] strArr = StringSplitUtilsAfterTrim
										.splitPreserveAllTokens(str, ",");
								if (strArr.length >= 9) {
									FBMatchEuropeOddDetail fbMatchEuropeOddDetail = new FBMatchEuropeOddDetail();
									fbMatchEuropeOddDetail.setCorpId(strArr[0]);
									fbMatchEuropeOddDetail
											.setCorpName(strArr[1]);
									fbMatchEuropeOddDetail
											.setInitHomeWin(strArr[2]);
									fbMatchEuropeOddDetail
											.setInitDraw(strArr[3]);
									fbMatchEuropeOddDetail
											.setInitGuestWin(strArr[4]);
									fbMatchEuropeOddDetail
											.setHomeWin(strArr[5]);
									fbMatchEuropeOddDetail.setDraw(strArr[6]);
									fbMatchEuropeOddDetail
											.setGuestWin(strArr[7]);
									fbMatchEuropeOddDetailList
											.add(fbMatchEuropeOddDetail);
								}
							}
						}
						fbBjEuropeOddsContentData
								.setFbMatchEuropeOddDetailList(fbMatchEuropeOddDetailList);
					}
					fbBjEuropeOddsContentDataList
							.add(fbBjEuropeOddsContentData);
					result.fbBjEuropeOddsContentDataList=fbBjEuropeOddsContentDataList;
				}
			}
			
		}
		return result;
	}

	private BaseName changeParseNameToBaseName(String nameStr) {
		BaseName result = new BaseName();
		if (StringUtils.isNotBlank(nameStr)) {
			String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(nameStr, ",");
			if (null != strArr && strArr.length >= 3) {
				result.setEnglishName(strArr[0]);
				result.setTraditional(strArr[1]);
				result.setChineseName(strArr[2]);
			}
		}
		return result;
	}

	/**
	 * 赛程赛果
	 * 
	 * @return
	 */
	public FBBFResultData getFBBfResultAndSaveFile(Date date) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			if(StringUtils.isNotBlank(dateStr)){
				extendParams.put("date", dateStr);
			}
		}
		String pilePath = getFileRealPathFromDownloadDataToFileWithExtendParams(
				extendParams, DataInterfaceName.BFResult);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BFResult);
			result = changeBFResultModelResponseToModel(bfResultModel);
		}
		return result;
	}

	public FBBFResultData getFBBfResult(Date date) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			if(StringUtils.isNotBlank(dateStr)){
				extendParams.put("date", dateStr);
			}
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.BFResult);
		if (StringUtils.isNotBlank(contentStr)) {
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BFResult);
			result = changeBFResultModelResponseToModel(bfResultModel);
		}
		return result;
	}

	private FBBFResultData changeBFResultModelResponseToModel(
			BFResultModel bfResultModel) {
		FBBFResultData result=null;
		if (null != bfResultModel && null != bfResultModel.contentModel
				&& !bfResultModel.contentModel.isEmpty()) {
			result=new FBBFResultData();
			List<FBBFResultContentData> fbBFResultContentDatasList=new ArrayList<FBBFResultContentData>();
 			List<BFResultContentModel> bfResultContentModelList=bfResultModel.contentModel;
			for(BFResultContentModel model:bfResultContentModelList){
				if(null!=model){
					FBBFResultContentData bfResultContentData=new FBBFResultContentData();
					bfResultContentData.setMatchId(model.a);
					bfResultContentData.setColour(model.b);
					if(StringUtils.isNotBlank(model.c)){
						String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
								model.c, ",");
						if(null!=strArr&&strArr.length>=5){
							League league=new League();
							league.setNameInGB(strArr[0]);
							league.setNameInBig(strArr[1]);
							league.setNameInEng(strArr[2]);
							league.setId(strArr[3]);
							league.setIsAllOrSimple(strArr[4]);
							bfResultContentData.setLeague(league);
							
						}else if(null!=strArr&&strArr.length>=4){
							League league=new League();
							league.setNameInGB(strArr[0]);
							league.setNameInBig(strArr[1]);
							league.setNameInEng(strArr[2]);
							league.setId(strArr[3]);
							bfResultContentData.setLeague(league);
						}
					}
					Date date=dateTimeStr(model.d,"yyyy/MM/dd hh:mm:ss");
					if(null!=date){
						bfResultContentData.setMatchTime(date);
						bfResultContentData
								.setMatchTimeInGMT0(convertGMT8ToGMT0Time(Long
										.toString(date.getTime())));
					}
					bfResultContentData.setLeagueSubtype(model.e);
					bfResultContentData.setMatchStatus(model.f);
					if(StringUtils.isNotBlank(model.h)){
						String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
								model.h, ",");
						if(null!=strArr&&strArr.length>=4){
							BaseName baseName=new BaseName();
							baseName.setChineseName(strArr[0]);
							baseName.setTraditional(strArr[1]);
							baseName.setEnglishName(strArr[2]);
							bfResultContentData.setHomeTeamId(strArr[3]);
							bfResultContentData.setHomeName(baseName);
						}
					}
					if(StringUtils.isNotBlank(model.i)){
						String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
								model.i, ",");
						if(null!=strArr&&strArr.length>=4){
							BaseName baseName=new BaseName();
							baseName.setChineseName(strArr[0]);
							baseName.setTraditional(strArr[1]);
							baseName.setEnglishName(strArr[2]);
							bfResultContentData.setAwayTeamId(strArr[3]);
							bfResultContentData.setAwayName(baseName);
						}
					}
					bfResultContentData.setHomeScores(model.j);
					bfResultContentData.setAwayScores(model.k);
					bfResultContentData.setHomeHalfScores(model.l);
					bfResultContentData.setAwayHalfScores(model.m);
					bfResultContentData.setHomeRedCard(model.n);
					bfResultContentData.setAwayRedCard(model.o);
					bfResultContentData.setHomeRankings(model.p);
					bfResultContentData.setAwayRankings(model.q);
					bfResultContentData.setMatchMessage(model.r);
					bfResultContentData.setGroupName(model.s);
					bfResultContentData.setMatchAddress(model.t);
					bfResultContentData.setWeatherImg(model.u);
					bfResultContentData.setWeather(model.v);
					bfResultContentData.setTemperature(model.w);
					bfResultContentData.setSeason(model.x);
					bfResultContentData.setTeamGroup(model.y);
					bfResultContentData.setIsNeutrality(model.z);
					fbBFResultContentDatasList.add(bfResultContentData);
				}
			}
			result.setBfResultContentDataList(fbBFResultContentDatasList);
		}
		return result;
	}
	
	/**
	 * 获取英超本赛季的赛程
	 * 
	 * http://bf.bet007.com/BF_XML.aspx?sclassid=36
	 */
	@Override
	public FBBFResultData getFBBfYingChaoAndSaveFile(String sclassid) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if (StringUtils.isNotBlank(sclassid)) {
			extendParams.put("sclassid", sclassid);
		}
		String pilePath = getFileRealPathFromDownloadDataToFileWithExtendParams(
				extendParams, DataInterfaceName.BFYingChao);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BFYingChao);
			result = changeBFResultModelResponseToModel(bfResultModel);
		}
		return result;
	}

	@Override
	public FBBFResultData getFBBfYingChao(String sclassid) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if (StringUtils.isNotBlank(sclassid)) {
			extendParams.put("sclassid", sclassid);
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.BFYingChao);
		if (StringUtils.isNotBlank(contentStr)) {
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BFYingChao);
			result = changeBFResultModelResponseToModel(bfResultModel);
		}
		return result;
	}
    

	/**
	 * 联赛杯赛
	 */

	public FBLeagueData getFBLeagueAndSaveFile() {
		FBLeagueData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.League);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			LeagueModel leagueModel = (LeagueModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile, DataInterfaceName.League);
			result = changeFBLeagueModelResponseToModel(leagueModel);
		}
		return result;
	}

	public FBLeagueData getFBLeague() {
		FBLeagueData result = null;
		String contentStr = downloadData(DataInterfaceName.League);
		if (StringUtils.isNotBlank(contentStr)) {
			LeagueModel leagueModel = (LeagueModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.League);
			result = changeFBLeagueModelResponseToModel(leagueModel);
		}
		return result;
	}
	
	/**
	 * 篮球联赛信息
	 * 
	 * @return
	 */
	public BBLeagueData getBBLeague() {
		BBLeagueData result = null;
		String contentStr = downloadData(DataInterfaceName.BBLeague);
		if (StringUtils.isNotBlank(contentStr)) {
			BBLeagueModel leagueModel = (BBLeagueModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BBLeague);
			result = changeBBLeagueModelResponseToModel(leagueModel);
		}
		return result;
	}
	
	private BBLeagueData changeBBLeagueModelResponseToModel(
			BBLeagueModel leagueModel) {
		BBLeagueData result=null;
		if (null != leagueModel && null != leagueModel.contentModel
				&& !leagueModel.contentModel.isEmpty()) {
			result=new BBLeagueData();
			List<BBLeagueContentData> bbLeagueContentDataList=new ArrayList<BBLeagueContentData>();
			for(BBLeagueContentModel model:leagueModel.contentModel){
				if(null!=model){
					BBLeagueContentData bbLeagueContentData=new BBLeagueContentData();
					bbLeagueContentData.setLeagueId(model.id);
					bbLeagueContentData.setColour(model.color);
					bbLeagueContentData.setShortName(model.shortName);
					bbLeagueContentData.setChineseName(model.gb);
					bbLeagueContentData.setTraditionalName(model.big);
					bbLeagueContentData.setEnglishName(model.en);
					bbLeagueContentData.setType(model.type);
					bbLeagueContentData
							.setCurrMatchSeason(model.Curr_matchSeason);
					bbLeagueContentData.setCountryID(model.countryID);
					bbLeagueContentData.setCountry(model.country);
					bbLeagueContentData.setCurrMonth(model.curr_month);
					bbLeagueContentData.setCurrYear(model.curr_year);
					bbLeagueContentData.setSclassKind(model.sclass_kind);
					bbLeagueContentData.setSclassTime(model.sclass_time);
					bbLeagueContentDataList.add(bbLeagueContentData);
				}
			}
			result.setBbLeagueContentDataList(bbLeagueContentDataList);
		}
		return result;
	}

	private FBLeagueData changeFBLeagueModelResponseToModel(
			LeagueModel leagueModel) {
		FBLeagueData result=null;
		if (null != leagueModel && null != leagueModel.contentModel
				&& !leagueModel.contentModel.isEmpty()) {
			result=new FBLeagueData();
			List<FBLeagueContentData> fbLeagueContentDataList=new ArrayList<FBLeagueContentData>();
			for(LeagueContentModel model:leagueModel.contentModel){
				if(null!=model){
					FBLeagueContentData fbLeagueContentData=new FBLeagueContentData();
					fbLeagueContentData.setLeagueId(model.id);
					fbLeagueContentData.setColour(model.color);
					BaseName leagueSimpleName=new BaseName();
					leagueSimpleName.setChineseName(model.gb_short);
					leagueSimpleName.setTraditional(model.big_short);
					leagueSimpleName.setEnglishName(model.en_short);
					fbLeagueContentData.setLeagueSimpleName(leagueSimpleName);
					BaseName leagueAllName=new BaseName();
					leagueAllName.setChineseName(model.gb);
					leagueAllName.setTraditional(model.big);
					leagueAllName.setEnglishName(model.en);
					fbLeagueContentData.setLeagueAllName(leagueAllName);
					fbLeagueContentData.setType(model.type);
					fbLeagueContentData.setSumRound(model.sum_round);
					fbLeagueContentData.setCurrRound(model.curr_round);
					fbLeagueContentData
							.setCurrMatchSeason(model.Curr_matchSeason);
					fbLeagueContentData.setCountryID(model.countryID);
					fbLeagueContentData.setCountry(model.country);
					fbLeagueContentData.setAreaID(model.areaID);
					fbLeagueContentDataList.add(fbLeagueContentData);
				}
			}
			result.setFbLeagueContentDataList(fbLeagueContentDataList);
		}
		return result;
	}

	/**
	 * 球队资料
	 * 
	 * @return FBTeamData
	 */

	public FBTeamData getFBTeamAndSaveFile() {
		FBTeamData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.Team);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			TeamModel teamModel = (TeamModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile, DataInterfaceName.Team);
			result = changeFBTeamModelResponseToModel(teamModel);
		}
		return result;
	}

	/**
	 * 篮球球队资料
	 * 
	 * @return
	 */
	@Override
	public BBTeamData getBBTeam() {
		BBTeamData result = null;
		String contentStr = downloadData(DataInterfaceName.BBTeam);
		if (StringUtils.isNotBlank(contentStr)) {
			BBTeamModel teamModel = (BBTeamModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BBTeam);
			result = changeBBTeamModelResponseToModel(teamModel);
		}
		return result;
	}
	
	private BBTeamData changeBBTeamModelResponseToModel(BBTeamModel teamModel) {
		BBTeamData result=null;
		if (null != teamModel && null != teamModel.contentModel
				&& !teamModel.contentModel.isEmpty()) {
			result=new BBTeamData();
			List<BBTeamContentData> bbTeamContentDataList=new ArrayList<BBTeamContentData>();
			for(BBTeamContentModel model:teamModel.contentModel){
				if(null!=model){
					BBTeamContentData bbTeamContentData=new BBTeamContentData();
					bbTeamContentData.setLeaId(model.lsID);
					bbTeamContentData.setTeamId(model.id);
					bbTeamContentData.setShortName(model.shortName);
					bbTeamContentData.setChineseName(model.gb);
					bbTeamContentData.setTraditionalName(model.big5);
					bbTeamContentData.setEnglishName(model.en);
					bbTeamContentData.setLogo(model.logo);
					bbTeamContentData.setUrl(model.url);
					bbTeamContentData.setLocationID(model.LocationID);
					bbTeamContentData.setMatchAddrID(model.MatchAddrID);
					bbTeamContentData.setCity(model.City);
					bbTeamContentData.setCapacity(model.Capacity);
					bbTeamContentData.setDrillmaster(model.Drillmaster);
					bbTeamContentData.setGymnasium(model.Gymnasium);
					bbTeamContentData.setJoinYear(model.JoinYear);
					bbTeamContentData.setFirstTime(model.FirstTime);
					bbTeamContentDataList.add(bbTeamContentData);
				}
			}
			result.setBbTeamContentDataList(bbTeamContentDataList);
		}
		return result;
	}
	
	public FBTeamData getFBTeam() {
		FBTeamData result = null;
		String contentStr = downloadData(DataInterfaceName.Team);
		if (StringUtils.isNotBlank(contentStr)) {
			TeamModel teamModel = (TeamModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.Team);
			result = changeFBTeamModelResponseToModel(teamModel);
		}
		return result;
	}

	private FBTeamData changeFBTeamModelResponseToModel(TeamModel teamModel) {
		FBTeamData result=null;
		if (null != teamModel && null != teamModel.contentModel
				&& !teamModel.contentModel.isEmpty()) {
			result=new FBTeamData();
			List<FBTeamContentData> fbTeamContentDataList=new ArrayList<FBTeamContentData>();
			for(TeamContentModel model:teamModel.contentModel){
				if(null!=model){
					FBTeamContentData fbTeamContentData=new FBTeamContentData();
					fbTeamContentData.setLeaId(model.lsID);
					fbTeamContentData.setTeamId(model.id);
					BaseName teamName=new BaseName();
					teamName.setChineseName(model.g);
					teamName.setTraditional(model.b);
					teamName.setEnglishName(model.e);
					fbTeamContentData.setTeamName(teamName);
					fbTeamContentData.setAddress(model.addr);
					fbTeamContentData.setArea(model.Area);
					fbTeamContentData.setCapacity(model.Capacity);
					fbTeamContentData.setFlag(model.Flag);
					fbTeamContentData.setFoundDate(model.Found);
					fbTeamContentData.setGym(model.gym);
					fbTeamContentData.setMaster(model.master);
					fbTeamContentData.setUrl(model.URL);
					
					fbTeamContentDataList.add(fbTeamContentData);
				}
			}
			result.setFbTeamContentDataList(fbTeamContentDataList);
		}
		return result;
	}

	/**
	 * 彩票赛事与球探网的关联表
	 * 
	 * @return FBMatchidData
	 */
	public FBMatchidData getFBMatchidAndSaveFile() {
		FBMatchidData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.Matchid);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			MatchidModel matchidModel = (MatchidModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.Matchid);
			result = changeFBMatchidModelResponseToModel(matchidModel);
		}
		return result;
	}

	public FBMatchidData getFBMatchid() {
		FBMatchidData result = null;
		String contentStr = downloadData(DataInterfaceName.Matchid);
		if (StringUtils.isNotBlank(contentStr)) {
			MatchidModel matchidModel = (MatchidModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.Matchid);
			result = changeFBMatchidModelResponseToModel(matchidModel);
		}
		return result;
	}

	private FBMatchidData changeFBMatchidModelResponseToModel(
			MatchidModel matchidModel) {
		FBMatchidData result=null;
		if (null != matchidModel && null != matchidModel.contentModel
				&& !matchidModel.contentModel.isEmpty()) {
			result=new FBMatchidData();
			List<FBMatchidContentData> fbMatchidContentDataList=new ArrayList<FBMatchidContentData>();
			for(MatchidContentModel model:matchidModel.contentModel){
				if(null!=model){
					FBMatchidContentData fbMatchidContentData=new FBMatchidContentData();
					fbMatchidContentData.setLotteryName(model.LotteryName);
					fbMatchidContentData.setIssueNum(model.IssueNum);
					fbMatchidContentData.setId(model.ID);
					fbMatchidContentData.setQiuTanWangMatchId(model.ID_bet007);
					Date date=dateTimeStr(model.time,"yyyy/MM/dd hh:mm:ss");
					if(null!=date){
						fbMatchidContentData.setMatchTime(date);
						fbMatchidContentData
								.setMatchTimeInGMT0(convertGMT8ToGMT0Time(Long
										.toString(date.getTime())));
					}
					fbMatchidContentData.setHomeName(model.Home);
					fbMatchidContentData.setAwayName(model.Away);
					fbMatchidContentData
							.setQiuTanWangTurnHomeAndAway(model.Turn);
					fbMatchidContentDataList.add(fbMatchidContentData);
				}
				
				
			}
			
			
			result.setFbMatchidContentDataList(fbMatchidContentDataList);
		}
		
		
		
		return result;
	}

	/**
	 * 一天内比赛的技术统计
	 */

	public FBTechnicData getFBTechnicAndSaveFile() {
		FBTechnicData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.Technic);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			TechnicModel technicModel = (TechnicModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.Technic);
			result = changeFBTechnicModelResponseToModel(technicModel);
		}
		return result;
	}
	
	

	public FBTechnicData getFBTechnic() {
		FBTechnicData result = null;
		String contentStr = downloadData(DataInterfaceName.Technic);
		if (StringUtils.isNotBlank(contentStr)) {
			TechnicModel technicModel = (TechnicModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.Technic);
			result = changeFBTechnicModelResponseToModel(technicModel);
		}
		return result;
	}
	
	private FBTechnicData changeFBTechnicModelResponseToModel(
			TechnicModel technicModel) {
		FBTechnicData result=null;
		if (null != technicModel && null != technicModel.contentModel
				&& technicModel.contentModel.isEmpty()) {
			result=new FBTechnicData();
			List<FBTechnicContentData> fbTechicContentDataList=new ArrayList<FBTechnicContentData>();
			for(TechnicContentModel model:technicModel.contentModel){
				if(null!=model){
					FBTechnicContentData fbTechnicContentData=new FBTechnicContentData();
					fbTechnicContentData.setMatchId(model.id);
					if(StringUtils.isNotBlank(model.TechnicCount)){
						String[] strArr=model.TechnicCount.split(";");
						for(String str:strArr){
							if(StringUtils.isNotBlank(str)){
								String[] strArr2 = StringSplitUtilsAfterTrim
										.splitPreserveAllTokens(str, ",");
								if(null!=strArr2&&strArr2.length>=3){
									if (StringUtils.equalsIgnoreCase("0",
											strArr2[0])) {
										//先开球
										fbTechnicContentData
												.setHomeKickOff(strArr2[1]);
										fbTechnicContentData
												.setAwayKickOff(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"1", strArr2[0])) {
										//第一个角球
										fbTechnicContentData
												.setHomeFirstCorner(strArr2[1]);
										fbTechnicContentData
												.setAwayFirstCorner(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"2", strArr2[0])) {
										//第一张黄牌
										fbTechnicContentData
												.setHomeFirstYellowCard(strArr2[1]);
										fbTechnicContentData
												.setAwayFirstYellowCard(strArr2[2]);
										
									} else if (StringUtils.equalsIgnoreCase(
											"3", strArr2[0])) {
										//射门次数
										fbTechnicContentData
												.setHomeShootNum(strArr2[1]);
										fbTechnicContentData
												.setAwayShootNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"4", strArr2[0])) {
										//射正次数
										fbTechnicContentData
												.setHomeShootRightNum(strArr2[1]);
										fbTechnicContentData
												.setAwayShootRightNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"5", strArr2[0])) {
										//犯规次数
										fbTechnicContentData
												.setHomeFoulTrouble(strArr2[1]);
										fbTechnicContentData
												.setAwayFoulTrouble(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"7", strArr2[0])) {
										//角球次数
										fbTechnicContentData
												.setHomeCorners(strArr2[1]);
										fbTechnicContentData
												.setAwayCorners(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"8", strArr2[0])) {
										//角球次数(加时)
										fbTechnicContentData
												.setHomeExtraTimeCorners(strArr2[1]);
										fbTechnicContentData
												.setAwayExtraTimeCorners(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"9", strArr2[0])) {
										//任意球次数
										fbTechnicContentData
												.setHomeFreeKickNum(strArr2[1]);
										fbTechnicContentData
												.setAwayFreeKickNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"10", strArr2[0])) {
										//越位次数
										fbTechnicContentData
												.setHomeFreeKickNum(strArr2[1]);
										fbTechnicContentData
												.setAwayFreeKickNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"11", strArr2[0])) {
										//乌龙球数
										fbTechnicContentData
												.setHomeOwnGoalNum(strArr2[1]);
										fbTechnicContentData
												.setAwayOwnGoalNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"12", strArr2[0])) {
										//黄牌数
										fbTechnicContentData
												.setHomeYellowCardNum(strArr2[1]);
										fbTechnicContentData
												.setAwayYellowCardNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"13", strArr2[0])) {
										//黄牌数(加时)
										fbTechnicContentData
												.setHomeExtraTimeYellowCardNum(strArr2[1]);
										fbTechnicContentData
												.setAwayExtraTimeYellowCardNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"14", strArr2[0])) {
										//红牌数
										fbTechnicContentData
												.setHomeRedCardNum(strArr2[1]);
										fbTechnicContentData
												.setAwayRedCardNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"15", strArr2[0])) {
										//控球时间
										fbTechnicContentData
												.setHomeBallControlTime(strArr2[1]);
										fbTechnicContentData
												.setAwayBallControlTime(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"16", strArr2[0])) {
										//头球
										fbTechnicContentData
												.setHomeHeadingNum(strArr2[1]);
										fbTechnicContentData
												.setAwayHeadingNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"17", strArr2[0])) {
										//救球
										fbTechnicContentData
												.setHomeFollowTheBallNum(strArr2[1]);
										fbTechnicContentData
												.setAwayFollowTheBallNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"18", strArr2[0])) {
										//守门员出击
										fbTechnicContentData
												.setHomeGoalkeeperPouncedNum(strArr2[1]);
										fbTechnicContentData
												.setAwayGoalkeeperPouncedNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"19", strArr2[0])) {
										//丟球
										fbTechnicContentData
												.setHomeBallverlustNum(strArr2[1]);
										fbTechnicContentData
												.setAwayBallverlustNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"20", strArr2[0])) {
										//成功抢断
										fbTechnicContentData
												.setHomeStealSuccess(strArr2[1]);
										fbTechnicContentData
												.setAwayStealSuccess(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"20", strArr2[0])) {
										//阻截
										fbTechnicContentData
												.setHomeInterceptNum(strArr2[1]);
										fbTechnicContentData
												.setAwayInterceptNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"21", strArr2[0])) {
										//长传
										fbTechnicContentData
												.setHomeLongPassNum(strArr2[1]);
										fbTechnicContentData
												.setAwayLongPassNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"22", strArr2[0])) {
										//短传
										fbTechnicContentData
												.setHomeShortPassNum(strArr2[1]);
										fbTechnicContentData
												.setAwayShortPassNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"23", strArr2[0])) {
										//助攻
										fbTechnicContentData
												.setHomeSecondaryAttackNum(strArr2[1]);
										fbTechnicContentData
												.setAwaySecondaryAttackNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"24", strArr2[0])) {
										//成功传中
										fbTechnicContentData
												.setHomeSuccessfulCrossNum(strArr2[1]);
										fbTechnicContentData
												.setAwaySuccessfulCrossNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"25", strArr2[0])) {
										//第一个换人
										fbTechnicContentData
												.setHomeFirstSubstitution(strArr2[1]);
										fbTechnicContentData
												.setAwayFirstSubstitution(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"26", strArr2[0])) {
										//最后换人
										fbTechnicContentData
												.setHomeLastSubstitution(strArr2[1]);
										fbTechnicContentData
												.setAwayLastSubstitution(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"27", strArr2[0])) {
										//第一个越位
										fbTechnicContentData
												.setHomeFirstOffside(strArr2[1]);
										fbTechnicContentData
												.setAwayFirstOffside(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"28", strArr2[0])) {
										//最后越位
										fbTechnicContentData
												.setHomeLastOffside(strArr2[1]);
										fbTechnicContentData
												.setAwayLastOffside(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"29", strArr2[0])) {
										//换人数
										fbTechnicContentData
												.setHomeSubstitutionNum(strArr2[1]);
										fbTechnicContentData
												.setAwaySubstitutionNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"30", strArr2[0])) {
										//最后角球
										fbTechnicContentData
												.setHomeLastCorner(strArr2[1]);
										fbTechnicContentData
												.setAwayLastCorner(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"31", strArr2[0])) {
										//最后黄牌
										fbTechnicContentData
												.setHomeYellowCard(strArr2[1]);
										fbTechnicContentData
												.setAwayYellowCard(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"32", strArr2[0])) {
										//换人数(加时)
										fbTechnicContentData
												.setHomeExtraTimeSubstitutionNum(strArr2[1]);
										fbTechnicContentData
												.setAwayExtraTimeSubstitutionNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"33", strArr2[0])) {
										//越位次数(加时)
										fbTechnicContentData
												.setHomeExtraTimeOffsideNum(strArr2[1]);
										fbTechnicContentData
												.setAwayExtraTimeOffsideNum(strArr2[2]);
									} else if (StringUtils.equalsIgnoreCase(
											"34", strArr2[0])) {
										//红牌数(加时)
										fbTechnicContentData
												.setHomeExtraTimeOffsideNum(strArr2[1]);
										fbTechnicContentData
												.setAwayExtraTimeOffsideNum(strArr2[2]);
									}
								}
							}
						}
					}
					fbTechicContentDataList.add(fbTechnicContentData);
				}
			}
			result.setFbTechicContentDataList(fbTechicContentDataList);
		}
		return result;
	}

	
	/**
	 * 按赛程ID查该比赛的数据
	 * 
	 * @return FBBFResultData
	 */
	
	public FBBFResultData getFBBFResultByIDAndSaveFile(String id) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(StringUtils.isNotBlank(id)){
			extendParams.put("id", id);
		}
		String pilePath = getFileRealPathFromDownloadDataToFileWithExtendParams(
				extendParams, DataInterfaceName.BF_XMLByID);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			try{
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BF_XMLByID);
			result = changeBFResultModelResponseToModel(bfResultModel);
			}catch(Throwable e){
				logger.error("", e);
			}
		}
		return result;
	}

	public FBBFResultData getFBBFResultByID(String id) {
		FBBFResultData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(StringUtils.isNotBlank(id)){
			extendParams.put("id", id);
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.BF_XMLByID);
		if (StringUtils.isNotBlank(contentStr)) {
			BFResultModel bfResultModel = (BFResultModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BF_XMLByID);
			result = changeBFResultModelResponseToModel(bfResultModel);
		}
		return result;
	}

	
	
	
	
	/**
	 * 当天比分数据(js解析)
	 */
	public FBBFData getFBBFDataAndSaveFile() {
		FBBFData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.BFData);
		if (StringUtils.isNotBlank(pilePath)) {
			File jsFile = new File(pilePath);
			Bindings bindingsModel = parseService.parseJSFromFile(jsFile,
					DataInterfaceName.BFData);
			// result = changeBFDataModelResponseToModel(bindingsModel);
		}
		return result;
	}

	public FBBFData getFBBFData() {
		FBBFData result = null;
		String contentStr = downloadData(DataInterfaceName.FBBFDayOdds);
		if (StringUtils.isNotBlank(contentStr)) {
			Object data = parseService.parseXmlFromStringWithJAXB(contentStr,
					DataInterfaceName.FBBFDayOdds);
			if (data != null) {
				com.unison.lottery.weibo.dataservice.commons.model.FBBFDataModel fbbfData = (com.unison.lottery.weibo.dataservice.commons.model.FBBFDataModel) data;
				result = changeBFDataModelResponseToModel(fbbfData);
			}

		}
		return result;
	}
	
	private FBBFData changeBFDataModelResponseToModel(
			com.unison.lottery.weibo.dataservice.commons.model.FBBFDataModel fbbfData) {
		FBBFData result=null;
		if (null != fbbfData) {
		result=new FBBFData();
			List<MatchAgenda> matchAgendas = new ArrayList<MatchAgenda>();
			for (com.unison.lottery.weibo.dataservice.commons.model.MatchAgendaModel matchAgenda : fbbfData.matchAgendaList) {
				MatchAgenda matchAgenda2 = new MatchAgenda();
				matchAgenda2.setMatchId(matchAgenda.matchId);
				matchAgenda2.setColour(matchAgenda.colour);
						BaseName matchTypeName=new BaseName();
				BaseName home = new BaseName();
				BaseName away = new BaseName();
				try {
					BeanUtils.copyProperties(matchTypeName, matchAgenda.getMatchTypeName());
					BeanUtils.copyProperties(home, matchAgenda.getHomeName());
					BeanUtils.copyProperties(away, matchAgenda.getAwayName());
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
						}
				matchAgenda2.setMatchTypeName(matchTypeName);
				matchAgenda2.setHomeName(home);
				matchAgenda2.setAwayName(away);
				
				matchAgenda2.setMatchTimeWithHourAndMin(matchAgenda.matchTimeWithHourAndMin);
				matchAgenda2.setMatchBeginTime(matchAgenda.matchBeginTime);
				if (matchAgenda.matchBeginTime != null) {
					matchAgenda
							.setMatchBeginTimeInGMT0(convertGMT8ToGMT0Time(Long
									.toString(matchAgenda.matchBeginTime
											.getTime())));
							}
				matchAgenda2.setMatchStatus(matchAgenda.matchStatus);
				matchAgenda2.setHomeHalfScores(matchAgenda.homeHalfScores);
				matchAgenda2.setHomeScores(matchAgenda.homeScores);
				matchAgenda2.setHomeRanking(matchAgenda.homeRanking);
				matchAgenda2.setHomeRedCards(matchAgenda.homeRedCards);
				matchAgenda2.setHomeTeamId(matchAgenda.getHomeTeamId());
				matchAgenda2.setHomeYellowCards(matchAgenda.homeYellowCards);
				matchAgenda2.setAwayHalfScores(matchAgenda.awayHalfScores);
				matchAgenda2.setAwayRanking(matchAgenda.awayRanking);
				matchAgenda2.setAwayRedCards(matchAgenda.awayRedCards);
				matchAgenda2.setAwayScores(matchAgenda.awayScores);
				matchAgenda2.setAwayYellowCards(matchAgenda.awayYellowCards);
				matchAgenda2.setAwayTeamId(matchAgenda.getAwayTeamId());
				matchAgenda2.setTVLive(matchAgenda.TVLive);
				matchAgenda2.setIsHaveBattleArray(matchAgenda.isHaveBattleArray);
				matchAgenda2.setMatchMessage(matchAgenda.matchMessage);
				matchAgenda2.setIsCenter(matchAgenda.isCenter);
							
				matchAgendas.add(matchAgenda2);
						}
			result.setMatchAgendaList(matchAgendas);
					}
		return result;
			// result=new FBBFData();
			// Object a = fbbfData.get("A");
			// if(null!=a){
			// List<MatchAgenda> matchAgendaList=new ArrayList<MatchAgenda>();
			// NativeArray nativeArray = (NativeArray) a;
			// List<List<String>> contentA = parseService
			// .nativeArrayToListOfStringList(nativeArray);
			// if (null != contentA && !contentA.isEmpty()) {
			// for (List<String> contentItem : contentA) {
			// if (null != contentItem &&
			// !contentItem.isEmpty()&&contentItem.size()>=41) {
			// MatchAgenda matchAgenda=new MatchAgenda();
			// matchAgenda.setMatchId(contentItem.get(0));
			// matchAgenda.setColour(contentItem.get(1));
			// BaseName matchTypeName=new BaseName();
			// matchTypeName.setChineseName(contentItem.get(2));
			// matchTypeName.setTraditional(contentItem.get(3));
			// matchTypeName.setEnglishName(contentItem.get(4));
			// matchAgenda.setMatchTypeName(matchTypeName);
			// BaseName homeName=new BaseName();
			// homeName.setChineseName(contentItem.get(5));
			// homeName.setTraditional(contentItem.get(6));
			// homeName.setEnglishName(contentItem.get(7));
			// matchAgenda.setHomeName(homeName);
			// BaseName awayName=new BaseName();
			// awayName.setChineseName(contentItem.get(8));
			// awayName.setTraditional(contentItem.get(9));
			// awayName.setEnglishName(contentItem.get(10));
			// matchAgenda.setAwayName(awayName);
			// matchAgenda.setMatchTimeWithHourAndMin(contentItem.get(11));
			// Date date=dateTimeStr(contentItem.get(12),"yyyy,MM,dd,HH,mm,ss");
			// if(null!=date){
			// matchAgenda.setMatchBeginTime(date);
			// matchAgenda.setMatchBeginTimeInGMT0(convertBeijingToGMT0Time(Long.toString(date.getTime())));
			// }
			// matchAgenda.setMatchStatus(contentItem.get(13));
			// matchAgenda.setHomeScores(contentItem.get(14));
			// matchAgenda.setAwayScores(contentItem.get(15));
			// matchAgenda.setHomeHalfScores(contentItem.get(16));
			// matchAgenda.setAwayHalfScores(contentItem.get(17));
			// matchAgenda.setHomeRedCards(contentItem.get(18));
			// matchAgenda.setAwayRedCards(contentItem.get(19));
			// matchAgenda.setHomeYellowCards(contentItem.get(20));
			// matchAgenda.setAwayYellowCards(contentItem.get(21));
			// matchAgenda.setHomeRanking(contentItem.get(22));
			// matchAgenda.setAwayRanking(contentItem.get(23));
			// matchAgenda.setIsAllOrSimple(contentItem.get(24));
			// matchAgenda.setIsFootballLotteryScore(contentItem.get(25));
			// matchAgenda.setTVLive(contentItem.get(26));
			// matchAgenda.setIsHaveBattleArray(contentItem.get(27));
			// if(StringUtils.isNotBlank(contentItem.get(28))){
			// if(StringUtils.endsWithIgnoreCase("true", contentItem.get(28))){
			// matchAgenda.setZouDi(true);
			// }
			// if(StringUtils.endsWithIgnoreCase("false", contentItem.get(28))){
			// matchAgenda.setZouDi(false);
			// }
			//
			// }
			// matchAgenda.setCrownPrimaryDisc(contentItem.get(29));
			// matchAgenda.setMatchMessage(contentItem.get(30));
			// matchAgenda.setLeagueDataUrl(contentItem.get(31));
			// matchAgenda.setCustomOne(contentItem.get(32));
			// matchAgenda.setCustomTwo(contentItem.get(33));
			// matchAgenda.setCustomThree(contentItem.get(34));
			// matchAgenda.setCustomFour(contentItem.get(35));
			// matchAgenda.setMatchTimeWithMonthAndDay(contentItem.get(36));
			// matchAgenda.setHomeTeamId(contentItem.get(37));
			// matchAgenda.setAwayTeamId(contentItem.get(38));
			// matchAgenda.setCustomFive(contentItem.get(39));
			// matchAgenda.setCountryId(contentItem.get(40));
			// matchAgendaList.add(matchAgenda);
			// }
			// }
			// }
			// result.setMatchAgendaList(matchAgendaList);
			// }

			// NativeArray b = (NativeArray) bindings.get("B");
			// if(null!=b){
			// List<MatchType> matchTypeList=new ArrayList<MatchType>();
			// List<List<Object>> contentB = parseService
			// .nativeArrayToListOfObjectList(b);
			//
			// if (null != contentB && !contentB.isEmpty()) {
			// for (List<Object> contentItem : contentB) {
			// MatchType matchType=new MatchType();
			// if (null != contentItem &&
			// !contentItem.isEmpty()&&contentItem.size()>=8) {
			// BaseName name=new BaseName();
			// name.setChineseName((String)contentItem.get(0));
			// name.setTraditional((String)contentItem.get(1));
			// name.setEnglishName((String)contentItem.get(2));
			// matchType.setName(name);
			// matchType.setType((String)contentItem.get(3));
			// matchType.setMatchId((String)contentItem.get(4));
			// matchType.setIsAllOrSimple((String)contentItem.get(5));
			// matchType.setCustomOne((String)contentItem.get(6));
			// matchType.setCustomTwo((String)contentItem.get(7));
			// }
			// matchTypeList.add(matchType);
			// }
			// }
			// result.setMatchTypeList(matchTypeList);
			//
			// }
			// NativeArray c = (NativeArray) bindings.get("C");
			// if(null!=c){
			// List<CountryDetail> countryDetailList=new
			// ArrayList<CountryDetail>();
			// List<List<Object>> contentC = parseService
			// .nativeArrayToListOfObjectList(c);
			// if (null != contentC && !contentC.isEmpty()) {
			// for (List<Object> contentItem : contentC) {
			// if (null != contentItem && !contentItem.isEmpty()) {
			// CountryDetail countryDetail=new CountryDetail();
			// if (null != contentItem &&
			// !contentItem.isEmpty()&&contentItem.size()>=4) {
			// countryDetail.setCountryId((Double)contentItem.get(0));
			// countryDetail.setCountryOrAreaName((String)contentItem.get(1));
			// countryDetail.setCustomOne((Double)contentItem.get(2));
			// countryDetail.setCustomTwo((String)contentItem.get(3));
			// countryDetailList.add(countryDetail);
			// }
			// }
			// }
			// }
			// result.setCountryDetailList(countryDetailList);
			// }
			// String matchdate = (String) fbbfData.get("matchdate");
			// result.setMatchdate(matchdate);
			// Double matchcount = (Double) fbbfData.get("matchcount");
			// result.setMatchcount(matchcount);

		
	}
	
	/**
	 * 20秒内变化数据 (实时更新) 
	 */
	public FBBFChangeData getFBBF20SecChangeAndSaveFile() {
		FBBFChangeData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.BFChange);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			BFChangeModel bfChangeModel = (BFChangeModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BFChange);
			result = changeBFChangeModelResponseToModel(bfChangeModel);
		}
		return result;
	}

	public FBBFChangeData getFBBF20SecChange() {
		FBBFChangeData result = null;
		String contentStr = downloadData(DataInterfaceName.BFChange);
		if (StringUtils.isNotBlank(contentStr)) {
			BFChangeModel bfChangeModel = (BFChangeModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BFChange);
			result = changeBFChangeModelResponseToModel(bfChangeModel);
		}
		return result;
	}
	
	private FBBFChangeData changeBFChangeModelResponseToModel(
			BFChangeModel bfChangeModel) {
		FBBFChangeData result = null;
		if (null != bfChangeModel && null != bfChangeModel.contentStringList
				&& !bfChangeModel.contentStringList.isEmpty()) {
			List<BFChangeContentData> bfChangeContentDataList=new ArrayList<BFChangeContentData>();
			result = new FBBFChangeData();
			for (String string : bfChangeModel.contentStringList) {
				if (StringUtils.isNotBlank(string)) {
					
					String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
							string, "^");
					if(null!=strArr&&strArr.length>=15){
						BFChangeContentData bfChangeContentData=new BFChangeContentData();
						bfChangeContentData.setMatchId(strArr[0]);
						bfChangeContentData.setMatchStatus(strArr[1]);
						bfChangeContentData.setHomeScores(strArr[2]);
						bfChangeContentData.setAwayScores(strArr[3]);
						bfChangeContentData.setHomeHalfScores(strArr[4]);
						bfChangeContentData.setAwayHalfScores(strArr[5]);
						bfChangeContentData.setHomeRedCards(strArr[6]);
						bfChangeContentData.setAwayRedCards(strArr[7]);
						bfChangeContentData
								.setMatchTimeWithHourAndMin(strArr[8]);
						if(StringUtils.isNotBlank(strArr[9])){
							String[] strTimeArr = StringSplitUtilsAfterTrim
									.splitPreserveAllTokens(strArr[9], ",");
							if(null!=strTimeArr&&strTimeArr.length>=6){
								strTimeArr[1] = Integer.toString(Integer
										.parseInt(strTimeArr[1]) + 1);
								StringBuffer sb=new StringBuffer();
								for(String str:strTimeArr){
									sb.append(str+",");
								}
								System.out.println(sb.toString());
								Date date = dateTimeStr(sb.toString(),
										"yyyy,MM,dd,HH,mm,ss,");
								if(null!=date){
									bfChangeContentData.setMatchBeginTime(date);
									bfChangeContentData
											.setMatchBeginTimeInGMT0(convertGMT8ToGMT0Time(Long
													.toString(date.getTime())));
								}
							}
						}
						bfChangeContentData.setMatchMessage(strArr[10]);
						bfChangeContentData.setIsHaveBattleArray(strArr[11]);
						bfChangeContentData.setHomeYellowCards(strArr[12]);
						bfChangeContentData.setAwayYellowCards(strArr[13]);
						bfChangeContentData
								.setMatchTimeWithMonthAndDay(strArr[14]);
						bfChangeContentDataList.add(bfChangeContentData);
					}
				}
			}
			result.setBfChangeContentDataList(bfChangeContentDataList);
		}
		return result;
	}
	
	
		
	/**
	 * 150秒内变化数据，
	 */
	public FBBFChangeData getFBBF150SecChangeAndSaveFile() {
		FBBFChangeData result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.BFChange2);
		if (StringUtils.isNotBlank(pilePath)) {
			File xmlFile = new File(pilePath);
			BFChangeModel bfChangeModel = (BFChangeModel) parseService
					.parseXmlFromFileWithJAXB(xmlFile,
							DataInterfaceName.BFChange2);
			result = changeBFChangeModelResponseToModel(bfChangeModel);
		}
		return result;
	}

	public FBBFChangeData getFBBF150SecChange() {
		FBBFChangeData result = null;
		String contentStr = downloadData(DataInterfaceName.BFChange2);
		if (StringUtils.isNotBlank(contentStr)) {
			BFChangeModel bfChangeModel = (BFChangeModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BFChange2);
			result = changeBFChangeModelResponseToModel(bfChangeModel);
		}
		return result;
	}
	
	

	/**
	 * 当天比赛的入球、红黄牌事件(js解析)
	 */
	public FBBFDetail getFBBFDetailAndSaveFile() {
		FBBFDetail result = null;
		String pilePath = getFileRealPathFromDownloadDataToFile(DataInterfaceName.BFDetail);
		if (StringUtils.isNotBlank(pilePath)) {
			File jsFile = new File(pilePath);
			Bindings bindingsModel = parseService.parseJSFromFile(jsFile,
					DataInterfaceName.BFDetail);
			result = changeBFDetailModelResponseToModel(bindingsModel);
		}
		return result;
	}

	public FBBFDetail getFBBFDetail() {
		FBBFDetail result = null;
		String contentStr = downloadData(DataInterfaceName.BFDetail);
		if (StringUtils.isNotBlank(contentStr)) {
			Bindings bindingsModel = parseService
					.parseJSFromUTF8String(contentStr);
			result = changeBFDetailModelResponseToModel(bindingsModel);
		}
		return result;
	}
	
	private FBBFDetail changeBFDetailModelResponseToModel(Bindings bindings) {
		FBBFDetail result = null;
		if (null != bindings) {
			result = new FBBFDetail();
			Object rq = bindings.get("rq");
			NativeArray nativeRQArray = (NativeArray) rq;
			List<String> rqContent = parseService
					.nativeArrayToListOfString(nativeRQArray);
			if (null != rqContent && !rqContent.isEmpty()) {
				List<FBEventContentData> fbEventContentDataList = new ArrayList<FBEventContentData>();
				for (String str : rqContent) {
					if (StringUtils.isNotBlank(str)) {
						String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
								str, "^");
						if (null != strArr && strArr.length >= 7) {
							FBEventContentData fbEventContentData = new FBEventContentData();
							fbEventContentData.setMatchId(strArr[0]);
							fbEventContentData.setHomeAwayTeamFlag(strArr[1]);
							fbEventContentData.setEventType(strArr[2]);
							fbEventContentData.setTimeInMin(strArr[3]);
							fbEventContentData.setFbPlayerName(strArr[4]);
							fbEventContentData.setBallId(strArr[5]);
							fbEventContentData
									.setFbplayerChineseName(strArr[6]);
							fbEventContentDataList.add(fbEventContentData);
						}
					}
				}

				result.setEventContentDataList(fbEventContentDataList);
			}
			Object tc = bindings.get("tc");
			NativeArray nativeTCArray = (NativeArray) tc;
			List<String> tcContent = parseService
					.nativeArrayToListOfString(nativeTCArray);
			if (null != tcContent && !tcContent.isEmpty()) {
				List<FBTechnicContentData> fbTechnicContentDataList = new ArrayList<FBTechnicContentData>();
				for (String str : tcContent) {
					if (StringUtils.isNotBlank(str)) {
							FBTechnicContentData fbTechnicContentData = changeStringToFBTechnicContentData(str);
							fbTechnicContentDataList.add(fbTechnicContentData);
					}
				}

				result.setTechnicContentDataList(fbTechnicContentDataList);
			}

		}
		return result;
	}
	
	private FBTechnicContentData changeStringToFBTechnicContentData(String str) {
		FBTechnicContentData result = new FBTechnicContentData();
		if (StringUtils.isNotBlank(str)) {
			String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(str, "^");
			if (null != strArr && strArr.length >= 2) {
				result.setMatchId(strArr[0]);
				if (StringUtils.isNotBlank(strArr[1])) {
					String[] strArr1 = strArr[1].split(";");
					for (String str1 : strArr1) {
						String[] strArr2 = StringSplitUtilsAfterTrim.splitPreserveAllTokens(
								str1, ",");
						if (null != strArr2 && strArr2.length >= 3) {
							if (StringUtils.equalsIgnoreCase("0", strArr2[0])) {
								// 先开球
								result.setHomeKickOff(strArr2[1]);
								result.setAwayKickOff(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("1",
									strArr2[0])) {
								// 第一个角球
								result.setHomeFirstCorner(strArr2[1]);
								result.setAwayFirstCorner(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("2",
									strArr2[0])) {
								// 第一张黄牌
								result.setHomeFirstYellowCard(strArr2[1]);
								result.setAwayFirstYellowCard(strArr2[2]);

							} else if (StringUtils.equalsIgnoreCase("3",
									strArr2[0])) {
								// 射门次数
								result.setHomeShootNum(strArr2[1]);
								result.setAwayShootNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("4",
									strArr2[0])) {
								// 射正次数
								result.setHomeShootRightNum(strArr2[1]);
								result.setAwayShootRightNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("5",
									strArr2[0])) {
								// 犯规次数
								result.setHomeFoulTrouble(strArr2[1]);
								result.setAwayFoulTrouble(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("6",
									strArr2[0])) {
								// 角球次数
								result.setHomeCorners(strArr2[1]);
								result.setAwayCorners(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("7",
									strArr2[0])) {
								// 角球次数(加时)
								result.setHomeExtraTimeCorners(strArr2[1]);
								result.setAwayExtraTimeCorners(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("8",
									strArr2[0])) {
								// 任意球次数
								result.setHomeFreeKickNum(strArr2[1]);
								result.setAwayFreeKickNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("9",
									strArr2[0])) {
								// 越位次数
								result.setHomeFreeKickNum(strArr2[1]);
								result.setAwayFreeKickNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("10",
									strArr2[0])) {
								// 乌龙球数
								result.setHomeOwnGoalNum(strArr2[1]);
								result.setAwayOwnGoalNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("11",
									strArr2[0])) {
								// 黄牌数
								result.setHomeYellowCardNum(strArr2[1]);
								result.setAwayYellowCardNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("12",
									strArr2[0])) {
								// 黄牌数(加时)
								result.setHomeExtraTimeYellowCardNum(strArr2[1]);
								result.setAwayExtraTimeYellowCardNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("13",
									strArr2[0])) {
								// 红牌数
								result.setHomeRedCardNum(strArr2[1]);
								result.setAwayRedCardNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("14",
									strArr2[0])) {
								result.setHomeBallControlTime(strArr2[1]);
								result.setAwayBallControlTime(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("15",
									strArr2[0])) {
								// 头球
								result.setHomeHeadingNum(strArr2[1]);
								result.setAwayHeadingNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("16",
									strArr2[0])) {
								// 救球
								result.setHomeFollowTheBallNum(strArr2[1]);
								result.setAwayFollowTheBallNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("17",
									strArr2[0])) {
								// 守门员出击
								result.setHomeGoalkeeperPouncedNum(strArr2[1]);
								result.setAwayGoalkeeperPouncedNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("18",
									strArr2[0])) {
								// 丟球
								result.setHomeBallverlustNum(strArr2[1]);
								result.setAwayBallverlustNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("19",
									strArr2[0])) {
								// 成功抢断
								result.setHomeStealSuccess(strArr2[1]);
								result.setAwayStealSuccess(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("20",
									strArr2[0])) {
								// 阻截
								result.setHomeInterceptNum(strArr2[1]);
								result.setAwayInterceptNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("21",
									strArr2[0])) {
								// 长传
								result.setHomeLongPassNum(strArr2[1]);
								result.setAwayLongPassNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("22",
									strArr2[0])) {
								// 短传
								result.setHomeShortPassNum(strArr2[1]);
								result.setAwayShortPassNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("23",
									strArr2[0])) {
								// 助攻
								result.setHomeSecondaryAttackNum(strArr2[1]);
								result.setAwaySecondaryAttackNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("24",
									strArr2[0])) {
								// 成功传中
								result.setHomeSuccessfulCrossNum(strArr2[1]);
								result.setAwaySuccessfulCrossNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("25",
									strArr2[0])) {
								// 第一个换人
								result.setHomeFirstSubstitution(strArr2[1]);
								result.setAwayFirstSubstitution(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("26",
									strArr2[0])) {
								// 最后换人
								result.setHomeLastSubstitution(strArr2[1]);
								result.setAwayLastSubstitution(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("27",
									strArr2[0])) {
								// 第一个越位
								result.setHomeFirstOffside(strArr2[1]);
								result.setAwayFirstOffside(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("28",
									strArr2[0])) {
								// 最后越位
								result.setHomeLastOffside(strArr2[1]);
								result.setAwayLastOffside(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("29",
									strArr2[0])) {
								// 换人数
								result.setHomeSubstitutionNum(strArr2[1]);
								result.setAwaySubstitutionNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("30",
									strArr2[0])) {
								// 最后角球
								result.setHomeLastCorner(strArr2[1]);
								result.setAwayLastCorner(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("31",
									strArr2[0])) {
								// 最后黄牌
								result.setHomeYellowCard(strArr2[1]);
								result.setAwayYellowCard(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("32",
									strArr2[0])) {
								// 换人数(加时)
								result.setHomeExtraTimeSubstitutionNum(strArr2[1]);
								result.setAwayExtraTimeSubstitutionNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("33",
									strArr2[0])) {
								// 越位次数(加时)
								result.setHomeExtraTimeOffsideNum(strArr2[1]);
								result.setAwayExtraTimeOffsideNum(strArr2[2]);
							} else if (StringUtils.equalsIgnoreCase("34",
									strArr2[0])) {
								// 红牌数(加时)
								result.setHomeExtraTimeOffsideNum(strArr2[1]);
								result.setAwayExtraTimeOffsideNum(strArr2[2]);
							}
						}
					}
				}
			}
		}
		return result;
	}
		
	/**
	 * 比赛详细事件接口（历史数据） http://dxbf.bet007.com/Event_XML.aspx?date=yyyy-mm-dd
	 */
		
	public FBEvent getFBEventAndSaveFile(Date date) {
		FBEvent result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			if(StringUtils.isNotBlank(dateStr)){
				extendParams.put("date", dateStr);
			}
		}
		String pilePath = getFileRealPathFromDownloadDataToFileWithExtendParams(
				extendParams, DataInterfaceName.Event);
		if (StringUtils.isNotBlank(pilePath)) {
			File jsFile = new File(pilePath);
			Bindings bindingsModel = parseService.parseJSFromFile(jsFile,
					DataInterfaceName.Event);
			result = changeEventModelResponseToModel(bindingsModel);
		}
		return result;
	}

		

	public FBEvent getFBEvent(Date date) {
		FBEvent result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			if(StringUtils.isNotBlank(dateStr)){
				extendParams.put("date", dateStr);
			}
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.Event);
		if (StringUtils.isNotBlank(contentStr)) {
			Bindings bindingsModel = parseService
					.parseJSFromUTF8String(contentStr);
			result = changeEventModelResponseToModel(bindingsModel);
	}
		return result;
	}

	private FBEvent changeEventModelResponseToModel(Bindings bindings) {
		FBEvent result=null;
		Object rq = bindings.get("rq");
		NativeArray nativeArray = (NativeArray) rq;
		List<String> content = parseService
				.nativeArrayToListOfString(nativeArray);
		if (null != content && !content.isEmpty()) {
			result=new FBEvent();
			List<FBEventContentData> fbEventContentDataList=new ArrayList<FBEventContentData>();
			for (String str: content) {
				if (StringUtils.isNotBlank(str)) {
					String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(str,
							"^");
					if(null!=strArr&&strArr.length>=7){
						FBEventContentData fbEventContentData=new FBEventContentData();
						fbEventContentData.setMatchId(strArr[0]);
						fbEventContentData.setHomeAwayTeamFlag(strArr[1]);
						fbEventContentData.setEventType(strArr[2]);
						fbEventContentData.setTimeInMin(strArr[3]);
						fbEventContentData.setFbPlayerName(strArr[4]);
						fbEventContentData.setBallId(strArr[5]);
						fbEventContentData.setFbplayerChineseName(strArr[6]);
						fbEventContentDataList.add(fbEventContentData);
			}
		}
	}
			result.setFbEventContentDataList(fbEventContentDataList);
		}
		return result;
	}

	private String downloadData(DataInterfaceName dataInterfaceName) {
		String result = "";
		DataInterfaceResponse resultResponse = dowloadService
				.downloadToString(dataInterfaceName);
		result = resultResponse.getResponseStr();
		return result;
		
	}
		
	private String getFileRealPathFromDownloadDataToFile(
			DataInterfaceName dataInterfaceName) {
		String result = "";
		DataInterfaceResponse resultResponse = dowloadService
				.downloadToFile(dataInterfaceName);
		if (null != resultResponse && null != resultResponse.getResponseFile()) {
			result = resultResponse.getResponseFile().getAbsolutePath();
		}
		return result;
	}	

	private String changeDateToString(Date date){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(null!=date){
			result=sdf.format(date);
		}
		return result;
	}
	
	public String getFileRealPathFromDownloadDataToFileWithExtendParams(
			Map<String, String> extendParams,
			DataInterfaceName dataInterfaceName) {
		String result = "";
		DataInterfaceResponse resultResponse = dowloadService
				.downloadToFileWithExtendParams(dataInterfaceName, extendParams);
		if (null != resultResponse && null != resultResponse.getResponseFile()) {
			result = resultResponse.getResponseFile().getAbsolutePath();
		}
		return result;
    }
	
	public String downloadWithParams(Map<String, String> extendParams,
			DataInterfaceName dataInterfaceName) {
    	String result = "";
		DataInterfaceResponse resultResponse = dowloadService
				.downloadToStringWithExtendParams(dataInterfaceName,
						extendParams);
		result = resultResponse.getResponseStr();
		return result;
	}

	@Override
	public List<BBMatchInfoData> getBBMatchInfo(Date date) {
		HashMap<String, String> params = new HashMap<>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		params.put("time", dateFormat.format(date));
		
		return getBBMatchInfoWithParams(params);
	}

	private List<BBMatchInfoData> getBBMatchInfoWithParams(
			HashMap<String, String> params) {
		String content = downloadWithParams(params,
				DataInterfaceName.BBMatchInfo);
		BBMatchInfoDocument matchInfoDoc = (BBMatchInfoDocument) parseService
				.parseXmlFromStringWithJAXB(content,
						DataInterfaceName.BBMatchInfo);
		List<BBMatchInfoData> matches = bBMatchInfoDataParseService.parseBBMatchInfoDataOfScheduleFromStrings(matchInfoDoc.contentStringList);
		return matches;
	}

	@Override
	public List<BBMatchInfoData> getBBMatchInfo(String sclassId, String season) {
		HashMap<String, String> params = new HashMap<>();
		params.put("sclassid", sclassId);
		params.put("season", season);
		
		return getBBMatchInfoWithParams(params);
	}
		
	@Override
	public List<BBMatchInfoData> getBBMatchInfoToday() {
		String content = downloadData(DataInterfaceName.BBMatchInfoToday);
		BBMatchInfoDocument matchInfoDoc = (BBMatchInfoDocument) 
			parseService.parseXmlFromStringWithJAXB(content, DataInterfaceName.BBMatchInfoToday);
		if(null==matchInfoDoc||null==matchInfoDoc.contentStringList||matchInfoDoc.contentStringList.isEmpty()){
			return null;
		}
		List<BBMatchInfoData> matches = bBMatchInfoDataParseService.parseBBMatchInfoDataFromStrings(matchInfoDoc.contentStringList);
		return matches;
	}
	
	@Override
	public List<BBMatchInfoData> getBBMatchInfoRealtime() {
		String content = downloadData(DataInterfaceName.BBMatchInfoRealtime);
		BBMatchInfoRealtimeDocument matchInfoDoc = (BBMatchInfoRealtimeDocument) parseService
				.parseXmlFromStringWithJAXB(content,
						DataInterfaceName.BBMatchInfoRealtime);
		List<BBMatchInfoData> matches = new LinkedList<>();
		if (matchInfoDoc == null || matchInfoDoc.contents == null){
			return matches;
		}
		matches=bBMatchInfoDataParseService.parseBBMatchInfoDataRealTimeFromStrings(matchInfoDoc.contents);
		
		return matches;
	}
	
	/**
	 *  篮球赔率接口http://vip.bet007.com/xmlvbs/odds.txt
	 */
	
	@Override
	public BBCurrentDayOddsData getBBCurrentDayOdds() {
		BBCurrentDayOddsData result = null;
		DataInterfaceResponse response = dowloadService
				.downloadToString(DataInterfaceName.BBCurrentDayOdds);
		if (null != response && response.isSucc()
				&& StringUtils.isNotBlank(response.getResponseStr())) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromUTF8String(response.getResponseStr());
			result = changeParsedDataToBBCurrentDayOddsData(parsedData);
		}
		return result;
	}
	
	
	
	private BBCurrentDayOddsData changeParsedDataToBBCurrentDayOddsData(
			List<List<List<String>>> parsedData) {
		BBCurrentDayOddsData result = null;
		if (null != parsedData && !parsedData.isEmpty()) {
			if (parsedData.size() >= 5) {// 当前共有5个部分
				result = new BBCurrentDayOddsData();
				result.setLeagueDatas(changeParsedDataToBBLeagueDatas(parsedData
						.get(0)));
				result.setMatchProcessDatas(changeParsedDataToBBMatchProcessDatas(parsedData
						.get(1)));
				result.setRangFenDatas(changeParsedDataToBBRangFenOddDatas(parsedData
						.get(2)));
				result.setEuropeOddDatas(changeParsedDataToBBEuropeOddDatas(parsedData
						.get(3)));
				result.setBigSmallOddDatas(changeParsedDataToBBBigSmallOddDatas(parsedData
						.get(4)));
			}
		}
		return result;
	}

	private List<BBLeague> changeParsedDataToBBLeagueDatas(
			List<List<String>> list) {
		List<BBLeague> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBLeague>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 6) {// 当前共有6个部分
						BBLeague league = new BBLeague();
						league.setId(listItem.get(0));
						league.setType(listItem.get(1));
						league.setColor(listItem.get(2));
						league.setName(listItem.get(3));
						league.setUndefined(listItem.get(4));
						league.setPitchNumber(listItem.get(5));
						result.add(league);
					}

				}
			}
		}
		return result;
	}


	private List<BBBigSmallOdd> changeParsedDataToBBBigSmallOddDatas(
			List<List<String>> list) {
		List<BBBigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBBigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 11) {// 当前共有11个部分
						BBBigSmallOdd bigSmallOdd = new BBBigSmallOdd();
						bigSmallOdd.setMatchId(listItem.get(0));
						bigSmallOdd.setCorpId(listItem.get(1));
						bigSmallOdd.setInitHandicap(listItem.get(2));
						bigSmallOdd.setInitBigOdd(listItem.get(3));
						bigSmallOdd.setInitSmallOdd(listItem.get(4));
						bigSmallOdd.setImmediateHandicap(listItem.get(5));
						bigSmallOdd.setImmediateBigOdd(listItem.get(6));
						bigSmallOdd.setImmediateSmallOdd(listItem.get(7));
						
						if (StringUtils.isNotBlank(listItem.get(8))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(8),
									"true")) {
								bigSmallOdd.setZouDi(true);
							}
						}
						bigSmallOdd.setBigZouDiOdd(listItem.get(9));
						bigSmallOdd.setSmallZouDiOdd(listItem.get(10));
						result.add(bigSmallOdd);
					} else if (listItem.size() >= 8) {// 当前共有8个部分
						BBBigSmallOdd bigSmallOdd = new BBBigSmallOdd();
						bigSmallOdd.setMatchId(listItem.get(0));
						bigSmallOdd.setCorpId(listItem.get(1));
						bigSmallOdd.setInitHandicap(listItem.get(2));
						bigSmallOdd.setInitBigOdd(listItem.get(3));
						bigSmallOdd.setInitSmallOdd(listItem.get(4));
						bigSmallOdd.setImmediateHandicap(listItem.get(5));
						bigSmallOdd.setImmediateBigOdd(listItem.get(6));
						bigSmallOdd.setImmediateSmallOdd(listItem.get(7));
						result.add(bigSmallOdd);
					}

				}
			}
		}
		return result;
	}

	private List<BBEuropeOdd> changeParsedDataToBBEuropeOddDatas(
			List<List<String>> list) {
		List<BBEuropeOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBEuropeOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 6) {// 当前共有6个部分
						BBEuropeOdd europseOdd = new BBEuropeOdd();
						europseOdd.setMatchId(listItem.get(0));
						europseOdd.setCorpId(listItem.get(1));
						europseOdd.setInitHomeWinOdd(listItem.get(2));
						europseOdd.setInitGuestWinOdd(listItem.get(3));
						europseOdd.setImmediateHomeWinOdd(listItem.get(4));
						europseOdd.setImmediateGuestWinOdd(listItem.get(5));
						result.add(europseOdd);
					}

				}
			}
		}
		return result;
	}

	private List<BBRangFen> changeParsedDataToBBRangFenOddDatas(
			List<List<String>> list) {
		List<BBRangFen> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBRangFen>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 11) {// 当前共有11个部分
						BBRangFen rangFen = new BBRangFen();
						rangFen.setMatchId(listItem.get(0));
						rangFen.setCorpId(listItem.get(1));
						rangFen.setInitHandicap(listItem.get(2));
						rangFen.setHomeInitOdd(listItem.get(3));
						rangFen.setGuestInitOdd(listItem.get(4));
						rangFen.setImmediateHandicap(listItem.get(5));
						rangFen.setHomeImmediateOdd(listItem.get(6));
						rangFen.setGuestImmediateOdd(listItem.get(7));
						if (StringUtils.isNotBlank(listItem.get(8))) {
							if (StringUtils.equalsIgnoreCase(listItem.get(8),
									"true")) {
								rangFen.setZouDi(true);
							}
						}
						rangFen.setHomeZouDiOdd(listItem.get(9));
						rangFen.setGuestZouDiOdd(listItem.get(10));
						result.add(rangFen);
					}

				}
			}
		}
		return result;
	}

	private List<BBMatchProcess> changeParsedDataToBBMatchProcessDatas(
			List<List<String>> list) {
		List<BBMatchProcess> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBMatchProcess>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 19) {// 当前共有19个部分
						try {
							BBMatchProcess matchProcess = new BBMatchProcess();
							matchProcess.setMatchId(listItem.get(0));
							matchProcess.setLeagueId(listItem.get(1));
							matchProcess
									.setMatchTime(convertGMT8ToBeijingTime(listItem
											.get(2)));// 原始数据是北京时间
							matchProcess
									.setMatchTimeInGMT0(convertGMT8ToGMT0Time(listItem
											.get(2)));// 也记下标准时间

							matchProcess.setHomeTeamStatus(makeTeamStatus(
									listItem.get(3), listItem.get(4),
									listItem.get(5), listItem.get(6),
									listItem.get(7), listItem.get(14), "", ""));
							matchProcess
									.setGuestTeamStatus(makeTeamStatus(
									listItem.get(8), listItem.get(9),
									listItem.get(10), listItem.get(11),
									listItem.get(12), listItem.get(15),
									"", ""));
							matchProcess.setMatchStatus(listItem.get(13));
							matchProcess.setTelevison(listItem.get(16));
							if (StringUtils.isNotBlank(listItem.get(17))) {
								if (StringUtils.equalsIgnoreCase(
										listItem.get(17), "1")) {
									matchProcess.setJcss(true);
								}
							}
							if (StringUtils.isNotBlank(listItem.get(18))) {
								if (StringUtils.equalsIgnoreCase(
										listItem.get(18), "1")) {
									matchProcess.setJcss(true);
								}
							}
							result.add(matchProcess);
						} catch (Exception e) {
							logger.error("", e);
						}
					} 

				}
			}
		}
		return result;
	}
	
	@Override
	public Date convertGMT0ToBeijingTime(String dateTimeStr) {
		return dateTransformBetweenTimeZone(dateTimeStr,
				TimeZone.getTimeZone("GMT+0"), TimeZone.getTimeZone("GMT+8"));
	}

	/**
	 * 
	 * 篮球历史盘口： http://nba.bet007.com/odds/OddsData/overData.aspx?date=
	 * yyyy-MM-dd
	 */

	@Override
	public BBCurrentDayOddsData getBBChangeOddsHistory(Date date) {
		BBCurrentDayOddsData result = null;
		
		Map<String,String> extendParams=new HashMap<String,String>();
		if(null!=date){
			String dateStr=changeDateToString(date);
			extendParams.put("date",dateStr);
		}
		DataInterfaceResponse response = dowloadService
				.downloadToFileWithExtendParams(
						DataInterfaceName.BBOddsHistory, extendParams);
		if (null != response && response.isSucc()
				&& null != response.getResponseFile()
				&& response.getResponseFile().exists()) {
			List<List<List<String>>> parsedData = parseService
					.parseTextFromFile(response.getResponseFile(),
							DataInterfaceName.BBOddsHistory);
			result = changeParsedDataToBBCurrentDayOddsData(parsedData);
		}
		return result;
	}
	
	/**
	 * 
	 * 篮球30S变化赔率接口 http://nba.bet007.com/odds/OddsData/ch_oddsBsk.xml
	 */
	@Override
	public BBCurrentDayOddsData getBBChangeOdds30Sec() {
		BBCurrentDayOddsData result = null;
		String contentStr = downloadData(DataInterfaceName.BBChangeOdds30Sec);
		if (StringUtils.isNotBlank(contentStr)) {
			List<List<List<String>>> resultResponse = parseService
					.parseXmlFromStringWithDom(contentStr,
							DataInterfaceName.BBChangeOdds30Sec);
			result = changeBBChangeOdds30SecResponseToModel(resultResponse);
		}
		return result;
	}
	
	
	private BBCurrentDayOddsData changeBBChangeOdds30SecResponseToModel(
			List<List<List<String>>> resultResponse) {
		BBCurrentDayOddsData result = null;
		if (null != resultResponse && !resultResponse.isEmpty()) {
			if (resultResponse.size() >= 3) {// 当前共有3个部分
				result = new BBCurrentDayOddsData();
				// 第一部分让分盘变化数据
				result.setRangFenDatas(changeParsed30SecChangeDataToRangFenDatas(resultResponse
						.get(0)));
				// 第二部分欧赔(标准盘）变化数据
				result.setEuropeOddDatas(changeParsed30SecChangeDataToBBEuropeOddDatas(resultResponse
						.get(1)));
				// 第三部分大小分变化数据
				result.setBigSmallOddDatas(changeParsed30SecChangeDataToBBBigSmallOddDatas(resultResponse
						.get(2)));
			}
		}
		return result;
	}
	
	
	private List<BBRangFen> changeParsed30SecChangeDataToRangFenDatas(
			List<List<String>> list) {
		List<BBRangFen> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBRangFen>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						BBRangFen rangFen = new BBRangFen();
						rangFen.setMatchId(listItem.get(0));
						rangFen.setCorpId(listItem.get(1));
						// 即使盘口
						rangFen.setImmediateHandicap(listItem.get(2));
						// 主队即时赔率
						rangFen.setHomeImmediateOdd(listItem.get(3));
						// 客队即时赔率
						rangFen.setGuestImmediateOdd(listItem.get(4));
						result.add(rangFen);
					}
				}
			}
		}
		return result;
	}
	
	private List<BBEuropeOdd> changeParsed30SecChangeDataToBBEuropeOddDatas(
			List<List<String>> list) {
		List<BBEuropeOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBEuropeOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 4) {// 当前共有4个部分
						BBEuropeOdd europseOdd = new BBEuropeOdd();
						europseOdd.setMatchId(listItem.get(0));
						europseOdd.setCorpId(listItem.get(1));
						// 即时盘主胜赔率
						europseOdd.setImmediateHomeWinOdd(listItem.get(2));
						// 即时盘客胜赔率
						europseOdd.setImmediateGuestWinOdd(listItem.get(3));
						result.add(europseOdd);
					}
				}
			}
		}
		return result;
	}
	
	
	private List<BBBigSmallOdd> changeParsed30SecChangeDataToBBBigSmallOddDatas(
			List<List<String>> list) {
		List<BBBigSmallOdd> result = null;
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BBBigSmallOdd>();
			for (List<String> listItem : list) {
				if (null != listItem && !listItem.isEmpty()) {
					if (listItem.size() >= 5) {// 当前共有5个部分
						BBBigSmallOdd bigSmallOdd = new BBBigSmallOdd();
						bigSmallOdd.setMatchId(listItem.get(0));
						bigSmallOdd.setCorpId(listItem.get(1));
						// 即时盘盘口
						bigSmallOdd.setImmediateHandicap(listItem.get(2));
						// 即时盘大分赔率
						bigSmallOdd.setImmediateBigOdd(listItem.get(3));
						// 即时盘小分赔率
						bigSmallOdd.setImmediateSmallOdd(listItem.get(4));
						result.add(bigSmallOdd);
					}

				}
			}
		}
		return result;
	}
	

	
	/**
	 * 篮球百家欧赔接口 http://data.nowgoal.com/1x2/fbdatacn.aspx
	 * 三个参数只需要传一个,如传入多个,只取其中一个,优先级参数从左到右 不带参数：从现在开始起10天内的所有赔率 参数：?day=n,
	 * 从现在开始起n天内的所有赔率 参数：?date=yyyy-MM-dd 指定日期的所有赔率 参数：?min=5 近5分钟的变化数据
	 */

	@Override
	public BBBjEuropeOddsData getBBBjEuropeOdds(String dateNum, Date date,
			String minNum) {
		BBBjEuropeOddsData result = null;
		Map<String,String> extendParams=new HashMap<String,String>();
		if (StringUtils.isNotBlank(dateNum) || null != date
				|| StringUtils.isNotBlank(minNum)) {
			if(StringUtils.isNotBlank(dateNum)){
				extendParams.put("day", dateNum);
			}else if(null!=date){
				String dateStr=changeDateToString(date);
				if(StringUtils.isNotBlank(dateStr)){
					extendParams.put("date", dateStr);
				}
			}else if(StringUtils.isNotBlank(minNum)){
				extendParams.put("min", minNum);
			}
		}
		String contentStr = downloadWithParams(extendParams,
				DataInterfaceName.BBBjEuropeOdds);
		if (StringUtils.isNotBlank(contentStr)) {
			BJEuroreOddsModel bjuroreOddsModel = (BJEuroreOddsModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BBBjEuropeOdds);
			result = changeBBBJEuroreOddsResponseToModel(bjuroreOddsModel);
		}
		return result;
	}
	
	
	private BBBjEuropeOddsData changeBBBJEuroreOddsResponseToModel(
			BJEuroreOddsModel bjuroreOddsModel) {
		BBBjEuropeOddsData result = null;
		if (null != bjuroreOddsModel) {
			result = new BBBjEuropeOddsData();
			List<BJEuroreOddsContentModel> bjEuroreOddsContentModelList = bjuroreOddsModel.bjEuroreOddsContentModelList;
			if (null != bjEuroreOddsContentModelList
					&& !bjEuroreOddsContentModelList.isEmpty()) {
				List<BBBjEuropeOddsContentData> bjEuropeOddsContentDataList = new ArrayList<BBBjEuropeOddsContentData>();
				for (BJEuroreOddsContentModel model : bjEuroreOddsContentModelList) {
					BBBjEuropeOddsContentData bjEuropeOddsContentData = new BBBjEuropeOddsContentData();
					bjEuropeOddsContentData.setMatchId(model.id);
					bjEuropeOddsContentData.setMatchTime(model.time);
					bjEuropeOddsContentData
							.setAwayName(changeParseNameToBaseName(model.away));
					bjEuropeOddsContentData
							.setHomeName(changeParseNameToBaseName(model.home));
					bjEuropeOddsContentData
							.setLeagueName(changeParseNameToBaseName(model.league));
					if (null != model && null != model.odds
							&& null != model.odds.o && !model.odds.o.isEmpty()) {
						List<String> strList = model.odds.o;
						List<BBMatchEuropeOddDetail> matchEuropeOddDetailList = new ArrayList<BBMatchEuropeOddDetail>();
						for (String str : strList) {
							if (StringUtils.isNotBlank(str)) {
								String[] strArr = StringSplitUtilsAfterTrim
										.splitPreserveAllTokens(str, ",");
								if (strArr.length >= 6) {
									BBMatchEuropeOddDetail matchEuropeOddDetail = new BBMatchEuropeOddDetail();
									matchEuropeOddDetail.setCorpId(strArr[0]);
									matchEuropeOddDetail.setCorpName(strArr[1]);
									matchEuropeOddDetail
											.setInitHomeWin(strArr[2]);
									matchEuropeOddDetail
											.setInitGuestWin(strArr[3]);
									matchEuropeOddDetail.setHomeWin(strArr[4]);
									matchEuropeOddDetail.setGuestWin(strArr[5]);
									matchEuropeOddDetailList
											.add(matchEuropeOddDetail);
								}
							}
						}
						bjEuropeOddsContentData
								.setMatchEuropeOddDetailList(matchEuropeOddDetailList);
					}
					bjEuropeOddsContentDataList.add(bjEuropeOddsContentData);
					result.fbBjEuropeOddsContentDataList=bjEuropeOddsContentDataList;
				}
			}
			
		}
		return result;
	}

	@Override
	public BBOdds getBBOddsHistory(Date date){
		Map<String,String> extendParams=new HashMap<String,String>();
		String dateStr=changeDateToString(date);
		extendParams.put("date",dateStr);
		
		return doGetBBOdds(extendParams, DataInterfaceName.BBOddsHistory);
	}
	
	public BBOdds doGetBBOdds(Map<String, String> extendParams,
			DataInterfaceName intfName) {
		BBOdds bbOdds = new BBOdds();
		String contentStr = "";
		if (extendParams != null){
			contentStr = downloadWithParams(extendParams, intfName);
		}else{
			contentStr = downloadData(intfName);
		}
		
		ExtractEngine<BBOddsEuroData> extractEuroEngine = configBBOddsEuroEngine();
		ExtractEngine<BBOddsBigData> extractBigEngine = configBBOddsBigEngine();
		ExtractEngine<BBOddsConcedeData> extractConcedeEngine = configBBOddsConcedeEngine();
		
		String parts[] = contentStr.split("\\$");
		// 解析欧赔
		String euroPart = parts[3];
		String records[] = euroPart.split(";");
		
		extractBBOddsEuro(records, bbOdds, extractEuroEngine);
		
		// 解析大小球
		String bigPart = parts[4];
		records = bigPart.split(";");
		
		extractBBOddsBig(records, bbOdds, extractBigEngine);
		
		// 解析 第三部分，让分赔率
		String concedePart = parts[2];
		records = concedePart.split(";");

		extractBBOddsConcede(records, bbOdds, extractConcedeEngine);

		return bbOdds;
	}

	private void extractBBOddsEuro(String[] records, BBOdds bbOdds, 
			ExtractEngine<BBOddsEuroData> extractEuroEngine) {
		for (String euroRecord :  records) {
			BBOddsEuroData bbOddsEuro;
			try {
				bbOddsEuro = extractEuroEngine.extractBeanFromText(euroRecord);
				bbOdds.getBbOddsEuro().add(bbOddsEuro);
			} catch (ExtractException e) {
				logger.error("不能解析篮球欧赔！\n{}", records, e);
			}
		}
	}

	private void extractBBOddsConcede(String[] records, BBOdds bbOdds,
			ExtractEngine<BBOddsConcedeData> extractConcedeEngine) {
		for (String record :  records) {
			BBOddsConcedeData bbOddsConcede;
			try {
				bbOddsConcede = extractConcedeEngine
						.extractBeanFromText(record);
				bbOdds.getBbOddsConcede().add(bbOddsConcede);
			} catch (ExtractException e) {
				logger.error("不能解析篮球大小分赔率！\n{}", records, e);
			}
		}
	}

	private void extractBBOddsBig(String[] records, BBOdds bbOdds,
			ExtractEngine<BBOddsBigData> extractBigEngine) {
		for (String record :  records) {
			BBOddsBigData bbOddsBig;
			try {
				bbOddsBig = extractBigEngine.extractBeanFromText(record);
				bbOdds.getBbOddsBig().add(bbOddsBig);
			} catch (ExtractException e) {
				logger.error("不能解析篮球大小分赔率！\n{}", records, e);
			}
		}
	}

	private ExtractEngine<BBOddsConcedeData> configBBOddsConcedeEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");			// 赛事ID
		textDoc.addField(1,"corpId");			// 公司ID
		textDoc.addField(2, "initHandicap");
		textDoc.addField(3, "initHomeOdds");
		textDoc.addField(4, "initGuestOdds");
		textDoc.addField(5, "curHandicap");
		textDoc.addField(6, "curHomeOdds");
		textDoc.addField(7, "curGuestOdds");
		textDoc.addField(8, "groundHandicap");
		textDoc.addField(9, "homeGroundOdds");
		textDoc.addField(10, "guestGroundOdds");
		
		return new ExtractEngine<BBOddsConcedeData>(textDoc,
				BBOddsConcedeData.class, new BBOddsConcedeTransformer());
	}

	private ExtractEngine<BBOddsConcedeData> configRtBBOddsConcedeEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");			// 赛事ID
		textDoc.addField(1,"corpId");			// 公司ID
		textDoc.addField(2, "curHandicap");
		textDoc.addField(3, "curHomeOdds");
		textDoc.addField(4, "curGuestOdds");
		
		return new ExtractEngine<BBOddsConcedeData>(textDoc,
				BBOddsConcedeData.class, new BBOddsConcedeTransformer());
	}

	private ExtractEngine<BBOddsBigData> configBBOddsBigEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");			// 赛事ID
		textDoc.addField(1,"corpId");			// 公司ID
		textDoc.addField(2, "initHandicap");
		textDoc.addField(3, "initBigOdds");
		textDoc.addField(4, "initSmallOdds");
		textDoc.addField(5, "realtimeHandicap");
		textDoc.addField(6, "realtimeBigOdds");
		textDoc.addField(7, "realtimeSmallOdds");
		textDoc.addField(8, "groundHandicap");
		textDoc.addField(9, "groundBigOdds");
		textDoc.addField(10, "groundSmallOdds");
		
		return new ExtractEngine<BBOddsBigData>(textDoc, BBOddsBigData.class,
				new BBOddsBigTransformer());
	}

	private ExtractEngine<BBOddsBigData> configRtBBOddsBigEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");			// 赛事ID
		textDoc.addField(1,"corpId");			// 公司ID
		
		textDoc.addField(2, "realtimeHandicap");
		textDoc.addField(3, "realtimeBigOdds");
		textDoc.addField(4, "realtimeSmallOdds");
		
		return new ExtractEngine<BBOddsBigData>(textDoc, BBOddsBigData.class,
				new BBOddsBigTransformer());
	}

	private ExtractEngine<BBOddsEuroData> configBBOddsEuroEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");			// 赛事ID
		textDoc.addField(1,"corpId");			// 公司ID
		textDoc.addField(2, "initHomeWinOdds");	// 初盘主胜赔率
		textDoc.addField(3, "initGuestWinOdds");	// 初盘客胜赔率
		textDoc.addField(4, "realtimeHomeWinOdds");	// 即时盘主胜赔率
		textDoc.addField(5, "realtimeGuestWinOdds");	// 即时盘客胜赔率
		
		return new ExtractEngine<BBOddsEuroData>(textDoc, BBOddsEuroData.class,
				new BBOddsEuroTransformer());
	}

	private ExtractEngine<BBOddsEuroData> configRtBBOddsEuroEngine() {
		TextDocument textDoc = new TextDocument();
		textDoc.setDelimiter(",");
		
		textDoc.addField(0,"matchId");					// 赛事ID
		textDoc.addField(1,"corpId");					// 公司ID
		textDoc.addField(2, "realtimeHomeWinOdds");		// 即时盘主胜赔率
		textDoc.addField(3, "realtimeGuestWinOdds");	// 即时盘客胜赔率
		
		return new ExtractEngine<BBOddsEuroData>(textDoc, BBOddsEuroData.class,
				new BBOddsEuroTransformer());
	}

	@Override
	public BBOdds getBBOdds() {
		return doGetBBOdds(null, DataInterfaceName.BBCurrentDayOdds);
	}
	
	/**
	 * 篮球实时变化赔率接口 http://nba.bet007.com/odds/OddsData/ch_oddsBsk.xml
	 */
	@Override
	public BBOdds getBBOddsRealtime() {
		String contentStr = downloadData(DataInterfaceName.BBChangeOdds30Sec);
		// hack "xml解析抛出'前言中有特殊字符的异常'"，因为第一个字节为 0xFEFF 了
		contentStr = contentStr.trim();
		if (contentStr.charAt(0) != '<'){
			contentStr = contentStr.substring(1);
		}
		// contentStr =
		// "<?xml version=\"1.0\" encoding=\"utf-8\"?><c><a><h>177561,3,-1.5,0.91,0.89</h></a><o></o><d></d></c>";
		
		if (StringUtils.isNotBlank(contentStr)) {
			BBOddsRealtimeModel xmlModel = (BBOddsRealtimeModel) parseService
					.parseXmlFromStringWithJAXB(contentStr,
							DataInterfaceName.BBChangeOdds30Sec);
			
			return xmlModelToBBOdds(xmlModel);
		}
		return null;
	}

	private BBOdds xmlModelToBBOdds(BBOddsRealtimeModel xmlModel) {
		BBOdds bbOdds = new BBOdds();
		if (xmlModel == null)
			return bbOdds;

		ExtractEngine<BBOddsEuroData> extractEuroEngine = configRtBBOddsEuroEngine();
		ExtractEngine<BBOddsBigData> extractBigEngine = configRtBBOddsBigEngine();
		ExtractEngine<BBOddsConcedeData> extractConcedeEngine = configRtBBOddsConcedeEngine();

		if (xmlModel.euro != null && xmlModel.euro.odds != null){
			extractBBOddsEuro(xmlModel.euro.odds.toArray(new String[1]),
					bbOdds, extractEuroEngine);
		}
		if (xmlModel.bigSmall != null && xmlModel.bigSmall.odds != null){
			extractBBOddsBig(xmlModel.bigSmall.odds.toArray(new String[1]),
					bbOdds, extractBigEngine);
		}
		if (xmlModel.concede != null && xmlModel.concede.odds != null){
			extractBBOddsConcede(xmlModel.concede.odds.toArray(new String[1]),
					bbOdds, extractConcedeEngine);
		}
		
		return bbOdds;
	}

}
