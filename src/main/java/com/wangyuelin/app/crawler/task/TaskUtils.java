package com.wangyuelin.app.crawler.task;

import com.wangyuelin.app.util.MyThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午4:48
 */
@Component
public class TaskUtils {
    private static CheckContentEmptyMovie checkContentEmptyMovie;

    @Autowired
    public void setCheckContentEmptyMovie(CheckContentEmptyMovie checkContentEmptyMovie) {
        TaskUtils.checkContentEmptyMovie = checkContentEmptyMovie;
    }

    /**
     * 开始系统的任务，一般都是定时检查的任务
     */
    public static void startAllTask() {
        MyThreadPool.submit(checkContentEmptyMovie);
    }


}