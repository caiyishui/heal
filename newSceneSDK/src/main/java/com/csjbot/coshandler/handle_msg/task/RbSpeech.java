package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.Speech;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;
import com.csjbot.coshandler.log.CsjlogProxy;

/**
 * 负责语音模块的消息的处理
 * Created by jingwc on 2017/8/12.
 */

public class RbSpeech extends RbBase {


    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        switch (msgId) {
            case NTFConstants.SPEECH_ISR_LAST_RESULT_NTF:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_ISR_LAST_RESULT_NTF");
                CsjRobot.getInstance().pushSpeech(dataSource, Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT);
                break;

            case NTFConstants.SPEECH_ISR_ONLY_RESULT_NTF:
                CsjRobot.getInstance().pushSpeech(dataSource, Speech.SPEECH_RECOGNITION_RESULT);
                break;
            case NTFConstants.SPEECH_READ_OVER_NTF:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_READ_OVER_NTF:");
                break;
            case NTFConstants.SPEECH_ISR_WAKEUP_NTF:
                CsjRobot.getInstance().pushWakeup(getIntSingleField(dataSource, "angle"));

                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_ISR_WAKEUP_NTF:");
                CsjlogProxy.getInstance().debug("SPEECH_ISR_WAKEUP_NTF");
                break;
            case NTFConstants.SPEECH_ISR_SLEEP_NTF:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_ISR_SLEEP_NTF:");
                break;
            case NTFConstants.SPEECH_AIUI_ERROR_NTF:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_AIUI_ERROR_NTF:");
                break;
            case NTFConstants.SPEECH_CTRL_LAST_RESULT_NTF:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->msgId:SPEECH_CTRL_LAST_RESULT_NTF:");
                break;
            case NTFConstants.SPEECH_ISR_ERROR_NTF:
//                CsjlogProxy.getInstance().debug("SPEECH_ISR_ERROR_NTF");
                break;
            // puyz add 2018年3月24日
            case NTFConstants.SPEECH_READ_STOP_NTF:
                break;
            case NTFConstants.SPEECH_TTS_NTF:
                //人工客服端发送的文本
                CsjRobot.getInstance().pushSpeech(dataSource, 2);
                break;
            // puyz add 2018年3月24日
            default:
                CosLogger.debug("RbSpeech-->handleNTFMessage-->DEFAULT:");
                break;
        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        switch (msgId) {
            case RSPConstants.SPEECH_SERVICE_START_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_SERVICE_START_RSP");
                break;
            case RSPConstants.SPEECH_SERVICE_STOP_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_SERVICE_STOP_RSP");
                break;
            case RSPConstants.SPEECH_ISR_START_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_ISR_START_RSP");
                break;
            case RSPConstants.SPEECH_ISR_STOP_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_ISR_STOP_RSP");
                break;
            case RSPConstants.SPEECH_ISR_ONCE_START_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_ISR_ONCE_START_RSP");
                break;
            case RSPConstants.SPEECH_ISR_ONCE_STOP_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_ISR_ONCE_STOP_RSP");
                break;
            case RSPConstants.SPEECH_TTS_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_TTS_RSP");
                break;
            case RSPConstants.SPEECH_READ_STOP_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_READ_STOP_RSP");
                break;
            case RSPConstants.SPEECH_LOAD_CMD_RSP:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->msgId:SPEECH_LOAD_CMD_RSP");
                break;
            case RSPConstants.SPEECH_GET_USERWORDS_RSP:
                CsjRobot.getInstance().pushHot(getStringListField(dataSource, "words"));
                break;
            default:
                CosLogger.debug("RbSpeech-->handleRSPMessage-->DEFAULT:");
                break;
        }
    }
}
