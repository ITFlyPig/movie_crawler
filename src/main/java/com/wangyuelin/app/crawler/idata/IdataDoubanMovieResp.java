package com.wangyuelin.app.crawler.idata;

import com.wangyuelin.app.crawler.base.MovieBean;
import com.wangyuelin.app.util.ArrayUtils;
import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 *
 * @outhor wangyuelin
 * @create 2018-06-26 下午8:00
 */
public class IdataDoubanMovieResp {


    /**
     * data : [{"wishCount":10982,"videoUrls":["http://vt1.doubanio.com/201806261933/ec7fedcb1574ce72dc6912880cd7a293/view/movie/M/302320164.mp4"],"durationMin":155,"ratingCount":19007,"tags":["女性","真实事件改编","宝莱坞","宗教","剧情","社会","爱情","女权","印度","喜剧"],"coverUrl":"http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2523632248.jpg","favoriteCount":20303,"directors":[{"name":"什里·那拉扬·辛","id":"1339168","nameEng":"ShreeNarayanSingh","type":null}],"actors":[{"name":"阿克谢·库玛尔","id":"1049615","nameEng":"AkshayKumar"},{"name":"阿努潘·凯尔","id":"1054303","nameEng":"AnupamKher"},{"name":"布米·佩德卡尔","id":"1354711","nameEng":"BhumiPednekar"},{"name":"拉什·沙玛","id":"1350832","nameEng":"RajeshSharma"}],"pressDate":"2017","catName1":"movie","description":"改编自真人真事，探讨印度农村家庭普遍没有厕所，妇女必须在野外如厕的现象。女主角担任教师，嫁给男主角后，发现家中没有厕所，和男主角\u201c闹离婚\u201d，还来了一场革命。","genres":["剧情","喜剧"],"countries":["印度"],"releaseDates":["2017-08-11(印度)","2018-06-08(中国大陆)"],"writers":[{"name":"格丽玛","id":"1387228","nameEng":"Garima"},{"name":"西达尔特","id":"1338345","nameEng":"Siddharth"}],"rating":7.1,"releaseStatus":"已上映","publishDate":1528387200,"languages":["印地语"],"url":"https://movie.douban.com/subject/26942645/","title":"厕所英雄","imageUrls":["http://img7.doubanio.com/view/photo/l/public/p2524229850.jpg","http://img3.doubanio.com/view/photo/l/public/p2523971818.jpg","http://img3.doubanio.com/view/photo/l/public/p2525295038.jpg","http://img7.doubanio.com/view/photo/l/public/p2525295033.jpg","http://img3.doubanio.com/view/photo/l/public/p2525295026.jpg","http://img7.doubanio.com/view/photo/l/public/p2525144244.jpg","http://img7.doubanio.com/view/photo/l/public/p2524713362.jpg","http://img3.doubanio.com/view/photo/l/public/p2524713359.jpg","http://img3.doubanio.com/view/photo/l/public/p2524608159.jpg","http://img3.doubanio.com/view/photo/l/public/p2524608157.jpg"],"publishDateStr":"2018-06-07T16:00:00","id":"26942645","reviewCount":328,"commentCount":7750,"titleAliases":["Toilet - Ek Prem Katha","厕所：一个爱情故事(台)","厕所：一个爱的故事","टॉयलेट: एक प्रेम कथा","Toilet: A Love Story"]}]
     * retcode : 000000
     * appCode : douban
     * hasNext : false
     * pageToken : null
     * dataType : movie
     */

    private String retcode;
    private String appCode;
    private boolean hasNext;
    private Object pageToken;
    private String dataType;
    private List<DataBean> data;

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Object getPageToken() {
        return pageToken;
    }

