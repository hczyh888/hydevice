#sql("select")
  select *
#end


#sql("from")
  from stock_inoutrecord order by id
#end

#sql("fromWhere")
  from stock_inoutrecord as hystockpile  where 1=1
#end
