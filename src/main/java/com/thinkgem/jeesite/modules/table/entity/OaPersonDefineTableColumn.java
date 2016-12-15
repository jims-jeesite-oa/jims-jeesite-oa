/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自定义数据源Entity
 * @author chenxy
 * @version 2016-11-24
 */
public class OaPersonDefineTableColumn extends DataEntity<OaPersonDefineTableColumn> {
	
	private static final long serialVersionUID = 1L;
	private OaPersonDefineTable table;		// 主表ID 父类
	private String columnName;		// 列名
	private String columnComment;		// 注释
	private String columnType;		// 列的类型
	private String tableStatus;		// 列的长度
	private String isRequired;		// 是否必填
	private String isShow;		// 是否显示到列表
	private String isProcess;		// 是否流程变量
	private String controlTypeId;		// 控件类型Id
	
	public OaPersonDefineTableColumn() {
		super();
	}

	public OaPersonDefineTableColumn(String id){
		super(id);
	}

	public OaPersonDefineTableColumn(OaPersonDefineTable table){
		this.table = table;
	}

	public OaPersonDefineTable getTable() {
		return table;
	}

	public void setTable(OaPersonDefineTable table) {
		this.table = table;
	}
	
	@Length(min=0, max=200, message="列名长度必须介于 0 和 200 之间")
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	@Length(min=0, max=200, message="注释长度必须介于 0 和 200 之间")
	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}
	
	@Length(min=0, max=80, message="列的类型长度必须介于 0 和 80 之间")
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
	@Length(min=0, max=11, message="列的长度长度必须介于 0 和 11 之间")
	public String getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
	
	@Length(min=0, max=1, message="是否必填长度必须介于 0 和 1 之间")
	public String getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(String isRequired) {
		this.isRequired = isRequired;
	}
	
	@Length(min=0, max=1, message="是否显示到列表长度必须介于 0 和 1 之间")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@Length(min=0, max=1, message="是否流程变量长度必须介于 0 和 1 之间")
	public String getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}
	
	@Length(min=0, max=64, message="控件类型Id长度必须介于 0 和 64 之间")
	public String getControlTypeId() {
		return controlTypeId;
	}

	public void setControlTypeId(String controlTypeId) {
		this.controlTypeId = controlTypeId;
	}
	
}