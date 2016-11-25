package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 邮件信息Entity
 * @author lgx
 * @version 2016-11-24
 */
public class MailInfo extends DataEntity<MailInfo> {
	
	private static final long serialVersionUID = 1L;
	private String theme;		// 邮件主题
	private String content;		// 邮件正文
	private String files;		// 附件
	private String readMark;		// 已读标记(0 未读， 1已读)
	private Date time;		// 时间
	private String senderId;		// 发件人
	private String receiverId;		// 收件人
	private String ccId;		// 抄送人
	private String ownId;		// 拥有此邮件人
	private String state;		// 状态:收件箱(INBOX),已发送(SENT),草稿箱(DRAFTS),已删除(DELETED)
	
	public MailInfo() {
		super();
	}

	public MailInfo(String id){
		super(id);
	}

	@Length(min=1, max=64, message="邮件主题长度必须介于 1 和 64 之间")
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
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
	
	@Length(min=0, max=1, message="已读标记长度必须介于 0 和 1 之间")
	public String getReadMark() {
		return readMark;
	}

	public void setReadMark(String readMark) {
		this.readMark = readMark;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="时间不能为空")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
	@Length(min=1, max=64, message="发件人长度必须介于 1 和 64 之间")
	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	
	@Length(min=1, max=2000, message="收件人长度必须介于 1 和 2000 之间")
	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	
	@Length(min=0, max=2000, message="抄送人长度必须介于 0 和 2000 之间")
	public String getCcId() {
		return ccId;
	}

	public void setCcId(String ccId) {
		this.ccId = ccId;
	}
	
	@Length(min=0, max=64, message="拥有此邮件人长度必须介于 0 和 64 之间")
	public String getOwnId() {
		return ownId;
	}

	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}