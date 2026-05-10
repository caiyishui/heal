package com.demo.csjbot.csjsdkdemo.future;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnSpeakListener;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.entity.RobotPose;
import com.demo.csjbot.csjsdkdemo.utils.SharedPreferencesSDCard;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MutiMapDeliveryActivity extends BaseActivity {

    private TextView elevator_info, elevator_ctrl_show, showCurrentFloor;
    private EditText current_floor, target_floor;
    private ScrollView elevator_ctrl_show_scroll;
    private SharedPreferences sharedPreferences = SharedPreferencesSDCard.getInstance().getMySharedPreferences();
    private SharedPreferences.Editor editor = sharedPreferences.edit();

    private Button saveF3OutWaitPoint, saveF3ElevatorInsidePoint, saveF3TargetPoint,
            saveF1OutWaitPoint, saveF1ElevatorInsidePoint, saveF1TargetPoint;
    private LinearLayout debug_layout, show_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutimap_delivery);

        Intent intent = getIntent();
        boolean isDebug = intent.getBooleanExtra("isDebug", true);
        debug_layout = (LinearLayout) findViewById(R.id.debug_layout);
        show_layout = (LinearLayout) findViewById(R.id.show_layout);

        if (isDebug) {
            show_layout.setVisibility(View.GONE);
            debug_layout.setVisibility(View.VISIBLE);
        } else {
            show_layout.setVisibility(View.VISIBLE);
            debug_layout.setVisibility(View.GONE);
        }

        elevator_info = (TextView) findViewById(R.id.elevator_info);
        elevator_ctrl_show = (TextView) findViewById(R.id.elevator_ctrl_show);
        showCurrentFloor = (TextView) findViewById(R.id.showCurrentFloor);

        current_floor = (EditText) findViewById(R.id.current_floor);
        target_floor = (EditText) findViewById(R.id.target_floor);


        saveF3OutWaitPoint = (Button) findViewById(R.id.saveF3OutWaitPoint);
        saveF3ElevatorInsidePoint = (Button) findViewById(R.id.saveF3ElevatorInsidePoint);
        saveF3TargetPoint = (Button) findViewById(R.id.saveF3TargetPoint);
        saveF1OutWaitPoint = (Button) findViewById(R.id.saveF1OutWaitPoint);
        saveF1ElevatorInsidePoint = (Button) findViewById(R.id.saveF1ElevatorInsidePoint);
        saveF1TargetPoint = (Button) findViewById(R.id.saveF1TargetPoint);

        saveF3OutWaitPoint.setOnLongClickListener(saveF3OutWaitPointLongClick);
        saveF3ElevatorInsidePoint.setOnLongClickListener(saveF3ElevatorInsidePointLongClick);
        saveF3TargetPoint.setOnLongClickListener(saveF3TargetPointLongClick);
        saveF1OutWaitPoint.setOnLongClickListener(saveF1OutWaitPointLongClick);
        saveF1ElevatorInsidePoint.setOnLongClickListener(saveF1ElevatorInsidePointLongClick);
        saveF1TargetPoint.setOnLongClickListener(saveF1TargetPointLongClick);

        elevator_ctrl_show_scroll = (ScrollView) findViewById(R.id.elevator_ctrl_show_scroll);
        initAllPoints();
        initLogShow();
    }


    private void initAllPoints() {
        F3OutWaitPoint = new Gson().fromJson(sharedPreferences.getString("F3OutWaitPoint", ""), RobotPose.class);
        F3ElevatorInsidePoint = new Gson().fromJson(sharedPreferences.getString("F3ElevatorInsidePoint", ""), RobotPose.class);
        F3TargetPoint = new Gson().fromJson(sharedPreferences.getString("F3TargetPoint", ""), RobotPose.class);
        F1OutWaitPoint = new Gson().fromJson(sharedPreferences.getString("F1OutWaitPoint", ""), RobotPose.class);
        F1ElevatorInsidePoint = new Gson().fromJson(sharedPreferences.getString("F1ElevatorInsidePoint", ""), RobotPose.class);
        F1TargetPoint = new Gson().fromJson(sharedPreferences.getString("F1TargetPoint", ""), RobotPose.class);
    }

    public void openElevator(View view) {
        mCsjBot.getEvelator().openElevatorModule(new OnElevatorStateListener() {
            @Override
            public void openState(int i) {
                showMsg("openState " + i);

                CosLogger.error("ElevatorDemoActivity " + i);
            }

            @Override
            public void getRobotid(String s) {

            }

            @Override
            public void getElevatorInfo(int min, int max) {
                showMsg("floor info : min = " + min + ", max = " + max);
                CosLogger.error("ElevatorDemoActivity floor info : min = " + min + " max = " + max);
                showMsgInTextView(elevator_info, "floor info : min = " + min + " max = " + max);
            }
        });
    }

    public void closeElevator(View view) {
    }

    public void getElevatorInfo(View view) {
        mCsjBot.getEvelator().getElevatorInfo(1, new OnElevatorStateListener() {
            @Override
            public void openState(int i) {

            }

            @Override
            public void getRobotid(String s) {

            }

            @Override
            public void getElevatorInfo(int min, int max) {
                showMsg("floor info : min = " + min + ", max = " + max);
                showMsgInTextView(elevator_info, "floor info : min = " + min + ", max = " + max);
                CosLogger.error("ElevatorDemoActivity floor info : min = " + min + ", max = " + max);
            }
        });
    }

    public void setCallInfo(View view) {
        int current = Integer.parseInt(current_floor.getText().toString());
        int target = Integer.parseInt(target_floor.getText().toString());

        mCsjBot.getEvelator().callInfo(1, current, target, onElevatorCtrlListener);
    }

    private OnElevatorCtrlListener onElevatorCtrlListener = new OnElevatorCtrlListener() {
        @Override
        public void response(int i, String cmd_type, String result) {
            showMsg("cmd_type = " + cmd_type + ", result = " + result);
            CosLogger.debug("ElevatorDemoActivity  error = " + i + " cmd_type = " + cmd_type + " result = " + result);

            switch (cmd_type) {
                case "CALLINFO":
                    if (TextUtils.equals(result, "OK")) {
                        showColorMsg("Elevator INFO OK, CALL Elevator");
                        mCsjBot.getEvelator().callingElevator(this);
                    }
                    break;
                case "COMING":
                    showColorMsg("Elevator coming");
                    break;
                case "ARRIVED_CURRENT":
                    showColorMsg("Elevator arrived Floor");
                    break;
                case "ENTERING":
                    showColorMsg("Robot is entering Elevator");
                    break;
                case "GOING_TARGET":
                    showColorMsg("Elevator is going target");
                    break;
                case "ARRIVED_TARGET":
                    showColorMsg("Elevator arrived target");
                    break;
                case "LEAVING":
                    showColorMsg("Robot is leaving Elevator");
                    break;
                case "OUTSIDE":
                    showColorMsg("Robot is outside Elevator");
                    break;
                default:
                    break;
            }
        }
    };

    public void callElevator(View view) {
        mCsjBot.getEvelator().callingElevator(onElevatorCtrlListener);
    }

    private void showColorMsg(final CharSequence msg) {
        showColorMsg(msg, "red");
    }

    private void showColorMsg(final CharSequence msg, String color) {
        String tmp = String.format(Locale.getDefault(), "<font color='%s'>%s</font>", color, msg);
        showMsg(Html.fromHtml(tmp));
    }

    private void showMsg(final CharSequence msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                elevator_ctrl_show.append(msg);
                elevator_ctrl_show.append("\r\n");
//                elevator_ctrl_show.fullScroll(View.FOCUS_DOWN);
                elevator_ctrl_show_scroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    public void simulatedEntering(View view) {
        mCsjBot.getEvelator().enteringElevator(onElevatorCtrlListener);
    }

    public void simulatedInside(View view) {
        mCsjBot.getEvelator().insideElevator(onElevatorCtrlListener);
    }

    public void cancel(View view) {
        mCsjBot.getEvelator().cancelElevator(onElevatorCtrlListener);
    }

    public void simulatedLeaving(View view) {
        mCsjBot.getEvelator().leavingElevator(onElevatorCtrlListener);
    }

    public void simulatedOutside(View view) {
        mCsjBot.getEvelator().outsideElevator(onElevatorCtrlListener);
    }

    // ******************************************* F3 *********************************************** //
    // ******************************************* F3 *********************************************** //
    private static final String DEFAULT_MAP_NAME = "default";
    private static final String DEFAULT_F1_MAP_NAME = "f1_map";
    private RobotPose F3OutWaitPoint, F3ElevatorInsidePoint, F3TargetPoint;
    private RobotPose F1OutWaitPoint, F1ElevatorInsidePoint, F1TargetPoint;
    private int currentFloor, targetFloor;
    private String currentPoseName = "";

    private void savePoint(String name, RobotPose pose) {
    }

    private void goPose(final RobotPose pose) {
        if (pose == null) {
            return;
        }
        currentPoseName = pose.getPoseName();
        mCsjBot.getAction().navi(new Gson().toJson(pose.getPos()), new OnNaviListener() {
            @Override
            public void moveResult(String s) {
                speechSpeak.startSpeaking(pose.getPoseName() + getString(R.string.navi_arrived), null);
//                showMsgInTextView(guide_state, pose.getPoseName() + getString(R.string.navi_arrived));
            }

            @Override
            public void messageSendResult(String s) {
                speechSpeak.startSpeaking(getString(R.string.navi_msg_send_ok) + currentPoseName, null);
//                showMsgInTextView(guide_state, getString(R.string.navi_msg_send_ok) + currentPoseName);
            }

            @Override
            public void cancelResult(String s) {
            }

            @Override
            public void goHome() {

            }
        });
    }

    public void saveDefaultMap(View view) {
        mCsjBot.getAction().saveMap(DEFAULT_MAP_NAME);
    }


    public void restoreDefaultMap(View view) {
        currentFloor = 3;
        RobotPose.PosBean posBean = F3ElevatorInsidePoint.getPos();
        mCsjBot.getAction().loadMap(DEFAULT_MAP_NAME, posBean.getX(), posBean.getY(), posBean.getRotation());
    }

    private View.OnLongClickListener saveF3OutWaitPointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("3楼电梯外等待点");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F3OutWaitPoint = robotPose;
                        editor.putString("F3OutWaitPoint", new Gson().toJson(F3OutWaitPoint)).apply();
                        speechSpeak.startSpeaking(F3OutWaitPoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };

    private View.OnLongClickListener saveF3ElevatorInsidePointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("3楼电梯内等待点");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F3ElevatorInsidePoint = robotPose;
                        editor.putString("F3ElevatorInsidePoint", new Gson().toJson(F3ElevatorInsidePoint)).apply();

                        speechSpeak.startSpeaking(F3ElevatorInsidePoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };

    private View.OnLongClickListener saveF3TargetPointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("3楼目标点");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F3TargetPoint = robotPose;
                        editor.putString("F3TargetPoint", new Gson().toJson(F3TargetPoint)).apply();

                        speechSpeak.startSpeaking(F3TargetPoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };

    public void saveF3OutWaitPoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }

    public void saveF3ElevatorInsidePoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }

    public void saveF3TargetPoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }


    public void GoF3TargetPoint(View view) {
        targetFloor = 3;
        if (currentFloor == targetFloor) {
            goPose(F3TargetPoint);
        } else {
            mCsjBot.getAction().navi(new Gson().toJson(F1OutWaitPoint.getPos()), f1OutPointNaviListener);
        }
    }


    private OnNaviListener f3InsidePointNaviListener = new OnNaviListener() {

        @Override
        public void moveResult(String s) {
            // 到达 三层电梯内等待点
            speechSpeak.startSpeaking(F3ElevatorInsidePoint.getPoseName() + getString(R.string.navi_arrived), null);
            mCsjBot.getEvelator().insideElevator(elevatorSendFlowListener);

            // 5秒后切换地图
            mHalder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (F1ElevatorInsidePoint != null) {
                        RobotPose.PosBean posBean = F1ElevatorInsidePoint.getPos();
                        mCsjBot.getAction().loadMap(DEFAULT_F1_MAP_NAME, posBean.getX(), posBean.getY(), posBean.getRotation());

                        currentFloor = 1;
                        showMsgInTextView(showCurrentFloor, "Current Floor  ==> " + currentFloor);
                    } else {
                        speechSpeak.startSpeaking("失败，一楼电梯等待点未设置", null);
                    }
                }
            }, 5000);
        }

        @Override
        public void messageSendResult(String s) {
            mCsjBot.getEvelator().enteringElevator(elevatorSendFlowListener);
        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };

    private OnNaviListener f3OutPointNaviListener = new OnNaviListener() {
        @Override
        public void moveResult(String s) {
            speechSpeak.startSpeaking(F3OutWaitPoint.getPoseName() + getString(R.string.navi_arrived), null);
            mCsjBot.getEvelator().callInfo(1, 3, 1, elevatorSendFlowListener);
        }

        @Override
        public void messageSendResult(String s) {

        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };


    private OnElevatorCtrlListener elevatorSendFlowListener = new OnElevatorCtrlListener() {
        @Override
        public void response(int i, String cmd_type, String result) {
            showMsg("cmd_type = " + cmd_type + ", result = " + result);
            CosLogger.debug("ElevatorDemoActivity  error = " + i + " cmd_type = " + cmd_type + " result = " + result);

            switch (cmd_type) {
                case "CALLINFO":
                    if (TextUtils.equals(result, "OK")) {
                        showColorMsg("Elevator INFO OK, CALL Elevator");
                        mCsjBot.getEvelator().callingElevator(this);
                    }
                    break;
                case "COMING":
                    showColorMsg("Elevator coming");
                    break;
                case "ARRIVED_CURRENT":
                    mCsjBot.getAction().navi(new Gson().toJson(F3ElevatorInsidePoint.getPos()), f3InsidePointNaviListener);
                    showColorMsg("Elevator arrived Floor");
                    break;
                case "ENTERING":
                    showColorMsg("Robot is entering Elevator");
                    break;
                case "GOING_TARGET":
                    showColorMsg("Elevator is going target");
                    break;
                case "ARRIVED_TARGET":
                    showColorMsg("Elevator arrived target");
                    mCsjBot.getAction().navi(new Gson().toJson(F1TargetPoint.getPos()), f1TargetPointNaviListener);
                    mCsjBot.getEvelator().leavingElevator(this);
                    break;
                case "LEAVING":
                    showColorMsg("Robot is leaving Elevator");
                    break;
                case "OUTSIDE":
                    showColorMsg("Robot is outside Elevator");
                    break;
                default:
                    break;
            }
        }
    };


    // ******************************************* F1 *********************************************** //
    // ******************************************* F1 *********************************************** //
    public void saveF1Map(View view) {
        mCsjBot.getAction().saveMap(DEFAULT_F1_MAP_NAME);
    }

    public void restoreF1Map(View view) {
        if (F1ElevatorInsidePoint != null) {
            RobotPose.PosBean posBean = F1ElevatorInsidePoint.getPos();
            mCsjBot.getAction().loadMap(DEFAULT_F1_MAP_NAME, posBean.getX(), posBean.getY(), posBean.getRotation());
        } else {
            mCsjBot.getAction().loadMap(DEFAULT_F1_MAP_NAME);
        }
    }


    private View.OnLongClickListener saveF1OutWaitPointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("一楼电梯外等待点");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F1OutWaitPoint = robotPose;
                        editor.putString("F1OutWaitPoint", new Gson().toJson(F1OutWaitPoint)).apply();
                        speechSpeak.startSpeaking(F1OutWaitPoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };
    private View.OnLongClickListener saveF1ElevatorInsidePointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("一楼电梯内等待点");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F1ElevatorInsidePoint = robotPose;
                        editor.putString("F1ElevatorInsidePoint", new Gson().toJson(F1ElevatorInsidePoint)).apply();
                        speechSpeak.startSpeaking(F1ElevatorInsidePoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };
    private View.OnLongClickListener saveF1TargetPointLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            mCsjBot.getAction().getPosition(new OnPositionListener() {
                @Override
                public void positionInfo(String s) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        String rotation = jsonObject.getString("rotation");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String z = jsonObject.getString("z");
                        RobotPose robotPose = new RobotPose();
                        robotPose.setPoseName("一楼目标点-前台");
                        RobotPose.PosBean posBean = new RobotPose.PosBean();
                        posBean.setRotation(Float.parseFloat(rotation));
                        posBean.setX(Float.parseFloat(x));
                        posBean.setY(Float.parseFloat(y));
                        posBean.setZ(Float.parseFloat(z));
                        robotPose.setPos(posBean);
                        F1TargetPoint = robotPose;
                        editor.putString("F1TargetPoint", new Gson().toJson(F1TargetPoint)).apply();
                        speechSpeak.startSpeaking(F1TargetPoint.getPoseName() + getString(R.string.get_pose_ok), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    };

    public void saveF1OutWaitPoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }

    public void saveF1ElevatorInsidePoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }

    public void saveF1TargetPoint(View view) {
        speechSpeak.startSpeaking("请长按操作", null);
    }

    private OnNaviListener f1OutPointNaviListener = new OnNaviListener() {
        @Override
        public void moveResult(String s) {
            mCsjBot.getEvelator().callInfo(1, currentFloor, targetFloor, elevatorBackFlowListener);
        }

        @Override
        public void messageSendResult(String s) {

        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };


    public void goF1TargetPoint(View view) {
        targetFloor = 1;
        if (currentFloor == targetFloor) {
            speechSpeak.startSpeaking("目前在一楼，我直接过去了", null);
            goPose(F1TargetPoint);
        } else {
            mCsjBot.getAction().navi(new Gson().toJson(F3OutWaitPoint.getPos()), f3OutPointNaviListener);
        }
    }

    private OnNaviListener f1TargetPointNaviListener = new OnNaviListener() {
        @Override
        public void moveResult(String s) {
//            speechSpeak.startSpeaking(F1TargetPoint.getPoseName() + getString(R.string.navi_arrived), new OnSpeakListener() {
//                @Override
//                public void onSpeakBegin() {
//
//                }
//
//                @Override
//                public void onCompleted(SpeechError speechError) {
//                    GoF3TargetPoint(null);
//                }
//            });

            mCsjBot.getEvelator().outsideElevator(elevatorSendFlowListener);
        }

        @Override
        public void messageSendResult(String s) {

        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };

    // 返程，到达了电梯内
    private OnNaviListener f1InsidePointNaviListener = new OnNaviListener() {
        @Override
        public void moveResult(String s) {
            // 到达 一层电梯内等待点
            speechSpeak.startSpeaking(F1ElevatorInsidePoint.getPoseName() + getString(R.string.navi_arrived), null);
            mCsjBot.getEvelator().insideElevator(elevatorBackFlowListener);

            // 5秒后切换地图
            mHalder.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (F3ElevatorInsidePoint != null) {
                        RobotPose.PosBean posBean = F3ElevatorInsidePoint.getPos();
                        mCsjBot.getAction().loadMap(DEFAULT_MAP_NAME, posBean.getX(), posBean.getY(), posBean.getRotation());

                        currentFloor = 3;
                        showMsgInTextView(showCurrentFloor, "Current Floor  ==> " + currentFloor);
                    } else {
                        speechSpeak.startSpeaking("失败，二楼电梯等待点未设置", null);
                    }
                }
            }, 5000);
        }

        @Override
        public void messageSendResult(String s) {
            mCsjBot.getEvelator().enteringElevator(elevatorBackFlowListener);
        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };
    private OnNaviListener f3TargetPointNaviListener = new OnNaviListener() {
        @Override
        public void moveResult(String s) {
            speechSpeak.startSpeaking(F3TargetPoint.getPoseName() + getString(R.string.navi_arrived), null);
            mCsjBot.getEvelator().outsideElevator(elevatorBackFlowListener);
        }

        @Override
        public void messageSendResult(String s) {

        }

        @Override
        public void cancelResult(String s) {

        }

        @Override
        public void goHome() {

        }
    };


    private OnElevatorCtrlListener elevatorBackFlowListener = new OnElevatorCtrlListener() {
        @Override
        public void response(int i, String cmd_type, String result) {
            showMsg("cmd_type = " + cmd_type + ", result = " + result);
            CosLogger.debug("ElevatorDemoActivity  error = " + i + " cmd_type = " + cmd_type + " result = " + result);

            switch (cmd_type) {
                case "CALLINFO":
                    if (TextUtils.equals(result, "OK")) {
                        showColorMsg("Elevator INFO OK, CALL Elevator", "green");
                        mCsjBot.getEvelator().callingElevator(this);
                    }
                    break;
                case "COMING":
                    showColorMsg("Elevator coming", "green");
                    break;
                case "ARRIVED_CURRENT":
                    mCsjBot.getAction().navi(new Gson().toJson(F1ElevatorInsidePoint.getPos()), f1InsidePointNaviListener);
                    showColorMsg("Elevator arrived Floor", "green");
                    break;
                case "ENTERING":
                    showColorMsg("Robot is entering Elevator", "green");
                    break;
                case "GOING_TARGET":
                    showColorMsg("Elevator is going target", "green");
                    break;
                case "ARRIVED_TARGET":
                    mCsjBot.getAction().navi(new Gson().toJson(F3TargetPoint.getPos()), f3TargetPointNaviListener);
                    mCsjBot.getEvelator().leavingElevator(this);
                    showColorMsg("Elevator arrived target", "green");
                    break;
                case "LEAVING":
                    showColorMsg("Robot is leaving Elevator", "green");
                    break;
                case "OUTSIDE":
                    showColorMsg("Robot is outside Elevator", "green");
                    break;
                default:
                    break;
            }
        }
    };

    public void cancelMission(View view) {
        mCsjBot.getAction().cancelNavi(null);
        mCsjBot.getEvelator().cancelElevator(null);
    }

    public void setCurrentFloor1(View view) {
        currentFloor = 1;
        showMsgInTextView(showCurrentFloor, "Current Floor  ==> " + currentFloor);
    }

    public void setCurrentFloor3(View view) {
        currentFloor = 3;
        showMsgInTextView(showCurrentFloor, "Current Floor  ==> " + currentFloor);
    }

    public void goQiantai(View view) {
        targetFloor = 1;
        if (currentFloor == targetFloor) {
            speechSpeak.startSpeaking("目前在一楼，我直接过去了", null);
            goPose(F1TargetPoint);
        } else {
            speechSpeak.startSpeaking("开始前往前台", null);
            mCsjBot.getAction().navi(new Gson().toJson(F3OutWaitPoint.getPos()), f3OutPointNaviListener);
        }

        startActivity(new Intent(this, FaceActivity.class));
    }

    public void goMeetingRoom(View view) {
        targetFloor = 3;
        if (currentFloor == targetFloor) {
            goPose(F3TargetPoint);
        } else {
            speechSpeak.startSpeaking("开始前往3楼", null);
            mCsjBot.getAction().navi(new Gson().toJson(F1OutWaitPoint.getPos()), f1OutPointNaviListener);
        }
        startActivity(new Intent(this, FaceActivity.class));
    }
}