package com.demo.csjbot.csjsdkdemo.future;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.listener.OnFaceListener;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;
import com.csjbot.coshandler.listener.OnSpeakListener;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.entity.PersonDetectBean;
import com.demo.csjbot.csjsdkdemo.entity.PersonListBean;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class FaceDemoActivity extends BaseActivity {
    private ImageView cameraPreview;
    private EditText register_name;
    private TextView person_state, person_selected;
    private ListView allFaceListLisView;
    private String selectedPersonId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_demo);
        cameraPreview = (ImageView) findViewById(R.id.cameraPreview);
        register_name = (EditText) findViewById(R.id.register_name);
        person_state = (TextView) findViewById(R.id.person_state);
        person_selected = (TextView) findViewById(R.id.person_selected);
        allFaceListLisView = (ListView) findViewById(R.id.allFaceListLisView);

//        mCsjBot.registerDetectPersonListener(personListener);
        mCsjBot.registerFaceListener(faceListener);

        initCountDownTimer();
        initLogShow();
    }

    private OnFaceListener faceListener = new OnFaceListener() {

        @Override
        public void personInfo(String s) {
            // There is only one person here
            CosLogger.error("takePicture === " + s);
            PersonDetectBean bean = new Gson().fromJson(s, PersonDetectBean.class);
            PersonDetectBean.FaceListBean.FaceRecgBean recgBean = bean.getFace_list().get(0).getFace_recg();

            int confidence = recgBean.getConfidence();
            if (confidence > 80) {
                speechSpeak.startSpeaking(getString(R.string.hello) + recgBean.getName(), null);
            }
        }

        @Override
        public void personNear(boolean b) {
            String person = b ? getString(R.string.have_person) : getString(R.string.have_no_person);
            showMsgInTextView(person_state, person);
            CosLogger.error("takePicture === " + b);
        }
    };

//    private OnDetectPersonListener personListener = new OnDetectPersonListener() {
//        @Override
//        public void response(int i) {
//            String person = i == 0 ? "无人" : "有人";
//            showMsgInTextView(person_state, person);
////            CosLogger.error("takePicture" + i);
//        }
//    };

    private OnGetAllFaceListener onGetAllFaceListener = new OnGetAllFaceListener() {
        @Override
        public void personList(final String s) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    PersonListBean personListBean = new Gson().fromJson(s, PersonListBean.class);
                    final PersonListAdapter adapter = new PersonListAdapter(personListBean);
                    allFaceListLisView.setAdapter(adapter);
                    allFaceListLisView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedPersonId = adapter.getItem(position);
                            person_selected.setText(selectedPersonId);
                        }
                    });

                    adapter.notifyDataSetChanged();
                }
            });

            CosLogger.error("takePicture" + s);
        }
    };


    class PersonListAdapter extends BaseAdapter {
        private PersonListBean personListBean;

        public PersonListAdapter(PersonListBean personListBean) {
            this.personListBean = personListBean;
        }

        @Override
        public int getCount() {
            return personListBean.getData_list().size();
        }

        @Override
        public String getItem(int position) {
            return personListBean.getData_list().get(position).getId();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        @SuppressLint("ViewHolder")
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(FaceDemoActivity.this).inflate(R.layout.person_list_item, null);
            TextView textView = view.findViewById(R.id.person_tv);
            PersonListBean.DataListBean bean = personListBean.getData_list().get(position);

            textView.setText(bean.getId() + "\r\n" + bean.getName());

            return view;
        }
    }


    @Override
    protected void onPause() {
        dismissPreview();
        mCsjBot.getFace().resumePreview(FaceDemoActivity.this);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPreview();
        mCsjBot.getFace().resumePreview(FaceDemoActivity.this);

        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public void openPreView(View view) {
        showPreview();
    }

    public void closePreView(View view) {
        dismissPreview();
    }

    /**
     * set x, y to your view
     */
    private void showPreview() {
        int[] viewLocation = new int[2];
        cameraPreview.getLocationInWindow(viewLocation);
        int preViewX = viewLocation[0]; // x
        int preViewY = viewLocation[1]; // y

        if (isPlus()) {
            mCsjBot.getFace().openPreView(this, 240, 240, 600, 480);
        } else {
            mCsjBot.getFace().openPreView(this, 0, 0, 600, 480);
        }
    }

    private boolean isPlus() {
        return false;
    }

    private void dismissPreview() {
        mCsjBot.getFace().closePreView(this);
    }

    private boolean snapshotOK = false;
    private static final int COUNT_DOWNCOUNT_MAX = 3;
    private int countDownCount = COUNT_DOWNCOUNT_MAX;
    private CountDownTimer countDownTimer;

    private void initCountDownTimer() {
        countDownTimer = new CountDownTimer(COUNT_DOWNCOUNT_MAX * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!FaceDemoActivity.this.isFinishing()) {
                    countDownCount--;
                    speechSpeak.startSpeaking(String.valueOf(countDownCount), null);
                }
            }

            /**
             *Called after the countdown ends
             */
            @Override
            public void onFinish() {
                if (snapshotOK) {
                    mCsjBot.getFace().pausePreview(FaceDemoActivity.this);
                }
                countDownCount = COUNT_DOWNCOUNT_MAX;
            }

        };
    }

    public void takePicture(View view) {
        mCsjBot.getFace().resumePreview(FaceDemoActivity.this);
//        speechSpeak.startSpeaking(getString(R.string.keep_smail) + String.valueOf(countDownCount), new OnSpeakListener() {
//            @Override
//            public void onSpeakBegin() {
//
//            }
//
//            @Override
//            public void onCompleted(SpeechError speechError) {
//                countDownTimer.start();
//            }
//        });

        mCsjBot.getFace().snapshot(new OnSnapshotoListener() {
            @Override
            public void response(String s) {
                CosLogger.info("snapshot " + s);
                try {
                    JSONObject root = new JSONObject(s);
                    int result = root.getInt("error_code");
                    int personNum = root.getInt("face_position");

                    if (result == 4001) {
                        speechSpeak.startSpeaking(getString(R.string.please_retry), null);
                    }

                    if (result == 0) {
                        snapshotOK = true;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void cancelRegisterFace(View view) {
        mCsjBot.getFace().resumePreview(FaceDemoActivity.this);
    }

    public void registerFace(View view) {
        String name = register_name.getText().toString();

        if (!TextUtils.isEmpty(name)) {
            mCsjBot.getFace().saveFace(name, new OnFaceSaveListener() {
                @Override
                public void response(String s) {
                    CosLogger.error("takePicture" + s);

                    try {
                        JSONObject root = new JSONObject(s);
                        int result = root.getInt("error_code");

                        if (result == 40002) {
                            speechSpeak.startSpeaking(getString(R.string.do_not_register_again), null);
                        }

                        if (result == 0) {
                            mCsjBot.getFace().resumePreview(FaceDemoActivity.this);
                            speechSpeak.startSpeaking(getString(R.string.register_ok), null);
                        }
                    } catch (JSONException e) {
                        mCsjBot.getFace().resumePreview(FaceDemoActivity.this);

                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void getAllFaces(View view) {
        mCsjBot.getFace().getFaceDatabase(onGetAllFaceListener);
    }

    public void delChoosePerson(View view) {
        if (!TextUtils.isEmpty(selectedPersonId)) {
            mCsjBot.getFace().faceDel(selectedPersonId);

            selectedPersonId = "";
            person_selected.setText(selectedPersonId);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCsjBot.getFace().getFaceDatabase(onGetAllFaceListener);
                }
            }, 500);
        }
    }
}
