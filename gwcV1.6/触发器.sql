----------------------------------------------------
-- Export file for user GWCDB                     --
-- Created by Administrator on 2018-7-16, 9:27:40 --
----------------------------------------------------

spool trigger.log

prompt
prompt Creating trigger TG_BAM_CARRUNTIME_INFO_CURD
prompt ============================================
prompt
CREATE OR REPLACE TRIGGER tg_bam_carruntime_info_curd
  AFTER DELETE OR INSERT OR UPDATE ON bam_carruntime_info
  FOR EACH ROW
DECLARE
  V_TYPE BAM_CARRUNTIME_INFO_task.flag%TYPE;
BEGIN
           -- 有效定位 北斗时间不能太大  车牌是以粤开头
    if nvl(:new.iseffective,0)>0 and  :new.GNSS_TIME < sysdate + 1 and :new.car_number like '粤%' then
    IF INSERTING THEN
      --INSERT触发
      V_TYPE := '1';

      insert into BAM_CARRUNTIME_INFO_task
        (ID,
         FLAG,
         Msg_Thread_Type,
         date_create_time,
         CAR_NUMBER,
         GNSS_TIME,
         LON,
         LAT,
         HAE,
         VEL,
         DIR,
         MILEAGE,
         POSMODE_DIC_ID,
         ALARM_STATE,
         IS_FIRE,
         IS_ONLINE,
         IS_CARD,
         UPDATE_TIME,
         ISEFFECTIVE)
      values
        (SEQ_BAM_CARRUNTIME_INFO_task.Nextval,
         V_TYPE,
         mod(seq_bam_carruntime_info_task.currval, 3),
         sysdate,
         :new.CAR_NUMBER,
         :new.GNSS_TIME,
         :new.LON,
         :new.LAT,
         :new.HAE,
         :new.VEL,
         :new.DIR,
         :new.MILEAGE,
         :new.POSMODE_DIC_ID,
         :new.ALARM_STATE,
         :new.IS_FIRE,
         :new.IS_ONLINE,
         :new.IS_CARD,
         :new.UPDATE_TIME,
         :new.ISEFFECTIVE);

    ELSIF UPDATING THEN
      --UPDATE触发
      V_TYPE := '2';
      insert into BAM_CARRUNTIME_INFO_task
        (ID,
         FLAG,
         Msg_Thread_Type,
         CAR_NUMBER,
         date_create_time,
         GNSS_TIME,
         LON,
         LAT,
         HAE,
         VEL,
         DIR,
         MILEAGE,
         POSMODE_DIC_ID,
         ALARM_STATE,
         IS_FIRE,
         IS_ONLINE,
         IS_CARD,
         UPDATE_TIME,
         ISEFFECTIVE)
      values
        (SEQ_BAM_CARRUNTIME_INFO_task.Nextval,
         V_TYPE,
         mod(seq_bam_carruntime_info_task.currval, 3),
         :new.CAR_NUMBER,
         sysdate,
         :new.GNSS_TIME,
         :new.LON,
         :new.LAT,
         :new.HAE,
         :new.VEL,
         :new.DIR,
         :new.MILEAGE,
         :new.POSMODE_DIC_ID,
         :new.ALARM_STATE,
         :new.IS_FIRE,
         :new.IS_ONLINE,
         :new.IS_CARD,
         :new.UPDATE_TIME,
         :new.ISEFFECTIVE);
    ELSIF DELETING THEN
      --DELETE触发
      V_TYPE := '3';
      insert into BAM_CARRUNTIME_INFO_task
        (ID,
         FLAG,
         Msg_Thread_Type,
         CAR_NUMBER,
         date_create_time,
         GNSS_TIME,
         LON,
         LAT,
         HAE,
         VEL,
         DIR,
         MILEAGE,
         POSMODE_DIC_ID,
         ALARM_STATE,
         IS_FIRE,
         IS_ONLINE,
         IS_CARD,
         UPDATE_TIME,
         ISEFFECTIVE)
      values
        (SEQ_BAM_CARRUNTIME_INFO_task.Nextval,
         V_TYPE,
         mod(seq_bam_carruntime_info_task.currval, 3),
         :old.CAR_NUMBER,
         sysdate,
         :old.GNSS_TIME,
         :old.LON,
         :old.LAT,
         :old.HAE,
         :old.VEL,
         :old.DIR,
         :old.MILEAGE,
         :old.POSMODE_DIC_ID,
         :old.ALARM_STATE,
         :old.IS_FIRE,
         :old.IS_ONLINE,
         :old.IS_CARD,
         :old.UPDATE_TIME,
         :old.ISEFFECTIVE);

    END IF;
  end if;
  --USER表示当前用户名
