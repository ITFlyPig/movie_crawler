package com.wangyuelin.app.crawler.dy2018;

import org.apache.http.util.TextUtils;

public class TableUtil {
    public static String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `%s` (\n" +
            "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(500) DEFAULT '',\n" +
            "  `time` varchar(500) DEFAULT '',\n"+
            "  `movie_name` varchar(500) DEFAULT '',\n"+
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

    /**
     * 影片的所有分类
     */
    public enum Table{
        DONGZUOPIAN("dognzuo","动作片", 1),
        JUQINGPIAN("juqing","剧情片", 2),
        AIQINGPIAN("aiqing","爱情片", 3),
        XUJUPIAN("xuju", "喜剧片", 4),
        KEHUANPIAN("kehuan", "科幻片", 5),
        KONGBUPIAN("kongbu", "恐怖片", 6),
        DONGHUAPIAN("donghua", "动画片", 7),
        JINGSONGPIAN("jingsong", "惊悚片", 8),
        ZHANZHENPIAN("zhangzheng", "战争片", 9),
        FANZUIPIAN("fanzui", "犯罪片", 10),
        JINGPIN("jingpin", "精品", 11),
        BIKAN("bikan", "必看", 12),
        XUNLEIZIYUAN("xunleidianying", "迅雷电影", 13),
        JINGDAINDAPAIN("jingdian", "经典大片", 14);

        Table(String enName, String name, int index) {
            this.name = name;
            this.index = index;
            this.enName = enName;
        }

        private String name;
        private int index;
        private String enName;

        public String getName() {
            return name;
        }

        public int getIndex() {
            return index;
        }

        public String getEnName() {
            return enName;
        }
    }

    /**
     * 创建表的语句
     * @param table
     * @return
     */
    public static String createTableStr(Table table){
        if (table == null){
            return null;
        }

       return String.format(CREATE_TABLE, table.enName);

    }

    /**
     * 获取title对应的表
     * @param title
     * @return
     */
    public static Table getMovieTable(String title){
        if (TextUtils.isEmpty(title)){
            return null;
        }
        Table[] tables = Table.values();
        if (tables == null){
            return null;
        }
        for (Table table : tables) {
            if (title.contains(table.name)){
                return table;
            }
        }
        return null;
    }

    /**
     * 据索引获得对应的名称
     * @param tableIndex
     * @return
     */
    public static String getTableName(int tableIndex) {
        Table[] tables = Table.values();
        if (tables == null){
            return null;
        }
        for (Table table : tables) {
            if (table.getIndex() == tableIndex) {}
            return table.getEnName();
        }
        return null;
    }
}
