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
  from( SELECT a.*,d.cnName deviceCnName,d.enName deviceEnName,b.useWeek ,useYear,charge,income,cost,recoveryTime,sampleNumber FROM plan_apply a LEFT JOIN sys_devicelist d ON a.deviceId = d.id
 LEFT JOIN plan_applybenefit b ON a.id = b.applyId) AS hyapply WHERE 1 = 1
#end


