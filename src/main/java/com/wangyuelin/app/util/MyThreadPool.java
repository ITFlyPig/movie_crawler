package com.wangyuelin.app.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述: 全局线程池
 *
 * @outhor wangyuelin
 * @create 2018-06-14 下午6:55
 */
public class MyThreadPool {

    private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(5);


    /**
     * 提交任务
     * @param runnable
     */
    public static void submit(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        cachedThreadPool.submit(runnable);

    }




}