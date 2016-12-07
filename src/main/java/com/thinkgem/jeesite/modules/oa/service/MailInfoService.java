package com.thinkgem.jeesite.modules.oa.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.oa.entity.MailInfo;
import com.thinkgem.jeesite.modules.oa.dao.MailInfoDao;

/**
 * 邮件信息Service
 * @author lgx
 * @version 2016-11-24
 */
@Service
@Transactional(readOnly = true)
public class MailInfoService extends CrudService<MailInfoDao, MailInfo> {

    @Autowired
    private MailInfoDao mailInfoDao;

	public MailInfo get(String id) {
		return super.get(id);
	}
	
	public List<MailInfo> findList(MailInfo mailInfo) {
		return super.findList(mailInfo);
	}
	
	public Page<MailInfo> findPage(Page<MailInfo> page, MailInfo mailInfo) {
		return super.findPage(page, mailInfo);
	}

    public Page<User> findPage1(Page<User> page, User user) {
        user.setPage(page);
        page.setList(mailInfoDao.getPhone(user));
        return page;
    }


	@Transactional(readOnly = false)
	public void send(MailInfo mailInfo) {
        if (mailInfo.getContent()!=null){
            mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                    mailInfo.getContent()));
        }
        String receiverStr = mailInfo.getReceiverId() ;
        if(StringUtils.isNotBlank(mailInfo.getCcId())){
            receiverStr += "," + mailInfo.getCcId();
        }
        String[] receivers = receiverStr.split(",");
        for(String receiver : receivers){
            mailInfo.setOwnId(receiver);
            mailInfo.setReadMark("0");
            mailInfo.setState("INBOX");
            mailInfo.setId(null);
            super.save(mailInfo);
        }
        mailInfo.setOwnId(mailInfo.getSenderId());
        mailInfo.setReadMark("1");
        mailInfo.setState("SENT");
        mailInfo.setId(null);
		super.save(mailInfo);
	}

    @Transactional(readOnly = false)
    public void saveDrafts(MailInfo mailInfo) {
        if (mailInfo.getContent()!=null){
            mailInfo.setContent(StringEscapeUtils.unescapeHtml4(
                    mailInfo.getContent()));
        }
        mailInfo.setOwnId(mailInfo.getSenderId());
        mailInfo.setReadMark("1");
        mailInfo.setState("DRAFTS");
        super.save(mailInfo);
    }

    @Transactional(readOnly = false)
    public void delete(MailInfo mailInfo) {
        super.delete(mailInfo);
    }

    /**
     * 修改已读标志
     * @param ids
     * @param readMark
     */
    @Transactional(readOnly = false)
    public void readMark(String ids, String readMark){
        String[] idArr = ids.split(",");
        MailInfo mail = null;
        for(int i = 0; i < idArr.length; i++){
            mail = super.get(idArr[i]);
            mail.setReadMark(readMark);
            super.save(mail);
        }
    }

    @Transactional(readOnly = false)
    public void allRead(String ownId){
        dao.allRead(ownId);
    }

    /**
     * 查看邮件的详细信息
     * @param id
     * @return
     */
    public MailInfo getMail(String id) {
        return mailInfoDao.getMail(id);
    }


}