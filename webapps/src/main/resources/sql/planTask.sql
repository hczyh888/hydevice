#sql("select")
select *
#end

#sql("from")
from plan_apply order by id
#end

#sql("getByAccount")
select * from plan_apply where account = #para(account) limit 1
#end

#sql("fromWhere")
  from plan_task where 1=1
#end


