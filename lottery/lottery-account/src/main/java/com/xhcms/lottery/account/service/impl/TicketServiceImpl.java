package com.xhcms.lottery.account.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.account.service.TicketService;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.lang.EntityStatus;

public class TicketServiceImpl implements TicketService {

    private static final Logger log = LoggerFactory.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private BetSchemeDao betSchemeDao;

    @Override
    @Transactional
    public void check(Map<Long, Ticket> tickets) {
        HashMap<Long, BetSchemePO> sMap = new HashMap<Long, BetSchemePO>();
        List<TicketPO> pos = ticketDao.find(tickets.keySet());

        for (TicketPO po : pos) {
            if (po.getStatus() != EntityStatus.TICKET_REQUIRED) {
                warn(new Object[] { "check", "status", po.getId(), EntityStatus.TICKET_REQUIRED, po.getStatus() });
                continue;
            }
            Ticket t = tickets.get(po.getId());
            if (t == null) {
                continue;
            }
            if (!po.getPlayId().equals(t.getPlayId())) {
                warn(new Object[] { "check", "playId", po.getId(), po.getPlayId(), t.getPlayId() });
                continue;
            }
            if (po.getMoney() != t.getMoney()) {
                warn(new Object[] { "check", "money", po.getId(), po.getMoney(), t.getMoney() });
                continue;
            }
            if (po.getMultiple() != t.getMultiple()) {
                warn(new Object[] { "check", "multiple", po.getId(), po.getMultiple(), t.getMultiple() });
                continue;
            }

            if (t.getStatus() == EntityStatus.TICKET_NOT_EXIST) {
                po.setStatus(EntityStatus.TICKET_INIT);
            } else {
                po.setNumber(t.getNumber());
                po.setMessage(t.getMessage());
                po.setStatus(t.getStatus());
                po.setOdds(t.getOdds());
            }
            ticketDao.update(po);
            sMap.put(po.getSchemeId(), null);
        }
        
        List<BetSchemePO> bss = betSchemeDao.find(sMap.keySet());
        for (BetSchemePO s : bss) {
            sMap.put(s.getId(), s);
        }

        // 检查方案的出票情况，设置方案状态
        for (Object[] data : ticketDao.sumBoughtNote(sMap.keySet())) {
            Long sid = (Long) data[0];
            int status = ((Number) data[1]).intValue();
            int note = ((Number) data[2]).intValue();

            BetSchemePO spo = sMap.get(sid);
            if(status == EntityStatus.TICKET_BUY_SUCCESS && note > 0){
                spo.setTicketNote(note);
            }else if(status == EntityStatus.TICKET_BUY_FAIL && note > 0){
                spo.setCancelNote(note);
            }
            
            if(spo.getBetNote() == spo.getCancelNote() + spo.getTicketNote()){
                if(spo.getTicketNote() > 0){
                    spo.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
                }else{
                    spo.setStatus(EntityStatus.TICKET_BUY_FAIL);
                }
            }
        }
    }

    private void warn(Object[] args) {
        log.warn("{}: {} is invalid. ticketId:{}, expected:{}, but was:{}.", args);
    }

}
