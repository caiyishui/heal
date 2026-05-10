package com.csjbot.coshandler.tts;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.csjbot.coshandler.global.RobotContants;
import com.csjbot.coshandler.listener.OnSpeakListener;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.databaker.synthesizer.BakerConstants;
import com.databaker.synthesizer.BakerMediaCallback;
import com.databaker.synthesizer.BakerSynthesizer;

import java.util.List;
import java.util.Locale;

public class BakerImpl implements ISpeechSpeak {

    private String clientId = RobotContants.BAKER_ID;
    private String clientSecret = RobotContants.BAKER_SECRET;
    private BakerSynthesizer bakerSynthesizer;
    private OnSpeakListener listener;
    private Handler handler = new Handler(Looper.getMainLooper());

    //发声人
//    public static String[] voiceSpeakers = new String[]{
//            "新闻合成_蓉蓉",
//            "特色合成_台湾女声_小美",
//            "智能客服_静静cc",
//            "标准合成_甜美女声_楠楠",
//            "标准合成_萝莉女声_小菲",
//            "标准合成_邻家女声_娇娇"
//    };

    private Context mContext;

    public static BakerImpl newInstance(Context ctx) {
        return new BakerImpl(ctx);
    }

    private BakerImpl(Context ctx) {
        mContext = ctx;

        bakerSynthesizer = new BakerSynthesizer(clientId, clientSecret);
        //默认发声人
        optionalInitBaker();
        CsjlogProxy.getInstance().info("BakerImpl: " + bakerSynthesizer);
    }


    public void setClientIdAndClientSecret(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        if (bakerSynthesizer != null) {
            bakerSynthesizer.onDestroy();
            bakerSynthesizer = null;
        }
        bakerSynthesizer = new BakerSynthesizer(clientId, clientSecret);
    }

    /**
     * 设置相关参数
     */
    private void setParams(String text) {
        if (bakerSynthesizer == null) {
            return;
        }
        /**********************以下是必填参数**************************/
        //私有化部署时，必须设置token和url，使用标贝公有云合成不需要设置这2个参数
//        bakerSynthesizer.setTtsToken("default");
//        bakerSynthesizer.setUrl("wss://xxxxx");
        //设置要转为语音的合成文本
        bakerSynthesizer.setText(text);
        //设置返回数据的callback
        bakerSynthesizer.setBakerCallback(bakerMediaCallback);
    }

    private void optionalInitBaker() {
        /**********************以下是选填参数**************************/
        //设置发音人声音名称，默认：标准合成_模仿儿童_果子
        bakerSynthesizer.setVoice(RobotContants.BAKER_SPEAK_VOICE);
        //合成请求文本的语言，目前支持ZH(中文和中英混)和ENG(纯英文，中文部分不会合成)、CAT(粤语),默认：ZH
        bakerSynthesizer.setLanguage(BakerConstants.LANGUAGE_ZH);
        //设置播放的语速，在0～9之间（支持浮点值），不传时默认为5
        bakerSynthesizer.setSpeed(RobotContants.BAKER_SPEAK_SPEED);
        //设置语音的音量，在0～9之间（只支持整型值），不传时默认值为5
        bakerSynthesizer.setVolume(5);
        //设置语音的音调，取值0-9，不传时默认为5中语调
        bakerSynthesizer.setPitch(5);
        /**
         * 可不填，不填时默认为4, 16K采样率的pcm格式
         * audiotype=4 ：返回16K采样率的pcm格式
         * audiotype=5 ：返回8K采样率的pcm格式
         * audiotype=6 ：返回16K采样率的wav格式
         * audiotype=6&rate=1 ：返回8K的wav格式
         */
        bakerSynthesizer.setAudioType(BakerConstants.AUDIO_TYPE_PCM_16K);
    }

