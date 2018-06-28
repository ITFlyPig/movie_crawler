package com.wangyuelin.app.proxypool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangyuelin.app.util.LogUtils;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 描述:代理ip池
 * 1、爬取网上的代理ip
 * 2、验证ip的有效性
 * 3、将有效的放进有效队列（过一定时间后悔变为无效，所以每隔一定时间还要对有效的ip进行验证）
 * 4、将无效的放进无效队列
 * 5、每隔固定的时间和有效ip不足10个就去网上开始采集代理ip
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午2:39
 */
public class ProxyPool {
    private static final Logger logger = LoggerFactory.getLogger(ProxyPool.class);

    public static LinkedBlockingDeque<IP> allIPsQeueu = new LinkedBlockingDeque<IP>();//最开始ip队列
    public static LinkedBlockingDeque<IP> validQeueu = new LinkedBlockingDeque<IP>();//有效队列
    public static LinkedBlockingDeque<IP> invalidQeueu = new LinkedBlockingDeque<IP>();//无效的队列
    public static List<IP> validIPPool =  Collections.synchronizedList(new LinkedList<IP>());//有效的i池
    private static Map<String, Integer> failIPCount = new LinkedHashMap<>();//记载一个ip页面的下载失败次数
    private GatherIpsRunnable gatherIpsRunnable;
    private CheckIP checkIP;
    private CheckValidQueue checkValidQueue;
    private static ProxyPool proxyPool;


    public static ProxyPool getInstance() {
        if (proxyPool == null) {
            synchronized (ProxyPool.class) {
                if (proxyPool == null) {
                    proxyPool = new ProxyPool();
                }
            }
        }
        return proxyPool;
    }

    /**
     * 开始采集ip
     * 两个线程：一个采集ip，一个验证和返回有效的ip
     */
    public void startGatherIps() {
//        if (gatherIpsRunnable == null) {
//            gatherIpsRunnable = new GatherIpsRunnable();
//            new Thread(gatherIpsRunnable).start();
//        }
        if (checkIP == null) {
            checkIP = new CheckIP();
            new Thread(checkIP).start();
        }


    }

    /**
     * 开始检查有效ip队列是否为空
     */
    public void startCheckValidQueue() {
        if (checkValidQueue == null) {
            checkValidQueue = new CheckValidQueue();
            new Thread(checkValidQueue).start();
        }
    }


