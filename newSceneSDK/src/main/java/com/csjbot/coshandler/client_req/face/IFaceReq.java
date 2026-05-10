package com.csjbot.coshandler.client_req.face;


import android.content.Context;

import com.csjbot.coshandler.client_req.base.IClientReq;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;

/**
 * 人脸识别接口
 * Created by jingwc on 2017/8/14.
 */

public interface IFaceReq extends IClientReq {

    /**
     * 打开摄像头
     */
    void openVideo();

    /**
     * 关闭摄像头
     */
    void closeVideo();

    /**
     * 启动人脸识别服务
     */
    void startFaceService();

    /**
     * 关闭人脸识别服务
     */
    void closeFaceService();

    /**
     * 人脸注册准备
     */
    void prepareReg();

    /**
     * 人脸注册结束
     */
    void faceRegEnd();

    /**
     * 摄像头拍照
     */
    void snapshot(OnSnapshotoListener listener);

    /**
     * 人脸注册
     */
    void saveFace(String name, OnFaceSaveListener listener);

    /**
     * 人脸信息删除
     *
     * @param faceId
     */
    void faceDel(String faceId);

    /**
     * 人脸信息批量删除
     *
     * @param faceIdsJson
     */
    void faceDelList(String faceIdsJson);

    /**
     * 人脸信息变更
     *
     * @param faceId
     */
    void faceInfoChanged(String faceId);

    /**
     * 人脸信息新增
     *
     * @param reguserId
     */
    void faceInfoAdd(String reguserId, String status);

    /**
     * 获取人脸数据库
     */
    void getFaceDatabase(OnGetAllFaceListener listener);

    /**
     * 人脸信息同步
     */
    void syncFaceData();

    /**
     * 开启预览
     *
     * @param context 上下文
     * @param x       预览出现的x坐标
     * @param y       预览出现的y坐标
     * @param w       预览出现的宽
     * @param h       预览出现的高
     */
    void openPreView(Context context, int x, int y, int w, int h);

    /**
     * 关闭预览
     *
     * @param context 上下文
     */
    void closePreView(Context context);

    /**
     * 恢复预览，用于注册
     *
     * @param context 上下文
     */
    void resumePreview(Context context);

    /**
     * 暂停预览，用于注册
     *
     * @param context 上下文
     */
    void pausePreview(Context context);
}
