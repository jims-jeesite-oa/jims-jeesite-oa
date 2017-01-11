
create table ACT_EVT_LOG
(
  log_nr_       NUMBER(6) not null,
  type_         VARCHAR2(64),
  proc_def_id_  VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64),
  execution_id_ VARCHAR2(64),
  task_id_      VARCHAR2(64),
  time_stamp_   TIMESTAMP(6) default CURRENT_TIMESTAMP(3) not null,
  user_id_      VARCHAR2(255),
  data_         BLOB,
  lock_owner_   VARCHAR2(255),
  lock_time_    TIMESTAMP(6),
  is_processed_ NUMBER default 0
)
;
alter table ACT_EVT_LOG
  add constraint PK_ACT_EVT_LOG primary key (LOG_NR_);


create table ACT_RE_DEPLOYMENT
(
  id_          VARCHAR2(64) not null,
  name_        VARCHAR2(255),
  category_    VARCHAR2(255),
  tenant_id_   VARCHAR2(255),
  deploy_time_ TIMESTAMP(6)
)
;
alter table ACT_RE_DEPLOYMENT
  add constraint PK_ACT_RE_DEPLOYMENT primary key (ID_);


create table ACT_GE_BYTEARRAY
(
  id_            VARCHAR2(64) not null,
  rev_           NUMBER,
  name_          VARCHAR2(255),
  deployment_id_ VARCHAR2(64),
  bytes_         BLOB,
  generated_     NUMBER
)
;
alter table ACT_GE_BYTEARRAY
  add constraint PK_ACT_GE_BYTEARRAY primary key (ID_);
alter table ACT_GE_BYTEARRAY
  add constraint ACT_FK_BYTEARR_DEPL foreign key (DEPLOYMENT_ID_)
  references ACT_RE_DEPLOYMENT (ID_);


create table ACT_GE_PROPERTY
(
  name_  VARCHAR2(64) not null,
  value_ VARCHAR2(300),
  rev_   NUMBER
)
;
alter table ACT_GE_PROPERTY
  add constraint PK_ACT_GE_PROPERTY primary key (NAME_);


create table ACT_HI_ACTINST
(
  id_                VARCHAR2(64) not null,
  proc_def_id_       VARCHAR2(64) not null,
  proc_inst_id_      VARCHAR2(64) not null,
  execution_id_      VARCHAR2(64) not null,
  act_id_            VARCHAR2(255) not null,
  task_id_           VARCHAR2(64),
  call_proc_inst_id_ VARCHAR2(64),
  act_name_          VARCHAR2(255),
  act_type_          VARCHAR2(255) not null,
  assignee_          VARCHAR2(255),
  start_time_        DATE not null,
  end_time_          DATE,
  duration_          NUMBER,
  tenant_id_         VARCHAR2(255)
)
;
create index ACT_IDX_HI_ACT_INST_END on ACT_HI_ACTINST (END_TIME_);
create index ACT_IDX_HI_ACT_INST_START on ACT_HI_ACTINST (START_TIME_);
alter table ACT_HI_ACTINST
  add constraint PK_ACT_HI_ACTINST primary key (ID_);


create table ACT_HI_ATTACHMENT
(
  id_           VARCHAR2(64) not null,
  rev_          NUMBER,
  user_id_      VARCHAR2(255),
  name_         VARCHAR2(255),
  description_  VARCHAR2(4000),
  type_         VARCHAR2(255),
  task_id_      VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64),
  url_          VARCHAR2(4000),
  content_id_   VARCHAR2(64),
  time_         TIMESTAMP(3)
)
;
alter table ACT_HI_ATTACHMENT
  add constraint PK_ACT_HI_ATTACHMENT primary key (ID_);


create table ACT_HI_COMMENT
(
  id_           VARCHAR2(64) not null,
  type_         VARCHAR2(255),
  time_         DATE not null,
  user_id_      VARCHAR2(255),
  task_id_      VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64),
  action_       VARCHAR2(255),
  message_      VARCHAR2(4000),
  full_msg_     BLOB
)
;
alter table ACT_HI_COMMENT
  add constraint PK_ACT_HI_COMMENT primary key (ID_);


create table ACT_HI_DETAIL
(
  id_           VARCHAR2(64) not null,
  type_         VARCHAR2(255) not null,
  proc_inst_id_ VARCHAR2(64),
  execution_id_ VARCHAR2(64),
  task_id_      VARCHAR2(64),
  act_inst_id_  VARCHAR2(64),
  name_         VARCHAR2(255) not null,
  var_type_     VARCHAR2(255),
  rev_          NUMBER,
  time_         DATE not null,
  bytearray_id_ VARCHAR2(64),
  double_       BINARY_DOUBLE,
  long_         NUMBER,
  text_         VARCHAR2(4000),
  text2_        VARCHAR2(4000)
)
;
create index ACT_IDX_HI_DETAIL_ACT_INST on ACT_HI_DETAIL (ACT_INST_ID_);
create index ACT_IDX_HI_DETAIL_NAME on ACT_HI_DETAIL (NAME_);
create index ACT_IDX_HI_DETAIL_PROC_INST on ACT_HI_DETAIL (PROC_INST_ID_);
create index ACT_IDX_HI_DETAIL_TASK_ID on ACT_HI_DETAIL (TASK_ID_);
create index ACT_IDX_HI_DETAIL_TIME on ACT_HI_DETAIL (TIME_);
alter table ACT_HI_DETAIL
  add constraint PK_ACT_HI_DETAIL primary key (ID_);


create table ACT_HI_IDENTITYLINK
(
  id_           VARCHAR2(64) not null,
  group_id_     VARCHAR2(255),
  type_         VARCHAR2(255),
  user_id_      VARCHAR2(255),
  task_id_      VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64)
)
;
create index ACT_IDX_HI_IDENT_LNK_PROCINST on ACT_HI_IDENTITYLINK (PROC_INST_ID_);
create index ACT_IDX_HI_IDENT_LNK_TASK on ACT_HI_IDENTITYLINK (TASK_ID_);
create index ACT_IDX_HI_IDENT_LNK_USER on ACT_HI_IDENTITYLINK (USER_ID_);
alter table ACT_HI_IDENTITYLINK
  add constraint PK_ACT_HI_IDENTITYLINK primary key (ID_);


create table ACT_HI_PROCINST
(
  id_                        VARCHAR2(64) not null,
  proc_inst_id_              VARCHAR2(64) not null,
  business_key_              VARCHAR2(255),
  proc_def_id_               VARCHAR2(64) not null,
  start_time_                DATE not null,
  end_time_                  DATE,
  duration_                  NUMBER,
  start_user_id_             VARCHAR2(255),
  start_act_id_              VARCHAR2(255),
  end_act_id_                VARCHAR2(255),
  super_process_instance_id_ VARCHAR2(64),
  delete_reason_             VARCHAR2(4000),
  tenant_id_                 VARCHAR2(255),
  name_                      VARCHAR2(255)
)
;
create index ACT_IDX_HI_PRO_INST_END on ACT_HI_PROCINST (END_TIME_);
create index ACT_IDX_HI_PRO_I_BUSKEY on ACT_HI_PROCINST (BUSINESS_KEY_);
alter table ACT_HI_PROCINST
  add constraint PK_ACT_HI_PROCINST primary key (ID_);
alter table ACT_HI_PROCINST
  add constraint PROC_INST_ID_ unique (PROC_INST_ID_);


create table ACT_HI_TASKINST
(
  id_             VARCHAR2(64) not null,
  proc_def_id_    VARCHAR2(64),
  task_def_key_   VARCHAR2(255),
  proc_inst_id_   VARCHAR2(64),
  execution_id_   VARCHAR2(64),
  name_           VARCHAR2(255),
  parent_task_id_ VARCHAR2(64),
  description_    VARCHAR2(4000),
  owner_          VARCHAR2(255),
  assignee_       VARCHAR2(255),
  start_time_     DATE not null,
  claim_time_     DATE,
  end_time_       DATE,
  duration_       NUMBER,
  delete_reason_  VARCHAR2(4000),
  priority_       NUMBER,
  due_date_       DATE,
  form_key_       VARCHAR2(255),
  category_       VARCHAR2(255),
  tenant_id_      VARCHAR2(255)
)
;
create index ACT_IDX_HI_TASK_INST_PROCINST on ACT_HI_TASKINST (PROC_INST_ID_);
alter table ACT_HI_TASKINST
  add constraint PK_ACT_HI_TASKINST primary key (ID_);


create table ACT_HI_VARINST
(
  id_                VARCHAR2(64) not null,
  proc_inst_id_      VARCHAR2(64),
  execution_id_      VARCHAR2(64),
  task_id_           VARCHAR2(64),
  name_              VARCHAR2(255) not null,
  var_type_          VARCHAR2(100),
  rev_               NUMBER,
  bytearray_id_      VARCHAR2(64),
  double_            BINARY_DOUBLE,
  long_              NUMBER,
  text_              VARCHAR2(4000),
  text2_             VARCHAR2(4000),
  create_time_       DATE,
  last_updated_time_ DATE
)
;
create index ACT_IDX_HI_PROCVAR_PROC_INST on ACT_HI_VARINST (PROC_INST_ID_);
create index ACT_IDX_HI_PROCVAR_TASK_ID on ACT_HI_VARINST (TASK_ID_);
alter table ACT_HI_VARINST
  add constraint PK_ACT_HI_VARINST primary key (ID_);


create table ACT_ID_GROUP
(
  id_   VARCHAR2(64) not null,
  rev_  NUMBER,
  name_ VARCHAR2(255),
  type_ VARCHAR2(255)
)
;
alter table ACT_ID_GROUP
  add constraint PK_ACT_ID_GROUP primary key (ID_);


create table ACT_ID_INFO
(
  id_        VARCHAR2(64) not null,
  rev_       NUMBER,
  user_id_   VARCHAR2(64),
  type_      VARCHAR2(64),
  key_       VARCHAR2(255),
  value_     VARCHAR2(255),
  password_  BLOB,
  parent_id_ VARCHAR2(255)
)
;
alter table ACT_ID_INFO
  add constraint PK_ACT_ID_INFO primary key (ID_);


create table ACT_ID_USER
(
  id_         VARCHAR2(64) not null,
  rev_        NUMBER,
  first_      VARCHAR2(255),
  last_       VARCHAR2(255),
  email_      VARCHAR2(255),
  pwd_        VARCHAR2(255),
  picture_id_ VARCHAR2(64)
)
;
alter table ACT_ID_USER
  add constraint PK_ACT_ID_USER primary key (ID_);


create table ACT_ID_MEMBERSHIP
(
  user_id_  VARCHAR2(64) not null,
  group_id_ VARCHAR2(64) not null
)
;
alter table ACT_ID_MEMBERSHIP
  add constraint PK_ACT_ID_MEMBERSHIP primary key (USER_ID_, GROUP_ID_);
alter table ACT_ID_MEMBERSHIP
  add constraint ACT_FK_MEMB_GROUP foreign key (GROUP_ID_)
  references ACT_ID_GROUP (ID_);
alter table ACT_ID_MEMBERSHIP
  add constraint ACT_FK_MEMB_USER foreign key (USER_ID_)
  references ACT_ID_USER (ID_);

create table ACT_RE_PROCDEF
(
  id_                     VARCHAR2(64) not null,
  rev_                    NUMBER,
  category_               VARCHAR2(255),
  name_                   VARCHAR2(255),
  key_                    VARCHAR2(255) not null,
  version_                NUMBER not null,
  deployment_id_          VARCHAR2(64),
  resource_name_          VARCHAR2(4000),
  dgrm_resource_name_     VARCHAR2(4000),
  description_            VARCHAR2(4000),
  has_start_form_key_     NUMBER,
  suspension_state_       NUMBER,
  tenant_id_              VARCHAR2(255),
  has_graphical_notation_ NUMBER
)
;
alter table ACT_RE_PROCDEF
  add constraint PK_ACT_RE_PROCDEF primary key (ID_);
