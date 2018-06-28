package com.wangyuelin.app.crawler.idata;

import com.google.gson.Gson;
import com.wangyuelin.app.crawler.baidu.task.BDMovieTask;
import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.crawler.db.StaticDBHandle;
import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 描述:使用idata提供的api来查询电影的信息
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午7:22
 */
public class DoubanSeacherTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DoubanSeacherTask.class);
    private String keyWord;

    public DoubanSeacherTask(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void run() {
        if (TextUtils.isEmpty(keyWord)) {
            return;
        }
        final String url = String.format(IdataUtil.MOVIE_SEACHER_API, keyWord);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        OkhttpUtil.getAsync(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                logger.info("使用idata查询豆瓣电影信息失败，url：" + url);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response == null) {
                    return;
                }
                ResponseBody body = response.body();
                if (body == null){
                    return;
                }
                String json = body.string();
                if (TextUtils.isEmpty(json)) {
                    return;
                }
                Gson gson = new Gson();
                IdataDoubanMovieResp resp = gson.fromJson(json, IdataDoubanMovieResp.class);
                //将数据保存到数据库
                if (resp == null && !resp.getRetcode().equals("000000")) {
                    return;
                }
                List<IdataDoubanMovieResp.DataBean> datas = resp.getData();
                if (datas == null) {
                    return;
                }
                for (IdataDoubanMovieResp.DataBean data : datas) {
                    MovieBean movieBean = data.parseToMovieBean();
                    if (movieBean == null) {
                        continue;
                    }
                    StaticDBHandle.doubanMovieService.updateByDetailUrl(movieBean);

                }



            }
        });


    }
}