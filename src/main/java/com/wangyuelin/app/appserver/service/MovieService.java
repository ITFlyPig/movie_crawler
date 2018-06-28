package com.wangyuelin.app.appserver.service;

import com.wangyuelin.app.appserver.bean.RepMoviebean;
import com.wangyuelin.app.appserver.dao.AppMovieDao;
import com.wangyuelin.app.appserver.service.itf.IMovie;
import com.wangyuelin.app.crawler.dylol.bean.LinkBean;
import com.wangyuelin.app.crawler.dylol.service.LinkService;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午5:49
 */
@Service
public class MovieService implements IMovie {
    @Autowired
    private LinkService linkService;

    @Autowired
    private AppMovieDao movieDao;

    /**
     * 分页查询数据
     * @param pageSize
     * @param pageIndex
     * @param type
     * @return
     */
    @Override
    public List<RepMoviebean> getMovies(int pageSize, int pageIndex, int type) {
        List<LinkBean> links = linkService.getLinks(pageSize, pageIndex, type);
        if (links == null) {
            return null;
        }
        List<RepMoviebean> repMoviebeans = new ArrayList<RepMoviebean>();
        for (LinkBean link : links) {
            RepMoviebean repMoviebean = movieDao.getByName(link.getMovieName());
            if (repMoviebean != null && !TextUtils.isEmpty(repMoviebean.getName())) {
                repMoviebeans.add(repMoviebean);
            }
        }
        return repMoviebeans;
    }

    /**
     * 是否还有更多的数据
     * @param pageSize
     * @param pageIndex
     * @param type
     * @return
     */
    @Override
    public boolean hasMore(int pageSize, int pageIndex, int type) {
        int totalNum = linkService.getTotalNum(type);
        if (totalNum > 0 && pageIndex * pageSize < totalNum) {
            return true;
        }
        return false;
    }


}