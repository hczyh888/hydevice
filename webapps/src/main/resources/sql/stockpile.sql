#sql("select")
  select *
#end


#sql("from")
  from stock_stockpile order by id
#end

#sql("fromWhere")
  from( select s.*,d.cnName deviceName,d.brand brandName,d.type TypeName,d.unit unitName,d1.name className,d1.simpleCode codeName,d1.status statusName,w.name warehouseName,w.type typesName,w.name nameName,w.place placeName, d2.agency agencyName,dd.companyId companyName,dd.regisNumber regisNumberName,dd.fixedAssets fixedAssetsName,dd.propertyId propertyIdName,dd.facNumber stockName,d3.simpleName deptName,u.name userName from stock_stockpile s left join sys_devicelist d on s.deviceId=d.id
      left join sys_deviceclass d1 on s.id=d1.id
      left join stock_warehouse w on s.id=w.id
			left join stock_enterstock d2 on s.enterId=d2.id
			LEFT JOIN stock_stockpile_subinfor dd ON s.id=dd.stockpileId
			LEFT JOIN sys_dept d3 ON s.deptId=d3.id
			LEFT JOIN sys_user u ON s.userId=u.id)
      as hystockpile  where 1=1
#end

#sql("exportSql")
  select * from( select s.*,d.cnName deviceName, d2.agency agencyName,dd.companyId companyName,dd.regisNumber regisNumberName,dd.fixedAssets fixedAssetsName,dd.propertyId propertyIdName,dd.facNumber stockName,d3.simpleName deptName,u.name userName from stock_stockpile s left join sys_devicelist d on s.deviceId=d.id
			left join stock_enterstock d2 on s.enterId=d2.id
			LEFT JOIN stock_stockpile_subinfor dd ON s.id=dd.stockpileId
			LEFT JOIN sys_dept d3 ON s.deptId=d3.id
			LEFT JOIN sys_user u ON s.userId=u.id)
      as hystockpile  where 1=1
#end