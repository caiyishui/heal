package com.csjbot.coshandler.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.csjbot.coshandler.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingwc on 2017/11/17.
 */

public class ShowPingView extends FrameLayout {

    Context mContext;

    public TextView tvPing;

    public ShowPingView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShowPingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowPingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.layout_showping, this, true);

        tvPing = findViewById(R.id.tv_ping);
    }

    public void setPing(String ping){
        tvPing.setText(ping);
    }
}
