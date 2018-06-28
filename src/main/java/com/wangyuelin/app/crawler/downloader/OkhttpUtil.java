package com.wangyuelin.app.crawler.downloader;


import com.wangyuelin.app.proxypool.ProxyPool;
import com.wangyuelin.app.proxypool.UserAgentPool;
import com.wangyuelin.app.crawler.SpiderCraw;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class OkhttpUtil {


    public static ThreadLocal<ProxyPool.IP> curIP  = new ThreadLocal<>();

    private static OkHttpClient okHttpClient;
    private static OkhttpDownloader okhttpDownloader;

    static {
//        okHttpClient = new OkHttpClient();
        //日志
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        okHttpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(loggingInterceptor)
//                .writeTimeout(2, TimeUnit.SECONDS)
//                .readTimeout(2, TimeUnit.SECONDS)
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.57.207.2",3128)))
                .build();
//
        okhttpDownloader = OkhttpDownloader.singleInstance();
    }

    private static final Logger logger = LoggerFactory.getLogger(OkhttpUtil.class);


    /**
     * okhttp下载工具
     *
     * @param url
     * @param client
     * @return
     */
    public static Response download(String url, OkHttpClient client) throws IOException {
        logger.info("okhttp开始下载：" + url);
        Request request = new Request.Builder().url(url)
                .removeHeader("User-Agent")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/53")
                .build();

        OkHttpClient newClient = client.newBuilder()
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .proxy(null)
                .build();
        Response response = newClient.newCall(request).execute();
        return response;

    }

    /**
     * 下载页面，使用ip池
     *
     * @param url
     * @param client
     * @return
     */
    public static Response downloadPageWithIp(String url, OkHttpClient client) {
        logger.info("okhttp开始下载：" + url);
        //将上一次使用的ip放回ip池
        ProxyPool.addBackToPool(curIP.get());



        ProxyPool.IP randomIP = ProxyPool.getInstance().getValidIp();

        //测试使用
//        ProxyPool.IP randomIP = new ProxyPool.IP();
//        randomIP.idAddress = "113.138.210.194";
//        randomIP.post = 1346;

        //TODO 对于多线程来说，这里是共享的变量，存在问题。已使用ThreadLocal解决
        if (randomIP != null) {
            curIP.set(randomIP);
        }

        if (randomIP == null) {//IP获取失败，将要下载的url重新爬取
            SpiderCraw.addRequestUrl(url, null);
        } else {
            logger.info("下载页面使用的IP:" + randomIP.idAddress + ":" + randomIP.post);
            String useragent = UserAgentPool.getRandomUserAgent();
            Request request = new Request.Builder().url(url)
                    .removeHeader("User-Agent")
                    .header("User-Agent", useragent)
                    .build();
            OkHttpClient newClient = client
                    .newBuilder()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(randomIP.idAddress, randomIP.post)))
                    .writeTimeout(6, TimeUnit.SECONDS)
                    .readTimeout(6, TimeUnit.SECONDS)
                    .build();
            Response response = null;
            try {
                response = newClient.newCall(request).execute();
                //一个ip多次下载失败，就从ip池中移除
                if (response == null || response.code() != 200) {
                    ProxyPool.addFailIp(randomIP);
                }
            } catch (IOException e) {
                e.printStackTrace();
                //一个ip多次下载失败，就从ip池中移除
                ProxyPool.addFailIp(randomIP);
            }

            return response;

        }
        return null;


    }

    /**
     * 异步get网络请求
     *
     * @param url
     * @param callback
     */
    public static void getAsync(String url, Callback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Request request = new Request.Builder().url(url)
//                .header("Cookie",cookie)
                .removeHeader("User-Agent")
//                .addHeader()
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537")
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步get网络请求
     *
     * @param url
     */
    public static Response get(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        Request request = new Request.Builder().url(url)
//                .header("Cookie",cookie)
//                .removeHeader("User-Agent")
//                .addHeader()
//                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537")
                .build();
        try {
            return okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 下载页面
     *
     * @param url
     * @return
     */
    public static Page downloadPage(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        us.codecraft.webmagic.Request request = new us.codecraft.webmagic.Request();
        request.setUrl(url);
        Page page = okhttpDownloader.download(request, null);
        return page;

    }


}
