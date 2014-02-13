-------------------------------------------
-- Export file for user SCOTT pwd:123456 --
-- Created by new on 2014-1-12, 10:51:28 --
-------------------------------------------

spool SMS.log

prompt
prompt Creating table CUSTOMER
prompt =======================
prompt
create table scott.CUSTOMER
(
  phoneno    NVARCHAR2(12) not null,
  password   NVARCHAR2(12) not null,
  money      BINARY_DOUBLE not null,
  createtime DATE not null,
  name       NVARCHAR2(12) not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column scott.CUSTOMER.phoneno
  is '手机号';
comment on column scott.CUSTOMER.password
  is '密码';
comment on column scott.CUSTOMER.money
  is '余额';
comment on column scott.CUSTOMER.createtime
  is '开户时间';
comment on column scott.CUSTOMER.name
  is '姓名';
alter table scott.CUSTOMER
  add primary key (PHONENO)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt
prompt Creating table ECARD
prompt ====================
prompt
create table scott.ECARD
(
  id     NVARCHAR2(10) not null,
  pwd    NVARCHAR2(6) not null,
  isused NUMBER not null,
  time   NVARCHAR2(12) not null,
  money  BINARY_DOUBLE not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column scott.ECARD.id
  is '卡号';
comment on column scott.ECARD.pwd
  is '密码';
comment on column scott.ECARD.isused
  is '是否被使用';
comment on column scott.ECARD.time
  is '有效期';
comment on column scott.ECARD.money
  is '金额';

prompt
prompt Creating table MESSAGE
prompt ======================
prompt
create table scott.MESSAGE
(
  sed    NVARCHAR2(12),
  rec    NVARCHAR2(12),
  msg    NVARCHAR2(222),
  time   NVARCHAR2(22),
  issend NUMBER,
  status NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column scott.MESSAGE.sed
  is '发件人';
comment on column scott.MESSAGE.rec
  is '收件人';
comment on column scott.MESSAGE.msg
  is '短信内容';
comment on column scott.MESSAGE.time
  is '发送时间';
comment on column scott.MESSAGE.issend
  is '服务器是否已发送';
comment on column scott.MESSAGE.status
  is '发送状态(成功，失败)';

prompt
prompt Creating table WEATHER
prompt ======================
prompt
create table scott.WEATHER
(
  city  NVARCHAR2(20) not null,
  weath NVARCHAR2(20) not null,
  lowt  NUMBER not null,
  hight NUMBER not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on column scott.WEATHER.city
  is '城市';
comment on column scott.WEATHER.weath
  is '天气';
comment on column scott.WEATHER.lowt
  is '最低温度';
comment on column scott.WEATHER.hight
  is '最高温度';


spool off
