package com.csjbot.coshandler.client_req.base;

import android.text.TextUtils;
import android.util.Log;

import com.csjbot.cosclient.CosClientAgent;
import com.csjbot.cosclient.entity.CommonPacket;
import com.csjbot.cosclient.entity.MessagePacket;
import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.service.HandlerMsgService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 请求基类
 * Created by jingwc on 2017/8/14.
 */

public abstract class BaseClientReq {
    /**
     * 发送请求
     *
     * @param json
     */
    protected void sendReq(String json) {
        if (TextUtils.isEmpty(json)) return;
//        CsjlogProxy.getInstance().info(json);
        try {
            MessagePacket packet = new CommonPacket(json.getBytes());
//            CosClientAgent.getRosClientAgent().sendMessage(packet);
            HandlerMsgService.sendMessageToSDKApp(json);
            if (directListener != null) {
                directListener.onSendMessage(json);
            }
        } catch (Exception e) {
            CosLogger.error("BaseClientReq:sendReq:e:" + e.toString());
        }
    }


    private static DirectListener directListener = null;

    public static void setDirectListener(DirectListener listener) {
        directListener = listener;
    }

    /**
     * 转换只包含msgid字段的json
     *
     * @param msgId
     * @return
     */
    protected String getJson(String msgId) {
        return "{\"msg_id\":\"" + msgId + "\"}";
    }

