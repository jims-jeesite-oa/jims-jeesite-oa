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
 * 周总结Entity
 * @author yangruidong
 * @version 2016-11-17
 */
public class OaSummaryWeek extends DataEntity<OaSummaryWeek> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 本周总结
	private String year;		// 年份
	private String week;		// 周数
	private String nextPlanContent;		// 下周工作计划综述
	private Date sumDate;		// 总结日期
	private String nextPlanTitle;		// 下周工作计划
	private String evaluate;		// 评阅内容
	private String evaluateMan;		// 评阅人
	private String evaluateManId;		// 评阅人id
    private String loginId;   //登录人id

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private Integer weekOfYear;    //当前日期是第几周
    private Integer flag;    //判断当前是上一周还是下一周
    private List<OaVo> oaVos;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    private List<OaSchedule> oaSchedules;

    public List<OaSchedule> getOaSchedules() {
        return oaSchedules;
    }

    public void setOaSchedules(List<OaSchedule> oaSchedules) {
        this.oaSchedules = oaSchedules;
    }

    public List<OaVo> getOaVos() {
        return oaVos;
    }

    public void setOaVos(List<OaVo> oaVos) {
        this.oaVos = oaVos;
    }

    public OaSummaryWeek() {
		super();
	}

	public OaSummaryWeek(String id){
		super(id);
	}

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    @Length(min=0, max=2000, message="本周总结长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=11, message="年份长度必须介于 0 和 11 之间")
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	@Length(min=0, max=11, message="周数长度必须介于 0 和 11 之间")
	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	@Length(min=0, max=2000, message="下周工作计划综述长度必须介于 0 和 2000 之间")
	public String getNextPlanContent() {
		return nextPlanContent;
	}

	public void setNextPlanContent(String nextPlanContent) {
		this.nextPlanContent = nextPlanContent;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getSumDate() {
		return sumDate;
	}

	public void setSumDate(Date sumDate) {
		this.sumDate = sumDate;
	}
	
	@Length(min=0, max=1000, message="下周工作计划长度必须介于 0 和 1000 之间")
	public String getNextPlanTitle() {
		return nextPlanTitle;
	}

	public void setNextPlanTitle(String nextPlanTitle) {
		this.nextPlanTitle = nextPlanTitle;
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