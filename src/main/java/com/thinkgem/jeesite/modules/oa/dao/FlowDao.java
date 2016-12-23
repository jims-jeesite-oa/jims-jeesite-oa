package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.FlowData;

import java.util.Map;

/**
 * 流程dao
 * @author lgx
 * @version 2016-12-19
 */
@MyBatisDao
public interface FlowDao extends CrudDao<FlowData> {

}
