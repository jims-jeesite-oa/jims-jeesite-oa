/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.units;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
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

import java.util.*;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CommonUtils {

	private static OaAuditManDao oaAuditManDao = SpringContextHolder.getBean(OaAuditManDao.class);
	private static OaSummaryPermissionDao oaSummaryPermissionDao = SpringContextHolder.getBean(OaSummaryPermissionDao.class);
	private static MailInfoDao mailInfoDao = SpringContextHolder.getBean(MailInfoDao.class);
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

    /**
     *查询公司所有的人
     * @return
     */
    public static List<User> getPhone(){
        List<User> manList = (List<User>)CacheUtils.get(CACHE_ALL_PERMISSION_List);
        if (manList==null){
            manList=new ArrayList<>();
            List<User> list= mailInfoDao.getPhone(new User());
            for(int i=0;i<list.size();i++){
                User u=list.get(i);
                manList.add(u);
            }
        }
        return manList;
    }


    public static Map<String,Object> mapConvert(Map map) {
        Map<String,Object> dataMap= new HashMap<String, Object>(0);
        if(map!=null){
            Iterator it=map.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry entry=(Map.Entry)it.next();
                Object ok=entry.getKey();
                Object ov=entry.getValue()==null?"":entry.getValue();
                String key=ok.toString();
                String keyval="";
                String[] value=new String[1];
                if(ov instanceof String[]){
                    value=(String[])ov;
                }else{
                    value[0]=ov.toString();
                }
                keyval+=value[0];
                for(int k=1;k<value.length;k++){
                    keyval+=","+value[k];
                }
                dataMap.put(key, keyval);
            }
        }
        return dataMap;
    }

    public static Map attributeMapFilter(Map map, String[] filterName) {
        for (int i = 0; i < filterName.length; i++) {
            if (map.containsKey(filterName[i])) map.remove(filterName[i]);
        }
        return map;
    }

}
