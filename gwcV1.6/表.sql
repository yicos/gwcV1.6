----------------------------------------------------
-- Export file for user GWCDB                     --
-- Created by Administrator on 2018-7-16, 9:29:39 --
----------------------------------------------------

spool table.log

prompt
prompt Creating table BAM_CARRUNTIME_INFO_TASK
prompt =======================================
prompt
create table BAM_CARRUNTIME_INFO_TASK
(
  id               VARCHAR2(12) not null,
  upload_degree    NUMBER(9) default 0,
  success_flag     NUMBER(9) default 0,
  car_number       VARCHAR2(12) not null,
  gnss_time        DATE,
  lon              NUMBER(9,6),
  lat              NUMBER(8,6),
  hae              NUMBER(6),
  vel              NUMBER(6,2),
  dir              NUMBER(4),
  mileage          NUMBER(11,1),
  posmode_dic_id   NUMBER(1),
  alarm_state      VARCHAR2(5),
  is_fire          VARCHAR2(2),
  is_online        VARCHAR2(2),
  is_card          VARCHAR2(2),
  update_time      DATE,
  iseffective      VARCHAR2(2),
  flag             VARCHAR2(12),
  exception_msg    CLOB,
  msg_thread_type  NUMBER(9) default 0,
  date_create_time DATE
)
;
comment on table BAM_CARRUNTIME_INFO_TASK
  is '车辆当前位置人任务表';
comment on column BAM_CARRUNTIME_INFO_TASK.upload_degree
  is '上传次数';
comment on column BAM_CARRUNTIME_INFO_TASK.success_flag
  is '是否上传成功';
comment on column BAM_CARRUNTIME_INFO_TASK.car_number
  is '车牌号码';
comment on column BAM_CARRUNTIME_INFO_TASK.gnss_time
  is '北斗时间';
comment on column BAM_CARRUNTIME_INFO_TASK.lon
  is '经度';
comment on column BAM_CARRUNTIME_INFO_TASK.lat
  is '纬度';
comment on column BAM_CARRUNTIME_INFO_TASK.hae
  is '高度';
comment on column BAM_CARRUNTIME_INFO_TASK.vel
  is '速度';
comment on column BAM_CARRUNTIME_INFO_TASK.dir
  is '方向';
comment on column BAM_CARRUNTIME_INFO_TASK.mileage
  is '里程';
comment on column BAM_CARRUNTIME_INFO_TASK.posmode_dic_id
  is '定位模式/状态';
comment on column BAM_CARRUNTIME_INFO_TASK.alarm_state
  is '提示状态';
comment on column BAM_CARRUNTIME_INFO_TASK.is_fire
  is '是否点火';
comment on column BAM_CARRUNTIME_INFO_TASK.is_online
  is '是否在线';
comment on column BAM_CARRUNTIME_INFO_TASK.is_card
  is '是否刷卡';
comment on column BAM_CARRUNTIME_INFO_TASK.update_time
  is '更新时间';
comment on column BAM_CARRUNTIME_INFO_TASK.iseffective
  is '是否有效';
comment on column BAM_CARRUNTIME_INFO_TASK.flag
  is '标签(1 新增；2修改；3删除)';
comment on column BAM_CARRUNTIME_INFO_TASK.exception_msg
  is '异常信息';
comment on column BAM_CARRUNTIME_INFO_TASK.msg_thread_type
  is '消息类型，0代表线程1处理，1代表线程2处理,2代表线程3处理';
comment on column BAM_CARRUNTIME_INFO_TASK.date_create_time
  is '任务数据生成时间';
alter table BAM_CARRUNTIME_INFO_TASK
  add constraint PK_BAM_CARRUNTIME_INFO_TASK primary key (ID);

