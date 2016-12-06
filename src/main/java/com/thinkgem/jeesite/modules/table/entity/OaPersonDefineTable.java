/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.table.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import com.google.common.collect.Lists;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 自定义数据源Entity
 * @author chenxy
 * @version 2016-11-24
 */
public class OaPersonDefineTable extends DataEntity<OaPersonDefineTable> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// sys_office 表的主键
	private String tableName;		// 表名
	private String tableComment;		// 注释
	private String tableProperty;		// 属性
	private String tableStatus;		// 状态
	private String isMaster;		// 是否主表
	private String isDetail;		// 是否从表
	private List<OaPersonDefineTableColumn> oaPersonDefineTableColumnList = Lists.newArrayList();		// 子表列表
	
	public OaPersonDefineTable() {
		super();
	}

	public OaPersonDefineTable(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=200, message="表名长度必须介于 0 和 200 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=0, max=200, message="注释长度必须介于 0 和 200 之间")
	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}
	
	@Length(min=0, max=10, message="属性长度必须介于 0 和 10 之间")
	public String getTableProperty() {
		return tableProperty;
	}

	public void setTableProperty(String tableProperty) {
		this.tableProperty = tableProperty;
	}
	
	@Length(min=0, max=10, message="状态长度必须介于 0 和 10 之间")
	public String getTableStatus() {
		return tableStatus;
	}

	public void setTableStatus(String tableStatus) {
		this.tableStatus = tableStatus;
	}
	
	@Length(min=0, max=1, message="是否主表长度必须介于 0 和 1 之间")
	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}
	
	@Length(min=0, max=1, message="是否从表长度必须介于 0 和 1 之间")
	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}
	
	public List<OaPersonDefineTableColumn> getOaPersonDefineTableColumnList() {
		return oaPersonDefineTableColumnList;
	}

	public void setOaPersonDefineTableColumnList(List<OaPersonDefineTableColumn> oaPersonDefineTableColumnList) {
		this.oaPersonDefineTableColumnList = oaPersonDefineTableColumnList;
	}
}