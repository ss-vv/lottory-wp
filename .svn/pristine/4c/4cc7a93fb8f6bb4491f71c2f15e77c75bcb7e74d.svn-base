package com.xhcms.lottery.commons.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.xhcms.lottery.lang.Constants;

@Entity
@Table(name = "lt_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountPO implements Serializable {

    private static final long serialVersionUID = -8314644574776562323L;

    public AccountPO(){
    	this.totalCommission=new BigDecimal(0);
    }
    @Id
    private Long id;
    
    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private BigDecimal fund;
    
    @Column(nullable = false, name="_grant")
    private BigDecimal grant;
    
    @Column(nullable = false)
    private BigDecimal free;
    
    @Column(nullable = false, name = "frozen_fund")
    private BigDecimal frozenFund;

    @Column(nullable = false, name = "frozen_grant")
    private BigDecimal frozenGrant;

    @Column(nullable = false, name = "total_recharge")
    private BigDecimal totalRecharge;
    
    @Column(nullable = false, name = "total_withdraw")
    private BigDecimal totalWithdraw;
    
    @Column(nullable = false, name = "total_bet")
    private BigDecimal totalBet;
    
    @Column(nullable = false, name = "total_award")
    private BigDecimal totalAward;
    
    @Column(nullable = false, name = "total_commission")
    private BigDecimal totalCommission;
    
    private String province;
    
    private String city;
    
    private String bank;
    
    @Column(name = "account_number")
    private String accountNumber;
    
    private String password;
    
    @Column(nullable = false, name = "verify_code")
    private String verifyCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "checked_time")
    private Date checkedTime;
    
    private int status;
    
    @Version
    private Integer version;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public BigDecimal getGrant() {
        return grant;
    }

    public void setGrant(BigDecimal grant) {
        this.grant = grant;
    }

    public BigDecimal getFrozenFund() {
        return frozenFund;
    }

    public void setFrozenFund(BigDecimal frozenFund) {
        this.frozenFund = frozenFund;
    }

    public BigDecimal getFrozenGrant() {
        return frozenGrant;
    }

    public void setFrozenGrant(BigDecimal frozenGrant) {
        this.frozenGrant = frozenGrant;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(BigDecimal totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public BigDecimal getTotalWithdraw() {
        return totalWithdraw;
    }

    public void setTotalWithdraw(BigDecimal totalWithdraw) {
        this.totalWithdraw = totalWithdraw;
    }

    public BigDecimal getTotalBet() {
        return totalBet;
    }

    public void setTotalBet(BigDecimal totalBet) {
        this.totalBet = totalBet;
    }

    public BigDecimal getTotalAward() {
        return totalAward;
    }

    public void setTotalAward(BigDecimal totalAward) {
        this.totalAward = totalAward;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Date getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(Date checkedTime) {
        this.checkedTime = checkedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public void add(int key, BigDecimal val){
        switch(key){
            case Constants.FUND:
                fund = fund.add(val);
                break;
            case Constants.FREE:
                free = free.add(val);
                break;
            case Constants.GRANT:
                grant = grant.add(val);
                break;
            case Constants.FROZEN_FUND:
                frozenFund = frozenFund.add(val);
                break;
            case Constants.FROZEN_GRANT:
                frozenGrant = frozenGrant.add(val);
                break;
            case Constants.TOTAL_BET:
                totalBet = totalBet.add(val);
                break;
            case Constants.TOTAL_AWARD:
                totalAward = totalAward.add(val);
                break;
            case Constants.TOTAL_WITHDRAW:
                totalWithdraw = totalWithdraw.add(val);
                break;
            case Constants.TOTAL_RECHARGE:
                totalRecharge = totalRecharge.add(val);
                break;
            case Constants.TOTAL_COMMISSION:
            	totalCommission = totalCommission.add(val);
                break;
        }
    }
    
    public void subtract(int key, BigDecimal val){
        switch(key){
            case Constants.FUND:
                fund = fund.subtract(val);
                break;
            case Constants.FREE:
                free = free.subtract(val);
                break;
            case Constants.GRANT:
                grant = grant.subtract(val);
                break;
            case Constants.FROZEN_FUND:
                frozenFund = frozenFund.subtract(val);
                break;
            case Constants.FROZEN_GRANT:
                frozenGrant = frozenGrant.subtract(val);
                break;
            case Constants.TOTAL_BET:
                totalBet = totalBet.subtract(val);
                break;
            case Constants.TOTAL_AWARD:
                totalAward = totalAward.subtract(val);
                break;
            case Constants.TOTAL_WITHDRAW:
                totalWithdraw = totalWithdraw.subtract(val);
                break;
            case Constants.TOTAL_RECHARGE:
                totalRecharge = totalRecharge.subtract(val);
                break;
        }
    }

	public BigDecimal getTotalCommission() {
		return totalCommission;
	}

	public void setTotalCommission(BigDecimal totalCommission) {
		this.totalCommission = totalCommission;
	}
}
