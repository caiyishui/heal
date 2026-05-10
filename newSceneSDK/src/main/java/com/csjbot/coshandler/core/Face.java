package com.csjbot.coshandler.core;

import android.content.Context;

import com.csjbot.coshandler.client_req.ReqFactory;
import com.csjbot.coshandler.client_req.face.IFaceReq;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;

/**
 * Created by Administrator on 2019/7/15.
 */

public class Face implements IFaceReq {

    private IFaceReq faceReq;

    Face() {
        faceReq = ReqFactory.getFaceReqInstance();
    }

    @Override
    public void openVideo() {
        faceReq.openVideo();
    }

    @Override
    public void closeVideo() {
        faceReq.closeVideo();
    }

    @Override
    public void startFaceService() {
        faceReq.startFaceService();
    }

    @Override
    public void closeFaceService() {
        faceReq.closeFaceService();
    }

    @Override
    public void prepareReg() {
        faceReq.prepareReg();
    }

    @Override
    public void faceRegEnd() {
        faceReq.faceRegEnd();
    }

    @Override
    public void snapshot(OnSnapshotoListener listener) {
        faceReq.snapshot(listener);
    }

    @Override
    public void saveFace(String name, OnFaceSaveListener listener) {
        faceReq.saveFace(name, listener);
    }

    @Override
    public void faceDel(String faceId) {
        faceReq.faceDel(faceId);
    }

    @Override
    public void faceDelList(String faceIdsJson) {
        faceReq.faceDelList(faceIdsJson);
    }

    @Override
    public void faceInfoChanged(String faceId) {
        faceReq.faceInfoChanged(faceId);
    }

    @Override
    public void faceInfoAdd(String reguserId, String status) {
        faceReq.faceInfoAdd(reguserId, status);
    }

    @Override
    public void getFaceDatabase(OnGetAllFaceListener listener) {
        faceReq.getFaceDatabase(listener);
    }

    @Override
    public void syncFaceData() {
        faceReq.syncFaceData();
    }

    @Override
    public void openPreView(Context context, int x, int y, int w, int h) {
        faceReq.openPreView(context, x, y, w, h);
    }

    @Override
    public void closePreView(Context context) {
        faceReq.closePreView(context);
    }

    @Override
    public void resumePreview(Context context) {
        faceReq.resumePreview(context);
    }

    @Override
    public void pausePreview(Context context) {
        faceReq.pausePreview(context);
    }
}
