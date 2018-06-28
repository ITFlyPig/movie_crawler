package com.wangyuelin.app.crawler.task;

import com.wangyuelin.app.crawler.downloader.OkhttpDownloader;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.proxypool.ProxyPoolUtil;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述: 直接下载或者动态ip下载
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午8:17
 */
public class GetTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(GetTask.class);
    private boolean isDone;//表示是否下载完成
    private int minSleepTime = 100;//100ms
    private int maxSleepTime = 2000;//4s
    private Map<String, Integer> mapCount = new LinkedHashMap<>();//统计一个url下载的次数
    private final int maxDownloadTime = 5;//一个url最大下载次数

    private TaskCallBack taskCallBack;
    private String[] tags;
    private int pageIndex = 415;
    private String type;
    private String location;

    public GetTask(TaskCallBack taskCallBack, String[] tags, String type, String location) {
        this.taskCallBack = taskCallBack;
        this.tags = tags;
        this.type = type;
        this.location = location;
    }

    private GetTask() {
    }

    @Override
    public void run() {
        if (taskCallBack == null) {
            return;
        }


        String content = null;
        String url = null;

        do {

            logger.info("开始处理标签：" + (tags == null ? "" : tags.toString()) + "下的第 " +  pageIndex + " 页数据");
            sleep();//随机休眠一段时间
            url = taskCallBack.next(tags, pageIndex, type, location);
            Response response = null;
            try {
                if (taskCallBack.isNeedProxy()) {//使用代理ip下载
                    response = OkhttpUtil.downloadPageWithIp(url, OkhttpDownloader.singleInstance().getmOkHttpClient());
                } else {//不使用
                    response = OkhttpUtil.download(url, OkhttpDownloader.singleInstance().getmOkHttpClient());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) {
                ifTryAgain(response, url);

                ResponseBody body = response.body();
                if (body != null) {
                    try {
                        content = body.string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            pageIndex++;//下一页

        } while (taskCallBack.result(url, tags, content));

        isDone = true;

    }

    /**
     * 是否再次下载这个url
     *
     * @param response
     * @param url
     * @return
     */
    private boolean ifTryAgain(Response response, String url) {
        if (response == null) {
            return false;
        }

        //这里可能会产生死循环，比如一直下载失败，则一直卡在这个url这
        if (response.code() != 200) {
            int downloadTime = mapCount.get(url);
            if (downloadTime < maxDownloadTime) {
                downloadTime++;//尝试下载的次数增加
                pageIndex--;//再次下载
                mapCount.put(url, downloadTime);
                return true;
            } else {
                mapCount.remove(url);
            }
        }

        return false;

    }

    public boolean isDone() {
        return isDone;
    }


    /**
     * 随机休眠一段时间
     */
    private void sleep() {
        int sleepTime = ProxyPoolUtil.random(minSleepTime, maxSleepTime);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}