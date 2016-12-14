/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.table.entity.OaControlType;
import com.thinkgem.jeesite.modules.table.dao.OaControlTypeDao;

/**
 * 控件添加Service
 * @author chenxy
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class OaControlTypeService extends CrudService<OaControlTypeDao, OaControlType> {

	public OaControlType get(String id) {
		return super.get(id);
	}
	
	public List<OaControlType> findList(OaControlType oaControlType) {
		return super.findList(oaControlType);
	}
	
	public Page<OaControlType> findPage(Page<OaControlType> page, OaControlType oaControlType) {
		return super.findPage(page, oaControlType);
	}
	
	@Transactional(readOnly = false)
	public void save(OaControlType oaControlType) {
		super.save(oaControlType);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaControlType oaControlType) {
		super.delete(oaControlType);
	}
	
}