END;
/

prompt
prompt Creating trigger TG_BDM_CAR_INFO_CURD
prompt =====================================
prompt
CREATE OR REPLACE TRIGGER tg_bdm_car_info_curd
  AFTER DELETE OR INSERT OR UPDATE ON BDM_CAR_INFO
  FOR EACH ROW
DECLARE
  V_TYPE bdm_car_info_task.flag%TYPE;
BEGIN
  IF INSERTING THEN
    --INSERT触发
    V_TYPE := '1';

    insert into BDM_CAR_INFO_TASK
      (id,
       flag,
       car_info_id,
       car_number,
       date_create_time,
       terminal_id,
       dept_info_id,
       tel,
       real_mileage,
       scope_dic_id,
       data_upload_freq,
       is_data_upload,
       is_positioning,
       posmode_dic_id,
       ip_address_1,
       ip_address_2,
       server_type,
       max_speed,
       savemode_time_thres_1,
       savemode_time_thres_2,
       savemode_time_thres_3,
       savemode_time_thres_4,
       savemode_time_thres_5,
       is_usepile_onpilebad,
       stopfire_time_thres,
       create_time,
       create_person,
       last_edit_time,
       last_edit_person,
       car_type,
       car_brand,
       car_owner,
       car_reg_time,
       car_chassis_number,
       db_department,
       db_number,
       contact_person_name,
       contact_person_dept,
       contact_person_title,
       contact_person_tel,
       contact_person_mobile,
       contact_person_fax,
       sim_number,
       install_time,
       city_scope,
       obligate1,
       obligate2,
       obligate3,
       obligate4,
       obligate5,
       car_status,
       remark,
       stop_reason,
       emergency_flag,
       transfer_time,
       car_use_type,
       car_kind)
    values
      (SEQ_CAR_INFO_task.Nextval,
       V_TYPE,
       :new.car_info_id,
       :new.car_number,
       sysdate,
       :new.terminal_id,
       :new.dept_info_id,
       :new.tel,
       :new.real_mileage,
       :new.scope_dic_id,
       :new.data_upload_freq,
       :new.is_data_upload,
       :new.is_positioning,
       :new.posmode_dic_id,
       :new.ip_address_1,
       :new.ip_address_2,
       :new.server_type,
       :new.max_speed,
       :new.savemode_time_thres_1,
       :new.savemode_time_thres_2,
       :new.savemode_time_thres_3,
       :new.savemode_time_thres_4,
       :new.savemode_time_thres_5,
       :new.is_usepile_onpilebad,
       :new.stopfire_time_thres,
       :new.create_time,
       :new.create_person,
       :new.last_edit_time,
       :new.last_edit_person,
       :new.car_type,
       :new.car_brand,
       :new.car_owner,
       :new.car_reg_time,
       :new.car_chassis_number,
       :new.db_department,
       :new.db_number,
       :new.contact_person_name,
       :new.contact_person_dept,
       :new.contact_person_title,
       :new.contact_person_tel,
       :new.contact_person_mobile,
       :new.contact_person_fax,
       :new.sim_number,
       :new.install_time,
       :new.city_scope,
       :new.obligate1,
       :new.obligate2,
       :new.obligate3,
       :new.obligate4,
       :new.obligate5,
       :new.car_status,
       :new.remark,
       :new.stop_reason,
       :new.emergency_flag,
       :new.transfer_time,
       :new.car_use_type,
       :new.car_kind);

  ELSIF UPDATING THEN
    --UPDATE触发
    V_TYPE := '2';

    insert into BDM_CAR_INFO_TASK
      (id,
       flag,
       car_info_id,
       car_number,
       date_create_time,
       terminal_id,
       dept_info_id,
       tel,
       real_mileage,
       scope_dic_id,
       data_upload_freq,
       is_data_upload,
       is_positioning,
       posmode_dic_id,
       ip_address_1,
       ip_address_2,
       server_type,
       max_speed,
       savemode_time_thres_1,
       savemode_time_thres_2,
       savemode_time_thres_3,
       savemode_time_thres_4,
       savemode_time_thres_5,
       is_usepile_onpilebad,
       stopfire_time_thres,
       create_time,
       create_person,
       last_edit_time,
       last_edit_person,
       car_type,
       car_brand,
       car_owner,
       car_reg_time,
       car_chassis_number,
       db_department,
       db_number,
       contact_person_name,
       contact_person_dept,
       contact_person_title,
       contact_person_tel,
       contact_person_mobile,
       contact_person_fax,
       sim_number,
       install_time,
       city_scope,
       obligate1,
       obligate2,
       obligate3,
       obligate4,
       obligate5,
       car_status,
       remark,
       stop_reason,
       emergency_flag,
       transfer_time,
       car_use_type,
       car_kind)
    values
      (SEQ_CAR_INFO_task.Nextval,
       V_TYPE,
       :new.car_info_id,
       :new.car_number,
       sysdate,
       :new.terminal_id,
       :new.dept_info_id,
       :new.tel,
       :new.real_mileage,
       :new.scope_dic_id,
       :new.data_upload_freq,
       :new.is_data_upload,
       :new.is_positioning,
       :new.posmode_dic_id,
       :new.ip_address_1,
       :new.ip_address_2,
       :new.server_type,
       :new.max_speed,
       :new.savemode_time_thres_1,
       :new.savemode_time_thres_2,
       :new.savemode_time_thres_3,
       :new.savemode_time_thres_4,
       :new.savemode_time_thres_5,
       :new.is_usepile_onpilebad,
       :new.stopfire_time_thres,
       :new.create_time,
       :new.create_person,
       :new.last_edit_time,
       :new.last_edit_person,
       :new.car_type,
       :new.car_brand,
       :new.car_owner,
       :new.car_reg_time,
       :new.car_chassis_number,
       :new.db_department,
       :new.db_number,
       :new.contact_person_name,
       :new.contact_person_dept,
       :new.contact_person_title,
       :new.contact_person_tel,
       :new.contact_person_mobile,
       :new.contact_person_fax,
       :new.sim_number,
       :new.install_time,
       :new.city_scope,
       :new.obligate1,
       :new.obligate2,
       :new.obligate3,
       :new.obligate4,
       :new.obligate5,
       :new.car_status,
       :new.remark,
       :new.stop_reason,
       :new.emergency_flag,
       :new.transfer_time,
       :new.car_use_type,
       :new.car_kind);

  ELSIF DELETING THEN
    --DELETE触发
    V_TYPE := '3';
    insert into BDM_CAR_INFO_TASK
      (id,
       flag,
       car_info_id,
       car_number,
       date_create_time,
       terminal_id,
       dept_info_id,
       tel,
       real_mileage,
       scope_dic_id,
       data_upload_freq,
       is_data_upload,
       is_positioning,
       posmode_dic_id,
       ip_address_1,
       ip_address_2,
       server_type,
       max_speed,
       savemode_time_thres_1,
       savemode_time_thres_2,
       savemode_time_thres_3,
       savemode_time_thres_4,
       savemode_time_thres_5,
       is_usepile_onpilebad,
       stopfire_time_thres,
       create_time,
       create_person,
       last_edit_time,
       last_edit_person,
       car_type,
       car_brand,
       car_owner,
       car_reg_time,
       car_chassis_number,
       db_department,
       db_number,
       contact_person_name,
       contact_person_dept,
       contact_person_title,
       contact_person_tel,
       contact_person_mobile,
       contact_person_fax,
       sim_number,
       install_time,
       city_scope,
       obligate1,
       obligate2,
       obligate3,
       obligate4,
       obligate5,
       car_status,
       remark,
       stop_reason,
       emergency_flag,
       transfer_time,
       car_use_type,
       car_kind)
    values
      (SEQ_CAR_INFO_task.Nextval,
       V_TYPE,
       :old.car_info_id,
       :old.car_number,
       sysdate,
       :old.terminal_id,
       :old.dept_info_id,
       :old.tel,
       :old.real_mileage,
       :old.scope_dic_id,
       :old.data_upload_freq,
       :old.is_data_upload,
       :old.is_positioning,
       :old.posmode_dic_id,
       :old.ip_address_1,
       :old.ip_address_2,
       :old.server_type,
       :old.max_speed,
       :old.savemode_time_thres_1,
       :old.savemode_time_thres_2,
       :old.savemode_time_thres_3,
       :old.savemode_time_thres_4,
       :old.savemode_time_thres_5,
       :old.is_usepile_onpilebad,
       :old.stopfire_time_thres,
       :old.create_time,
       :old.create_person,
       :old.last_edit_time,
       :old.last_edit_person,
       :old.car_type,
       :old.car_brand,
       :old.car_owner,
       :old.car_reg_time,
       :old.car_chassis_number,
       :old.db_department,
       :old.db_number,
       :old.contact_person_name,
       :old.contact_person_dept,
       :old.contact_person_title,
       :old.contact_person_tel,
       :old.contact_person_mobile,
       :old.contact_person_fax,
       :old.sim_number,
       :old.install_time,
       :old.city_scope,
       :old.obligate1,
       :old.obligate2,
       :old.obligate3,
       :old.obligate4,
       :old.obligate5,
       :old.car_status,
       :old.remark,
       :old.stop_reason,
       :old.emergency_flag,
       :old.transfer_time,
       :old.car_use_type,
       :old.car_kind);

  END IF;

  --USER表示当前用户名
