#sql("select")
  select *
#end

#sql("treeSelect")

#end

#sql("from")
  from sys_dept order by num
#end

#sql("fromWhere")
 from(select d1.*,d.simpleName deptName from sys_dept d1 left join sys_dept d on d1.pid=d.id) as hydept  where 1=1
#end