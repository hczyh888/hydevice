package com.hy;

import com.hy.common.controller.CacheController;
import com.hy.common.controller.GetDataController;
import com.hy.hyEquipManage.badManage.BadManageController;
import com.hy.hyEquipManage.departmentManage.DepartmentManageController;
import com.hy.hyEquipManage.dispatchManage.DispatchManageController;
import com.hy.hyEquipManage.documentManage.DocumentManageController;
import com.hy.hyEquipManage.engineerManage.EngineerManageController;
import com.hy.hyEquipManage.installManage.InstallManageController;
import com.hy.hyEquipManage.maintainManage.MaintainManageController;
import com.hy.hyEquipManage.qualityManage.QualityManageController;
import com.hy.hyEquipManage.repairManage.RepairManageController;
import com.hy.hyplan.applybenefit.ApplyBenefitController;
import com.hy.hyplan.audit.AuditController;
import com.hy.hyplan.planApply.ApplyController;
import com.hy.hyplan.planSum.PlanSumController;
import com.hy.hyplan.planTask.PlanTaskController;
import com.hy.hystock.archivesinfo.*;
import com.hy.hystock.deptstock.DeptStockController;
import com.hy.hystock.deviceallot.DeviceAllotController;
import com.hy.hystock.enterstock.EnterStockController;
import com.hy.hystock.enterstockdetail.EnterStockDetailController;
import com.hy.hystock.initstock.InitStockController;
import com.hy.hystock.leavestock.LeaveStockController;
import com.hy.hystock.leavestockdetail.LeaveStockDetailController;
import com.hy.hystock.servicerecord.ServicerecordController;
import com.hy.hystock.stockpile.FixedDevicesController;
import com.hy.hystock.stockpile.StockPileController;
import com.hy.hystock.stockpile.StockpileSubinforController;
import com.hy.hystock.subpart.SubpartController;
import com.hy.hystock.subservice.SubserviceController;
import com.hy.hystock.warehouse.WareHouseController;
import com.hy.purchase.purchaseAudit.PurchaseAuditController;
import com.hy.purchase.purchaseTask.PurchaseTaskController;
import com.hy.purchase.purchaseSum.PurchaseSumController;
import com.hy.system.company.CompanyController;
import com.hy.system.dept.DeptController;
import com.hy.system.deviceclass.DeviceclassController;
import com.hy.system.devicelist.DevicelistControler;
import com.hy.common.dict.DictController;
import com.hy.system.index.IndexController;
import com.hy.system.loginLog.LoginLogController;
import com.hy.system.menu.MenuController;
import com.hy.system.operationLog.OperationLogController;
import com.hy.system.parameter.ParameterController;
import com.hy.system.partner.PartnerController;
import com.hy.system.roles.RolesController;
import com.hy.system.user.UserController;
import com.jfinal.config.Routes;


/**
 * 前台路由
 */
public class FrontRoutes extends Routes {

	public void config() {
		setBaseViewPath("/_view");
		/*
		* 系统部分
		* */
		add("/", IndexController.class, "/system/index");
		add("/cache", CacheController.class, "/system/index");
		add("/sys/user", UserController.class, "/system/user");
		add("/sys/roles", RolesController.class, "/system/roles");
		add("/sys/dept",DeptController.class, "/system/dept");
		add("/sys/dict", DictController.class, "/system/dict");
		add("/sys/menu", MenuController.class, "/system/menu");
		add("/sys/deviceclass", DeviceclassController.class, "/system/deviceclass");
		add("/log/operationLog", OperationLogController.class, "/system/operationLog");
		add("/log/loginLog", LoginLogController.class, "/system/loginLog");
		add("/sys/devicelist", DevicelistControler.class, "/system/devicelist");
		add("/sys/partner", PartnerController.class, "/system/partner");
		add("/sys/company", CompanyController.class, "/system/company");
		add("/sys/parameter", ParameterController.class, "/system/parameter");

		/*
		* 计划部分
		* */
		add("/plan/planApply", ApplyController.class, "/plan/planApply");
		add("/plan/audit", AuditController.class,"/plan/audit");
		add("/plan/planSum", PlanSumController.class, "/plan/planSum");
		add("/plan/applyBenefit", ApplyBenefitController.class, "/plan/planApply");
		add("/plan/planTask", PlanTaskController.class, "/plan/planTask");
		add("/getData", GetDataController.class, "/getData");

		/*
		* 库房部分
		* */
		add("/stock/wareHouse", WareHouseController.class, "/stock/wareHouse");
		add("/stock/initStock", InitStockController.class,"/stock/initstock");
		add("/stock/enterStock", EnterStockController.class, "/stock/enterstock");
		add("/stock/enterstockdetail",EnterStockDetailController.class, "/stock/enterstockdetail");
		add("/stock/leaveStock", LeaveStockController.class, "/stock/leavestock");
		add("/stock/leavestockdetail", LeaveStockDetailController.class, "/stock/leavestockdetail");
		add("/stock/deptStock", DeptStockController.class, "/stock/deptstock");
		add("/stock/stockPile", StockPileController.class, "/stock/stockpile");
		add("/stock/stockpileSubinfor", StockpileSubinforController.class, "/stock/stockpileSubinfor");
		add("/stock/deviceAllot", DeviceAllotController.class, "/stock/deviceAllot");

		/*
		* 档案管理
		* */
		add("/stock/archivesInfo",ArchivesInfoController.class, "/stock/archivesinfo");
		add("/stock/fixedDevices",FixedDevicesController.class, "/stock/fixeddevices");
		add("/stock/subpart", SubpartController.class, "/stock/subpart");
		add("/stock/subservice", SubserviceController.class, "/stock/subservice");
		add("/stock/servicerecord", ServicerecordController.class, "/stock/servicerecord");
		add("/stock/repair", RepairController.class, "/stock/archivesinfo");
		add("/stock/install", InstallController.class, "/stock/archivesInfo");
		add("/stock/upkeep", UpkeepController.class, "/stock/archivesInfo");
		add("/stock/inoutrecord", InoutrecordController.class, "/stock/in" +"outrecord");

		/*
		* 采购管理
		* */
		add("/purchase/purchaseTask", PurchaseTaskController.class, "/purchase/purchaseTask");
		add("/purchase/purchaseSum", PurchaseSumController.class, "/purchase/purchasesum");
		add("/purchase/purchaseAudit", PurchaseAuditController.class,"/purchase/purchaseAudit");

		/*
		* 设备科管理
		* */
		add("/equipManage/engineerManage", EngineerManageController.class, "/equipManage/engineerManage");
		add("/equipManage/dispatchManage",DispatchManageController.class,"/equipManage/dispatchManage");
		add("/equipManage/documentManage",DocumentManageController.class, "/equipManage/documentManage");
		add("/equipManage/departmentManage",DepartmentManageController.class, "/equipManage/departmentManage");

		/*
		* 日常业务
		* */
		add("/equipManage/installManage", InstallManageController.class, "/equipManage/installManage");
		add("/equipManage/repairManage", RepairManageController.class, "/equipManage/repairManage");
		add("/equipManage/maintainManage",MaintainManageController.class, "/equipManage/maintainManage");
		add("/equipManage/qualityManage", QualityManageController.class, "/equipManage/qualityManage");
		add("/equipManage/badManage",BadManageController.class, "/equipManage/badManage");
	}
	}
