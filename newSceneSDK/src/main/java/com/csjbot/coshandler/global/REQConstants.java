package com.csjbot.coshandler.global;

/**
 * REQ类型的请求消息
 * Created by jingwc on 2017/8/12.
 */

public class REQConstants {

    ////////////////////////////语音部分
    /**
     * 打开语音服务
     * 需要先调用此接口打开科大讯飞语音服务才能进行后续识别和语义等功能
     */
    public static final String SPEECH_SERVICE_START_REQ = "SPEECH_SERVICE_START_REQ";
    public static final String SEND_TO_MESSAGE_DATESOCKET = "SEND_TO_MESSAGE_DATESOCKET";
    public static final String NAVI_SET_MODE_REQ = "NAVI_SET_MODE_REQ";
    public static final String GET_CURRENT_MAP_IMAGE_REQ = "GET_CURRENT_MAP_IMAGE_REQ";
    public static final String MQTT_CLIENT_START_REQ = "MQTT_CLIENT_START_REQ";
    public static final String GET_SENSOR_HEALTH_REQ = "GET_SENSOR_HEALTH_REQ";
    public static final String START_CUSTOMER_SERVERS_REQ = "START_CUSTOMER_SERVERS_REQ";
    /**
     * 关闭语音服务
     * 不需要使用语音功能时，可以使用此接口关闭科大讯飞语音服务
     */
    public static final String SPEECH_SERVICE_STOP_REQ = "SPEECH_SERVICE_STOP_REQ";

    /**
     * 开始多次语音识别
     */
    public static final String SPEECH_ISR_START_REQ = "SPEECH_ISR_START_REQ";

    /**
     * 关闭多次语音识别
     */
    public static final String SPEECH_ISR_STOP_REQ = "SPEECH_ISR_STOP_REQ";

    /**
     * 打开单次语音识别
     */
    public static final String SPEECH_ISR_ONCE_START_REQ = "SPEECH_ISR_ONCE_START_REQ";

    /**
     * 关闭单次语音识别
     */
    public static final String SPEECH_ISR_ONCE_STOP_REQ = "SPEECH_ISR_ONCE_STOP_REQ";

    /**
     * 文本转语音请求
     * {
     * "msg_id":"SPEECH_TTS_REQ"
     * "content":"你好！"
     * }
     */
    public static final String SPEECH_TTS_REQ = "SPEECH_TTS_REQ";

    /**
     * 手动停止语音朗读
     */
    public static final String SPEECH_READ_STOP_REQ = "SPEECH_READ_STOP_REQ";

    /**
     * 发送文字信息识别请求
     * {
     * "msg_id":"SPEECH_TXT_ONCE_START_REQ",
     * “content”:”要识别的内容”
     * }
     */
    public static final String SPEECH_TXT_ONCE_START_REQ = "SPEECH_TXT_ONCE_START_REQ";

    /**
     * 加载用户语义
     * 立即重新加载用户自定义语义。（默认开启服务时自动加载一次,即WebResource文件夹下的voicecmd.txt）
     */
    public static final String SPEECH_LOAD_CMD_REQ = "SPEECH_LOAD_CMD_REQ";


    /**
     * 手动唤醒机器人麦克风
     */
    public static final String SPEECH_ISR_MICRO_REQ = "SPEECH_ISR_MICRO_REQ";


    ////////////////////////////人脸识别部分
    /**
     * 打开摄像头
     * 打开视频流传输,视频流协议详见视频流demo，视频流需要用到opencv库，socket连接60003端口。
     */
    public static final String FACE_DETECT_OPEN_VIDEO_REQ = "FACE_DETECT_OPEN_VIDEO_REQ";

    /**
     * 关闭摄像头
     * 关闭视频流传输
     */
    public static final String FACE_DETECT_CLOSE_VIDEO_REQ = "FACE_DETECT_CLOSE_VIDEO_REQ";

