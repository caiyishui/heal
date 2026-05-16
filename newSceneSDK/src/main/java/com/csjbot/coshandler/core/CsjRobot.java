package com.csjbot.coshandler.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import com.csjbot.cosclient.CosClientAgent;
import com.csjbot.cosclient.entity.CommonPacket;
import com.csjbot.cosclient.utils.ClientConfig;
import com.csjbot.coshandler.BuildConfig;
import com.csjbot.coshandler.client_req.base.BaseClientReq;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.core.interfaces.IChassis;
import com.csjbot.coshandler.global.ConnectConstants;
import com.csjbot.coshandler.global.RobotContants;
import com.csjbot.coshandler.listener.OnAuthenticationListener;
import com.csjbot.coshandler.listener.OnBodyCtrlListener;
import com.csjbot.coshandler.listener.OnCameraListener;
import com.csjbot.coshandler.listener.OnChargeFailureListener;
import com.csjbot.coshandler.listener.OnCustomServiceMsgListener;
import com.csjbot.coshandler.listener.OnCustomerStateListener;
import com.csjbot.coshandler.listener.OnDestReachableListener;
import com.csjbot.coshandler.listener.OnDetectPersonListener;
import com.csjbot.coshandler.listener.OnDeviceInfoListener;
import com.csjbot.coshandler.listener.OnDoorControlListener;
import com.csjbot.coshandler.listener.OnDoorStatusListener;
import com.csjbot.coshandler.listener.OnDoubleDoorStateListener;
import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;
import com.csjbot.coshandler.listener.OnEmergencyStatusListener;
import com.csjbot.coshandler.listener.OnErrorInfoListener;
import com.csjbot.coshandler.listener.OnExpressionListener;
import com.csjbot.coshandler.listener.OnFaceCoordinateListener;
import com.csjbot.coshandler.listener.OnFaceListener;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnGetAllPointsListener;
import com.csjbot.coshandler.listener.OnGetCurrentMapImageListener;
import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnHeadTouchListener;
import com.csjbot.coshandler.listener.OnHotWordsListener;
import com.csjbot.coshandler.listener.OnConnectListener;
import com.csjbot.coshandler.listener.OnMapListener;
import com.csjbot.coshandler.listener.OnMapStateListener;
import com.csjbot.coshandler.listener.OnMqttConnectStateListener;
import com.csjbot.coshandler.listener.OnPositioningQualityListener;
import com.csjbot.coshandler.listener.OnPowerStatusListener;
import com.csjbot.coshandler.listener.OnRemoteCtrlKeyListener;
import com.csjbot.coshandler.listener.OnRobotConnectionStateListener;
import com.csjbot.coshandler.listener.OnRobotCtrlListener;
import com.csjbot.coshandler.listener.OnRobotDockStateListener;
import com.csjbot.coshandler.listener.OnRobotInitListener;
import com.csjbot.coshandler.listener.OnRobotMoveStatusListener;
import com.csjbot.coshandler.listener.OnRobotNaviStatesListener;
import com.csjbot.coshandler.listener.OnRobotSerialPowerInfoListener;
import com.csjbot.coshandler.listener.OnRobotTypeListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnMicroVolumeListener;
import com.csjbot.coshandler.listener.OnMotoOverloadListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnNaviSearchListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnSNListener;
import com.csjbot.coshandler.listener.OnSensorHealthListener;
import com.csjbot.coshandler.listener.OnSlamVersionListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;
import com.csjbot.coshandler.listener.OnSpeechGetResultListener;
import com.csjbot.coshandler.listener.OnSpeechListener;
import com.csjbot.coshandler.listener.OnSpeedGetListener;
import com.csjbot.coshandler.listener.OnUpgradeListener;
import com.csjbot.coshandler.listener.OnWakeupListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;
import com.csjbot.coshandler.listener.OnWebSocketDataListener;
import com.csjbot.coshandler.log.CosLogger;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.log.Csjlogger;
import com.csjbot.coshandler.service.BaseReceiveService;
import com.csjbot.coshandler.service.CameraService;
import com.csjbot.coshandler.service.CheckEthernetService;
import com.csjbot.coshandler.service.HandlerMsgService;
import com.csjbot.coshandler.service.HandlerMsgSocketService;
import com.csjbot.coshandler.tts.ISpeechSpeak;
import com.csjbot.coshandler.tts.SpeechFactory;
import com.csjbot.coshandler.util.ConfInfoUtil;
import com.csjbot.coshandler.util.DeviceIdUtil;
import com.csjbot.coshandler.util.ShellUtil;
import com.csjbot.coshandler.util.WifiMacUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import ch.qos.logback.classic.pattern.SyslogStartConverter;

/**
 * 对外提供的机器人功能类
 * Created by jingwc on 2019/7/4.
 */
public class CsjRobot {


    /**
     * Get the default address
     *
     * @return Returns the default address, which is 192.168.99.101 by default
     */
    public static String getDefaultIpAddr() {
        return defaultIpAddr;
    }

    /**
     * Get the default port
     *
     * @return Returns the default port, which is 60002 by default
     */
    public static int getDefaultPort() {
        return defaultPort;
    }

    public void setOnInitListener(OnRobotInitListener listener) {
        onRobotInitListener = listener;
    }


    /**
     * The enum Robot type.
     */
    public enum RobotType {

        /**
         * alice:       迎宾机器人 
         * snow:        小雪 
         * amy:         艾米机器人 
         * alice_pro:    迎宾大屏机器人 
         * amy_pro:      AMY大屏机器人 
         * jingling:    精灵机器人 
         * timo:        小鱼机器人 
         * <p>
         * panda:        熊猫机器人 
         * amy_food:      艾米送餐机器人 
         * Scud:         飞毛腿机器人 
         * jingbao:      净宝机器人 
         * songxiaofan:  宋小贩机器人 
         */

        ALICE_NEW("alice"),
        ALICE("alice"),
        SNOW("snow"),
        AMY("amy"),
        ALICE_PRO("alice_big"),
        AMY_PRO("amy_big"),
        JINGLING("jingling"),
        TIMO("Timo"),

        PANDA("panda"),
        AMY_FOOD("amy_food"),
        SCUD("scud"),
        JINGBAO("jingbao"),
        SONGXIAOFAN("songxiaofan");

        private String name;

        private RobotType(String name) {
            this.name = name;
        }

        /**
         * Gets name.
         *
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * Sets name.
         *
         * @param name the name
         */
        public void setName(String name) {
            this.name = name;
        }
    }

    private static void main() {
        final CsjRobot csjRobot = CsjRobot.getInstance();

        csjRobot.init(null);

        csjRobot.registerConnectListener(new OnConnectListener() {
            @Override
            public void success() {

            }

            @Override
            public void faild() {

            }

            @Override
            public void timeout() {

            }

            @Override
            public void disconnect() {

            }
        });

        csjRobot.registerSpeechListener(new OnSpeechListener() {
            @Override
            public void speechInfo(String json, int type) {
                if (Speech.SPEECH_RECOGNITION_RESULT == type) {

                } else if (Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT == type) {

                }
            }
        });

        csjRobot.registerWakeupListener(new OnWakeupListener() {
            @Override
            public void response(int angle) {
                csjRobot.getAction().startDance();

                Action action = csjRobot.getAction();
                action.startDance();

                action.moveAngle(180, new OnGoRotationListener() {
                    @Override
                    public void response(int code) {

                    }
                });
            }
        });

        String sn = csjRobot.getSN();

        int electricity = csjRobot.getState().getElectricity();

        boolean isConnect = csjRobot.getState().isConnect();
    }

