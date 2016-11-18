/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.form.entity.OaColumnShowStyle;

/**
 * 字段显示方式设置DAO接口
 * @author chenxy
 * @version 2016-11-18
 */
@MyBatisDao
public interface OaColumnShowStyleDao extends CrudDao<OaColumnShowStyle> {
	
}