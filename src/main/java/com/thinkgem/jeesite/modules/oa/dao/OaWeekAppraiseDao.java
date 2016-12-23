/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaWeekAppraise;

import java.util.List;

/**
 * 月评阅DAO接口
 * @author yangruidong
 * @version 2016-12-22
 */
@MyBatisDao
public interface OaWeekAppraiseDao extends CrudDao<OaWeekAppraise> {

    public List<OaWeekAppraise> findByEvaluate(OaWeekAppraise oaWeekAppraise);
}