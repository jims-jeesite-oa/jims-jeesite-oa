--created by chenxy
--记录各大医院所自定义的表结构信息
--暂时不考虑表名重复的情况
create table oa_person_define_table
(
   id                   national varchar(64) not null comment '编号',
   office_id            national varchar(200) comment 'sys_office 表的主键',
   table_name           national varchar(200) comment '表名',
   table_comment        national varchar(200) comment '注释',
   table_property       national varchar(10) comment '属性',
   table_status         national varchar(10) comment '状态',
   is_master             boolean comment '是否主表',
   is_detail             boolean  comment '是否从表',
   master_table_id       national varchar(64) comment '主表ID',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);




create table oa_person_define_table_column
(
   id                   national varchar(64) not null comment '编号',
   table_id             national varchar(64) comment 'oa_person_define_table 表的主键',
   column_name           national varchar(200) comment '列名',
   column_comment        national varchar(200) comment '注释',
   column_type          national varchar(80) comment '列的类型',
   table_status           national varchar(20) comment '列的长度',
   is_required                boolean comment '是否必填',
   is_show                boolean comment '是否显示到列表',
   is_process              boolean  comment '是否流程变量',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);