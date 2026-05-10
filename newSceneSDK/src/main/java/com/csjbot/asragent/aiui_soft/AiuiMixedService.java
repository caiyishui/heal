package com.csjbot.asragent.aiui_soft;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.PixelFormat;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.csjbot.asragent.IAlsaRawDataSender;
import com.csjbot.asragent.IAlsaServerConnecter;
import com.csjbot.asragent.aiui_soft.listener.MsgCollentListener;
import com.csjbot.asragent.aiui_soft.util.Config;
import com.csjbot.asragent.aiui_soft.util.FileUtil;
import com.csjbot.asragent.aiui_soft.util.NetworkUtils;
import com.csjbot.asragent.aiui_soft.util.FileUtil;
import com.csjbot.coshandler.R;
import com.csjbot.coshandler.aiui.aiui_soft.send_msg.http.ServerFactory;
import com.csjbot.coshandler.log.Csjlogger;
import com.csjbot.coshandler.util.ConfInfoUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import com.csjbot.coshandler.log.CosLogger;
import com.csjbot.coshandler.log.Csjlogger;

import java.io.File;
import java.util.Locale;


public class AiuiMixedService extends Service {

    private static AIUIMixedManager aiuiManager;
    private Handler handler = new Handler();

    public static AIUIPushSpeechRecgContentListener pushDataListener;
    private static MsgCollentListener msgCollentListener;
    private static AIUIMixedManager.AIUIType aiuiType;
    String path = "/sdcard/vtn/cae/resources/config/vtn.ini";
    private IAlsaRawDataSender iAlsaRawDataSender;
    String vniString = "[auth]\n" +
            "appid=%s\n" +
            "\n" +
            "\n" +
            "[cae]\n" +
            "#是否开启降噪功能, 0为不开启，其他为开启，默认为开启\n" +
            "cae_enable = 1\n" +
            "\n" +
            "#beam取值说明: -2 表示不输出任何音频, -1 第四路为识别音频，无第五路,\n" +
            "#0，1，2时，第四路为指定波束的音频，第五路为vad音频\n" +
            "beam = 1\n" +
            "\n" +
            "# 采样位深度说明  2：短整型16bit 、 4：整型32bit\n" +
            "input_audio_unit = 2\n" +
            "\n" +
            "#是否抛出VAD音频  不启用或取值为0\n" +
            "output_vad = 1\n" +
            "\n" +
            "# 新版本降噪算法加载的算法资源\n" +
            "aes_model = /sdcard/vtn/cae/resources/models/mlp_aes_1024_tv_xTxT_denoise.bin\n" +
            "aes_vcall_model= /sdcard/vtn/cae/resources/models/mlp_aes_01_vcall_20210510.bin\n" +
            "partition_model= /sdcard/vtn/cae/resources/models/mlp_partition_4mic_5beam_512.bin\n" +
            "partition_model_rec= /sdcard/vtn/cae/resources/models/mlp_lstm_sp_20201016.bin\n" +
            "select_model= /sdcard/vtn/cae/resources/models/mlp_select_6to3_1024.bin\n" +
            "td_model= /sdcard/vtn/cae/resources/models/mlp_td_fsmn_hxxj.bin\n" +
            "\n" +
            "\n" +
            "[caeEngine]\n" +
            "td_model_type = fsmn\n" +
            "\n" +
            "# (窄波束：设置位1的时候是40-140，0的话就是中间最窄的75-105)\n" +
            "# nWorkMode =1\n" +
            "\n" +
            "# 窄波束VAD音频平滑处理： 1启用，0不启用。不启用抑制效果更好,但识别率会下降\n" +
            "vad_sqrt = 0\n" +
            "\n" +
            "[ivw]\n" +
            "# 0为不开启唤醒，其他为开启\n" +
            "ivw_enable = 1\n" +
            "\n" +
            "#唤醒资源\n" +
            "res_path=/sdcard/vtn/cae/resources/ivw/res.bin\n" +
            "\n" +
            "# 1为开启唤醒音频保存，0为不开启\n" +
            "output_wakeup_audio = 0\n" +
            "# 唤醒音频保存的文件夹路径,自定义\n" +
            "wakeup_output_dir = /sdcard/vtn/cae/resources/audio_ivw\n";

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setVni();
        aiuiManager = AIUIMixedManager.getInstance();
        aiuiManager.setPushSpeechRecgContentListener(AiuiMixedService.pushDataListener);
        aiuiManager.initSDK(this, aiuiType);
        aiuiManager.setMsgCollentListener(AiuiMixedService.msgCollentListener);
        handler.postDelayed(() -> aiuiManager.startAudioRecognize(), 1000);

        createFloatView();

        getAIUIkey();

