package com.bupt.filter;

import com.bupt.domain.User;
import com.bupt.service.usesr.IUserService;
import com.bupt.util.Constants;
import com.bupt.util.LoggerUtil;
import com.bupt.webContext.WebContextThreadLocal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午10:42
 * To change this template use File | Settings | File Templates.
 */
@Component
@Qualifier("contextFilter")
public class ContextFilter{

    @Autowired
    private IUserService userService;
    public void init() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        {
            HttpSession session = req.getSession(false);
            if (session != null) {
                User user = (User) session.getAttribute(Constants.USER_INFO);
                if (user != null) {
                    WebContextThreadLocal.setCurrentUser(user);
                    request.setAttribute(Constants.USER_INFO, user);
                    chain.doFilter(request, response);
                    return;
                }
            }
        }
        {
            String userId = request.getParameter(Constants.USERID);
            String passwd = request.getParameter(Constants.PASSWD);
            if (!"".equals(userId) && null != userId && !"".equals(passwd) && null != passwd) {
                chain.doFilter(request, response);
                return;
            }
        }
        {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (Constants.BRUSERID.equals(cookie.getName())) {
                        User user = this.userService.getUserByUserName(cookie.getValue());
                        if (user != null) {
                            HttpSession session = req.getSession();
                            WebContextThreadLocal.setCurrentUser(user);
                            LoggerUtil.info(this.getClass(),"当前登录用户为:"+user.getUserName());
                            //AuthenticateRole.authenticate(req, user,this.userIdRuleReg);
                            //request.setAttribute(Constants.CURRENT_PAGE, req.getServletPath());
                            request.setAttribute(Constants.USER_INFO, user);
                            session.setAttribute(Constants.USER_INFO, user);
                        }
                        chain.doFilter(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response);
        this.destroy();

    }

    public void destroy() {
        WebContextThreadLocal.unbind();
    }

    private void redirectOtherPage(ServletResponse response, String url) throws IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect("login.html");
    }

    private void userVerifySuccess(ServletRequest request, ServletResponse response, FilterChain chain, User currentUser) throws ServletException, IOException {
        WebContextThreadLocal.setCurrentUser(currentUser);
        chain.doFilter(request, response);
    }
}