END;
/

prompt
prompt Creating trigger TG_BDM_DEPT_INFO_CURD
prompt ======================================
prompt
CREATE OR REPLACE TRIGGER tg_BDM_DEPT_INFO_curd
  AFTER DELETE OR INSERT OR UPDATE ON BDM_DEPT_INFO
  FOR EACH ROW
DECLARE
  V_TYPE BDM_DEPT_INFO_task.flag%TYPE;
BEGIN
  IF INSERTING THEN
    --INSERT触发
    V_TYPE := '1';


insert into BDM_DEPT_INFO_task
  (ID,
   FLAG,
   DEPT_INFO_ID,
   date_create_time,
   ADMIN_DIC_ID,
   DEPT_NAME,
   MANAGER_NAME,
   MANAGER_MOBILE,
   SUPERVISOR_NAME,
   SUPERVISOR_MOBILE,
   LEADER1_NAME,
   LEADER2_NAME,
   LEADER3_NAME,
   TEL_ALLDAY,
   TEL,
   ADDRESS,
   CREATE_TIME,
   CREATE_PERSON,
   LAST_EDIT_TIME,
   LAST_EDIT_PERSON,
   LEADER1_MOBILE,
   LEADER2_MOBILE,
   LEADER3_MOBILE,
   REMARK,
   ISCOMPLETE,
   DEPT_TYPE,
   DEPT_LEVEL,
   DUTY_AREA,
   AREACODE)
