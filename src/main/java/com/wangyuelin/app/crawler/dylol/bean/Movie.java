package com.wangyuelin.app.crawler.dylol.bean;

import com.wangyuelin.app.util.ArrayUtils;
import org.apache.http.util.TextUtils;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

@Table(name = "movie")
public class Movie {

    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    //电影详情的url
    @Column(name = "detail_url")
    private String detailUrl;
    //电影封面的url
    @Column(name = "cover_url")
    private String coverUrl;
    //表示电影是动作的、恐怖的、剧情的等
    @Column(name = "type")
    private String type;
    @Column(name = "type_str")
    private String typeStr;

    //电影展示页面：首页、恐怖页等
    @Column(name = "location")
    private String location;
    @Column(name = "location_str")
    private String locationStr;

    @Column(name = "rank_type")
    private String rankType;

    @Column(name = "rank_type_str")
    private String rankTypeStr;

    @Column(name = "time")
    private String time;
    //简介
    @Column(name = "intro")
    private String intro;

    @Column(name = "magnets")
    private String magnetsStr;

    @Column(name = "thunders")
    private String thundersStr;

    @Column(name = "bts")
    private String btsStr;

    //磁力下载链接
    @Transient
    private List<MovieDownload> magnets;
    //迅雷下载链接
    @Transient
    private List<MovieDownload> thunders;

    //bt下载链接
    @Transient
    private List<MovieDownload> bts;


    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationStr() {
        return locationStr;
    }

    public void setLocationStr(String locationStr) {
        this.locationStr = locationStr;
    }

    public String getMagnetsStr() {
        return magnetsStr;
    }

    public void setMagnetsStr(String magnetsStr) {
        this.magnetsStr = magnetsStr;
    }

    public String getThundersStr() {
        return thundersStr;
    }

    public void setThundersStr(String thundersStr) {
        this.thundersStr = thundersStr;
    }

    public String getBtsStr() {
        return btsStr;
    }

    public void setBtsStr(String btsStr) {
        this.btsStr = btsStr;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<MovieDownload> getMagnets() {
        return magnets;
    }

    public void setMagnets(List<MovieDownload> magnets) {
        this.magnets = magnets;
    }

    public List<MovieDownload> getThunders() {
        return thunders;
    }

    public void setThunders(List<MovieDownload> thunders) {
        this.thunders = thunders;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<MovieDownload> getBts() {
        return bts;
    }

    public void setBts(List<MovieDownload> bts) {
        this.bts = bts;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public String getRankTypeStr() {
        return rankTypeStr;
    }

    public void setRankTypeStr(String rankTypeStr) {
        this.rankTypeStr = rankTypeStr;
    }

    /**
     * 将下载链接转为字符串，格式 如下
     * http://222.186.37.46:808/bt14BA.torrent[@$杀手$@];http://222.186.37.46:808/bt14BA.torrent[@$杀手2$@]
     * @param links
     * @return
     */
    public String parseLinks(List<MovieDownload> links){
        if (ArrayUtils.isEmpty(links)){
            return null;
        }
        int size = links.size();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++){
            MovieDownload movieDownload = links.get(i);
            if (i != 0){
                buffer.append(";");
            }
            buffer.append(movieDownload.getUrl()).append("[@$").append(movieDownload.getName()).append("$@]");
        }
        return buffer.toString();

    }

    public List<MovieDownload> parse(String links){
        if (TextUtils.isEmpty(links)){
            return null;
        }
        String[] linkArray = links.split(";");
        if (linkArray == null){
            return null;
        }
        List<MovieDownload> downloads = new ArrayList<MovieDownload>();
        for (String link : linkArray) {
            if (TextUtils.isEmpty(link)){
                continue;
            }
            MovieDownload download = new MovieDownload();
            int index = link.indexOf("[@$");
            int indexEnd = link.indexOf("$@]");
            String url = link.substring(0, index);
            String name = link.substring(index + "[@$".length(), indexEnd);
            download.setUrl(url);
            download.setName(name);
        }
        return downloads;

    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ name=").append(getName()).append("; detailUrl=").append(getDetailUrl())
                .append(";coverUrl=").append(coverUrl).append(";type=").append(type).append(";time=").append(time)
                .append(";magnets=").append(getMagnets()).append(";thunders=").append(getThunders()).append(";intro=")
                .append(intro).append(";typeStr=").append(typeStr).append(";rankType=").append(rankType).append(";rankTypeStr=").append(rankTypeStr)
                .append(";location=").append(getLocation()).append(";locationStr=").append(getLocationStr())
                .append("]");
        return buffer.toString();
    }
}
