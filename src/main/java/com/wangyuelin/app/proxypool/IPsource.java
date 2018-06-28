package com.wangyuelin.app.proxypool;

import java.util.List;

/**
 * 描述:代理ip的来源
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午2:44
 */
public interface IPsource {
    List<ProxyPool.IP> getIPs();

}