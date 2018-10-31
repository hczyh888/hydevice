#sql("select")
   select *
#end

#sql("from")
   from sys_parameter order by id
#end

#sql("where")
   where status = ? and code= ?
#end

#sql("getByCode")
select para from sys_parameter where code = #para(code)
#end

#sql("fromWhere")
  from sys_parameter where 1=1
#end

#sql("updateValue")
  update sys_parameter set para=#para(v) where num=#para(num) and code=#para(code)
#end