prompt
prompt Creating table BDM_CAR_INFO_TASK
prompt ================================
prompt
create table BDM_CAR_INFO_TASK
(
  id                    NUMBER(9) not null,
  flag                  NUMBER(9) not null,
  upload_degree         NUMBER(9) default 0,
  success_flag          NUMBER(9) default 0,
  car_info_id           NUMBER(9) not null,
  car_number            VARCHAR2(50),
  terminal_id           NUMBER(9),
  dept_info_id          NUMBER(9),
  tel                   VARCHAR2(50),
  real_mileage          NUMBER(9,1),
  scope_dic_id          NUMBER(9),
  data_upload_freq      NUMBER(5),
  is_data_upload        VARCHAR2(2),
  is_positioning        VARCHAR2(2),
  posmode_dic_id        NUMBER(9),
  ip_address_1          VARCHAR2(50),
  ip_address_2          VARCHAR2(50),
  server_type           VARCHAR2(50),
  max_speed             NUMBER(5),
  savemode_time_thres_1 NUMBER(5),
  savemode_time_thres_2 NUMBER(5),
  savemode_time_thres_3 NUMBER(5),
  savemode_time_thres_4 NUMBER(5),
  savemode_time_thres_5 NUMBER(5),
  is_usepile_onpilebad  VARCHAR2(2),
  stopfire_time_thres   NUMBER(5),
  create_time           DATE,
  create_person         VARCHAR2(50),
  last_edit_time        DATE,
  last_edit_person      VARCHAR2(50),
  car_type              VARCHAR2(50),
  car_brand             VARCHAR2(50),
  car_owner             VARCHAR2(50),
  car_reg_time          DATE,
  car_chassis_number    VARCHAR2(80),
  db_department         VARCHAR2(50),
  db_number             VARCHAR2(20),
  contact_person_name   VARCHAR2(20),
  contact_person_dept   VARCHAR2(50),
  contact_person_title  VARCHAR2(30),
  contact_person_tel    VARCHAR2(20),
  contact_person_mobile VARCHAR2(20),
  contact_person_fax    VARCHAR2(20),
  sim_number            VARCHAR2(20),
  install_time          DATE,
  city_scope            VARCHAR2(2),
  obligate1             VARCHAR2(50),
  obligate2             VARCHAR2(50),
  obligate3             VARCHAR2(50),
  obligate4             VARCHAR2(50),
  obligate5             VARCHAR2(50),
  car_status            NUMBER,
  remark                VARCHAR2(2000),
  stop_reason           VARCHAR2(2000),
  emergency_flag        CHAR(1) default 0,
  transfer_time         DATE,
  car_use_type          NUMBER(1) default 1,
  car_kind              NUMBER(2) default 0,
  exception_msg         CLOB,
  date_create_time      DATE
)
;
comment on table BDM_CAR_INFO_TASK
  is '车辆及终端信息传输任务表';
comment on column BDM_CAR_INFO_TASK.id
  is '自增列，主键';
comment on column BDM_CAR_INFO_TASK.flag
  is '标签(1 新增；2修改；3删除)';
comment on column BDM_CAR_INFO_TASK.upload_degree
  is '上传次数';
comment on column BDM_CAR_INFO_TASK.success_flag
  is '是否上传成功';
comment on column BDM_CAR_INFO_TASK.car_info_id
  is '车辆表数据id';
comment on column BDM_CAR_INFO_TASK.car_number
  is '车辆表车牌号码';
comment on column BDM_CAR_INFO_TASK.terminal_id
  is '终端ID';
comment on column BDM_CAR_INFO_TASK.dept_info_id
  is '所属单位，车辆使用单位';
comment on column BDM_CAR_INFO_TASK.tel
  is '车辆联系电话';
comment on column BDM_CAR_INFO_TASK.real_mileage
  is '初始里程表读数';
comment on column BDM_CAR_INFO_TASK.scope_dic_id
  is '限行区域';
comment on column BDM_CAR_INFO_TASK.data_upload_freq
  is '数据上传频度';
comment on column BDM_CAR_INFO_TASK.is_data_upload
  is '是否上传数据';
comment on column BDM_CAR_INFO_TASK.is_positioning
  is '是否定位';
comment on column BDM_CAR_INFO_TASK.posmode_dic_id
  is '定位模式（1：北斗；2：GPS；3：组合）';
comment on column BDM_CAR_INFO_TASK.ip_address_1
  is '管理中心IP地址';
comment on column BDM_CAR_INFO_TASK.ip_address_2
  is '管理中心备份IP地址';
comment on column BDM_CAR_INFO_TASK.server_type
  is '管理中心服务器类型';
comment on column BDM_CAR_INFO_TASK.max_speed
  is '超速报警门限值';
comment on column BDM_CAR_INFO_TASK.savemode_time_thres_1
  is '节能模式1时间门限值';
comment on column BDM_CAR_INFO_TASK.savemode_time_thres_2
  is '节能模式2时间门限值';
comment on column BDM_CAR_INFO_TASK.savemode_time_thres_3
  is '节能模式3时间门限值';
