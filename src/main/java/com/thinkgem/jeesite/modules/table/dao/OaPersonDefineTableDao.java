/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;

/**
 * 自定义数据源DAO接口
 * @author chenxy
 * @version 2016-11-17
 */
@MyBatisDao
public interface OaPersonDefineTableDao extends CrudDao<OaPersonDefineTable> {
	
}