    /**
     * 开启人脸识别后台服务
     * 开启人脸识别功能。开启后，会对人脸进行自动推送。调用此接口后会马上上报一条人脸识别信息
     */
    public static final String FACE_DETECT_SERVICE_START_REQ = "FACE_DETECT_SERVICE_START_REQ";

    /**
     * 关闭人脸识别后台服务
     * 关闭人脸识别以后，停止对人脸进行检测
     */
    public static final String FACE_DETECT_SERVICE_STOP_REQ = "FACE_DETECT_SERVICE_STOP_REQ";

    /**
     * 人脸注册准备
     * 人脸注册前使用。调用此接口后，后面所有使用的人脸注册接口才有效
     */
    public static final String FACE_REG_START_REQ = "FACE_REG_START_REQ";

    /**
     * 人脸注册完毕
     * 人脸注册后调用。调用此接口后，后面所有再使用人脸注册接口(2.2.9)无效，即人脸注册接口调了没用。
     */
    public static final String FACE_REG_STOP_REQ = "FACE_REG_STOP_REQ";

    /**
     * 摄像头拍照
     */
    public static final String FACE_SNAPSHOT_REQ = "FACE_SNAPSHOT_REQ";

    /**
     * 人脸注册
     * {
     * "msg_id":"FACE_SAVE_REQ",
     * "name":"李和亮"
     * }
     */
    public static final String FACE_SAVE_REQ = "FACE_SAVE_REQ";

    /**
     * 人脸信息删除
     * 删除数据库中某一人脸信息
     * {
     * "msg_id":"FACE_DATA_DEL_REQ"
     * "face_id": "1"
     * }
     */
    public static final String FACE_DATA_DEL_REQ = "FACE_DATA_DEL_REQ";

    /**
     * 人脸数据库获取
     */
    public static final String FACE_DATABASE_REQ = "FACE_DATABASE_REQ";

    /**
     * 人脸信息变更
     */
    public static final String FACE_DATA_CHANGED_REQ = "FACE_DATA_CHANGED_REQ";
    /**
     * 人脸信息变更
     */
    public static final String FACE_INFO_SYNC_REQ = "FACE_INFO_SYNC_REQ";
    /**
     * 人脸信息批量删除
     */
    public static final String FACE_DATA_DELETE_LIST_REQ = "FACE_DATA_DELETE_LIST_REQ";


    ////////////////////////////表情部分
    /**
     * 设置面部表情
     * {
     * "msg_id": "SET_ROBOT_EXPRESSION_REQ",
     * "expression": 5003
     * “once”:1
     * “time”:0
     * }
     */
    public static final String SET_ROBOT_EXPRESSION_REQ = "SET_ROBOT_EXPRESSION_REQ";

    public static final String ALICE_EXPRESSION_REQ = "ALICE_EXPRESSION_REQ";

    /**
     * 获取当前面部表情
     */
    public static final String GET_ROBOT_EXPRESSION_REQ = "GET_ROBOT_EXPRESSION_REQ";


    ////////////////////////////地盘及导航
    /**
     * 获取当前位置
     */
    public static final String NAVI_GET_CURPOS_REQ = "NAVI_GET_CURPOS_REQ";

    /**
     * 底盘移动指令
     * {
     * "msg_id":"NAVI_ROBOT_MOVE_REQ",
     * "direction":0
     * }
     * 0 前
     * 1 后
     * 2 左
     * 3 右
     */
    public static final String NAVI_ROBOT_MOVE_REQ = "NAVI_ROBOT_MOVE_REQ";

    /**
     * * {
     * * "msg_id":"NAVI_ROBOT_MOVE_SERIAL_REQ",
     * * "direction":0
     * * }
     * 0x01:表示前进 10cm;
     * 0x02:表示后退 10cm;
     * 0x03:表示左转 45 度;
     * 0x04:表示右转45 度;
     * 0x05:表示立即停止;
     */
    public static final String NAVI_ROBOT_MOVE_BY_SERIAL_REQ = "NAVI_ROBOT_MOVE_BY_SERIAL_REQ";