    Runnable doTask = new Runnable() {
        @Override
        public void run() {
            if (listener != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       CsjlogProxy.getInstance().debug("超时 onCompleted");


                        listener.onCompleted(null);
                    }
                });
            }
        }
    };

    BakerMediaCallback bakerMediaCallback = new BakerMediaCallback() {

        @Override
        public void onPrepared() {

            if (bakerSynthesizer != null) {
                bakerSynthesizer.bakerPlay();
                if (listener != null) {
                    handler.removeCallbacks(doTask);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onSpeakBegin();
                        }
                    });
                    int assessment = bakerSynthesizer.getDuration() - bakerSynthesizer.getCurrentPosition();
                   CsjlogProxy.getInstance().debug("onPrepared 大概需要时间: " + assessment);
                    handler.postDelayed(doTask, assessment * 1000 + 2500);
                }
            }
        }

        @Override
        public void onCacheAvailable(int percentsAvailable) {
        }

        @Override
        public void onCompletion() {

            if (listener != null) {
                handler.removeCallbacks(doTask);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        CsjlogProxy.getInstance().debug("onCompleted");
                        listener.onCompleted(null);
                    }
                });
            }
        }

        @Override
        public void onError(int errorCode, String errorMsg, String traceId) {
            CsjlogProxy.getInstance().debug("--onError-- errorCode=" + errorCode + ", errorMsg=" + errorMsg);
            if (listener != null) {
                handler.removeCallbacks(doTask);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       CsjlogProxy.getInstance().debug("error onCompleted");
                        listener.onCompleted(null);
                    }
                });
            }
        }

        @Override
        public void playing() {
        }

        @Override
        public void noPlay() {
        }
    };

    @Override
    public void startSpeaking(String text, OnSpeakListener listener) {
        if (bakerSynthesizer != null) {
            this.listener = listener;
            bakerSynthesizer.bakerStop();
            setParams(text);
            bakerSynthesizer.start();
        }
    }

    @Override
    public void stopSpeaking() {
        if (bakerSynthesizer != null) {
            handler.removeCallbacks(doTask);
            bakerSynthesizer.bakerStop();
        }
    }

    @Override
    public void pauseSpeaking() {
        if (bakerSynthesizer != null) {
           CsjlogProxy.getInstance().debug("pauseSpeaking");
            boolean isPlaying = bakerSynthesizer.isPlaying();
            if (isPlaying) {
                handler.removeCallbacks(doTask);
                bakerSynthesizer.bakerPause(); //暂停
            }
        }
    }

    @Override
    public void resumeSpeaking() {
        if (bakerSynthesizer != null) {
           CsjlogProxy.getInstance().debug("resumeSpeaking");
            boolean isPlaying = bakerSynthesizer.isPlaying();
            if (!isPlaying) {
                bakerSynthesizer.bakerPlay(); //继续播放
                int assessment = bakerSynthesizer.getDuration() - bakerSynthesizer.getCurrentPosition();
                handler.postDelayed(doTask, assessment * 1000 + 2500);

            }
        }
    }

    @Override
    public void resumeSpeaking(OnSpeakListener listener) {
        this.listener = listener;
        resumeSpeaking();
    }

    @Override
    public boolean isSpeaking() {
        if (bakerSynthesizer != null) {
            return bakerSynthesizer.isPlaying();
        }
        return false;
    }

    // TODO: 2020/9/1 在线播不完策略

//    public void playDuration(View view) {
//        if (bakerSynthesizer != null) {
//            int currentPosition = bakerSynthesizer.getCurrentPosition();
//            Csjlogger.debug("\n当前播放至：" + currentPosition + "秒");
//        }
//    }
//
//    public void duration(View view) {
//        if (bakerSynthesizer != null) {
//            int duration = bakerSynthesizer.getDuration();
//            Csjlogger.debug("\n音频总长度：" + duration + "秒");
//        }
//    }

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
    public List<String> getSpeakerNames(String language, String country) {
        return null;
    }

    @Override
    public void setVolume(float volume) {
        if (bakerSynthesizer != null) {
            int value = Math.round(volume);
            if (value <= 0) {
                value = 1;
            }

            if (value >= 10) {
                value = 9;
            }

            CsjlogProxy.getInstance().info("setVolume " + value);
            bakerSynthesizer.setVolume(value);
        }
    }

    @Override
    public void setSpeed(int speed) {

    }
}
