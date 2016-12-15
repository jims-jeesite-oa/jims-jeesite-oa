/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义数据源DAO接口
 * @author chenxy
 * @version 2016-11-24
 */
@MyBatisDao
public interface OaPersonDefineTableDao extends CrudDao<OaPersonDefineTable> {
    /**
     * 根据表名和机构Id查询
     * @param tableName
     * @param officeId
     * @return
     */
    OaPersonDefineTable findByTableName(@Param("tableName")String tableName, @Param("officeId")String officeId);

    /**
     * 执行sql
     * @param sql
     */
    int executeSql(@Param("sql")String sql);
}