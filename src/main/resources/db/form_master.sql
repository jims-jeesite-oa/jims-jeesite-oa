--created by chenxy
--表单主要信息
create table oa_form_master
(
   id                   national varchar(64) not null comment '编号',
   office_id            national varchar(64) not null comment '医院机构Id',
   title                national varchar(100) comment '表单标题',
   alias                national varchar(100) comment '表单别名',
   table_name           national varchar(100) comment '对应表',
   form_type            national varchar(100) comment '表单分类',
   publish_status       national varchar(100) comment '发布状态',
   data_templete        national varchar(100) comment '数据模板',
   design_type          national varchar(10) comment '设计类型',
   content              national varchar(2000) comment '内容',
   form_desc            national varchar(100) comment '表单描述',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);