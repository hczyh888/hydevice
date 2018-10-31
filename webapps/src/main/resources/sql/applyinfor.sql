#sql("select")
select *
#end

#sql("from")
from plan_applyinfor order by id
#end

#sql("getByAccount")
select * from plan_applyinfor where account = #para(account) limit 1
#end

#sql("fromWhere")
 from plan_applyinfor WHERE 1 = 1
#end


