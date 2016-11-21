--created by chenxy
--记录各大医院所自定义的表结构信息
--暂时不考虑表名重复的情况
create table org_table_infos
(
   id                   national varchar(64) not null comment '编号',
   office_id            national varchar(200) comment 'sys_office 表的主键',
   table_name           national varchar(200) comment '表名',
   table_comment        national varchar(200) comment '注释',
   table_property       national varchar(10) comment '属性',
   table_status         national varchar(10) comment '状态',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);