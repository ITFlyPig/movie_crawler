package com.wangyuelin.app.util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述: 全局线程池
 *
 * @outhor wangyuelin
 * @create 2018-06-14 下午6:55
 */
public class MyThreadPool {
    private static ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10000);
    private static Thread thread;

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


    /**
     * 将任务提交到单线程的处理队列中
     * @param runnable
     */
    public static void submitOneThredPoolQueue(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (thread == null) {
            thread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        Runnable task = null;
                        try {
                            task = workQueue.take();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (task == null) {
                            continue;
                        }
                        task.run();

                    }



                }
            };
            thread.start();
        }


        try {
            workQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }






}