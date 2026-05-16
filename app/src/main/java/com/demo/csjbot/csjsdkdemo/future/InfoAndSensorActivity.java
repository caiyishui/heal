package com.demo.csjbot.csjsdkdemo.future;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.csjbot.coshandler.listener.OnEmergencyStatusListener;
import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnMotoOverloadListener;
import com.csjbot.coshandler.listener.OnRobotTypeListener;
import com.csjbot.coshandler.listener.OnSNListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;
import com.demo.csjbot.csjsdkdemo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class InfoAndSensorActivity extends BaseActivity {

    private TextView sdkVersion, robotSN, robotType, robotHW, emergency_stop, motor_overload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sensor);

        initLogShow();

        sdkVersion = (TextView) findViewById(R.id.sdkVersion);
        robotSN = (TextView) findViewById(R.id.robotSN);
        robotType = (TextView) findViewById(R.id.robotType);
        robotHW = (TextView) findViewById(R.id.robotHW);
        emergency_stop = (TextView) findViewById(R.id.emergency_stop);
        motor_overload = (TextView) findViewById(R.id.motor_overload);

        mCsjBot.getState().getSN(onSNListener);
        mCsjBot.getState().getRobotType(onRobotTypeListener);
        mCsjBot.getState().getEmergencyStatus(onEmergencyStatusListener);
        mCsjBot.getState().getMotoOverloadState(onMotoOverloadListener);
        mCsjBot.getState().checkSelf(onWarningCheckSelfListener);
        mCsjBot.getVersion().getVersion(onVersionListener);
        mCsjBot.getState().releaseEmergency();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private OnEmergencyStatusListener onEmergencyStatusListener = new OnEmergencyStatusListener() {
        @Override
        public void response(int i) {
            String stop = i == 0 ? getString(R.string.emergency_pressed) : getString(R.string.emergency_released);
            showMsgInTextView(emergency_stop, stop);
        }
    };

    private OnMotoOverloadListener onMotoOverloadListener = new OnMotoOverloadListener() {
        @Override
        public void response(int i) {
            String overload = i == 0 ? getString(R.string.not_overload) : getString(R.string.overload);
            showMsgInTextView(motor_overload, overload);
        }
    };

    private OnRobotTypeListener onRobotTypeListener = new OnRobotTypeListener() {
        @Override
        public void robotType(String s) {
            if (!TextUtils.isEmpty(s)) {
                showMsgInTextView(robotType, s);
            }
        }
    };

    private OnWarningCheckSelfListener onWarningCheckSelfListener = new OnWarningCheckSelfListener() {
        @Override
        public void response(String s) {
            showMsgInTextView(robotHW, s);
        }
    };

    private OnSNListener onSNListener = new OnSNListener() {
        @Override
        public void response(String s) {
            try {
                JSONObject root = new JSONObject(s);
                showMsgInTextView(robotSN, root.optString("sn"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private OnGetVersionListener onVersionListener = new OnGetVersionListener() {
        @Override
        public void response(String s) {
            showMsgInTextView(sdkVersion, s);
        }
    };

    public void getSDKVersion(View view) {
        mCsjBot.getVersion().getVersion(onVersionListener);
    }

    public void getSN(View view) {
        mCsjBot.getState().getSN(onSNListener);
    }

    public void getRobotType(View view) {
        mCsjBot.getState().getRobotType(onRobotTypeListener);
    }

    public void emergencyStop(View view) {
        mCsjBot.getState().getEmergencyStatus(onEmergencyStatusListener);
    }

    public void motorOverload(View view) {
        mCsjBot.getState().getMotoOverloadState(onMotoOverloadListener);
    }

    public void getRobotHW(View view) {
        mCsjBot.getState().checkSelf(onWarningCheckSelfListener);
    }


    public void RelieveOverload(View view) {
        mCsjBot.getState().clearMotoOverload();
    }
}
