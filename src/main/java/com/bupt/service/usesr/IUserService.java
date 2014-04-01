package com.bupt.service.usesr;

import com.bupt.domain.*;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-28
 * Time: 上午1:12
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {

    public User getUserByUserId(String userId)throws Exception;

    public User getUserByUserName(String username)throws Exception;

    public User getUser(String userId, String userPasswd)throws Exception;

    public int getUsersCount()throws Exception;

    public void flush() throws Exception;

    public void saveUser(User user) throws Exception;

}
