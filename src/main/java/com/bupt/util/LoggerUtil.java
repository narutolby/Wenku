package com.bupt.util;

import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: liboyang01
 * Date: 12-7-28
 * Time: 下午11:37
 * To change this template use File | Settings | File Templates.
 */
public class LoggerUtil {

    private static Map<Class, Logger> loggerMap = Collections.synchronizedMap(new HashMap<Class, Logger>());

    public static void debug(Class clazz, String message) {
        Logger logger = getLoggerByClassType(clazz);
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }

    }

    public static boolean isDebug(Class clazz) {
         Logger logger = getLoggerByClassType(clazz);
         return logger.isDebugEnabled();
    }
    public static void info(Class clazz, String message) {
        Logger logger = getLoggerByClassType(clazz);
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public static void error(Class clazz, String message) {
        Logger logger = getLoggerByClassType(clazz);
        logger.error(message);
    }

    private static Logger getLoggerByClassType(Class clazz) {
        Logger logger = loggerMap.get(clazz);
        if (logger == null) {
            logger = Logger.getLogger(clazz);
            loggerMap.put(clazz, logger);
        }
        return logger;
    }
}
