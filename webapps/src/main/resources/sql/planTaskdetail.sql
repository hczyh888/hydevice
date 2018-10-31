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
  from( select t.*,a.*,t1.*  from plan_taskdetail t left join plan_apply a on t.applyId=a.id
 left join plan_task t1 on t.taskId=t1.id) as hyplanTaskdetail  where 1=1
#end


