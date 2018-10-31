#sql("select")
  select *
#end

#sql("from")
  from sys_deviceclass order by id
#end

#sql("getByAccount")
 select * from sys_deviceclass where account = #para(account) limit 1
#end

#sql("fromWhere")
  from(select d1.*,d.name deviceclassName from sys_deviceclass d1 left join sys_deviceclass d on d1.pid=d.id ) as hydeviceclass
  where 1=1
#end