    /**
     * 获取有效的代理ip，返回之前需要验证是否有效
     *
     * @return
     */
    public IP getValidIp() {
        logger.info("开始获取有效的ip");
        if (validIPPool.size() + allIPsQeueu.size() < 1) {
            ProxyPoolUtil.startGetIps();
        }

        IP validIp = getRadomIP();
        if (validIp != null) {//这里可能会阻塞
            try {
                validQeueu.put(validIp);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            return validQeueu.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 检测ip是否有效
     * @return
     */
    private IP getRadomIP() {
        while (true) {
            int size = validIPPool.size();
            if (size == 0) {
                return null;
            }
            int pos = 0;
            if (size == 1){
                pos = 0;
            } else {
                pos = ProxyPoolUtil.random(0, size - 1);
            }
            IP ip = validIPPool.get(pos);
//            boolean isValid = ProxyPoolUtil.isIpValid(ip);
            validIPPool.remove(pos);
//            if (isValid) {//ip有效
//                return ip;
//
//            }
            return ip;
        }

    }


    public static class IP {
        public String idAddress;//ip地址
        public int post;//端口
        public String expireTime;//过期时间
    }

    //ip采集线程
    private class GatherIpsRunnable implements Runnable {
        private int splitTime = 1000 * 60 * 8;//15分钟就采集一次
        private boolean stop;

        @Override
        public void run() {
            while (!stop) {
                logger.info("开始采集代理ip");
                IPsource iPsource = new XiciIPSource();
                List<IP> ips = iPsource.getIPs();
                if (ips != null) {//将采集到的ip放到队列
                    for (IP ip : ips) {
                        try {
                            if (!TextUtils.isEmpty(ip.idAddress) && ip.post > 0) {
                                allIPsQeueu.put(ip);
                            }

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                logger.info("采集到的代理ip数：" + (ips == null ? 0 : ips.size()));

                try {
                    Thread.sleep(splitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        public void setSplitTime(int splitTime) {
            this.splitTime = splitTime;
        }

        public void setStop(boolean stop) {
            this.stop = stop;
        }
    }

    /**
     * 检测ip的有效性
     */
    private class CheckIP implements Runnable {
        private boolean stop;

        @Override
        public void run() {
            while (!stop) {
                logger.info("有效的ip数：" + validIPPool.size() + " 无效的ip数：" + invalidQeueu.size());
                boolean isIPValis = false;
                IP ip = null;
                try {
                    ip = allIPsQeueu.take();
                    isIPValis = ProxyPoolUtil.isIpValid(ip);
                    logger.info("是否有效：" + isIPValis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (isIPValis && !contains(validIPPool, ip)) {
                            validIPPool.add(ip);//放到有效队列
                            logger.info("有效放到有效队列");
                        } else {
                            if (!contains(invalidQeueu, ip)) {
                                invalidQeueu.put(ip);//放到无效队列
                                logger.info("无效放到无效队列");
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    /**
     * 定时检查代销费的ip队列是否为空，如果为空且则从ip池获取然后添加
     */
    private static final int CHECK_TIME = 10 * 1000;//10s检查一次
    private class CheckValidQueue implements Runnable{

        @Override
        public void run() {
            while (true) {
                if (validQeueu.size() == 0) {
                    IP ip = getRadomIP();
                    if (ip != null) {
                        try {
                            validQeueu.put(ip);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                try {
                    Thread.sleep(CHECK_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 是否已存在
     *
     * @param ip
     * @return
     */
    private boolean contains(Iterable iterable, IP ip) {
        if (ip == null) {
            return false;
        }
        Iterator<IP> it = iterable.iterator();
        while (it.hasNext()) {
            IP p = it.next();
            if (!TextUtils.isEmpty(p.idAddress) && p.idAddress.equals(ip.idAddress) && p.post == ip.post) {
                return true;
            }

        }
        return false;
    }


    /**
     * z芝麻http代理返回的存ip的json解析和添加ip到队列
     * http://h.zhimaruanjian.com/
     *
     * @param ipJson
     */
    public static void parseAndAddIpZhima(String ipJson, String path) {
        logger.info("开始解析芝麻代理的json文件");
        if (TextUtils.isEmpty(ipJson)) {
            return;
        }
        JSONObject jsonObject = JSONObject.parseObject(ipJson);
        if (jsonObject == null) {
            return;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if (jsonArray == null) {
            return;
        }

        int size = jsonArray.size();

        //将获取ip的log写到文件
        LogUtils.writeGetIpLog(LogUtils.formatGetIpLog(size, path));

        for (int i = 0; i < size; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String ipStr = obj.getString("ip");
            String portStr = obj.getString("port");
            String expireTime = obj.getString("expire_time");
            if (TextUtils.isEmpty(ipStr) || TextUtils.isEmpty(portStr)) {
                continue;
            }
            IP ip = new IP();
            ip.idAddress = ipStr;
            ip.post = Integer.valueOf(portStr);
            ip.expireTime = expireTime;
            logger.info("解析得到的ip：" + ipStr + ":" + portStr);
            try {
                allIPsQeueu.put(ip);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 将ip放回ip池
     * @param ip
     */
    public static void addBackToPool(IP ip) {
        if (ip == null) {
            return;
        }
        validIPPool.add(ip);
    }

    /**
     * 统计ip下载失败的次数
     * @param ip
     */
    public static void addFailIp(IP ip) {
        if (ip == null) {
            return;
        }
        Integer icount = failIPCount.get(ip.idAddress);
        int failCount = 0;
        if (icount != null) {
            failCount = icount;
        }

        failCount++;
        failIPCount.put(ip.idAddress, failCount);
    }

    /**
     * 获取ip下载失败的次数
     * @param ip
     * @return
     */
    public static int getFailIPCount(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return 0;
        }
        Integer count = failIPCount.get(ip);
        return count == null ? 0 : count;

    }

}