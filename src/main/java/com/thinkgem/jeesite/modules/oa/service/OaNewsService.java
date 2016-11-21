/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;
import com.thinkgem.jeesite.modules.oa.dao.OaNewsDao;

/**
 * 新闻公告Service
 * @author lgx
 * @version 2016-11-17
 */
@Service
@Transactional(readOnly = true)
public class OaNewsService extends CrudService<OaNewsDao, OaNews> {

	public OaNews get(String id) {
		return super.get(id);
	}
	
	public List<OaNews> findList(OaNews oaNews) {
		return super.findList(oaNews);
	}
	
	public Page<OaNews> findPage(Page<OaNews> page, OaNews oaNews) {
		return super.findPage(page, oaNews);
	}
	
	@Transactional(readOnly = false)
	public void save(OaNews oaNews) {
        if (oaNews.getContent()!=null){
            oaNews.setContent(StringEscapeUtils.unescapeHtml4(
                    oaNews.getContent()));
        }
		super.save(oaNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(OaNews oaNews) {
		super.delete(oaNews);
	}

}