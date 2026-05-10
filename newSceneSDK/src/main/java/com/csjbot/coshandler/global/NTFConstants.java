package com.csjbot.coshandler.global;

/**
 * NTF通知消息类型
 * NTF:由底层主动发送的通知信息
 * Created by jingwc on 2017/8/12.
 */
public class NTFConstants {

    ////////////////////////////语音部分
    /**
     * 2.1.8.语音及语义识别结果上报
     * {
     * "msg_id":"SPEECH_ISR_LAST_RESULT_NTF"
     * "result": {
     * “text”:”识别到观众讲的内容”
     * “answer”:{
     * “type”:0
     * “answer_text”:”回答的内容”
     * }
     * }
     * }
     */
    public static final String SPEECH_ISR_LAST_RESULT_NTF = "SPEECH_ISR_LAST_RESULT_NTF";


    /**
     * 人工客服 底层让机器人说话
     * {
     * "msg_id": "SPEECH_TTS_NTF",
     * "sid": 1521716485000,
     * "sn": "1234567890",
     * "text": "要让机器人说的话"
     * }
     */
    public static final String SPEECH_TTS_NTF = "SPEECH_TTS_NTF";


    /**
     * 人工客服 机器人停止说话
     * {
     * "msg_id": "SPEECH_READ_STOP_NTF",
     * "sid": 1521716485000,
     * "sn": "1234567890",
     * }
     */
    public static final String SPEECH_READ_STOP_NTF = "SPEECH_READ_STOP_NTF";

    /**
     * 人工客服 机器人复杂动作
     * {
     * "msg_id": "ROBOT_COMPLEX_ACTION_NTF",
     * "sid": 1521716485000,
     * "action": "ROBOT_ACTION_NOD"
     * }
     */
    public static final String ROBOT_COMPLEX_ACTION_NTF = "ROBOT_COMPLEX_ACTION_NTF";


    /**
     * 人工客服 机器人设置音量
     * {
     * "msg_id": "ROBOT_SET_VOLUME_NTF",
     * "sid": 1521716485000,
     * "volume": 1,
     * }
     */

    public static final String ROBOT_SET_VOLUME_NTF = "ROBOT_SET_VOLUME_NTF";
    /**
     * 机器人停止导航
     */
    public static final String HUMAN_SERVICE_STOP_ROBOT_NTF = "HUMAN_SERVICE_STOP_ROBOT_NTF";

    /**
     * 语音识别结果上报
     * {
     * "msg_id":"SPEECH_ISR_ONLY_RESULT_NTF"
     * “text”:”识别到观众讲的内容”，
     * }
     */
    public static final String SPEECH_ISR_ONLY_RESULT_NTF = "SPEECH_ISR_ONLY_RESULT_NTF";

    /**
     * 语音朗读结束通知
     */
    public static final String SPEECH_READ_OVER_NTF = "SPEECH_READ_OVER_NTF";

    /**
     * 语音唤醒通知
     * 当语音功能被唤醒时(语音唤醒/打开多次或者单次识别时)，会自行通知此消息。
     * {
     * "msg_id":"SPEECH_ISR_WAKEUP_NTF",
     * “wakeType”:0,.
     * “error_code”:0
     * }
     */
    public static final String SPEECH_ISR_WAKEUP_NTF = "SPEECH_ISR_WAKEUP_NTF";


    /**
     * 语音休眠通知
     * 当语音功能休眠时(语音休眠/关闭多次或者单次识别/开启语音服务后长时间没有操作时)，会自行通知此消息。
     */
    public static final String SPEECH_ISR_SLEEP_NTF = "SPEECH_ISR_SLEEP_NTF";

    /**
     * 语音异常通知
     * 当语音功能有异常时，会自行通知此消息。
     */
    public static final String SPEECH_AIUI_ERROR_NTF = "SPEECH_AIUI_ERROR_NTF";

    /**
     * 语音异常通知
     * 当语音功能有异常时，会自行通知此消息。
     */
    public static final String SPEECH_ISR_ERROR_NTF = "SPEECH_ISR_ERROR_NTF";

    /**
     * 用户语义语音识别结果
     * {
     * "msg_id":" SPEECH_CTRL_LAST_RESULT_NTF "
     * "result": {
     * “custom_cmd”:” 带我参观一下”
     * “action”:“NaviCmd”
     * “value”:“0,999”
     * “voiceValue”:” 好的”
     * }
     * }
     */
    public static final String SPEECH_CTRL_LAST_RESULT_NTF = "SPEECH_CTRL_LAST_RESULT_NTF ";


    ////////////////////////////人脸识别部分
    /**
     * 人脸感应信息上报
     * 摄像头附近是否有人脸靠近。此消息会在状态发生改变的时候自动推送。
     * 即person的值由true变为false或是由false变为true的时候自动推送，其他时间不推送。
     * <p>
     * {
     * "msg_id" : "FACE_DETECT_PERSON_NEAR_NTF",
     * "person" : true
     * }
     */
    public static final String FACE_DETECT_PERSON_NEAR_NTF = "FACE_DETECT_PERSON_NEAR_NTF";


    /**
     * 人脸识别信息上报
     * {
     * "msg_id" : "FACE_DETECT_FACE_LIST_NTF",
     * "face_num" : 2,
     * "face_list" : [
     * {
     * "face_detect" : {
     * "age" : 20,
     * "gender" : 2,
     * "smile" : 34
     * },
     * "face_recg" : {
     * "confidence" : 924,
     * "name" : "李和亮"
     * }
     * },
     * {
     * "face_detect" : {
     * "age" : 28,
     * "gender" : 2,
     * "smile" : 45
     * },
     * "face_recg" : {
     * "confidence" : 887,
     * "name" : "齐旭川",
     * “person_id”:”personx20170107161021mRJOVw”,
     * }
     * }
     */
    public static final String FACE_DETECT_FACE_LIST_NTF = "FACE_DETECT_FACE_LIST_NTF";


