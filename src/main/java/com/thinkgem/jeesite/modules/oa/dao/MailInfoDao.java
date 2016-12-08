/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 邮件信息DAO接口
 * @author lgx
 * @version 2016-11-24
 */
@MyBatisDao
public interface MailInfoDao extends CrudDao<MailInfo> {

    /**
     * 所有邮件标记为已读
     * @param ownId
     */
    public void allRead(String ownId);


    /**
     * 查看邮件的详细信息
     * @param id
     * @return
     */
    public MailInfo getMail(String id) ;

    /**
     * 查看联系人
     * @param
     * @return
     */
    public List<User> getPhone(User user) ;


    /**
     * 查询用户信息
     * @param id
     * @return
     */
    public User getById(String id);


}