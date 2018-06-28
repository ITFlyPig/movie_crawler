package com.wangyuelin.app.proxypool;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.util.TextUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午2:53
 */
public class MyOkhttp3Downloader implements IDownloader<String> {

    private static MyOkhttp3Downloader downloader;
    private static OkHttpClient okHttpClient;


    public static MyOkhttp3Downloader getInstance() {
        if (downloader == null) {
            synchronized (MyOkhttp3Downloader.class) {
                if (downloader == null) {
                    downloader = new MyOkhttp3Downloader();
                }
            }
        }
        return downloader;
    }

    private MyOkhttp3Downloader(){
        okHttpClient =  new OkHttpClient.Builder()
//                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.57.207.2",3128)))
                .build();
    };


    @Override
    public String download(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        Request request = new Request.Builder().url(url)
                .removeHeader("User-Agent")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537")
                .removeHeader("Cookie")
                .header("Cookie", "PHPSESSID=dbvps4lhqri156te0uba8rq0n1")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                ResponseBody body = response.body();
                if (body != null) {
                    return body.string();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String downloadWithIP(String url, String ip, int port) {
        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(ip) || port <= 0) {
            return null;
        }
        OkHttpClient okHttpClient =new OkHttpClient.Builder()
                .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip,port)))
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url)
                .removeHeader("User-Agent")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response == null) {
                return null;
            }
            ResponseBody body =  response.body();
            if (body == null) {
                return null;
            }
            return body.string();
        } catch (IOException e) {
//            e.printStackTrace();
        }


        return null;
    }
}