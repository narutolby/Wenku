package com.bupt.webContext;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-29
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class BRWebApplicationContext {

    private static String webRootPath;
    private static ServletContext sc;

    public static String getDomain() {
        return domain;
    }

    public static void setDomain(String domain) {
        BRWebApplicationContext.domain = domain;
    }

    private static String domain;


    public static ServletContext getSc() {
        return sc;
    }

    public static void setSc(ServletContext sc) {
        BRWebApplicationContext.sc = sc;
    }


    public static void setWebRootPath(String rootPath) {
        webRootPath = rootPath;
    }

    public static String getWebRootPath() {
        return webRootPath;
    }
}
