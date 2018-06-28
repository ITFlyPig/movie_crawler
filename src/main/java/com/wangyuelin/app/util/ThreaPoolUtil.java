package com.wangyuelin.app.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 全局线程池的工具类
 */
public class ThreaPoolUtil {
    private static ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public static void add(Runnable runnable){
        if (runnable == null){
            return;
        }
        threadPool.execute(runnable);

    }
}
