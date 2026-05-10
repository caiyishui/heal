package com.csjbot.asragent.aiui_soft.util;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;


import com.csjbot.coshandler.log.CosLogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class AudioPlayer {
    private final AudioTrack audioTrack;
    private static final int SAMPLE_RATE = 16000;
    private final BlockingQueue<byte[]> pcmDataList = new LinkedBlockingDeque<>();
    private boolean isRunning = false;

    public AudioPlayer() {
        this(SAMPLE_RATE, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        ExecutorService audioPool = Executors.newSingleThreadExecutor();
        isRunning = true;
        Runnable audioPlayRunnable = new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try {
                        byte[] audio = pcmDataList.take();
                        audioTrack.write(audio, 0, audio.length);
                        audioTrack.play();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        audioPool.execute(audioPlayRunnable);
    }

    private AudioPlayer(int sampleRate, int channelConfig, int audioFormat) {
        CosLogger.debug("AudioPlayer constructor");
        //每秒16K个点 
        //单声道
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE,//每秒16K个点 
                channelConfig,//单声道
                audioFormat);//一个采样点16比特-2个字节

        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, channelConfig, audioFormat, bufferSize, AudioTrack.MODE_STREAM);
        audioTrack.setPlaybackRate(sampleRate);
    }

    public void play(byte[] pcmData) {
        CosLogger.debug("play() called");
        pcmDataList.add(pcmData);
    }

    public void stop() {
        CosLogger.debug("stop() called");
        isRunning = false;
        audioTrack.stop();
        audioTrack.release();
    }
}