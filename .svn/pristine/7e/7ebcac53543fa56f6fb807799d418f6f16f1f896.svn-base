package com.xhcms.lottery.account.web.action.bet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.service.BetSchemeRecService;
import com.xhcms.lottery.lang.AppCode;

/**
 * 投注推荐
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-4-1 上午10:28:54
 */
public class BetRecomendAction extends BaseAction {

    private static final long serialVersionUID = 5896522198258232555L;

    private Long id;
    private BetScheme scheme;
    private Map<String, PlayMatch> matches;
	
	@Autowired
	BetSchemeRecService betSchemeRecService;
    
	public String execute() {
        if (id == null) {
            throw new XHRuntimeException(AppCode.SCHEME_NOT_EXIST);
        }
        //Long userId = getUserId();
        //String username = getUsername();
        scheme = betSchemeRecService.viewRecScheme(id);
        String ids = scheme.getPassTypeIds();
        if(null != ids){
        	scheme.setPassTypeIds(ids.replace(',', ' ').replaceAll("@", "串"));
        }
        matches = new HashMap<String, PlayMatch>();
        List<BetMatch> mlist = scheme.getMatchs();
		if (mlist != null && mlist.size() > 0) {
			for (BetMatch m : mlist) {
				matches.put(m.getCode(), (PlayMatch) m);
			}
		}
        
        return SUCCESS;
    }

	
    public BetScheme getScheme() {
        return scheme;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }

	public Map<String, PlayMatch> getMatches() {
		return matches;
	}
}
