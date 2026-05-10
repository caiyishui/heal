package com.csjbot.coshandler.listener;

/**
 * Created by jingwc on 2017/9/5.
 */

public interface OnSpeechListener {
    /**
     * 接受语音识别结果
     * @param json
     * @param type (0:语音识别结果,1:语音识别结果加上语义结果)
     */
    void speechInfo(String json,int type);
}
