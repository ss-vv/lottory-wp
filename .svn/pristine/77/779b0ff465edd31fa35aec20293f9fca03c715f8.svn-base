package com.xhcms.lottery.admin.web.action.ticket;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.util.model.NameAndValue;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.admin.persist.service.LotteryPlatformPriorityService;
import com.xhcms.lottery.admin.web.action.BaseAction;

public class AllocateTicketAction extends BaseAction {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -758568393829672143L;
	
	private Logger log = LoggerFactory.getLogger(AllocateTicketAction.class);

	@Autowired
    private LotteryPlatformPriorityService lotteryPlatformPriorityService;
  
    private List<LotteryPlatformPriority> priorityList;
    
    private Long id;
    public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getPriorityOfBigTicket() {
		return priorityOfBigTicket;
	}
	public void setPriorityOfBigTicket(int priorityOfBigTicket) {
		this.priorityOfBigTicket = priorityOfBigTicket;
	}

	private int priority;
    private int priorityOfBigTicket;
    
    private String lotteryId;
    
    private String lotteryPlatformId;
    
    private List<NameAndValue> lotteryPlatformAliasNameList;

	private Data data=Data.success(getText("message.success"));

	private List<NameAndValue> lotteryNameList;
	
	private List<Long> selectedIds;
	
	private List<Integer> selectedPrioritys;
	
	private List<Integer> selectedPriorityOfBigTickets;
    
    @Override
    public String execute(){
    	if(StringUtils.isBlank(lotteryPlatformId)){
    		lotteryPlatformId="all";
    	}
    	if(StringUtils.isBlank(lotteryId)){
    		lotteryId="all";
    	}
    	priorityList=lotteryPlatformPriorityService.listShiTiDianPriorityList(lotteryId,lotteryPlatformId);
    	lotteryPlatformAliasNameList=lotteryPlatformPriorityService.listShiTiDianAliasNameList();
    	setLotteryNameList(lotteryPlatformPriorityService.listShiTiDianLotteryNameList());
        return SUCCESS;
    }
	public List<LotteryPlatformPriority> getPriorityList() {
		return priorityList;
	}
	
	public String edit(){
		log.info("修改实体店分票比例：id={}, priority={},priorityOfBigTicket={}", new Object[]{id, priority,priorityOfBigTicket});
    	
		lotteryPlatformPriorityService.updatePriority(id, priority,priorityOfBigTicket);
        data=Data.success(null);
        
        return SUCCESS;
	}
	
	public String batchEdit(){
		boolean result = false;
        try {
			result = lotteryPlatformPriorityService.batchUpdatePriority(selectedIds,selectedPrioritys,selectedPriorityOfBigTickets);
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
        if(!result) {
        	data = Data.failure("批量修改分票比例失败！");
        }
    	return SUCCESS;
	}
	
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public List<NameAndValue> getLotteryPlatformAliasNameList() {
		return lotteryPlatformAliasNameList;
	}
	public void setLotteryPlatformAliasNameList(
			List<NameAndValue> lotteryPlatformAliasNameList) {
		this.lotteryPlatformAliasNameList = lotteryPlatformAliasNameList;
	}
	public String getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
	public List<NameAndValue> getLotteryNameList() {
		return lotteryNameList;
	}
	public void setLotteryNameList(List<NameAndValue> lotteryNameList) {
		this.lotteryNameList = lotteryNameList;
	}
	public List<Long> getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(List<Long> selectedIds) {
		this.selectedIds = selectedIds;
	}
	public List<Integer> getSelectedPrioritys() {
		return selectedPrioritys;
	}
	public void setSelectedPrioritys(List<Integer> selectedPrioritys) {
		this.selectedPrioritys = selectedPrioritys;
	}
	public List<Integer> getSelectedPriorityOfBigTickets() {
		return selectedPriorityOfBigTickets;
	}
	public void setSelectedPriorityOfBigTickets(
			List<Integer> selectedPriorityOfBigTickets) {
		this.selectedPriorityOfBigTickets = selectedPriorityOfBigTickets;
	}
	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}
	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}
    
    
}