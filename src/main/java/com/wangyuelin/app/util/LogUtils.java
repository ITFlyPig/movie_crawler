package com.wangyuelin.app.util;

import org.apache.http.util.TextUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Date;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午3:06
 */
public class LogUtils {


    private static  String GET_IP_LOG_FORMAT = "获取IP---------数量:%s---------时间：%s---------获取的ur:%s \n";

    private static String DOWNLOAD_PAGE_SPEED = "已下载页面---------时间为：%s---------页面的url为:%s \n";


    /**
     * log保存到文件，以便分析
     *
     * @param log
     * @param fileName
     */
    public static void logToFIle(String log, String fileName) {
        if (TextUtils.isEmpty(log) || TextUtils.isEmpty(fileName)) {
            return;
        }
        File logDir = getLogDir();
        if (logDir == null) {
            return;
        }

        String logPath = logDir.getAbsolutePath() + File.separator + fileName;
        try {
            FileWriter fileWriter = new FileWriter(new File(logPath), true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.print(log);
            printWriter.flush();
            fileWriter.flush();
            fileWriter.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取获取ip的log的格式
     * @param num
     * @param url
     * @return
     */
    public static String formatGetIpLog(int num, String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }

        String curTime = TimeUtil.getCurFormatTime();
        return String.format(GET_IP_LOG_FORMAT, num, curTime, url);
    }

    /**
     * 下载页面的log格式
     * @param url
     * @return
     */
    public static String formatDownloadPageSpeed(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String curTime = TimeUtil.getCurFormatTime();
        return String.format(DOWNLOAD_PAGE_SPEED ,curTime, url);
    }

    /**
     * 将下载页面的log写到日志里
     * @param url
     */
    public static void writeDownloadPageLog(String url) {
        String log = formatDownloadPageSpeed(url);
        if (TextUtils.isEmpty(log)) {
            return;
        }
        logToFIle(log, Constant.FileNames.DOWNLOAD_PAGE_LOG);


    }

    /***
     * 将获取ip的文log文件保存
     * @param log
     */
    public static void writeGetIpLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }
        logToFIle(log, Constant.FileNames.GET_IP_LOG);

    }

    /**
     * 获取log文件存放的目录
     *
     * @return
     */
    public static File getLogDir() {
        File logDir = null;
        try {
            logDir = ResourceUtils.getFile("classpath:log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logDir;
    }



}