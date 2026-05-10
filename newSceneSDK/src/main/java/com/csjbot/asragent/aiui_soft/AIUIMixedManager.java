package com.csjbot.asragent.aiui_soft;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;

import com.csjbot.asragent.IAlsaRawDataSender;
import com.csjbot.asragent.aiui_soft.cae.CaeOperator;
import com.csjbot.asragent.aiui_soft.cae.OnCaeOperatorlistener;
import com.csjbot.asragent.aiui_soft.listener.MsgCollentListener;
import com.csjbot.asragent.aiui_soft.listener.OnAIUISpeakListener;
import com.csjbot.asragent.aiui_soft.listener.OnSpeakProgressListener;
import com.csjbot.asragent.aiui_soft.recorder.RecOperator;
import com.csjbot.asragent.aiui_soft.recorder.RecordListener;
import com.csjbot.asragent.aiui_soft.util.AudioPlayer;
import com.csjbot.asragent.aiui_soft.util.Config;
import com.csjbot.asragent.aiui_soft.util.FileUtil;
import com.csjbot.asragent.aiui_soft.util.RecordAudioUtil;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.FucUtil;
import com.csjbot.coshandler.aiui.aiui_soft.util.HotWordsReplaceBean;
import com.csjbot.coshandler.global.NTFConstants;
import com.csjbot.coshandler.global.RSPConstants;
import com.csjbot.coshandler.global.RobotContants;
import com.csjbot.coshandler.log.CosLogger;
import com.csjbot.coshandler.util.JsonFormatUtil;
import com.iflytek.aiui.AIUIAgent;
import com.iflytek.aiui.AIUIConstant;
import com.iflytek.aiui.AIUIEvent;
import com.iflytek.aiui.AIUIListener;
import com.iflytek.aiui.AIUIMessage;
import com.iflytek.aiui.AIUISetting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class AIUIMixedManager {
    private AIUIPushSpeechRecgContentListener ppListener;
    private MsgCollentListener listener;
    private static AIUIMixedManager aiuiManager;
    private IAlsaRawDataSender dataSender;

    // 多麦克算法库
    private CaeOperator mCaeOperator;
    private RecOperator mRecOperator;
    // AIUI
    private AIUIAgent mAIUIAgent = null;
    // AIUI工作状态
    private int mAIUIState = AIUIConstant.STATE_IDLE;
    private Context mContext;
    private static int ret = 0;
    // 录音机工作状态
    private static boolean isRecording = false;
    // 写音频线程工作中
    private static final boolean isWriting = false;
    private final List<HotWordsReplaceBean> replaceHotWordsList = new ArrayList<>();
    final String path = "/sdcard/vtn/cae/resources/config/vtn.ini";

    public static final String AIUI_PATH = Environment.getExternalStorageDirectory() +
            File.separator + ".robot_info" + File.separator + "aiui.soft.use";
    private AIUIType aiuiType = AIUIType.AIUI_R16;
    private boolean mIsWakeupEnable;
    private AudioPlayer audioPlayer;
    private final Handler handler = new Handler(Looper.getMainLooper());
    public static String aiuiKey, aiuiId;

    private final Runnable speakTimeOutRun = new Runnable() {
        @Override
        public void run() {
            CosLogger.error("AIUI speak time out ");
            if (onAIUISpeakListener != null) {
                onAIUISpeakListener.onCompleted(-1000);
            }
        }
    };

    private String vniString = "[auth]\n" +
            "appid=%s\n" +
            "\n" +
            "[cae]\n" +
            "#是否开启降噪功能, 0为不开启，其他为开启，默认为开启\n" +
            "cae_enable = 1\n" +
            "\n" +
            "# 固定在某个波束降噪，和代码的setRealbeam能力一致\n" +
            "#fix_beam = 1\n" +
            "\n" +
            "#beam取值说明: -2 表示不输出任何音频, -1 第四路为识别音频，无第五路, \n" +
            "#0，1，2时，第四路为指定波束的音频，第五路为vad音频\n" +
            "beam = 1\n" +
            "\n" +
            "# 采样位深度说明  2：短整型16bit 、 4：整型32bit\n" +
            "input_audio_unit = 2\n" +
            "\n" +
            "#output_audio_type 输出音频类型,0 iat; 1 iat_vad\n" +
            "output_audio_type = 1\n" +
            "\n" +
            "[caeEngine]\n" +
            "td_model_type = fsmn\n" +
            "# 窄波束VAD音频平滑处理： 1启用，0不启用。不启用抑制效果更好,但识别率会下降\n" +
            "vad_sqrt = 0\n" +
            "\n" +
            "\n" +
            "# 新版本降噪算法加载的算法资源\n" +
            "aes_model = /sdcard/vtn/cae/resources/models/mlp_aes_1024_tv_xTxT_denoise.bin\n" +
            "aes_vcall_model= /sdcard/vtn/cae/resources/models/mlp_aes_01_vcall_20210510.bin\n" +
            "partition_model= /sdcard/vtn/cae/resources/models/mlp_partition_4mic_5beam_512.bin\n" +
            "partition_model_rec= /sdcard/vtn/cae/resources/models/mlp_lstm_sp_20201016.bin\n" +
            "select_model= /sdcard/vtn/cae/resources/models/mlp_select_6to3_1024.bin\n" +
            "td_model= /sdcard/vtn/cae/resources/models/mlp_td_fsmn_hxxj.bin\n" +
            "\n" +
            "#客户配置回声收敛文件路径\n" +
            "aec_coef_path =  /sdcard/vtn/cae/resources/config/eccof.bin\n" +
            "agc_max_evolop = 10000\n" +
            "agc_target_gain = 5000\n" +
            "\n" +
            "[ivw]\n" +
            "#是否开启唤醒功能, 0为不开启，其他为开启，默认为不开启\n" +
            "ivw_enable = 1\n" +
            "\n" +
            "#唤醒资源\n" +
            "res_path=/sdcard/vtn/cae/resources/ivw/res.bin\n" +
            "\n";

    public static AIUIMixedManager getInstance() {
        if (aiuiManager == null) {
            aiuiManager = new AIUIMixedManager();
        }
        return aiuiManager;
    }

    public void setPushSpeechRecgContentListener(AIUIPushSpeechRecgContentListener pushDataListener) {
        ppListener = pushDataListener;
    }

    public AIUIMixedManager() {
    }


    public void reInit() {
        CosLogger.info("AIUI reInit , type");
        stopRecord();
        if (mAIUIAgent != null) {
            mAIUIAgent.destroy();
        }
        mAIUIAgent = null;
//
        initSDK(mContext, aiuiType);
    }

    public void initSDK(Context context, AIUIType type) {
        mContext = context;
        File aiui = new File(AIUI_PATH);
        aiuiType = aiui.exists() ? AIUIMixedManager.AIUIType.AIUI_SOFT : AIUIMixedManager.AIUIType.AIUI_R16;
        CosLogger.info("AIUI initSDK, type = {}", aiuiType);
//        CaeOperator.AUTH_SN = DeviceUtils.getDeviceId(this);

        // 初始化AIUI
        // AIUI_DATA_ONLY 只初始化 cae 和 alsa
        // AIUIType.R16 只初始化 aiui
        // AIUIType.AIUI_SOFT 全部初始化
        if (AIUIType.AIUI_DATA_ONLY.equals(aiuiType)) {
            CaeOperator.portingFile(mContext);

            // 初始化CAE
            initCaeEngine();
            // 初始化alsa录音
            initAlsa();
            CosLogger.warn("aiuiType ==  AIUI_DATA_ONLY, create initCaeEngine and initAlsa");
        } else if (AIUIType.AIUI_SOFT.equals(aiuiType)) {
            CaeOperator.portingFile(mContext);

            createAgent();
            // 初始化CAE
            initCaeEngine();
            // 初始化alsa录音
            initAlsa();
            CosLogger.warn("aiuiType == AIUI_SOFT, create createAgent ,initCaeEngine and initAlsa");
        } else {
            createAgent();
            CosLogger.warn("aiuiType == AIUI_R16, create createAgent ,initCaeEngine and initAlsa");
        }
    }

    /**
     * 初始化AIUI
     */
    private void createAgent() {
        if (null == mAIUIAgent) {
            CosLogger.info("AIUI 初始化AIUI agent");

            // AIUI SDK上报设备唯一标识，建议与CAE鉴权设备唯一标识一致，便于统计
            AIUISetting.setSystemInfo(AIUIConstant.KEY_SERIAL_NUM, CaeOperator.AUTH_SN);
            mAIUIAgent = AIUIAgent.createAgent(mContext, getAIUIParams(), mAIUIListener);
        }

        setVni();
        if (null == mAIUIAgent) {
            CosLogger.error("AIUI初始化失败!");
        } else {
            CosLogger.info("AIUI AIUI初始化成功!");
        }
    }


    /**
     * 读取AIUI配置
     */
    private String getAIUIParams() {
        String params = "";
        if (mContext == null) {
            return "";
        }
        AssetManager assetManager = mContext.getResources().getAssets();

        String fileName = AIUIType.AIUI_SOFT.equals(aiuiType) ? "cfg/aiui_phone.cfg" : "cfg/aiui_phone_r16.cfg";

        try {
            InputStream ins = assetManager.open(fileName);
            byte[] buffer = new byte[ins.available()];
            ins.read(buffer);
            ins.close();
            params = new String(buffer);

            if (AIUIType.AIUI_R16.equals(aiuiType)) {
                JSONObject paramsJson = new JSONObject(params);

                mIsWakeupEnable = !"off".equals(paramsJson.optJSONObject("speech").optString("wakeup_mode"));
                if (mIsWakeupEnable) {
                    FucUtil.copyAssetFolder(mContext, "ivw", "/sdcard/AIUI/ivw");
                }
                params = paramsJson.toString();
            }

            if (TextUtils.isEmpty(aiuiId)) {
                String aiuiFile = FileUtil.readFromFile("/sdcard/.robot_info/aiuikey.txt");
                if (!TextUtils.isEmpty(aiuiFile)) {
                    JSONObject jsonObject = new JSONObject(aiuiFile);
                    aiuiId = jsonObject.getString("appId");
                    aiuiKey = jsonObject.getString("appKey");
                }
            }

            if (!TextUtils.isEmpty(aiuiId) && !TextUtils.isEmpty(aiuiKey)) {
                JSONObject jsonObject = new JSONObject(params);
                JSONObject login = jsonObject.getJSONObject("login");
                login.put("appid", aiuiId);
                login.put("key", aiuiKey);
                params = jsonObject.toString();
            }
            if (TextUtils.isEmpty(Config.aiuiID)) {
                String aiuiFile = FileUtil.readFromFile("/sdcard/.robot_info/aiuikey.txt");
                if (!TextUtils.isEmpty(aiuiFile)) {
                    JSONObject jsonObject = new JSONObject(aiuiFile);
                    Config.aiuiID = jsonObject.getString("appId");
                    Config.aiuiKey = jsonObject.getString("appKey");
                }
            }

            if (!TextUtils.isEmpty(Config.aiuiID) && !TextUtils.isEmpty(Config.aiuiKey)) {
                JSONObject jsonObject = new JSONObject(params);
                JSONObject login = jsonObject.getJSONObject("login");
                login.put("appid", Config.aiuiID);
                login.put("key", Config.aiuiKey);

//                jsonObject.put("login", login);
                params = jsonObject.toString();
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        CosLogger.info("AIUI AIUI params = {}", params);
        return params;
    }

    public void setVni() {
        File file = new File(path);
        if (TextUtils.isEmpty(Config.aiuiID)) {
            String aiuiFile = FileUtil.readFromFile("/sdcard/.robot_info/aiuikey.txt");
            if (!TextUtils.isEmpty(aiuiFile)) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(aiuiFile);
                    Config.aiuiID = jsonObject.getString("appId");
                    Config.aiuiKey = jsonObject.getString("appKey");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        if (file.exists()) {
            String vntid = TextUtils.isEmpty(Config.aiuiID) ? "6a2398a5" : Config.aiuiID;
            FileUtil.writeTxtToFile(path, String.format(Locale.getDefault(), vniString, vntid));
        }
    }

    public void closeSpeechService() {
        stopRecord();
        if (mAIUIAgent != null) {
            mAIUIAgent.destroy();
        }
        mAIUIAgent = null;
    }

    private void stopRecord() {
        if (isRecording && mRecOperator != null) {
            mRecOperator.stopRecord();
            mCaeOperator.stopSaveAudio();
            isRecording = false;
            CosLogger.warn("AIUI 停止录音");
        }
    }

    private void startRecord() {
        if (mAIUIAgent == null) {
            createAgent();
            return;
        }
        String strTip;
        if (!isRecording && mRecOperator != null) {
            if (isWriting) {
                CosLogger.warn("AIUI 正在写音频测试中，等结束后再开启录音测试");
                CosLogger.warn("AIUI ---------start_alsa_record---------");
                return;
            }
            ret = mRecOperator.startrecord();
            if (0 == ret) {
                strTip = "开启录音成功！";
                isRecording = true;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        CosLogger.debug("AIUI 手动唤醒");
//                        mCaeOperator.setRealBeam(0);
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        AIUIMessage resetWakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
//                        mAIUIAgent.sendMessage(resetWakeupMsg);
//                    }
//                }).start();

            } else if (111111 == ret) {
                strTip = "AIUI AlsaRecorder is null ...";
                CosLogger.warn(strTip);
                CosLogger.warn("AIUI ---------start_alsa_record---------");
            } else {
                strTip = "开启录音失败，请查看/dev/snd/下的设备节点是否有777权限！\nAndroid 8.0 以上需要暂时使用setenforce 0 命令关闭Selinux权限！";
                // TODO: 2023/9/6  这里需要加入一个错误状态，在 UI 显示
                CosLogger.error(strTip);
                destroyRecord();
            }
        } else {
            CosLogger.warn("AIUI 已经开启，无需重复开启");
            if (System.currentTimeMillis() - lastWakeupTime > 5000) {
                AIUIMessage resetWakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
                mAIUIAgent.sendMessage(resetWakeupMsg);
            }
            lastWakeupTime = System.currentTimeMillis();
        }
    }

    private long lastWakeupTime = 0L;


    private void destroyRecord() {
        if (null != mRecOperator && null != mCaeOperator) {
            mRecOperator.stopRecord();
            mCaeOperator.stopSaveAudio();
        } else {
            CosLogger.warn("AIUI distoryCaeEngine is Done!");
        }
    }


    public void releaseSpeechRecognizer() {
    }

    public void startAudioRecognize() {
        if (AIUIType.AIUI_SOFT.equals(aiuiType)) {
            startRecord();
        } else {
            if (mAIUIAgent == null) {
                CosLogger.error("AIUI r16 startAudioRecognize() called  mAIUIAgent  isNull");
                return;
            }

            if (!mIsWakeupEnable) {
                if (System.currentTimeMillis() - lastWakeupTime > 5000) {
                    AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
                    mAIUIAgent.sendMessage(wakeupMsg);
                }
                lastWakeupTime = System.currentTimeMillis();
            }

            // 打开AIUI内部录音机，开始录音。若要使用上传的个性化资源增强识别效果，则在参数中添加pers_param设置
            // 个性化资源使用方法可参见http://doc.xfyun.cn/aiui_mobile/的用户个性化章节
            // 在输入参数中设置tag，则对应结果中也将携带该tag，可用于关联输入输出
            String params = "sample_rate=16000,data_type=audio,pers_param={\"uid\":\"\"},tag=audio-tag";
            AIUIMessage startRecord = new AIUIMessage(AIUIConstant.CMD_START_RECORD, 0, 0, params, null);

            mAIUIAgent.sendMessage(startRecord);
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    AIUIMessage wakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
//                    mAIUIAgent.sendMessage(wakeupMsg);
//                }
//            }, 1000);

        }
    }

    private void stopAIUIRecognize() {
        if (mAIUIAgent != null) {
            AIUIMessage resetWakeupMsg = new AIUIMessage(AIUIConstant.CMD_RESET_WAKEUP, 0, 0, null, null);
            mAIUIAgent.sendMessage(resetWakeupMsg);
        }
    }

    public void cancelAudioRecognize() {
        stopAIUIRecognize();
    }

    public void stopAiUiRecord() {
        if (mAIUIAgent == null) {
            return;
        }
        // 停止录音
        AIUIMessage stopRecord;
        if (AIUIType.AIUI_R16.equals(aiuiType)) {
            String params = "sample_rate=16000,data_type=audio";
            stopRecord = new AIUIMessage(AIUIConstant.CMD_STOP_RECORD, 0, 0, params, null);
        } else {
            stopRecord = new AIUIMessage(AIUIConstant.CMD_RESET_WAKEUP, 0, 0, null, null);
        }
        mAIUIAgent.sendMessage(stopRecord);
    }

    public String replaceHotWord(String text) {
        if (replaceHotWordsList.size() >= 1) {
            if (TextUtils.isEmpty(text)) return text;
            String punctuation = text.substring(text.length() - 1);
            String content = text.substring(0, text.length() - 1);
            for (int i = 0; i < replaceHotWordsList.size(); i++) {
                List<String> subList = replaceHotWordsList.get(i).getReplaceList();
                for (int j = 0; j < subList.size(); j++) {
                    String replaceWord = subList.get(j);
                    if (content.contains(replaceWord)) {
                        return content.replace(replaceWord, replaceHotWordsList.get(i).getHotWordsName()) + punctuation;
                    }
                }
            }
        }
        return text;
    }


    /**
     * AIUI 回调信息处理
     */
    private final AIUIListener mAIUIListener = new AIUIListener() {
        @Override
        public void onEvent(AIUIEvent event) {
            switch (event.eventType) {
                case AIUIConstant.EVENT_CONNECTED_TO_SERVER:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            startAudioRecognize();
                        }
                    }).start();
                    CosLogger.warn("AIUI 已连接服务器");
                    break;

                case AIUIConstant.EVENT_SERVER_DISCONNECTED:
                    CosLogger.error("AIUI 与服务器断开连接");
                    break;

                case AIUIConstant.EVENT_WAKEUP:
                    CosLogger.debug("AIUI 进入识别状态" + event.info);
                    break;
                case AIUIConstant.EVENT_RESULT:
                    try {
                        JSONObject bizParamJson = new JSONObject(event.info);
                        JSONObject data = bizParamJson.getJSONArray("data").getJSONObject(0);
                        JSONObject params = data.getJSONObject("params");
                        JSONObject content = data.getJSONArray("content").getJSONObject(0);
                        String sub = params.optString("sub");

                        if (content.has("cnt_id")) {
                            String cnt_id = content.getString("cnt_id");
                            byte[] dataBuffer = event.data.getByteArray(cnt_id);

                            if (dataBuffer == null) {
                                return;
                            }
                            // 获取该路会话的id，将其提供给支持人员，有助于问题排查
                            // 也可以从Json结果中看到
                            String sid = event.data.getString("sid");
                            String tag = event.data.getString("tag");

//                            showTip("tag=" + tag);

                            if ("nlp".equals(sub) || "iat".equals(sub) || "asr".equals(sub)) {
                                String cntStr = new String(dataBuffer, StandardCharsets.UTF_8);

                                // 获取从数据发送完到获取结果的耗时，单位：ms
                                // 也可以通过键名"bos_rslt"获取从开始发送数据到获取结果的耗时
                                long eosRsltTime = event.data.getLong("eos_rslt", -1);
//                                mTimeSpentText.setText(eosRsltTime + "ms");

                                if (TextUtils.isEmpty(cntStr)) {
                                    return;
                                }

                                JSONObject cntJson = new JSONObject(cntStr);

                                if ("nlp".equals(sub)) {
                                    // 解析得到语义结果
                                    String resultStr = cntJson.optString("intent");
//                                    Log.i( TAG, resultStr );
//                                    CosLogger.debug("AIUI nlp = {}", resultStr);
                                } else if ("iat".equals(sub)) {
                                    // 听写pgs结果更新
//                                    CosLogger.debug("AIUI iat = {}", cntJson);
                                    updateIATPGS(cntJson);
                                }
//                                mNlpText.append( "\n" );
                            } else if ("tts".equals(sub)) {
//                                mTtsBufferProgress = event.data.getInt("percent");
                            }
                        }
                    } catch (Throwable e) {
                        e.printStackTrace();
//                        mNlpText.append( "\n" );
//                        mNlpText.append( e.getLocalizedMessage());
                    }
                    break;

                case AIUIConstant.EVENT_ERROR:
                    CosLogger.error("AIUI 错误: " + event.arg1 + "\n" + event.info);
                    if (listener != null) {
                        listener.collentMessage(JsonFormatUtil.SplicingSimpleJson(NTFConstants.SPEECH_ISR_ERROR_NTF,
                                "desc", event.info, event.arg1));
                    }

                    int arg1 = event.arg1;
                    if (arg1 == 10120) {
                        CosLogger.debug("AIUI  播放出错 code=10120网络异常 播放完成, uuid = {}", uuid);
                        if (onAIUISpeakListener != null) {
                            onAIUISpeakListener.onCompleted(arg1);
                        }
                    }
                    break;
                case AIUIConstant.EVENT_VAD:
                    if (AIUIConstant.VAD_BOS == event.arg1) {
                        CosLogger.debug("AIUI 找到vad_bos");
                    } else if (AIUIConstant.VAD_BOS_TIMEOUT == event.arg1) {
                        CosLogger.debug("AIUI 前端点超时");
                    } else if (AIUIConstant.VAD_EOS == event.arg1) {
                        if (!TextUtils.isEmpty(lastPushString)) {
                            handler.postDelayed(postNotLastButVadEosString, 5000);
                        } else {
                            CosLogger.debug("AIUI 找到vad_eos");
                        }
                    } else {
//                        CosLogger.debug("AIUI event_vad" + event.arg2);
                    }
                    break;
                case AIUIConstant.EVENT_SLEEP:
                    if (listener != null) {
                        listener.collentMessage(JsonFormatUtil.SplicingSimpleJson(RSPConstants.SPEECH_ISR_STOP_RSP));
                    }
                    lastWakeupTime = 0;
                    CosLogger.debug("AIUI 设备进入休眠 " + (event.arg1 == 0 ? "自动休眠" : "发送CMD_RESET_WAKEUP"));
                    break;
                case AIUIConstant.EVENT_START_RECORD:
                    CosLogger.debug("AIUI 已开始录音");
                    break;

                case AIUIConstant.EVENT_STOP_RECORD:
                    CosLogger.debug("AIUI 已停止录音");
                    break;

                case AIUIConstant.EVENT_STATE:    // 状态事件
                    mAIUIState = event.arg1;
                    if (AIUIConstant.STATE_IDLE == mAIUIState) {
                        // 闲置状态，AIUI未开启
                        CosLogger.debug("AIUI event state is STATE_IDLE, 闲置状态，AIUI未开启");
                    } else if (AIUIConstant.STATE_READY == mAIUIState) {
                        // AIUI已就绪，等待唤醒
                        CosLogger.debug("AIUI event state is STATE_READY , AIUI已就绪，等待唤醒");
                    } else if (AIUIConstant.STATE_WORKING == mAIUIState) {
                        // AIUI工作中，可进行交互
                        CosLogger.debug("AIUI event state is STATE_WORKING, AIUI工作中，可进行交互");
                    }
                    break;

                case AIUIConstant.EVENT_TTS: {
                    // 处理TTS结果
                    dealWithTTSEvent(event.arg1, event);

                }
                default:
                    break;
            }
        }
    };

    private void dealWithTTSEvent(int arg1, AIUIEvent event) {
        switch (arg1) {
            case AIUIConstant.TTS_SPEAK_BEGIN:
//                CosLogger.debug("AIUI 开始播放");
//                isSpeaking = true;
//                if (onAIUISpeakListener != null) {
//                    onAIUISpeakListener.onSpeakBegin();
//                }
                CosLogger.debug("AIUI 开始播放");
                isSpeaking = true;
                handler.removeCallbacks(speakTimeOutRun);
                break;
            case AIUIConstant.TTS_SPEAK_PROGRESS:
                handler.removeCallbacks(speakTimeOutRun);
//                            CosLogger.debug("AIUI 缓冲进度为" + "mTtsBufferProgress" +
//                                    ", 播放进度为" + event.data.getInt("percent"));
//                CosLogger.info("播报进度 "+event.data.getInt("percent"));
                if (onSpeakProgressListener != null) {
                    onSpeakProgressListener.onSpeakProgress(tempText, event.data.getInt("percent"));
                }
                break;
            case AIUIConstant.TTS_SPEAK_PAUSED:
                CosLogger.debug("AIUI 暂停播放");
                if (!userPause) {
                    if (onAIUISpeakListener != null) {
                        onAIUISpeakListener.onCompleted(-1);
                    }
                    CosLogger.warn("AIUI 暂停播放, 非用户暂停，直接设置完成播放。");
                    userPause = false;
                }
                break;
            case AIUIConstant.TTS_SPEAK_RESUMED:
                CosLogger.debug("AIUI 恢复播放");
                break;
            case AIUIConstant.TTS_SPEAK_COMPLETED:
                isSpeaking = false;
                if (onAIUISpeakListener != null) {
                    onAIUISpeakListener.onCompleted(0);
                }
                userPause = false;
                CosLogger.debug("AIUI 播放完成, uuid = {}, onAIUISpeakListener = {}", uuid, onAIUISpeakListener);
                break;
            default:
                break;
        }

    }

    // 处理听写PGS的队列
    private final String[] mIATPGSStack = new String[100];
    private String lastPushString = "";

    private void updateIATPGS(JSONObject cntJson) throws JSONException {
        JSONObject text = cntJson.optJSONObject("text");
        // 解析拼接此次听写结果
        StringBuilder iatText = new StringBuilder();
        JSONArray words = text.optJSONArray("ws");
        boolean lastResult = text.optBoolean("ls");
        for (int index = 0; index < words.length(); index++) {
            JSONArray charWord = words.optJSONObject(index).optJSONArray("cw");
            for (int cIndex = 0; cIndex < charWord.length(); cIndex++) {
                iatText.append(charWord.optJSONObject(cIndex).opt("w"));
            }
        }


        String voiceIAT = "";
        String pgsMode = text.optString("pgs");
        //非PGS模式结果
        if (TextUtils.isEmpty(pgsMode)) {

        } else {
            int serialNumber = text.optInt("sn");
            mIATPGSStack[serialNumber] = iatText.toString();
            //pgs结果两种模式rpl和apd模式（替换和追加模式）
            if ("rpl".equals(pgsMode)) {
                //根据replace指定的range，清空stack中对应位置值
                JSONArray replaceRange = text.optJSONArray("rg");
                int start = replaceRange.getInt(0);
                int end = replaceRange.getInt(1);

                for (int index = start; index <= end; index++) {
                    mIATPGSStack[index] = null;
                }
            }

            StringBuilder PGSResult = new StringBuilder();
            //汇总stack经过操作后的剩余的有效结果信息
            for (int index = 0; index < mIATPGSStack.length; index++) {
                if (TextUtils.isEmpty(mIATPGSStack[index])) continue;

//                if (!TextUtils.isEmpty(PGSResult.toString())) PGSResult.append("\n");
                PGSResult.append(mIATPGSStack[index]);
                //如果是最后一条听写结果，则清空stack便于下次使用
                if (lastResult) {
                    mIATPGSStack[index] = null;
                }
            }
            voiceIAT = PGSResult.toString();

            if (ppListener != null && !TextUtils.isEmpty(voiceIAT)) {
                if (!RobotContants.isPushText) {
                    CosLogger.info("AIUI 识别到结果，因人工客服音频打开，不向上推送识别结果！");
                    return;
                }
                if (lastResult) {
                    CosLogger.info("AIUI voiceIAT = {}, lastResult = {}", voiceIAT, lastResult);
                    lastPushString = replaceHotWord(voiceIAT);
                    ppListener.pushSpeechRecgData(lastPushString, true);
                    lastPushString = "";
                    handler.removeCallbacks(postNotLastButVadEosString);
                    if (TextUtils.isEmpty(voiceIAT)) {
                        if (listener != null) {
                            listener.collentMessage(JsonFormatUtil.SplicingSimpleJson(NTFConstants.SPEECH_ISR_ERROR_NTF,
                                    "desc", "内容为空", -3));
                        }
                    }
                } else {
                    if (voiceIAT.length() > 2) {
                        lastPushString = replaceHotWord(voiceIAT);
                        ppListener.pushSpeechRecgData(lastPushString, false);
                    } else {
                        CosLogger.debug("AIUI voiceIAT = {}, voiceIAT.length() = {}", voiceIAT, voiceIAT.length());
                    }
                }
            }
        }
    }


    private final Runnable postNotLastButVadEosString = new Runnable() {
        @Override
        public void run() {
            ppListener.pushSpeechRecgData(lastPushString, true);
            CosLogger.warn("AIUI 找到vad_eos，但是有未结束的，直接结束");
            lastPushString = "";
        }
    };

    public String getRecgStatus() {
        boolean open = mAIUIAgent != null && (isRecording && mRecOperator != null);
        return open ? "语音识别开启(is opened)" : "语音识别关闭(is closed)";
    }

    public void replaceHotWords(List<HotWordsReplaceBean> list) {
        replaceHotWordsList.clear();
        replaceHotWordsList.addAll(list);
    }

    public void setMsgCollentListener(MsgCollentListener listener) {
        this.listener = listener;
    }

    private void initTTSParams() {
        params = new StringBuffer();
        // 发音人，发音人列表：https://aiui-doc.xf-yun.com/project-1/doc-93/
        params.append("vcn=").append(speakerName);
        // 语速，取值范围[0,100]
        params.append(",speed=").append(ttsSpeed);
        // 音调，取值范围[0,100]
        params.append(",pitch=55");
        // 音量，取值范围[0,100]
        params.append(",volume=").append(ttsVolume);
    }

    private OnAIUISpeakListener onAIUISpeakListener;
    private byte[] ttsData;
    private boolean isSpeaking;
    private String uuid;
    private StringBuffer params = new StringBuffer();
    private int ttsVolume = 100;
    private int ttsSpeed = 50;
    private boolean userPause = false;
    private String speakerName = "x4_lingxiaoying_em_v2";
    private String tempText;
    private OnSpeakProgressListener onSpeakProgressListener;

    public void setVolume(float volume) {
        ttsVolume = (int) (volume * 10);
        params = new StringBuffer();
        // 发音人，发音人列表：https://aiui-doc.xf-yun.com/project-1/doc-93/
        params.append("vcn=").append(speakerName);
//        params.append("vcn=x2_xiaojuan");
        // 语速，取值范围[0,100]
        params.append(",speed=").append(ttsSpeed);
        // 音调，取值范围[0,100]
//        params.append(",pitch=50");
        // 音量，取值范围[0,100]
        params.append(",volume=").append(ttsVolume);

    }

    public void startSpeak(String text, OnAIUISpeakListener listener) {
        if (mAIUIAgent == null) {
            createAgent();
            return;
        }
        initTTSParams();
        // 转为二进制数据
        ttsData = text.getBytes(StandardCharsets.UTF_8);
        uuid = UUID.randomUUID().toString();
        if (listener != null) {
            onAIUISpeakListener = listener;
        }
        tempText = text;
        CosLogger.info("startSpeak = {}. uuid = {} , onAIUISpeakListener = {}", text, uuid, onAIUISpeakListener);
        handler.removeCallbacks(speakTimeOutRun);
        handler.postDelayed(speakTimeOutRun, 5000);

        AIUIMessage startTTS = new AIUIMessage(AIUIConstant.CMD_TTS, AIUIConstant.START, 0, params.toString(), ttsData);
        mAIUIAgent.sendMessage(startTTS);

        if (onAIUISpeakListener != null) {
            onAIUISpeakListener.onSpeakBegin();
        }
    }


    public void setOnSpeakProgressListener(OnSpeakProgressListener listener) {
        onSpeakProgressListener = listener;

    }

    public void stopSpeaking() {
        if (mAIUIAgent == null) {
            createAgent();
            return;
        }
        CosLogger.info("stopSpeaking  uuid = {}", uuid);
        AIUIMessage cancelTTS = new AIUIMessage(AIUIConstant.CMD_TTS, AIUIConstant.CANCEL, 0, params.toString(), ttsData);
        mAIUIAgent.sendMessage(cancelTTS);
    }

    public void pauseSpeaking() {
        if (mAIUIAgent == null) {
            createAgent();
            return;
        }
        CosLogger.info("pauseSpeaking  uuid = {}", uuid);
        handler.removeCallbacks(speakTimeOutRun);
        userPause = true;
        AIUIMessage pauseTTS = new AIUIMessage(AIUIConstant.CMD_TTS, AIUIConstant.PAUSE, 0, params.toString(), ttsData);
        mAIUIAgent.sendMessage(pauseTTS);
    }

    public void resumeSpeaking(OnAIUISpeakListener listener) {
        if (mAIUIAgent == null) {
            createAgent();
            return;
        }
        handler.removeCallbacks(speakTimeOutRun);
        handler.postDelayed(speakTimeOutRun, 5000);

        CosLogger.info("resumeSpeaking = {}. uuid = {}", params.toString(), uuid);
        if (listener != null) {
            onAIUISpeakListener = listener;
        }
        AIUIMessage resumeTTS = new AIUIMessage(AIUIConstant.CMD_TTS, AIUIConstant.RESUME, 0, params.toString(), ttsData);
        mAIUIAgent.sendMessage(resumeTTS);
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    public boolean startSaveAudio() {
        if (AIUIType.AIUI_SOFT.equals(aiuiType)) {
            if (mCaeOperator != null) {
                if (mCaeOperator.isAudioSaving()) {
                    return false;
                }
                mCaeOperator.startSaveAudio();
                return true;
            }
        }

        return false;
    }

    public boolean stopSaveAudio() {
        if (AIUIType.AIUI_SOFT.equals(aiuiType)) {
            if (mCaeOperator != null) {
                if (mCaeOperator.isAudioSaving()) {
                    mCaeOperator.stopSaveAudio();
                    return true;
                }
            }
        }

        return false;
    }


    /**************************   AIUI 软核    **************************/

    /**
     * Alsa录音回调消息处理
     * 如果声卡采集音频直接满足CAE格式要求，就需要做音频格式转换
     * CAE音频格式要求：例线性4麦  需要 6通道 16k 16bit|32bit
     */
    private final RecordListener onRecordListener = new RecordListener() {
        @Override
        public void onPcmData(byte[] bytes) {
            // 保存原始录音数据
            mCaeOperator.saveAduio(bytes, CaeOperator.mAlsaRawFileUtil);


            // 录音数据转换：usb声卡 线性2mic
//            byte[] data = RecordAudioUtil.adapeter2Mic(bytes);
            // 录音数据转换：usb声卡 线性4mic
//            byte[] data = RecordAudioUtil.adapeter4Mic(bytes);
            // 录音数据转换：usb声卡 线性/环形6mic
//            byte[] data = RecordAudioUtil.adapeter6Mic(bytes);

            // 录音数据转换：usb声卡 线性/环形4mic
            byte[] data = RecordAudioUtil.adapeter4Mic2(bytes);
            // 保存转换后录音数据
            mCaeOperator.saveAduio(data, CaeOperator.mAlsaRecFileUtil);
            // 写入CAE引擎
            mCaeOperator.writeAudioTest(data);
        }
    };


    /**
     * 初始化CAE
     */
    private void initCaeEngine() {
        mCaeOperator = new CaeOperator();
        ret = mCaeOperator.initCAE(onCaeOperatorlistener);

        if (ret == 0) {
            initAlsa();
            CosLogger.info("AIUI CAE初始化成功!");
        } else {
            CosLogger.error("CAE初始化失败,错误信息为：" + ret);
        }
    }

    /**
     * 初始化ALSA
     */
    private void initAlsa() {
        mRecOperator = new RecOperator();
        mRecOperator.initRec(mContext, onRecordListener);
    }


    /**
     * CAE 回调消息处理
     */
    private final OnCaeOperatorlistener onCaeOperatorlistener = new OnCaeOperatorlistener() {
        @Override
        public void onAudio(byte[] audioData, int dataLen) {
            // CAE降噪后音频写入AIUI SDK进行语音交互
            String params = "data_type=audio,sample_rate=16000";
            AIUIMessage msg = new AIUIMessage(AIUIConstant.CMD_WRITE, 0, 0, params, audioData);
            if (mAIUIAgent == null) {
//                reInit();
                return;
            }

            mAIUIAgent.sendMessage(msg);
            if (audioPlayer != null) {
                audioPlayer.play(audioData);
            }

            if (dataSender != null) {
                try {
                    dataSender.sendAlsaData(audioData);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            // CosLogger.debug("AIUI onAudio ==> dataLen = {}", dataLen);
        }

        @Override
        public void onWakeup(int angle, int beam) {
            if (!RobotContants.isPushText) {
                CosLogger.debug("AIUI 唤醒成功,因人工客服音频打开，不向上推送唤醒结果,angle:" + angle + " beam:" + beam);

                return;
            }
            CosLogger.debug("AIUI 唤醒成功,angle:" + angle + " beam:" + beam);


            String ntf = String.format(Locale.getDefault(), "{\"msg_id\":\"%s\",\"wakeType\":0,\"angle\":%d,\"error_code\":0}",
                    NTFConstants.SPEECH_ISR_WAKEUP_NTF, angle);

            if (listener != null) {
                listener.collentMessage(ntf);
            }

//            setText("唤醒成功,angle:" + a + " beam:" + b);
//            setText("---------WAKEUP_CAE---------");
            // CAE SDK触发唤醒后给AIUI SDK发送手动唤醒事件：让AIUI SDK置于工作状态
            AIUIMessage resetWakeupMsg = new AIUIMessage(AIUIConstant.CMD_WAKEUP, 0, 0, "", null);
            mAIUIAgent.sendMessage(resetWakeupMsg);
        }
    };

    public boolean startAudioPlayBack() {
        if (audioPlayer == null) {
            audioPlayer = new AudioPlayer();
            return true;
        }

        return false;
    }

    public boolean stopAudioPlayBack() {
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
        audioPlayer = null;
        return true;
    }

    public void setSpeakerName(String name) {
        speakerName = name;
        params = new StringBuffer();
        // 发音人，发音人列表：https://aiui-doc.xf-yun.com/project-1/doc-93/
        params.append("vcn=").append(speakerName);
//        params.append("vcn=x2_xiaojuan");
        // 语速，取值范围[0,100]
        params.append(",speed=").append(ttsSpeed);
        // 音调，取值范围[0,100]
//        params.append(",pitch=50");
        // 音量，取值范围[0,100]
        params.append(",volume=").append(ttsVolume);
    }

    public void setSpeed(int speed) {
        ttsSpeed = speed;
        params = new StringBuffer();
        // 发音人，发音人列表：https://aiui-doc.xf-yun.com/project-1/doc-93/
        params.append("vcn=").append(speakerName);
//        params.append("vcn=x2_xiaojuan");
        // 语速，取值范围[0,100]
        params.append(",speed=").append(ttsSpeed);
        // 音调，取值范围[0,100]
//        params.append(",pitch=50");
        // 音量，取值范围[0,100]
        params.append(",volume=").append(ttsVolume);
    }


//    public boolean startSendCaeData(){
//    }

    public enum AIUIType {
        AIUI_DATA_ONLY,
        AIUI_SOFT,
        AIUI_R16
    }

    public void setDataSender(IAlsaRawDataSender sender) {
        dataSender = sender;
    }
}
