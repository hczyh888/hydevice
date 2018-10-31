#sql("select")
  select *
#end


#sql("from")
  from stock_enterstock order by id
#end

#sql("fromWhere")
 from  (SELECT
			s.* ,d.enternum  num,d.deviceId device,d.price price,d.productdate productdate,d.beginTime beginTime,
			d.warrantyPeriod warrantyPeriod,l.otherCompanyID otherCompanyID,w.name housename,w.type housetype
		FROM
			stock_enterstock s
		LEFT JOIN stock_enterstock_detail d ON s.id = d.enterId
    LEFT JOIN sys_devicelist l ON s.id = l.id
    LEFT JOIN stock_warehouse w ON s.warehouseId= w.id
		)
		AS hystock where 1=1
#end

#sql("count")
SELECT count(1)

FROM stock_enterstock
#end