comment on column BDM_CAR_INFO_TASK.savemode_time_thres_4
  is '节能模式4时间门限值';
comment on column BDM_CAR_INFO_TASK.savemode_time_thres_5
  is '节能模式5时间门限值';
comment on column BDM_CAR_INFO_TASK.is_usepile_onpilebad
  is '电池异常时是否使用电池';
comment on column BDM_CAR_INFO_TASK.stopfire_time_thres
  is '停车未熄火时长';
comment on column BDM_CAR_INFO_TASK.create_time
  is '录入时间';
comment on column BDM_CAR_INFO_TASK.create_person
  is '录入人';
comment on column BDM_CAR_INFO_TASK.last_edit_time
  is '编辑时间';
comment on column BDM_CAR_INFO_TASK.last_edit_person
  is '最后编辑人';
comment on column BDM_CAR_INFO_TASK.car_type
  is '车辆类型';
comment on column BDM_CAR_INFO_TASK.car_brand
  is '品牌型号';
comment on column BDM_CAR_INFO_TASK.car_owner
  is '所有人';
comment on column BDM_CAR_INFO_TASK.car_reg_time
  is '注册时间';
comment on column BDM_CAR_INFO_TASK.car_chassis_number
  is '车辆识别代号（车架号）';
comment on column BDM_CAR_INFO_TASK.db_department
  is '定编单位';
comment on column BDM_CAR_INFO_TASK.db_number
  is '定编证号码';
comment on column BDM_CAR_INFO_TASK.contact_person_name
  is '联系人姓名';
comment on column BDM_CAR_INFO_TASK.contact_person_dept
  is '联系人所属部门';
comment on column BDM_CAR_INFO_TASK.contact_person_title
  is '联系人行政职务';
comment on column BDM_CAR_INFO_TASK.contact_person_tel
  is '联系人办公电话';
comment on column BDM_CAR_INFO_TASK.contact_person_mobile
  is '联系人手机';
comment on column BDM_CAR_INFO_TASK.contact_person_fax
  is '联系人传真';
comment on column BDM_CAR_INFO_TASK.sim_number
  is 'SIM卡号';
comment on column BDM_CAR_INFO_TASK.install_time
  is '安装时间';
comment on column BDM_CAR_INFO_TASK.city_scope
  is '是否限制广州界';
comment on column BDM_CAR_INFO_TASK.obligate1
  is '预留字段(排量)';
comment on column BDM_CAR_INFO_TASK.obligate2
  is '预留字段';
comment on column BDM_CAR_INFO_TASK.obligate3
  is '预留字段';
comment on column BDM_CAR_INFO_TASK.obligate4
  is '预留字段';
comment on column BDM_CAR_INFO_TASK.obligate5
  is '预留字段';
comment on column BDM_CAR_INFO_TASK.car_status
  is '车辆状态，0表示在用，1表示报废';
comment on column BDM_CAR_INFO_TASK.remark
  is '备注';
comment on column BDM_CAR_INFO_TASK.stop_reason
  is '停用原因';
comment on column BDM_CAR_INFO_TASK.emergency_flag
  is '空气污染应急状态（0:无；1:一级响应；2:二级响应；3:一级和二级响应），默认0';
comment on column BDM_CAR_INFO_TASK.transfer_time
  is '三公监察系统交换时间';
comment on column BDM_CAR_INFO_TASK.car_use_type
  is '车辆用途：1－其他用车；2－机要通信应急用车；3－执法执勤用车(警车除外)；4－特种专业技术用车';
comment on column BDM_CAR_INFO_TASK.car_kind
  is '车辆种类';
comment on column BDM_CAR_INFO_TASK.exception_msg
  is '异常信息';
comment on column BDM_CAR_INFO_TASK.date_create_time
  is '任务数据生成时间';
alter table BDM_CAR_INFO_TASK
  add constraint PK_BDM_CAR_INFO_TASK primary key (ID);
create index INX_BDM_CAR_INFO_TASK_DEPT_ID on BDM_CAR_INFO_TASK (DEPT_INFO_ID);
create index INX_CAR_TASK_CAR_NUMBER on BDM_CAR_INFO_TASK (CAR_NUMBER);