alter table ACT_RE_PROCDEF
  add constraint ACT_UNIQ_PROCDEF unique (KEY_, VERSION_, TENANT_ID_);


create table ACT_PROCDEF_INFO
(
  id_           VARCHAR2(64) not null,
  proc_def_id_  VARCHAR2(64) not null,
  rev_          NUMBER,
  info_json_id_ VARCHAR2(64)
)
;
alter table ACT_PROCDEF_INFO
  add constraint PK_ACT_PROCDEF_INFO primary key (ID_);
alter table ACT_PROCDEF_INFO
  add constraint ACT_UNIQ_INFO_PROCDEF unique (PROC_DEF_ID_);
alter table ACT_PROCDEF_INFO
  add constraint ACT_FK_INFO_JSON_BA foreign key (INFO_JSON_ID_)
  references ACT_GE_BYTEARRAY (ID_);
alter table ACT_PROCDEF_INFO
  add constraint ACT_FK_INFO_PROCDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);


create table ACT_RE_MODEL
(
  id_                           VARCHAR2(64) not null,
  rev_                          NUMBER,
  name_                         VARCHAR2(255),
  key_                          VARCHAR2(255),
  category_                     VARCHAR2(255),
  create_time_                  TIMESTAMP(6),
  last_update_time_             TIMESTAMP(6),
  version_                      NUMBER,
  meta_info_                    VARCHAR2(4000),
  deployment_id_                VARCHAR2(64),
  editor_source_value_id_       VARCHAR2(64),
  editor_source_extra_value_id_ VARCHAR2(64),
  tenant_id_                    VARCHAR2(255)
)
;
alter table ACT_RE_MODEL
  add constraint PK_ACT_RE_MODEL primary key (ID_);
alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_DEPLOYMENT foreign key (DEPLOYMENT_ID_)
  references ACT_RE_DEPLOYMENT (ID_);
alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_SOURCE foreign key (EDITOR_SOURCE_VALUE_ID_)
  references ACT_GE_BYTEARRAY (ID_);
alter table ACT_RE_MODEL
  add constraint ACT_FK_MODEL_SOURCE_EXTRA foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_)
  references ACT_GE_BYTEARRAY (ID_);


create table ACT_RU_EXECUTION
(
  id_               VARCHAR2(64) not null,
  rev_              NUMBER,
  proc_inst_id_     VARCHAR2(64),
  business_key_     VARCHAR2(255),
  parent_id_        VARCHAR2(64),
  proc_def_id_      VARCHAR2(64),
  super_exec_       VARCHAR2(64),
  act_id_           VARCHAR2(255),
  is_active_        NUMBER,
  is_concurrent_    NUMBER,
  is_scope_         NUMBER,
  is_event_scope_   NUMBER,
  suspension_state_ NUMBER,
  cached_ent_state_ NUMBER,
  tenant_id_        VARCHAR2(255),
  name_             VARCHAR2(255),
  lock_time_        TIMESTAMP(6)
)
;
create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION (BUSINESS_KEY_);
alter table ACT_RU_EXECUTION
  add constraint PK_ACT_RU_EXECUTION primary key (ID_);
alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PARENT foreign key (PARENT_ID_)
  references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PROCDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);
alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_EXECUTION
  add constraint ACT_FK_EXE_SUPER foreign key (SUPER_EXEC_)
  references ACT_RU_EXECUTION (ID_);


create table ACT_RU_EVENT_SUBSCR
(
  id_            VARCHAR2(64) not null,
  rev_           NUMBER,
  event_type_    VARCHAR2(255) not null,
  event_name_    VARCHAR2(255),
  execution_id_  VARCHAR2(64),
  proc_inst_id_  VARCHAR2(64),
  activity_id_   VARCHAR2(64),
  configuration_ VARCHAR2(255),
  created_       TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  proc_def_id_   VARCHAR2(64),
  tenant_id_     VARCHAR2(255)
)
;
create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR (CONFIGURATION_);
alter table ACT_RU_EVENT_SUBSCR
  add constraint PK_ACT_RU_EVENT_SUBSCR primary key (ID_);
alter table ACT_RU_EVENT_SUBSCR
  add constraint ACT_FK_EVENT_EXEC foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_);


create table ACT_RU_TASK
(
  id_               VARCHAR2(64) not null,
  rev_              NUMBER,
  execution_id_     VARCHAR2(64),
  proc_inst_id_     VARCHAR2(64),
  proc_def_id_      VARCHAR2(64),
  name_             VARCHAR2(255),
  parent_task_id_   VARCHAR2(64),
  description_      VARCHAR2(4000),
  task_def_key_     VARCHAR2(255),
  owner_            VARCHAR2(255),
  assignee_         VARCHAR2(255),
  delegation_       VARCHAR2(64),
  priority_         NUMBER,
  create_time_      TIMESTAMP(6),
  due_date_         DATE,
  category_         VARCHAR2(255),
  suspension_state_ NUMBER,
  tenant_id_        VARCHAR2(255),
  form_key_         VARCHAR2(255)
)
;
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK (CREATE_TIME_);
alter table ACT_RU_TASK
  add constraint PK_ACT_RU_TASK primary key (ID_);
alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_EXE foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);
alter table ACT_RU_TASK
  add constraint ACT_FK_TASK_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_);


create table ACT_RU_IDENTITYLINK
(
  id_           VARCHAR2(64) not null,
  rev_          NUMBER,
  group_id_     VARCHAR2(255),
  type_         VARCHAR2(255),
  user_id_      VARCHAR2(255),
  task_id_      VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64),
  proc_def_id_  VARCHAR2(64)
)
;
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK (GROUP_ID_);
create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK (USER_ID_);
alter table ACT_RU_IDENTITYLINK
  add constraint PK_ACT_RU_IDENTITYLINK primary key (ID_);
alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_ATHRZ_PROCEDEF foreign key (PROC_DEF_ID_)
  references ACT_RE_PROCDEF (ID_);
alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_IDL_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_IDENTITYLINK
  add constraint ACT_FK_TSKASS_TASK foreign key (TASK_ID_)
  references ACT_RU_TASK (ID_);


create table ACT_RU_JOB
(
  id_                  VARCHAR2(64) not null,
  rev_                 NUMBER,
  type_                VARCHAR2(255) not null,
  lock_exp_time_       TIMESTAMP(6),
  lock_owner_          VARCHAR2(255),
  exclusive_           NUMBER,
  execution_id_        VARCHAR2(64),
  process_instance_id_ VARCHAR2(64),
  proc_def_id_         VARCHAR2(64),
  retries_             NUMBER,
  exception_stack_id_  VARCHAR2(64),
  exception_msg_       VARCHAR2(4000),
  duedate_             TIMESTAMP(6),
  repeat_              VARCHAR2(255),
  handler_type_        VARCHAR2(255),
  handler_cfg_         VARCHAR2(4000),
  tenant_id_           VARCHAR2(255)
)
;
alter table ACT_RU_JOB
  add constraint PK_ACT_RU_JOB primary key (ID_);
alter table ACT_RU_JOB
  add constraint ACT_FK_JOB_EXCEPTION foreign key (EXCEPTION_STACK_ID_)
  references ACT_GE_BYTEARRAY (ID_);


create table ACT_RU_VARIABLE
(
  id_           VARCHAR2(64) not null,
  rev_          NUMBER,
  type_         VARCHAR2(255) not null,
  name_         VARCHAR2(255) not null,
  execution_id_ VARCHAR2(64),
  proc_inst_id_ VARCHAR2(64),
  task_id_      VARCHAR2(64),
  bytearray_id_ VARCHAR2(64),
  double_       BINARY_DOUBLE,
  long_         NUMBER,
  text_         VARCHAR2(4000),
  text2_        VARCHAR2(4000)
)
;
create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE (TASK_ID_);
alter table ACT_RU_VARIABLE
  add constraint PK_ACT_RU_VARIABLE primary key (ID_);
alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_BYTEARRAY foreign key (BYTEARRAY_ID_)
  references ACT_GE_BYTEARRAY (ID_);
alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_EXE foreign key (EXECUTION_ID_)
  references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_VARIABLE
  add constraint ACT_FK_VAR_PROCINST foreign key (PROC_INST_ID_)
  references ACT_RU_EXECUTION (ID_);


create table CHENXY_TEST
(
  id   VARCHAR2(64) not null,
  name VARCHAR2(20) not null
)
;
comment on table CHENXY_TEST
  is '陈晓阳ceshi';
comment on column CHENXY_TEST.id
  is '编号';
comment on column CHENXY_TEST.name
  is 'name';
alter table CHENXY_TEST
  add constraint PK_CHENXY_TEST primary key (ID);


create table CHENXY_TEST3
(
  id   VARCHAR2(64) not null,
  name VARCHAR2(10) not null
)
;
comment on table CHENXY_TEST3
  is '测试数据';
comment on column CHENXY_TEST3.id
  is '编号';
comment on column CHENXY_TEST3.name
  is '名称';
alter table CHENXY_TEST3
  add constraint PK_CHENXY_TEST3 primary key (ID);


create table CHENYX_TEST22
(
  id   VARCHAR2(64) not null,
  name VARCHAR2(12) not null
)
;
comment on table CHENYX_TEST22
  is '陈晓阳ceshi';
comment on column CHENYX_TEST22.id
  is '编号';
comment on column CHENYX_TEST22.name
  is 'name';
alter table CHENYX_TEST22
  add constraint PK_CHENYX_TEST22 primary key (ID);


create table CMS_ARTICLE
(
  id                  VARCHAR2(64) not null,
  category_id         VARCHAR2(64) not null,
  title               VARCHAR2(255) not null,
  link                VARCHAR2(255),
  color               VARCHAR2(50),
  image               VARCHAR2(255),
  keywords            VARCHAR2(255),
  description         VARCHAR2(255),
  weight              NUMBER default 0,
  weight_date         DATE,
  hits                NUMBER default 0,
  posid               VARCHAR2(10),
  custom_content_view VARCHAR2(255),
  view_config         CLOB,
  create_by           VARCHAR2(64),
  create_date         DATE,
  update_by           VARCHAR2(64),
  update_date         DATE,
  remarks             VARCHAR2(255),
  del_flag            CHAR(1) default '0' not null
)
;
comment on table CMS_ARTICLE
  is '文章表';
comment on column CMS_ARTICLE.id
  is '编号';
comment on column CMS_ARTICLE.category_id
  is '栏目编号';
comment on column CMS_ARTICLE.title
  is '标题';
comment on column CMS_ARTICLE.link
  is '文章链接';
comment on column CMS_ARTICLE.color
  is '标题颜色';
comment on column CMS_ARTICLE.image
  is '文章图片';
comment on column CMS_ARTICLE.keywords
  is '关键字';
comment on column CMS_ARTICLE.description
  is '描述、摘要';
comment on column CMS_ARTICLE.weight
  is '权重，越大越靠前';
comment on column CMS_ARTICLE.weight_date
  is '权重期限';
comment on column CMS_ARTICLE.hits
  is '点击数';
comment on column CMS_ARTICLE.posid
  is '推荐位，多选';
comment on column CMS_ARTICLE.custom_content_view
  is '自定义内容视图';
comment on column CMS_ARTICLE.view_config
  is '视图配置';
comment on column CMS_ARTICLE.create_by
  is '创建者';
