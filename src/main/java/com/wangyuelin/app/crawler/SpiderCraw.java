package com.wangyuelin.app.crawler;

import com.wangyuelin.app.crawler.downloader.OkhttpDownloader;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 描述:爬虫的一些操作
 *
 * @outhor wangyuelin
 * @create 2018-05-30 下午4:39
 */
@Component
public class SpiderCraw {

    private static MovieProcessor movieProcessor;

    private static Spider spider;


    @Autowired
    public void setMovieProcessor(MovieProcessor movieProcessor) {
        SpiderCraw.movieProcessor = movieProcessor;
    }

    /**
     * 开始爬取
     *
     * @param url
     * @param pageProcessor
     */
    public static void start(String url, PageProcessor pageProcessor) {
        if (TextUtils.isEmpty(url) || pageProcessor == null) {
            return;
        }
        stop();//保证只有一个爬虫
        spider = Spider.create(pageProcessor);
        spider.setDownloader(OkhttpDownloader.singleInstance());
        spider.addPipeline(new ConsolePipeline()).addUrl(url).
                thread(10).runAsync();
    }

    /**
     * 结束爬虫
     */
    public static void stop() {
        if (spider != null && spider.getStatus() == Spider.Status.Running) {
            spider.stop();
        }
    }

    /**
     * 添加处理的url
     *
     * @param url
     */
    public static void addRequestUrl(String url, PageProcessor pageProcessor) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (pageProcessor == null) {
            pageProcessor = movieProcessor;
        }
        if (spider != null && spider.getStatus() == Spider.Status.Running) {
            spider.addUrl(url);
        } else {
            start(url, pageProcessor);
        }

    }

}