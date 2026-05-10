package com.csjbot.coshandler.aiui.aiui_soft.send_msg;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import com.csjbot.asragent.aiui_soft.listener.MsgCollentListener;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.FucUtil;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.ParameterConstants;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.ServerFactory;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.util.ConfInfoUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * nlp
 *
 * @author ShenBen
 * @date 2019/8/26 16:14
 * @email 714081644@qq.com
 */
public class SendMsgThread implements Runnable {

    private String text;
    private boolean isLast;

    private int scence;
    private String pack;
    private MsgCollentListener listener;
    private Context mContext;
    private String[] punctuatio = {
            "。", "，", ",", ".", "!", "！", "?", "？"
    };
    private List<String> punctuatioWordList = new ArrayList<>();

    public SendMsgThread(Context ctx, MsgCollentListener listener) {
        this.listener = listener;
        this.mContext = ctx;
        punctuatioWordList.addAll(Arrays.asList(punctuatio));
    }

    public void setTextContent(String text, int version, String packN, boolean lsLast) {
        this.text = text;
        this.scence = version;
        this.pack = packN;
        ASRConstants.code = version;
        ASRConstants.packName = packN;
        this.isLast = lsLast;

    }

    @Override
    public void run() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("msg_id", NTFConstants.SPEECH_ISR_ONLY_RESULT_NTF);
            obj.put("text", text);
            obj.put("isLast", this.isLast);
             CsjlogProxy.getInstance().debug("run() called with: text = [" + text + "]");
             CsjlogProxy.getInstance().debug("run() called with: isLast = [" + isLast + "]");

            listener.collentMessage(obj.toString());
//            MessengerUtils.getInstance().sendData(0, "1", obj.toString());   //NTFConstants.SPEECH_ISR_ONLY_RESULT_NTF
//            sendBroadcast(NTFConstants.SPEECH_ISR_ONLY_RESULT_NTF, NTFConstants.SPEECH_ISR_ONLY_RESULT_NTF, obj.toString());
            if (isLast) {
                CsjlogProxy.getInstance().debug("run() called with: text = [" + text + "]");
                 CsjlogProxy.getInstance().debug( "run() called with: isLast = [" + isLast + "]");

                if (!ASRConstants.isIntervened) {  //人工客服没有介入
                    uploadChatP2P();
                    getAnswer(text);
                } else {//人工客服介入
                    uploadChatP2P();
                }
            }
        } catch (JSONException e) {
             CsjlogProxy.getInstance().error(e.toString());
        }
    }

//    private void sendBroadcast(String action, String msgType, String text) {
//        Intent intent = new Intent();
//        intent.setAction(action);
//        intent.putExtra(msgType, text);
//        mContext.sendBroadcast(intent);
//    }

    //上传至人工客服--问题
    private void uploadChatP2P() {
        String topActivity = getTopActivity(mContext);
        if (topActivity != null && topActivity.contains("StoryActivity") || topActivity.contains("MusicActivity")) {
            return;
        }

        try {
            JSONObject obj = new JSONObject();
            obj.put("msg_id", AIUIConstants.SEND_CHAT_MSG);
            obj.put("msg", text);
            obj.put("type", 1);
            obj.put("sid", System.currentTimeMillis());
            obj.put("sn", ConfInfoUtil.getSN());
            listener.collentMessage(obj.toString());
        } catch (JSONException e) {
             CsjlogProxy.getInstance().error(e.toString());
        }
    }


    //判断当前界面显示的是哪个Activity
    public static String getTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = am.getRunningTasks(1);
        if (runningTaskInfoList != null && runningTaskInfoList.size() > 0) {
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
//            Log.d("测试", "pkg:" + cn.getPackageName());//包名
//            Log.d("测试", "cls:" + cn.getClassName());//包名加类名
            return cn.getClassName();
        }
        return "";
    }

    //上传至人工客服--答案
    public void uploadAnswerP2P() {
        try {
            JSONObject obj = new JSONObject();
            obj.put("msg_id", AIUIConstants.SEND_CHAT_MSG);
            obj.put("msg", text);
            obj.put("type", 2);
            obj.put("sn", ConfInfoUtil.getSN());
            listener.collentMessage(obj.toString());
        } catch (JSONException e) {
             CsjlogProxy.getInstance().error(e.toString());
        }
    }
