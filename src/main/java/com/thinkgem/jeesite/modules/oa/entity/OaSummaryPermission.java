/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 评阅管理Entity
 * @author yangruidong
 * @version 2016-11-22
 */
public class OaSummaryPermission extends DataEntity<OaSummaryPermission> {
	
	private static final long serialVersionUID = 1L;
	private String evaluateId;		// 可评阅用户id
	private String evaluateById;		// 评阅人id


    private String evaluateByNames;    //被评阅人的姓名
    private String evaluateName;     //评阅人姓名

    public String getEvaluateName() {
        return evaluateName;
    }

    public void setEvaluateName(String evaluateName) {
        this.evaluateName = evaluateName;
    }

    public String getEvaluateByNames() {
        return evaluateByNames;
    }

    public void setEvaluateByNames(String evaluateByNames) {
        this.evaluateByNames = evaluateByNames;
    }

    private String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public OaSummaryPermission() {
		super();
	}

	public OaSummaryPermission(String id){
		super(id);
	}

	@Length(min=0, max=64, message="可评阅用户id长度必须介于 0 和 64 之间")
	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	@Length(min=0, max=64, message="评阅人id长度必须介于 0 和 64 之间")
	public String getEvaluateById() {
		return evaluateById;
	}

	public void setEvaluateById(String evaluateById) {
		this.evaluateById = evaluateById;
	}
	
}