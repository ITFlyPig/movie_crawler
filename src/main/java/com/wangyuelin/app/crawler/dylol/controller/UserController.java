package com.wangyuelin.app.crawler.dylol.controller;

import com.wangyuelin.app.crawler.MovieProcessor;
import com.wangyuelin.app.crawler.baidu.BDMoviePage;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.db.StaticDBHandle;
import com.wangyuelin.app.crawler.douban.DoubanChooseMoviePage;
import com.wangyuelin.app.crawler.douban.DoubanMediaChoosePage;
import com.wangyuelin.app.crawler.douban.DoubanPage;
import com.wangyuelin.app.crawler.douban.MovieHousePage;
import com.wangyuelin.app.crawler.douban.mapper.MovieMapper;
import com.wangyuelin.app.crawler.douban.service.DoubanMovieService;
import com.wangyuelin.app.crawler.dy2018.DyttProcessor;
import com.wangyuelin.app.crawler.dy2018.TableUtil;
import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.dylol.bean.User;
import com.wangyuelin.app.crawler.dylol.service.RankMovieService;
import com.wangyuelin.app.crawler.dylol.service.TableService;
import com.wangyuelin.app.crawler.dylol.service.itf.ITest;
import com.wangyuelin.app.crawler.idata.IdataUtil;
import com.wangyuelin.app.crawler.task.TaskUtils;
import com.wangyuelin.app.proxypool.ProxyPool;
import com.wangyuelin.app.proxypool.monitor.MonitorFileUtil;
import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.util.JdbcUtil;
import com.wangyuelin.app.crawler.SpiderCraw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Spider;

import java.util.List;

@Controller
@RequestMapping("user")
public class
UserController {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ITest test;

    @Autowired
    private MovieProcessor mMovieProcessor;

    @Autowired
    private RankMovieService rankMovieService;
    @Autowired
    private TableService tableService;

    @Autowired
    private DyttProcessor dyttProcessor;

    @Autowired
    private DoubanPage doubanPage;

    private Spider spider;

    private boolean isSpiderRunning;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private DoubanChooseMoviePage doubanChooseMoviePage;

    @Autowired
    private MovieHousePage movieHousePage;

    @Autowired
    private DoubanMediaChoosePage doubanMediaChoosePage;

    @Autowired
    private SpiderCraw spiderCraw;

    @Autowired
    private BDMoviePage bdMoviePage;

    @Autowired
    private DoubanMovieService doubanMovieService;

    @RequestMapping("/getOneUser")
    @ResponseBody
    public Movie getOneUser() {
        return rankMovieService.getByName("军情观察室");
    }


    @RequestMapping("/getAll")
    @ResponseBody
    public List<User> getAll() {
        logger.info("getOneUser");
        return test.getAll();
    }

    @RequestMapping("/craw_movie")
    @ResponseBody
    public String crawMovie() {

        /*
        //爬取电影院正在和即将上映的
        movieHousePage.startCrawHouseMovie();
        //爬取感兴趣的tag的电影
        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.HOT);
        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.DOUBANGAOFEN);
        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.JINGDIAN);
        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.NEW);
        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.LENGMENJIAPIAN);

         */

//        deleteContent();
//        BeePage.startSearch("护垫侠", mMovieProcessor);

//        SpiderUtil.start("https://movie.douban.com/subject/26674021/", mMovieProcessor);
//        SpiderUtil.start("https://movie.douban.com/subject/26890502/", mMovieProcessor);

//        createTables();

//        MovieBean movieBean = new MovieBean();
//        movieBean.setActors("de");
//        movieBean.setIntro("intro");
//        movieBean.setShowYear("showyear");
//        movieBean.setShowTime("showtime");
//        movieBean.setName("name1");
//        movieBean.setNickName("nickname");
//        movieBean.setLanguage("language");
//        movieBean.setMovieTime("movirtime");
//        movieBean.setLocation("location");
//        movieBean.setTypes("types");
//        movieBean.setScriptwriter("sc");
//        movieBean.setEnName("enmae");
//        movieBean.setDirector("direcotr");
//        movieMapper.insert(movieBean);
        //爬取不同tag的数据
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.HOT);

//
//        ProxyPool.IP ip = null;
//        ProxyPool proxyPool = new ProxyPool();
//        proxyPool.startGatherIps();
//       while (ip != null) {
//           ip = proxyPool.getValidIp();
//           try {
//               Thread.sleep(1000 * 5);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
//       }

        //ip池
//        ProxyPool.getInstance().startGatherIps();
//        ProxyPool.getInstance().startCheckValidQueue();
//        MonitorFileUtil.startMonitorFile();

//        movieHousePage.startCrawHouseMovie();
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.HOT);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.DOUBANGAOFEN);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.JINGDIAN);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.NEW);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.LENGMENJIAPIAN);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.AIQING);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.DONGZUO);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.HANGUO);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.HUAYU);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.OUMEI);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.RIBEN);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.XUANYU);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.KEHUAN);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.KONGBU);
//        doubanChooseMoviePage.startCrawSpecialTagMovies(Constant.MovieTag.ZHIYU);

        //检查数据库为空的任务
//        TaskUtils.startAllTask();

//        doubanMediaChoosePage.startCrawAll();

//        SpiderCraw.addRequestUrl("https://movie.douban.com/subject/1303603/", null);

//        bdMoviePage.startCrawBaiduMovie();

        //idata搜索电影
//        IdataUtil.searchMovie("厕所英雄");
        //爬取百度电影

//        MovieBean movieBean = new MovieBean();
//        movieBean.setName("死侍2");
//        movieBean.setShowYear("2018");
//        movieBean.setTag("最热");
//        doubanMovieService.insertBDMovie(movieBean, -1);

        bdMoviePage.startCrawBaiduMovie();

        return "开始爬取";
    }


    @RequestMapping("/doubanmovie")
    @ResponseBody
    public String doubanMovie() {
        doubanPage.searchMovieId("超时空同居");
        return "doubanmovie";
    }

    /**
     * 创建对应的表
     */
    private void createTables() {
        String sql = "CREATE TABLE `%s` (\n" +
                "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  `title` varchar(500) NOT NULL DEFAULT '',\n" +
                "  `link` varchar(500) NOT NULL DEFAULT '',\n" +
                "  `time` varchar(100) DEFAULT NULL,\n" +
                "  `movie_name` varchar(100) DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        TableUtil.Table[] tables = TableUtil.Table.values();
        for (TableUtil.Table table : tables) {
            String realSql = String.format(sql, table.getEnName());
            boolean res = JdbcUtil.excutrSQL(realSql);
            System.out.println("表： ---" + table.getEnName() + "--- 的创建结果" + res);


        }


    }

    private void deleteContent() {
        TableUtil.Table[] tables = TableUtil.Table.values();
        for (TableUtil.Table table : tables) {
            String sql = "delete from " + table.getEnName();
            JdbcUtil.excutrSQL(sql);


        }
    }


}
