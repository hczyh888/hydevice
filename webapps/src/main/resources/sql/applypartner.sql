#sql("select")
select *
#end

#sql("from")
from plan_applybenefit order by createtime
#end

#sql("getByAccount")
select * from plan_applypartner where account = #para(account) limit 1
#end

#sql("fromWhere")
from plan_applypartner where 1=1
#end