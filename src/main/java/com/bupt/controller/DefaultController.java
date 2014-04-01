package com.bupt.controller;

import com.bupt.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: narutolby
 * Date: 13-8-5
 * Time: 下午8:30
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class DefaultController {




    @RequestMapping("/index")
    public void index(HttpServletRequest request,ModelMap modelMap){
        HttpSession session = request.getSession(false);
       if(session==null) {
           modelMap.put("online","NO");
       }else{
           User user = (User)session.getAttribute("user");
           if(user!=null){
               modelMap.put("user",user) ;
               modelMap.put("online","YES");
           }else{
               modelMap.put("online","NO");
           }
       }
    }
}