    private volatile static CsjRobot robot;

    private static boolean enableAsr = true;
    private static boolean enableFace = true;
    private static boolean enableSlam = true;
    private static String defaultIpAddr = "127.0.0.1";
    private static int defaultPort = 60002;
    private static boolean isInit;
    private static boolean useSocket = false;
    private static String apkey = "csjbot_default";
    /**
     * The constant isNewBodyAction.
     */
    public static boolean isNewBodyAction = true;
    // 机器人SN
    private String sn = "888888888888";

    private static RobotType defaultType = RobotType.AMY;


    /**
     * Sets robot type.
     *
     * @param robotType the robot type
     */
    public static void setRobotType(RobotType robotType) {
        if (robotType.equals(RobotType.ALICE)) {
            isNewBodyAction = false;
        }

        defaultType = robotType;
    }

    /**
     * Enable/Disable speech recognition
     *
     * @param enable true is Enable
     */
    public static void enableAsr(boolean enable) {
        enableAsr = enable;
    }

    /**
     * Enable/Disable face recognition
     *
     * @param enable true is Enable
     */
    public static void enableFace(boolean enable) {
        enableFace = enable;
    }

    /**
     * Enable/Disable Navigation module
     *
     * @param enable true is Enable
     */
    public static void enableSlam(boolean enable) {
        enableSlam = enable;
    }

