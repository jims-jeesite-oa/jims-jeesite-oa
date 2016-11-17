/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 新闻审核人Entity
 * @author lgx
 * @version 2016-11-17
 */
public class OaAuditMan extends DataEntity<OaAuditMan> {
	
	private static final long serialVersionUID = 1L;
	private String auditId;		// 审核人ID
	private String auditMan;		// 审核人姓名
	private String auditJob;		// 审核人职位
	
	public OaAuditMan() {
		super();
	}

	public OaAuditMan(String id){
		super(id);
	}

	@Length(min=0, max=64, message="审核人ID长度必须介于 0 和 64 之间")
	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}
	
	@Length(min=0, max=40, message="审核人姓名长度必须介于 0 和 40 之间")
	public String getAuditMan() {
		return auditMan;
	}

	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}
	
	@Length(min=0, max=80, message="审核人职位长度必须介于 0 和 80 之间")
	public String getAuditJob() {
		return auditJob;
	}

	public void setAuditJob(String auditJob) {
		this.auditJob = auditJob;
	}
	
}