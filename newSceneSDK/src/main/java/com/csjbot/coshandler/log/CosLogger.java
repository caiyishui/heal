package com.csjbot.coshandler.log;


import java.text.SimpleDateFormat;
import java.util.Date;

import ch.qos.logback.classic.pattern.SyslogStartConverter;

public class CosLogger {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static String getTime() {
        return simpleDateFormat.format(new Date());
    }

    public static void setSN(String sn) {
        SyslogStartConverter.setSN("NewApp_" + sn);
    }

    public static void debug(String msg) {
        Csjlogger.debug(msg);
    }

    public static void debug(String format, Object... arguments) {
        Csjlogger.debug(format, arguments);
    }

    public static void debug(String msg, Throwable t) {
        Csjlogger.debug(msg);
    }


    public static void info(String msg) {
        Csjlogger.info(msg);
    }

    public static void info(String format, Object... arguments) {
        Csjlogger.info(format, arguments);
    }

    public static void info(String msg, Throwable t) {
        Csjlogger.info(msg);
    }

    public static void warn(String msg) {
        Csjlogger.warn(msg);
    }

    public static void warn(String format, Object... arguments) {
        Csjlogger.warn(format, arguments);
    }

    public static void warn(String msg, Throwable t) {
        Csjlogger.warn(msg);
    }

    public static void error(String msg) {
        Csjlogger.error(msg);
    }

    public static void error(String format, Object... arguments) {
        Csjlogger.error(format, arguments);
    }

    public static void error(String msg, Throwable t) {
        Csjlogger.error(msg);
    }


    public static void getCallMethod(String format, Object... arguments) {
        Csjlogger.getCallMethod(format, arguments);
    }
}
