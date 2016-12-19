/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.service;

import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.form.entity.OaFormMaster;
import com.thinkgem.jeesite.modules.form.dao.OaFormMasterDao;

/**
 * 编辑器设计表单Service
 * @author chenxy
 * @version 2016-11-18
 */
@Service
@Transactional(readOnly = true)
public class OaFormMasterService extends CrudService<OaFormMasterDao, OaFormMaster> {

    @Autowired
    private OaFormMasterDao oaFormMasterDao;
	public OaFormMaster get(String id) {
		return super.get(id);
	}
	
	public List<OaFormMaster> findList(OaFormMaster oaFormMaster) {
		return super.findList(oaFormMaster);
	}
	
	public Page<OaFormMaster> findPage(Page<OaFormMaster> page, OaFormMaster oaFormMaster) {
		return super.findPage(page, oaFormMaster);
	}
	
	@Transactional(readOnly = false)
	public void save(OaFormMaster oaFormMaster) {
		super.save(oaFormMaster);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaFormMaster oaFormMaster) {
		super.delete(oaFormMaster);
	}

    public OaFormMaster findFormContentByTableName(String tableName, String officeId) {
        return oaFormMasterDao.findFormContentByTableName(tableName,officeId);
    }

    public OaFormMaster findByNo(String formNo,String officeId){
        return oaFormMasterDao.findByNo(formNo, officeId);
    }
}