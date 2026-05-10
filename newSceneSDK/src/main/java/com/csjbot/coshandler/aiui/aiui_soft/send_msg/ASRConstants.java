package com.csjbot.coshandler.aiui.aiui_soft.send_msg;

/**
 * @author ShenBen
 * @date 2019/8/27 14:35
 * @email 714081644@qq.com
 */
public class ASRConstants {

    /**
     * 语音识别模式
     */
    public static int RECG_MODE;

    public static boolean isIntervened = false;

    public static String packName;

    public static int code = 0;

    public class RECG {
        /**
         * 多次识别
         */
        public static final int CIRCLE_RECG = 0x11;

        /**
         * 一次识别
         */
        public static final int ONCE_RECG = 0x12;
    }

}
