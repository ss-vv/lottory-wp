package com.xhcms.lottery.account.web.action.bonus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.service.BetSchemeService;
import com.xhcms.lottery.account.service.BonusDetailException;
import com.xhcms.lottery.account.service.BonusDetailService;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Play;
import com.xhcms.lottery.commons.data.bonus.BonusDetail;
import com.xhcms.lottery.commons.data.bonus.SupposeHit;
import com.xhcms.lottery.commons.data.bonus.WinTicketDetail;
import com.xhcms.lottery.commons.persist.service.CheckMatchService;
import com.xhcms.lottery.commons.persist.service.PlayService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.MixPlayType;

/**
 * 奖金评测Action
 * @author lei.li@unison.net.cn
 */
public class BonusEvalAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private Logger log = LoggerFactory.getLogger(BonusEvalAction.class);
	
	@Autowired
	private BonusDetailService bonusDetailService;
	
	@Autowired
	private BetSchemeService betSchemeService;
	
	@Autowired
    private PlayService playService;
	
	private Data data = Data.success(true);
	
	private String playId;
	
	private String matchs;
	
	private String passTypes;
	
	private int multiple;
	
	private int totalAmount;
	
	private String maxBonus;
	
	private BetScheme scheme;
	
	private BetScheme schemeView;
	
	private Map<Integer, List<Map<String, List<WinTicketDetail>>>> detailMap;

	@Autowired
	private CheckMatchService checkMatchService;
	
	/**
	 * 计算奖金评测计算
	 */
	@Override
	public String execute() {
		try {
			BonusDetail bonusDetail = getBonusDetail();
			Collections.reverse(bonusDetail.getSupposeHits());//倒排中奖评测结果
			data.setData(bonusDetail);
		} catch (Exception e) {
			data.setSuccess(false);
			log.error("奖金评测计算，方案：{}, 出现异常：", new Object[]{scheme, e});
		}
		return SUCCESS;
	}

	private void fillScheme() {
		scheme = new BetScheme();
		scheme.setPlayId(playId);
		scheme.setMultiple(multiple);
		scheme.setTotalAmount(totalAmount);
		if(StringUtils.isNotBlank(maxBonus)) {
			scheme.setMaxBonus(new BigDecimal(maxBonus));
		}
		Play play = null;
		if(null != playId) {
			play = playService.get(playId);
		}
        if (play != null) {
            scheme.setLotteryId(play.getLotteryId());
        }
		if(StringUtils.isNotBlank(matchs)) {
			parseBetMatchs(scheme);
		}
		// 过关方式
		List<String> pts = new ArrayList<String>();
		if(StringUtils.isNotBlank(passTypes)) {
			String[] passTypeArr = passTypes.split(",");
			for (int i = 0; i < passTypeArr.length; i++) {
				pts.add(passTypeArr[i]);
			}
			scheme.setPassTypes(pts);
			scheme.setPassTypeIds("," + passTypes + ",");
			scheme.setPassTypeIds(passTypes.replace(',', ' ').replaceAll("@", "串"));
		}
	}
	
	private BonusDetail getBonusDetail() throws BonusDetailException {
		fillScheme();
		BonusDetail bonusDetail = bonusDetailService.computeBonusDetail(scheme);
		return bonusDetail;
	}
	
	private void parseBetMatchs(BetScheme betScheme) {
		String[] matchArr = matchs.split(",");
        Pattern p = Pattern.compile("-");
        List<BetMatch> matchList = new ArrayList<BetMatch>(matchArr.length);
        for (int i = 0; i < matchArr.length; i++) {
            String match = matchArr[i];
            String[] m = p.split(match);
            BetMatch bm = new BetMatch();
            bm.setMatchId(Long.parseLong(m[0]));
            bm.setCode(m[1]);
            //加胆码
            bm.setSeed(Boolean.parseBoolean(m[2]));
            if(m.length>3){
            	MixPlayType mp = MixPlayType.valueOfPlayName(m[3]);
            	bm.setPlayId(mp.getPlayId());
            }
            matchList.add(bm);
        }

        int code = checkMatchService.checkMatch(playId, matchList);
        if (code != 0) {
            throw new XHRuntimeException(getErrorText(code));
        }
        betScheme.setMatchNumber(matchList.size());
        betScheme.setMatchs(matchList);
        
        // 截止时间
        Date offtime = minEntrustDeadline(matchList);
        betScheme.setOfftime(offtime);
	}
	
	/**奖金评测明细显示*/
	public String bonusEvalDetail() {
		BonusDetail bonusDetail = (BonusDetail) request.getSession().getAttribute(Constants.BONUS_DETAIL);
		fillScheme();
		if(null == bonusDetail && StringUtils.isNotBlank(playId)) {
			try {
				bonusDetail = getBonusDetail();
			} catch (Exception e) {
				data.setSuccess(false);
				addActionError("请求参数无效");
				log.error("奖金评测明细数据显示异常：", e);
			}
		}
		if(null != bonusDetail && StringUtils.isNotBlank(playId)) {
			schemeView = new BetScheme();
			BeanUtils.copyProperties(scheme, schemeView);
			if(!scheme.getLotteryId().equals(Constants.CTZC)){
				betSchemeService.confirmScheme(schemeView);
			}
		}
		
		data.setData(bonusDetail);
		if(null != bonusDetail) {
			detailMap = new HashMap<Integer, List<Map<String, List<WinTicketDetail>>>>();
			List<SupposeHit> supposeHits = bonusDetail.getSupposeHits();
			if(null != supposeHits) {
				Collections.reverse(supposeHits);
				for(int i = 0; i < supposeHits.size(); i++) {
					SupposeHit supposeHit = supposeHits.get(i);
					Integer hitCount = supposeHit.getHitCount();
					
					List<WinTicketDetail> winTicketMinList = supposeHit.getMinDetails();
					List<WinTicketDetail> winTicketMaxList = supposeHit.getMaxDetails();
					
					Map<String, List<WinTicketDetail>> minBonusMapView = new HashMap<String, List<WinTicketDetail>>();
					Map<String, List<WinTicketDetail>> maxBonusMapView = new HashMap<String, List<WinTicketDetail>>();
					fillPassTypeMatch(winTicketMinList, minBonusMapView, supposeHit);
					fillPassTypeMatch(winTicketMaxList, maxBonusMapView, supposeHit);
					
					List<Map<String, List<WinTicketDetail>>> bonusDetailList = new ArrayList<Map<String, List<WinTicketDetail>>>();
					bonusDetailList.add(maxBonusMapView);
					bonusDetailList.add(minBonusMapView);
					
					detailMap.put(hitCount, bonusDetailList);
				}
			}
		}
		return SUCCESS;
	}
	
	private void fillPassTypeMatch(List<WinTicketDetail> winTicketList, Map<String, List<WinTicketDetail>> map, SupposeHit supposeHit) {
		if(null != winTicketList) {
			for(WinTicketDetail ticketDetail : winTicketList) {
				String passType = ticketDetail.getPassType();
				if(StringUtils.isNotBlank(passType)) {
					String passTypeStr = passType.replace("@", "串");
					List<WinTicketDetail> ret = map.get(passTypeStr);
					if(null == ret) {
						List<WinTicketDetail> ticketList = new ArrayList<WinTicketDetail>();
						ticketList.add(ticketDetail);
						map.put(passTypeStr, ticketList);
					} else {
						map.get(passTypeStr).add(ticketDetail);
					}
					
					//设置“过关类型”和“中奖注数”的对应关系
					Map<String, Integer> passTypeWinNotes = new HashMap<String, Integer>();
					Set<Entry<String, List<WinTicketDetail>>> set = map.entrySet();
					Iterator<Entry<String, List<WinTicketDetail>>> iter = set.iterator();
					while(iter.hasNext()) {
						Entry<String, List<WinTicketDetail>> entry = iter.next();
						String key = entry.getKey();
						Integer cnt = entry.getValue().size();
						passTypeWinNotes.put(key.replace("串", "@"), cnt);
					}
					supposeHit.setPassTypeWinNotes(passTypeWinNotes);
				}
			}
		}
	}
	
    /**
     * 停售时间
     * @param matches
     * @return 
     */
    private Date minEntrustDeadline(List<BetMatch> matches) {
        Date t = null;
        for (BetMatch match : matches) {
            Date deadline = match.getEntrustDeadline();
            if (t == null || t.after(deadline)) {
                t = deadline;
            }
        }
        return t;
    }
	
	public Object getData() {
		return data;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public void setMatchs(String matchs) {
		this.matchs = matchs;
	}

	public void setPassTypes(String passTypes) {
		this.passTypes = passTypes;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public BetScheme getScheme() {
		return scheme;
	}

	public void setScheme(BetScheme scheme) {
		this.scheme = scheme;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setMaxBonus(String maxBonus) {
		this.maxBonus = maxBonus;
	}

	public BetScheme getSchemeView() {
		return schemeView;
	}

	public Map<Integer, List<Map<String, List<WinTicketDetail>>>> getDetailMap() {
		return detailMap;
	}
}
