/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.form.entity;

import com.thinkgem.jeesite.modules.sys.entity.Office;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 编辑器设计表单Entity
 * @author chenxy
 * @version 2016-11-18
 */
public class OaFormMaster extends DataEntity<OaFormMaster> {
	
	private static final long serialVersionUID = 1L;
	private Office office;		// 医院机构Id
	private String title;		// 表单标题
	private String alias;		// 表单别名
	private String formNo;		// 表单编号
	private String tableName;		// 对应表
	private String formType;		// 表单分类
	private String publishStatus;		// 发布状态
	private String dataTemplete;		// 数据模板
	private String designType;		// 设计类型
	private String content;		// 内容
	private String formDesc;		// 表单描述
	
	public OaFormMaster() {
		super();
	}

	public OaFormMaster(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	@Length(min=0, max=100, message="表单标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=100, message="表单别名长度必须介于 0 和 100 之间")
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	@Length(min=0, max=100, message="对应表长度必须介于 0 和 100 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=0, max=100, message="表单分类长度必须介于 0 和 100 之间")
	public String getFormType() {
		return formType;
	}

	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	@Length(min=0, max=100, message="发布状态长度必须介于 0 和 100 之间")
	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}
	
	@Length(min=0, max=100, message="数据模板长度必须介于 0 和 100 之间")
	public String getDataTemplete() {
		return dataTemplete;
	}

	public void setDataTemplete(String dataTemplete) {
		this.dataTemplete = dataTemplete;
	}
	
	@Length(min=0, max=10, message="设计类型长度必须介于 0 和 10 之间")
	public String getDesignType() {
		return designType;
	}

	public void setDesignType(String designType) {
		this.designType = designType;
	}
	
	@Length(min=0, max=2000, message="内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=100, message="表单描述长度必须介于 0 和 100 之间")
	public String getFormDesc() {
		return formDesc;
	}

	public void setFormDesc(String formDesc) {
		this.formDesc = formDesc;
	}

    @Length(min=0, max=50, message="表单编号必须介于 0 和 50 之间")
    public String getFormNo() {
        return formNo;
    }

    public void setFormNo(String formNo) {
        this.formNo = formNo;
    }
}