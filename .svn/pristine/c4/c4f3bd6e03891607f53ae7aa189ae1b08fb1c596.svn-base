/**
 * 
 */
package com.xhcms.ucenter.sso.ticket;


/**
 * @author bean
 *
 */
public class AbstractTicket implements ITicket {
	protected String id;
	protected long createTime;
	protected long lastTimeUsed;
	protected long lastPreTimeUsed;
	protected int count;
	
    private IExpirationPolicy expirationPolicy;
    
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public int getCountOfUses() {
		return count;
	}

	@Override
	public long getCreateTime() {
		return createTime;
	}

	@Override
	public long getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(long lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public void addCountOnce() {
		this.count++;
	}
	
	public void clearCount() {
		this.count = 0;
	}

	public IExpirationPolicy getExpirationPolicy() {
		return expirationPolicy;
	}

	public void setExpirationPolicy(IExpirationPolicy expirationPolicy) {
		this.expirationPolicy = expirationPolicy;
	}
	
	public boolean isExpired() {
		return expirationPolicy.isExpired(this);
	}
	
	public void update() {
		this.count++;
		this.lastPreTimeUsed = this.lastTimeUsed;
		this.lastTimeUsed = System.currentTimeMillis();
	}
}
