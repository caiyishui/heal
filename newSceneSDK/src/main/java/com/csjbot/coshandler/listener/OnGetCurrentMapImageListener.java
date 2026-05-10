package com.csjbot.coshandler.listener;

import android.graphics.Bitmap;

/**
 * @author cll
 * @date :2022/7/8
 */
public interface OnGetCurrentMapImageListener {

    void onCurrentMapBitmap(String currentMapName, String base64, int error);
}
