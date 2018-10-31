#sql("select")
  select *
#end

#sql("treeSelect")

#end

#sql("from")
  from stock_stockpile_subpart order by id
#end

#sql("fromWhere")
  from stock_stockpile_subpart as hydict where 1=1
#end
