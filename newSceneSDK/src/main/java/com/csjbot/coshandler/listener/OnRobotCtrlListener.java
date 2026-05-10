package com.csjbot.coshandler.listener;

/**
 * web端控制机器人导览，上一个点  下一个点   暂停等指令监听
 * @author cll
 * @date :2022/1/4
 */
public interface OnRobotCtrlListener {
    /**
     * 0  暂停任务
     * 1  继续任务
     * 2  上一个
     * 3  下一个
     * 4  取消任务
     * 5  禁止识别
     * 6  开始识别
     * 7  返回迎宾
     * 8  开始导览
     * @param action
     */
    void onCtrlAction(int action);
}
