-- 更新车辆和终端
update BDM_CAR_INFO t set t.tel=t.tel;

-- 更新司机
update BDM_DRIVER t set t.mobile=t.mobile;

--更新实时信息
update BAM_CARRUNTIME_INFo t set t.vel=t.vel;



-- 更新机构
update bdm_dept_info set dept_name=dept_name  
where dept_name not like '__区' and    dept_info_id not in (1167,1267) and dept_info_id not in(
select dept_info_id
  from bdm_dept_info i
 where i.dept_info_id in
       (select child_dept_id
          from bam_dept_relation
         start with parent_dept_id = '228'
        connect by prior child_dept_id = parent_dept_id)
);





select * from bdm_dept_info where dept_name  like '__区' or    dept_info_id  in (1167,1267)
or dept_info_id  in(
select dept_info_id
  from bdm_dept_info i
 where i.dept_info_id in
       (select child_dept_id
          from bam_dept_relation
         start with parent_dept_id = '228'
        connect by prior child_dept_id = parent_dept_id)
);



