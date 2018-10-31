#sql("select")
  select *
#end

#sql("treeSelect")

#end

#sql("from")
  from sys_dict order by num
#end

#sql("fromWhere")
  from (select d1.*,d2.name dictName from sys_dict d1 LEFT JOIN sys_dict d2 on d1.pid=d2.id) as hydict where 1=1
#end

#sql("selectFirst")
  select d1.*,d2.name dictName from sys_dict d1 LEFT JOIN sys_dict d2 on d1.pid=d2.id  where d1.id=#para(dictId)
#end