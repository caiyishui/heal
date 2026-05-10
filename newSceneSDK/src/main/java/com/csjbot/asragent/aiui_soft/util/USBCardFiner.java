package com.csjbot.asragent.aiui_soft.util;


import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Android运行linux命令
 */
public final class USBCardFiner {
    private static final String TAG = "USBCardFiner";
    private static boolean mHaveRoot = false;
    private static int cardNum = 0;

    public interface SoundCardNameCheck {
        boolean checkName(String name);
    }

    /**
     * 判断机器Android是否已经root，即是否获取root权限
     */
    public static boolean haveRoot() {
        if (!mHaveRoot) {
            int ret = execRootCmdSilent("echo test"); // 通过执行测试命令来检测
            if (ret != -1) {
                Log.i(TAG, "have root!");
                mHaveRoot = true;
            } else {
                Log.i(TAG, "not root!");
            }
        } else {
            Log.i(TAG, "mHaveRoot = true, have root!");
        }
        return mHaveRoot;
    }

    public static int fetchCards(int card, SoundCardNameCheck check) {
        cardNum = card;
        if (cardNum == -1) {
            cardNum = execRootCmd("cat /proc/asound/cards", check);
        }

        if (cardNum == -1) {
            cardNum = execRootCmd("cat /proc/asound/cards", check);
        }
//        if (cardNum == -1) {
//            // 865
//            cardNum = execUbiotCmd("cat /proc/asound/cards", check);
//        }
        execRootCmdSilent("setenforce 0");
        execRootCmdSilent("chmod 777 /dev/snd/pcmC" + cardNum + "D0c");
        return cardNum;
    }

    public static void execShellCmd(String cmd) {
        //865需要使用ubiot
        String auth = "ubiot";
        try {
            Process process = Runtime.getRuntime().exec(auth);
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e(TAG, "RootShell-execShellCmd-Error:" + e.getMessage());
        }
    }

    public static int execUbiotCmd(String cmd, SoundCardNameCheck check) {
        int cardN = -1;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            Process p = Runtime.getRuntime().exec("ubiot");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            Log.i(TAG, cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            //while ((line = dis.readUTF()) != null) {
//            (line.contains("Bothlent UAC Dongle") || line.contains("HSWL Microphone"))
            while ((line = dis.readLine()) != null) {
                if (line != null && check.checkName(line)) {
                    Log.d(TAG, "Find USB card:" + line);
                    line = line.replace('[', ',');
                    line = line.replace(']', ',');
                    Log.d(TAG, "Find USB card parse:" + line);
                    String[] strs = line.split(",");
                    if (strs.length > 0) {
                        String numStr = strs[0].trim();
                        cardN = Integer.parseInt(numStr);
                    }
                    Log.d(TAG, "USB card Number=" + cardN);
                    break;
                }
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cardN;
    }

    /**
     * 执行命令并且输出结果
     */
    public static int execRootCmd(String cmd, SoundCardNameCheck check) {
        int cardN = -1;
        DataOutputStream dos = null;
        DataInputStream dis = null;
        try {
            Process p = Runtime.getRuntime().exec(cmd + "\n");// 经过Root处理的android系统即有su命令
            dos = new DataOutputStream(p.getOutputStream());
            dis = new DataInputStream(p.getInputStream());

            Log.i(TAG, cmd);
            dos.writeBytes("exit\n");
            dos.flush();
            String line = null;
            //while ((line = dis.readUTF()) != null) {
//            (line.contains(" Bothlent UAC Dongle") || line.contains("HSWL Microphone"))
            while ((line = dis.readLine()) != null) {
                if (line != null && check.checkName(line)) {
                    Log.d(TAG, "Find USB card:" + line);
                    line = line.replace('[', ',');
                    line = line.replace(']', ',');
                    Log.d(TAG, "Find USB card parse:" + line);
                    String[] strs = line.split(",");
                    if (strs.length > 0) {
                        String numStr = strs[0].trim();
                        cardN = Integer.parseInt(numStr);
                    }
                    Log.d(TAG, "USB card Number=" + cardN);
                    break;
                }
            }
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dis != null) {
                try {
                    dis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cardN;
    }

    /**
     * 执行命令但不关注结果输出
     */
    public static int execRootCmdSilent(String cmd) {
        int result = -1;
        DataOutputStream dos = null;

        try {
            Process p = Runtime.getRuntime().exec("su");
            dos = new DataOutputStream(p.getOutputStream());

            Log.i(TAG, cmd);
            dos.writeBytes(cmd + "\n");
            dos.flush();
            dos.writeBytes("exit\n");
            dos.flush();
            p.waitFor();
            result = p.exitValue();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
}
