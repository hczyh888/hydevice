#sql("select")
select *
#end

#sql("from")
from purchase_purchasetask order by id
#end

#sql("fromWhere")
   from( select p.*,u.name userName from purchase_purchasetask p left join sys_user u on p.userId=u.id  ) as hyPurchase  where 1=1
#end


