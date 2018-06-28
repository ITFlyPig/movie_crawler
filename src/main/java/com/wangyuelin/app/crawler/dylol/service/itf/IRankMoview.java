package com.wangyuelin.app.crawler.dylol.service.itf;

import com.wangyuelin.app.crawler.dylol.bean.Movie;

import java.util.List;

public interface IRankMoview {
    void addAll(List<Movie> movies);
    void addOne(Movie movie);
}
