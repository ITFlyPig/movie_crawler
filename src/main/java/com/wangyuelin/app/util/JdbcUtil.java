package com.wangyuelin.app.util;

import org.apache.http.util.TextUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用jdbc的方式执行sql语句，主要是解决mybatis执行创建表的sql不好使的问题
 */
public class JdbcUtil {
    private static String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static String DB_USER = "root";
    private static String DB_PWD = "root";
    private static String DB_URL = "jdbc:mysql://localhost:3306/movie";

    private static Connection connection;

    /**
     * 获取连接
     * @return
     */
    private static Connection getConnection(){
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * 执行sql语句
     * @param sql
     */
    public static boolean excutrSQL(String sql){
        getConnection();

        PreparedStatement statement = null;
        try {
            if (TextUtils.isEmpty(sql) || connection == null || connection.isClosed()){
                return false;
            }

             statement = connection.prepareStatement(sql);
            if (statement != null){
                statement.executeUpdate(sql);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    if (connection != null){
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return false;

    }
}
