package com.wangyuelin.app.proxypool.monitor;

import com.wangyuelin.app.crawler.downloader.OkhttpUtil;
import com.wangyuelin.app.proxypool.MyOkhttp3Downloader;
import com.wangyuelin.app.proxypool.ProxyPool;
import com.wangyuelin.app.proxypool.ProxyPoolUtil;
import com.wangyuelin.app.util.Constant;
import com.wangyuelin.app.util.FileUtil;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 描述: 监听文件修改的工具类
 *
 * @outhor wangyuelin
 * @create 2018-06-14 上午11:02
 */
public class MonitorFileUtil {
    private static final Logger logger = LoggerFactory.getLogger(MonitorFileUtil.class);
    private static Thread monitorThred;
    private static String monitorPath;
    /**
     * 开始监听文件的变化
     *
     */
    public static void startMonitorFile(){
        if (monitorThred != null && monitorThred.isAlive()) {
            return;
        }
        File file = null;
        try {
             file = ResourceUtils.getFile("classpath:ippool");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (file == null) {
            return;
        }

        monitorPath = file.getAbsolutePath();
        logger.info("开始监听文件夹：" + monitorPath);
        MonitorTask monitorTask = new MonitorTask(monitorPath, new MonitorTask.FileChangeListener() {
            @Override
            public void onCreated(String path) {
                logger.info("onCreated：" + path);

            }

            @Override
            public void onDelete(String path) {
                logger.info("onDelete：" + path);

            }

            @Override
            public void onModify(String path) {
                path = monitorPath + File.separator + path;

                logger.info("onModify：" + path);
                //读取新的ip
                if (!TextUtils.isEmpty(path) && path.contains(Constant.FileNames.IP_JSON_FILE)) {//解析本地json文件
                    ProxyPoolUtil.getIpsFromLocal(path);
                } else if (!TextUtils.isEmpty(path) && path.contains(Constant.FileNames.IP_URL_FILE)) {//下载，然后解析
                    ProxyPoolUtil.getIPFromUrl(path);
                }

            }
        });
        monitorThred = new Thread(monitorTask);
        monitorThred.start();

    }





}