package com.csjbot.coshandler.tts;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.speech.tts.Voice;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;



import com.csjbot.coshandler.listener.OnSpeakListener;
import com.csjbot.coshandler.log.CsjlogProxy;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2018, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2018/08/06 0006-17:48.
 * Email: puyz@csjbot.com
 */

public class GoogleSpechImpl implements ISpeechSpeak {
    private Locale currentLanguage = Locale.getDefault();
    private static final Locale DEFAULT_LANGUAGE = Locale.US;
    private static final String TAG = "GoogleSpechImpl";

    private TextToSpeech mTts;
    private static final String TTS_PACKAGE_NAME = "com.google.android.tts";


    public static final String TTS_LANGUAGE_NAME = "TTS_LANGUAGE_NAME";
    public static final String TTS_COUNTRY_NAME = "TTS_COUNTRY_NAME";
    public static final String TTS_VOICE_NAME = "TTS_VOICE_NAME";

    public ExecutorService executorService = Executors.newCachedThreadPool();


    private Handler handler;
    private long lastTimestamp;

    private float volume = 1.0f;


    public static GoogleSpechImpl newInstance(Context context) {
        return new GoogleSpechImpl(context);
    }


    /**
     * 私有有参构造
     *
     * @param ctx
     */
    private GoogleSpechImpl(Context ctx) {

        handler = new Handler(Looper.myLooper());

        WeakReference<Context> mContext = new WeakReference<>(ctx.getApplicationContext());

        mTts = new TextToSpeech(mContext.get(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                Log.e(TAG, "onInit status" + status);
                if (status == 0) {
                    mTts.setLanguage(Locale.US);
                }
            }
        }, TTS_PACKAGE_NAME);
    }


    @Override
    public boolean setLanguage(int language) {
        return false;
    }

    /**
     * 设置语言
     *
     * @param language 语言（包含语言和国家（地区））
     * @return 如果返回true，则设置成功
     */

    @Override
    public boolean setLanguage(Locale language) {
        int ret = mTts.setLanguage(language);
//        int ret = mTts.setLanguage(new Locale("en","IN"));
        boolean retBoolean = false;

        if (ret != TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e(TAG, "setLanguage LANG_NOT_SUPPORTED  set to  US");
            currentLanguage = language;
            retBoolean = true;
        } else {
//            mTts.setLanguage(DEFAULT_LANGUAGE);
            mTts.setLanguage(new Locale("en","IN"));
        }

        switch (ret) {
            case TextToSpeech.LANG_AVAILABLE:
                Log.e(TAG, "ret of " + language.toString() + " is  " + "TextToSpeech.LANG_AVAILABLE");
                break;
            case TextToSpeech.LANG_COUNTRY_AVAILABLE:
                Log.e(TAG, "ret of " + language.toString() + " is  " + "TextToSpeech.LANG_COUNTRY_AVAILABLE");
                break;
            case TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE:
                Log.e(TAG, "ret of " + language.toString() + " is  " + "TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE");
                break;
            case TextToSpeech.LANG_MISSING_DATA:
                Log.e(TAG, "ret of " + language.toString() + " is  " + "TextToSpeech.LANG_MISSING_DATA");
                break;
            case TextToSpeech.LANG_NOT_SUPPORTED:
                Log.e(TAG, "ret of " + language.toString() + " is  " + "TextToSpeech.LANG_NOT_SUPPORTED");
                break;
            default:
                break;
        }

        return retBoolean;
    }

    /**
     * 获取支持的发声人列表
     *
     * @return 发声人列表，可能为null
     */
    @Override
    public ArrayList<String> getSpeakerNames(@NonNull String language, String country) {
        Set<Voice> voiceSet = mTts.getVoices();
        ArrayList<String> voiceList = new ArrayList<>();

        for (Voice voice : voiceSet) {
            // 如果 language =  getLocale().getLanguage()，并且 country 为空，就添加到list
            // 如果 language =  getLocale().getLanguage()，并且 country 不为空 再判断 country
            if (voice.getLocale().getLanguage().equalsIgnoreCase(language)
                    && voice.getName().contains("local")
                    && !voice.isNetworkConnectionRequired()) {
                if (!TextUtils.isEmpty(country)) {
                    if (voice.getLocale().getCountry().equalsIgnoreCase(country)) {
                        voiceList.add(voice.getName());
                    }
                } else {
                    voiceList.add(voice.getName());
                }
            }
        }

        return voiceList;
    }

    @Override
    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public void setSpeed(int speed) {
    }


    private String utteranceID;
    private String lastUtteranceID;

    @Override
    public void startSpeaking(final String text, final OnSpeakListener listener) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> myHashAlarm = null;
                if (listener != null) {
                    utteranceID = "";
                    myHashAlarm = new HashMap<>();
                    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(System.currentTimeMillis()));
                    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, String.valueOf(volume));
                    mTts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                        @Override
                        public void onStart(String s) {
                             CsjlogProxy.getInstance().debug("google speech id onStart: " + s);
                            utteranceID = s;
                            lastUtteranceID = s;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.onSpeakBegin();
                                }
                            });
                        }

                        @Override
                        public void onDone(final String s) {

                            if (TextUtils.equals(lastUtteranceID, utteranceID)) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        listener.onCompleted(null);
                                        CsjlogProxy.getInstance().debug("google speech id  onDone: " + s);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onError(String s) {
                             CsjlogProxy.getInstance().debug("google onError: " + s.toString());
                        }
                    });
                } else {
                    myHashAlarm = new HashMap<>();
                    myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_VOLUME, String.valueOf(volume));
                }

                mTts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
            }
        });
    }

    @Override
    public void stopSpeaking() {
        lastUtteranceID = "";
        mTts.stop();
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
        return mTts.isSpeaking();
    }

    /**
     * 设置发声人名字
     *
     * @param name 发声人名字
     * @return 如果返回true，则设置成功
     */
    @Override
    public boolean setSpeakerName(String name) {
        return setVoice(currentLanguage, name) == TextToSpeech.SUCCESS;
    }

    /**
     * 设置发声人
     *
     * @param locale    所属地区（包括语言和国家（地区））
     * @param voiceName 发声人名字
     * @return {@link TextToSpeech#ERROR} or {@link TextToSpeech#SUCCESS}.
     */
    private int setVoice(Locale locale, String voiceName) {
        Voice voice = new Voice(voiceName, locale, Voice.QUALITY_HIGH,
                Voice.LATENCY_LOW, false, null);

        int ret = mTts.setVoice(voice);
        Log.e(TAG, "setVoice = " + String.valueOf(ret));

        return ret;
    }
}
