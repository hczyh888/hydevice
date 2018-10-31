#sql("select")
  select *
#end


#sql("from")
  from stock_leavestock_detail order by id
#end

#sql("fromWhere")
 from  (SELECT e.*

FROM stock_leavestock_detail e, stock_leavestock_detail c

WHERE e.leaveId=c.id)as hysss where 1=1
#end

#sql("selectByLeaveId")
SELECT * from stock_leavestock_detail where leaveId = #para(leaveId)
#end