package com.wangyuelin.app.proxypool;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.TextUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午6:07
 */
public class ZhimaProxyPool {
    private static final Logger logger = LoggerFactory.getLogger(ZhimaProxyPool.class);
    private static final String IP_JSON = "{\n" +
            "  \"code\": 0,\n" +
            "  \"success\": true,\n" +
            "  \"msg\": \"0\",\n" +
            "  \"data\": [\n" +
            "    {\n" +
            "      \"ip\": \"42.243.138.107\",\n" +
            "      \"port\": 51246,\n" +
            "      \"expire_time\": \"2018-06-13 18:27:06\",\n" +
            "      \"city\": \"云南省红河哈尼族彝族自治州\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"58.218.3.197\",\n" +
            "      \"port\": 56511,\n" +
            "      \"expire_time\": \"2018-06-13 18:26:02\",\n" +
            "      \"city\": \"江苏省徐州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"115.212.126.160\",\n" +
            "      \"port\": 54230,\n" +
            "      \"expire_time\": \"2018-06-13 18:30:54\",\n" +
            "      \"city\": \"浙江省金华市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"124.163.72.10\",\n" +
            "      \"port\": 6996,\n" +
            "      \"expire_time\": \"2018-06-13 18:16:15\",\n" +
            "      \"city\": \"山西省忻州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"153.99.184.158\",\n" +
            "      \"port\": 4221,\n" +
            "      \"expire_time\": \"2018-06-13 18:23:04\",\n" +
            "      \"city\": \"江苏省连云港市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"119.7.226.61\",\n" +
            "      \"port\": 4246,\n" +
            "      \"expire_time\": \"2018-06-13 18:25:46\",\n" +
            "      \"city\": \"四川省眉山市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"183.188.84.3\",\n" +
            "      \"port\": 56996,\n" +
            "      \"expire_time\": \"2018-06-13 18:26:15\",\n" +
            "      \"city\": \"山西省忻州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"123.186.228.149\",\n" +
            "      \"port\": 2341,\n" +
            "      \"expire_time\": \"2018-06-13 18:14:01\",\n" +
            "      \"city\": \"辽宁省丹东市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"60.160.171.47\",\n" +
            "      \"port\": 54263,\n" +
            "      \"expire_time\": \"2018-06-13 18:26:52\",\n" +
            "      \"city\": \"云南省玉溪市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"182.34.16.80\",\n" +
            "      \"port\": 59077,\n" +
            "      \"expire_time\": \"2018-06-13 18:30:11\",\n" +
            "      \"city\": \"山东省烟台市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"106.92.102.226\",\n" +
            "      \"port\": 4697,\n" +
            "      \"expire_time\": \"2018-06-13 18:28:05\",\n" +
            "      \"city\": \"重庆市市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"49.71.106.99\",\n" +
            "      \"port\": 4954,\n" +
            "      \"expire_time\": \"2018-06-13 18:18:27\",\n" +
            "      \"city\": \"江苏省扬州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"113.120.61.70\",\n" +
            "      \"port\": 54252,\n" +
            "      \"expire_time\": \"2018-06-13 18:25:15\",\n" +
            "      \"city\": \"山东省济南市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"113.121.43.223\",\n" +
            "      \"port\": 9077,\n" +
            "      \"expire_time\": \"2018-06-13 18:30:10\",\n" +
            "      \"city\": \"山东省烟台市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"140.255.7.205\",\n" +
            "      \"port\": 5649,\n" +
            "      \"expire_time\": \"2018-06-13 18:15:16\",\n" +
            "      \"city\": \"山东省泰安市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"220.185.15.25\",\n" +
            "      \"port\": 54276,\n" +
            "      \"expire_time\": \"2018-06-13 18:22:16\",\n" +
            "      \"city\": \"浙江省衢州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"223.245.93.194\",\n" +
            "      \"port\": 54254,\n" +
            "      \"expire_time\": \"2018-06-13 18:16:48\",\n" +
            "      \"city\": \"安徽省蚌埠市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"182.100.239.152\",\n" +
            "      \"port\": 54184,\n" +
            "      \"expire_time\": \"2018-06-13 18:12:07\",\n" +
            "      \"city\": \"江西省吉安市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"110.187.91.130\",\n" +
            "      \"port\": 3244,\n" +
            "      \"expire_time\": \"2018-06-13 18:31:09\",\n" +
            "      \"city\": \"四川省眉山市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"ip\": \"101.27.20.161\",\n" +
            "      \"port\": 51246,\n" +
            "      \"expire_time\": \"2018-06-13 18:25:01\",\n" +
            "      \"city\": \"河北省保定市\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";


    /**
     * 查找可用的ip，从当前ip的下一个ip查找
     *
     * @param ip
     * @return
     */
    public static ProxyPool.IP getValidIp(ProxyPool.IP ip) {
        JSONObject jsonObject = JSONObject.parseObject(IP_JSON);
        if (jsonObject == null) {
            return null;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        if (jsonArray == null) {
            return null;
        }

        int index = 0;
        int size = jsonArray.size();
        if (ip != null) {
            for (int i = 0; i < size; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String ipStr = obj.getString("ip");
                String portStr = obj.getString("port");
                String expireTime = obj.getString("expire_time");
                if (ipStr.equalsIgnoreCase(ip.idAddress)) {
                    index = i;
                    break;
                }
            }
        }


        int pos = 0;
        if (index == size - 1) {
            pos = 0;
        } else {
            pos = index + 1;
        }

        while (pos != index) {
            JSONObject obj = jsonArray.getJSONObject(pos);
            String ipStr = obj.getString("ip");
            String portStr = obj.getString("port");
            String expireTime = obj.getString("expire_time");
            MyOkhttp3Downloader downloader = MyOkhttp3Downloader.getInstance();
            if (!TextUtils.isEmpty(ipStr) && !TextUtils.isEmpty(portStr)) {
                String html = downloader.downloadWithIP("https://www.baidu.com/", ipStr, Integer.valueOf(portStr));
                if (!TextUtils.isEmpty(html)) {
                    logger.info("ip有效：" + ipStr + ":" + portStr);
                    ProxyPool.IP validIp = new ProxyPool.IP();
                    validIp.idAddress = ipStr;
                    validIp.post = Integer.valueOf(portStr);
                    return validIp;

                }

            }


        }
        return null;

    }
}