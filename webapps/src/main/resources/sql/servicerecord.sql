#sql("select")
  select *
#end

#sql("treeSelect")

#end

#sql("from")
  from stock_stockpile_servicerecord order by id
#end

#sql("fromWhere")
  from stock_stockpile_servicerecord as hydict where 1=1
#end