values
  (SEQ_BDM_DEPT_INFO_task.Nextval,
   V_TYPE,
   :new.DEPT_INFO_ID,
   sysdate,
   :new.ADMIN_DIC_ID,
   :new.DEPT_NAME,
   :new.MANAGER_NAME,
   :new.MANAGER_MOBILE,
   :new.SUPERVISOR_NAME,
   :new.SUPERVISOR_MOBILE,
   :new.LEADER1_NAME,
   :new.LEADER2_NAME,
   :new.LEADER3_NAME,
   :new.TEL_ALLDAY,
   :new.TEL,
   :new.ADDRESS,
   :new.CREATE_TIME,
   :new.CREATE_PERSON,
   :new.LAST_EDIT_TIME,
   :new.LAST_EDIT_PERSON,
   :new.LEADER1_MOBILE,
   :new.LEADER2_MOBILE,
   :new.LEADER3_MOBILE,
   :new.REMARK,
   :new.ISCOMPLETE,
   :new.DEPT_TYPE,
   :new.DEPT_LEVEL,
   :new.DUTY_AREA,
   :new.AREACODE);


  ELSIF UPDATING THEN
    --UPDATE触发
    V_TYPE := '2';


insert into BDM_DEPT_INFO_task
  (ID,
   FLAG,
   DEPT_INFO_ID,
   date_create_time,
   ADMIN_DIC_ID,
   DEPT_NAME,
   MANAGER_NAME,
   MANAGER_MOBILE,
   SUPERVISOR_NAME,
   SUPERVISOR_MOBILE,
   LEADER1_NAME,
   LEADER2_NAME,
   LEADER3_NAME,
   TEL_ALLDAY,
   TEL,
   ADDRESS,
   CREATE_TIME,
   CREATE_PERSON,
   LAST_EDIT_TIME,
   LAST_EDIT_PERSON,
   LEADER1_MOBILE,
   LEADER2_MOBILE,
   LEADER3_MOBILE,
   REMARK,
   ISCOMPLETE,
   DEPT_TYPE,
   DEPT_LEVEL,
   DUTY_AREA,
   AREACODE)
