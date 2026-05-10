package com.csjbot.coshandler.listener;


/**
 * Created by jingwc on 2017/9/12.
 */

public interface OnSpeakListener {

    /**
     *  说话之前
     */
    void onSpeakBegin();

    /**
     *  说话完成
     */
    void onCompleted(SpeechError speechError);
}
