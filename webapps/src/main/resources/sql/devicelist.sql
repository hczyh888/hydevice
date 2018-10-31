#sql("select")
  select *
#end

#sql("from")
  from sys_devicelist order by createTime
#end

#sql("getByAccount")
 select * from sys_devicelist where account = #para(account) limit 1
#end

#sql("fromWhere")
  from (SELECT dl.* ,dc.`name` className,p.companyName
        from sys_devicelist dl
        LEFT JOIN sys_deviceclass dc on dl.classId=dc.id
        left JOIN sys_partner p on dl.otherCompanyID = p.id
 ) as hydevicelist
  where 1=1
#end