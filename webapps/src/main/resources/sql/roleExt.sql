#sql("getByUserId")
select * from sys_role_ext where userId = #para(userId) limit 1
#end