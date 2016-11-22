/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryWeek;

/**
 * 周总结DAO接口
 * @author yangruidong
 * @version 2016-11-17
 */
@MyBatisDao
public interface OaSummaryWeekDao extends CrudDao<OaSummaryWeek> {
	public OaSummaryWeek findByWeek(OaSummaryWeek oaSummaryWeek);
}