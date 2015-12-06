/**
 * 
 */
package com.xhcms.ucenter.sso.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.ucenter.exception.UCException;
import com.xhcms.ucenter.persist.dao.IRegistSSOServiceDao;
import com.xhcms.ucenter.persist.entity.RegistSSOServicePO;
import com.xhcms.ucenter.sso.principal.Credentials;
import com.xhcms.ucenter.sso.service.IAuthenticateHandler;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.UserProfile;
import com.xhcms.ucenter.sso.ticket.IExpirationPolicy;
import com.xhcms.ucenter.sso.ticket.IService;
import com.xhcms.ucenter.sso.ticket.ITicketRegistry;
import com.xhcms.ucenter.sso.ticket.impl.GrantingTicket;
import com.xhcms.ucenter.sso.ticket.impl.ServiceTicket;
import com.xhcms.ucenter.sso.util.generator.UniqueTicketIdGenerator;
import com.xhcms.ucenter.vo.sso.RegistSSOService;

/**
 * @author bean
 *
 */
public class AuthenticationManagerDefault implements IAuthenticationManager {
	@Autowired
	private IRegistSSOServiceDao registSSOServiceDao;
	@Autowired
	private UniqueTicketIdGenerator ticketIdGenerator;
	@Autowired
	private ITicketRegistry ticketRegistry;
	
	@Autowired
	private IAuthenticateHandler authenticateHandler;
	
	@Autowired
	private IAuthenticateHandler openIdAuthenticateHandler;

	@Autowired
	private IExpirationPolicy serviceTicketExpirationPolicy;
	@Autowired
	private IExpirationPolicy grantingTicketExpirationPolicy;
	
	@Override
	public ServiceTicket createServiceTicket(String grantingTicketId,
			IService service) {
		
		//validateService(service);
		
		GrantingTicket grantingTicket = (GrantingTicket)ticketRegistry.getTicket(grantingTicketId, GrantingTicket.class);
		if(grantingTicket == null) {
			throw new UCException("user.sso.InvalidateTicket", "invalidate Granting Ticket!");
		}
		
		String serviceTicketId = ticketIdGenerator.getNewTicketId(ServiceTicket.PREFIX);
		ServiceTicket serviceTicket = new ServiceTicket(serviceTicketId, 
				service);
		serviceTicket.setGrantingTicketId(grantingTicketId);
		
		grantingTicket.addSerice(service);
		grantingTicket.update();

		serviceTicket.setExpirationPolicy(serviceTicketExpirationPolicy);
		
		return serviceTicket;
	}

	@Override
	public ServiceTicket createServiceTicket(GrantingTicket grantingTicket,
			IService service) {
		String serviceTicketId = ticketIdGenerator.getNewTicketId(ServiceTicket.PREFIX);
		
		ServiceTicket serviceTicket = new ServiceTicket(serviceTicketId, 
				service);
		
		serviceTicket.setGrantingTicketId(grantingTicket.getId());
		grantingTicket.addSerice(service);

		grantingTicket.update();

		ticketRegistry.addTicket(serviceTicket);
		
		serviceTicket.setExpirationPolicy(serviceTicketExpirationPolicy);
		
		return serviceTicket;
	}

	@Override
	public GrantingTicket createGrantingTicket(UserProfile userProfile) {
		String ticket = ticketIdGenerator.getNewTicketId(GrantingTicket.PREFIX);
		GrantingTicket grantingTicket = new GrantingTicket(ticket);
		ticketRegistry.addTicket(grantingTicket);
		
		if(userProfile.isRefresh()) {
			userProfile = authenticateHandler.loadUserProfile(userProfile);
		}
		
		grantingTicket.setUserProfile(userProfile);
		grantingTicket.setExpirationPolicy(grantingTicketExpirationPolicy);
		
		return grantingTicket;
	}

	@Override
	public GrantingTicket createGrantingTicket(String grantingTiket, UserProfile userProfile) {
		GrantingTicket grantingTicket = 
			(GrantingTicket)ticketRegistry.getTicket(grantingTiket, GrantingTicket.class);
		if(grantingTicket == null) {
			grantingTicket = new GrantingTicket(grantingTiket);
			ticketRegistry.addTicket(grantingTicket);
		}
		
		grantingTicket.update();
		
		if(grantingTicket.getUserProfile() == null && userProfile.isRefresh()) {
			userProfile = authenticateHandler.loadUserProfile(userProfile);
		}
		
		grantingTicket.setUserProfile(userProfile);
		grantingTicket.setExpirationPolicy(grantingTicketExpirationPolicy);
		
		return grantingTicket;
	}

	@Override
	public void validateService(IService service) {
		RegistSSOService registSSOServicePO =
			findRegistSSOServiceByServiceId(service.getId());
		
		if(registSSOServicePO == null) {
			throw new UCException("ucenter.sso.InvalidateService", "Invalidate Service!");
		}
	}
	
	public RegistSSOService findRegistSSOServiceByServiceId(String serviceId) {
		RegistSSOServicePO po = registSSOServiceDao.findRegistSSOServiceByServiceId(serviceId);
		return po2vo(po);
	}
	
	@Override
	public void registSSOService(String serviceId, String name, String memo) {
		RegistSSOServicePO registSSOServicePO = new RegistSSOServicePO();
		
		registSSOServicePO.setServiceId(serviceId);
		registSSOServicePO.setName(name);
		registSSOServicePO.setMemo(memo);
		
		registSSOServiceDao.save(registSSOServicePO);
	}	
	
	@Override
	public UserProfile authenticate(Credentials credentials) {
		UserProfile userProfile = authenticateHandler.authenticate(credentials);
		if(userProfile != null) {
			return userProfile;
		}
		
		return null;
	}

	private RegistSSOService po2vo(RegistSSOServicePO registSSOServicePO) {
		if(registSSOServicePO == null)
			return null;
		
		RegistSSOService registSSOService = new RegistSSOService();
		BeanUtils.copyProperties(registSSOServicePO, registSSOService);
		
		return registSSOService;
	}

	@Override
	public UserProfile authenticateByOpenId(Credentials credentials) {
		UserProfile userProfile = openIdAuthenticateHandler.authenticate(credentials);
		if(userProfile != null) {
			return userProfile;
		}
		return null;
	}
}
