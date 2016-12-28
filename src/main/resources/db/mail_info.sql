-- Create table
create table MAIL_INFO
(
  id          VARCHAR2(64) not null,
  theme       VARCHAR2(250),
  content     CLOB,
  files       VARCHAR2(2000),
  read_mark   CHAR(1),
  time        DATE not null,
  sender_id   VARCHAR2(64) not null,
  receiver_id VARCHAR2(2000) not null,
  cc_id       VARCHAR2(2000),
  own_id      VARCHAR2(64),
  state       VARCHAR2(50),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null,
  flag        CHAR(1) default '0'
);
-- Add comments to the table 
comment on table MAIL_INFO
  is '邮件信息';
-- Add comments to the columns 
comment on column MAIL_INFO.theme
  is '邮件主题';
comment on column MAIL_INFO.content
  is '邮件正文';
comment on column MAIL_INFO.files
  is '附件';
comment on column MAIL_INFO.read_mark
  is '已读标记(0 未读， 1已读)';
comment on column MAIL_INFO.time
  is '时间';
comment on column MAIL_INFO.sender_id
  is '发件人';
comment on column MAIL_INFO.receiver_id
  is '收件人';
comment on column MAIL_INFO.cc_id
  is '抄送人';
comment on column MAIL_INFO.own_id
  is '拥有此邮件人';
comment on column MAIL_INFO.state
  is '状态:收件箱(INBOX),已发送(SENT),草稿箱(DRAFTS),已删除(DELETED)';
comment on column MAIL_INFO.create_by
  is '创建者';
comment on column MAIL_INFO.create_date
  is '创建时间';
comment on column MAIL_INFO.update_by
  is '更新者';
comment on column MAIL_INFO.update_date
  is '更新时间';
comment on column MAIL_INFO.remarks
  is '备注信息';
comment on column MAIL_INFO.del_flag
  is '删除标记';
comment on column MAIL_INFO.flag
  is '标识为内部邮箱';
-- Create/Recreate primary, unique and foreign key constraints 
alter table MAIL_INFO
  add constraint PK_MAIL_INFO primary key (ID);
