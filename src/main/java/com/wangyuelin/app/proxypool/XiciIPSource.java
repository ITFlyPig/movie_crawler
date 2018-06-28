package com.wangyuelin.app.proxypool;

import org.apache.http.util.TextUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:XiciDaili  代理ip来源
 *
 * @outhor wangyuelin
 * @create 2018-06-13 下午2:49
 */
public class XiciIPSource implements IPsource {
    private static final String XICI = "http://www.xicidaili.com/nn/";
    private static final String XICI_HTTP_PROXY = "http://www.xicidaili.com/wn/";

    private String[] urls = new String[]{XICI, XICI_HTTP_PROXY};//采集代理ip的url

    @Override
    public List<ProxyPool.IP> getIPs() {
        List<ProxyPool.IP> allIps = new ArrayList<ProxyPool.IP>();
        IDownloader<String> iDownloader = MyOkhttp3Downloader.getInstance();
        for (String url : urls) {
            String html = iDownloader.download(url);
            if (TextUtils.isEmpty(html)) {
                continue;
            }

            List<ProxyPool.IP> ips = parseIPs(html, url);
            if (ips != null && ips.size() > 0) {
                allIps.addAll(ips);
            }
        }

        return allIps;
    }


    /**
     * 解析得到代理ip
     * @param html
     * @param url
     * @return
     */
    private List<ProxyPool.IP> parseIPs(String html, String url) {
        if (TextUtils.isEmpty(html) || TextUtils.isEmpty(url)) {
            return null;
        }
        Document doc = Jsoup.parse(html);
        if (doc == null) {
            return null;
        }
        if (url.equals(XICI) || url.equalsIgnoreCase(XICI_HTTP_PROXY)) {//解析xici
            return parseXici(doc, url);
        }

        return null;

    }

    /**
     * 解析xici
     * @param doc
     * @param url
     * @return
     */
    private List<ProxyPool.IP> parseXici(Document doc, String url) {
        List<ProxyPool.IP> ips = new ArrayList<ProxyPool.IP>();
        Elements trs = doc.select("tbody tr");
        if (trs == null) {
            return null;
        }
        for (Element tr : trs) {
            Elements tds = tr.select("td");
            if (tds == null) {
                continue;
            }
            ProxyPool.IP ip = new ProxyPool.IP();
            for (Element td : tds) {
                String text = td.ownText();
                if (ProxyPoolUtil.isIP(text)) {
                    ip.idAddress = text;
                } else if (ProxyPoolUtil.isPort(text)) {
                    try {
                        ip.post = Integer.valueOf(text);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }

                }
            }
            ips.add(ip);
        }
        return ips;

    }
}