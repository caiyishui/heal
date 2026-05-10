package com.csjbot.coshandler.handle_msg.task;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.log.CsjlogProxy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息处理Task基类
 * Created by jingwc on 2017/8/12.
 */

public abstract class RbBase implements Runnable {

    private String dataSource;
    private String msgId;

    @Override
    public void run() {
        handleBefore();
    }

    /**
     * run方法一开始执行的操作
     */
    private void handleBefore() {
        if (msgId.contains("NTF")) {
            handleNTFMessage(dataSource, msgId);
        } else if (msgId.contains("RSP")) {
            handleRSPMessage(dataSource, msgId);
        } else if (msgId.contains("RESP")) {
            handleRSPMessage(dataSource, msgId);
        }
    }

    /**
     * 解析单个字段并返回
     *
     * @param json
     * @param field
     * @return
     */
    protected String getSingleField(String json, String field) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getString(field);
        } catch (JSONException e) {
            CosLogger.error("RbBase:getSingleField:e" + e.toString());
        }
        return null;
    }


    /**
     * 解析字符串数组并且返回
     *
     * @param json
     * @param field
     * @return
     */
    protected List<String> getStringListField(String json, String field) {
        List<String> out = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(field);
            for (int i = 0; i < jsonArray.length(); i++) {
                out.add(jsonArray.getString(i));
//                CsjlogProxy.getInstance().warn(jsonArray.getString(i));
            }

        } catch (JSONException e) {
            CosLogger.error("RbBase:getSingleField:e" + e.toString());
        }

        return out;
    }

    /**
     * 解析单个字段并返回
     *
     * @param json
     * @param field
     * @return
     */
    protected int getIntSingleField(String json, String field) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getInt(field);
        } catch (JSONException e) {
            CosLogger.error("RbBase:getSingleField:e" + e.toString());
        }
        return 0;
    }

    protected boolean getBooleanSingleField(String json, String field) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.optBoolean(field);
        } catch (JSONException e) {
            CosLogger.error("RbBase:getSingleField:e" + e.toString());
        }
        return false;
    }

    /**
     * 解析单个字段并返回
     *
     * @param json
     * @param field
     * @return
     */
    protected double getDoubleSingleField(String json, String field) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject.getDouble(field);
        } catch (JSONException e) {
            CosLogger.error("RbBase:getSingleField:e" + e.toString());
        }
        return 0;
    }

    /**
     * 处理NTF消息
     *
     * @param dataSource
     * @param msgId
     */
    protected abstract void handleNTFMessage(String dataSource, String msgId);

    /**
     * 处理RSP消息
     *
     * @param dataSource
     * @param msgId
     */
    protected abstract void handleRSPMessage(String dataSource, String msgId);

    public void setDataSource(String json) {
        this.dataSource = json;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
