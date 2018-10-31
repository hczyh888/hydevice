#sql("select")
 select *
#end

#sql("from")
  from sys_user order by createtime
#end

#sql("getByAccount")
 select * from sys_user where account = #para(account) limit 1
#end

#sql("fromWhere")
 from( select u.*,d.simpleName deptName,r.name rolesName  from sys_user u left join sys_dept d on u.deptId=d.id
 left join sys_roles r on u.roleId=r.id)
 as hyuser  where 1=1
#end

#sql("getCount")
  select count(*) as cnt from sys_user
#end