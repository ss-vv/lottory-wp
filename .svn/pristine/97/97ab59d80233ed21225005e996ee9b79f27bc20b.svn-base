package com.xhcms.ucenter.sso.client.session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.ucenter.sso.client.UserProfile;

public final class SSOAuthentication {
    
    private static final Logger log = LoggerFactory.getLogger(SSOAuthentication.class);

    private static final SingleSignOutHandler handler = new SingleSignOutHandler();

    private static final SSOAuthentication instance = new SSOAuthentication();

    // 获取UserProfile的URL
    private String profileUrl;
    private String ssoLoginUrl;
    private String appLoginUrl;

    private SSOAuthentication(){
    }

    public static void init(String profileUrl, String ssoLoginUrl, String appLoginUrl){
        handler.init();
        instance.profileUrl = profileUrl;
        instance.ssoLoginUrl = ssoLoginUrl;
        instance.appLoginUrl = appLoginUrl;
    }

    public final static SessionMappingStorage getSessionMappingStorage(){
        return handler.getSessionMappingStorage();
    }

    /**
     * 检查用户是否已经登录
     * @param request
     * @param response
     * @return true：已登录；false：未登录
     */
    public static boolean check(ServletRequest request, ServletResponse response){
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String ticket = req.getParameter(SSOConstant.SSO_TICKET);
        
        // 单点退出
        if(StringUtils.isNotBlank(ticket) && StringUtils.isNotBlank(request.getParameter(SSOConstant.SSO_LOGOUT))){
            session.removeAttribute(SSOConstant.SSO_CLIENT_KEY);
            handler.destroySession(req);

            return false;
        }
        // 单点登录
        UserProfile profile = (UserProfile)session.getAttribute(SSOConstant.SSO_USER_PROFILE);
        String clientTicket = (String) session.getAttribute(SSOConstant.SSO_CLIENT_KEY);

        if(StringUtils.isNotBlank(ticket) && !ticket.equals(clientTicket)){
            profile = instance.loadProfileByTicket(ticket);
            if(profile != null){
                session.setAttribute(SSOConstant.SSO_CLIENT_KEY, ticket);
                // 首次在该应用中登录
                handler.recordSession(req);
                session.setAttribute(SSOConstant.SSO_USER_PROFILE, profile);
            }
        }

        // 未登录
        if(profile == null){
            return false;
        }

        return true;
    }

    /**
     * 通过用户ID和用户登录名从SSO服务器加载当前登录用户的个人信息
     * @param uid
     * @param username
     * @return
     */
    public static UserProfile loadProfile(Long uid, String username){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if(uid != null){
            params.add(new BasicNameValuePair("uid", uid.toString()));
        }
        if(StringUtils.isNotBlank(username)){
            params.add(new BasicNameValuePair("username", username));
        }
        
        if(params.size() == 0){
            return null;
        }
        return instance.post(params);
    }

    public static String retriveRedirect(HttpServletRequest request, HttpServletResponse response, boolean encode){
        if(instance.appLoginUrl == null){
            return instance.ssoLoginUrl + "?referer=" + instance.retriveServiceUrl(request, response, encode);
        }
        String url = instance.appLoginUrl + "?backurl=" + instance.retriveServiceUrl(request, response, encode);
        
        if(encode){
            try {
                return instance.ssoLoginUrl + "?referer=" + URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return instance.ssoLoginUrl + "?referer=" + url;
    }
    
    private String retriveServiceUrl(HttpServletRequest request, HttpServletResponse response, boolean encode) {
        StringBuilder sb = new StringBuilder();
        
        String serverName = request.getServerName();
        sb.append("http://").append(serverName);
        sb.append(request.getServerPort() == 80 ? "" : ":" + request.getServerPort());
        sb.append(request.getRequestURI());
        
        String requestURI = request.getQueryString();
        
        //过滤
        Map<String, String> paramsMap = new HashMap<String, String>();
        if(StringUtils.isNotBlank(requestURI)) {
            String[] params = requestURI.split("&");
            if(params.length > 0) {
                for(int i = 0; i < params.length; i++) {
                    String[] nameValuseParis = params[i].split("=");
                    if(nameValuseParis.length == 2 && !"ticket".equalsIgnoreCase(nameValuseParis[0])) {
                        paramsMap.put(nameValuseParis[0], nameValuseParis[1]);
                    }
                }
            }
        }
        
        if(paramsMap.size() > 0) {
            sb.append("?");
            Iterator<Entry<String, String>> itr = paramsMap.entrySet().iterator();
            int count = 0;
            while(itr.hasNext()) {
                Entry<String, String> entry = itr.next();
                if(count > 0) {
                    sb.append("&");
                }
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                count++;
            }
        }
        
        if(encode) {
            try {
                return URLEncoder.encode(sb.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        
        return sb.toString();
    }
    /**
     * 通过ticket从SSO服务器加载当前登录用户的个人信息
     * @param ticket
     * @return
     */
    private UserProfile loadProfileByTicket(String ticket){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("ticket", ticket));

        return post(params);
    }

    private UserProfile post(List<NameValuePair> params){
        if(StringUtils.isBlank(profileUrl)){
            return null;
        }

        HttpPost post = new HttpPost(profileUrl);

        try{
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpClient client = new DefaultHttpClient();

            return client.execute(post, new ResponseHandler<UserProfile>(){
                @Override
                public UserProfile handleResponse(HttpResponse resp) throws ClientProtocolException, IOException{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent(), "UTF-8"));
                    String line = null;
                    StringBuilder sb = new StringBuilder(128);

                    while((line = reader.readLine()) != null){
                        sb.append(line);
                    }

                    JSONObject json = JSONObject.fromObject(sb.toString());
                    return (UserProfile) JSONObject.toBean(json, UserProfile.class);
                }
            });
        }catch (Exception e){
            log.warn(e.getMessage());
        }

        return null;
    }

}
