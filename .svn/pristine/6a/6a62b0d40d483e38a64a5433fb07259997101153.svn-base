package com.xhcms.ucenter.web.velocity;

import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.apache.struts2.dispatcher.VelocityResult;
import org.apache.struts2.views.JspSupportServlet;
import org.apache.struts2.views.velocity.VelocityManager;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 
 * <p>Title: Struts2使用的Velocity Layout结果</p>
 * <p>Description: 用于整合Struts2和Velocity Layout，实现参考org.apache.velocity.tools.view.VelocityLayoutServlet</p>
 * <p>Copyright：Copyright (c) 2010</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * @author wanged
 * @version 1.0
 */
public class VelocityLayoutResult extends StrutsResultSupport {

    private static final long serialVersionUID = -8343564727051357555L;

    /**
     * Velocity Layout模板文件目录
     */
    public static final String PROPERTY_LAYOUT_DIR = "tools.view.servlet.layout.directory";
    
    /**
     * 默认Velocity Layout模板文件
     */
    public static final String PROPERTY_DEFAULT_LAYOUT = "tools.view.servlet.layout.default.template";
    
    private static final String KEY_SCREEN_CONTENT = "screen_content";

    private static final String KEY_LAYOUT = "layout";

    private static final Logger LOG = LoggerFactory.getLogger(VelocityResult.class);

    protected String defaultLayout;
    protected String layoutDir;
    
    private String defaultEncoding;
    private VelocityManager velocityManager;
    private VelocityEngine engine;
    
    public VelocityLayoutResult() {
    }

    public VelocityLayoutResult(String location) {
        super(location);
    }

    @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
    public void setDefaultEncoding(String val) {
        defaultEncoding = val;
    }

    @Inject
    public void setVelocityManager(VelocityManager velocityManager) {
        this.velocityManager = velocityManager;
        this.velocityManager.init(ServletActionContext.getServletContext());
        
        this.engine = velocityManager.getVelocityEngine();
        this.layoutDir = (String) engine.getProperty(PROPERTY_LAYOUT_DIR);
        if (!layoutDir.endsWith("/")) {
            layoutDir += '/';
        }

        if (!layoutDir.startsWith("/")) {
            layoutDir = "/" + layoutDir;
        }
        
        this.defaultLayout = layoutDir + (String) engine.getProperty(PROPERTY_DEFAULT_LAYOUT);
    }

    public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        ValueStack stack = ActionContext.getContext().getValueStack();

        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();
        Servlet servlet = JspSupportServlet.jspSupportServlet;
        JspFactory jspFactory = null;
        boolean usedJspFactory = false;

        PageContext pageContext = (PageContext) ActionContext.getContext().get(ServletActionContext.PAGE_CONTEXT);

        if (pageContext == null && servlet != null) {
            jspFactory = JspFactory.getDefaultFactory();
            pageContext = jspFactory.getPageContext(servlet, request, response, null, true, 8192, true);
            ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT, pageContext);
            usedJspFactory = true;
        }

        try {
            String encoding = getEncoding(finalLocation);
            String contentType = getContentType(finalLocation);

            if (encoding != null) {
                contentType = contentType + ";charset=" + encoding;
            }

            Context ctx = velocityManager.createContext(stack, request, response);
            
            StringWriter sw = new StringWriter();
            getTemplate(stack, invocation, finalLocation, encoding).merge(ctx, sw);
            ctx.put(KEY_SCREEN_CONTENT, sw.toString());
            
            String layout = defaultLayout;
            Object obj = ctx.get(KEY_LAYOUT);
            if(obj != null){
                layout = layoutDir + obj.toString();
            }

            Template layoutVm = getTemplate(stack, invocation, layout, encoding);

            Writer writer = new OutputStreamWriter(response.getOutputStream(), encoding);
            response.setContentType(contentType);

            layoutVm.merge(ctx, writer);
            writer.flush();
        } catch (Exception e) {
            LOG.error("Unable to render Velocity Template, '" + finalLocation + "'", e);
            throw e;
        } finally {
            if (usedJspFactory) {
                jspFactory.releasePageContext(pageContext);
            }
        }

        return;
    }

    protected String getContentType(String templateLocation) {
        return "text/html";
    }

    protected String getEncoding(String templateLocation) {
        String encoding = defaultEncoding;
        if (encoding == null) {
            encoding = System.getProperty("file.encoding");
        }
        if (encoding == null) {
            encoding = "UTF-8";
        }
        return encoding;
    }

    protected Template getTemplate(ValueStack stack, ActionInvocation invocation, String location, String encoding) throws Exception {
        if (!location.startsWith("/")) {
            location = invocation.getProxy().getNamespace() + "/" + location;
        }

        return engine.getTemplate(location, encoding);
    }

}
