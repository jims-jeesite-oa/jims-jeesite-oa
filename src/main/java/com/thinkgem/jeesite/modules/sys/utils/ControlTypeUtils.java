package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.table.dao.OaControlTypeDao;
import com.thinkgem.jeesite.modules.table.entity.OaControlType;

import java.util.List;

/**
 * Created by chenxy on 2016/11/24.
 */
public class ControlTypeUtils {

    private static OaControlTypeDao oaControlTypeDao = SpringContextHolder.getBean(OaControlTypeDao.class);

    /**
     * 获取所有控件类型
     * @return
     */
    public static List<OaControlType> getControlType(){
        OaControlType oaControlType=new OaControlType();
        List<OaControlType> list=oaControlTypeDao.findList(oaControlType);
        if(list==null){
            list= Lists.newArrayList();
        }
        return list;
    }

}
