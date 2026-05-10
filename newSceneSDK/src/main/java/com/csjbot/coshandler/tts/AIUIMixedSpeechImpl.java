package com.csjbot.coshandler.tts;

import android.content.Context;

import com.csjbot.asragent.aiui_soft.AIUIMixedManager;
import com.csjbot.asragent.aiui_soft.listener.OnAIUISpeakListener;
import com.csjbot.coshandler.listener.OnSpeakListener;
import com.csjbot.coshandler.log.CsjlogProxy;

import java.util.List;
import java.util.Locale;

public class AIUIMixedSpeechImpl implements ISpeechSpeak {
    private Context mContext;

    public static AIUIMixedSpeechImpl newInstance(Context ctx) {
        return new AIUIMixedSpeechImpl(ctx);
    }

    private AIUIMixedSpeechImpl(Context ctx) {
        mContext = ctx;
    }

    @Override
    public void startSpeaking(String text, final OnSpeakListener listener) {
        AIUIMixedManager.getInstance().startSpeak(text, new OnAIUISpeakListener() {
            @Override
            public void onSpeakBegin() {
                if (listener != null) {
                    listener.onSpeakBegin();
                }
            }

            @Override
            public void onCompleted(int ISpeechError) {
                if (listener != null) {
                    listener.onCompleted(null);
                }
            }
        });
    }

    @Override
    public void stopSpeaking() {
        AIUIMixedManager.getInstance().stopSpeaking();
    }

    @Override
    public void pauseSpeaking() {
        AIUIMixedManager.getInstance().pauseSpeaking();
    }

    @Override
    public void resumeSpeaking() {
        AIUIMixedManager.getInstance().resumeSpeaking(null);
    }

    @Override
    public void resumeSpeaking(final OnSpeakListener listener) {
        AIUIMixedManager.getInstance().resumeSpeaking(new OnAIUISpeakListener() {
            @Override
            public void onSpeakBegin() {
                listener.onSpeakBegin();
            }

            @Override
            public void onCompleted(int ISpeechError) {
                listener.onCompleted(null);
            }
        });
    }

    @Override
    public boolean isSpeaking() {
        return AIUIMixedManager.getInstance().isSpeaking();
    }

    @Override
    public boolean setSpeakerName(String name) {
        AIUIMixedManager aiuiMixedManager = AIUIMixedManager.getInstance();
        if (aiuiMixedManager != null) {
            aiuiMixedManager.setSpeakerName(name);
        }
        return true;
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
    public List<String> getSpeakerNames(String language, String country) {
        return null;
    }

    @Override
    public void setVolume(float volume) {
        CsjlogProxy.getInstance().info("setVolume  = " + volume);
        AIUIMixedManager aiuiMixedManager = AIUIMixedManager.getInstance();
        if (aiuiMixedManager != null) {
            aiuiMixedManager.setVolume(volume);
        }
    }

    @Override
    public void setSpeed(int speed) {
        AIUIMixedManager aiuiMixedManager = AIUIMixedManager.getInstance();
        if (aiuiMixedManager != null) {
            aiuiMixedManager.setSpeed(speed);
        }
    }
}
