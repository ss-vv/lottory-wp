/**
 * 
 */
package com.xhcms.lottery.commons.persist.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Bean.Long
 *
 */
@Entity
@Table(name = "lt_bet_scheme")
public class BetSchemeWithPlayPO extends BetSchemePOBase {
	private static final long serialVersionUID = -8756981191305014341L;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="play_id", updatable=false, insertable=false)
	private PlayPO play;

	public PlayPO getPlay() {
		return play;
	}

	public void setPlay(PlayPO play) {
		this.play = play;
	}
}