//    private String isLastPunctuatio(String text) {
//        String last = text.substring(text.length() - 1);
//        for (int i = 0; i < punctuatioWordList.size(); i++) {
//            if (TextUtils.equals(last, punctuatioWordList.get(i))) {
//                return text.substring(0, text.length() - 1);
//            }
//        }
//        return text;
//    }


    public void getAnswer(String text) {
//        final String text = isLastPunctuatio(content);
        if (TextUtils.isEmpty(ParameterConstants.session_id)) {
            FucUtil.makeSession();
        }

        text = text.replace("。", "");
        text = text.replace("，", "");
        text = text.replace("！", "");
        text = text.replace("？", "");
        text = text.replace(".", "");
        text = text.replace(",", "");
        text = text.replace("!", "");
        text = text.replace("?", "");
        String requestJson = "{\"question\":\"%s\",\"sn\":\"%s\"," +
                "\"sid\":\"\",\"sessionId\":\"%s\",\"chatType\":\"1\",\"audioUrl\":\"\",\"versionCode\":%d,\"packageName\":\"%s\"}";

        String requestJson2 = "{\"question\":\"%s\",\"sn\":\"%s\"," +
                "\"sid\":\"\",\"sessionId\":\"%s\",\"chatType\":\"1\",\"audioUrl\":\"\"}";
        if ("com.csjbot.CSJ_BOT".equals(pack)) {
            pack = null;
        }
        if (TextUtils.isEmpty(pack)) {
            requestJson = String.format(Locale.getDefault(), requestJson2, text, ConfInfoUtil.getSN(), ParameterConstants.session_id);
        } else {
            requestJson = String.format(Locale.getDefault(), requestJson, text, ConfInfoUtil.getSN(), ParameterConstants.session_id, scence, pack);
        }
         CsjlogProxy.getInstance().info("getAnswer requestJson " + requestJson);

        final String finalText = text;
        ServerFactory.createApi().getAnswerV3(ConfInfoUtil.getSN(), requestJson, new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
//                if (Constants.sIsCustomerService) {
//                    CosLogger.info("ROBOT_SET_VOLUME_NTF sIsCustomerService ");
//                    return;
//                }
                String bodyJson = "";
                try {
                    bodyJson = responseBody.string();
                } catch (IOException e) {
                     CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
                }

                 CsjlogProxy.getInstance().info("getAnswer:onNext");
                 CsjlogProxy.getInstance().info("getAnswer:bodyJson:" + bodyJson);
                if (!TextUtils.isEmpty(bodyJson)) {
                    try {
                        JSONObject jsonObject = new JSONObject(bodyJson);
                        JSONObject rowsObj = jsonObject.getJSONObject("rows");
                        String answer = rowsObj.getString("say");
                        if (bodyJson.contains("robotQaInfoId")) {
                            long robotQaInfoId = rowsObj.getLong("robotQaInfoId");
//                            Config.robotQaInfoId = robotQaInfoId;
                        }
                        try {
                            String serviceId = rowsObj.getString("serviceId");
                            if (!TextUtils.isEmpty(serviceId) && serviceId.equals("MUSIC")) {
                                String dataJson = rowsObj.getJSONObject("data").toString();
                                String json = "{\"msg_id\":\"SPEECH_ISR_LAST_RESULT_NTF\",\"result\":{\"text\":\"" + finalText + "\",\"error_code\":0,\"data\":{\"type\":\"chat\",\"serviceId\":\"MUSIC\",\"data\":" + dataJson + "}}}";
                                listener.collentMessage(json);
//                                MessengerUtils.getInstance().sendData(1, NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
//                                sendBroadcast(NTFConstants.SPEECH_ISR_LAST_RESULT_NTF,NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
                                return;
                            } else if (!TextUtils.isEmpty(serviceId) && serviceId.equals("STORY")) {
                                String dataJson = rowsObj.getJSONObject("data").toString();
                                String json = "{\"msg_id\":\"SPEECH_ISR_LAST_RESULT_NTF\",\"result\":{\"text\":\"" + finalText + "\",\"error_code\":0,\"data\":{\"type\":\"chat\",\"serviceId\":\"STORY\",\"data\":" + dataJson + "}}}";
                                listener.collentMessage(json);
//                                MessengerUtils.getInstance().sendData(1, NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
//                                sendBroadcast(NTFConstants.SPEECH_ISR_LAST_RESULT_NTF,NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
                                return;
                            } else if (!TextUtils.isEmpty(serviceId) && serviceId.equals("NEWS")) {
                                String dataJson = rowsObj.getJSONObject("data").toString();
                                String json = "{\"msg_id\":\"SPEECH_ISR_LAST_RESULT_NTF\",\"result\":{\"text\":\"" + finalText + "\",\"error_code\":0,\"data\":{\"type\":\"chat\",\"serviceId\":\"NEWS\",\"data\":" + dataJson + "}}}";
                                listener.collentMessage(json);
//                                MessengerUtils.getInstance().sendData(1, NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
//                                sendBroadcast(NTFConstants.SPEECH_ISR_LAST_RESULT_NTF,NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, json);
                                return;
                            } else {
                                //serviceId 为空
                                String dataJson = rowsObj.getJSONObject("data").toString();
                                String type = rowsObj.getJSONObject("type").toString();
                                String json = "{\"msg_id\":\"SPEECH_ISR_LAST_RESULT_NTF\",\"result\":{\"text\":\"" + finalText + "\",\"error_code\":0,\"data\":{\"type\":\"" + type + "\",\"serviceId\":\"UNIT_ERROR\",\"data\":" + dataJson + "}}}";
                                listener.collentMessage(json);
                                return;
                            }
                        } catch (Exception e) {
                             CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
                        }

                        RawDataChatBean rawDataChatBean = new RawDataChatBean();
                        rawDataChatBean.setMsg_id("SPEECH_ISR_LAST_RESULT_NTF");
                        RawDataChatBean.ResultBean resultBean = new RawDataChatBean.ResultBean();
                        resultBean.setError_code(0);
                        resultBean.setText(finalText);
                        RawDataChatBean.ResultBean.DataBean dataBean = new RawDataChatBean.ResultBean.DataBean();
                        try {
                            dataBean.setGraphic(rowsObj.getString("graphic"));
                        } catch (Exception e) {
                             CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
                        }

                        if (rowsObj.has("actionList")) {
                            List<String> ActionList = new Gson().fromJson(rowsObj.getJSONArray("actionList").toString(),
                                    new TypeToken<List<String>>() {
                                    }.getType());
                            dataBean.setActionList(ActionList);
                        } else {
                            List<String> ActionList = new ArrayList<>();
                            dataBean.setActionList(ActionList);
                        }

                        try {
                            String serviceId = rowsObj.getString("serviceId");
                            dataBean.setServiceId(serviceId);
                        } catch (Exception e) {
                             CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
                        }

                        dataBean.setAnswer(answer);
                        try {
                            dataBean.setGraphic(rowsObj.getString("graphic"));
                        } catch (Exception e) {
                             CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
                        }
                        dataBean.setSay(answer);
                        dataBean.setType(rowsObj.getString("type"));
                        resultBean.setData(dataBean);
                        rawDataChatBean.setResult(resultBean);
                        String speechJson = new Gson().toJson(rawDataChatBean);
                        listener.collentMessage(speechJson);
//                        MessengerUtils.getInstance().sendData(1, NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, speechJson);
//                        sendBroadcast(NTFConstants.SPEECH_ISR_LAST_RESULT_NTF,NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, speechJson);

                        /**
                         *  最终处理见
                         *  @see com.csjbot.mobileshop.base.test.BaseFullScreenActivity#internationalSpeechMessage
                         */

                    } catch (JSONException e) {
                        build10119("SN_NOT_FOUND");
//                        CosLogger.error("getAnswer " + e.getMessage());
                    }
                } else {
                    build10119(finalText);
                }
            }

            @Override
            public void onError(Throwable e) {
                build10119(e.getMessage());
//                MessengerUtils.getInstance().sendData(1, NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, speechJson);
//                sendBroadcast(NTFConstants.SPEECH_ISR_LAST_RESULT_NTF,NTFConstants.SPEECH_ISR_LAST_RESULT_NTF, speechJson);
                 CsjlogProxy.getInstance().error("getAnswer " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    private void build10119(String reason) {
        RawDataChatBean rawDataChatBean = new RawDataChatBean();
        rawDataChatBean.setMsg_id("SPEECH_ISR_LAST_RESULT_NTF");
        RawDataChatBean.ResultBean resultBean = new RawDataChatBean.ResultBean();
        resultBean.setError_code(10119);
        resultBean.setText(text);
        RawDataChatBean.ResultBean.DataBean dataBean = new RawDataChatBean.ResultBean.DataBean();
        dataBean.setGraphic(reason);
        dataBean.setType("error");
        resultBean.setData(dataBean);
        rawDataChatBean.setResult(resultBean);
        String speechJson = new Gson().toJson(rawDataChatBean);
        listener.collentMessage(speechJson);
    }
}
