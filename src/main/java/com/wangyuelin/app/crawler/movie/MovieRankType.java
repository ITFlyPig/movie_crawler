package com.wangyuelin.app.crawler.movie;

/**
 * 电影排行的分类
 */
public enum MovieRankType {
    RECOMAND(1, "今日推荐"),
    UPDATE(2, "电影最新更新"),
    MONTH_RANK(3, "电影周排行榜"),
    PRAISE_RANK(4, "超赞排行榜"),
    NEW_TV(5, "最新电视剧"),
    RANK_TV(6, "电视剧周排行榜"),
    NEW_CARTOON(7, "最新动漫"),
    RANK_CARTOON(8, "动漫周排行榜"),
    NEW_VAR(9, "最新综艺"),
    RANK_VAR(10, "综艺周排行榜"),
    HOT_RANK(11, "最热排行榜"),
    UP_TO_DATE(12, "最新更新"),
    BEN_YUE_RANK(13,"本月排行");



    private int rankType;
    private String rankTypeStr;

    MovieRankType(int rankType, String rankTypeStr) {
        this.rankType = rankType;
        this.rankTypeStr = rankTypeStr;
    }

    public int getRankType() {
        return rankType;
    }

    public String getRankTypeStr() {
        return rankTypeStr;
    }
}
