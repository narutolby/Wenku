package com.bupt.webContext;

import com.bupt.domain.*;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-27
 * Time: 下午9:57
 * To change this template use File | Settings | File Templates.
 */
public class WebContextThreadLocal {

    private static ThreadLocal<User> currentUser = new ThreadLocal<User>();

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void unbind() {
        currentUser.remove();
    }
}
