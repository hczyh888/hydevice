#sql("select")
  select *
#end


#sql("from")
  from stock_enterstock_detail order by id
#end

#sql("fromWhere")
 from(SELECT e.*

FROM stock_enterstock_detail e ,stock_enterstock_detail c

WHERE e.enterId=c.id) as hysss where 1=1
#end

#sql("selectByEnterId")
SELECT * from stock_enterstock_detail where enterId = #para(enterId)
#end