comment on column CMS_ARTICLE.create_date
  is '创建时间';
comment on column CMS_ARTICLE.update_by
  is '更新者';
comment on column CMS_ARTICLE.update_date
  is '更新时间';
comment on column CMS_ARTICLE.remarks
  is '备注信息';
comment on column CMS_ARTICLE.del_flag
  is '删除标记';
create index CMS_ARTICLE_CATEGORY_ID on CMS_ARTICLE (CATEGORY_ID);
create index CMS_ARTICLE_CREATE_BY on CMS_ARTICLE (CREATE_BY);
create index CMS_ARTICLE_DEL_FLAG on CMS_ARTICLE (DEL_FLAG);
create index CMS_ARTICLE_KEYWORDS on CMS_ARTICLE (KEYWORDS);
create index CMS_ARTICLE_TITLE on CMS_ARTICLE (TITLE);
create index CMS_ARTICLE_UPDATE_DATE on CMS_ARTICLE (UPDATE_DATE);
create index CMS_ARTICLE_WEIGHT on CMS_ARTICLE (WEIGHT);
alter table CMS_ARTICLE
  add constraint PK_CMS_ARTICLE primary key (ID);


create table CMS_ARTICLE_DATA
(
  id            VARCHAR2(64) not null,
  content       CLOB,
  copyfrom      VARCHAR2(255),
  relation      VARCHAR2(255),
  allow_comment CHAR(1)
)
;
comment on table CMS_ARTICLE_DATA
  is '文章详表';
comment on column CMS_ARTICLE_DATA.id
  is '编号';
comment on column CMS_ARTICLE_DATA.content
  is '文章内容';
comment on column CMS_ARTICLE_DATA.copyfrom
  is '文章来源';
comment on column CMS_ARTICLE_DATA.relation
  is '相关文章';
comment on column CMS_ARTICLE_DATA.allow_comment
  is '是否允许评论';
alter table CMS_ARTICLE_DATA
  add constraint PK_CMS_ARTICLE_DATA primary key (ID);


create table CMS_CATEGORY
(
  id                  VARCHAR2(64) not null,
  parent_id           VARCHAR2(64) not null,
  parent_ids          VARCHAR2(2000) not null,
  site_id             VARCHAR2(64) default '1',
  office_id           VARCHAR2(64),
  module              VARCHAR2(20),
  name                VARCHAR2(100) not null,
  image               VARCHAR2(255),
  href                VARCHAR2(255),
  target              VARCHAR2(20),
  description         VARCHAR2(255),
  keywords            VARCHAR2(255),
  sort                NUMBER default 30,
  in_menu             CHAR(1) default '1',
  in_list             CHAR(1) default '1',
  show_modes          CHAR(1) default '0',
  allow_comment       CHAR(1),
  is_audit            CHAR(1),
  custom_list_view    VARCHAR2(255),
  custom_content_view VARCHAR2(255),
  view_config         CLOB,
  create_by           VARCHAR2(64),
  create_date         DATE,
  update_by           VARCHAR2(64),
  update_date         DATE,
  remarks             VARCHAR2(255),
  del_flag            CHAR(1) default '0' not null
)
;
comment on table CMS_CATEGORY
  is '栏目表';
comment on column CMS_CATEGORY.id
  is '编号';
comment on column CMS_CATEGORY.parent_id
  is '父级编号';
comment on column CMS_CATEGORY.parent_ids
  is '所有父级编号';
comment on column CMS_CATEGORY.site_id
  is '站点编号';
comment on column CMS_CATEGORY.office_id
  is '归属机构';
comment on column CMS_CATEGORY.module
  is '栏目模块';
comment on column CMS_CATEGORY.name
  is '栏目名称';
comment on column CMS_CATEGORY.image
  is '栏目图片';
comment on column CMS_CATEGORY.href
  is '链接';
comment on column CMS_CATEGORY.target
  is '目标';
comment on column CMS_CATEGORY.description
  is '描述';
comment on column CMS_CATEGORY.keywords
  is '关键字';
comment on column CMS_CATEGORY.sort
  is '排序（升序）';
comment on column CMS_CATEGORY.in_menu
  is '是否在导航中显示';
comment on column CMS_CATEGORY.in_list
  is '是否在分类页中显示列表';
comment on column CMS_CATEGORY.show_modes
  is '展现方式';
comment on column CMS_CATEGORY.allow_comment
  is '是否允许评论';
comment on column CMS_CATEGORY.is_audit
  is '是否需要审核';
comment on column CMS_CATEGORY.custom_list_view
  is '自定义列表视图';
comment on column CMS_CATEGORY.custom_content_view
  is '自定义内容视图';
comment on column CMS_CATEGORY.view_config
  is '视图配置';
comment on column CMS_CATEGORY.create_by
  is '创建者';
comment on column CMS_CATEGORY.create_date
  is '创建时间';
comment on column CMS_CATEGORY.update_by
  is '更新者';
comment on column CMS_CATEGORY.update_date
  is '更新时间';
comment on column CMS_CATEGORY.remarks
  is '备注信息';
comment on column CMS_CATEGORY.del_flag
  is '删除标记';
create index CMS_CATEGORY_DEL_FLAG on CMS_CATEGORY (DEL_FLAG);
create index CMS_CATEGORY_MODULE on CMS_CATEGORY (MODULE);
create index CMS_CATEGORY_NAME on CMS_CATEGORY (NAME);
create index CMS_CATEGORY_OFFICE_ID on CMS_CATEGORY (OFFICE_ID);
create index CMS_CATEGORY_PARENT_ID on CMS_CATEGORY (PARENT_ID);
create index CMS_CATEGORY_SITE_ID on CMS_CATEGORY (SITE_ID);
create index CMS_CATEGORY_SORT on CMS_CATEGORY (SORT);
alter table CMS_CATEGORY
  add constraint PK_CMS_CATEGORY primary key (ID);


create table CMS_COMMENT
(
  id            VARCHAR2(64) not null,
  category_id   VARCHAR2(64) not null,
  content_id    VARCHAR2(64) not null,
  title         VARCHAR2(255),
  content       VARCHAR2(255),
  name          VARCHAR2(100),
  ip            VARCHAR2(100),
  create_date   DATE not null,
  audit_user_id VARCHAR2(64),
  audit_date    DATE,
  del_flag      CHAR(1) default '0' not null
)
;
comment on table CMS_COMMENT
  is '评论表';
comment on column CMS_COMMENT.id
  is '编号';
comment on column CMS_COMMENT.category_id
  is '栏目编号';
comment on column CMS_COMMENT.content_id
  is '栏目内容的编号';
comment on column CMS_COMMENT.title
  is '栏目内容的标题';
comment on column CMS_COMMENT.content
  is '评论内容';
comment on column CMS_COMMENT.name
  is '评论姓名';
comment on column CMS_COMMENT.ip
  is '评论IP';
comment on column CMS_COMMENT.create_date
  is '评论时间';
comment on column CMS_COMMENT.audit_user_id
  is '审核人';
comment on column CMS_COMMENT.audit_date
  is '审核时间';
comment on column CMS_COMMENT.del_flag
  is '删除标记';
create index CMS_COMMENT_CATEGORY_ID on CMS_COMMENT (CATEGORY_ID);
create index CMS_COMMENT_CONTENT_ID on CMS_COMMENT (CONTENT_ID);
create index CMS_COMMENT_STATUS on CMS_COMMENT (DEL_FLAG);
alter table CMS_COMMENT
  add constraint PK_CMS_COMMENT primary key (ID);


