package com.csjbot.coshandler.core.interfaces;

public interface DirectListener {
    void onRecMessage(String json);
    void onSendMessage(String json);
}
