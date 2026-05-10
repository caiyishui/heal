package com.csjbot.coshandler.log;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

/**
 * Created by jingwc on 2017/11/17.
 */

public class CsjlogProxy {

    public static final String LOG_ACTION = "csjbot_log";
    public static final String LOG_CONTENT = "csjbot_log_content";
    public static final String LOG_COLOR = "csjbot_log_color";
    public static final String PING_ACTION = "csjbot_ping";
    public static final String PING_NAME = "csjbot_ping_name";

    public static boolean isShowLogWindow = false;
    public static boolean isShowPingWindow = false;

    Context mContext;

    int debugColor;
    int infoColor;
    int warnColor;
    int errorColor;

    private static class CsjlogProxyHolder {
        private static final CsjlogProxy INSTANCE = new CsjlogProxy();
    }

    public static CsjlogProxy getInstance() {
        return CsjlogProxyHolder.INSTANCE;
    }

    private CsjlogProxy() {
        debugColor = Color.parseColor("#0070BB");
        infoColor = Color.parseColor("#48BB31");
        warnColor = Color.parseColor("#BBBB23");
        errorColor = Color.parseColor("#FF0006");
    }

    public void initLog(Context context) {
        mContext = context;
    }

    public void sendLogMsg(String name, int color) {
        if (!isShowLogWindow) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(LOG_ACTION);
        intent.putExtra(LOG_CONTENT, name);
        intent.putExtra(LOG_COLOR, color);
        if (mContext != null) {
            mContext.sendBroadcast(intent);
        }
    }

    public void printPing(String ping) {
        if (!isShowPingWindow) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(PING_ACTION);
        intent.putExtra(PING_NAME, ping);
        if (mContext != null) {
            mContext.sendBroadcast(intent);
        }
    }

    public void debug(String msg) {
        Csjlogger.debug(msg);
        sendLogMsg(msg, debugColor);
    }

    public void debug(String msg, Throwable t) {
        Csjlogger.debug(msg, t);
    }

    public void debug(String format, Object... arguments) {
        Csjlogger.debug(format, arguments);
    }

    // =============== info================/
    // =============== info================/
    public void info(String msg) {
        Csjlogger.info(msg);
        sendLogMsg(msg, infoColor);
    }

    public void info(String msg, Throwable t) {
        Csjlogger.info(msg, t);
    }

    public void info(String format, Object... arguments) {
        Csjlogger.info(format, arguments);
    }

    // =============== warn================/
    // =============== warn================/
    public void warn(String msg) {
        Csjlogger.warn(msg);
        sendLogMsg(msg, warnColor);
    }

    public void warn(String msg, Throwable t) {
        Csjlogger.warn(msg, t);
    }

    public void warn(String format, Object... arguments) {
        Csjlogger.warn(format, arguments);
    }

    // =============== error================/
    // =============== error================/
    public void error(String msg) {
        Csjlogger.error(msg);
        sendLogMsg(msg, errorColor);
    }

    public void error(Throwable t) {
        Csjlogger.error(t);
    }

    public void error(String msg, Throwable t) {
        Csjlogger.error(msg, t);
    }

    public void error(String format, Object... arguments) {
        Csjlogger.error(format, arguments);
    }

    // =============== Debug Line================/
    // =============== Debug Line================/
    public void DBL() {
        Csjlogger.DBL();
    }

    public void exitLogger() {
        Csjlogger.exitLogger();
    }

    public void getCallMethod(String msg) {
        Csjlogger.getCallMethod(msg);
        sendLogMsg(msg, debugColor);
    }

    public void setOutPutEnable(boolean b) {
        Csjlogger.setOutPutEnable(b);
    }
}
