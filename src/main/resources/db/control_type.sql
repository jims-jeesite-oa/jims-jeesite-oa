--created by chenxy
--控件类型
create table oa_control_type
(
   id                   national varchar(64) not null comment '编号',
   controlName          national varchar(200) comment '控件名称',
   controlContent       national varchar(200) comment '控件内容',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);

----

