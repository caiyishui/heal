package com.csjbot.coshandler.util;

/**
 * Created by xiasuhuei321 on 2017/10/27.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class CommandResult {
    // 结果状态码
    public int result;
    // 成功信息
    public String successMsg;
    // 失败信息
    public String errorMsg;

    public CommandResult(int result, String successMsg, String errorMsg) {
        this.result = result;
        this.successMsg = successMsg;
        this.errorMsg = errorMsg;
    }
}
