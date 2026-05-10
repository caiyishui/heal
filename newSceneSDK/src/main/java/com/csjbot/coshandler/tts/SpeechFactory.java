package com.csjbot.coshandler.tts;

import android.content.Context;

import com.csjbot.coshandler.log.CosLogger;

/**
 * 语音合成工厂类
 * Created by jingwc on 2017/9/12.
 */

public class SpeechFactory {
    private static ISpeechSpeak speechSpeak = null;

    /**
     * 获取一个语音合成提供类
     *
     * @param context
     * @param type
     * @return
     */
    public static ISpeechSpeak createSpeech(Context context, int type) {
        switch (type) {
            case SpeechType.GOOGLE:
                speechSpeak = getGoogleSpeech(context);
                break;
            case SpeechType.BAKER:
                speechSpeak = getBakerSpeech(context);
                break;
            case SpeechType.AIUI_R16:
            case SpeechType.AIUI_ALL:
            case SpeechType.AIUI:
                speechSpeak = getAIUIMixedSpeech(context);
                break;
            default:
            case SpeechType.IFLY:
//                speechSpeak = getAidlSpeech(context, type);
                break;
        }
        return speechSpeak;
    }


    public void setSpeakerName(String speakerName) {
        speechSpeak.setSpeakerName(speakerName);
    }


    /**
     * 获取讯飞语音合成实现
     *
     * @param context
     * @return
     */
    private static ISpeechSpeak getIflySpeech(Context context) {
        return IflySpeechImpl.newInstance(context);
    }

    private static ISpeechSpeak getBakerSpeech(Context context) {
        return BakerImpl.newInstance(context);
    }

    private static ISpeechSpeak getGoogleSpeech(Context context) {
        return GoogleSpechImpl.newInstance(context);
    }

    private static ISpeechSpeak getAIUIMixedSpeech(Context context) {
        return AIUIMixedSpeechImpl.newInstance(context);
    }
//    private static ISpeechSpeak getAidlSpeech(Context context, int type) {
//        return AidlSpechImpl.newInstance(context, type);
//    }
//
//    private static ISpeechSpeak getYunzhishengSpeech(Context context) {
//        return YunzhishengSpeechImpl.newInstance(context);
//    }


    /**
     * 语音合成标识类(可用于标识使用哪家第三方的语音合成)
     */
    public static final class SpeechType {

        /* 科大讯飞 */
        public static final int IFLY = 0;

        /* 日语 */
        public static final int JAPAN = 1;

        /* 日语 小雪 */
        public static final int JAPAN_SNOW = 2;

        /* Google tts */
        public static final int GOOGLE = 3;

        /* AIUI 软核 */
        public static final int AIUI = 4;

        /* 标贝 */
        public static final int BAKER = 5;

        /* AIUI 硬核 */
        public static final int AIUI_R16 = 6;

        /* AIUI 硬核 */
        public static final int AIUI_ALL = 8;
    }
}
