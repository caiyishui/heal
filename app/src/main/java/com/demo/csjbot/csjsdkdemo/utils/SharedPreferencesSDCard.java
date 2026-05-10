package com.demo.csjbot.csjsdkdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Copyright (c) 2020, SuZhou CsjBot. All Rights Reserved.
 * www.csjbot.com
 * <p>
 * Created by 浦耀宗 at 2020/8/12-15:21.
 * Email: puyz@csjbot.com
 */
public class SharedPreferencesSDCard {
    private static final String TAG = SharedPreferencesSDCard.class.getSimpleName();

    private static SharedPreferencesSDCard instance;

    private static SharedPreferences mySharedPreferences;

    private SharedPreferencesSDCard() {
    }

    public static synchronized SharedPreferencesSDCard getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesSDCard();
        }
        return instance;
    }

    public SharedPreferences getMySharedPreferences() {
        return mySharedPreferences;
    }

    public void init(Context context) {
        mySharedPreferences = getMySharedPreferences(context,
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/csjbot_sdk_demo", "csjbot_sdk_demo");
    }


    private static SharedPreferences getMySharedPreferences(Context context, String dir, String fileName) {
        try {
            // 获取 ContextWrapper对象中的mBase变量。该变量保存了 ContextImpl 对象
            Field field_mBase = ContextWrapper.class.getDeclaredField("mBase");
            field_mBase.setAccessible(true);
            // 获取 mBase变量
            Object obj_mBase = field_mBase.get(context);
            // 获取 ContextImpl。mPreferencesDir变量，该变量保存了数据文件的保存路径
            Field field_mPreferencesDir = obj_mBase.getClass().getDeclaredField("mPreferencesDir");
            field_mPreferencesDir.setAccessible(true);
            // 创建自定义路径
//            String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android";
            File file = new File(dir);
            // 修改mPreferencesDir变量的值
            field_mPreferencesDir.set(obj_mBase, file);
            // 返回修改路径以后的 SharedPreferences :%FILE_PATH%/%fileName%.xml
            Log.e(TAG, "getMySharedPreferences filep=" + file.getAbsolutePath() + "| fileName=" + fileName);
            return context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getMySharedPreferences end filename=" + fileName);
        // 返回默认路径下的 SharedPreferences : /data/data/%package_name%/shared_prefs/%fileName%.xml
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
}
