/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;

/**
 * 编辑器设计表单DAO接口
 * @author chenxy
 * @version 2016-11-18
 */
@MyBatisDao
public interface OaFormMasterDao extends CrudDao<OaFormMaster> {
    OaFormMaster findFormContentByTableName(String tableName, String officeId);

    OaFormMaster findByNo(String formNo,String officeId);
}