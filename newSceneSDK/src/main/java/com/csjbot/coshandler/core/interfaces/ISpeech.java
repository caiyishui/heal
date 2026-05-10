package com.csjbot.coshandler.core.interfaces;

import android.content.Context;

import com.csjbot.coshandler.tts.ISpeechSpeak;

/**
 * Created by jingwc on 2017/9/5.
 */
public interface ISpeech extends ISpeechSpeak {
    /**
     * Init speak.
     *
     * @param context    the context
     * @param speechType the speech type
     */
    void initSpeak(Context context, int speechType);
}
