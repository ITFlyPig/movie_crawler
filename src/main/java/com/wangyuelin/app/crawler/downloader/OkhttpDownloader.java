package com.wangyuelin.app.crawler.downloader;

import com.wangyuelin.app.crawler.SpiderCraw;
import com.wangyuelin.app.proxypool.ProxyPool;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.PlainText;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

//爬出自带的下载经常出现超时，自定义下载
public class OkhttpDownloader implements Downloader {
    private static final Logger logger = LoggerFactory.getLogger(OkhttpDownloader.class);
    private OkHttpClient mOkHttpClient;
    private static OkhttpDownloader okhttpDownloader;


    public static OkhttpDownloader singleInstance() {
        if (okhttpDownloader == null) {
            synchronized (OkhttpDownloader.class) {
                if (okhttpDownloader == null) {
                    okhttpDownloader = new OkhttpDownloader();
                }
            }
        }

        return okhttpDownloader;

    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }

    private OkhttpDownloader() {
        mOkHttpClient = getUnsafeOkHttpClient();
    }

    @Override
    public Page download(Request request, Task task) {


        sleep();


        String url = request.getUrl();
        Response response = null;
        Page page = new Page();
        page.setDownloadSuccess(false);
        try {
            if (UrlUtils.isNeedProxyPool(url)) {
                response = OkhttpUtil.downloadPageWithIp(url, mOkHttpClient);
            } else {
                response = OkhttpUtil.download(url, mOkHttpClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //下载异常,将ur添加到爬取队列
            SpiderCraw.addRequestUrl(url, null);
            return page;

        }
        if (response == null){
            return page;
        }
        String content = null;

        try {
            ResponseBody body = response.body();
            if (body != null){

                page.setRawText(body.string());
                String html = page.getRawText();

                MediaType mediaType = body.contentType();
                Charset charset = null;
                if (mediaType != null) {
                    charset = mediaType.charset();
                }
                if (charset == null) {
                    charset = Charset.forName("utf-8");
                }
                if (!TextUtils.isEmpty(html)) {
                    page.setBytes(html.getBytes(charset));
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        page.setRequest(request);
        page.setStatusCode(response.code());
        page.setDownloadSuccess(true);
        page.setHeaders(response.headers().toMultimap());
        page.setUrl(new PlainText(request.getUrl()));

        return page;
    }

    @Override
    public void setThread(int threadNum) {

    }

    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
//            final TrustManager[] trustAllCerts = new TrustManager[]{
//                    new X509TrustManager() {
//                        @Override
//                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
//                        }
//
//                        @Override
//                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//                            return new java.security.cert.X509Certificate[]{};
//                        }
//                    }
//            };
//
//            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("SSL");
//            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//            // Create an ssl socket factory with our all-trusting manager
//            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            //日志
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
//            builder.sslSocketFactory(sslSocketFactory);
            builder.readTimeout(15, TimeUnit.SECONDS);
//            builder.addNetworkInterceptor(loggingInterceptor);
            builder.retryOnConnectionFailure(true);
//            builder.hostnameVerifier(new HostnameVerifier() {
//                @Override
//                public boolean verify(String hostname, SSLSession session) {
//                    return true;
//                }
//            });
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 获取随机的休眠时间，避免被网站禁止怕取
     * @param start
     * @param end
     * @return
     */
    public static  int getRandom(int start,int end){
        return (int)(Math.random()*(end-start+1)+start);
    }

    private int max = 3000;//10s
    private int min = 100;//3s

    /**
     * 随机的休眠
     */
    private void sleep(){
        int sleepTime = getRandom(min, max);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
