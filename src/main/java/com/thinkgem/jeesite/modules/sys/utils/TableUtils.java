package com.thinkgem.jeesite.modules.sys.utils;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.Office;
import com.thinkgem.jeesite.modules.table.dao.OaPersonDefineTableDao;
import com.thinkgem.jeesite.modules.table.entity.OaPersonDefineTable;

import java.util.List;

/**
 * 数据表工具类
 * @author lgx
 * @version 2016-12-16
 */
public class TableUtils {

    private static OaPersonDefineTableDao oaPersonDefineTableDao = SpringContextHolder.getBean(OaPersonDefineTableDao.class);

    /**
     * 获取自定义表
     * @return
     */
    public static List<OaPersonDefineTable> getSelfTable(){
        OaPersonDefineTable table = new OaPersonDefineTable();
//        table.setOffice(new Office());
//        table.getOffice().setId(UserUtils.getUser().getOffice().getId());
        List<OaPersonDefineTable> tables = oaPersonDefineTableDao.findList(table);
        if(tables == null){
            tables = Lists.newArrayList();
        }
        return tables;
    }
}
