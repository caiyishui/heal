package com.csjbot.coshandler.client_req.face;


import android.content.Context;
import android.content.Intent;

import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;

/**
 * 人脸识别实现类
 * Created by jingwc on 2017/8/14.
 */

public class FaceReqImpl extends BaseClientReq implements IFaceReq {

    @Override
    public void openVideo() {
        sendReq(getJson(REQConstants.FACE_DETECT_OPEN_VIDEO_REQ));
    }

    @Override
    public void closeVideo() {
        sendReq(getJson(REQConstants.FACE_DETECT_CLOSE_VIDEO_REQ));
    }

    @Override
    public void startFaceService() {
        sendReq(getJson(REQConstants.FACE_DETECT_SERVICE_START_REQ));
    }

    @Override
    public void closeFaceService() {
        sendReq(getJson(REQConstants.FACE_DETECT_SERVICE_STOP_REQ));
    }

    @Override
    public void prepareReg() {
        sendReq(getJson(REQConstants.FACE_REG_START_REQ));
    }

    @Override
    public void faceRegEnd() {
        sendReq(getJson(REQConstants.FACE_REG_STOP_REQ));
    }

    @Override
    public void snapshot(OnSnapshotoListener listener) {
        CsjRobot.getInstance().setOnSnapshotoListener(listener);
        sendReq(getJson(REQConstants.FACE_SNAPSHOT_REQ));
    }

    @Override
    public void saveFace(String name, OnFaceSaveListener listener) {
        CsjRobot.getInstance().setOnFaceSaveListener(listener);
        sendReq(getJson(REQConstants.FACE_SAVE_REQ, "name", name));
    }

    @Override
    public void faceDel(String faceId) {
        sendReq(getJson(REQConstants.FACE_DATA_DEL_REQ, "person_id", faceId));
    }

    @Override
    public void faceDelList(String faceIdsJson) {
        sendReq(getJsonFromJsonContent(REQConstants.FACE_DATA_DELETE_LIST_REQ, "person_id", faceIdsJson));
    }

    @Override
    public void faceInfoChanged(String faceId) {
        sendReq(getJson(REQConstants.FACE_DATA_CHANGED_REQ, "person_id", faceId));
    }

    @Override
    public void faceInfoAdd(String reguserId, String status) {
        String json = "{\"msg_id\":\"" + REQConstants.FACE_INFO_SYNC_REQ
                + "\",\"reguser_id\": " + reguserId + ",\"status\":\"" + status + "\"}";
        sendReq(json);
    }

    @Override
    public void getFaceDatabase(OnGetAllFaceListener listener) {
        CsjRobot.getInstance().setOnGetAllFaceListener(listener);
        sendReq(getJson(REQConstants.FACE_DATABASE_REQ));
    }

    @Override
    public void syncFaceData() {
        sendReq(getJson(REQConstants.FACE_SYNC_UNDO_REG_REQ));
    }

    @Override
    public void openPreView(Context context, int x, int y, int w, int h) {
        Intent intent = new Intent();
        intent.setAction("FaceDetectService");
        intent.putExtra("action", 0);
//        if (isPlus()) {
//            intent.putExtra("x", 240);
//            intent.putExtra("y", 240);
//        } else {
//            intent.putExtra("x", 1040);
//            intent.putExtra("y", 270);
//        }

        intent.putExtra("x", x);
        intent.putExtra("y", y);
        intent.putExtra("w", w);
        intent.putExtra("h", h);
        context.sendBroadcast(intent);
    }

    @Override
    public void closePreView(Context context) {
        Intent intent = new Intent();
        intent.setAction("FaceDetectService");
        intent.putExtra("action", 0);
        intent.putExtra("x", 0);
        intent.putExtra("y", 0);
        intent.putExtra("w", 1);
        intent.putExtra("h", 1);
        context.sendBroadcast(intent);
    }

    @Override
    public void resumePreview(Context context) {
        Intent intent = new Intent();
        intent.setAction("FaceDetectService");
        intent.putExtra("action", 1);
        context.sendBroadcast(intent);
    }

    @Override
    public void pausePreview(Context context) {
        Intent intent = new Intent();
        intent.setAction("FaceDetectService");
        intent.putExtra("action", 2);
        context.sendBroadcast(intent);
    }
}
