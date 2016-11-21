package com.thinkgem.jeesite.modules.table.vo;

import java.util.HashSet;
import java.util.Set;

/**
 * created by chenxy
 * 封装创建表的信息
 */
public class TableInfo {


    private String tableName;//表名
    private String comment;//表的注释
    private Boolean isMaster;//是否主表
    private Boolean isDetail;//是否从表
    private String  attachMasterName;//所属主表表名
    private Set<ColumnInfo> columnInfos=new HashSet<>();

    public Set<ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Set<ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(Boolean isMaster) {
        this.isMaster = isMaster;
    }

    public Boolean getIsDetail() {
        return isDetail;
    }

    public void setIsDetail(Boolean isDetail) {
        this.isDetail = isDetail;
    }

    public String getAttachMasterName() {
        return attachMasterName;
    }

    public void setAttachMasterName(String attachMasterName) {
        this.attachMasterName = attachMasterName;
    }
}
