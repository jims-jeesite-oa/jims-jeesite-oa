/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 工作日志Entity
 * @author yangruidong
 * @version 2016-11-16
 */
public class OaSummaryDay extends DataEntity<OaSummaryDay> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 总结
	private Date sumDate;		// 总结日期
	private String evaluate;		// 评阅内容
	private String evaluateMan;		// 评阅人
	private String evaluateManId;		// 评阅人id
    private String loginId;    //登录人id

    private String flag;    //是否公开
    private String evaluateContent;

    private String dd;

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getEvaluateContent() {
        return evaluateContent;
    }

    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    public OaSummaryDay(List<OaSchedule> oaScheduleList,Date sumDate) {
        this.oaScheduleList = oaScheduleList;
        this.sumDate=sumDate;
    }
    public OaSummaryDay(List<OaSchedule> oaScheduleList,Date sumDate,String loginId) {
        this.oaScheduleList = oaScheduleList;
        this.sumDate=sumDate;
        this.loginId=loginId;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private List<OaSchedule> oaScheduleList;      //完成的任务

    public List<OaSchedule> getOaScheduleList() {
        return oaScheduleList;
    }

    public void setOaScheduleList(List<OaSchedule> oaScheduleList) {
        this.oaScheduleList = oaScheduleList;
    }

    public OaSummaryDay() {
		super();
	}

	public OaSummaryDay(String id){
		super(id);
	}

	@Length(min=0, max=2000, message="总结长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSumDate() {
		return sumDate;
	}

	public void setSumDate(Date sumDate) {
		this.sumDate = sumDate;
	}
	
	@Length(min=0, max=2000, message="评阅内容长度必须介于 0 和 2000 之间")
	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	
	@Length(min=0, max=40, message="评阅人长度必须介于 0 和 40 之间")
	public String getEvaluateMan() {
		return evaluateMan;
	}

	public void setEvaluateMan(String evaluateMan) {
		this.evaluateMan = evaluateMan;
	}
	
	@Length(min=0, max=64, message="评阅人id长度必须介于 0 和 64 之间")
	public String getEvaluateManId() {
		return evaluateManId;
	}

	public void setEvaluateManId(String evaluateManId) {
		this.evaluateManId = evaluateManId;
	}
	
}