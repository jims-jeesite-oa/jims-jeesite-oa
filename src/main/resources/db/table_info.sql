drop table if exists oa_audit_man;

/*==============================================================*/
/* Table: oa_audit_man                                          */
/*==============================================================*/
create table oa_audit_man
(
   id                   national varchar(64) not null comment '编号',
   audit_id             national varchar(64) comment '审核人ID',
   audit_man            national varchar(40) comment '审核人姓名',
   audit_job            national varchar(80) default '0' comment '审核人职位',
   primary key (id)
);

alter table oa_audit_man comment '新闻公告审核人';



drop table if exists oa_news;

/*==============================================================*/
/* Table: oa_news                                               */
/*==============================================================*/
create table oa_news
(
   id                   national varchar(64) not null comment '编号',
   title                national varchar(200) comment '标题',
   content              national varchar(2000) comment '内容',
   files                national varchar(2000) comment '附件',
   audit_flag           national char(1) comment '审核状态（0 未审核，1 已审核）',
   audit_man            varchar(64) comment '审核人ID',
   is_topic             char(1) comment '是否置顶（0不置顶，1置顶）',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);

alter table oa_news comment '新闻公告';



CREATE TABLE oa_schedule
(
   ID                   NATIONAL VARCHAR(64) NOT NULL,
   CONTENT              VARCHAR(2000) COMMENT '日志内容',
   IMPORTANT_LEVEL      CHAR(1) COMMENT '重要等级(0不重要，1重要)',
   EMERGENCY_LEVEL      CHAR(1) COMMENT '缓急程度（0不紧急，1紧急）',
   SCHEDULE_DATE        DATE COMMENT '日志日期',
   FLAG                 CHAR(1) COMMENT '完成状态（0未完成，1完成）',
   PRIMARY KEY (ID)
);

alter table oa_schedule comment '日程表';

create table oa_summary
(
   ID                   national varchar(64) not null,
   content              varchar(2000) comment '总结',
   type                 char(1) comment '总结类型（1日总结，2 月总结）',
   year                 int comment '年份（类型为2时填写）',
   week                 int comment '周数（类型为2时填写）',
   sum_date             date comment '总结日期（类型为1时填写数据）',
   evaluate             varchar(2000) comment '评阅内容',
   evaluate_man         varchar(40) comment '评阅人',
   evaluate_man_id      varchar(64) comment '评阅人id',
   primary key (ID)
);

alter table oa_summary comment '日程总结表';

create table oa_summary_permission
(
   evaluate_id          varchar(64) comment '可评阅用户id',
   evaluate_by_id       varchar(64) comment '评阅人id',
   primary key ()
);

alter table oa_summary_permission comment '日程总结评阅权限表';