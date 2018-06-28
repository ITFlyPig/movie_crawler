package com.wangyuelin.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-15 下午3:53
 */
public class TimeUtil {

    /**
     * 获取当前的时间
     * @return
     */
    public static String getCurFormatTime() {
        SimpleDateFormat format = new SimpleDateFormat("YYYY年MM月dd日  HH小时mm分ss秒");
        return format.format(new Date());
    }

}