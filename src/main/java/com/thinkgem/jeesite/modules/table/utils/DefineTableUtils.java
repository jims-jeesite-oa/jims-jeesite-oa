package com.thinkgem.jeesite.modules.table.utils;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;

/**
 * 定义表工具类
 * @author lgx
 * @version 2016-12-22
 */
public class DefineTableUtils {

    private static OaPersonDefineTableDao oaPersonDefineTableDao = SpringContextHolder.getBean(OaPersonDefineTableDao.class);

    //根据表明获取自定义表
    public static OaPersonDefineTable findByTableName(String tableName) {
        return oaPersonDefineTableDao.findByTableName(tableName,null);
    }
}