        /**
         * adb shell am broadcast -a "com.csjbot.aiui_playback" --ez playback true
         *
         * adb shell am broadcast -a "com.csjbot.aiui_playback" --ez playback false
         */
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean playback = intent.getBooleanExtra("playback", false);
                boolean ret = false;
                if (playback) {
                    ret = aiuiManager.startAudioPlayBack();
                    Toast.makeText(AiuiMixedService.this, ret ? "start playback OK " : "start playback Failed", Toast.LENGTH_SHORT).show();
                } else {
                    ret = aiuiManager.stopAudioPlayBack();
                    Toast.makeText(AiuiMixedService.this, ret ? "stop playback OK " : "stop playback Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter("com.csjbot.aiui_playback"));

        /**
         *
         * adb shell am broadcast -a "com.csjbot.aiui.action" --ez start true
         *
         * adb shell am broadcast -a "com.csjbot.aiui.action" --ez start false
         */
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean start = intent.getBooleanExtra("start", false);
                if (start) {
                    boolean b = aiuiManager.startSaveAudio();
                    if (b) {
                        Toast.makeText(context, "开始录音成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "开始录音失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean b = aiuiManager.stopSaveAudio();
                    if (b) {
                        Toast.makeText(context, "关闭录音成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "关闭录音失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new IntentFilter("com.csjbot.aiui.action"));

        IntentFilter usbDeviceStateFilter = new IntentFilter();
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        usbDeviceStateFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        registerReceiver(mUsbReceiver, usbDeviceStateFilter);
    }

    BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            CosLogger.debug("usb 动作 " + action);
//            UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

//            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
//                if (device != null) {
//                    String proName = device.getProductName();
//                    CosLogger.error("usb devices {} DETACHED", proName);
//                }
//            } else if (UsbManager.ACTION_USB_DEVICE_AT    TACHED.equals(action)) {
//                if (device != null) {
//                    String proName = device.getProductName();
//                    if (!TextUtils.isEmpty(proName) && proName.contains("AIUI")) {
//                        handler.postDelayed(() -> AIUIMixedManager.getInstance().reInit(), 2000);
//                    }
//                }
//            }
        }
    };


    private void getAIUIkey() {
        NetworkUtils.init(this);
        ServerFactory.createApi().getAiuiKey(ConfInfoUtil.getSN(), NetworkUtils.getMac(AiuiMixedService.this), new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String json = responseBody.string();
                    Csjlogger.info("获取AIUI  key=" + json);
                    JSONObject jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        JSONObject rows = jsonObject.getJSONObject("rows");
                        Config.aiuiID = rows.getString("appId");
                        Config.aiuiKey = rows.getString("appKey");

                        String aiuiKey = String.format(Locale.getDefault(),
                                "{\"appId\":\"%s\",\"appKey\":\"%s\"}", Config.aiuiID, Config.aiuiKey);
                        Csjlogger.warn("获取AIUI file = {}", aiuiKey);

                        FileUtil.writeToFile(new File("/sdcard/.robot_info/aiuikey.txt"), aiuiKey);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Csjlogger.error("获取AIUI  key error " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public static void setAIUIType(AIUIMixedManager.AIUIType type) {
        aiuiType = type;
    }

    public static void setMsgCollentListener(MsgCollentListener msgCollentListener) {
        AiuiMixedService.msgCollentListener = msgCollentListener;
    }

    public static void setPushDataListener(AIUIPushSpeechRecgContentListener pushDataListener) {
        AiuiMixedService.pushDataListener = pushDataListener;
    }

    private void createFloatView() {
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        //获取WindowManagerImpl.CompatModeWrapper
        //创建浮动窗口设置布局参数的对象
        WindowManager mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        //设置window type
        if (Build.VERSION.SDK_INT >= 26) {
            wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        //设置图片格式，效果为背景透明
        wmParams.format = PixelFormat.RGBA_8888;
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams.flags =
//          LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//          LayoutParams.FLAG_NOT_TOUCHABLE
        ;

        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams.gravity = Gravity.TOP | Gravity.START;
//        wmParams.gravity = Gravity.RIGHT | Gravity.CENTER;

        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = -10;
        wmParams.y = -10;

        //设置悬浮窗口长宽数据
        wmParams.width = 1;
        wmParams.height = 1;

        LayoutInflater inflater = LayoutInflater.from(getApplication());
        //获取浮动窗口视图所在布局
        //定义浮动窗口布局
        LinearLayout mFloatLayout = (LinearLayout) inflater.inflate(R.layout.aiui_layout, null);
        //添加mFloatLayout
        mWindowManager.addView(mFloatLayout, wmParams);
    }

    class MyBinder extends IAlsaServerConnecter.Stub {
        @Override
        public void bindAslaServer(String myAppName, String serviceName) throws RemoteException {
            bindClientService(myAppName, serviceName);
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Csjlogger.info("AIUI SDKAar 服务绑定成功，SDKAar 所在App名字 = [" + name + "]");
            iAlsaRawDataSender = IAlsaRawDataSender.Stub.asInterface(service);

//            String test = "测试内容";
//            try {
//                iAlsaRawDataSender.sendAlsaData(test.getBytes());
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
            aiuiManager.setDataSender(iAlsaRawDataSender);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void bindClientService(String appName, String serviceName) {
        Csjlogger.info("AIUI SDKApp 收到命令，开始绑定 [ {} ] 的 [{}] 服务", appName, serviceName);
        Intent intent = new Intent();
        intent.setClassName(appName, serviceName);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }


    public void setVni() {
        File file = new File(path);
        if (file.exists()) {
            String vntid = TextUtils.isEmpty(AIUIMixedManager.aiuiId) ? "6a2398a5" : AIUIMixedManager.aiuiId;
            FileUtil.writeTxtToFile(path, String.format(Locale.getDefault(), vniString, vntid));
        }
    }
}
