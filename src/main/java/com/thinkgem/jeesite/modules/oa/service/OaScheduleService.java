/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaSchedule;
import com.thinkgem.jeesite.modules.oa.dao.OaScheduleDao;

/**
 * 保存日程安排Service
 * @author yangruidong
 * @version 2016-11-15
 */
@Service
@Transactional(readOnly = true)
public class OaScheduleService extends CrudService<OaScheduleDao, OaSchedule> {

	public OaSchedule get(String id) {
		return super.get(id);
	}
	
	public List<OaSchedule> findList(OaSchedule oaSchedule) {
		return super.findList(oaSchedule);
	}
	
	public Page<OaSchedule> findPage(Page<OaSchedule> page, OaSchedule oaSchedule) {
		return super.findPage(page, oaSchedule);
	}

    public Page<OaSchedule> find(Page<OaSchedule> page, OaSchedule oaSchedule) {
        oaSchedule.setPage(page);
        page.setList(dao.findList(oaSchedule));
        return page;
    }
    @Transactional(readOnly = false)
    public int complete(OaSchedule oaSchedule) {
        oaSchedule.setFlag("1");
        return dao.update(oaSchedule);
    }
	
	@Transactional(readOnly = false)
	public void save(OaSchedule oaSchedule) {
		super.save(oaSchedule);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaSchedule oaSchedule) {
		super.delete(oaSchedule);
	}
	
}