    /**
     * * {
     * "msg_id":"NAVI_ROBOT_MOVE_SERIAL_REQ",
     * "linear":0,
     * "angular":0
     * }
     * linear:线速度 单位 mm/s。;
     * angular:线速度 单位 mrad/s。;
     */
    public static final String NAVI_ROBOT_MOVE_SERIAL_REQ = "NAVI_ROBOT_MOVE_SERIAL_REQ";

    /**
     * 特定点导航
     * {
     * "msg_id":"NAVI_ROBOT_MOVE_TO_REQ",
     * "pos": {
     * "x":10,
     * "y":200,
     * "z":25,
     * "rotation":1000
     * }
     * }
     */
    public static final String NAVI_ROBOT_MOVE_TO_REQ = "NAVI_ROBOT_MOVE_TO_REQ";

    /**
     * 特定点导航取消
     */
    public static final String NAVI_ROBOT_CANCEL_REQ = "NAVI_ROBOT_CANCEL_REQ";

    /**
     * 转向至特定角度
     * {
     * "msg_id":"NAVI_GO_ROTATION_TO_REQ",
     * "rotation":0
     * }
     */
    public static final String NAVI_GO_ROTATION_TO_REQ = "NAVI_GO_ROTATION_TO_REQ";

    /**
     * 步进角度
     * {
     * "msg_id":"NAVI_GO_ROTATION_REQ",
     * "rotation":0
     * <p>
     * }
     * 请求参数说明
     * Rotation>0:向左转，Rotation<0:向右转
     */
    public static final String NAVI_GO_ROTATION_REQ = "NAVI_GO_ROTATION_REQ";


    public static final String NAVI_DEST_REACHABLE_REQ = "NAVI_DEST_REACHABLE_REQ";

    /**
     * 回充电点位
     */
    public static final String NAVI_GO_HOME_REQ = "NAVI_GO_HOME_REQ";

    /**
     * 存储地图
     */
    public static final String NAVI_GET_MAP_REQ = "NAVI_GET_MAP_REQ";

    /**
     * 加载地图
     */
    public static final String NAVI_SET_MAP_REQ = "NAVI_SET_MAP_REQ";

    public static final String NAVI_GET_MAPLIST_REQ = "NAVI_GET_MAPLIST_REQ";

    public static final String NAVI_SET_MAP_AND_POSE_REQ = "NAVI_SET_MAP_AND_POSE_REQ";

    /**
     * 速度设置
     */
    public static final String NAVI_ROBOT_SET_SPEED_REQ = "NAVI_ROBOT_SET_SPEED_REQ";

    /**
     * 速度获取
     */
    public static final String NAVI_ROBOT_GET_SPEED_REQ = "NAVI_ROBOT_GET_SPEED_REQ";

    /**
     * 设置自启时间
     */
    public static final String ROBOT_SET_POWERTIME_REQ = "ROBOT_SET_POWERTIME_REQ";
    /**
     * 查询导航状态
     */
    public static final String NAVI_GET_STATUS_REQ = "NAVI_GET_STATUS_REQ";

    /**
     * 获取设备SN
     */
    public static final String GET_SN_REQ = "GET_SN_REQ";

    /**
     * 设置机器人SN号
     */
    public static final String SET_SN_REQ = "SET_SN_REQ";

    /**
     * 生成本地设备列表
     */
    public static final String GET_LOCAL_DEVICE_REQ = "GET_LOCAL_DEVICE_REQ";


    /**
     * 机器人关机
     */
    public static final String ROBOT_SHUTDOWN_REQ = "ROBOT_SHUTDOWN_REQ";

    /**
     * 机器人重启
     */
    public static final String ROBOT_REBOOT_REQ = "ROBOT_REBOOT_REQ";

    /**
     * 机器人电量获取
     */
    public static final String ROBOT_GET_BATTERY_REQ = "ROBOT_GET_BATTERY_REQ";

