package com.wangyuelin.app.crawler.baidu.task;

import com.jayway.jsonpath.JsonPath;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.db.StaticDBHandle;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.crawler.idata.IdataUtil;
import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.util.LogUtils;
import com.wangyuelin.app.util.MyThreadPool;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午3:42
 */
public class BDMovieTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(BDMovieTask.class);
    private String mUrl;//请求的url
    private static final int LATEST_MOVIE_PER_PAGE_NUM = 8;//最新电影，一页显示多少数据
    private static Map<String, Integer> FAIL_KEY_COUNT = new HashMap<>();//统计一种key下载失败的次数，连续10次下载失败，就不再下载这种key对应的url
    private static Map<String, Integer> PRE_FIAL_PAGE = new HashMap<>();//记录前一个下载失败的页面
    private int pageIndex = 0;
    private String seacherKey;//爬取的关键词
    private String movieTag;//电影的tag
    private String location;
    private String year;//上映的年份
    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMovieTag(String movieTag) {
        this.movieTag = movieTag;
    }

    public void setSeacherKey(String seacherKey) {
        this.seacherKey = seacherKey;
    }

    public BDMovieTask(String url, int pageIndex) {
        this.mUrl = url;
        this.pageIndex = pageIndex;
    }

    @Override
    public void run() {
        logger.info("开始处理：" + mUrl);
        LogUtils.logToFIle("开始处理key对应的ur" +  seacherKey + "\n", Constant.FileNames.BAIDU_CRAW_LOG);
        String httpUrl = null;
        if (pageIndex == 0) {
            httpUrl = getQueryUrl(pageIndex);

        } else {
            httpUrl = mUrl;
        }


        logger.info("task中的url：" + httpUrl);


        if (TextUtils.isEmpty(httpUrl)) {
            return;
        }
        //http下载
        Response response = OkhttpUtil.get(httpUrl);
        if (response == null) {
            return;
        }
        ResponseBody body = response.body();
        if (body == null) {
            return;
        }
        String respStr = null;
        try {
            respStr = body.string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(respStr)) {
            return;
        }

        String json = respStr;


        //解析得到的电影
        List<String> names = null;
        try {
            names = JsonPath.read(json, "$.data[0].result[*].name");
        } catch (Exception e) {
            e.printStackTrace();

            failCount(seacherKey);

        }

        if (names == null) {
            return;
        }

        if (names != null) {
            int size = names.size();
            IdataUtil.putData(seacherKey, size);
            for (int i = 0; i < size; i++) {
                String name = names.get(i);
                logger.info("解析得到的百度电影 ：" + name);
                MovieBean movieBean = new MovieBean();
                movieBean.setName(name);
                movieBean.setShowYear(year);
                movieBean.setLocation(location);
                movieBean.setTag(movieTag);
                movieBean.setTypes(type);
                //电影的序列
                int movieIndex = -1;
                if (isSortAccordingTo()) {
                    movieIndex = pageIndex * 8 + i + 1;
                }

                StaticDBHandle.doubanMovieService.insertBDMovie(movieBean, movieIndex);
            }
        }


        if (pageIndex == 0) {//将所有的请求url添加

            try {
                String totalNum = JsonPath.read(json, "$.data[0].dispNum");
                logger.info("得到电影的数量：" + totalNum);
                int total = Integer.valueOf(totalNum);
                addAll(total);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    /**
     * 将所有的url添加，每一个url返回一页的数据
     *
     * @param totalNum
     */
    private void addAll(int totalNum) {
        if (totalNum <= 0) {
            return;
        }

        int totalPage = (int) (totalNum / LATEST_MOVIE_PER_PAGE_NUM + 0.5);

        //测试
//        totalPage = 3;

        for (int i = 1; i < totalPage; i++) {



        //测试
//        for (int i = 40000; i < 50000; i++) {

            if (!shouldHandle()) {
                logger.info(seacherKey + "连续事变十次，不处理这种key对应的url" );
                LogUtils.logToFIle(seacherKey + "连续事变十次，不处理这种key对应的url" + "\n", Constant.FileNames.BAIDU_CRAW_LOG);
                return;
            }

            String url = getQueryUrl(i);

            if (!TextUtils.isEmpty(url)) {
                BDMovieTask movieTask = new BDMovieTask(url, i);
                movieTask.setSeacherKey(seacherKey);
                movieTask.setMovieTag(movieTag);
                movieTask.setLocation(location);
                movieTask.setYear(year);
                movieTask.setType(type);
                logger.info("key:" + seacherKey + "添加到待抓取队列：" + url);
                MyThreadPool.submit(movieTask);
//                movieTask.run();
            }
        }

    }


    /**
     * 据页码获取不同的url
     *
     * @param pageIndex
     * @return
     */
    private String getQueryUrl(int pageIndex) {
        int realPage = pageIndex;
        if (realPage < 0) {
            return null;
        }
        String url = null;
        try {
            url = String.format(mUrl, String.valueOf(realPage * LATEST_MOVIE_PER_PAGE_NUM));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * 将返回的文本删除多余的，返回json文本
     *
     * @param text
     * @return
     */
    private String getJson(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        int start = text.indexOf("(");
        int end = text.lastIndexOf(")");
        if (start + 1 < end && start + 1 > 0) {
            return text.substring(start + 1, end);
        }
        return null;

    }

    /**
     * 是否是排序的依据
     * 1）当是排序的依据，电影的序列计算出来
     * 2）不是排序的依据，电影的序列为添加到对应的tag后面
     *
     * @return
     */
    private boolean isSortAccordingTo() {
        if (TextUtils.isEmpty(type) && TextUtils.isEmpty(location) && TextUtils.isEmpty(year)) {
            return true;
        }
        return false;
    }

    /**
     * 是否应该处理当前key对应的url
     *
     * @return
     */
    private boolean shouldHandle() {
        Integer failCount = FAIL_KEY_COUNT.get(seacherKey);
        if (failCount == null) {
            failCount = 0;
        }
        if (failCount > 10) {
            return false;
        }
        return true;
    }

    /**
     * key对应的失败次数的统计
     */
    private void failCount(String key) {
        Integer preFailPage = PRE_FIAL_PAGE.get(key);
        if (preFailPage == null) {
            preFailPage = -4;
        }

        Integer count = FAIL_KEY_COUNT.get(seacherKey);
        if (count == null) {
            count = 0;
        }

        if (preFailPage + 1 == pageIndex) {
            count++;
        } else {
            count = 0;
        }
        FAIL_KEY_COUNT.put(seacherKey, count);
        PRE_FIAL_PAGE.put(seacherKey, pageIndex);




    }

}