/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 保存日程安排Entity
 * @author yangruidong
 * @version 2016-11-15
 */
public class OaSchedule extends DataEntity<OaSchedule> {
	
	private static final long serialVersionUID = 1L;
	private String content;		// 日志内容
	private String importantLevel;		// 重要等级(0不重要，1重要)
	private String emergencyLevel;		// 缓急程度（0不紧急，1紧急）
	private Date scheduleDate;		// 日志日期
	private String flag;		// 完成状态（0未完成，1完成）

    private boolean isSelf;		// 是否只查询自己的日程

	public OaSchedule() {
		super();
	}

	public OaSchedule(String id){
		super(id);
	}

	@Length(min=0, max=2000, message="日志内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=1, message="重要等级(0不重要，1重要)长度必须介于 0 和 1 之间")
	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}
	
	@Length(min=0, max=1, message="缓急程度（0不紧急，1紧急）长度必须介于 0 和 1 之间")
	public String getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(String emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = scheduleDate;
	}
	
	@Length(min=0, max=1, message="完成状态（0未完成，1完成）长度必须介于 0 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean isSelf) {
        this.isSelf = isSelf;
    }
	
}