    public void setPageToken(Object pageToken) {
        this.pageToken = pageToken;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * 将get请求返回的数据转换为存数据库的数据
         * @return
         */
        public MovieBean parseToMovieBean() {
            MovieBean movieBean = new MovieBean();
            movieBean.setName(getTitle());
            movieBean.setNickName(ArrayUtils.listToStr(getTitleAliases(), "/"));
            movieBean.setDirector(getDirectorStr());
            movieBean.setScriptwriter(getwriterStr());
            movieBean.setActors(getActorStr());
            movieBean.setTypes(ArrayUtils.listToStr(getGenres(), "/"));
            movieBean.setLocation(ArrayUtils.listToStr(getCountries(), "/"));
            movieBean.setLanguage(ArrayUtils.listToStr(getLanguages(), "/"));
            movieBean.setShowTime(ArrayUtils.listToStr(getReleaseDates(), "/"));
            movieBean.setIntro(getDescription());
            movieBean.setDetaiWeblUrl(getUrl());
            movieBean.setDoubanRank((float) getRating());
            movieBean.setTag(ArrayUtils.listToStr(getTags(), "/"));
            List<String> imgs = new ArrayList<>();
            if (TextUtils.isEmpty(getCoverUrl())) {
                imgs.add(getCoverUrl());
            }
            if (getImageUrls() != null) {
                imgs.addAll(getImageUrls());
            }

            movieBean.setPosters(ArrayUtils.listToStr(imgs, ";"));
            movieBean.setMovieTime(String.valueOf(getDurationMin()));
            movieBean.setShowYear(getPressDate());
            return movieBean;
        }




        /**
         * wishCount : 10982
         * videoUrls : ["http://vt1.doubanio.com/201806261933/ec7fedcb1574ce72dc6912880cd7a293/view/movie/M/302320164.mp4"]
         * durationMin : 155
         * ratingCount : 19007
         * tags : ["女性","真实事件改编","宝莱坞","宗教","剧情","社会","爱情","女权","印度","喜剧"]
         * coverUrl : http://img3.doubanio.com/view/photo/s_ratio_poster/public/p2523632248.jpg
         * favoriteCount : 20303
         * directors : [{"name":"什里·那拉扬·辛","id":"1339168","nameEng":"ShreeNarayanSingh","type":null}]
         * actors : [{"name":"阿克谢·库玛尔","id":"1049615","nameEng":"AkshayKumar"},{"name":"阿努潘·凯尔","id":"1054303","nameEng":"AnupamKher"},{"name":"布米·佩德卡尔","id":"1354711","nameEng":"BhumiPednekar"},{"name":"拉什·沙玛","id":"1350832","nameEng":"RajeshSharma"}]
         * pressDate : 2017
         * catName1 : movie
         * description : 改编自真人真事，探讨印度农村家庭普遍没有厕所，妇女必须在野外如厕的现象。女主角担任教师，嫁给男主角后，发现家中没有厕所，和男主角“闹离婚”，还来了一场革命。
         * genres : ["剧情","喜剧"]
         * countries : ["印度"]
         * releaseDates : ["2017-08-11(印度)","2018-06-08(中国大陆)"]
         * writers : [{"name":"格丽玛","id":"1387228","nameEng":"Garima"},{"name":"西达尔特","id":"1338345","nameEng":"Siddharth"}]
         * rating : 7.1
         * releaseStatus : 已上映
         * publishDate : 1528387200
         * languages : ["印地语"]
         * url : https://movie.douban.com/subject/26942645/
         * title : 厕所英雄
         * imageUrls : ["http://img7.doubanio.com/view/photo/l/public/p2524229850.jpg","http://img3.doubanio.com/view/photo/l/public/p2523971818.jpg","http://img3.doubanio.com/view/photo/l/public/p2525295038.jpg","http://img7.doubanio.com/view/photo/l/public/p2525295033.jpg","http://img3.doubanio.com/view/photo/l/public/p2525295026.jpg","http://img7.doubanio.com/view/photo/l/public/p2525144244.jpg","http://img7.doubanio.com/view/photo/l/public/p2524713362.jpg","http://img3.doubanio.com/view/photo/l/public/p2524713359.jpg","http://img3.doubanio.com/view/photo/l/public/p2524608159.jpg","http://img3.doubanio.com/view/photo/l/public/p2524608157.jpg"]
         * publishDateStr : 2018-06-07T16:00:00
         * id : 26942645
         * reviewCount : 328
         * commentCount : 7750
         * titleAliases : ["Toilet - Ek Prem Katha","厕所：一个爱情故事(台)","厕所：一个爱的故事","टॉयलेट: एक प्रेम कथा","Toilet: A Love Story"]
         */

        private int wishCount;
        private int durationMin;
        private int ratingCount;
        private String coverUrl;
        private int favoriteCount;
        private String pressDate;
        private String catName1;
        private String description;
        private double rating;
        private String releaseStatus;
        private int publishDate;
        private String url;
        private String title;
        private String publishDateStr;
        private String id;
        private int reviewCount;
        private int commentCount;
        private List<String> videoUrls;
        private List<String> tags;
        private List<DirectorsBean> directors;
        private List<ActorsBean> actors;
        private List<String> genres;
        private List<String> countries;
        private List<String> releaseDates;
        private List<WritersBean> writers;
        private List<String> languages;
        private List<String> imageUrls;
        private List<String> titleAliases;

        private String getDirectorStr() {
            if (directors == null) {
                return "";
            }
            StringBuffer buffer = new StringBuffer();
            for (DirectorsBean director : directors) {
                if (!TextUtils.isEmpty(buffer)) {
                    buffer.append("/");
                }
                buffer.append(director.name);

            }
            return buffer.toString();
        }

        private String getwriterStr() {
            if (writers == null) {
                return "";
            }
            StringBuffer buffer = new StringBuffer();
            for (WritersBean writersBean : writers) {
                if (!TextUtils.isEmpty(buffer)) {
                    buffer.append("/");
                }
                buffer.append(writersBean.name);

            }
            return buffer.toString();
        }

        private String getActorStr() {
            if (actors == null) {
                return "";
            }
            StringBuffer buffer = new StringBuffer();
            for (ActorsBean actorsBean : actors) {
                if (!TextUtils.isEmpty(buffer)) {
                    buffer.append("/");
                }
                buffer.append(actorsBean.name);

            }
            return buffer.toString();
        }



        public int getWishCount() {
            return wishCount;
        }

        public void setWishCount(int wishCount) {
            this.wishCount = wishCount;
        }

        public int getDurationMin() {
            return durationMin;
        }

        public void setDurationMin(int durationMin) {
            this.durationMin = durationMin;
        }

        public int getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(int ratingCount) {
            this.ratingCount = ratingCount;
        }

        public String getCoverUrl() {
            return coverUrl;
        }

        public void setCoverUrl(String coverUrl) {
            this.coverUrl = coverUrl;
        }

        public int getFavoriteCount() {
            return favoriteCount;
        }

        public void setFavoriteCount(int favoriteCount) {
            this.favoriteCount = favoriteCount;
        }

        public String getPressDate() {
            return pressDate;
        }

        public void setPressDate(String pressDate) {
            this.pressDate = pressDate;
        }

        public String getCatName1() {
            return catName1;
        }

        public void setCatName1(String catName1) {
            this.catName1 = catName1;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public String getReleaseStatus() {
            return releaseStatus;
        }

        public void setReleaseStatus(String releaseStatus) {
            this.releaseStatus = releaseStatus;
        }

        public int getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(int publishDate) {
            this.publishDate = publishDate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPublishDateStr() {
            return publishDateStr;
        }

        public void setPublishDateStr(String publishDateStr) {
            this.publishDateStr = publishDateStr;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getReviewCount() {
            return reviewCount;
        }

        public void setReviewCount(int reviewCount) {
            this.reviewCount = reviewCount;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public List<String> getVideoUrls() {
            return videoUrls;
        }

        public void setVideoUrls(List<String> videoUrls) {
            this.videoUrls = videoUrls;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public List<ActorsBean> getActors() {
            return actors;
        }

        public void setActors(List<ActorsBean> actors) {
            this.actors = actors;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }

        public List<String> getReleaseDates() {
            return releaseDates;
        }

        public void setReleaseDates(List<String> releaseDates) {
            this.releaseDates = releaseDates;
        }

        public List<WritersBean> getWriters() {
            return writers;
        }

        public void setWriters(List<WritersBean> writers) {
            this.writers = writers;
        }

        public List<String> getLanguages() {
            return languages;
        }

        public void setLanguages(List<String> languages) {
            this.languages = languages;
        }

        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        public List<String> getTitleAliases() {
            return titleAliases;
        }

        public void setTitleAliases(List<String> titleAliases) {
            this.titleAliases = titleAliases;
        }

        public static class DirectorsBean {
            /**
             * name : 什里·那拉扬·辛
             * id : 1339168
             * nameEng : ShreeNarayanSingh
             * type : null
             */

            private String name;
            private String id;
            private String nameEng;
            private Object type;

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

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }

            public Object getType() {
                return type;
            }

            public void setType(Object type) {
                this.type = type;
            }
        }

        public static class ActorsBean {
            /**
             * name : 阿克谢·库玛尔
             * id : 1049615
             * nameEng : AkshayKumar
             */

            private String name;
            private String id;
            private String nameEng;

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

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }
        }

        public static class WritersBean {
            /**
             * name : 格丽玛
             * id : 1387228
             * nameEng : Garima
             */

            private String name;
            private String id;
            private String nameEng;

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

            public String getNameEng() {
                return nameEng;
            }

            public void setNameEng(String nameEng) {
                this.nameEng = nameEng;
            }
        }
    }
}