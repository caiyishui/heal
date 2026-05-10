package com.demo.csjbot.csjsdkdemo.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/7/23 0023.
 */
public class CSJToast {
    private static Toast mToast;
    private static Handler mHandler = new Handler();

    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;
        }
    };

    public static void showToast(Context mContext, String text, int duration) {
        mHandler.removeCallbacks(r);
        if (mToast != null) {
            mToast.setText(text);
        } else {
            mToast = Toast.makeText(mContext, text, Toast.LENGTH_LONG);
        }
        mHandler.postDelayed(r, duration);

        mToast.show();
    }

    public static void showToast(Context mContext, String text) {
        showToast(mContext, text, 2000);
    }

    public static void showToast(Context mContext, int resId, int duration) {
        showToast(mContext, mContext.getResources().getString(resId), duration);
    }
}
