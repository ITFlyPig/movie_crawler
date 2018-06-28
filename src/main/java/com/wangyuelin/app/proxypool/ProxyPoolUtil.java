package com.wangyuelin.app.proxypool;

import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.util.FileUtil;
import com.wangyuelin.app.util.LogUtils;
import com.wangyuelin.app.util.TimeUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;


/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午3:08
 */
public class ProxyPoolUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProxyPool.class);

    /**
     * 是否是ip
     * @param ip
     * @return
     */
    public static boolean isIP(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        String reg = "^[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}$";
        if (Pattern.matches(reg, ip)) {
            return true;
        }
        return false;

    }

    /**
     * 是否是端口
     * @param port
     * @return
     */
    public static boolean isPort(String port) {
        if (TextUtils.isEmpty(port)) {
            return false;
        }
        String reg = "^[0-9]{1,6}$";
        if (Pattern.matches(reg, port)) {
            return true;
        }
        return false;
    }


    /**
     * get请求
     * @param url
     * @return
     */
    public static String get(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    /**
     * 开始获取ip
     * 1、从文件获取
     * 2、从url获取
     */
    public static void startGetIps() {
        File f = null;
        try {
            f =  ResourceUtils.getFile("classpath:ippool");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (f != null) {
            String ipJsonFIlePath = f.getAbsolutePath() + File.separator + Constant.FileNames.IP_JSON_FILE;
            getIpsFromLocal(ipJsonFIlePath);
        }

        String jsonUrlFIlePath = f.getAbsolutePath() + File.separator + Constant.FileNames.IP_URL_FILE;
        getIPFromUrl(jsonUrlFIlePath);



    }

    /**
     * 从文件里面获取url，然后从url下载ip
     * @param path
     */
    public static void getIPFromUrl(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        logger.info("从网络获取ip" + TimeUtil.getCurFormatTime());
        String urls = FileUtil.readToString(path, "utf-8");
        if (!TextUtils.isEmpty(urls)) {
            String[] urlArray = urls.split(";");
            if (urlArray == null) {
                return;
            }
            for (String url : urlArray) {
                handleUrl(url);

            }
        }

    }

    /**
     * 从本地文件获取ip
     * @param path
     */
    public static void getIpsFromLocal(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        String ipJsonFIlePath = path;
        String contet = FileUtil.readToString(ipJsonFIlePath, "utf-8");
        if (!TextUtils.isEmpty(contet)) {
            contet = contet.replaceAll(" ", "");
        }
        if (!TextUtils.isEmpty(contet)) {//文件内容不为空才清空，避免检测文件变换导致的死循环
            FileUtil.clearFileContent(ipJsonFIlePath);
        }

        if (!TextUtils.isEmpty(contet)) {
            ProxyPool.parseAndAddIpZhima(contet, path);
        }

    }


    /**
     * get请求url，然后处理返回的json文件
     * @param url
     */
    public static void handleUrl(String url) {
        logger.info("开始处理url：" + url);
        if (TextUtils.isEmpty(url)) {
            return;
        }
        String content = ProxyPoolUtil.get(url);
        logger.info("返回值：" + content);
        if (url.contains(Constant.Host.ZHIMA)) {
            ProxyPool.parseAndAddIpZhima(content, url);
        }
    }


    /**
     * 产生特定范围的随机数
     * @param min
     * @param max
     * @return
     */
    public static int random(int min, int max) {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    /**
     * 检测IP是否有效
     * @param ip
     * @return
     */
    private static final int TYR_TIME = 3;//判断一个ip是否可以连接试的次数
    public static boolean isIpValid(ProxyPool.IP ip) {
        if (ip == null || TextUtils.isEmpty(ip.idAddress)) {
            return false;
        }
        int count = 0;
        while (count < TYR_TIME) {
            IDownloader<String> downloader = MyOkhttp3Downloader.getInstance();
            String html = downloader.downloadWithIP("http://www.baidu.com/", ip.idAddress, ip.post);
            if (!TextUtils.isEmpty(html)) {
                logger.info("IP连接网络有效");
                if (ProxyPool.getFailIPCount(ip.idAddress) > 5 ) {//ip下载失败5次就算是无效的
                    return false;
                }

                return true;
            }

            count++;
        }


        logger.info("IP无效");
        return false;
    }
}