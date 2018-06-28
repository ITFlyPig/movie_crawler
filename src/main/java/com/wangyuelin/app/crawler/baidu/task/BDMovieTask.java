package com.wangyuelin.app.crawler.baidu.task;

import com.jayway.jsonpath.JsonPath;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.crawler.idata.IdataUtil;
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
    private int pageIndex = 0;
    private String seacherKey;//爬取的关键词

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

        //解析得到的电影
        List<String> names = JsonPath.read(json, "$.data[0].result[*].name");
        if (names != null) {
            IdataUtil.putData(seacherKey, names.size());
            for (String name : names) {
                logger.info("解析得到的百度电影 ：" + name);
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
        for (int i = 1; i < totalPage; i++) {

            String url = getQueryUrl(i);

            if (!TextUtils.isEmpty(url)) {
                BDMovieTask movieTask = new BDMovieTask(url, i);
                movieTask.setSeacherKey(seacherKey);
                logger.info("key:" + seacherKey + "添加到待抓取队列：" + url);
                MyThreadPool.submit(movieTask);
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

}