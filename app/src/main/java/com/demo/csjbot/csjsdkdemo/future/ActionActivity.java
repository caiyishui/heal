package com.demo.csjbot.csjsdkdemo.future;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.Speech;
import com.csjbot.coshandler.core.State;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.global.REQConstants;
import com.csjbot.coshandler.listener.OnBodyCtrlListener;
import com.csjbot.coshandler.listener.OnDoubleDoorStateListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnSpeechListener;
import com.csjbot.coshandler.listener.OnWakeupListener;
import com.csjbot.coshandler.tts.ISpeechSpeak;
import com.csjbot.coshandler.tts.SpeechFactory;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.utils.AutoTestManager;
import com.demo.csjbot.csjsdkdemo.utils.JsonFormatTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActionActivity extends BaseActivity {
    private TextView asr_result, nlp_result, wakeup_angle, double_door_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        initLogShow();

        double_door_state = (TextView) findViewById(R.id.double_door_state);

        AutoTestManager.getInstance().setOnMsgListener(new AutoTestManager.OnMsgListener() {
            @Override
            public void msg(String msg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }
        });

//        mCsjBot.getAction().getDoubleDoorState(stateListener);
        CsjRobot.getInstance().setOnDoubleDoorStateListener(stateListener);

//        mCsjBot.getAction().
    }

    private OnDoubleDoorStateListener stateListener = new OnDoubleDoorStateListener() {
        @Override
        public void onDoorState(int state,int downState) {
            if (state == 5) {
                showMsgInTextView(double_door_state, "door state is  error");
                return;
            }

            boolean isClose = state == 1;
            if(isClose){
                // go
            } else {
                // do something
            }

            showMsgInTextView(double_door_state, "door state is " + (state == 1 ? "closed" : "open"));
        }
    };

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void AliceNormal(View view) {
        mCsjBot.getExpression().normal();
    }

    public void AliceHappy(View view) {
        mCsjBot.getExpression().happy();
    }

    public void AliceSad(View view) {
        mCsjBot.getExpression().sadness();
    }

    public void AliceAngry(View view) {
        mCsjBot.getExpression().angry();
    }

    public void AliceCharge(View view) {
        mCsjBot.getExpression().lightning();
    }

    public void AliceSleepy(View view) {
        mCsjBot.getExpression().sleepiness();
    }

    public void aliceReset(View view) {
        mCsjBot.getAction().AliceHeadHReset();
    }

    public void aliceUpHead(View view) {
        mCsjBot.getAction().AliceHeadUp();
    }

    public void aliceDownHead(View view) {
        mCsjBot.getAction().AliceHeadDown();
    }

    public void aliceLeftHead(View view) {
        mCsjBot.getAction().AliceHeadLeft();
    }

    public void aliceRightHead(View view) {
        mCsjBot.getAction().AliceHeadRight();
    }

    public void aliceDownLeft(View view) {
        mCsjBot.getAction().AliceLeftArmDown();
    }


    private OnBodyCtrlListener listener = new OnBodyCtrlListener() {
        @Override
        public void response() {

        }
    };

    public void aliceUpLeft(View view) {
//        mCsjBot.getAction().AliceLeftArmUp();

        mCsjBot.getAction().AliceLeftArmUp(listener);

    }

    public void aliceUpRight(View view) {
        mCsjBot.getAction().AliceRightArmUp();
    }

    public void aliceDownRight(View view) {
        mCsjBot.getAction().AliceRightArmDown();
    }

    public void aliceAutoTestOpen(View view) {
        AutoTestManager.getInstance().start();
    }

    public void aliceAutoTestClose(View view) {
        AutoTestManager.getInstance().stop();
    }

    public void openDoubleDoor(View view) {
        mCsjBot.getAction().openDoubleDoor(null);
    }

    public void closeOpenDoor(View view) {
        mCsjBot.getAction().closeDoubleDoor(null);
    }


    boolean auto_run = false;

    public void arm_auto_run(View view) {
        auto_run = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (auto_run) {
                    mCsjBot.getAction().AliceRightArmUp();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mCsjBot.getAction().AliceRightArmDown();
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mCsjBot.getAction().AliceLeftArmUp();


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mCsjBot.getAction().AliceLeftArmDown();

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void arm_auto_run_stop(View view) {
        auto_run = false;
    }


    boolean isSnowSwingArms = false;

    public void snow_start_arm_swing(View view) {
        if (isSnowSwingArms) {
            return;
        }
        isSnowSwingArms = true;
        JSONObject jo = new JSONObject();
        try {
            jo.put("msg_id", REQConstants.BodyAction.ROBOT_ARM_LOOP_START_REQ);
            jo.put("interval_time", 1500);
            mCsjBot.sendDirectMessage(jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void snow_stop_arm_swing(View view) {
        JSONObject jo = new JSONObject();
        isSnowSwingArms = false;

        try {
            jo.put("msg_id", REQConstants.BodyAction.ROBOT_ARM_LOOP_STOP_REQ);
            mCsjBot.sendDirectMessage(jo.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
