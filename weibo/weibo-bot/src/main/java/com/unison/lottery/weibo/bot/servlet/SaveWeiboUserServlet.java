package com.unison.lottery.weibo.bot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.weibo.bot.model.SinaWeiboUser;
import com.unison.lottery.weibo.bot.service.RobotService;

import weibo4j.Account;
import weibo4j.Friendships;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.http.AccessToken;
import weibo4j.http.HttpClient;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONException;

/**
 * @Description:保存新浪微博授权用户
 * 		--内部使用的新浪微博用户，用于订阅大V用户的微博消息 
 * @author 江浩翔   
 * @date 2013-10-21 上午10:53:38 
 * @version V1.0
 */
public class SaveWeiboUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SaveWeiboUserServlet.class.getName());
	private RobotService robotService;
    
	@Override    
	public void init() throws ServletException {             
		super.init();     
		ServletContext servletContext = this.getServletContext();     
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);     
		robotService = (RobotService)ctx.getBean("robotService");     
	}     
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		String code = request.getParameter("code");
		if (null != code) {
			log.info("获得code:{}" + code);
			Oauth oauth = new Oauth();
			try {
				Date createDate = new Date();
				AccessToken accessToken = oauth.getAccessTokenByCode(code);
				SinaWeiboUser sinaWeiboUser = makeSinaWeiboUser(createDate, accessToken);
				robotService.saveSinaWeiboUser(sinaWeiboUser);
				PrintWriter out = response.getWriter();
				out.println("<html><body>");
				out.println("code:" + code + "<br/>");
				out.println("access_token:" + accessToken.toString() + "<br/>");
				out.println("</body></html>");
				out.flush();
			} catch (WeiboException e) {
				log.error("使用code换取access_token失败！", e);
				e.printStackTrace();
			}
		} else {
			PrintWriter out = response.getWriter();
			log.info("code is null");
			out.println("<html><body><h1>code is null.</h1></body></html>");
			out.flush();
		}
	}
	
	/** 将accessToken 信息转换成 SinaWeiboUser 信息*/
	private SinaWeiboUser makeSinaWeiboUser(Date createDate, AccessToken accessToken){
		SinaWeiboUser sinaWeiboUser = new SinaWeiboUser();
		sinaWeiboUser.setUid(accessToken.getUid());
		sinaWeiboUser.setAccessToken(accessToken.getAccessToken());
		sinaWeiboUser.setExpires(Long.parseLong(accessToken.getExpireIn()));
		sinaWeiboUser.setCreateTime(createDate);
		return sinaWeiboUser;
	} 
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
