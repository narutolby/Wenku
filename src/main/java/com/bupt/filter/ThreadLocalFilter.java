package com.bupt.filter;

import com.bupt.domain.User;
import com.bupt.service.converter.OfficeConverter;
import com.bupt.webContext.BRWebApplicationContext;
import com.bupt.webContext.WebContextThreadLocal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: meng.zm
 * Date: 14-3-18
 * Time: 下午10:26
 * To change this template use File | Settings | File Templates.
 */
public class ThreadLocalFilter implements Filter {
    public void destroy() {
        OfficeConverter.stopService();
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        if(session!=null){
            String rootPath = session.getServletContext().getRealPath("/");
            BRWebApplicationContext.setWebRootPath(rootPath + "upload");
            User user = (User)session.getAttribute("user");
            if(user!=null){
                WebContextThreadLocal.setCurrentUser(user);
            }
        }
        chain.doFilter(req, resp);
        if(!OfficeConverter.isStart()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OfficeConverter.startService();
                }
            }).start();
        }
        WebContextThreadLocal.unbind();
    }

    public void init(FilterConfig config) throws ServletException {
        //OfficeConverter.startService();
    }

}