create table CMS_GUESTBOOK
(
  id          VARCHAR2(64) not null,
  type        CHAR(1) not null,
  content     VARCHAR2(255) not null,
  name        VARCHAR2(100) not null,
  email       VARCHAR2(100) not null,
  phone       VARCHAR2(100) not null,
  workunit    VARCHAR2(100) not null,
  ip          VARCHAR2(100) not null,
  create_date DATE not null,
  re_user_id  VARCHAR2(64),
  re_date     DATE,
  re_content  VARCHAR2(100),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table CMS_GUESTBOOK
  is '留言板';
comment on column CMS_GUESTBOOK.id
  is '编号';
comment on column CMS_GUESTBOOK.type
  is '留言分类';
comment on column CMS_GUESTBOOK.content
  is '留言内容';
comment on column CMS_GUESTBOOK.name
  is '姓名';
comment on column CMS_GUESTBOOK.email
  is '邮箱';
comment on column CMS_GUESTBOOK.phone
  is '电话';
comment on column CMS_GUESTBOOK.workunit
  is '单位';
comment on column CMS_GUESTBOOK.ip
  is 'IP';
comment on column CMS_GUESTBOOK.create_date
  is '留言时间';
comment on column CMS_GUESTBOOK.re_user_id
  is '回复人';
comment on column CMS_GUESTBOOK.re_date
  is '回复时间';
comment on column CMS_GUESTBOOK.re_content
  is '回复内容';
comment on column CMS_GUESTBOOK.del_flag
  is '删除标记';
create index CMS_GUESTBOOK_DEL_FLAG on CMS_GUESTBOOK (DEL_FLAG);
alter table CMS_GUESTBOOK
  add constraint PK_CMS_GUESTBOOK primary key (ID);


create table CMS_LINK
(
  id          VARCHAR2(64) not null,
  category_id VARCHAR2(64) not null,
  title       VARCHAR2(255) not null,
  color       VARCHAR2(50),
  image       VARCHAR2(255),
  href        VARCHAR2(255),
  weight      NUMBER default 0,
  weight_date DATE,
  create_by   VARCHAR2(64),
  create_date DATE,
  update_by   VARCHAR2(64),
  update_date DATE,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table CMS_LINK
  is '友情链接';
comment on column CMS_LINK.id
  is '编号';
comment on column CMS_LINK.category_id
  is '栏目编号';
comment on column CMS_LINK.title
  is '链接名称';
comment on column CMS_LINK.color
  is '标题颜色';
comment on column CMS_LINK.image
  is '链接图片';
comment on column CMS_LINK.href
  is '链接地址';
comment on column CMS_LINK.weight
  is '权重，越大越靠前';
comment on column CMS_LINK.weight_date
  is '权重期限';
comment on column CMS_LINK.create_by
  is '创建者';
comment on column CMS_LINK.create_date
  is '创建时间';
comment on column CMS_LINK.update_by
  is '更新者';
comment on column CMS_LINK.update_date
  is '更新时间';
comment on column CMS_LINK.remarks
  is '备注信息';
comment on column CMS_LINK.del_flag
  is '删除标记';
create index CMS_LINK_CATEGORY_ID on CMS_LINK (CATEGORY_ID);
create index CMS_LINK_CREATE_BY on CMS_LINK (CREATE_BY);
create index CMS_LINK_DEL_FLAG on CMS_LINK (DEL_FLAG);
create index CMS_LINK_TITLE on CMS_LINK (TITLE);
create index CMS_LINK_UPDATE_DATE on CMS_LINK (UPDATE_DATE);
create index CMS_LINK_WEIGHT on CMS_LINK (WEIGHT);
alter table CMS_LINK
  add constraint PK_CMS_LINK primary key (ID);


create table CMS_SITE
(
  id                VARCHAR2(64) not null,
  name              VARCHAR2(100) not null,
  title             VARCHAR2(100) not null,
  logo              VARCHAR2(255),
  domain            VARCHAR2(255),
  description       VARCHAR2(255),
  keywords          VARCHAR2(255),
  theme             VARCHAR2(255) default 'default',
  copyright         CLOB,
  custom_index_view VARCHAR2(255),
  create_by         VARCHAR2(64),
  create_date       DATE,
  update_by         VARCHAR2(64),
  update_date       DATE,
  remarks           VARCHAR2(255),
  del_flag          CHAR(1) default '0' not null
)
;
comment on table CMS_SITE
  is '站点表';
comment on column CMS_SITE.id
  is '编号';
comment on column CMS_SITE.name
  is '站点名称';
comment on column CMS_SITE.title
  is '站点标题';
comment on column CMS_SITE.logo
  is '站点Logo';
comment on column CMS_SITE.domain
  is '站点域名';
comment on column CMS_SITE.description
  is '描述';
comment on column CMS_SITE.keywords
  is '关键字';
comment on column CMS_SITE.theme
  is '主题';
comment on column CMS_SITE.copyright
  is '版权信息';
comment on column CMS_SITE.custom_index_view
  is '自定义站点首页视图';
comment on column CMS_SITE.create_by
  is '创建者';
comment on column CMS_SITE.create_date
  is '创建时间';
comment on column CMS_SITE.update_by
  is '更新者';
comment on column CMS_SITE.update_date
  is '更新时间';
comment on column CMS_SITE.remarks
  is '备注信息';
comment on column CMS_SITE.del_flag
  is '删除标记';
create index CMS_SITE_DEL_FLAG on CMS_SITE (DEL_FLAG);
alter table CMS_SITE
  add constraint PK_CMS_SITE primary key (ID);


create table GEN_SCHEME
(
  id                   VARCHAR2(64) not null,
  name                 VARCHAR2(200),
  category             VARCHAR2(2000),
  package_name         VARCHAR2(500),
  module_name          VARCHAR2(30),
  sub_module_name      VARCHAR2(30),
  function_name        VARCHAR2(500),
  function_name_simple VARCHAR2(100),
  function_author      VARCHAR2(100),
  gen_table_id         VARCHAR2(200),
  create_by            VARCHAR2(64),
  create_date          DATE,
  update_by            VARCHAR2(64),
  update_date          DATE,
  remarks              VARCHAR2(255),
  del_flag             CHAR(1) default '0' not null
)
;
comment on table GEN_SCHEME
  is '生成方案';
comment on column GEN_SCHEME.id
  is '编号';
comment on column GEN_SCHEME.name
  is '名称';
comment on column GEN_SCHEME.category
  is '分类';
comment on column GEN_SCHEME.package_name
  is '生成包路径';
comment on column GEN_SCHEME.module_name
  is '生成模块名';
comment on column GEN_SCHEME.sub_module_name
  is '生成子模块名';
comment on column GEN_SCHEME.function_name
  is '生成功能名';
comment on column GEN_SCHEME.function_name_simple
  is '生成功能名（简写）';
comment on column GEN_SCHEME.function_author
  is '生成功能作者';
comment on column GEN_SCHEME.gen_table_id
  is '生成表编号';
comment on column GEN_SCHEME.create_by
  is '创建者';
comment on column GEN_SCHEME.create_date
  is '创建时间';
comment on column GEN_SCHEME.update_by
  is '更新者';
comment on column GEN_SCHEME.update_date
  is '更新时间';
comment on column GEN_SCHEME.remarks
  is '备注信息';
comment on column GEN_SCHEME.del_flag
  is '删除标记（0：正常；1：删除）';
create index GEN_SCHEME_DEL_FLAG on GEN_SCHEME (DEL_FLAG);
alter table GEN_SCHEME
  add constraint PK_GEN_SCHEME primary key (ID);


create table GEN_TABLE
(
  id              VARCHAR2(64) not null,
  name            VARCHAR2(200),
  comments        VARCHAR2(500),
  class_name      VARCHAR2(100),
  parent_table    VARCHAR2(200),
  parent_table_fk VARCHAR2(100),
  create_by       VARCHAR2(64),
  create_date     DATE,
  update_by       VARCHAR2(64),
  update_date     DATE,
  remarks         VARCHAR2(255),
  del_flag        CHAR(1) default '0' not null
)
;
comment on table GEN_TABLE
  is '业务表';
comment on column GEN_TABLE.id
  is '编号';
comment on column GEN_TABLE.name
  is '名称';
comment on column GEN_TABLE.comments
  is '描述';
comment on column GEN_TABLE.class_name
  is '实体类名称';
comment on column GEN_TABLE.parent_table
  is '关联父表';
comment on column GEN_TABLE.parent_table_fk
  is '关联父表外键';
comment on column GEN_TABLE.create_by
  is '创建者';
comment on column GEN_TABLE.create_date
  is '创建时间';
comment on column GEN_TABLE.update_by
  is '更新者';
comment on column GEN_TABLE.update_date
  is '更新时间';
comment on column GEN_TABLE.remarks
  is '备注信息';
comment on column GEN_TABLE.del_flag
  is '删除标记（0：正常；1：删除）';
create index GEN_TABLE_DEL_FLAG on GEN_TABLE (DEL_FLAG);
create index GEN_TABLE_NAME on GEN_TABLE (NAME);
alter table GEN_TABLE
  add constraint PK_GEN_TABLE primary key (ID);


create table GEN_TABLE_COLUMN
(
  id           VARCHAR2(64) not null,
  gen_table_id VARCHAR2(64),
  name         VARCHAR2(200),
  comments     VARCHAR2(500),
  jdbc_type    VARCHAR2(100),
  java_type    VARCHAR2(500),
  java_field   VARCHAR2(200),
  is_pk        CHAR(1),
  is_null      CHAR(1),
  is_insert    CHAR(1),
  is_edit      CHAR(1),
  is_list      CHAR(1),
  is_query     CHAR(1),
  query_type   VARCHAR2(200),
  show_type    VARCHAR2(200),
  dict_type    VARCHAR2(200),
  settings     VARCHAR2(2000),
  sort         NUMBER(10),
  create_by    VARCHAR2(64),
  create_date  DATE,
  update_by    VARCHAR2(64),
  update_date  DATE,
  remarks      VARCHAR2(255),
  del_flag     CHAR(1) default '0' not null
)
;
comment on table GEN_TABLE_COLUMN
  is '业务表字段';
comment on column GEN_TABLE_COLUMN.id
  is '编号';
comment on column GEN_TABLE_COLUMN.gen_table_id
  is '归属表编号';
comment on column GEN_TABLE_COLUMN.name
  is '名称';
comment on column GEN_TABLE_COLUMN.comments
  is '描述';
comment on column GEN_TABLE_COLUMN.jdbc_type
  is '列的数据类型的字节长度';
comment on column GEN_TABLE_COLUMN.java_type
  is 'JAVA类型';
comment on column GEN_TABLE_COLUMN.java_field
  is 'JAVA字段名';
comment on column GEN_TABLE_COLUMN.is_pk
  is '是否主键';
comment on column GEN_TABLE_COLUMN.is_null
  is '是否可为空';
comment on column GEN_TABLE_COLUMN.is_insert
  is '是否为插入字段';
comment on column GEN_TABLE_COLUMN.is_edit
  is '是否编辑字段';
comment on column GEN_TABLE_COLUMN.is_list
  is '是否列表字段';
comment on column GEN_TABLE_COLUMN.is_query
  is '是否查询字段';
comment on column GEN_TABLE_COLUMN.query_type
  is '查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）';
comment on column GEN_TABLE_COLUMN.show_type
  is '字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）';
comment on column GEN_TABLE_COLUMN.dict_type
  is '字典类型';
comment on column GEN_TABLE_COLUMN.settings
  is '其它设置（扩展字段JSON）';
comment on column GEN_TABLE_COLUMN.sort
  is '排序（升序）';
comment on column GEN_TABLE_COLUMN.create_by
  is '创建者';
comment on column GEN_TABLE_COLUMN.create_date
  is '创建时间';
comment on column GEN_TABLE_COLUMN.update_by
  is '更新者';
comment on column GEN_TABLE_COLUMN.update_date
  is '更新时间';
comment on column GEN_TABLE_COLUMN.remarks
  is '备注信息';
comment on column GEN_TABLE_COLUMN.del_flag
  is '删除标记（0：正常；1：删除）';
create index GEN_TABLE_COLUMN_DEL_FLAG on GEN_TABLE_COLUMN (DEL_FLAG);
create index GEN_TABLE_COLUMN_NAME on GEN_TABLE_COLUMN (NAME);
create index GEN_TABLE_COLUMN_SORT on GEN_TABLE_COLUMN (SORT);
create index GEN_TABLE_COLUMN_TABLE_ID on GEN_TABLE_COLUMN (GEN_TABLE_ID);
alter table GEN_TABLE_COLUMN
  add constraint PK_GEN_TABLE_COLUMN primary key (ID);


create table GEN_TEMPLATE
(
  id          VARCHAR2(64) not null,
  name        VARCHAR2(200),
  category    VARCHAR2(2000),
  file_path   VARCHAR2(500),
  file_name   VARCHAR2(200),
  content     CLOB,
  create_by   VARCHAR2(64),
  create_date DATE,
  update_by   VARCHAR2(64),
  update_date DATE,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table GEN_TEMPLATE
  is '代码模板表';
comment on column GEN_TEMPLATE.id
  is '编号';
comment on column GEN_TEMPLATE.name
  is '名称';
comment on column GEN_TEMPLATE.category
  is '分类';
comment on column GEN_TEMPLATE.file_path
  is '生成文件路径';
comment on column GEN_TEMPLATE.file_name
  is '生成文件名';
comment on column GEN_TEMPLATE.content
  is '内容';
comment on column GEN_TEMPLATE.create_by
  is '创建者';
comment on column GEN_TEMPLATE.create_date
  is '创建时间';
comment on column GEN_TEMPLATE.update_by
  is '更新者';
comment on column GEN_TEMPLATE.update_date
  is '更新时间';
comment on column GEN_TEMPLATE.remarks
  is '备注信息';
comment on column GEN_TEMPLATE.del_flag
  is '删除标记（0：正常；1：删除）';
create index GEN_TEMPLATE_DEL_FALG on GEN_TEMPLATE (DEL_FLAG);
alter table GEN_TEMPLATE
  add constraint PK_GEN_TEMPLATE primary key (ID);


create table OA_AUDIT_MAN
(
  id        VARCHAR2(64) not null,
  audit_id  VARCHAR2(64),
  audit_man VARCHAR2(40),
  audit_job VARCHAR2(80) default '0'
)
;
comment on table OA_AUDIT_MAN
  is '新闻公告审核人';
comment on column OA_AUDIT_MAN.id
  is '编号';
comment on column OA_AUDIT_MAN.audit_id
  is '审核人ID';
comment on column OA_AUDIT_MAN.audit_man
  is '审核人姓名';
comment on column OA_AUDIT_MAN.audit_job
  is '审核人职位';
alter table OA_AUDIT_MAN
  add constraint PK_OA_AUDIT_MAN primary key (ID);


create table OA_COLUMN_SHOW_STYLE
(
  id          VARCHAR2(64) not null,
  office_id   VARCHAR2(200),
  is_common   NUMBER,
  table_name  VARCHAR2(50),
  form_name   VARCHAR2(50),
  column_name VARCHAR2(200),
  column_type VARCHAR2(20),
  show_type   VARCHAR2(30),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on column OA_COLUMN_SHOW_STYLE.id
  is '编号';
comment on column OA_COLUMN_SHOW_STYLE.office_id
  is '所属机构ID';
comment on column OA_COLUMN_SHOW_STYLE.is_common
  is '是否是通用';
comment on column OA_COLUMN_SHOW_STYLE.table_name
  is '表名';
comment on column OA_COLUMN_SHOW_STYLE.form_name
  is '表单名';
comment on column OA_COLUMN_SHOW_STYLE.column_name
  is '字段名';
comment on column OA_COLUMN_SHOW_STYLE.column_type
  is '字段类型';
comment on column OA_COLUMN_SHOW_STYLE.show_type
  is '显示方式';
comment on column OA_COLUMN_SHOW_STYLE.create_by
  is '创建者';
comment on column OA_COLUMN_SHOW_STYLE.create_date
  is '创建时间';
comment on column OA_COLUMN_SHOW_STYLE.update_by
  is '更新者';
comment on column OA_COLUMN_SHOW_STYLE.update_date
  is '更新时间';
comment on column OA_COLUMN_SHOW_STYLE.remarks
  is '备注信息';
comment on column OA_COLUMN_SHOW_STYLE.del_flag
  is '删除标记';
alter table OA_COLUMN_SHOW_STYLE
  add constraint PK_OA_COLUMN_SHOW_STYLE primary key (ID);


create table OA_CONTROL_TYPE
(
  id             VARCHAR2(64) not null,
  controlname    VARCHAR2(200),
  controlcontent VARCHAR2(200),
  create_by      VARCHAR2(64) not null,
  create_date    DATE not null,
  update_by      VARCHAR2(64) not null,
  update_date    DATE not null,
  remarks        VARCHAR2(255),
  del_flag       CHAR(1) default '0' not null
)
;
comment on column OA_CONTROL_TYPE.id
  is '编号';
comment on column OA_CONTROL_TYPE.controlname
  is '控件名称';
comment on column OA_CONTROL_TYPE.controlcontent
  is '控件内容';
comment on column OA_CONTROL_TYPE.create_by
  is '创建者';
comment on column OA_CONTROL_TYPE.create_date
  is '创建时间';
comment on column OA_CONTROL_TYPE.update_by
  is '更新者';
comment on column OA_CONTROL_TYPE.update_date
  is '更新时间';
comment on column OA_CONTROL_TYPE.remarks
  is '备注信息';
comment on column OA_CONTROL_TYPE.del_flag
  is '删除标记';
alter table OA_CONTROL_TYPE
  add constraint PK_OA_CONTROL_TYPE primary key (ID);


create table OA_FORM_MASTER
(
  id             VARCHAR2(64) not null,
  office_id      VARCHAR2(64),
  form_no        VARCHAR2(50),
  title          VARCHAR2(100),
  alias          VARCHAR2(100),
  table_name     VARCHAR2(100),
  form_type      VARCHAR2(100),
  publish_status VARCHAR2(100),
  data_templete  VARCHAR2(100),
  design_type    VARCHAR2(10),
  content        CLOB,
  form_desc      VARCHAR2(100),
  create_by      VARCHAR2(64) not null,
  create_date    DATE not null,
  update_by      VARCHAR2(64) not null,
  update_date    DATE not null,
  remarks        VARCHAR2(255),
  del_flag       CHAR(1) default '0' not null
)
;
comment on column OA_FORM_MASTER.id
  is '编号';
comment on column OA_FORM_MASTER.office_id
  is '医院机构Id';
comment on column OA_FORM_MASTER.form_no
  is '表单编号';
comment on column OA_FORM_MASTER.title
  is '表单标题';
comment on column OA_FORM_MASTER.alias
  is '表单别名';
comment on column OA_FORM_MASTER.table_name
  is '对应表';
comment on column OA_FORM_MASTER.form_type
  is '表单分类';
comment on column OA_FORM_MASTER.publish_status
  is '发布状态';
comment on column OA_FORM_MASTER.data_templete
  is '数据模板';
comment on column OA_FORM_MASTER.design_type
  is '设计类型';
comment on column OA_FORM_MASTER.content
  is '内容';
comment on column OA_FORM_MASTER.form_desc
  is '表单描述';
comment on column OA_FORM_MASTER.create_by
  is '创建者';
comment on column OA_FORM_MASTER.create_date
  is '创建时间';
comment on column OA_FORM_MASTER.update_by
  is '更新者';
comment on column OA_FORM_MASTER.update_date
  is '更新时间';
comment on column OA_FORM_MASTER.remarks
  is '备注信息';
comment on column OA_FORM_MASTER.del_flag
  is '删除标记';
alter table OA_FORM_MASTER
  add constraint PK_OA_FORM_MASTER primary key (ID);


create table OA_LEAVE
(
  id                  VARCHAR2(64) not null,
  process_instance_id VARCHAR2(64),
  start_time          DATE,
  end_time            DATE,
  leave_type          VARCHAR2(20),
  reason              VARCHAR2(255),
  apply_time          DATE,
  reality_start_time  DATE,
  reality_end_time    DATE,
  create_by           VARCHAR2(64) not null,
  create_date         DATE not null,
  update_by           VARCHAR2(64) not null,
  update_date         DATE not null,
  remarks             VARCHAR2(255),
  del_flag            CHAR(1) default '0' not null
)
;
comment on table OA_LEAVE
  is '请假流程表';
comment on column OA_LEAVE.id
  is '编号';
comment on column OA_LEAVE.process_instance_id
  is '流程实例编号';
comment on column OA_LEAVE.start_time
  is '开始时间';
comment on column OA_LEAVE.end_time
  is '结束时间';
comment on column OA_LEAVE.leave_type
  is '请假类型';
comment on column OA_LEAVE.reason
  is '请假理由';
comment on column OA_LEAVE.apply_time
  is '申请时间';
comment on column OA_LEAVE.reality_start_time
  is '实际开始时间';
comment on column OA_LEAVE.reality_end_time
  is '实际结束时间';
comment on column OA_LEAVE.create_by
  is '创建者';
comment on column OA_LEAVE.create_date
  is '创建时间';
comment on column OA_LEAVE.update_by
  is '更新者';
comment on column OA_LEAVE.update_date
  is '更新时间';
comment on column OA_LEAVE.remarks
  is '备注信息';
comment on column OA_LEAVE.del_flag
  is '删除标记';
create index OA_LEAVE_CREATE_BY on OA_LEAVE (CREATE_BY);
create index OA_LEAVE_DEL_FLAG on OA_LEAVE (DEL_FLAG);
create index OA_LEAVE_PROCESS_INSTANCE_ID on OA_LEAVE (PROCESS_INSTANCE_ID);
alter table OA_LEAVE
  add constraint PK_OA_LEAVE primary key (ID);


create table OA_NEWS
(
  id          VARCHAR2(64) not null,
  title       VARCHAR2(200),
  content     CLOB,
  files       VARCHAR2(2000),
  audit_flag  CHAR(1) default '0',
  audit_man   VARCHAR2(64),
  is_topic    CHAR(1),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table OA_NEWS
  is '新闻公告';
comment on column OA_NEWS.id
  is '编号';
comment on column OA_NEWS.title
  is '标题';
comment on column OA_NEWS.content
  is '内容';
comment on column OA_NEWS.files
  is '附件';
comment on column OA_NEWS.audit_flag
  is '审核状态（0 未审核，1 已审核）';
comment on column OA_NEWS.audit_man
  is '审核人ID';
comment on column OA_NEWS.is_topic
  is '是否置顶（0不置顶，1置顶）';
comment on column OA_NEWS.create_by
  is '创建者';
comment on column OA_NEWS.create_date
  is '创建时间';
comment on column OA_NEWS.update_by
  is '更新者';
comment on column OA_NEWS.update_date
  is '更新时间';
comment on column OA_NEWS.remarks
  is '备注信息';
comment on column OA_NEWS.del_flag
  is '删除标记';
alter table OA_NEWS
  add constraint PK_OA_NEWS primary key (ID);


create table OA_NOTIFY
(
  id          VARCHAR2(64) not null,
  type        CHAR(1),
  title       VARCHAR2(200),
  content     VARCHAR2(2000),
  files       VARCHAR2(2000),
  status      CHAR(1),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table OA_NOTIFY
  is '通知通告';
comment on column OA_NOTIFY.id
  is '编号';
comment on column OA_NOTIFY.type
  is '类型';
comment on column OA_NOTIFY.title
  is '标题';
comment on column OA_NOTIFY.content
  is '内容';
comment on column OA_NOTIFY.files
  is '附件';
comment on column OA_NOTIFY.status
  is '状态';
comment on column OA_NOTIFY.create_by
  is '创建者';
comment on column OA_NOTIFY.create_date
  is '创建时间';
comment on column OA_NOTIFY.update_by
  is '更新者';
comment on column OA_NOTIFY.update_date
  is '更新时间';
comment on column OA_NOTIFY.remarks
  is '备注信息';
comment on column OA_NOTIFY.del_flag
  is '删除标记';
create index OA_NOTIFY_DEL_FLAG on OA_NOTIFY (DEL_FLAG);
alter table OA_NOTIFY
  add constraint PK_OA_NOTIFY primary key (ID);


create table OA_NOTIFY_RECORD
(
  id           VARCHAR2(64) not null,
  oa_notify_id VARCHAR2(64),
  user_id      VARCHAR2(64),
  read_flag    CHAR(1) default '0',
  read_date    DATE
)
;
comment on table OA_NOTIFY_RECORD
  is '通知通告发送记录';
comment on column OA_NOTIFY_RECORD.id
  is '编号';
comment on column OA_NOTIFY_RECORD.oa_notify_id
  is '通知通告ID';
comment on column OA_NOTIFY_RECORD.user_id
  is '接受人';
comment on column OA_NOTIFY_RECORD.read_flag
  is '阅读标记';
comment on column OA_NOTIFY_RECORD.read_date
  is '阅读时间';
create index OA_NOTIFY_RECORD_NOTIFY_ID on OA_NOTIFY_RECORD (OA_NOTIFY_ID);
create index OA_NOTIFY_RECORD_READ_FLAG on OA_NOTIFY_RECORD (READ_FLAG);
create index OA_NOTIFY_RECORD_USER_ID on OA_NOTIFY_RECORD (USER_ID);
alter table OA_NOTIFY_RECORD
  add constraint PK_OA_NOTIFY_RECORD primary key (ID);


create table OA_PERSON_DEFINE_TABLE
(
  id              VARCHAR2(64) not null,
  office_id       VARCHAR2(200),
  table_name      VARCHAR2(200),
  table_comment   VARCHAR2(200),
  table_property  VARCHAR2(10),
  table_status    VARCHAR2(10),
  is_master       NUMBER,
  is_detail       NUMBER,
  create_by       VARCHAR2(64) not null,
  create_date     DATE not null,
  update_by       VARCHAR2(64) not null,
  update_date     DATE not null,
  remarks         VARCHAR2(255),
  del_flag        CHAR(1) default '0' not null,
  master_table_id VARCHAR2(64)
)
;
comment on column OA_PERSON_DEFINE_TABLE.id
  is '编号';
comment on column OA_PERSON_DEFINE_TABLE.office_id
  is 'sys_office 表的主键';
comment on column OA_PERSON_DEFINE_TABLE.table_name
  is '表名';
comment on column OA_PERSON_DEFINE_TABLE.table_comment
  is '注释';
comment on column OA_PERSON_DEFINE_TABLE.table_property
  is '属性';
comment on column OA_PERSON_DEFINE_TABLE.table_status
  is '状态';
comment on column OA_PERSON_DEFINE_TABLE.is_master
  is '是否主表';
comment on column OA_PERSON_DEFINE_TABLE.is_detail
  is '是否从表';
comment on column OA_PERSON_DEFINE_TABLE.create_by
  is '创建者';
comment on column OA_PERSON_DEFINE_TABLE.create_date
  is '创建时间';
comment on column OA_PERSON_DEFINE_TABLE.update_by
  is '更新者';
comment on column OA_PERSON_DEFINE_TABLE.update_date
  is '更新时间';
comment on column OA_PERSON_DEFINE_TABLE.remarks
  is '备注信息';
comment on column OA_PERSON_DEFINE_TABLE.del_flag
  is '删除标记';
comment on column OA_PERSON_DEFINE_TABLE.master_table_id
  is '主表Id';
alter table OA_PERSON_DEFINE_TABLE
  add constraint PK_OA_PERSON_DEFINE_TABLE primary key (ID);


create table OA_PERSON_DEFINE_TABLE_COLUMN
(
  id              VARCHAR2(64) not null,
  table_id        VARCHAR2(64),
  column_name     VARCHAR2(200),
  column_comment  VARCHAR2(200),
  column_type     VARCHAR2(80),
  table_status    VARCHAR2(11),
  is_required     NUMBER,
  is_show         NUMBER,
  is_process      NUMBER,
  create_by       VARCHAR2(64) not null,
  create_date     DATE not null,
  update_by       VARCHAR2(64) not null,
  update_date     DATE not null,
  remarks         VARCHAR2(255),
  del_flag        CHAR(1) default '0' not null,
  control_type_id VARCHAR2(64),
  is_audit        char(1),
  show            number,
  audit_post      varchar2(64)
)
;
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.id
  is '编号';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.table_id
  is 'oa_person_define_table 表的主键';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.column_name
  is '列名';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.column_comment
  is '注释';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.column_type
  is '列的类型';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.table_status
  is '列的长度';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.is_required
  is '是否必填';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.is_show
  is '是否显示到列表';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.is_process
  is '是否流程变量';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.create_by
  is '创建者';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.create_date
  is '创建时间';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.update_by
  is '更新者';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.update_date
  is '更新时间';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.remarks
  is '备注信息';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.del_flag
  is '删除标记';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.control_type_id
  is '控件类型Id';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.is_audit
  is '是否为审批字段';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.audit_post
  is '审批人';
comment on column OA_PERSON_DEFINE_TABLE_COLUMN.show
  is '显示顺序';
alter table OA_PERSON_DEFINE_TABLE_COLUMN
  add constraint PK_OA_PERSON_DEFINE_T_C primary key (ID);


create table OA_SCHEDULE
(
  id              VARCHAR2(64) not null,
  content         VARCHAR2(2000),
  important_level CHAR(1),
  emergency_level CHAR(1),
  schedule_date   DATE,
  flag            CHAR(1),
  login_id        VARCHAR2(64)
)
;
comment on table OA_SCHEDULE
  is '日程表';
comment on column OA_SCHEDULE.content
  is '日志内容';
comment on column OA_SCHEDULE.important_level
  is '重要等级(0不重要，1重要)';
comment on column OA_SCHEDULE.emergency_level
  is '缓急程度（0不紧急，1紧急）';
comment on column OA_SCHEDULE.schedule_date
  is '日志日期';
comment on column OA_SCHEDULE.flag
  is '完成状态（0未完成，1完成）';
alter table OA_SCHEDULE
  add constraint PK_OA_SCHEDULE primary key (ID);


create table OA_SUMMARY
(
  id              VARCHAR2(64) not null,
  content         VARCHAR2(2000),
  type            CHAR(1),
  year            NUMBER,
  week            NUMBER,
  sum_date        DATE,
  evaluate        VARCHAR2(2000),
  evaluate_man    VARCHAR2(40),
  evaluate_man_id VARCHAR2(64)
)
;
comment on table OA_SUMMARY
  is '日程总结表';
comment on column OA_SUMMARY.content
  is '总结';
comment on column OA_SUMMARY.type
  is '总结类型（1日总结，2 月总结）';
comment on column OA_SUMMARY.year
  is '年份（类型为2时填写）';
comment on column OA_SUMMARY.week
  is '周数（类型为2时填写）';
comment on column OA_SUMMARY.sum_date
  is '总结日期（类型为1时填写数据）';
comment on column OA_SUMMARY.evaluate
  is '评阅内容';
comment on column OA_SUMMARY.evaluate_man
  is '评阅人';
comment on column OA_SUMMARY.evaluate_man_id
  is '评阅人id';
alter table OA_SUMMARY
  add constraint PK_OA_SUMMARY primary key (ID);


create table OA_SUMMARY_DAY
(
  id              VARCHAR2(64) not null,
  content         VARCHAR2(2000),
  sum_date        DATE,
  evaluate        VARCHAR2(2000),
  evaluate_man    VARCHAR2(40),
  evaluate_man_id VARCHAR2(64) ,
  login_id        VARCHAR2(64)
)
;
comment on table OA_SUMMARY_DAY
  is '日工作总结表';
comment on column OA_SUMMARY_DAY.content
  is '总结';
comment on column OA_SUMMARY_DAY.sum_date
  is '总结日期';
comment on column OA_SUMMARY_DAY.evaluate
  is '评阅内容';
comment on column OA_SUMMARY_DAY.evaluate_man
  is '评阅人';
comment on column OA_SUMMARY_DAY.evaluate_man_id
  is '评阅人id';
alter table OA_SUMMARY_DAY
  add constraint PK_OA_SUMMARY_DAY primary key (ID);


create table OA_SUMMARY_PERMISSION
(
  evaluate_id    VARCHAR2(64),
  evaluate_by_id VARCHAR2(64)
)
;
comment on table OA_SUMMARY_PERMISSION
  is '日程总结评阅权限表';
comment on column OA_SUMMARY_PERMISSION.evaluate_id
  is '可评阅用户id';
comment on column OA_SUMMARY_PERMISSION.evaluate_by_id
  is '评阅人id';


create table OA_SUMMARY_WEEK
(
  id                VARCHAR2(64) not null,
  content           VARCHAR2(2000),
  year              NUMBER,
  week              NUMBER,
  next_plan_content VARCHAR2(2000),
  sum_date          DATE,
  next_plan_title   VARCHAR2(1000),
  evaluate          VARCHAR2(2000),
  evaluate_man      VARCHAR2(40),
  evaluate_man_id   VARCHAR2(64) ,
  login_id          VARCHAR2(64)
)
;
comment on table OA_SUMMARY_WEEK
  is '周工作总结表';
comment on column OA_SUMMARY_WEEK.content
  is '本周总结';
comment on column OA_SUMMARY_WEEK.year
  is '年份';
comment on column OA_SUMMARY_WEEK.week
  is '周数';
comment on column OA_SUMMARY_WEEK.next_plan_content
  is '下周工作计划综述';
comment on column OA_SUMMARY_WEEK.sum_date
  is '总结日期';
comment on column OA_SUMMARY_WEEK.next_plan_title
  is '下周工作计划';
comment on column OA_SUMMARY_WEEK.evaluate
  is '评阅内容';
comment on column OA_SUMMARY_WEEK.evaluate_man
  is '评阅人';
comment on column OA_SUMMARY_WEEK.evaluate_man_id
  is '评阅人id';
alter table OA_SUMMARY_WEEK
  add constraint PK_OA_SUMMARY_WEEK primary key (ID);


create table OA_TABLE_TYPE
(
  id    NUMBER(6) not null,
  type  VARCHAR2(11),
  value VARCHAR2(20),
  flag  NUMBER
)
;
comment on column OA_TABLE_TYPE.id
  is '主键';
alter table OA_TABLE_TYPE
  add constraint PK_OA_TABLE_TYPE primary key (ID);


create table OA_TEST_AUDIT
(
  id             VARCHAR2(64) not null,
  proc_ins_id    VARCHAR2(64),
  user_id        VARCHAR2(64),
  office_id      VARCHAR2(64),
  post           VARCHAR2(255),
  age            CHAR(1),
  edu            VARCHAR2(255),
  content        VARCHAR2(255),
  olda           VARCHAR2(255),
  oldb           VARCHAR2(255),
  oldc           VARCHAR2(255),
  newa           VARCHAR2(255),
  newb           VARCHAR2(255),
  newc           VARCHAR2(255),
  add_num        VARCHAR2(255),
  exe_date       VARCHAR2(255),
  hr_text        VARCHAR2(255),
  lead_text      VARCHAR2(255),
  main_lead_text VARCHAR2(255),
  create_by      VARCHAR2(64) not null,
  create_date    DATE not null,
  update_by      VARCHAR2(64) not null,
  update_date    DATE not null,
  remarks        VARCHAR2(255),
  del_flag       CHAR(1) default '0' not null
)
;
comment on table OA_TEST_AUDIT
  is '审批流程测试表';
comment on column OA_TEST_AUDIT.id
  is '编号';
comment on column OA_TEST_AUDIT.proc_ins_id
  is '流程实例ID';
comment on column OA_TEST_AUDIT.user_id
  is '变动用户';
comment on column OA_TEST_AUDIT.office_id
  is '归属部门';
comment on column OA_TEST_AUDIT.post
  is '岗位';
comment on column OA_TEST_AUDIT.age
  is '性别';
comment on column OA_TEST_AUDIT.edu
  is '学历';
comment on column OA_TEST_AUDIT.content
  is '调整原因';
comment on column OA_TEST_AUDIT.olda
  is '现行标准 薪酬档级';
comment on column OA_TEST_AUDIT.oldb
  is '现行标准 月工资额';
comment on column OA_TEST_AUDIT.oldc
  is '现行标准 年薪总额';
comment on column OA_TEST_AUDIT.newa
  is '调整后标准 薪酬档级';
comment on column OA_TEST_AUDIT.newb
  is '调整后标准 月工资额';
comment on column OA_TEST_AUDIT.newc
  is '调整后标准 年薪总额';
comment on column OA_TEST_AUDIT.add_num
  is '月增资';
comment on column OA_TEST_AUDIT.exe_date
  is '执行时间';
comment on column OA_TEST_AUDIT.hr_text
  is '人力资源部门意见';
comment on column OA_TEST_AUDIT.lead_text
  is '分管领导意见';
comment on column OA_TEST_AUDIT.main_lead_text
  is '集团主要领导意见';
comment on column OA_TEST_AUDIT.create_by
  is '创建者';
comment on column OA_TEST_AUDIT.create_date
  is '创建时间';
comment on column OA_TEST_AUDIT.update_by
  is '更新者';
comment on column OA_TEST_AUDIT.update_date
  is '更新时间';
comment on column OA_TEST_AUDIT.remarks
  is '备注信息';
comment on column OA_TEST_AUDIT.del_flag
  is '删除标记';
create index OA_TEST_AUDIT_DEL_FLAG on OA_TEST_AUDIT (DEL_FLAG);
alter table OA_TEST_AUDIT
  add constraint PK_OA_TEST_AUDIT primary key (ID);


create table ORG_TABLE_INFOS
(
  id             VARCHAR2(64) not null,
  office_id      VARCHAR2(200),
  table_name     VARCHAR2(200),
  table_comment  VARCHAR2(200),
  table_property VARCHAR2(10),
  table_status   VARCHAR2(10),
  create_by      VARCHAR2(64) not null,
  create_date    DATE not null,
  update_by      VARCHAR2(64) not null,
  update_date    DATE not null,
  remarks        VARCHAR2(255),
  del_flag       CHAR(1) default '0' not null
)
;
comment on column ORG_TABLE_INFOS.id
  is '编号';
comment on column ORG_TABLE_INFOS.office_id
  is 'sys_office 表的主键';
comment on column ORG_TABLE_INFOS.table_name
  is '表名';
comment on column ORG_TABLE_INFOS.table_comment
  is '注释';
comment on column ORG_TABLE_INFOS.table_property
  is '属性';
comment on column ORG_TABLE_INFOS.table_status
  is '状态';
comment on column ORG_TABLE_INFOS.create_by
  is '创建者';
comment on column ORG_TABLE_INFOS.create_date
  is '创建时间';
comment on column ORG_TABLE_INFOS.update_by
  is '更新者';
comment on column ORG_TABLE_INFOS.update_date
  is '更新时间';
comment on column ORG_TABLE_INFOS.remarks
  is '备注信息';
comment on column ORG_TABLE_INFOS.del_flag
  is '删除标记';
alter table ORG_TABLE_INFOS
  add constraint PK_ORG_TABLE_INFOS primary key (ID);


create table SCHEMA_VERSION
(
  "version_rank"   INTEGER not null,
  "installed_rank" INTEGER not null,
  "version"        VARCHAR2(50) not null,
  "description"    VARCHAR2(200) not null,
  "type"           VARCHAR2(20) not null,
  "script"         VARCHAR2(1000) not null,
  "checksum"       INTEGER,
  "installed_by"   VARCHAR2(100) not null,
  "installed_on"   TIMESTAMP(6) default CURRENT_TIMESTAMP not null,
  "execution_time" INTEGER not null,
  "success"        NUMBER(1) not null
)


create table SYS_AREA
(
  id          VARCHAR2(64) not null,
  parent_id   VARCHAR2(64) not null,
  parent_ids  VARCHAR2(2000) not null,
  name        VARCHAR2(100) not null,
  sort        NUMBER(10) not null,
  code        VARCHAR2(100),
  type        CHAR(1),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table SYS_AREA
  is '区域表';
comment on column SYS_AREA.id
  is '编号';
comment on column SYS_AREA.parent_id
  is '父级编号';
comment on column SYS_AREA.parent_ids
  is '所有父级编号';
comment on column SYS_AREA.name
  is '名称';
comment on column SYS_AREA.sort
  is '排序';
comment on column SYS_AREA.code
  is '区域编码';
comment on column SYS_AREA.type
  is '区域类型';
comment on column SYS_AREA.create_by
  is '创建者';
comment on column SYS_AREA.create_date
  is '创建时间';
comment on column SYS_AREA.update_by
  is '更新者';
comment on column SYS_AREA.update_date
  is '更新时间';
comment on column SYS_AREA.remarks
  is '备注信息';
comment on column SYS_AREA.del_flag
  is '删除标记';
create index SYS_AREA_DEL_FLAG on SYS_AREA (DEL_FLAG);
create index SYS_AREA_PARENT_ID on SYS_AREA (PARENT_ID);
alter table SYS_AREA
  add constraint PK_SYS_AREA primary key (ID);


create table SYS_DICT
(
  id          VARCHAR2(64) not null,
  value       VARCHAR2(100) not null,
  label       VARCHAR2(100) not null,
  type        VARCHAR2(100) not null,
  description VARCHAR2(100) not null,
  sort        NUMBER(10) not null,
  parent_id   VARCHAR2(64) default '0',
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table SYS_DICT
  is '字典表';
comment on column SYS_DICT.id
  is '编号';
comment on column SYS_DICT.value
  is '数据值';
comment on column SYS_DICT.label
  is '标签名';
comment on column SYS_DICT.type
  is '类型';
comment on column SYS_DICT.description
  is '描述';
comment on column SYS_DICT.sort
  is '排序（升序）';
comment on column SYS_DICT.parent_id
  is '父级编号';
comment on column SYS_DICT.create_by
  is '创建者';
comment on column SYS_DICT.create_date
  is '创建时间';
comment on column SYS_DICT.update_by
  is '更新者';
comment on column SYS_DICT.update_date
  is '更新时间';
comment on column SYS_DICT.remarks
  is '备注信息';
comment on column SYS_DICT.del_flag
  is '删除标记';
create index SYS_DICT_DEL_FLAG on SYS_DICT (DEL_FLAG);
create index SYS_DICT_LABEL on SYS_DICT (LABEL);
create index SYS_DICT_VALUE on SYS_DICT (VALUE);
alter table SYS_DICT
  add constraint PK_SYS_DICT primary key (ID);


create table SYS_LOG
(
  id          VARCHAR2(64) not null,
  type        CHAR(1) default '1',
  title       VARCHAR2(255),
  create_by   VARCHAR2(64),
  create_date DATE,
  remote_addr VARCHAR2(255),
  user_agent  VARCHAR2(255),
  request_uri VARCHAR2(255),
  method      VARCHAR2(5),
  params      CLOB,
  exception   CLOB
)
;
comment on table SYS_LOG
  is '日志表';
comment on column SYS_LOG.id
  is '编号';
comment on column SYS_LOG.type
  is '日志类型';
comment on column SYS_LOG.title
  is '日志标题';
comment on column SYS_LOG.create_by
  is '创建者';
comment on column SYS_LOG.create_date
  is '创建时间';
comment on column SYS_LOG.remote_addr
  is '操作IP地址';
comment on column SYS_LOG.user_agent
  is '用户代理';
comment on column SYS_LOG.request_uri
  is '请求URI';
comment on column SYS_LOG.method
  is '操作方式';
comment on column SYS_LOG.params
  is '操作提交的数据';
comment on column SYS_LOG.exception
  is '异常信息';
create index SYS_LOG_CREATE_BY on SYS_LOG (CREATE_BY);
create index SYS_LOG_CREATE_DATE on SYS_LOG (CREATE_DATE);
create index SYS_LOG_REQUEST_URI on SYS_LOG (REQUEST_URI);
create index SYS_LOG_TYPE on SYS_LOG (TYPE);
alter table SYS_LOG
  add constraint PK_SYS_LOG primary key (ID);


create table SYS_MDICT
(
  id          VARCHAR2(64) not null,
  parent_id   VARCHAR2(64) not null,
  parent_ids  VARCHAR2(2000) not null,
  name        VARCHAR2(100) not null,
  sort        NUMBER(10) not null,
  description VARCHAR2(100),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table SYS_MDICT
  is '多级字典表';
comment on column SYS_MDICT.id
  is '编号';
comment on column SYS_MDICT.parent_id
  is '父级编号';
comment on column SYS_MDICT.parent_ids
  is '所有父级编号';
comment on column SYS_MDICT.name
  is '名称';
comment on column SYS_MDICT.sort
  is '排序';
comment on column SYS_MDICT.description
  is '描述';
comment on column SYS_MDICT.create_by
  is '创建者';
comment on column SYS_MDICT.create_date
  is '创建时间';
comment on column SYS_MDICT.update_by
  is '更新者';
comment on column SYS_MDICT.update_date
  is '更新时间';
comment on column SYS_MDICT.remarks
  is '备注信息';
comment on column SYS_MDICT.del_flag
  is '删除标记';
create index SYS_MDICT_DEL_FLAG on SYS_MDICT (DEL_FLAG);
create index SYS_MDICT_PARENT_ID on SYS_MDICT (PARENT_ID);
alter table SYS_MDICT
  add constraint PK_SYS_MDICT primary key (ID);


create table SYS_MENU
(
  id          VARCHAR2(64) not null,
  parent_id   VARCHAR2(64) not null,
  parent_ids  VARCHAR2(2000) not null,
  name        VARCHAR2(100) not null,
  sort        NUMBER(10) not null,
  href        VARCHAR2(2000),
  target      VARCHAR2(20),
  icon        VARCHAR2(100),
  is_show     CHAR(1) not null,
  permission  VARCHAR2(200),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table SYS_MENU
  is '菜单表';
comment on column SYS_MENU.id
  is '编号';
comment on column SYS_MENU.parent_id
  is '父级编号';
comment on column SYS_MENU.parent_ids
  is '所有父级编号';
comment on column SYS_MENU.name
  is '名称';
comment on column SYS_MENU.sort
  is '排序';
comment on column SYS_MENU.href
  is '链接';
comment on column SYS_MENU.target
  is '目标';
comment on column SYS_MENU.icon
  is '图标';
comment on column SYS_MENU.is_show
  is '是否在菜单中显示';
comment on column SYS_MENU.permission
  is '权限标识';
comment on column SYS_MENU.create_by
  is '创建者';
comment on column SYS_MENU.create_date
  is '创建时间';
comment on column SYS_MENU.update_by
  is '更新者';
comment on column SYS_MENU.update_date
  is '更新时间';
comment on column SYS_MENU.remarks
  is '备注信息';
comment on column SYS_MENU.del_flag
  is '删除标记';
create index SYS_MENU_DEL_FLAG on SYS_MENU (DEL_FLAG);
create index SYS_MENU_PARENT_ID on SYS_MENU (PARENT_ID);
alter table SYS_MENU
  add constraint PK_SYS_MENU primary key (ID);


create table SYS_OFFICE
(
  id             VARCHAR2(64) not null,
  parent_id      VARCHAR2(64) not null,
  parent_ids     VARCHAR2(2000) not null,
  name           VARCHAR2(100) not null,
  sort           NUMBER(10) not null,
  area_id        VARCHAR2(64) not null,
  code           VARCHAR2(100),
  type           CHAR(1) not null,
  grade          CHAR(1) not null,
  address        VARCHAR2(255),
  zip_code       VARCHAR2(100),
  master         VARCHAR2(100),
  phone          VARCHAR2(200),
  fax            VARCHAR2(200),
  email          VARCHAR2(200),
  useable        VARCHAR2(64),
  primary_person VARCHAR2(64),
  deputy_person  VARCHAR2(64),
  create_by      VARCHAR2(64) not null,
  create_date    DATE not null,
  update_by      VARCHAR2(64) not null,
  update_date    DATE not null,
  remarks        VARCHAR2(255),
  del_flag       CHAR(1) default '0' not null
)
;
comment on table SYS_OFFICE
  is '机构表';
comment on column SYS_OFFICE.id
  is '编号';
comment on column SYS_OFFICE.parent_id
  is '父级编号';
comment on column SYS_OFFICE.parent_ids
  is '所有父级编号';
comment on column SYS_OFFICE.name
  is '名称';
comment on column SYS_OFFICE.sort
  is '排序';
comment on column SYS_OFFICE.area_id
  is '归属区域';
comment on column SYS_OFFICE.code
  is '区域编码';
comment on column SYS_OFFICE.type
  is '机构类型';
comment on column SYS_OFFICE.grade
  is '机构等级';
comment on column SYS_OFFICE.address
  is '联系地址';
comment on column SYS_OFFICE.zip_code
  is '邮政编码';
comment on column SYS_OFFICE.master
  is '负责人';
comment on column SYS_OFFICE.phone
  is '电话';
comment on column SYS_OFFICE.fax
  is '传真';
comment on column SYS_OFFICE.email
  is '邮箱';
comment on column SYS_OFFICE.useable
  is '是否启用';
comment on column SYS_OFFICE.primary_person
  is '主负责人';
comment on column SYS_OFFICE.deputy_person
  is '副负责人';
comment on column SYS_OFFICE.create_by
  is '创建者';
comment on column SYS_OFFICE.create_date
  is '创建时间';
comment on column SYS_OFFICE.update_by
  is '更新者';
comment on column SYS_OFFICE.update_date
  is '更新时间';
comment on column SYS_OFFICE.remarks
  is '备注信息';
comment on column SYS_OFFICE.del_flag
  is '删除标记';
create index SYS_OFFICE_DEL_FLAG on SYS_OFFICE (DEL_FLAG);
create index SYS_OFFICE_PARENT_ID on SYS_OFFICE (PARENT_ID);
create index SYS_OFFICE_TYPE on SYS_OFFICE (TYPE);
alter table SYS_OFFICE
  add constraint PK_SYS_OFFICE primary key (ID);


create table SYS_ROLE
(
  id          VARCHAR2(64) not null,
  office_id   VARCHAR2(64),
  name        VARCHAR2(100) not null,
  enname      VARCHAR2(255),
  role_type   VARCHAR2(255),
  data_scope  CHAR(1),
  is_sys      VARCHAR2(64),
  useable     VARCHAR2(64),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table SYS_ROLE
  is '角色表';
comment on column SYS_ROLE.id
  is '编号';
comment on column SYS_ROLE.office_id
  is '归属机构';
comment on column SYS_ROLE.name
  is '角色名称';
comment on column SYS_ROLE.enname
  is '英文名称';
comment on column SYS_ROLE.role_type
  is '角色类型';
comment on column SYS_ROLE.data_scope
  is '数据范围';
comment on column SYS_ROLE.is_sys
  is '是否系统数据';
comment on column SYS_ROLE.useable
  is '是否可用';
comment on column SYS_ROLE.create_by
  is '创建者';
comment on column SYS_ROLE.create_date
  is '创建时间';
comment on column SYS_ROLE.update_by
  is '更新者';
comment on column SYS_ROLE.update_date
  is '更新时间';
comment on column SYS_ROLE.remarks
  is '备注信息';
comment on column SYS_ROLE.del_flag
  is '删除标记';
create index SYS_ROLE_DEL_FLAG on SYS_ROLE (DEL_FLAG);
create index SYS_ROLE_ENNAME on SYS_ROLE (ENNAME);
alter table SYS_ROLE
  add constraint PK_SYS_ROLE primary key (ID);


create table SYS_ROLE_MENU
(
  role_id VARCHAR2(64) not null,
  menu_id VARCHAR2(64) not null
)
;
comment on table SYS_ROLE_MENU
  is '角色-菜单';
comment on column SYS_ROLE_MENU.role_id
  is '角色编号';
comment on column SYS_ROLE_MENU.menu_id
  is '菜单编号';
alter table SYS_ROLE_MENU
  add constraint PK_SYS_ROLE_MENU primary key (ROLE_ID, MENU_ID);


create table SYS_ROLE_OFFICE
(
  role_id   VARCHAR2(64) not null,
  office_id VARCHAR2(64) not null
)
;
comment on table SYS_ROLE_OFFICE
  is '角色-机构';
comment on column SYS_ROLE_OFFICE.role_id
  is '角色编号';
comment on column SYS_ROLE_OFFICE.office_id
  is '机构编号';
alter table SYS_ROLE_OFFICE
  add constraint PK_SYS_ROLE_OFFICE primary key (ROLE_ID, OFFICE_ID);


create table SYS_USER
(
  id          VARCHAR2(64) not null,
  company_id  VARCHAR2(64) not null,
  office_id   VARCHAR2(64) not null,
  login_name  VARCHAR2(100) not null,
  password    VARCHAR2(100) not null,
  grade       VARCHAR2(100) not null,
  no          VARCHAR2(100),
  name        VARCHAR2(100) not null,
  email       VARCHAR2(200),
  phone       VARCHAR2(200),
  mobile      VARCHAR2(200),
  user_type   CHAR(1),
  photo       VARCHAR2(1000),
  login_ip    VARCHAR2(100),
  login_date  DATE,
  login_flag  VARCHAR2(64),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null,
   grade       CHAR(1),
   leader_ship  VARCHAR2(100),
  ac_name      VARCHAR2(100),
  dept         VARCHAR2(100),
  ac_dept_name VARCHAR2(100)
)
;
comment on table SYS_USER
  is '用户表';
comment on column SYS_USER.id
  is '编号';
comment on column SYS_USER.company_id
  is '归属公司';
comment on column SYS_USER.office_id
  is '归属部门';
comment on column SYS_USER.login_name
  is '登录名';
comment on column SYS_USER.password
  is '密码';
comment on column SYS_USER.no
  is '工号';
comment on column SYS_USER.name
  is '姓名';
comment on column SYS_USER.email
  is '邮箱';
comment on column SYS_USER.phone
  is '电话';
comment on column SYS_USER.mobile
  is '手机';
comment on column SYS_USER.user_type
  is '用户类型';
comment on column SYS_USER.photo
  is '用户头像';
comment on column SYS_USER.login_ip
  is '最后登陆IP';
comment on column SYS_USER.login_date
  is '最后登陆时间';
comment on column SYS_USER.login_flag
  is '是否可登录';
comment on column SYS_USER.create_by
  is '创建者';
comment on column SYS_USER.create_date
  is '创建时间';
comment on column SYS_USER.update_by
  is '更新者';
comment on column SYS_USER.update_date
  is '更新时间';
comment on column SYS_USER.remarks
  is '备注信息';
comment on column SYS_USER.del_flag
  is '删除标记';
create index SYS_USER_COMPANY_ID on SYS_USER (COMPANY_ID);
create index SYS_USER_DEL_FLAG on SYS_USER (DEL_FLAG);
create index SYS_USER_LOGIN_NAME on SYS_USER (LOGIN_NAME);
create index SYS_USER_OFFICE_ID on SYS_USER (OFFICE_ID);
create index SYS_USER_UPDATE_DATE on SYS_USER (UPDATE_DATE);
alter table SYS_USER
  add constraint PK_SYS_USER primary key (ID);


create table SYS_USER_ROLE
(
  user_id VARCHAR2(64) not null,
  role_id VARCHAR2(64) not null
)
;
comment on table SYS_USER_ROLE
  is '用户-角色';
comment on column SYS_USER_ROLE.user_id
  is '用户编号';
comment on column SYS_USER_ROLE.role_id
  is '角色编号';
alter table SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE primary key (USER_ID, ROLE_ID);


create table TEST_DATA
(
  id          VARCHAR2(64) not null,
  user_id     VARCHAR2(64),
  office_id   VARCHAR2(64),
  area_id     VARCHAR2(64),
  name        VARCHAR2(100),
  sex         CHAR(1),
  in_date     DATE,
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table TEST_DATA
  is '业务数据表';
comment on column TEST_DATA.id
  is '编号';
comment on column TEST_DATA.user_id
  is '归属用户';
comment on column TEST_DATA.office_id
  is '归属部门';
comment on column TEST_DATA.area_id
  is '归属区域';
comment on column TEST_DATA.name
  is '名称';
comment on column TEST_DATA.sex
  is '性别';
comment on column TEST_DATA.in_date
  is '加入日期';
comment on column TEST_DATA.create_by
  is '创建者';
comment on column TEST_DATA.create_date
  is '创建时间';
comment on column TEST_DATA.update_by
  is '更新者';
comment on column TEST_DATA.update_date
  is '更新时间';
comment on column TEST_DATA.remarks
  is '备注信息';
comment on column TEST_DATA.del_flag
  is '删除标记';
create index TEST_DATA_DEL_FLAG on TEST_DATA (DEL_FLAG);
alter table TEST_DATA
  add constraint PK_TEST_DATA primary key (ID);


create table TEST_DATA_CHILD
(
  id                VARCHAR2(64) not null,
  test_data_main_id VARCHAR2(64),
  name              VARCHAR2(100),
  create_by         VARCHAR2(64) not null,
  create_date       DATE not null,
  update_by         VARCHAR2(64) not null,
  update_date       DATE not null,
  remarks           VARCHAR2(255),
  del_flag          CHAR(1) default '0' not null
)
;
comment on table TEST_DATA_CHILD
  is '业务数据子表';
comment on column TEST_DATA_CHILD.id
  is '编号';
comment on column TEST_DATA_CHILD.test_data_main_id
  is '业务主表ID';
comment on column TEST_DATA_CHILD.name
  is '名称';
comment on column TEST_DATA_CHILD.create_by
  is '创建者';
comment on column TEST_DATA_CHILD.create_date
  is '创建时间';
comment on column TEST_DATA_CHILD.update_by
  is '更新者';
comment on column TEST_DATA_CHILD.update_date
  is '更新时间';
comment on column TEST_DATA_CHILD.remarks
  is '备注信息';
comment on column TEST_DATA_CHILD.del_flag
  is '删除标记';
create index TEST_DATA_CHILD_DEL_FLAG on TEST_DATA_CHILD (DEL_FLAG);
alter table TEST_DATA_CHILD
  add constraint PK_TEST_DATA_CHILD primary key (ID);


create table TEST_DATA_MAIN
(
  id          VARCHAR2(64) not null,
  user_id     VARCHAR2(64),
  office_id   VARCHAR2(64),
  area_id     VARCHAR2(64),
  name        VARCHAR2(100),
  sex         CHAR(1),
  in_date     DATE,
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table TEST_DATA_MAIN
  is '业务数据表';
comment on column TEST_DATA_MAIN.id
  is '编号';
comment on column TEST_DATA_MAIN.user_id
  is '归属用户';
comment on column TEST_DATA_MAIN.office_id
  is '归属部门';
comment on column TEST_DATA_MAIN.area_id
  is '归属区域';
comment on column TEST_DATA_MAIN.name
  is '名称';
comment on column TEST_DATA_MAIN.sex
  is '性别';
comment on column TEST_DATA_MAIN.in_date
  is '加入日期';
comment on column TEST_DATA_MAIN.create_by
  is '创建者';
comment on column TEST_DATA_MAIN.create_date
  is '创建时间';
comment on column TEST_DATA_MAIN.update_by
  is '更新者';
comment on column TEST_DATA_MAIN.update_date
  is '更新时间';
comment on column TEST_DATA_MAIN.remarks
  is '备注信息';
comment on column TEST_DATA_MAIN.del_flag
  is '删除标记';
create index TEST_DATA_MAIN_DEL_FLAG on TEST_DATA_MAIN (DEL_FLAG);
alter table TEST_DATA_MAIN
  add constraint PK_TEST_DATA_MAIN primary key (ID);


create table TEST_TREE
(
  id          VARCHAR2(64) not null,
  parent_id   VARCHAR2(64) not null,
  parent_ids  VARCHAR2(2000) not null,
  name        VARCHAR2(100) not null,
  sort        NUMBER(10) not null,
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null
)
;
comment on table TEST_TREE
  is '树结构表';
comment on column TEST_TREE.id
  is '编号';
comment on column TEST_TREE.parent_id
  is '父级编号';
comment on column TEST_TREE.parent_ids
  is '所有父级编号';
comment on column TEST_TREE.name
  is '名称';
comment on column TEST_TREE.sort
  is '排序';
comment on column TEST_TREE.create_by
  is '创建者';
comment on column TEST_TREE.create_date
  is '创建时间';
comment on column TEST_TREE.update_by
  is '更新者';
comment on column TEST_TREE.update_date
  is '更新时间';
comment on column TEST_TREE.remarks
  is '备注信息';
comment on column TEST_TREE.del_flag
  is '删除标记';
create index TEST_DATA_PARENT_ID on TEST_TREE (PARENT_ID);
create index TEST_TREE_DEL_FLAG on TEST_TREE (DEL_FLAG);
alter table TEST_TREE
  add constraint PK_TEST_TREE primary key (ID);


create table MAIL_INFO
(
  id          VARCHAR2(64) not null,
  theme       VARCHAR2(64) not null,
  content     CLOB not null,
  files       VARCHAR2(2000),
  read_mark   CHAR(1),
  time        DATE not null,
  sender_id   VARCHAR2(64) not null,
  receiver_id VARCHAR2(2000) not null,
  cc_id       VARCHAR2(2000),
  own_id       VARCHAR2(64),
  state       VARCHAR2(50),
  create_by   VARCHAR2(64) not null,
  create_date DATE not null,
  update_by   VARCHAR2(64) not null,
  update_date DATE not null,
  remarks     VARCHAR2(255),
  del_flag    CHAR(1) default '0' not null,
  constraint PK_MAIL_INFO primary key (ID)
);

comment on table MAIL_INFO
  is '邮件信息';
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
