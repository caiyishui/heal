package com.demo.csjbot.csjsdkdemo.utils;

import com.csjbot.coshandler.core.CsjRobot;

/**
 * Created by Administrator on 2019/5/23.
 */

public class AutoTestManager {

    private volatile static AutoTestManager autuTestManager;

    private volatile static boolean isStart;


    private CsjRobot mCsjBot = CsjRobot.getInstance();


    public static AutoTestManager getInstance() {
        if (autuTestManager == null) {
            synchronized (AutoTestManager.class) {
                if (autuTestManager == null) {
                    autuTestManager = new AutoTestManager();
                }
            }
        }
        return autuTestManager;
    }

    public void start() {
        isStart = true;
    }

    public void stop() {
        isStart = false;
    }

    private AutoTestManager() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!isStart) {
                        continue;
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                     if (onMsgListener != null) {
                        onMsgListener.msg("Turn head left");
                    }

                    mCsjBot.getAction().actionNew(2, 1, 100, 3);
                    mCsjBot.getAction().actionNew(3, 2, 50, 3);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (onMsgListener != null) {
                        onMsgListener.msg("Head down");
                    }

                    mCsjBot.getAction().actionNew(1, 1, 30, 3);
                    mCsjBot.getAction().actionNew(2, 2, 45, 3);
                    mCsjBot.getAction().actionNew(3, 1, 50, 3);
                    mCsjBot.getAction().actionNew(4, 1, 50, 3);

                    try {
                        Thread.sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (onMsgListener != null) {
                        onMsgListener.msg("Turn right");
                    }

                    mCsjBot.getAction().actionNew(1, 2, 30, 3);
                    mCsjBot.getAction().actionNew(2, 2, 45, 3);

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCsjBot.getAction().actionNew(4, 2, 50, 3);
                }
            }
        }).start();
    }

    public volatile OnMsgListener onMsgListener;

    public void setOnMsgListener(OnMsgListener listener) {
        onMsgListener = listener;
    }

    public interface OnMsgListener {
        void msg(String msg);
    }
}
