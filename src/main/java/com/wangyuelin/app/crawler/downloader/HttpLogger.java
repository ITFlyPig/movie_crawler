package com.wangyuelin.app.crawler.downloader;

import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述:okhttp网络请求日志
 *
 * @outhor wangyuelin
 * @create 2018-05-23 上午11:56
 */
public class HttpLogger implements HttpLoggingInterceptor.Logger {
    private final Logger logger = LoggerFactory.getLogger(HttpLogger.class);

    @Override
    public void log(String s) {
        logger.info(s);
    }
}