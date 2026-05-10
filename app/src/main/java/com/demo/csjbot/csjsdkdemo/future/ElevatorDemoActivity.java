package com.demo.csjbot.csjsdkdemo.future;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.csjbot.cosclient.utils.CosLogger;
import com.csjbot.coshandler.listener.OnElevatorCtrlListener;
import com.csjbot.coshandler.listener.OnElevatorStateListener;
import com.demo.csjbot.csjsdkdemo.R;

public class ElevatorDemoActivity extends BaseActivity {

    private TextView elevator_info, elevator_ctrl_show;
    private EditText current_floor, target_floor,send_filter;
    private ScrollView elevator_ctrl_show_scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elevator_demo);
        elevator_info = (TextView) findViewById(R.id.elevator_info);
        elevator_ctrl_show = (TextView) findViewById(R.id.elevator_ctrl_show);

        current_floor = (EditText) findViewById(R.id.current_floor);
        target_floor = (EditText) findViewById(R.id.target_floor);
        send_filter = (EditText) findViewById(R.id.send_filter);


        elevator_ctrl_show_scroll = (ScrollView) findViewById(R.id.elevator_ctrl_show_scroll);

        initLogShow();
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
        showMsg(Html.fromHtml("<font color='red'>" + msg + "</font>"));
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
}