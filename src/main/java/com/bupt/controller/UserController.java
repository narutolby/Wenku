package com.bupt.controller;

import com.bupt.domain.User;
import com.bupt.service.usesr.IUserService;
import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: meng.zm
 * Date: 14-3-17
 * Time: 上午11:37
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class UserController {

   @Autowired
   private IUserService userService;

   @RequestMapping(value="/reg")
   public void register(@RequestParam(value="username") String username,@RequestParam(value="password")String password,ModelMap modelMap) throws Exception{
       User oldUser = this.userService.getUserByUserName(username);
       if(oldUser!=null){
           modelMap.put("register","exists");
           return;
       }
       User newUser = new User() ;
       newUser.setUserName(username);
       newUser.setUserPasswd(password);
       this.userService.saveUser(newUser);
       modelMap.put("register","OK");
   }

    @RequestMapping(value="/login")
    public void login(@RequestParam(value="username") String username,@RequestParam(value="password")String password,ModelMap modelMap,HttpServletRequest request) throws Exception{
       User user = this.userService.getUser(username,password);
       if(user==null){
          modelMap.put("login","ERROR") ;
       }else{
           HttpSession session = request.getSession();
           session.setAttribute("user",user);
           modelMap.put("login","OK") ;
           modelMap.put("user",user);
       }
    }

    @RequestMapping(value="/logout")
    public void logout(HttpServletRequest request,ModelMap modelMap){
       HttpSession session = request.getSession(false);
       if(session!=null){
           session.invalidate();
       }
       modelMap.put("logout","OK");
    }
}
