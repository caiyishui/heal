package com.csjbot.coshandler.handle_msg.task.taskfactory;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.handle_msg.RbSetSn;
import com.csjbot.coshandler.handle_msg.task.RbBase;
import com.csjbot.coshandler.handle_msg.task.RbBody;
import com.csjbot.coshandler.handle_msg.task.RbChassis;
import com.csjbot.coshandler.handle_msg.task.RbCustomer;
import com.csjbot.coshandler.handle_msg.task.RbDevice;
import com.csjbot.coshandler.handle_msg.task.RbElevator;
import com.csjbot.coshandler.handle_msg.task.RbExpression;
import com.csjbot.coshandler.handle_msg.task.RbFace;
import com.csjbot.coshandler.handle_msg.task.RbOther;
import com.csjbot.coshandler.handle_msg.task.RbRobotState;
import com.csjbot.coshandler.handle_msg.task.RbSn;
import com.csjbot.coshandler.handle_msg.task.RbSpeech;
import com.csjbot.coshandler.handle_msg.task.RbVersion;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产实例对象
 * Created by jingwc on 2017/8/12.
 */

public class RbFactory {

    private static final Map<String, RbBase> prMap = new HashMap<>();

    public static <T extends RbBase> T createRbBase(String tag) {
        RbBase rbBase = null;
        String type = convert(tag);
        if (prMap.containsKey(type)) {
            rbBase = prMap.get(type);
        } else {
            try {
                rbBase = (RbBase) Class.forName(getClass(type).getName()).newInstance();
                prMap.put(type, rbBase);
            } catch (Exception e) {
                CosLogger.error("RbFactory:e:" + e.toString());
            }
        }
        return (T) rbBase;
    }

    public static String convert(String tag) {
        String type = null;
        if (tag.contains(RbParams.SPEECH)) {
            type = RbParams.SPEECH;
        } else if (tag.contains(RbParams.FACE)) {
            type = RbParams.FACE;
        } else if (tag.contains(RbParams.EXPRESSION)) {
            type = RbParams.EXPRESSION;
        } else if (tag.contains(RbParams.CHASSIS)) {
            type = RbParams.CHASSIS;
        } else if (tag.contains(RbParams.BODYACTION) || tag.contains(RbParams.ARM_LOOP)) {
            type = RbParams.BODYACTION;
        } else if (tag.contains(RbParams.SN)) {
            type = RbParams.SN;
        } else if (tag.contains(RbParams.DEVICE)) {
            type = RbParams.DEVICE;
        } else if (tag.contains(RbParams.ROBOT_STATE)) {
            type = RbParams.ROBOT_STATE;
        } else if (tag.contains(RbParams.GET_VERSION) || tag.contains(RbParams.UPGRADE)) {
            type = RbParams.GET_VERSION;
        } else if (tag.contains(RbParams.CUSTOMER) || tag.contains(RbParams.CUSTOMER_HANGUP) || tag.contains(RbParams.CUSTOMER_HEAT)) {
            type = RbParams.CUSTOMER;
        } else if (tag.contains(RbParams.ELEVATOR)) {
            type = RbParams.ELEVATOR;
        } else {
            type = RbParams.OTHER;
        }
        return type;
    }

    public static Class<?> getClass(String type) {
        Class<?> c = null;
        switch (type) {
            case RbParams.SPEECH:
                c = RbSpeech.class;
                break;
            case RbParams.FACE:
                c = RbFace.class;
                break;
            case RbParams.EXPRESSION:
                c = RbExpression.class;
                break;
            case RbParams.CHASSIS:
                c = RbChassis.class;
                break;
            case RbParams.BODYACTION:
            case RbParams.ARM_LOOP:
                c = RbBody.class;
                break;
            case RbParams.SN:
                c = RbSn.class;
                break;
            case RbParams.DEVICE:
                c = RbDevice.class;
                break;
            case RbParams.ROBOT_STATE:
                c = RbRobotState.class;
                break;
            case RbParams.SET_SN:
                c = RbSetSn.class;
                break;
            case RbParams.GET_VERSION:
                c = RbVersion.class;
                break;
            case RbParams.CUSTOMER:
                c = RbCustomer.class;
                break;
            case RbParams.OTHER:
                c = RbOther.class;
                break;
            case RbParams.ELEVATOR:
                c = RbElevator.class;
                break;
            default:
                break;
        }
        return c;
    }

    public static final class RbParams {
        /* 语音 */
        public static final String SPEECH = "SPEECH";
        /* 人脸识别 */
        public static final String FACE = "FACE";
        /* 表情切换 */
        public static final String EXPRESSION = "EXPRESSION";
        /* 地盘 */
        public static final String CHASSIS = "NAVI";
        /* 肢体动作 */
        public static final String BODYACTION = "ROBOT_BODY";
        /* 摆手 */
        public static final String ARM_LOOP = "ARM_LOOP";
        /* 机器人状态操作 */
        public static final String ROBOT_STATE = "ROBOT_GET_BATTERY";
        /* 获取SN */
        public static final String SN = "GET_SN";
        /* 生成本地设备列表 */
        public static final String DEVICE = "GET_LOCAL_DEVICE";
        /* 设置SN */
        public static final String SET_SN = "SET_SN";
        /* 获取版本 */
        public static final String GET_VERSION = "GET_VERSION";
        public static final String UPGRADE = "UPGRADE";
        /*人工客服*/
        public static final String CUSTOMER = "CALL_HUMAN_SERVICE";
        /*人工客服挂断*/
        public static final String CUSTOMER_HANGUP = "HANGUP_HUMAN_SERVICE";
        /*人工客服心跳*/
        public static final String CUSTOMER_HEAT = "HUMAN_SERVICE_STATUS";
        /*电梯模块*/
        public static final String ELEVATOR = "ELEVATOR";

        /* 其他 */
        public static final String OTHER = "OTHER";
    }
}
