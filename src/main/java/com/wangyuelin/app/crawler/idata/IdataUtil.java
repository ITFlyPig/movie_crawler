package com.wangyuelin.app.crawler.idata;

import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.util.LogUtils;
import com.wangyuelin.app.util.MyThreadPool;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午7:24
 */
public class IdataUtil {
    private static final Logger logger = LoggerFactory.getLogger(IdataUtil.class);

    static {
//        MyThreadPool.submit(new QueueCheckTask());//开始检查带搜索的队列
    }


    /**
     * idata查询豆瓣电影具体信息的api
     */
    public static String MOVIE_SEACHER_API = "http://120.76.205.241:8000/movie/douban?kw=%s&apikey=yHsGtByGPEtywmCb01Xj33u2qpW4kiP4bvKmALb4bVGbPx1bZRXLCvpnN8PBTr2T";

    /**
     * 开始搜索电影
     * @param movieName
     */
    public static void searchMovie(String movieName) {
        if (TextUtils.isEmpty(movieName)) {
            return;
        }
        try {
            IdataMoviesQueue.movieQueue.put(movieName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static HashMap<String ,Integer> count = new HashMap<>();

    /**
     * 统计某种类型的电影爬取了多少
     * @param key
     * @param num
     */
    public  static void putData(String key, int num) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        Integer preCount = count.get(key);
        if (preCount == null) {
            preCount = 0;
        }
        preCount += num;
        count.put(key, preCount);

        for (Map.Entry<String, Integer>  entry: count.entrySet()) {
            LogUtils.logToFIle(entry.getKey() + " 对应的电影数量：" + entry.getValue() + "\n", Constant.FileNames.BAIDU_CRAW_LOG);
        }
    }

}