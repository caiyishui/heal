package com.csjbot.coshandler.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by xiasuhuei321 on 2017/10/27.
 * author:luo
 * e-mail:xiasuhuei321@163.com
 */

public class IOCloseUtil {
    /**
     * 关闭IO
     *
     * @param closeables closeables
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
