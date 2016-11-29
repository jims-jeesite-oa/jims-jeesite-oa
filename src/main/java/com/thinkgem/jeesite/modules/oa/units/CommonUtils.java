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
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryPermissionDao;
import com.thinkgem.jeesite.modules.oa.entity.OaAuditMan;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryDay;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CommonUtils {

	private static OaAuditManDao oaAuditManDao = SpringContextHolder.getBean(OaAuditManDao.class);
	private static OaSummaryPermissionDao oaSummaryPermissionDao = SpringContextHolder.getBean(OaSummaryPermissionDao.class);
	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);

	public static final String CACHE_AUDIT_MAN_List = "oaAuditManAllList";
	public static final String CACHE_ALL_PERMISSION_List = "oaAllPermissionList";

	public static List<OaAuditMan> getAuditManAllList(){
		List<OaAuditMan> manList = (List<OaAuditMan>)CacheUtils.get(CACHE_AUDIT_MAN_List);
		if (manList==null){
            manList = oaAuditManDao.findAllList(new OaAuditMan());
		}
		return manList;
	}

    /**
     *查询被评阅人
     * @return
     */
    public static List<User> getAllPermission(){
        List<User> manList = (List<User>)CacheUtils.get(CACHE_ALL_PERMISSION_List);
        User user= UserUtils.getUser();
        if (manList==null){
            manList=new ArrayList<>();
            String loginId=user.getId();
           List<OaSummaryPermission> list= oaSummaryPermissionDao.findListByLoginId(loginId);
           for(int i=0;i<list.size();i++){
               User u=userDao.get(list.get(i).getEvaluateId());
               manList.add(u);
           }
        }
        return manList;
    }

}
