package com.wangyuelin.app.crawler.douban.service;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.base.PlayUrl;
import com.wangyuelin.app.crawler.douban.DoubanChooseMoviePage;
import com.wangyuelin.app.crawler.douban.mapper.MovieMapper;
import com.wangyuelin.app.crawler.douban.mapper.PlayUrlMapper;
import com.wangyuelin.app.crawler.douban.service.itf.IDoubanMovie;
import com.wangyuelin.app.util.Constant;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-12 上午11:17
 */
@Service
public class DoubanMovieService implements IDoubanMovie {
    private static final Logger logger = LoggerFactory.getLogger(DoubanMovieService.class);

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private PlayUrlMapper playUrlMapper;

    @Override
    public int insert(MovieBean movieBean) {
        if (movieBean == null) {
            return Constant.ReturnCode.ERROE;
        }
        movieMapper.insert(movieBean);
        return movieBean.getId();
    }

    @Override
    public void updateByDetailUrl(MovieBean movieBean) {
        if (movieBean == null) {
            return;
        }

        MovieBean old = movieMapper.getByDetailUrl(movieBean.getDetaiWeblUrl());
        if (old != null) {//据detailurl查询
            old.setValueFromOther(movieBean);//从传递进来的bean更新字段的值
            logger.info("数据库存在，更新,id为：" + old.getId());
            movieMapper.update(old);
        }
        if (old == null) {//据名称查询
            List<MovieBean> seacherList = movieMapper.getByName(movieBean.getName());
            if (seacherList != null && seacherList.size() > 0) {
                for (MovieBean searchBean : seacherList) {
                    old = searchBean;
                    old.setValueFromOther(movieBean);//从传递进来的bean更新字段的值
                    logger.info("数据库存在，更新");
                    movieMapper.update(old);

                }

            }
        }

        if (old == null) {
            logger.info("数据库不存在，插入");
            insert(movieBean);
        }
    }

    @Override
    public List<MovieBean> getMoviesByName(String movieName) {
        if (TextUtils.isEmpty(movieName)) {
            return null;
        }
        return movieMapper.getByName(movieName);
    }

    /**
     * 将播放链接保存
     *
     * @param playUrl
     */
    @Override
    public void savePlayUrl(PlayUrl playUrl, String movieName) {
        if (playUrl == null && TextUtils.isEmpty(playUrl.getUrl()) || TextUtils.isEmpty(movieName)) {
            return;
        }

        List<MovieBean> movies = getMoviesByName(movieName);
        if (movies == null || movies.size() == 0) {
            return;
        }

        for (MovieBean movie : movies) {
            List<PlayUrl> playUrls = playUrlMapper.getByUrlAndMovieId(playUrl.getUrl(), movie.getId());
            if (playUrls == null || playUrls.size() == 0) {//没有，则插入
                playUrl.setMovieId(movie.getId());
                playUrlMapper.insert(playUrl);
            } else {//这个电影对应的这个下载链接已经存在，则更新（更新已经失效的连接）
                for (PlayUrl url : playUrls) {
                    if (!playUrl.getName().equals(url.getName())) {
                        url.setName(playUrl.getName());
                        playUrlMapper.updateById(url);
                    }
                }


            }

        }

    }

    /**
     * 判断一个电影是否应该爬取，数据库存在就不爬
     *
     * @param movieName
     * @return
     */
    @Override
    public boolean isShouldCraw(String movieName) {
        if (TextUtils.isEmpty(movieName)) {
            return false;
        }
        List<MovieBean> list = movieMapper.getByName(movieName);
        if (list == null || list.size() == 0) {
            return true;
        }
        for (MovieBean movie : list) {
            if (TextUtils.isEmpty(movie.getLocation()) || TextUtils.isEmpty(movie.getIntro())) {
                return true;
            }

        }

        return false;
    }

    /**
     * 获取电影内容没有抓取到的集合
     *
     * @return
     */
    @Override
    public List<MovieBean> getEmptyContentMovie() {
        return movieMapper.getEmptyMovies();
    }

    @Override
    public void deleteByTag(String tag, String table) {
        if (TextUtils.isEmpty(table) || TextUtils.isEmpty(tag)) {
            return;
        }
        movieMapper.deleteByTag(tag, table);
    }

    @Override
    public List<MovieBean> getMovieByTagAndName(String tag, String name, String table) {
        if (TextUtils.isEmpty(table) || TextUtils.isEmpty(tag) || TextUtils.isEmpty(name)) {
            return null;
        }

        return movieMapper.getMovieByTagAndName(tag, name, table);
    }

    @Override
    public void insertTagMovie(MovieBean movieBean, String table, int index) {
        if (movieBean == null || TextUtils.isEmpty(table) || index < 0) {
            return;
        }
        movieMapper.insertTagMovie(movieBean, table, index);

    }

    @Override
    public void insertBDMovie(MovieBean movieBean, int index) {
        if (movieBean == null) {
            return;
        }

        //插入到电影数据库中
        List<MovieBean> list = getMoviesByName(movieBean.getName());
        if (list == null || list.size() == 0) {
            insert(movieBean);
        }

        if (TextUtils.isEmpty(movieBean.getTag())) {
            return;
        }
        //放入到百度的tag movie数据库中
        List<MovieBean> olds  = getMovieByTagAndName(movieBean.getTag(), movieBean.getName(), Constant.Table.BAIDU_TAG_MOVIE_TABLE);
        if (olds == null || olds.size() == 0) {//对应的tag中还咩有这个电影
            if (index < 0) {
                int num = movieMapper.getNumByTag(movieBean.getTag(), Constant.Table.BAIDU_TAG_MOVIE_TABLE);
                if (num > 0) {
                    index = num + 1;
                }
            }
            insertTagMovie(movieBean, Constant.Table.BAIDU_TAG_MOVIE_TABLE, index);
        }else {//更新,主要为了更新类型 时间等信息
            for (MovieBean old : olds) {
                old.setValueFromOther(movieBean);
                movieMapper.updateTagMovie(old, Constant.Table.BAIDU_TAG_MOVIE_TABLE);
            }


        }


    }


}