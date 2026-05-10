package com.csjbot.coshandler.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

/**
 * Created by xiasuhuei321 on 2017/10/27.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class ShellUtil {
    public static final String TAG = "ShellUtil";
    public static CommandResult execCmd(String command, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if (TextUtils.isEmpty(command)) {
            return new CommandResult(result, null, "输入有误");
        }

        Process process = null;
        BufferedReader successResult = null;
        BufferedReader errorResult = null;
        StringBuilder successMsg = null;
        StringBuilder errorMsg = null;
        DataOutputStream os = null;

        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            os.write(command.getBytes());
            os.writeBytes("\n");
            os.writeBytes("exit\n");
            os.flush();

            result = process.waitFor();
            if (isNeedResultMsg) {
                successMsg = new StringBuilder();
                errorMsg = new StringBuilder();
                successResult = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

                String s;
                while ((s = successResult.readLine()) != null) {
                    successMsg.append(s);
                }

                while ((s = errorResult.readLine()) != null) {
                    errorMsg.append(s);
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "这段代码运行了");
            e.printStackTrace();
        } finally {
            IOCloseUtil.closeIO(os, successResult, errorResult);
            if (process != null) process.destroy();
        }
        return new CommandResult(result,
                successMsg == null ? null : successMsg.toString(),
                errorMsg == null ? null : errorMsg.toString());
    }
}
