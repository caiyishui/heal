package com.csjbot.coshandler.handle_msg;


import android.support.annotation.NonNull;
import android.text.TextUtils;


import com.csjbot.coshandler.handle_msg.task.RbBase;
import com.csjbot.coshandler.handle_msg.task.taskfactory.RbFactory;
import com.csjbot.coshandler.log.CsjlogProxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 消息处理工厂
 * Created by jingwc on 2017/8/12.
 */

public class MessageHandleFactory {
    private volatile static MessageHandleFactory handleFactory;

    /**
     * 线程池
     */
    private ExecutorService executorService;

    private MessageHandleFactory() {
        // 实例化线程池
        if (executorService == null) {
            executorService = Executors.newCachedThreadPool(new ThreadFactory() {
                @Override
                public Thread newThread(@NonNull Runnable r) {
                    Thread ret = new Thread(r, "MessageHandlePool");
                    ret.setDaemon(true);
                    return ret;
                }
            });
        }
    }

    /**
     * 获取当前类的实例
     */
    public static MessageHandleFactory getInstance() {
        if (handleFactory == null) {
            synchronized (MessageHandleFactory.class) {
                if (handleFactory == null) {
                    handleFactory = new MessageHandleFactory();
                }
            }
        }
        return handleFactory;
    }

    /**
     * 开始工作
     *
     * @param json
     */
    public synchronized void startWork(String json) {
        RbBase rbBase = null;
//        CsjlogProxy.getInstance().debug(json);
        if (TextUtils.isEmpty(json)) {
            return;
        }

//        // 因为坑逼华晓斌会给到不是json的数据，所以需要判断
        if (!json.trim().endsWith("}")) {
            return;
        }

        try {
            String msgId = new JSONObject(json).getString("msg_id");
            if (TextUtils.isEmpty(msgId)) {
                return;
            }
            rbBase = RbFactory.createRbBase(msgId);
            if (rbBase == null) {
                return;
            }
            rbBase.setDataSource(json);
            rbBase.setMsgId(msgId);
        } catch (JSONException e) {
            CsjlogProxy.getInstance().error(e.toString());
        }
        if (rbBase == null) {
            return;
        }
        executorService.execute(rbBase);
    }
}