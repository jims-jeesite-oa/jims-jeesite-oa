/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.oa.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 邮件帐户设置Entity
 * @author yangruidong
 * @version 2016-12-15
 */
public class MailAccount extends DataEntity<MailAccount> {
	
	private static final long serialVersionUID = 1L;
	private String mailName;		// 邮件显示名称
	private String mailAddress;		// 邮件地址
	private String mailAccept;		// 接收服务器
	private String mailSend;		// 发送服务器
	private String username;		// 用户名
	private String password;		// 密码
    private String loginId;         //当前登录人ID
    private String port;            //端口号
	
	public MailAccount() {
		super();
	}

	public MailAccount(String id){
		super(id);
	}

	@Length(min=0, max=64, message="邮件显示名称长度必须介于 0 和 64 之间")
	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}
	
	@Length(min=0, max=100, message="邮件地址长度必须介于 0 和 100 之间")
	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	
	@Length(min=0, max=100, message="接收服务器长度必须介于 0 和 100 之间")
	public String getMailAccept() {
		return mailAccept;
	}

	public void setMailAccept(String mailAccept) {
		this.mailAccept = mailAccept;
	}
	
	@Length(min=0, max=100, message="发送服务器长度必须介于 0 和 100 之间")
	public String getMailSend() {
		return mailSend;
	}

	public void setMailSend(String mailSend) {
		this.mailSend = mailSend;
	}
	
	@Length(min=0, max=100, message="用户名长度必须介于 0 和 100 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}