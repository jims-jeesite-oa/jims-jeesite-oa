package com.thinkgem.jeesite.modules.table.utils;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * created by chenxy
 * 获取数据库信息
 */
public class JdbcUtils {

    public static String JDBC_DRIVER = "";
    public static String USERNAME = "";
    public static String PASSWORD = "";
    public static String URL = "";
    public static ResourceBundle resourceBundle = ResourceBundle.getBundle("jims");

    public static Connection connection=null;

    public static void init(){
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
        if(URL == null) init();
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

    /**
     * 自定义表
     * @param t
     * @return
     */
    public static boolean createTable(OaPersonDefineTable t){
         StringBuilder sb=null;
         sb = new StringBuilder
         ("create table "+t.getTableName()+"" +
                "( id national varchar(64) not null comment '编号',");
         List<OaPersonDefineTableColumn> list=t.getOaPersonDefineTableColumnList();
         for (int i=0;i<list.size();i++){
             String columnStr="";
             columnStr=list.get(i).getColumnName()+"  "+list.get(i).getColumnType();
             if(list.get(i).getTableStatus()!=null&&!"".equals(list.get(i).getTableStatus())){
                 columnStr+=" ("+list.get(i).getTableStatus()+") ";
             }
             if("1".equals(list.get(i).getIsRequired())){
                 columnStr+="  not null ";
             }
             columnStr+=" comment '"+list.get(i).getColumnComment()+"',";
              sb.append(columnStr);
        }
        sb.append("primary key (id) )"+" COMMENT='"+t.getTableComment()+"'");
        String sql=sb.toString();
        System.out.println("创建表的Sql语句位:"+sql);
        connection=getConnection();
        Statement statement=null;
        try {
            statement=connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(statement!=null){
                    statement.close();
                }
                if(connection!=null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static Map<String,Object> getSql(OaPersonDefineTable table){
        List<String> comments = new ArrayList<>();
        StringBuilder sql = new StringBuilder("create table "+table.getTableName() +
                        "( id varchar(64) not null primary key,");
        List<OaPersonDefineTableColumn> columns = table.getOaPersonDefineTableColumnList();
        if(columns != null && columns.size() > 0) {
            for(OaPersonDefineTableColumn column : columns) {
                sql.append(getColumnInfo(column));
                sql.append("1".equals(column.getIsRequired()) ? " not null," : ",");
                if(StringUtils.isNotBlank(column.getColumnComment())){
                    comments.add("comment on column " + table.getTableName() + "." + column.getColumnName()
                            + " is '" + column.getColumnComment() + "'");
                }
            }
        }
        sql.append("create_by VARCHAR2(64) not null,create_date DATE not null,")
                .append("update_by VARCHAR2(64) not null,update_date DATE not null,")
                .append("remarks VARCHAR2(255),del_flag CHAR(1) default '0' not null,")
                .append("proc_ins_id VARCHAR2(64),proc_def_id VARCHAR2(150) not null)");
        if(StringUtils.isNotBlank(table.getTableComment())){
            comments.add("comment on table " + table.getTableName()
                    + " is '" + table.getTableComment() + "'");
        }
        Map<String,Object> result = new HashMap<>();
        result.put("tableSql",sql.toString());
        result.put("comment",comments);
        return result;
    }

    public static String getColumnInfo(OaPersonDefineTableColumn column) {
        if("REMARK".equalsIgnoreCase(column.getColumnType())) {
            column.setColumnType("VARCHAR2");
        }
        return column.getColumnName() + " " + column.getColumnType() +
                ("VARCHAR2".equalsIgnoreCase(column.getColumnType()) ? "(" + column.getTableStatus() + ")" : "");
    }
}


