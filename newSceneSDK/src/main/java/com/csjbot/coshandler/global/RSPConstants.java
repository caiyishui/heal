package com.csjbot.coshandler.global;

/**
 * RSP类型的响应消息
 * Created by jingwc on 2017/8/12.
 */
public class RSPConstants {

    ////////////////////////////语音部分
    /**
     * 打开语音服务
     */
    public static final String SPEECH_SERVICE_START_RSP = "SPEECH_SERVICE_START_RSP";
    public static final String GET_SENSOR_HEALTH_RSP = "GET_SENSOR_HEALTH_RSP";
    public static final String ROBOT_NAVI_CTRL_RSP = "ROBOT_NAVI_CTRL_RSP";
    /**
     * 2.1.2.关闭讯飞语音服务
     */
    public static final String SPEECH_SERVICE_STOP_RSP = "SPEECH_SERVICE_STOP_RSP";
    /**
     * 开始多次语音识别
     */
    public static final String SPEECH_ISR_START_RSP = "SPEECH_ISR_START_RSP";
    /**
     * 关闭多次语音识别
     */
    public static final String SPEECH_ISR_STOP_RSP = "SPEECH_ISR_STOP_RSP";
    /**
     * 打开单次语音识别
     */
    public static final String SPEECH_ISR_ONCE_START_RSP = "SPEECH_ISR_ONCE_START_RSP";
    /**
     * 关闭单次语音识别
     */
    public static final String SPEECH_ISR_ONCE_STOP_RSP = "SPEECH_ISR_ONCE_STOP_RSP";

    /**
     * 文本转语音请求
     */
    public static final String SPEECH_TTS_RSP = "SPEECH_TTS_RSP";

    /**
     * 手动停止语音朗读
     */
    public static final String SPEECH_READ_STOP_RSP = "SPEECH_READ_STOP_RSP";


    /**
     * 加载用户语义
     */
    public static final String SPEECH_LOAD_CMD_RSP = "SPEECH_LOAD_CMD_RSP";


    ////////////////////////////人脸识别部分
    /**
     * 打开摄像头
     */
    public static final String FACE_DETECT_OPEN_VIDEO_RSP = "FACE_DETECT_OPEN_VIDEO_RSP";

    /**
     * 关闭摄像头
     */
    public static final String FACE_DETECT_CLOSE_VIDEO_RSP = "FACE_DETECT_CLOSE_VIDEO_RSP";

    /**
     * 开启人脸识别后台服务
     */
    public static final String FACE_DETECT_SERVICE_START_RSP = "FACE_DETECT_SERVICE_START_RSP";


    /**
     * 关闭人脸识别后台服务
     */
    public static final String FACE_DETECT_SERVICE_STOP_REQ = "FACE_DETECT_SERVICE_STOP_REQ";

    /**
     * 人脸注册准备
     */
    public static final String FACE_REG_START_RSP = "FACE_REG_START_RSP";

    /**
     * 人脸注册完毕
     */
    public static final String FACE_REG_STOP_RSP = "FACE_REG_STOP_RSP";

    /**
     * 摄像头拍照
     * {
     * "msg_id":"FACE_SNAPSHOT_RESULT_RSP",
     * "file_path":“H:\\csjusher_2010\\FaceDetect\\Face_0.jpg”,
     * “error_code”:0
     * }
     */
    public static final String FACE_SNAPSHOT_RESULT_RSP = "FACE_SNAPSHOT_RESULT_RSP";

    /**
     * 人脸注册
     * {
     * "msg_id":"FACE_SAVE_RSP",
     * “person_id”:”personx20170107161021mRJOVw”,
     * "error_code":0
     * }
     */
    public static final String FACE_SAVE_RSP = "FACE_SAVE_RSP";

    /**
     * 人脸信息删除
     */
    public static final String FACE_DATA_DEL_RSP = "FACE_DATA_DEL_RSP";

    /**
     * 人脸数据库获取
     * {
     * "msg_id":"FACE_DATABASE_RSP"
     * "data_list" : [
     * {
     * "id" : 1,
     * "name" : "李和亮"
     * },
     * {
     * "id" : 2,
     * "name" : "齐旭川"
     * }
     * ],
     * "list_num" : 2
     * “all_num”:2
     * “page_num”:0
     * }
     */
    public static final String FACE_DATABASE_RSP = "FACE_DATABASE_RSP";

    /**
     * 人脸信息变更
     */
    public static final String FACE_DATA_CHANGED_RSP = "FACE_DATA_CHANGED_RSP";

    /**
     * 人脸信息批量删除
     */
    public static final String FACE_DATA_DELETE_LIST_RSP = "FACE_DATA_DELETE_LIST_RSP";


    ////////////////////////////表情部分
    /**
     * 设置面部表情
     */
    public static final String SET_ROBOT_EXPRESSION_RSP = "SET_ROBOT_EXPRESSION_RSP";

