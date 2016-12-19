/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTableColumn;

import java.util.List;

/**
 * 自定义数据源DAO接口
 * @author chenxy
 * @version 2016-11-24
 */
@MyBatisDao
public interface OaPersonDefineTableColumnDao extends CrudDao<OaPersonDefineTableColumn> {

    List<OaPersonDefineTableColumn> findColumnListByTableId(String tableId);

    /**
     * 获取最大的列索引
     * @return
     */
    String getMaxColumnIndex();
}