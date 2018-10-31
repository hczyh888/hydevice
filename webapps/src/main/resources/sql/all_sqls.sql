在此统一管理所有 sql，优点有：
1：避免在 JFinalClubConfig 中一个个添加 sql 模板文件
2：免除在实际的模板文件中书写 namespace，以免让 sql 定义往后缩进一层
3：在此文件中还可以通过 define 指令定义一些通用模板函数，供全局共享
   例如定义通用的 CRUD 模板函数

#namespace("index")
#include("index.sql")
#end

#namespace("project")
#include("project.sql")
#end

#namespace("share")
#include("share.sql")
#end

#namespace("feedback")
#include("feedback.sql")
#end

#namespace("user")
#include("user.sql")
#end

#namespace("roles")
#include("roles.sql")
#end

#namespace("dept")
#include("dept.sql")
#end

#namespace("menu")
#include("menu.sql")
#end

#namespace("operationLog")
#include("operationLog.sql")
#end

#namespace("loginLog")
#include("loginLog.sql")
#end

#namespace("dict")
#include("dict.sql")
#end

#namespace("parameter")
#include("parameter.sql")
#end

#namespace("roleExt")
#include("roleExt.sql")
#end

#namespace("deviceclass")
#include("deviceclass.sql")
#end

#namespace("devicelist")
#include("devicelist.sql")
#end

#namespace("partner")
#include("partner.sql")
#end

#namespace("company")
#include("company.sql")
#end

#namespace("apply")
#include("apply.sql")
#end

#namespace("applybenefit")
#include("applybenefit.sql")
#end

#namespace("applyinfor")
#include("applyinfor.sql")
#end

#namespace("applypartner")
#include("applypartner.sql")
#end

#namespace("warehouse")
#include("warehouse.sql")
#end

#namespace("planTask")
#include("planTask.sql")
#end

#namespace("planTaskdetail")
#include("planTaskdetail.sql")
#end

#namespace("enterstock")
#include("enterstock.sql")
#end

#namespace("enterstockdetail")
#include("enterstockdetail.sql")
#end

#namespace("leavestock")
#include("leavestock.sql")
#end

#namespace("leavestockdetail")
#include("leavestockdetail.sql")
#end

#namespace("deptstock")
#include("deptstock.sql")
#end

#namespace("stockpile")
#include("stockpile.sql")
#end

#namespace("subpart")
#include("subpart.sql")
#end

#namespace("subservice")
#include("subservice.sql")
#end

#namespace("servicerecord")
#include("servicerecord.sql")
#end


#namespace("repair")
#include("repair.sql")
#end

#namespace("install")
#include("install.sql")
#end

#namespace("upkeep")
#include("upkeep.sql")
#end

#namespace("inoutrecord")
#include("inoutrecord.sql")
#end

#namespace("purchaseTask")
#include("purchaseTask.sql")
#end

#namespace("allot")
#include("allot.sql")
#end

#namespace("purchaseTaskDetail")
#include("purchaseTaskDetail.sql")
#end