    /**
     * 获取当前面部表情
     * {
     * "msg_id":" GET_ROBOT_EXPRESSION_RSP",
     * "expression":5003,
     * "error_code":0
     * }
     */
    public static final String GET_ROBOT_EXPRESSION_RSP = "GET_ROBOT_EXPRESSION_RSP";

    /**
     * 机器人过载状态
     */
    public static final String MOTOR_OVERLOAD_STATUS_RSP = "MOTOR_OVERLOAD_STATUS_RSP";
    public static final String GET_SLAM_VERSION_RSP = "GET_SLAM_VERSION_RSP";

    /**
     * 急停状态回馈
     */
    public static final String GET_EMERGENCY_STATUS_RSP = "GET_EMERGENCY_STATUS_RSP";

    /**
     * The constant DEVICE_DOOR_STATUS_RSP.
     */
    public static final String DEVICE_DOOR_STATUS_RSP = "DEVICE_DOOR_STATUS_RSP";

    ////////////////////////////地盘及导航
    /**
     * 获取当前位置
     * {
     * "msg_id":"NAVI_GET_CURPOS_RSP",
     * "x":0,
     * "y":0,
     * "z":0,
     * "rotation":0,
     * "error_code":0
     * }
     */
    public static final String NAVI_GET_CURPOS_RSP = "NAVI_GET_CURPOS_RSP";

    /**
     * 底盘移动指令
     */
    public static final String NAVI_ROBOT_MOVE_RSP = "NAVI_ROBOT_MOVE_RSP";


    /**
     * 特定点导航
     */
    public static final String NAVI_ROBOT_MOVE_TO_RSP = "NAVI_ROBOT_MOVE_TO_RSP";

    /**
     * 设置速度
     */
    public static final String NAVI_ROBOT_SET_SPEED_RSP = "NAVI_ROBOT_SET_SPEED_RSP";

    /**
     * 特定点导航取消
     */
    public static final String NAVI_ROBOT_CANCEL_RSP = "NAVI_ROBOT_CANCEL_RSP";

    /**
     * 导航状态查询
     */
    public static final String NAVI_GET_STATUS_RSP = "NAVI_GET_STATUS_RSP";

    /**
     * 转向至特定角度
     */
    public static final String NAVI_GO_ROTATION_TO_RSP = "NAVI_GO_ROTATION_TO _RSP";

    /**
     * 步进角度
     */
    public static final String NAVI_GO_ROTATION_RSP = "NAVI_GO_ROTATION_RSP";

    /**
     * 回充电点位
     */
    public static final String NAVI_GO_HOME_RSP = "NAVI_GO_HOME_RSP";

    /**
     * 存储地图
     */
    public static final String NAVI_GET_MAP_RSP = "NAVI_GET_MAP_RSP";

    /**
     * 加载地图
     */
    public static final String NAVI_SET_MAP_RSP = "NAVI_SET_MAP_RSP";

    /**
     * The constant NAVI_GET_MAPLIST_RSP.
     */
    public static final String NAVI_GET_MAPLIST_RSP = "NAVI_GET_MAPLIST_RSP";

    /**
     * 速度设置
     */
    public static final String NAVI_ROBOT_SET_SPEED_REQ = "NAVI_ROBOT_SET_SPEED_REQ";

    /**
     * 速度获取
     */
    public static final String NAVI_ROBOT_GET_SPEED_RSP = "NAVI_ROBOT_GET_SPEED_RSP";

    /**
     *
     */
    public static final String NAVI_DEST_REACHABLE_RSP = "NAVI_DEST_REACHABLE_RSP";

    /**
     * 获取SN
     */
    public static final String GET_SN_RSP = "GET_SN_RSP";

    /**
     * 生成本地设备列表
     */
    public static final String GET_LOCAL_DEVICE_RSP = "GET_LOCAL_DEVICE_RSP";

    /**
     * 设置SN
     */
    public static final String SET_SN_RSP = "SET_SN_RSP";

    /**
     * 机器人关机
     */
    public static final String ROBOT_SHUTDOWN_REQ = "ROBOT_SHUTDOWN_RSP";

    /**
     * 机器人重启
     */
    public static final String ROBOT_REBOOT_REQ = "ROBOT_REBOOT_RSP";

    /**
     * 机器人电量获取
     */
    public static final String ROBOT_GET_BATTERY_RSP = "ROBOT_GET_BATTERY_RSP";

    /**
     * 充电状态获取
     */
    public static final String ROBOT_GET_CHARGE_RSP = "ROBOT_GET_CHARGE_RSP";

    /**
     * 自检
     */
    public static final String WARNING_CHECK_SELF_RSP = "WARNING_CHECK_SELF_RSP";


    /**
     * 获取硬件版本
     */
    public static final String GET_HARDWARE_INFO_RSP = "GET_HARDWARE_INFO_RSP";

