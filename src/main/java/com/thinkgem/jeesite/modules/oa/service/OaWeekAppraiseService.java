/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaWeekAppraise;
import com.thinkgem.jeesite.modules.oa.dao.OaWeekAppraiseDao;

/**
 * 月评阅Service
 * @author yangruidong
 * @version 2016-12-22
 */
@Service
@Transactional(readOnly = true)
public class OaWeekAppraiseService extends CrudService<OaWeekAppraiseDao, OaWeekAppraise> {

	public OaWeekAppraise get(String id) {
		return super.get(id);
	}
	
	public List<OaWeekAppraise> findList(OaWeekAppraise oaWeekAppraise) {
		return super.findList(oaWeekAppraise);
	}
	
	public Page<OaWeekAppraise> findPage(Page<OaWeekAppraise> page, OaWeekAppraise oaWeekAppraise) {
		return super.findPage(page, oaWeekAppraise);
	}
	
	@Transactional(readOnly = false)
	public void save(OaWeekAppraise oaWeekAppraise) {
		super.save(oaWeekAppraise);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaWeekAppraise oaWeekAppraise) {
		super.delete(oaWeekAppraise);
	}

    public List<OaWeekAppraise> findByEvaluate(OaWeekAppraise oaWeekAppraise){
        return dao.findByEvaluate(oaWeekAppraise);
    }
	
}