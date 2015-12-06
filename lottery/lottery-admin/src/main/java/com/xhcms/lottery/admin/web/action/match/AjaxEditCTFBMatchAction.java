
package com.xhcms.lottery.admin.web.action.match;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.persist.service.IssueService;

/**
 * @author yonglizhu
 * @version 1.0.0
 */
public class AjaxEditCTFBMatchAction extends BaseAction {

	private static final long serialVersionUID = 4152905516383928444L;

	@Autowired
	private MatchService matchService;
	
	@Autowired
    private IssueService issueService;
	
	private String issueNumber;
	
	private String playId;

	private Long id;
  
	private int status;
	private boolean valid;//是否有效
	private Data data;
		
	public String execute() {
		List<CTFBMatch> ctfbMatchs = matchService.findByIssueNumberAndPlayId(issueNumber, playId);
    	data = Data.success(ctfbMatchs);
        return SUCCESS;
    }

	public String aj_editctfb() {
		issueService.updateJXIssue_(id, status,valid);
		data = Data.success(null);
		return SUCCESS;
	}
	
    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
	
    public Data getData() {
        return data;
    }
    
    public String getIssueNumber() {
		return issueNumber;
	}

	public void setIssueNumber(String issueNumber) {
		this.issueNumber = issueNumber;
	}

	public String getPlayId() {
		return playId;
	}

	public void setPlayId(String playId) {
		this.playId = playId;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
