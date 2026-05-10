package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;

/**
 * Created by jingwc on 2017/9/20.
 */

public class RbChassis extends RbBase {

    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        switch (msgId) {
            case NTFConstants.NAVI_ROBOT_MOVE_TO_NTF:
                CosLogger.debug("RbChassis--->NAVI_ROBOT_MOVE_TO_NTF");
                CsjRobot.getInstance().pushNaviMoveResult(dataSource);
                CosLogger.debug("msgid=" + msgId + "\ndataSource=" + dataSource);
                break;
            case NTFConstants.GET_ALL_NAVI_POINTS_NTF:
                CosLogger.info("GET_ALL_NAVI_POINTS_NTF ");
                CsjRobot.getInstance().pushNaviPoints();
                break;
            case NTFConstants.NAVI_GO_ROTATION_NTF:
                CsjRobot.getInstance().pushGoRotation(getIntSingleField(dataSource, "error_code"));
                break;
            case NTFConstants.NAVI_ROBOT_CHARGE_FAILURE_NTF:
                CsjRobot.getInstance().pushChargeFailure();
                break;
            case NTFConstants.NAVI_ROBOT_LQ_LOW_NTF:
                CsjRobot.getInstance().pushLQStateLow(getIntSingleField(dataSource, "lq"));
                break;
            case NTFConstants.NAVI_ROBOT_LQ_NORMAL_NTF:
                CsjRobot.getInstance().pushLQStateNormal(getIntSingleField(dataSource, "lq"));
                break;
            case NTFConstants.NAVI_ROBOT_BLOCKED_NTF:
                CsjRobot.getInstance().pushBlockedState();
                break;
            case NTFConstants.NAVI_ROBOT_WAITSHORT_NTF:
                CsjRobot.getInstance().pushWaitShortStatus();
                break;
            case NTFConstants.NAVI_ROBOT_WAITLONG_NTF:
                CsjRobot.getInstance().pushWaitLongStatus();
                break;
            case NTFConstants.NAVI_ROBOT_RUNNING_NTF:
                CsjRobot.getInstance().pushRunningStatus();
                break;
            case NTFConstants.NAVI_ROBOT_CONNECTION_STATE_NTF:
                CsjRobot.getInstance().pushConnectState(getBooleanSingleField(dataSource, "state"));
                break;
            case "NAVI_ROBOT_STATES_NTF":
                boolean naviReady = getBooleanSingleField(dataSource, "naviReady");
                int motion_mode = getIntSingleField(dataSource, "motion_mode");
                CsjRobot.getInstance().pushRobotNaviStates(naviReady, motion_mode);
                break;

        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {
        int errorCode = getIntSingleField(dataSource, "error_code");
        switch (msgId) {
            case RSPConstants.NAVI_GET_MAP_RSP:
                CsjRobot.getInstance().pushOnMapSaveListener(getIntSingleField(dataSource, "error_code"));
                break;
            case RSPConstants.NAVI_SET_MAP_RSP:
                CsjRobot.getInstance().pushOnMapLoadListener(getIntSingleField(dataSource, "error_code"));
                break;
            case RSPConstants.NAVI_GET_MAPLIST_RSP:
                CsjRobot.getInstance().pushMapList(dataSource);
                break;
            case RSPConstants.NAVI_GET_CURPOS_RSP:
                CsjRobot.getInstance().pushPosition(dataSource);
                CosLogger.debug("RbChassis--->NAVI_GET_CURPOS_RSP" + dataSource);
                break;
            case RSPConstants.NAVI_GO_HOME_RSP:
                CsjRobot.getInstance().pushNaviGohomeResult(dataSource);
                CosLogger.debug("RbChassis--->NAVI_GO_HOME_RSP");
                break;
            case RSPConstants.NAVI_ROBOT_MOVE_RSP:
                CosLogger.debug("RbChassis--->NAVI_ROBOT_MOVE_RSP");
                break;
            case RSPConstants.NAVI_ROBOT_MOVE_TO_RSP:
                CsjRobot.getInstance().pushNaviMessageSendResult(dataSource);
                CosLogger.debug("RbChassis--->NAVI_ROBOT_MOVE_TO_RSP");
                break;
            case RSPConstants.NAVI_ROBOT_CANCEL_RSP:
                CosLogger.debug("RbChassis--->NAVI_ROBOT_CANCEL_RSP");
                CsjRobot.getInstance().pushNaviCancelResult(dataSource);
                break;
            case RSPConstants.NAVI_GO_ROTATION_TO_RSP:
                CosLogger.debug("RbChassis--->NAVI_GO_ROTATION_TO_RSP");
                break;
            case RSPConstants.NAVI_GO_ROTATION_RSP:
                CosLogger.debug("RbChassis--->NAVI_GO_ROTATION_RSP");
                break;
            case RSPConstants.NAVI_ROBOT_SET_SPEED_RSP:
                CosLogger.debug("RbChassis--->NAVI_ROBOT_SET_SPEED_RSP");
                break;
            case RSPConstants.NAVI_GET_STATUS_RSP:
                CosLogger.debug("RbChassis--->NAVI_GET_STATUS_RSP\n" + dataSource);
                CsjRobot.getInstance().pushNaviSearch(dataSource);
                break;
            case RSPConstants.NAVI_ROBOT_GET_SPEED_RSP:
                CosLogger.debug("RbChassis--->NAVI_ROBOT_GET_SPEED_RSP\n" + dataSource);
                double speed = getDoubleSingleField(dataSource, "speed");
                CsjRobot.getInstance().pushNaviSpeed(speed);
                break;
            case "NAVI_GET_MAPSTATUS_RSP":
                CosLogger.debug("RbChassis--->NAVI_GET_MAPSTATUS_RSP\n" + dataSource);
                CsjRobot.getInstance().pushNaviMapState(dataSource);
                break;
            case RSPConstants.ROBOT_NAVI_CTRL_RSP:
                CsjRobot.getInstance().pushRobotCtrl(getIntSingleField(dataSource, "data"));
                break;
            case RSPConstants.NAVI_DEST_REACHABLE_RSP:
                CsjRobot.getInstance().pushDestReachable(getBooleanSingleField(dataSource, "destReachable"));
                break;
            default:
                break;
        }
    }
}
