package com.bupt.dao.user;

import com.bupt.dao.HibernateGenericDaoImpl;
import com.bupt.domain.User;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-22
 * Time: 上午5:02
 * To change this template use File | Settings | File Templates.
 */
@Repository
public class UserDaoImpl extends HibernateGenericDaoImpl<User,String> implements IUserDao {

}