    ////////////////////////////上肢动作
    /**
     * 机器人肢体操作
     */
    public static final String ROBOT_BODY_CTRL_NTF = "ROBOT_BODY_CTRL_NTF";


    ////////////////////////////地盘以及导航
    /**
     * 特定点导航
     * 到达目的地后推送
     * {
     * "msg_id":"NAVI_ROBOT_MOVE_TO_NTF",
     * "pos": {
     * "x":10,
     * "y":200,
     * "z":25,
     * "rotation":1000
     * }
     * "error_code":0
     * }
     */
    public static final String NAVI_ROBOT_MOVE_TO_NTF = "NAVI_ROBOT_MOVE_TO_NTF";
    /**
     * 底层主动获取所有导航点名称
     * {"msg_id":"GET_ALL_NAVI_POINTS_NTF"}
     */
    public static final String GET_ALL_NAVI_POINTS_NTF = "GET_ALL_NAVI_POINTS_NTF";

    /**
     * 头部触摸上报(小雪机器人)
     */
    public static final String ROBOT_BODY_TOUCH_NTF = "ROBOT_BODY_TOUCH_NTF";


    /**
     * 2.9.6.机器人充电状态推送
     */
    public static final String ROBOT_CHARGE_STATE_NTF = "ROBOT_CHARGE_STATE_NTF";


    /**
     * 当机器人检测到人到来，或者人离开时，会推送此消息，
     * 需要注意的是，此消息与2.2.8人脸感应信息上报不同，
     * 该消息是综合多个传感器所得出的结论，而2.2.8是单独针对于摄像头的。
     * 返回结果示例
     * {
     * "msg_id":"DEVICE_DETECT_PERSON_NEAR_NTF",
     * "state":0,
     * “error_code”:0
     * }
     * 0:没人 1:有人
     */
    public static final String DEVICE_DETECT_PERSON_NEAR_NTF = "DEVICE_DETECT_PERSON_NEAR_NTF";


    /**
     * linux软件版本更新进度上报
     */
    public static final String UPGRADE_PROGRESS_NTF = "UPGRADE_PROGRESS_NTF";


    /**
     * 当用户长按机器人开关机按钮时，会主动推送此消息，用户可根据此消息来决定开机器人是否需要软关机。
     */
    public static final String ROBOT_SHUTDOWN_NTF = "ROBOT_SHUTDOWN_NTF";


    public static final String FACE_SYNC_UNDO_REG_NTF = "FACE_SYNC_UNDO_REG_NTF";

    public static final String NAVI_GO_ROTATION_NTF = "NAVI_GO_ROTATION_NTF";

    public static final String NAVI_ROBOT_CHARGE_FAILURE_NTF = "NAVI_ROBOT_CHARGE_FAILURE_NTF";

    public static final String FACE_DETECT_COORDINATE_NTF = "FACE_DETECT_COORDINATE_NTF";


    /**
     * 过载消息推送
     */
    public static final String MOTOR_OVERLOAD_NTF = "MOTOR_OVERLOAD_NTF";

    /**
     * 急停推送
     */
    public static final String ROBOT_GET_EMERGENCY_NTF = "ROBOT_GET_EMERGENCY_NTF";

    public static final String ROBOT_ELEVATOR_CTRL_NTF = "ROBOT_ELEVATOR_CTRL_NTF";

    public static final String DEVICE_DOOR_CONTROL_NTF = "DEVICE_DOOR_CONTROL_NTF";

    /**
     * 定位质量差推送
     */
    public static final String NAVI_ROBOT_LQ_LOW_NTF = "NAVI_ROBOT_LQ_LOW_NTF";

    /**
     * 定位质量恢复
     */
    public static final String NAVI_ROBOT_LQ_NORMAL_NTF = "NAVI_ROBOT_LQ_NORMAL_NTF";
    //开关机状态
    public static final String ROBOT_POWER_STATUS_NTF = "ROBOT_POWER_STATUS_NTF";

    public static final String MQTT_CLIENT_MESSAGE_ARRIVED_NTF = "MQTT_CLIENT_MESSAGE_ARRIVED_NTF";

    public static final String MQTT_CLIENT_STATE_CHANGED_NTF = "MQTT_CLIENT_STATE_CHANGED_NTF";


    public static final String NAVI_ROBOT_BLOCKED_NTF = "NAVI_ROBOT_BLOCKED_NTF";
    public static final String NAVI_ROBOT_WAITSHORT_NTF = "NAVI_ROBOT_WAITSHORT_NTF";
    public static final String NAVI_ROBOT_WAITLONG_NTF = "NAVI_ROBOT_WAITLONG_NTF";
    public static final String NAVI_ROBOT_RUNNING_NTF = "NAVI_ROBOT_RUNNING_NTF";

    public static final String REMOTE_CTRL_KEY_NTF = "REMOTE_CTRL_KEY_NTF";

    public static final String ROBOT_SERIAL_POWER_STATE_NTF = "ROBOT_SERIAL_POWER_STATE_NTF";
    public static final String NAVI_ROBOT_CONNECTION_STATE_NTF = "NAVI_ROBOT_CONNECTION_STATE_NTF";
}
