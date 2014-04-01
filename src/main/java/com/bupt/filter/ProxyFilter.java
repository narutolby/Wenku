package com.bupt.filter;

import com.bupt.webContext.BRWebApplicationContext;
import com.bupt.webContext.WebContextThreadLocal;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-28
 * Time: 上午12:55
 * To change this template use File | Settings | File Templates.
 */
public class ProxyFilter implements Filter {

    private ContextFilter contextFilter = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext sc = filterConfig.getServletContext();
        String webRootPath = sc.getRealPath("/");
        BRWebApplicationContext.setWebRootPath(webRootPath);
        BRWebApplicationContext.setSc(sc);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        WebContextThreadLocal.unbind();
        HttpServletRequest req = (HttpServletRequest) request;
        if (BRWebApplicationContext.getDomain() == null) {
            String url = req.getRequestURL().toString();
            String contextPath = req.getContextPath();
            Pattern pattern = Pattern.compile("(http://.*?)/");
            Matcher matcher = pattern.matcher(url);
            String basePath = null;
            while(matcher.find()){
                basePath = matcher.group(1);
            }
            if(!"".equals(contextPath)){
               basePath+="/"+contextPath;
            }
            BRWebApplicationContext.setDomain(basePath);
        }
        try {
            initContextFilter()
                    .doFilter(request, response, chain);
        } catch (Exception e) {
            this.destroy();
        }
    }

    @Override
    public void destroy() {
        if (contextFilter != null) {
            this.contextFilter.destroy();
            contextFilter = null;
        }
    }

    private ContextFilter initContextFilter() {
        if (this.contextFilter == null) {
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(BRWebApplicationContext.getSc());
            contextFilter = (ContextFilter) webApplicationContext.getBean("contextFilter");
            contextFilter.init();
        }
        return this.contextFilter;
    }
}