    /**
     * Set the IP address and port of the connection
     *
     * @param ip   ip address
     * @param port port of the connection
     */
    public static void setIpAndrPort(String ip, int port) {
        defaultIpAddr = ip;
        defaultPort = port;
        useSocket = true;
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CsjRobot getInstance() {
        if (!check() && "csjbot_default".equals(apkey) == false) {
            throw new NullPointerException("Not authorized, please verify [key] and [secret] first! -> CsjRobot.authentication(key,secret);");
        }
        if (robot == null) {
            synchronized (CsjRobot.class) {
                if (robot == null) {
                    robot = new CsjRobot();
                }
            }
        }
        return robot;
    }

    public static void setDebug(boolean enable) {
        BaseReceiveService.setEnableDebug(enable);
    }

    private CsjRobot() {
        //  启动服务
        String cmd = "am startservice -a com.csjbot.robotsdkservice.startservice ";
        ShellUtil.execCmd(cmd, true, false);
        CosLogger.info("init CsjRobot, start SDK service");
    }

    /**
     * Authentication.
     *
     * @param context   the context
     * @param appKey    the app key
     * @param appSecret the app secret
     * @param listener  the listener
     */
    public static void authentication(final Context context, final String appKey, final String appSecret, final OnAuthenticationListener listener) {
        apkey = appKey;
        if (TextUtils.equals(appKey, "csjbot_default")) {
            try {
                createAuthFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (listener != null) {
                listener.success();
            }

            return;
        }

        if (check()) {
            if (listener != null) {
                listener.success();
            }
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://partner.csjbot.com/open-platform/sys/getAppSecret");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    OutputStream os = connection.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write("appKey=" + appKey + "&appSecret=" + appSecret + "&appId=" + DeviceIdUtil.getDeviceId(context));
                    bw.flush();
                    final InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is, "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    StringBuilder sb = new StringBuilder();
                    String result = "";
                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                        result = sb.toString();
                        Log.d("TAG", "result:" + result);
                    }
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = new JSONObject(result);
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            createAuthFile();
                            if (listener != null) {
                                listener.success();
                            }
                        } else {
                            if (listener != null) {
                                listener.error();
                            }
                        }
                    } else {
                        if (listener != null) {
                            listener.error();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static void createAuthFile() throws IOException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ".csj" + File.separator + ".csj.csj";
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private static boolean check() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + ".csj" + File.separator + ".csj.csj";
        if (new File(path).exists()) {
            return true;
        } else {
            return false;
        }
    }

    private void initLogger() {
        String sn = ConfInfoUtil.getSN();
        if (TextUtils.isEmpty(sn)) {
            sn = "Mac" + WifiMacUtils.getLocalMacAddressFromIp();
        }

        SyslogStartConverter.setSN("newapp_" + sn);
        headerLog();
    }

    private void headerLog() {
        CsjlogProxy.getInstance().warn("============== app start ======================");
        CsjlogProxy.getInstance().warn("============== app version      :"  + " ");
        CsjlogProxy.getInstance().warn("============== app SN           :" + ConfInfoUtil.getSN() + " ");
        CsjlogProxy.getInstance().warn("============== app BUILD_TYPE   :" + BuildConfig.BUILD_TYPE + ", App version code :"  + " ");
    }

    /**
     * Gets sn.
     *
     * @return the sn
     */
    public String getSN() {
        return sn;
    }

    /**
     * Init.
     *
     * @param context the context
     */
    public void init(Context context) {
        Log.d("CSJBOT", "start isInit " + isInit);

        if (isInit) {
            return;
        }
        isInit = true;

        mState = new State();
        mAction = new Action();
        mFace = new Face();
        mSpeech = new Speech();
        mExpression = new Expression();
        mExtra = new Extra();
        mVersion = new Version();
        mEvelator = new Evelator();
        mCustomer = new Customer();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setDefaultIpAddr(defaultIpAddr);
        clientConfig.setDefaultPort(defaultPort);
        clientConfig.setEnableAsr(enableAsr);
        clientConfig.setEnableFace(enableFace);
        clientConfig.setEnableSlam(enableSlam);
        clientConfig.setRobotType(defaultType.getName());
        HandlerMsgService.setConfig(clientConfig);
        HandlerMsgSocketService.setConfig(clientConfig);
//        if (defaultType == RobotType.SNOW || defaultType == RobotType.TIMO) {
//            RobotContants.BAKER_SPEAK_VOICE = "Lele";
//            RobotContants.BAKER_SPEAK_SPEED = 3.5f;
//        } else {
//            RobotContants.BAKER_SPEAK_VOICE = "Jiaojiao";
//            RobotContants.BAKER_SPEAK_SPEED = 5.0f;
//        }
        if (defaultType == RobotType.TIMO) {
            mTts = SpeechFactory.createSpeech(context, SpeechFactory.SpeechType.AIUI);
        } else {
            mTts = SpeechFactory.createSpeech(context, SpeechFactory.SpeechType.AIUI_ALL);
        }

        if (defaultType == RobotType.SNOW) {
            Intent intent = new Intent();
            intent.setPackage("com.csjrobot.csjrobotaidl");
            intent.setAction("com.csjbot.new app action");
            context.startService(intent);
        }

        context.startService(new Intent(context, CheckEthernetService.class));
        if (useSocket) {
            context.startService(new Intent(context, HandlerMsgSocketService.class));
        } else {
            context.startService(new Intent(context, HandlerMsgService.class));
        }
        //此服务不要开
//        context.startService(new Intent(context, CameraService.class));

        initLogger();

        new Thread(new Runnable() {
            @Override
            public void run() {

                setOnRobotStateBatterySelfQueryListener(new OnRobotStateListener() {
                    @Override
                    public void getBattery(int battery) {
                        getState().setElectricity(battery);
                    }

                    @Override
                    public void getCharge(int charge) {

                    }
                });

                setOnRobotStateChargeSelfQueryListener(new OnRobotStateListener() {
                    @Override
                    public void getBattery(int battery) {

                    }

                    @Override
                    public void getCharge(int charge) {
                        getState().setChargeState(charge);
                    }
                });

                int i = 0;

                while (true) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    i++;

                    if (i % 5 == 0) {
                        getState().getBattery(onRobotStateBatteryListener);
                    }

                    if (i % 2 == 0) {
                        getState().getCharge(onRobotStateBatteryListener);
                    }

                    if (i % 3 == 0) {
                        getState().getEmergencyStatus(onEmergencyStatusListener);

                    }

                }
            }
        }).start();

        CsjlogProxy.getInstance().warn("start isInit " + isInit);
    }

    private void getBasicInfo() {
        connectToMqtt("admin", "1234", "csj_device_" + ConfInfoUtil.getSN());
    }

    public void connectToMqtt(String userName, String password, String clientId) {
        mExtra.connectMqttServer(userName, password, clientId);
    }

    private State mState;

    private Action mAction;

    private Face mFace;

    private Speech mSpeech;

    private Expression mExpression;

    private Version mVersion;

    private Extra mExtra;

    private ISpeechSpeak mTts;

    private Evelator mEvelator;

    private Customer mCustomer;

    /**
     * Gets state.
     *
     * @return the state
     */
    public State getState() {
        return mState;
    }

    /**
     * Gets action.
     *
     * @return the action
     */
    public Action getAction() {
        return mAction;
    }

    /**
     * Gets face.
     *
     * @return the face
     */
    public Face getFace() {
        return mFace;
    }

    /**
     * Gets speech.
     *
     * @return the speech
     */
    public Speech getSpeech() {
        return mSpeech;
    }

    /**
     * Gets expression.
     *
     * @return the expression
     */
    public Expression getExpression() {
        return mExpression;
    }

    /**
     * Gets version.
     *
     * @return the version
     */
    public Version getVersion() {
        return mVersion;
    }

    /**
     * Gets extra.
     *
     * @return the extra
     */
    public Extra getExtra() {
        return mExtra;
    }

    public Customer getmCustomer() {
        return mCustomer;
    }

    /**
     * Gets tts.
     *
     * @return the tts
     */
    public ISpeechSpeak getTts() {
        return mTts;
    }

    /**
     * Sets tts.
     *
     * @param tts the tts
     */
    public void setTts(ISpeechSpeak tts) {
        this.mTts = tts;
    }

    /**
     * Gets evelator.
     *
     * @return the evelator
     */
    public Evelator getEvelator() {
        return mEvelator;
    }

    private CopyOnWriteArrayList<OnConnectListener> onConnectListeners = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<OnSpeechListener> onSpeechListeners = new CopyOnWriteArrayList<>();

//    private CopyOnWriteArrayList<OnWakeupListener> onWakeupListeners = new CopyOnWriteArrayList<>();

    private OnWakeupListener onWakeupListener;

    private CopyOnWriteArrayList<OnFaceListener> onFaceListeners = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<OnDetectPersonListener> onDetectPersonListeners = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<OnCameraListener> onCameraListeners = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<OnHeadTouchListener> onHeadTouchListeners = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<OnNaviListener> onNaviListeners = new CopyOnWriteArrayList<>();

    private OnSpeechGetResultListener onSpeechGetResultListener;

    private OnGetAllFaceListener onGetAllFaceListener;

    private OnPositionListener onPositionListener;

    private OnNaviListener onNaviMoveResultListener;

    private OnNaviListener onNaviMoveToResultListener;

    private OnNaviListener onNaviCancelListener;

    private OnNaviListener onNaviGoHomeListener;

    private OnGoRotationListener onGoRotationListener;

    private OnNaviSearchListener onNaviSearchListener;

    private OnMicroVolumeListener onMicroVolumeListener;

    private OnExpressionListener onExpressionListener;

    private OnHotWordsListener onHotWordsListener;

    private OnRobotStateListener onRobotStateBatteryListener;

    private OnRobotStateListener onRobotStateChargeListener;

    private OnRobotStateListener onRobotStateBatterySelfQueryListener;

    private OnRobotStateListener onRobotStateChargeSelfQueryListener;

    private OnWarningCheckSelfListener onWarningCheckSelfListener;

    private OnRobotTypeListener onRobotTypeListener;

    private OnDetectPersonListener onDetectPersonListener;

    private OnGetVersionListener onGetVersionListener;

    private OnUpgradeListener onUpgradeSoftwareCheckListener;

    private OnUpgradeListener onUpgradeSoftwareUpgradeListener;

    private OnSNListener onSNListener;

    private OnDeviceInfoListener onDeviceInfoListener;

    private OnSnapshotoListener onSnapshotoListener;

    private OnFaceSaveListener onFaceSaveListener;

    private OnMapListListener onMapListListener;

    private OnSpeedGetListener onSpeedGetListener;

    private OnMotoOverloadListener onMotoOverloadListener;

    private OnEmergencyStatusListener onEmergencyStatusListener;

    private OnMapStateListener onMapStateListener;

    private OnElevatorCtrlListener onElevatorCtrlListener;

    private OnElevatorStateListener onElevatorStateListener;

    private OnDoubleDoorStateListener onDoubleDoorStateListener;

    private OnMapListener onMapListener;

    private OnRobotDockStateListener robotDockStateListener;

    private OnBodyCtrlListener onBodyCtrlListener;

    private OnSlamVersionListener slamVersionListener;

    private OnPositioningQualityListener onPositioningQualityListener;

    private OnPowerStatusListener onPowerStatusListener;

    private OnGetCurrentMapImageListener onGetCurrentMapImageListener;

    private OnRobotInitListener onRobotInitListener;

    private OnMqttConnectStateListener onMqttConnectStateListener;

    private OnSensorHealthListener onSensorHealthListener;

    private OnChargeFailureListener onChargeFailureListener;

    private OnFaceCoordinateListener faceCoordinateListener;
    private OnRobotMoveStatusListener robotMoveStatusListener;
    private OnDoorStatusListener doorStatusListeners;
    private OnDoorControlListener doorControlListeners;

    private OnWebSocketDataListener onWebSocketDataListener;
    private OnRemoteCtrlKeyListener onRemoteCtrlKeyListener;

    private OnRobotSerialPowerInfoListener onSerialPowerInfoListener;
    private OnCustomServiceMsgListener onCustomServiceMsgListener;
    private OnRobotCtrlListener onRobotCtrlListener;
    private OnCustomerStateListener customerStateListener;
    private OnDestReachableListener reachableListener;
    private OnErrorInfoListener onErrorInfoListener;

    private OnGetAllPointsListener onGetAllPointsListener;
    private OnRobotNaviStatesListener onRobotNaviStatesListener;
    private OnRobotConnectionStateListener robotConnectionStateListener;

    public void setOnErrorInfoListener(OnErrorInfoListener listener) {
        onErrorInfoListener = listener;
    }

    /**
     * Sets on moto overload listener.
     *
     * @param listener the listener
     */
    public void setOnMotoOverloadListener(OnMotoOverloadListener listener) {
        onMotoOverloadListener = listener;
    }

    public void setOnGetAllPointsListener(OnGetAllPointsListener listener) {
        onGetAllPointsListener = listener;
    }

    public void setRobotConnectionStateListener(OnRobotConnectionStateListener listener) {
        robotConnectionStateListener = listener;
    }
    /**
     * Sets on emergency status listener.
     *
     * @param listener the listener
     */
    public void setOnEmergencyStatusListener(OnEmergencyStatusListener listener) {
        onEmergencyStatusListener = listener;
    }
    public void setonRobotNaviStatesListener(OnRobotNaviStatesListener listener) {
        onRobotNaviStatesListener = listener;
    }
    public void setOnPowerStatusListener(OnPowerStatusListener listener) {
        onPowerStatusListener = listener;
    }

    public void setOnPositioningQualityListener(OnPositioningQualityListener listener) {
        onPositioningQualityListener = listener;
    }

    public void setFaceCoordinateListener(OnFaceCoordinateListener listener) {
        faceCoordinateListener = listener;
    }

    public void setRobotMoveStatusListener(OnRobotMoveStatusListener listener) {
        robotMoveStatusListener = listener;
    }

    public void setOnCustomerStateListener(OnCustomerStateListener listener) {
        customerStateListener = listener;
    }

    /**
     * Register connect listener.
     *
     * @param listener the listener
     */
    public void registerConnectListener(OnConnectListener listener) {
        if (listener != null && !onConnectListeners.contains(listener)) {
            onConnectListeners.add(listener);
        }
    }


    /**
     * Un register connect listener.
     *
     * @param listener the listener
     */
    public void unRegisterConnectListener(OnConnectListener listener) {
        if (listener != null && onConnectListeners.contains(listener)) {
            onConnectListeners.remove(listener);
        }
    }

    OnSpeechListener onSpeechListener;

    /**
     * Register speech listener.
     *
     * @param listener the listener
     */
    public void registerSpeechListener(OnSpeechListener listener) {
        onSpeechListener = listener;
//        if (listener != null && !onSpeechListeners.contains(listener)) {
//            onSpeechListeners.add(listener);
//        }
    }

    /**
     * Un register speech listener.
     *
     * @param listener the listener
     */
    public void unRegisterSpeechListener(OnSpeechListener listener) {
        if (listener != null && onSpeechListeners.contains(listener)) {
            onSpeechListeners.remove(listener);
        }
    }

    /**
     * Register wakeup listener.
     *
     * @param listener the listener
     */
    public void registerWakeupListener(OnWakeupListener listener) {
        onWakeupListener = listener;
//        if (listener != null && !onWakeupListeners.contains(listener)) {
//            onWakeupListeners.add(listener);
//        }
    }

    /**
     * Un register wakeup listener.
     *
     * @param listener the listener
     */
    public void unRegisterWakeupListener(OnWakeupListener listener) {
//        if (listener != null && onWakeupListeners.contains(listener)) {
//            onWakeupListeners.remove(listener);
//        }
        onWakeupListener = null;
    }

    /**
     * Register face listener.
     *
     * @param listener the listener
     */
    public void registerFaceListener(OnFaceListener listener) {
        if (listener != null && !onFaceListeners.contains(listener)) {
            onFaceListeners.add(listener);
        }
    }

    /**
     * Un register face listener.
     *
     * @param listener the listener
     */
    public void unRegisterFaceListener(OnFaceListener listener) {
        if (listener != null && onFaceListeners.contains(listener)) {
            onFaceListeners.remove(listener);
        }
    }

    /**
     * Register detect person listener.
     *
     * @param listener the listener
     */
    public void registerDetectPersonListener(OnDetectPersonListener listener) {
        if (listener != null && !onDetectPersonListeners.contains(listener)) {
            onDetectPersonListeners.add(listener);
        }
    }

    /**
     * Un register detect person listener.
     *
     * @param listener the listener
     */
    public void unRegisterDetectPersonListener(OnDetectPersonListener listener) {
        if (listener != null && onDetectPersonListeners.contains(listener)) {
            onDetectPersonListeners.remove(listener);
        }
    }

    /**
     * Register camera listener.
     *
     * @param listener the listener
     */
    public void registerCameraListener(OnCameraListener listener) {
        if (listener != null && !onCameraListeners.contains(listener)) {
            onCameraListeners.add(listener);
        }
    }

    /**
     * Un register camera listener.
     *
     * @param listener the listener
     */
    public void unRegisterCameraListener(OnCameraListener listener) {
        if (listener != null && onCameraListeners.contains(listener)) {
            onCameraListeners.remove(listener);
        }
    }

    /**
     * Register head touch listener.
     *
     * @param listener the listener
     */
    public void registerHeadTouchListener(OnHeadTouchListener listener) {
        if (listener != null && !onHeadTouchListeners.contains(listener)) {
            onHeadTouchListeners.add(listener);
        }
    }

    /**
     * Register navi listeners.
     *
     * @param listener the listener
     */
    public void registerNaviListeners(OnNaviListener listener) {
        if (listener != null && !onNaviListeners.contains(listener)) {
            onNaviListeners.add(listener);
        }
    }

    /**
     * Un register navi listeners.
     *
     * @param listener the listener
     */
    public void unRegisterNaviListeners(OnNaviListener listener) {
        if (listener != null) {
            onNaviListeners.remove(listener);
        }
    }

    /**
     * 设置恢复地图的监听
     *
     * @param onMapListener the on map listener
     */
    public void setOnMapListener(OnMapListener onMapListener) {
        this.onMapListener = onMapListener;
    }


    /**
     * Sets on robot dock state listener.
     *
     * @param listener the listener
     */
    public void setOnRobotDockStateListener(OnRobotDockStateListener listener) {
        this.robotDockStateListener = listener;
    }

    public void setOnCustomServiceMsgListener(OnCustomServiceMsgListener listener) {
        this.onCustomServiceMsgListener = listener;
    }

    public void setOnSensorHealthListener(OnSensorHealthListener listener) {
        this.onSensorHealthListener = listener;
    }

    public void setOnBodyCtrlListener(OnBodyCtrlListener onBodyCtrlListener) {
        this.onBodyCtrlListener = onBodyCtrlListener;
    }

    public void setDoorStatusListener(OnDoorStatusListener listener) {
        doorStatusListeners = listener;
    }

    public void setDoorControlListener(OnDoorControlListener listener) {
        doorControlListeners = listener;
    }

    /**
     * Sets on map state listener.
     *
     * @param listener the listener
     */
    public void setOnMapStateListener(OnMapStateListener listener) {
        this.onMapStateListener = listener;
    }

    /**
     * Sets on speed get listener.
     *
     * @param listener the listener
     */
    public void setOnSpeedGetListener(OnSpeedGetListener listener) {
        this.onSpeedGetListener = listener;
    }

    /**
     * Sets on speech get result listener.
     *
     * @param listener the listener
     */
    public void setOnSpeechGetResultListener(OnSpeechGetResultListener listener) {
        this.onSpeechGetResultListener = listener;
    }

    /**
     * Sets on get all face listener.
     *
     * @param listener the listener
     */
    public void setOnGetAllFaceListener(OnGetAllFaceListener listener) {
        this.onGetAllFaceListener = listener;
    }

    /**
     * Sets on position listener.
     *
     * @param listener the listener
     */
    public void setOnPositionListener(OnPositionListener listener) {
        this.onPositionListener = listener;
    }

    /**
     * Sets on navi move result listener.
     *
     * @param listener the listener
     */
    public void setOnNaviMoveResultListener(OnNaviListener listener) {
        this.onNaviMoveResultListener = listener;
    }

    /**
     * Sets on navi move to result listener.
     *
     * @param listener the listener
     */
    public void setOnNaviMoveToResultListener(OnNaviListener listener) {
        this.onNaviMoveToResultListener = listener;
    }

    /**
     * Sets on navi cancel listener.
     *
     * @param listener the listener
     */
    public void setOnNaviCancelListener(OnNaviListener listener) {
        this.onNaviCancelListener = listener;
    }

    /**
     * Sets on navi go home listener.
     *
     * @param listener the listener
     */
    public void setOnNaviGoHomeListener(OnNaviListener listener) {
        this.onNaviGoHomeListener = listener;
    }

    /**
     * Sets on go rotation listener.
     *
     * @param listener the listener
     */
    public void setOnGoRotationListener(OnGoRotationListener listener) {
        this.onGoRotationListener = listener;
    }

    /**
     * Sets on navi search listener.
     *
     * @param listener the listener
     */
    public void setOnNaviSearchListener(OnNaviSearchListener listener) {
        this.onNaviSearchListener = listener;
    }

    /**
     * Sets on micro volume listener.
     *
     * @param listener the listener
     */
    public void setOnMicroVolumeListener(OnMicroVolumeListener listener) {
        this.onMicroVolumeListener = listener;
    }

    /**
     * Sets on expression listener.
     *
     * @param listener the listener
     */
    public void setOnExpressionListener(OnExpressionListener listener) {
        this.onExpressionListener = listener;
    }

    /**
     * Sets on hot words listener.
     *
     * @param listener the listener
     */
    public void setOnHotWordsListener(OnHotWordsListener listener) {
        this.onHotWordsListener = listener;
    }

    /**
     * Sets on robot state battery listener.
     *
     * @param listener the listener
     */
    public void setOnRobotStateBatteryListener(OnRobotStateListener listener) {
        this.onRobotStateBatteryListener = listener;
    }

    /**
     * Sets on robot state charge listener.
     *
     * @param listener the listener
     */
    public void setOnRobotStateChargeListener(OnRobotStateListener listener) {
        this.onRobotStateChargeListener = listener;
    }

    private void setOnRobotStateBatterySelfQueryListener(OnRobotStateListener listener) {
        this.onRobotStateBatterySelfQueryListener = listener;
    }

    private void setOnRobotStateChargeSelfQueryListener(OnRobotStateListener listener) {
        this.onRobotStateChargeSelfQueryListener = listener;
    }


    public void setChargeFailureListener(OnChargeFailureListener listener) {
        this.onChargeFailureListener = listener;
    }


    /**
     * Sets on warning check self listener.
     *
     * @param listener the listener
     */
    public void setOnWarningCheckSelfListener(OnWarningCheckSelfListener listener) {
        this.onWarningCheckSelfListener = listener;
    }

    /**
     * Sets on robot type listener.
     *
     * @param listener the listener
     */
    public void setOnRobotTypeListener(OnRobotTypeListener listener) {
        this.onRobotTypeListener = listener;
    }

    /**
     * Sets on detect person listeners.
     *
     * @param listener the listener
     */
    public void setOnDetectPersonListeners(OnDetectPersonListener listener) {
        this.onDetectPersonListener = listener;
    }

    /**
     * Sets on get version listener.
     *
     * @param listener the listener
     */
    public void setOnGetVersionListener(OnGetVersionListener listener) {
        this.onGetVersionListener = listener;
    }

    /**
     * Sets on upgrade software check listener.
     *
     * @param listener the listener
     */
    public void setOnUpgradeSoftwareCheckListener(OnUpgradeListener listener) {
        this.onUpgradeSoftwareCheckListener = listener;
    }

    /**
     * Sets on upgrade software upgrade listener.
     *
     * @param listener the listener
     */
    public void setOnUpgradeSoftwareUpgradeListener(OnUpgradeListener listener) {
        this.onUpgradeSoftwareUpgradeListener = listener;
    }


    /**
     * Sets on sn listener.
     *
     * @param listener the listener
     */
    public void setOnSNListener(OnSNListener listener) {
        this.onSNListener = listener;
    }

    /**
     * Sets on device info listener.
     *
     * @param listener the listener
     */
    public void setOnDeviceInfoListener(OnDeviceInfoListener listener) {
        this.onDeviceInfoListener = listener;
    }


    public void setOnGetCurrentMapImageListener(OnGetCurrentMapImageListener listener) {
        onGetCurrentMapImageListener = listener;
    }

    /**
     * Sets on snapshoto listener.
     *
     * @param listener the listener
     */
    public void setOnSnapshotoListener(OnSnapshotoListener listener) {
        this.onSnapshotoListener = listener;
    }

    /**
     * Sets on face save listener.
     *
     * @param listener the listener
     */
    public void setOnFaceSaveListener(OnFaceSaveListener listener) {
        this.onFaceSaveListener = listener;
    }

    /**
     * Sets on map list listener.
     *
     * @param listListener the list listener
     */
    public void setOnMapListListener(OnMapListListener listListener) {
        this.onMapListListener = listListener;
    }

    /**
     * Sets on elevator ctrl listener.
     *
     * @param listener the listener
     */
    public void setOnElevatorCtrlListener(OnElevatorCtrlListener listener) {
        onElevatorCtrlListener = listener;
    }

    /**
     * Sets on elevator state listener.
     *
     * @param listener the listener
     */
    public void setOnElevatorStateListener(OnElevatorStateListener listener) {
        onElevatorStateListener = listener;
    }

    /**
     * Sets on double door state listener.
     *
     * @param listener the listener
     */
    public void setOnDoubleDoorStateListener(OnDoubleDoorStateListener listener) {
        onDoubleDoorStateListener = listener;
    }

    public void setOnWebSocketDataListener(OnWebSocketDataListener listener) {
        this.onWebSocketDataListener = listener;
    }

    public void setOnRemoteCtrlKeyListener(OnRemoteCtrlKeyListener listener) {
        this.onRemoteCtrlKeyListener = listener;
    }

    public void setSerialPowerInfoListener(OnRobotSerialPowerInfoListener listener) {
        onSerialPowerInfoListener = listener;
    }

    public void setOnRobotCtrlListener(OnRobotCtrlListener listener) {
        this.onRobotCtrlListener = listener;
    }

    /**
     * Push connect.
     *
     * @param type the type
     */
    public void pushConnect(int type) {
        switch (type) {
            case ConnectConstants.ConnectStatus.SUCCESS:
                if (mState != null) {
                    mState.setConnect(true);
                }

                if (onConnectListeners.size() > 0) {
                    for (OnConnectListener onInitListener : onConnectListeners) {
                        onInitListener.success();
                    }
                }

                getBasicInfo();
                break;
            case ConnectConstants.ConnectStatus.FAILD: {
//                String cmd = "am startservice -a com.csjbot.robotsdkservice.startservice ";
//                ShellUtil.execCmd(cmd, true, false);
//                CosLogger.info("FAILD, start SDK service");
//
//                if (mState != null) {
//                    mState.setConnect(true);
//                }
//                if (onConnectListeners.size() > 0) {
//                    for (OnConnectListener onInitListener : onConnectListeners) {
//                        onInitListener.faild();
//                    }
//                }
            }
            break;
            case ConnectConstants.ConnectStatus.TIMEOUT:
                CosLogger.info("TIMEOUT, start SDK service");

                if (mState != null) {
                    mState.setConnect(true);
                }
                if (onConnectListeners.size() > 0) {
                    for (OnConnectListener onInitListener : onConnectListeners) {
                        onInitListener.timeout();
                    }
                }
                break;
            case ConnectConstants.ConnectStatus.DISCONNECT: {
                if (mState != null) {
                    mState.setConnect(true);
                }

                String cmd = "am startservice -a com.csjbot.robotsdkservice.startservice ";
                ShellUtil.execCmd(cmd, true, false);
                CosLogger.info("DISCONNECT, start SDK service");

                if (onConnectListeners.size() > 0) {
                    for (OnConnectListener onInitListener : onConnectListeners) {
                        onInitListener.disconnect();
                    }
                }
            }
            break;
            default:
                break;
        }

    }

    /**
     * Push speech.
     *
     * @param json the json
     * @param type the type
     */
    public void pushSpeech(String json, int type) {
//        for (OnSpeechListener onSpeechListener : onSpeechListeners) {
//            onSpeechListener.speechInfo(json, type);
//        }
        if (onSpeechListener != null) {
            onSpeechListener.speechInfo(json, type);
        }
    }

    /**
     * Push wakeup.
     *
     * @param angle the angle
     */
    public void pushWakeup(int angle) {
//        for (OnWakeupListener listener : onWakeupListeners) {
//            if (listener != null) {
//                listener.response(angle);
//            }
//        }

        if (onWakeupListener != null) {
            onWakeupListener.response(angle);
        }
    }

    /**
     * Push speech get result.
     *
     * @param json the json
     */
    public void pushSpeechGetResult(String json) {
        if (onSpeechGetResultListener != null) {
            onSpeechGetResultListener.response(json);
            onSpeechGetResultListener = null;
        }
    }

    /**
     * Push position.
     *
     * @param json the json
     */
    public void pushPosition(String json) {
        if (onPositionListener != null) {
            onPositionListener.positionInfo(json);
            onPositionListener = null;
        }
    }

    /**
     * Push face.
     *
     * @param json the json
     */
    public void pushFace(String json) {
        for (OnFaceListener listener : onFaceListeners) {
            if (listener != null) {
                listener.personInfo(json);
            }
        }
    }

    /**
     * Push face.
     *
     * @param person the person
     */
    public void pushFace(boolean person) {
        for (OnFaceListener listener : onFaceListeners) {
            if (listener != null) {
                listener.personNear(person);
            }
        }
    }

    /**
     * Push detect person.
     *
     * @param state the state
     */
    public void pushDetectPerson(int state) {
        if (onDetectPersonListener != null) {
            onDetectPersonListener.response(state);
            onDetectPersonListener = null;
        }
        for (OnDetectPersonListener detectPersonListener : onDetectPersonListeners) {
            detectPersonListener.response(state);
        }
    }

    /**
     * Push camera.
     *
     * @param bitmap the bitmap
     */
    public void pushCamera(Bitmap bitmap) {
        for (OnCameraListener cameraListener : onCameraListeners) {
            cameraListener.response(bitmap);
        }
    }

    /**
     * Push head touch.
     */
    public void pushHeadTouch() {
        for (OnHeadTouchListener onHeadTouchListener : onHeadTouchListeners) {
            onHeadTouchListener.response();
        }
    }

    /**
     * Push head touch.
     */
    public void pushBodyCtrl() {
        if (onBodyCtrlListener != null) {
            onBodyCtrlListener.response();
        }
    }

    /**
     * Push face list.
     *
     * @param json the json
     */
    public void pushFaceList(String json) {
        if (onGetAllFaceListener != null) {
            onGetAllFaceListener.personList(json);
            onGetAllFaceListener = null;
        }
    }

    /**
     * Push navi move result.
     *
     * @param json the json
     */
    public void pushNaviMoveResult(String json) {
        for (OnNaviListener listener : onNaviListeners) {
            listener.moveResult(json);
            Log.d("navi", "pushNaviMoveResult_listener: " + listener);
        }
        Log.d("navi", "pushNaviMoveResult_onNaviMoveResultListener: " + onNaviMoveResultListener);
        if (onNaviMoveResultListener != null) {
            onNaviMoveResultListener.moveResult(json);
            //onNaviMoveResultListener = null;
        }
    }

    /**
     * Push navi message send result.
     *
     * @param json the json
     */
    public void pushNaviMessageSendResult(String json) {
        for (OnNaviListener listener : onNaviListeners) {
            listener.messageSendResult(json);
        }
        if (onNaviMoveResultListener != null) {
            onNaviMoveResultListener.messageSendResult(json);
        }
    }

    /**
     * Push navi cancel result.
     *
     * @param json the json
     */
    public void pushNaviCancelResult(String json) {
        for (OnNaviListener listener : onNaviListeners) {
            listener.cancelResult(json);
        }

        if (onNaviCancelListener != null) {
            onNaviCancelListener.cancelResult(json);
        }
    }

    /**
     * Push navi gohome result.
     *
     * @param json the json
     */
    public void pushNaviGohomeResult(String json) {
        for (OnNaviListener listener : onNaviListeners) {
            listener.goHome();
        }

        if (onNaviGoHomeListener != null) {
            onNaviGoHomeListener.goHome();
            onNaviGoHomeListener = null;
        }
    }

    /**
     * Push go rotation.
     *
     * @param code the code
     */
    public void pushGoRotation(int code) {
        if (onGoRotationListener != null) {
            onGoRotationListener.response(code);
            onGoRotationListener = null;
        }
    }

    /**
     * Push moto overload.
     *
     * @param code the code
     */
    public void pushMotoOverload(int code) {
        if (onMotoOverloadListener != null) {
            onMotoOverloadListener.response(code);
        }
    }

    public void pushSlamVersion(String json) {
        if (slamVersionListener != null) {
            slamVersionListener.response(json);
        }
    }

    /**
     * Push emergency status.
     *
     * @param status the status
     */
    public void pushEmergencyStatus(int status) {
        if (onEmergencyStatusListener != null) {
            onEmergencyStatusListener.response(status);
        }
    }


    /**
     * Push navi search.
     *
     * @param json the json
     */
    public void pushNaviSearch(String json) {
        if (onNaviSearchListener != null) {
            onNaviSearchListener.searchResult(json);
            onNaviSearchListener = null;
        }
    }

    /**
     * Push navi map state.
     *
     * @param json the json
     */
    public void pushNaviMapState(String json) {
        if (onMapStateListener != null) {
            onMapStateListener.mapState(json);
            onMapStateListener = null;
        }
    }

    /**
     * Push micro volume.
     *
     * @param volume the volume
     */
    public void pushMicroVolume(int volume) {
        if (onMicroVolumeListener != null) {
            onMicroVolumeListener.response(volume);
            onMicroVolumeListener = null;
        }
    }

    /**
     * Push expression.
     *
     * @param expression the expression
     */
    public void pushExpression(int expression) {
        if (onExpressionListener != null) {
            onExpressionListener.response(expression);
            onExpressionListener = null;
        }
    }

    /**
     * Push hot words.
     *
     * @param hotWords the hot words
     */
    public void pushHot(List<String> hotWords) {
        if (onHotWordsListener != null) {
            onHotWordsListener.hotWords(hotWords);
            onHotWordsListener = null;
        }
    }

    /**
     * Push battery.
     *
     * @param battery the battery
     */
    public void pushBattery(int battery) {
        if (onRobotStateBatteryListener != null) {
            onRobotStateBatteryListener.getBattery(battery);
        }

        if (onRobotStateBatterySelfQueryListener != null) {
            onRobotStateBatterySelfQueryListener.getBattery(battery);
            onRobotStateBatterySelfQueryListener = null;
        }

        if (onRobotInitListener != null) {
            onRobotInitListener.onSlamState(0, "OK");
            onRobotInitListener = null;
        }
    }

    /**
     * Push charge.
     *
     * @param charge the charge
     */
    public void pushCharge(int charge) {
        if (onRobotStateChargeListener != null) {
            onRobotStateChargeListener.getCharge(charge);
        }

        if (onRobotStateBatteryListener != null) {
            onRobotStateBatteryListener.getCharge(charge);
        }

        if (onRobotStateChargeSelfQueryListener != null) {
            onRobotStateChargeSelfQueryListener.getCharge(charge);
            onRobotStateChargeSelfQueryListener = null;
        }
    }

    /**
     * Push warning check self.
     *
     * @param json the json
     */
    public void pushWarningCheckSelf(String json) {
        CsjlogProxy.getInstance().debug("onWarningCheckSelfListener = {}", onWarningCheckSelfListener);

        if (onWarningCheckSelfListener != null) {
            onWarningCheckSelfListener.response(json);
        }

        CsjlogProxy.getInstance().debug("hardware = {}", json);
        if (onRobotInitListener != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                boolean isX = jsonObject.optBoolean("isXRobot");
                if (isX) {
                    JSONArray jsonArray = jsonObject.optJSONArray("list");
                    JSONObject first = jsonArray.getJSONObject(0);
                    if (first != null) {
                        String ok = first.optString("state");
                        if (TextUtils.equals(ok, "OK")) {
                            onRobotInitListener.onHardWareHealthState(0, "OK");
                        } else {
                            onRobotInitListener.onHardWareHealthState(1, "is XRobot but not OK");
                        }
                    }
                } else {
                    onRobotInitListener.onHardWareHealthState(0, "NOT XROBOT");
//                    onRobotInitListener.onHardWareHealthState(-1, "NOT XRobot");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Push robot type.
     *
     * @param linuxRobotType the linux robot type
     */
    public void pushRobotType(String linuxRobotType) {
        if (onRobotTypeListener != null) {
            onRobotTypeListener.robotType(linuxRobotType);
        }
    }

    /**
     * Push version.
     *
     * @param version the version
     */
    public void pushVersion(String version) {
        if (onGetVersionListener != null) {
            onGetVersionListener.response(version);
        }
    }

    /**
     * Push software check.
     *
     * @param errorCode the error code
     */
    public void pushSoftwareCheck(int errorCode) {
        if (onUpgradeSoftwareCheckListener != null) {
            onUpgradeSoftwareCheckListener.checkRsp(errorCode);
            onUpgradeSoftwareCheckListener = null;
        }
    }

    /**
     * Push software upgrade.
     *
     * @param errorCode the error code
     */
    public void pushSoftwareUpgrade(int errorCode) {
        if (onUpgradeSoftwareUpgradeListener != null) {
            onUpgradeSoftwareUpgradeListener.upgradeRsp(errorCode);
            onUpgradeSoftwareUpgradeListener = null;
        }
    }

    /**
     * Push software upgrade progress.
     *
     * @param downloadProgress the download progress
     */
    public void pushSoftwareUpgradeProgress(int downloadProgress) {
        if (onUpgradeSoftwareUpgradeListener != null) {
            onUpgradeSoftwareUpgradeListener.upgradeProgress(downloadProgress);
        }
    }

    /**
     * Push sn.
     *
     * @param sn the sn
     */
    public void pushSn(String sn) {
        if (onSNListener != null) {
            onSNListener.response(sn);
            onSNListener = null;
        }
    }

    /**
     * Push device info.
     *
     * @param info the info
     */
    public void pushDeviceInfo(String info) {
        if (onDeviceInfoListener != null) {
            onDeviceInfoListener.response(info);
            onDeviceInfoListener = null;
        }
    }

    /**
     * Push snapshoto.
     *
     * @param json the json
     */
    public void pushSnapshoto(String json) {
        if (onSnapshotoListener != null) {
            onSnapshotoListener.response(json);
            onSnapshotoListener = null;
        }
    }

    /**
     * Push face save.
     *
     * @param json the json
     */
    public void pushFaceSave(String json) {
        if (onFaceSaveListener != null) {
            onFaceSaveListener.response(json);
            onFaceSaveListener = null;
        }
    }

    /**
     * Push map list.
     *
     * @param json the json
     */
    public void pushMapList(String json) {
        if (onMapListListener != null) {
            onMapListListener.response(json);
            onMapListListener = null;
        }
    }

    /**
     * Push navi speed.
     *
     * @param speed the speed
     */
    public void pushNaviSpeed(double speed) {
        if (onSpeedGetListener != null) {
            onSpeedGetListener.getNaviSpeed(speed);
        }
    }

    /**
     * Push elevator ctrl.
     *
     * @param error    the error
     * @param cmd_type the cmd type
     * @param result   the result
     */
    public void pushElevatorCtrl(int error, String cmd_type, String result) {
        if (onElevatorCtrlListener != null) {
            onElevatorCtrlListener.response(error, cmd_type, result);
        }
    }

    /**
     * Push elevator open state.
     *
     * @param errorcode the errorcode
     */
    public void pushElevatorOpenState(int errorcode) {
        if (onElevatorStateListener != null) {
            onElevatorStateListener.openState(errorcode);
        }
    }

    public void pushCurrentMap(String mapName, String base64, int code) {

        if (onGetCurrentMapImageListener != null) {
            onGetCurrentMapImageListener.onCurrentMapBitmap(mapName, base64, code);
        }
    }


    /**
     * Push elevator info.
     *
     * @param min the min
     * @param max the max
     */
    public void pushElevatorInfo(int min, int max) {
        if (onElevatorStateListener != null) {
            onElevatorStateListener.getElevatorInfo(min, max);
        }
    }

    /**
     * Push on map load listener.
     *
     * @param state the state
     */
    public void pushOnMapLoadListener(int state) {
        if (onMapListener != null) {
            onMapListener.loadMap(state);
        }
    }

    /**
     * Push on map save listener.
     *
     * @param state the state
     */
    public void pushOnMapSaveListener(int state) {
        if (onMapListener != null) {
            onMapListener.saveMap(state);
        }
    }


    /**
     * Push robot dock state.
     *
     * @param state the state
     */
    public void pushRobotDockState(int state) {
        if (robotDockStateListener != null) {
            robotDockStateListener.getDockState(state);
        }
    }

    /**
     * Push double door state.
     *
     * @param up  上仓状态，down  下仓状态
     */
    public void pushDoubleDoorState(int up,int down) {
        if (onDoubleDoorStateListener != null) {
            onDoubleDoorStateListener.onDoorState(up, down);
        }
    }

    public void pushDoorControl(int state) {
        if (doorControlListeners != null) {
            doorControlListeners.response(state);
        }
    }

    public void pushPowerStatus(int p) {
        if (onPowerStatusListener != null) {
            if (p == 0) {
                onPowerStatusListener.onNotPressed();
            } else if (p == 1) {
                onPowerStatusListener.onPressed();
            } else if (p == 2) {
                onPowerStatusListener.onLongPressOne();
            } else if (p == 3) {
                onPowerStatusListener.onLongPressFive();
            }
        }
    }

    public void pushLQStateLow(int i) {
        if (onPositioningQualityListener != null) {
            onPositioningQualityListener.lQStateLow(i);
        }

        if (onRobotInitListener != null) {
            onRobotInitListener.onSlamState(0, "OK");
        }
    }

    public void pushLQStateNormal(int lq) {
        if (onPositioningQualityListener != null) {
            onPositioningQualityListener.lQStateNormal(lq);
        }

        if (onRobotInitListener != null) {
            onRobotInitListener.onSlamState(0, "OK");

        }
    }

    public void setOnMqttConnectStateListener(OnMqttConnectStateListener listener) {
        onMqttConnectStateListener = listener;
    }

    public void pushMqttState(int error_code) {
        if (onMqttConnectStateListener != null) {
            onMqttConnectStateListener.mqttConnect(error_code == 0);
        }

        if (onRobotInitListener != null) {
            onRobotInitListener.onServerConnectState(error_code, "error_code");
            CosLogger.warn("mqtt connected ");
        }
    }

    /**
     * Sets direct listener.
     *
     * @param listener the listener
     */
    public void setDirectListener(DirectListener listener) {
        BaseReceiveService.setDirectListener(listener);
        BaseClientReq.setDirectListener(listener);
    }

    public void setSlamVersionListener(OnSlamVersionListener listener) {
        this.slamVersionListener = listener;
    }

    public void setOnDestReachableListener(OnDestReachableListener listener) {
        this.reachableListener = listener;
    }

    public void pushDestReachable(boolean reachable) {
        if (reachableListener != null) {
            reachableListener.destReachable(reachable);
        }
    }

    /**
     * Send direct message.
     *
     * @param msg the msg
     */
    public void sendDirectMessage(String msg) {
        CosClientAgent agent = CosClientAgent.getRosClientAgent();
        agent.sendMessage(new CommonPacket(msg.getBytes()));
    }

    public void pushSensorHealth(String json) {
        if (onSensorHealthListener != null) {
            onSensorHealthListener.health(json);
        }
    }

    public void pushChargeFailure() {
        if (onChargeFailureListener != null) {
            onChargeFailureListener.response();
        }
    }

    public void pushFaceCoordinate(String json) {
        if (faceCoordinateListener != null) {
            faceCoordinateListener.response(json);
        }
    }

    /**
     * （可能一直等待）运动跟踪中的无有效跟踪点：
     */
    public void pushBlockedState() {
        if (robotMoveStatusListener != null) {
            robotMoveStatusListener.blocked();
        }
    }

    //5s左右等待）局部路径被挡：
    public void pushWaitShortStatus() {
        if (robotMoveStatusListener != null) {
            robotMoveStatusListener.waitShort();
        }
    }

    //（20s左右等待）绕路：
    public void pushWaitLongStatus() {
        if (robotMoveStatusListener != null) {
            robotMoveStatusListener.waitLong();
        }
    }

    public void pushRunningStatus() {
        if (robotMoveStatusListener != null) {
            robotMoveStatusListener.onRunning();
        }
    }

    public void pushWebSocketData(String message) {
//        CsjlogProxy.getInstance().debug("mqtt message =  " + message);
        if (onWebSocketDataListener != null) {
            onWebSocketDataListener.onWebDataMessage(message);
        }
    }


    public void pushRemoteCtrl(int keyCode, int keyValue) {
        if (onRemoteCtrlKeyListener != null) {
            onRemoteCtrlKeyListener.onKeyEvent(keyCode, keyValue);
        }
    }

    // 从串口获取的电量信息
    public void pushSerialPowerInfo(int powerState, int powerValue) {
        if (onSerialPowerInfoListener != null) {
            onSerialPowerInfoListener.onPowerInfo(powerState, powerValue);
        }
    }

    public void pushCustomServiceMsg(String json) {
        if (onCustomServiceMsgListener != null) {
            onCustomServiceMsgListener.onMsg(json);
        }
    }

    public void pushRobotCtrl(int action) {
        if (onRobotCtrlListener != null) {
            onRobotCtrlListener.onCtrlAction(action);
        }
    }

    public void putCustomerState(int type, int state) {
        if (customerStateListener != null) {
            customerStateListener.response(type, state);
        }
    }

    public void pushErrorInfo(String type, String msg) {
        if (onErrorInfoListener != null) {
            onErrorInfoListener.onErrorInfo(type, msg);
        }
    }

    /**
     * 当人工客户切换成音频时，不上推识别结果
     *
     * @param isPushText
     */
    public void setCustomerAudioStatus(boolean isPushText) {
        RobotContants.isPushText = isPushText;
    }

    public void pushNaviPoints() {
        if (onGetAllPointsListener != null) {
            onGetAllPointsListener.getAllPoints();

        }
    }

    public void pushConnectState(boolean state) {
        if (robotConnectionStateListener != null) {
            robotConnectionStateListener.connectState(state);
        }
    }

    public void pushRobotNaviStates(boolean ready, int mode) {
        if (onRobotNaviStatesListener != null) {
            onRobotNaviStatesListener.onNavi(ready, mode);
        }
    }
}
