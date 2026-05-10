package com.demo.csjbot.csjsdkdemo.future;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.interfaces.DirectListener;
import com.csjbot.coshandler.tts.ISpeechSpeak;
import com.csjbot.coshandler.tts.SpeechFactory;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.utils.JsonFormatTool;
import com.demo.csjbot.csjsdkdemo.utils.SystemUtils;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class  BaseActivity extends AppCompatActivity {
    protected CsjRobot mCsjBot = CsjRobot.getInstance();
    protected static ISpeechSpeak speechSpeak;
    protected Handler mHalder = new MyHandler(this);

    private static class MyHandler extends Handler {
        WeakReference<BaseActivity> myActivity;

        MyHandler(BaseActivity Activity) {
            myActivity = new WeakReference<>(Activity);
        }

        @Override
        public void handleMessage(Message msg) {
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (speechSpeak == null) {
            speechSpeak = SpeechFactory.createSpeech(this, SpeechFactory.SpeechType.GOOGLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // if country != CHINA, set tts to English
                    Configuration configuration = getResources().getConfiguration();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        String language = LocaleList.getDefault().get(0).getLanguage();
                        speechSpeak.setLanguage(Locale.CHINA);
                        if (!TextUtils.equals(language, Locale.CHINA.getLanguage())) {
                            speechSpeak.setLanguage(Locale.CHINA);
                        }
                    } else {
                        speechSpeak.setLanguage(Locale.CHINA);
                    }
                }
            }, 5000);
        }
    }


    protected void showMsgInTextView(final TextView tv, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv.setText(message);
            }
        });
    }


    private EditText send_filter, rec_filter;
    private Button send_filter_clean, rec_filter_clean;
    private ScrollView send_scrollview, rec_scrollview;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private TextView cmd_send_textview, cmd_rec_textview;
    private boolean stopSendShow = false, stopRecShow = false;

    protected void initLogShow() {
        cmd_send_textview = (TextView) findViewById(R.id.cmd_send_textview);
        cmd_rec_textview = (TextView) findViewById(R.id.cmd_rec_textview);
        send_scrollview = (ScrollView) findViewById(R.id.send_scrollview);
        rec_scrollview = (ScrollView) findViewById(R.id.rec_scrollview);
        send_filter = (EditText) findViewById(R.id.send_filter);
        rec_filter = (EditText) findViewById(R.id.rec_filter);
        send_filter_clean = (Button) findViewById(R.id.send_filter_clean);
        rec_filter_clean = (Button) findViewById(R.id.rec_filter_clean);

        mCsjBot.setDirectListener(new DirectListener() {
            @Override
            public void onRecMessage(final String s) {
                if (stopRecShow) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (cmd_rec_textview.getText().length() > 100000) {
                            cmd_rec_textview.setText("");
                        }
                        Date date = new Date();
                        cmd_rec_textview.append("============= " + sdf.format(date) + " =============\r\n");
                        cmd_rec_textview.append(JsonFormatTool.formatJson(s) + "\r\n");
                        cmd_rec_textview.append("\r\n");
                        cmd_rec_textview.append("\r\n");
                        rec_scrollview.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onSendMessage(final String s) {
                if (stopSendShow) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (cmd_send_textview.getText().length() > 100000) {
                            cmd_send_textview.setText("");
                        }

                        Date date = new Date();
                        cmd_send_textview.append("============= " + sdf.format(date) + " =============\r\n");
                        cmd_send_textview.append(JsonFormatTool.formatJson(s) + "\r\n");
                        cmd_send_textview.append("\r\n");
                        cmd_send_textview.append("\r\n");
                        send_scrollview.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });

        send_filter_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSendShow ^= true;
            }
        });

        rec_filter_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecShow ^= true;
            }
        });
    }
}
