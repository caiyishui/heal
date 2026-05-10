package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;

/**
 * 负责人脸识别模块消息的处理
 * Created by jingwc on 2017/8/12.
 */

public class RbFace extends RbBase {

    @Override
    protected void handleNTFMessage(String dataSource, String msgId) {
        switch (msgId){
            case NTFConstants.FACE_DETECT_PERSON_NEAR_NTF:// 检测到有人脸靠近
//                CosLogger.debug("RbFace-------->FACE_DETECT_PERSON_NEAR_NTF");
                String value = getSingleField(dataSource,"person");
//                Robot.sessionId = getSingleField(dataSource,"session_id");
                // person = true 检测有人脸出现
                // person = false 检测区域没有检测到人
                CsjRobot.getInstance().pushFace(Boolean.valueOf(value));
                break;
            case NTFConstants.FACE_DETECT_FACE_LIST_NTF:// 识别到人脸信息
                // 推送检测到的人脸识别信息
//                CosLogger.debug("RbFace-------->FACE_DETECT_FACE_LIST_NTF");
                CsjRobot.getInstance().pushFace(dataSource);
                break;
            case NTFConstants.FACE_SYNC_UNDO_REG_NTF:
                break;
            case NTFConstants.FACE_DETECT_COORDINATE_NTF:
                CsjRobot.getInstance().pushFaceCoordinate(dataSource);
                break;
            default:
                break;
        }
    }

    @Override
    protected void handleRSPMessage(String dataSource, String msgId) {

        switch (msgId){
            case RSPConstants.FACE_SAVE_RSP:
                CsjRobot.getInstance().pushFaceSave(dataSource);
                break;
            case RSPConstants.FACE_DATABASE_RSP:
                CsjRobot.getInstance().pushFaceList(dataSource);
                CosLogger.debug("FACE_DATABASE_RSP------------------->json:"+dataSource);
                break;
            case RSPConstants.FACE_SNAPSHOT_RESULT_RSP:
                CsjRobot.getInstance().pushSnapshoto(dataSource);
                break;
            case RSPConstants.FACE_SYNC_UNDO_REG_RSP:
                break;
            default:
                break;
        }
    }
}
