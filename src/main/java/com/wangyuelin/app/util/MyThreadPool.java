package com.wangyuelin.app.util;

import com.wangyuelin.app.crawler.baidu.task.BDMovieTask;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.db.StaticDBHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 描述: 全局线程池
 *
 * @outhor wangyuelin
 * @create 2018-06-14 下午6:55
 */
public class MyThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(MyThreadPool.class);
    private static Thread thread;

    private static ExecutorService cachedThreadPool = Executors.newFixedThreadPool(10);

    private static ArrayBlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(1000);

    private static ArrayList<String> noCrawKeys = new ArrayList<>();


    private static ArrayBlockingQueue<MovieBean> movieQueue = new ArrayBlockingQueue<>(50000);//存放待放入数据库的数据
    private static Runnable dbRunnable;

    /**
     * 添加到队列
     * @param movieBean
     */
    public static void addMovie(final MovieBean movieBean) {
        if (movieBean == null) {
            return;
        }
        if (dbRunnable == null) {

            for (int i = 0; i < 8; i++) {
                dbRunnable = new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                MovieBean movie = movieQueue.take();
                                StaticDBHandle.doubanMovieService.insertBDMovie(movie, 0);
                                logger.info("将电影放入数据库：" + movie.getName() + " 队列中电影的而数量：" + movieQueue.size());
                                LogUtils.logToFIle("将电影放入数据库：" + movie.getName() + " 队列中电影的而数量：" + movieQueue.size(), Constant.FileNames.BAIDU_CRAW_LOG);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                };
                cachedThreadPool.submit(dbRunnable);
            }


        }

        try {
            logger.info("将电影放入队列：" + movieBean.getName());
            movieQueue.put(movieBean);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


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
     * 将任务提交,为的是顺序处理（即先提交的先处理）
     * @param runnable
     */
    public static void submitSequenceTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (thread == null) {

            for (int i = 0; i < 5; i++) {//开启5个线程处理任务
                thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        while (true) {
                            logger.info("队列中任务的数量：" + workQueue.size());
                            Runnable task = null;
                            try {
                                task = workQueue.take();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (task == null) {
                                continue;
                            }

                            if (task instanceof BDMovieTask) {
                                BDMovieTask bdMovieTask = (BDMovieTask) task;
                                if (contains(noCrawKeys, bdMovieTask.getSeacherKey())) {
                                    logger.info("放弃key对应的任务：" + bdMovieTask.getSeacherKey());
                                    continue;
                                }
                            }

                            logger.info("队列中取出的任务开始执行");
                            task.run();



                        }



                    }
                };
                thread.start();
            }



        }


        try {
            workQueue.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * 删除工作队列中key对应的任务
     * @param key
     */
    public static void putNoCrawKey(String key) {
        if (key == null) {
            return;
        }
        if (!contains(noCrawKeys, key)) {
            noCrawKeys.add(key);
        }

    }

    /**
     * 是否包含
     * @param list
     * @param key
     * @return
     */
    private static boolean contains(List<String> list, String key) {
        if (key == null || list == null) {
            return false;
        }
        for (String s : list) {
            if (s.equals(key)) {
                return true;
            }
        }
        return false;
    }




}