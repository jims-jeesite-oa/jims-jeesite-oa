package com.thinkgem.jeesite.modules.form.utils;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * created by chenxy
 *
 * 获取数据库信息
 *
 */
public class JdbcUtils {

    public static String JDBC_DRIVER = "";
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String URL = "";
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("jims");

    public static Connection connection=null;
    static {
        JDBC_DRIVER = resourceBundle.getString("jdbc.driver");
        USERNAME = resourceBundle.getString("jdbc.username");
        PASSWORD = resourceBundle.getString("jdbc.password");
        URL = resourceBundle.getString("jdbc.url");
    }

    /**
     * 获取数据库的链接
     * @return
     */
    public static Connection getConnection(){
        try {
            Class.forName(JDBC_DRIVER);
            connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
         } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
     }





}


