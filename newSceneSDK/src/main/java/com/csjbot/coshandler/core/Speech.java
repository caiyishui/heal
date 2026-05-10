package com.csjbot.coshandler.core;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.speech.ISpeechReq;
import com.csjbot.coshandler.listener.OnSpeechGetResultListener;

/**
 * Created by Administrator on 2019/7/15.
 */

public class Speech implements ISpeechReq {

    public static final int SPEECH_RECOGNITION_RESULT = 0;

    public static final int SPEECH_RECOGNITION_AND_ANSWER_RESULT = 1;

    private ISpeechReq speechReq;

    Speech(){
        speechReq = ReqFactory.getSpeechReqInstance();
    }

    @Override
    public void startSpeechService() {
        speechReq.startSpeechService();
    }

    @Override
    public void closeSpeechService() {
        speechReq.closeSpeechService();
    }

    @Override
    public void startIsr() {
        speechReq.startIsr();
    }

    @Override
    public void stopIsr() {
        speechReq.stopIsr();
    }

    @Override
    public void startOnceIsr() {
        speechReq.startOnceIsr();
    }

    @Override
    public void stopOnceIsr() {
        speechReq.stopOnceIsr();
    }

    @Override
    public void openMicro() {
        speechReq.openMicro();
    }

    @Override
    public void speak(String content) {
        speechReq.speak(content);
    }

    @Override
    public void stopSpeak() {
        speechReq.stopSpeak();
    }

    @Override
    public void setLanguage(String language) {
        speechReq.setLanguage(language);
    }

    @Override
    public void setLanguageAndAccent(String language, String accent) {
        speechReq.setLanguageAndAccent(language,accent);
    }

    @Override
    public void getResult(String text,OnSpeechGetResultListener listener) {
        speechReq.getResult(text,listener);
    }
}
