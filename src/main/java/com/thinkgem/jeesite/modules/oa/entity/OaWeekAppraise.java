/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 月评阅Entity
 * @author yangruidong
 * @version 2016-12-22
 */
public class OaWeekAppraise extends DataEntity<OaWeekAppraise> {
	
	private static final long serialVersionUID = 1L;
	private String loginId;		// 当前登录人
	private String evaluateId;		// 被评阅人
	private Date appraiseWeekDate;		// 评阅日期
	private String content;		// 评阅内容
	private String year;		// 年份
	private String week;		// 周数
	private String flag;		// 是否公开    1  公开   0不公开
    private String  name;    //评阅人姓名
	
	public OaWeekAppraise() {
		super();
	}

	public OaWeekAppraise(String id){
		super(id);
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=64, message="当前登录人长度必须介于 0 和 64 之间")
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	@Length(min=0, max=64, message="被评阅人长度必须介于 0 和 64 之间")
	public String getEvaluateId() {
		return evaluateId;
	}

	public void setEvaluateId(String evaluateId) {
		this.evaluateId = evaluateId;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getAppraiseWeekDate() {
		return appraiseWeekDate;
	}

	public void setAppraiseWeekDate(Date appraiseWeekDate) {
		this.appraiseWeekDate = appraiseWeekDate;
	}
	
	@Length(min=0, max=2000, message="评阅内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=20, message="年份长度必须介于 0 和 20 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=20, message="周数长度必须介于 0 和 20 之间")
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	@Length(min=0, max=2, message="是否公开    1  公开   0不公开长度必须介于 0 和 2 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}