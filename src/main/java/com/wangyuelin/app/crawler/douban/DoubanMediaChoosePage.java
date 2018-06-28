package com.wangyuelin.app.crawler.douban;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.task.GetTask;
import com.wangyuelin.app.crawler.task.TaskCallBack;
import com.wangyuelin.app.util.MyThreadPool;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.apache.http.util.TextUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.List;
import java.util.Map;

/**
 * 描述:豆瓣筛选 电影 电视剧 综艺 动画 纪录片 短片的页面
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午4:55
 */
@Component
public class DoubanMediaChoosePage implements IPage, TaskCallBack {
    private static final Logger logger = LoggerFactory.getLogger(DoubanMediaChoosePage.class);
    //搜索的例子 https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电影,青春&start=0&genres=历史&countries=中国大陆
    //https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=电影&start=0
    private static final String DEMO_URL = "https://movie.douban.com/tag/#/?sort=T&range=0,10&tags=%E7%94%B5%E5%BD%B1";
    private int perPageNum = 20;//每次请求返回的数据量20个
    private static String REQUEST_URL = "https://movie.douban.com/j/new_search_subjects?sort=T&range=0,10&tags=%s&start=%s&genres=%s&countries=%s";

    private GetTask taskGatAll;

    @Autowired
    private DoubanMovieService movieService;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        Document doc = page.getHtml().getDocument();


    }

    @Override
    public boolean isMine(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        if (url.startsWith("https://movie.douban.com/j/new_search_subjects")) {
            return true;
        }
        return false;
    }


    /**
     * 获得请求的连接
     * @param tags  tags: 电影的标签，多个使用","分隔 比如 电影、电视剧、动画，对于豆瓣还可以和全部特色组合查询（即一个电影一个特色组合）
     * @param type  genres: 电影的类型 比如动作、恐怖
     * @param location countries: 拍摄电影的国家/地区
     * @return
     */
    private String getRequestUrl(String[] tags, int pageStart, String type, String location) {
        if (tags == null ) {
            return null;
        }
        if (type == null) {
            type = "";
        }
        if (location == null) {
            location = "";
        }
        StringBuffer buffer = new StringBuffer();
        for (String tag : tags) {
            if (!TextUtils.isEmpty(buffer)) {
                buffer.append(",");
            }
            buffer.append(tag);
        }
        return String.format(REQUEST_URL, buffer.toString(),pageStart ,type, location);
    }



    /**
     * 爬取全部的电影
     */
    public void startCrawAll() {
        String[] tags = new String[]{"电影"};
        String type  = "";
        String location = "";
        if (taskGatAll == null || taskGatAll.isDone()) {
            taskGatAll = new GetTask(this, tags, type, location);
            MyThreadPool.submit(taskGatAll);

        }
    }


    /**
     * 下一个页面的url
     * @param tags
     * @param pageIndex
     * @param type
     * @param location
     * @return
     */
    @Override
    public String next(String[] tags, int pageIndex, String type, String location) {
        String nextUrl = getRequestUrl(tags, pageIndex * perPageNum , type, location);
        return nextUrl;
    }

    /**
     * 返回值表示是否继续
     * @return
     */
    @Override
    public boolean isNeedProxy() {
        return true;
    }

    /**
     * 下载到的数据
     * @param url
     * @param response
     * @return
     */
    @Override
    public boolean result(String url, String[] tags, String response) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(response)) {
            logger.info("标签：" + (tags == null ? "" : tags.toString()) + "下的数据为空，停止次标签的获取 ");
            return false;
        }

        int size = parseAllMovies(response, tags);
        logger.info("获得标签：" + (tags == null ? "" : tags.toString()) + "下的数据量 " + size);
        if (size <= 0) {
            return false;//已经完了，停止
        }
        return true;//还没有抓取玩，继续


    }


    /**
     * 解析返回的json数据
     * @param jsonMovies
     * @param tags
     * @return 返回解析得到的数据量
     */
    private int parseAllMovies(String jsonMovies, String[] tags) {
        if (TextUtils.isEmpty(jsonMovies)) {
            return 0;
        }
        ReadContext ctx = JsonPath.parse(jsonMovies);
        List<Map<String, String>> list = ctx.read("$.data[*]");
        if (list == null) {
            return 0;
        }
        StringBuffer tagsBuffer = new StringBuffer();
        for (String tag : tags) {
            if (!TextUtils.isEmpty(tagsBuffer)) {
                tagsBuffer.append("/");
            }
            tagsBuffer.append(tag);
        }

        for (Map<String, String> map : list) {
            String detailUrl = map.get("url");//详情页的url
            String movieName = map.get("title");//电影的名称
            MovieBean movieBean = new MovieBean();
            movieBean.setName(movieName);
            movieBean.setDetaiWeblUrl(detailUrl);
            movieBean.setTag(tagsBuffer.toString());
            movieService.updateByDetailUrl(movieBean);//保存到数据库

            //将详情页添加到抓取
            SpiderCraw.addRequestUrl(detailUrl, null);

        }
        return list.size();

    }


}