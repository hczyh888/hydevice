#sql("select")
  select *
#end

#sql("from")
  from sys_partner order by id
#end

#sql("getByAccount")
 select * from sys_partner where account = #para(account) limit 1
#end

#sql("fromWhere")
  from sys_partner where 1=1
#end
