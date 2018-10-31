#sql("select")
  select *
#end


#sql("from")
  from stock_leavestock
#end

#sql("fromWhere")
  from( select l.*,d.simpleName deptName from stock_leavestock l left join sys_dept d on l.deptId=d.id  ) as hyuser  where 1=1
#end


