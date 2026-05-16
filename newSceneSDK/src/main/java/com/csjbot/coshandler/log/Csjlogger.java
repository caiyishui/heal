/**
 * Project Name:TEST_LOG4J_SOCKET
 * File Name:LOlogger2.java
 * Package Name:com.example.utility
 * Date:2014年1月22日下午3:09:36
 * Copyright (c) 2014, ShangHai Leadon IOT Technology Co.,Ltd.  All Rights Reserved.
 */

package com.csjbot.coshandler.log;




import androidx.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import ch.qos.logback.classic.LoggerContext;

/**
 * ClassName:LOlogger2 <br/>
 * 用法：在类中定义 <br/>
 * private static final Csjlogger loLogger = new Csjlogger(xxxx.class); <br/>
 * loLogger.info(xxxxx); Date: 2014年1月22日 下午3:09:36 <br/>
 *
 * @author "浦耀宗"
 * @see
 */

public class Csjlogger implements Serializable {
    /**
     * serialVersionUID:TODO(用一句话描述这个变量表示什么).
     */
    private static final long serialVersionUID = 1245549555563181056L;

    private static Logger DBL_LOGGER = LoggerFactory.getLogger("DBL");
    private static Logger DEFAULT_LOGGER = LoggerFactory.getLogger("csjbot_scence");
    private static boolean outPutEnable = false;

    // =============== debug================/
    // =============== debug================/
    public static void debug(String msg) {
        DEFAULT_LOGGER.debug(msg + getLineOut());
    }

    public static void debug(String msg, Throwable t) {
        DEFAULT_LOGGER.debug(msg + getLineOut(), t);
    }

    public static void debug(String format, Object... arguments) {
        DEFAULT_LOGGER.debug(format + getLineOut(), arguments);
    }

    // =============== info================/
    // =============== info================/
    public static void info(String msg) {
        DEFAULT_LOGGER.info(msg + getLineOut());
    }

    public static void info(String msg, Throwable t) {
        DEFAULT_LOGGER.info(msg + getLineOut(), t);
    }

    public static void info(String format, Object... arguments) {
        DEFAULT_LOGGER.info(format + getLineOut(), arguments);
    }

    // =============== warn================/
    // =============== warn================/
    public static void warn(String msg) {
        DEFAULT_LOGGER.warn(msg + getLineOut());
    }

    public static void warn(String msg, Throwable t) {
        DEFAULT_LOGGER.warn(msg + getLineOut(), t);
    }

    public static void warn(String format, Object... arguments) {
        DEFAULT_LOGGER.warn(format + getLineOut(), arguments);
    }

    // =============== error================/
    // =============== error================/
    public static void error(String msg) {
        DEFAULT_LOGGER.error(msg + getLineOut());
    }

    public static void error(Throwable t) {
        DEFAULT_LOGGER.error("error", t);
    }

    public static void error(String msg, Throwable t) {
        DEFAULT_LOGGER.error(msg + getLineOut(), t);
    }

    public static void error(String format, Object... arguments) {
        DEFAULT_LOGGER.error(format + getLineOut(), arguments);
    }

    // =============== Debug Line================/
    // =============== Debug Line================/
    public static void DBL() {
        DBL_LOGGER.trace(" ========== " + getLineOut() + " ========== ");
    }

    public static void exitLogger() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        if (loggerContext != null) {
            loggerContext.stop();
        }
    }

    public static void setOutPutEnable(boolean b) {
        outPutEnable = b;
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static void getCallMethod(String format, Object... arguments) {
        DEFAULT_LOGGER.warn(format + getLineOutCall(), arguments);
    }

    private static String getLineOutCall() {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("getLineOutCall") == 0) {
                currentIndex = i + 4;
                break;
            }
        }

        return " [" +
                stackTraceElement[currentIndex].getMethodName() +
                "] (" +
                stackTraceElement[currentIndex].getFileName() +
                ":" +
                stackTraceElement[currentIndex].getLineNumber() +
                ")";
    }

    @NonNull
    private static String getLineOut() {
        StackTraceElement[] stackTraceElement = Thread.currentThread()
                .getStackTrace();
        int currentIndex = -1;
        for (int i = 0; i < stackTraceElement.length; i++) {
            if (stackTraceElement[i].getMethodName().compareTo("getLineOut") == 0) {
                currentIndex = i + 3;
                break;
            }
        }

        return " [" +
                stackTraceElement[currentIndex].getMethodName() +
                "] (" +
                stackTraceElement[currentIndex].getFileName() +
                ":" +
                stackTraceElement[currentIndex].getLineNumber() +
                ")";
    }
}
