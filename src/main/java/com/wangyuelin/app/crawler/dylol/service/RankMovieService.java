package com.wangyuelin.app.crawler.dylol.service;

import com.wangyuelin.app.crawler.dylol.bean.Movie;
import com.wangyuelin.app.crawler.dylol.dao.*;
import com.wangyuelin.app.crawler.dylol.service.itf.IRankMoview;
import com.wangyuelin.app.util.ArrayUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankMovieService implements IRankMoview {
    private final Logger logger = LoggerFactory.getLogger(RankMovieService.class);


    @Autowired
    private MovieRankDao movieRankDao;

    @Autowired
    private RecommandDao recommandDao;

    @Autowired
    private UptodateDao uptodateDao;

    @Autowired
    private MonthRankDao monthRankDao;
    @Autowired
    private HotDao hotDao;

    @Autowired
    private MovieDao movieDao;


    @Override
    public void addOne(Movie movie){
        if (movie == null){
            return;
        }
        checkDownloadUrls(movie);
        Movie old = movieDao.getByName(movie.getName());
        if (old == null){
            logger.info("插入的：" + movie.getName() + " " + movie.getRankTypeStr() + " " + movie.getRankType());
            movieDao.insert(movie);
        }else {
            logger.info("查询到的：" + old);
            logger.info(movie.getName() + " 已存在");

            old = addProperty(old, movie);
            logger.info("修改后的排行：" + old.getRankTypeStr());
            movieDao.updateByName(old);

        }

    }

    /**
     * 将一个新的对象的不为空属性添加到老的对象
     * @param old
     * @param movie
     * @return
     */
    private Movie addProperty(Movie old, Movie movie){
        if (old == null){
            return old;
        }
        if (movie == null){
            return old;
        }

        if (!TextUtils.isEmpty(movie.getDetailUrl())){
            old.setDetailUrl(movie.getDetailUrl());
        }
        if (!TextUtils.isEmpty(movie.getCoverUrl())){
            old.setCoverUrl(movie.getCoverUrl());
        }
        if (!TextUtils.isEmpty(movie.getIntro())){
            old.setIntro(movie.getIntro());
        }
        if (!TextUtils.isEmpty(movie.getLocation())){
            String oldLoc = old.getLocation();
            if (TextUtils.isEmpty(oldLoc)){
                old.setLocation(movie.getLocation());
            }else if (!oldLoc.contains(movie.getLocation())){
                old.setLocation(oldLoc + "," + movie.getLocation());
            }
        }
        if (!TextUtils.isEmpty(movie.getLocationStr())){
            String oldLoc = old.getLocationStr();
            if (TextUtils.isEmpty(oldLoc)){
                old.setLocationStr(movie.getLocationStr());
            }else if (!oldLoc.contains(movie.getLocationStr())){
                old.setLocationStr(oldLoc + "," + movie.getLocationStr());
            }
        }

        if (!TextUtils.isEmpty(movie.getRankType())){
            String oldRank = old.getRankType();
            if (TextUtils.isEmpty(oldRank)){
                old.setRankType(movie.getRankType());
            }else if (!oldRank.contains(movie.getRankType())){
                old.setRankType(oldRank + "," + movie.getRankType());
            }
        }

        if (!TextUtils.isEmpty(movie.getRankTypeStr())){
            String oldRank = old.getRankTypeStr();
            logger.info("新的 ：" + movie.getRankTypeStr() + " 老的：" + oldRank);
            if (TextUtils.isEmpty(oldRank)){
                old.setRankTypeStr(movie.getRankTypeStr());
            }else if (!oldRank.contains(movie.getRankTypeStr())){
                old.setRankTypeStr(oldRank + "," + movie.getRankTypeStr());
                logger.info("结合后：" + old.getRankTypeStr());
            }
        }

        if (!TextUtils.isEmpty(movie.getIntro())){
            old.setIntro(movie.getIntro());
        }
        if (!TextUtils.isEmpty(movie.getTime())){
            old.setTime(movie.getTime());
        }
        if (!TextUtils.isEmpty(movie.getType())){
            old.setType(movie.getType());
        }
        if (!TextUtils.isEmpty(movie.getTypeStr())){
            old.setTypeStr(movie.getRankTypeStr());
        }

        if (TextUtils.isEmpty(movie.getBtsStr())){
            movie.setBtsStr(movie.parseLinks(movie.getBts()));
        }
        if (!TextUtils.isEmpty(movie.getBtsStr())){
            old.setBtsStr(movie.getBtsStr());
        }
        if (TextUtils.isEmpty(movie.getThundersStr())){
            movie.setThundersStr(movie.parseLinks(movie.getThunders()));
        }
        if (!TextUtils.isEmpty(movie.getThundersStr())){
            old.setThundersStr(movie.getThundersStr());
        }
        if (TextUtils.isEmpty(movie.getMagnetsStr())){
            movie.setMagnetsStr(movie.parseLinks(movie.getMagnets()));
        }
        if (!TextUtils.isEmpty(movie.getMagnetsStr())){
            old.setMagnetsStr(movie.getMagnetsStr());
        }
        return old;

    }

    @Override
    public void addAll(List<Movie> movies) {
        if (ArrayUtils.isEmpty(movies)){
            return;
        }
        for (Movie movie : movies) {
            addOne(movie);
        }

    }

    public Movie getByName(String name){
        return movieDao.getByName(name);

    }

    /**
     * 检查下载链接是否转换好了
     * @param movie
     */
    private void checkDownloadUrls(Movie movie){
        if (TextUtils.isEmpty(movie.getThundersStr())){
            movie.setThundersStr(movie.parseLinks(movie.getThunders()));
        }
        if (TextUtils.isEmpty(movie.getMagnetsStr())){
            movie.setMagnetsStr(movie.parseLinks(movie.getMagnets()));
        }
        if (TextUtils.isEmpty(movie.getBtsStr())){
            movie.setBtsStr(movie.parseLinks(movie.getBts()));
        }

    }
}