prompt
prompt Creating table BDM_DEPT_INFO_TASK
prompt =================================
prompt
create table BDM_DEPT_INFO_TASK
(
  id                NUMBER(9) not null,
  flag              NUMBER(9) not null,
  upload_degree     NUMBER(9) default 0,
  success_flag      NUMBER(9) default 0,
  dept_info_id      NUMBER(9) not null,
  admin_dic_id      NUMBER(9),
  dept_name         VARCHAR2(50),
  manager_name      VARCHAR2(50),
  manager_mobile    VARCHAR2(20),
  supervisor_name   VARCHAR2(20),
  supervisor_mobile VARCHAR2(20),
  leader1_name      VARCHAR2(20),
  leader2_name      VARCHAR2(20),
  leader3_name      VARCHAR2(20),
  tel_allday        VARCHAR2(20),
  tel               VARCHAR2(20),
  address           VARCHAR2(500),
  create_time       DATE,
  create_person     VARCHAR2(20),
  last_edit_time    VARCHAR2(20),
  last_edit_person  VARCHAR2(20),
  leader1_mobile    VARCHAR2(20),
  leader2_mobile    VARCHAR2(20),
  leader3_mobile    VARCHAR2(20),
  remark            VARCHAR2(20),
  iscomplete        VARCHAR2(2) default '0',
  dept_type         NUMBER default 0,
  dept_level        NUMBER(4),
  duty_area         VARCHAR2(1),
  areacode          NUMBER,
  exception_msg     CLOB,
  date_create_time  DATE
)
;
comment on table BDM_DEPT_INFO_TASK
  is '单位基础信息任务表';
comment on column BDM_DEPT_INFO_TASK.id
  is '自增列，主键';
comment on column BDM_DEPT_INFO_TASK.flag
  is '标签(1 新增；2修改；3删除)';
comment on column BDM_DEPT_INFO_TASK.upload_degree
  is '上传次数';
comment on column BDM_DEPT_INFO_TASK.success_flag
  is '是否上传成功';
comment on column BDM_DEPT_INFO_TASK.dept_info_id
  is '自增列，主键';
comment on column BDM_DEPT_INFO_TASK.admin_dic_id
  is '单位行政所属';
comment on column BDM_DEPT_INFO_TASK.dept_name
  is '单位名称';
comment on column BDM_DEPT_INFO_TASK.manager_name
  is '联系人';
comment on column BDM_DEPT_INFO_TASK.manager_mobile
  is '联系人电话';
comment on column BDM_DEPT_INFO_TASK.supervisor_name
  is '分管领导';
comment on column BDM_DEPT_INFO_TASK.supervisor_mobile
  is '分管领导电话';
comment on column BDM_DEPT_INFO_TASK.leader1_name
  is '主要领导姓名1';
comment on column BDM_DEPT_INFO_TASK.leader2_name
  is '主要领导姓名2';
comment on column BDM_DEPT_INFO_TASK.leader3_name
  is '主要领导姓名3';
comment on column BDM_DEPT_INFO_TASK.tel_allday
  is '24小时联系电话';
comment on column BDM_DEPT_INFO_TASK.tel
  is '联系电话';
comment on column BDM_DEPT_INFO_TASK.address
  is '地址';
comment on column BDM_DEPT_INFO_TASK.create_time
  is '录入时间';
comment on column BDM_DEPT_INFO_TASK.create_person
  is '录入人';
comment on column BDM_DEPT_INFO_TASK.last_edit_time
  is '编辑时间';
comment on column BDM_DEPT_INFO_TASK.last_edit_person
  is '最后编辑人';
comment on column BDM_DEPT_INFO_TASK.leader1_mobile
  is '主要领导手机1';
comment on column BDM_DEPT_INFO_TASK.leader2_mobile
  is '主要领导手机2';
comment on column BDM_DEPT_INFO_TASK.leader3_mobile
  is '主要领导手机3';
comment on column BDM_DEPT_INFO_TASK.remark
  is '备注';
comment on column BDM_DEPT_INFO_TASK.dept_level
  is '排序所用';
comment on column BDM_DEPT_INFO_TASK.duty_area
  is '行政区域划分(市直/区县)';
comment on column BDM_DEPT_INFO_TASK.areacode
  is '行政区代码';
comment on column BDM_DEPT_INFO_TASK.exception_msg
  is '异常信息';
comment on column BDM_DEPT_INFO_TASK.date_create_time
  is '任务数据生成时间';
alter table BDM_DEPT_INFO_TASK
  add constraint PK_BDM_DEPT_INFO_TASK primary key (ID);


spool off
