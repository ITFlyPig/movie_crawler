package com.wangyuelin.app.crawler.idata;

import com.wangyuelin.app.crawler.db.StaticDBHandle;
import com.wangyuelin.app.util.MyThreadPool;
import org.apache.http.util.TextUtils;

/**
 * 描述:检查存放电影名的队列是否有需要处理的
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午7:18
 */
public class QueueCheckTask implements Runnable{


    @Override
    public void run() {
        while (true) {
            String movieName = null;
            try {
                movieName = IdataMoviesQueue.movieQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(movieName)) {
                continue;
            }

            //检查数据库是否已经有数据了，有了就不更新了
            boolean isCraw = StaticDBHandle.doubanMovieService.isShouldCraw(movieName);
            if (!isCraw) {
                continue;
            }


            MyThreadPool.submit(new DoubanSeacherTask(movieName));

        }
    }
}