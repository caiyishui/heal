package com.csjbot.asragent.aiui_soft.cae;

public interface OnCaeOperatorlistener {
    void onAudio(byte[] audioData, int dataLen);

    void onWakeup(int angle, int beam);
}
