/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.OaSummaryPermission;
import com.thinkgem.jeesite.modules.oa.dao.OaSummaryPermissionDao;

/**
 * 评阅管理Service
 *
 * @author yangruidong
 * @version 2016-11-22
 */
@Service
@Transactional(readOnly = true)
public class OaSummaryPermissionService extends CrudService<OaSummaryPermissionDao, OaSummaryPermission> {

    @Autowired
    private MailInfoDao mailInfoDao;

    public OaSummaryPermission get(String id) {
        return super.get(id);
    }

    public List<OaSummaryPermission> findList(OaSummaryPermission oaSummaryPermission) {
        return super.findList(oaSummaryPermission);
    }

    public Page<OaSummaryPermission> findPage(Page<OaSummaryPermission> page, OaSummaryPermission oaSummaryPermission) {
        return super.findPage(page, oaSummaryPermission);
    }

    @Transactional(readOnly = false)
    public void save(OaSummaryPermission oaSummaryPermission) {
        super.save(oaSummaryPermission);
    }

    @Transactional(readOnly = false)
    public void delete(OaSummaryPermission oaSummaryPermission) {
        super.delete(oaSummaryPermission);
    }

    /**
     * 根据评阅人   查询被评阅人   回显数据
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public OaSummaryPermission findById(String id) {
        OaSummaryPermission oaSummaryPermission = new OaSummaryPermission();
        StringBuilder sb = new StringBuilder();
        StringBuilder cc = new StringBuilder();
        User user1;
        List<OaSummaryPermission> list = dao.findById(id);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                User user = mailInfoDao.getById(list.get(i).getEvaluateId());
                sb.append(user.getName() + ",");
                cc.append(list.get(i).getEvaluateId() + ",");
            }
            user1 = mailInfoDao.getById(list.get(0).getEvaluateById());
            String receiverName = sb.toString().substring(0, sb.toString().length() - 1);
            String ccId = cc.toString().substring(0, cc.toString().length() - 1);
            oaSummaryPermission.setEvaluateByNames(receiverName);
            oaSummaryPermission.setEvaluateId(ccId);
            oaSummaryPermission.setEvaluateName(user1.getName());
        }  else{
            user1 = mailInfoDao.getById(id);
            oaSummaryPermission.setEvaluateName(user1.getName());
        }
        return oaSummaryPermission;
    }


}