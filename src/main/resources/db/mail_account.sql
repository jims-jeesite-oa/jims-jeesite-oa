create table MAIL_ACCOUNT
(
  id           VARCHAR2(64),
  mail_name    VARCHAR2(64),
  mail_address VARCHAR2(100),
  mail_accept  VARCHAR2(100),
  mail_send    VARCHAR2(100),
  username     VARCHAR2(100),
  password     VARCHAR2(100),
  login_id     VARCHAR2(64),
  port         VARCHAR2(10)
);
-- Add comments to the table 
comment on table MAIL_ACCOUNT
  is '邮件账户设置';
-- Add comments to the columns 
comment on column MAIL_ACCOUNT.id
  is 'id';
comment on column MAIL_ACCOUNT.mail_name
  is '邮件显示名称';
comment on column MAIL_ACCOUNT.mail_address
  is '邮件地址';
comment on column MAIL_ACCOUNT.mail_accept
  is '接收服务器';
comment on column MAIL_ACCOUNT.mail_send
  is '发送服务器';
comment on column MAIL_ACCOUNT.username
  is '用户名';
comment on column MAIL_ACCOUNT.password
  is '密码';
comment on column MAIL_ACCOUNT.login_id
  is '当前登录人ID';
comment on column MAIL_ACCOUNT.port
  is '端口号';