package com.wangyuelin.app.crawler.douban.bean;

import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-05-30 上午11:15
 */
public class DoubanMovieInfo {
    /**
     * rating : {"max":10,"average":8.3,"stars":"45","min":0}
     * reviews_count : 110
     * wish_count : 13819
     * douban_site :
     * year : 1977
     * images : {"small":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp","large":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp","medium":"http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp"}
     * alt : https://movie.douban.com/subject/1293838/
     * id : 1293838
     * mobile_url : https://movie.douban.com/subject/1293838/mobile
     * title : 星球大战
     * do_count : null
     * share_url : http://m.douban.com/movie/subject/1293838
     * seasons_count : null
     * schedule_url :
     * episodes_count : null
     * countries : ["美国"]
     * genres : ["动作","科幻","冒险"]
     * collect_count : 87098
     * casts : [{"alt":"https://movie.douban.com/celebrity/1009238/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp"},"name":"哈里森·福特","id":"1009238"},{"alt":"https://movie.douban.com/celebrity/1027809/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452759480.3.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452759480.3.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452759480.3.webp"},"name":"马克·哈米尔","id":"1027809"},{"alt":"https://movie.douban.com/celebrity/1027813/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1482901672.88.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1482901672.88.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1482901672.88.webp"},"name":"凯丽·费雪","id":"1027813"},{"alt":"https://movie.douban.com/celebrity/1019084/","avatars":{"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1227.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1227.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1227.webp"},"name":"彼得·库欣","id":"1019084"}]
     * current_season : null
     * original_title : Star Wars
     * summary : 遥远星系发生叛乱，银河共和国被推翻，奥尔德兰星的莉亚公主（卡里•费什尔 Carrie Fisher 饰）领导抵抗组织对抗银河帝国，却不幸被银河帝国的维德勋爵（大卫•普劳斯 David Prowse 饰）所俘。机器人R2-D2携银河帝国新武器“死星”的结构图与同伴翻译机器人C-3PO逃至塔图因星，途中被加瓦人抓住卖给青年卢克•天行者（马克•哈米尔 Mark Hamill 饰）的叔父。在卢克的帮助下，众人阴错阳差找到了隐居的绝地武士欧比-旺•肯诺比（亚历克•吉尼斯 Alec Guinness 饰）。欧比-旺交给卢克绝地之道，并带卢克与两机器人到港口城市茅斯伊斯垒，雇佣走私者韩•索罗（哈里森•福特饰）的千年隼飞船前往奥尔德兰……
     本片获第50界奥斯卡最佳艺术指导，最佳服装设计，最佳视觉效果，最佳剪辑，最佳原创音乐，最佳声效六项大奖及特殊贡献奖（本•伯特 Ben Burtt），并获最佳电影，最佳男配角（亚历克•吉尼斯），最佳导演（乔治•卢卡斯），最佳原创电影音乐四项提名。©豆瓣
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1054507/","avatars":{"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp"},"name":"乔治·卢卡斯","id":"1054507"}]
     * comments_count : 10809
     * ratings_count : 67842
     * aka : ["星球大战第四集：新希望","星际大战四部曲：曙光乍现","星际大战","Star Wars: Episode IV - A New Hope","星球大战"]
     */

    private RatingBean rating;//评分
    private int reviews_count;
    private int wish_count;
    private String douban_site;
    private String year;//年代
    private ImagesBean images;//海报
    private String alt;
    private String id;//对应的豆瓣id
    private String mobile_url;
    private String title;//名称
    private Object do_count;
    private String share_url;
    private Object seasons_count;
    private String schedule_url;
    private Object episodes_count;
    private int collect_count;
    private Object current_season;
    private String original_title;
    private String summary;
    private String subtype;
    private int comments_count;
    private int ratings_count;
    private List<String> countries;//国家
    private List<String> genres;//类型
    private List<CastsBean> casts;//演员
    private List<DirectorsBean> directors;//导演
    private List<String> aka;//别名
    private String language;//语言
    private int total_time;//总的时长
    private List<ShowTime> showTimes;//上映的时间
    private List<String> aboutPics;//相关的图片
    private List<Video> videos;//相关的视频
    private List<Download> downloads;//下载地址


    public List<Download> getDownloads() {
        return downloads;
    }

    public void setDownloads(List<Download> downloads) {
        this.downloads = downloads;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public List<ShowTime> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTime> showTimes) {
        this.showTimes = showTimes;
    }

    public List<String> getAboutPics() {
        return aboutPics;
    }

    public void setAboutPics(List<String> aboutPics) {
        this.aboutPics = aboutPics;
    }

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<DirectorsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<DirectorsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }


    public static class RatingBean {
        /**
         * max : 10
         * average : 8.3
         * stars : 45
         * min : 0
         */

        private int max;
        private double average;
        private String stars;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        /**
         * small : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp
         * large : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp
         * medium : http://img7.doubanio.com/view/photo/s_ratio_poster/public/p732941361.webp
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1009238/
         * avatars : {"small":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp","large":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp","medium":"http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp"}
         * name : 哈里森·福特
         * id : 1009238
         */

        private String alt;
        private AvatarsBean avatars;//演员头像
        private String name;//演员名称
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


    }

    public static class AvatarsBean {
        /**
         * small : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp
         * large : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp
         * medium : http://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p1452760482.06.webp
         */

        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class DirectorsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1054507/
         * avatars : {"small":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp","large":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp","medium":"http://img7.doubanio.com/view/celebrity/s_ratio_celebrity/public/p650.webp"}
         * name : 乔治·卢卡斯
         * id : 1054507
         */

        private String alt;
        private AvatarsBean avatars;
        private String name;
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

    }

    /**
     * 上映时间
     */
    public static class ShowTime{

        private String time;//时间
        private String coutry;//国家

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getCoutry() {
            return coutry;
        }

        public void setCoutry(String coutry) {
            this.coutry = coutry;
        }
    }

    /**
     * 影片相关的视频：例如预告等
     */
    public static class Video{
        private String name;
        private String url;
        private String coverUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }
    }

    /**
     * 下载链接
     */
    public static class Download{
        private String name;
        private String link;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

}