package com.demo.csjbot.csjsdkdemo;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.csjbot.coshandler.core.Action;
import com.csjbot.coshandler.core.CsjRobot;
import com.csjbot.coshandler.core.Expression;
import com.csjbot.coshandler.core.Extra;
import com.csjbot.coshandler.core.Face;
import com.csjbot.coshandler.core.Speech;
import com.csjbot.coshandler.core.State;
import com.csjbot.coshandler.core.Version;
import com.csjbot.coshandler.listener.OnCameraListener;
import com.csjbot.coshandler.listener.OnDetectPersonListener;
import com.csjbot.coshandler.listener.OnExpressionListener;
import com.csjbot.coshandler.listener.OnFaceListener;
import com.csjbot.coshandler.listener.OnFaceSaveListener;
import com.csjbot.coshandler.listener.OnGetAllFaceListener;
import com.csjbot.coshandler.listener.OnGetVersionListener;
import com.csjbot.coshandler.listener.OnGoRotationListener;
import com.csjbot.coshandler.listener.OnHeadTouchListener;
import com.csjbot.coshandler.listener.OnHotWordsListener;
import com.csjbot.coshandler.listener.OnMapListListener;
import com.csjbot.coshandler.listener.OnNaviListener;
import com.csjbot.coshandler.listener.OnNaviSearchListener;
import com.csjbot.coshandler.listener.OnPositionListener;
import com.csjbot.coshandler.listener.OnRobotStateListener;
import com.csjbot.coshandler.listener.OnSNListener;
import com.csjbot.coshandler.listener.OnSnapshotoListener;
import com.csjbot.coshandler.listener.OnSpeakListener;
import com.csjbot.coshandler.listener.OnSpeechGetResultListener;
import com.csjbot.coshandler.listener.OnSpeechListener;
import com.csjbot.coshandler.listener.OnUpgradeListener;
import com.csjbot.coshandler.listener.OnWakeupListener;
import com.csjbot.coshandler.listener.OnWarningCheckSelfListener;
import com.csjbot.coshandler.log.CsjlogProxy;
import com.csjbot.coshandler.tts.ISpeechSpeak;
import com.demo.csjbot.csjsdkdemo.entity.RobotPose;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity_back extends AppCompatActivity {
    CsjRobot mCsjBot;

    RobotPose robotPose1;

    RobotPose robotPose2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_back);

        mCsjBot = CsjRobot.getInstance();

        mCsjBot.registerWakeupListener(new OnWakeupListener() {
            @Override
            public void response(int i) {
                Log.d("TAG", "registerWakeupListener:i:" + i);
                mCsjBot.getTts().startSpeaking("I'm here!", null);
                mCsjBot.getAction().moveAngle(i, new OnGoRotationListener() {
                    @Override
                    public void response(int i) {
                        if (i > 0 && i < 360) {
                            if (i <= 180) {
                                CsjlogProxy.getInstance().debug("turn left:+" + i);
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(i, null);
                                }
                            } else {
                                CsjlogProxy.getInstance().debug("turn right:-" + (360 - i));
                                if (mCsjBot.getState().getChargeState() == State.NOT_CHARGING) {
                                    mCsjBot.getAction().moveAngle(-(360 - i), null);
                                }
                            }
                        }
                    }
                });
            }
        });

        mCsjBot.registerSpeechListener(new OnSpeechListener() {
            @Override
            public void speechInfo(final String s, final int i) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Simple parsing example
                        Log.d("TAG", "registerSpeechListener:s:" + s);
                        if (Speech.SPEECH_RECOGNITION_RESULT == i) { // Identified information
                            try {
                                String text = new JSONObject(s).getString("text");
                                Toast.makeText(MainActivity_back.this, "Recognized text:" + text, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else if (Speech.SPEECH_RECOGNITION_AND_ANSWER_RESULT == i) {// Identified information and answers
                            try {
                                String say = new JSONObject(s).getJSONObject("result").getJSONObject("data").getString("say");
                                Toast.makeText(MainActivity_back.this, "Obtained answer information:" + say, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });

        mCsjBot.registerCameraListener(new OnCameraListener() {
            @Override
            public void response(Bitmap bitmap) {

            }
        });

        mCsjBot.registerDetectPersonListener(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                if (i == 0) { // unmanned
                    Log.d("TAG", "registerDetectPersonListener:unmanned");
                } else if (i == 1) { // Someone
                    Log.d("TAG", "registerDetectPersonListener:Someone");
                }
            }
        });

        mCsjBot.registerFaceListener(new OnFaceListener() {
            @Override
            public void personInfo(String s) {
                 Log.d("TAG", "registerFaceListener:s:" + s);
            }

            @Override
            public void personNear(boolean b) {

                if (b) {// There is a face approaching
                    Log.d("TAG", "registerFaceListener:There is a face approaching");
                } else {// Face disappearing
                    Log.d("TAG", "registerFaceListener:Face disappearing");
                }
            }
        });

        mCsjBot.registerHeadTouchListener(new OnHeadTouchListener() {
            @Override
            public void response() {
                Log.d("TAG", "registerHeadTouchListener:Head sensor trigger");
            }
        });

        findViewById(R.id.bt_alice_arm_right_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsjBot.getAction().AliceRightArmUp();
            }
        });

        findViewById(R.id.bt_alice_arm_right_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsjBot.getAction().AliceRightArmDown();
            }
        });

        findViewById(R.id.bt_happy_expression).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsjBot.getExpression().happy();
            }
        });

        findViewById(R.id.bt_save_point1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            robotPose.setPoseName("pos1");
                            RobotPose.PosBean posBean = new RobotPose.PosBean();
                            posBean.setRotation(Float.valueOf(rotation));
                            posBean.setX(Float.valueOf(x));
                            posBean.setY(Float.valueOf(y));
                            posBean.setZ(Float.valueOf(z));
                            robotPose.setPos(posBean);
                            robotPose1 = robotPose;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        findViewById(R.id.bt_save_point2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            robotPose.setPoseName("pos2");
                            RobotPose.PosBean posBean = new RobotPose.PosBean();
                            posBean.setRotation(Float.valueOf(rotation));
                            posBean.setX(Float.valueOf(x));
                            posBean.setY(Float.valueOf(y));
                            posBean.setZ(Float.valueOf(z));
                            robotPose.setPos(posBean);
                            robotPose2 = robotPose;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        findViewById(R.id.bt_navi_point1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (robotPose1 == null) {
                    return;
                }
                mCsjBot.getAction().navi(new Gson().toJson(robotPose1.getPos()), new OnNaviListener() {
                    @Override
                    public void moveResult(String s) {
                        mCsjBot.getTts().startSpeaking(robotPose1.getPoseName() + "It's already here!", null);
                    }

                    @Override
                    public void messageSendResult(String s) {
                        Log.d("TAG", "Successfully distributed navigation message");
                    }

                    @Override
                    public void cancelResult(String s) {

                    }

                    @Override
                    public void goHome() {

                    }
                });
            }
        });

        findViewById(R.id.bt_navi_point2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (robotPose2 == null) {
                    return;
                }
                mCsjBot.getAction().navi(new Gson().toJson(robotPose2.getPos()), new OnNaviListener() {
                    @Override
                    public void moveResult(String s) {
                        mCsjBot.getTts().startSpeaking(robotPose2.getPoseName() + "It's already here!", null);
                    }

                    @Override
                    public void messageSendResult(String s) {
                        Log.d("TAG", "Successfully distributed navigation message");
                    }


                    @Override
                    public void cancelResult(String s) {

                    }

                    @Override
                    public void goHome() {

                    }
                });
            }
        });

        findViewById(R.id.bt_save_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCsjBot.getAction().saveMap();
            }
        });

        mCsjBot.getSpeech().startIsr();

        mCsjBot.getFace().getFaceDatabase(new OnGetAllFaceListener() {
            @Override
            public void personList(String s) {
                Log.d("TAG", "getFaceDatabase:" + s);
            }
        });

    }

    private void actionTest() {

        Action action = CsjRobot.getInstance().getAction();

        action.reset();

        action.action(2, 6);//

        action.startWaveHands(1000);

        action.stopWaveHands();

        action.startDance();

        action.stopDance();

        action.getPosition(new OnPositionListener() {
            @Override
            public void positionInfo(String s) {
                /*{
                    "msg_id":"NAVI_GET_CURPOS_RSP",
                        "x":”0”,
                    "y":”0”,
                    "z":”0”,
                    "rotation":”0”,
                    "error_code":0
                  }*/
            }
        });

        action.move(0);

        String json = "";
        /*{
            "msg_id":"NAVI_ROBOT_MOVE_TO_REQ",
                "pos": {
                    "x":2,
                    "y":1,
                    "z":0,
                    "rotation":30
                 }
          }*/
        action.navi(json, new OnNaviListener() {
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

        action.cancelNavi(new OnNaviListener() {
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

        action.goAngle(180);

        action.moveAngle(180, new OnGoRotationListener() {
            @Override
            public void response(int i) {
                // Arrival angle notification
            }
        });

        action.goHome(new OnNaviListener() {
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

        action.saveMap();

        action.saveMap("name");

        action.loadMap();

        action.loadMap("name");

        action.getMapList(new OnMapListListener() {
            @Override
            public void response(String s) {

            }
        });

        action.setSpeed(0.6f);

        action.search(new OnNaviSearchListener() {
            @Override
            public void searchResult(String s) {
                /*{
                    "msg_id":"NAVI_GET_STATUS_RSP",
                     "state":0,
                     "error_code":0
                  }*/
                // State: 0 idle, 1: navigating
            }
        });

        action.nodAction();

        action.snowRightArm();

        action.snowLeftArm();

        action.snowDoubleArm();

        action.AliceHeadUp();

        action.AliceHeadDown();

        action.AliceHeadHReset();

        action.AliceLeftArmUp();

        action.AliceLeftArmDown();

        action.AliceRightArmUp();

        action.AliceRightArmDown();

        action.SnowLeftArmSwing(20);

        action.SnowRightArmSwing(20);

        action.SnowDoubleArmSwing(20);

        action.turnLeft(new OnGoRotationListener() {
            @Override
            public void response(int i) {
            }
        });

        action.turnRight(new OnGoRotationListener() {
            @Override
            public void response(int i) {
            }
        });

        action.moveLeft();

        action.moveRight();

        action.moveForward();

        action.moveBack();
    }

    public void ttsTest() {
        ISpeechSpeak speak = CsjRobot.getInstance().getTts();

//        speak.startSpeaking("How do you do！", new OnSpeakListener() {
//            @Override
//            public void onSpeakBegin() {
//            }
//
//            @Override
//            public void onCompleted(SpeechError speechError) {
//            }
//        });

        speak.stopSpeaking();

        speak.pauseSpeaking();

        speak.resumeSpeaking();

        speak.isSpeaking();

        CsjRobot.getInstance().setTts(null);
    }

    public void speechTest() {
        Speech speech = CsjRobot.getInstance().getSpeech();

        speech.startSpeechService();

        speech.closeSpeechService();

        speech.startIsr();

        speech.stopIsr();

        speech.startOnceIsr();

        speech.stopOnceIsr();

        speech.openMicro();

        speech.getResult("What's your name?？", new OnSpeechGetResultListener() {
            @Override
            public void response(String s) {

            }
        });
    }

    private void faceTest() {
        Face face = CsjRobot.getInstance().getFace();

        face.openVideo();

        face.closeVideo();

        face.startFaceService();

        face.closeFaceService();


        face.snapshot(new OnSnapshotoListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "error_code": 0,
                    "face_position": 0,
                    "msg_id":”FACE_SNAPSHOT_RESULT_RSP"
                    } */
                // erro_ Code: 0 means there is a face; other means there is no face
            }
        });

        face.saveFace("Zhang San", new OnFaceSaveListener() {
            @Override
            public void response(String s) {
                /*
                * {
                    "msg_id":"FACE_SAVE_RSP",
                    “person_id”:”personx20170107161021mRJOVw”,
                       “error_code":0
                   }*/

                // error_cdoe : 0 succeeded, 40002 face has been registered, 40003 face name format error
            }
        });

        face.faceDel("faceId");

        face.faceDelList("faceIdsJson");

        face.getFaceDatabase(new OnGetAllFaceListener() {
            @Override
            public void personList(String s) {
                // s
            }
        });
    }

    private void stateTest() {
        State state = CsjRobot.getInstance().getState();

        state.isConnect();

        state.getElectricity();

        state.getChargeState();

        state.shutdown();

        state.reboot();

        state.getBattery(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {
            }

            @Override
            public void getCharge(int i) {

            }
        });

        state.getCharge(new OnRobotStateListener() {
            @Override
            public void getBattery(int i) {

            }

            @Override
            public void getCharge(int i) {
            }
        });


        state.checkSelf(new OnWarningCheckSelfListener() {
            @Override
            public void response(String s) {
                // Return the inspection information of the robot
            }
        });

        state.getPerson(new OnDetectPersonListener() {
            @Override
            public void response(int i) {
                // Status returned
                // I==0 unmanned i==1 manned
            }
        });

        state.getSN(new OnSNListener() {
            @Override
            public void response(String s) {
                // SN information returned
            }
        });
    }

    private void expressionTest() {
        Expression expression = CsjRobot.getInstance().getExpression();

        expression.getExpression(new OnExpressionListener() {
            @Override
            public void response(int i) {
                // Returned expression
            }
        });

        expression.happy();

        expression.sadness();

        expression.surprised();

        expression.smile();

        expression.normal();

        expression.angry();

        expression.lightning();

        expression.sleepiness();
    }

    private void versionTest() {
        Version version = CsjRobot.getInstance().getVersion();

        version.getVersion(new OnGetVersionListener() {
            @Override
            public void response(String s) {
                // Version information returned
            }
        });

        version.softwareCheck(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {
                if (i == 60002) {//Is the latest version

                } else if (i == 60001) {//No version information obtained, please check the network

                } else if (i == 0) {//Normal update

                }
            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {

            }
        });


        version.softwareUpgrade(new OnUpgradeListener() {
            @Override
            public void checkRsp(int i) {

            }

            @Override
            public void upgradeRsp(int i) {

            }

            @Override
            public void upgradeProgress(int i) {
            }
        });
    }

    private void extraTest() {
        Extra extra = CsjRobot.getInstance().getExtra();

        extra.getHotWords(new OnHotWordsListener() {
            @Override
            public void hotWords(List<String> list) {
                // List of hot words returned
            }
        });

        extra.setHotWords(null);

        extra.resetRobot();
    }
}
