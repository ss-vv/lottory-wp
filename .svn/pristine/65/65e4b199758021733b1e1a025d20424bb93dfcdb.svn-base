package com.unison.lottery.api.showAndFollow.servlet;


import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.framework.servlet.AbstractProcessServlet;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;
import com.unison.lottery.api.showAndFollow.bo.ShowAndFollowBO;





/**
 * Servlet implementation class RegisteWithSecurityCodeServlet
 */
@WebServlet("/xml/showAndFollow")
public class ShowAndFollowServlet extends AbstractProcessServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -666524087840466789L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public ShowAndFollowServlet() {
        super();
        
    }
	@Override
	protected String getMethodName() {
		return MethodNames.SHOW_AND_FOLLOW;
	}
	@Override
	protected String getResponseVOName() {
		return VONames.SHOW_AND_FOLLOW_VO_NAME;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected
	HaveReturnStatusResponse process(HttpServletRequest request) {
		Map<String,String>  paramMap = (Map<String,String>)request.getAttribute(VONames.SHOW_AND_FOLLOW_VO_NAME);
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		ShowAndFollowBO showAndFollowBO=(ShowAndFollowBO) ctx.getBean("showAndFollowBO");
		return showAndFollowBO.queryShowingScheme(paramMap,user);
	}

}
