/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.OaNews;

/**
 * 新闻公告DAO接口
 * @author lgx
 * @version 2016-11-17
 */
@MyBatisDao
public interface OaNewsDao extends CrudDao<OaNews> {

    /**
     * 置顶操作
     * @param id
     * @param type 0不置顶，1置顶
     * @return
     */
    public int toUp(String id,String type);
}