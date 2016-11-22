/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryWeek;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryWeekDao;

/**
 * 周总结Service
 * @author yangruidong
 * @version 2016-11-17
 */
@Service
@Transactional(readOnly = true)
public class OaSummaryWeekService extends CrudService<OaSummaryWeekDao, OaSummaryWeek> {
    @Autowired
    private OaSummaryWeekDao oaSummaryWeekDao;

	public OaSummaryWeek get(String id) {
		return super.get(id);
	}
	
	public List<OaSummaryWeek> findList(OaSummaryWeek oaSummaryWeek) {
		return super.findList(oaSummaryWeek);
	}
	
	public Page<OaSummaryWeek> findPage(Page<OaSummaryWeek> page, OaSummaryWeek oaSummaryWeek) {
		return super.findPage(page, oaSummaryWeek);
	}

    public OaSummaryWeek findByWeek(OaSummaryWeek oaSummaryWeek){
      return  oaSummaryWeekDao.findByWeek(oaSummaryWeek);
    }
	
	@Transactional(readOnly = false)
	public void save(OaSummaryWeek oaSummaryWeek) {
		super.save(oaSummaryWeek);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaSummaryWeek oaSummaryWeek) {
		super.delete(oaSummaryWeek);
	}
	
}