#sql("select")
  select *
#end

#sql("from")
  from sys_roles order by num
#end

#sql("fromWhere")
 from( select r1.*,d.simpleName deptName,r2.name rolesName  from sys_roles r1 left join sys_dept d on r1.deptId=d.id
 left join sys_roles r2 on r1.pid=r2.id) as hyroles  where 1=1
#end
