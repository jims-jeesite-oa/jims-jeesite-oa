
DROP TABLE IF EXISTS oa_audit_man;
CREATE TABLE oa_audit_man
(
   id                   NATIONAL VARCHAR(64) NOT NULL COMMENT '编号',
   audit_id             NATIONAL VARCHAR(64) COMMENT '审核人ID',
   audit_man            NATIONAL VARCHAR(40) COMMENT '审核人姓名',
   audit_job            NATIONAL VARCHAR(80) DEFAULT '0' COMMENT '审核人职位',
   PRIMARY KEY (id)
);

ALTER TABLE oa_audit_man COMMENT '新闻公告审核人';

DROP INDEX oa_notify_del_flag ON oa_news;

DROP TABLE IF EXISTS oa_news;

/*==============================================================*/
/* Table: oa_news                                               */
/*==============================================================*/
CREATE TABLE oa_news
(
   id                   NATIONAL VARCHAR(64) NOT NULL COMMENT '编号',
   title                NATIONAL VARCHAR(200) COMMENT '标题',
   content              NATIONAL VARCHAR(2000) COMMENT '内容',
   files                NATIONAL VARCHAR(2000) COMMENT '附件',
   audit_flag           NATIONAL CHAR(1) COMMENT '审核状态（0 未审核，1 已审核）',
   audit_man            VARCHAR(64) COMMENT '审核人ID',
   is_topic             CHAR(1) COMMENT '是否置顶（0不置顶，1置顶）',
   create_by            NATIONAL VARCHAR(64) NOT NULL COMMENT '创建者',
   create_date          DATETIME NOT NULL COMMENT '创建时间',
   update_by            NATIONAL VARCHAR(64) NOT NULL COMMENT '更新者',
   update_date          DATETIME NOT NULL COMMENT '更新时间',
   remarks              NATIONAL VARCHAR(255) COMMENT '备注信息',
   del_flag             NATIONAL CHAR(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
   PRIMARY KEY (id)
);

ALTER TABLE oa_news COMMENT '新闻公告';

/*==============================================================*/
/* Index: oa_notify_del_flag                                    */
/*==============================================================*/
CREATE INDEX oa_notify_del_flag ON oa_news
(
   del_flag
);