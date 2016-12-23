/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAppraise;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 同事评阅DAO接口
 * @author yangruidong
 * @version 2016-12-21
 */
@MyBatisDao
public interface OaAppraiseDao extends CrudDao<OaAppraise> {


    public List<OaAppraise> findByEvaluate(OaAppraise oaAppraise);
}