package com.csjbot.coshandler.global;

/**
 * Cmd命令消息类型
 * Created by jingwc on 2017/8/12.
 */

public class CmdConstants {

    ////////////////////////////语音部分
    /**
     * ISR参数设置
     * {
     * "msg_id":"SPEECH_SET_ISR_PARAM_CMD",
     * "accent":"mandarin",
     * "language":"zh_cn"
     * }
     */
    public static final String SPEECH_SET_ISR_PARAM_CMD = "SPEECH_SET_ISR_PARAM_CMD";

    /**
     * s’s参数设置
     * {
     * "msg_id":"SPEECH_SET_TTS_PARAM_CMD",
     * "engine_type": 1,
     * "voice_name": "xiaoyan",
     * "speed": 50,
     * "pitch": 50,
     * "rdn": 1
     * }
     */
    public static final String SPEECH_SET_TTS_PARAM_CMD = "SPEECH_SET_TTS_PARAM_CMD";


    ////////////////////////////人脸识别部分


    ////////////////////////////上肢动作
    /**
     * 机器人肢体操作
     * 控制机器人上身肢体
     * {
     * "msg_id":"ROBOT_BODY_CTRL_CMD",
     * "body_part":3,
     * "action":2
     * }
     * body_part：肢体部位
     * 1重置
     * 2头部关节
     * 3左大臂关节
     * 4右大臂关节
     * 5双大臂关节
     * 6左小臂关节
     * 7右小臂关节
     * 8双小臂关节
     * 9腰部关节
     * <p>
     * action：动作
     * 1无动作
     * 2左转上
     * 3右转下
     * 4左右转
     * 5上转
     * 6下转
     * 7上下转
     * 8上下停止
     */
    public static final String ROBOT_BODY_CTRL_CMD = "ROBOT_BODY_CTRL_CMD";

    public static final String ROBOT_START_WAVE_HANDS = "ROBOT_START_WAVE_HANDS";

    /**
     * 打开打印机
     */
    public static final String PRINTER_OPEN_CMD = "PRINTER_OPEN_CMD";

    /**
     * 打印机打印文本
     */
    public static final String PRINTER_PRINT_TEXT_CMD = "PRINTER_PRINT_TEXT_CMD";

    /**
     * 打印机切刀
     */
    public static final String PRINTER_PAPER_CUT_CMD = "PRINTER_PAPER_CUT_CMD";

    /**
     * 打印图片
     */
    public static final String PRINTER_PRINT_IMG_CMD = "PRINTER_PRINT_IMG_CMD";


    /**
     * 设置机器人种类
     */
    public static final String SET_ROBOT_TYPE_CMD = "SET_ROBOT_TYPE_CMD";

    public static final String ROBOT_SDK_AGENT_ENABLE = "ROBOT_SDK_AGENT_ENABLE";


    /**
     * 打印二维码
     */
    public static final String PRINTER_PRINT_QRCODE_CMD = "PRINTER_PRINT_QRCODE_CMD";

    /**
     * 用于设置新后台的地域
     */
    public static final String SET_TIMEZONE_CMD = "SET_TIMEZONE_CMD";

    public class LanguageType {
        public static final String ZH_CN = "zh_cn";
        public static final String EN_US = "en_us";
        public static final String JA_JP = "ja_jp";
    }

    public static final String ALICE_HEAD_LEFT_RIGHT_CTRL = "head_left_right";

    public static final String ALICE_HEAD_UP_DOWN_CTRL = "head_up_down";

    public static final String ALICE_LEFT_HAND_CTRL = "left_hand";
    public static final String ALICE_RIGHT_HAND_CTRL = "right_hand";
    //自定义各个角度控制
    public static final String ALICE_CUSTOMER_CTRL = "custom_ctrl";
}
