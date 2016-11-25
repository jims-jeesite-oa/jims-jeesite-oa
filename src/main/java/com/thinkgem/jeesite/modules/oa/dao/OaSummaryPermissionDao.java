/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;

/**
 * 评阅管理DAO接口
 * @author yangruidong
 * @version 2016-11-22
 */
@MyBatisDao
public interface OaSummaryPermissionDao extends CrudDao<OaSummaryPermission> {
	
}