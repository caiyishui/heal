package com.csjbot.coshandler.tts;

import com.csjbot.coshandler.listener.OnSpeakListener;

import java.util.List;
import java.util.Locale;

/**
 * 语音合成提供类接口
 * Created by jingwc on 2017/9/12.
 */

public interface ISpeechSpeak {
    /**
     * 开始说话
     *
     * @param text
     * @param listener
     */
    void startSpeaking(String text, OnSpeakListener listener);

    /**
     * 停止说话
     */
    void stopSpeaking();

    /**
     * 暂停说话
     */
    void pauseSpeaking();

    /**
     * 重新说话
     */
    void resumeSpeaking();

    void resumeSpeaking(OnSpeakListener listener);

    /**
     * 是否正在说话
     */
    boolean isSpeaking();


    /**
     * 设置发声人名字
     *
     * @param name 发声人名字
     * @return 如果返回true，则设置成功
     */
    boolean setSpeakerName(String name);

    /**
     * 设置语言
     *
     * @param language 语言（包含语言和国家（地区））
     * @return 如果返回true，则设置成功
     */
    boolean setLanguage(int language);

    boolean setLanguage(Locale language);


    /**
     * 获取支持的发声人列表
     *
     * @return 发声人列表，可能为null
     */
    List<String> getSpeakerNames(String language, String country);

    void setVolume(float volume);

    void setSpeed(int speed);
}
