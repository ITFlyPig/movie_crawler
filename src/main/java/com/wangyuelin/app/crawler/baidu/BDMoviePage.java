package com.wangyuelin.app.crawler.baidu;


import com.wangyuelin.app.crawler.baidu.task.BDMovieTask;
import com.wangyuelin.app.crawler.movie.MovieConstantInfo;
import com.wangyuelin.app.crawler.movie.MovieTag;
import com.wangyuelin.app.crawler.movie.MovieType;
import com.wangyuelin.app.util.MyThreadPool;
import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;

import java.util.ArrayList;

/**
 * 描述:解析百度的最新电影
 *
 * @outhor wangyuelin
 * @create 2018-05-29 下午2:43
 */
@Component
public class BDMoviePage {
    private static final String LATEST_URL = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=28286&from_mid=1&&format=json&ie=utf-8&oe=utf-8&query=%E7%94%B5%E5%BD%B1&sort_key=17&sort_type=1&stat0=&stat1=&stat2=&stat3=&pn=%s&rn=%s&cb=jQuery110207876018427985891_1527575677852&_=1527575677858";
    private static final int LATEST_MOVIE_PER_PAGE_NUM = 8;//最新电影，一页显示多少数据


    public void parse(Page page) {


    }

    /**
     * 开始爬取百度不同的类型组合的电影
     */
    public void startCrawBaiduMovie() {

        //获取所有的电音分类
        final MovieType[] movieTypes = MovieType.values();
        Runnable crawBaiduMovieTask = new Runnable() {
            @Override
            public void run() {
                //测试
//                MovieType[] movieTypes = new MovieType[]{ MovieType.ACTION};
//
                ArrayList<String> types = new ArrayList<>();
                types.add("");//对于百度电影分类来说""表示全部
                for (MovieType movieType : movieTypes) {
                    types.add(movieType.getTypeStr());

                }


                for (int sort : MovieConstantInfo.BD_SORT) {
                    for (String movieType : types) {
                        for (String location : MovieConstantInfo.LOCATION) {
                            for (String year : MovieConstantInfo.BD_YEAR) {


                                String url = MovieConstantInfo.getBDUrl(sort, movieType, location, year, "");
                                if (TextUtils.isEmpty(url)) {
                                    continue;
                                }
                                BDMovieTask task = new BDMovieTask(url, 0);
                                task.setSeacherKey(sort + movieType + location + year);
                                //设置电影的tag
                                task.setLocation(location);
                                task.setYear(year);
                                task.setType(movieType);
                                MovieTag tag = MovieConstantInfo.getBaiduMovieTag(sort);
                                if (tag != null) {
                                    task.setMovieTag(tag.getTagStr());
                                }

//                                MyThreadPool.submit(task);
                                task.run();
                            }

                        }
                    }
                }

            }
        };
        MyThreadPool.submitSequenceTask(crawBaiduMovieTask);


    }

}