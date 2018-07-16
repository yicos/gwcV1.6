----------------------------------------------------
-- Export file for user GWCDB                     --
-- Created by Administrator on 2018-7-16, 9:28:44 --
----------------------------------------------------

spool seq.log

prompt
prompt Creating sequence SEQ_BAM_CARRUNTIME_INFO_TASK
prompt ==============================================
prompt
create sequence SEQ_BAM_CARRUNTIME_INFO_TASK
minvalue 1
maxvalue 99999999999
start with 14826340
increment by 1
cache 20
cycle;

prompt
prompt Creating sequence SEQ_BDM_DEPT_INFO_TASK
prompt ========================================
prompt
create sequence SEQ_BDM_DEPT_INFO_TASK
minvalue 1
maxvalue 99999999999999
start with 3781
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BDM_DRIVER_TASK
prompt =====================================
prompt
create sequence SEQ_BDM_DRIVER_TASK
minvalue 1
maxvalue 99999999999999
start with 101
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CAR_INFO_TASK
prompt ===================================
prompt
create sequence SEQ_CAR_INFO_TASK
minvalue 1
maxvalue 99999999999999
start with 296801
increment by 1
cache 20;


spool off
