package com.thinkgem.jeesite.modules.table.utils;

import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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

    /**
     * 自定义表
     * @param oaPersonDefineTable
     * @return
     */

    /**
     * create table oa_person_define_table
     (
     id                   national varchar(64) not null comment '编号',
     office_id            national varchar(200) comment 'sys_office 表的主键',
     table_name           national varchar(200) comment '表名',
     table_comment        national varchar(200) comment '注释',
     table_property       national varchar(10) comment '属性',
     table_status         national varchar(10) comment '状态',
     is_master             boolean comment '是否主表',
     is_detail             boolean  comment '是否从表',
     create_by            national varchar(64) not null comment '创建者',
     create_date          datetime not null comment '创建时间',
     update_by            national varchar(64) not null comment '更新者',
     update_date          datetime not null comment '更新时间',
     remarks              national varchar(255) comment '备注信息',
     del_flag             national char(1) not null default '0' comment '删除标记',
     primary key (id)
     );
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


}


