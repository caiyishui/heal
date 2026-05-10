package com.demo.csjbot.csjsdkdemo.future;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnMapStateListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnNaviSearchListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnSpeedGetListener;
import com.demo.csjbot.csjsdkdemo.R;
import com.demo.csjbot.csjsdkdemo.entity.RobotPose;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class SlamDemoActivity extends BaseActivity {
    private TextView current_battery, charge_state, map_restore_result, guide_state;
    private RobotPose robotPose1;
    private RobotPose robotPose2;
    private String currentPoseName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slam_demo);

        initLogShow();

        current_battery = (TextView) findViewById(R.id.current_battery);
        charge_state = (TextView) findViewById(R.id.charge_state);
        map_restore_result = (TextView) findViewById(R.id.map_restore_result);
        guide_state = (TextView) findViewById(R.id.guide_state);

        mCsjBot.setOnRobotStateBatteryListener(new OnRobotStateListener() {
            @Override
            public void getBattery(final int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        current_battery.setText(i + " %");
                    }
                });
            }

            @Override
            public void getCharge(final int i) {
//                CosLogger.error("getCharge " + i);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String inCharge = i == 0 ? getString(R.string.not_charge) : getString(R.string.in_charge);
                        charge_state.setText(inCharge);
                    }
                });
            }
        });

        mCsjBot.getAction().getMapState(onMapStateListener);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void saveMap(View view) {
        mCsjBot.getAction().saveMap();
    }

    public void restoreMap(View view) {
        mCsjBot.getAction().loadMap();
    }


    private OnMapStateListener onMapStateListener = new OnMapStateListener() {
        @Override
        public void mapState(String s) {
            JSONObject root = null;
            try {
                root = new JSONObject(s);
                boolean state = root.getBoolean("state");
                String result = state ? getString(R.string.map_restored) : getString(R.string.map_not_restored);
                showMsgInTextView(map_restore_result, result);
            } catch (JSONException e) {
                showMsgInTextView(map_restore_result, "parse error");
                e.printStackTrace();
            }

        }
    };

    public void queryMapState(View view) {
        mCsjBot.getAction().getMapState(onMapStateListener);
    }

    public void turnRight60Degrees(View view) {
        mCsjBot.getAction().moveAngle(-60, new OnGoRotationListener() {
            @Override
            public void response(int i) {

            }
        });
    }

    public void turnLeft60Degrees(View view) {
        mCsjBot.getAction().moveAngle(60, new OnGoRotationListener() {
            @Override
            public void response(int i) {

            }
        });
    }

    public void turnTo60(View view) {
        mCsjBot.getAction().goAngle(60);
    }

    public void forward(View view) {
        mCsjBot.getAction().moveForward();
    }

    public void backward(View view) {
        mCsjBot.getAction().moveBack();
    }

    public void turnLeft(View view) {
        mCsjBot.getAction().moveLeft();
    }

    public void turnRight(View view) {
        mCsjBot.getAction().moveLeft();
    }

    public void getAndSavePose1(View view) {
        mCsjBot.getAction().getPosition(new OnPositionListener() {
            @Override
            public void positionInfo(String s) {
                Log.d("TAG", "OnPositionListener:s:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rotation = jsonObject.getString("rotation");
                    String x = jsonObject.getString("x");
                    String y = jsonObject.getString("y");
                    String z = jsonObject.getString("z");
                    RobotPose robotPose = new RobotPose();
                    robotPose.setPoseName(getString(R.string.pose1));
                    RobotPose.PosBean posBean = new RobotPose.PosBean();
                    posBean.setRotation(Float.valueOf(rotation));
                    posBean.setX(Float.valueOf(x));
                    posBean.setY(Float.valueOf(y));
                    posBean.setZ(Float.valueOf(z));
                    robotPose.setPos(posBean);
                    robotPose1 = robotPose;
                    showMsgInTextView(guide_state, getString(R.string.get_pose) + robotPose1.toString());
                    speechSpeak.startSpeaking(robotPose1.getPoseName() + getString(R.string.get_pose_ok), null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void goPose1(View view) {
        mCsjBot.getAction().search(new OnNaviSearchListener() {
            @Override
            public void searchResult(String s) {
                CosLogger.error("searchResult " + s);
                try {
                    JSONObject root = new JSONObject(s);
                    int state = root.getInt("state");

                    if (state == 0) {
                        goPose(robotPose1);
                    } else {
                        speechSpeak.startSpeaking(getString(R.string.please_try_later), null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getAndSavePose2(View view) {
        mCsjBot.getAction().getPosition(new OnPositionListener() {
            @Override
            public void positionInfo(String s) {
                Log.d("TAG", "OnPositionListener:s:" + s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    String rotation = jsonObject.getString("rotation");
                    String x = jsonObject.getString("x");
                    String y = jsonObject.getString("y");
                    String z = jsonObject.getString("z");
                    RobotPose robotPose = new RobotPose();
                    robotPose.setPoseName(getString(R.string.pose2));
                    RobotPose.PosBean posBean = new RobotPose.PosBean();
                    posBean.setRotation(Float.valueOf(rotation));
                    posBean.setX(Float.valueOf(x));
                    posBean.setY(Float.valueOf(y));
                    posBean.setZ(Float.valueOf(z));
                    robotPose.setPos(posBean);

                    robotPose2 = robotPose;
                    showMsgInTextView(guide_state, getString(R.string.get_pose) + robotPose2.toString());
                    speechSpeak.startSpeaking(robotPose2.getPoseName() + getString(R.string.get_pose_ok), null);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void goPose2(View view) {
        mCsjBot.getAction().search(new OnNaviSearchListener() {
            @Override
            public void searchResult(String s) {
                CosLogger.error("searchResult " + s);
                try {
                    JSONObject root = new JSONObject(s);
                    int state = root.getInt("state");

                    if (state == 0) {
                        goPose(robotPose2);
                    } else {
                        speechSpeak.startSpeaking(getString(R.string.please_try_later), null);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void cancelNavi(View view) {
        mCsjBot.getAction().cancelNavi(new OnNaviListener() {
            @Override
            public void moveResult(String s) {

            }

            @Override
            public void messageSendResult(String s) {

            }

            @Override
            public void cancelResult(String s) {
                String say = currentPoseName + getString(R.string.navi_cancel);

                speechSpeak.startSpeaking(say, null);
                showMsgInTextView(guide_state, say);
            }

            @Override
            public void goHome() {

            }
        });
    }

    public void goHome(View view) {
        currentPoseName = "回充";
        mCsjBot.getAction().goHome(new OnNaviListener() {
            @Override
            public void moveResult(String s) {
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
        });
    }

    public void getNaviSpeed(View view) {
        mCsjBot.getAction().getSpeed(new OnSpeedGetListener() {
            @Override
            public void getNaviSpeed(double v) {
                speechSpeak.startSpeaking(getString(R.string.move_speed) + v, null);
            }
        });
    }

    public void setNaviSpeed05(View view) {
        mCsjBot.getAction().setSpeed(0.5f);
    }

    public void setNaviSpeed07(View view) {
        mCsjBot.getAction().setSpeed(0.7f);
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
                showMsgInTextView(guide_state, pose.getPoseName() + getString(R.string.navi_arrived));
            }

            @Override
            public void messageSendResult(String s) {
                speechSpeak.startSpeaking(getString(R.string.navi_msg_send_ok) + currentPoseName, null);
                showMsgInTextView(guide_state, getString(R.string.navi_msg_send_ok) + currentPoseName);
            }

            @Override
            public void cancelResult(String s) {
            }

            @Override
            public void goHome() {

            }
        });
    }

    public void getMapList(View view) {
        mCsjBot.getAction().getMapList(new OnMapListListener() {
            @Override
            public void response(String s) {
                CosLogger.info("getMapList == >" + s);
            }
        });
    }
}
