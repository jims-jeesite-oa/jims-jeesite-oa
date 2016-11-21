/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 新闻公告Entity
 * @author lgx
 * @version 2016-11-17
 */
public class OaNews extends DataEntity<OaNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String files;		// 附件
	private String auditFlag;		// 审核状态（0待审核，1 已审核发布，2拒绝发布）
	private String auditMan;		// 审核人ID
	private String isTopic;		// 是否置顶（0不置顶，1置顶）

    private String auditManName;  //审核人姓名
    private String createManName;  //创建人姓名

	public OaNews() {
		super();
	}

	public OaNews(String id){
		super(id);
	}

	@Length(min=1, max=200, message="标题长度必须介于 1 和 200 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=2000, message="附件长度必须介于 0 和 2000 之间")
	public String getFiles() {
		return files;
	}

	public void setFiles(String files) {
		this.files = files;
	}
	
	@Length(min=0, max=1, message="审核状态（0 未审核，1 已审核）长度必须介于 0 和 1 之间")
	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	
	@Length(min=1, max=64, message="审核人ID长度必须介于 1 和 64 之间")
	public String getAuditMan() {
		return auditMan;
	}

	public void setAuditMan(String auditMan) {
		this.auditMan = auditMan;
	}
	
	@Length(min=0, max=1, message="是否置顶（0不置顶，1置顶）长度必须介于 0 和 1 之间")
	public String getIsTopic() {
		return isTopic;
	}

	public void setIsTopic(String isTopic) {
		this.isTopic = isTopic;
	}

    public String getAuditManName() {
        return auditManName;
    }

    public void setAuditManName(String auditManName) {
        this.auditManName = auditManName;
    }

    public String getCreateManName() {
        return createManName;
    }

    public void setCreateManName(String createManName) {
        this.createManName = createManName;
    }
}