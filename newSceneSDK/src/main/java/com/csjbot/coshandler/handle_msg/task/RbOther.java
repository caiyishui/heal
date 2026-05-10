package com.csjbot.coshandler.handle_msg.task;

import android.graphics.Bitmap;

import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;
import com.csjbot.coshandler.log.CosLogger;
import com.csjbot.coshandler.log.CsjlogProxy;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jingwc on 2017/11/21.
 */

public class RbOther extends RbBase {
    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        switch (msgId) {
            case NTFConstants.GET_ALL_NAVI_POINTS_NTF:
                CosLogger.info("GET_ALL_NAVI_POINTS_NTF ");
                CsjRobot.getInstance().pushNaviPoints();
                break;
            case NTFConstants.ROBOT_CHARGE_STATE_NTF:
                CsjRobot.getInstance().pushCharge(getIntSingleField(dataSource, "charge_state"));
                break;
            case NTFConstants.DEVICE_DETECT_PERSON_NEAR_NTF:
                CsjRobot.getInstance().pushDetectPerson(getIntSingleField(dataSource, "state"));
                break;
            case NTFConstants.ROBOT_SHUTDOWN_NTF:
                break;
            case NTFConstants.DEVICE_DOOR_CONTROL_NTF:
                int downState = getIntSingleField(dataSource, "state");
                int upState = getIntSingleField(dataSource, "upState");
                CsjRobot.getInstance().pushDoubleDoorState(upState, downState);
//                CsjRobot.getInstance().pushDoorControl(getIntSingleField(dataSource, "state"));
                break;
            case NTFConstants.ROBOT_POWER_STATUS_NTF:
                int power_status = getIntSingleField(dataSource, "power_status");
                CsjRobot.getInstance().pushPowerStatus(power_status);
                break;
            case NTFConstants.MQTT_CLIENT_STATE_CHANGED_NTF:
                int error_code = getIntSingleField(dataSource, "error_code");
                CsjRobot.getInstance().pushMqttState(error_code);
                break;
            case NTFConstants.MOTOR_OVERLOAD_NTF:
                CsjRobot.getInstance().pushMotoOverload(getIntSingleField(dataSource, "error_code"));
                break;

            case NTFConstants.ROBOT_GET_EMERGENCY_NTF:
                CsjRobot.getInstance().pushEmergencyStatus(getIntSingleField(dataSource, "status"));
                break;
            case NTFConstants.MQTT_CLIENT_MESSAGE_ARRIVED_NTF:
                try {
                    JSONObject jsonObject = new JSONObject(dataSource);
                    String data = jsonObject.getString("data");
                    CsjRobot.getInstance().pushWebSocketData(data);
                } catch (Exception e) {
                }
                break;
            case NTFConstants.ROBOT_COMPLEX_ACTION_NTF:
            case NTFConstants.ROBOT_SET_VOLUME_NTF:
            case NTFConstants.HUMAN_SERVICE_STOP_ROBOT_NTF:
            case "ROBOT_MOVE_NTF":
            case "ROBOT_EXPRESS_NTF":
            case "ROBOT_BODY_NTF":
            case "ROBOT_COMPLEX_ACTION_REQ":
            case "ROBOT_WEB_MOVE_NTF":
            case "ROBOT_HUMAN_INTERVENTI_NTF":
            case "ROBOT_HANGUP_HUMAN_INTERVENTI_NTF":
            case "ROBOT_VIDEO_STATUS_NTF":
            case "ASR_START_AUDIO":
            case "ASR_STOP_AUDIO":

                CsjRobot.getInstance().pushCustomServiceMsg(dataSource);
                break;

            case NTFConstants.REMOTE_CTRL_KEY_NTF: {
                int keyCode = getIntSingleField(dataSource, "keyCode");
                int keyValue = getIntSingleField(dataSource, "keyValue");
                CsjRobot.getInstance().pushRemoteCtrl(keyCode, keyValue);
            }
            break;
            case NTFConstants.ROBOT_SERIAL_POWER_STATE_NTF: {
                int powerState = getIntSingleField(dataSource, "powerState");
                int powerValue = getIntSingleField(dataSource, "powerValue");
                CsjRobot.getInstance().pushSerialPowerInfo(powerState, powerValue);
            }
            break;
            case "ROBOT_ERROR_NTF":
                String type = getSingleField(dataSource, "type");
                CsjRobot.getInstance().pushErrorInfo(type, getSingleField(dataSource, "msg"));
                break;
            default:
//                CsjlogProxy.getInstance().debug("RbOther default [" + msgId + "]");
                break;
        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        switch (msgId) {
            case RSPConstants.GET_MICRO_VOLUME_RSP:
                CsjRobot.getInstance().pushMicroVolume(getIntSingleField(dataSource, "volume"));
                break;
            case RSPConstants.CUSTSERVICE_GET_RESULT_RSP:
                CsjRobot.getInstance().pushSpeechGetResult(dataSource);
                break;
            case RSPConstants.WARNING_CHECK_SELF_RSP:
                CsjRobot.getInstance().pushWarningCheckSelf(dataSource);
                break;
            case RSPConstants.GET_HARDWARE_INFO_RSP:
                break;
            case RSPConstants.GET_ROBOT_TYPE_RSP:
                CsjRobot.getInstance().pushRobotType(getSingleField(dataSource, "robot_type"));
                break;
            case RSPConstants.ROBOT_MAKE_SESSIONID_RSP:
                break;
            case RSPConstants.ROBOT_GET_CHARGE_RSP:
                CsjRobot.getInstance().pushCharge(getIntSingleField(dataSource, "charge"));
                break;
            case RSPConstants.MOTOR_OVERLOAD_STATUS_RSP:
                CsjRobot.getInstance().pushMotoOverload(getIntSingleField(dataSource, "status"));
                break;
            case RSPConstants.GET_SLAM_VERSION_RSP:
                CsjRobot.getInstance().pushSlamVersion(dataSource);
                break;
            case RSPConstants.DEVICE_DOOR_STATUS_RSP:
                int downState = getIntSingleField(dataSource, "state");
                int upState = getIntSingleField(dataSource, "upState");
                CsjRobot.getInstance().pushDoubleDoorState(upState, downState);
                break;
            case RSPConstants.ROBOT_GET_DOCK_STATUS_RSP:
                CsjRobot.getInstance().pushRobotDockState(getIntSingleField(dataSource, "state"));
                break;
            case RSPConstants.SOFT_SHUT_DOWN_ALREADY_RSP:
//                Robot.getInstance().pushSoftShutDownRSP();
                break;
            case RSPConstants.TIMING_POWER_OFF_RSP:
//                Robot.getInstance().pushTimingPowerOffRSP();
                break;
            case RSPConstants.GET_CURRENT_MAP_IMAGE_RSP:
                try {
                    JSONObject jsonObject = new JSONObject(dataSource);
                    int errorCode = jsonObject.getInt("errorCode");
                    String current_map = jsonObject.getString("current_map");
                    if (errorCode == 0) {
                        String data = jsonObject.getString("data");
                        CsjRobot.getInstance().pushCurrentMap(current_map, data, errorCode);
                    } else {
                        CsjRobot.getInstance().pushCurrentMap(current_map, null, errorCode);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case RSPConstants.GET_SENSOR_HEALTH_RSP:
                CsjRobot.getInstance().pushSensorHealth(getSingleField(dataSource, "data"));
                break;
            case RSPConstants.REMOTE_CTRL_OPEN_RSP:
                CosLogger.debug(dataSource);
                break;

            default:
                break;
        }
    }
}