    /**
     * 获取版本号
     */
    public static final String GET_VERSION_RSP = "GET_VERSION_RSP";

    /**
     * 获取版本号
     */
    public static final String GET_ROBOT_TYPE_RSP = "GET_ROBOT_TYPE_RSP";

    /**
     * 摆手开始
     */
    public static final String ROBOT_ARM_LOOP_START_RSP = "ROBOT_ARM_LOOP_START_RSP";

    /**
     * 摆手停止
     */
    public static final String ROBOT_ARM_LOOP_STOP_RSP = "ROBOT_ARM_LOOP_STOP_RSP";


    // 配置
    /**
     * 设置麦克风音量
     */
    public static final String SET_MICRO_VOLUME_RSP = "SET_MICRO_VOLUME_RSP";

    /**
     * 获取麦克风音量
     */
    public static final String GET_MICRO_VOLUME_RSP = "GET_MICRO_VOLUME_RSP";

    /**
     * linux软件版本检查
     */
    public static final String UPGRADE_CHECK_RSP = "UPGRADE_CHECK_RSP";

    /**
     * linux软件版本全量更新
     */
    public static final String UPGRADE_TOTAL_RSP = "UPGRADE_TOTAL_RSP";

    /**
     * The constant ROBOT_DANCE_START_RSP.
     */
    public static final String ROBOT_DANCE_START_RSP = "ROBOT_DANCE_START_RSP";

    /**
     * The constant ROBOT_DANCE_STOP_RSP.
     */
    public static final String ROBOT_DANCE_STOP_RSP = "ROBOT_DANCE_STOP_RSP";

    /**
     * The constant CUSTSERVICE_GET_RESULT_RSP.
     */
    public static final String CUSTSERVICE_GET_RESULT_RSP = "CUSTSERVICE_GET_RESULT_RSP";

    /**
     * The constant UPGRADE_ROBOT_EXPRESSION_RSP.
     */
    public static final String UPGRADE_ROBOT_EXPRESSION_RSP = "UPGRADE_ROBOT_EXPRESSION_RSP";

    /**
     * The constant FACE_SYNC_UNDO_REG_RSP.
     */
    public static final String FACE_SYNC_UNDO_REG_RSP = "FACE_SYNC_UNDO_REG_RSP";


    /**
     * 2.1.19.	热词查询
     * 接口说明
     * 查询当前已经上传的热词
     * <p>
     * 请求数据示例
     * {
     * "msg_id": "SPEECH_GET_USERWORDS_REQ"
     * }
     * 请求参数说明
     * 无
     * <p>
     * 返回结果示例
     * {
     * "msg_id":"SPEECH_GET_USERWORDS_RSP”,
     * "words": ["穿山甲", "机器人", "关键词"],
     * "error_code":0
     * }
     */
    public static final String SPEECH_GET_USERWORDS_RSP = "SPEECH_GET_USERWORDS_RSP";

    /**
     * The constant ROBOT_MAKE_SESSIONID_RSP.
     */
    public static final String ROBOT_MAKE_SESSIONID_RSP = "ROBOT_MAKE_SESSIONID_RSP";

    /**
     * The constant ROBOT_ELEVATOR_OPEN_RSP.
     */
    public static final String ROBOT_ELEVATOR_OPEN_RSP = "ROBOT_ELEVATOR_OPEN_RSP";
    /**
     * The constant ROBOT_ELEVATOR_CTRL_RSP.
     */
    public static final String ROBOT_ELEVATOR_CTRL_RSP = "ROBOT_ELEVATOR_CTRL_RSP";
    /**
     * The constant ROBOT_ELEVATOR_ROBOTID_RSP.
     */
    public static final String ROBOT_ELEVATOR_ROBOTID_RSP = "ROBOT_ELEVATOR_ROBOTID_RSP";
    /**
     * The constant ROBOT_ELEVATOR_INFO_RSP.
     */
    public static final String ROBOT_ELEVATOR_INFO_RSP = "ROBOT_ELEVATOR_INFO_RSP";


    /**
     * 是否在充电桩上
     */
    public static final String ROBOT_GET_DOCK_STATUS_RSP = "ROBOT_GET_DOCK_STATUS_RSP";

    /**
     * The constant SOFT_SHUT_DOWN_ALREADY_RSP.
     */
    public static final String SOFT_SHUT_DOWN_ALREADY_RSP = "SOFT_SHUT_DOWN_ALREADY_RSP";//软关机成功回复

    /**
     * The constant TIMING_POWER_OFF_RSP.
     */
    public static final String TIMING_POWER_OFF_RSP = "TIMING_POWER_OFF_RSP";//定时开关机成功回复
    public static final String GET_CURRENT_MAP_IMAGE_RSP = "GET_CURRENT_MAP_IMAGE_RSP";

    public static final String REMOTE_CTRL_OPEN_RSP = "REMOTE_CTRL_OPEN_RSP";
}
