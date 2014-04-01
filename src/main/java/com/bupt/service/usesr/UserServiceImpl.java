package com.bupt.service.usesr;

import com.bupt.dao.user.IUserDao;
import com.bupt.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-28
 * Time: 上午1:12
 * To change this template use File | Settings | File Templates.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public void saveUser(User user) throws Exception {
        this.userDao.save(user);
        this.userDao.flush();
    }

    @Override
    public User getUserByUserId(String userId) throws Exception {
        User user = this.userDao.findEntity("from User user where user.id=?",userId);
        //  Set<Course> courseSet = user.getCourses();
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public User getUserByUserName(String username) throws Exception {
        User user = this.userDao.findEntity("from User user where user.userName=?",username);
      //  Set<Course> courseSet = user.getCourses();
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }

    @Override
    public User getUser(String username, String password) throws Exception {
        User user = null;
        user = this.userDao.findEntity("from User user where user.userName=? and userPasswd=?",username,password);
        return user;
    }

    @Override
    public int getUsersCount() throws Exception {
        return this.userDao.find("from User user").size();
    }

    @Override
    public void flush() throws Exception {
        this.userDao.flush();
    }
}
