package com.demo.csjbot.csjsdkdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.csjbot.coshandler.core.CsjRobot;
import com.demo.csjbot.csjsdkdemo.future.ActionActivity;
import com.demo.csjbot.csjsdkdemo.future.AsrNlpActivity;
import com.demo.csjbot.csjsdkdemo.future.BaseActivity;
import com.demo.csjbot.csjsdkdemo.future.ElevatorDemoActivity;
import com.demo.csjbot.csjsdkdemo.future.FaceDemoActivity;
import com.demo.csjbot.csjsdkdemo.future.InfoAndSensorActivity;
import com.demo.csjbot.csjsdkdemo.future.MutiMapDeliveryActivity;
import com.demo.csjbot.csjsdkdemo.future.SlamDemoActivity;


public class MainActivity extends BaseActivity {

    private Button mutiMapText;
    private CsjRobot mCsjBot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mutiMapText = (Button) findViewById(R.id.mutiMapText);

        mutiMapText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, MutiMapDeliveryActivity.class);
                intent.putExtra("isDebug", true);
                startActivity(intent);

                return true;
            }
        });
        mCsjBot = CsjRobot.getInstance();
    }

    public void openASrNlpPage(View view) {
        startActivity(new Intent(this, AsrNlpActivity.class));
    }

    public void openSlamDemo(View view) {
        startActivity(new Intent(this, SlamDemoActivity.class));
    }

    public void openFaceDemo(View view) {
        startActivity(new Intent(this, FaceDemoActivity.class));
    }

    public void openAction(View view) {
        startActivity(new Intent(this, ActionActivity.class));
    }

    public void openInfo(View view) {
        startActivity(new Intent(this, InfoAndSensorActivity.class));
    }

    public void openElevator(View view) {
//        startActivity(new Intent(this, ElevatorDemoActivity.class));
        startActivity(new Intent(this, ElevatorDemoActivity.class));
    }

    public void openMutiMapDelivery(View view) {
        Intent intent = new Intent(MainActivity.this, MutiMapDeliveryActivity.class);
        intent.putExtra("isDebug", false);
        startActivity(intent);
    }
}