    protected String getJson(String msgId, String key1, int value1, String key2, int value2) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", msgId);
            object.put(key1, value1);
            object.put(key2, value2);
            json = object.toString();
        } catch (Exception e) {

        }

        return json;
    }

    protected String getXRobotTimeJson(String year, String month, String day, String hour, String minute, String second, String week) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", REQConstants.SET_TIME_REQ);
            object.put("year", year);
            object.put("month", month);
            object.put("day", day);
            object.put("hour", hour);
            object.put("minute", minute);
            object.put("second", second);
            object.put("week", week);
            json = object.toString();

        } catch (Exception e
        ) {

        }

        return json;
    }

    protected String getXTimingRobotJson(boolean isOpen, String year, int month, int day, int hour, int minute, int second) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", REQConstants.SET_X_TIMING_BOOT);
            object.put("needTimingOpen", isOpen);
            object.put("year", year);
            object.put("month", month);
            object.put("day", day);
            object.put("hour", hour);
            object.put("minute", minute);
            object.put("second", second);
            json = object.toString();
        } catch (Exception e) {

        }
        return json;
    }

    protected String getDoorCtrlJson(boolean open, int floor) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", REQConstants.DOUBLE_DOORS_CONTROL);
            object.put("open", open);
            object.put("floor", floor);
            json = object.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 转换包含msgid和一个字段的json
     *
     * @param msgId
     * @param field
     * @param value
     * @return
     */
    protected String getJson(String msgId, String field, String value) {
        return "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":\"" + value + "\"}";
    }

    protected String getJsonFromJsonContent(String msgId, String field, String jsonContent) {
        return "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + jsonContent + "}";
    }

    protected String getJson(String msgId, String k1, String v1, String k2, String v2, String k3, String v3) {
        String json = "";
        try {
            JSONObject obj = new JSONObject();
            obj.put("msg_id", msgId);
            obj.put(k1, v1);
            obj.put(k2, v2);
            obj.put(k3, v3);
            json = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    protected String getSendDataJson(String json) {
        String json2 = "";
        JSONObject obg = new JSONObject();
        try {
            obg.put("msg_id", REQConstants.SEND_TO_MESSAGE_DATESOCKET);
            obg.put("data", json);
            json2 = obg.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json2;
    }
    protected String getMQTTSendDataJson(String json) {
        String json2 = "";
        JSONObject obg = new JSONObject();
        try {
            obg.put("msg_id", "MQTT_SEND_MSG_REQ");
            obg.put("payload", json);
            json2 = obg.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json2;
    }

    /**
     * 转换包含msgid和一个字段的json
     *
     * @param msgId
     * @param field
     * @param value
     * @return
     */
    protected String getJson(String msgId, String field, int value) {
        String json = "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + value + "}";
        return json;
    }

    /**
     * 转换包含msgid和一个字段的json
     *
     * @param msgId
     * @param field
     * @param value
     * @return
     */
    protected String getJson(String msgId, String field, float value) {
        String json = "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + value + "}";
        return json;
    }

    /**
     * 转换包含msgid和一个json的json
     *
     * @param msgId
     * @param field
     * @param json
     * @return
     */
    protected String getChassisJson(String msgId, String field, String json) {
        CosLogger.debug("{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + json + "}");
        return "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + json + "}";
    }

    /**
     * 转换肢体动作的json
     *
     * @param msgId
     * @param bodyPart
     * @param action
     * @return
     */
    protected String getBodyActionJson(String msgId, int bodyPart, int action) {
        String json = "{\"msg_id\":\"" + msgId + "\",\"body_part\":" + bodyPart + ",\"action\":" + action + "}";
        return json;
    }

    protected String getNewBodyActionJson(String msgId, int bodyPart, int action, int angle, int speed) {
        String json = "{\"msg_id\":\"" + msgId + "\",\"part\":" + bodyPart + ",\"direction\":" + action + ",\"angle\":" + angle + ",\"speed\":" + speed + "}";
        return json;
    }

    protected String getExpressionJson(String msgId, int expression, int once, int time) {
        String json = "{\"msg_id\":\"" + msgId + "\",\"expression\":" + expression + ",\"once\":" + once + ",\"time\":" + time + "}";
        return json;
    }


    protected String getHotWordJson(String msgId, List<String> hotwords) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hotwords.size(); i++) {
            sb.append("\"").append(hotwords.get(i)).append("\"");
            if (i != hotwords.size() - 1) {
                sb.append(",");
            }
        }
        String json = "{\"msg_id\":\"" + msgId + "\",\"words\":[" + sb.toString() + "]}";
        CosLogger.debug("setHotWordJson " + json);
        return json;
    }

    protected String getJson(String msgId, String field, boolean value) {
        return "{\"msg_id\":\"" + msgId + "\",\"" + field + "\":" + value + "}";
    }

    protected String getAliceActionCtrlJson(String ctrlType, int angle) {
        String json = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg_id", REQConstants.ALICE_NEW_ACTION_CTRL_REQ);
            jsonObject.put("ctrl_type", ctrlType);
            jsonObject.put("ctrl_angle", angle);
            json = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    protected String getDestReachableJson(float x, float y, float rotation) {
        String json = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg_id", REQConstants.NAVI_DEST_REACHABLE_REQ);
            jsonObject.put("x", x);
            jsonObject.put("y", y);
            jsonObject.put("rotation", rotation);
            json = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    //自定义各个角度
    protected String getAliceActionCustomerCtrlJson(String ctrlType, int head_left, int head_up, int lefthand, int righthand) {
        String json = "";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg_id", REQConstants.ALICE_NEW_ACTION_CTRL_REQ);
            jsonObject.put("ctrl_type", ctrlType);
            jsonObject.put("ctrl_angle", 0);
            List<Integer> mm = new ArrayList<>();
            mm.add(head_left);
            mm.add(head_up);
            mm.add(lefthand);
            mm.add(righthand);
            jsonObject.put("ctrl_angles", mm);
            json = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    protected String getScanWebSocketJson(String type, String sn) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", REQConstants.CONNECT_SCAN);
            object.put("type", type);
            object.put("sn", sn);
            json = object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    protected String getDataWebSocketJson(String type, String sn) {
        String json = "";
        try {
            JSONObject object = new JSONObject();
            object.put("msg_id", REQConstants.CONNECT_DATA);
            object.put("type", type);
            object.put("sn", sn);
            json = object.toString();
        } catch (Exception e) {
            CsjlogProxy.getInstance().error("webSocket data error:" + e.getLocalizedMessage());

            e.printStackTrace();
        }

        return json;
    }
}
