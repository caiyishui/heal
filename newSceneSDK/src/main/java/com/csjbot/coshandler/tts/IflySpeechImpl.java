package com.csjbot.coshandler.tts;

import android.content.Context;

import com.csjbot.coshandler.listener.OnSpeakListener;

import java.util.ArrayList;
import java.util.Locale;

/**
 * 讯飞语音合成具体实现类
 * Created by jingwc on 2017/9/12.
 */

public class IflySpeechImpl implements ISpeechSpeak {

    private IflySpeechImpl(){

    }

    /**
     * 创建实例
     *
     * @param context
     * @return
     */
    public static IflySpeechImpl newInstance(Context context) {
        return new IflySpeechImpl();
    }

    @Override
    public void startSpeaking(String text, OnSpeakListener listener) {

    }

    @Override
    public void stopSpeaking() {

    }

    @Override
    public void pauseSpeaking() {

    }

    @Override
    public void resumeSpeaking() {

    }

    @Override
    public void resumeSpeaking(OnSpeakListener listener) {

    }

    @Override
    public boolean isSpeaking() {
        return false;
    }

    @Override
    public boolean setSpeakerName(String name) {
        return false;
    }

    @Override
    public boolean setLanguage(int language) {
        return false;
    }

    @Override
    public boolean setLanguage(Locale language) {
        return false;
    }

    @Override
    public ArrayList<String> getSpeakerNames(String language, String country) {
        return null;
    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public void setSpeed(int speed) {

    }
}
