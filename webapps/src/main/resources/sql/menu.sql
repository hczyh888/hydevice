#sql("select")
  select *
#end

#sql("from")
  from sys_menu order by num
#end

#sql("getByRole")
  select id,code,url from sys_menu
 where status=1 and url is not null
	      and (id in (select menuId from sys_relation where roleId in (#para(roleId)) or id in (#para(roleIn)))
	      and id not in( #para(roleOut) );

 order by levels,pCode,num
#end

#sql("fromWhere")
  from sys_menu where 1=1
#end

#sql("getMenuSide")
  select  *  from sys_menu
    where (status = 1) and (menuType < 3) and id in(select menuId from sys_relation where roleId=#para(roleId))
    order by levels,pCode,num
#end

#sql("menuTreeListByRoleId")
    select  m.id "id",(select id FROM sys_menu WHERE CODE = m.pCode ) "pId",NAME "name",
      (CASE   WHEN m.menuType = 1 THEN   'true'   ELSE   'false'   END ) "open",
      (CASE   WHEN r.menuId IS NOT NULL THEN   'true'   ELSE   'false'   END ) "checked"
   FROM
      sys_menu m  LEFT JOIN ( SELECT menuid FROM sys_role_menu WHERE roleId =#para(roleId) GROUP BY menuid ) r ON m.id = r.menuId
   WHERE
      m.STATUS = 1
   ORDER BY m.menuType,m.num ASC
#end

#sql("queryAllMenuId")
    select distinct rm.menuId from sys_user_role ur
           LEFT JOIN sys_role_menu rm on ur.roleId = rm.roleId
           where ur.userId = #para(userId)
#end

#sql("queryListParentId")
    select * from sys_menu where pcode = #para(pcode) order by num asc
#end