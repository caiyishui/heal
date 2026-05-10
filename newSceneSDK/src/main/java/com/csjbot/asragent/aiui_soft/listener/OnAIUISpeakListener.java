package com.csjbot.asragent.aiui_soft.listener;



/**
 * Created by jingwc on 2017/9/12.
 */

public interface OnAIUISpeakListener {

    /**
     *  说话之前
     */
    void onSpeakBegin();

    /**
     *  说话完成
     */
    void onCompleted(int ISpeechError);
}
