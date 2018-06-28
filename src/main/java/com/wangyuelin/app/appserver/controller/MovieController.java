package com.wangyuelin.app.appserver.controller;

import com.wangyuelin.app.appserver.bean.RepMoviebean;
import com.wangyuelin.app.appserver.service.itf.IMovie;
import com.wangyuelin.app.crawler.dylol.bean.app.RespBean;
import com.wangyuelin.app.util.RespCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-28 下午4:56
 */
@Controller
@RequestMapping("movie")
public class MovieController {
    @Autowired
    private IMovie movieService;


    /**
     * 获取电影的列表
     * @param pageSize
     * @param pageIndex
     * @param type
     * @return
     */
    @RequestMapping("/getMovies")
    @ResponseBody
    public RespBean getMovies(@RequestParam("pageSize") int pageSize,
                              @RequestParam("pageIndex") int pageIndex, @RequestParam("type") int type) {
        //对分页数据的合法性判断
        boolean hasMore = movieService.hasMore(pageSize, pageIndex, type);
        RespBean respBean = new RespBean();
        if (!hasMore) {//请求的数据不在范围内
            respBean.setCode(RespCode.ERROR.getIndex());
            respBean.setMsg(RespCode.ERROR.getMsg());
            return null;
        }

        List<RepMoviebean> movies = movieService.getMovies(pageSize, pageIndex, type);
        if (movies == null) {
            respBean.setCode(RespCode.ERROR.getIndex());
            respBean.setMsg(RespCode.ERROR.getMsg());
            return null;
        }

        hasMore = movieService.hasMore(pageSize, pageIndex + 1, type);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("hasMore", hasMore);
        data.put("movies", movies);
        respBean.setResponse(data);
        respBean.setCode(RespCode.SUCCESS.getIndex());
        respBean.setMsg(RespCode.SUCCESS.getMsg());

        return respBean;

    }

}