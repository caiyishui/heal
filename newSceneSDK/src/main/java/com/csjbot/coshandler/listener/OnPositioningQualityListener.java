package com.csjbot.coshandler.listener;

/**
 * 定位质量
 */
public interface OnPositioningQualityListener {

    void lQStateLow(int lq);

    void lQStateNormal(int lq);
}
