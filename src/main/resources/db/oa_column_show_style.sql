--created by chenxy
--字段显示方式设置--即字段与控件的一一对应
create table oa_column_show_style
(
   id                   national varchar(64) not null comment '编号',
   office_id            national varchar(200) comment '所属机构ID',
   is_common            boolean comment '是否是通用',
   table_name           national  varchar(50) comment '表名',
   form_name            national  varchar(50) comment '表单名',
   column_name          national varchar(200) comment '字段名',
   column_type          national varchar(20) comment '字段类型',
   show_type            national varchar(30) comment '显示方式',
   create_by            national varchar(64) not null comment '创建者',
   create_date          datetime not null comment '创建时间',
   update_by            national varchar(64) not null comment '更新者',
   update_date          datetime not null comment '更新时间',
   remarks              national varchar(255) comment '备注信息',
   del_flag             national char(1) not null default '0' comment '删除标记',
   primary key (id)
);
--显示方式（控件类型）
INSERT INTO `sys_dict` VALUES ('chenxy1', '文本', '文本', 'column_type', '列的类型', '103', '0', '1', '2016-11-15 13:48:22', '1', '2016-11-15 13:48:22', '', '0');
INSERT INTO `sys_dict` VALUES ('sf34aasd4a', '日期', '日期', 'column_type', '列的类型', '103', '0', '1', '2016-11-15 13:48:22', '1', '2016-11-15 13:48:22', '', '0');
INSERT INTO `sys_dict` VALUES ('rrrrr1111', '大文本', '大文本', 'column_type', '列的类型', '103', '0', '1', '2016-11-15 13:48:22', '1', '2016-11-15 13:48:22', '', '0');
INSERT INTO `sys_dict` VALUES ('rrrr33333', '数字', '数字', 'column_type', '列的类型', '103', '0', '1', '2016-11-15 13:48:22', '1', '2016-11-15 13:48:22', '', '0');

