package com.demo.csjbot.csjsdkdemo.future;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.Speech;
import com.csjbot.coshandler.core.State;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.global.CmdConstants;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnSpeechListener;
import com.csjbot.coshandler.listener.OnWakeupListener;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.tts.ISpeechSpeak;
import com.csjbot.coshandler.tts.SpeechFactory;
import com.csjbot.coshandler.util.ShellUtil;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.entity.AsrCfgBean;
import com.demo.csjbot.csjsdkdemo.utils.JsonFormatTool;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AsrNlpActivity extends BaseActivity {
    private TextView asr_result, nlp_result, wakeup_angle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asr_nlp);

        initLogShow();

        asr_result = (TextView) findViewById(R.id.asr_result);
        nlp_result = (TextView) findViewById(R.id.nlp_result);
        wakeup_angle = (TextView) findViewById(R.id.wakeup_angle);

        mCsjBot.registerSpeechListener(onSpeechListener);

        mCsjBot.registerWakeupListener(onWakeupListener);
    }

    private OnSpeechListener onSpeechListener = new OnSpeechListener() {
        @Override
        public void speechInfo(final String s, final int i) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Simple parsing example
                    if (Speech.SPEECH_RECOGNITION_RESULT == i) { // Identified information
                        try {
                            String text = new JSONObject(s).getString("text");
                            asr_result.setText(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else if (Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT == i) {// Identified information and answers
                        try {
                            String say = new JSONObject(s).getJSONObject("result").getJSONObject("data").getString("say");
                            nlp_result.setText(say);
                            speechSpeak.startSpeaking(say, null);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    };

    private OnWakeupListener onWakeupListener = new OnWakeupListener() {
        @Override
        public void response(final int i) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wakeup_angle.setText(String.format(Locale.getDefault(), getString(R.string.wakeup_angle), i));

                    speechSpeak.startSpeaking(getString(R.string.im_here), null);

                    // Turn around after locating the sound source
                    mCsjBot.getAction().moveAngle(i, new OnGoRotationListener() {
                        @Override
                        public void response(int i) {
                            if (i > 0 && i < 360) {
                                if (i <= 180) {
                                    if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                        mCsjBot.getAction().moveAngle(i, null);
                                    }
                                } else {
                                    if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                        mCsjBot.sendDirectMessage("{\"msg_id\":\"SET_WAKEUP_BEAM_REQ\",\"beam\":0}");
                                        mCsjBot.getAction().moveAngle(-(360 - i), null);
                                    }
                                }
                            }
                        }
                    });
                }
            });
        }
    };

    @Override
    public void onBackPressed() {
        mCsjBot.unRegisterSpeechListener(onSpeechListener);
        mCsjBot.unRegisterWakeupListener(onWakeupListener);
        this.finish();
    }

    public void startAsrService(View view) {
        CsjRobot.getInstance().getSpeech().startSpeechService();
    }

    public void stopAsrService(View view) {
        CsjRobot.getInstance().getSpeech().closeSpeechService();
        mCsjBot.sendDirectMessage(String.format(Locale.getDefault(), "{\"msg_id\":\"%s\",\"level\":%d}",
                "SET_SERIAL_PORT_LOG", 1));
    }

    public void TurnOnMultipleASR(View view) {
        CsjRobot.getInstance().getSpeech().startIsr();
    }

    public void TurnOffMultipleASR(View view) {
        CsjRobot.getInstance().getSpeech().stopIsr();
    }

    public void textToSpeech(View view) {
        String result = nlp_result.getText().toString();
        if (TextUtils.isEmpty(result)) {
            result = getString(R.string.content_is_null);
        }
        speechSpeak.startSpeaking(result, null);
    }
}
