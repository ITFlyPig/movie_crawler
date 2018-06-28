package com.wangyuelin.app.crawler.douban;

import com.jayway.jsonpath.JsonPath;
import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.base.IPage;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.crawler.SpiderCraw;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.io.IOException;
import java.util.List;

/**
 * 描述:豆瓣的页面处理工具
 *
 * @outhor wangyuelin
 * @create 2018-05-29 下午3:26
 */

@Component
public class DoubanPage implements IPage {
    private static final Logger logger = LoggerFactory.getLogger(DoubanPage.class);
    private static final String SEACHER_HOST = "https://movie.douban.com/subject_search";
    private static final String SEACHER_MOVIE = "http://api.douban.com/v2/movie/search?q=";//豆瓣的搜索电影
    private static final String DEATIL_MOVIE = "http://api.douban.com/v2/movie/subject/";//电影的具体信息

    @Autowired
    private DoubanMovieDetailPage doubanMoviePage;

    @Autowired
    private MovieProcessor pageProcessor;
    @Autowired
    private MovieHousePage movieHousePage;

    @Override
    public void parse(Page page) {
        if (page == null) {
            return;
        }
        String url = page.getUrl().get();
        if (doubanMoviePage.isMine(url)) {//解析详情页
            doubanMoviePage.parse(page);
        } else if (movieHousePage.isMine(url)) {
            movieHousePage.parse(page);
        }
    }

    @Override
    public boolean isMine(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.startsWith(DoubanMovieDetailPage.HOST) || url.startsWith(MovieHousePage.URL)) {
            return true;
        }
        return false;
    }

    /**
     * 据名称搜索对应电影的id
     *
     * @param name
     * @return
     */
    public void searchMovieId(final String name) {
        if (TextUtils.isEmpty(name)) {
            return;
        }
        OkhttpUtil.getAsync(SEACHER_MOVIE + name, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.info(name + " 搜索失败");


            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                logger.info("返回");
                String content = response.body().string();
                if (TextUtils.isEmpty(content)) {
                    return;
                }
                logger.info("返回的数据：" + content);
                List<String> ids = JsonPath.read(content, "$.subjects[*].id");
                if (ids == null) {
                    return;
                }

                logger.info("返回的id：" + ids);
                //使用id再去查询具体的电影信息
                for (String id : ids) {
                    getMovieById(id);
                }


            }
        });

    }

    /**
     * 据id查询电影的基本信息
     *
     * @param id
     */
    public void getMovieById(final String id) {
        if (TextUtils.isEmpty(id)) {
            return;
        }
//        OkhttpUtil.get(DEATIL_MOVIE + id, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                logger.info("电影id：" + id + " 查询失败");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String content = response.body().string();
//                logger.info("返回的数据：" + content);
//                if (TextUtils.isEmpty(content)) {
//                    return;
//                }
//                //转为对象
//                Gson gson = new Gson();
//                DoubanMovieInfo movieInfo = gson.fromJson(content, DoubanMovieInfo.class);
//                if (movieInfo == null) {
//                    return;
//                }
//
//                //下载html页面，解析得到其他的信息
//                if (!TextUtils.isEmpty(movieInfo.getAlt())) {
//                    Page page = OkhttpUtil.downloadPage(movieInfo.getAlt());
//                    if (page != null) {//解析页面得到想要的数据
//                        Element infoEl = PageUtil.getOne(page.getHtml().getDocument(), "div#info");
//                        if (infoEl != null) {
//                            List<Node> nodes = infoEl.childNodes();
//                            if (nodes != null) {
//                                int size = nodes.size();
//                                for (int i = 0; i < size; i++) {
//                                    Node node = nodes.get(i);
//                                    String nodeStr = node.toString();
//                                    if (TextUtils.isEmpty(nodeStr)) {
//                                        continue;
//                                    }
//                                    if (nodeStr.contains("语言")) {
//                                        Node next = getNextNode(nodes, i);
//                                        if (next != null) {
//                                            String lanuage = next.toString();//语言
//                                            movieInfo.setLanguage(lanuage);
//                                            logger.info("语言：" + lanuage);
//                                        }
//
//                                    } else if (nodeStr.contains("上映日期")) {
//                                        Node showTime = getNextNode(nodes, i);
//                                        if (showTime != null) {
//                                            String time = showTime.attr("content");//上映日期
//                                            DoubanMovieInfo.ShowTime date = parseShowTime(time);
//                                            if (date != null) {
//                                                if (movieInfo.getShowTimes() == null) {
//                                                    movieInfo.setShowTimes(new ArrayList<DoubanMovieInfo.ShowTime>());
//                                                }
//                                                movieInfo.getShowTimes().add(date);
//                                            }
//
//                                            logger.info("上映日期：" + time);
//
//                                        }
//
//                                    } else if (nodeStr.contains("片长")) {
//                                        Node totalTimeEl = getNextNode(nodes, i);
//                                        if (totalTimeEl != null) {
//                                            String mins = totalTimeEl.attr("content");//片长
//                                            logger.info("时长" + mins);
//                                            try {
//                                                int totalMins = Integer.valueOf(mins);
//                                                movieInfo.setTotal_time(totalMins);
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                            }
//                                        }
//
//                                    }
//
//                                }
//                            }
//                        }
//
//                    }
//
//
//                    getRelatedPic(page, movieInfo);//解析相关的图片
//
//                }
//
//
//                //持久化
//
//
//            }
//        });

        SpiderCraw.addRequestUrl(DEATIL_MOVIE + id, pageProcessor);

    }




    /**
     * 检查视频的连接，如果不是.mp4结尾则打开相应的页面，解析
     *
     * @param link
     */
    private void checkVideo(String link) {
        if (TextUtils.isEmpty(link) || link.endsWith("mp4")) {
            return;
        }
        SpiderCraw.addRequestUrl(link, pageProcessor);
    }


}