values
  (SEQ_BDM_DEPT_INFO_task.Nextval,
   V_TYPE,
   :new.DEPT_INFO_ID,
   sysdate,
   :new.ADMIN_DIC_ID,
   :new.DEPT_NAME,
   :new.MANAGER_NAME,
   :new.MANAGER_MOBILE,
   :new.SUPERVISOR_NAME,
   :new.SUPERVISOR_MOBILE,
   :new.LEADER1_NAME,
   :new.LEADER2_NAME,
   :new.LEADER3_NAME,
   :new.TEL_ALLDAY,
   :new.TEL,
   :new.ADDRESS,
   :new.CREATE_TIME,
   :new.CREATE_PERSON,
   :new.LAST_EDIT_TIME,
   :new.LAST_EDIT_PERSON,
   :new.LEADER1_MOBILE,
   :new.LEADER2_MOBILE,
   :new.LEADER3_MOBILE,
   :new.REMARK,
   :new.ISCOMPLETE,
   :new.DEPT_TYPE,
   :new.DEPT_LEVEL,
   :new.DUTY_AREA,
   :new.AREACODE);


  ELSIF DELETING THEN
    --DELETE触发
    V_TYPE := '3';

insert into BDM_DEPT_INFO_task
  (ID,
   FLAG,
   DEPT_INFO_ID,
   date_create_time,
   ADMIN_DIC_ID,
   DEPT_NAME,
   MANAGER_NAME,
   MANAGER_MOBILE,
   SUPERVISOR_NAME,
   SUPERVISOR_MOBILE,
   LEADER1_NAME,
   LEADER2_NAME,
   LEADER3_NAME,
   TEL_ALLDAY,
   TEL,
   ADDRESS,
   CREATE_TIME,
   CREATE_PERSON,
   LAST_EDIT_TIME,
   LAST_EDIT_PERSON,
   LEADER1_MOBILE,
   LEADER2_MOBILE,
   LEADER3_MOBILE,
   REMARK,
   ISCOMPLETE,
   DEPT_TYPE,
   DEPT_LEVEL,
   DUTY_AREA,
   AREACODE)
values
  (SEQ_BDM_DEPT_INFO_task.Nextval,
   V_TYPE,
   :old.DEPT_INFO_ID,
   sysdate,
   :old.ADMIN_DIC_ID,
   :old.DEPT_NAME,
   :old.MANAGER_NAME,
   :old.MANAGER_MOBILE,
   :old.SUPERVISOR_NAME,
   :old.SUPERVISOR_MOBILE,
   :old.LEADER1_NAME,
   :old.LEADER2_NAME,
   :old.LEADER3_NAME,
   :old.TEL_ALLDAY,
   :old.TEL,
   :old.ADDRESS,
   :old.CREATE_TIME,
   :old.CREATE_PERSON,
   :old.LAST_EDIT_TIME,
   :old.LAST_EDIT_PERSON,
   :old.LEADER1_MOBILE,
   :old.LEADER2_MOBILE,
   :old.LEADER3_MOBILE,
   :old.REMARK,
   :old.ISCOMPLETE,
   :old.DEPT_TYPE,
   :old.DEPT_LEVEL,
   :old.DUTY_AREA,
   :old.AREACODE);


  END IF;

  --USER表示当前用户名
END;
/

prompt
prompt Creating trigger TG_BDM_DRIVER_CURD
prompt ===================================
prompt
CREATE OR REPLACE TRIGGER tg_BDM_DRIVER_curd
  AFTER DELETE OR INSERT OR UPDATE ON BDM_DRIVER
  FOR EACH ROW
