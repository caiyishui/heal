package com.csjbot.coshandler.widget;

import android.content.Context;
import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;



import com.csjbot.coshandler.R;
import com.csjbot.coshandler.log.CsjlogProxy;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jingwc on 2017/11/17.
 */

public class ShowLogView extends FrameLayout {

    /**
     * 记录系统状态栏的高度
     */
    private static int statusBarHeight;

    /**
     * 用于更新小悬浮窗的位置
     */
    private WindowManager windowManager;

    /**
     * 小悬浮窗的参数
     */
    public WindowManager.LayoutParams mParams;

    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    Context mContext;

    List<LogBean> mDatas;

    RecyclerView mRecyclerView;

    LogAdapter mAdapter;

    public ShowLogView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ShowLogView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShowLogView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        mDatas = new ArrayList<>();

        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.layout_showlog, this, true);

        mRecyclerView = findViewById(R.id.log_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter = new LogAdapter());
    }

    public void addLogMsg(String log,int color){
        this.mDatas.add(new LogBean(log,color));
        int lastItemIndex = (mDatas.size() - 1);
        this.mAdapter.notifyItemChanged(lastItemIndex);
        this.mRecyclerView.scrollToPosition(lastItemIndex);
    }

    public void clearLogMsg() {
        this.mDatas.clear();
        this.mAdapter.notifyDataSetChanged();
    }

    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

    /**
     * 更新小悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this, mParams);
    }

    public void setParams(WindowManager.LayoutParams params){
        this.mParams = params;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }


    class LogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        LogAdapter() {

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_log, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            LogBean logBean = mDatas.get(position);
            ((ViewHolder) holder).setLog(logBean.log,logBean.color);
        }


        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvLog;

            ViewHolder(View itemView) {
                super(itemView);
                tvLog = itemView.findViewById(R.id.tv_log);
            }

            public void setLog(String log,int color) {
                if(!TextUtils.isEmpty(log)) {
                    tvLog.setText(log);
                }
                if(color != 0) {
                    tvLog.setTextColor(color);
                }
            }
        }
    }

    class LogBean{
        public String log;
        public int color;

        public LogBean(){

        }

        public LogBean(String log,int color){
            this.log = log;
            this.color = color;
        }
    }
}
