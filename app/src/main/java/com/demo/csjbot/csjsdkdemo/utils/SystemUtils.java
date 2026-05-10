package com.demo.csjbot.csjsdkdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import java.util.Locale;

public class SystemUtils {
    /**
     * Get the current system language
     *
     * @return Current system language
     */
    public static String getSystemLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        return locale.getLanguage();
    }

    /**
     * Set App language
     *
     * @param context
     * @param language
     */
    public static void setLanguage(Context context, String language) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        switch (language) {
            case "en":
                configuration.locale = Locale.ENGLISH;
                break;
            default:
                configuration.locale = Locale.CHINESE;
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    /**
     * Restart App
     *
     * @param context
     */
    public static void resetApp(Context context) {
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
}
