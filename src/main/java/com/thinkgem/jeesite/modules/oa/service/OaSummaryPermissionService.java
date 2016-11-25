/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryPermissionDao;

/**
 * 评阅管理Service
 * @author yangruidong
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class OaSummaryPermissionService extends CrudService<OaSummaryPermissionDao, OaSummaryPermission> {

	public OaSummaryPermission get(String id) {
		return super.get(id);
	}
	
	public List<OaSummaryPermission> findList(OaSummaryPermission oaSummaryPermission) {
		return super.findList(oaSummaryPermission);
	}
	
	public Page<OaSummaryPermission> findPage(Page<OaSummaryPermission> page, OaSummaryPermission oaSummaryPermission) {
		return super.findPage(page, oaSummaryPermission);
	}
	
	@Transactional(readOnly = false)
	public void save(OaSummaryPermission oaSummaryPermission) {
		super.save(oaSummaryPermission);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaSummaryPermission oaSummaryPermission) {
		super.delete(oaSummaryPermission);
	}
	
}