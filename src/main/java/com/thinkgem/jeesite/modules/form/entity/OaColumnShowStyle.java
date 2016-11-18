/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 字段显示方式设置Entity
 * @author chenxy
 * @version 2016-11-18
 */
public class OaColumnShowStyle extends DataEntity<OaColumnShowStyle> {
	
	private static final long serialVersionUID = 1L;
	private String officeId;		// 所属机构ID
	private String isCommon;		// 是否是通用
	private String tableName;		// 表名
	private String formName;		// 表单名
	private String columnName;		// 字段名
	private String columnType;		// 字段类型
	private String showType;		// 显示方式
	
	public OaColumnShowStyle() {
		super();
	}

	public OaColumnShowStyle(String id){
		super(id);
	}

	@Length(min=0, max=200, message="所属机构ID长度必须介于 0 和 200 之间")
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}
	
	@Length(min=0, max=1, message="是否是通用长度必须介于 0 和 1 之间")
	public String getIsCommon() {
		return isCommon;
	}

	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}
	
	@Length(min=0, max=50, message="表名长度必须介于 0 和 50 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=0, max=50, message="表单名长度必须介于 0 和 50 之间")
	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	@Length(min=0, max=200, message="字段名长度必须介于 0 和 200 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	@Length(min=0, max=20, message="字段类型长度必须介于 0 和 20 之间")
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	@Length(min=0, max=30, message="显示方式长度必须介于 0 和 30 之间")
	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}
	
}