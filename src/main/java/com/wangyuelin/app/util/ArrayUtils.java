package com.wangyuelin.app.util;

import org.apache.http.util.TextUtils;

import java.util.List;

public class ArrayUtils {
    public static boolean isEmpty(List list){
        if (list == null || list.size() == 0){
            return true;
        }
        return false;

    }

    /**
     * 将list转为带分隔符的字符串
     * @param list
     * @param splitStr
     * @return
     */
    public static String listToStr(List<String> list, String splitStr) {
        if (list == null || TextUtils.isEmpty(splitStr)) {
            return "";
        }
        StringBuffer buffer = new StringBuffer();
        for (String s : list) {
            if (!TextUtils.isEmpty(buffer)) {
                buffer.append(splitStr);
            }
            buffer.append(s);

        }
        return buffer.toString();

    }
}
