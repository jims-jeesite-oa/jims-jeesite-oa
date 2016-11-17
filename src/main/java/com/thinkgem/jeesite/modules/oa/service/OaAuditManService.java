/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.dao.OaAuditManDao;

/**
 * 新闻审核人Service
 * @author lgx
 * @version 2016-11-17
 */
@Service
@Transactional(readOnly = true)
public class OaAuditManService extends CrudService<OaAuditManDao, OaAuditMan> {

	public OaAuditMan get(String id) {
		return super.get(id);
	}
	
	public List<OaAuditMan> findList(OaAuditMan oaAuditMan) {
		return super.findList(oaAuditMan);
	}
	
	public Page<OaAuditMan> findPage(Page<OaAuditMan> page, OaAuditMan oaAuditMan) {
		return super.findPage(page, oaAuditMan);
	}
	
	@Transactional(readOnly = false)
	public void save(OaAuditMan oaAuditMan) {
		super.save(oaAuditMan);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaAuditMan oaAuditMan) {
		super.delete(oaAuditMan);
	}
	
}