
package com.hy.common.controller;

import com.hy.common.constant.Const;
import com.hy.common.constant.ConstCache;
import com.hy.common.enumresource.EnumBean;
import com.hy.common.enumresource.EnumUtils;
import com.hy.common.enumresource.StateEnum;
import com.hy.common.excelKit.ExportExcelTemplateKit;
import com.hy.common.model.Account;
import com.hy.common.utils.EnumMessage;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Record;
import com.hy.common.dict.DictService;
import java.util.List;
import java.util.Map;

/**
 * 基础控制器，方便获取登录信息
 *
 * 注意：
 * 需要 LoginSessionInterceptor 配合，该拦截器使用
 * setAttr("loginAccount", ...) 事先注入了登录账户
 * 否则即便已经登录，该控制器也会认为没有登录
 *
 */
public class BaseController extends Controller implements Const,ConstCache {
	DictService dictService = DictService.me;
	public boolean isAjax(){
		String header = getRequest().getHeader("X-Requested-With");
		boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
		return isAjax;
	}

	private Account loginAccount = null;

	public Account getLoginAccount() {
		/*if (loginAccount == null) {
			loginAccount = getAttr(LoginService.loginAccountCacheName);
			if (loginAccount != null && ! loginAccount.isStatusOk()) {
				throw new IllegalStateException("当前用户状态不允许登录，status = " + loginAccount.getStatus());
			}
		}*/
		return loginAccount;
	}

	public boolean isLogin() {
		return getLoginAccount() != null;
	}

	public boolean notLogin() {
		return !isLogin();
	}

	/**
	 * 获取登录账户id
	 * 确保在 FrontAuthInterceptor 之下使用，或者 isLogin() 为 true 时使用
	 * 也即确定已经是在登录后才可调用
	 */
	public int getLoginAccountId() {
		return getLoginAccount().getId();
	}
	public void exportToExcel(String path, String resultName,Map<String,Object> map){
		try{
			ExportExcelTemplateKit.renderExcelTempl(this.getResponse(),path, resultName,map);
		}catch (Exception ex){
		}
	}

	/**
	 * 获取字典库的值传前端页面
	 * @param dictPcode
	 * @return
	 */
	public Ret getDictListByCache(String dictPcode){
		//获取单位列表传前端页面
		List<Record> records = dictService.getDictListByCache(dictPcode);
		Ret ret = Ret.create();
		for(Record rd :records){
			ret.set("id_"+rd.get("ID"),rd.get("TEXT"));
		}
		return ret;
	}
	public Ret getEnumRet(String enumName){
		List<EnumBean> lst = EnumUtils.EnumToList(enumName);
		Ret ret = Ret.create();
		//获取状态列表传前端页面
		for(EnumBean enumBean :lst){
			ret.set("id_"+enumBean.getCode(),enumBean.getValue());
		}
		return ret;
	}
}


