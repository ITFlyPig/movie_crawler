package com.wangyuelin.app.util;

import org.apache.http.util.TextUtils;

import java.io.*;

/**
 * 描述:文件操作的工具类
 *
 * @outhor wangyuelin
 * @create 2018-05-29 下午4:20
 */
public class FileUtil {

    /**
     * 读取文件的字符
     *
     * @param fileName
     * @return
     */
    public static String readToString(String fileName, String charset) {
        String encoding = null;
        if (TextUtils.isEmpty(charset)) {
            encoding = "UTF-8";
        } else {
            encoding = charset;
        }

        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 清空文件的内容
     * @param path
     */
    public static void clearFileContent(String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        File f = new File(path);
        if (!f.exists()) {
            return;
        }
        try {
            FileWriter fileWriter =new FileWriter(f);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}