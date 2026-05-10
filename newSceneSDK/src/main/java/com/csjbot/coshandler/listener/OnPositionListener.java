package com.csjbot.coshandler.listener;


/**
 * Created by jingwc on 2017/9/21.
 */
public interface OnPositionListener {
    /**
     * Position info.
     *
     * @param json the json
     *  {
     *             "error_code": 0,
     *             "msg_id": "NAVI_GET_CURPOS_RSP",
     *             "rotation": 0,
     *             "x": 0,
     *             "y": 0,
     *             "z": 0
     *  }
     */
    void positionInfo(String json);
}
