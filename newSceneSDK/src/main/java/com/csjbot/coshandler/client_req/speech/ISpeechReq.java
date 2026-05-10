package com.csjbot.coshandler.client_req.speech;


import com.csjbot.coshandler.client_req.base.IClientReq;
import com.csjbot.coshandler.listener.OnSpeechGetResultListener;

/**
 * 语音接口
 * Created by jingwc on 2017/8/14.
 */

public interface ISpeechReq extends IClientReq {

    /**
     * 开启讯飞语音服务
     */
    void startSpeechService();

    /**
     * 关闭讯飞语音服务
     */
    void closeSpeechService();

    /**
     * 开启多次识别
     */
    void startIsr();

    /**
     * 停止多次识别
     */
    void stopIsr();

    /**
     * 开始单次识别
     */
    void startOnceIsr();

    /**
     * 停止单次识别
     */
    void stopOnceIsr();

    /**
     * 唤醒机器人麦克风
     */
    void openMicro();

    /**
     * 说话
     */
    void speak(String content);

    /**
     * 停止说话
     */
    void stopSpeak();

    /**
     * 设置识别语音类型
     * @param language
     */
    void setLanguage(String language);

    /**
     * 设置识别语音类型
     * @param language
     */
    void setLanguageAndAccent(String language,String accent);

    /**
     * 手动获取语义结果
     * @param text
     */
    void getResult(String text, OnSpeechGetResultListener listener);
}
