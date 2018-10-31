#sql("select")
  select *
#end

#sql("from")
  from sys_login_log order by userId
#end

#sql("fromWhere")
  from sys_login_log where 1=1
#end

#sql("exportSql")
  select * from sys_login_log order by createtime
#end