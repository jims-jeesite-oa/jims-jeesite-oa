/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;

/**
 * 同事评阅Entity
 * @author yangruidong
 * @version 2016-12-21
 */
public class OaAppraise extends DataEntity<OaAppraise> {
	
	private static final long serialVersionUID = 1L;
	private String loginId;		// 当前登录人
	private String evaluateId;		// 被评阅人
	private String content;		// 评阅内容
	private String flag;		// 是否公开    1 公开    0不公开
    private String name;   //评阅人的姓名
    private String evaluateById ;   //评阅人id

    private Date appraiseDate;
	
	public OaAppraise() {
		super();
	}

	public OaAppraise(String id){
		super(id);
	}

    public Date getAppraiseDate() {
        return appraiseDate;
    }

    public void setAppraiseDate(Date appraiseDate) {
        this.appraiseDate = appraiseDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluateById() {
        return evaluateById;
    }

    public void setEvaluateById(String evaluateById) {
        this.evaluateById = evaluateById;
    }
}