DECLARE
  V_TYPE BDM_DRIVER_task.flag%TYPE;
BEGIN
  IF INSERTING THEN
    --INSERT触发
    V_TYPE := '1';

    insert into BDM_DRIVER_task
      (ID,
       BDM_DRIVER_TASK_ID,
       FLAG,
       DRIVER_NAME,
       date_create_time,
       DEPT_ID,
       TELEPHONE,
       MOBILE,
       DRIVER_ID,
       LICENCE_ID,
       AUTHORITY,
       EFFECTIVE_TIME,
       LICENCE_TYPE,
       CREATER,
       CREATE_TIME,
       UPDATER,
       UPDATE_TIME,
       DELETE_FLAG,
       DELETER,
       DELETE_TIME)
    values
      (seq_BDM_DRIVER_task.Nextval,
       :new.id,
       V_TYPE,
       :new.DRIVER_NAME,
       sysdate,
       :new.DEPT_ID,
       :new.TELEPHONE,
       :new.MOBILE,
       :new.DRIVER_ID,
       :new.LICENCE_ID,
       :new.AUTHORITY,
       :new.EFFECTIVE_TIME,
       :new.LICENCE_TYPE,
       :new.CREATER,
       :new.CREATE_TIME,
       :new.UPDATER,
       :new.UPDATE_TIME,
       :new.DELETE_FLAG,
       :new.DELETER,
       :new.DELETE_TIME);

  ELSIF UPDATING THEN
    --UPDATE触发
    V_TYPE := '2';

    insert into BDM_DRIVER_task
      (ID,
       BDM_DRIVER_TASK_ID,
       FLAG,
       DRIVER_NAME,
       date_create_time,
       DEPT_ID,
       TELEPHONE,
       MOBILE,
       DRIVER_ID,
       LICENCE_ID,
       AUTHORITY,
       EFFECTIVE_TIME,
       LICENCE_TYPE,
       CREATER,
       CREATE_TIME,
       UPDATER,
       UPDATE_TIME,
       DELETE_FLAG,
       DELETER,
       DELETE_TIME)
    values
      (seq_BDM_DRIVER_task.Nextval,
       :new.id,
       V_TYPE,
       :new.DRIVER_NAME,
       sysdate,
       :new.DEPT_ID,
       :new.TELEPHONE,
       :new.MOBILE,
       :new.DRIVER_ID,
       :new.LICENCE_ID,
       :new.AUTHORITY,
       :new.EFFECTIVE_TIME,
       :new.LICENCE_TYPE,
       :new.CREATER,
       :new.CREATE_TIME,
       :new.UPDATER,
       :new.UPDATE_TIME,
       :new.DELETE_FLAG,
       :new.DELETER,
       :new.DELETE_TIME);

  ELSIF DELETING THEN
    --DELETE触发
    V_TYPE := '3';
    insert into BDM_DRIVER_task
      (ID,
       BDM_DRIVER_TASK_ID,
       FLAG,
       DRIVER_NAME,
       date_create_time,
       DEPT_ID,
       TELEPHONE,
       MOBILE,
       DRIVER_ID,
       LICENCE_ID,
       AUTHORITY,
       EFFECTIVE_TIME,
       LICENCE_TYPE,
       CREATER,
       CREATE_TIME,
       UPDATER,
       UPDATE_TIME,
       DELETE_FLAG,
       DELETER,
       DELETE_TIME)
    values
      (seq_BDM_DRIVER_task.Nextval,
       :old.id,
       V_TYPE,
       :old.DRIVER_NAME,
       sysdate,
       :old.DEPT_ID,
       :old.TELEPHONE,
       :old.MOBILE,
       :old.DRIVER_ID,
       :old.LICENCE_ID,
       :old.AUTHORITY,
       :old.EFFECTIVE_TIME,
       :old.LICENCE_TYPE,
       :old.CREATER,
       :old.CREATE_TIME,
       :old.UPDATER,
       :old.UPDATE_TIME,
       :old.DELETE_FLAG,
       :old.DELETER,
       :old.DELETE_TIME);

  END IF;

  --USER表示当前用户名
END;
/


spool off