    /**
     * 充电状态获取
     */
    public static final String ROBOT_GET_CHARGE_REQ = "ROBOT_GET_CHARGE_REQ";

    /**
     * 自检
     */
    public static final String WARNING_CHECK_SELF_REQ = "WARNING_CHECK_SELF_REQ";

    /**
     * 自检
     */
    public static final String GET_HARDWARE_INFO_REQ = "GET_HARDWARE_INFO_REQ";
    /**
     * 导航版本
     */
    public static final String GET_SLAM_VERSION_REQ = "GET_SLAM_VERSION_REQ";


    /**
     * 获取版本号
     */
    public static final String GET_VERSION_REQ = "GET_VERSION_REQ";

    /**
     * 获取版本号
     */
    public static final String GET_ROBOT_TYPE_REQ = "GET_ROBOT_TYPE_REQ";

    /**
     * 控制开关门
     */
    public static final String DOUBLE_DOORS_CONTROL = "DOUBLE_DOORS_CONTROL";

    /**
     * 主动获取门的状态
     */
    public static final String DEVICE_DOOR_STATUS_REQ = "DEVICE_DOOR_STATUS_REQ";
    public static final String RELEASE_EMERGENCY_REQ = "RELEASE_EMERGENCY_REQ";


    // 配置

    /**
     *
     */
    public static final String SET_MICRO_VOLUME_REQ = "SET_MICRO_VOLUME_REQ";

    /**
     * 获取麦克风音量
     */
    public static final String GET_MICRO_VOLUME_REQ = "GET_MICRO_VOLUME_REQ";


    /**
     * linux软件版本检查
     */
    public static final String UPGRADE_CHECK_REQ = "UPGRADE_CHECK_REQ";

    /**
     * linux软件版本全量更新
     */
    public static final String UPGRADE_TOTAL_REQ = "UPGRADE_TOTAL_REQ";


    public static final String ROBOT_DANCE_START_REQ = "ROBOT_DANCE_START_REQ";

    public static final String ROBOT_DANCE_STOP_REQ = "ROBOT_DANCE_STOP_REQ";


    public static final String CUSTSERVICE_GET_RESULT_REQ = "CUSTSERVICE_GET_RESULT_REQ";


    public static final String UPGRADE_ROBOT_EXPRESSION_REQ = "UPGRADE_ROBOT_EXPRESSION_REQ";


    public static final String FACE_SYNC_UNDO_REG_REQ = "FACE_SYNC_UNDO_REG_REQ";


    /**
     * 2.2.11.	人脸跟随开启
     * 接口说明
     * 开启后机器人会自动检测最近的并且已经注册过的人，并且每次都朝向该人。
     * <p>
     * {
     * "msg_id":"FACE_FOLLOW_START_REQ"
     * }
     */
    public static final String FACE_FOLLOW_START_REQ = "FACE_FOLLOW_START_REQ";


    /**
     * 2.2.12.	人脸跟随关闭
     * 接口说明
     * 开启后机器人会自动检测最近的人，并且每次都朝向该人。
     * <p>
     * 请求数据示例
     * {
     * "msg_id":"FACE_FOLLOW_CLOSE_REQ"
     * }
     */
    public static final String FACE_FOLLOW_CLOSE_REQ = "FACE_FOLLOW_CLOSE_REQ";


    /**
     * 2.1.19.	热词查询
     * <p>
     * 接口说明
     * 查询当前已经上传的热词
     * <p>
     * 请求数据示例
     * {
     * "msg_id": "SPEECH_GET_USERWORDS_REQ"
     * }
     * 请求参数说明
     * 无
     */
    public static final String SPEECH_GET_USERWORDS_REQ = "SPEECH_GET_USERWORDS_REQ";

