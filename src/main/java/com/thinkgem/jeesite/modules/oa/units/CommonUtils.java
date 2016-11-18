/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.units;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.oa.dao.OaAuditManDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CommonUtils {

	private static OaAuditManDao oaAuditManDao = SpringContextHolder.getBean(OaAuditManDao.class);

	public static final String CACHE_AUDIT_MAN_List = "oaAuditManAllList";

	public static List<OaAuditMan> getAuditManAllList(){
		List<OaAuditMan> manList = (List<OaAuditMan>)CacheUtils.get(CACHE_AUDIT_MAN_List);
		if (manList==null){
            manList = oaAuditManDao.findAllList(new OaAuditMan());
		}
		return manList;
	}

}
