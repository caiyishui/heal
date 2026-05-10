package com.csjbot.coshandler.util;

import java.util.Locale;

public class JsonFormatUtil {
    public static String SplicingSimpleJson(String msg_id) {
        return String.format(Locale.getDefault(), "{\"error_code\":0,\"msg_id\":\"%s\"}", msg_id);
    }

    public static String SplicingSimpleJson(String msg_id, String key, String value) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":\"%s\",\"error_code\":0}", msg_id, key, value);
    }

    public static String SplicingSimpleJson(String msg_id, String key, int value) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"error_code\":0}", msg_id, key, value);
    }

    public static String SplicingSimpleJson(String msg_id, String key1, int value1, String key2, int value2) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%d,\"error_code\":0}", msg_id, key1, value1, key2, value2);
    }

    public static String SplicingSimpleJson(String msg_id, String key1, int value1, String key2, int value2, String key3, int value3) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%d,\"%s\":%d,\"error_code\":0}", msg_id, key1, value1, key2, value2, key3, value3);
    }

    public static String SplicingSimpleJson(String msg_id, String key1, int value1, String key2, long value2) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%ld,\"error_code\":0}", msg_id, key1, value1, key2, value2);
    }


    public static String SplicingSimpleJson(String msg_id, String key1, boolean value1,
                                            String key2, boolean value2, String key3, boolean value3) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%b,\"%s\":%b,\"%s\":%b,\"error_code\":0}",
                msg_id, key1, value1, key2, value2, key3, value3);
    }

    /**
     * {
     * "msg_id":"SPEECH_ISR_ERROR_NTF",
     * "key":"value",
     * "error_code":error_code
     * }
     *
     * @param msg_id     消息ID
     * @param key        key
     * @param value      value String 类型
     * @param error_code error code
     * @return
     */
    public static String SplicingSimpleJson(String msg_id, String key, String value, int error_code) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":\"%s\",\"error_code\":%d}", msg_id, key, value, error_code);
    }

    public static String SplicingSimpleJsonREQ(String msg_id) {
        return String.format(Locale.getDefault(), "{\"msg_id\":\"%s\"}", msg_id);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, int code) {
        return String.format(Locale.getDefault(), "{\"msg_id\":\"%s\",\"status\":%d}", msg_id, code);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key, long value) {
        return String.format(Locale.getDefault(), "{\"msg_id\":\"%s\",\"%s\":\"%s\"}", msg_id, key, System.currentTimeMillis());
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key, String value) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":\"%s\"}", msg_id, key, value);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key, int value) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d}", msg_id, key, value);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key1, int value1, String key2, int value2) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%d}", msg_id, key1, value1, key2, value2);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key1, int value1, String key2, int value2, String key3, int value3) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%d,\"%s\":%d}", msg_id, key1, value1, key2, value2, key3, value3);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key1, long value1, String key2, int value2, String key3, String value3) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%s,\"%s\":%d,\"%s\":%s}", msg_id, key1, value1, key2, value2, key3, value3);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key1, int value1, String key2, long value2) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%s}", msg_id, key1, value1, key2, value2);
    }

    public static String SplicingSimpleJsonREQ(String msg_id, String key2, int value2, String key3, String value3) {
        return String.format(Locale.getDefault(),
                "{\"msg_id\":\"%s\",\"%s\":%d,\"%s\":%s}", msg_id, key2, value2, key3, value3);
    }
}