    /**
     * 当某些关键词需要准确识别时，推荐使用此功能。
     * 注：每次上传会覆盖上一次上传的热词,更新完请重启机器人。
     * <p>
     * 请求数据示例
     * {
     * "msg_id": "SPEECH_SET_USERWORDS_REQ",
     * "words": ["穿山甲", "机器人", "关键词"]
     * }
     */
    public static final String SPEECH_SET_USERWORDS_REQ = "SPEECH_SET_USERWORDS_REQ";

    /**
     * 手动获取机器人人体检测信息
     */
    public static final String DEVICE_DETECT_PERSON_NEAR_REQ = "DEVICE_DETECT_PERSON_NEAR_REQ";

    public static final String ROBOT_MAKE_SESSIONID_REQ = "ROBOT_MAKE_SESSIONID_REQ";

    /**
     * 机器人过载状态
     */
    public static final String GET_MOTOR_OVERLOAD_STATUS_REQ = "GET_MOTOR_OVERLOAD_STATUS_REQ";

    /**
     * 机器人急停状态
     */
    public static final String GET_EMERGENCY_STATUS_REQ = "GET_EMERGENCY_STATUS_REQ";

    /**
     * 清除过载
     */
    public static final String MOTOR_OVERLOAD_CLEAR_REQ = "MOTOR_OVERLOAD_CLEAR_REQ";

    /**
     * 是否在充电桩上
     */
    public static final String ROBOT_GET_DOCK_STATUS_REQ = "ROBOT_GET_DOCK_STATUS_REQ";


    public static final String SET_TIME_REQ = "SET_TIME_REQ";
    public static final String SET_X_TIMING_BOOT = "SET_X_TIMING_BOOT";
    public static final String AMY_EXPRESSION_NEW_REQ = "AMY_EXPRESSION_NEW_REQ";

    public static class Expression {
        // 高兴
        public final static int HAPPY = 5000;
        // 悲伤
        public final static int SADNESS = 5001;
        // 惊讶
        public final static int SURPRISED = 5002;
        // 微笑
        public final static int SMILE = 5003;
        // 普通
        public final static int NORMAL = 5004;
        // 生气
        public final static int ANGRY = 5005;
        // 闪电
        public final static int LIGHTNING = 5006;
        // 困倦
        public final static int SLEEPINESS = 5007;

        public final static int YES = 1;

        public final static int NO = 0;


    }


    // 肢体部位
    public static class BodyPart {
        // 1重置
        public static final int RESET = 1;
        // 2头部关节
        public static final int HEAD = 2;
        // 3左大臂关节
        public static final int LEFT_ARM = 3;
        // 4右大臂关节
        public static final int RIGHT_ARM = 4;
        // 5双大臂关节
        public static final int DOUBLE_ARM = 5;
        // 6左小臂关节
        public static final int LEFT_FOREARM = 6;
        // 7右小臂关节
        public static final int RIGHT_FOREARM = 7;
        // 8双小臂关节
        public static final int DOUBLE_FOREARM = 8;
        // 腰部关节
        public static final int WAIST = 9;
    }


    // 动作
    public static class BodyAction {
        // 1无动作
        public static final int NONE = 1;
        // 2左转上
        public static final int LEFT_UP = 2;
        // 3右转下
        public static final int RIGHT_DOWN = 3;

        /**
         * 注意：action的4、5、6、7、8仅对头部电机有用。
         */

        // 4左右转
        public static final int LEFT_THEN_RIGHT = 4;
        // 5上转
        public static final int UP = 5;
        // 6下转
        public static final int DOWN = 6;
        // 7上下转
        public static final int UP_AND_DOWN = 7;
        // 上下停止
        public static final int HEAD_UP_AND_DOWN_STOP = 8;
        // 开始摆手
        public static final String ROBOT_ARM_LOOP_START_REQ = "ROBOT_ARM_LOOP_START_REQ";
        // 停止摆手
        public static final String ROBOT_ARM_LOOP_STOP_REQ = "ROBOT_ARM_LOOP_STOP_REQ";
    }

