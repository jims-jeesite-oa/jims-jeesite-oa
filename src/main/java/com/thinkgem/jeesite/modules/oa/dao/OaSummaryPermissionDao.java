/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评阅管理DAO接口
 * @author yangruidong
 * @version 2016-11-22
 */
@MyBatisDao
public interface OaSummaryPermissionDao extends CrudDao<OaSummaryPermission> {


    /**
     *根据当前登录人的id查询  能够评阅的人
     * @param loginId
     * @return
     */
    public List<OaSummaryPermission> findListByLoginId(@Param("loginId")String loginId);

    /**
     * 回显评阅人
     * @param
     * @return
     */
    public List<OaSummaryPermission> findById(@Param("evaluateById")String evaluateById);

}