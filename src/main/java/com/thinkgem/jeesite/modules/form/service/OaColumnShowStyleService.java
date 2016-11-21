/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.form.entity.OaColumnShowStyle;
import com.thinkgem.jeesite.modules.form.dao.OaColumnShowStyleDao;

/**
 * 字段显示方式设置Service
 * @author chenxy
 * @version 2016-11-18
 */
@Service
@Transactional(readOnly = true)
public class OaColumnShowStyleService extends CrudService<OaColumnShowStyleDao, OaColumnShowStyle> {

	public OaColumnShowStyle get(String id) {
		return super.get(id);
	}
	
	public List<OaColumnShowStyle> findList(OaColumnShowStyle oaColumnShowStyle) {
		return super.findList(oaColumnShowStyle);
	}
	
	public Page<OaColumnShowStyle> findPage(Page<OaColumnShowStyle> page, OaColumnShowStyle oaColumnShowStyle) {
		return super.findPage(page, oaColumnShowStyle);
	}
	
	@Transactional(readOnly = false)
	public void save(OaColumnShowStyle oaColumnShowStyle) {
		super.save(oaColumnShowStyle);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaColumnShowStyle oaColumnShowStyle) {
		super.delete(oaColumnShowStyle);
	}
	
}