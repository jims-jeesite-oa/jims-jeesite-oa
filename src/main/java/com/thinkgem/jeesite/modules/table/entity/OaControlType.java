/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 控件添加Entity
 * @author chenxy
 * @version 2016-11-24
 */
public class OaControlType extends DataEntity<OaControlType> {
	
	private static final long serialVersionUID = 1L;
	private String controlname;		// 控件名称
	private String controlcontent;		// 控件内容
	
	public OaControlType() {
		super();
	}

	public OaControlType(String id){
		super(id);
	}

	@Length(min=0, max=200, message="控件名称长度必须介于 0 和 200 之间")
	public String getControlname() {
		return controlname;
	}

	public void setControlname(String controlname) {
		this.controlname = controlname;
	}
	
	@Length(min=0, max=200, message="控件内容长度必须介于 0 和 200 之间")
	public String getControlcontent() {
		return controlcontent;
	}

	public void setControlcontent(String controlcontent) {
		this.controlcontent = controlcontent;
	}
	
}