package com.wangyuelin.app.util;

import org.apache.http.util.TextUtils;

/**
 * 描述:字符处理工具
 *
 * @outhor wangyuelin
 * @create 2018-05-21 下午4:14
 */
public class MyTextUtil {

    /**
     * 去除字符串中所包含的空格（包括:空格(全角，半角)、制表符、换页符等）
     * @param s
     * @return
     */
    public static String removeAllBlank(String s){
        String result = "";
        if(null!=s && !"".equals(s)){
            result = s.replaceAll("[　*| *| *|\\s*]*", "");
            result = result.replaceAll("[\\u00A0]+", "");//去除160的空格
        }
        return result;
    }

    /**
     * 解析(XXXXX)类似的字符串
     * @param text
     * @return
     */
    public static String getRealText(String text) {
        if (TextUtils.isEmpty(text)) {
            return null;
        }
        int start = text.indexOf("(");
        int end = text.lastIndexOf(")");
        if (start + 1 < end && start + 1 > 0) {
            return text.substring(start, end);
        }
        return null;
    }


    /**
     * 判断是否为空
     * @param s
     * @return
     */
    public static boolean isEmpty(CharSequence s) {
        if (s == null) {
            return true;
        } else {
            int length = s.length();
            if (length == 0) {
                return true;
            }
            for (int i = 0; i < length; i++) {
                int ch = s.charAt(i);
                if (ch != 32 && ch != 160) {
                    return false;
                }
            }
            return true;

        }
    }
}