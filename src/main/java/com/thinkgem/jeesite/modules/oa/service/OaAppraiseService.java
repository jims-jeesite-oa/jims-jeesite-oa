/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaAppraise;
import com.thinkgem.jeesite.modules.oa.dao.OaAppraiseDao;

/**
 * 同事评阅Service
 * @author yangruidong
 * @version 2016-12-21
 */
@Service
@Transactional(readOnly = true)
public class OaAppraiseService extends CrudService<OaAppraiseDao, OaAppraise> {

	public OaAppraise get(String id) {
		return super.get(id);
	}
	
	public List<OaAppraise> findList(OaAppraise oaAppraise) {
		return super.findList(oaAppraise);
	}
	
	public Page<OaAppraise> findPage(Page<OaAppraise> page, OaAppraise oaAppraise) {
		return super.findPage(page, oaAppraise);
	}
	
	@Transactional(readOnly = false)
	public void save(OaAppraise oaAppraise) {
		super.save(oaAppraise);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaAppraise oaAppraise) {
		super.delete(oaAppraise);
	}

    /**
     * 根据被评阅人的id查询
     * @param oaAppraise
     * @return
     */
    public List<OaAppraise> findByEvaluate(OaAppraise oaAppraise){
      return  dao.findByEvaluate(oaAppraise);
    }
	
}