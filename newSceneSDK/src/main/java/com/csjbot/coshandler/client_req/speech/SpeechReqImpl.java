package com.csjbot.coshandler.client_req.speech;


import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnSpeechGetResultListener;

/**
 * 语音实现类
 * Created by jingwc on 2017/8/14.
 */

public class SpeechReqImpl extends BaseClientReq implements ISpeechReq {
    @Override
    public void startSpeechService() {
        sendReq(getJson(REQConstants.SPEECH_SERVICE_START_REQ));
    }

    @Override
    public void closeSpeechService() {
        sendReq(getJson(REQConstants.SPEECH_SERVICE_STOP_REQ));
    }

    @Override
    public void startIsr() {
        sendReq(getJson(REQConstants.SPEECH_ISR_START_REQ));
    }

    @Override
    public void stopIsr() {
        sendReq(getJson(REQConstants.SPEECH_ISR_STOP_REQ));
    }

    @Override
    public void startOnceIsr() {
        sendReq(getJson(REQConstants.SPEECH_ISR_ONCE_START_REQ));
    }

    @Override
    public void stopOnceIsr() {
        sendReq(getJson(REQConstants.SPEECH_ISR_ONCE_STOP_REQ));
    }

    @Override
    public void speak(String content) {
        sendReq(getJson(REQConstants.SPEECH_TTS_REQ,"content",content));
    }

    @Override
    public void stopSpeak() {
        sendReq(getJson(REQConstants.SPEECH_READ_STOP_REQ));
    }

    @Override
    public void setLanguage(String language) {
        sendReq(getJson(CmdConstants.SPEECH_SET_ISR_PARAM_CMD,"local_type",language));
    }

    @Override
    public void setLanguageAndAccent(String language,String accent){
        String json = "{\"msg_id\":\"" + CmdConstants.SPEECH_SET_ISR_PARAM_CMD + "\",\"local_type\":\""+language+"\",\"accent\":\""+accent+"\"}";
        sendReq(json);
    }

    @Override
    public void getResult(String text, OnSpeechGetResultListener listener) {
        CsjRobot.getInstance().setOnSpeechGetResultListener(listener);
        sendReq(getJson(REQConstants.CUSTSERVICE_GET_RESULT_REQ,"text",text));
    }

    @Override
    public void openMicro() {
        sendReq(getJson(REQConstants.SPEECH_ISR_MICRO_REQ));
    }
}
