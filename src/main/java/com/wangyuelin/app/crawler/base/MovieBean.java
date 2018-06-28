package com.wangyuelin.app.crawler.base;

import org.apache.http.util.TextUtils;

import java.util.List;

/**
 * 描述: 电影的实体类，包含所有的电影属性
 *
 * @outhor wangyuelin
 * @create 2018-06-11 下午2:41
 */
public class MovieBean {
    private int id;
    private String name;//中文名
    private String enName;//英文名和其他的的名
    private String nickName;//多个翻译名，使用/分隔
    private String director;//导演，多个以/分隔
    private String scriptwriter;//多个以/分隔
    private String actors;//演员 多个以/分隔
    private String types;//类型 多个以/分隔
    private String location;//国家和地区，多个以/分隔
    private String language;//语言
    private String showTime;//上映日期多个以/分隔
    private String showYear;//上映的年份 如2017
    private String movieTime;//片长 多个以/分隔
    private String intro;//简介
    private String detaiWeblUrl;//从第三方抓的详情页
    private String downloadWebUrl;//抓取下载链接的url，多个url用;分隔
    private float doubanRank;//豆瓣评分
    private String tag;//用来给电影打标签，比如最新，最热，豆瓣高分等，多个使用/号分隔
    private List<ActorBean> aboutPerson;//相关的影人
    private List<String> movieScreenshot;//电影的截图
    private String posters;//海报的url，多个url使用;分隔

    public String getPosters() {
        return posters;
    }

    public void setPosters(String posters) {
        this.posters = posters;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDownloadWebUrl() {
        return downloadWebUrl;
    }

    public void setDownloadWebUrl(String downloadWebUrl) {
        this.downloadWebUrl = downloadWebUrl;
    }

    public float getDoubanRank() {
        return doubanRank;
    }

    public void setDoubanRank(float doubanRank) {
        this.doubanRank = doubanRank;
    }

    public String getDetaiWeblUrl() {
        return detaiWeblUrl;
    }

    public void setDetaiWeblUrl(String detaiWeblUrl) {
        this.detaiWeblUrl = detaiWeblUrl;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getScriptwriter() {
        return scriptwriter;
    }

    public void setScriptwriter(String scriptwriter) {
        this.scriptwriter = scriptwriter;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public List<ActorBean> getAboutPerson() {
        return aboutPerson;
    }

    public void setAboutPerson(List<ActorBean> aboutPerson) {
        this.aboutPerson = aboutPerson;
    }

    public List<String> getMovieScreenshot() {
        return movieScreenshot;
    }

    public void setMovieScreenshot(List<String> movieScreenshot) {
        this.movieScreenshot = movieScreenshot;
    }


    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getShowYear() {
        return showYear;
    }

    public void setShowYear(String showYear) {
        this.showYear = showYear;
    }

    /**
     * 设置数据
     * @param key
     * @param value
     */
    public void set(String key, String value) {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        if (key.contains("导演")) {
            setDirector(value);
        } else if (key.contains("主演")) {
            setActors(value);
        } else if (key.contains("编剧")) {
            setScriptwriter(value);
        } else if (key.contains("类型")) {
            setTypes(value);
        } else if (key.contains("国家") || key.contains("地区")) {
            setLocation(value);
        } else if (key.contains("语言")) {
            setLanguage(value);
        } else if (key.contains("上映日期")) {
            setShowTime(value);
        } else if (key.contains("片长")) {
            setMovieTime(value);
        } else if (key.contains("又名")) {
            setNickName(value);
        }
    }

    /**
     * 两个实例对比，将变化的属性赋值到新的实例
     * @param old
     */
    public void setValueFromOther(MovieBean old) {
        if (old.getId() > 0) {
            setId(old.getId());
        }

        //因为name设置了不可重复，所以不可以修改name的值
        if (TextUtils.isEmpty(getName())) {
            if (!TextUtils.isEmpty(old.getName())) {
                setName(old.getName());
            }
        }


        if (!TextUtils.isEmpty(old.getEnName())) {
            setEnName(old.getEnName());
        }

        if (!TextUtils.isEmpty(old.getNickName())) {
            setNickName(old.getNickName());
        }

        if (!TextUtils.isEmpty(old.getDirector())) {
            setDirector(old.getDirector());
        }

        if (!TextUtils.isEmpty(old.getScriptwriter())) {
            setScriptwriter(old.getScriptwriter());
        }

        if (!TextUtils.isEmpty(old.getActors())) {
            setActors(old.getActors());
        }

        setTypes(combine(getTypes(), old.getTypes(), "/"));

        setTag(combine(getTag(), old.getTag(), "/"));
        setPosters(combine(getPosters(), old.getPosters(), ";"));

        if (!TextUtils.isEmpty(old.getLocation())) {
            setLocation(old.getLocation());
        }

        if (!TextUtils.isEmpty(old.getLanguage())) {
            setLanguage(old.getLanguage());
        }

        if (!TextUtils.isEmpty(old.getShowTime())) {
            setShowTime(old.getShowTime());
        }

        if (!TextUtils.isEmpty(old.getMovieTime())) {
            setMovieTime(old.getMovieTime());
        }

        if (!TextUtils.isEmpty(old.getIntro())) {
            setIntro(old.getIntro());
        }

        if (!TextUtils.isEmpty(old.getDetaiWeblUrl())) {
            setDetaiWeblUrl(old.getDetaiWeblUrl());
        }

        if (!TextUtils.isEmpty(old.getShowYear())) {
            setShowYear(old.getShowYear());
        }

        if (old.getDoubanRank() > 0) {
            setDoubanRank(old.getDoubanRank());
        }
        if (!TextUtils.isEmpty(old.getDownloadWebUrl())) {
            setDownloadWebUrl(old.getDownloadWebUrl());
        }




    }


    /**
     * 将src中新的追加到des中，都是以/分隔的字符串
     * @param src
     * @param des
     * @return
     */
    private String combine(String src, String des, String splitChar) {
        if (TextUtils.isEmpty(splitChar)) {
            return des;
        }
        if (src == null) {
            src = "";
        }
        if (des == null) {
            des = "";
        }
        StringBuffer buffer = new StringBuffer(des);
        if (TextUtils.isEmpty(des) ) {
            if (!TextUtils.isEmpty(src)) {
                return src;
            } else {
                return des;
            }
        } else {
            if (TextUtils.isEmpty(src)) {
                return des;
            } else {
                String[] srcArray = src.split(splitChar);
                String[] desArray = des.split(splitChar);
                for (String s : srcArray) {
                    boolean isContains = false;
                    for (String d : desArray) {
                        if (!TextUtils.isEmpty(d) && !TextUtils.isEmpty(s) && (d.equals(s) || d.contains(s) || s.contains(d))) {
                            isContains = true;
                            break;
                        }

                    }
                    if (!isContains && !TextUtils.isEmpty(s)) {//将新的添加到老的
                        buffer.append(splitChar).append(s);
                    }
                }
            }

        }

        return buffer.toString();

    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("【id=").append(id).append("; name=").append(name).append("; nickName=").append(nickName).append("; director=")
                .append(director).append("; scriptwriter=").append(scriptwriter).append("; actors=").append(actors)
                .append("; types=").append(types).append("; location= ").append(location).append("; language=").append(language)
                .append("; showTime=").append(showTime).append("; movieTime=").append(movieTime).append("; intro=").append(intro)
                .append("; aboutPerson=").append(aboutPerson).append("; movieScreenshot=").append(movieScreenshot).append("】");
        return buffer.toString();
    }
}