    /**
     * 第二版硬件
     * 动作	        body_part	action
     * 低头	        2	        5
     * 抬头	        2	        6
     * 左转头	    2	        2
     * 右转头	    2	        3
     * 头部水平归位	2	        4
     * 抬左胳膊	    3	        2
     * 放左胳膊	    3	        3
     * 抬右胳膊	    4	        2
     * 放右胳膊	    4	        3
     */
    public static class BodyPartV2 {
        // 2头部关节
        public static final int HEAD1 = 2;
        public static final int HEAD2 = 2;
        public static final int LEFT_ARM = 3;
        public static final int RIGHT_ARM = 4;
    }

    public static class BodyActionV2 {
        public static final int HEAD_UP = 5;
        public static final int HEAD_DOWN = 6;
        public static final int HEAD_LEFT = 2;
        public static final int HEAD_RIGHT = 3;
        public static final int HORIZONTAL_RESET = 4;

        public static final int ARM_UP = 2;
        public static final int ARM_DOWN = 3;
    }


    /**
     * 请求数据示例
     * 请求参数说明
     * 动作	    body_part	action（摆动次数，<=20次）
     * 右臂摆动	1	        1
     * 左臂摆动	2	        1
     * 双臂摆动	3	        1
     */
    public static class SnowBodyActionV2 {
        public static final int SNOW_RIGHT_ARM_SWING = 1;
        public static final int SNOW_LEFT_ARM_SWING = 2;
        public static final int SNOW_DOUBLE_ARM_SWING = 2;
    }

    /**
     * 底盘移动方向
     */
    public static final class MoveDirection {
        /* 前 */
        public static final int FORWARD = 0;
        /* 后 */
        public static final int BACK = 1;
        /* 左 */
        public static final int LEFT = 2;
        /* 右 */
        public static final int RIGHT = 3;
    }

    /**
     * 电梯控制
     */
    public static final String ROBOT_ELEVATOR_OPEN_REQ = "ROBOT_ELEVATOR_OPEN_REQ";

    public static final String ROBOT_ELEVATOR_CLOSE_REQ = "ROBOT_ELEVATOR_CLOSE_REQ";

    public static final String ROBOT_ELEVATOR_CTRL_REQ = "ROBOT_ELEVATOR_CTRL_REQ";
    public static final String ROBOT_ELEVATOR_ROBOTID_REQ = "ROBOT_ELEVATOR_ROBOTID_REQ";
    public static final String ROBOT_ELEVATOR_INFO_REQ = "ROBOT_ELEVATOR_INFO_REQ";

    public static class RobotElevatorCtrl {
        public static final String CALLINFO = "CALLINFO";
        public static final String CALLING = "CALLING";
        public static final String ENTERING = "ENTERING";
        public static final String INSIDE = "INSIDE";
        public static final String LEAVING = "LEAVING";
        public static final String OUTSIDE = "OUTSIDE";
        public static final String CANCEL = "CANCEL";
        public static final String STATUS = "STATUS";
    }

    //2023-04-19 最新 alice头部手部电机控制
    public static final String ALICE_NEW_ACTION_CTRL_REQ = "ALICE_NEW_ACTION_CTRL_REQ";
    //2023-04-19 最新 alice动作复位指令
    public static final String ALICE_NEW_ACTION_CTRL_RESET_REQ = "ALICE_NEW_ACTION_CTRL_RESET_REQ";


    public static final String REMOTE_CTRL_AGENT_START_REQ = "REMOTE_CTRL_AGENT_START_REQ";
    public static final String REMOTE_CTRL_OPEN_REQ = "REMOTE_CTRL_OPEN_REQ";
    public static final String REMOTE_CTRL_CLOSE_REQ = "REMOTE_CTRL_CLOSE_REQ";


    //连接远程扫图的websocket
    public static final String CONNECT_SCAN = "CONNECT_SCAN";
    //连接收发数据的websocket
    public static final String CONNECT_DATA = "CONNECT_DATA";
}
