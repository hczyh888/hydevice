#sql("select")
  select *
#end

#sql("from")
  from sys_operation_log order by id
#end

#sql("fromWhere")
  from sys_operation_log where 1=1
#end