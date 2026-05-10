package com.csjbot.coshandler.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.log.Csjlogger;
import com.csjbot.coshandler.util.ShellUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by xiasuhuei321 on 2017/11/11.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class CheckEthernetService extends IntentService {
    public CheckEthernetService() {
        super("CheckEthernetService");
    }

    /**
     * 打开以太网命令
     */
    private static final String OPEN = "ifconfig eth0 up " + "\n"
            + "exit\n";
    /**
     * 关闭以太网命令
     */
    private static final String CLOSE = "ifconfig eth0 down " + "\n"
            + "exit\n";

    /**
     * 获取以太网IP地址命令
     */
    private static final String ETHERNET_IP = "ifconfig eth0";

    /**
     * 间隔时间(15秒)
     */
    private static final int TIME = 15000;


    /**
     * 是否连接
     */
    private boolean isConnected = false;

    private String checkString = "192.168.99";

    @Override
    protected void onHandleIntent(Intent intent) {

      
        while (!(getEthernetIP() != null && getEthernetIP().contains(checkString))) {
            isConnected = CsjRobot.getInstance().getState().isConnect();
            if (isConnected) {
                break;
            }
            upDownEht0(CLOSE);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            upDownEht0(OPEN);

            try {
                Thread.sleep(TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void upDownEht0(String cmd) {
//        try {
//            Process su;
//            su = Runtime.getRuntime().exec("/system/bin/su");
//            su.getOutputStream().write(cmd.getBytes());
//            int status = su.waitFor();
//            if ((status != 0)) {
//                Csjlogger.error("status = {}", status);
//            }
//        } catch (Exception e) {
//            Csjlogger.error(e);
//        }

        ShellUtil.execCmd(cmd, true, false);
    }


    /**
     * 获取以太网ip地址
     *
     * @return ip地址
     */
    private String getEthernetIP() {
        InputStream is = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            Process process = Runtime.getRuntime().exec(ETHERNET_IP);
            is = process.getInputStream();
            inputStreamReader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = "";
            StringBuilder sb = new StringBuilder(line);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            int status = process.waitFor();
            if (status != 0) {
                Csjlogger.error("status is {}" + status);
            }
//            Csjlogger.debug("===EthernetIP====" + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            Csjlogger.error(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
