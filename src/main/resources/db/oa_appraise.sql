-- Create table
create table OA_WEEK_APPRAISE
(
  id                 VARCHAR2(64),
  login_id           VARCHAR2(64),
  evaluate_id        VARCHAR2(64),
  appraise_week_date DATE,
  content            VARCHAR2(2000),
  year               VARCHAR2(20),
  week               VARCHAR2(20),
  flag               VARCHAR2(2)
);
-- Add comments to the table 
comment on table OA_WEEK_APPRAISE
  is '月评阅';
-- Add comments to the columns 
comment on column OA_WEEK_APPRAISE.id
  is '主键';
comment on column OA_WEEK_APPRAISE.login_id
  is '当前登录人';
comment on column OA_WEEK_APPRAISE.evaluate_id
  is '被评阅人';
comment on column OA_WEEK_APPRAISE.appraise_week_date
  is '评阅日期';
comment on column OA_WEEK_APPRAISE.content
  is '评阅内容';
comment on column OA_WEEK_APPRAISE.year
  is '年份';
comment on column OA_WEEK_APPRAISE.week
  is '周数';
comment on column OA_WEEK_APPRAISE.flag
  is '是否公开    1  公开   0不公开';
