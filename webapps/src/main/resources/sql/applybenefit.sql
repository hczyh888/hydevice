#sql("select")
select *
#end

#sql("from")
from plan_applybenefit order by createtime
#end

#sql("getByAccount")
select * from plan_applybenefit where account = #para(account) limit 1
#end

#sql("fromWhere")
from plan_applybenefit where 1=1
#end