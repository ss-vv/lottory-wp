/**
 * 
 */
package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Bean.Long
 *
 */
@Entity
@Table(name = "lt_bet_scheme")
public class BetSchemeWithIssueInfoPO extends BetSchemePOBase {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6025770844951371245L;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "lt_hf_bet_content", joinColumns = @JoinColumn(name = "scheme_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "issue_id", referencedColumnName = "id"))
	private IssueInfoPO issueInfoPO;

	public IssueInfoPO getIssueInfoPO() {
		return issueInfoPO;
	}

	public void setIssueInfoPO(IssueInfoPO issueInfoPO) {
		this.issueInfoPO = issueInfoPO;
	}

	
}
