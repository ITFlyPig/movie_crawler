package com.wangyuelin.app.crawler.dy2018.service;

import com.wangyuelin.app.crawler.dy2018.DyttMovie;
import com.wangyuelin.app.crawler.dy2018.dao.DyttMovieDao;
import com.wangyuelin.app.crawler.dy2018.service.itf.IDyttMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-21 下午6:18
 */
@Service
public class DyttMovieService implements IDyttMovie {

    @Autowired
    private DyttMovieDao dyttMovieDao;

    @Override
    public void saveOne(DyttMovie movie) {
        if (movie == null){
            return;
        }
        DyttMovie old = dyttMovieDao.getByName(movie.getMovieName());
        if (old == null){
            dyttMovieDao.insert(movie);
        }else {
            movie.setId(old.getId());
            dyttMovieDao.updateByPrimaryKey(movie);
        }
    }

}